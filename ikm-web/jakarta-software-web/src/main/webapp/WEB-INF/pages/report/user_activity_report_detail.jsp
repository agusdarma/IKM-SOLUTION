<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.userActivity" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="UserActivity!processInput"/>
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
            	<h1><s:text name="t.report" /> | <s:text name="t.userActivity" /></h1>
                <h2><s:text name="t.userActivity.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="UserActivityReport!gotoSearch" includeParams="none"/>
					<s:a href="%{search}" cssClass="ClearHyperlink">
		                <div class="PaperHeader">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.search"/></h2>
		                </div>
	                </s:a>
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.detail"/></h2>
	                </div>
                </div>
                
                <!-- PAPER CONTENT -->
                <s:form id="form1" >
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.userActivity"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
							<label for="userCode"><s:text name="l.userCode" /></label>		
							<s:textfield type="text" id="userCode" name="userActivity.userCode" cssClass="Property" readonly="true" />

							<br> 
							<label for="activity"><s:text name="l.activity" /></label>		
							<s:textfield type="text" id="activity" name="userActivity.action" cssClass="Property" readonly="true" />
							
							<br> 
							<label for="moduleName"><s:text name="l.moduleName" /></label>		
							<s:textfield type="text" id="moduleName" name="userActivity.moduleName" cssClass="Property" readonly="true"/>
							
							<br> 
							<label for="targetTable"><s:text name="l.targetTable" /></label>		
							<s:textfield type="text" id="targetTable" name="userActivity.targetTable" cssClass="Property" readonly="true" />
							
							<br> 
							<label for="targetId"><s:text name="l.targetId" /></label>		
							<s:textfield type="text" id="targetId" name="userActivity.targetId" cssClass="Property" readonly="true" />
                        </div>
                    </fieldset>
                  
					<table>
						<thead>
							<tr>
								<th><s:text name="l.field" /></th>
								<th><s:text name="l.beforeValue" /></th>
								<th><s:text name="l.afterValue" /></th>
							</tr>
						</thead>
						<tbody id="bodyId">
							<s:iterator value="changedAttribute" status="stat">
								<tr>
									<td><s:property value="field"/></td>
									<td><s:property value="oldValue"/></td>
									<td><s:property value="newValue"/></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
                    
    			</s:form> 	 	
            </section>
        </main>
	</body>
</html>