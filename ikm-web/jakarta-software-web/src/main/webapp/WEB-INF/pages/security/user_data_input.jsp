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
		<s:url var="remoteurl" action="UserData!processInput"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				ButtonRequest("#form1", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.security" /> | <s:text name="t.userData" /></h1>
                <h2><s:text name="t.userData.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="UserData!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="UserData!gotoInput" includeParams="none"/>
            		<s:if test="userData.id == 0">
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
						<div class="PaperHeader">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.insert"/></h2>
		                </div>
		                </s:a>
		                <div class="PaperHeader PaperSelected">
		                    <div class="ShapeFolderHornBorder"></div>
		                    <div class="ShapeFolderHornBody"></div>
		                    <h2><s:text name="f.update"/></h2>
		                </div>
					</s:else>
                </div>
                
                <!-- PAPER CONTENT -->
                <s:form id="form1" action="UserData!processInput" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.userData"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                        	<s:hidden name="userData.id" id="userDataId"/>
                            <s:if test="userData.id==0">
                           		<label for="userID"><s:text name="l.userCode" /></label>		
								<s:textfield type="text" id="userID" name="userData.userCode" cssClass="SingleLine" placeholder="%{getText('p.newUserCode')}" required="required"/>                            
							</s:if>
							<s:else>
								<label for="userID"><s:text name="l.userCode" /></label>
								<s:textfield type="text" id="userID" name="userData.userCode" cssClass="SingleLine" placeholder="%{getText('p.currentUserCode')}" readonly="true"/>
							</s:else>
							
							<s:if test="usePasswordMode==@com.emobile.mmbs.web.data.WebConstants@USE_RANDOMIZE_PASS_NO">
								<s:if test="userData.id==0">
									<br>
									<label for="password"><s:text name="l.password"/></label>
									<s:password type="password" id="password" name="userData.userPassword" cssClass="SingleLine" placeholder="%{getText('p.newPassword')}" required="required"/>
									
									<br>
									<label for="confirmPassword"><s:text name="l.confirmPassword" /></label>
									<s:password type="password" id="confirmPassword" name="confirmPassword" cssClass="SingleLine" placeholder="%{getText('p.confirmPassword')}" required="required"/>
								</s:if>
							</s:if>
							
							<br>
							<label for="userName"><s:text name="l.userName" /></label>
							<s:textfield type="text" id="userName" name="userData.userName" cssClass="SingleLine" placeholder="%{getText('p.newUsername')}" required="true"/>
							
							<br>
							<label for="userName"><s:text name="l.department" /></label>
							<s:select id="userName" name="userData.department" list="listDepartment" listValue="lookupDesc" listKey="lookupDesc" cssClass="DropDown"/>
							
							<br>
							<label for="email"><s:text name="l.email" /></label>
							<s:textfield id="email" name="userData.email" cssClass="SingleLine" required="true"/>
							
							<br>
							<label for="userBranch"><s:text name="l.branch" /></label>
							<s:select id="userBranch" name="userData.branchId" list="listBranch" listKey="id" listValue="branchName" cssClass="DropDown"/>
							
							<br>
							<label for="userStatus"><s:text name="l.userStatus" /></label>
							<s:select id="userStatus" name="userData.userStatus" list="listUserStatus" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
							
							<br>
							<label for="userlevel"><s:text name="l.userLevel" /></label>
							<s:select id="userlevel" name="userData.levelId" list="listUserLevel" listKey="id" listValue="levelName" cssClass="DropDown"/>
							
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
         			<div class="PositionerCenter">
                    	<s:submit id="btnSave" type="submit" value="%{getText('b.save')}" cssClass="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
    			</s:form>
            </section>
        </main>
	</body>
</html>