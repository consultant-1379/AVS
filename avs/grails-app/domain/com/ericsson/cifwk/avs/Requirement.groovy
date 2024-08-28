package com.ericsson.cifwk.avs
/**
 * Class to store information about Story 
 * @author erafkos
 *
 */
class Requirement {

	String jiraId
	String title
	Requirement parent
	
    static constraints = {
		jiraId(blank:false,unique:true)
		title(nullable:true)
		parent(nullable:true)
    }
	
	String toString(){
		return jiraId
	}
}
