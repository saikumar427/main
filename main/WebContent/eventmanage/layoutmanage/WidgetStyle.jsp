<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eventid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String title=DBHelper.getTitle(eventid,widgetid); 

JSONObject style = null;
String headingBackground="",headingText="",border="",contentBackground="",contentText="",topLeft="",topRight="",bottomRight="",bottomLeft="";
Boolean isGlobal=false,hideHeader=false;
try {
	style = DBHelper.getWidgetStyles(eventid,widgetid,"draft");
	JSONObject radius = style.getJSONObject("radius");
	headingBackground = style.getString("header");
	headingText = style.getString("headerText");
	border = style.getString("border");
	contentBackground = style.getString("content");
	contentText = style.getString("contentText");
	topLeft = radius.getString("topLeft");
	topRight = radius.getString("topRight");
	bottomRight = radius.getString("bottomRight");
	bottomLeft = radius.getString("bottomLeft");
	isGlobal = style.getBoolean("global");
	hideHeader = style.getBoolean("hideHeader");
} catch(Exception e) {
	style = DBHelper.InitWidgetStyle(eventid,widgetid);
	if(!"{}".equals(style.toString())) {
		JSONObject radius = style.getJSONObject("radius");
		headingBackground = style.getString("header");
		headingText = style.getString("headerText");
		border = style.getString("border");
		contentBackground = style.getString("content");
		contentText = style.getString("contentText");
		topLeft = radius.getString("topLeft");
		topRight = radius.getString("topRight");
		bottomRight = radius.getString("bottomRight");
		bottomLeft = radius.getString("bottomLeft");
		isGlobal = style.getBoolean("global");
	    hideHeader = style.getBoolean("hideHeader");
	} else {
		out.println("<h2>Server encountered an error.<br>Try again later</h2>");
		return;
	}
}

%>
<!DOCTYPE html>
<html>
<head>
<title>Configure widget: <%=title%>
</title>
<style>
body {
	font-family: Tahoma, Geneva, sans-serif;
	font-size: 12px;
	margin: 0 20px;
}
.widget {
	margin:5px 2px  20px;
	position:"relative";
	border:1px solid <%=border%>;
	width: 100%;
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-webkit-border-bottom-right-radius: <%=bottomRight%>;
	-webkit-border-bottom-left-radius: <%=bottomLeft%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	-moz-border-radius-bottomright: <%=bottomRight%>;
	-moz-border-radius-bottomleft: <%=bottomLeft%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
	border-bottom-right-radius: <%=bottomRight%>;
	border-bottom-left-radius: <%=bottomLeft%>;
}
.widget h2 {
	margin:0;
	font-size:12px;
	background-color:<%=headingBackground%>;
	color:<%=headingText%>;
	border-bottom:1px solid <%=border%>;
	padding:10px;
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
}
.widget-content {
	background-color:<%=contentBackground%>;
	color:<%=contentText%>;
	min-height:30px;
	font-size:0.8em;
	padding:5px 20px;
	text-align: justify;
	-webkit-border-bottom-right-radius: <%=bottomRight%>;
	-webkit-border-bottom-left-radius: <%=bottomLeft%>;
	-moz-border-radius-bottomright: <%=bottomRight%>;
	-moz-border-radius-bottomleft: <%=bottomLeft%>;
	border-bottom-right-radius: <%=bottomRight%>;
	border-bottom-left-radius: <%=bottomLeft%>;
}
.wrapper {
	width:100%;
}

#collapse {
	<%
	if(isGlobal)out.print("display:none;");
	else out.print("display:block;");
	%>
}	
.collapse {
	<%
	if(isGlobal)out.print("display:none;");
	else out.print("display:block;");
	%>
	height: 400px;
	width:800px;
	overflow-x: hidden;
	overflow-y:scroll;
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
.preview {
	width: 250px;
	margin-left:60px;
	font-size:12px;
	/*background-image: url('images/grid.png');
	padding:5px 15px 5px 5px;*/
}
table td {
	padding:2px;
}
/* webkit scroll bar */
::-webkit-scrollbar {
    width: 6px;
}
/* Track */
::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
    -webkit-border-radius: 10px;
    border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
    -webkit-border-radius: 10px;
    border-radius: 10px;
    background: rgba(255,120,0,0.8); 
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5); 
}
::-webkit-scrollbar-thumb:window-inactive {
	background: rgba(255,120,0,0.4); 
}

