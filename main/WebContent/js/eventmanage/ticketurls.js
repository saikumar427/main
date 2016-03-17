$('#createPrivateTickets').click(function(){
	showProcessing('forload');
	createTrackUrl(eid);
	hideProcessing();
});

function createTrackUrl(eid){
	var eid = eid;
	var url = 'TicketURLs!createTicketingURL?eid='+eid;	
	$('#mainTrackUrl').load(url,function(){
		$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
		$('#mainTrackUrl').fadeIn(slideTime);
		$('input[name="name"]').focus();
		$('#selectall').trigger('click');
	});
}

$(document).on('click','.cancelTrackUrl',function(){
	$('#mainTrackUrl').html('');
	$('#createPrivateTickets').prop("disabled",false);
});

var globalEditTkt ='';
function getTickets(eid,code){
	enableLinks();
	disableLinks(code);
	$('#ticketurlsbox').html('');
	$('.editTktRow').remove();
	$('<tr class="editTktRow" id="edited"><td colspan="4"  style="border-top: 0 none !important;"><div id="editTktBox"><div id="loading"><center><img src="../images/ajax-loader.gif"></center></div></div></td></tr>').insertAfter($('#'+code));
	
	$('#urls_'+code).hide();
	$('#tickets_'+code).hide();
	
	$('#dtickets_'+code).removeClass('highlighted-text');
	
	/*$('[id^=dtickets]').each(function(){
		$(this).removeClass('highlighted-text');		
	});*/
	
	$('span[data-toggle="tickets_'+code+'"]').addClass('original').removeClass('down');
	$('span[data-toggle="urls_'+code+'"]').addClass('original').removeClass('down');
	
	$('#dtickets_'+code).removeClass('highlighted-text');
	$('#durls_'+code).removeClass('highlighted-text');
	
	globalEditTkt = code;
	$('#createprivateurls').prop("disabled",true);
	var url='TicketURLs!getTickets?eid='+eid+'&code='+code;		
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	
	$('#editTktBox').load(url,function(){
		$(this).css("display","block");
		//$('html, body').animate({ scrollDown: $("#rmblock").height()}, 1000);
		$('html, body').animate({ scrollTop: $("#errormsg").offset().top-100}, 1000);
		$('#loading').remove();
		$('#editTktBox').fadeIn(slideTime);
	});
	hideProcessing();
}


function disableLinks(id){
	$('tr#'+id+' span.tdhover a').each(function(){
		$(this).css({"pointer-events": "none"});
	});
}


function enableLinks(){
	$('#ticketurlsData span.tdhover a').each(function(){
		$(this).css({"pointer-events": "visible"});
	});
}



function visibleLink(){
	$('#editRow_'+globalEditTkt+' a').each(function(){
		 $(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
	        $(this).prop("disabled",false);
	});
}



function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}



//  code for private ticket urls




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
 
 
 
 $(document).on('click','#createprivateurls',function(){
	 if($('#currentLevel').val()>=300)
		 createPrivateTicketURLs();
	 else{
		 specialFee(eid,"300","TicketURLs","Ticketing");
	 }
	
	/* showTktProcessing();
	 var url = 'TicketURLs!createTicketingURL?eid='+eid;	
	 $('#ticketurlsbox').load(url,function(){
			$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
			//$('#mainTrackUrl').fadeIn(slideTime);
			$('#tktsloading').remove();
			$('input[name="name"]').focus();
			$('#selectall').trigger('click');
		});*/
	 });
 
 function createPrivateTicketURLs(){
	 showTktProcessing();
	 var url = 'TicketURLs!createTicketingURL?eid='+eid;	
	 $('#ticketurlsbox').load(url,function(result){
			//$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
		 $('#tktsloading').remove();
		 if(!isValidActionMessage(result)) return;
			$('html, body').animate({ scrollTop: $("#name").offset().top-100}, 1000);
			
			
			$('input[name="name"]').focus();
			$('#selectall').trigger('click');
		});
 }
 
 
 
 

 
 $(document).on('click','.cancelTrackUrl',function(){
		$('#ticketurlsbox').html('');
		$('#createprivateurls').prop("disabled",false);
	});
 
 
 



