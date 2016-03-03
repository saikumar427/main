<%@taglib uri="/struts-tags" prefix="s"%>
<style>
    .panel-footer{
    background: url(/main/images/buttons_bg.png) repeat-x;
    }
     
</style>
<script type="text/javascript"
	src="ManageTrackingPartner!populateTrackURLList?trackid=${trackid}"></script>
<s:form name="managetrackingPartnerform" id="managetrackingPartnerform">
<s:hidden name="trackid" ></s:hidden>
	<s:iterator value="indivualTrackingpartnerList"
		var="TrackingPartnerData" status="indivualTrackingpartnerListstatus">
		<s:set name="trackcode"
			value="%{#TrackingPartnerData.getTrackname()}"></s:set>
				
					<div class="well">
						Manage Tracking Partner <b><s:property value="%{#TrackingPartnerData.getTrackname()}" /></b>
						<span class="pull-right">
							<a href="home">Back to My Settings Home</a>
						</span>
					</div>
				
<div class="row">
<div class="col-md-12">
									<div class="panel panel-primary">
		                               <div class="panel-heading">
		                                <h3 class="panel-title">Partner Events</h3>
		                                </div>
		                                <div class="panel-body">
				                               
											<div class="tab-pane active" id="boxofficetab1">
											<table class="table table-hover" id='boxofficetabl1' >
											<thead>
											<tr>
											<th><b>Event Name</b></th>
											<th>Status</th>
											</tr>
											</thead>
											<tbody id="eventslist">
											<tr  id="nodata">
												<td>No Data</td>
												<td></td>
											
											</tr>
											</tbody>
											</table>
											</div>
											
		                                </div> 
		                                 <div class="panel-footer" ><a href="javascript:;" class="btn btn-primary" id='myothereventsbtn'
										>Add to my other events</a></div>                               
		                            </div>
		                            <br/>
		                            
		                            <div class="panel panel-primary">
		                               <div class="panel-heading">
		                                <h3 class="panel-title">Status</h3>
		                                </div>
		                                <div class="panel-body">
		                                <s:set name="stat" value="%{#TrackingPartnerData.getStatus()}"></s:set>
		                                <div class="col-md-2 col-sm-4">Status:</div>
		                                <div class="col-md-7 col-sm-5">
		                                
		                                 <s:hidden value="%{#TrackingPartnerData.getStatus()}" id="hiddentrackstatus"></s:hidden>
		                                <span id="trackstatus"><s:if test="%{#stat == 'Suspended'}">Suspended</s:if><s:else>Approved</s:else></span>
		                                
		                                
		                                </div>
		                                <div class="col-md-3 col-sm-3"> 
		                            <%--   <s:if test="%{#stat == 'Suspended'}"> --%>
					  <input type="button"  class="btn btn-primary" id="changestatus" value="<s:if test="%{#stat == 'Suspended'}">Activate</s:if><s:else>Suspend</s:else>"/>
			
				<input type="button"  class="btn btn-primary" id="delete" value="Delete"/>
		                                </div> 
		                                <div class="col-sm-2"></div>
		                                <div class="col-sm-7 sub-text">
		                                	Suspending tracking URL redirects all traffic to the main URL, resulting no visits and ticket sales credited to the tracking URL
		                                </div><div class="clo-sm-3"></div>
		                            </div>
								</div>
								<br/>
								
								<div class="panel panel-primary">
		                               <div class="panel-heading">
		                                <h3 class="panel-title">Manage URL</h3>
		                                </div>
		                                <div class="panel-body">
		                                <div class="col-md-2 col-sm-2">Manage URL:</div>
		                                <div class="col-md-8 col-sm-8">
		                                
		                                 <s:hidden value="%{#TrackingPartnerData.getPassword()}" id="hiddentrackpassword"></s:hidden>
		                                 <a href="<s:property value='manageURL' /><span id='trackpassword'><s:property value='%{#TrackingPartnerData.getTrackname()}'/></span>" target="_blank"><s:property value="manageURL" /><span id="trackpassword"><s:property value="%{#TrackingPartnerData.getTrackname()}"/></span></a>
		                                 
		                     <%-- <s:property value="manageURL" /><span id="trackpassword"><s:property value="%{#TrackingPartnerData.getTrackname()}"/></span> --%>
		                                </div>
		                                <div class="col-md-2 col-sm-2"> 
		                              
					  <input type="button"  class="btn btn-primary" id="password" value="Password"/>
		                                </div> <div class="col-sm-2"></div>
		                                <div class="col-sm-8 sub-text">
		                                	Partners/Affiliates can visit this URL to view reports, set photo, and post message. You can protect this page with password, and share it with Partner/Affiliate
		                                </div><div class="col-sm-2"></div>
		                            </div>
								</div>
								<br/>
								
								<div class="panel panel-primary">
		                               <div class="panel-heading">
		                                <h3 class="panel-title">Photo</h3>
		                                </div>
		                                <div class="panel-body">
		                                <div class="col-md-2 col-sm-2">Photo:</div>
		                                <div class="col-md-8 col-sm-8">
		                             <s:hidden value="%{#TrackingPartnerData.getPhotoURL()}" id="hiddentrackphoto"></s:hidden> 
		                              <span id="trackphoto">   
		                                <s:if test="%{#TrackingPartnerData.getPhotoURL()!=''}">
            							  <img src='<s:property value="%{#TrackingPartnerData.getPhotoURL()}" />'
												width="200">
             </s:if></span>
		                                </div>
		                                <div class="col-md-2 col-sm-2"> 
					  <input type="button"  class="btn btn-primary" id="addphoto" value="Add/Change"/>
		                                </div> 
		                            </div>
								</div>
								<br/>
								
								<div class="panel panel-primary">
		                               <div class="panel-heading">
		                                <h3 class="panel-title">Message</h3>
		                                </div>
		                                <div class="panel-body">
		                                <div class="col-md-2 col-sm-2">Message:</div>
		                                <div class="col-md-8 col-sm-8">
		                                <s:hidden value="%{#TrackingPartnerData.getMessage()}" id="hiddentrackmsg"></s:hidden>
		                            <span id="trackmsg">  <s:property value="%{#TrackingPartnerData.getMessage()}" /></span>
		                                </div>
		                                <div class="col-md-2 col-sm-2"> 
					  <input type="button"  class="btn btn-primary" id="addmessage" value="Add/Change"/>
		                                </div> 
		                            </div>
								</div>
								
								
								
</div>
</div>
</s:iterator>
</s:form>
  <script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script>


function rebildtracktable(result)
{
	var TrackURLDetails=JSON.parse(result);
	$('#eventslist').html('');
	$('#nodata').remove();
	$.each(TrackURLDetails.TrackURLDetails,function(inkey,valueobj){
		var trackpartners='<tr style="cursor:pointer" onclick="gotoEventManagePage('+TrackURLDetails.TrackURLDetails[inkey].eventid+');"><td>'+TrackURLDetails.TrackURLDetails[inkey].eventname+'</td>'+
		               '<td><a>'+TrackURLDetails.TrackURLDetails[inkey].status+'</a></td>'+
		               '</tr>';
		               
		$('#eventslist').append(trackpartners);
	});
}

$(document).ready(function(){
	buidTrackEvents();
});



function buidTrackEvents()
{        
	 if(TrackURLDetails.TrackURLDetails.length>0)
		$('#nodata').remove();
	
	$.each(TrackURLDetails.TrackURLDetails,function(inkey,valueobj){
		var trackpartners='<tr style="cursor:pointer" onclick="gotoEventManagePage('+TrackURLDetails.TrackURLDetails[inkey].eventid+');"><td>'+TrackURLDetails.TrackURLDetails[inkey].eventname+'</td>'+
		               '<td><a>'+TrackURLDetails.TrackURLDetails[inkey].status+'</a></td>'+
		               '</tr>';
		$('#eventslist').append(trackpartners);
	});
}



$('#changestatus').click(function(){
	var tid='${trackid}';
	$('#onefieldtextoption').hide();
	var defval='';
	var trackstatus=$('#hiddentrackstatus').val();
	if(trackstatus=='Approved'){
		trackstatus='Suspended';
	}
	else{
		trackstatus='Approved';
	}
	var lable='Changing status to '+trackstatus+' <br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner';
	
	openSinglePopUpWithOptions($(this).offset(),changestatussuccess,cancel,trackstatus,lable,defval);
});

$('#delete').click(function(){
	//bootbox.confirm('Clicking on submit will delete Tracking Partner at global level. If this tracking partner is assigned to any events, partner will not be removed from those events.<br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner', function (result) {	   });
	var tid='${trackid}';
	$('#onefieldtextoption').hide();
	var defval='';
	
	var lable='Clicking on submit will delete Tracking Partner at global level.<br>If this tracking partner is assigned to any events, partner will not be removed from those events.<br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner';
	openSinglePopUpWithOptions($(this).offset(),deletesuccess,cancel,'',lable,defval);
});


$('#password').click(function(){
	$('#onefieldtextoption').show();
	var tid='${trackid}';
	var lable='<br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner';
	var defval='';
	var trackpassword=$('#hiddentrackpassword').val();
	openSinglePopUpWithOptions($(this).offset(),passwordsuccess,cancel,trackpassword,lable,defval);
});


$('#addphoto').click(function(){
	$('#onefieldtextoption').show();
	var tid='${trackid}';
	var lable='<br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner';
	var defval='';
	var trackphoto=$('#hiddentrackphoto').val();
	openSinglePopUpWithOptions($(this).offset(),photosuccess,cancel,trackphoto,lable,defval);
	
});


$('#addmessage').click(function(){
	$('#onefieldtextoption').show();
	var tid='${trackid}';
	var lable='<br/><input type="checkbox" id="allevents">&nbsp;Apply to all events of this partner';
	var defval='';
	var trackmsg=$('#hiddentrackmsg').val();
	openSinglePopUpWithOptions($(this).offset(),msgsuccess,cancel,trackmsg,lable,defval);
});

var deletesuccess=function(value){
	var tid='${trackid}';
	var ischecked = document.getElementById('allevents').checked;
	var url = "ManageTrackingPartner!deleteTrackPartnerSuccess?trackid="
		+ tid + "&checked=" + ischecked;
			$.ajax({
			method:'POST',
			url:url,
			success: function( result ) {
				window.location.href='../mysettings/home';
					}
						});
	
}

var msgsuccess=function(value){
	var tid='${trackid}';
	var ischecked = document.getElementById('allevents').checked;
	var url = "ManageTrackingPartner!changeTrackPartnerMessage?trackid="
		+ tid + "&message=" + value + "&checked=" + ischecked;

$.ajax({
method:'POST',
url:url,
success: function( result ) {
		$('#trackmsg').html(value);
		$('#hiddentrackmsg').val(value);
		$('#singleValuePopUpOptions').hide();
		$('#notification-holder').html('');
		if($('#hiddentrackmsg').val()==''){
			//alert('No Message');
		}else{
			$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			notification("Message Updated.","success");
		}
		}
			}); 
	
}



var photosuccess = function(value){
	var tid='${trackid}';
	var ischecked = document.getElementById('allevents').checked;
	var url = "ManageTrackingPartner!changeTrackPartnerPhotoURL?trackid="
			+ tid + "&photoURL=" + value + "&checked=" + ischecked;

$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
		$('#trackphoto').html('<img src="'+value+'" width="200">');
		$('#hiddentrackphoto').val(value);
		$('#singleValuePopUpOptions').hide();
		$('#notification-holder').html('');
		if($('#hiddentrackphoto').val()==''){
			$('#trackphoto').html('');
			//alert('No Photo');
		}else{
			$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			notification("Photo Updated.","success");
		}
		
			}
				});
}


