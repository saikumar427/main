<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String reset = request.getParameter("reset");
String layout_type = request.getParameter("layout_type");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?BuyerAttHelper.getWidgetOptions("0", widgetid,layout_type):BuyerAttHelper.getWidgetOptions(eventid, widgetid,layout_type);
if(data==null || "".equals(data.trim()))data="{}";
JSONObject jsonData = new JSONObject(data);
String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type); 
String ref_tTitle =widgetTitle;//DBHelper.getRefTitle(eventid,widgetid);

%>
<!DOCTYPE html>
<html>
<head>
<title><%=widgetTitle%></title>

<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>
<style>
.sm-font{
font-size: 12px !important;
}

</style>
<script>
$(function(){

	var getData = function(){
		var data = {};
		data["url"] = $('#url').val();
		data["link"] = $('#link').val();
		return JSON.stringify(data);
	};
	var getTitle=function(){
		var title=$('#photott').val();
		return title;
	};
	var saveRefTitle=function(savebutton){
		var title=$('#retxttt').val();		
		$.getJSON('SaveSettings?type=saveRefTiltleBA&eid=<%=eventid%>&widgetid=<%=widgetid%>&layout_type=<%=layout_type%>',{
			data:parent.convert(getData()),title:parent.convert(title)
	
		}).done(function(response){		
			
			parent.changeTitle('<%=widgetid%>',title);
			
				if(savebutton.attr('id').indexOf('Exit')>-1)			
				parent.disablePopup();
				else{
					savebutton.html(props.pg_widgets_save_lbl);
					savebutton.removeAttr('disabled');
				  }
	    });
	  return "";
	}
	


	$('.saveButton').click(function(){
		if($('#retxttt').val()==''){alert("Widget reference title should not be empty "); return ;}
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		var data = getData();
		$('#data').val(data);
		var url = "SaveSettings?type=BuyerAttOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&action=SaveSettings&layout_type=<%=layout_type%>";
		$.ajax({
			type:'POST',
			url:url,
			data:$('#photoForm').serialize(),
			success:function(response){
				if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
				if(response.status=="success") {
					parent.notification(props.widgets_status_msg,"success");
					
					saveRefTitle(savebutton);
					
				} else {
					alert(props.pg_widgets_not_respond);
					savebutton.removeAttr('disabled');
				}
			},
			error:function(){
				//alert('no');
			}
		});
	});

	$('#exit').on('click',function() {
			parent.disablePopup();
	});
});
</script>
</head>
<body>
<form id="photoForm">
<input type="hidden" name="data" id="data" />
<%-- <h3>Widget Options : <%=ref_tTitle%></h3>
<hr><br> --%>
<div class="col-md-12 col-sm-12 col-xs-12">
	<div class="col-md-3 col-sm-3 col-xs-12 control-label">
		<label><s:text name="pg.widgets.ref.title" /></label>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input type="text" name="reftitle" value="<%=ref_tTitle%>" id="retxttt" />
	</div>

	<div class="col-md-3 col-sm-3 col-xs-12 control-label">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input type="text" name="title" value="<%=title%>" id="photott"/>
	</div>
	
	<div class="col-md-3 col-sm-3 col-xs-12 control-label">
		<label><s:text name="pg.photo.url" /></label><br>
		<span class="sm-font"><s:text name="pg.photo.note" /></span>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input value="<%=jsonData.has("url")?jsonData.getString("url"):""%>" type="text" id="url" />
		&nbsp;<s:text name="pg.or.labl"/>&nbsp;
		<a id="coverImg" style="cursor:pointer"><span class="sm-font"><s:text name="pg.upload.image.lbl" /></span></a>
	</div>
	<div style="clear:both;"></div>
	<div class="col-md-9 col-sm-9 col-xs-12" style="margin:10px 0px;  display:none; border-bottom:1px solid #ddd;"  id="imageUploadIframe">
		<iframe src="" style="border:none;  width:100%; height:60px;" id="frameID"></iframe>
	</div>
	<div style="clear:both;"></div>
	<div class="col-md-3 col-sm-3 col-xs-12 control-label">
		<label><s:text name="pg.photo.ex.link" /></label><br>
		<span class="sm-font"><s:text name="pg.photo.ex.note" /></span>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input value="<%=jsonData.has("link")?jsonData.getString("link"):""%>" type="text" id="link" />
	</div>
	
	
</div>


<!-- <div id="dialog" style="display:none" title="Alert"><p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>All changes will be lost. Are you sure?</p></div>
<br> -->
</form>

<div class="col-md-12 copl-sm-12 col-xs-12 text-center">
		<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
	<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
	<button id="exit" class='btn btn-cancel'><i class="fa fa-times"></i></button>
	<button id="reset" class='btn btn-primary' style="display:none;"><s:text name="pg.widgets.reset" /></button>
	</div>
	
	<script>
	$('#reset').click(function(){
		bootbox.confirm(props.pg_widgets_change_lbl, function (result) {
	        if(result){
	        	var ref=window.location.href;
				if(ref.indexOf("&reset=reset")>-1){}else
				ref=ref+"&reset=reset";
				window.location.href=ref;  
	        }
	        	
	       });
	});
		
	$('#coverImg').click(function(){
		if($('#imageUploadIframe').is( ":hidden" )){
			$('#frameID').attr('src','/main/membertasks/ImageUpload?type=photoWidget')
			$('#imageUploadIframe').slideDown();
		}else{
			$('#imageUploadIframe').slideUp();
		}
			
	});
	function setWebPath(fullPath,uploadurl,photoname){
		$('#imageUploadIframe').slideUp();
		$('#url').val(fullPath);
	};
	setInterval(function(){
		var obj = document.getElementById('frameID');
		obj.style.height = obj.contentWindow.document.body.scrollHeight+'px';
		},900);
		
</script>
	
</body>
</html>