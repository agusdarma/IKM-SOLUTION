<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.systemSetting" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="listUrl" action="SystemSetting!processList" />
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<script type="text/javascript">
			$(document).ready(function()
			{
				if("<s:property value='message' />".length > 0)
				{
					MessageResult("<s:property value='message' />", "#MessageResult");
				}
				GenerateTableNoProcessTime("", "#ProgressRequest", "divSearch", '<s:property value="%{listUrl}"/>');
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
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
                </div>
            	
                <!-- CONTENT MAIN -->
                <s:form>
                <!-- MESSAGE RESULT -->
             	<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>                
             	
	           	 <div id="divSearch" ></div>
                </s:form>
            </section>
        </main>
	</body>
</html>