package com.ericsson.cifwk.avs
/**
 * Class storing information about Action Point
 * @author erafkos
 *
 */
class ActionPoint extends TestPoint{

	static hasMany = [verificationPoints : VerificationPoint]
	static belongsTo = [testCase: TestCase]
    static constraints = {
    }
	@Override
	public String toString(){
		return super.toString()
	}
}
