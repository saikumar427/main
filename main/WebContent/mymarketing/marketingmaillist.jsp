<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="/main/js/marketing/marketingmaillist.js"></script>

<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="listid" id="blastid"></s:hidden>
<div class="container">
	<div class="row">
		<div class="col-md-11">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Marketing MailList</h3>
				</div>
				<div class="panel-body">					
						
								<div class="tab-pane active table-responsive" id="tab2">
									<div class='table-responsive'>
										<table class="table table-hover" id='tabl2'>
											<thead>
												<tr>
													<th>ID</th>
													<th>Name</th>
													<th></th>
												</tr>
											</thead>
											<tbody class='nodata'></tbody>
										</table>
									</div>
								</div>
												
				</div>
				<div class="panel-footer">
					<button class="btn btn-primary" id="createEventGrpBtn">Create
						New MailList</button>
					<span class="pull-right panel-footer-link">&nbsp;<a href="javascript:;"
						id="unsubscribeemail">Unsubscribe Email</a>&nbsp;
					</span>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
var jsonobj=${mailingListJson};

function createMailList(){
var url= 'home!createMailList?userId='+${userId};
purpose='maillist';
$('.modal-title').html('Bulk Upload');
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",url);
$('iframe#popup').css("height","400px"); 
});
$('#myModal').modal('show');
}

function editMailList(listid){
	var url='home!editMailList?listid='+listid;
	purpose='maillist';
	$('.modal-title').html('Bulk Upload');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","400px"); 
	});
	$('#myModal').modal('show');
}

$('#unsubscribeemail').click(function(){
     unsubscribeEmail();
});

$(function(){
	$.each(jsonobj.list,function(inkey,valueobj){  
		var managedata ='<a href="javascript:;" onClick=editMailList('+valueobj.listid+');>Edit</a>&nbsp&nbsp<a href="javascript:;"  onClick=parent.deleteMailList('+valueobj.listid+');>Delete</a>';
		var tempentry=$('<tr><td>'+valueobj.listid+'</td><td>'+valueobj.name+'</td><td>'+managedata+'</td></tr>');            
        $('#tabl2 .nodata').remove();
         $('#tabl2').append(tempentry);
   });
	
	$('#createEventGrpBtn').click(function() {
		createMailList();
	});
	
	$('#tabl2').dataTable( {
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "bFilter":false,
        "aoColumns": [null,
                      null,
                     { "bSortable": false },
                     ] ,
        "aaSorting": [[0, "desc" ]]          
    });

	if(jsonobj.list.length<=5)
		removePagination('tabl2');  
});	
</script>
