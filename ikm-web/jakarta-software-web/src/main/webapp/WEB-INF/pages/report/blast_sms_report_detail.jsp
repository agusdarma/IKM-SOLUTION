<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.branchData" /></title>
		
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
                    	<legend><s:text name="d.blastInfo"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
							<label for="deviceCode"><s:text name="l.sendTime"/></label>
                            <s:textfield id="deviceCode" cssClass="Property" name="pushRequestHeader.sentTime" readonly="true"/>
                            
                            <br>
                            <label for="message"><s:text name="l.message"/></label>
                            <s:textarea name="pushRequestHeader.message" id="message" cssClass="MultiLine" readonly="true" />
                            
                            <br>
                            <label for="createdOn"><s:text name="l.createdOn"/></label>
                            <s:textfield id="createdOn" cssClass="Property" name="pushRequestHeader.createdOn" readonly="true"/>
                            
                            <br>
                            <label for="createdBy"><s:text name="l.createdBy"/></label>
                            <s:textfield id="createdBy" cssClass="Property" name="pushRequestHeader.createdByDisplay" readonly="true"/>
                        </div>
                    </fieldset>
                  
					<table>
						<thead>
							<tr>
								<th><s:text name="l.phoneNo" /></th>
								<th><s:text name="l.status" /></th>
								<th><s:text name="l.groupName" /></th>
							</tr>
						</thead>
						<tbody id="bodyId">
							<s:iterator value="PushRequestVO" status="stat">
								<tr>
									<td><s:property value="msisdn"/></td>
									<td><s:property value="statusDisplay"/></td>
									<td><s:property value="groupName"/></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
                    
    			</s:form> 	 	
            </section>
        </main>
	</body>
</html>