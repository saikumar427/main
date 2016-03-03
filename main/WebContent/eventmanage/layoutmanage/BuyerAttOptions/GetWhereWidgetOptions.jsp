<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject,java.util.HashMap"%>
 <%@page import="com.eventbee.layout.DBHelper"%> 
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String layout_type = request.getParameter("layout_type");
String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type); 
System.out.println("title in WhereWidget::"+title);
//String data = DBHelper.getWidgetOptions(eventid, widgetid);
String venue="",address1="",address2="",state="",country="",city="";
HashMap<String,String> datamap=DBHelper.getEventinfo(eventid);
address1=datamap.get("address1");if(address1==null)address1="";
address2=datamap.get("address2");if(address2==null)address2="";
state=datamap.get("state");if(state==null)state="";
country=datamap.get("country");if(country==null)country="";
city=datamap.get("city");if(city==null)city="";
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?BuyerAttHelper.getWidgetOptions("0", widgetid,layout_type):BuyerAttHelper.getWidgetOptions(eventid, widgetid,layout_type);// DBHelper.getWidgetOptions("1", widgetid) get orginal datain DB.
String wheredata="",lat="",lng="",gmap="";
try{
	JSONObject wherejson=new JSONObject(data);
	System.out.println("where json is::"+wherejson);
	wheredata=wherejson.getString("wherec");
	gmap=wherejson.getString("gmap");
	lat=wherejson.getString("lat");
	lng=wherejson.getString("lng");
}catch(Exception e){System.out.println("when  json parsing error"+e.getMessage());}
wheredata=wheredata==null?"":wheredata;
gmap=gmap==null?"":gmap;
lat=lat==null?"":lat;
lng=lng==null?"":lng;
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
<script type="text/javascript"  src="/main/js/nice.js"></script>
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
		var keywords=['$Venue','$Address1','$Address2','$City','$Country'];
		data.wherec=nicEditors.findEditor('content').getContent();
		 var val = [];
	        $(':checkbox:checked').each(function(i){
	          val[i] = $(this).val();
	        });
	        data.gmap=val;	
	
		data.lat=$('#lat').val();
	    data.lng=$('#lng').val();
		data.keyw=keywords;
		//alert(JSON.stringify(data));
		return JSON.stringify(data);
		
		//return $('#content').val();
	};  
	
	var getTitle=function(){
		var title=$('#wheretitle').val();
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
			data:$('#wherewidform').serialize(),
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
    $('.wherekey').each(function(i,e){
		 var key=$(e).html();
		 //alert(key);
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
<style type="text/css">
#scrolldiv{
	height:415px;
	#width:915px;
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

</head>
<body>
<form id="wherewidform">
<input type="hidden" name="data" id="data" />
<div id="scrolldiv">

<div class="col-md-12 col-sm-12 col-xs-12">
	<%-- <div class="col-md-4 col-sm-4 col-xs-12 row"><label>Widget Title:</label></div>
	<div class="col-md-8 col-sm-8 col-xs-12 form-group"><input type="text" name="title" value="<%=title%>" id="wheretitle" size="30"/></div> --%>
	
	<div class="col-md-8 col-sm-8 col-xs-12">
	
	<div class="col-md-4 col-sm-4 col-xs-12 row"><label><s:text name="pg.widgets.title" /></label></div>
	<div class="col-md-8 col-sm-8 col-xs-12 form-group"><input type="text" name="title" value="<%=title%>" id="wheretitle" class="form-control"/></div>
	<div style="clear:both;"></div>
	
		<textarea id="content" rows="10" cols="60" placeholder="<s:text name="pg.where.placeholder.text.lbl" />" onkeyup="showpreview();"><%=wheredata%></textarea>
		<br><b><s:text name="pg.widgets.preview.lbl" /></b><br>
		<div id="previewcontent" class="form-group" style="height:100%;border:0px solid gray;overflow-y:auto;overflow-x:auto;padding:5px;"></div>
		
		<div class="col-md-12 col-xs-12 col-sm-12">
			<input name="where" type="checkbox" value='gmap' onchange="javascript:$('#whereoptions').slideToggle( 'slow');"  <%if(gmap.indexOf("gmap")>-1)out.print("checked='checked'");%> />
			<s:text name="pg.where.show.google.lbl" />
			
			<div style="display:none;">
			<div id='whereoptions' style=<%if(gmap.indexOf("gmap")<0)out.print("display:none;");%>>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' id='chnglat'  name="where"  value='chnglat' style='border:2px solid grey;cursor:pointer' <%if(gmap.indexOf("chnglat")>-1)out.print("checked='checked'");%>  onchange="javascript:$('#latlong').slideToggle( 'slow');" /><s:text name="pg.where.lat.log.lbl" />
			 
			 <div id='latlong' style=<%if(gmap.indexOf("chnglat")<0)out.print("display:none;");%>>
			  <br/>
			<div style="padding-left:43px"> 
			<table width="100%" class="form-group">
			<tr><td width="10%"><s:text name="pg.where.latitude.lbl" /></td>
			<td><input id='lat' placeholder="Enter your latitude  text here"  size="30"   value='<%=lat%>' /></td>
			</tr>
			</table>
			</div>
			
			<div style="padding-left:43px"> 
			<table width="100%" class="form-group">
			<tr><td width="10%"><s:text name="pg.where.longitude.lbl" /></td>
			 <td><input  id='lng' placeholder="Enter your longitude  text here"  size="30"  value='<%=lng%>'  /></td></tr>
			 
			   </table>
			 </div>
			
			 </div>
			 </div>
			 </div>
			 
			 
		</div>
	</div>
	<div class="col-md-4 col-sm-4 col-xs-12">
	<div class="text-center"><b><s:text name="pg.where.key.words.lbl" /></b></div><br>
	<div class="row col-md-12 col-sm-12 col-xs-12" style="border: 1px solid #e5e5e5; padding:10px 0px; ">
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="wherekey" id="ve" onClick="selectText('ve');">$Venue</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="venue" id="venue" value="<%=venue%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="wherekey" id="a1" onClick="selectText('a1');">$Address1</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="address1" id="address1" value="<%=address1%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="wherekey" id="a2" onClick="selectText('a2');">$Address2</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="address2" id="address2" value="<%=address2%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="wherekey" id="st" onClick="selectText('st');">$City</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="state" id="city" value="<%=city%>" onkeyup="showpreview();"/></div>
		</div>
		<div class="">
			<div class="col-ms-12 col-sm-12 col-xs-12 bottom-gap"><div class="wherekey" id="ctry" onClick="selectText('ctry');">$Country</div></div>
			<div class="col-ms-8 col-sm-8 col-xs-12 form-group display-none"><input type="text" name="country" id="country" value="<%=country%>" onkeyup="showpreview();"/></div>
		</div>
	</div>
	
	</div>
	</div>
 </div>
	</form>
	<div class="col-md-12 col-xs-12 col-sm-12 text-center">
		<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
		<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
		<button id="exit" class='btn btn-cancel'><i class="fa fa-times"></i></button>
		<button id="reset" class='btn btn-primary' style="display:none;"><s:text name="pg.widgets.reset" /></button>
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
