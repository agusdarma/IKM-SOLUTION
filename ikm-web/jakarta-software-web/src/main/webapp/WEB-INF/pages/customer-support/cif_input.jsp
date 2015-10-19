<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security" /> | <s:text name="t.cif" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="CIF!processInput"/>
		<s:url var="forcedAuthUrl" action="CIF!forcedUrl"/>
		<s:url var="saveCifValue" action="CIF!saveCifValue"/>
		<s:url var="updateCifValue" action="CIF!updateCifValue"/>
		<s:url var="deleteCifValue" action="CIF!deleteCifValue"/>
		<s:url var="checkAccount" action="CIF!checkAccount"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				$("#btnSave").click(function()
				{
					$("#MessageAccount").removeClass();
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
									
									$("#MessageResult").addClass("MessageDefault ResultSuccess");
									$("#MessageResult").empty();
									$("#MessageResult").append(resultObject.message + ", process took " + ProcessTime + " seconds");
									$("#checkAccountResult").hide();
								}
							}
						}
						else
						{
							$("#MessageResult").addClass("MessageDefault ResultFailure");
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
				
				$('#btnCheck').click(function()
				{
					$("#MessageResult").removeClass();
					$("#MessageAccount").removeClass();		
					ProcessStart = Date.now();
					$("#ProgressRequest").show();
					formParam = $("#formInput").serialize();					
					$.ajax
					({
						type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
						url 		: '<s:property value="%{checkAccount}" />', // the url where we want to POST
						data 		: formParam, // our data object
					}).done(function(resultJson) 
					{
						if(resultJson.substr(2,7)=="DOCTYPE")
						{
							window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN_EXPIRED"/>';
						}
						$("#ProgressRequest").hide();
						var result = JSON.parse(resultJson);
						if (result.resultCode == 0)
						{
							ProcessEnd = Date.now();
							ProcessTime = (ProcessEnd - ProcessStart) / 1000;
							
							$("#MessageAccount").addClass("MessageDefault ResultSuccess");
							$("#MessageAccount").empty();
							$("#MessageAccount").append("Check success, process took " + ProcessTime + " seconds");

							var body = document.getElementById("bodyId");
							/* $("#bodyId").empty(); */
							var rowLine = "";
							for (var i=0; i < result.listAccount.length; i++)
							{
								rowLine +="<tr>"
									+"<td><input type=\"number\" class=\"ShortLine\" name=\"cif.listAccount["+i+"].accIndex\" maxlength=\"2\" size=\"5\" value="+result.listAccount[i].accIndex+" /></td>"
									+"<td><input type=\"hidden\" name=\"cif.listAccount["+i+"].accountNo\" value='"+result.listAccount[i].accountNo+"'/>"+result.listAccount[i].accountNo+"</td>"
									+"<td><input type=\"hidden\" name=\"cif.listAccount["+i+"].cardNo\" value='"+result.listAccount[i].cardNo+"' />"+result.listAccount[i].cardNo+"</td>"
									+"<td><input type=\"hidden\" name=\"cif.listAccount["+i+"].remarks\" value='"+result.listAccount[i].remarks+"'/>"+result.listAccount[i].remarks+"</td>"
									+"</tr>"
							}
							body.innerHTML=rowLine;
							columnFlag=0;
						}
						else
						{
							$("#MessageAccount").addClass("MessageDefault ResultFailure");
							$("#MessageAccount").empty();
							$("#MessageAccount").append(result.errorMessage);
						}
	 				});
					
					$("#MessageResult").addClass("MessageDefault MessageState");
					$("#MessageResult").empty();
				});

			});//end document ready
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.cif" /></h1>
                <h2><s:text name="t.cif.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="CIF!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="CIF!gotoInputTemp" includeParams="none"/>
            		<s:if test="cif.id == 0">
            			<s:a href="%{search}" cssClass="ClearHyperlink">
			                <div class="PaperHeader">
			                    <div class="ShapeFolderHornBorder"></div>
			                    <div class="ShapeFolderHornBody"></div>
			                    <h2><s:text name="f.search"/></h2>
			                </div>
		                </s:a>
						<div class="PaperHeader PaperSelected">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.insert"/></h2>
		                </div>
					</s:if>
					<s:else>
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
		                    <h2><s:text name="f.insert"/></h2>
		                </div>
		                </s:a>
						<s:a href="%{input}" cssClass="ClearHyperlink">
						<div class="PaperHeader PaperSelected">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.update"/></h2>
		                </div>
		                </s:a>
					</s:else>
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
	                        <s:if test="cif.id==0">
								<label for="deviceCode"><s:text name="l.cifDeviceCode" /></label>		
								<s:textfield type="text" id="deviceCode" name="cif.deviceCode" maxlength="20" cssClass="SingleLine" required="required"/>																								
							</s:if>
							<s:else>
								<label for="deviceCode"><s:text name="l.cifDeviceCode" /></label>
								<s:textfield type="text" id="deviceCode" name="cif.deviceCode" cssClass="SingleLine" readonly="true"/>
							</s:else>	
							
							<%--  BANK SUMUT GA PAKE ACCOUNT TYPE
							<br>
							<label for="cifAccountType"><s:text name="l.cifAccountType" /></label>
							<s:select id="cifAccountType" name="cif.accountType" list="listAccountType" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/> 
							--%>
												
							<br>
							<label for="cifName"><s:text name="l.cifName" /></label>
							<s:textfield id="cifName" name="cif.cifName" maxlength="50" required="required" cssClass="SingleLine" />
							
							<br>
							<label for="cifEmail"><s:text name="l.cifEmail" /></label>
							<s:textfield id="cifEmail" name="cif.email" maxlength="50" cssClass="SingleLine" />
							
							<br>
							<label for="identityCode"><s:text name="l.cifIdentityCode" /></label>
							<s:textfield id="identityCode" name="cif.identityCode" maxlength="50" required="required" cssClass="SingleLine" />
							
							<br>
							<label for="motherName"><s:text name="l.motherName" /></label>
							<s:textfield id="motherName" name="cif.motherName" required="required" cssClass="SingleLine" />
							
							<br>
							<label for="address"><s:text name="l.address" /></label>
							<s:textfield id="motherName" name="cif.address" required="required" cssClass="SingleLine" />
							
							<br>
							<label for="birthDate"><s:text name="l.birthDate" /></label>
							<s:textfield id="birthDate" name="cif.birthDate"  required="required" cssClass="Date" />
													
							<br>
							<label for="cifGroup"><s:text name="l.cifGroup" /></label>
							<s:select id="cifGroup" name="cif.group" list="listCifGroup" listKey="id" listValue="groupName" cssClass="DropDown"/>
														
							<br>
							<label for="accessId"><s:text name="l.cifAccessGroup" /></label>
							<s:select id="accessId" name="cif.accessId" list="listAccessId" listKey="id" listValue="accessName" cssClass="DropDown"/>
														
							<br>
							<label for="status"><s:text name="l.status" /></label>							
							<s:if test="cif.id == 0">
								<span class="Property"><s:text name="l.active" /></span>
								<s:hidden name="cif.status"/>
							</s:if>
							<s:else>
								<s:select id="status" name="cif.status" list="listStatus" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
							</s:else>
											
							<%-- <br>
							<label for="useBlastSms"><s:text name="l.useBlastSms" /></label> 
							<s:select id="useBlastSms" name="cif.useBlastSms" list="#{0:'No', 1:'Yes'}" cssClass="DropDown"/>
							--%>
							<s:hidden name="cif.useBlastSms" value="0" />

						</div>
					</fieldset>
					
					<br>
					<div id="MessageAccount" class="MessageDefault MessageState"></div>
					
					<fieldset>
                    	<legend><s:text name="d.account"/></legend>                    	
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                      	<div class="FieldSetContainer">
	                      	<s:if test='cif.authStatus!="Y"'>
	                      		<div class="PositionerCenter" style="margin-bottom: 0px;">
		                      		<label for="cifAccount"><s:text name="l.cifAccount" /></label>
									<s:textfield id="cifAccount" name="accountNo"  required="required" cssClass="SingleLine"/>					
								
									<br>
									<label for="cifCardNo"><s:text name="l.cifCardNo" /></label>
									<s:textfield id="cifCardNo" name="cardNo" required="required" cssClass="SingleLine"/>
								
									<br>
									<input type="button" id="btnCheck" value="<s:text name='b.add' />" class="ButtonPrimary"/>
								</div>
							</s:if>
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
									<s:if test="cif.listAccount.size > 0" >
										<s:iterator value="cif.listAccount" status="stat">
			                            <tr>
			                            	<td><s:textfield type="number" id="accountIndex" cssClass="ShortLine" name="cif.listAccount[%{#stat.index}].accIndex" maxlength="2" size="5" /></td>
			                            	<td><s:hidden name="cif.listAccount[%{#stat.index}].accountNo" /><s:property value="accountNo" /></td>
			                            	<td><s:hidden name="cif.listAccount[%{#stat.index}].cardNo" /><s:property value="cardNo" /></td>
			                            	<td><s:hidden name="cif.listAccount[%{#stat.index}].remarks" /><s:property value="remarks" /></td>				                            	
			                            </tr>
			                            </s:iterator>
		                            </s:if>
		                            <s:if test="cif.listAccount.size == 0" >
			                            <tr>
			                            	<td colspan="4">No Account</td>
			                            </tr>
		                            </s:if>
								</tbody>
							</table>
                      	</div>
					</fieldset>
					
					<!-- BUTTON -->
					<s:if test='cif.authStatus!="Y"'>
	         			<div class="PositionerCenter" style="margin-top: 20px;">
	                    	<input type="button" id="btnSave" value="<s:text name='b.save' />" class="ButtonPrimary"/>
	                    	<s:reset type="reset" id="btnReset" cssClass="ButtonReset" value=""/>
	                    </div>
                    </s:if>
				</s:form>
			</section>
		</main>
	</body>
</html>