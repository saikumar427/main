<%@page import="java.util.Arrays"%>
<%@page import="com.eventbee.filters.SMTPHostProps"%>
<%@page import="java.util.Properties,java.io.*,java.util.Map,java.util.HashMap,java.util.ArrayList"%>
<%@page import="java.util.Set,java.util.Iterator"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="/main/js/jquery.tablesorter.js"></script>
 <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">
<style type="text/css">

body { font-size: 62.5%; }
input.text { margin-bottom:12px; width:95%; padding: .4em; }
div#users { width: 350px; margin: 20px 0; }
div#users table { margin: 1em 0; border-collapse: collapse; width: 100%; }
div#users table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
.validateTips { border: 1px solid transparent; padding: 0.3em; }
.ui-state-error { padding: .3em; }

#backgroundPopup
{  
position:absolute;
z-index:1003;
width:100%;  
height:2000px;
top:0;  
left:0;  
background-color:#080808;
filter:alpha(opacity=50);   
-moz-opacity:0.5;        
opacity: 0.5;  
         
}
</style>

<%
HashMap FileMap=SMTPHostProps.getFileMap();
HashMap MemoryMap=(HashMap)SMTPHostProps.getAllProperties();

String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
String action=request.getParameter("action");
String sink=request.getParameter("sink");
String deleteproperty=request.getParameter("deleteproperty");
String changepgpool=request.getParameter("changepgpool");

ArrayList<String> arrlist=new ArrayList<String>();
try{
		String uri=request.getRequestURI();
		String url=request.getRequestURL().toString();
		String arruri[]=uri.split("/");

		if("main".equals(arruri[1])){
			arrlist=new ArrayList<String>(Arrays.asList("SIGNUP_CONFIRMATION",
														"EVENT_LISTING_CONFIRMATION_MAIL","FORGOT_LOGIN","FORGOT_MULTIPLE_LOGIN_PASSWORD",
	    												"FORGOT_SUBMGR_LOGIN","CONTACT_EVENT_MANAGER","Eventbee_Support_Mail","EVENT_SUBMANAGER_CONFIRMATION",
	    												"Marketing_Test_Mail","EmailMarketing_Test_Mail","Attendees_Test_Mail","GET_MY_TICKETS","BEECREDITS_CONFIRMATION","Manual_Test_Mail","REFER_FRIEND_MAIL","WAITLIST_ACTIVATION_LINK"));
         }else if("attendee".equals(arruri[1])){
        	 arrlist=new ArrayList<String>(Arrays.asList("EVENT_REGISTARTION_CONFIRMATION","RSVP_CONFIRMATION"));
         }else if("adminconsole".equals(arruri[1])){
         }else{ 
        	 if(url.contains("www.volumebee.com")|| url.contains("vbee")){
        		 arrlist=new ArrayList<String>(Arrays.asList("VBEE_SIGNUP_CONFIRMATION",
						 "VOLUME_LISTING_CONFIRMATION_MAIL","VOLUME_FORGOT_LOGIN","VOLUME_FORGOT_MULTIPLE_LOGIN_PASSWORD",
						     "VOLUMES_REGISTARTION_CONFIRMATION","Volumebee_Support_Mail"));
		 
        	 }
         }
}catch(Exception e){  
	System.out.println("Exception occured in getting urls from getRequest URI");
}

