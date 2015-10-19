<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.bankSetting" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="Bank!processInput"/>
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
            	<h1><s:text name="t.setting" /> | <s:text name="t.bankSetting" /></h1>
                <h2><s:text name="t.bankSetting.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="Bank!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="Bank!gotoInput" includeParams="none"/>
            		<s:if test="bank == null">
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
                <s:form id="form1" action="Bank!processInput" method="post">
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.bank"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                        	<s:hidden name="bank.id" id="bankId"/>
							<s:if test="bank.id > 0">
								<label for="bankCode"><s:text name="l.bankCode" /></label>
								<s:textfield type="text" id="bankCode" name="bank.bankCode" maxlength="3" readonly="true" cssClass="SingleLine" required="true"/>
								<br>
							</s:if>
							<s:else>
								<label for="bankCode"><s:text name="l.bankCode" /></label>
								<s:textfield type="text" id="bankCode" name="bank.bankCode" maxlength="3" onkeypress="return isNumberKey(event)" readonly="false" cssClass="SingleLine" required="true"/>
								<br>
							</s:else>
							<label for="bankName"><s:text name="l.bankName" /></label>
							<s:textfield type="text" id="bankName" name="bank.bankName" maxlength="45" readonly="false" cssClass="SingleLine" required="true"/>
							
						<%-- 							
 							<s:if test="bank.id > 0">
							<br>							
							<label for="bankShown"><s:text name="l.shown" /></label>
								<div class="CheckboxSlide CheckboxSlideForOthers">							
									<s:if test="bank.showValue == 0">
										<input type="checkbox" id="bankShown" name="bank.show" value="true">
									</s:if>
									<s:else>
										<input type="checkbox" id="bankShown" name="bank.show" checked="checked" value="false">
									</s:else>								
									<label for="bankShown"></label>							
								</div>
							</s:if>
							
							<br>
							<label for="switching"><s:text name="l.switching" /></label>
							<s:select id="switching" name="bank.switching" list="listSwitching" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
						 --%>
						 
							<br>
							<label for="remarks"><s:text name="l.remarks" /></label>
							<s:textarea type="text" id="remarks" name="bank.remarks" maxlength="80" readonly="false" cssClass="MultiLine"/>
							<br>
						</div>
					</fieldset>
					
         			<div class="PositionerCenter">
                    	<s:submit id="btnSave" type="submit" value="%{getText('b.save')}" cssClass="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
				</s:form>
            </section>
        </main>
	</body>
</html>