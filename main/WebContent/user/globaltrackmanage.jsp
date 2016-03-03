<%@taglib uri="/struts-tags" prefix="s" %>
<style>
.taskheader{
<style>
.taskheader{
color:black;
padding: 0px;
}
</style>

<div style="padding:30px">
<div class="taskheader">Global Tracking Settings&nbsp;>&nbsp;<s:property value="%{trackcode}"/>
&nbsp;-&nbsp;Tracking URL</div><br/>
<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title">Manage URL</h3>
</div>
<div class="panel-body">
<div class="row">
<div class="col-md-10"><b>Manage URL:</b>&nbsp;<s:property value="%{eventURL}"></s:property></div>
<div class="col-md-2"><button class="btn btn-primary" id="trckpwd">Password</button></div>
</div>
<div class="row">
<div class="col-md-10">
<span>Partners/Affiliates can visit this URL to view reports, set photo, and post message. You can protect this page with password, and share it with Partner/Affiliate </span>
</div></div>                                    
</div></div>
<br/>
<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title">Photo</h3>
</div>
<div class="panel-body">
<div class="row">
<div class="col-md-10"><b>Photo:</b>&nbsp;<img src='<s:property value="photo"/>' width="200"></div>
<div class="col-md-2"><button class="btn btn-primary" id="addphotobtn">Add/Change</button></div>
</div>
</div></div>
<br/>
<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title">Message</h3>
</div>
<div class="panel-body">
<div class="row">
<div class="col-md-10"><b>Message:</b>&nbsp;<s:property value="message" /></div>
<div class="col-md-2"><button class="btn btn-primary" id="addmsgbtn">Add/Change</button></div>
</div>
</div></div>
<br/>
<s:form action="" name="" id="">
<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title">My Events</h3>
</div>
<div class="panel-body">
<div class="row">
<div class="col-md-12">
<div class="tab-content">
<div class="tab-pane active" id="tab1">
<table class="table" id='tabl1'>
<thead><tr>
<th>Name</th>
<th>Status</th>
<th>Visits</th>
<th>Registrations</th>                                             
<th></th>                                          
</tr></thead>
<tbody>
<tr class='nodata'> 
<td></td>                                           
<td colspan='2' >No Data</td> 
</tr>                                            
</tbody>
</table>
</div>
</div>
</div></div></div></div>
</s:form>
</div>
<script>
var userid='${userid}';
var trackcode='${trackcode}';
var scode='${secretcode}';
var data = '${jsondata}';
var jdata=eval('('+data+')');

function closediv(){
	$('#myModal').modal('hide');
	}
$(function(){
	
	$('#trckpwd').click(function() {
		$('.modal-title').html('Track Manage Password');
		var url="TrackUrlManage!getTrackPassword?userid="+userid+"&trackcode="+trackcode+"&secretcode="+scode;
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","200px"); 
		});
		$('#myModal').modal('show');
	});
	
	$('#addphotobtn').click(function() {
		$('.modal-title').html('Track Manage Photo');
		var url="TrackUrlManage!getTrackPhoto?userid="+userid+"&trackcode="+trackcode+"&secretcode="+scode;
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","200px"); 
		});
		$('#myModal').modal('show');
	});
	
	$('#addmsgbtn').click(function() {
		$('.modal-title').html('Track Manage Message');
		var url="TrackUrlManage!getTrackMessage?userid="+userid+"&trackcode="+trackcode+"&secretcode="+scode;
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","300px"); 
		});
		$('#myModal').modal('show');
	});
	
	$.each(jdata.tracksummary,function(inkey,valueobj){    
		  var tempentry=$('<tr><td>'+valueobj.name+'</td><td>'+valueobj.status+'</td><td>'+valueobj.visits+'</td><td>'+valueobj.registrations+'</td><td><a href="#" onclick=editManage(\''+valueobj.eventid+'\')>Manage</a>&nbsp;|&nbsp;<a href="#" onclick=editReports(\''+valueobj.eventid+'\')>Reports</a></td></tr>');            
          $(' .table .nodata').remove();
           $(' .table').append(tempentry);
           
      
   });
	
});

var isValidActionMessage=function(messageText){
	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
	    	alert('You need to login to perform this action');
	    	window.location.href="/main/user/login";
	    	return false;
	    }
	 else if(messageText.indexOf('Something went wrong')>-1){
	 		alert('Sorry! You do not have permission to perform this action');
	 		closediv();
	 		return false;
	 	} 
	    return true;	
	}


function submitform(locdata,locurl){
   $.ajax({
  	   type: "POST",
  	   url: locurl,
  	   data: locdata,
  	   success: function(response) {
	    if(!isValidActionMessage(response)) return;  
  		 window.location.href='TrackUrlManage!getGlobalTrackePage?userid='+userid+'&trackcode='+trackcode+'&secretcode='+scode;	  
  	   },failure:function(){
  		  alert("fail");
  	   }});
}


function editManage(eventid){
var url='TrackUrlManage!getManagePage?eid='+eventid+'&trackcode='+trackcode+'&secretcode='+scode;
//window.location.href=url;
window.open(url,"_blank");

}

function editReports(eventid){
var url='TrackUrlManage!getTrackUrlReports?eid='+eventid+'&trackcode='+trackcode+'&secretcode='+scode;
window.open(url,"_blank");
}
</script>

