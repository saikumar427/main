$(document).ready(function(){
	
	 $(document).on('click','.copylink',function(){
		//alert("hi this");
     	//this.select();
	//	copyToClipboard('http://www.eventbee.com/p/ganesh/event?eventid=151701494&code=HI');
     });
	
    
	 $(document).on('click','#discountsettings',function(){
	        $('#discountdetails').slideToggle("slow");
	    });
	 
	 $(document).on('mouseover','.trhover',function(){
		$(this).find('.tdhover').css("display","block"); 
	 });
	 
	 $(document).on('mouseout','.trhover',function(){
			$(this).find('.tdhover').css("display","none"); 
		 });
	 
	 
	 $(document).on('mouseover','.trcopylink',function(){
			$(this).find('.tdcopylink').css("display","block"); 
		 });
		 
		 $(document).on('mouseout','.trcopylink',function(){
				$(this).find('.tdcopylink').css("display","none"); 
			 });
	 
	 
	 $(document).on('click','#closediscount',function(){
	    	$('#dataDiv').html('');
	    	$("#dataDiv").css('margin-top','0px');
		 $('#edited').remove();
		 $('#adddiscount').prop("disabled",false);
		 $('html, body').animate({ scrollTop: $("#discounts").offset().top-200 },1000);
	 });
	 
	 $(document).on('click','#savediscount',function(){
		saveDiscount();
		$('#adddiscount').prop("disabled",false);
	 });
	 
	 
	 $(document).on('click','.toggle',function(){
		 var id=$(this).data('toggle');
		 
		 
		 var tktid=id.split('_');
		 
		 
		 if(tktid[0]=='codes'){
			 $('#tickets_'+tktid[1]).slideUp();
			 $('#dtickets_'+tktid[1]).removeClass('highlighted-text');
			 $('span[data-toggle="tickets_'+tktid[1]+'"]').addClass('original').removeClass('down');	
			 
			 if($('#codes_'+tktid[1]).is(':hidden')){
				 $('span[data-toggle="codes_'+tktid[1]+'"]').addClass('down').removeClass('original');	 
			 $('#codes_'+tktid[1]).slideDown(1000);
			 $('#dcodes_'+tktid[1]).addClass('highlighted-text');
			 }else{
				 $('span[data-toggle="codes_'+tktid[1]+'"]').addClass('original').removeClass('down');		 
			$('#codes_'+tktid[1]).slideUp();
			 $('#dcodes_'+tktid[1]).removeClass('highlighted-text');
			 }
		 }else{
			 $('#codes_'+tktid[1]).slideUp();
			 $('#dcodes_'+tktid[1]).removeClass('highlighted-text');
			 $('span[data-toggle="codes_'+tktid[1]+'"]').addClass('original').removeClass('down');	
			 
			 
			 if($('#tickets_'+tktid[1]).is(':hidden')){
				 $('span[data-toggle="tickets_'+tktid[1]+'"]').addClass('down').removeClass('original');
				 $('#tickets_'+tktid[1]).slideDown(1000);
				 $('#dtickets_'+tktid[1]).addClass('highlighted-text');
			 }else{
				 $('span[data-toggle="tickets_'+tktid[1]+'"]').addClass('original').removeClass('down');		
				$('#tickets_'+tktid[1]).slideUp(); 
				 $('#dtickets_'+tktid[1]).removeClass('highlighted-text');
			 }
		 }
		 
		 triggerCopy();
	 });
});

