<%@page import="java.util.Properties,java.io.*,java.util.Map,java.util.HashMap,java.util.ArrayList"%>
<%@page import="java.util.Set,java.util.Iterator,com.eventbee.filters.DsnProperty"%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">

<style>
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

<%!
 private void writeIntoFile(HashMap fileMap){
	String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
	try{
	FileOutputStream writer = new FileOutputStream(fileconfproppath+"/dbdsn.ebeeprops");
	writer.write(new String().getBytes());
	Set keys = fileMap.keySet();
	for (Iterator i = keys.iterator(); i.hasNext();)   {
       String key = (String) i.next();
       String value = (String) fileMap.get(key);
       String content=key+"="+value+"\n";
     	writer.write(content.getBytes());
   }
writer.close();
}catch(Exception e){}
}
%>

<%
HashMap FileMap=DsnProperty.getFileMap();
HashMap MemoryMap=(HashMap)DsnProperty.getAllProperties();
String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
String action=request.getParameter("action");
String sink=request.getParameter("sink");
String deleteproperty=request.getParameter("deleteproperty");
String changepgpool=request.getParameter("changepgpool");

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
	writeIntoFile(FileMap);
}
}

	if("sinkwithfile".equals(sink)){
		MemoryMap.clear();
		MemoryMap.putAll(FileMap);
}

	if("sinkwithmemory".equalsIgnoreCase(sink)){
		FileMap.clear();
		FileMap.putAll(MemoryMap);
		writeIntoFile(FileMap);
}

if("changetopgpool".equalsIgnoreCase(changepgpool) && changepgpool!=null){
	String changeinfile=request.getParameter("chnginfile").trim();
	String chnageinmem=request.getParameter("chnginmem").trim();
	
	if(!"".equals(chnageinmem)){
	Set keys = MemoryMap.keySet();
	for (Iterator i = keys.iterator(); i.hasNext();)   {
       String key = (String) i.next();
       MemoryMap.put(key,chnageinmem); 	
	}
	}
	
	if(!"".equals(changeinfile)){
	FileOutputStream writer = new FileOutputStream(fileconfproppath+"/dbdsn.ebeeprops");
	writer.write(new String().getBytes());
	Set keyes = FileMap.keySet();
		for (Iterator i = keyes.iterator(); i.hasNext();)   {
	       String key = (String) i.next();
	       String value = (String) FileMap.get(key);
	       String content=key+"="+changeinfile+"\n";
	     	writer.write(content.getBytes());
	   }
	writer.close();
	FileMap=DsnProperty.getFileMap();
	}
	}

if("editsave".equalsIgnoreCase(action)){	
	String pvalinfile=request.getParameter("pvalinfile");
	String pvalinmem=request.getParameter("pvalinmem");
	String propname=request.getParameter("propname");
	
	FileMap.put(propname,pvalinfile);
	writeIntoFile(FileMap);
	if(!"".equals(propname.trim()))
		MemoryMap.put(propname,pvalinmem);
	
}

	ArrayList arrlist=new ArrayList();
	Set keys = FileMap.keySet();
	int j=0;
	for (Iterator i = keys.iterator(); i.hasNext();)    {
	   String key = (String) i.next();
	   arrlist.add(j,key);
	   j++;
    }
	Set memkey=MemoryMap.keySet();
	for(Iterator k=memkey.iterator();k.hasNext();){
		String memorykey=(String) k.next();
		if(!arrlist.contains(memorykey)){
		arrlist.add(j,memorykey);
      j++;
	}
	}
%>
 <%
	if(session.getAttribute("authDatauttool")==null){
		response.sendRedirect("login.jsp?usereq=dbdsnprops");
		return;
	}
%>

<html>
<title>DSN Change</title>
<body>
<div class="ui-widget" >
<h1>DB DSN Properties</h1>

<table id="users" class="pure-table" width="80%">
<thead>
<tr class="ui-widget-header"" >
<th width="35%" nowrap="nowrap">Property Name</th>
<th width="15%" nowrap="nowrap">File Value</th>
<th width="15%" nowrap="nowrap">Memory Value</th>
<th width="15%" nowrap="nowrap">Action</th>
</tr>
</thead>
<tbody>
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

<tr id='<%=i%>' class='<%=css%>'>
<td width="35%"><%=arrlist.get(i)%></td>
<td width="15%"><%=filetemp%></td>
<td width="15%"><%=memtemp%></td>
<td width="15%"><a href="#" onclick="editProperty('<%=i%>','<%=arrlist.get(i)%>','<%=FileMap.get(arrlist.get(i))%>','<%=MemoryMap.get(arrlist.get(i))%>')">Edit</a>&nbsp;|
<a href="#" onclick="deleteProperty('<%=i%>','<%=arrlist.get(i)%>','<%=FileMap.get(arrlist.get(i))%>','<%=MemoryMap.get(arrlist.get(i))%>')">Delete</a>
</td>
</tr>	
<%}%>