if(!"".equals(deleteproperty) && deleteproperty!=null){
	String delmemchk=request.getParameter("memorydelete");
	String delfilechk=request.getParameter("filedelete");
if("on".equals(delmemchk)){
	if(MemoryMap.containsKey(deleteproperty))
		MemoryMap.remove(deleteproperty);
}
if("on".equals(delfilechk)){
	if(FileMap.containsKey(deleteproperty))
		FileMap.remove(deleteproperty);
	//writeIntoFile(FileMap);
}
}

	if("sinkwithfile".equals(sink)){
		for(int i=0;i<arrlist.size();i++){
			if(MemoryMap.containsKey(arrlist.get(i))){
			MemoryMap.remove(arrlist.get(i));
			MemoryMap.put(arrlist.get(i),FileMap.get(arrlist.get(i)));
		}
		}
}

	if("sinkwithmemory".equalsIgnoreCase(sink)){
		FileInputStream in = new FileInputStream(fileconfproppath+"/smtphostprops.ebeeprops");
		Properties props = new Properties();
		props.load(in);
		in.close();
		FileOutputStream fos = new FileOutputStream(fileconfproppath+"/smtphostprops.ebeeprops");
		for(int i=0;i<arrlist.size();i++){
		String key=arrlist.get(i);
			props.setProperty(key,(String)MemoryMap.get(arrlist.get(i)));
		}
		props.store(fos, null);	
		fos.close();
}
	
if("changetopgpool".equalsIgnoreCase(changepgpool) && changepgpool!=null){
	String changeinfile=request.getParameter("chnginfile").trim();
	String chnageinmem=request.getParameter("chnginmem").trim();
	
	if(!"".equals(chnageinmem)){
		for(int i=0;i<arrlist.size();i++){
		if(MemoryMap.containsKey(arrlist.get(i))){
		MemoryMap.remove(arrlist.get(i));
		MemoryMap.put(arrlist.get(i),chnageinmem);
	}
	}
	}
	
	if(!"".equals(changeinfile)){
		
		FileInputStream in = new FileInputStream(fileconfproppath+"/smtphostprops.ebeeprops");
		Properties props = new Properties();
		props.load(in);
		in.close();
		FileOutputStream fos = new FileOutputStream(fileconfproppath+"/smtphostprops.ebeeprops");
		for(int i=0;i<arrlist.size();i++){
		String key=arrlist.get(i);
			props.setProperty(key,changeinfile);
		}
		props.store(fos, null);	
		fos.close();
	}
	}

if("editsave".equalsIgnoreCase(action)){	
	String pvalinfile=request.getParameter("pvalinfile");
	String pvalinmem=request.getParameter("pvalinmem");
	String propname=request.getParameter("propname");
	FileInputStream in = new FileInputStream(fileconfproppath+"/smtphostprops.ebeeprops");
	Properties props = new Properties();
	props.load(in);
	in.close();
	FileOutputStream fos = new FileOutputStream(fileconfproppath+"/smtphostprops.ebeeprops");
	if(!"".equals(propname.trim())){
		MemoryMap.put(propname,pvalinmem);
		props.setProperty(propname,pvalinfile);
		props.store(fos, null);	    
	    fos.close();
	}
	
}
%>

<html>
<title>DSN Change</title>
<body>
<table>
<tr height="10"><td></td></tr>
<tr><td style="padding-left:10px"><h2>Mail Sending Options</h2></td></tr>
</table>

<table  class="pure-table"  width="80%">
<thead>
<tr class="ui-widget-header">
<th width="25%">Mail Type</th>
<th width="25%">File Value</th>
<th width="25%">Memory Value</th>
<th width="25%">Action</th>
</tr>
<%
String css="pure-table-even";
for(int i=0;i<arrlist.size();i++){
String memtemp="";
String filetemp="";	
if(MemoryMap.containsKey(arrlist.get(i)))
	memtemp=(String)MemoryMap.get(arrlist.get(i));
if(FileMap.containsKey(arrlist.get(i)))
	filetemp=(String)FileMap.get(arrlist.get(i));
	
	if(i%2==0)css="pure-table-odd";
	else css="pure-table-even";
	
%>
<tr class='<%=css%>'><td width="25%"><%=arrlist.get(i)%></td>
<td width="25%"><%=filetemp%></td>
<td width="25%"><%=memtemp%></td>
<td width="25%"><a href="#" onclick="editProperty('data','edit','<%=arrlist.get(i)%>','<%=FileMap.get(arrlist.get(i))%>','<%=MemoryMap.get(arrlist.get(i))%>')">Edit</a>&nbsp;
<!--  |
<a href="#" onclick="openpopup('delete','delete','<%=arrlist.get(i)%>','<%=FileMap.get(arrlist.get(i))%>','<%=MemoryMap.get(arrlist.get(i))%>')">Delete</a>
-->
</td>
</tr>	
<%}
%>
</table><br/>
<table>
<tr>
 <!-- <td><input id="btn1" type="button" value="Add New property"></td> -->
