
<!-- <script type="text/javascript" src="marketingmanage!populateListData?templateId=${templateId}"></script> -->
<script src="/main/js/marketing/marketingschedules.js" language="JavaScript" type="text/javascript"></script>

<div class="container">
<div class="row">
<div class="col-md-11">
<div class="panel panel-default">
<div class="panel-body">
<ul class="nav nav-tabs">
<li class="active"><a href="#tab1" data-toggle="tab">Scheduled (<label class='tab1Count'>0</label>)</a></li>
<li><a href="#tab2" data-toggle="tab">Running (<label class='tab2Count'>0</label>)</a></li>
<li><a href="#tab3" data-toggle="tab">Completed (<label class='tab3Count'>0</label>)</a></li>
</ul>
<div class="tab-content" style="border: 0px solid #FFF">
	<div class="tab-pane active table-responsive" id="tab1">
		<div class='table-responsive'>
			<table class="table table-hover" id='tabl1'>
				<thead>
				<th>ID</th>
				<th>Name</th>                                            
				<th>Date</th>
				<th></th>
				</thead>
				<tbody class='nodata'></tbody>
			</table>
		</div>
	</div>




<div class="tab-pane  table-responsive" id="tab2" >
		<div class='table-responsive'>
			<table class="table table-hover" id='tabl2'>
				<thead>
				<th>ID</th>
				<th>Name</th>                                           
				<th>Date</th>
				<th>Sent Count</th>
				<th>Start Time</th>
				</thead>
				<tbody class='nodata'></tbody>
			</table>
		</div>
	</div>
<div class="tab-pane  table-responsive" id="tab3">
		<div class='table-responsive'>
			<table class="table table-hover" id='tabl3'>
				<thead>
				<th>ID</th>
				<th>Name</th>                                            
				<th>Date</th>
				<th>Sent Count</th>
				<th>Start Time</th>
				<th>End Time</th>
				</thead>
				<tbody class='nodata'></tbody>
			</table>
		</div>
	</div>
</div>
</div>
<div class="panel-footer">
<button class="btn btn-primary" id="createschedulelink">Create Schedule</button>
</div>
</div>
</div></div></div>

<script>
var data=${jsonData};
var templateid=${templateId};
$(function(){
	$.each(data,function(outkey,resObj){
		var tab=outkey=="SCHEDULED"?"tab1":outkey=="RUNNING"?"tab2":"tab3";
		$('.'+tab+'Count').text(resObj.length);
		var tempentry='';
		$.each( resObj, function( inkey, valueobj ) {				
		 if(tab=='tab1'){
			var manage='&nbsp;<a href="../mymarketing/marketingmanage!createSchedule?templateId='+templateid+'&schId='+valueobj.schid+'">Edit</a>&nbsp&nbsp <a href="javascript:;" onClick=deleteTemaplteSchedule('+templateid+','+valueobj.schid+');>Delete</a>'; 
			tempentry=$('<tr><td>'+valueobj.schid+'</td><td>'+valueobj.schname+'</td><td>'+valueobj.schdate+'</td><td>'+manage+'</td></tr>');	
		 }else if(tab=='tab2')
			 tempentry=$('<tr><td>'+valueobj.schid+'</td><td>'+valueobj.schname+'</td><td>'+valueobj.schdate+'</td><td>'+valueobj.sent_count+'</td><td>'+valueobj.starttime+'</td></tr>');	 
		 else if(tab=='tab3')
			 tempentry=$('<tr><td>'+valueobj.schid+'</td><td>'+valueobj.schname+'</td><td>'+valueobj.schdate+'</td><td>'+valueobj.sent_count+'</td><td>'+valueobj.starttime+'</td><td>'+valueobj.lastsenttime+'</td></tr>');	
           $('#'+tab+' .table .nodata').remove();
			$('#'+tab+' .table').append(tempentry);
			
		});
	});
	
	
	$('#createschedulelink').click(function(){
		window.location.href='/main/mymarketing/marketingmanage!createSchedule?templateId='+${templateId};
	});

	$('#tabl1').dataTable( {
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "bFilter":false,
        "aoColumns": [null,
                      null,
                      null,
                      { "bSortable": false }
                    ] ,
        "aaSorting": [[0, "desc" ]]
    } );
	$('#tabl2').dataTable( {
       "sPaginationType": "full_numbers",
       "iDisplayLength":5,
       "bLengthChange": false,
       "bFilter":false,
       "aoColumns": [null,
                     null,  
                     null,
                     { "bSortable": false },
                     { "bSortable": false }
                   ] ,
       "aaSorting": [[0, "desc" ]]
   } );
	$('#tabl3').dataTable( {
       "sPaginationType": "full_numbers",
       "iDisplayLength":5,
       "bLengthChange": false,
       "bFilter":false,
       "aoColumns": [null,
                     null,
                     null,
                     { "bSortable": false },
                     { "bSortable": false },
                     { "bSortable": false }
                   ] ,
       "aaSorting": [[0, "desc" ]]
   } );
	
	if(data.SCHEDULED.length<=5)
		removePagination('tabl1');  
	if(data.RUNNING.length<=5)
		removePagination('tabl2');  
	if(data.COMPLETED.length<=5)
		removePagination('tabl3');  
	
});	


function deleteTemaplteSchedule(tempid,schid){
	bootbox.confirm('Schedule will be deleted from this list for ever. This operation cannot be revert back.', function (result) {
        if (result){
        	var url='../mymarketing/marketingmanage!deleteSchedule?schId='+schid;
           	$.ajax({
           		type: "POST",
                url:url,
                success:function(result){
                	window.location.reload();
                },failure:function(){
  		  		  alert("fail");
 		  	   }
           	});
        }
    });
}

</script>