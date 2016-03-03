<meta name="viewport" content="width=device-width, initial-scale=1">
<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="eventslist!populateEventsPage"></script>
<script type="text/javascript" src="eventslist!populateEventThemesList"></script>
<script type="text/javascript" src="eventslist!populateTrackingPartnersList"></script>
<script type="text/javascript" src="eventslist!populateEventGroupsList"></script>
    <style>
     .panel-footer{
    background: url(/main/images/buttons_bg.png) repeat-x;
    } 
 
    </style>  
        
           <!--  <div class="row"> -->
            <div class="col-md-8">
					          <div class="panel panel-primary">
					          <div class="panel-heading">
					                 <h3 class="panel-title">My Box Office&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="boxofficeinfo" title="This is your Box Office URL. You can display all your events or customize what events to display on this page. This URL automatically detects where user is coming from to render UI accordingly, for example if user is visiting from iPad, UI displayed is iPad specific!"></i></h3>
					          </div>
					              	 <div class="panel-body">	
					              	 	<div class="row"> 				                 	
											<div class="tab-pane active" id="boxofficetab1">																							
													<div  id="boxoffice"></div>						
											</div>
										</div>
					           		</div>                                
					            </div>
					            
					            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">My Events Streamer</h3>
                                </div>
                                <div class="panel-body">
                                   Display your events anywhere on the web. Copy and paste	the following code into your website, blog or forums.
                                    <br/>
                                   <s:textarea rows="3" cols="65" onClick="this.select()"
							name="streamerCodeString" theme="simple" onClick="this.select()"  readonly="true" cssClass="form-control"></s:textarea>
                                </div> 
                                 <div class="panel-footer" ><a id='customizeStreamerlink'
					href='ManageStreamer' class="btn btn-primary">Customize Look and Feel</a></div>                               
                            </div>
					            
					        <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">My Event Themes&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="themesinfo" title="Create custom event page themes to match up with your website look & feel.
To start with, click on Create Theme button, enter your look & feel settings and click on Submit button. This creates your new theme, you can further customize look & feel by editing theme's HTML & CSS.
You can apply the theme you created to all your events and save time customizing your future events"></i></h3>
                                </div>
                                <div class="panel-body">
                                   
									<div class="tab-pane active" id="eventthemestab1">
									<table class="table table-hover" id='eventthemestabl1' >
									<thead>
									<tr>
									<th><b>Name</b></th>
									<th></th>
									<th></th>
									</tr>
									</thead>
									<tbody  id="eventthemes">
										<tr  id="nodata">
											<td>No Data</td>
											<td></td>
											<td></td>
										</tr>
									</tbody>
									</table>
									</div>
							
                                </div>   
                                <div class="panel-footer" ><a href='javascript:createTheme();' class="btn btn-primary">Create Theme</a></div>                              
                            </div>     
					            
					    
					    		<div class="panel panel-primary">
                                	<div class="panel-heading">
                                   		 <h3 class="panel-title">My Tracking Partners&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="trackpartnerinfo" title="Create Tracking Partners with unique name at account level here, and with one click you can create Tracking URLs for all (or selected) Tracking Partners at event level."></i></h3>
                                	</div>                                
                                   	<div class="tab-content">
										<div class="tab-pane active" id="trackpartnertab1">
											<table class="table table-hover" id='trackpartnertabl1' >
												<thead>
													<tr>
														<th><b>Name</b></th>
														<th></th>
													</tr>
												</thead>
												<tbody id="trackparners">
													<tr id="nodata"><td >No Data</td><td></td></tr>
												</tbody>
											</table>
										</div> <br> 
									</div>                                 
                                	<div class="panel-footer" ><a id='createTrackPartner' href="javascript:;" class="btn btn-primary"> Create Tracking Partner</a></div>                              
                        		
                        		 <s:form class="form-horizontal" name="createtrackingpartnerform"
	action="CreateTrackingPartner!save" id="createtrackingpartnerform"
	method="get">
        <input type="hidden" name="mgrId" value="<s:property value="%{mgrId}"/>"/>
         <input type="hidden" id="trackpartnercode" name="trackingPartnerData.trackname">
        </s:form>
                        		
                        		
                        		</div> 
					    
					            
					            
					            
					            
                        </div>
                        <div class="col-md-4">                              
							<div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">My Event Groups&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="groupeventinfo" title="Combine multiple events into a single group with a common front page having links to register for each individual event.