</tbody>
</table><br/>
</div>
<div id="backgroundPopup" class="backgroundPopup" style="display:none"></div>
<div id="loading"></div>

<table>
<tr><td><button id="btn1">Add New Property</button></td>
<td><button id="btn2" onclick="sink('file');">Sync With File</button></td>
<td><button id="btn3" onclick="sink('memory');">Sync With Memory</button></td>
<td><button id="btn4">Change All</button></td>
</tr>
</table>
<br/><br/>
<div id="data" title="Add/Edit Property" style="display:none;padding-left:18px;">
<form name="editdata" method="post" id="editdata">
<p class="validateTips" style="font-size:15px; ">All form fields are required.</p>
<table width="100%">
<tr><td><div><span><b>Add/Edit Property</b></span></div></td></tr>
<tr height="12"><td></td></tr>
<tr>
<td>Property Name :</td><td> <input type="text" size="30" id="pname" name="propname"></td></tr>
<tr height="5"><td></td></tr>
<tr>
<td>Property Value in File:</td><td><select id="pvalinfile" name="pvalinfile">
<option value="PostgresDS">PostgresDS</option>
<option value="Lb1DbCheck">Lb1DbCheck</option>
<option value="Lb2DbCheck">Lb2DbCheck</option>
</select>
</td></tr>
<input type="hidden" value="editsave" name="action">
<tr height="10"><td></td></tr>
<!-- <tr><td><input type="checkbox" name="filechk" checked="checked">Add to File</td>
<td><input type="checkbox" name="memorychk" checked="checked">Add to Memory</td>
</tr>-->
<tr>
<td>Property Value in Memory:</td><td><select id="pvalinmem" name="pvalinmem">
<option value="PostgresDS">PostgresDS</option>
<option value="Lb1DbCheck">Lb1DbCheck</option>
<option value="Lb2DbCheck">Lb2DbCheck</option>
</select>
</td></tr>
</table>
</form>
</div>

<div id="delete" title="Delete Property" style="display:none">
<form name="deleteprop" method="post" id="deleteprop">
<table width="94%">
<tr height="10"><td></td></tr>
<tr><td><div><span><b>Delete Property :</b></span>&nbsp;<span id="propname"></span></div></td></tr>
</table>
<table width="100%">
<tr height="10"><td></td></tr>
<tr><td><input type="checkbox" id="filechk" name="filedelete" checked="checked">&nbsp;Delete from File</td></tr>
<tr><td><input type="checkbox" id="memchk" name="memorydelete" checked="checked">&nbsp;Delete from Memory 
<tr height="10"><td>
<input type="hidden" value="" name="deleteproperty" id="deleteproperty">
</td></tr>
</table>
</form>
</div>

<div id="pgpool" style="display:none" title="Change All">
<form name="pgpoolprop" method="post" id="changeall">
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

<td>Property Value in File:</td><td>
<input type="hidden" value="" name="changepgpool" id="pgpoolproperty">
<select id="chnginfile" name="chnginfile">
<option value="">--Select--</option>
<option value="PostgresDS">PostgresDS</option>
<option value="Lb1DbCheck">Lb1DbCheck</option>
<option value="Lb2DbCheck">Lb2DbCheck</option>
</select>
</td></tr>

<tr height="10"><td></td></tr>
<tr>
<td>Property Value in Memory:</td><td><select id="chnginmem" name="chnginmem">
<option value="">--Select--</option>
<option value="PostgresDS">PostgresDS</option>
<option value="Lb1DbCheck">Lb1DbCheck</option>
<option value="Lb2DbCheck">Lb2DbCheck</option>
</select>
</td></tr>
</table>
</form>
</div>
<div id="bg" class="popup_bg"></div> 
<form name="dummy" method="post">
<input type="hidden" name="sink" id="sink">
</form>
</body></html>

<script>
var temparr=new Array();
temparr='<%=arrlist%>';
temparr=temparr.replace("[","");
temparr=temparr.replace("]","");

var proparr=new Array();

var a = temparr.split(","); 
for (var i = 0; i < a.length; i++){
	proparr.push($.trim(a[i]));
}

