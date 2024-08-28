package com.ericsson.cifwk.avs.mm

import com.ericsson.cifwk.avs.TestCase
/**
 * Class for generating ID for a Test Case
 * @author erafkos
 *
 */
class TestCaseIdGeneratorService {

	static final String FUNC = "Feature"
	static final String INTEG = "Integration"
	static final String LOAD = "Load"
	static final String PERF = "Performance"
	static final String SEC = "Security"
	
	static final Map<String,String> TYPE_IDS = ["$FUNC":'FUNC',"$INTEG":'INTEG',"$LOAD":'LOAD',"$PERF":'PERF',"$SEC":'SEC']
    /**
     * Method to generate Test Case based on requirement (Story ID) and type of test case using amount of this test cases in database
     * @param requirement
     * @param type
     * @return
     */
	def generateTestCaseId(String requirement,String type) {
		String typeIdKey = TYPE_IDS.keySet().find {type.trim().toLowerCase().startsWith(it.toLowerCase())}
		String typeId = TYPE_IDS.find{ return it.key==typeIdKey}.value
		String tcPrefix = "${requirement}_${typeId}"
		int nextNumber = TestCase.list().findAll { it.tcId.startsWith(tcPrefix)}.size() + 1
		return "${requirement}_${typeId}_${nextNumber}"
    }
}
