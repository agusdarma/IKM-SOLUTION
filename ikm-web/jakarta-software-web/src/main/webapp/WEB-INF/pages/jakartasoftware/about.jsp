<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
<%-- HEADER --%>
<%@ include file="/WEB-INF/includes/include_header_jack_soft.jsp"%>
<title><s:text name="t.about"/></title>		
</head>
<body>
  <%@ include file="/WEB-INF/includes/include_menu_top_jack_soft.jsp"%>
   <div class="banner">
      	<div class="wrap">
      	    <h2>About Us</h2><div class="clear"></div>
      	</div>
    </div>
  <div class="main">	
	 <div class="project-wrapper">
	 	<div class="wrap">
	 	  <div class="section group">
				<div class="lsidebar span_1_of_about">
				   <img src="<s:url value='/Resource/images/about-img.jpg'/>" alt=""/>
			    </div>
				<div class="cont span_2_of_about">
					<h2>Welcome to our Team</h2>
				    <h3>JackSoft is a leading provider of business application
						solutions and software-related services in Indonesia. </h3>
					<div class="section group example">
						<div class="col_1_of_2 span_1_of_2">
						   <p>We focus on
						developing innovations and providing customer-oriented solutions
						to help companies of all sizes and industries achieve their
						business goals. Established in 2015, JackSoft was founded on the
						passion to combine management and IT</p>
		 				</div>
						<div class="col_1_of_2 span_1_of_2">
						   <p>JackSoft is founded by young men who meet each other in Software House Company IT division where they handle important project. From there
						   they see needs Indonesia IT industry. JackSoft concentrating in web application ( JAVA ) and mobile application ( Android and IOS )</p>
						</div>
						<div class="clear"></div>	
		    		</div>				    
				</div>
				<div class="clear"></div>				
		    </div>
		    <div class="about-middle">
		   	 <h4><span>Our Skills</span></h4>
		   	 <p></p>
		   </div>
		   <div class="section group example">
			   <div class="col_1_of_2 span_1_of_2">
			   	  <div class="skills">
                     <ul>
                     	<li>
                     		<div class="percentage">96%</div>
                     		<div class="percent-text">Java</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">95%</div>
                     		<div class="percent-text1">Sql Server</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">90%</div>
                     		<div class="percent-text2">Html/css</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">94%</div>
                     		<div class="percent-text3">Android</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<div class="clear"></div>	
                     </ul>
                  </div>
               </div>
			   <div class="col_1_of_2 span_1_of_2">
				  <div class="skills">
                     <ul>
                     	<li>
                     		<div class="percentage">90%</div>
                     		<div class="percent-text2">IOS</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">93%</div>
                     		<div class="percent-text3">Oracle</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">92%</div>
                     		<div class="percent-text3">Spring framework</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<li>
                     		<div class="percentage">94%</div>
                     		<div class="percent-text3">Mybatis framework</div>
                     		<div class="clear"></div>	
                     	</li>
                     	<div class="clear"></div>	
                     </ul>
                  </div>
			   </div>
			   <div class="clear"></div>	
		  </div>
		  <%-- 
		   <div class="about-middle">
		   	 <h4><span>Our Team</span></h4>
		   	 <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
		   </div>
		   <div class="team1">
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img2.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">	
					<img src="<s:url value='/Resource/images/about-img3.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img4.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img5.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="clear"></div>
			</div>
			<div class="team1">
				<div class="col_1_of_about-grids span_1_of_about-grids">
					<a class="popup-with-zoom-anim" href="#small-dialog2">
						<img src="<s:url value='/Resource/images/about-img6.jpg'/>" alt=""/>
						<h3>Web Designer</h3>
					</a>
						<!-- start magnific -->
								 <div id="small-dialog2" class="mfp-hide">
									<div class="pop_up">
									 	<h2>about designer</h2>
									 	<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
									 	<div class="social-icons">
									 			
					 	<h2 style="margin-top: 4%;">get in touch</h2>
						    <ul>	
							  <li class="facebook"><a href="#"><span> </span></a></li>
					          <li class="google"><a href="#"><span> </span></a></li>
					          <li class="twitter"><a href="#"><span> </span></a></li>
					          <li class="linkedin"><a href="#"><span> </span></a></li>	 
					          <li class="youtube"><a href="#"><span> </span></a></li>	
					          <li class="bloger"><a href="#"><span> </span></a></li>
					          <li class="rss"><a href="#"><span> </span></a></li>	
					          <li class="dribble"><a href="#"><span> </span></a></li>		        	
					        </ul>
				     </div>
						  			</div>
								</div>
					<!-- end  magnific -->

				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img7.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img8.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="col_1_of_about-grids span_1_of_about-grids">
				<a class="popup-with-zoom-anim" href="#small-dialog2">
					<img src="<s:url value='/Resource/images/about-img9.jpg'/>" alt=""/>
					<h3>Web Designer</h3>
				</a>
				</div>
				<div class="clear"></div>
			</div>
			--%>
		 </div>
     </div>
  </div>	
  <%@ include file="/WEB-INF/includes/include_menu_bottom_jack_soft.jsp"%>
</body>
</html>