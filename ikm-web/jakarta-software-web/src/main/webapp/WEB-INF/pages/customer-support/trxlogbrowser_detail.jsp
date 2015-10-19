<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.trxLogBrowser" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="TrxLogBrowser!listDetailTrx"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				GenerateTable("", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.trxLogBrowser" /></h1>
                <h2><s:text name="t.trxLogBrowser.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="TrxLogBrowser!gotoSearch" includeParams="none"/>
	                	<s:a href="%{search}" cssClass="ClearHyperlink">
			                <div class="PaperHeader">
			                    <div class="ShapeFolderHornBorder"></div>
			                    <div class="ShapeFolderHornBody"></div>
			                    <h2><s:text name="f.search"/></h2>
			                </div>
		                </s:a>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form id="form1" method="post">
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.trxLogBrowser"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                 							
						<label for="syslogno"><s:text name="l.syslogno" /></label>
						<s:textfield type="text" id="syslogno" name="detailTrx.sysLogNo" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="receivedTime"><s:text name="l.dateTime" /></label>
						<s:textfield type="text" id="receivedTime" name="detailTrx.receivedTime" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="trxCode"><s:text name="l.trxCode" /></label>
						<s:textfield type="text" id="trxCode" name="detailTrx.trxCode" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="sourceAcc"><s:text name="l.sourceAccount" /></label>
						<s:textfield type="text" id="sourceAcc" name="detailTrx.sourceAccount" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="destAccount"><s:text name="l.destAccount" /></label>
						<s:textfield type="text" id="destAccount" name="detailTrx.destAccount" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="sourceCardNo"><s:text name="l.sourceCardNo" /></label>
						<s:textfield type="text" id="sourceCardNo" name="detailTrx.sourceCardNo" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="destCardNo"><s:text name="l.destCardNo" /></label>
						<s:textfield type="text" id="destCardNo" name="detailTrx.destCardNo" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="lastState"><s:text name="l.lastState" /></label>
						<s:textfield type="text" id="lastState" name="detailTrx.lastState" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="lastRc"><s:text name="l.lastRc" /></label>
						<s:textfield type="text" id="lastRc" name="detailTrx.lastRc" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="amount"><s:text name="l.amount" /></label>
						<s:textfield type="text" id="amount" name="detailTrx.amount" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="sysTraceNo"><s:text name="l.sysTraceNo" /></label>
						<s:textfield type="text" id="sysTraceNo" name="detailTrx.systraceNo" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="rcBti"><s:text name="l.rcBTI" /></label>
						<s:textfield type="text" id="rcBti" name="detailTrx.rcBti" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
						
						<label for="channelType"><s:text name="l.channelType" /></label>
						<s:textfield type="text" id="channelType" name="detailTrx.channelType" readonly="true" cssClass="SingleLine" required="true"/>
						<br>
							
						</div>
					</fieldset>
					
         			<div id="divSearch" ></div>
				</s:form>
            </section>
        </main>
	</body>
</html>