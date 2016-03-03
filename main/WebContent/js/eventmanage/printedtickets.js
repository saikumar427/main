var selectedAttendees=new Array();
var Notsold;
var Sold;
$(document).ready(function(){
	
	$(document).on('click','#createprintedtickets',function(){
		createNewPrintedtickets();
		setTimeout(function(){
			//$('#listcount').remove();	
		},500);
	});
	
	
	$(document).on('click','#saveprintedtickets',function(){
		savePrePrintedTickets();
	});
	
	$(document).on('click','#closeprintedtickets',function(){
		$('#dataDiv').html('');
	});
	
	$(document).on('ifChecked','.dataarr',function(){
		selectedAttendees.push($(this).val());
	});
	
	$(document).on('ifUnchecked','.dataarr',function(){
		selectedAttendees.splice($.inArray($(this).val(), selectedAttendees),1);
	});

	$(document).on('click','#markassold',function(){
		makeAsSold();
	});
	
	
	$(document).on('click','#delete',function(){
		deleteTransactions();
	});
	
	
	
});


function createNewPrintedtickets(){
	$('#loadingimage').show();
	var url='PrintedTickets!getPrintedTickets?eid='+eid+'&isrecur='+isrecur;
	 $.ajax({url:url})
     .done(function(result){
    	// if(!isValidActionMessage(result)) return;
    	 $('#loadingimage').hide();
    	 $("#dataDiv").html(result);
    	 $('#listid').val('');
         $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow'); 

        /* $('.tktsapplicable').iCheck({
        	 checkboxClass: 'icheckbox_square-grey', 
        	 radioClass: 'iradio_square-grey' 
        });*/
     })
     .complete(function(){});
}


function savePrePrintedTickets(){
	alert($('#selecteddateval').val());
	$('#loadingimage').show();
	$('#saveprintedtickets').prop('disabled',true);
	$.ajax({
		url:'PrintedTickets!savePrePrintedTicket',
		data:$('#printedticketsform').serialize(),
		type:'POST',
		success:function(result){
			if(!isValidActionMessage(result)) return;
	        if(result.indexOf("errorMessage")>-1){
	        	$('#loadingimage').hide();
	        	$('#saveprintedtickets').prop('disabled',false); 
	        	$('#printStatusMsg').hide();
	       		$('#preprinterrors').show().html(result);
	        }else{
	           if(result.indexOf("statusMessageBox")>-1){
	        	   $('#selecteddate').val($('#selecteddateval').val());
	        	   $('#preprinterrors').hide();
	        	   $('#printStatusMsg').show().html(result);
	        	 } 
	           $('#selecteddate').val($('#selecteddateval').val());
	            result=JSON.parse(result);
				jsondata=result;
				var listid=result.listid;
				selectedAttendees=new Array();
				generatePrintedTicketsTable(result);
	        	$('#dataDiv').html('');
	        	setTimeout(function(){
	        		$('#loadingimage').hide();
	        		window.location.href='PrintedTickets!downloadPdf?eid='+eid+'&listid='+listid;
	        	},1000);
	        	 
	        }
		}
	});
}

function makeAsSold(){
	if(selectedAttendees.length>0){
		var url='PrintedTickets!changeToAssigned?eid='+eid+'&attendees='+selectedAttendees+'&listid='+_listId;
		if(document.getElementById('selecteddate')){
			var evtDate=document.getElementById('selecteddate').value;
			url='PrintedTickets!changeToAssigned?eid='+eid+'&attendees='+selectedAttendees+'&isrecur='+isrecur+"&selecteddate="+evtDate+'&listid='+_listId;;
		}
		$('.modal-title').html('Mark As Sold');
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height", "150px");
		$('#myModal').modal('show');
	}else
		bootbox.alert("Select atleast one ticket to assign");
	
}

function deleteTransactions(){
	if(selectedAttendees.length>0){
		var url='PrintedTickets!deleteAttendees?eid='+eid+'&attendees='+selectedAttendees+'&isrecur='+isrecur;
		if(document.getElementById('selecteddate')){
			var evtDate=document.getElementById('selecteddate').value;
			url='PrintedTickets!deleteAttendees?eid='+eid+'&attendees='+selectedAttendees+'&isrecur='+isrecur+"&selecteddate="+evtDate;
		}
		$.ajax({
			url:url,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				/*result=JSON.parse(result);
				jsondata=result;*/
				selectedAttendees=new Array();
				/*$('#tabl1').dataTable().fnDestroy(false);
				$('#tabl2').dataTable().fnDestroy(false);
				generatePrintedTicketsTable(result);*/
				
				editThisPrint(_listId);
				
				
			}
		});
		}else
			bootbox.alert("Select atleast one ticket to delete");
		
}


