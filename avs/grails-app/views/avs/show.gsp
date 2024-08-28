
<%@ page import="com.ericsson.cifwk.avs.Avs"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'avs.label', default: 'Avs')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#show-avs" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="list" action="list">
					<g:message code="default.list.label" args="[entityName]" />
				</g:link></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div id="show-avs" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<ol class="property-list avs">

	<g:if test="${avsInstance?.name}">
				<li class="fieldcontain"><span id="owner-label"
					class="property-label"><g:message code="avs.owner.label"
							default="name" /></span> <span class="property-value"
					aria-labelledby="owner-label"><g:fieldValue
							bean="${avsInstance}" field="name" /></span></li>
			</g:if>
			<g:if test="${avsInstance?.owner}">
				<li class="fieldcontain"><span id="owner-label"
					class="property-label"><g:message code="avs.owner.label"
							default="Owner" /></span> <span class="property-value"
					aria-labelledby="owner-label"><g:fieldValue
							bean="${avsInstance}" field="owner" /></span></li>
			</g:if>

			<g:if test="${avsInstance?.avsFile}">
				<li class="fieldcontain"><span id="avsFile-label"
					class="property-label"><g:message code="avs.avsFile.label"
							default="Avs File" /></span> <span class="property-value"
					aria-labelledby="avsFile-label"><g:link controller="avsFile"
							action="show" id="${avsInstance?.avsFile?.id}">
							${avsInstance?.avsFile?.encodeAsHTML()}
						</g:link></span></li>
			</g:if>



			<g:if test="${avsInstance?.testCases}">
				<li class="fieldcontain"><span id="testCase-label"
					class="property-label"><g:message code="avs.testCase.label"
							default="Test Cases" /></span> <g:each in="${avsInstance.testCases}"
						var="t">
						<span class="property-value" aria-labelledby="testCase-label"><g:link
								controller="testCase" action="show" id="${t.id}">
								${t?.encodeAsHTML()}
							</g:link></span>
					</g:each></li>
			</g:if>

			<g:if test="${avsInstance?.components}">
				<li class="fieldcontain"><span id="avsFile-label"
					class="property-label"><g:message code="avs.avsFile.label"
							default="Download skeleton files" /></span> <g:each
						in="${avsInstance.components }" var="component">
						<span class="property-value" aria-labelledby="avsFile-label"><g:link controller="avs" action="downloadSkeleton"  params="[component:component, avs:avsInstance.id]" >
								${component.encodeAsHTML()}
							</g:link></span>
					</g:each></li>
			</g:if>


		</ol>
		<g:form>
			<fieldset class="buttons">
				<g:hiddenField name="id" value="${avsInstance?.id}" />
				<g:link class="edit" action="edit" id="${avsInstance?.id}">
					<g:message code="default.button.edit.label" default="Edit" />
				</g:link>
				<g:actionSubmit class="delete" action="delete"
					value="${message(code: 'default.button.delete.label', default: 'Delete')}"
					onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>
