<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="j"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting"/> | <s:text name="t.accessGroup"/></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="AccessGroup!processSearch"/>
		<script type="text/javascript">
		$(document).ready(function() 
		{
			MessageResult("<s:property value='message' />", "#MessageResult");
			//GenerateTable(FormID, ButtonID, ProgressRequest, divID, arrayHead, arrayBody, urlDetail)
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
            	<h1><s:text name="t.setting"/> | <s:text name="t.accessGroup"/></h1>
                <h2><s:text name="t.accessGroup.description"/></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
	                <div class="PaperHeader PaperSelected">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.search"/></h2>
	                </div>
	                <s:url id="input" action="AccessGroup!gotoInput" includeParams="none"/>
					<s:a href="%{input}" cssClass="ClearHyperlink">
					<div class="PaperHeader">
	                    <div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.insert"/></h2>
	                </div>
					</s:a>
                </div>
                
                <!-- PAPER MAIN -->
                <s:form id="formSearch" action="AccessGroup!processSearch" method="post">
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
                	
                	<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.accessGroup"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                        <div class="FieldSetContainer">
                            <label for="accessName"><s:text name="l.accessName"/></label>
                            <s:textfield type="text" id="accessName" name="paramVO.accessName" cssClass="SingleLine" placeholder="%{getText('p.accessName')}"/>
                        </div>
                    </fieldset>
                    
                    <!-- BUTTON -->
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