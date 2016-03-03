<%@page import="com.event.helpers.I18n"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.event.helpers.MetaTagsData"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.io.*"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
<s:set var="jspVariable" value="%{pattern}"/>
<jsp:useBean id="jspVariable" type="java.lang.String" />

<title><%=MetaTagsData.getPageTag(jspVariable,"title",I18n.getLanguageFromSession())==null?MetaTagsData.getPageTag("default","title",I18n.getLanguageFromSession()):MetaTagsData.getPageTag(jspVariable,"title",I18n.getLanguageFromSession())%></title>
<meta name="Description"
	content="<%=MetaTagsData.getPageTag(jspVariable,"description",I18n.getLanguageFromSession())==null?MetaTagsData.getPageTag("default","description",I18n.getLanguageFromSession()):MetaTagsData.getPageTag(jspVariable,"description",I18n.getLanguageFromSession())%>" />
<meta name="Keywords"
	content="<%=MetaTagsData.getPageTag(jspVariable,"keywords",I18n.getLanguageFromSession())==null?MetaTagsData.getPageTag("default","keywords",I18n.getLanguageFromSession()):MetaTagsData.getPageTag(jspVariable,"keywords",I18n.getLanguageFromSession())%>" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="canonical" href="http://www.eventbee.com" />

<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/tooltipster.css" />

<tiles:importAttribute name="menucontext" ignore="true" scope="request" />

<style>
#header {
	height: 94px;
}
#bodycontainer {
margin:90px auto 0px;
}
#container {
background: transparent;
}
body {
background-color: #F3F6FA;
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
<body>
<div id="baseheader"><!-------------------------Begin of Header----------------------------->
<tiles:insertAttribute name="header" ignore="true" /> <!-------------------------End of Header----------------------------->
</div>
<div id="bodycontainer">




<div id="singledatacol"><s:if test='actionTitle==""'></s:if><s:else>
	<!--<div id="tasktitlebar">${actionTitle}</div>  -->

</s:else>
<div id="maincontent"><!-------------------------Begin of main content----------------------------->
<div class="container" style="width:100%;padding:0px;margin:0px;">

<tiles:insertAttribute name="maincontent" ignore="true" />
 <!-------------------------End of main content----------------------------->
 
 </div>
</div>
</div>



</div>
<div id="footer1"><!-------------------------Begin of Footer----------------------------->
<tiles:insertAttribute name="footer" /> <!-------------------------End of Footer----------------------------->
</div>
<script src="<%=resourceaddress%>/main/js/bootstrap.min.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.tooltipster.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.validate.min.js"></script>
<script src="<%=resourceaddress%>/main/js/jquery.ezpz_hint.js"></script>
</body>
</html>