Example: Event that needs multiple currency payments, create individual event with specific currency setting, combine them into one group to present single view to the attendee."></i></h3>
                               </div>                               
                                   			<div class="tab-content">
												<div class="tab-pane active" id="evtgroupstab1">
													<table class="table table-hover" id='evtgroupstabl1' >
														<thead>
															<tr>
																<th><b>Name</b></th>
																<th>Events Count</th>
																<th></th>
															</tr>
														</thead>
													<tbody id="eventgroups">
														<tr  id="nodata">
															<td>No Data</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													</table>
												</div>
											</div>                                  
                                     		<div class="panel-footer" ><a id='createeventgroups' href="#" class="btn btn-primary">Create Event Group</a></div>                        
                            </div>                                
                            <div class="panel panel-primary" >
                                <div class="panel-success">
                                    <span class="panel-text">My Referral Links &nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="myReferralLink" title="Earn 15% in referral fees for each new customer sign up from your referral links!"></i></span>
                                </div>
                                <div class="panel-body">
                                   URL: Publish following URL on your website, emails and social networks
                            
							<textarea name="referralURL"
							cols="25" rows="2" readonly="readonly"
							onClick="this.select()" class="form-control">${serveraddress}/signup/${userName }</textarea>
							<br/>
							Button: Copy and paste the following code into your website or blog
							<textarea class="form-control" cols="25" rows="7" readonly="readonly"  onClick="this.select()"><a href="${serveraddress}/signup/${userName }"><img border="0" src="${serveraddress}/main/images/referral.jpg" /></a></textarea>
                            	</div>                                
                            </div>                                                     
                            </div>
                            
                            
                            
                          
<!-- </div> -->
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>

<script>


$(document).ready(function(){
	
	$('#boxofficeinfo').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });	
	
	$('#themesinfo').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });	
	
	$('#trackpartnerinfo').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });	
	
	$('#groupeventinfo').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });	
	$('#myReferralLink').tooltipster({
		fixedWidth:'100px',
	    position: 'top'
	});
	
	var rowCount = $('#trackparners tr').length;
	$('#trackpartnertabl1').dataTable({
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "aoColumns": [{ "bSortable": false },
                      { "bSortable": false }
                    ] ,
         "bFilter": false,
    });
	
		if(rowCount <= 5)
			removePagination('trackpartnertabl1');
		
		var rowCount = $('#eventthemes tr').length;
		$('#eventthemestabl1').dataTable({
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "aoColumns": [{ "bSortable": false },
                      { "bSortable": false },                                 
                      { "bSortable": false }
                    ] ,
         "bFilter": false,
    });
	
		if(rowCount <= 5){
			removePagination('eventthemestabl1');
		}
});






var mgrId='${mgrId}';
$(function(){
	$('#createTrackPartner').click(function() {
		openSinglePopUp($(this).offset(),success,cancel,'');
		/* $('.modal-title').html('Tracking Partner');
		$('#myModal').on('show.bs.modal', function () {
		var url = 'CreateTrackingPartner?mgrId='+mgrId;
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","180px"); 
		});
		$('#myModal').modal('show'); */
		});

$('#createeventgroups').click(function(){
	$('.modal-title').html('Event Group Details');
	$('#myModal').on('show.bs.modal', function () {
	var url = 'CreateEventGroup!addEventGroup?a=1';
	$('iframe#popup').attr("src",url);
	});
	$('#myModal').modal('show');
});


$('#boxofficetabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    "bPaginate":false,
    bInfo: false,
    bFilter:false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false }
                ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
} );



/* $('#eventthemestabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    bFilter:false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false },                                 
                  { "bSortable": false }
                ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');},
                "fnDrawCallback": function(oSettings) {
                    if ($('#eventthemestabl1 tr').length < 5) {
                        $('.dataTables_paginate').hide();
                    }
                }
} ); */


/* $('#trackpartnertabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    bFilter:false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false }
                ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');},
                "fnDrawCallback": function(oSettings) {
                    if ($('#trackpartnertabl1 tr').length < 5) {
                        $('.dataTables_paginate').hide();
                    }
                }
} );
 */

$('#evtgroupstabl1').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    bFilter:false,
    "bPaginate":false,
    bInfo: false,
    "aoColumns": [{ "bSortable": false },
                  { "bSortable": false },
                  { "bSortable": false }
                ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
} );



});
$.each(jsondata.eventsPageURL,function(inkey,valueobj){ 
	var tempentry='<div class="col-md-10"> <a target="_blank" href="'+jsondata.eventsPageURL[0].eventsPageURL+'">'+ jsondata.eventsPageURL[0].eventsPageURL   +'</a></div><div class="col-md-2"><a href="CustomizeEventsPageTheme">'+props.myo_settings_link+'</a></div>';
		  $('#boxoffice').append(tempentry);
	 });

