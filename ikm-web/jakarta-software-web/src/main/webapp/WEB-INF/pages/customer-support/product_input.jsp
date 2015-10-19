<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.customerSupport" /> | <s:text name="t.product" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="Product!processInput"/>
		<s:url var="saveProductValue" action="Product!saveProductValue"/>
		<s:url var="updateProductValue" action="Product!updateProductValue"/>
		<s:url var="deleteProductValue" action="Product!deleteProductValue"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				var rowCounter=100;
				ButtonRequest("#formInput", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');				
				
				$('#addRowInput').click(function()
				{
					var foot=document.getElementById("footId");
					var rowAdd="<tr id=\"newRow"+rowCounter+"\">"
							+"<td><input type=\"text\" id=\"inputProductValue"+rowCounter+"\" onchange=\"saveNewRow('"+rowCounter+"')\"/></td>"
							+"<td></td>"
							+"<td></td>"
							+"<td></td>"
							+"<td></td>"
							+"<td>"
							+"<input type='button' class='ButtonCancel' onclick=\"cancelNewRow('"+rowCounter+"')\"/>"
							+"</td>"
							+"</tr>";
					foot.innerHTML+=rowAdd;			
					rowCounter++;
				});
				
			});//end document ready
			var columnFlag=0;
			function cancelNewRow(rowId)
			{
				var rowJs="#newRow"+rowId;
				$(rowJs).remove();				
			}
			
			function saveNewRow(rowId)
			{
				var rowJs="#newRow"+rowId;
				var productValue="#inputProductValue"+rowId;
 	 			var formParam ="productId="+$('#productId').val()+"&productValue="+$(productValue).val();
				$.ajax
				({
					type 		: 'POST',
					url 		: '<s:property value="%{saveProductValue}" />',
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
						$(rowJs).remove();
						var body=document.getElementById("bodyId");
						$("#bodyId").empty();
						var listProductValue = JSON.parse(resultJson);
						var rowLine="";
						for (var i=0; i < listProductValue.length; i++)
						{
							rowLine +="<tr>"
								+"<td id=\"bodyColumn"+i+"\" onclick=\"editBodyColumn("+i+", "+listProductValue[i].id+") \">"+listProductValue[i].productValue+"</td>"
								+"<td>"+listProductValue[i].createdOn+"</td>"
								+"<td>"+listProductValue[i].createdByDisplay+"</td>"							
								+"<td>"+listProductValue[i].updatedOn+"</td>"							
								+"<td>"+listProductValue[i].updatedByDisplay+"</td>"
								+"<td id=\"cancelColumn"+i+"\"><input type='button' class='ButtonDelete' value='' onclick='deleteBody("+listProductValue[i].id+")'/>"
								+"</tr>"
						}
						body.innerHTML=rowLine;
						columnFlag=0;
					}
				});			
 			}
			
			function editBodyColumn(columnId, prodValId)
			{
				if(columnFlag==0)
				{
					var columnIdJs="bodyColumn"+columnId;
					var cancelColumnIdJs="cancelColumn"+columnId;
					var column=document.getElementById(columnIdJs);
					var cancelColumn=document.getElementById(cancelColumnIdJs);
					var varTemp=column.innerHTML;
					column.innerHTML="<input type=\"text\" id=\"editProductValue"+columnId+"\" value="+varTemp+" onchange=\"updateNewRow('"+columnId+"', "+prodValId+")\"/>";
					cancelColumn.innerHTML="<input type='button' class='ButtonCancel' value='' onclick='cancelUpdate("+varTemp+","+columnId+", "+prodValId+")''/>";
					columnFlag=1;
				}
			}
			
			function cancelUpdate(prodVal, columnId, prodValId)
			{
				var columnIdJs="bodyColumn"+columnId;				
				var column=document.getElementById(columnIdJs);
				var cancelColumnIdJs="cancelColumn"+columnId;
				var cancelColumn=document.getElementById(cancelColumnIdJs);
				column.innerHTML=prodVal;
				cancelColumn.innerHTML="<input type='button' class='ButtonDelete' value='' onclick='deleteBody("+prodValId+")'/>";
				columnFlag=0;
			}
			
			function updateNewRow(rowId, prodValId)
			{
				$("#errorMessage").removeClass();
				var productValue="#editProductValue"+rowId;
 	 			var formParam ="productId="+$('#productId').val()+"&productValue="+$(productValue).val()+"&prodValId="+prodValId;		
				$.ajax
				({
					type 		: 'POST',
					url 		: '<s:property value="%{updateProductValue}" />',
					data 		: formParam, // our data object
				}).done(function(resultJson) 
				{
					var resultSplit=resultJson.split('-');
					if(resultSplit[0]=="duplicate")
					{
						$("#errorMessage").addClass("ResultFailure");
						$("#errorMessage").empty();
						$("#errorMessage").append(resultSplit[1]);
					}
					else
					{
						var body=document.getElementById("bodyId");
						$("#bodyId").empty();
						var listProductValue = JSON.parse(resultJson);
						var rowLine="";
						for (var i=0; i < listProductValue.length; i++)
						{
							rowLine +="<tr>"
								+"<td id=\"bodyColumn"+i+"\" onclick=\"editBodyColumn("+i+", "+listProductValue[i].id+") \">"+listProductValue[i].productValue+"</td>"
								+"<td>"+listProductValue[i].createdOn+"</td>"
								+"<td>"+listProductValue[i].createdByDisplay+"</td>"							
								+"<td>"+listProductValue[i].updatedOn+"</td>"							
								+"<td>"+listProductValue[i].updatedByDisplay+"</td>"
								+"<td id=\"cancelColumn"+i+"\"><input type='button' class='ButtonDelete' value='' onclick='deleteBody("+listProductValue[i].id+")'/></td>"
								+"</tr>"
						}
						body.innerHTML=rowLine;
						columnFlag=0;
					}
				});
			}
			
			function deleteBody(prodValId)
			{
 	 			var formParam ="prodValId="+prodValId+"&productId="+$("#productId").val();	
				$.ajax
				({
					type 		: 'POST',
					url 		: '<s:property value="%{deleteProductValue}" />',
					data 		: formParam, // our data object
				}).done(function(resultJson) 
				{
					var body=document.getElementById("bodyId");
					$("#bodyId").empty();
					var listProductValue = JSON.parse(resultJson);
					var rowLine="";
					for (var i=0; i < listProductValue.length; i++)
					{
						rowLine +="<tr>"
							+"<td id=\"bodyColumn"+i+"\" onclick=\"editBodyColumn("+i+", "+listProductValue[i].id+") \">"+listProductValue[i].productValue+"</td>"
							+"<td>"+listProductValue[i].createdOn+"</td>"
							+"<td>"+listProductValue[i].createdByDisplay+"</td>"							
							+"<td>"+listProductValue[i].updatedOn+"</td>"							
							+"<td>"+listProductValue[i].updatedByDisplay+"</td>"
							+"<td id=\"cancelColumn"+i+"\"><input type='button' class='ButtonDelete' value='' onclick='deleteBody("+listProductValue[i].id+")'/></td>"
							+"</tr>"
					}
					body.innerHTML=rowLine;
					columnFlag=0;
				});
			}

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
            	
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="Product!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="Product!gotoInput" includeParams="none"/>
            		<s:if test="product.id == 0">
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
                    	<legend><s:text name="d.productData"/></legend>                    	
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <s:actionerror /><s:actionmessage />
                      	<div class="FieldSetContainer">  
	                       	<s:hidden name="product.id" id="productId"/>
	                           <s:if test="product.id==0">
								<label for="productCode"><s:text name="l.productCode" /></label>		
								<s:textfield type="text" id="productCode" name="product.productCode" maxlength="15" cssClass="SingleLine" required="required"/>
							</s:if>
							<s:else>
								<label for="productCode"><s:text name="l.productCode" /></label>
								<s:textfield type="text" id="productCode" name="product.productCode" cssClass="SingleLine" readonly="true"/>
							</s:else>
							
							<br>
							<label for="productName"><s:text name="l.productName" /></label>
							<s:textfield id="productName" name="product.productName" maxlength="25" required="required" cssClass="SingleLine"/>
							
							<br>
							<label for="institutionCode"><s:text name="l.institutionCode" /></label>
							<s:textfield id="institutionCode" name="product.institutionCode" maxlength="3" required="required" cssClass="SingleLine"/>
														
							<br>
							<label for="productType"><s:text name="l.productType" /></label>
							<s:select id="productType" name="product.productType" list="listProductType" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
														
							<br>
							<label for="status"><s:text name="l.status" /></label>
							<s:select id="status" name="product.status" list="listStatus" listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown"/>
						</div>
					</fieldset>
					
					<!-- BUTTON -->
         			<div class="PositionerCenter">
                    	<input type="button" id="btnSave" type="submit" value="<s:text name='b.save' />" class="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
                    
                    <s:if test="product.id!=0">
						<div id="errorMessage" class="MessageDefault MessageState"></div>
						<table>
							<thead>
								<tr>
									<th>Product Value</th>
									<th>Created On</th>
									<th>Created By</th>
									<th>Updated On</th>
									<th>Updated By</th>										
									<th><a href="javascript:void(0)" id="addRowInput"><input type="button" class="ButtonAdd" value="Add Item"/></a></th>
								</tr>
							</thead>
							<tbody id="bodyId">
								<s:iterator value="listProductValue" status="stat">
									<tr>
										<td id="bodyColumn<s:property value='%{#stat.index}'/>" 
										onclick="editBodyColumn(<s:property value='%{#stat.index}'/>, <s:property value='listProductValue[#stat.index].id'/>)">
											<s:property value="listProductValue[#stat.index].productValue"/>
										</td>
										<td><s:property value="listProductValue[#stat.index].createdOn"/></td>
										<td><s:property value="listProductValue[#stat.index].createdByDisplay"/></td>
										<td><s:property value="listProductValue[#stat.index].updatedOn"/></td>
										<td><s:property value="listProductValue[#stat.index].updatedByDisplay"/></td>
										<td id="cancelColumn<s:property value='%{#stat.index}'/>">
										<input type="button" class="ButtonDelete" value="" onclick="deleteBody('<s:property value="listProductValue[#stat.index].id"/>')"/>
										</td>
									</tr>
								</s:iterator>
							</tbody>
							<tfoot id="footId">
							</tfoot>
						</table>
					</s:if>
				</s:form>
			</section>
		</main>
	</body>
</html>