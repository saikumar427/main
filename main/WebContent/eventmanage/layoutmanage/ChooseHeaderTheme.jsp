<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eid");
JSONObject j = DBHelper.BrowseThemes();
JSONArray themes = j.getJSONArray("themes");
String currentThemeName = DBHelper.currentThemeName(eventid);
String curTheme = "";
%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />

<title>Choose Header Theme</title>
<style>

.content {
	
	overflow-y:scroll;
	height: 400px;
}

img{
	width:100%;
	border: 1px solid #f5f5f5;
}
 ::-webkit-scrollbar-track
{

	-webkit-box-shadow: inset 0 0 0px rgba(0,0,0,0.3);
	background-color: tranparent;
	width:10px;
}
::-webkit-scrollbar{height:10px;width:7px}
::-webkit-scrollbar-button{height:0;width:0}
 ::-webkit-scrollbar-thumb{
 background-clip:padding-box;
 background-color:rgba(0,0,0,.3);
 -webkit-border-radius:10px;
 border-radius:10px;
 min-height:10px;
 min-width:10px;
 height:10px;
 width:10px
 }
::-webkit-scrollbar-thumb:hover, ::-webkit-scrollbar-thumb:active {
  background-color:rgba(0,0,0,.4)
 }
 ::-webkit-scrollbar-track:hover, ::-webkit-scrollbar-track:active{
   -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.3);
    background-color: #f4f4f4;
	width:10px;
}
.fa{
	color:#000;
}
.applyTheme_{
	background-color:#1b90f2;
}
</style>
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script>
$(function(){
	$('.applyTheme').on('click',function() {
		$('#checkId').remove();
		var savebutton = $(this);
		savebutton.html(props.pg_header_theme_plz_wait_lbl);
		savebutton.attr('disabled','disabled');
		savebutton.after('<span id="checkId">&nbsp;<i class="fa fa-check"></i></span>');
		var url="SaveSettings?type=applyHeaderTheme&eid=<%=eventid%>&action=SaveSettings";
		var data = savebutton.parent().data('themeid');
		$('#chooseData').val(data);
		$.ajax({
			type:'POST',
			url:url,
			data:$('#chooseForm').serialize(),
			success:function(response){
				if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
				if(response.status=="success") {
					//$('.applyTheme').css('background-color','#444');
					$('.applyTheme').html(props.pg_theme_apply_this_lbl);
					$('.applyTheme').removeAttr('disabled');
					savebutton.css('background-color','#5388C4');
					//savebutton.html('Theme applied &#x2713;');
					savebutton.html(props.pg_header_theme_applied_lbl);
					//savebutton.html('<button class="applyTheme">Theme applied &#x2713;</button><span id="checkId">&nbsp;<i class="fa fa-check"></i></sapn>');
					savebutton.attr('disabled','disabled');
					
				} else {
					alert(props.pg_header_not_respond_lbl);
					savebutton.removeAttr('disabled');
					savebutton.html(props.pg_theme_apply_this_lbl);
				}
			}
		});
		
		<%-- $.getJSON('SaveSettings?type=applyHeaderTheme&eid=<%=eventid%>',{
			data:savebutton.parent().data('themeid')
		}).done(function(response){
			if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
			if(response.status=="success") {
				//$('.applyTheme').css('background-color','#444');
				$('.applyTheme').html(props.pg_theme_apply_this_lbl);
				$('.applyTheme').removeAttr('disabled');
				savebutton.css('background-color','#5388C4');
				//savebutton.html('Theme applied &#x2713;');
				savebutton.html(props.pg_header_theme_applied_lbl);
				//savebutton.html('<button class="applyTheme">Theme applied &#x2713;</button><span id="checkId">&nbsp;<i class="fa fa-check"></i></sapn>');
				savebutton.attr('disabled','disabled');
				
			} else {
				alert(props.pg_header_not_respond_lbl);
				savebutton.removeAttr('disabled');
				savebutton.html(props.pg_theme_apply_this_lbl);
			}
		}); --%>
	});
});
</script>
</head>
<body>
<form id="chooseForm">
<input type="hidden" name="data" id="chooseData" />
</form>
<%-- 	<div class="tabmenu">
		<ul class="menu">
		     <li><a href="/main/eventmanage/layoutmanage/HeaderSettings?eid=<%=eventid%>">Header Settings</a>
			<li class="active"><a href="#">Choose Header Theme</a></li>
			<li><a href="/main/eventmanage/layoutmanage/HeaderSettings?eid=<%=eventid%>">Header Settings</a>
			<li><a href="/main/eventmanage/layoutmanage/MyHeaderTheme?eid=<%=eventid%>">My Header Theme</a></li>
		</ul>
	</div> --%>
	<div class="content">
		<%for(int i=0;i<themes.length();i++) {
			JSONObject currentTheme = themes.getJSONObject(i);
			curTheme = currentTheme.getString("name");
		%>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="col-md-6 col-sm-6 col-xs-12">
				<img src="<%=currentTheme.getString("thumbnail")%>" />
			</div>
			<div class="col-md-6 col-sm-6 col-xs-12" data-themeid="<%=currentTheme.getString("themeid")%>">
				<p style="font-weight: bold;margin-top: 0"><%=curTheme%></p>
				<p><%=currentTheme.get("description")%></p>
				<%if(curTheme.equals(currentThemeName)){ %>
				<button class="applyTheme btn btn-primary" disabled="disabled"><s:text name="pg.header.choose.selected.theme.lbl" /></button>
				<span id='checkId'>&nbsp;<i class="fa fa-check"></i></span>
				<%}else{ %>
				<button class="applyTheme btn btn-primary"><s:text name="pg.header.apply.this.theme.lbl" /></button>
				<%} %>
				
			</div>
		</div>
		<div style="clear: both;"></div>
		<hr>
		<%} %>
	</div>
	<div class="text-center" style="margin-top:10px;">
		<button id="exit"  class="btn btn-primary "><s:text name="pg.widgets.exit" /></button>
	</div>
	
	<script>
		document.getElementById("exit").onclick = function(){
			parent.disablePopup();
		};
	</script>
</body>
</html>