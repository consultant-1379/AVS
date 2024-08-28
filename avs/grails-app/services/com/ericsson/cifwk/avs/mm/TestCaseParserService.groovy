package com.ericsson.cifwk.avs.mm

import com.ericsson.cifwk.avs.ActionPoint
import com.ericsson.cifwk.avs.TestCase
import com.ericsson.cifwk.avs.VerificationPoint
/**
 * Service to parse Test Case node from Mindmap
 * @author erafkos
 *
 */
class TestCaseParserService {

	static final String DESCRIPTION = "DESCRIPTION"
	static final String PRIORITY = "PRIORITY"
	static final String GROUP = "GROUP"
	static final String COMPONENT = "COMPONENT"
	static final String PRE = "PRE"
	static final String VUSERS = "VUSERS"
	static final String CONTEXT = "CONTEXT"

	static final List<String> MANDATORY_FIELDS= [DESCRIPTION, PRIORITY, GROUP,COMPONENT,PRE,VUSERS,CONTEXT]
	/**
	 * Parse all specified mandatory fields and put it in a map
	 * @param tcNode
	 * @return
	 */
	def parseMandatoryFields(def tcNode){
		Map result = [:]
		MANDATORY_FIELDS.each { field ->
			result[field]=getFieldFromTestCase(tcNode, field)
		}
		return result
	}

	/**
	 * Get a node of Minmap and remove a prefix if specified
	 * @param tcFieldNode
	 * @return
	 */
	private String getFieldWithSubNodes(def tcFieldNode, String optionalPrefixToRemove=null){
		String result
		
		if (optionalPrefixToRemove)
			result = getTextFromNode(tcFieldNode,true)[optionalPrefixToRemove.size()+1..getTextFromNode(tcFieldNode).size()-1].trim()
		else
			result = getTextFromNode(tcFieldNode, true)

		println("Parsed field $result")
		return result
	}
	/**
	 * Get a field specified by name name
	 * @param tcNode
	 * @param tcFieldName
	 * @return
	 */
	private String getFieldFromTestCase(def tcNode, String tcFieldName){
		def tcFieldNode = tcNode.node.find { getTextFromNode(it).toLowerCase().startsWith(tcFieldName.toLowerCase()) }
		if (tcFieldNode)
			return getFieldWithSubNodes(tcFieldNode, tcFieldName)
		else return ""
	}

	/**
	 * Add mandatory field to Test Case with filtering for group
	 * @param tc
	 * @param fields
	 */
	void addMandatoryFields(TestCase tc, Map fields){
		MANDATORY_FIELDS.each { field ->
			if (field == GROUP)
				tc.addGroup(fields.get(field))
			else
				tc."${field.toLowerCase()}" = fields.get(field)
		}
	}
/**
 * Wrapping method for parsing mandatory fields from minmap and putting it into the Test Case
 * @param tc
 * @param tcNode
 */
	void parseAndPopulateMandatoryFields(TestCase tc, def tcNode){
		addMandatoryFields(tc, parseMandatoryFields(tcNode))
	}
/**
 * Verify if field is mandatory
 * @param tcNode
 * @return
 */
	boolean isMandatoryField(def tcNode){
		boolean result = false
		MANDATORY_FIELDS.each {
			result = result || getTextFromNode(tcNode).toLowerCase().startsWith(it.toLowerCase())
		}
		return result
	}

	/**
	 * Parse Action Points from Test Case node and put it in the Test Ase
	 * @param tc
	 * @param tcNode
	 */
	void parseAndPopulateActionPoints(TestCase tc, def tcNode){
		tcNode.node.findAll { ! isMandatoryField(it)}.each{ ap ->
			String apDescription = getTextFromNode(ap)
			ActionPoint newActionPoint = new ActionPoint(description: apDescription)
			ap.node.each {
				newActionPoint.addToVerificationPoints(new VerificationPoint(description:getFieldWithSubNodes(it)))
			}
			tc.addToActionPoints(newActionPoint)
		}
	}

	/**
	 * Helper method to get TEXT field from mindmap node optionally including all subnodes as part of the text
	 * @param rootNode
	 * @return
	 */
	String getTextFromNode(def rootNode,boolean includeSubNodes=false){
		String result = rootNode.@TEXT
		result.trim()
		if (includeSubNodes){
			rootNode.node.each {
				result += """
${getTextFromNode(it)}"""
			}
		}
		return result
	}
}
