package com.ericsson.cifwk.avs

import java.util.Date;
/**
 * Class for storing information about Test Case
 * @author erafkos
 *
 */
class TestCase {

	Date dateCreated
	Date lastUpdated
	
	String tcId
	String title
	String description
	String type
	String component
	String priority
	String groups
	String pre
	String vusers
	String context

		
	static hasMany = [requirement : Requirement, actionPoints: ActionPoint ]
    
	static constraints = {
		tcId(unique:true, blank: false)
		title(blank: false)
		component(blank:false)
    }
	/**
	 * Method to add group of a test case
	 * @param group
	 */
	void addGroup(String group){
		if (!groups) groups = ""
		 groups+="$group,"
	}
	
	static mapping = {
		groups type:'text'
		description type: 'text'
		pre type: 'text'
	}
	
	@Override
	String toString(){
		return "$tcId $title"
	}
}
