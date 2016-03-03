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
	

}

.title {
	position: relative;
	color:<%=title%>;
    font-weight: 500;
    font-size: 30px;
    font-family: Rosewood Std Fill;
}
.logoMessage{
	color:<%=title%>;
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
.bottom-gap{
	margin-bottom:10px;
}
/* .logoimg{
	position: relative;
    top: -120px;
    left: -10px;
} */

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
	
	<div class="col-md-6 col-sm-6 col-xs-12 row form-horizontal">
	<%-- <span class="section-main-header"><b><s:text name="pg.header.settings.lbl" /></b></span><div style="clear:both;margin-top: 10px;"></div> --%>
		<div class="col-md-6 col-sm-6 col-xs-12 control-label"><s:text name="pg.header.cover.photo.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=coverPhoto%>" placeholder="<s:text name="pg.header.placeholder.image.lbl" />" id="cover-photo" class="bottom-gap"/>
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 control-label"><s:text name="pg.header.logo.url.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=logoURL%>" placeHolder="<s:text name="pg.header.logo.placeholde.url.lbl" />" id="logourl" class="bottom-gap">
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 control-label"><s:text name="pg.header.logo.msg.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" value="<%=logoMsg%>" placeHolder="<s:text name="pg.header.logo.placeholder.msg.lbl" />" id="logomsg" class="bottom-gap">
		</div><div style="clear:both;"></div>
		
		<div class="col-md-6 col-sm-6 col-xs-12 control-label"><s:text name="pg.header.color.title.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input class="color {hash:true,onImmediateChange:'updateTitle(this);',pickerPosition:'left'} bottom-gap" id="title" size="8" value="<%=title%>" />
		</div><div style="clear:both;"></div>
		
		<div style="display:none;">
		
		<div class="col-md-6 col-sm-6 col-xs-12 control-label"><s:text name="pg.header.details.text.color.lbl" /></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input class="color {hash:true,onImmediateChange:'updateDetails(this);',pickerPosition:'left'} " id="details" size="8" value="<%=details%>"/>
		</div><div style="clear:both;"></div> 
		
		</div>
		
	</div>
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="section-main-header"><b><s:text name="pg.header.preview.lbl" /></b></div>
			<%-- <div class="header">
			<img src="<%=coverPhoto%>" style="width:100%" />
			</div> --%>
			<div id="coverPhotoId" class="col-md-12 col-sm-12 col-xs-12" style=" background-size:cover;background-image: url('<%=coverPhoto%>');min-height:80px">
				<div class="row" >
				<div class="col-md-6 col-sm-6 col-xs-12" style="position:absolute;bottom:0px">
					<div class="title"><s:text name="pg.header.title.event.lbl" /></div>
				</div>
				<div class="pull-right" style="float:right">
					<div class="logoImg">
						<div class="text-right logoImage"></div>
					</div>
					<div class="logoMsg">
						<div class="text-right logoMessage" ><%=logoMsg%></div>
					</div>
				</div>
				
				</div>
			</div>
			
						
			<%-- <div class="bgcolor">
				<div class="preview">
					<div class="header">
						<img src="<%=coverPhoto%>" style="width:100%" />
						<div class="title">&nbsp;<s:text name="pg.header.title.event.lbl" /></div>
						<div class="pull-right"><img src="http://stylonica.com/wp-content/uploads/2014/02/2668.jpg" width="100px;" class="logoimg"/></div>
						<div class="pull-right"><span class="logoMsg">hi</span></div>
						
					</div>
				</div>
			</div> --%>
	</div>
</div>

<div style="clear: both;"></div>
	<div class="text-center" style="margin-top: 15px;">
		<button class="btn btn-primary" id="save"><s:text name="pg.widgets.save" /></button>
		<button class="btn btn-primary" id="saveExit" style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
		<button class="btn btn-cancel" id="exit"><i class="fa fa-times"></i></button>
	</div> 


<div style="clear:both;"></div><div style="clear:both;"></div>

<div class="col-md-12 col-xs-12 col-sm-12 row" id="myHeadertheme" style="display:none;"><div style="clear:both;"></div></div><div style="clear:both;"></div>
</body>

<script>
var updateTitle = null;
var updateDetails = null;
$(function() {

	var logophoto ='<%=logoURL%>';
	if(logophoto=='')
		$('.logoImage').html('');
	else
		$('.logoImage').html('<img src='+logophoto+' width="100px;" />');
	
	
	$('#cover-photo').change(function(){
		$('#coverPhotoId').css('background-image',"url('"+this.value+"')");
	});
	
	
	updateTitle = function(color) {
		$('.title').css('color','#'+color.toString());
		$('.logoMessage').css('color','#'+color.toString());
	};

	updateDetails = function(color) {
		$('.layer').css('color','#'+color.toString());
	};
	
	$('#logomsg').change(function(){
			$('.logoMessage').html($('#logomsg').val());
	});
	
	$('#logourl').change(function(){
		if(this.value=='')
			$('.logoImage').html('');
		else
			$('.logoImage').html('<img src='+this.value+' width="100px;" />');
		//$('.logoImage img').attr('src',this.value);
	});

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
				parent.notification(props.widgets_status_msg,"success");
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