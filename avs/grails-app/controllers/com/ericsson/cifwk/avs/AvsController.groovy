package com.ericsson.cifwk.avs

import org.springframework.dao.DataIntegrityViolationException

/**
 * Class for operating on AVS model
 * @author erafkos
 *
 */
class AvsController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def mmParserService
	def testCaseParserService
	def skeletonCreatorService
	/**
	 * Redirect to create by default
	 * @return
	 */
	def index() {
		redirect(action: "create", params: params)
	}
	/**
	 * List AVS
	 * @return
	 */
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[avsInstanceList: Avs.list(params), avsInstanceTotal: Avs.count()]
	}
	/**
	 * Show screen for creating AVS
	 * @return
	 */
	def create() {
		[avsInstance: new Avs(params)]
	}

	private AvsFile parseFile(def filePartOfRequest, String owner){
		String fileName = filePartOfRequest.originalFilename

		String fileContent = ""
		filePartOfRequest.inputStream.eachLine {
			fileContent += it
		}
		if (fileContent.size() < 1){
			flash.message = message(code: 'default.blank.message',args: [message(code: 'avs.label', default: 'Avs'),params.id])
			render(view: "create", model: [avsInstance: new Avs(owner:owner)])
			return
		}

		def avsFileInstance = new AvsFile([fileName:fileName, fileContent: fileContent])
	}
	/**
	 * Parse Mindmap and create AVS
	 * @return
	 */
	def save() {
		def avsFileInstance = parseFile(request.getFile('avsFile'),params.owner)

		def avsInstance = new Avs([owner:params.owner,avsFile:avsFileInstance,name:"Unnamed"])
		if (!avsInstance.save()) {
			avsInstance.errors.each {
				println it
			}
			log.info ("AVS created not created $avsInstance")
			render(view: "create", model: [avsInstance: avsInstance])
			return
		}

		mmParserService.parseFile(avsFileInstance.fileContent).each {
			if (! it.save()){
				flash.message = message (code: "Incorrect test case $it")
				render(view: "create", model: [avsInstance: new Avs(owner:owner)])
			} else {
				avsInstance.addToTestCases(it)
			}
		}


		flash.message = message(code: 'default.created.message', args: [message(code: 'avs.label', default: 'Avs'), avsInstance.id])
		redirect(action: "show", id: avsInstance.id)
	}

	/**
	 * Show AVS 
	 * @return
	 */
	def show() {
		def avsInstance = Avs.get(params.id)
		if (!avsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'avs.label', default: 'Avs'), params.id])
			redirect(action: "list")
			return
		}

		[avsInstance: avsInstance]
	}

	/**
	 * Delete AVS
	 * @return
	 */
	def delete() {
		def avsInstance = Avs.get(params.id)
		if (!avsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'avs.label', default: 'Avs'), params.id])
			redirect(action: "list")
			return
		}

		try {
			avsInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'avs.label', default: 'Avs'), params.id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'avs.label', default: 'Avs'), params.id])
			redirect(action: "show", id: params.id)
		}
	}
	/**
	 * Download dynamically created skeleton file
	 * @return
	 */
	def downloadSkeleton(){
		println params
		def avsInstance = Avs.get(params.avs)

		def groovyFile = skeletonCreatorService.createComponentFile(avsInstance, params.component)
		response.setHeader("Content-disposition", "attachment; filename=\"${params.component}.groovy\"")
		render(contentType: "text/plain", text: groovyFile)
	}
}
