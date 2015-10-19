<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="j"%>

<html>
<head>

<meta name="decorator" content="general">
<title><s:text name="pages.ussdMenu.title" /></title>
<link href="<s:url value='/styles/jmesa.css'/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value='/styles/jmesa-pdf.css'/>" rel="stylesheet" type="text/css"/>
<script language="javascript" src='<s:url value="/scripts/jmesa.min.js"/>' type="text/javascript"></script>
<script language="javascript" src='<s:url value="/scripts/jquery.jmesa.min.js"/>'  type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnCancel').click(function() {
			window.location.href = "<s:url action="UssdMenuForMbank" />";
		});
		$('#btnAddNew').click(function() {
			window.location.href = "<s:url action="UssdMenuForMbank!detail" />?parentMenuId=<s:property value="parentMenuId" />";
		});
		$('#btnAddNewChild').click(function() {
			window.location.href = "<s:url action="UssdMenuForMbank!detail" />?parentMenuId=<s:property value="ussdMenuId" />";
		});
		$('#btnDelete').click(function() {
			if (confirm('Are You sure want to delete?')) {
				window.location.href = "<s:url action="UssdMenuForMbank!delete" />?ussdMenuId=<s:property value="ussdMenuId" />";
			}
		});
	
	/* 	$('#trxType').change(function(){
			if($('#trxType').val() == 0){
		 		$('#biller').hide();
		 	} else {
		 		$('#biller').show();
		 	}
		}); 
			
		var trxType = $('#trxType').val();
		if(trxType == 0) {
		  	$('#biller').hide();
		} else {
			$('#biller').show();
		} */
	});
</script>

</head>

