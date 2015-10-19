<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.accessGroup" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="processInput" action="AccessGroup!processInput" />
		<script type="text/javascript">
		$(document).ready(function() 
		{
			ButtonRequest("#form1", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{processInput}"/>');
		});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.setting" /> | <s:text name="t.accessGroup" /></h1>
                <h2><s:text name="t.accessGroup.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
					<s:url id="search" action="AccessGroup!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="AccessGroup!gotoInput" includeParams="none"/>
            		<s:if test="accessGroup.id == 0">
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
                
                <!-- PAPER CONTENT -->
               	<s:form id="form1" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.accessGroup"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />                
						<div class="FieldSetContainer">
						<s:hidden name="accessGroup.id" />
						<s:hidden name="accessGroup.accessStatus" />
						<s:hidden name="accessId" />						
	                        <label for="accessName"><s:text name="l.accessName"/></label>
							<s:textfield id="accessName" name="accessGroup.accessName" cssClass="SingleLine" placeholder="%{getText('p.accessName')}" required="required"/>
							<br>
							<label for="remark"><s:text name="l.accessRemark"/></label>
							<s:textarea id="remark" name="accessGroup.remark" cssClass="MultiLine" placeholder="%{getText('p.accessRemark')}"/>
						</div>
					</fieldset>
					
					<!-- GENERATED TABLE -->
					<div class="TableGroup">	                       
						<table>
							<thead>
								<tr>
									<th colspan="2" scope="colgroup"><s:text name="l.listTransaction"/></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="listTransactions" status="Index">
									<tr>
										<td>
											<label for="TriggerSubModule<s:property value="lookupValue"/>"><s:property value="lookupDesc"/></label>
										</td>
										<td>
											<div class="CheckboxSlide CheckboxSlideForOthers">
											<s:if test="isModulesSelected(lookupValue)">
												<input type="checkbox" id="TriggerSubModule<s:property value="lookupValue"/>" name="modulesSelected" checked="checked" value="<s:property value="lookupValue"/>">
											</s:if>	
											<s:else>
												<input type="checkbox" id="TriggerSubModule<s:property value="lookupValue"/>" name="modulesSelected"  value="<s:property value="lookupValue"/>">
											</s:else>
												<label for="TriggerSubModule<s:property value="lookupValue"/>"></label>
											</div>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
	                        
					<!-- BUTTON -->
					<div class="PositionerCenter">
						<s:submit type="submit" id="btnSave" cssClass="ButtonPrimary" value="%{getText('b.save')}"/>
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
					</div>
				</s:form>
            </section>
        </main>
	</body>
</html>