function saveDiscount(){
	$('#savediscount').prop("disabled",true);
	var enddate=$('#enddate').val().split(" ");
	$('#monthpart').val(enddate[0].split('/')[0]);
	$('#datepart').val(enddate[0].split('/')[1]);
	$('#yearpart').val(enddate[0].split('/')[2]);
	$('#hourspart').val(enddate[1].split(':')[0]);
	$('#minspart').val(enddate[1].split(':')[1]);
	$('#ampmpart').val(enddate[2]);
	
	
	$('#discountVal').val($('#searchBy').val());
	if($('#limittype').val()!="1"){
		$('#limittype').val($('#limittypeselect').val());
		if($('#limittype').val()=="2")
		$('#alllimitval').val($('#limitone').val());
			else
		$('#eachlimitval').val($('#limitone').val());
	}
		
	
	
	$.ajax({
		type:'POST',
		url:'DiscountDetails!save',
		data:$('#discountform').serialize(),
		success:function(result){
			$('#savediscount').prop("disabled",false);
			 if(!isValidActionMessage(result)) return;
			if(result.indexOf('errorMessage')>-1){
				$('#discounterrormessages').show().html(result);
				if(result.indexOf('Select Applicable Tickets')>-1){
					if($('#discountdetails').is(':hidden'))
					$('#discountsettings').trigger('click');
				}
			}else{
				$('#notification-holder').html('');
                $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
                if($('#discId').val()=='')
                notification(props.disc_added_status_msg,"success");
                else
                notification(props.disc_updated_status_msg,"success");	
                removeNotificationHeader();
				$('#closediscount').trigger('click');
				showProcessing('discounts');
				$('#adddiscount').prop("disabled",false);
				getReloadedDiscountData();
			}
		}
	});
}


function editDiscount(dicountId,eid){
	$('#dataDiv').html('');
	 $('#edited').remove();
	//showProcessing('discounts'); 	
	
	$('#tickets_'+dicountId).hide();
	$('#codes_'+dicountId).hide();
	$('span[data-toggle="tickets_'+dicountId+'"]').addClass('original').removeClass('down');
	$('span[data-toggle="codes_'+dicountId+'"]').addClass('original').removeClass('down');
	
	$('#dtickets_'+dicountId).removeClass('highlighted-text');
	$('#dcodes_'+dicountId).removeClass('highlighted-text');
	
	
	var temp='<tr id="edited"><td style="border-top:0px !important;"><div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	
	$(temp).insertAfter('#'+dicountId);
	
	var url='DiscountDetails!edit?eid='+eid+'&id='+dicountId;
	$.ajax({url:url})
    .done(function(result){
   	 if(!isValidActionMessage(result)) return;
   	 hideProcessing();
   	 	$('#loading').remove();
   	 	
   	 	$('#edited').html('<td style="border-top:0px !important;">'+result+'</td>');
   	 	
        $('html, body').animate({ scrollTop: $("#name").offset().top-100}, 1000);
        $("#dataDiv").css("display","block");
        $("#dataDiv").css("margin-top","16px !important");
        $('input[name="discountData.name"]').focus();
		 $('#adddiscount').prop("disabled",true);
		 $('#savediscount').html(props.global_save_btn);
    })
    .complete(function(){});
}

function deleteDiscount(discountId,eid){
	var url='ManageDiscounts!delete?eid='+eid+'&id='+discountId;
	bootbox.confirm(props.disc_delete_confirm_msg, function(result) {
		if(result){
			 $('#closediscount').trigger('click');
			 showProcessing('discounts'); 
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				// if(!isValidActionMessage(result)) return;
				$('#notification-holder').html('');
                $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
                notification(props.disc_delete_status_msg,"success");
                removeNotificationHeader();
				getReloadedDiscountData();
			}
		});
	}	
});
}

function showProcessing(divid){
	var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$('#discountbox').html(html);
}

function hideProcessing(){
	$('#loading').remove();
}

function getReloadedDiscountData(){
	var url='ManageDiscounts!getReloadedDiscountData?eid='+eid;
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			 if(!isValidActionMessage(result)) return;
			prepareDiscountTable(JSON.parse(result));
			hideProcessing();
		}
});
}

