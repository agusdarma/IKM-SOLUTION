<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security" /> | <s:text name="t.userLevel" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="processInput" action="UserLevel!processInput" />
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
            	<h1><s:text name="t.security" /> | <s:text name="t.userLevel" /></h1>
                <h2><s:text name="t.userLevel.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
					<s:url id="search" action="UserLevel!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="UserLevel!gotoInput" includeParams="none"/>
            		<s:if test="UserLevel.id == 0">
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
                    	<legend><s:text name="d.userLevel"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />                
						<div class="FieldSetContainer">
							<s:hidden name="userLevel.id" />
							<s:hidden name="levelId" />						

	                        <label for="levelName"><s:text name="l.levelName"/></label>
	                        <s:if test="userLevel.id > 0">
								<s:textfield id="levelName" type="text" name="userLevel.levelName" cssClass="Property" readOnly="true"/>
							</s:if>
							<s:else>
								<s:textfield id="levelName" name="userLevel.levelName" cssClass="SingleLine" placeholder="%{getText('p.levelName')}" required="required"/>							
							</s:else>
							<br>
							<label for="remark"><s:text name="l.levelDescription"/></label>
							<s:textarea id="remark" name="userLevel.levelDesc" cssClass="MultiLine" placeholder="%{getText('p.levelDescription')}"/>
						</div>
					</fieldset>
					
					<!-- GENERATED TABLE -->
					<div class="TableGroup">	                       
						<s:iterator value="listModule">
							<table>
								<thead>
									<tr>
										<th><s:property value="header"/></th>
										<th><s:text name="l.access"/></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="bodies" status="Index">
										<tr>
											<td>
												<label for="TriggerSubModule<s:property value="menuId"/>"><s:property value="menuDescription"/></label>
											</td>
											<td>
												<div class="CheckboxSlide CheckboxSlideForOthers">
												<s:if test="isModulesSelected(menuId)">
													<input type="checkbox" id="TriggerSubModule<s:property value="menuId"/>" name="modulesSelected" checked="checked" value="<s:property value="menuId"/>">
												</s:if>	
												<s:else>
													<input type="checkbox" id="TriggerSubModule<s:property value="menuId"/>" name="modulesSelected"  value="<s:property value="menuId"/>">
												</s:else>
													<label for="TriggerSubModule<s:property value="menuId"/>"></label>
												</div>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:iterator>						
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