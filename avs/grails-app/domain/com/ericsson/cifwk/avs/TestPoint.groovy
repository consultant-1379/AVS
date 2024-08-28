package com.ericsson.cifwk.avs

import java.util.Date;
/**
 * Abstract class to store information about description for Action Point and Verification Point
 * @author erafkos
 *
 */
abstract class TestPoint {

	Date dateCreated
	Date lastUpdated
	
	String description
	
    static constraints = {
		description(blank:false)
    }
	
	static mappings = {
		description type: 'text'
	}
	
	@Override
	public String toString(){
		return description
	}
}
