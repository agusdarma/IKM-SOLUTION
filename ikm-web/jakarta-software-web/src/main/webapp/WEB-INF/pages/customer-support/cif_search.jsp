<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.security" /> | <s:text name="t.cif" /></title>
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="CIF!processSearch"/>
		<script type="text/javascript">	 
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				$('#btnSearch').click(function(){	
					GenerateTable("#formSearch", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
				});

			});
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
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
	                <s:url id="input" action="CIF!gotoInputTemp" includeParams="none"/>
					<s:a href="%{input}" cssClass="ClearHyperlink">
					<div class="PaperHeader">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
					</s:a>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="CIF!processSearch" id="formSearch" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.cif"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
	                       	<label for="mobilePhone"><s:text name="l.cifDeviceCode" /></label>		
							<s:textfield type="text" id="deviceCode" name="paramVO.mobilePhone" maxlength="15" cssClass="SingleLine" required="required"/>
	                       	
							<br>
							<label for="name"><s:text name="l.cifName" /></label>
							<s:textfield id="cifName" name="paramVO.name" maxlength="25" required="required" cssClass="SingleLine"/>
							
							<br>
							<label for="email"><s:text name="l.cifEmail" /></label>
							<s:textfield id="cifEmail" name="paramVO.email" maxlength="30" required="required" cssClass="SingleLine"/>
														
							<br>
							<label for="status"><s:text name="l.status" /></label>
							<s:select id="status" name="paramVO.status" list="listStatus" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
							
							<div class="PositionerCenter">
	    	        	       	<input type="button" id="btnSearch" class="ButtonPrimary" value="search"/>
		        	           	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
	        	             </div>
						</div>
                    </fieldset>
					                    
                    <div id="divSearch">                    	
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>