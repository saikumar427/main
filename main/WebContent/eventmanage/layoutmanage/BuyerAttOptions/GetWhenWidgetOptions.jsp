<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.HashMap"%>
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
String startday="",startmon="",startdate="",startyear="",starttime="",startampm="",endday="",endmon="",enddate="",endyear="",endtime="",endampm="";
HashMap<String,String> datamap=BuyerAttHelper.getEventinfo(eventid);
startday=datamap.get("startday");if(startday==null)startday="";
startmon=datamap.get("startmon");if(startmon==null)startmon="";
startdate=datamap.get("startdate");if(startdate==null)startdate="";
startyear=datamap.get("startyear");if(startyear==null)startyear="";
starttime=datamap.get("starttime");if(starttime==null)starttime="";
startampm=datamap.get("startampm");if(startampm==null)startampm="";
endday=datamap.get("endday");if(endday==null)endday="";
endmon=datamap.get("endmon");if(endmon==null)endmon="";
enddate=datamap.get("enddate");if(enddate==null)enddate="";
endyear=datamap.get("endyear");if(endyear==null)endyear="";
endtime=datamap.get("endtime");if(endtime==null)endtime="";
endampm=datamap.get("endampm");if(endampm==null)endampm="";
String whendata="",addcndata="",addcntext="";
try{ 
 JSONObject whenjson=new JSONObject(data);
 whendata=whenjson.getString("whenc");
 addcndata=whenjson.getString("addcn");
 addcntext=whenjson.getString("addcntxt");
}catch(Exception e){System.out.println("when  json parsing error"+e.getMessage());}
addcndata=addcndata==null?"":addcndata;
whendata=whendata==null?"":whendata;
addcntext=addcntext==null?"":addcntext;  
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
<script type="text/javascript" language="JavaScript" src="/main/js/nice.js"></script>
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
	showpreview();
	$(".nicEdit-main").keyup(function () {
        showpreview();
    });
	$(".nicEdit-main").mouseup(function () {
        showpreview();
    });
	$(".nicEdit-main").mouseover(function () {
        showpreview();
    });
	var getData = function(){
		if(window.Prototype) {
			delete Object.prototype.toJSON;
			delete Array.prototype.toJSON;
			delete Hash.prototype.toJSON;
			delete String.prototype.toJSON;
			}
		var data={};
		var keywords=['$startDay','$startMon','$startDate','$startYear','$startTime','$startAMPM','$endDay','$endMon','$endDate','$endYear','$endTime','$endAMPM'];
		data.whenc=nicEditors.findEditor('content').getContent();
		 var val = [];
	        $(':checkbox:checked').each(function(i){
	          val[i] = $(this).val();
	        });
		if(val.length>1 && JSON.stringify(val).indexOf('addTocalnder')>-1)
		{data.addcn=val;	
		data.addcntxt=$('#addmycaltxt').val();
		}
		data.keyw=keywords;
		return JSON.stringify(data);
		
	};
	
	var getTitle=function(){
		return $('#whenwidget').val();
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
			data:$('#whenwidgform').serialize(),
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
					//window.close();
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

function selectText(id) {
    if (document.selection) {
        var range = document.body.createTextRange();
        range.moveToElementText(document.getElementById(id));
        range.select();
    } else if (window.getSelection) {
        var range = document.createRange();
        range.selectNode(document.getElementById(id));
        window.getSelection().addRange(range);
    }
}

function showpreview(){
	var content=nicEditors.findEditor('content').getContent();
    $('.whenkey').each(function(i,e){
		 var key=$(e).html();
		 var id=key.replace("$","").toLowerCase();
		 while(content.indexOf(key)>-1){
		 var position=Number(content.indexOf(key))+Number(key.length);
		 if(content.charAt(position)==',' && ($("#"+id).val()=='' || $("#"+id).val()=='undefined'))
			 content=content.substring(0,position)+content.substring(position+1,content.length);
		content=content.replace(key,$("#"+id).val());
		 }
	});
    if(content.substring(content.lastIndexOf("<br>"),content.length)=='<br>')
		content=content.substring(0,content.length-4);
    $("#previewcontent").html(content);
}
</script>
</head>
<style type="text/css">
#scrolldiv{
	height:415px;
	width:100%;
	overflow-x: hidden;
	overflow-y:scroll;
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
.display-none{
	display: none;
}
.bottom-gap{
	margin-bottom:3px;
}
</style> 
<body>

<form id="whenwidgform">
<input type="hidden" name="data" id="data" />

<div id="scrolldiv">
<div class="col-md-12 col-sm-12 col-xs-12">
	
	<div class="col-md-8 col-sm-8 col-xs-12 ">
	
	<div class="col-md-4 col-sm-4 col-xs-12 row">
		<label><s:text name="pg.widgets.title" /></label>
	</div>
	<div class="col-md-8 col-sm-8 col-xs-12 form-group">
		<input type="text" value="<%=title%>" id="whenwidget" name="title" class="form-control">
	</div>
	
		<textarea id="content" rows="10" cols="60" placeholder="<s:text name="pg.when.placeholder.text.lbl" />" onkeyup="showpreview();"><%=whendata%></textarea>
		<br><label><b><s:text name="pg.widgets.preview.lbl" /></b></label><br>
		<div id="previewcontent" class="form-group" style="height:100%;border:0px solid gray;overflow-y:auto;overflow-x:auto;padding:5px;"></div>
		
		
		<div class="col-md-12 col-sm-12 col-xs-12 form-group">
			<input name="whencal" type="checkbox" value='addTocalnder' onchange="javascript:$('#caloptions').slideToggle( 'slow');"  <%if(addcndata.indexOf("addTocalnder")>-1)out.print("checked='checked'");%> /><s:text name="pg.when.add.calendar.lbl" />
		</div>
		
		<div class="col-md-12 col-sm-12 col-xs-12 ">
			  <div id='caloptions' style=<%if(addcndata.indexOf("addTocalnder")<0)out.print("display:none;");%>>
			  <s:text name="pg.when.text.lbl" /><input value="<%=addcntext%>"  placeholder="Enter your add calendar text here"  size="40" type="text" id="addmycaltxt" />
			  <br/><br>
			  <input name="whencal" type="checkbox" value='ical'   <%if(addcndata.indexOf("ical")>-1)out.print("checked='checked'");%> /> <s:text name="pg.when.iCal.lbl" /> &nbsp;&nbsp;
			  <input name="whencal" type="checkbox" value='google'   <%if(addcndata.indexOf("google")>-1)out.print("checked='checked'");%> /> <s:text name="pg.when.google.lbl" /> &nbsp;&nbsp;
			  <input name="whencal" type="checkbox" value='yahoo'   <%if(addcndata.indexOf("yahoo")>-1)out.print("checked='checked'");%> /> <s:text name="pg.when.yahoo.lbl" /> &nbsp;&nbsp;
 			 </div>
		</div>
	</div>
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="text-center"><b><s:text name="pg.when.key.words.lbl" /></b></div><br>
		<div class="row col-md-12 col-sm-12 col-xs-12" style="border: 1px solid #e5e5e5; padding:10px 0px; ">
		
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="sday" onClick="selectText('sday');">$startDay</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="startday" id="startday" value="<%=startday%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="smon" onClick="selectText('smon');">$startMon</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="startmon" id="startmon" value="<%=startmon%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="sdate" onClick="selectText('sdate');">$startDate</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="startdate" id="startdate" value="<%=startdate%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="syear" onClick="selectText('syear');">$startYear</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="startyear" id="startyear" value="<%=startyear%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="stime" onClick="selectText('stime');">$startTime</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="starttime" id="starttime" value="<%=starttime%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="sampm" onClick="selectText('sampm');">$startAMPM</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="startampm" id="startampm" value="<%=startampm%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="eday"  onClick="selectText('eday');">$endDay</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="endday" id="endday" value="<%=endday%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="emon" onClick="selectText('emon');">$endMon</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="endmon" id="endmon" value="<%=endmon%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="edate" onClick="selectText('edate');">$endDate</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="enddate" id="enddate" value="<%=enddate%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="eyear" onClick="selectText('eyear');">$endYear</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="endyear" id="endyear" value="<%=endyear%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="etime" onClick="selectText('etime');">$endTime</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="endtime" id="endtime" value="<%=endtime%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="whenkey" id="eampm" onClick="selectText('eampm');">$endAMPM</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="endampm" id="endampm" value="<%=endampm%>" onkeyup="showpreview();"/></div>
		</div>
		</div>
	</div>
	</div>
 </div>
	</form>
	<div class="col-md-12 col-sm-12 col-xs-12 text-center">
		<button id="save" class='saveButton btn btn-primary'><s:text name="pg.widgets.save" /></button>
		<button id="saveExit" class='saveButton btn btn-primary' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
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