function openDiscount(){
		showProcessing('forload');
	     $('#adddiscount').prop('disabled',true);
		//showProcessing('discounts'); 
		 var url='DiscountDetails?eid='+eid;
		 $.ajax({url:url})
         .done(function(result){
        	 if(!isValidActionMessage(result)) return;
        	// hideProcessing();
             $("#dataDiv").html(result);
             $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow'); 
             $("#dataDiv").css('margin-top','16px');
     		/* $('#selectall').trigger('click');
     		 $('input[name="discountData.name"]').focus();
     		 $('#adddiscount').prop("disabled",true);*/
         })
         .complete(function(){
        	 $('html, body').animate({ scrollTop: $("#dataDiv").offset().top-100}, 1000);
        	hideProcessing();
         });
		 
}

function copyToClipboard(text) {
//	alert(text);
   
        window.clipboardData.setData("Text", text);
    
}



function prepareDiscountTable(discdata){
	
	if(discdata.tktcount<=0) {
		$('#breaks').html('');
    	$("#dataDiv").html(props.disc_no_tkts_help_msg);
   		 $('#adddiscount').prop('disabled', true);
    }else{
    	if(discdata.DiscountDetails.length==0){
    	//	 $('#adddiscount').trigger('click');  
    		  $("#discounts").html('<div class="not-found" id="noTicketsDiv" style="">'+props.dt_no_disc_to_display+'</div>');
    		}else{    
    			
    		var discountSoldObject=discdata.discountsSold;
    		var data='<table class="table table-responsive table-hover" id="discountsData"><tbody>';
    		$(discdata.DiscountDetails).each(function (keys,values){
    			var displaymsg='';
    			var limittype=values.codetype;
    			if(limittype=='eachcodelimit')
    				displaymsg=props.dt_limit_lbl1+values.discountcount+ props.dt_per_code_type;
    			else if(limittype=='allcodelimit')
    				displaymsg=props.dt_limit_lbl1+values.discountcount;
    			else
    				displaymsg=props.dt_no_limit_type;
    			
    			var tickets=values.tickets;
    			var codes=values.codes;
    			var discountSold=0;
    			var codesul='';
    			var discountSalesInfo='';
    			
    			
    			if(limittype=='eachcodelimit')
    				discountSalesInfo='/'+values.discountcount;
    			
    			
    			
    			$.each(codes,function(key,val){
    				if(discountSoldObject[key]!=undefined)
    					discountSold=discountSoldObject[key];
    				else
    					discountSold=0;
    				/*if(limittype=='eachcodelimit')
    				codesul+='<tr style="margin-bottom:10px;" class="trcopylink"><td width="100px">'+key+'</td><td  width="130px">'+discountSold+discountSalesInfo+'</td><td width="550px"><a target="_blank" href="'+values.discountURL+key+'">'+values.discountURL+key+'</a><td width="100px">&nbsp;&nbsp;&nbsp;&nbsp;<span class="tdcopylink" style="display:none; margin-top: -20px;"><a class="copylink" data-link="'+values.discountURL+key+'" style="cursor:pointer;">Copy Link</a></span></td></td></tr><tr><td colspan="2" style="height:15px;"></td></tr>';
    				else
    				codesul+='<tr style="margin-bottom:10px;" class="trcopylink"><td width="130px">'+key+'</td><td  width="80px;">'+discountSold+discountSalesInfo+'</td><td width="550px"><a target="_blank" href="'+values.discountURL+key+'">'+values.discountURL+key+'</a><td width="100px">&nbsp;&nbsp;&nbsp;&nbsp;<span class="tdcopylink" style="display:none; margin-top: -20px;"><a class="copylink" data-link="'+values.discountURL+key+'"style="cursor:pointer;">Copy Link</a></span></td></td></tr><tr><td colspan="2" style="height:15px;"></td></tr>';*/	
    				
    				codesul+='<div class="row trcopylink">';
    				
    				if(limittype=='eachcodelimit'){
    				codesul+='<div class="col-md-1">'+key+'</div>';
    				codesul+='<div class="col-md-2">'+discountSold+discountSalesInfo+props.dt_used_code_lbl+'</div>';
    				}else{	
    				codesul+='<div class="col-md-2">'+key+'</div>';
    				codesul+='<div class="col-md-1">'+discountSold+discountSalesInfo+props.dt_used_code_lbl+'</div>';
    				}
    				
    				//codesul+='<div class="col-md-7"><a target="_blank" href="'+values.discountURL+key+'">'+values.discountURL+key+'</a></div>';
    				codesul+='<div class="col-md-7">'+values.discountURL+key+'</div>';
    				
    				codesul+='<div class="col-md-2"><span class="tdcopylink" style="display:none;"><a id="link_'+key+'" class="copylink" data-link="'+values.discountURL+key+'" style="cursor:pointer;"> '+props.global_copy_link+' </a></span></div>';
    				codesul+='</div>';
    				
    				codesul+='<div style="height:5px;"></div>';
    				});
    			
    			
    			
    			var ticketsul='<ul>';
    			
    			$.each(tickets,function(key,val){
    				if(discdata["TicketNames"][val]!=undefined)
    					ticketsul+='<li class="li_disc">&nbsp;'+discdata["TicketNames"][val]+'</li>';
    			});
    			ticketsul+='</ul>';
    			
    			var noofcodes=props.dt_mul_codes_lbl;
    			if(Number(values.noofcodes)<=1)
    				noofcodes=props.dt_single_code;
    			
    			
    			var exptext='';
    			
    			if(values.exptype=='Y' && values.expdate!=undefined)
    				exptext='<div class="sub-text">'+   props.dt_expiration_ends_lbl+" "+values.expdate+'</div>';
    			//alert(values.exptype);
    			
    			
    			data+='<tr class="trhover" id='+values.discountId+'><td>';
    			
    			data+='<div class="row" >';
    			
    		    data+='<div class="col-md-2"><span id="dname_'+values.discountId+'">'+values.dicsountname+'</span>'+exptext+'</div>';
    				   
    				   
    			data+='<div class="col-md-3 sm-font"><lable class="toggle" data-toggle="tickets_'+values.discountId+'" style="cursor: pointer;"><span id="dtickets_'+values.discountId+'">'+values.discountValue+ props.dt_on_selected_tkts_lbl+'</span><span style="cursor: pointer;  margin-top: 3px;"  data-toggle="tickets_'+
 	    		      values.discountId+'" class="arrow-gap glyphicon glyphicon-menu-right"></span><lable></div>';
    			
    			
    			data+='<div class="col-md-2 sm-font"><lable class="toggle" data-toggle="codes_'+values.discountId+'" style="cursor: pointer;" ><span id="dcodes_'+values.discountId+'">'+values.noofcodes+' '+noofcodes+'</span><span style="cursor: pointer;  margin-top: 3px;"'+
	    		      ' id="toggling-code-menu" class="arrow-gap glyphicon glyphicon-menu-right" data-toggle="codes_'+values.discountId+'"></span></lable></div>';
    				
    			
    			data+='<div class="col-md-3 sm-font">'+displaymsg+'</div>';
    		   
    			
    			data+='<div class="col-md-2 sm-font"><span class="tdhover" style="display:none;"><a href="javascript:;" onclick=editDiscount(\''+values.discountId+'\','+eid+')>'+props.global_edit_lnk_lbl+'</a>&nbsp; &nbsp;<a href="javascript:;" onclick=deleteDiscount(\''+values.discountId+'\','+eid+')>'+props.global_delete_lnk_lbl+'</a></span></div>';
    			
    			data+='</div>';
    			data+='<div style="clear:both"></div>';
    			
    			data+='<div class="background-options sm-font" style="display:none;" id="tickets_'+values.discountId+'">'+
    			        '<span  class="discounttkts"'+
	    		      ' style="">'+ticketsul+'</span>'+
    			'</div>';
    			
    			
    			data+='<div class="background-options sm-font" style="display:none;" id="codes_'+values.discountId+'">'+
		        '<span  class="discounttkts"'+
		      ' style="">'+codesul+'</span>'+
		        '</div>';
    			
    			
    			data+='</td></tr>';
    				
    			
	    		/*data+='<tr id='+values.discountId+' class="trhover"><td width="20%"><span id="dname_'+values.discountId+'">'+values.dicsountname+'<span></td>'+
	    		      '<td style="overflow: inherit !important;"><span id="dtickets_'+values.discountId+'">'+values.discountValue+' on select tickets</span><span style="cursor: pointer;  margin-top: 3px; margin-left: 7px;"  data-toggle="tickets_'+
	    		      values.discountId+'" class="glyphicon glyphicon-menu-right toggle"></span><br/><span id="tickets_'+values.discountId+'" class="discounttkts"'+
	    		      ' style="display:none;margin-top: 10px;margin-left: -192px;background-color:#fef2c1;width:555%;">'+ticketsul+'</span></td>'+
	    		      
	    		      '<td style="overflow: inherit !important;"><span id="dcodes_'+values.discountId+'">'+values.noofcodes+' '+noofcodes+' </span> &nbsp;<span style="cursor: pointer;  margin-top: 3px; margin-right: -17px;"'+
	    		      ' id="toggling-code-menu" class="glyphicon glyphicon-menu-right toggle" data-toggle="codes_'+values.discountId+'"></span><br/>';
	    		if(limittype=='eachcodelimit')
	    		data+= '<span id="codes_'+values.discountId+'" class="discounttkts" style="display:none;margin-left:-392px;margin-top:20px;width:555%;background-color:#fef2c1">'+codesul+'</span>';
	    		else
	    		data+= '<span id="codes_'+values.discountId+'" class="discounttkts" style="display:none;margin-left:-392px;margin-top:20px;width:555%;background-color:#fef2c1">'+codesul+'</span>';
	    		data+= '</td><td>'+displaymsg+'</td>';                                 
	    		data+='<td>&nbsp;<span style="display:none;margin-top: -20px;" class="tdhover"><a href="javascript:;" onclick=editDiscount(\''+values.discountId+'\','+eid+')>'+props.global_edit_lnk_lbl+'</a>&nbsp; &nbsp;<a href="javascript:;" onclick=deleteDiscount(\''+values.discountId+'\','+eid+')>'+props.global_delete_lnk_lbl+'</a></span></td></tr>';*/
    				});
    		data+='</tbody></table>';
    		$('#discounts').html(data);
    		
    		/*$("a.copylink").zclip({
  		      path:"/main/bootstrap/js/ZeroClipboard.swf",
  		      copy:function(){
  		      return $(this).data('link');
  		      }
  		   });
    		*/
    		$("#discountsData thead").remove();
    		}
    	}
	 var count = discdata.DiscountDetails.length;

var dataTab=$('#discountsData').dataTable( {
	    "sPaginationType": "full_numbers",
	    "iDisplayLength":10,
	    "bLengthChange": false,
	    "bSort" : false,
	    bFilter:false,
	    "aoColumns": [{ "bSortable": true}
	                ] ,
	                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');},
	                "fnDrawCallback": function(oSettings) {
	                     /*if ($('#discountsData tr').length < 10) {
	                        $('.dataTables_paginate').hide();
	                    } */

	                }
	} );

$('#discountsData thead').remove();
//alert(dataTab);
//dataTab.fnPageChange(2,true);
$('#discountsData').on('page.dt', function () {
	$('html, body').animate({ scrollTop: $("#design").offset().top-100}, 1000);
} );	
	if(count<=10)
        removePagination('discountsData');
	
	$('#discountsData').css("table-layout","fixed");
	
	triggerCopy(); 
	
}
function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}

function triggerCopy(){
	
	// $("[id^='link']:lt("+copyLinkLimit+")").parent().hide();
	
	$("[id^='link']:lt("+copyLinkLimit+")").zclip({
		path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
        copy:function(){ return $(this).data('link')}
	});	
   $("[id^='link']:lt("+copyLinkLimit+")").trigger('mouseenter');
}




