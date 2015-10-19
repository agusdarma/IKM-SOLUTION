<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="j"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security"/> | <s:text name="t.changePassword"/></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="ChangePassword!process"/>
		<script type="text/javascript">
		$(document).ready(function() 
		{
			ButtonRequest("#form1", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
		});
		</script>
		
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.security"/> | <s:text name="t.changePassword"/></h1>
                <h2><s:text name="t.changePassword.description"/></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.update"/></h2>
	                </div>
                </div>
                
                <!-- PAPER MAIN -->
                <s:form id="form1" action="ChangePassword!process" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	
                	<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.changePassword"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
                            <label for="changepassword_username"><s:text name="l.userName"/></label>
                            <s:textfield type="text" id="changepassword_username" name="ChangePasswordUsername" cssClass="SingleLine" placeholder="%{getText('p.username')}" value="%{#session.LOGIN_KEY.userCode}" readonly="true"/>
	                        <br>	
	                        <label for="oldPassword"><s:text name="l.oldPassword"/></label>
	                        <s:password type="password" id="oldPassword" name="oldPassword" cssClass="SingleLine" placeholder="%{getText('p.oldPassword')}" required="true"/>
	                        <br>
	                        <label for="newPassword"><s:text name="l.newPassword"/></label>
	                        <s:password type="password" id="newPassword" name="newPassword" cssClass="SingleLine" placeholder="%{getText('p.newPassword')}" required="true"/>
	                        <br>
	                        <label for="confirmPassword"><s:text name="l.confirmPassword"/></label>
	                        <s:password type="password" id="confirmPassword" name="confirmPassword" cssClass="SingleLine" placeholder="%{getText('p.confirmPassword')}" required="true"/>
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
         			<div class="PositionerCenter">
                    	<s:submit type="submit" id="btnSave" cssClass="ButtonPrimary" value="%{getText('b.change')}"/><s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>