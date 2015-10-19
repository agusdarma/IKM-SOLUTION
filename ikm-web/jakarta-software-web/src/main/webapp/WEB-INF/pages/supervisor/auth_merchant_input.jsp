<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.supervisor" /> | <s:text name="t.authMerchant" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="AuthMerchant!processAuthorize"/>
		<script type="text/javascript">
			$(document).ready(function()
			{
				ButtonAuthorize("#form1", ".ButtonAuthorize", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
			});//end jquery				
		</script>		
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.supervisor" /> | <s:text name="t.authMerchant" /></h1>
                <h2><s:text name="t.authMerchant.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="AuthMerchant!execute" includeParams="none"/>
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
	                    <h2><s:text name="f.update"/></h2>
	                </div>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="AuthMerchant!processAuthorize" id="form1" >
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.merchant"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
						<div class="FieldSetContainer">
							<s:hidden name="merchantVO.id"/>
							<s:hidden name="merchantVO.merchantId" />
							<s:hidden name="mode" id="mode" />					

							<label for="activityType"><s:text name="l.requestType" /></label>
							<span class="Property" ><s:property value="merchantVO.activityType" /></span>
							
							<br>
							<label for="merchantCode"><s:text name="l.merchantCode" /></label>
							<span class="Property" ><s:property value="merchantVO.merchantCode" /></span>
		
							<br>
							<label for="merchantName"><s:text name="l.merchantName" /></label>
							<span class="Property" ><s:property value="merchantVO.merchantName" /></span>
							
							<br>
							<label for="alias"><s:text name="l.alias" /></label>
							<span class="Property" ><s:property value="merchantVO.alias" /></span>
							
							<br>
							<label for="accountType"><s:text name="l.accountType" /></label>
							<span class="Property" ><s:property value="merchantVO.accountTypeDisplay" /></span>
		
							<br>
							<label for="accountNumber"><s:text name="l.accountNumber" /></label>
							<span class="Property" ><s:property value="merchantVO.accountNumber" /></span>
		
							<br>
						    <label for="currency"><s:text name="l.currency" /></label></span>
							<span class="Property" ><s:property value="merchantVO.currency" /></span>
		
							<br>
							<label for="timeout"><s:text name="l.timeout" /></label>
							<span class="Property" ><s:property value="merchantVO.timeout" /></span>
		
							<br> 
							<label for="status"><s:text name="l.status" /></label>
							<span class="Property" ><s:property value="merchantVO.merchantStatusDisplay" /></span>
		
							<br> 
							<label for="createdOn"><s:text name="l.createdOn" /></label>
							<span class="Property" ><s:property value="merchantVO.createdOn" /></span>
		
							<br>
							<label for="createdBy"><s:text name="l.createdBy" /></label>
							<span class="Property" ><s:property value="merchantVO.createdByDisplay" /></span>
							
							<br>
							<label for="updatedOn"><s:text name="l.updatedOn" /></label>
							<span class="Property" ><s:property value="merchantVO.updatedOn" /></span>
		
							<br>
							<label for="updatedBy"><s:text name="l.updatedBy" /></label>
							<span class="Property" ><s:property value="merchantVO.updatedByDisplay" /></span>
						</div>
					</fieldset>
					
					<br>
					<div class="PositionerCenter">
						<input type="button" value="<s:text name="b.approve" />" id="btnApprove" class="ButtonPrimary ButtonAuthorize" />
						<input type="button" value="<s:text name="b.reject" />" id="btnReject" class="ButtonPrimary ButtonAuthorize" />	
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
					</div>
				</s:form>
            </section>
        </main>
	</body>
</html>