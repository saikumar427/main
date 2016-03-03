<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.layout.DBHelper,java.util.ArrayList,com.eventbee.general.GenUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eventid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String title=DBHelper.getTitle(eventid,widgetid);
System.out.println("title in AttendeeZone::"+title);
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?DBHelper.getWidgetOptions("0", widgetid):DBHelper.getWidgetOptions(eventid, widgetid);

String contactlabel="",getticketslabel="";
ArrayList<String> attendeezoneoptions=new ArrayList<String>();
try{
if(!"".equals(data)){
JSONObject jsonData = new JSONObject(data);
String optionslist="",azoptions="";
optionslist=jsonData.get("attendeezoneoptions")+"";
if(optionslist!=null && !"".equals(optionslist))
azoptions=optionslist.substring(1, optionslist.length()-1);
String[] strarr=GenUtil.strToArrayStr(azoptions,",");
for(int i=0;i<strarr.length;i++){
	attendeezoneoptions.add(strarr[i]);
}
JSONObject labels=jsonData.getJSONObject("labels");
System.out.println("attendeezoneoptions are:"+attendeezoneoptions);
System.out.println("attendeezone labels are:"+labels);

try{
	contactlabel=labels.getString("contact");
	getticketslabel=labels.getString("gettickets");
}catch(Exception e){
	System.out.println("Exception occured in getting attendeezone labels:"+e.getMessage());
	e.printStackTrace();
}
}
}catch(Exception e){
	System.out.println("Exception occured while getting data in Attendee Zone Options:"+e.getMessage());
	e.printStackTrace();
}
%>
<!DOCTYPE html>
<html>
<head>
<title><%=widgetTitle%></title>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
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
			   $('#'+id+'div').slideToggle();
		});
	  }	  
	var getData = function(){
		var jsondata={},data={};
		var attendeezoneoptions=[];
		$(':checkbox:checked').each(function(i){
			   var id = $(this).val();
			   attendeezoneoptions[i]=id;
		});
		
		
		
		
	    if($('#contactlabel').val().trim()==''){
			$('#contactlabel').val("Contact");
		}
	     
	     if($('#getticketslabel').val().trim()==''){
	    	$('#getticketslabel').val("Get Tickets");
	    }
		 
		 jsondata['contact']=$('#contactlabel').val();
		 jsondata['gettickets']=$('#getticketslabel').val();
		 data['attendeezoneoptions']="["+attendeezoneoptions+"]";
		 data['labels']=jsondata;
	    return JSON.stringify(data);   
  };
  
  var getTitle=function(){
		var title=$('#attendeezonetitle').val();
		return title;
	};


	$('#reset').on('click',function(){
		$( "#dialog" ).dialog({
			modal:true,
		    width: 420,
		    height:200,
			buttons:{
				"OK":function(){
					reset();
					},
				Cancel: function() {
				       $( this ).dialog( "close" );
				        }
				}});
	});

		var reset=function(){
			var ref=window.location.href;
			if(ref.indexOf("&reset=reset")>-1){}else
			ref=ref+"&reset=reset";
			window.location.href=ref; 
		}
	
	
	
	$('.savebtn').on('click',function(){
		var savebutton = $(this);
		savebutton.html('Saving...');
		savebutton.attr('disabled','disabled');
		$.getJSON('SaveSettings?type=widgetOptions&eventid=<%=eventid%>&widgetid=<%=widgetid%>',{
			data: parent.convert(getData()),
			title: parent.convert(getTitle())
		}).done(function(response){
			if(response.status=="success") {
				if(savebutton.attr('id').indexOf('Exit')>-1)			
					parent.disablePopup();
					else{
						savebutton.html('Save');
						savebutton.removeAttr('disabled');
					}
				
			} else {
				alert("Server did not respond in a timely fashion. Try again later.");
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
  
function showLabel(id){
	 $('#'+id+'div').slideToggle(); 
 }
</script>
</head>
<body>
<%-- <h3>Widget Options : <%=widgetTitle%></h3>
<hr><br> --%>
<!-- write your code here -->
<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr><td width="20.25%">Widget Title:&nbsp;</td>	
		<td><input type="text" size="30" name="title" value="<%=title%>" id="attendeezonetitle"/></td>
		</tr>
	</table> 
	
<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
		<td><input name="contact"  type="checkbox" id="contact" value="contact" <%if(attendeezoneoptions.contains("contact"))out.print("checked='checked'");%> onclick="showLabel('contact');"/>	
		Show Contact</td>
		</tr>
	</table>
	 
	<div id="contactdiv" style="padding-left:25px;display:none;">
	
		<table border="0" cellpadding="3" cellspacing="0" width="100%">     
		<tr>
	   <td width="18%">Contact Label:&nbsp;</td><td><input type="text" size="30" name="contactlabel" value="<%=contactlabel%>" id="contactlabel"/></td>
	</tr> 
	</table> 
	</div>
	
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
	<tr>
		<td><input name="gettickets" type="checkbox"   id="gettickets" value="gettickets" <%if(attendeezoneoptions.contains("gettickets"))out.print("checked='checked'");%> onclick="showLabel('gettickets');"/>Show Get Tickets</td>
	</tr>
	</table>
	
	<div id="getticketsdiv" style="padding-left:25px;display:none;">
	
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
	<tr>
	   <td width="18%">Get Tickets Label:&nbsp;</td><td><input type="text" size="30" name="getticketslabel" value="<%=getticketslabel%>" id="getticketslabel"/></td>
	</tr> 
</table>
</div>
    
<div id="dialog" style="display:none" title="Alert"><p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>All changes will be lost. Are you sure?</p></div>
<!-- end --></br>

<button id="save" class='savebtn'>Save</button>
<button id="saveExit" class='savebtn'>Save &amp; Exit</button>
<button id="exit">Exit</button>
<button id="reset">Reset</button>
</body>
</html> 
