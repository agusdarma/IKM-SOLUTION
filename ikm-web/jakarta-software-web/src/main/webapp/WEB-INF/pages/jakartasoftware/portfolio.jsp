<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>
<%-- HEADER --%>
<%@ include file="/WEB-INF/includes/include_header_jack_soft.jsp"%>
<title><s:text name="t.portfolio"/></title>		
</head>
<body>
  <%@ include file="/WEB-INF/includes/include_menu_top_jack_soft.jsp"%>
    <div class="banner">
      	<div class="wrap">
      	    <h2>Portfolio</h2> <div class="clear"></div>
      	</div>
    </div>
	<div class="main">	
	   <div class="portfolio-top">
		   	<div class="wrap">
		        <h3>We build with love and passion</h3>
				<h5>This is JackSoft Portfolio</h5>
			</div>
		</div>
		<div class="container">
		   <ul id="filters" class="clearfix">
				 <li><span class="filter active" data-filter="app card icon logo web">All</span></li>
				 <li><span class="filter" data-filter="app">Website</span></li>
				 <li><span class="filter" data-filter="card">Mobile</span></li>
				 <%-- <li><span class="filter" data-filter="icon">UI Kit</span></li>
				 <li><span class="filter" data-filter="logo">Photo</span></li>
				 <li><span class="filter" data-filter="web">App Design</span></li>
				 --%>
		   </ul>
		   <div class="clear"></div>
		   <div id="portfoliolist" style="     " class="">
			<div class="wrapper">
			<%-- <div class="portfolio logo1 mix_all" data-cat="logo" style=" ">
				<div class="portfolio-wrapper">				
					<a href="#">
						<img src="<s:url value='/Resource/images/pic12.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">Lorem ipsum dolor sit amet</a></h4><p class="img"><img src="<s:url value='/Resource/images/likes.png'/>"  title="likes" alt=""/>12</p><div class="clear"></div>
						   <ul>
				  			  <li><a href="#"><span>December 14, 2013</span></a></li>
				  		   </ul>
		  			</div>
				</div>
			</div>
			--%>				
			<div class="portfolio app mix_all" data-cat="app" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/hkm.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">www.hkm-machinery.com perusahaan yang bergerak di bidang molding plastik.</a></h4><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>December 14, 2013</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>
			<div class="portfolio app mix_all" data-cat="app" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/homepage_sablonbalon.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">www.sablonbalon.com perusahaan yang bergerak di bidang balon.</a></h4><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>December 14, 2012</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>		
			<%-- <div class="portfolio web mix_all" data-cat="web" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">						
					<a href="#">
						<img src="<s:url value='/Resource/images/pic14.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">Lorem ipsum dolor sit amet</a></h4><p class="img"><img src="<s:url value='/Resource/images/likes.png'/>"  title="likes" alt=""/>12</p><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>December 14, 2013</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>
			--%>				
			<div class="portfolio card mix_all" data-cat="card" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/p2_online.jpg'/>" alt="Parking Online">
					</a>
					<div class="links">
						<h4><a href="">Parking Online Solution, solusi mudah cari parkir jaman sekarang</a></h4><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>15 September 2015</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>	
			<div class="portfolio app mix_all" data-cat="app" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">
					<a href="#">
						<img src="<s:url value='/Resource/images/moon_home.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">www.moonbabyshop.com Baby Shop Online di daerah Metland Puri.</a></h4><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>14 September, 2014</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>					
			<div class="portfolio card mix_all" data-cat="card" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/myagenda.jpg'/>" alt="My Agenda JackSoft">
					</a>
					<div class="links">
						<h4><a href="">My Agenda, hari gini masih butuh kertas untuk mencatat, pakai agenda online aja.</a></h4><div class="clear"></div>
							<ul>
				  				<li><a href="#"><span>01 Oktober 2015</span></a></li>
				  			</ul>
		  			</div>
				</div>
			</div>	
			<div class="portfolio card mix_all" data-cat="card" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/highway.jpg'/>" alt="Highway Traffic">
					</a>
					<div class="links">
						<h4><a href="">Takut kejebak macet di jalan tol, cek dengan aplikasi ini dulu dunk..</a></h4><div class="clear"></div>
							<ul>
				  				<li><span><a href="#">20 Desember 2014</a></span></li>
				  			</ul>
		  			</div>
				</div>
			</div>	
			<%-- <div class="portfolio logo1 mix_all" data-cat="logo" style=" ">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/pic19.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">Lorem ipsum dolor sit amet</a></h4><p class="img"><img src="<s:url value='/Resource/images/likes.png'/>"  title="likes" alt=""/>12</p><div class="clear"></div>
							<ul>
				  				<li><span><a href="#">December 14, 2013</a></span></li>
				  			</ul>
		  			</div>
				</div>
			</div>
																																										
			<div class="portfolio app mix_all" data-cat="app" style="  display: inline-block; opacity: 1;">
				<div class="portfolio-wrapper">			
					<a href="#">
						<img src="<s:url value='/Resource/images/pic20.jpg'/>" alt="Image 2">
					</a>
					<div class="links">
						<h4><a href="">Lorem ipsum dolor sit amet</a></h4><p class="img"><img src="<s:url value='/Resource/images/likes.png'/>"  title="likes" alt=""/>12</p><div class="clear"></div>
							<ul>
				  				<li><span><a href="#">December 14, 2013</a></span></li>
				  			</ul>
		  			</div>
				</div>
			</div>
			--%>														
			<div class="clear"> </div>
		</div>			
	  </div>
	 </div>
	</div>
  <%@ include file="/WEB-INF/includes/include_menu_bottom_jack_soft.jsp"%>
</body>
</html>