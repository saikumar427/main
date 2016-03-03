<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
<style>
.section-main-header{
	color: #333;
    font-size: 18px;
    margin-bottom: 9px;
}
.right{
	float: left;
	width:40%;
	margin-left:40px;
}
.bgcolor {
	border: 1px solid #e5e5e5;
	#padding:0 20px;
	background-color: <%=bodyBackgroundColor%>;
}
.preview {
	background: <%=bodyBackgroundColor%>;
	background-image: url('<%=bodyBackgroundImage%>');
	background-repeat:no-repeat;
	background-position:top;
	background-size:100% 100%;
	padding:0px 30px;

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
	padding-top:10px;
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
<head>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/js/layoutmanage/jscolor/jscolor.js"></script>
<script src="/main/bootstrap/js/custom.js" ></script>
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>

<script>
var eid= '<%=eventid%>';
</script>
</head>
<body>
<div class="col-md-12 col-sm-12 col-xs-12">
	<div class="tab-bottom-gap">
		<div class="center btn-group btn-toggle" data-toggle="buttons">
			<label id="chooseHthemeBtn"
				class="optiontype btn btn-default no-radius"> <input
				class="datesel" id="firstBtn" name="events" value="1" type="radio"><s:text name="pg.header.choose.theme.lbl" />
			</label> 
			<label id="headerSettingsBtn"
				class="optiontype btn btn-active no-radius"> <input
				class="datesel" id="secondBtn" name="events" value="2" type="radio"><s:text name="pg.header.settings.lbl" />
			</label> 
			<label id="myHeaderThemeBtn"
				class="optiontype btn btn-active no-radius"> <input
				class="datesel" id="thirdBtn" name="events" value="3" type="radio"><s:text name="pg.header.mysettings.lbl" />
			</label>
		</div>
	</div>
</div><div style="clear:both;"></div>
<br>
 <div id="onLoad" class="text-center" style="display:none;"><img src="/main/images/ajax-loader.gif"></div>
<div class="col-md-12 col-xs-12 col-sm-12 row" id="chooseHeaderTheme" style="display:none;"><div style="clear:both;"></div></div><div style="clear:both;"></div>

<div class="col-md-12 col-xs-12 col-sm-12 row" id="headerSettings" style="display:none;">

	<div class="col-md-12 col-sm-12 col-xs-12">
	
	<div class="col-md-6 col-sm-6 col-xs-12 ">
	<span class="section-main-header"><b><s:text name="pg.header.settings.lbl" /></b></span><div style="clear:both;margin-top: 10px;"></div>
		<div class="col-md-6 col-sm-6 col-xs-12 form-group"><s:text name="pg.header.cover.photo.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=coverPhoto%>" placeholder="<s:text name="pg.header.placeholder.image.lbl" />" id="cover-photo"/>
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 form-group"><s:text name="pg.header.logo.url.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=logoURL%>" placeHolder="<s:text name="pg.header.logo.placeholde.url.lbl" />" id="logourl">
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 form-group"><s:text name="pg.header.logo.msg.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=logoMsg%>" placeHolder="<s:text name="pg.header.logo.placeholder.msg.lbl" />" id="logomsg">
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 form-group"><s:text name="pg.header.color.title.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input class="color {hash:true,onImmediateChange:'updateTitle(this);',pickerPosition:'left'}" id="title" size="8" value="<%=title%>"/>
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 form-group"><s:text name="pg.header.details.text.color.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input class="color {hash:true,onImmediateChange:'updateDetails(this);',pickerPosition:'left'}" id="details" size="8" value="<%=details%>"/>
		</div><div style="clear:both;"></div>
	</div>
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="section-main-header"><b><s:text name="pg.header.preview.lbl" /></b></div>
		<div >
			<div class="bgcolor">
				<div class="preview">
					<div class="header">
						<img src="<%=coverPhoto%>" style="width:100%" />
						<div class="title">&nbsp;<s:text name="pg.header.title.event.lbl" /></div>
					</div>
					<div class="layer">
						<s:text name="pg.header.time.date.lbls" />
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

<div style="clear: both;"></div>
	<div class="text-center" style="margin-top: 15px;">
		<button class="btn btn-primary" id="save"><s:text name="pg.widgets.save" /></button>
		<button class="btn btn-primary" id="saveExit"><s:text name="pg.widgets.save.exit" /></button>
		<button class="btn btn-primary" id="exit"><s:text name="pg.widgets.exit.withoutsave.lbl" /></button>
	</div> 


<div style="clear:both;"></div></div><div style="clear:both;"></div>

<div class="col-md-12 col-xs-12 col-sm-12 row" id="myHeadertheme" style="display:none;"><div style="clear:both;"></div></div><div style="clear:both;"></div>
</body>
<script>
	
	$(document).ready(function(){
		 $('#chooseHeaderTheme').show();
		loadChooseHeadertheme();
		
		
		$("#chooseHthemeBtn").click(function(){
			/* $('#headerSettingsBtn').attr('disabled',true);
			$('#myHeaderThemeBtn').attr('disabled',true); */
			if(!$(this).hasClass("btn-default")){
				
				$('#chooseHeaderTheme').show();
				$('#headerSettings').hide();
				$('#myHeadertheme').html('').hide();
				$("#chooseHthemeBtn").addClass("btn-default").removeClass("btn-active");
				$("#headerSettingsBtn").removeClass("btn-default").addClass("btn-active");
				$('#myHeaderThemeBtn').removeClass("btn-default").addClass("btn-active");
		   		loadChooseHeadertheme();
			}
		 });
		$("#headerSettingsBtn").click(function(){ 
			/* $('#chooseHthemeBtn').attr('disabled',true);
			$('#myHeaderThemeBtn').attr('disabled',true); */
			if(!$(this).hasClass("btn-default")){
				$('#onLoad').show();
				$('#headerSettings').show();
				$('#chooseHeaderTheme').html('').hide();
				$('#myHeadertheme').html('').hide();
				$("#headerSettingsBtn").addClass("btn-default").removeClass("btn-active");
				$("#chooseHthemeBtn").removeClass("btn-default").addClass("btn-active");
				$('#myHeaderThemeBtn').removeClass("btn-default").addClass("btn-active");
				loadHeaderSettings();
			}
		 });
		
		$("#myHeaderThemeBtn").click(function(){ 
			/* $('#chooseHthemeBtn').attr('disabled',true);
			$('#headerSettingsBtn').attr('disabled',true); */
			if(!$(this).hasClass("btn-default")){
				$('#onLoad').show();
				$('#myHeadertheme').show();
				$('#chooseHeaderTheme').html('').hide();
				$('#headerSettings').hide();
				$("#myHeaderThemeBtn").addClass("btn-default").removeClass("btn-active");
				$("#chooseHthemeBtn").removeClass("btn-default").addClass("btn-active");
				$("#headerSettingsBtn").removeClass("btn-default").addClass("btn-active");
				loadMyHeaderTheme();
			}
		 });
	});
	var url = '';
	
	var loadChooseHeadertheme = function(){
		$('#onLoad').show();
		url='/main/eventmanage/layoutmanage/ChooseHeaderTheme?eid='+eid;
		$.ajax({
			type:'POST',
			url:url,
				success:function(result){
					if(!isValidActionMessageIFRAME(result)) return;
					$('#onLoad').hide();
					$('#chooseHeaderTheme').html(result);
					/* $('#headerSettingsBtn').attr('disabled',false);
					$('#myHeaderThemeBtn').attr('disabled',false); */
				},
				error:function(){
					alert(props.pg_header_chooseheader_error_msg);
				}
		});
		
	}
	var loadHeaderSettings = function(){
		$('#onLoad').hide();
	};
/* 	 var loadHeaderSettings = function(){
		url = '/main/eventmanage/layoutmanage/HeaderSettings?eid='+eid;
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				$('#onLoad').hide();
				$('#headerSettings').html(result);
				
				 $('#chooseHthemeBtn').attr('disabled',false);
				$('#myHeaderThemeBtn').attr('disabled',false); 
			},
			error:function(){
				alert('Error while getting HeaderSettings');
			}
		});
	} */
	var loadMyHeaderTheme = function(){
		url = '/main/eventmanage/layoutmanage/MyHeaderTheme?eid='+eid;
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				if(!isValidActionMessageIFRAME(result)) return;
				$('#onLoad').hide();
				$('#myHeadertheme').html(result);
				/* $('#chooseHthemeBtn').attr('disabled',false);
				$('#headerSettingsBtn').attr('disabled',false); */
			},
			error:function(){
				alert(props.pg_header_myheader_error_msg);
			}
		});
	}