<td><input type="button" value="Sync With File" onclick="sink('file');"></td>
<td><input type="button" value="Sync With Memory" onclick="sink('memory');"></td>
<td><input id="btn4" type="button" value="Change All"></td> 
</tr>
</table>
<br/><br/>

<div id="backgroundPopup" class="backgroundPopup" style="display:none"></div>
<div id="loading" style="display:none"><center>Processing...<br><img src="/main/images/loading.gif"/></center></div>
<div id="data" style="display:none" class="popup" style="padding-left:18px" title="Add/Edit Property">
<form name="editdata" method="post" id="editdata">
<!--<p class="validateTips" style="font-size:15px; ">All form fields are required.</p>-->
<table width="100%">
<tr height="30"><td></td></tr>
<tr><td><div><span><b>Add/Edit Property</b></span></div></td></tr>
<tr height="10"><td></td></tr>
<tr>
<td>Property Name :</td><td> <input type="text" size="42" id="pname" name="propname"></td></tr>
<tr height="5"><td></td></tr>
<tr>
<td>File Property :</td><td><select id="pvalinfile" name="pvalinfile">
<option value="AmazonSes">AmazonSes</option>
<option value="Mailjet">Mailjet</option>
<option value="Sendgrid">Sendgrid</option>
<option value="AmzonPhpMail">AmzonPhpMail</option>
<option value="Mandrill">Mandrill</option>
</select>
</td></tr>
<input type="hidden" value="editsave" name="action">
<tr height="10"><td></td></tr>
<!-- <tr><td><input type="checkbox" name="filechk" checked="checked">Add to File</td>
<td><input type="checkbox" name="memorychk" checked="checked">Add to Memory</td>
</tr>-->
<tr>
<td>Memory Property :</td><td><select id="pvalinmem" name="pvalinmem">
<option value="AmazonSes">AmazonSes</option>
<option value="Mailjet">Mailjet</option>
<option value="Sendgrid">Sendgrid</option>
<option value="AmzonPhpMail">AmzonPhpMail</option>
<option value="Mandrill">Mandrill</option>
</select>
</td></tr>
<tr height="20"><td></td></tr>
<!-- <tr>
<td colspan="2" align="center"><input type="submit" value="save" class="submitbtn-style" >
<input type="button" value="cancel" onclick="closepopup('data')" class="submitbtn-style"></td>
</tr> -->
</table>
</form>
</div>

<div id="delete" style="display:none">
<form name="deleteprop" method="post">
<table width="94%">
<tr height="10"><td></td></tr>
<tr><td><div><span><b>Delete Property :</b></span>&nbsp;<span id="propname"></div></td></tr>
</table>
<table width="100%">
<tr height="10"><td></td></tr>
<tr><td><input type="checkbox" name="filedelete" checked="checked">Delete from File</td></tr>
<tr><td><input type="checkbox" name="memorydelete" checked="checked">Delete from Memory </td>
</tr>
<tr height="10"><td></td></tr>
<input type="hidden" value="" name="deleteproperty" id="deleteproperty">

<tr height="10"><td></td></tr>
<tr><td><input type="submit" value="Delete" class="submitbtn-style"></td></tr>
</table>
</form>
</div>

<div id="pgpool" style="display:none" title="Change All">
<form name="pgpoolprop" method="post" id="changeallform">
<table width="68%">
<tr height="10"><td></td></tr>
<tr><td><div><span><b>Change all to</b></span></div></td></tr>
</table>
<table>
<tr height="10"><td></td></tr>
<!-- <tr><td><input type="checkbox" name="filepgpool" checked="checked">Change in File</td>
<td><input type="checkbox" name="memorypgpool" checked="checked">Change in Memory 
<input type="hidden" value="" name="changepgpool" id="pgpoolproperty">
</td>
</tr>-->
<tr>

