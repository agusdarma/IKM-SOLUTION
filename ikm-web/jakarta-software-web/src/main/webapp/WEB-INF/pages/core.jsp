<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<html>
<head>

<s:url var="coreUrl" action="Login!toInput"/>
<script type="text/javascript">
jQuery(document).ready(function ($) {
	$("#coreContent").html("<center><strong>Loading.... Please wait</strong></center>");
	$("#coreContent").load('<s:property value="%{coreUrl}" />');
});
</script>
</head>

<body>
<div id="coreContent"></div>
</body>
</html>