$.each(eventThemes.themes,function(inkey,valueobj){
	var themes='<tr><td>'+eventThemes.themes[inkey].themename+'</td>'+
				   '<td><a href="eventslist!themeTemplates?themeid='+eventThemes.themes[inkey].themeid+'">'+eventThemes.themes[inkey].htmlcss+'</a></td>'+
	               '<td><a href="eventslist!editUserTheme?action=edit&themeid='+eventThemes.themes[inkey].themeid+'">'+eventThemes.themes[inkey]["look&feel"]+'</a></td>'+
	               '</tr>';
	               $('#eventthemestabl1 #nodata').remove();
	$('#eventthemestabl1').append(themes);
});

$.each(trackingpartnerData.TrackingPartnerDetails,function(inkey,valueobj){
	var trackpartners='<tr onclick="manageTrackPartner('+trackingpartnerData.TrackingPartnerDetails[inkey].TrackId+');"><td>'+trackingpartnerData.TrackingPartnerDetails[inkey].Name+'</td>'+
	               '<td><a style="cursor:pointer">Manage <span class="glyphicon glyphicon-chevron-right"></span>   </a></td>'+
	               '</tr>';
	               $('#trackpartnertabl1 #nodata').remove();
	$('#trackpartnertabl1').append(trackpartners);
});

$.each(evtgroupsData.groups,function(inkey,valueobj){
	var trackpartners='<tr onclick="manageEventGroups('+evtgroupsData.groups[inkey].id+');"><td>'+evtgroupsData.groups[inkey].title+'</td>'+
	               '<td style="width:5%">'+evtgroupsData.groups[inkey].count+'</td>'+
	               '<td ><a style="cursor:pointer">Manage <span class="glyphicon glyphicon-chevron-right"></span></a></td>'+
	               '</tr>';
	              $('#evtgroupstabl1 #nodata').remove();
	$('#evtgroupstabl1').append(trackpartners);
});

function closediv(){
	$('#myModal').modal('hide');
	window.location.reload();
}

function manageTrackPartner(trackid){
	window.location.href='ManageTrackingPartner?trackid='+trackid;
}

function manageEventGroups(groupid){
	window.location.href='ManageGroup?gid='+groupid;
}
function createTheme(){
	var url = 'eventslist!addUserTheme';
	window.location.href = url;
}

var success=function(value){
	$('#trackpartnercode').val(value);
	//alert(JSON.stringify($('#trackpartnercode').val(value)));

$.ajax( {
	   type: "GET",
	   url: 'CreateTrackingPartner!save',
	   data: $("#createtrackingpartnerform").serialize(),
	   success: function( result ) {
	   	   if(result.indexOf('success')>-1){
	   	   	var a = result.split("_");
	   	 row = $("<tr onclick='manageTrackPartner("+a[1]+");'></tr>");
	     col1 = $("<td>"+value+"</td>");
	     col2 = $("<td><a>Manage <span class='glyphicon glyphicon-chevron-right'></span></a></td>");
	     row.append(col1,col2).prependTo("#trackparners");  
	     $('#singleValuePopUp').hide();
				initializeTrackPartnerTable();
		   	   }else{
	   		$('#singleerror').show();
  				$('#singleerror').html(result);
	   	   }
	   }});
};
  var cancel=function(){
};


function initializeTrackPartnerTable(){
	
	 var newRow = $('#trackparners tr').length;
	$('#trackpartnertabl1').dataTable().fnDestroy(false);
	
	$('#trackpartnertabl1').dataTable({
	    "sPaginationType": "full_numbers",
	    "iDisplayLength":5,
	    "bLengthChange": false,
	    "aoColumns": [{ "bSortable": false },
	                  { "bSortable": false }
	                ] ,
	     "bFilter": false,
	});
	if(newRow <= 5)
		removePagination('trackpartnertabl1');
	
}





	function closeeventspopup(){
	    	$('#myModal').modal('hide');
	    }
	function rebuildEVTable()
	{
		
		$('#myModal').modal('hide');
		var url='eventslist!rebuildEventsGroupTable?mgrId='+mgrId;
		
		$.ajax({
			type:"GET",
			url: url,
			success: function(jsonresult)
				{
				
			var	 result=JSON.parse(jsonresult);
				$('#eventgroups').html('');
				
				 $.each(result.groups, function(inkey, valueobj) {
					 
							var trackpartners = '<tr onclick="manageEventGroups('
									+ result.groups[inkey].id
									+ ');"><td>'
									+ result.groups[inkey].title
									+ '</td>'
									+ '<td style="width:5%">'
									+ result.groups[inkey].count
									+ '</td>'
									+ '<td ><a style="cursor:pointer">Manage <span class="glyphicon glyphicon-chevron-right"></span></a></td>'
									+ '</tr>';
							$('#eventgroups #nodata').remove();
							$('#eventgroups').append(trackpartners);
						}); 
				
				
				}   /* end of success */
				});   /* end of ajax */
				
	}



</script>
<style type="text/css" title="currentStyle">
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>
