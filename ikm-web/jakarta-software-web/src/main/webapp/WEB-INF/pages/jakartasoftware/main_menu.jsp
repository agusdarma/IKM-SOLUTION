<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>	
<title><s:text name="t.home"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Modern Responsive web template" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
 <!-- Bootstrap Core CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/bootstrap.min.css'/>" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/style.css'/>" rel='stylesheet' type='text/css' />
<!-- Graph CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/lines.css'/>" rel='stylesheet' type='text/css' />
<link href="<s:url value='/Style/jakartasoftware/admin/font-awesome.css'/>" rel="stylesheet"> 
<!-- jQuery -->
<script src="<s:url value='/Java Script/jakartasoftware/admin/jquery.min.js'/>"></script>
<!----webfonts--->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>
<!---//webfonts--->    
<!-- Nav CSS -->
<link href="<s:url value='/Style/jakartasoftware/admin/custom.css'/>" rel="stylesheet">
<!-- Metis Menu Plugin JavaScript -->
<script src="<s:url value='/Java Script/jakartasoftware/admin/metisMenu.min.js'/>"></script>
<script src="<s:url value='/Java Script/jakartasoftware/admin/custom.js'/>"></script>
<!-- Graph JavaScript -->
<script src="<s:url value='/Java Script/jakartasoftware/admin/d3.v3.js'/>"></script>
<script src="<s:url value='/Java Script/jakartasoftware/admin/rickshaw.js'/>"></script>
	<style type="text/css">        
        #report { border-collapse:collapse;}
        #report h4 { margin:0px; padding:0px;}
        #report img { float:right;}
        #report ul { margin:10px 0 10px 40px; padding:0px;}
        #report th { background:#7CB8E2 url("<s:url value='/Resource/images/header_bkg.png'/>") repeat-x scroll center left; color:#fff; padding:7px 15px; text-align:left;width: 100%;}
        #report td { background:#C7DDEE none repeat-x scroll center left; color:#000; padding:7px 15px; }
        #report tr.odd td { background:#fff url("<s:url value='/Resource/images/row_bkg.png'/>") repeat-x scroll center left; cursor:pointer; }
        #report div.arrow { background:transparent url("<s:url value='/Resource/images/arrows.png'/>") no-repeat scroll 0px -16px; width:16px; height:16px; display:block;}
        #report div.up { background-position:0px 0px;}
    </style>    
    <script type="text/javascript">  
        $(document).ready(function(){
            $("#report tr:odd").addClass("odd");
            $("#report tr:not(.odd)").hide();
            $("#report tr:first-child").show();
            
            $("#report tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            //$("#report").jExpand();
        });
    </script> 
    <s:url id="refreshUrl" action="MainMenu!refresh"/>
    <script type="text/javascript">
    function onOptionSelect(value) {
    	var agendavalue;
    	agendavalue = value;    	
    	
    	window.location.href='<s:property value="%{refreshUrl}"/>?agendaType='+agendavalue;
    	
    	
    }
    </script> 
</head>
<body>
<div id="wrapper">
     <!-- Navigation -->
        <nav class="top1 navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="MainMenu.web"><s:text name="w.hello"></s:text><s:property value="loginData.nama"/></a>
            </div>
            <!-- /.navbar-header -->
            <ul class="nav navbar-nav navbar-right">
				<%-- 
				<li class="dropdown">
	        		<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-comments-o"></i><span class="badge">4</span></a>
	        		<ul class="dropdown-menu">
						<li class="dropdown-menu-header">
							<strong>Messages</strong>
							<div class="progress thin">
							  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
							    <span class="sr-only">40% Complete (success)</span>
							  </div>
							</div>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/1.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
								<span class="label label-info">NEW</span>
							</a>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/2.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
								<span class="label label-info">NEW</span>
							</a>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/3.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
							</a>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/4.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
							</a>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/5.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
							</a>
						</li>
						<li class="avatar">
							<a href="#">
								<img src="<s:url value='/Resource/images/pic1.png'/>" alt=""/>
								<div>New message</div>
								<small>1 minute ago</small>
							</a>
						</li>
						<li class="dropdown-menu-footer text-center">
							<a href="#">View all messages</a>
						</li>	
	        		</ul>
	      		</li>
	      		--%>
			    <li class="dropdown">
	        		<a href="#" class="dropdown-toggle avatar" data-toggle="dropdown"><img src="<s:url value='/Resource/images/myagenda.png'/>"><span class="badge"><%--<s:property value="respListAgendaVO.jumlahMessageUnread"/>--%></span></a>
	        		<ul class="dropdown-menu">
						<li class="dropdown-menu-header text-center">
							<strong><s:text name="t.menu.myagenda"></s:text></strong>
						</li>
						<s:if test="loginData.userType == 1">
							<%@ include file="/WEB-INF/includes/include_menu_drop_down_teacher.jsp"%>						
						</s:if>
						<s:if test="loginData.userType == 2">
							<%@ include file="/WEB-INF/includes/include_menu_drop_down_parent.jsp"%>						
						</s:if>												
						<%--
						<li class="dropdown-menu-header text-center">
							<strong>Settings</strong>
						</li>
						<li class="m_2"><a href="#"><i class="fa fa-user"></i> Profile</a></li>
						<li class="m_2"><a href="#"><i class="fa fa-wrench"></i> Settings</a></li>
						<li class="m_2"><a href="#"><i class="fa fa-usd"></i> Payments <span class="label label-default">42</span></a></li>
						<li class="m_2"><a href="#"><i class="fa fa-file"></i> Projects <span class="label label-primary">42</span></a></li>
						<li class="divider"></li>
						<li class="m_2"><a href="#"><i class="fa fa-shield"></i> Lock Profile</a></li>
						<li class="m_2"><a href="#"><i class="fa fa-lock"></i> Logout</a></li>
						--%>	
	        		</ul>
	      		</li>
			</ul>			
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <s:if test="loginData.userType == 1">
							<%@ include file="/WEB-INF/includes/include_menu_left_teacher.jsp"%>						
						</s:if>
						<s:if test="loginData.userType == 2">
							<%@ include file="/WEB-INF/includes/include_menu_left_parent.jsp"%>						
						</s:if>
                        <%--
                        <li>
                            <a href="#"><i class="fa fa-laptop nav_icon"></i>Layouts<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="grids.html">Grid System</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-indent nav_icon"></i>Menu Levels<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="graphs.html">Graphs</a>
                                </li>
                                <li>
                                    <a href="typography.html">Typography</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-envelope nav_icon"></i>Mailbox<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="inbox.html">Inbox</a>
                                </li>
                                <li>
                                    <a href="compose.html">Compose email</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="widgets.html"><i class="fa fa-flask nav_icon"></i>Widgets</a>
                        </li>
                         <li>
                            <a href="#"><i class="fa fa-check-square-o nav_icon"></i>Forms<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="forms.html">Basic Forms</a>
                                </li>
                                <li>
                                    <a href="validation.html">Validation</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-table nav_icon"></i>Tables<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="basic_tables.html">Basic Tables</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap fa-fw nav_icon"></i>Css<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="media.html">Media</a>
                                </li>
                                <li>
                                    <a href="login.html">Login</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        --%>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        <div id="page-wrapper">
        <div class="graphs">
		<s:text name="t.label.agendaType"></s:text>
        <!--<s:radio label="Agenda Type" name="reqListAgendaData.agendaType" onclick="onOptionSelect(this.value)" list="listAgendaType" listKey="lookupValue" listValue="lookupDesc" value="<s:property value='agendaType'/>" />
        -->
        <s:select label="Agenda Type" name="reqListAgendaData.agendaType" onchange="onOptionSelect(this.value)" list="listAgendaType" listKey="lookupValue" listValue="lookupDesc" />
        </br>
		</br>
		<table id="report">
        <tr>                  
            <th><s:text name="t.label.tanggalAgenda"></s:text></th>
            <th></th>
        </tr>
        <s:iterator value="respListAgendaVO.listAgendaHeaderVO" var="listHeader">		    
		    <tr>
	            <td><s:property value="#listHeader.tanggalAgendaVal"/></td>	            
	            <td><div class="arrow"></div></td>
	        </tr>
	        
		        <tr>
		            <td colspan="5">		                
		                <h4><s:text name="t.label.agendaDetail"></s:text></h4>
		                <ul>
		                	<s:iterator value="#listHeader.agendaDetail" var="listDetail">
		                    <li><s:property value="#listDetail.subject"/> : <s:property value="#listDetail.isiAgenda"/></li>
		                    </s:iterator>			                    
		                 </ul>   
		            </td>
		        </tr>
	        		
	        
		</s:iterator>             
    </table>
    <div class="clearfix"> </div>
	    </div>
     	<%-- <div class="col_3">
        	<div class="col-md-3 widget widget1">
        		<div class="r3_counter_box">
                    <i class="pull-left fa fa-thumbs-up icon-rounded"></i>
                    <div class="stats">
                      <h5><strong>45%</strong></h5>
                      <span>New Orders</span>
                    </div>
                </div>
        	</div>
        	<div class="col-md-3 widget widget1">
        		<div class="r3_counter_box">
                    <i class="pull-left fa fa-users user1 icon-rounded"></i>
                    <div class="stats">
                      <h5><strong>1019</strong></h5>
                      <span>New Visitors</span>
                    </div>
                </div>
        	</div>
        	<div class="col-md-3 widget widget1">
        		<div class="r3_counter_box">
                    <i class="pull-left fa fa-comment user2 icon-rounded"></i>
                    <div class="stats">
                      <h5><strong>1012</strong></h5>
                      <span>New Users</span>
                    </div>
                </div>
        	</div>
        	<div class="col-md-3 widget">
        		<div class="r3_counter_box">
                    <i class="pull-left fa fa-dollar dollar1 icon-rounded"></i>
                    <div class="stats">
                      <h5><strong>$450</strong></h5>
                      <span>Profit Today</span>
                    </div>
                </div>
        	 </div>
        	<div class="clearfix"> </div>
      </div>
      <div class="col_1">
		    <div class="col-md-4 span_7">	
		      <div class="cal1 cal_2"><div class="clndr"><div class="clndr-controls"><div class="clndr-control-button"><p class="clndr-previous-button">previous</p></div><div class="month">July 2015</div><div class="clndr-control-button rightalign"><p class="clndr-next-button">next</p></div></div><table class="clndr-table" border="0" cellspacing="0" cellpadding="0"><thead><tr class="header-days"><td class="header-day">S</td><td class="header-day">M</td><td class="header-day">T</td><td class="header-day">W</td><td class="header-day">T</td><td class="header-day">F</td><td class="header-day">S</td></tr></thead><tbody><tr><td class="day adjacent-month last-month calendar-day-2015-06-28"><div class="day-contents">28</div></td><td class="day adjacent-month last-month calendar-day-2015-06-29"><div class="day-contents">29</div></td><td class="day adjacent-month last-month calendar-day-2015-06-30"><div class="day-contents">30</div></td><td class="day calendar-day-2015-07-01"><div class="day-contents">1</div></td><td class="day calendar-day-2015-07-02"><div class="day-contents">2</div></td><td class="day calendar-day-2015-07-03"><div class="day-contents">3</div></td><td class="day calendar-day-2015-07-04"><div class="day-contents">4</div></td></tr><tr><td class="day calendar-day-2015-07-05"><div class="day-contents">5</div></td><td class="day calendar-day-2015-07-06"><div class="day-contents">6</div></td><td class="day calendar-day-2015-07-07"><div class="day-contents">7</div></td><td class="day calendar-day-2015-07-08"><div class="day-contents">8</div></td><td class="day calendar-day-2015-07-09"><div class="day-contents">9</div></td><td class="day calendar-day-2015-07-10"><div class="day-contents">10</div></td><td class="day calendar-day-2015-07-11"><div class="day-contents">11</div></td></tr><tr><td class="day calendar-day-2015-07-12"><div class="day-contents">12</div></td><td class="day calendar-day-2015-07-13"><div class="day-contents">13</div></td><td class="day calendar-day-2015-07-14"><div class="day-contents">14</div></td><td class="day calendar-day-2015-07-15"><div class="day-contents">15</div></td><td class="day calendar-day-2015-07-16"><div class="day-contents">16</div></td><td class="day calendar-day-2015-07-17"><div class="day-contents">17</div></td><td class="day calendar-day-2015-07-18"><div class="day-contents">18</div></td></tr><tr><td class="day calendar-day-2015-07-19"><div class="day-contents">19</div></td><td class="day calendar-day-2015-07-20"><div class="day-contents">20</div></td><td class="day calendar-day-2015-07-21"><div class="day-contents">21</div></td><td class="day calendar-day-2015-07-22"><div class="day-contents">22</div></td><td class="day calendar-day-2015-07-23"><div class="day-contents">23</div></td><td class="day calendar-day-2015-07-24"><div class="day-contents">24</div></td><td class="day calendar-day-2015-07-25"><div class="day-contents">25</div></td></tr><tr><td class="day calendar-day-2015-07-26"><div class="day-contents">26</div></td><td class="day calendar-day-2015-07-27"><div class="day-contents">27</div></td><td class="day calendar-day-2015-07-28"><div class="day-contents">28</div></td><td class="day calendar-day-2015-07-29"><div class="day-contents">29</div></td><td class="day calendar-day-2015-07-30"><div class="day-contents">30</div></td><td class="day calendar-day-2015-07-31"><div class="day-contents">31</div></td><td class="day adjacent-month next-month calendar-day-2015-08-01"><div class="day-contents">1</div></td></tr></tbody></table></div></div>
		    </div>
		    <div class="col-md-4 span_8">
		       <div class="activity_box">
		        <div class="scrollbar" id="style-2">
                   <div class="activity-row">
	                 <div class="col-xs-1"><i class="fa fa-thumbs-up text-info icon_13"> </i>  </div>
	                 <div class="col-xs-3 activity-img"><img src='images/5.png' class="img-responsive" alt=""/></div>
	                 <div class="col-xs-8 activity-desc">
	                 	<h5><a href="#">Lorem Ipsum</a> liked <a href="#">random</a></h5>
	                    <p>Lorem Ipsum is simply dummy</p>
	                    <h6>8:03</h6>
	                 </div>
	                 <div class="clearfix"> </div>
                    </div>
	  			    <div class="activity-row">
	                 <div class="col-xs-1"><i class="fa fa-comment text-info"></i> </div>
	                 <div class="col-xs-3 activity-img"><img src='images/3.png' class="img-responsive" alt=""/></div>
	                 <div class="col-xs-8 activity-desc">
	                 	<h5><a href="#">simply random</a> liked <a href="#">passages</a></h5>
	                    <p>Lorem Ipsum is simply dummy</p>
	                    <h6>8:03</h6>
	                 </div>
	                 <div class="clearfix"> </div>
                    </div>
                    <div class="activity-row">
	                 <div class="col-xs-1"><i class="fa fa-check text-info icon_11"></i></div>
	                 <div class="col-xs-3 activity-img"><img src='images/1.png' class="img-responsive" alt=""/></div>
	                 <div class="col-xs-8 activity-desc">
	                 	<h5><a href="#">standard chunk</a> liked <a href="#">model</a></h5>
	                    <p>Lorem Ipsum is simply dummy</p>
	                    <h6>8:03</h6>
	                 </div>
	                 <div class="clearfix"> </div>
                    </div>
                    <div class="activity-row1">
	                 <div class="col-xs-1"><i class="fa fa-user text-info icon_12"></i></div>
	                 <div class="col-xs-3 activity-img"><img src='images/4.png' class="img-responsive" alt=""/></div>
	                 <div class="col-xs-8 activity-desc">
	                 	<h5><a href="#">perspiciatis</a> liked <a href="#">donating</a></h5>
	                    <p>Lorem Ipsum is simply dummy</p>
	                    <h6>8:03</h6>
	                 </div>
	                 <div class="clearfix"> </div>
                     </div>
	  		        </div>
		          </div>
		    </div>
			<div class="col-md-4 stats-info">
                <div class="panel-heading">
                    <h4 class="panel-title">Browser Stats</h4>
                </div>
                <div class="panel-body">
                    <ul class="list-unstyled">
                        <li>Google Chrome<div class="text-success pull-right">12%<i class="fa fa-level-up"></i></div></li>
                        <li>Firefox<div class="text-success pull-right">15%<i class="fa fa-level-up"></i></div></li>
                        <li>Internet Explorer<div class="text-success pull-right">18%<i class="fa fa-level-up"></i></div></li>
                        <li>Safari<div class="text-danger pull-right">17%<i class="fa fa-level-down"></i></div></li>
                        <li>Opera<div class="text-danger pull-right">10%<i class="fa fa-level-down"></i></div></li>
                        <li>Mobile &amp; tablet<div class="text-success pull-right">14%<i class="fa fa-level-up"></i></div></li>
                        <li class="last">Others<div class="text-success pull-right">5%<i class="fa fa-level-up"></i></div></li> 
                    </ul>
                </div>
            </div>
            <div class="clearfix"> </div>
	  </div>
	  --%> 
	  <%--
	  <div class="span_11">
		<div class="col-md-6 col_4">
		  <div class="map_container"><div id="vmap" style="width: 100%; height: 400px;"></div></div>
		 
			<link rel="stylesheet" href="<s:url value='/Style/jakartasoftware/admin/clndr.css'/>" type="text/css" />
			<script src="<s:url value='/Java Script/jakartasoftware/admin/underscore-min.js'/>" type="text/javascript"></script>
			<script src= "<s:url value='/Java Script/jakartasoftware/admin/moment-2.2.1.js'/>" type="text/javascript"></script>
			<script src="<s:url value='/Java Script/jakartasoftware/admin/clndr.js'/>" type="text/javascript"></script>
			<script src="<s:url value='/Java Script/jakartasoftware/admin/site.js'/>" type="text/javascript"></script>
		
		</div>
		<div class="col-md-6 col_5">
		  <div id="chart_container">
		   <div id="chart"></div>
	       <div id="slider"></div>
		
<script>

var seriesData = [ [], [], [], [], [] ];
var random = new Rickshaw.Fixtures.RandomData(50);

for (var i = 0; i < 75; i++) {
	random.addData(seriesData);
}

var graph = new Rickshaw.Graph( {
	element: document.getElementById("chart"),
	renderer: 'multi',
	
	dotSize: 5,
	series: [
		{
			name: 'temperature',
			data: seriesData.shift(),
			color: '#AFE9FF',
			renderer: 'stack'
		}, {
			name: 'heat index',
			data: seriesData.shift(),
			color: '#FFCAC0',
			renderer: 'stack'
		}, {
			name: 'dewpoint',
			data: seriesData.shift(),
			color: '#555',
			renderer: 'scatterplot'
		}, {
			name: 'pop',
			data: seriesData.shift().map(function(d) { return { x: d.x, y: d.y / 4 } }),
			color: '#555',
			renderer: 'bar'
		}, {
			name: 'humidity',
			data: seriesData.shift().map(function(d) { return { x: d.x, y: d.y * 1.5 } }),
			renderer: 'line',
			color: '#ef553a'
		}
	]
} );


graph.render();

var detail = new Rickshaw.Graph.HoverDetail({
	graph: graph
});
</script>
</div>
	      
<link href="<s:url value='/Style/jakartasoftware/admin/jqvmap.css'/>" rel='stylesheet' type='text/css' />
<script src="<s:url value='/Java Script/jakartasoftware/admin/jquery.vmap.js'/>"></script>
<script src="<s:url value='/Java Script/jakartasoftware/admin/jquery.vmap.sampledata.js'/>" type="text/javascript"></script>
<script src="<s:url value='/Java Script/jakartasoftware/admin/jquery.vmap.world.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('#vmap').vectorMap({
		    map: 'world_en',
		    backgroundColor: '#333333',
		    color: '#ffffff',
		    hoverOpacity: 0.7,
		    selectedColor: '#666666',
		    enableZoom: true,
		    showTooltip: true,
		    values: sample_data,
		    scaleColors: ['#C8EEFF', '#006491'],
		    normalizeFunction: 'polynomial'
		});
	});
