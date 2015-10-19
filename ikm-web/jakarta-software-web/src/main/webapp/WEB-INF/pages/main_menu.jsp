<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
		<!-- META DATA -->
		<meta name="decorator" content="home-standard">
		<title><s:text name="t.home"/></title>
	</head>

	<body>
	<!-- MAIN -->
        <main>
        	<!-- PAGE TITLE -->
        	<hgroup>
            	<h1><s:text name="t.home"/></h1>
                <h2><s:text name="t.home.description"/></h2>
            </hgroup>
            
            <!-- PROFILE -->
            <aside class="ContainerInlineBlock">
            	<!-- PROFILE HEADER -->
            	<div class="PaperHeaderGroup">
	            	<div class="PaperHeader PaperSelected">
	                	<div class="ShapeFolderHornBorder"></div>
	                    <div class="ShapeFolderHornBody"></div>
	                    <h2><s:text name="f.profile"/></h2>
	                </div>
                </div>
                
                <!-- PROFILE MAIN -->
                <s:form>
                	<div class="BubbleContainer">
                        <span id="ContainerGreeting"></span>, 
                        <br>
                        <mark><s:property value="%{#session.LOGIN_KEY.userCode}"/></mark>
                    </div>
                    <div class="ShapeTailBorder"></div>
                    <div class="ShapeTailBody"></div>
                    
                    <p>
                        <s:text name="w.as"/> <mark><s:property value="%{#session.LOGIN_KEY.userLevelDisplay}"/></mark>
                        <br>
                        <s:text name="w.lastLogin"/> : 
                        <mark>
	                        <s:if test="%{#session.LOGIN_KEY.neverLogin}">
			            		<s:text name="w.NA" />
			            	</s:if>
			            	<s:else>
			            		<s:date name="%{#session.LOGIN_KEY.lastLoginOn}" format="dd.MM.yyyy HH:mm:ss"/>
			            	</s:else>
            			</mark>
                        
                        <br>
                        <s:text name="w.prefixNotifications" />
                        <s:url id="authUserData" action="AuthUserData" includeParams="none" />
                        	<s:a href="%{authUserData}" >
                        		<mark><s:property value="notifUser" /></mark> <s:text name="t.authUserData"/>
                        	</s:a> 
                        <s:text name="w.suffixNotifications"/>
                        
                        <br>
                        <s:text name="w.prefixNotifications" /> 
                        <s:url id="authMerchant" action="AuthMerchant" includeParams="none" />
                        	<s:a href="%{authMerchant}" >
                        		<mark><s:property value="notifMerchant" /></mark> <s:text name="t.authMerchant"/> 
                        	</s:a> 
                        <s:text name="w.suffixNotifications"/>
                        
                        <br>
                        <s:text name="w.prefixNotifications" />
                        <s:url id="authCif" action="AuthCif" includeParams="none" />
                        	<s:a href="%{authCif}" >
                        		<mark><s:property value="notifCif" /></mark> <s:text name="t.authCif"/> 
                        	</s:a>
                        <s:text name="w.suffixNotifications"/>
                    </p>
                    
                    <!-- BUTTON -->
                    <img src="./Resource/Icon/icon_fullscreen_inactive.svg" id="ButtonFullScreen" alt="PT Emobile Indonesia - MMBS, Button Full Screen"/>
                    <s:url id="ButtonLogout" action="Logout" includeParams="none"/>
					<s:a href="%{ButtonLogout}" cssClass="ClearHyperlink">
	               		<input type="button" id="ButtonLogout" class="ButtonPrimary" value="LOG OUT"/>
	               	</s:a>
                </s:form>
            </aside>
            
            <!-- CONTENT -->
            <section class="ContainerInlineBlock">
            	<!-- PAPER HEADER -->
            	<div class="PaperHeaderGroup">
                    <div class="PaperHeader PaperSelected">
                        <div class="ShapeFolderHornBorder"></div>
                        <div class="ShapeFolderHornBody"></div>
                        <h2><s:text name="f.FAQ"/></h2>
                    </div>
                    <div class="PaperHeader">
                        <div class="ShapeFolderHornBorder"></div>
                        <div class="ShapeFolderHornBody"></div>
                        <h2><s:text name="f.guide"/></h2>
                    </div>
                    <div class="PaperHeader">
                        <div class="ShapeFolderHornBorder"></div>
                        <div class="ShapeFolderHornBody"></div>
                        <h2><s:text name="f.support"/></h2>
                    </div>
                </div>
                
                <!-- PAPER CONTENT -->
                <form>
                	<div class="FAQItem">
                		<div class="BubbleContainer">Q.</div>
                        <dl>
                        	<dt>How to authorize new user ?</dt>
                            <dd>To avoid misuse of newly registered user, we need to ...</dd>
                        </dl>
                    </div>
                    <div class="FAQItem">
                    	<div class="BubbleContainer">Q.</div>
                    	<dl>
                        	<dt>How to authorize new user ?</dt>
                            <dd>To avoid misuse of newly registered user, we need to ...</dd>
                        </dl>
                    </div>
                    <div class="FAQItem">
                    	<div class="BubbleContainer">Q.</div>
                    	<dl>
                        	<dt>How to authorize new user ?</dt>
                            <dd>To avoid misuse of newly registered user, we need to ...</dd>
                        </dl>
                    </div>
                </form>
            </section>
        </main>
	</body>
</html>