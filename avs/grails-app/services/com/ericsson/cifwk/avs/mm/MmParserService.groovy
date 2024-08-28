package com.ericsson.cifwk.avs.mm

import com.ericsson.cifwk.avs.Requirement
import com.ericsson.cifwk.avs.TestCase

/**
 * Class for parsing Mindmap
 * @author erafkos
 *
 */
class MmParserService {

	def jiraService
	def testCaseIdGeneratorService
	def testCaseParserService
	
	/**
	 * Parse the mindmap file into List of Test Cases
	 * @param mmFile
	 * @return
	 */
	def parseFile(String mmFile) {
		List<TestCase> result = []
		def mmMap = new XmlSlurper().parseText(mmFile)
		println mmMap.node.each { story ->
			String currentRequirement = testCaseParserService.getTextFromNode(story)
			assert jiraService.checkStory(currentRequirement) : "Problem with finding JIRA Story"
			story.node.each { type ->
				String currentType = testCaseParserService.getTextFromNode(type)
				type.node.each { testCase ->
					String tcTitle = testCaseParserService.getTextFromNode(testCase)
					if (tcTitle.size() > 0){
						String tcId = testCaseIdGeneratorService.generateTestCaseId(currentRequirement, currentType)
						println("Parsing test case $tcTitle with id $tcId from type $currentType for $currentRequirement")
						def tc = new TestCase([tcId:tcId,title:tcTitle,type:currentType])
						testCaseParserService.parseAndPopulateMandatoryFields(tc, testCase)
						if (! tc.save()) { tc.errors.each {println it} ; assert false}
						try{
							def req = Requirement.findByJiraId(currentRequirement)
							if (!req)
								req = new Requirement(jiraId:currentRequirement)
							tc.addToRequirement(req)
						} catch (Exception e) {
							e.printStackTrace()
						}
						testCaseParserService.parseAndPopulateActionPoints(tc, testCase)
						result << tc
					}
				}
			}
		}
		return result
	}
}
