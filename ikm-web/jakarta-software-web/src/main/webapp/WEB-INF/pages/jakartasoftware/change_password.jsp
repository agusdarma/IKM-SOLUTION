<!DOCTYPE html>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
	<head>	
<title><s:text name="t.changePassword"/></title>
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
			    <li class="dropdown">
	        		<a href="#" class="dropdown-toggle avatar" data-toggle="dropdown"><img src="<s:url value='/Resource/images/myagenda.png'/>"><span class="badge"><s:property value="respListAgendaVO.jumlahMessageUnread"/></span></a>
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
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        <div id="page-wrapper">
        <div class="graphs">
			<div class="xs">
				<h3>Change Password</h3>
				<div class="tab-content">
					<div class="tab-pane active" id="horizontal-form">
						<form class="form-horizontal">
								<div class="form-group">
									<label for="focusedinput" class="col-sm-2 control-label">Old Password</label>
									<div class="col-sm-8">
										<input type="text" class="form-control1" id="focusedinput" placeholder="Default Input">
									</div>									
								</div>
								<div class="form-group">
									<label for="focusedinput" class="col-sm-2 control-label">New Password</label>
									<div class="col-sm-8">
										<input type="text" class="form-control1" id="focusedinput" placeholder="Default Input">
									</div>									
								</div>
								<div class="form-group">
									<label for="focusedinput" class="col-sm-2 control-label">Confirm New Password</label>
									<div class="col-sm-8">
										<input type="text" class="form-control1" id="focusedinput" placeholder="Default Input">
									</div>									
								</div>
						</form>
					</div>
				</div>
		
    		<div class="clearfix"> </div>
	    </div>     	
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