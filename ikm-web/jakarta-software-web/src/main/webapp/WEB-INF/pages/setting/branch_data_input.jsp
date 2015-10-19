<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.branchData" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="BranchData!processInput"/>
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
            	<h1><s:text name="t.setting" /> | <s:text name="t.branchData" /></h1>
                <h2><s:text name="t.branchData.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="BranchData!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="BranchData!gotoInput" includeParams="none"/>
            		<s:if test="branchData.id == 0">
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
                <s:form id="form1" >
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.branchData"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                        	<s:hidden name="branchData.id" id="branchData"/>
							<label for="branchCode"><s:text name="l.branchCode" /></label>		
							<s:textfield type="text" id="branchCode" name="branchData.branchCode" 
								cssClass="SingleLine" required="true"/>

							<br> 
							<label for="branchName"><s:text name="l.branchName" /></label>
							<s:textfield type="text" id="branchName" name="branchData.branchName" 
								cssClass="SingleLine" required="true"/>

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