$(document).ready(function(){
	
	$('#btn1').click(function(){
		$('.validateTips').html("All form fields are required.");
		 $('#pname').removeClass("ui-state-error");
		 $('#pname').val("");
		 $('#pname').removeAttr('readonly');
		$('#data').dialog({
			height: 300,
    		width:520,
    		modal: true,
    		buttons:{
    			"Submit": function() {
       				addOrEditSave('add',-1);
       			 
       			 },
       			 "Cancel": function() {
           			 $( this ).dialog( "close" );
           			 }
    		}});
	});
	
	
	$('#btn4').click(function(){
		$('#pgpool').dialog({
			height: 250,
    		width:365,
    		modal: true,
    		buttons:{
    			"Change": function() {
       				chnageAllTo();
       			 $( this ).dialog( "close" );
       			 },
       			 "Cancel": function() {
           			 $( this ).dialog( "close" );
           			 }
    		}});
		
	});
});

function addOrEditSave(type,id){
	 //$('#loading').html("loading.........");
	calloading();
	 var flag=true;
	 var pvalue=$.trim($('#pname').val());
	 $('#pname').val(pvalue);
	 flag=checkRegexp($('#pname'), /^[a-z]([0-9a-z_.])+$/i, "Property name is invalid." );
     if(flag==false){
    	 $("#backgroundPopup").hide();
    	 $("#loading").hide();
    	 return;
     }
     if(type=='add'){
    	var tempflag= checkexists($('#pname'),"Property Name Already Exists");
     	if(tempflag==false){
     		$("#backgroundPopup").hide();
     		 $("#loading").hide();
    	 return;
     	}
     }
     
	 $.post("dbdsnprops.jsp",$('#editdata').serialize(),
			function(data){
          window.location.reload();		
	});
}


function editProperty(id,propname,fileval,memval){
	$('.validateTips').html("All form fields are required.");
	$('#pname').removeClass("ui-state-error");
	 $('#pname').val(propname);
	 $('#pname').attr('readonly', 'readonly');
	 $('#pvalinfile').val(fileval);
	 $('#pvalinmem').val(memval);
	$('#data').dialog({
		height: 330,
		width:520,
		modal: true,
		buttons:{
			"Submit": function() {
   				addOrEditSave('edit',id);
   			 $( this ).dialog( "close" );
   			 },
   			 "Cancel": function() {
       			 $( this ).dialog( "close" );
       			 }
		}});
	
}


function deleteProperty(id,propname,fileval,memval){
	$('#propname').html('<b>'+propname+'</b>');
	$('#delete').dialog({height: 220,width:400,modal: true,
		buttons:{"Delete": function() {
   				deleteSave(id,propname);
   			 $( this ).dialog( "close" );
   			 },"Cancel": function() {
       			 $( this ).dialog( "close" );
       			 }
		}});
}

function deleteSave(id,propname){
	calloading();
	$('#deleteproperty').val(propname);
	$.post("dbdsnprops.jsp",$('#deleteprop').serialize(),
			function (data){
		window.location.reload();
	});
}


function chnageAllTo(){
	calloading();
	$('#pgpoolproperty').val('changetopgpool');
	$.post("dbdsnprops.jsp",$('#changeall').serialize(),
			function(data){
		window.location.reload();
		  });
}

function sink(val){
	if(val=='file'){
		var agree=confirm("Are you sure to sync with file");
		if(agree)
		document.getElementById('sink').value='sinkwithfile';
	    else
	    return false;
	}else if(val=='memory'){
	    var agree=confirm("Are you sure to sync with memory");
		if(agree)
		document.getElementById('sink').value='sinkwithmemory';
	    else
	    return false;
	}
	document.dummy.submit(); 
}

var tips = $(".validateTips");

function updateTips( t ) {
	tips.text( t ).addClass( "ui-state-highlight" );
	setTimeout(function() {
	tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
}

function checkRegexp( o, regexp, n ) {
	if ( !( regexp.test( o.val() ) ) ) {
	o.addClass( "ui-state-error" );
	updateTips( n );
	return false;
	} else {
	return true;
	}
}

function checkexists(o, msg) {
	var vals=$.inArray(o.val(),proparr);
	if (vals >-1) {
	o.addClass( "ui-state-error" );
	updateTips(msg);
	return false;
	} else {
	return true;
	}
}

function calloading(){
	 $("#backgroundPopup").show();
	 $("#loading").show();
	 $('#loading').html("<center>Processing...<br><img src='/main/images/loading.gif'></center>");
	 document.getElementById('loading').style.cssText="top:50%;left:40%;background:#F5F5F5;float:left;padding: 0px;z-index: 100003;position:absolute;margin-top:-50px;";
}


</script>