</script>
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
	savebutton.html(props.pg_widgets_saving_lbl);
	savebutton.attr('disabled','disabled');
	var data = getGlobalStyles();
	var dataObject = {};
	dataObject['type'] = 'HeaderSettings';
	dataObject['eid'] = '<%=eventid%>';
	dataObject['action'] = 'SaveSettings';
	dataObject['data']=JSON.stringify(data);
	$.ajax({
		type:'POST',
		url:'SaveSettings',
		data:dataObject,
		success:function(response){
			if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
			if(response.status=="success"){
				savebutton.html(props.pg_header_changes_saved_lbl);
				setTimeout(function(){
					savebutton.removeAttr('disabled');
					savebutton.html(props.pg_widgets_save_lbl);
				},1000);
			}
		},
		error:function(){
			alert(props.pg_header_server_not_received_lbl);
			savebutton.removeAttr('disabled');
		}
	});
	
});
$('#saveExit').on('click',function() {
	var savebutton = $(this);
	savebutton.html(props.pg_widgets_saving_lbl);
	savebutton.attr('disabled','disabled');
	var data = getGlobalStyles();
	var saveExitData = {};
	saveExitData['type'] = 'HeaderSettings';
	saveExitData['eid'] = '<%=eventid%>';
	saveExitData['action'] = 'SaveSettings';
	saveExitData['data']=JSON.stringify(data);
	$.ajax({
		type:'POST',
		url:'SaveSettings',
		data:saveExitData,
		success:function(response){
			if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
			if(response.status=="success")
				parent.disablePopup();
		},
		error:function(){
			alert(props.pg_header_server_not_received_lbl);
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
</html>