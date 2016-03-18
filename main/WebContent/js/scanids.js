var sdataTable;
$('#scanids').click(function(){
	  var html='<div class="well"><div class="alert alert-danger" id="errormsg" style="display:none;">Please Enter Scan IDs</div><div class="row"><div class="col-md-10 bottom-gap"><input type="text" placeholder="'+props.sid_enter_sid_ph+'" class="form-control placeholder"'+
	            ' name="scanid" id="scanId"></div><div class="col-md-2"> <span class="pull-right"> <button class="btn '+
	             'btn-primary" id="confirmscanid" type="button"><b>'+props.sid_add_popup_btn_lbl+'</b></button> <button class="btn btn-cancel" id="cancelscanbox" type="button"><i class="fa fa-times"></i></button>'+
	             '</span></div> </div></div>';                              
	             $('#loading').hide();                          
		$('#loadedData').html(html);
		$('html, body').animate({ scrollTop: $("#cancelscanbox").offset().top-scrollTo}, 1000);
		$( "#scanId" ).focus();
		//$('html, body').animate({ scrollTop: $("#scanId").height()+200}, 500);	  
});

$(document).ready(function(){
	$(document).on('click','#cancelscanbox',function(){
	$('html, body').animate({ scrollTop: $("#scanids").offset().top-200}, 1000);
       $('#loadedData').html('');
		});
$(document).on('click','#confirmscanid',function(){

if($.trim($('#scanId').val())==''){
	$('#errormsg').html(props.sid_pls_enter_scanid_errmsg);
	$('#errormsg').show();
	return;
}

var value=$('#scanId').val();

var url = "ScanIDs!insertScanID?eid="+eventid+"&name="+value;
$.ajax({
    url : url,
    type: 'POST',
    success: function(transport) {
    	if(!isValidActionMessage(transport)) return;
      var result=transport;
      if(result.indexOf("Name Exists")>-1){
          $('#errormsg').html(props.sid_pls_enter_new_scanid_errmsg);
          $('#errormsg').show();
      }
      else if(result.indexOf("Invalid")>-1){ 
          $('#errormsg').html(props.global_enter_valid_text_msg);  
          $('#errormsg').show();
    	}
      else if(result.indexOf("spacesInUrl")>-1){
          $('#errormsg').html(props.global_use_alphanumeric_chars_msg);
          $('#errormsg').show();
      }
      else{
    	  $('#errormsg').hide();
           reloadData();
           $('html, body').animate({ scrollTop: $("#scanids").offset().top-scrollTo}, 1000);
           notification(props.sid_scanid_addedd_msg,'success');
    	 // createScanidsView({'name':value});
      }
   }
});
	
});

	 $(document).on('mouseover','.trhover',function(){
			$(this).find('.delete').css("display","block"); 
		 });
		 
		 
		  $(document).on('mouseout','.trhover',function(){
			$(this).find('.delete').css("display","none"); 
		 });
	

		  createScanIdtable();
});


function createScanIdtable(){
	var datahtml='<table class="table table-responsive table-hover" id="scandata">';
    $.each(data.citems,function(key,eachItem){
   	datahtml+='<tr class="trhover"><td><div class="row"><div class="col-md-6">'+eachItem.scanid+'</div><div class="col-md-6 delete sm-font" style="display:none;"><a href="javascript:;" onclick=deleteScan(\''+eachItem.scanid+'\','+eachItem.id+')>'+props.global_delete_lnk_lbl+'</a></div></div></td></tr>';
         });
	datahtml+='</table>';
	$('#scandatadiv').html(datahtml);
	generateDataTable();
}


function reloadData(){
var url='ScanIDs!reloadedData?eid='+eventid;
	$.ajax({
           url:url,
           success:function(result){
data=JSON.parse(result);
createScanIdtable();
$('#loadedData').html('');

generateDataTable();
               }
	   });
	
}


function generateDataTable(){
	if(data.citems.length>0){
		sdataTable=$('#scandata').dataTable({
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,
	        "bLengthChange": false,
	        "aoColumns": [
	                      {"bSortable":false}
	                    ] ,
	         "bFilter": false     
	    });	
		$('#scandata thead').remove();
		}else{
		var datahtml='<table id="scandata" class="table table-responsive table-hover"><tbody><tr class="trhover"><td><div class="not-found">'+props.sid_no_ids_to_display+'</div></td></tr></tbody></table>';
		$('#scandatadiv').html(datahtml);
		}
    
		if(data.citems.length<=5){			removePagination('scandata');}
		if(data.citems.length==6){
			$('#scandata_br').remove();
			$('<div id="scandata_br" style="height:34px"></div>').insertAfter($('#scandatadiv'));
			}
	
}




function deleteScan(scanid,eventid){	
	 var url='ScanIDs!deleteScan?scanid='+scanid+'&eid='+eventid;
	 var dynatimestamp = new Date();
	  url += '&dynatimestamp='+dynatimestamp.getTime();
     var agree=props.sid_scanid_delete_msg;	
     bootbox.confirm('<h3>'+props.sid_del_scanid_confirm_msg+'</h3>'+agree, function (result) {
    	 	if (result){	
    	 		$('#loading').show();
    		$.ajax({
    				url : url,
    			  type: 'get',
    			  success: function(t) {    				   				
    				  if(!isValidActionMessage(t)) return;
    				  data=JSON.parse(t);
    				  createScanIdtable();
    				  $('#loadedData').html('');
    				  generateDataTable();
    				  $('#loading').hide();
    		      	
    		      	notification(props.sid_scanid_deleted_msg,'success');
    				//$('html, body').animate({ scrollTop: $("#statusMsg").height()}, 1000);
    				//statusMessage("statusMsg", props.sid_scanid_deleted_msg,"success");
    		      }
    		});	
    	}

    	 });
}


function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}