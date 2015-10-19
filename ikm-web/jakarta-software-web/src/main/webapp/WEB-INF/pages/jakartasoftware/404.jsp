<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
<!-- FAVICON -->
<link rel="shortcut icon"
	href="<s:url value='/Resource/Icon/favicon.ico'/>" />
<link rel="shortcut icon"
	href="<s:url value='/Resource/Icon/favicon.ico'/>"
	type="image/x-icon">
<link rel="icon"
	href="<s:url value='/Resource/Icon/favicon.ico'/>"
	type="image/x-icon">
<title><s:text name="t.home"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href='http://fonts.googleapis.com/css?family=Karla' rel='stylesheet' type='text/css'>
<link href="<s:url value='/Style/jakartasoftware/style.css'/>" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
  <div class="header">	
      <div class="wrap"> 
	         <div class="logo">
				<a href="MainMenu.web"><img src="<s:url value='/Resource/images/logo.png'/>" alt=""/></a>
			 </div>
			 <div class="cssmenu">
				<ul>
					<li class="active"><a href="MainMenu.web">Home</a></li>
					<li><a href="Portfolio.web">Portfolio</a></li> 
					<li><a href="About.web">About Us</a></li> 
					<li><a href="Contact.web">Contact</a></li>
				</ul>
		     </div>
		    <div class="clear"></div>
	   </div>
   </div>
   <div class="banner">
      	<div class="wrap">
      	    <h2>404 Error</h2><div class="clear"></div>
      	</div>
    </div>
  <div class="main">	
     <div class="page-not-found">
		<h3>404</h3>
		<h4><span><img src="<s:url value='/Resource/images/404.png'/>" alt=""/></span></h4>
		<a href="MainMenu.web" class="home">Back to homepage</a> 
		<!--  <div class="or">or</div>
		<a href="blog.html" class="blogpage">Our Blog</a>
		-->
	    <div class="clear"></div>
	</div>
  </div>	
  <div class="footer">
	<div class="footer-top">
	   <div class="wrap">
		   <div class="section group">
				<div class="col_1_of_3 span_1_of_3">
					<h3>About Us</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
					<button class="btn1 btn-8 btn-8b">Learn more</button>
					<h4>Photo Stream</h4>
					<div class="gallery">
						<ul>
							<li><img src="<s:url value='/Resource/images/g1.jpg'/>" alt=""/></li>
							<li><img src="<s:url value='/Resource/images/g2.jpg'/>" alt=""/></li>
							<li><img src="<s:url value='/Resource/images/g3.jpg'/>" alt=""/></li>
							<li><img src="<s:url value='/Resource/images/g4.jpg'/>" alt=""/></li>
							<li><img src="<s:url value='/Resource/images/g5.jpg'/>" alt=""/></li>
							<li><img src="<s:url value='/Resource/images/g6.jpg'/>" alt=""/></li>
						</ul>
					</div>
				</div>
				<div class="col_1_of_3 span_1_of_3">
					<h3>Latest tweets</h3>
					<div class="footer-list">
						<ul>
							<li><img src="<s:url value='/Resource/images/tw.png'/>" alt=""/><p>Lorem ipsum <span class="yellow"><a href="#">consectetuer</a></span> adipiscing elit, seddia<br><span class="small">&nbsp;web design</span></p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/tw.png'/>" alt=""/><p>Lorem ipsum <span class="yellow"><a href="#">consectetuer</a></span> adipiscing elit, seddia<br><span class="small">&nbsp;web design</span></p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/tw.png'/>" alt=""/><p>Lorem ipsum dolor sit amet, <span class="yellow"><a href="#">consectetuer</a></span> adipiscing elit, sed diam nonummy nibh<br><span class="small">&nbsp;web design</span></p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/tw.png'/>" alt=""/><p>Lorem ipsum dolor sit amet, <span class="yellow"><a href="#">consectetuer</a></span> adipiscing elit, sed diam nonummy nibh<br><span class="small">&nbsp;web design</span></p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/tw.png'/>" alt=""/><p>Lorem ipsum <span class="yellow"><a href="#">consectetuer</a></span> adipiscing elit, seddia<br><span class="small">&nbsp;web design</span></p><div class="clear"></div></li>
						</ul>
					</div>
					 <div class="social-icons">	
					 	<h4>Social Connecting</h4>
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
				<div class="col_1_of_3 span_1_of_3">
					<h3>Contact info</h3>
					<div class="footer-list">
						<ul>
							<li><img src="<s:url value='/Resource/images/address.png'/>" alt=""/><p>Lorem ipsum dolor sit amet,consectetuer adipiscing elit, sed diam nonummy nibh<br>&nbsp;web design</p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/phone.png'/>" alt=""/><p>Phone: +1 800 258 2689<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;+1 800 258 2689 </p><div class="clear"></div></li>
							<li><img src="<s:url value='/Resource/images/msg.png'/>" alt=""/><p>Email: <span class="yellow"><a href="#">info(at)skokov.com</a></span></p><div class="clear"></div></li>
					   </ul>
					</div>
					<div class="follow">
					   <h4>Follow Us</h4>
					   <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod</p>
					   <div class="search">
						  <form>
							   <input type="text" value="">
							   <input type="submit" value="">
						  </form>
					   </div>
				    </div>
				</div>
				<div class="clear"></div>
			</div>
	  </div>
	 </div>
	 <div class="footer-bottom">
	 	<div class="wrap">
		 	<div class="copy">
			     <p class="copy">© 2015 Jakarta Software Design by <a href="http://w3layouts.com" target="_blank">w3layouts</a></p>
		    </div>
		 	<div class="footer-nav">
		 		<ul>
					<li><a href="MainMenu.web">Home</a></li>
					<li><a href="Portfolio.web">Portfolio</a></li> 					
					<li><a href="About.web">About Us</a></li> 
					<li><a href="Contact.web">Contact</a></li>
				</ul>
		 	</div>
		 	<div class="clear"></div>
	    </div>
	</div>
</div>
</body>
</html>