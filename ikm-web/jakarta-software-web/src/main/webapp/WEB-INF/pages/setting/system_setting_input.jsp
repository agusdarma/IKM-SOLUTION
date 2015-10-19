<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.systemSetting" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="SystemSetting!processInput"/>
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
            	<h1><s:text name="t.setting" /> | <s:text name="t.systemSetting" /></h1>
                <h2><s:text name="t.systemSetting.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="SystemSetting!gotoSearch" includeParams="none"/>
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
                <s:form id="form1" method="post">
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.systemSetting"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                       	<div class="FieldSetContainer">
                        	<s:hidden name="systemSetting.id" id="settingId"/>
							<label for="settingName"><s:text name="l.settingName" /></label>
							<s:textfield type="text" id="settingName" name="systemSetting.settingName" disabled="true" cssClass="SingleLine" />
							
							<br>
							<label for="settingDescription"><s:text name="l.settingDesc" /></label>
							<s:textarea type="text" id="settingDescription" name="systemSetting.settingDesc" disabled="true" cssClass="MultiLine" />
							
							<br>
							<label for="valueType"><s:text name="l.valueType" /></label>
							<s:textfield type="text" id="valueType" name="systemSetting.valueType" disabled="true" cssClass="SingleLine" />
							
							<br>
							<label for="settingValue"><s:text name="l.settingValue" /></label>
							<s:textarea type="text" id="settingValue" name="systemSetting.settingValue" 
								cssClass="MultiLine" required="true" rows="3"/>
							
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