<body>
                    <span class="ContentHeaderBold">SYSTEM SUPPORT</span><br>
                    <span class="ContentHeader"><s:property value="titleMenu" /></span><br>
                    <span class="ContentHeaderSubTitle">Manage Ussd Menu</span>
                </div>
            </div>

            <div id="ContentContent">
            	<div class="ContentData">
					<s:actionerror /><s:actionmessage/>
					
					<s:iterator value="listParentMenu" status="stat">
						<s:url id="detailUrl" action="UssdMenuForMbank!detail">
							<s:param name="ussdMenuId"><s:property value="id" /></s:param>
						</s:url>
						<span class="LabelContent"><label for="ussdMenuNote"><s:text name="label.ussdMenu.level"/> <s:property value="#stat.index" /></label></span>
						<s:a href="%{detailUrl}"><span class="LabelHeader"><s:property value="note"/></span></s:a>
					</s:iterator>
					
					<s:form action="UssdMenuForMbank!process">
						<s:hidden name="ussdMenuId" />
						<s:hidden name="parentMenuId" />
						
						<span class="LabelContent"><label for="ussdMenuNote"><s:text name="label.ussdMenu.note"/></label></span>
						<s:textfield id="ussdMenuNote" name="ussdMenu.note" cssClass="SingleLine" required="required"/>
						
						<br>
						<span class="LabelContent"><label for="ussdMenuCommandTypeNormal"><s:text name="label.ussdMenu.command"/></label></span>
						<s:select name="commandTypeSpecial" list="listCommandType" cssClass="DropDown" />
						<s:textfield type="number" id="ussdMenuCommandTypeNormal" name="commandTypeNormal" cssClass="Number" min="0"/>
						
						<br>
						<span class="LabelContent"><label for="ussdMenuData"><s:text name="label.ussdMenu.data"/></label></span>
						<s:textfield id="ussdMenuData" name="ussdMenu.data" cssClass="SingleLine"/>
						
						<br>
						<span class="LabelContent"><label for="ussdMenuTrxCode"><s:text name="label.ussdMenu.trxCode"/></label></span>
						<s:textfield id="ussdMenuTrxCode" name="ussdMenu.trxCode" cssClass="SingleLine"/>
						
						<br>
						<%-- <span class="LabelContent"><label for="trxType"><s:text name="label.ussdMenu.trxType"/></label></span>
						<s:select name="ussdMenu.trxType" list="listTrxType" cssClass="DropDown" id="trxType" /> --%>
						
						<br>
						<span class="LabelContent"><label for="ussdMenuOperator"><s:text name="label.ussdMenu.operator"/></label></span>
						<s:select id="ussdMenuOperator" name="ussdMenu.operator" list="listOperator" cssClass="DropDown" required="required"/>
						
						<br>
						<span class="LabelContent"><label for="ussdMenuShowOrder"><s:text name="label.ussdMenu.showOrder"/></label></span>
						<s:textfield type="number" id="ussdMenuShowOrder" name="ussdMenu.showOrder" cssClass="Number" min="0"/>
						
						<br>
						<%-- <span class="LabelContent"><label for="ussdMenuBillerNo"><s:text name="label.ussdMenu.biller"/></label></span>
						<s:select id="ussdMenuBillerNo" name="ussdMenu.billerNo" headerKey="0" headerValue="Semua Biller"
							listKey="billerNumber" listValue="displayBiller" list="listBiller" cssClass="DropDown" />
						
						<br>
						<span class="LabelContent"><label for="ussdMenuTrxDesc"><s:text name="label.ussdMenu.trxDesc"/></label></span>
						<s:textfield id="ussdMenuTrxDesc" name="ussdMenu.trxDesc" cssClass="SingleLine"/> --%>
						
						<br>
						<span class="LabelNote"><s:text name="label.required.legends" /></span>
						
						<br>
		      			<s:if test="ussdMenu.id == 0">
							<s:submit type="button" value="%{getText('button.save')}" cssClass="MediumRedButton" />
						</s:if>
						<s:else>
							<%-- <input type="button" value='<s:text name="label.ussdMenu.createNew" />' id="btnAddNew" class="MediumRedButton" /> --%>
							<s:if test="canCreateNewChild">
								<input type="button" value='<s:text name="label.ussdMenu.addNewChild" />' id="btnAddNewChild" class="MediumRedButton" />
							</s:if>
							<input type="button" value='<s:text name="label.ussdMenu.delete" />' id="btnDelete" class="MediumRedButton" />
							<s:submit type="button" value="%{getText('button.save')}" cssClass="MediumRedButton" />
						</s:else>
		    			<input type="button" value="<s:text name="button.back" />" id="btnCancel" class="MediumMaroonButton" />
					</s:form>
					
					<s:if test="listChildMenu.size() > 0">
						<br />
						<div class="jmesa">
							<table  border="0"  cellpadding="0"  cellspacing="0"  class="table"  width="100%">
								<caption><s:text name="label.ussdMenu.listChildMenu" /></caption>
								<thead>
									<tr class="header" >
										<td><s:text name="label.ussdMenu.note" /></td>
										<td><s:text name="label.ussdMenu.command" /></td>
										<td><s:text name="label.ussdMenu.data" /></td>
										<td><s:text name="label.ussdMenu.trxCode" /></td>
										<td><s:text name="label.ussdMenu.operator" /></td>
										<td><s:text name="label.ussdMenu.trxType" /></td>
										<td><s:text name="label.createdOn" /></td>
										<td><s:text name="label.createdBy" /></td>
										<td><s:text name="label.updatedOn" /></td>
										<td><s:text name="label.updatedBy" /></td>					
									</tr>
								</thead>
								<tbody class="tbody" >
									<s:iterator value="listChildMenu" status="rowstatus">
									<s:if test="#rowstatus.odd">
										<tr class="odd">
									</s:if>
									<s:else>
										<tr class="even">
									</s:else>
										<s:url id="detailUrl" action="UssdMenuForMbank!detail">
											<s:param name="ussdMenuId"><s:property value="id" /></s:param>
										</s:url>
										<td><s:a href="%{detailUrl}"><s:property value="note" /></s:a></td>
										<td><s:property value="commandDisplay" /></td>
										<td><s:property value="data" /></td>
										<td><s:property value="trxCode" /></td>
										<td><s:property value="operatorDisplay" /></td>
										<td><s:property value="trxTypeDisplay" /></td>
										<td><s:date name="createdOn" format="dd-MM-yyyy HH:mm:ss"/></td>
										<td><s:property value="createdByDisplay" /></td>
										<td><s:date name="updatedOn" format="dd-MM-yyyy HH:mm:ss"/></td>
										<td><s:property value="updatedByDisplay" /></td>
									</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</s:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
