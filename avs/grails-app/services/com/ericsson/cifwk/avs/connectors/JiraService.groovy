package com.ericsson.cifwk.avs.connectors

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

/**
 * Class to use REST service of JIRA
 * @author erafkos
 *
 */
class JiraService {

	String defaultUser="lciadm100"
	String defaultPass="lciadm100"

	HTTPBuilder http = new HTTPBuilder( 'http://jira.eei.ericsson.se:8081' )
	/**
	 * Check if Story with requested ID exists in JIRA
	 * @param id
	 * @return
	 */
	def checkStory(String id){
		try {
			println "Story ID: ${getSummary(id.trim())}"
			return true
		} catch (Exception e) {
			println "ERROR: $e"
			return false
		}
	}
	/**
	 * Get an Entity from Jira using  REST interface
	 * @param entityId
	 * @return
	 */
	def getEntity(String entityId){
		println "/rest/api/latest/issue/$entityId"
		http.get(path: "/rest/api/latest/issue/$entityId", query: [os_username:defaultUser,os_password:defaultPass]){ resp, json ->
			println json.fields.summary.value
			return json
		}
	}

	/**
	 * Get Summary of JIRA entity
	 * @param entityId
	 * @return
	 */
	def getSummary(String entityId){
		return getEntity(entityId).fields.summary.value
	}

	/**
	 * Submit a JQL query for JIRA
	 * @param query
	 * @return
	 */
	def submitQuery(String query){
		http.get(path: "/rest/api/latest/search", query: [os_username:defaultUser,os_password:defaultPass, jql:query]){ resp, json ->
			return json.issues.collect {return it.key}
		}
	}

}
