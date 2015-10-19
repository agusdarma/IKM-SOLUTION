<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>

<meta name="decorator" content="blank">
<title><s:text name="pages.login.title" /></title>
<meta http-equiv="Refresh" content="1; URL=<s:url action="Login" />">
</head>

<body>

<div class="span-24 last">
<p class="center">
  	<s:text name="label.message.logoff" /> <br/>
  	<s:url id="loginUrl" action="Login" includeParams="none" />
	<s:a href="%{loginUrl}"><s:text name="label.backToLogin" /></s:a>  
</p>
</div>

</body>
</html>