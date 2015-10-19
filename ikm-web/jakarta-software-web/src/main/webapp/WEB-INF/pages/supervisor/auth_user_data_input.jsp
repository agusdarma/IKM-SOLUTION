<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security" /> | <s:text name="t.userData" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="AuthUserData!processAuthorize"/>
		<script type="text/javascript">
			$(document).ready(function()
			{
				ButtonAuthorize("#form1", ".ButtonAuthorize", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
			});//end jquery				
		</script>		
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.supervisor" /> | <s:text name="t.authUserData" /></h1>
                <h2><s:text name="t.authUserData.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="AuthUserData!gotoSearch" includeParams="none"/>
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
	                    <h2><s:text name="f.update"/></h2>
	                </div>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="AuthUserData!processAuthorize" id="form1" >
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
					<s:if test="userDataVO.userId>0">
						<fieldset>
	                    	<legend><s:text name="l.currentData" /></legend>
	                        <div class="ShapeTailBorder"></div>
	                        <div class="ShapeTailBody"></div>
	                        <s:actionerror /><s:actionmessage />
							<div class="FieldSetContainer">
								<label for="userID"><s:text name="l.userCode" /></label>
								<span class="Property" ><s:property value="userDataCurrent.userCode" /></span>
			
								<br>
								<label for="userName"><s:text name="l.userName" /></label>
								<span class="Property" ><s:property value="userDataCurrent.userName" /></span>
								
								<br>
								<label for="email"><s:text name="l.email" /></label>
								<span class="Property" ><s:property value="userDataCurrent.email" /></span>
								
								<br>
								<label for="userlevel"><s:text name="l.userLevel" /></label>
								<span class="Property" ><s:property value="userDataCurrent.userLevelDisplay" /></span>
								
								<br>
								<label for="userInvalidCount"><s:text name="l.invalidCount" /></label>
								<span class="Property" ><s:property value="userDataCurrent.invalidCount" /></span>
			
								<br>
								<label for="userStatus"><s:text name="l.userStatus" /></label>
								<span class="Property" ><s:property value="userDataCurrent.userStatusDisplay" /></span>
			
								<br>
								<label for="branch"><s:text name="l.branch" /></label>
								<span class="Property" ><s:property value="userDataCurrent.branchDisplay" /></span>
			
								<br>
								<label for="department"><s:text name="l.department" /></label>
								<span class="Property" ><s:property value="userDataCurrent.department" /></span>
			
								<br>
								<label for="createdOn"><s:text name="l.createdOn" /></label>
								<span class="Property" ><s:property value="userDataCurrent.createdOn" /></span>
			
								<br>
								<label for="createdBy"><s:text name="l.createdBy" /></label>
								<span class="Property" ><s:property value="userDataCurrent.createdByDisplay" /></span>
								
								<br>
								<label for="updatedOn"><s:text name="l.updatedOn" /></label>
								<span class="Property" ><s:property value="userDataCurrent.updatedOn" /></span>
			
								<br>
								<label for="updatedBy"><s:text name="l.updatedBy" /></label>
								<span class="Property" ><s:property value="userDataCurrent.updatedByDisplay" /></span>
							</div>
						</fieldset>
					</s:if>           		
					
                	<fieldset>
                    	<legend>
                    	<s:if test="userDataVO.userId==0">
                    		<s:text name="d.userData"/>
                    	</s:if>
                    	<s:else>
                    		<s:text name="l.proposedData" />
                    	</s:else>
                    	</legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
						<div class="FieldSetContainer">
							<s:hidden name="userDataVO.id"/>
							<s:hidden name="userDataVO.userId" />
							<s:hidden name="mode" id="mode" />																			
							<label for="userID"><s:text name="l.userCode" /></label>
							<span class="Property" ><s:property value="userDataVO.userCode" /></span>
		
							<br>
							<label for="userName"><s:text name="l.userName" /></label>
							<span class="Property" ><s:property value="userDataVO.userName" /></span>
							
							<br>
							<label for="email"><s:text name="l.email" /></label>
							<span class="Property" ><s:property value="userDataVO.email" /></span>
							
							<br>
							<label for="userlevel"><s:text name="l.userLevel" /></label>
							<span class="Property" ><s:property value="userDataVO.userLevelDisplay" /></span>
							
							<br>
							<label for="userInvalidCount"><s:text name="l.invalidCount" /></label>
							<span class="Property" ><s:property value="userDataVO.invalidCount" /></span>
		
							<br>
							<label for="userStatus"><s:text name="l.userStatus" /></label>
							<span class="Property" ><s:property value="userDataVO.userStatusDisplay" /></span>
		
							<br>
							<label for="branch"><s:text name="l.branch" /></label>
							<span class="Property" ><s:property value="userDataVO.branchDisplay" /></span>
		
							<br>
							<label for="department"><s:text name="l.department" /></label>
							<span class="Property" ><s:property value="userDataVO.department" /></span>
		
							<br>
							<label for="createdOn"><s:text name="l.createdOn" /></label>
							<span class="Property" ><s:property value="userDataVO.createdOn" /></span>
		
							<br>
							<label for="createdBy"><s:text name="l.createdBy" /></label>
							<span class="Property" ><s:property value="userDataVO.createdByDisplay" /></span>
							
							<br>
							<label for="updatedOn"><s:text name="l.updatedOn" /></label>
							<span class="Property" ><s:property value="userDataVO.updatedOn" /></span>
		
							<br>
							<label for="updatedBy"><s:text name="l.updatedBy" /></label>
							<span class="Property" ><s:property value="userDataVO.updatedByDisplay" /></span>
							
							<br>
							<label for="activityType"><s:text name="l.requestType" /></label>
							<span class="Property" ><s:property value="userDataVO.activityType" /></span>
							
						</div>
					</fieldset>
					
					<br>
					<div class="PositionerCenter">
						<input type="button" value="<s:text name="b.approve" />" id="btnApprove" class="ButtonPrimary ButtonAuthorize" />
						<input type="button" value="<s:text name="b.reject" />" id="btnReject" class="ButtonPrimary ButtonAuthorize" />	
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
					</div>
				</s:form>
            </section>
        </main>
	</body>
</html>