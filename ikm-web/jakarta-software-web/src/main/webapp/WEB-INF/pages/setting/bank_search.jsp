<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-jmesa">
		<title><s:text name="t.setting" /> | <s:text name="t.bankSetting" /></title>
		
		<!-- JAVA SCRIPT -->
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<s:url var="listUrl" action="Bank!processSearch" />
		<script type="text/javascript">			
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				$('#btnSearch').click(function(){	
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
            	<h1><s:text name="t.setting" /> | <s:text name="t.bankSetting" /></h1>
                <h2><s:text name="t.bankSetting.description" /></h2>
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
	                <s:url id="input" action="Bank!gotoInput" includeParams="none"/>
					<s:a href="%{input}" cssClass="ClearHyperlink">
					<div class="PaperHeader">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
					</s:a>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form id="formSearch">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.bank"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
						<%--<s:hidden name="tableId" value="tag" />--%>
                            <label for="bankCode"><s:text name="l.bankCode"/></label>
                            <s:textfield type="text" id="bankCode" name="paramVO.bankCode" maxlength="3" onkeypress="return isNumberKey(event)" cssClass="SingleLine" placeholder="%{getText('p.bankCode')}"/>
	                        <br>
	                         <label for="bankName"><s:text name="l.bankName"/></label>
                            <s:textfield type="text" id="bankName" name="paramVO.bankName" maxlength="45" cssClass="SingleLine" placeholder="%{getText('p.bankName')}"/>
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