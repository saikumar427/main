<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
//String ref_tTitle = request.getParameter("reftitle");
String layout_type = request.getParameter("layout_type");


String widgettype = request.getParameter("type");
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?BuyerAttHelper.getWidgetOptions("0", widgetid,layout_type):BuyerAttHelper.getWidgetOptions(eventid, widgetid,layout_type);

String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type); 
if(data == null || "".equals(data))data="{\"templatedata\":\"\"}";

JSONObject textjson = new JSONObject(data);
String templatedata = "";
if(textjson.has("templatedata"))
	templatedata = textjson.getString("templatedata");

//data=data==null||"{}".equals(data)?"":data;
System.out.println("widgetTitle::"+widgetTitle);
String ref_tTitle =widgetTitle;//DBHelper.getRefTitle(eventid,widgetid); 


%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script type="text/javascript" language="JavaScript" src="/main/js/nice.js"></script>
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js?id=1" ></script>
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
			if(window.Prototype) {
				delete Object.prototype.toJSON;
				delete Array.prototype.toJSON;
				delete Hash.prototype.toJSON;
				delete String.prototype.toJSON;
				}
			var data={};
			data.templatedata=nicEditors.findEditor('content').getContent();
			return JSON.stringify(data);
			
		};
	var getTitle=function(){
		var title=$('#txttt').val();
		return title;
	};
	var saveRefTitle=function(savebutton){
		var title=$('#retxttt').val();		
		$.getJSON('SaveSettings?type=saveRefTiltleBA&eid=<%=eventid%>&widgetid=<%=widgetid%>&layout_type=<%=layout_type%>',{
			title:parent.convert(title)
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
			data:$('#textForm').serialize(),
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
<form id="textForm">
<input type="hidden" name="data" id="data" />
<%-- <h3>Widget Options : <%=widgetTitle%></h3>
<hr> --%>
<div class="col-md-12 col-sm-12 col-xs-12">
	<div class="col-md-3 col-sm-3 col-xs-12 ">
		<label><s:text name="pg.widgets.ref.title" /></label>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input type="text" name="reftitle" value="<%=ref_tTitle%>" id="retxttt" size="30" />
	</div>
	
	<div class="col-md-3 col-sm-3 col-xs-12 ">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		<input type="text" size="30" name="title" value="<%=title%>" id="txttt"/>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 form-group">
		<textarea id="content" rows="10" cols="70" placeholder="Enter your text here"><%=templatedata%></textarea><br>
		<span class="sm-font"><s:text name="pg.text.html.note" /></span>
	</div>
</div>

</form>
</body>
<div class="col-md-12 col-sm-12 col-xs-12 text-center">
		<button id="save" class='saveButton btn btn-primary'><s:text name="pg.widgets.save" /></button>
		<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
		<button id="exit" class="btn btn-cancel"><i class="fa fa-times"></i></button>
		<button id="reset" class="btn btn-primary" style="display:none;"><s:text name="pg.widgets.reset" /></button>
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
	</script>
<script>
var o = {
		buttonList: ['save','bold','italic','underline','left','center','right','justify','ol','ul','fontSize','fontFamily','fontFormat','indent','outdent','image','forecolor','bgcolor','xhtml','textalign','align','removeformat','strikethrough','subscript','superscript','arrow','close','link','unlink','upload'],
		iconsPath:('/main/images/nicEditIcons-latest.gif'),
		maxHeight : 350
		};
			
new nicEditor(o).panelInstance('content');
</script>
</html>