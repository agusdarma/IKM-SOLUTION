<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="login-standard">
		<title><s:text name="t.login" /></title>
		<script type="text/javascript">	 
			$(document).ready(function()
			{
				if('<s:property value="message" />'.length  > 0)
				{
					$("#MessageResult").removeClass(MessageState);
					$("#MessageResult").addClass(ResultFailure);
					$("#MessageResult").empty();
					$("#MessageResult").append('<s:property value="message" />');
				}
				else
				{
					$("#MessageResult").removeClass();
					$("#MessageResult").addClass(MessageDefault);
					$("#MessageResult").addClass(MessageState);
				}
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main id="MainLogin">
        	<!-- PAGE TITLE -->
        	<hgroup>
            	<h1><s:text name="t.login"/></h1>
                <h2><s:text name="t.login.description"/></h2>
            </hgroup>
            
            <!-- CONTENT -->
            <section class="ContainerCenter">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
	            	<div class="PaperHeader PaperSelected">
	                	<div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.login"/></h2>
	                </div>
                </div>
                
                <!-- PAPER CONTENT -->
                <s:form action="Login!process" method="post">
                	<!-- FORM -->
                    <fieldset>
                    	<legend><s:text name="d.userData"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                        <div class="FieldSetContainer">
                            <label for="Username"><s:text name="l.userCode"/></label>
                            <s:textfield type="text" id="Username" name="loginData.userCode" cssClass="SingleLine" placeholder="%{getText('p.currentUserCode')}" required="true"/>
                            <br>
                            <label for="Userpassword"><s:text name="l.password"/></label>
                            <s:password type="password" id="Userpassword" name="loginData.password" cssClass="SingleLine" placeholder="%{getText('p.oldPassword')}" required="true"/>
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
                    <div class="PositionerCenter">
                    	<s:submit type="submit" id="ButtonLogin" cssClass="ButtonPrimary" value="%{getText('b.logIn')}"/> <s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>