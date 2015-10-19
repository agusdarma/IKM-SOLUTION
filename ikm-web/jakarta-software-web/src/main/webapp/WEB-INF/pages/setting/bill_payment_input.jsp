<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="general-standard">
		<title><s:text name="t.setting" /> | <s:text name="t.billPayment" /></title>
		
		<!-- JAVA SCRIPT -->
		<s:url var="remoteurl" action="BillPayment!processInput"/>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				ButtonRequest("#form1", "#btnSave", "#ProgressRequest", "#MessageResult", '<s:property value="%{remoteurl}"/>');
				
			/* 	for(var i=1; i < 4; i++)
				{
					var billRef="#billRef"+i;
					var billRefGroup="#billRefGroup"+i;
					if($(billRef).val().length > 0)
					{
						$(billRefGroup).show();	
					}
					else
					{
						$(billRefGroup).hide();
					}
				}
		
				$('#billRef1').change(function() {
					if($('#billRef1').val().length > 0)
					{
						$('#billRefGroup1').show();
					}					
					else
					{
						$('#billRefGroup1').hide();
					}
				});//end billkey1.change
				
				$('#billRef2').change(function() {
					if($('#billRef2').val().length > 0)
					{
						$('#billRefGroup2').show();
					}					
					else
					{
						$('#billRefGroup2').hide();
					}
				});//end billkey2.change
				
				$('#billRef3').change(function() {
					if($('#billRef3').val().length > 0)
					{
						$('#billRefGroup3').show();
					}					
					else
					{
						$('#billRefGroup3').hide();
					}
				});//end billkey3.change */
				
			});
		</script>
	</head>

	<body>
		<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup class="ContainerInlineBlock">
            	<h1><s:text name="t.setting" /> | <s:text name="t.billPayment" /></h1>
                <h2><s:text name="t.billPayment.description" /></h2>
            </hgroup>
            
            <!-- SHORT PROFILE -->
            <%@ include file="/WEB-INF/includes/include_aside_shortprofile.jsp"%>
            
            <!-- CONTENT -->
            <section class="ContainerBlock">
            	
            	<!-- CONTENT HEADER -->
            	<div class="PaperHeaderGroup">
            		<s:url id="search" action="BillPayment!gotoSearch" includeParams="none"/>
            		<s:url id="input" action="BillPayment!gotoInput" includeParams="none"/>
            		<s:if test="billPayment.id==0">
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
                
                <!-- CONTENT MAIN -->
                <s:form id="form1" action="BillPayment!processInput" method="post">
                	
                	<!-- MESSAGE RESULT -->
            		<%@ include file="/WEB-INF/includes/include_message_result.jsp"%>
            		
                	<fieldset>
                    	<legend><s:text name="d.billPayment"/></legend>
                        <div class="ShapeTailBorder"></div>
                        <div class="ShapeTailBody"></div>
                        <div class="FieldSetContainer">
                        	<s:hidden name="billPayment.id" />
							<s:if test="billPayment.id > 0">
								<label for="billerNo"><s:text name="l.billerCode" /></label>
								<s:textfield type="text" id="billerNo" name="billPayment.billerNo" maxlength="12"
									readonly="true" cssClass="SingleLine" required="true" />		
							</s:if>
							<s:else>
								<label for="billerNo"><s:text name="l.billerCode" /></label>
								<s:textfield type="text" id="billerNo" name="billPayment.billerNo" 
									maxlength="12" cssClass="SingleLine" required="true" onkeypress="return spaceNotAllowed(event)"/>								
							</s:else>
							
							<br>
							<label for="billerName">
								<s:text name="l.billerName" />
							</label>
							<s:textfield type="text" id="billerName" name="billPayment.billerName" maxlength="30" cssClass="SingleLine" required="true"/>

							<br>
							<label for="billerDesc" class="LabelContent">
								<s:text name="l.billerDesc" />
							</label>
							<s:textfield name="billPayment.billerDesc" id="billerDesc" cssClass="SingleLine" required="true" />
					
							<br>
							<label for="category" class="LabelContent">
								<s:text name="l.category" />
							</label>
		                    <s:select id="category" name="billPayment.category" list="listBillPaymentCategory" 
		                    	listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown" />
		                    	<!-- headerKey="nocat" headerValue="no category" -->
										
							<br>
							<label for="status">
								<s:text name="l.status" />
							</label>
							<s:select id="status" name="billPayment.status" list="listStatus" 
								listValue="lookupDesc" listKey="lookupValue" cssClass="DropDown"/>
												
							<br>
							<label for="isotype" class="LabelContent"><s:text name="l.isotype" /></label>
		                    <s:select id="isotype" name="billPayment.isoType" list="listIsoType" 
		                    	listKey="lookupValue" listValue="lookupDesc" cssClass="DropDown" />
				
							<br>
							<label for="billRef1" class="LabelContent"><s:text name="l.bankRef" /></label>
							<s:textfield name="billPayment.bankRef1" id="bankRef1"  cssClass="SingleLine" required="required"/>
											
							<br>
							<label for="billRef1" class="LabelContent"><s:text name="l.billRef" /></label>
							<s:textfield name="billPayment.billRef1" id="billRef1"  cssClass="SingleLine" required="required"/>
							
							<!-- <div id="billRefGroup1" class="Subfieldset"> -->
							<br>
							<label for="billType1" class="LabelContent"><s:text name="l.type" /></label>
							<s:select name="billPayment.billType1" id="billType1" cssClass="DropDown" list="listBillType" listKey="lookupValue" listValue="lookupDesc" />
							
							<br>
							<label for="billMinLength1" class="LabelContent"><s:text name="l.minLength" /></label>
							<s:textfield name="billPayment.billMinLength1" id="billMinLength1" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
							
							<br>
							<label for="billMaxLength1" class="LabelContent"><s:text name="l.maxLength" /></label>
							<s:textfield name="billPayment.billMaxLength1" id="billMaxLength1" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
							<!-- </div> -->
							
							<%-- <br>
							<label for="billRef2" class="LabelContent">
								<s:text name="l.billRef" /> 2
							</label>
							<s:textfield name="billPayment.billRef2" id="billRef2" cssClass="SingleLine" />
							
							<div id="billRefGroup2" class="Subfieldset">
								<br>
								<label for="billType2" class="LabelContent">
									<s:text name="l.type" />
								</label>
								<s:select name="billPayment.billType2" id="billType2" cssClass="DropDown" list="listBillType" 
		                    	listKey="lookupValue" listValue="lookupDesc" />
								
								<br>
								<label for="billMinLength2" class="LabelContent">
									<s:text name="l.minLength2" /> 2
								</label>
								<s:textfield name="billPayment.billMinLength2" id="billMinLength2" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
								
								<br>
								<label for="billMaxLength2" class="LabelContent">
									<s:text name="l.maxLength2" /> 2
								</label>
								<s:textfield name="billPayment.billMaxLength2" id="billMaxLength2" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
							</div>
							
							<br>
							<label for="billRef3" class="LabelContent">
								<s:text name="l.billRef" /> 3
							</label>
							<s:textfield name="billPayment.billRef3" id="billRef3" cssClass="SingleLine" />
							
							<div id="billRefGroup3" class="Subfieldset">
								<br>
								<label for="billType3" class="LabelContent">
									<s:text name="l.type" />
								</label>
								<s:select name="billPayment.billType3" id="billType3" cssClass="DropDown" list="listBillType" 
		                    	listKey="lookupValue" listValue="lookupDesc" />
								
								<br>
								<label for="minLength2" class="LabelContent">
									<s:text name="l.minLength3" /> 3
								</label>
								<s:textfield name="billPayment.billMinLength3" id="minLength2" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
								
								<br>
								<label for="billMaxLength3" class="LabelContent">
									<s:text name="l.maxLength3" /> 3
								</label>
								<s:textfield name="billPayment.billMaxLength3" id="billMaxLength3" cssClass="SingleLine" maxlength="2"  onKeyPress="return isNumberKey(event)"/>
							</div> --%>							
						</div>
					</fieldset>		
					
         			<div class="PositionerCenter">
                    	<s:submit id="btnSave" type="submit" value="%{getText('b.save')}" cssClass="ButtonPrimary"/>
                    	<s:reset type="reset" id="ButtonReset" cssClass="ButtonReset" value=""/>
                    </div>
				</s:form>
            </section>
        </main>
	</body>
</html>