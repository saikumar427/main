<%@taglib uri="/struts-tags" prefix="s"%>
<div class="wellddd" style="padding:19px;">
		<div class="row">
			<div class="col-md-12">

<div id="preprinterrors" style="display:none;" class="alert alert-danger"></div>
<div id="printStatusMsg" style="display:none;"></div>

 <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab1" data-toggle="tab">Not Sold (<label class='tab1Count'>0</label>)</a></li>
                                <li><a href="#tab2" data-toggle="tab">Sold (<label class='tab2Count'>0</label>)</a></li>
                            </ul>
                        
                        
                        <div class="tab-content" id="tabcontent"> 
                                <div class="tab-pane active table-responsive" id="tab1">
                                    <table class="table table-hover" id='tabl1' width="100%">
                                        <thead id="notSoldHead" >
                                            <th></th>
                                            <th>Ticket Name</th>                                            
                                            <th>Serial No</th>
                                             <th>Agent Code</th>
                                        </thead>
                                        <tbody id="tab1body">
                                         <tr class='nodata' id="tab1nodata"> 
                                             <td></td>    
                                             <td></td>                                      
                                             <td>No Data</td> 
                                             <td></td>
                                             </tr>                                            
                                        </tbody>
                                    </table>
<div style="clear:both"></div>
							<span class="buttons-div">
						<button class="btn btn-primary" id="markassold">Mark As Sold</button>
						<button class="btn btn-primary" id="delete">Delete</button>
						 </span> 

                                </div>
                                <div class="tab-pane table-responsive" id="tab2">
                                    <table class="table table-hover" id='tabl2' width="100%">
                                        <thead id="soldHead" >
                                            <th></th>
                                            <th>Ticket Name</th>                                            
                                            <th>Serial No</th>
                                             <th>Agent Code</th>
                                        </thead>
                                        <tbody id="tab2body">   
                                        <tr class='nodata' id="tab2nodata">   
                                          <td></td>    
                                          <td></td>                                      
                                             <td colspan='2'>No Data</td> 
                                             <td></td>
                                             </tr>
                                        </tbody>
                                    </table>
                                </div>
                                
                                
                                
                            </div>
                            </div>
				
				
				
				
			</div>
		</div>


<script>

var json=${jsonData}


var notSoldLn=json.notassigned.length;
var soldLn=json.assigned.length;
$('#tab1body').html('');
$('#tab2body').html('');


$.each(json,function(outkey,resObj){	
	 var tab=outkey=="notassigned"?"tab1":"tab2";
	 $('.'+tab+'Count').text(resObj.length);	
	 
	$.each( resObj, function( inkey, valueobj ) {
		var tempentry='<tr>';
		if(tab=='tab1')
		tempentry+='<td><input type="checkbox" onchange=callMethod(this); value='+valueobj.attendeekey+' name='+valueobj.attendeekey+' data-id='+valueobj.attendeekey+' class="dataarr"></td>';
		else
		tempentry+='<td></td>'; 				
		tempentry+='<td >'+valueobj.ticketname+'</td><td >'+valueobj.internalkey+'</td><td >'+valueobj.agentcode+'</td></tr>';
		$('#'+tab+' .table').append(tempentry);
	});
	
	if(resObj.length==0){ 
		 $('#'+tab+' .table .'+tab+'body').html('<tr><td></td><td></td><td>No Data</td><td></td></tr>');                                  
	 }

});
	$('input.dataarr').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey' 
			});


	if(json.notassigned.length>0){
		$('#tabl1').dataTable().fnDestroy(false);
	var	Notsold=$('#tabl1').dataTable( {
	           "sPaginationType": "full_numbers",
	           "iDisplayLength":5,
	           "bLengthChange": false,
	           "bFilter": false, 
	           'bAutoWidth': false ,
	           "bSortable": false,
	            "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
	       } );
	   	}
		else
			$('.table #tab1body').html('<tr><td></td><td></td><td>No Data</td><td></td></tr>');      
		
		if(notSoldLn<=5)
			removePagination('tabl1');



		if(soldLn>0){
			  $('#tabl2').dataTable().fnDestroy(false);
			 var Sold=$('#tabl2').dataTable( {
			            "sPaginationType": "full_numbers",
			            "iDisplayLength":5,
			            "bLengthChange": false,
			            "bFilter": false, 
			            'bAutoWidth': false , 
			            "bSortable": false,
			            "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
			        } );
			      }
			      else
			    	$('.table #tab2body').html('<tr><td></td><td></td><td>No Data</td><td></td></tr>');  


		$('#tabl2').css('width','');
	  	
			      if(soldLn<=5)
			  		removePagination('tabl2');

		
	 
	







</script>


