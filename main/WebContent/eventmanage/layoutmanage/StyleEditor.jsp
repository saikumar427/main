<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.awt.Color"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="org.json.JSONObject"%>
<%@page trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String eventid = request.getParameter("eid");
	if (eventid == null || "".equals(eventid)) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	JSONObject global_style = new JSONObject(DBHelper.getGlobalStyles(eventid));
	String bodyBackgroundImage = global_style.getString("bodyBackgroundImage");
	String bodyBackgroundColor = global_style.getString("bodyBackgroundColor");
	String bodyBackgroundPosition = global_style.getString("backgroundPosition");
	String bodyFullWidth = global_style.getString("fullWidth");
	String logoURL = "", logoMsg = "";
	if (global_style.has("logourl"))
		logoURL = global_style.getString("logourl");
	if (global_style.has("logomsg"))
		logoMsg = global_style.getString("logomsg");
	String title = global_style.getString("title");
	String coverPhoto = global_style.getString("coverPhoto");
	String contentBackgroundRgba = global_style.getString("contentBackground");
	
	String contentBackgroundHex = contentBackgroundRgba.split("rgba")[1];
	contentBackgroundHex = contentBackgroundHex.substring(1,contentBackgroundHex.length() - 1);
	int r = Integer.parseInt(contentBackgroundHex.split(",")[0]);
	int g = Integer.parseInt(contentBackgroundHex.split(",")[1]);
	int b = Integer.parseInt(contentBackgroundHex.split(",")[2]);
	Color color = new Color(r, g, b);
	contentBackgroundHex = "#"+ Integer.toHexString(color.getRGB()).substring(2);
	String contentBackgroundOpacity = contentBackgroundRgba.split(",")[3];
	contentBackgroundOpacity = contentBackgroundOpacity.substring(0,contentBackgroundOpacity.length() - 1);
	//String details = global_style.getString("details");
	String header = global_style.getString("header");
	String headerText = global_style.getString("headerText");

	String headerTextFont = "Verdana";
	String headerTextSize = "16";
	String contentTextSize = "14";
	String contentTextFont = "Verdana";
	String titleShowHide = "Y";

	if (global_style.has("headerTextFont"))
		headerTextFont = global_style.getString("headerTextFont");
	if (global_style.has("headerTextSize"))
		headerTextSize = global_style.getString("headerTextSize");
	if (global_style.has("contentTextSize"))
		contentTextSize = global_style.getString("contentTextSize");
	if (global_style.has("contentTextFont"))
		contentTextFont = global_style.getString("contentTextFont");
	if(global_style.has("titleShow"))
		titleShowHide =global_style.getString("titleShow");

	String border = global_style.getString("border");
	String content = global_style.getString("content");
	String contentText = global_style.getString("contentText");
	JSONObject radius = new JSONObject(global_style.getString("radius"));
	String topLeft = radius.getString("topLeft");
	String topRight = radius.getString("topRight");
	String bottomLeft = radius.getString("bottomLeft");
	String bottomRight = radius.getString("bottomRight");
%>
<!DOCTYPE html>
<html>
<head>
<title>Style Editor</title>
<style>
.widget {
	margin-bottom: 30px;
	position: "relative";
	border: 1px solid<%=border%>;
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
	margin: 0;
	font-size: <%=headerTextSize%>px;
	font-family: <%=headerTextFont%>;
	background-color: <%=header%>;
	color: <%=headerText%>;
	border-bottom: 1px solid<%=border%>;
	padding: 10px;
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
}

.widget-content {
	font-family: <%=contentTextFont%>;
	font-size: <%=contentTextSize%>px;
	background-color: <%=content%>;
	color: <%=contentText%>;
	min-height: 30px;
	padding: 15px;
	text-align: justify;
	-webkit-border-bottom-right-radius: <%=bottomRight%>;
	-webkit-border-bottom-left-radius: <%=bottomLeft%>;
	-moz-border-radius-bottomright: <%=bottomRight%>;
	-moz-border-radius-bottomleft: <%=bottomLeft%>;
	border-bottom-right-radius: <%=bottomRight%>;
	border-bottom-left-radius: <%=bottomLeft%>;
}
/* .wrapper {
	width:800px;
	height:100%;
	margin:0 30px;
} */
.editor { #
	height: 350px; #
	overflow-y: scroll;
	float: left;
	width: 55%;
}

.preview { #
	width: 80%;
	background: <%=bodyBackgroundColor%>;
	background-image: url('<%=bodyBackgroundImage%>');
	background-repeat: no-repeat;
	background-position: top;
	background-size: 100% 100%;
	padding-left: 30px;
	padding-right: 30px;
}

.layer {
	background-color: <%=contentBackgroundRgba%>;
	padding: 1px 20px;
	height: 100%;
	position: relative; <%--
	color: <%=details%>; --%>
	font-size: 12px;
}

.header {
	margin-bottom: -3px
}

.title {
	position: relative;
	color: <%=title%>;
	font-weight: 500;
	font-size: 25px;
	font-family: Rosewood Std Fill;
	width: 100%;
}

.right { #
	float: left; #
	width: 40%; #
	margin-left: 40px;
}

.bgcolor { #
	border: 1px solid #000; #
	padding: 0 20px;
	background-color: <%=bodyBackgroundColor%>;
}
/* table css */
table td {
	padding-bottom: 10px;
}

