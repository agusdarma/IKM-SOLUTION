<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
<%-- HEADER --%>
<%@ include file="/WEB-INF/includes/include_header_jack_soft.jsp"%>
<title><s:text name="t.contact"/></title>
</head>
<body>
  <%@ include file="/WEB-INF/includes/include_menu_top_jack_soft.jsp"%>
   <div class="banner">
      	<div class="wrap">
      	    <h2>Contact</h2><div class="clear"></div>
      	</div>
    </div>
  <div class="main">	
	 <div class="project-wrapper">
	 	<div class="map">		   
		   <iframe width="100%" height="400" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3966.9031710938107!2d106.67810444262064!3d-6.143706996368176!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0000000000000000%3A0x3810ecfe7ef15c03!2sDuta+Garden+Square!5e0!3m2!1sen!2s!4v1444921804871" width="400" height="300" frameborder="0" style="border:0" allowfullscreen></iframe>
	    </div> 
	    <div class="wrap">
	 	  <div class="contact">
	 	  		<div class="cont span_2_of_contact">
	 	  		<h5 class="leave">Send Us A Message</h5><div class="clear"></div>		 	  						  
				  <s:form action="Contact!process" method="post">
					<div class="contact-to">
						<s:textfield type="text" id="Name" name="contactUsData.name" cssClass="text" placeholder="%{getText('l.name')}" required="true"/>
                     	<!--  <input type="text" class="text" value="Name..." onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Name...';}">
                     		  <input type="text" class="text" value="Email..." onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email...';}" style="margin-left: 10px">
                     		  <input type="text" class="text" value="Subject..." onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Subject...';}" style="margin-left: 10px">
                     		  <textarea value="Message:" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Message';}">Message..</textarea>
                     	-->
                     	<s:textfield type="text" id="Email" name="contactUsData.email" cssClass="text" placeholder="%{getText('l.email')}" required="true" style="margin-left: 10px"/>
                     	<s:textfield type="text" id="Subject" name="contactUsData.subject" cssClass="text" placeholder="%{getText('l.subject')}" required="true" style="margin-left: 10px"/>					 						 	
					</div>
					<div class="text2">	                   
	                   <s:textarea name="contactUsData.message" id="message" cssClass="MultiLine" required="true"/>
	                </div>	                
                    <s:if test="hasActionErrors()">
					   <div class="errors">
					      <s:actionerror/>
					   </div>
					</s:if>
					<s:if test="hasActionMessages()">
					   <div class="MessageDefault MessageState">
					      <s:actionmessage/>
					   </div>
					</s:if>
					
                    
	                <div>
	               		<!--<a href="#" class="submit">Send a Message</a>-->
	               		<s:submit type="submit" id="ButtonSend" cssClass="submit" value="%{getText('b.sendMessage')}"/>
	                </div>
	             </s:form>
				</div>
				<div class="lsidebar span_1_of_about">
				   <h5 class="leave">Contact Info</h5><div class="clear"></div>
				   <div class="contact-list">
						<ul>
							<li><img src="<s:url value='/Resource/images/address.png'/>" alt=""><p>Duta Square Blok C-35 Juru Mudi <br>&nbsp;Tangerang 15124</p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/phone.png'/>" alt=""><p>Phone / WA: +62 856 9393 8630<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/msg.png'/>" alt=""><p>Email: <span class="yellow1"><a href="#">sales(at)jakartasoftware.com</a></span></p><div class="clear"></div></li>
					   </ul>
					</div>
			    </div>
				<div class="clear"></div>				
		    </div>
		</div>
     </div>
  </div>	
  <%@ include file="/WEB-INF/includes/include_menu_bottom_jack_soft.jsp"%>
</body>
</html>