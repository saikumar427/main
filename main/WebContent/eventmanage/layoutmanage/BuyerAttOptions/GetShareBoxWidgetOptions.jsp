<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList,com.eventbee.general.GenUtil,com.eventbee.general.EbeeConstantsF"%>
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String layout_type = request.getParameter("layout_type");
String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type);
System.out.println("title in ShareBoxWidget::"+title);
String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?BuyerAttHelper.getWidgetOptions("0", widgetid,layout_type):BuyerAttHelper.getWidgetOptions(eventid, widgetid,layout_type);
String eventname=BuyerAttHelper.getEventinfo(eventid).get("eventname");
if(eventname==null)eventname="";
String eventurllabel="",emailfriendlabel="",briefmessage="";
ArrayList<String> shareoptions=new ArrayList<String>();
try{
if(!"".equals(data)){
JSONObject jsonData = new JSONObject(data);
String optionslist="",soptions="";
optionslist=jsonData.get("shareboxoptions")+"";
if(optionslist!=null && !"".equals(optionslist))
soptions=optionslist.substring(1, optionslist.length()-1);
String[] strarr=GenUtil.strToArrayStr(soptions,",");
for(int i=0;i<strarr.length;i++){
	shareoptions.add(strarr[i]);
}
JSONObject labels=jsonData.getJSONObject("labels");
System.out.println("shareoptions are:"+shareoptions);
System.out.println("labels are:"+labels);

try{
	emailfriendlabel=labels.getString("emailfrnd");
	eventurllabel=labels.getString("eventurl");
	briefmessage=labels.getString("bmessage");
	briefmessage=briefmessage.replace("$eid", eventid);
	briefmessage=briefmessage.replace("$serveraddress", serveraddress);
	briefmessage=briefmessage.replace("$eventname", eventname);
	
}catch(Exception e){
	System.out.println("Exception occured in getting sharebox labels:"+e.getMessage());
	e.printStackTrace();
}}
}catch(Exception e){
	System.out.println("Exception occured while getting data in ShareBox Options:"+e.getMessage());
	e.printStackTrace();
}
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
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js?id=1" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>
<style>
.ui-widget-overlay{
background: #aaaaaa url(images/ui-bg_flat_0_aaaaaa_40x100.png) 50% 50% repeat-x;
opacity: .3;
 }
 .ui-dialog .ui-dialog-titlebar 
{
    height: 20px; /* or whatever you want */
}
.ui-button-text {
    font-size: 15px;
}
</style>
<script>            
$(function(){
	showDivs();
	  function showDivs() {
		 $(':checkbox:checked').each(function(i){
			   var id = $(this).val();
			   $('#'+id+'div').toggle();
		});
	  }	  
	var getData = function(){
		var jsondata={},data={};
		var shareoptions=[];
		$(':checkbox:checked').each(function(i){
			   var id = $(this).val();
			   shareoptions[i]=id;
		});
	    if($('#emailfrndlabel').val().trim()==''){
			$('#emailfrndlabel').val(props.pg_share_widget_email_friends_lable);
		}
	     
	     if($('#eventurllabel').val().trim()==''){
	    	$('#eventurllabel').val(props.pg_share_widget_event_url);
	    }
		 
		 jsondata['emailfrnd']=$('#emailfrndlabel').val();
		 jsondata['eventurl']=$('#eventurllabel').val();
		 jsondata['bmessage']=$('#bmessage').val();
		data['shareboxoptions']="["+shareoptions+"]";
		data['labels']=jsondata;
	    return JSON.stringify(data);   
  };
  
  var getTitle=function(){
		var title=$('#sharetitle').val();
		return title;
	};

	
	$('.saveButton').click(function(){
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		var data = getData();
		$('#data').val(data);
		var url = "SaveSettings?type=BuyerAttOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=<%=widgetTitle%>&action=SaveSettings&layout_type=<%=layout_type%>";
		$.ajax({
			type:'POST',
			url:url,
			data:$('#getshareform').serialize(),
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

	
	
	$('#exit').on('click',function() {
		//if(confirm("All changes will be lost. Are you sure?"))
			parent.disablePopup();
			//window.close();
	});
	
});
  function showLabel(id){
	 $('#'+id+'div').slideToggle(); 
  }
  
</script>
</head>
<body>
<form id="getshareform">
<input type="hidden" name="data" id="data" />

<div class="col-md-12 col-xs-12 col-sm-12">
	<div class="col-md-2 col-sm-2 col-xs-12 ">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input type="text" name="title" value="<%=title%>" id="sharetitle" size="30"/>
	</div>

	<div class="col-md-12 col-xs-12 col-sm-12 form-group">
		<input name="emailfrnd" type="checkbox" id="emailfrnd" value="emailfrnd" <%if(shareoptions.contains("emailfrnd"))out.print("checked='checked'");%> onclick="showLabel('emailfrnd');"/><s:text name="pg.share.show.email.lbl" />
	</div>
	
	<div id="emailfrnddiv" style="display:none">
		<div class="col-md-2 col-sm-2 col-xs-12">
			<label><s:text name="pg.share.email.friend.lbl" /></label>
		</div>
		<div class="col-md-10 col-sm-10 col-xs-12 form-group">
			<input type="text" size="30" name="emailfrndlabel" value="<%=emailfriendlabel%>" id="emailfrndlabel"/>
		</div>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group">
		<input name="fsend" type="checkbox" id="fsend" <%if(shareoptions.contains("fsend"))out.print("checked='checked'");%> value="fsend"/><s:text name="pg.share.show.fbsend" />
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group">
		<input name="eventurl" type="checkbox"  id="eventurl" value="eventurl" <%if(shareoptions.contains("eventurl"))out.print("checked='checked'");%> onclick="showLabel('eventurl');"/><s:text name="pg.share.show.event.url" />
	</div>
	
	<div id="eventurldiv" style="display:none;">
		<div class="col-md-2 col-sm-2 col-xs-12">
			<label><s:text name="pg.share.show.event.lbl" /></label>
		</div>
		<div class="col-md-10 col-sm-10 col-xs-12 form-group">
			<input type="text" size="30"  name="eventurllabel" value="<%=eventurllabel%>" id="eventurllabel"/>
		</div>
	</div>
	
    <div id="briefmsg" >
	    <div class="col-md-2 col-sm-2 col-xs-12">
	    	<label><s:text name="pg.share.brief.msg" /></label>
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-12 form-group">
	    	<textarea  name="bmessage" cols="60" rows="11" id="bmessage"><%=briefmessage%></textarea>
	    </div>
    </div>
    </div>
    </form>
<div class="col-md-12 col-xs-12 col-sm-12 text-center">
	<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
	<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
	<button id="exit" class='btn btn-cancel'><i class="fa fa-times"></i></button>
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
</script>

</html> 