var passwordsuccess=function(value){
	var tid='${trackid}';
	var ischecked = document.getElementById('allevents').checked;
	var url = "ManageTrackingPartner!changeTrackPartnerPassword?trackid="
			+ tid + "&password=" + value + "&checked=" + ischecked;

$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
		$('#hiddentrackpassword').val(value);
		$('#singleValuePopUpOptions').hide();
		$('#notification-holder').html('');
		if($('#hiddentrackpassword').val()==''){
			//alert('NO Password');
		}else{
			$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			notification("Manage URL Password Updated.","success");
		}
		}
	});
};


var changestatussuccess = function(value){
	var tid='${trackid}';
	var ischecked = document.getElementById('allevents').checked;
	var url = "ManageTrackingPartner!changeTrackPartnerStatus?trackid="
			+tid+"&status="+value+"&checked="+ischecked;
	$.ajax({
		method:'POST',
		url:url,
		success: function( result ) {
			$('#hiddentrackstatus').val(value);
			if(value=='Suspended'){
				$('#changestatus').attr({"value":"Activate"});
				$('#trackstatus').html('Suspended');
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification("Status Suspended.","success");
			}else{
				$('#changestatus').attr({"value":"Suspend"});
				$('#trackstatus').html('Approved');
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification("Status Approved.","success");
			}
			
			$('#singleValuePopUpOptions').hide();
				}
					});
};


function cancel(){
	
} 


$('#myothereventsbtn').click(function(){
	var trackid = '${trackid}';
	var url = 'ManageTrackingPartner!getMyOtherEvents?trackid=' + trackid;
	$('.modal-title').html('My Other Events');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","310px"); 
	});
	$('#myModal').modal('show');
});


var trackcode="<s:property value='trackcode'/>";

function gotoEventManagePage(eventid){
	window.location.href="../eventmanage/TrackURL!manageTrackURL?eid="+eventid+"&trackcode=<s:property value='trackcode'/>";
}



var eventsRowLength=$('#boxofficetabl1 tr').length;

$('#boxofficetabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
     bFilter:false,
   // "bPaginate":false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false }
                ] ,
    "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');},
    "fnDrawCallback": function(oSettings) {
        if (eventsRowLength <= 5) {
          // $('#boxofficetabl1_paginate').hide();
           removePagination('boxofficetabl1');
       } 
   }
} );

</script>
<style type="text/css" title="currentStyle">
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>