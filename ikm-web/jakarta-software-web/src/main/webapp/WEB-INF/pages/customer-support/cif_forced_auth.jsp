<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.supervisor" /> | <s:text name="t.cif" /></title>

		<!-- JAVA SCRIPT -->
		<s:url var="processAuthorize" action="AuthCif!processAuthorize" />
		<s:url var="spvLogin" action="Login!processLoginSupervisor" />
		<script type="text/javascript">
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");	
				ButtonAuthorize("#formAuthorize", ".ButtonAuthorize", "#ProgressRequest", "#MessageResult", '<s:property value="%{processAuthorize}"/>');

				if('<s:property value="hasLogin" />'==0)
				{
					$('#formAuthorize').hide();
					$('#formLogin').show();
				}
				else
				{
					$('#formAuthorize').show();
					$('#formLogin').hide();
				}
				
				$("#btnLogin").click(function()
				{
					$("#MessageResult").removeClass();					
					$("#ProgressRequest").show();
					formParam = $("#formLogin").serialize();
					
					// PROCESS							
					$.ajax
					({
						type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
						url 		: '<s:property value="%{spvLogin}" />', // the url where we want to POST
						data 		: formParam, // our data object
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
							$("#formLogin").hide();
							$("#spvName").html($("#userCode").val());
							$("#formAuthorize").show();
						}
						else
						{
							$("#MessageResult").addClass("ResultFailure");
							$("#MessageResult").empty();
							$("#MessageResult").append(resultObject.message);
							//$("#checkAccountResult").hide();
						}
					});
					// TIMEOUT
					setTimeout(function()
					{
						$("#ProgressRequest").hide();
					}, 
					15000);
					return false;
				});//end btnSave
			});//end jquery
		</script>
	</head>

	<body>
		<!-- MAIN -->
		<main> <!-- PAGE TITLE -->
			<hgroup class="ContainerInlineBlock">
				<h1>
					<s:text name="t.supervisor" />
					|
					<s:text name="t.authCif" />
				</h1>
				<h2>
					<s:text name="t.authCif.description" />
				</h2>
			</hgroup>

			<!-- SHORT PROFILE --> 
			<%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>

			<!-- CONTENT -->
			<section class="ContainerBlock">

				<!-- CONTENT HEADER -->
				<div class="PaperHeaderGroup">
					<s:url id="search" action="Cif!gotoSearch" includeParams="none" />
					<s:a href="%{search}" cssClass="ClearHyperlink">
						<div class="PaperHeader">
							<div class="ShapeFolderHornBorder"></div>
							<div class="ShapeFolderHornBody"></div>
							<h2>
								<s:text name="f.search" />
							</h2>
						</div>
					</s:a>
					<div class="PaperHeader PaperSelected">
						<div class="ShapeFolderHornBorder"></div>
						<div class="ShapeFolderHornBody"></div>
						<h2>
							<s:text name="f.update" />
						</h2>
					</div>
				</div>
	
				<!-- CONTENT form authorize -->
				<s:form id="formLogin">
					<s:hidden name="hasLogin" />
					<!-- MESSAGE RESULT -->
					<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
					
					<fieldset>
                    	<legend><s:text name="l.loginSupervisor"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                        <div class="FieldSetContainer">
                            <label for="userCode"><s:text name="l.userCode"/></label>
                            <s:textfield type="text" id="userCode" name="loginData.userCode" cssClass="SingleLine" placeholder="%{getText('p.currentUserCode')}" required="true"/>
                            <br>
                            <label for="Userpassword"><s:text name="l.password"/></label>
                            <s:password type="password" id="Userpassword" name="loginData.password" cssClass="SingleLine" placeholder="%{getText('p.oldPassword')}" required="true"/>
                        </div>
                    </fieldset>
					
					<div class="PositionerCenter">
						<input type="button" value="<s:text name="b.logIn" />" id="btnLogin" class="ButtonPrimary" /> 
                    </div>
				</s:form>
				
				<s:form id="formAuthorize" >				
					<!-- MESSAGE RESULT -->
					<div id="MessageAuthorize" class="MessageDefault MessageState"></div>
					
					<!-- SHORT PROFILE -->
					<div  class="ProfileExtra" 
						style="
							float: right;
							padding-top: 5px;
							padding-left: 20px;
							padding-right: 20px;
							padding-bottom: 5px;
							font-family: 'Utsaah_Regular';
							font-size: 1.2em;
							text-align: right;
							vertical-align: middle;
							background-color: #fafafa;
							border-style: solid;
							border-width: 1px;
							border-radius: 6px;
							border-color: #f0f0f0;
							color: #787878;
							">
		                <s:text name="w.prefixSupervisor"/> : <mark><span id="spvName"></span></mark>
		                
		                <br>
		                <s:text name="w.prefixRequest"/> : <s:property value="cifHistoryVO.activityType" />
		            </div>
		            <div style="clear: both"></div>
		            <br>
					
					<s:if test="cifHistoryVO.cifId>0">
						<fieldset>
							<legend>
								<s:text name="l.currentData" />
							</legend>
							<div class="ShapeTailBorder"></div>
							<div class="ShapeTailBody"></div>
							<s:actionerror /> <s:actionmessage />
							<div class="FieldSetContainer">				
								<label class="LabelContent"> <s:text name="l.deviceCode" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.deviceCodeOld" /></span>
								
								<br>
								<label class="LabelContent"><s:text name="l.userName" /></label>
								<span class="Property"><s:property value="cifHistoryVO.cifNameOld" /></span> 
								
								<br> 
								<label class="LabelContent"><s:text name="l.identityCode" /></label>
								<span class="Property"><s:property value="cifHistoryVO.identityCodeOld" /></span>
								
								<br>
								<label class="LabelContent"><s:text name="l.userStatus" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.statusDisplayOld" /></span>
								
								<br> 
								<label class="LabelContent"><s:text name="l.branch" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.branchDisplay" /></span> 
								
								<br>
								<label class="LabelContent"><s:text name="l.useBlastSms" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.blastSmsOldDisplay" /></span>
							</div>
							
							<div class="TableGroup">	                       
								<table>
									<thead>
										<tr>
											<th><s:text name="l.accountNumber"/></th>
											<th><s:text name="l.cardNo" /></th>
											<th><s:text name="l.remarks" /></th>
											<th><s:text name="l.accIndex" /></th>
											<th><s:text name="l.status" /></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="cifHistoryVO.listAccount" status="Index">
											<tr>
												<td><s:property value="accountNo"/></td>
												<td><s:property value="cardNo"/></td>
												<td><s:property value="remarks"/></td>
												<td><s:property value="accIndex"/></td>
												<td><s:property value="statusDisplay"/></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
						</fieldset>
					</s:if>
					
					<fieldset>
						<legend>
							<s:if test="cifHistoryVO.cifId==0">
								<s:text name="d.cifData" />
							</s:if>
							<s:else>
								<s:text name="l.proposedData" />
							</s:else>
						</legend>
						<div class="ShapeTailBorder"></div>
						<div class="ShapeTailBody"></div>
						<s:actionerror /><s:actionmessage />
						<div class="FieldSetContainer">
							<s:hidden name="cifHistoryVO.id" />
							<s:hidden name="cifHistoryVO.cifId" />
							<s:hidden name="mode" id="mode" />
							<s:hidden name="actionFrom" id="actionFrom"/>
							<s:if test="cifHistoryVO.cifId==0">
								<label class="LabelContent"> <s:text name="l.requestType" /></label>
								<span class="Property"><s:property value="cifHistoryVO.activityType" /></span>
								<br>
							</s:if>
		
							<label class="LabelContent"><s:text name="l.deviceCode" /></label>
							<span class="Property"><s:property value="cifHistoryVO.deviceCode" /></span> 
							
							<br>
							<label class="LabelContent"><s:text name="l.cifName" /></label>
							<span class="Property"><s:property value="cifHistoryVO.cifName" /></span>
							
							<br>
							<label class="LabelContent"><s:text name="l.identityCode" /></label>
							<span class="Property"><s:property value="cifHistoryVO.identityCode" /></span>
							
							<br>			
							<label class="LabelContent"><s:text name="l.userStatus" /></label>
							<span class="Property"><s:property value="cifHistoryVO.statusDisplay" /></span>
								
							
							<br>
							<label class="LabelContent"><s:text name="l.branch" /></label>
							<span class="Property"><s:property value="cifHistoryVO.branchDisplay" /></span>
							
							<br> 
							<label class="LabelContent"><s:text name="l.useBlastSms" /></label>
							<span class="Property"><s:property value="cifHistoryVO.blastSmsDisplay" /></span>	
						</div>
						
						<div class="TableGroup">	                       
							<table>
								<thead>
									<tr>
										<th><s:text name="l.accountNumber"/></th>
										<th><s:text name="l.cardNo" /></th>
										<th><s:text name="l.remarks" /></th>
										<th><s:text name="l.accIndex" /></th>
										<th><s:text name="l.status" /></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="cifHistoryVO.listAccountHistory" status="Index">
										<tr>
											<td><s:property value="accountNo"/></td>
											<td><s:property value="cardNo"/></td>
											<td><s:property value="remarks"/></td>
											<td><s:property value="accIndex"/></td>
											<td><s:property value="statusDisplay"/></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</fieldset>
												
					<div class="PositionerCenter">
						<input type="button" value="<s:text name="b.approve" />" id="btnApprove" class="ButtonPrimary ButtonAuthorize" /> 
						<input type="button" value="<s:text name="b.reject" />" id="btnReject" class="ButtonPrimary ButtonAuthorize" />
					</div>
				</s:form>
			</section>
		</main>
	</body>
</html>