\<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.resetPin" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="ResetPin!processSearch"/>
		<script type="text/javascript">	 
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				DirectSubmit("#formSearch", "#btnSearch", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.resetPin" /></h1>
                <h2><s:text name="t.resetPin.description" /></h2>
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
                <s:form action="ResetPin!processSearch" id="formSearch">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	<fieldset>
                    	<legend><s:text name="d.cifData"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
	                       	<label for="mobilePhone"><s:text name="l.cifDeviceCode" /></label>		
							<s:textfield type="text" id="deviceCode" name="paramVO.mobilePhone" maxlength="15" cssClass="SingleLine"/>
						</div>
					</fieldset>
					
					<br>
					<div class="PositionerCenter">
						<input type="button" value="search" id="btnSearch" class="ButtonPrimary" />
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
					</div>
					<div id="divSearch"></div>
				</s:form>
				
			</section>
		</main>
	</body>
</html>