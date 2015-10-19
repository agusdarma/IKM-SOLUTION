<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.terminatedCif" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="TerminatedCifReport!processSearch"/>
		<script type="text/javascript">	 
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");	
				$("#btnSearch").click(function()
				{
					$('#exportType').val("");
					GenerateTable("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
				});
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.report" /> | <s:text name="t.terminatedCif" /></h1>
                <h2><s:text name="t.terminatedCif.description" /></h2>
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
                    	<legend><s:text name="d.terminatedCif"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
         					<%@ include file="/WEB-INF/includes/param_export.jsp" %>
                            <label for="startDate"><s:text name="l.registrationDate"/></label>
                            <s:textfield id="startDate" cssClass="DateTime" name="paramVO.startDate"/> - <s:textfield id="endDate" cssClass="DateTime" name="paramVO.endDate"/> 
							
							<br>
                            <label for="deviceCode"><s:text name="l.phoneNo"/></label>
                            <s:textfield id="deviceCode" cssClass="SingleLine" name="paramVO.deviceCode"/>                                                                                  
                                                       
                            <br>
                            <label for="accessType"><s:text name="l.accessGroup"/></label>
                            <s:select id="accessType" cssClass="DropDown" name="paramVO.accessGroup" 
                            	list="listAccessGroup" listKey="id" listValue="accessName"
                            	headerKey="0" headerValue="All"/>
                            	
                            <br>
							<label for="cifGroup"><s:text name="l.cifGroup" /></label>
							<s:select id="cifGroup" name="paramVO.cifGroup" list="listCifGroup" 
								listKey="id" listValue="groupName" cssClass="DropDown"
								headerKey="0" headerValue="All"/>								
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
                    <div class="PositionerCenter">
                    	<input type="button" id="btnSearch" class="ButtonPrimary" value="search"/>
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