<td>File Property :</td><td>
<input type="hidden" value="" name="changepgpool" id="pgpoolproperty">
<select id="chnginfile" name="chnginfile">
<option value="">--Select--</option>
<option value="AmazonSes">AmazonSes</option>
<option value="Mailjet">Mailjet</option>
<option value="Sendgrid">Sendgrid</option>
<option value="AmzonPhpMail">AmzonPhpMail</option>
<option value="Mandrill">Mandrill</option>
</select>
</td></tr>

<tr height="10"><td></td></tr>
<tr>
<td>Memory Property :</td><td><select id="chnginmem" name="chnginmem">
<option value="">--Select--</option>
<option value="AmazonSes">AmazonSes</option>
<option value="Mailjet">Mailjet</option>
<option value="Sendgrid">Sendgrid</option>
<option value="AmzonPhpMail">AmzonPhpMail</option>
<option value="Mandrill">Mandrill</option>
</select>
</td></tr>
</table>
</form>
</div>
<div id="bg" class="popup_bg"></div> 
<form name="dummy" method="post" id="dummy">
<input type="hidden" name="sink" id="sink" value="">
</form>
</body></html>

<script>

function sink(val){
	if(val=='file'){
		var agree=confirm("Are you sure to sync with file");
		if(agree){
		$('#sink').val('sinkwithfile');
		}else
	    return false;
	}else if(val=='memory'){
	    var agree=confirm("Are you sure to sync with memory");
		if(agree){
		$('#sink').val('sinkwithmemory');
		}else
	    return false;
	}
	saveData('dummy');
}


$(document).ready(function(){
	
	$('#btn1').click(function(){
		$('.validateTips').html("All form fields are required.");
		 $('#pname').removeClass("ui-state-error");
		 $('#pname').val("");
		 $('#pname').removeAttr('readonly');
		$('#data').dialog({
			height: 320,
   			width:600,
   			modal:true,
   			buttons:{
   			"Submit": function() {
   				saveData('editdata');
      			 },
      			 "Cancel": function() {
          			 $( this ).dialog( "close" );
          			 }
   		}, 
   		hide:{effect: "explode",duration: 1000},
   		show:{effect: "clip",duration: 1000}
		});
	});
	
	$('#btn4').click(function(){
		$('#pgpool').dialog({
			height:250,
			width:400,
			modal:true,
			buttons:{
	   			"Submit": function() {
	   			$('#pgpoolproperty').val('changetopgpool');
	   			saveData('changeallform');
	      			 },
	      		 "Cancel": function() {
	          	 $( this ).dialog( "close" );
	          	}
	   		}, 
	   		hide:{effect: "explode",duration: 1000},
	   		show:{effect: "clip",duration: 1000}
			});
		});
});

function editProperty(id,purpose,propname,fileval,memval){
     $('#pname').val(propname);
	 $('#pname').attr('readonly', 'readonly');
	 $('#pvalinfile').val(fileval);
	 $('#pvalinmem').val(memval);
	$('#data').dialog({
		height:300,
		width:600,
		modal:true,
		hide:{effect:"explode",duration:500},
		show:{effect:"clip",duration: 500},
		buttons:{
			"Submit":function(){
				saveData('editdata');
			},
			"Cancel":function(){
				$(this).dialog("close");
			}
		}
	});
}

function saveData(id){
	$.post("smtphostprops.jsp",$('#'+id).serialize(),
			function(data){
		     calloading();
			 window.location.reload();
	});
}

function calloading(){
	 $("#backgroundPopup").show();
	 $("#loading").show();
	// $('#loading').html("<center>Processing...<br><img src='/main/images/loading.gif'></center>");
	 document.getElementById('loading').style.cssText="top:50%;left:40%;background:#F5F5F5;float:left;padding: 0px;z-index: 100003;position:absolute;margin-top:-50px;";
}

</script>
