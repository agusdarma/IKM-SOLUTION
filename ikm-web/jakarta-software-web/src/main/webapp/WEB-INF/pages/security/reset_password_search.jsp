<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-jmesa">
		<title><s:text name="t.security" /> | <s:text name="t.resetPassword" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="listUrl" action="ResetPassword!processSearch" />
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<script type="text/javascript">			
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				DirectSubmit("#formSearch", "#btnSearch", "#ProgressRequest", "divSearch", '<s:property value="%{listUrl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.security" /> | <s:text name="t.resetPassword" /></h1>
                <h2><s:text name="t.resetPassword.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form id="formSearch">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.resetPassword"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
							<%--<s:hidden name="tableId" value="tag" />--%>
                            <label for="userCode"><s:text name="l.userCode"/></label>
                            <s:textfield type="text" id="userCode" name="paramVO.userCode" cssClass="SingleLine" placeholder="%{getText('p.searchUserCode')}"/>
	                        <br>
	                         <label for="userName"><s:text name="l.userName"/></label>
                            <s:textfield type="text" id="userName" name="paramVO.userName" cssClass="SingleLine" placeholder="%{getText('p.searchUserName')}"/>
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
                    <div class="PositionerCenter">
                    	<input type="submit" id="btnSearch" class="ButtonPrimary" value="search"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                    
                    <!-- TABLE -->
                    <div id="divSearch">
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>