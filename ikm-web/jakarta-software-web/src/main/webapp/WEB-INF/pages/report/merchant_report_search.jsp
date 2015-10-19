<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.merchant" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="listUrl" action="MerchantReport!processSearch" />
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<script type="text/javascript">
			
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				$('#btnSearch').click(function(){
					$('#exportType').val("");
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
            	<h1><s:text name="t.report" /> | <s:text name="t.merchant" /></h1>
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
                <s:form id="formSearch">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
					<fieldset>
                    	<legend><s:text name="d.merchantReport"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                            <%@ include file="/WEB-INF/includes/param_export.jsp" %>
							<span class="LabelContent"><label for="merchantCode">
								<s:text name="l.merchantCode"/>
							</label></span>
							<s:textfield id="merchantCode" name="paramVO.merchantCode" cssClass="SingleLine" />
							
							<br>
							<label for="startDate"><s:text name="l.startDate"/></label>
                            <s:textfield id="startDate" cssClass="DateTime" name="paramVO.startDate"/>
                            
                            <br>
                            <label for="endDate"><s:text name="l.endDate"/></label>
                            <s:textfield id="endDate" cssClass="DateTime" name="paramVO.endDate"/> 
                            
                            <br>
							<label for="status"><s:text name="l.status" /></label>
							<s:select id="status" name="paramVO.status" list="listStatus" 
								listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"
								headerKey="0" headerValue="All"/>
						</div>
					</fieldset>

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