<%@taglib uri="/struts-tags" prefix="s"%>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<div class="row">
<div class="row" style="margin-bottom:10px; margin-left:10px;">
    	<div style="float:left"><a href="home"> <i class="glyphicon glyphicon-chevron-left"></i><s:text name="cept.back.to.settings.lbl"></s:text></a></div>
    </div>
<div class="col-md-7">
	<div class="panel panel-primary">
					                <div class="panel-heading">
					                 <h3 class="panel-title"><s:text name=""></s:text></h3>
					                  </div>
					              	 <div class="panel-body">
									<div class="tab-pane active" id="evtgrptab1" >
									<div class="table-responsive">
									<table class="table table-hover" id='evtgrptabl1'>
									<thead>
									<tr>
									<th><b><s:text name="mg.event.name.header.lbl"></s:text></b></th>
									<th></th>
									</tr>
									</thead>
									<tbody id="evtgrp">
										<tr id="nodata"><td></td><td></td></tr>
									</tbody>
									</table>
									</div>
									</div>
							
					           </div> 
					           <div class="panel-footer" ><a id='editgroupevents' href="#" class="btn btn-primary"><s:text name="mg.edit.group.btn.lbl"></s:text></a>
					           &nbsp;&nbsp;<a id='deleteeventgroup' href="#" class="btn btn-primary"><s:text name="mg.delete.group.btn.lbl"></s:text></a>
					           </div>                                
					            </div>
					           
					            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="mg.event.group.url.th.lbl"></s:text></h3>
                                </div>
                                <div class="panel-body">
                                  <s:text name="mg.publish.url.lbl"></s:text>
                                    <br/><br/>
                                <span id="groupurl">   
                                <%-- <s:textfield readonly="true" name="eventGroupData.eventURL" size="60" onClick="this.select()" cssClass="form-control"></s:textfield> --%>
                                <s:text name="eventGroupData.eventURL"></s:text>
                                </span>
                                <a href="javascript:;" id="customurledit"><s:text name="global.edit.lnk.lbl"></s:text></a>
                                    <s:hidden value="%{eventGroupData.customURL}" id="customurl"></s:hidden>
                                <br/>
                                </div> 
                                 <div class="panel-footer" >
                                 <a id='editGrpThemeBtn'
					href='javascript:;' class="btn btn-primary"><s:text name="mg.customize.page.btn.lbl"></s:text></a>&nbsp;&nbsp;
					<a id='previewlink'
					href='javascript:;' class="btn btn-primary"><s:text name="global.preview.btn.lbl"></s:text></a>&nbsp;&nbsp;
					<!-- <a id='customurlbutton'
					href='javascript:;' class="btn btn-primary">Set Custom URL</a>&nbsp;&nbsp; -->
					
					</div>                               
                            </div>

</div>
<div class="col-md-5">
				<div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="mg.integration.link.th.lbl"></s:text></h3>
                                </div>
                                <div class="panel-body">
                                <a href='<s:text name="eventGroupData.eventURL"></s:text>' ><img border='0' src='../images/register.jpg'/></a><br/>
                                  <s:text name="mg.copy.paste.lbl"></s:text>
                                    <br/>
                                <textarea  readonly="true" class="form-control" cols="60" rows="5" onClick="this.select()"><a href="<s:text name='eventGroupData.eventURL'></s:text>" ><img border="0" src="${serveraddress}/home/images/register.jpg"/></a></textarea>
                                <br/>
                                <a href='<s:text name="eventGroupData.eventURL"></s:text>' ><img border='0' src='../images/buyticket.jpg'/></a><br/>
                                <s:text name="mg.copy.paste.lbl"></s:text><br/>
                                <textarea readonly="true" class="form-control" cols="60" rows="5" onClick="this.select()"><a href="<s:text name='eventGroupData.eventURL'></s:text>" ><img border="0" src="<s:text name='serveraddress'></s:text>/home/images/buyticket.jpg"/></a></textarea>
                                </div> 
                            </div>
                            
                            
                            
                            
</div>
</div>
<div id="content" style="display:none">


</div>

<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>

<script>

function closesingleEGpopup()
	{
	$('#myModal').modal('hide');
	}


var mgrid='${mgrId}';
var gid='${gid}';

var grpevtData=${jsonData};
$(function(){

$('#customurledit').click(function(){
	 openSinglePopUp($(this).offset(),success,cancel,$('#customurl').val());
});

	
$('#deleteeventgroup').click(function(){

	bootbox.confirm(props.mg_delete_popup_lbl, function (result) {
    	if (result){
    		var groupid='${gid}';
    		var url = 'CreateEventGroup!deleteEventGroup?eventgroupid='+groupid;
    		$.ajax({
    			method:'GET',
    			url:url,
    			success: function( result ) {
    				window.location.href='../mysettings/home';
    					}
    						});
       }
	});
});

$('#editgroupevents').click(function(){
	var groupid='${gid}';
	$('.modal-title').html(props.mg_group_details_heafer_lbl);
	$('#myModal').on('show.bs.modal', function () {
	var url = 'CreateEventGroup!editEventGroup?eventgroupid=' + groupid;
	$('iframe#popup').attr("src",url);
	/* $('iframe#popup').css("height","350px"); 
	$('.modal-dialog').css('width', '700px'); */
	});
	$('#myModal').modal('show');
});


$('#editGrpThemeBtn').click(function(){
	var groupid='${gid}';
	var url = 'ManageGroup!editGroupTheme?gid='+groupid;
	$('.modal-title').html(props.mg_customize_page_btn_lbl);
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	/* $('iframe#popup').css("height","450px"); 
	$('.modal-dialog').css('width', '750px'); */
	});
	$('#myModal').modal('show');
});

