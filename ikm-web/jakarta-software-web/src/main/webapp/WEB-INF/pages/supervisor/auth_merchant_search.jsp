<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.supervisor" /> | <s:text name="t.authMerchant" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="listUrl" action="AuthMerchant!processSearch" />
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<script type="text/javascript">
			$(document).ready(function()
			{
				GenerateTableNoProcessTime("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{listUrl}"/>');
				if("<s:property value='message' />".length > 0)
				{
					MessageResult("<s:property value='message' />", "#MessageResult");	
				}
				
				$('#btnSearch').click(function()
				{	
					GenerateTable("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{listUrl}"/>');
				});
			});
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
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="AuthMerchant!processSearch" id="formSearch">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	<fieldset>
                    	<legend><s:text name="d.merchant"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
						<div class="FieldSetContainer">
							<s:hidden name="tableId" value="tag" />
							<label for="merchantCode"><s:text name="l.merchantCode"/></label>
							<s:textfield id="merchantCode" name="paramVO.merchantCode" cssClass="SingleLine" />
						</div>
					</fieldset>
					
					<br>
					<div class="PositionerCenter">
						<input type="button" value="search" id="btnSearch" class="ButtonPrimary" />
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
					</div>
					
					<div id="divSearch"></div>
				</s:form>
			</section>
		</main>
	</body>
</html>