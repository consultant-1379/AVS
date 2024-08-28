package com.ericsson.cifwk.avs
/**
 * Class to store AVS mindmap
 * @author erafkos
 *
 */
class AvsFile {

	Date dateCreated
	Date lastUpdated
	String fileName
	String fileContent
    
	static constraints = {
		fileName(blank:false)
    }

	static belongsTo = [avs:Avs]	
	static mapping = {
		fileContent type: 'text'
	 }
	
	@Override
	public String toString(){
		return fileName
	}
}
