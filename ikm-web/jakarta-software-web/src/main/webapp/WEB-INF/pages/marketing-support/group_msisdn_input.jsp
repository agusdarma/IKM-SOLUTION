<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.marketingSupport" /> | <s:text name="t.groupMsisdn" /></title>

		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="GroupMsisdn!processInput" />
		<script type="text/javascript">
			$(document).ready(
			function() {
				ButtonRequest("#form1", "#btnSave", "#ProgressRequest",
						"#MessageResult",
						'<s:property value="%{remoteurl}"/>');
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
		<main> <!-- PAGE TITLE -->
			<hgroup class="ContainerInlineBlock">
				<h1>
					<s:text name="t.marketingSupport" />|<s:text name="t.groupMsisdn" />
				</h1>
				<h2>
					<s:text name="t.groupMsisdn.description" />
				</h2>
			</hgroup>
	
			<!-- SHORT PROFILE --> 
			<%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
	
			<!-- CONTENT -->
			<section class="ContainerBlock">
				<!-- PAPER HEADER -->
				<div class="PaperHeaderGroup">
					<s:url id="search" action="GroupMsisdn!gotoSearch" includeParams="none" />
					<s:url id="input" action="GroupMsisdn!gotoInput" includeParams="none" />
					<s:if test="groupMsisdnHeader.id == 0">
						<s:a href="%{search}" cssClass="ClearHyperlink">
							<div class="PaperHeader">
								<div class="ShapeFolderHornBorder"></div>
								<div class="ShapeFolderHornBody"></div>
								<h2>
									<s:text name="f.search" />
								</h2>
							</div>
						</s:a>
						<div class="PaperHeader PaperSelected">
							<div class="ShapeFolderHornBorder"></div>
							<div class="ShapeFolderHornBody"></div>
							<h2>
								<s:text name="f.insert" />
							</h2>
						</div>
					</s:if>
					<s:else>
						<s:a href="%{search}" cssClass="ClearHyperlink">
							<div class="PaperHeader">
								<div class="ShapeFolderHornBorder"></div>
								<div class="ShapeFolderHornBody"></div>
								<h2>
									<s:text name="f.search" />
								</h2>
							</div>
						</s:a>
						<s:a href="%{input}" cssClass="ClearHyperlink">
							<div class="PaperHeader">
								<div class="ShapeFolderHornBorder"></div>
								<div class="ShapeFolderHornBody"></div>
								<h2>
									<s:text name="f.insert" />
								</h2>
							</div>
						</s:a>
						<div class="PaperHeader PaperSelected">
							<div class="ShapeFolderHornBorder"></div>
							<div class="ShapeFolderHornBody"></div>
							<h2>
								<s:text name="f.update" />
							</h2>
						</div>
					</s:else>
				</div>
		
				<!-- PAPER CONTENT -->
				<s:form id="form1" method="post">
					<!-- MESSAGE RESULT -->
					<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
		
					<!-- FORM -->
					<fieldset>
						<legend><s:text name="d.groupMsisdn" /></legend>
						<div class="ShapeTailBorder"></div>
						<div class="ShapeTailBody"></div>
						<s:actionerror /><s:actionmessage />
						<div class="FieldSetContainer">
							<s:hidden name="groupMsisdnHeader.id" id="headerId" />
							<label for="groupName"><s:text name="l.name" /></label>
							<s:textfield type="text" id="groupName" name="groupMsisdnHeader.groupName" cssClass="SingleLine" required="true"/>
		
							<br> 
							<label for="groupRemarks"><s:text name="l.remarks" /></label>
							<s:textarea name="groupMsisdnHeader.groupRemarks" id="groupRemarks" cssClass="MultiLine" />
		
							<br> 
							<label for="msisdn"><s:text name="l.phoneNo" /></label>
							<s:textarea name="msisdn" id="msisdn" cssClass="MultiLine" required="true"/>
						</div>
					</fieldset>
		
					<!-- BUTTON -->
					<div class="PositionerCenter">
						<s:submit id="btnSave" type="submit" value="%{getText('b.save')}" cssClass="ButtonPrimary" />
						<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value="" />
					</div>
				</s:form>
			</section>
		</main>
	</body>
</html>