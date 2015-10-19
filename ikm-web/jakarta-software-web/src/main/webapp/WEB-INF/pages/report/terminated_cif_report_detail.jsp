<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.report" /> | <s:text name="t.terminatedCif" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="TerminatedCifReport!findAccount"/>
		<script type="text/javascript">
			function findAccount(id)
			{
				jQuery.getJSON('<s:property value="%{remoteurl}" escape="false" />?&cifId=' + id, function(data) {
					$("#accountBody").empty();
					var body=document.getElementById("accountBody");
					var rowAdd="";
					var cardNo="";
					var accountTypeDisplay="";
					
					for (var i=0; i < data.length; i++)
					{
						cardNo=data[i].cardNo||'';
						accountTypeDisplay=data[i].accountTypeDisplay||'';
						rowAdd +="<tr>"
							+"<td>"+data[i].accountNo+"</td>"
							+"<td>"+cardNo+"</td>"
							+"<td>"+accountTypeDisplay+"</td>"							
							+"<td>"+data[i].accIndex+"</td>"							
							+"<td>"+data[i].statusDisplay+"</td>"
							+"<td>"+data[i].createdOn+"</td>"
							+"<td>"+data[i].createdByDisplay+"</td>"
							+"<td>"+data[i].updatedOn+"</td>"
							+"<td>"+data[i].updatedByDisplay+"</td>"
							+"</tr>";
					}
					body.innerHTML+=rowAdd;
				});//end jquery json 
				$('#fsAccountHistory').show();
			}
		
			$(document).ready(function() 
			{
				ButtonRequest("#form1", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
				$('#fsAccountHistory').hide();				
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.report" /> | <s:text name="t.terminatedCif" /></h1>
                <h2><s:text name="t.terminatedCif.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="TerminatedCifReport!gotoSearch" includeParams="none"/>
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
	                    <h2><s:text name="f.detail"/></h2>
	                </div>
                </div>
                
                <!-- PAPER CONTENT -->
                <s:form id="form1" >
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<!-- FORM -->
                	<fieldset>
                    	<legend><s:text name="d.cifHistory"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
                        <table>
							<thead>
								<tr>
									<th><s:text name="l.id" /></th>
									<th><s:text name="l.deviceCode" /></th>
									<th><s:text name="l.accessName" /></th>
									<th><s:text name="l.cifName" /></th>
									<th><s:text name="l.email" /></th>
									<th><s:text name="l.activity" /></th>										
									<th><s:text name="l.status" /></th>
									<th><s:text name="l.authStatus" /></th>
									<th><s:text name="l.authBy" /></th>
									<th><s:text name="l.authOn" /></th>
								</tr>
							</thead>
							<tbody id="bodyId">
								<s:iterator value="listCifHistory" status="stat">
									<tr>
										<td><a href="javascript:void(0)" onclick="findAccount(<s:property value="id" />)" ><s:property value="id" /></a></td>
										<td><s:property value="deviceCode" /></td>
										<td><s:property value="accessName" /></td>
										<td><s:property value="cifName" /></td>
										<td><s:property value="email" /></td>
										<td><s:property value="activityType" /></td>
										<td><s:property value="status" /></td>
										<td><s:property value="authStatus" /></td>
										<td><s:property value="authBy" /></td>
										<td><s:property value="authOn" /></td>
									</tr>
								</s:iterator>
							</tbody>
							<tfoot id="footId">
							</tfoot>
						</table>
                        </div>
                    </fieldset>
                    
                    <fieldset id="fsAccountHistory">
                    	<legend><s:text name="d.accountHistory"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
                        <table>
							<thead>
								<tr>
									<th><s:text name="l.accountNumber" /></th>
									<th><s:text name="l.cardNo" /></th>
									<th><s:text name="l.accountType" /></th>
									<th><s:text name="l.accIndex" /></th>
									<th><s:text name="l.status" /></th>										
									<th><s:text name="l.createdOn" /></th>
									<th><s:text name="l.createdBy" /></th>
									<th><s:text name="l.updatedOn" /></th>
									<th><s:text name="l.updatedBy" /></th>
								</tr>
							</thead>
							<tbody id="accountBody">
							</tbody>
						</table>
                        </div>
                    </fieldset>
    			</s:form>
            </section>
        </main>
	</body>
</html>