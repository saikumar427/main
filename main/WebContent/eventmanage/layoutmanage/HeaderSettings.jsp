<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eid");
if(eventid==null || "".equals(eventid)) {
	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	return;
}
JSONObject global_style = new JSONObject(DBHelper.getGlobalStyles(eventid));
String logoURL="",logoMsg="",coverPhoto="",title="",details="",bodyBackgroundImage="",bodyBackgroundColor="";
String bodyBackgroundPosition="", contentBackgroundRgba="",border="";
if(global_style.has("logourl"))
 logoURL=global_style.getString("logourl");
if(global_style.has("logomsg"))
 logoMsg=global_style.getString("logomsg");	
if(global_style.has("coverPhoto"))
coverPhoto = global_style.getString("coverPhoto");
if(global_style.has("title"))title = global_style.getString("title");
if(global_style.has("details")) details = global_style.getString("details");
if(global_style.has("bodyBackgroundImage")) bodyBackgroundImage= global_style.getString("bodyBackgroundImage");
if(global_style.has("bodyBackgroundColor")) bodyBackgroundColor= global_style.getString("bodyBackgroundColor");
if(global_style.has("backgroundPosition")) bodyBackgroundPosition= global_style.getString("backgroundPosition");
if(global_style.has("contentBackground")) contentBackgroundRgba = global_style.getString("contentBackground");
if(global_style.has("border"))border = global_style.getString("border");
%>
<!DOCTYPE html>
<html>
<head>
<title>My Header Theme</title>
<style>

body {
	margin:20px 0;
	font-family: Tahoma, Geneva, sans-serif;
}
.tabmenu {
	border-bottom: 1px solid #cccccc;
}
ul.menu {
	list-style: none;
	padding: 0 0 10px 0;
	margin: 0 0 0 10px;
	font-size: 0.75em;
	font-family: Arial, Verdana, sans-serif;
}
ul.menu li {
	display: inline;
}
ul.menu li a {
	border: 1px solid #cccccc;
	padding: 10px 25px 10px;
	text-decoration: none;
	background-color: #ffffff;
	border-bottom: none;
	outline: none;
	color:#444;
}
ul.menu li a:hover {
	color: #888888;
}
ul.menu li.active a {
	border-bottom: none;
	color: #888888;
	padding-bottom: 11px;
}
ul.menu li.active a:hover {
	color: #444444;
}

.wrapper {
	width:800px;
	height:100%;
	margin:0 30px;
}

.editor {
	height: 400px;
	overflow-y:scroll;
	float: left;
	width: 50%;
	font-size: 12px;
	
	/* shadows when scrolled. */
	overflow: auto;
	background:
		/* Shadow covers */
		linear-gradient(white 30%, rgba(255,255,255,0)),
		linear-gradient(rgba(255,255,255,0), white 70%) 0 100%,
		
		/* Shadows */
		radial-gradient(50% 0, farthest-side, rgba(0,0,0,.2), rgba(0,0,0,0)),
		radial-gradient(50% 100%,farthest-side, rgba(0,0,0,.2), rgba(0,0,0,0)) 0 100%;
	background:
		/* Shadow covers */
		linear-gradient(white 30%, rgba(255,255,255,0)),
		linear-gradient(rgba(255,255,255,0), white 70%) 0 100%,
		
		/* Shadows */
		radial-gradient(farthest-side at 50% 0, rgba(0,0,0,.2), rgba(0,0,0,0)),
		radial-gradient(farthest-side at 50% 100%, rgba(0,0,0,.2), rgba(0,0,0,0)) 0 100%;
	background-repeat: no-repeat;
	background-color: white;
	background-size: 100% 40px, 100% 40px, 100% 14px, 100% 14px;
	
	/* Opera doesn't support this in the shorthand */
	background-attachment: local, local, scroll, scroll;
	
}
.right{
	float: left;
	width:40%;
	margin-left:40px;
}
.bgcolor {
	border: 1px solid #000;
	padding:0 20px;
	background-color: <%=bodyBackgroundColor%>;
}
.preview {
	width:80%;
	background: <%=bodyBackgroundColor%>;
	background-image: url('<%=bodyBackgroundImage%>');
	background-repeat:no-repeat;
	background-position:top;
	background-size:100% 100%;
	padding-left: 30px;
	padding-right: 30px;
}
.header {
	margin-bottom: -3px
}
.title {
	position:relative;
	margin-top:-21px;
	color:<%=title%>;
	background: -moz-linear-gradient(top,  rgba(0,0,0,0) 0%, rgba(0,0,0,0.65) 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(0,0,0,0)), color-stop(100%,rgba(0,0,0,0.65)));
	background: -webkit-linear-gradient(top,  rgba(0,0,0,0) 0%,rgba(0,0,0,0.65) 100%);
	background: -o-linear-gradient(top,  rgba(0,0,0,0) 0%,rgba(0,0,0,0.65) 100%);
	background: -ms-linear-gradient(top,  rgba(0,0,0,0) 0%,rgba(0,0,0,0.65) 100%);
	background: linear-gradient(to bottom,  rgba(0,0,0,0) 0%,rgba(0,0,0,0.65) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#00000000', endColorstr='#a6000000',GradientType=0 );
}

