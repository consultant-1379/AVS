package com.ericsson.cifwk.avs

import org.springframework.dao.DataIntegrityViolationException

/**
 * Default admin scaffolded controller for ActionPoints
 * @author erafkos
 *
 */
class ActionPointController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def scaffold = true
}
