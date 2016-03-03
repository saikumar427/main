<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.io.*"%>
<%@include file="../getresourcespath.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<tiles:importAttribute name="baseref" ignore="true" scope="request" />
<tiles:importAttribute name="basecontent" ignore="true" scope="request" />
<tiles:importAttribute name="othercontext" ignore="true" scope="request" />

<!-- <title>Eventbee - Your Online Registration, Event Ticketing And Event Promotion Solution</title> -->

<meta name="Description"
	content="Easy to use web based Event Management solution that includes Event Ticketing, Email Marketing, Event Promotion and Membership Management" />

<meta name="Keywords"
	content="event registration, event ticketing, online registration, online ticketing, conference registration, online event payments, event software, email marketing, membership management, paypal ticketing, google ticketing, facebook ticketing, ning ticketing, party ticketing, sports ticketing, venue ticketing" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="canonical" href="http://www.eventbee.com" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/tooltipster.css" />

<tiles:importAttribute name="menucontext" ignore="true" scope="request" />

<style>


#container {
background: transparent;
}

.navbar-nav {
	margin: 18px -8px;
}

.signup {
	margin-top: -8px
}
</style>
</head>
<script>
window.resizeIframe = function() {
var obj = document.getElementById('popup');
obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
}
</script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<body style="background-color:#f3f6fa">
<div id="baseheader"><!-------------------------Begin of Header----------------------------->
<tiles:insertAttribute name="header" ignore="true" /> <!-------------------------End of Header----------------------------->
</div>

<div id="singleValuePopUpOptions">
		<div id="singleerroroptions" style="display:none; color: red;"></div>
		<span id="defoption"></span>
	    <input  key="VALUEOPTION" class="form-control" style="width:100%; display:none;" id="onefieldtextoption"/>
	    <span id="lable"></span><div style="height:10px;"></div>
	    <input type="button" value="<s:text name="global.submit.btn.lbl"/>" class="btn btn-sm btn-primary" key="YES"/>
	    <input type="button" value="<s:text name="global.cancel.btn.lbl"/>" class="btn btn-sm btn-active" key="NO"/><br/>
	</div>

<div style="margin-top: 120px;margin-bottom: 30px;" id="maincontent">
		<div class="container eb-container">
				<div class="main-content-body col-md-12">
				<tiles:insertAttribute name="maincontent" ignore="true" />
				 <!-------------------------End of main content----------------------------->
				</div>
		</div>
		<!-- eb-contaner -->
</div>


</div>
<div id="footer1"><!-------------------------Begin of Footer----------------------------->
<tiles:insertAttribute name="footer" /> <!-------------------------End of Footer----------------------------->
</div>
<script src="<%=resourceaddress%>/main/bootstrap/js/bootstrap.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.tooltipster.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.validate.min.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.ezpz_hint.js"></script>
</body>
</html>
