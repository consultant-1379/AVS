package com.ericsson.cifwk.avs

import java.util.Date;
/**
 * Class storing information about Agile Verification Specification
 * @author erafkos
 *
 */
class Avs {
	Date dateCreated
	Date lastUpdated
	
	String owner
	AvsFile avsFile
	String name
	/**
	 * Get components used in AVS
	 * @return
	 */
	List<String> getComponents(){
		testCases?.collect {it.component}.unique()
	}
	/**
	 * Update name if it has not been set
	 * @return
	 */
	String getName(){
		if (name == "Unnamed" && testCases)
			this.name =	testCases.collect {it.requirement}.unique().join("_")
		return this.name
	}
	
	static hasMany = [testCases : TestCase]
    static constraints = {
		owner(blank:false)
		avsFile(blank:false)
		
    }
}
