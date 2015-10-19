<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security" /> | <s:text name="t.userPreference" /></title>
		
		<!-- JAVA SCRIPT -->
		<script type="text/javascript">
			<!-- ON DOCUMENT READY -->
			$(document).ready(function()
			{
				// INPUT
				
				MessageResult("<s:property value='message' />", "#MessageResult");
				
				CSSDropDown(ThemeArray, "#UITheme");
				CSSDropDown(FontFamilyArray, "#FontFamily");
				CSSDropDown(FontSizeArray, "#FontSize");
				CSSDropDown(LanguageArray, "#Language");
				
				DropDownSelector("#UITheme", CurrentTheme);
				DropDownSelector("#FontFamily", CurrentFontFamily);
				DropDownSelector("#FontSize", CurrentFontSize);
				DropDownSelector("#Language", '<s:property value="%{#session.LOGIN_KEY.userPreference.language}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.security" /> | <s:text name="t.userPreference" /></h1>
                <h2><s:text name="t.userPreference.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.update"/></h2>
	                </div>
                </div>
                
                <!-- PAPER CONTENT -->
                <s:form action="UserPreference!update" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.userPreference"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
                            <label for="UITheme"><s:text name="l.UITheme"/></label>
                            <select id="UITheme" name="userPreference.theme" class="DropDown"></select>
	                        <br>
	                        <label for="FontFamily"><s:text name="l.fontFamily"/></label>
                            <select id="FontFamily" name="userPreference.fontFamily" class="DropDown"></select>
	                        <br>
	                        <label for="FontSize"><s:text name="l.fontSize"/></label>
                            <select id="FontSize" name="userPreference.fontSize" class="DropDown"></select>
	                        <br>
	                        <label for="Language"><s:text name="l.language"/></label>
                            <select id="Language" name="userPreference.language" class="DropDown"></select>
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
         			<div class="PositionerCenter">
                    	<s:submit type="submit" id="ButtonChange" cssClass="ButtonPrimary" value="%{getText('b.change')}"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>