.layer {
	background-color: <%=contentBackgroundRgba%>;
	padding:30px;
	height:100%;
	position: relative;
	color: <%=details%>;
	font-size: 12px;
}
.widget {
	margin:5px 2px  20px;
	position:"relative";
	width: 100%;
	
}

</style>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/js/layoutmanage/jscolor/jscolor.js"></script>
<script>
var updateTitle = null;
var updateDetails = null;
$(function() {

	$('#cover-photo').change(function(){
		$('.header img').attr('src',this.value);
	});
	
	
	updateTitle = function(color) {
		$('.title').css('color','#'+color.toString());
	};

	updateDetails = function(color) {
		$('.layer').css('color','#'+color.toString());
	};

	var getGlobalStyles = function(){
		var settings = {};
		
		settings['details']=$('#details').val();
		settings['coverPhoto']=$('#cover-photo').val();
		settings['logourl']=$('#logourl').val();
		settings['logomsg']=$('#logomsg').val();
		settings['title']=$('#title').val();
		return settings;
	};


	

$('#save').on('click',function() {
	var savebutton = $(this);
	savebutton.html('Saving...');
	savebutton.attr('disabled','disabled');
	var data = getGlobalStyles();
	$.getJSON('SaveSettings.jsp?type=HeaderSettings&eid=<%=eventid%>',{
		data:JSON.stringify(data)
	}).done(function(response){
		if(response.status=="success") {
			savebutton.html('All changes saved');
			setTimeout(function(){
				savebutton.removeAttr('disabled');
				savebutton.html('Save');
			},1000);
		} else {
			alert("No data received from server. Try again later.");
			savebutton.removeAttr('disabled');
		}
	});
});
$('#saveExit').on('click',function() {
	var savebutton = $(this);
	savebutton.html('Saving...');
	savebutton.attr('disabled','disabled');
	var data = getGlobalStyles();
	$.getJSON('SaveSettings.jsp?type=HeaderSettings&eid=<%=eventid%>',{
		data:JSON.stringify(data)
	}).done(function(response){
		if(response.status=="success") {
			parent.disablePopup();
		} else {
			alert("No data received from server. Try again later.");
			savebutton.removeAttr('disabled');
		}
	});
});


$('#exit').on('click',function() {
	//if(confirm("Are you sure?"))
		parent.disablePopup();
});



});
</script>


</head>
<body>
<div class="tabmenu">
		<ul class="menu">
		   <!--  <li class="active"><a href="#">Header Settings</a></li> -->
			<li><a href="/main/eventmanage/layoutmanage/ChooseHeaderTheme?eid=<%=eventid%>">Choose Header Theme</a></li>
			 <li class="active"><a href="#">Header Settings</a></li>
			<li><a href="/main/eventmanage/layoutmanage/MyHeaderTheme?eid=<%=eventid%>">My Header Theme</a></li>
		</ul>
	</div>
	<br/>
<div class="wrapper" style="clear:both;">

<div class="editor" style="float:left;border-right:1px solid grey;">
<table>
				<tr>
					<td colspan="2"><h2>Header settings</h2></td>
				</tr>
				<tr>
					<td>Cover photo</td>
					<td><input type="text" value="<%=coverPhoto%>" placeholder="Enter image URL here" id="cover-photo"/></td>
				</tr>
				<tr>
				     <td>Logo URL</td>
				     <td><input type="text" value="<%=logoURL%>" placeHolder="Enter logo URL here" id="logourl"> </td>  
				</tr>
				
				<tr>
				     <td>Logo Message</td>
				     <td><input type="text" value="<%=logoMsg%>" placeHolder="Enter logo Msg here" id="logomsg"> </td>  
				</tr>
				
				<tr>
					<td>Title color</td>
					<td><input class="color {hash:true,onImmediateChange:'updateTitle(this);',pickerPosition:'left'}" id="title" size="8" value="<%=title%>"/></td>
				</tr>
				<tr>
					<td>Details text color</td>
					<td><input class="color {hash:true,onImmediateChange:'updateDetails(this);',pickerPosition:'left'}" id="details" size="8" value="<%=details%>"/></td>
				</tr>


</table>
</div>
<div style="float:left;top:0px;left:370px;">
<div style="padding-left:50px;"><h3>Preview</h3></div>
	<div style="padding-left: 50px">
	<div class="bgcolor">
	<div class="preview">
		<div class="header">
		<img src="<%=coverPhoto%>" style="width:222px" />
						<div class="title">&nbsp;Event title</div>
		</div>
		
		<div class="layer">
						Time, date, place...
						<div class="widget">
							 <h2></h2> 
							<div class="widget-content" style="height:50px">
								
							</div>
						</div>
					</div>
	</div>
	</div>


</div>
</div>
</div>
<div style="clear: both;height: 0px"></div>
		<button id="save">Save</button>
		<button id="saveExit">Save &amp; exit</button>
		<button id="exit">Exit without saving</button>

</body>