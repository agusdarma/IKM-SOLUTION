<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.merchant" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="Merchant!processInput"/>
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
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.merchant" /></h1>
                <h2><s:text name="t.merchant.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="Merchant!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="Merchant!gotoInput" includeParams="none"/>
            		<s:if test="merchant.id==0">
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
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
	                </s:if>
	                <s:else>
	                	<s:a href="%{search}" cssClass="ClearHyperlink">
			                <div class="PaperHeader">
			                    <div class="ShapeFolderHornBorder"></div>
			                    <div class="ShapeFolderHornBody"></div>
			                    <h2><s:text name="f.search"/></h2>
			                </div>
		                </s:a>
		                <s:a href="%{input}" cssClass="ClearHyperlink">
						<div class="PaperHeader">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.insert"/></h2>
		                </div>
		                </s:a>		                
		                <div class="PaperHeader PaperSelected">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.update"/></h2>
		                </div>
	                </s:else>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form id="form1" action="Merchant!processInput" method="post">
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.merchant"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                        	<s:hidden name="merchant.id" id="merchantId"/>
							<s:if test="merchant.id > 0">
								<label for="merchantCode"><s:text name="l.merchantCode" /></label>
								<s:textfield type="text" id="merchantCode" name="merchant.merchantCode" maxlength="16"
									readonly="true" cssClass="SingleLine" required="true" 
									onkeypress="return spaceNotAllowed(event)"/>								
							</s:if>
							<s:else>
								<label for="merchantCode"><s:text name="l.merchantCode" /></label>
								<s:textfield type="text" id="merchantCode" name="merchant.merchantCode" 
									maxlength="16" readonly="false" cssClass="SingleLine" 
									required="true" onkeypress="return spaceNotAllowed(event)"/>								
							</s:else>
							
							<br>
							<label for="merchantName"><s:text name="l.merchantName" /></label>
							<s:textfield type="text" id="merchantName" name="merchant.merchantName" maxlength="30" readonly="false" cssClass="SingleLine" required="true"/>
							
							<br>							
							<label for="merchantAlias"><s:text name="l.alias" /></label>
							<s:textfield type="text" id="merchantAlias" name="merchant.alias" maxlength="15" readonly="false" cssClass="SingleLine" required="true"/>
							
							<br>							
							<label for="accountType"><s:text name="l.accountType" /></label>
							<s:select id="accountType" name="merchant.accountType" list="listAccountType" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
							
							<br>	
							<label for="accountNumber"><s:text name="l.accountNumber" /></label>
							<s:textfield type="text" id="accountNumber" name="merchant.accountNumber" onkeypress="return isNumberKey(event)" maxlength="15" readonly="false" cssClass="SingleLine" required="true"/>
							
							<br>
							<label for="currency"><s:text name="l.currency" /></label>
							<s:select id="currency" name="merchant.currency" list="listCurrency" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
							
							<br>	
							<label for="timeout"><s:text name="l.timeout" /></label>
							<s:textfield type="text" id="timeout" name="merchant.timeout" placeholder="p.inMinute" onkeypress="return isNumberKey(event)" maxlength="3" cssClass="SingleLine" required="true"/>
							
							<br>
							<label for="status"><s:text name="l.status" /></label>
							<s:select id="status" name="merchant.status" list="listStatus" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
						</div>
					</fieldset>
					
					<br> 				
          			<div class="PositionerCenter">
                     	<s:submit id="btnSave" type="submit" value="%{getText('b.save')}" cssClass="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
				</s:form>
            </section>
        </main>
	</body>
</html>