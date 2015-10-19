<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-jmesa">
		<title><s:text name="t.setting" /> | <s:text name="t.billPayment" /></title>
		
		<!-- JAVA SCRIPT -->
		<%@ include file="/WEB-INF/includes/include_script_table_generator.jsp"%>
		<s:url var="listUrl" action="BillPayment!processSearch" />
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
            	<h1><s:text name="t.setting" /> | <s:text name="t.billPayment" /></h1>
                <h2><s:text name="t.billPayment.description" /></h2>
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
	                <s:url id="input" action="BillPayment!gotoInput" includeParams="none"/>
					<s:a href="%{input}" cssClass="ClearHyperlink">
					<div class="PaperHeader">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
					</s:a>
                </div>
                
                <!-- CONTENT MAIN -->
                 <s:form action="BillPayment!processSearch" id="formSearch" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	<fieldset>
                    	<legend><s:text name="d.billPayment"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                            <label for="billerCode"><s:text name="l.billerCode"/></label>
                            <s:textfield type="text" id="billerCode" name="paramVO.billerNo" maxlength="12" cssClass="SingleLine" placeholder="%{getText('p.bpCode')}"/>
	                        <br>
	                         <label for="billerName"><s:text name="l.billerName"/></label>
                            <s:textfield type="text" id="billerName" name="paramVO.billerName" maxlength="30" cssClass="SingleLine" placeholder="%{getText('p.bpName')}"/>
	                        <br>
	                    </div>
					</fieldset>
         			<div class="PositionerCenter">
                    	<input type="button" id="btnSearch" class="ButtonPrimary" value="search"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                   <div id="divSearch">                    	
    	           </div>                
				</s:form>                    
            </section>
        </main>
	</body>
</html>