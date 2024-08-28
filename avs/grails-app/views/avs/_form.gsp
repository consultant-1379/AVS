<%@ page import="com.ericsson.cifwk.avs.Avs" %>



<div class="fieldcontain ${hasErrors(bean: avsInstance, field: 'owner', 'error')} required">
	<label for="owner">
		<g:message code="avs.owner.label" default="Owner" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="owner" required="" value="${avsInstance?.owner}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: avsInstance, field: 'avsFile', 'error')} required">
	<label for="avsFile">
		<g:message code="avs.avsFile.label" default="Avs File" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="avsFile" name="avsFile.id" from="${com.ericsson.cifwk.avs.AvsFile.list()}" optionKey="id" required="" value="${avsInstance?.avsFile?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: avsInstance, field: 'testCase', 'error')} ">
	<label for="testCase">
		<g:message code="avs.testCase.label" default="Test Case" />
		
	</label>
	<g:select name="testCase" from="${com.ericsson.cifwk.avs.TestCase.list()}" multiple="multiple" optionKey="id" size="5" value="${avsInstance?.testCase*.id}" class="many-to-many"/>
</div>