/* webkit scroll bar */
::-webkit-scrollbar {
	width: 6px;
}
/* Track */
body {
	font-family: Avenir Next;
	font-size: 14px;
	line-height: 1.42857143;
	color: #333;
}

.section-header {
	cursor: pointer;
}

#DivToShow {
	position: fixed;
	top: 11px;
	right: 5px;
}

.logoMessage {
	color: <%=title%>;
}
</style>

<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css"> -->
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/js/layoutmanage/jscolor/jscolor.js"></script>
<link href="/main/bootstrap/css/custom.css" rel="stylesheet">
<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>


<script src="/main/bootstrap/js/custom.js"></script>
<!-- <link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" /> -->
<style>
.ui-widget-overlay {
	background: #aaaaaa url(images/ui-bg_flat_0_aaaaaa_40x100.png) 50% 50%
		repeat-x;
	opacity: .3;
}

.ui-dialog .ui-dialog-titlebar {
	height: 20px; /* or whatever you want */
}

.ui-button-text {
	font-size: 15px;
}

#cb1, #cb2, #cb3, #cb4, #cb5, #fullYes, #fullNo {
	box-shadow: 0 0 0px #aaa;
}

p {
	margin: 0px;
}
</style>
<script>
$(document).ready(function(){
	
	$("#headerTextFont").val('<%=headerTextFont%>');
	$("#contentTextFont").val('<%=contentTextFont%>');
	
	var logoimageurl = '<%=logoURL%>';
	if(logoimageurl==''){
		$('.logoImage').html('');
	}else{
		$('.logoImage').html('<img src='+logoimageurl+' width="70px;" />');
	}
	var containerW='<%=bodyFullWidth%>';
	if('Y'==containerW){
		$('#removeWidth').removeClass('layer');
		$('.preview').css("padding","0px 0px");
	}
	var titleShowHide='<%=titleShowHide%>';
	  if('Y'==titleShowHide){
		 $('#titleSH').html(props.pg_hide_title);
	  }else{
		  $('#titleSH').html(props.pg_show_title);
	  }
		
});



