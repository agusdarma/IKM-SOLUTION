<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="j"%>

<html>
<head>
	<meta name="decorator" content="general">
	<link href="<s:url value='/styles/jmesa.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<s:url value='/styles/jmesa-pdf.css'/>" rel="stylesheet" type="text/css"/>
	<script language="javascript" src='<s:url value="/scripts/jmesa.min.js"/>' type="text/javascript"></script>
	<script language="javascript" src='<s:url value="/scripts/jquery.jmesa.min.js"/>'  type="text/javascript"></script>
</head>

<body>
                    <span class="ContentHeaderBold">SYSTEM SUPPORT</span><br>
                    <span class="ContentHeader"><s:property value="titleMenu" /></span><br>
                    <span class="ContentHeaderSubTitle">Manage Ussd Menu</span>
                </div>
            </div>

            <div id="ContentContent">
            	<div class="ContentData">
					<s:actionerror /><s:actionmessage />
					<div align="right">
						<s:url id="detail" action="UssdMenuForMbank!detail" includeParams="none" />
						<s:a href="%{detail}" cssClass="ClearHyperlink">
							<input type="button" value='<s:text name="label.ussdMenu.createNew" />' class="MediumRedButton" />
						</s:a>
					</div>
		
					<j:struts2TableModel items="${listRootMenu}" id="tag" var="bean">
						<j:htmlTable captionKey="label.manageUssdMenu.listMenu" width="100%">
							<j:htmlRow filterable="false" sortable="false">
								<j:htmlColumn property="note" titleKey="label.ussdMenu.note" >
									<s:url id="detail" action="UssdMenuForMbank!detail" includeParams="none">
									<s:param name="ussdMenuId">${pageScope.bean.id}</s:param>
									</s:url>
									<s:a href="%{detail}">${pageScope.bean.note}</s:a>
								</j:htmlColumn>
								<j:htmlColumn property="operatorDisplay" titleKey="label.ussdMenu.operator" />
								<j:htmlColumn property="commandDisplay" titleKey="label.ussdMenu.command" />
								<j:htmlColumn property="data" titleKey="label.ussdMenu.data" />
								<j:htmlColumn property="trxCode" titleKey="label.ussdMenu.trxCode" />
								<j:htmlColumn property="trxTypeDisplay" titleKey="label.ussdMenu.trxType" />
								<j:htmlColumn property="createdOn" titleKey="label.createdOn"
				                	pattern="dd-MM-yyyy HH:mm:ss" cellEditor="org.jmesa.view.editor.DateCellEditor"/>            
				                <j:htmlColumn property="createdByDisplay" titleKey="label.createdBy" />
				                <j:htmlColumn property="updatedOn" titleKey="label.updatedOn"
				                	pattern="dd-MM-yyyy HH:mm:ss" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
				                <j:htmlColumn property="updatedByDisplay" titleKey="label.updatedBy" />
							</j:htmlRow>
						</j:htmlTable>
					</j:struts2TableModel>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
