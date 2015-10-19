<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.merchantReport" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="AuthMerchant!processAuthorize"/>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.report" /> | <s:text name="t.merchantReport" /></h1>
                <h2><s:text name="t.merchantReport.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="MerchantReport!gotoSearch" includeParams="none"/>
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
                    	<legend><s:text name="d.merchantReport"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
							<span class="LabelContent">
								<label for="merchantCode"><s:text name="l.merchantCode" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.merchantCode" /></span>
		
							<br>
							<span class="LabelContent">
								<label for="merchantName"><s:text name="l.merchantName" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.merchantName" /></span>
							
							<br>
							<span class="LabelContent">
								<label for="alias"><s:text name="l.alias" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.alias" /></span>
							
							<br>
							<span class="LabelContent">
								<label for="accountType"><s:text name="l.accountType" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.accountTypeDisplay" /></span>
		
							<br>
							<span class="LabelContent">
								<label for="accountNumber"><s:text name="l.accountNumber" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.accountNumber" /></span>
		
							<br>
						    <span class="LabelContent">
								<label for="currency"><s:text name="l.currency" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.currency" /></span>
		
							<br>
							<span class="LabelContent">
								<label for="timeout"><s:text name="l.timeout" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.timeout" /></span>
		
							<br> 
							<span class="LabelContent">
								<label for="status"><s:text name="l.status" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.merchantStatusDisplay" /></span>
		
							<br> 
							<span class="LabelContent">
								<label for="createdOn"><s:text name="l.createdOn" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.createdOn" /></span>
		
							<br>
							<span class="LabelContent">
								<label for="createdBy"><s:text name="l.createdBy" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.createdByDisplay" /></span>
							
							<br>
							<span class="LabelContent">
								<label for="updatedOn"><s:text name="l.updatedOn" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.updatedOn" /></span>
		
							<br>
							<span class="LabelContent">
								<label for="updatedBy"><s:text name="l.updatedBy" /></label>
							</span>
							<span class="Property" ><s:property value="merchantVO.updatedByDisplay" /></span>
						</div>
					</fieldset>
					
					<table>
						<thead>
							<tr>
								<th><s:text name="l.activity" /></th>
								<th><s:text name="l.merchantCode" /></th>
								<th><s:text name="l.merchantName" /></th>
								<th><s:text name="l.alias" /></th>
								<th><s:text name="l.accountType" /></th>
								<th><s:text name="l.accountNumber" /></th>
								<th><s:text name="l.currency" /></th>
								<th><s:text name="l.timeout" /></th>
								<th><s:text name="l.status" /></th>
								<th><s:text name="l.authStatus" /></th>
								<th><s:text name="l.createdOn" /></th>
								<th><s:text name="l.createdBy" /></th>
							</tr>
						</thead>
						<tbody id="bodyId">
							<s:iterator value="listHistoryMerchant" status="stat">
								<tr>
									<td><s:property value="activityType" /></td>
									<td><s:property value="merchantCode" /></td>
									<td><s:property value="merchantName" /></td>
									<td><s:property value="alias" /></td>
									<td><s:property value="accountTypeDisplay" /></td>
									<td><s:property value="accountNumber" /></td>
									<td><s:property value="currency" /></td>
									<td><s:property value="timeout" /></td>
									<td><s:property value="merchantStatusDisplay" /></td>
									<td><s:property value="authStatusDisplay" /></td>
									<td><s:property value="createdOn" /></td>
									<td><s:property value="createdByDisplay" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					
    			</s:form> 	 	
            </section>
        </main>
	</body>
</html>