$(document).on('click','.toggle',function(){
	 var id=$(this).data('toggle');
	  var temptktid=id.split('_');
	 var tempString='';
	 for(var ite=1;ite<temptktid.length;ite++){
		 tempString+=temptktid[ite];
		 if(ite!=temptktid.length-1)
			 tempString+='_';
	 }
	 
	var tktid=new Array();
	
	tktid.push(temptktid[0]);
	tktid.push(tempString);
	 if(tktid[0]=='urls'){
		 $('#tickets_'+tktid[1]).slideUp();
		 $('#dtickets_'+tktid[1]).removeClass('highlighted-text');
		 $('span[data-toggle="tickets_'+tktid[1]+'"]').addClass('original').removeClass('down');	
		 if($('#urls_'+tktid[1]).is(':hidden')){
			$('span[data-toggle="urls_'+tktid[1]+'"]').addClass('down').removeClass('original');	 
		 $('#urls_'+tktid[1]).slideDown(1000);
		 $('#durls_'+tktid[1]).addClass('highlighted-text');
		 }else{
			$('span[data-toggle="urls_'+tktid[1]+'"]').addClass('original').removeClass('down');		 
		$('#urls_'+tktid[1]).slideUp();
		 $('#durls_'+tktid[1]).removeClass('highlighted-text');
		 }
	 }else{
		 $('#urls_'+tktid[1]).slideUp();
		 $('#durls_'+tktid[1]).removeClass('highlighted-text');
		 $('span[data-toggle="urls_'+tktid[1]+'"]').addClass('original').removeClass('down');	
		 
		 
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


function buildPrivateTicketURLData(){    
	
	var htmldata='<table class="table table-responsive table-hover" id="ticketurlsData"><tbody>';
	
	$.each(data.citems,function(index,eachObj){
		
		var urlsul='<div class="row trcopylink">';
		
		urlsul+='<div class="col-md-6">'+eachObj.url+'</div>';
		urlsul+='<div class="col-md-6"><span class="tdcopylink" style="display:none;"><a id="link_'+eachObj.id+'" data-link="'+eachObj.url+'" class="copylink" href="javascript:;">'+props.global_copy_lnk_lbl+'</a></span></div>';
		urlsul+='</div>';
		
		
		var ticketsul='<ul>';
		
		var tempName='';
		var tktcounts=Number(eachObj.count);
		if(tktcounts==1){
			tempName='1 '+props.global_single_tkts_lbl;
		}else{
			tempName=tktcounts+' '+props.global_tickets_lbl;
		}
		$.each(eachObj.ticketnames,function(key,val){
				ticketsul+='<li class="li_disc">'+val["name"]+'</li>';
		});
		ticketsul+='</ul>';
		htmldata+='<tr class="trhover" id="'+eachObj.id+'"><td>';
		htmldata+='<div class="row" >';
    	htmldata+='<div class="col-md-3 col-sm-3"><span>'+getReducedTicketCode(eachObj.code)+'</span></div>';
    	htmldata+='<div class="col-md-3 col-sm-3 sm-font"><lable class="toggle" style="cursor:pointer" data-toggle="tickets_'+eachObj.id+'"><span id="dtickets_'+eachObj.id+'">'+tempName+'</span><span style="cursor: pointer;  margin-top: 3px;"'+
    	'data-toggle="tickets_'+eachObj.id+'" class="arrow-gap glyphicon glyphicon-menu-right"></span></lable></div>';
    	htmldata+='<div class="col-md-3 col-sm-3 sm-font"><lable class="toggle" data-toggle="urls_'+eachObj.id+'" style="cursor: pointer;" ><span id="durls_'+eachObj.id+'">'+props.global_urls_lbl+'</span><span style="cursor: pointer;  margin-top: 3px;"'+
	      ' id="toggling-code-menu" class="arrow-gap glyphicon glyphicon-menu-right" data-toggle="urls_'+eachObj.id+'"></span></lable></div>';
    	htmldata+='<div class="col-md-3 col-sm-3 sm-font"><span class="tdhover" style="display:none;"><a href="javascript:;"  onclick=getTickets('+eid+',"'+eachObj.id+'")>'+props.global_edit_lnk_lbl+'</a>&nbsp;&nbsp;<a href="javascript:;" onclick=deleteTicketingURL('+eid+',"'+eachObj.id+'")>'+props.global_delete_lnk_lbl+'</a></span></div>';
    	htmldata+='<div style="clear:both"></div>';
    	htmldata+='<div class="background-options sm-font" style="display:none;" id="tickets_'+eachObj.id+'">'+
		        '<span  class="discounttkts"'+
		      ' style="">'+ticketsul+'</span>'+
		'</div>';
    	htmldata+='<div class="background-options sm-font" style="display:none;" id="urls_'+eachObj.id+'">'+
        '<span  class="discounttkts"'+
      ' style="">'+urlsul+'</span>'+
        '</div>';
		htmldata+='</td></tr>';
	});
	
	htmldata+='</tbody></table>';
	
	
	$('#ticketurls').html(htmldata);
	
	triggerCopy();
}



function triggerCopy(){
	$("[id^='link']").zclip({
		path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
        copy:function(){ return $(this).data('link')}
	});	
   $("[id^='link']").trigger('mouseenter');
}


function getReducedTicketCode(code){

	if(code.length<=22)
		return code;
		else
		return code.substring(0,22)+'...';
}



function showTktProcessing(){
	var html='<div id="tktsloading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$('#ticketurlsloading').html(html);
}


function getURLsReloadedData(){
	//loadingPopup();
	$.ajax({
    	url : 'TicketURLs!reloadTicketurlsList?eid='+eid,
    	type : 'get',
    	success : function(t){
    		$('#createprivateurls').prop("disabled",false);	
    		data=JSON.parse(t);
    		
    		if(data.citems.length<=0){
    			//var htmldata='<table class="table table-responsive table-hover" id="ticketurlsData"><tr class="nodata"><td><div class="row"><div class="col-md-2"></div><div class="col-md-4">No private tickets page URLs to display</div></div></td></tr></table>';
    			var htmldata='<div class="not-found" id="noTicketsDiv" style="">'+props.ptu_no_urls_to_diplay_msg+'</div>';
    			$('#ticketurls').html(htmldata);
    			$('#ticketurlsData_br').remove();
    		}else{
    			buildPrivateTicketURLData();
        		callDataTable();
    		}
    		
    		
    		hidePopup();
    		//alert(t);
    	}	    
    });
	
}

function deleteTicketingURL(eid,code){
	 var url='TicketURLs!deleteTicketURL?eid='+eid+'&code='+code;			
	 var dynatimestamp = new Date();
     url += '&dynatimestamp='+dynatimestamp.getTime();
	 bootbox.confirm('<h3>'+props.global_confirm_delete_msg+'</h3>'+props.global_do_you_want_continue_msg, function (result) {
	     if (result){	           	
		   parent.loadingPopup();			    	
          	$.ajax({
          		type : "GET",
               url : url,
               success:function(result){	                
               	parent.hidePopup();	                	
					if(result.indexOf("success")>-1){
					// window.location.reload(true);
					/* $('#privateticketsdata').dataTable().fnDestroy(false);
					$('#'+code).remove();*/
					getURLsReloadedData();
					$('#privateStatusMsg').html('');
					notification(props.ptu_ticket_url_deleted_successfully_msg,"success");
					/*
					$('#notification-holder').html('');
					 $('#notification-holder').html('');
					notification(props.ptu_ticket_url_deleted_successfully_msg,"success");*/
					}
					else if(result.indexOf("fail")>-1)
						//alert("Deletion failed, there are registrations with this url.");
						bootbox.confirm(props.ptu_deletion_failed_there_are_registrations, function (result) {	    		
				     	});
               }
          	});
	     	}
	     });
	}

function callDataTable(){	
	 $('#ticketurlsData').dataTable( {
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "aoColumns": [{ "bSortable": true}
        ] ,
        "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
        },
         "bFilter" : true,
         "bSort" : false,
        aaSorting: [] 
    } );
	 
	 $('#ticketurlsData thead').remove();
	 
	 if(data.citems.length<=5)
		 removePagination('ticketurlsData');
	//$('#privateticketsdata_paginate').hide();
}