$('#previewlink').click(function(){
var url='${eventGroupData.eventURL}';
	//url='http://vasu1280.citypartytix.com/event?eid=100945';
	$('.modal-title').html('Preview');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	 $('iframe#popup').css("height","400px"); 
	$('.modal-dialog').css('width', '750px'); 
	});
	$('#myModal').modal('show');
});

});

/* <a style="cursor:pointer">Manage </a> */

$.each(grpevtData.events,function(inkey,valueobj){ 
	var tempentry='<tr onclick="manageEventGroups('+grpevtData.events[inkey].id+');"><td>'+grpevtData.events[inkey].name+'</td><td><a style="cursor:pointer">'+props.myo_my_tracking_partner_manage_lnk_lbl+' <span class="glyphicon glyphicon-chevron-right"></span></a></td>';
		 tempentry+="</tr>";  
		 $('#evtgrptabl1 #nodata').remove();
          $('#evtgrptabl1').append(tempentry);
	 });
	 
	 
	 function rebuildsingletable()
	 {
		 $('#myModal').modal('hide');
		 var url='ManageGroup!rebuildJson?gid='+gid;
		 $.ajax({
				type:"GET",
				url: url,
				success: function(jsonresult)
					{
					$('#evtgrptabl1').dataTable().fnDestroy(false);
						var	 grpevtData=JSON.parse(jsonresult);
						$('#evtgrp').html('');
					
						$.each(grpevtData.events,function(inkey,valueobj)
							{ 
								var tempentry='<tr onclick="manageEventGroups('+grpevtData.events[inkey].id+');"><td>'+grpevtData.events[inkey].name+'</td><td><a style="cursor:pointer">'+props.myo_my_tracking_partner_manage_lnk_lbl+' <span class="glyphicon glyphicon-chevron-right"></span></a></td>';
							 	tempentry+="</tr>";  
							 	$('#evtgrp #nodata').remove();
					          	$('#evtgrp').append(tempentry);
						 	});
						 datatablerebuild();   /* $('#evtgrptabl1').dataTable().fnDestroy(false);     */
					 }
		 });
	 }
	 
	 
	 
	 function datatablerebuild()
	 {
		 var rowCount = $('#evtgrp tr').length;
			$('#evtgrptabl1').dataTable( {
			    "sPaginationType": "full_numbers",
			    "iDisplayLength":5,
			    "bLengthChange": false,
			    bFilter:false,
			    "aoColumns": [{ "bSortable": false},
			                  { "bSortable": false}
			                ] ,
			                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
			               
			} );
			if(rowCount <=5)
				removePagination('evtgrptabl1');
	 }

$(document).ready(function(){
	var rowCount = $('#evtgrp tr').length;
	$('#evtgrptabl1').dataTable( {
	    "sPaginationType": "full_numbers",
	    "iDisplayLength":5,
	    "bLengthChange": false,
	    bFilter:false,
	    "aoColumns": [{ "bSortable": false},
	                  { "bSortable": false}
	                ] ,
	                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
	               
	} );
	if(rowCount <=5)
		removePagination('evtgrptabl1');

});


/* $('#evtgrptabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    bFilter:false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false }
                ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');},
                "fnDrawCallback": function(oSettings) {
                    if (eventsRowLength <= 6) {
                       $('#evtgrptabl1_paginate').hide();
                       removePagination('evtgrptabl1');
                   } 
               }
               
} ); */

function manageEventGroups(eventid){
	window.location.href='../eventmanage/Snapshot?eid='+eventid;
}

var success = function(result){
	$('#singleerror').hide();
	var userurl=$('#customurl').val();	
	var url="ManageGroup!setCustomURL?gid="+gid+"&mgrId="+mgrid+"&userurl="+userurl+"&customURL="+result;
	$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
		 if(result.indexOf("URL Exists")>-1){
			$('#singleerror').show();
			$("#singleerror").html(props.mg_invalid_url_msg_lbl);
		}
		else if(result.indexOf("Invalid")>-1){
			$('#singleerror').show();
			$("#singleerror").html(props.global_enter_valid_text_msg);
		}
		else if(result.indexOf("spacesInUrl")>-1){
			$('#singleerror').show();
			$('#singleerror').html(props.global_use_alphanumeric_chars_msg);
		}else{
			if(result.indexOf('newurl')>-1){
				var tempObj=JSON.parse(result);
				$('#singleValuePopUp').hide();
				$('#customurl').val(tempObj.newurl);
				$('#groupurl').html(tempObj.url);
				}
	}
			}
				});



	
}

var cancel = function(){
	
}


</script>
<style type="text/css" title="currentStyle">
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>
