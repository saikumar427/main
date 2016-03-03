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
String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type);
JSONObject jsonData;
try{
	/* briefmessage=labels.getString("des"); */
 jsonData = new JSONObject(data);
}catch(Exception e){
	 jsonData = new JSONObject();}
%>




<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=widgetTitle%></title>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>

</head>
<body>

<div class=" alert alert-danger" id="errormsg" style="display: none" ></div>
<form id="ticketform">
<input type="hidden" name="data" id="data" />

<div class="col-md-12 col-sm-12 col-xs-12 ">
	<div class="col-md-2 col-sm-2 col-xs-12 ">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input type="text" name="title" value="<%=title%>" id="mytcktw" size="30"/>
	</div>



<div class="col-md-2 col-sm-2 col-xs-12 ">
		<label><s:text name="pg.widget.description.name" /></label>
	</div>

<div class="col-md-10 col-sm-10 col-xs-12 form-group">
<textarea rows="4" cols="50" name="des" id="des" placeholder="Enter Description" ><%=jsonData.getString("des")%></textarea>
		<!-- <input type="text" name="des" id="des" size="30"/> -->
	</div>

	<div class="col-md-2 col-sm-2 col-xs-12 ">
		<label><s:text name="pg.widgets.bname" /></label>
	</div>
	 <div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input type="text" name="btnLbl" id="btnLbl" placeholder="Enter Label" size="30" value="<%=jsonData.getString("btnLbl")%>"/>
	</div> 


</div>
</form>



<div class="col-md-12 col-sm-12 col-xs-12 text-center">
	<button class="btn btn-primary saveButton" id="save"><s:text name="pg.widgets.save" /></button>
	<button class="btn btn-primary saveButton" id="saveExit" style="display:none;"><s:text name="pg.widgets.save.exit" /></button> 
	 <button class="btn btn-cancel" id="exit"><i class="fa fa-times"></i></button>
	<button class="btn btn-primary " id="reset"  style="display:none;"><s:text name="pg.widgets.reset" /></button> 
</div>
</body>
<script>
$(function(){


	 var getData = function(){
		var data={};
		 data = {				 
				/* height:$('#height').val(), */
				des : $('#des').val(),
				btnLbl :$('#btnLbl').val()
			}; 
		return JSON.stringify(data);
	};
	var getTitle=function(){
		var title=$('#mytcktw').val();
		return title;
		
	};
	
		
	$('.saveButton').click(function(){
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		$('#errormsg').hide();
		$('#errormsg').html('');
		var flag=true;
		var button_lbl = $('#btnLbl').val();
		if(button_lbl.trim()=='' || button_lbl.trim()==null){
			flag=false;
			savebutton.removeAttr('disabled');
			savebutton.html(props.pg_widgets_save_lbl);
			$('#errormsg').html('<ul class="errorMessage">  <li><span>Button name is empty.</span></li> </ul>');
			$('#errormsg').show();
		}
		if(flag){
			var data = getData();
			/* alert(data); */
			$('#data').val(data);
			var url = "SaveSettings?type=BuyerAttOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=<%=widgetTitle%>&action=SaveSettings&layout_type=<%=layout_type%>";
			$.ajax({
				type:'POST',
				url:url,
				data:$('#ticketform').serialize(),
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
		}
	});

					
$('#exit').on('click',function() {
	parent.disablePopup();
});
});
</script>
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
</script>
</html>

