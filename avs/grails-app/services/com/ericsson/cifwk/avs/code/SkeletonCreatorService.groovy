package com.ericsson.cifwk.avs.code

import com.ericsson.cifwk.avs.Avs
import com.ericsson.cifwk.avs.TestCase
/**
 * Class to generate skeleton groovy file based on Test Case
 * @author erafkos
 *
 */
class SkeletonCreatorService {
	/**
	 * Static class for all the basic imports
	 */
	static final List<String> IMPORT_FILES = [
		"import org.testng.annotations.Test",
		"import com.ericsson.cifwk.taf.TorTestCaseHelper",
		"import se.ericsson.jcat.fw.annotations.Setup"
	]
	/**
	 * Create method base on Test Case information Action Points and Verification Points
	 * @param tc
	 * @return
	 */
	def createTestCaseMethod(TestCase tc){
		String tcMethod= """
		/**
		/* ${tc.title}
		/* @DESCRIPTION ${tc.description}
		/* @PRE ${tc.pre}
		/* @PRIORITY ${tc.priority}
		*/
		@Test (groups = ${formatTcGroups(tc.groups)})
		void ${formatTitle(tc.title)}(){
			setTestcase("${tc.tcId}","${tc.title.replaceAll(/"/,"'")}")
		
		${formatActionPoints(tc)}

			throw new TestCaseNotImplementedException()
		}
		"""


	}
	/**
	 * Format groups part of test case annotation specified in Test Case 
	 * @param tcGroups
	 * @return
	 */
	private String formatTcGroups(String tcGroups){
		String result = """["""
		tcGroups.split(",").each{
			if (it.size() > 0)
				result += "\"$it\""
		}
		result += "]"
	}
	/**
	 * Format title of Test CAse in camel notation
	 * @param tcTitle
	 * @return
	 */
	private String formatTitle(String tcTitle){
		String result = tcTitle.
				replaceAll(/\W[A-z]/){
					it.toUpperCase().trim()
				}.replaceAll(/\W/,"").
				replaceAll(/^\w/){it.toLowerCase()}
		return result
	}
	/**
	 * Format action Point as Test Step including Verification Points as Test Steps as well
	 * @param tc
	 * @return
	 */
	private String formatActionPoints(TestCase tc){
		String result = """"""
		tc.actionPoints.each { ap ->
			result += """
			setTestStep("${ap.description.replaceAll(/"/,"'")}")
			//TODO ${ap.description}

			"""
			ap.verificationPoints.each { vp ->
				result += """setTestStep("${vp.description}")
				//TODO ${vp.description}

			"""
			}
		}
		return result
	}

	/**
	 * Format imports based on static imports
	 * @return
	 */
	private String formatImports(){
		IMPORT_FILES.collect { return """import $it
"""}.join()
	}

	/**
	 * Create Prefix of component file
	 * @param component
	 * @return
	 */
	def createFilePrefix(String component){
		String result = """
${formatImports()}
/**
*
*	Class to execute tests against $component
**/
class ${formatTitle(component).replaceAll(/^\w/){it.toUpperCase()}} extends TorTestCaseHelper {

		"""
	}
	/**
	 * Create postfix of component fir
	 * @return
	 */
	def createFilePostfix(){
		return """
}"""
	}
	/**
	 * Create setup method based on PRE node of Test Case
	 * @param tc
	 * @return
	 */
	def createSetupMethod(TestCase tc){
		return """
		@Setup void prepareTestCaseFor${tc.tcId.replaceAll(/\W/,"")}(){
			//TODO ${tc.pre}
		} 
"""
	}
	/**
	 * Create file for component based on all test cases in AVS
	 * @param avs
	 * @param component
	 * @return
	 */
	def createComponentFile(Avs avs,String component){
		String result = createFilePrefix(component)
		avs.testCases.findAll { it.component == component}.each {
			result += createSetupMethod(it)
			result += createTestCaseMethod(it)
		}
		result += createFilePostfix()
		return result

	}
	/**
	 * Create all files required for AVS based on components
	 * @param avs
	 * @return
	 */
	def createFilesForAvs(Avs avs){
		List componentFiles = []
		List components = avs.testCases.collect { it.component}.unique()
		components.each {
			componentFiles << createComponentFile(avs, it)
		}
	}
}
