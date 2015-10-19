<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.product" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="Product!processSearch"/>
		<script type="text/javascript">	 
			$(document).ready(function()
			{
				MessageResult("<s:property value='message' />", "#MessageResult");
				DirectSubmit("#formSearch", "#btnSearch", "#ProgressRequest", "divSearch", '<s:property value="%{remoteurl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.customerSupport" /> | <s:text name="t.product" /></h1>
                <h2><s:text name="t.product.description" /></h2>
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
	                <s:url id="input" action="Product!gotoInput" includeParams="none"/>
					<s:a href="%{input}" cssClass="ClearHyperlink">
					<div class="PaperHeader">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
					</s:a>
                </div>
                
                <!-- CONTENT MAIN -->
                <s:form action="Product!processSearch" id="formSearch" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.productData"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
	                       	<label for="productCode"><s:text name="l.productCode" /></label>		
							<s:textfield type="text" id="productCode" name="paramVO.productCode" maxlength="15" cssClass="SingleLine"/>
	                       	
							<br>
							<label for="productName"><s:text name="l.productName" /></label>
							<s:textfield id="productName" name="paramVO.productName" maxlength="25" cssClass="SingleLine"/>
							
							<br>
							<label for="institutionCode"><s:text name="l.institutionCode" /></label>
							<s:textfield id="institutionCode" name="paramVO.institutionCode" maxlength="3" cssClass="SingleLine"/>
														
							<br>
							<label for="productType"><s:text name="l.productType" /></label>
							<s:select id="productType" name="paramVO.productType" list="listProductType" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
														
							<br>
							<label for="status"><s:text name="l.status" /></label>
							<s:select id="status" name="paramVO.status" list="listStatus" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
						</div>
                    </fieldset>

					<div class="PositionerCenter">
	                   	<input type="submit" id="btnSearch" class="ButtonPrimary" value="search"/>
	                   	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                     </div>
                    
                    <div id="divSearch">                    	
                    </div>
                </s:form>
            </section>
        </main>
	</body>
</html>