</script>
       </div>
       <div class="clearfix"> </div>
    </div>
	--%>
	<%--
    <div class="content_bottom">
     <div class="col-md-8 span_3">
		  <div class="bs-example1" data-example-id="contextual-table">
		    <table class="table">
		      <thead>
		        <tr>
		          <th>#</th>
		          <th>Column heading</th>
		          <th>Column heading</th>
		          <th>Column heading</th>
		        </tr>
		      </thead>
		      <tbody>
		        <tr class="active">
		          <th scope="row">1</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr>
		          <th scope="row">2</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr class="success">
		          <th scope="row">3</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr>
		          <th scope="row">4</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr class="info">
		          <th scope="row">5</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr>
		          <th scope="row">6</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr class="warning">
		          <th scope="row">7</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr>
		          <th scope="row">8</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		        <tr class="danger">
		          <th scope="row">9</th>
		          <td>Column content</td>
		          <td>Column content</td>
		          <td>Column content</td>
		        </tr>
		      </tbody>
		    </table>
		   </div>
	   </div>
	   <div class="col-md-4 span_4">
		 <div class="col_2">
		  <div class="box_1">
		   <div class="col-md-6 col_1_of_2 span_1_of_2">
             <a class="tiles_info">
			    <div class="tiles-head red1">
			        <div class="text-center">Orders</div>
			    </div>
			    <div class="tiles-body red">10</div>
			 </a>
		   </div>
		   <div class="col-md-6 col_1_of_2 span_1_of_2">
              <a class="tiles_info tiles_blue">
			    <div class="tiles-head tiles_blue1">
			        <div class="text-center">Sales</div>
			    </div>
			    <div class="tiles-body blue1">30</div>
			  </a>
		   </div>
		   <div class="clearfix"> </div>
		 </div>
		 <div class="box_1">
		   <div class="col-md-6 col_1_of_2 span_1_of_2">
             <a class="tiles_info">
			    <div class="tiles-head fb1">
			        <div class="text-center">Facebook</div>
			    </div>
			    <div class="tiles-body fb2">10</div>
			 </a>
		   </div>
		   <div class="col-md-6 col_1_of_2 span_1_of_2">
              <a class="tiles_info tiles_blue">
			    <div class="tiles-head tw1">
			        <div class="text-center">Twitter</div>
			    </div>
			    <div class="tiles-body tw2">30</div>
			  </a>
		   </div>
		   <div class="clearfix"> </div>
		   </div>
		  </div>
		  <div class="cloud">
			<div class="grid-date">
				<div class="date">
					<p class="date-in">New York</p>
					<span class="date-on">°F °C </span>
					<div class="clearfix"> </div>							
				</div>
				<h4>30°<i class="fa fa-cloud-upload"> </i></h4>
			</div>
			<p class="monday">Monday 10 July</p>
		  </div>
		</div>
		<div class="clearfix"> </div>
	    </div>
		--%>
		<div class="copy">
            <p><s:text name="w.copyright"></s:text></p>
	    </div>
		</div>
       </div>
      <!-- /#page-wrapper -->
   </div>
    <!-- /#wrapper -->
    <!-- Bootstrap Core JavaScript -->
    <script src="<s:url value='/Java Script/jakartasoftware/admin/bootstrap.min.js'/>"></script>
</body>
</html>