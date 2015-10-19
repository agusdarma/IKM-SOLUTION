<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.trxLogBrowser" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="listUrl" action="TrxLogBrowser!processSearch" />
		<script type="text/javascript">			
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				$('#btnSearch').click(function()
				{	
					$('#exportType').val("");
					GenerateTable("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{listUrl}"/>');
				});
			});
			
			
			$(document).ready(function(){
				var trxCode = $('#trxCode').val();
				if(trxCode == 'TRF_OTH'){
					$("#bank").show();
					$("#product").hide();
					$("#biller").hide();
				} else if(trxCode == 'PURCHASE'){
					$("#bank").hide();
					$("#product").show();
					$("#biller").hide();
				} else if(trxCode == 'BILL_PMT'){
					$("#bank").hide();
					$("#product").hide();
					$("#biller").show();
				} else {
					$("#bank").hide();
					$("#product").hide();
					$("#biller").hide();
				}
				
				$('#trxCode').change(function(){
					if($('#trxCode').val() == 'TRF_OTH'){
						$("#bank").show();
						$("#product").hide();
						$("#biller").hide();
					} else if($('#trxCode').val() == 'PURCHASE'){
						$("#bank").hide();
						$("#product").show();
						$("#biller").hide();
					} else if($('#trxCode').val() == 'BILL_PMT'){
						$("#bank").hide();
						$("#product").hide();
						$("#biller").show();
					} else {
						$("#bank").hide();
						$("#product").hide();
						$("#biller").hide();
					}
				});
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
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="TrxLogBrowser!processSearch" id="formSearch" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	<fieldset>
                    	<legend><s:text name="d.trxLogBrowser"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
						<%--<s:hidden name="tableId" value="tag" />--%>
						<%@ include file="/WEB-INF/includes/param_export.jsp" %>
                            <label for="trxDate"><s:text name="l.trxDate"/></label>
                            <s:textfield type="text" id="trxDate" name="paramVO.startDate" cssClass="Date"/> - <s:textfield type="text" id="trxDate" name="paramVO.endDate" cssClass="Date"/>
	                        
	                        <br>
	                        <label for="syslogno"><s:text name="l.syslogno"/></label>
                            <s:textfield type="text" id="syslogno" name="paramVO.syslogno" maxlength="30" cssClass="SingleLine" />
                            
                            <br>
	                        <label for="phoneNo"><s:text name="l.phoneNo"/></label>
                            <s:textfield type="text" id="phoneNo" name="paramVO.phoneNo" maxlength="30" cssClass="SingleLine" />
                            
                            <br>
	                        <label for="sourceAccount"><s:text name="l.sourceAccount"/></label>
                            <s:textfield type="text" id="sourceAccount" name="paramVO.sourceAccount" maxlength="30" cssClass="SingleLine" />
                            
                            <br>
	                        <label for="chType"><s:text name="l.channelType"/></label>
                            <s:select type="text" id="chType" name="paramVO.channelType" cssClass="DropDown" 
                            	headerKey="all" headerValue="All Channel" list="listChannelType" listValue="lookupDesc" listKey="lookupValue" />
                            
                            <br>
                            <label for="trxCode"><s:text name="l.trxName" /></label>
							<s:select id="trxCode" name="paramVO.trxCode" list="listTrxCode" 
								headerKey="all" headerValue="All Transaction" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
							
							<tag id="bank">
								<br>
	                            <label for="bankSelect"><s:text name="l.bankName" /></label>
								<s:select id="bankSelect" name="paramVO.bankCode" headerKey="all" headerValue="All"
									list="listBankCode" listValue="bankName" listKey="bankCode" cssClass="DropDown"/>
							</tag>
							
							<anes id="product">
								<br>
	                            <label for="productSelect"><s:text name="l.productName" /></label>
								<s:select id="productSelect" name="paramVO.productCode" headerKey="all" headerValue="All"
									list="listProduct" listValue="productName" listKey="productCode" cssClass="DropDown"/>
							</anes>
							
							<tag id="biller">
								<br>
	                            <label for="billerSelect"><s:text name="l.billerName" /></label>
								<s:select id="billerSelect" name="paramVO.billerCode" headerKey="all" headerValue="All"
									 list="listBillPayment" listValue="billerName" listKey="billerNo" cssClass="DropDown"/>
							</tag>

							<br>
                            <label for="listAccountType"><s:text name="l.accountType" /></label>
							<s:select id="listAccountType" name="paramVO.accountType" headerKey="all" headerValue="All"
								list="listAccountType" listValue="groupName" listKey="id" cssClass="DropDown"/>
							
							<br>
                            <label for="listStatus"><s:text name="l.status" /></label>
							<s:select id="listStatus" name="paramVO.status" headerKey="all" headerValue="All" 
								list="listStatus" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
							
							<br>
                            <label for="listTrxType"><s:text name="l.trxType" /></label>
							<s:select id="listTrxType" name="paramVO.trxType" headerKey="-1" headerValue="All"  
								list="listTrxType" listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
														
						</div>
					</fieldset>	

                    <br>
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