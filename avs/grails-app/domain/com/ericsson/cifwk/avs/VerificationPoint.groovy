package com.ericsson.cifwk.avs
/**
 * Class to store information about Verification Point
 * @author erafkos
 *
 */
class VerificationPoint extends TestPoint{

	static belongsTo = [actionPoint: ActionPoint]
    static constraints = {
    }
	@Override
	public String toString(){
		return super.toString()
	}
}
