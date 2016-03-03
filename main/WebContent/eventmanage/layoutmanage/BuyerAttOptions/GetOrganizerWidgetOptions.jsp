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
String hostbydata="", ctcmgrdata="",notesdata="";
try{
	JSONObject orgnzrjson=new JSONObject(data);
	hostbydata=orgnzrjson.getString("hostbydata");
	ctcmgrdata=orgnzrjson.getString("ctcmgrdata");
	notesdata=orgnzrjson.getString("notesdata");
}catch(Exception e){System.out.println("when  json parsing error: "+e.getMessage());}
%>
<!DOCTYPE html>
<html>
<head>
<title><%=widgetTitle%></title>

<script type="text/javascript" language="JavaScript" src="/main/js/nice.js"></script>


<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
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

	var getData = function(){
		var data={};
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
        data.chkflds=val;	
		data.hostbydata=$('#hostbyid').val();
		data.ctcmgrdata=$('#ctcmgrid').val();
		data.notesdata=nicEditors.findEditor('notesid').getContent();
		return JSON.stringify(data);
	};
	var getTitle=function(){
	 return $('#organizerwidget').val();	
	}
	
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
			data:$('#getorgform').serialize(),
			success:function(response){
				if(response.status=="success") {
					parent.notification(props.widgets_status_msg,"success");
					
					if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
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
			parent.disablePopup();
	});   
	
	$('#chkmgr').on('click',function(){
			$(".contactmanager").slideToggle();
	});
	
});
</script>
</head>
<body>
<form id="getorgform">
<input type="hidden" name="data" id="data" />
<div id="scrolldiv">
<div class="col-md-12 col-sm-12 col-xs-12 ">
	<div class="col-md-2 col-sm-2 col-xs-12">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input type="text" size="30" name="title" id="organizerwidget" value="<%=title%>">
	</div>
	
	<div class="col-md-2 col-sm-2 col-xs-12">
		<input name="organizer" type="checkbox" value='chkhost' <%if(data.indexOf("chkhost")>-1)out.print("checked='checked'");%> />&nbsp;<s:text name="pg.org.host.name"/>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input value="<%=hostbydata%>" type="text" id="hostbyid" size="30"/>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group">
		<input name="organizer" type="checkbox" value='chkctmgr' id="chkmgr" <%if(data.indexOf("chkctmgr")>-1)out.print("checked='checked'");%>/>&nbsp;<s:text name="pg.org.contact.lbl"/>
	</div>
	
	<div class="col-md-2 col-sm-2 col-xs-12">
		<label><s:text name="pg.org.label.lbl"/></label>
	</div>
	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
		<input value="<%=ctcmgrdata%>" type="text" id="ctcmgrid" size="30"/>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group contactmanager" <%if(data.indexOf("chkctmgr")>-1)out.print("style=display:table-row"); else out.print("style=display:none"); %>>
		<input name="organizer" type="checkbox" value='email' <%if(data.indexOf("email")>-1)out.print("checked='checked'");%>  /><s:text name="pg.org.email.lbl" />
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group contactmanager" <%if(data.indexOf("chkctmgr")>-1)out.print("style=display:table-row"); else out.print("style=display:none"); %>>
		<input name="organizer" type="checkbox" value='show' <%if(data.indexOf("show")>-1)out.print("checked='checked'");%>  /><s:text name="pg.org.show.my.console.lbl" />
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group">
		<input name="organizer" type="checkbox" value='chknotes' id="noteschk" <%if(data.indexOf("chknotes")>-1)out.print("checked='checked'");%> />&nbsp;<s:text name="pg.org.notes.lbl" />
	</div>

</div>
<div class="col-md-12 col-xs-12 col-sm-12 form-group">
<table id="notessection">
<tr>
		<td>
		<div><textarea cols="70" rows="10" id="notesid"><%=notesdata%></textarea></div>
		</td></tr>

</table>
</div>
</div>
</form>

<div class="col-md-12 col-xs-12 col-sm-12 text-center">
	<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
	<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
	<button id="exit" class="btn btn-cancel"><i class="fa fa-times"></i></button>
	<button id="reset" class="btn btn-primary"  style="display:none;"><s:text name="pg.widgets.reset" /></button>
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
<br/>
<script>
var o = {
		buttonList: ['save','bold','italic','underline','left','center','right','justify','ol','ul','fontSize','fontFamily','fontFormat','indent','outdent','image','forecolor','bgcolor','xhtml','textalign','align','removeformat','strikethrough','subscript','superscript','arrow','close','link','unlink','upload'],
		iconsPath:('/main/images/nicEditIcons-latest.gif'),
		maxHeight : 350
		};
			
new nicEditor(o).panelInstance('notesid');
</script>
</html> 