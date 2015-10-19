<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
<title><s:text name="t.login"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="My Agenda,Android,Iphone" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
 <!-- Bootstrap Core CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/bootstrap.min.css'/>" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/style.css'/>" rel='stylesheet' type='text/css' />
<link href="<s:url value='/Style/jakartasoftware/admin/font-awesome.css'/>" rel="stylesheet"> 
<!-- jQuery -->
<script src="<s:url value='/Java Script/jakartasoftware/admin/jquery.min.js'/>"></script>
<!----webfonts--->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>
<!---//webfonts--->    
<!-- Bootstrap Core JavaScript -->
<script src="<s:url value='/Java Script/jakartasoftware/admin/bootstrap.min.js'/>"></script>
</head>
<body id="login">
  <div class="login-logo">
    <a href="index.html"><img height="151" width="151" src="<s:url value='/Resource/images/myagenda.png'/>" alt=""/></a>
  </div>
  <h2 class="form-heading"><s:text name="t.login"/></h2>
  <div class="app-cam">
	  <s:form action="Login!go" method="post">
	  	<s:textfield type="text" name="webLoginData.kodeSekolah" cssClass="text" placeholder="%{getText('l.schoolCode')}" required="true"/>
	  	<s:textfield type="text" name="webLoginData.noInduk" cssClass="text" placeholder="%{getText('l.noInduk')}" required="true"/>
	  	<s:textfield type="password" name="webLoginData.password" cssClass="text" placeholder="%{getText('l.password')}" required="true"/>
		<s:radio label="Login Type" name="webLoginData.userType" list="listLoginType" listKey="lookupValue" listValue="lookupDesc" value="2" />
		<div class="submit"><s:submit type="submit" id="ButtonLogin" value="%{getText('b.logIn')}"/></div>
		<%-- <div class="login-social-link">
          <a href="index.html" class="facebook">
              Facebook
          </a>
          <a href="index.html" class="twitter">
              Twitter
          </a>
        </div>
		<ul class="new">
			<li class="new_left"><p><a href="#">Forgot Password ?</a></p></li>
			<li class="new_right"><p class="sign">New here ?<a href="register.html"> Sign Up</a></p></li>
			<div class="clearfix"></div>
		</ul>
		--%>
	</s:form>
  </div>
   <div class="copy_layout login">
      <p>Copyright &copy; 2015 Indo Kanaan Mandiri. All Rights Reserved | Design by W3layouts </p>
   </div>
</body>
</html>