function hexToRgb(hex) {
    var bigint = parseInt(hex, 16);
    var r = (bigint >> 16) & 255;
    var g = (bigint >> 8) & 255;
    var b = bigint & 255;
    return r + "," + g + "," + b;
}
var updateBodyBackground = null;
var updateTitle = null;
var updateContentBackground = null;
var updateDetails = null;
var updateHeader = null;
var updateHeaderText = null;
var updateBorder = null;
var updateContent = null;
var updateContentText = null;
$(function() {
	
	// header image
	/* $('#cover-photo').change(function(){
		$('.header img').attr('src',this.value);
	}); */
	
	//body background
	updateBodyBackground = function(color) {
		if($('#body-image').val().trim()=="") {
			$('.preview').css('background',$('#body-color').val());
			$('.bgcolor').css('background',$('#body-color').val());
		} else {
			$('.bgcolor').css('background',$('#body-color').val());
		}
	};
	
	$('#body-image').change(function(){
		if($(this).val().trim()=="") {
			$('.preview').css('background',$('#body-color').val());
			$('.bgcolor').css('background',$('#body-color').val());
		}
		else {
			$(".preview").css("background-image","url('"+this.value+"')");
		}
	});
	
	$('#cover-photo').change(function(){
		$('#coverPhotoId').css('background-image',"url('"+this.value+"')");
	});
	
	$('#logomsg').change(function(){
		$('.logoMessage').html($('#logomsg').val());
	});
	$('#logourl').change(function(){
	if(this.value=='')
		$('.logoImage').html('');
	else
		$('.logoImage').html('<img src='+this.value+' width="70px;" />');
	//$('.logoImage img').attr('src',this.value);
	});
	
		
	updateTitle = function(color) {
		$('.title').css('color','#'+color.toString());
	};
	updateContentBackground = function(color) {
		$('.layer').css('background-color','rgba('+hexToRgb(color.toString())+','+$('#opacity').val()+')');
	};
	
	$('#opacity').change(function(){
		var color = ($('#contentBackground').val()).substr(1,6);
		color = 'rgba('+hexToRgb(color)+','+this.value+')';
		$('.layer').css('background-color',color);
		$('#opacityText').val(this.value);
	});
	updateDetails = function(color) {
		$('.layer').css('color','#'+color.toString());
	};
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
		$('.widget-content').css('background-color','#'+color.toString());
	};
	updateContentText = function(color) {
		$('.widget-content').css('color','#'+color.toString());
	};
	
	updateTitle = function(color) {
		$('.title').css('color','#'+color.toString());
		$('.logoMessage').css('color','#'+color.toString());
	};

	updateDetails = function(color) {
		$('.layer').css('color','#'+color.toString());
	};
	
	
	
	
	
	$('#borderRadiusTopLeft').change(function(){
		$('.widget').css('-webkit-border-top-left-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-topleft',this.value+'px');
		$('.widget').css('border-top-left-radius',this.value+'px');
		$('.widget h2').css('-webkit-border-top-left-radius',this.value+'px');
		$('.widget h2').css('-moz-border-radius-topleft',this.value+'px');
		$('.widget h2').css('border-top-left-radius',this.value+'px');
		$('#borderRadiusTopLeftText').val(this.value+'px');
	});
	$('#borderRadiusTopRight').change(function(){
		$('.widget').css('-webkit-border-top-right-radius',this.value+'px');
		$('.widget').css('-moz-border-radius-topright',this.value+'px');
		$('.widget').css('border-top-right-radius',this.value+'px');
		$('.widget h2').css('-webkit-border-top-right-radius',this.value+'px');
		$('.widget h2').css('-moz-border-radius-topright',this.value+'px');
		$('.widget h2').css('border-top-right-radius',this.value+'px');
		$('#borderRadiusTopRightText').val(this.value+'px');
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
	
	$('#headerTextFont').change(function(){
		$('.widget h2').css('font-family',this.value);
	});
	$('#headerTextSize').change(function(){
		$('.widget h2').css('font-size',this.value+'px');
	});
	
	$('#contentTextFont').change(function(){
		$('.widget-content p').css('font-family',this.value);
	});
	$('#contentTextSize').change(function(){
		$('.widget-content p').css('font-size',this.value+'px');
	});
	$('.widthCheck').change(function(){
		var isFull = $('input[name=fullWidth]:checked').val();
		if('N'==isFull){
			$('#removeWidth').addClass('layer');
			$('.preview').css("padding","0px 30px");
		}else{
			$('#removeWidth').removeClass('layer');
			$('.preview').css("padding","0px 0px");
		}
	});
	
	var getGlobalStyles = function(){
		var settings = {};
		var color = ($('#contentBackground').val()).substr(1,6);
		color = 'rgba('+hexToRgb(color)+','+$('#opacity').val()+')';
		settings['contentBackground']=color;
		settings['details']='#000000';
		settings['title']=$('#title').val();
		settings['header']=$('#header').val();
		settings['headerText']=$('#headerText').val();
		settings['border']=$('#border').val();
		settings['content']=$('#content').val();
		settings['contentText']=$('#contentText').val();
		settings['headerTextSize']=$('#headerTextSize').val();
		settings['headerTextFont']=$('#headerTextFont').val();
		settings['contentTextSize']=$('#contentTextSize').val();
		settings['contentTextFont']=$('#contentTextFont').val();
		settings['fullWidth']=$('input[name=fullWidth]:checked').val();
		settings['titleShow']= $('#titleSH').attr('data');
		
		var radius = {};
		radius['topLeft']=$('#borderRadiusTopLeftText').val();
		radius['topRight']=$('#borderRadiusTopRightText').val();
		radius['bottomLeft']=$('#borderRadiusBottomLeftText').val();
		radius['bottomRight']=$('#borderRadiusBottomRightText').val();
		settings['radius']=radius;
		
		settings['bodyBackgroundColor']=$('#body-color').val();
		settings['bodyBackgroundImage']=$('#body-image').val();
		settings['coverPhoto']=$('#cover-photo').val();
		settings['backgroundPosition']=$('input[name=backgroundPosition]:checked').val();
		settings['logourl']=$('#logourl').val();
		settings['logomsg']=$('#logomsg').val();
		//console.log(settings);
		return settings;
	};
	$('#classForHoverEffect').change(function(){
		var savebutton = $(this);
		var data = getGlobalStyles();
		$('#styleData').val(JSON.stringify(data));
		var url = 'SaveSettings?type=allStyle&eid=<%=eventid%>&action=globalStyle';
		$.ajax({
			type:'POST',
			url:url,
			data:$('#styleForm').serialize(),
			success:function(response){
				if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
				if(response.status=="success") {
					setTimeout(function(){
					},1000);
				} else {
					alert(props.pg_header_server_not_received_lbl);
				}
			},
			error:function(){
				alert('error');
			}
		}); 
	});
		
		
		<%-- 
		$.getJSON('SaveSettings.jsp?type=globalStyle&eid=<%=eventid%>',{
			data:JSON.stringify(data)
		}).done(function(response){alert(response);
			if(!isValidActionMessageIFRAME(response)) return;
			if(response.status=="success") {
				setTimeout(function(){
				},1000);
			} else {
				alert(props.pg_header_server_not_received_lbl);
			}
		}); --%>
	
	<%-- $('#save').on('click',function() {
		var savebutton = $(this);
		savebutton.html('Saving...');
		savebutton.attr('disabled','disabled');
		var data = getGlobalStyles();
		$.getJSON('SaveSettings.jsp?type=globalStyle&eid=<%=eventid%>',{
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
	}); --%>
	$('#saveExit').on('click',function() {
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		var data = getGlobalStyles();
		$.getJSON('SaveSettings.jsp?type=globalStyle&eid=<%=eventid%>',{
			data:JSON.stringify(data)
		}).done(function(response){
			if(response.status=="success") {
				parent.disablePopup();
			} else {
				alert(props.pg_header_server_not_received_lbl);
				savebutton.removeAttr('disabled');
			}
		});
	});
	$('#exit').on('click',function() {
		//if(confirm("Are you sure?"))
			parent.disablePopup();
	});


	 $('#stylehelp').on('click',function(){

        $( "#dialog" ).dialog({
			modal:true,
		    width: 500,
		    height:220,
			buttons:{}
		});
		 });
	
	
	/* position cover photo accordingly */
	$('.header img').width($('.preview').width());
});




</script>

</head>
<body>
	<form id="styleForm">
		<input type="hidden" name="data" id="styleData" />
	</form>

	<div class="">
    <div class="col-sm-12 col-xs-12 col-md-12 editor ">
        <div id="classForHoverEffect">
            <span class="section-header" id="headerDiv">&nbsp;<s:text
						name="pg.header.cover.lbl" /><span id="changeIconHeader"
					class="small">&nbsp;<i
						class="glyphicon glyphicon-menu-right"></i></span></span>
            <div class="row">
                <div id="headerSettings" style="display: none;" class="background-options sm-font row">
                    <div class="col-xs-12 col-sm-5 col-md-5" style="margin-bottom: 10px">
                        <s:text name="pg.header.cover.photo.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-4 col-md-4">
                        <input class="bottom-gap form-control" id="cover-photo" value="<%=coverPhoto%>" placeholder="<s:text name="pg.header.placeholder.image.lbl" />" />
                    </div>
                    <div class=" col-xs-4 col-sm-3 col-md-3 row" style="margin-top:7px;"><s:text name="pg.or.labl"/>&nbsp;&nbsp;
                    <a id="coverImg" style="cursor:pointer"><span class="sm-font"><s:text name="pg.upload.image.lbl" /></span></a>
                    	<%-- <button class="btn btn-primary btn-xs" id="coverImg" data-target="#myModalStyle"><span class="xsm-font">Upload Image</span></button> --%>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.header.logo.url.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-4 col-md-4">
                        <input class="bottom-gap form-control" id="logourl" value="<%=logoURL%>" placeHolder="<s:text name="pg.header.logo.placeholde.url.lbl" />" />
                    </div>
                    <div class=" col-xs-4 col-sm-3 col-md-3 row" style="margin-top:7px;"><s:text name="pg.or.labl"/>&nbsp;&nbsp;
                    <a style="cursor:pointer" id="logoImg"><span class="sm-font"><s:text name="pg.upload.image.lbl" /></span></a>
                    	<%-- <button class="btn btn-primary btn-xs" id="logoImg"><span class="xsm-font">Upload Image</span></button> --%>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.header.logo.msg.lbl" />
                    </div>
                    <div class="col-xs-12 col-sm-4 col-md-4">
                        <input class="bottom-gap form-control" id="logomsg" value="<%=logoMsg%>" placeHolder="<s:text name="pg.header.logo.placeholder.msg.lbl"/>" />
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.header.color.title.lbl" />
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-1" style="margin-top:7px;">
                             <a style="cursor:pointer" id="titleSH" data="<%=titleShowHide%>"  class="sm-font"></a>         
                    </div>
                    <div class="col-xs-4 col-sm-3 col-md-2">
                        <input class="color {hash:true,onImmediateChange:'updateTitle(this);',pickerPosition:'left'} bottom-gap form-control" id="title" size="8" value="<%=title%>" placeHolder="<s:text name=" pg.header.logo.placeholder.msg.lbl "/>" />
                    </div>
                    <div style="clear:both;"></div>
                </div>
            </div>

            <div class="col-sm-12 col-xs-12 row">
                <br>
            </div>
            <span class="section-header" id="bodyDiv">&nbsp;<s:text
						name="pg.style.body.lbl" /> <span id="changeIconF" class="small">&nbsp;<i
						class="glyphicon glyphicon-menu-right"> </i></span></span>
            <div class="row">
                <div id="bodySettings" style="display: none;" class="background-options sm-font row">
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.body.bg.color.lbl" />
                    </div>
                    <div class="col-xs-5 col-sm-3 col-md-2" style="margin-bottom: 10px">
                        <input class="color {hash:true,onImmediateChange:'updateBodyBackground(this);',pickerPosition:'left'} form-control" id="body-color" size="8" value="<%=bodyBackgroundColor%>" />
                    </div>
                     <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.body.bg.img.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-4 col-md-4" style="margin-bottom: 10px">
                        <input type="text" placeholder="<s:text name="pg.enter.img.url.here"/>" value="<%=bodyBackgroundImage%>" id="body-image" class="form-control" title="Enter image URL here" />
                    </div>
                    <div class=" col-xs-4 col-sm-3 col-md-3 row" style="margin-top:7px;"><s:text name="pg.or.labl"/>&nbsp;&nbsp;
                    <a id="bodyImg" style="cursor:pointer"><span class="sm-font"><s:text name="pg.upload.image.lbl" /></span></a>
                    <%-- 	<button class="btn btn-primary btn-xs" id="bodyImg"><span class="xsm-font">Upload Image</span></button> --%>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.body.bg.position.lbl" />
                    </div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <input id="cb1" type="radio" name="backgroundPosition" value="no-repeat" <%if ( "no-repeat".equals(bodyBackgroundPosition)) out.print( "checked");%> />
                        <label for="cb1"><s:text name="pg.style.body.bg.no.repeatcenter.lbl" />
                        </label>
                        <br>
                        <input id="cb2" type="radio" name="backgroundPosition" value="repeat" <%if ( "repeat".equals(bodyBackgroundPosition)) out.print( "checked");%> />
                        <label for="cb2"><s:text name="pg.style.body.bg.repeat.center.lbl" />
                        </label>
                        <br>
                        <input id="cb3" type="radio" name="backgroundPosition" value="repeat-x" <%if ( "repeat-x".equals(bodyBackgroundPosition)) out.print( "checked");%> />
                        <label for="cb3"><s:text name="pg.style.body.bg.repeat.X.center" />
                        </label>
                        <br>
                        <input id="cb4" type="radio" name="backgroundPosition" value="repeat-y" <%if ( "repeat-y".equals(bodyBackgroundPosition)) out.print( "checked");%> />
                        <label for="cb4"><s:text name="pg.style.body.bg.repeat.Y.center" />
                        </label>
                        <br>
                        <input id="cb5" type="radio" name="backgroundPosition" value="cover" <%if ( "cover".equals(bodyBackgroundPosition)) out.print( "checked");%> />
                        <label for="cb5"><s:text name="pg.style.body.bg.fit.screen.lbl" />
                        </label>
                        <br>
                    </div>
					<div style="clear:both;"></div>

                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.body.fullWidth.lbl" />
                    </div>
                    <div class="col-xs-12 col-sm-5 col-md-5" style="margin-bottom: 10px">
                        <input class="widthCheck" id="fullYes" type="radio" name="fullWidth" value="Y" <%if ( "Y".equals(bodyFullWidth)) out.print( "checked");%> />
                        <label for="fullYes"><s:text name="pg.style.body.full.yes.lbl" />
                        </label>
                        <br>
                        <input class="widthCheck" id="fullNo" type="radio" name="fullWidth" value="N" <%if ( "N".equals(bodyFullWidth)) out.print( "checked");%> />
                        <label for="fullNo"><s:text name="pg.style.body.full.no.lbl" />
                        </label>
                    </div>
					<div style="clear:both;"></div>
                </div>
            </div>

            <div class="col-sm-12 col-xs-12 row">
                <br>
            </div>

            <span class="section-header" id="mainContentDiv">&nbsp;<s:text
						name="pg.style.main.content.lbl" /><span id="changeIconS"
					class="small">&nbsp; <i
						class="glyphicon glyphicon-menu-right"></i></span></span>
            <div class="row">
                <div id="mainContent" style="display: none;" class="background-options sm-font row">
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.main.bg.color.lbl" />
                    </div>
                    <div class="col-xs-5 col-sm-3 col-md-2" style="margin-bottom: 10px">
                        <input class="color {hash:true,onImmediateChange:'updateContentBackground(this);',pickerPosition:'left'} form-control" id="contentBackground" size="8" value="<%=contentBackgroundHex%>" />
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.main.bg.opacity.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-5 col-md-5" ><input class="form-control" type="range" id="opacity" value="<%=contentBackgroundOpacity%>" min="0" max="1" step="0.01" /></div>
					<div class="col-xs-4 col-sm-2 col-md-2"><input class="form-control" type="text" size="3" readonly="readonly" id="opacityText" value="<%=contentBackgroundOpacity%>" /></div><span style="clear:both;"></span>

                </div>
            </div>
            <div class="col-sm-12 col-xs-12 row">
                <br>
            </div>

            <span class="section-header" id="boxDiv">&nbsp;<s:text
						name="pg.style.box.lbl" /><span id="changeIconT" class="small">&nbsp;<i
						class="glyphicon glyphicon-menu-right"></i>
				</span></span>
            <div class="row">
                <div id="boxSettings" style="display: none;" class="background-options sm-font row">
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.header.color.lbl" />
                    </div>
                    <div class="col-xs-5 col-sm-3 col-md-2" style="margin-bottom: 10px">
                        <input class="color {hash:true,onImmediateChange:'updateHeader(this);',pickerPosition:'left'} form-control" id="header" size="8" value="<%=header%>" />
                    </div>
                    <div style="clear:both;"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.header.text.lbl" />
                    </div>
                    <div class="col-xs-12 col-sm-7 col-md-7">

                        <table>
                            <tr>
                                <td>
                                    <select class="form-control" id="headerTextFont" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-right-color: #fff;">
                                        <option value="Georgia"><s:text name="pg.style.box.font.georgia" />
                                        </option>
                                        <option value="Palatino Linotype"><s:text name="pg.style.box.font.palatinolinotype" />
                                        </option>
                                        <option value="Book Antiqua"><s:text name="pg.style.box.font.bookantiqua" />
                                        </option>
                                        <option value="Times New Roman"><s:text name="pg.style.box.font.timesnewroman" />
                                        </option>
                                        <option value="Arial"><s:text name="pg.style.box.font.arial" />
                                        </option>
                                        <option value="Arial Black"><s:text name="pg.style.box.font.arialblack" />
                                        </option>
                                        <option value="Helvetica"><s:text name="pg.style.box.font.helvetica" />
                                        </option>
                                        <option value="Impact"><s:text name="pg.style.box.font.impact" />
                                        </option>
                                        <option value="Lucida Sans Unicode"><s:text name="pg.style.box.font.lucidasansunicode" />
                                        </option>
                                        <option value="Tahoma"><s:text name="pg.style.box.font.tahoma" />
                                        </option>
                                        <option value="Verdana"><s:text name="pg.style.box.font.verdana" />
                                        </option>
                                        <option value="Courier New"><s:text name="pg.style.box.font.couriernew" />
                                        </option>
                                        <option value="Lucida Console"><s:text name="pg.style.box.font.lucidaconsole" />
                                        </option>
                                        <option value="initial"><s:text name="pg.style.box.font.initial" />
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="headerTextSize" style="width: 70px; border-radius: 0px;" value="<%=headerTextSize%>" min="1" max="20" /> <span class="input-group-addon" id="" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px;">px</span>
                                    </div>
                                </td>
                                <td>
                                    <input class="color {hash:true,onImmediateChange:'updateHeaderText(this);',pickerPosition:'left'} form-control" id="headerText" size="8" value="<%=headerText%>" style="height: 34px; border-bottom-left-radius: 0px; border-top-left-radius: 0px;" />
                                </td>
                            </tr>

                        </table>


                        <%-- <input class="color {hash:true,onImmediateChange:'updateHeaderText(this);',pickerPosition:'left'}" id="headerText" size="8" value="<%=headerText%>" /> --%>
                    </div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.content.color.lbl" />
                    </div>
                    <div class="col-xs-5 col-sm-3 col-md-2" style="margin-bottom: 10px">
                        <input class="color {hash:true,onImmediateChange:'updateContent(this);',pickerPosition:'left'} form-control" id="content" size="8" value="<%=content%>" />
                    </div>
                    <div style="clear:both"></div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.content.text.lbl" />
                    </div>
                    <div class="col-xs-12 col-sm-7 col-md-7">
                        <table>
                            <tr>
                                <td>
                                    <select class="form-control" id="contentTextFont" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-right-color: #fff;">
                                        <option value="Georgia"><s:text name="pg.style.box.font.georgia" />
                                        </option>
                                        <option value="Palatino Linotype"><s:text name="pg.style.box.font.palatinolinotype" />
                                        </option>
                                        <option value="Book Antiqua"><s:text name="pg.style.box.font.bookantiqua" />
                                        </option>
                                        <option value="Times New Roman"><s:text name="pg.style.box.font.timesnewroman" />
                                        </option>
                                        <option value="Arial"><s:text name="pg.style.box.font.arial" />
                                        </option>
                                        <option value="Arial Black"><s:text name="pg.style.box.font.arialblack" />
                                        </option>
                                        <option value="Helvetica"><s:text name="pg.style.box.font.helvetica" />
                                        </option>
                                        <option value="Impact"><s:text name="pg.style.box.font.impact" />
                                        </option>
                                        <option value="Lucida Sans Unicode"><s:text name="pg.style.box.font.lucidasansunicode" />
                                        </option>
                                        <option value="Tahoma"><s:text name="pg.style.box.font.tahoma" />
                                        </option>
                                        <option value="Verdana"><s:text name="pg.style.box.font.verdana" />
                                        </option>
                                        <option value="Courier New"><s:text name="pg.style.box.font.couriernew" />
                                        </option>
                                        <option value="Lucida Console"><s:text name="pg.style.box.font.lucidaconsole" />
                                        </option>
                                        <option value="initial"><s:text name="pg.style.box.font.initial" />
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="contentTextSize" style="width: 70px; border-radius: 0px;" value="<%=contentTextSize%>" min="1" max="20" /> <span class="input-group-addon" id="" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px;">px</span>
                                    </div>
                                </td>
                                <td>
                                    <input class="color {hash:true,onImmediateChange:'updateContentText(this);',pickerPosition:'left'} form-control" id="contentText" size="8" value="<%=contentText%>" style="height: 34px; border-bottom-left-radius: 0px; border-top-left-radius: 0px;" />
                                </td>
                            </tr>
                        </table>



                        <%-- <input class="color {hash:true,onImmediateChange:'updateContentText(this);',pickerPosition:'left'}" id="contentText" size="8" value="<%=contentText%>" /> --%>
                    </div>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.border.color.lbl" />
                    </div>
                    <div class="col-xs-5 col-sm-3 col-md-2" style="margin-bottom: 10px">
                        <input class="color {hash:true,onImmediateChange:'updateBorder(this);',pickerPosition:'left'} form-control" id="border" size="8" value="<%=border%>" />
                    </div>
					<div style="display:none;"></div>


                    <!-- Box Corner Radius Settings start -->

                    <label class="col-xs-12" style="margin-bottom: 10px;"><b><s:text
									name="pg.style.box.corner.rounding.lbl" /></b>
                    </label>
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.top.left.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-5 col-md-5" style="margin-bottom: 10px;">
                        <input type="range" class="form-control" id="borderRadiusTopLeft" value="<%=topLeft.substring(0, topLeft.length() - 2)%>" min="0" max="35" />
                    </div>
                    <div class="col-xs-4 col-sm-2 col-md-2">
                        <input type="text" class="form-control" size="4" readonly="readonly" id="borderRadiusTopLeftText" value="<%=topLeft%>" />
                    </div>
                    
                   
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.top.right.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-5 col-md-5" style="margin-bottom: 10px;">
                        <input type="range" class="form-control" id="borderRadiusTopRight" value="<%=topRight.substring(0, topRight.length() - 2)%>" min="0" max="35" />
                    </div>
                    <div class="col-xs-4 col-sm-2 col-md-2">
                        <input type="text" class="form-control" size="4" readonly="readonly" id="borderRadiusTopRightText" value="<%=topRight%>" />
                    </div>
                    
                    
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.bottom.left.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-5 col-md-5" style="margin-bottom: 10px;">
                        <input type="range" class="form-control" id="borderRadiusBottomLeft" value="<%=bottomLeft.substring(0, bottomLeft.length() - 2)%>" min="0" max="35" />
                    </div>
                    <div class="col-xs-4 col-sm-2 col-md-2">
                        <input type="text" class="form-control" size="4" readonly="readonly" id="borderRadiusBottomLeftText" value="<%=bottomLeft%>" />
                    </div>
                    
                    
                    <div class="col-xs-12 col-sm-5 col-md-5">
                        <s:text name="pg.style.box.bottom.right.lbl" />
                    </div>
                    <div class="col-xs-8 col-sm-5 col-md-5">
                        <input type="range" class="form-control" id="borderRadiusBottomRight" value="<%=bottomRight.substring(0, bottomRight.length() - 2)%>" min="0" max="35" />
                    </div>
                    <div class="col-xs-4 col-sm-2 col-md-2">
                        <input type="text" class="form-control" size="4" readonly="readonly" id="borderRadiusBottomRightText" value="<%=bottomRight%>" />
                    </div>

                </div>
            </div>
            <div class="col-sm-12 col-xs-12 row">
                <br>
            </div>




            <!-- <div class="col-xs-12 col-sm-12"><button id="save" class="btn btn-primary">Save</button></div> -->

        </div>
    </div>
    <div class="col-sm-4 col-xs-4 col-md-4 text-center" id="DivToShow" Style="display:none;">
        <button id="previewPageBtn" class="btn btn-primary"><s:text name="global.preview.lnk.lbl" />
        </button>
        <br>
        <button id="closepreviewBtn" class="btn btn-primary" style="display:none; margin-top: 3px;"><s:text name="pg.close.preview.lbl" />
        </button>
    </div>



    <!-- this feature closed temporarily (instant preview) -->
    <div class="col-sm-6 col-xs-12 col-md-6" style="display:none;">
        <!-- <span><h2>Preview</h2></span> -->
        <div id="">
            <div class="right">
                <div class="bgcolor">
                    <div class="preview">
                        <%-- <div class="header">
                            <img src="<%=coverPhoto%>" />
                            <div class="title">&nbsp;Event title</div>
                    </div> --%>

                    <div class="layer" style="padding: 2px 20px;" id="removeWidth">
                        <!-- Time, date, place... -->
                        <div class="good" style="width: 100%; height: 80px;">
                            <div id="coverPhotoId" class="col-md-12 col-sm-12 col-xs-12" style=" background-size:cover;background-image: url('<%=coverPhoto%>');min-height:80px">
                                <div class="row">
                                    <div class="col-md-6 col-sm-6 col-xs-12" style="position: absolute; bottom: 0px">
                                        <div class="title"> <s:text name="pg.header.title.event.lbl" /> </div>
                                    </div>
                                    <div style="clear: both;"></div>
                                    <div class="col-md-6 pull-right" style="float: right">
                                        <div class="logoImg">
                                            <div class="text-right logoImage"></div>
                                        </div>
                                        <div class="logoMsg">
                                            <div class="text-right logoMessage">
                                                <%=logoMsg%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="clear: both;"></div>
                        <hr>
                        <div class="widget">
                            <h2>&nbsp;<s:text name="pg.style.box.header.lbl" /></h2>
                            <div class="widget-content">
                                <p><s:text name="pg.style.box.text.goes.lbl" />
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br> <span style="font-size: 11px;"> <s:text name="pg.style.box.note.msg" /><br>
					</span>
        </div>
        <div style="clear: both; height: 10px"></div>
        <div id="dialog" style="display: none" title="Box Settings">
            <p>Settings made here will be applicable to all boxes of the event page. To configure a particular box with diffrent settings use ' Look & Feel ' of that box in the main Layout Page.</p>
        </div>
    </div>
</div>
<div id="DivToShowHide"></div>
</div>


<div class="modal fade" id="myModalStyle" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><b><s:text name="pg.upload.image.lbl" /></b></h4>
        </div>
        <div class="modal-body">
          <iframe src="" style="border:none; width:100%;" height="90px;" id="imageUploadIframe"></iframe>
        </div>
        <div class="modal-footer" style="display:none;">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> 
        </div>
      </div>
      
    </div>
  </div>
  



</body>
<script>
	var photoID = '';
	var openTime = 900;

	function setWebPath(fullPath,uploadurl,photoname){
		$('#myModalStyle').modal('hide');
		if('cover'==photoID){
			$('#cover-photo').val(fullPath);
		}else if('logo'==photoID){
			$('#logourl').val(fullPath);
		}else if('body'==photoID){
			$('#body-image').val(fullPath);
		}
		$("#classForHoverEffect").trigger("change");
	};
	function loadHeaedr(){
		$('#headerSettings').slideDown();
		$('#headerDiv').find('.glyphicon').addClass('down').removeClass('original');
		parent.setIframeHeight();
	}
$(function() {
	loadHeaedr();
	$('#coverImg').click(function(){
		$('#imageUploadIframe').attr('src','/main/membertasks/ImageUpload?type=cover');
		photoID='cover';
		$('#myModalStyle').modal();
		$('.modal-backdrop').css("background-color","transparent");
	});
	$('#logoImg').click(function(){
		$('#imageUploadIframe').attr('src','/main/membertasks/ImageUpload?type=logo');
		photoID='logo';
		$('#myModalStyle').modal();
		$('.modal-backdrop').css("background-color","transparent");
	});
	$('#bodyImg').click(function(){
		$('#imageUploadIframe').attr('src','/main/membertasks/ImageUpload?type=body');
		photoID='body';
		$('#myModalStyle').modal();
		$('.modal-backdrop').css("background-color","transparent");
	});
	
	
	$('#titleSH').click(function(){
		if('Y'==$(this).attr('data')){
			$('#titleSH').html(props.pg_show_title);
			$('#titleSH').attr('data','N');
		}else{
			$('#titleSH').html(props.pg_hide_title);
			$('#titleSH').attr('data','Y');
		}
		$("#classForHoverEffect").trigger("change");
	});
	
	

	
	$('#bodyDiv').click(function() {
		if($('#bodySettings').is(':hidden')){
			$('#bodySettings').slideDown(openTime);
			//$('#mainContent').slideUp();
			//$('#boxSettings').slideUp();
			$(this).find('.glyphicon').addClass('down').removeClass('original');
			//$("#changeIconS").find('.glyphicon').addClass('original').removeClass('down');
			//$("#changeIconT").find('.glyphicon').addClass('original').removeClass('down');
			parent.setIframeHeight();
		}else{
			$('#bodySettings').slideUp(openTime);
			$(this).find('.glyphicon').addClass('original').removeClass('down');
			parent.decIframeHeight('bodyDiv');
		}
		
	});
	$('#mainContentDiv').click(function(){
		if($('#mainContent').is(':hidden')){
			$('#mainContent').slideDown(openTime);
			//$('#bodySettings').slideUp();
			//$('#boxSettings').slideUp();
			$(this).find('.glyphicon').addClass('down').removeClass('original');
			parent.setIframeHeight();
			//$("#changeIconF").find('.glyphicon').addClass('original').removeClass('down');
			//$("#changeIconT").find('.glyphicon').addClass('original').removeClass('down');
		}else{
			$('#mainContent').slideUp(openTime);
			$(this).find('.glyphicon').addClass('original').removeClass('down');
			parent.decIframeHeight('mainContentDiv');
		}
		
	});
	$('#boxDiv').click(function(){
		if($('#boxSettings').is(':hidden')){
			$('#boxSettings').slideDown(openTime);
			//$('#mainContent').slideUp();
			//$('#bodySettings').slideUp();
			$(this).find('.glyphicon').addClass('down').removeClass('original');
			//$("#changeIconF").find('.glyphicon').addClass('original').removeClass('down');
			//$("#changeIconS").find('.glyphicon').addClass('original').removeClass('down');
			parent.setIframeHeight();
		}else{
			$('#boxSettings').slideUp(openTime);
			$(this).find('.glyphicon').addClass('original').removeClass('down');
			parent.decIframeHeight('boxDiv');
		}
		//parent.setIframeHeight();
	});
	$('#headerDiv').click(function(){
		if($('#headerSettings').is(':hidden')){
			$('#headerSettings').slideDown(openTime);
			$(this).find('.glyphicon').addClass('down').removeClass('original');	
			parent.setIframeHeight();
		}
		else{
		$('#headerSettings').slideUp(openTime);
		$(this).find('.glyphicon').addClass('original').removeClass('down');
		parent.decIframeHeight('headerDiv');
		
	}
		
		});
		
		<%-- setInterval(function(){
			if(parent.$('#previewPage').is(":visible")){
				$('#previewPageBtn').html(props.pg_refresh_preview_lbl);
				$('#closepreviewBtn').show();
			}else if(!iframeLoading){
				$('#closepreviewBtn').hide();
				$('#previewPageBtn').html(props.globa_preview);
			}
			}, 1000);

		var iframeLoading = false;
		var iframeloadSRC = false;
		$('#previewPageBtn').click(function(){
			parent.animateDIV();
			iframeLoading= true;
			iframeloadSRC = true;
			parent.$('#previewPage').hide();
			parent.$('#loadingpreview').show();
	        parent.$('#previewPage').attr('src', '/event?eid=<%=eventid%>&preview=draft');
			$('#previewPageBtn').html(props.pg_refresh_preview_lbl);
			loadIframe();
		});
		function loadIframe(){
			parent.$('#previewPage').load(function(){
				parent.$(this).show();
				parent.$('#loadingpreview').hide();
             });
        }
		$('#closepreviewBtn').click(function(){
			$(this).hide();
			iframeloadSRC = false;
			parent.$('#previewPage').hide();
			$('#previewPageBtn').html(props.globa_preview);
		}); --%>
	
	
    var $sidebar   = $("#DivToShow"), 
        $window    = $(window.parent.document),
        offset     = $sidebar.offset(),
        topPadding = 15;

    $window.scroll(function() {
        if ($window.scrollTop() > offset.top) {
            $sidebar.css({
                marginTop: $window.scrollTop() - offset.top + topPadding
            });
        } else {
        	 $sidebar.css({
                 marginTop: 0
             });
        }
    });
    
});
</script>
</html>
