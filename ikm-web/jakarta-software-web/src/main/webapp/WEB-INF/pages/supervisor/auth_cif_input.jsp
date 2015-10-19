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
		<s:url var="remoteurl" action="AuthCif!processAuthorize" />
		<script type="text/javascript">
			$(document).ready(function()
			{
				ButtonAuthorize("#form1", ".ButtonAuthorize", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
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
					<s:url id="search" action="AuthCif!gotoSearch"
						includeParams="none" />
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
							<s:if test="cifHistoryVO.cifId>0">
								<s:text name="f.update" />
							</s:if>
							<s:else>
								<s:text name="f.insert" />
							</s:else>
						</h2>
					</div>
				</div>
	
				<!-- CONTENT MAIN -->
				<s:form action="AuthCif!processAuthorize" id="form1">
		
					<!-- MESSAGE RESULT -->
					<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
		
					<s:if test="cifHistoryVO.cifId>0">
						<fieldset>
							<legend>
								<s:text name="l.currentData" />
							</legend>
							<div class="ShapeTailBorder"></div>
							<div class="ShapeTailBody"></div>
							<s:actionerror /> <s:actionmessage />
							<div class="FieldSetContainer">				
								<label><s:text name="l.deviceCode" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.deviceCodeOld" /></span>
								
								<br>
								<label><s:text name="l.userName" /></label>
								<span class="Property"><s:property value="cifHistoryVO.cifNameOld" /></span> 
								
								<br> 
								<label><s:text name="l.identityCode" /></label>
								<span class="Property"><s:property value="cifHistoryVO.identityCodeOld" /></span>
								
								<br>
								<label><s:text name="l.userStatus" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.statusDisplayOld" /></span>
								
								<br> 
								<label><s:text name="l.branch" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.branchDisplay" /></span> 
								
								<%--<br>
								<label><s:text name="l.useBlastSms" /></label> 
								<span class="Property"><s:property value="cifHistoryVO.blastSmsOldDisplay" /></span>--%>
							</div>
							
							<div class="TableGroup">	                       
								<table>
									<thead>
										<tr>
											<th><s:text name="l.accountNumber"/></th>
											<th><s:text name="l.cardNo" /></th>
											<th><s:text name="l.remarks" /></th>
											<th><s:text name="l.accIndex" /></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="cifHistoryVO.listAccount" status="Index">
											<tr>
												<td><s:property value="accountNo"/></td>
												<td><s:property value="cardNo"/></td>
												<td><s:property value="remarks"/></td>
												<td><s:property value="accIndex"/></td>
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
							<s:hidden name="actionFrom" id="actionFrom" />		
							<label><s:text name="l.deviceCode" /></label>
							<span class="Property"><s:property value="cifHistoryVO.deviceCode" /></span> 
							
							<br>
							<label><s:text name="l.cifName" /></label>
							<span class="Property"><s:property value="cifHistoryVO.cifName" /></span>
							
							<br>
							<label><s:text name="l.identityCode" /></label>
							<span class="Property"><s:property value="cifHistoryVO.identityCode" /></span>
							
							<br>			
							<label><s:text name="l.userStatus" /></label>
							<span class="Property"><s:property value="cifHistoryVO.statusDisplay" /></span>
							
							<br>
							<label><s:text name="l.branch" /></label>
							<span class="Property"><s:property value="cifHistoryVO.branchDisplay" /></span>
							
						<%--<br> 
							<label><s:text name="l.useBlastSms" /></label>
							<span class="Property"><s:property value="cifHistoryVO.blastSmsDisplay" /></span>	 --%>
							
							<br>
							<label><s:text name="l.requestType" /></label>
							<span class="Property"><s:property value="cifHistoryVO.activityType" /></span>							
						</div>
						
						<div class="TableGroup">	                       
							<table>
								<thead>
									<tr>
										<th><s:text name="l.accountNumber"/></th>
										<th><s:text name="l.cardNo" /></th>
										<th><s:text name="l.remarks" /></th>
										<th><s:text name="l.accIndex" /></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="cifHistoryVO.listAccountHistory" status="Index">
										<tr>
											<td><s:property value="accountNo"/></td>
											<td><s:property value="cardNo"/></td>
											<td><s:property value="remarks"/></td>
											<td><s:property value="accIndex"/></td>
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