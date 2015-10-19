<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.airtimeRefill" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="AirtimeRefillReport!processSearch"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				$("#btnSearch").click(function()
				{
					$('#exportType').val("");
					GenerateTable("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
				});
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.report" /> | <s:text name="d.airtimeRefill" /></h1>
                <h2><s:text name="t.airtimeRefill.description" /></h2>
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
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.airtimeRefill"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                        	<%@ include file="/WEB-INF/includes/param_export.jsp" %>
                            <label for="deviceCode"><s:text name="l.phoneNo"/></label>
                            <s:textfield id="deviceCode" name="paramVO.deviceCode" cssClass="SingleLine"/>
                            
                            <br>
                            <label for="startDate"><s:text name="l.trxDate"/></label>
                            <s:textfield id="startDate" cssClass="Date" name="paramVO.startDate"/> - <s:textfield id="endDate" cssClass="Date" name="paramVO.endDate"/>

							<br>
                            <label for="listTrxType"><s:text name="l.trxType" /></label>
							<s:select id="listTrxType" name="paramVO.trxType" headerKey="-1" headerValue="All"  
								list="listTrxType" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>

                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
                    <div class="PositionerCenter">
                    	<input type="button" id="btnSearch" class="ButtonPrimary" value="search"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                    			
                    <!-- TABLE -->
                    <div id="divSearch">
                    </div>
                </s:form> 	 	
            </section>
        </main>
	</body>
</html>