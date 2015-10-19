<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.resetPin" /></title>
		
		<s:url var="remoteurl" action="ResetPin!processInput"/>
		<s:url var="forcedAuthUrl" action="CIF!forcedUrl"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{		
				$("#btnSave").click(function()
				{
					$("#MessageResult").removeClass();
					ProcessStart = Date.now();
					$("#ProgressRequest").show();
					formParam = $("#formInput").serialize();
					decodedParam = decodeURI(formParam);
					// PROCESS
					$.ajax
					({
						type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
						url 		: '<s:property value="%{remoteurl}" />', // the url where we want to POST
						data 		: decodedParam, // our data object
					}).done(function(resultJson) 
					{
						if(resultJson.substr(2,7)=="DOCTYPE")
						{
							window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN_EXPIRED"/>';
						}
						$("#ProgressRequest").hide();
						resultObject = JSON.parse(resultJson);
						
						if(resultObject.rc == 0)
						{
							if(resultObject.key1=='<s:property value="@com.emobile.mmbs.web.data.WebConstants@YES"/>')
							{
								window.location.href='<s:property value="%{forcedAuthUrl}"/>?cifHistoryId='+resultObject.key2;
							}
							else
							{
								if(resultObject.type > 0)
								{
									window.location.href=resultObject.path;	
								}
								else
								{
									$("#formInput").trigger("reset");
									ProcessEnd = Date.now();
									ProcessTime = (ProcessEnd - ProcessStart) / 1000;
									
									$("#MessageResult").addClass("MessaageDefault ResultSuccess");
									$("#MessageResult").empty();
									$("#MessageResult").append(resultObject.message + ", process took " + ProcessTime + " seconds");
									$("#checkAccountResult").hide();
								}
							}
						}
						else
						{
							$("#MessageResult").addClass("MessaageDefault ResultFailure");
							$("#MessageResult").empty();
							$("#MessageResult").append(resultObject.message);
							//$("#checkAccountResult").hide();
						}
						
						$("#MessageAccount").addClass("MessageDefault MessageState");
						$("#MessageAccount").empty();
					});
							
					// TIMEOUT
					
					setTimeout(function()
					{
						$("#ProgressRequest").hide();
					}, 
					15000);
					
					return false;
				});
			
			});//end document ready

		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.resetPin" /></h1>
                <h2><s:text name="t.resetPin.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="ResetPin!execute" includeParams="none"/>
					<s:a href="%{search}" cssClass="ClearHyperlink">
		                <div class="PaperHeader">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.search"/></h2>
		                </div>
	                </s:a>
					<s:a href="%{input}" cssClass="ClearHyperlink">
						<div class="PaperHeader PaperSelected">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.update"/></h2>
		                </div>
	                </s:a>
                </div>
                
				<!-- PAPER CONTENT -->
                <s:form id="formInput" >
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<fieldset>
                    	<legend><s:text name="d.cif"/></legend>                    	
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                      	<div class="FieldSetContainer">  
	                       	<s:hidden name="cif.id" id="cifId"/>
							<label for="deviceCode"><s:text name="l.cifDeviceCode" /></label>
							<s:textfield id="deviceCode" name="cif.deviceCode" cssClass="Property" readonly="true"/>
							
							<br>
							<label for="cifCardNo"><s:text name="l.cifCardNo" /></label>
							<s:textfield id="cifCardNo" name="cif.cardNo" cssClass="Property" readonly="true"/>
							
							<br>
							<label for="cifName"><s:text name="l.cifName" /></label>
							<s:textfield id="cifName" name="cif.cifName" cssClass="Property" readonly="true"/>
							
							<br>
							<label for="cifEmail"><s:text name="l.cifEmail" /></label>
							<s:textfield id="cifEmail" name="cif.email" cssClass="Property" readonly="true"/>
							
							<br>
							<label for="identityCode"><s:text name="l.cifIdentityCode" /></label>
							<s:textfield id="identityCode" name="cif.identityCode" cssClass="Property" readonly="true"/>
														
							<br>
							<label for="cifGroup"><s:text name="l.cifGroup" /></label>
							<s:textfield id="cifGroup" name="cif.group" cssClass="Property" readonly="true"/>
														
							<br>
							<label for="accessId"><s:text name="l.cifAccessGroup" /></label>
							<s:textfield id="accessId" name="cif.accessId" cssClass="Property" readonly="true"/>
														
							<br>
							<label for="status"><s:text name="l.status" /></label>
							<s:textfield id="status" name="cif.mobileStatusDisplay" cssClass="Property" readonly="true"/>
							
							<br>
							<label for="useBlastSms"><s:text name="l.useBlastSms" /></label>
							<s:textfield id="useBlastSms" name="cif.useBlastSms" cssClass="Property" readonly="true"/>

							<table>
								<thead>
									<tr>
										<th><s:text name="l.accountIndex" /></th>
										<th><s:text name="l.accountNumber" /></th>
										<th><s:text name="l.cifCardNo" /></th>
										<th><s:text name="l.accountRemarks" /></th>
									</tr>
								</thead>
								<tbody id="bodyId">
									<s:iterator value="listAccount" status="stat">
										<tr>
											<td><s:property value="accIndex" /></td>
											<td><s:property value="accountNo" /></td>
											<td><s:property value="cardNo" /></td>
											<td><s:property value="remarks" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</fieldset>

         			<div class="PositionerCenter">
                    	<input type="submit" id="btnSave" value="<s:text name='b.resetPin' />" class="ButtonPrimary"/>
                    </div>
				</s:form>
			</section>
		</main>
	</body>
</html>