
<%@ page import="com.ericsson.cifwk.avs.Avs" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'avs.label', default: 'Avs')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-avs" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-avs" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'avs.owner.label', default: 'Name')}" />
						<g:sortableColumn property="owner" title="${message(code: 'avs.owner.label', default: 'Owner')}" />
					
						<th><g:message code="avs.avsFile.label" default="Avs File" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${avsInstanceList}" status="i" var="avsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${avsInstance.id}">${fieldValue(bean: avsInstance, field: "owner")}</g:link></td>
					
						<td>${fieldValue(bean: avsInstance, field: "avsFile")}</td>
					
						<td><g:formatDate date="${avsInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${avsInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${avsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