function generateTickets(){
	selectedAttendees=new Array();
	var date=document.getElementById('selecteddate').value;
	if(date!='0'){
	$('#loadingimage').show();
	$('#dataDiv').html('');
	var url='PrintedTickets!generateRecurringEventPDFTickets?eid='+eid+'&selecteddate='+date+'&isrecur='+true;
	$.ajax({
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			result=JSON.parse(result);
			jsondata=result;
			selectedAttendees=new Array();
			$('#recurcontent').show();
			generatePrintedTicketsTable(result);
			//alert("loan::"+JSON.stringify(result,undefined,2));
			$('#loadingimage').hide();
			$('#notSoldHead').show();
		}
	});
	}else{
		var Obj={"printedList":[]};
		generatePrintedTicketsTable(Obj);
		$('#dataDiv').html('');
		$('#recurcontent').hide();
	}
	
}


var _listId;

function editThisPrint(listid){
	_listId=listid;
/*	$('#loadingimage').show();
	var url='PrintedTickets!getPrintedTickets?eid='+eid+'&isrecur='+isrecur;
	 $.ajax({url:url})
     .done(function(result){
    	// if(!isValidActionMessage(result)) return;
    	 $('#loadingimage').hide();
    	 $("#dataDiv").html(result);
    	 $('#listid').val('');
         $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow'); */
	
	
	var url='PrintedTickets!getPrintedListDetails?eid='+eid+'&listid='+listid;
	 $("#dataDiv").html('');
	 $('#loadingimage').show();
	$.ajax({
		url:url,
		success:function(result){
		    	 $('#loadingimage').hide();
		    	 $("#dataDiv").html(result);
		}
	});
}

function downloadPdf(listid,eid){
	window.location.href='PrintedTickets!downloadPdf?eid='+eid+"&listid="+listid;
}


function generatePrintedTicketsTable(jsonData){
	/*var notSoldLn=jsonData.notassigned.length;
	var soldLn=jsonData.assigned.length;*/
//	$('#tab1body').html('');
	//$('#tab2body').html('');
	$('#printedtickets').html('');
	
	
	var data='<table class="table" id="printedData"><thead><th>Ticket Name</th><th>Agent Code</th><th>Prefix</th><th>From</th><th>To</th><th>Manage</th></thead><tbody>';
	if(jsonData.printedList.length<=0){
		data+='<tr><td></td><td></td><td>No Data</td><td></td><td></td><td></td></tr>';
	}
	else{
	$(jsonData.printedList).each(function (keys,values){
		data+='<tr id='+values.discountId+'><td>'+values.ticketname+'</td><td>'+values.agentcode+'</td><td>'+values.prefix+'</td><td>'+values.startsfrom+'</td><td>'+values.ends+'</td>';                                 
	
		data+='<td><a onclick="editThisPrint(\''+values.listid+'\');" style="cursor:pointer;" >Details</a>&nbsp;&nbsp;<a  onclick="downloadPdf(\''+values.listid+'\',\''+eid+'\');" style="cursor:pointer;">Get Tickets</a></td></tr>';
		//	data+='<td>&nbsp;<a href="javascript:;" onclick=editDiscount(\''+values.discountId+'\','+eid+')>Edit</a>&nbsp; &nbsp;<a href="javascript:;" onclick=deleteDiscount(\''+values.discountId+'\','+eid+')>Delete</a></td></tr>';
			});
	data+='</tbody></table>';
	}
	$('#printedtickets').html(data);
	
	
	
	
	/*$.each(jsonData,function(outkey,resObj){	
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
			 alert("hi htis:::"+ $('#'+tab+' .table .'+tab+'body'));
			 $('#'+tab+' .table .'+tab+'body').html('<tr><td></td><td></td><td>No Data</td><td></td></tr>');                                  
		 }
 		
 	});*/
	
	/*$('input.dataarr').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey' 
		
			});
	
	
	if(jsonData.notassigned.length>0){
		$('#tabl1').dataTable().fnDestroy(false);
		Notsold=$('#tabl1').dataTable( {
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
			   	 Sold=$('#tabl2').dataTable( {
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
			  	
			      if(soldLn<=5)
			  		removePagination('tabl2');*/
	
}


function downloadpdf(){
	var eid=document.getElementById('evtid').value;
	window.location.href='PrintedTickets?eid='+eid+'&download=yes';
}
