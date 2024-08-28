package com.ericsson.cifwk.avs

import org.springframework.dao.DataIntegrityViolationException

/**
 * Default scaffolded controller for requirements
 * @author erafkos
 *
 */
class RequirementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def scaffold = true
}
