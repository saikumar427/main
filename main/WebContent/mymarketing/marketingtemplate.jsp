<%@taglib uri="/struts-tags" prefix="s"%>
<div class="container">
	<div class="row">
		<div class="col-md-11">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Marketing Templates</h3>
				</div>
				<div class="panel-body">
					<div>
						<div class="tab-pane active" role="tabpanel" id="tab1">
							<div class='table-responsive'>
								<table class="table table-hover" id='tabl1'>
									<thead>
										<tr>
											<th>ID</th>
											<th>Name</th>
											<th>Subject</th>
											<th>From</th>
											<th>Notes</th>
											<th>Created At</th>
											<th></th>
										</tr>
									</thead>
									<tbody class='nodata'></tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
				<div class="panel-footer">
					<button class="btn btn-primary" id="createtempBtn">Create
						New Template</button>
				</div>
			</div>
		</div>
	</div>
</div>	

<script>
var userid=${userId};
var json=${templateJson};
$(function(){
	$.each(json.tempaltes,function(inkey,valueobj){  
		var temp1='<a href=" ../mymarketing/marketingmanage!templateInfo?templateId='+valueobj.tempid+'">';
    	var temp2='<a href="javascript:;" onclick=parent.sendTestEmail('+valueobj.tempid+');>';
    	var temp3='<a href="javascript:;" onclick=parent.editTemplate('+valueobj.tempid+');>';
    	var temp4='<a href="javascript:;" onClick=parent.deleteTemplate('+valueobj.tempid+');>';
    	var managedata = temp1+'Show&nbsp;Schedules</a>&nbsp;&nbsp;&nbsp;&nbsp;'+temp2+'Test&nbsp;Email</a>&nbsp;&nbsp;<br/><br/>&nbsp;&nbsp;'+temp3+'Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;'+temp4+'Delete';
		var tempentry=$('<tr><td>'+valueobj.tempid+'</td><td>'+valueobj.name+'</td><td>'+valueobj.subject+'</td><td>'+valueobj.from+'</td><td>'+valueobj.notes+'</td><td>'+valueobj.createdat+'</td><td>'+managedata+'</td></tr>');            
        $('#tabl1 .nodata').remove();
         $('#tabl1').append(tempentry);
   });
	
	$('#createtempBtn').click(function() {
		addTemplate();
	});
	
	$('#tabl1').dataTable( {
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "bFilter":false,
        "aoColumns": [null,
                      null,                                 
                      { "bSortable": false },
                      { "bSortable": false },
                      { "bSortable": false },
                      null,
                      { "bSortable": false },
                    ] ,
        "aaSorting": [[0, "desc" ]]
                  
    });
	
	if(json.tempaltes.length<=5)
		removePagination('tabl1');  
});
</script>





