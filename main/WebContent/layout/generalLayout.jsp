<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@include file="../getresourcespath.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<!-- YUI Engine Files-->
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/container/assets/skins/sam/container.css" />
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/datatable/assets/skins/sam/datatable.css" />
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/tabview/assets/skins/sam/tabview.css" />
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/build/paginator/assets/skins/sam/paginator.css" />

<script type="text/javascript" src="<%=resourceaddress%>/main/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/element/element-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/button/button-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/datatable/datatable-min.js"></script>

<script type="text/javascript" src="<%=resourceaddress%>/main/build/connection/connection-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/container/container-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/tabview/tabview-min.js"></script>

<script type="text/javascript" src="<%=resourceaddress%>/main/build/paginator/paginator-min.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/build/json/json-min.js"></script>
<!-- End YUI Engine Files-->
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=resourceaddress%>/main/css/yuioverrides.css"/>
<link rel="stylesheet" type="text/css" href='<%=resourceaddress%>/main/css/common.css' />


<script type="text/javascript" src="<%=resourceaddress%>/main/js/common/nifty.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/prototype.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/common/common.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/braintreestates.js"></script>
</head>
<body class="yui-skin-sam">
<div id="header">
<!-------------------------Begin of Header----------------------------->
<tiles:insertAttribute name="header" ignore="true" />
<!-------------------------End of Header----------------------------->
</div>
<div id="bodycontainer">
<div id="wrapper" class="clearfix" > 
<div id="singledatacol">
<s:if test='actionTitle==""'></s:if><s:else><div id="tasktitlebar">${actionTitle}</div></s:else>
<div id="maincontent">
<!-------------------------Begin of main content----------------------------->
<tiles:insertAttribute name="maincontent" ignore="true" />
<!-------------------------End of main content----------------------------->
</div>
</div>
</div>
<div  id="footer" >
<!-------------------------Begin of Footer----------------------------->
<tiles:insertAttribute name="footer" />
<!-------------------------End of Footer----------------------------->
</div>
</div>
</body>
</html>
