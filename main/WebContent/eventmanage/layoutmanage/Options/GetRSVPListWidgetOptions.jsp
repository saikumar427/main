<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?DBHelper.getWidgetOptions("0", widgetid):DBHelper.getWidgetOptions(eventid, widgetid);
String title = DBHelper.getTitle(eventid, widgetid);
String fbeventiddata="";
try{
	JSONObject fbrsvpjson=new JSONObject(data);
	fbeventiddata=fbrsvpjson.getString("fbeventid");
}catch(Exception e){System.out.println("when  json parsing error"+e.getMessage());}
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
<script>
$(function(){

	var getData = function(){
		var data={};
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
        data.chkflds=val;	
		data.fbeventid=$('#fbeventid').val();
		return JSON.stringify(data);

	};
	
	var getTitle = function(){
		return $('#rsvplistwidget').val();
	}
	

		var reset=function(){
			var ref=window.location.href;
			if(ref.indexOf("&reset=reset")>-1){}else
			ref=ref+"&reset=reset";
			window.location.href=ref; 
		}

		$('.saveButton').click(function(){
			$('.alert-danger').slideUp();
			var savebutton = $(this);
			savebutton.html(props.pg_widgets_saving_lbl);
			savebutton.attr('disabled','disabled');
			var data = getData();
			$('#data').val(data);
			var url = "SaveSettings?type=widgetOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=<%=widgetTitle%>&action=SaveSettings";
			if($('input.checkbox_check').is(':checked')){
				var textValue = $('#fbeventid').val();
				if(textValue == null || textValue == ""){
					$('.alert-danger').slideDown();
					savebutton.html(props.pg_widgets_save_lbl);
					savebutton.removeAttr('disabled');
					return;
				}
			}
			$.ajax({
				type:'POST',
				url:url,
				data:$('#getrsvpform').serialize(),
				success:function(response){
					if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
					if(response.status=="success") {
						parent.notification(props.widgets_status_msg,"success");
						
						if(savebutton.attr('id').indexOf('Exit')>-1)			
							parent.disablePopup();
							else{
								savebutton.html(props.pg_widgets_save_lbl);
								savebutton.removeAttr('disabled');
							  }
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




	
	$('.savebtn').on('click',function(){
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		$.getJSON('SaveSettings?type=widgetOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=RSVP List',{
			data: parent.convert(getData()),
			title: parent.convert(getTitle())
		}).done(function(response){
			if(response.status=="success") {
					if(savebutton.attr('id').indexOf('Exit')>-1)			
					parent.disablePopup();
					else{
						savebutton.html(props.pg_widgets_save_lbl);
						savebutton.removeAttr('disabled');
					  }
			} else {
				alert(props.pg_widgets_not_respond);
				savebutton.removeAttr('disabled');
			}
		});
	});
	$('#exit').on('click',function() {
		//if(confirm("All changes will be lost. Are you sure?"))
			parent.disablePopup();
			//window.close();
	});
});
</script>
</head>
<body>
<form id="getrsvpform">
<input type="hidden" name="data" id="data" />
<div class="alert alert-danger" style="display:none;"><s:text name="pg.facebook.not.empty.lbl"/></div>
<div class="col-md-12 col-sm-12 col-xs-12 ">
	<div class="col-md-2 col-sm-2 col-xs-12 ">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input type="text" id="rsvplistwidget" name="title" value="<%=title%>" size="30">
	</div>
	
	<div class="col-md-5 col-sm-5 col-xs-12 ">
		<input class="checkbox_check" name="fbrsvplist" type="checkbox" value='fbatt' <%if(data.indexOf("fbatt")>-1)out.print("checked='checked'");%> />
		<span style="margin-left:5px;"><s:text name="pg.rsvp.fb.id" /></span>
	</div>
	<div class="col-md-7 col-sm-7 col-xs-12 form-group showDiv" style="display:none;">
		<input value="<%=fbeventiddata%>" type="text" id="fbeventid" size="30"/>
	</div>

	<div class="col-md-12 col-sm-12 col-xs-12 form-group" style="display:none;">
		<input name="fbrsvplist" type="checkbox" value='fbmfcount' <%if(data.indexOf("fbmfcount")>-1)out.print("checked='checked'");%>  /><s:text name="pg.rsvp.count.male.female" />
	</div>
	</div>
	</form>
	<div class="col-md-12 col-sm-12 co-xs-12 text-center">
		<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
		<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
		<button id="exit" class="btn btn-cancel"><i class="fa fa-times"></i></button>
		<button id="reset" class="btn btn-primary" style="display:none;"><s:text name="pg.widgets.reset" /></button>
	</div>
</body>
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



$('.checkbox_check').change(function(){
	if($('input.checkbox_check').is(':checked')){
		$('.showDiv').show();
	}else{
		$('.showDiv').hide();
		$('#fbeventid').val('');
	}
	
});
if($('input.checkbox_check').is(':checked')){
	$('.showDiv').show();
}

	
</script>
</html> 