/* webkit scroll bar */
::-webkit-scrollbar {
    width: 6px;
}
/* Track */
::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
    -webkit-border-radius: 10px;
    border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
    -webkit-border-radius: 10px;
    border-radius: 10px;
    background: rgba(255,120,0,0.8); 
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5); 
}
::-webkit-scrollbar-thumb:window-inactive {
	background: rgba(255,120,0,0.4); 
}


</style>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/js/layoutmanage/jscolor/jscolor.js"></script>
<script>
function hexToRgb(hex) {
    var bigint = parseInt(hex, 16);
    var r = (bigint >> 16) & 255;
    var g = (bigint >> 8) & 255;
    var b = bigint & 255;
    return r + "," + g + "," + b;
}
var updateBackground = null;
var updateHeader = null;
var updateHeaderText = null;
var updateBorder = null;
var updateContent = null;
var updateContentText = null;
$(function() {
	updateHeader = function(color) {
		$('.widget h2').css('background-color','#'+color.toString());
	};
	updateHeaderText = function(color) {
		$('.widget h2').css('color','#'+color.toString());
	};
	updateBorder = function(color) {
		$('.widget').css('border','1px solid #'+color.toString());
		$('.widget h2').css('border-bottom','1px solid #'+color.toString());
	};
	updateContent = function(color) {
		$('.widget-content').css('background','#'+color.toString());
	};
	updateContentText = function(color) {
		$('.widget-content').css('color','#'+color.toString());
	};
	
	$('#borderRadiusTopLeft').change(function(){
		$('.widget').css('-webkit-border-top-left-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-topleft',this.value+'px');
		$('.widget').css('border-top-left-radius',this.value+'px');
		$('.widget h2').css('-webkit-border-top-left-radius',this.value+'px');
		$('.widget h2').css('-moz-border-radius-topleft',this.value+'px');
		$('.widget h2').css('border-top-left-radius',this.value+'px');
		$('#borderRadiusTopLeftText').val(this.value+'px');
		if($('#hideHeader').is(':checked')) {
			$('.widget-content').css('-webkit-border-top-left-radius',$('#borderRadiusTopLeft').val()+'px');
			$('.widget-content').css('-moz-border-radius-topleft',$('#borderRadiusTopLeft').val()+'px');
			$('.widget-content').css('border-top-left-radius',$('#borderRadiusTopLeft').val()+'px');
		}
	});
	$('#borderRadiusTopRight').change(function(){
		$('.widget').css('-webkit-border-top-right-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-topright',this.value+'px');
		$('.widget').css('border-top-right-radius',this.value+'px');
		$('.widget h2').css('-webkit-border-top-right-radius',this.value+'px');
		$('.widget h2').css('-moz-border-radius-topright',this.value+'px');
		$('.widget h2').css('border-top-right-radius',this.value+'px');
		$('#borderRadiusTopRightText').val(this.value+'px');
		if($('#hideHeader').is(':checked')) {
			$('.widget-content').css('-webkit-border-top-right-radius',$('#borderRadiusTopRight').val()+'px');
			$('.widget-content').css('-moz-border-radius-topright',$('#borderRadiusTopRight').val()+'px');
			$('.widget-content').css('border-top-right-radius',$('#borderRadiusTopRight').val()+'px');
		}
	});
	$('#borderRadiusBottomLeft').change(function(){
		$('.widget').css('-webkit-border-bottom-left-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-bottomleft',this.value+'px');
		$('.widget').css('border-bottom-left-radius',this.value+'px');
		$('.widget-content').css('-webkit-border-bottom-left-radius',this.value+'px');
		$('.widget-content').css('-moz-border-radius-bottomleft',this.value+'px');
		$('.widget-content').css('border-bottom-left-radius',this.value+'px');
		$('#borderRadiusBottomLeftText').val(this.value+'px');
	});
	$('#borderRadiusBottomRight').change(function(){
		$('.widget').css('-webkit-border-bottom-right-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-bottomright',this.value+'px');
		$('.widget').css('border-bottom-right-radius',this.value+'px');
		$('.widget-content').css('-webkit-border-bottom-right-radius',this.value+'px');
		$('.widget-content').css('-moz-border-radius-bottomright',this.value+'px');
		$('.widget-content').css('border-bottom-right-radius',this.value+'px');
		$('#borderRadiusBottomRightText').val(this.value+'px');
	});
	
	var getGlobalStyles = function(){
		var settings = {};
		settings['global']=$('#global').is(':checked');
		settings['header']=$('#header').val();
		settings['headerText']=$('#headerText').val();
		settings['border']=$('#border').val();
		settings['content']=$('#content').val();
		settings['contentText']=$('#contentText').val();
		var radius = {};
		radius['topLeft']=$('#borderRadiusTopLeftText').val();
		radius['topRight']=$('#borderRadiusTopRightText').val();
		radius['bottomLeft']=$('#borderRadiusBottomLeftText').val();
		radius['bottomRight']=$('#borderRadiusBottomRightText').val();
		settings['radius']=radius;
		settings['hideHeader']=$('#hideHeader').is(':checked');
		return settings;
	};
	
	$('.savebtn').on('click',function() {
		var savebutton = $(this);
		savebutton.html('Saving...');
		savebutton.attr('disabled','disabled');
		var data = getGlobalStyles();
		$.getJSON('SaveSettings.jsp?type=widgetStyle&eid=<%=eventid%>&widgetid=<%=widgetid%>',{
			data:JSON.stringify(data)
		}).done(function(response){
			if(response.status=="success") {
				if(savebutton.attr('id').indexOf('Exit')>-1)			
					parent.disablePopup();
					else{
						savebutton.html('Save');
						savebutton.removeAttr('disabled');
					}
			} else {
				alert("Server did not respond in a timely fashion. Try again later.");
				savebutton.removeAttr('disabled');
			}
		});
	});
	
	$('#cancel').click(function(){
		//if(confirm("All changes will be discarded. Continue?"))
			parent.disablePopup();
			//window.close();
	})
	
	$('#global').click(function(){
		if($('#collapse').is(':visible'))
			parent.$('#popup').css("height","150px");
		else
			parent.$('#popup').css("height","530px");
		
		$("#collapse").slideToggle();
		
				
	});
	
	$('#hideHeader').click(function(){
		if(this.checked) {
			$('.widget').find('h2').hide();
			$('.widget-content').css('-webkit-border-top-left-radius',$('#borderRadiusTopLeft').val()+'px');
			$('.widget-content').css('-moz-border-radius-topleft',$('#borderRadiusTopLeft').val()+'px');
			$('.widget-content').css('border-top-left-radius',$('#borderRadiusTopLeft').val()+'px');
			$('.widget-content').css('-webkit-border-top-right-radius',$('#borderRadiusTopRight').val()+'px');
			$('.widget-content').css('-moz-border-radius-topright',$('#borderRadiusTopRight').val()+'px');
			$('.widget-content').css('border-top-right-radius',$('#borderRadiusTopRight').val()+'px');
		} else {
			$('.widget').find('h2').show();
			$('.widget-content').css('-webkit-border-top-left-radius','0px');
			$('.widget-content').css('-moz-border-radius-topleft','0px');
			$('.widget-content').css('border-top-left-radius','0px');
			$('.widget-content').css('-webkit-border-top-right-radius','0px');
			$('.widget-content').css('-moz-border-radius-topright','0px');
			$('.widget-content').css('border-top-right-radius','0px');
		}
	});
});
</script>
</head>
<body>
	<div class="wrapper">
		<h3>Edit widget style: <%=title%></h3>
		<input type="checkbox" id="global" <%if(isGlobal)out.print("checked");%>/>
		<label for="global"> Use global Style</label>
		<br><br>
		<!-- div class="collapse" style="height: 400px;width:800px;overflow-x: hidden;overflow-y:scroll;"-->
		<div style="clear:both;" id="collapse">
			<hr>
			<div class="editor" style="float:left;border-right:1px solid grey;">
				<table>
					<tr>
						<td>Widget heading background</td>
						<td><input class="color {hash:true,onImmediateChange:'updateHeader(this);',pickerPosition:'left'}" id="header" size="8" value="<%=headingBackground%>"/></td>
						<td rowspan="2"><input type="checkbox" id="hideHeader" <%if(hideHeader)out.print("checked");%>/><label for="hideHeader">Hide header</label></td>
					</tr>
					<tr>
						<td>Widget heading text</td>
						<td><input class="color {hash:true,onImmediateChange:'updateHeaderText(this);',pickerPosition:'left'}" id="headerText" size="8" value="<%=headingText%>"/></td>
					</tr>
					<tr>
						<td>Border</td>
						<td><input class="color {hash:true,onImmediateChange:'updateBorder(this);',pickerPosition:'left'}" id="border" size="8" value="<%=border%>"/></td>
					</tr>
					<tr>
						<td>Widget content background</td>
						<td><input class="color {hash:true,onImmediateChange:'updateContent(this);',pickerPosition:'left'}" id="content" size="8" value="<%=contentBackground%>"/></td>
					</tr>
					<tr>
						<td>Widget content text</td>
						<td><input class="color {hash:true,onImmediateChange:'updateContentText(this);',pickerPosition:'left'}" id="contentText" size="8" value="<%=contentText%>"/></td>
					</tr>
					<tr>
						<td><strong>Widget Radius</strong></td>
						<td></td>
					</tr>
					<tr>
						<td>Top Left</td>
						<td><input type="range" id="borderRadiusTopLeft" value="<%=topLeft.substring(0, topLeft.length()-2)%>" min="0" max="35" /></td>
						<td><input type="text" size="1" readonly="readonly" id="borderRadiusTopLeftText" value="<%=topLeft%>"/></td>
					</tr>
					<tr>
						<td>Top Right</td>
						<td><input type="range" id="borderRadiusTopRight" value="<%=topRight.substring(0, topRight.length()-2)%>" min="0" max="35" /></td>
						<td><input type="text" size="1" readonly="readonly" id="borderRadiusTopRightText" value="<%=topRight%>"/></td>
					</tr>
					<tr>
						<td>Bottom Left</td>
						<td><input type="range" id="borderRadiusBottomLeft" value="<%=bottomLeft.substring(0, bottomLeft.length()-2)%>" min="0" max="35" /></td>
						<td><input type="text" size="1" readonly="readonly" id="borderRadiusBottomLeftText" value="<%=bottomLeft%>"/></td>
					</tr>
					<tr>
						<td>Bottom Right</td>
						<td><input type="range" id="borderRadiusBottomRight" value="<%=bottomRight.substring(0, bottomRight.length()-2)%>" min="0" max="35" /></td>
						<td><input type="text" size="1" readonly="readonly" id="borderRadiusBottomRightText" value="<%=bottomRight%>"/></td>
					</tr>
				</table>
				</div>
				<div style="float:left;top:0px;left:370px;">
				<div style="padding-left:60px;"><h3>Preview</h3></div>
				<div class="preview">
				<div class="widget">
				<% if(hideHeader) { %>
					<h2 style="display: none;"><%=widgetTitle%></h2>
					<div style="
					-webkit-border-top-left-radius:<%=topLeft%>;
					-moz-border-radius-topleft:<%=topLeft%>;
					border-top-left-radius:<%=topLeft%>;
					-webkit-border-top-right-radius:<%=topRight%>;
					-moz-border-radius-topright:<%=topRight%>;
					border-top-right-radius:<%=topRight%>;
					" class="widget-content">
				<%} else {%>
				<h2><%=widgetTitle%></h2>
						<div class="widget-content">
				<%} %>
							<p>Content goes here</p>
							<p>Content goes here</p>
						</div>
					</div>
				</div>
				</div>
				
				</div>
				
			</div>
			
			<div style="clear: both;height: 10px;"></div>
			<button id="save" class="savebtn">Save</button>
			<button id="saveExit" class="savebtn">Save &amp; Exit</button>
			<button id="cancel">Exit</button>
		</div>
	
</body>
</html>
