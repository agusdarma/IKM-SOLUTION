<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.groupLimit" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="GroupLimit!processInput"/>
		<s:url var="saveGroupLimitDetail" action="GroupLimit!saveGroupLimitDetail"/>
		<s:url var="deleteGroupLimitDetail" action="GroupLimit!deleteGroupLimitDetail"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{	
				$('#newRow').hide();
				$('#addRowInput').attr('disabled',false);
				ButtonRequest("#formInput", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');				
				
				$('#addRowInput').click(function()
				{
					$('#adminFee').val('0');
					$('#limitPerTrx').val('0');
					$('#limitPerDay').val('0');
					$('#transactionCode').prop('selectedIndex', 0);
					$('#newRow').show();
					$('#addRowInput').attr('disabled',true);
				});
				
				$('#btnCancelAdd').click(function()
				{
					$('#newRow').hide();
					$('#addRowInput').attr('disabled',false);
				});//end .click
				
				$('#btnSaveRow').click(function()
				{
	 	 			var formParam ="groupLimitDetail.trxCode="+$('#transactionCode').val()
	 	 						+"&groupLimitDetail.limitPerTrx="+$('#limitPerTrx').val()
	 	 						+"&groupLimitDetail.limitPerDay="+$('#limitPerDay').val()
	 	 						+"&groupLimitDetail.feeTrx="+$('#adminFee').val()
	 	 						+"&groupLimitDetail.groupLimitId="+$('#groupLimitHeaderId').val();
					$.ajax
					({
						type 		: 'POST',
						url 		: '<s:property value="%{saveGroupLimitDetail}" />',
						data 		: formParam, // our data object
					}).done(function(resultJson) 
					{					
						var resultSplit=resultJson.split('-');
						if(resultSplit[0]=="duplicate")
						{
							$("#errorMessage").text(resultSplit[1]);	
						}
						else
						{
							var body=document.getElementById("bodyId");
							$("#bodyId").empty();
							var listGroupLimit = JSON.parse(resultJson);
							var rowLine="";
							for (var i=0; i < listGroupLimit.length; i++)
							{
								rowLine +="<tr id=\"row"+i+"\">"
									+"<td>"+listGroupLimit[i].trxCodeDisplay+"</td>"	
									+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerTrx)+"</td>"
									+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerDay)+"</td>"
									+"<td>"+accounting.formatMoney(listGroupLimit[i].feeTrx)+"</td>"										
									+"<td>"
									+"	<input type='button' class='ButtonDelete' onclick=\"deleteBody("+listGroupLimit[i].id+")\" />"
									+"	<input type='button' class='ButtonEdit'"
									+"	onclick=\"editBody("+i+","+listGroupLimit[i].id+", '"+listGroupLimit[i].trxCode+"'"
									+", "+listGroupLimit[i].limitPerDay+", "+listGroupLimit[i].limitPerTrx+""
									+", "+listGroupLimit[i].feeTrx+", '"+listGroupLimit[i].trxCodeDisplay+"')\" />"
									+"</td>"
									+"</tr>";
							}
							body.innerHTML=rowLine; 
							$('#newRow').hide();
							$('#addRowInput').attr('disabled',false);
						}
					});								
				});//end .click
				
			});//end document ready
			
			function editBody(rowId, detailId, trxCode, limitPerDay, limitPerTrx, feeTrx, trxCodeDisplay)
			{
				var listTrxCode = JSON.parse($('#listTrxCodeJson').val());
				var trxCodeId = "trxCode"+rowId;
				var limitPerDayId = "limitPerDay"+rowId;
				var limitPerTrxId = "limitPerTrx"+rowId;
				var feeTrxId = "feeTrx"+rowId;
				var row="row"+rowId;
				var rowHtml = document.getElementById(row);
				
				rowHtml.innerHTML="<td><select class=\"DropDown\" name=\"trxCodeEdit\" id=\""+trxCodeId+"\" /></td>"	
					+"<td><input type=\"text\" class=\"SingleLine\" id=\""+limitPerTrxId+"\" name=\"limitPerTrxEdit\" value=\""+limitPerTrx+"\" /></td>"
					+"<td><input type=\"text\" class=\"SingleLine\" id=\""+limitPerDayId+"\" name=\"limitPerDayEdit\" value=\""+limitPerDay+"\" /></td>"
					+"<td><input type=\"text\" class=\"SingleLine\" id=\""+feeTrxId+"\" name=\"feeTrxEdit\" value=\""+feeTrx+"\" /></td>"
					+"<td><input type='button' class='ButtonCancel' onclick=\"cancelUpdate("+rowId+","+detailId+", '"+trxCode+"', "+limitPerDay+", "+limitPerTrx+", "+feeTrx+", '"+trxCodeDisplay+"')\"/>"
					+"<input type='button' class='ButtonSave' onclick='updateDetail("+rowId+","+detailId+")'/></td>"; 
				for (var i=0; i < listTrxCode.length; i++) 
				{
					var o = document.createElement("option");
					o.text = listTrxCode[i].lookupDesc;
					o.value = listTrxCode[i].lookupValue;
					if(listTrxCode[i].lookupValue==trxCode)
						o.selected= true ;
					$('#'+trxCodeId).append(o);
				}	
			}
			
			function cancelUpdate(rowId, detailId, trxCode, limitPerDay, limitPerTrx, feeTrx, trxCodeDisplay)
			{
				var row="row"+rowId;
				var rowHtml = document.getElementById(row);
				rowHtml.innerHTML="<td>"+trxCodeDisplay+"</td>"						
					+"<td>"+accounting.formatMoney(limitPerTrx)+"</td>"
					+"<td>"+accounting.formatMoney(limitPerDay)+"</td>"
					+"<td>"+accounting.formatMoney(feeTrx)+"</td>"										
					+"<td>"
					+"	<input type='button' class='ButtonDelete' onclick=\"deleteBody("+detailId+")\" />"
					+"	<input type='button' class='ButtonEdit' onclick=\"editBody("+rowId+", "+detailId+", '"+trxCode+"', "+limitPerDay+", "+limitPerTrx+", "+feeTrx+", '"+trxCodeDisplay+"')\" />"
					+"</td>";
			}
			
			function updateDetail(rowId, detailId)
			{
				$("#errorMessage").removeClass();
				$("#errorMessage").html("");
 				var trxCode=$('#trxCode'+rowId).val();
				var limitPerDay=$('#limitPerDay'+rowId).val();
				var limitPerTrx=$('#limitPerTrx'+rowId).val();
				var feeTrx=$('#feeTrx'+rowId).val();
				var formParam ="groupLimitDetail.trxCode="+trxCode
					+"&groupLimitDetail.limitPerTrx="+limitPerTrx
					+"&groupLimitDetail.limitPerDay="+limitPerDay
					+"&groupLimitDetail.feeTrx="+feeTrx
					+"&groupLimitDetail.id="+detailId
					+"&groupLimitDetail.groupLimitId="+$('#groupLimitHeaderId').val();		
				$.ajax
				({
					type 		: 'POST',
					url 		: '<s:property value="%{saveGroupLimitDetail}" />',
					data 		: formParam, // our data object
				}).done(function(resultJson) 
				{
					var resultSplit=resultJson.split('-');
					if(resultSplit[0]=="duplicate")
					{
						$("#errorMessage").text(resultSplit[1]);	
					}
					else
					{
						var body=document.getElementById("bodyId");
						$("#bodyId").empty();
						var listGroupLimit = JSON.parse(resultJson);
						var rowLine="";
						for (var i=0; i < listGroupLimit.length; i++)
						{
							rowLine +="<tr id=\"row"+i+"\">"
								+"<td>"+listGroupLimit[i].trxCodeDisplay+"</td>"									
								+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerTrx)+"</td>"
								+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerDay)+"</td>"
								+"<td>"+accounting.formatMoney(listGroupLimit[i].feeTrx)+"</td>"										
								+"<td>"
								+"	<input type='button' class='ButtonDelete' onclick=\"deleteBody("+listGroupLimit[i].id+")\" />"
								+"	<input type='button' class='ButtonEdit'"
								+"	onclick=\"editBody("+i+","+listGroupLimit[i].id+", '"+listGroupLimit[i].trxCode+"'"
								+", "+listGroupLimit[i].limitPerDay+", "+listGroupLimit[i].limitPerTrx+""
								+", "+listGroupLimit[i].feeTrx+", '"+listGroupLimit[i].trxCodeDisplay+"')\" />"
								+"</td>"
								+"</tr>";
						}
						body.innerHTML=rowLine; 
						$('#newRow').hide();
						$('#addRowInput').attr('disabled',false);
					}
				});								
			} //end function
			
			
			function deleteBody(detailId)
			{
				if (confirm('Are you sure you want to delete this record?')) 
				{
	 	 			var formParam ="detailId="+detailId+"&headerId="+$('#groupLimitHeaderId').val();	
					$.ajax
					({
						type 		: 'POST',
						url 		: '<s:property value="%{deleteGroupLimitDetail}" />',
						data 		: formParam, // our data object
					}).done(function(resultJson) 
					{
						var body=document.getElementById("bodyId");
						$("#bodyId").empty();
						var listGroupLimit = JSON.parse(resultJson);
						var rowLine="";
						for (var i=0; i < listGroupLimit.length; i++)
						{
							rowLine +="<tr id=\"row"+i+"\">"
								+"<td>"+listGroupLimit[i].trxCodeDisplay+"</td>"									
								+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerTrx)+"</td>"
								+"<td>"+accounting.formatMoney(listGroupLimit[i].limitPerDay)+"</td>"
								+"<td>"+accounting.formatMoney(listGroupLimit[i].feeTrx)+"</td>"										
								+"<td>"
								+"	<input type='button' class='ButtonDelete' onclick=\"deleteBody("+listGroupLimit[i].id+")\" />"
								+"	<input type='button' class='ButtonEdit'"
								+"	onclick=\"editBody("+i+","+listGroupLimit[i].id+", '"+listGroupLimit[i].trxCode+"'"
								+", "+listGroupLimit[i].limitPerTrx+", "+listGroupLimit[i].limitPerDay+""
								+", "+listGroupLimit[i].feeTrx+", '"+listGroupLimit[i].trxCodeDisplay+"')\" />"
								+"</td>"
								+"</tr>";
						}
						body.innerHTML=rowLine;
						columnFlag=0;
					});
				}
			}

		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.setting" /> | <s:text name="t.groupLimit" /></h1>
                <h2><s:text name="t.groupLimit.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="GroupLimit!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="GroupLimit!gotoInput" includeParams="none"/>
            		<s:if test="groupLimitHeader.id == 0">
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
                <s:form id="formInput" >
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
            		<fieldset>
                    	<legend><s:text name="d.groupLimit"/></legend>                    	
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                      	<div class="FieldSetContainer">  
	                       	<s:hidden name="groupLimitHeader.id" id="groupLimitHeaderId"/>
	                       	<s:hidden name="listTrxCodeJson" id="listTrxCodeJson" />
							<label for="groupName"><s:text name="l.groupName" /></label>		
							<s:textfield type="text" id="groupName" name="groupLimitHeader.groupName" cssClass="SingleLine" required="required"/>
						</div>
					</fieldset>
					
					<!-- BUTTON -->
         			<div class="PositionerCenter">
                    	<input type="button" id="btnSave" value="<s:text name='b.save' />" class="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                    
                    <s:if test="groupLimitHeader.id!=0">
						<div id="errorMessage" class="MessageDefault"></div>
						<table>
							<thead>
								<tr>
									<th>Transaction</th>
									<th>Limit Per Trx</th>
									<th>Limit Per Day</th>
									<th>Admin Fee</th>
									<th class="ColumnPanel"><input type="button" class="ButtonAdd" id="addRowInput" value="Add Item"/></th>
								</tr>
							</thead>
							<tbody id="bodyId">
								<s:iterator value="listGroupLimitDetail" status="stat">
									<tr id="row<s:property value='%{#stat.index}'/>">
										<td>
											<s:property value="listGroupLimitDetail[#stat.index].trxCodeDisplay"/>
										</td>
										<td><s:property value="%{getText('u.formatMoney',{listGroupLimitDetail[#stat.index].limitPerDay})}"/></td>
										<td><s:property value="%{getText('u.formatMoney',{listGroupLimitDetail[#stat.index].limitPerTrx})}"/></td>
										<td><s:property value="%{getText('u.formatMoney',{listGroupLimitDetail[#stat.index].feeTrx})}"/></td>
										<td id="cancelColumn<s:property value='%{#stat.index}'/>">
											<input type="button" class="ButtonDelete" value="" 
												onclick="deleteBody('<s:property value="listGroupLimitDetail[#stat.index].id"/>')"/>
											<input type="button" class="ButtonEdit" value="" 
												onclick="editBody(<s:property value='%{#stat.index}'/>, 
												<s:property value='listGroupLimitDetail[#stat.index].id'/>,
												'<s:property value='listGroupLimitDetail[#stat.index].trxCode'/>',
												<s:property value='listGroupLimitDetail[#stat.index].limitPerTrx'/>,
												<s:property value='listGroupLimitDetail[#stat.index].limitPerDay'/>,												
												<s:property value='listGroupLimitDetail[#stat.index].feeTrx'/>, 
												'<s:property value='listGroupLimitDetail[#stat.index].trxCodeDisplay'/>')"/>
										</td>
									</tr>
								</s:iterator>
							</tbody>
							<tfoot id="footId">
								<tr id="newRow">
									<td>
										<s:select name="transactionCode" id="transactionCode" cssClass="DropDown"
											list="listTrxCode" listKey="lookupValue" listValue="lookupDesc" />
									</td>
									<td><s:textfield name="limitPerTrx" id="limitPerTrx" cssClass="SingleLine" /></td>
									<td><s:textfield name="limitPerDay" id="limitPerDay" cssClass="SingleLine" /></td>
									<td><s:textfield name="adminFee" id="adminFee" cssClass="SingleLine" /></td>
									<td>
										<input type='button' class='ButtonCancel' id="btnCancelAdd"/>
										<input type='button' class='ButtonSave' id="btnSaveRow"/>										
									</td>
								</tr>
							</tfoot>
						</table>
					</s:if>
				</s:form>
			</section>
		</main>
	</body>
</html>