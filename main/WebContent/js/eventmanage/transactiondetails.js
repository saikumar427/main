function getTransactionInfo(tid){
	$('#ul_'+tid).show();
}

var element;
var globalTrId='';
function profilefunc(eid,tid,pkey){
	
	if(attendeeProfileId!=''){
		$('#'+attendeeProfileId).show();
		$('#edit_'+attendeeProfileId).hide();
	}
	if(buyerProfileId!=''){
		$('#'+buyerProfileId).show();
		$('#edit_'+buyerProfileId).hide();
	}
	buyerProfileId=pkey;
	$('#edit_'+pkey).show();
	$('#'+pkey).hide();
	$("div.loadedData").remove();
	$('#buyerloading').show();
	var url='EditAttendee!editBuyerInfo?eid='+eid+'&tid='+tid+'&pid='+pkey;
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('#buyerProfileData').append(temp);
    	/*$('#filledData').load(url,function(result){
    		if(!isValidActionMessage(result)) return;
    		$('.taskheader').hide();
   		 $('.imgload').hide();
   		$('#filledData').show();
   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
   		$('.taskheader').show();
   		setTimeout(function(){
   		$('input.radiochk').iCheck({  
   			checkboxClass: 'icheckbox_square-grey', 
   			radioClass: 'iradio_square-grey'});
   		},500);
       	 }); */
	
	$.ajax({
		url:url,
		success:function(result){
			
			if(!isValidActionMessage(result)) return;
    		$('.taskheader').hide();
   		$('#filledData').html(result).show();
   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
   		$('.taskheader').show();
   		setTimeout(function(){
   		 $('.imgload').hide();	
   		$('input.radiochk').iCheck({  
   			checkboxClass: 'icheckbox_square-grey', 
   			radioClass: 'iradio_square-grey'});
   		},500);
		}
	});
	}

function deleteattendeenotes(eventid,profileid,ticketid,transactionid,profilecount){
    var url='EditAttendee!deleteattendeeNotes?eid='+eventid+'&tid='+transactionid+'&ticketid='+ticketid+'&pid='+profileid+'&profilecount='+profilecount;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html(props.trans_del_attnde_notes_lbl);
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
}


function ticketdetails(eid,tid){
	
	if(attendeeProfileId!=''){
		$('#'+attendeeProfileId).show();
		$('#edit_'+attendeeProfileId).hide();
	}	
	if(buyerProfileId!=''){
		$('#'+buyerProfileId).show();
		$('#edit_'+buyerProfileId).hide();
	}
	
	$("div.loadedData").remove();	
    $('#tktloading').show();	
	var url='EditTransactionTickets!editTickets?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#ticketDetailsData').append(temp);
	  $('#filledData').load(url,function(result){
		 // alert(JSON.stringify(result,undefined,2));
		  if(!isValidActionMessage(result)) return;
		 $('.imgload').hide();
		 $('#filledData').show("slide", {direction: "top" },"slow");
    	 }); 
	}

function getSeatingChart(eventid,tranid,pk,edate,seataction){
	if(pk!='')
	profilekey=pk;
	var dynatimestamp = new Date();
	var url='ManageAttendeeSeats!getSeatingChart?eid='+eventid+'&profilekey='+pk+'&tid='+tranid+'&seataction='+seataction;
	url += '&dynatimestamp='+dynatimestamp.getTime();
	if(seataction=='changeseat')
	$('.modal-title').html(props.trans_change_seat_lbl);
	else
	$('.modal-title').html(props.trans_add_seat_lbl);
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
}





function editattendeenotes(eventid,profileid,ticketid,transactionid){
	
	
	if(attendeeProfileId!=''){
		$('#'+attendeeProfileId).show();
		$('#edit_'+attendeeProfileId).hide();
	}
	if(buyerProfileId!=''){
		$('#'+buyerProfileId).show();
		$('#edit_'+buyerProfileId).hide();
	}
	attendeeProfileId=profileid;
	$('#edit_'+profileid).show();
	$('#'+profileid).hide();
	$("div.loadedData").remove();
	//$('#attendeeloading_'+profileid).show();
	//YAHOO.ebee.popup.wait.show();
	var url='EditAttendee?eid='+eventid+'&pid='+profileid+'&ticketid='+ticketid+'&tid='+transactionid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	//alert("the url is::"+url);
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#attedeedData_'+profileid).append(temp);
	/*$('#filledData').load(url,function(result){
		alert(result);
		if(!isValidActionMessage(result)) return; else{
   		 $('.imgload').hide();
   		$('#filledData').show();
   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
		setTimeout(function(){
   	   		$('input.radiochk').iCheck({  
   	   			checkboxClass: 'icheckbox_square-grey', 
   	   			radioClass: 'iradio_square-grey'});
   	   		},500);
		}
	});*/
	
	$.ajax({
		url:url,
	   success:function(result){
		   if(!isValidActionMessage(result)) return;
		   $('#filledData').html(result).show();
		  
	   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
			setTimeout(function(){
				 $('.imgload').hide();
	   	   		$('input.radiochk').iCheck({  
	   	   			checkboxClass: 'icheckbox_square-grey', 
	   	   			radioClass: 'iradio_square-grey'});
	   	   		},500);
			}
	});
	
	
	
}

function getTransactionDetails(tidObj){
	var currencySymbol=tidObj.currencySymbol;
	var tdate=tidObj.transdetails.transaction_date;
	var datepart=tdate.split(" ")[0];
	
	
	var dateformat=getDateFormat(datepart.split('/')[2]+'/'+datepart.split('/')[0]+'/'+datepart.split('/')[1])+' '+tdate.split(" ")[1]+' '+tdate.split(" ")[2];
	
	var traSourceLbl=tidObj.transdetails.bookingsource;
	if(traSourceLbl=='Added Manually') traSourceLbl=props.trans_source_added_manually_lbl;
	else if(traSourceLbl=='Online') traSourceLbl=props.global_online_lbl;
	
	var paymentStatusLbl=tidObj.transdetails.paymentstatus;
	if(paymentStatusLbl=='Deleted') paymentStatusLbl=props.trans_deleted_status_lbl;
	else if(paymentStatusLbl=='Pending Approval') paymentStatusLbl=props.trans_pending_approval_status_lbl;
	else if(paymentStatusLbl=='Refunded') paymentStatusLbl=props.trans_refunded_status_lbl;
	else if(paymentStatusLbl=='Chargeback') paymentStatusLbl=props.trans_chback_status_lbl;
	else if(paymentStatusLbl=='Completed') paymentStatusLbl=props.trans_completed_status_lbl;
	
	var paymentTypeLbl=tidObj.transdetails.paymenttype;
	if(paymentTypeLbl=='Other') paymentTypeLbl=props.trans_other_paymentmethod_lbl;
	else if(paymentTypeLbl=='No Payment') paymentTypeLbl=props.trans_no_payment_status_lbl;
	else if(paymentTypeLbl=='Added Manually') paymentTypeLbl=props.trans_source_added_manually_lbl;
	
	var content='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_order_no_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.orderNumber+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_tid_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.tid+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_purchase_date+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+dateformat+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_transaction_source_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+traSourceLbl+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_transaction_status+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+paymentStatusLbl+' </div><div style="clear:both;"></div></div>'+
	           
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_disc_code+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.discountcode+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_tot_disc_lbl+' ('+currencySymbol+'):</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.discount+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_cc_fee_tax+' ('+currencySymbol+'):</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.tax+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_tot_amount+' ('+currencySymbol+'):</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname">'+tidObj.transdetails.totalAmount+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_payment_mode+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+paymentTypeLbl+' </div><div style="clear:both;"></div></div>';
	//var content='hi htis is ';
	return content;
}


function closeTransactionTickets(){
    	$('tr.nowell').remove();
    	$('tr.info').removeClass('info');
    	if(globalTrId!='')
    	$("html, body").animate({scrollTop: $('#'+globalTrId).offset().top - 60}, "slow",function(){});
    	 
}



function getTicketDetails(tidObj){
	var content='<div class="row"><div class="col-md-1"></div><div class="col-md-9"><div id="ticketDetailsData"></div></div></div>';
	return content;
}

function getBuyerDetails(tidObj){
	var content='<div class="row"><div class="col-md-1"></div><div class="col-md-9"><div id="buyerProfileData"><div class="loadedData"> <div class="imgload" style="display: none;">'+
		   '<center><img src="../images/ajax-loader.gif"></center></div><div id="filledData"></div></div></div></div></div>';
		
	return content;
} 

function getAttendeeDetails(tidObj,tid){
	
	
	var len=$.map(tidObj.transdetails, function(n, i) { return i; }).length;
	var tktlen=$.map(tidObj.tktdetails, function(n, i) { return i; }).length;
	var buyerlen=$.map(tidObj.buyerdetails, function(n, i) { return i; }).length;
	var attlen=$.map(tidObj.attendeedetails, function(n, i) { return i; }).length;
	var paymentstatus=tidObj.transdetails.paymentstatus;
	var bookingdomain=tidObj.transdetails.bookingdomain;
	var refund=tidObj.transdetails.refund;
	var profilecount=tidObj.profilecount;
	var currencySymbol=tidObj.currencySymbol;
	var currentTransactionDate=tidObj.currentTransactionDate;
	
	var html='';
	var powertype='Ticketing';
	
	if(attlen>0){
        
    
        for(var i=0;i<tktlen;i++){
        	try{
        		var tktdata=tidObj.tktdetails[i];
            	var tktid=tktdata.tktnumber;
            	var tktname=tktdata.ticketName;
            	var attendeedetailsObj=tidObj.attendeedetails; 
            	var tktattendeedetails=attendeedetailsObj[tktid];
            	for(var att=0;att<tktattendeedetails.length;att++){
            		var c=att+1;
            		var eachtktdetails=tktattendeedetails[att];
            		var pk=eachtktdetails.profilekey;
            		html+='<div class="table table-responsive" id="'+pk+'">';
            		//html+='<table class="table table-responsive" id="'+pk+'">';
            		
            			/* if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' ){
                  		 html+='<tr><td><span><b>'+tktname+' - Profile   # '+c+'</b></span> [<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit<a/> '+ 
                  		 '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete<a/>]</td></tr>';
            			 }*/
            		
            			 if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' ){	 
            		html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-8 col-sm-12 col-xs-12"><label><span><b>'+tktname+' - '+props.rep_profile_lbl+'   # '+c+'</b></span>&nbsp;<span class="sm-font"> <a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.global_edit_lnk_lbl+'<a/> '+ 
             		      '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">'+props.global_delete_lnk_lbl+'<a/></span></label></div><div style="clear:both;"></div></div>';	 
            			 }

            		if(eachtktdetails.fname!='')	 
            		html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_first_name_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.fname+' </div><div style="clear:both;"></div></div>';
            		
            		
            		if(eachtktdetails.lname!='')	 
                		html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_last_name_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.lname+' </div><div style="clear:both;"></div></div>';
                		
            		
            		if(eachtktdetails.email!='')	 
                		html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_email_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.email+' </div><div style="clear:both;"></div></div>';
                		
            		
            		

            		 /*if(eachtktdetails.fname!='')
                     html+='<tr><td>First Name : '+eachtktdetails.fname+'</td></tr>';
                     if(eachtktdetails.lname!='')
                     html+='<tr><td>Last Name : '+eachtktdetails.lname+'</td></tr>';
                     if(eachtktdetails.email!='')
                     html+='<tr><td>Email : '+eachtktdetails.email+'</td></tr>';*/
                     var pkey=eachtktdetails.profilekey;
                     var seatevt=tidObj.seatingevt;
                     
                     if(seatevt=='Y'){
                         if(eachtktdetails.seatcode!=''){
                        	 
                         html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_seat_code_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" ><span id="scode_'+pk+'">'+eachtktdetails.seatcode+'</span>';	 
                         if(bookingdomain!='VBEE')   
                         html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'changeseat\')">'+props.trans_change_seat_lbl+'</a>]';
                         html+='</div><div style="clear:both;"></div></div>';
                         }else{
                        	 if(paymentstatus!='Deleted' && eachtktdetails.tickettype!='donationType'){
                            	
                            	  html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_seat_code_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" ><span id="scode_'+pk+'">'+eachtktdetails.seatcode+'</span>';
                            	
                            	    if(bookingdomain!='VBEE')
                            	    	 html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'addseat\')">'+props.trans_add_seat_lbl+'</a>]';
                            	   html+='</div><div style="clear:both;"></div></div>';
                            	 }
                             }
                         }
                     
                     
                     
                     var attcustomattribs=tidObj.attribmap[pkey];
                     if(attcustomattribs!=undefined && attcustomattribs!='undefined' && attcustomattribs.length>0 ){
                     for(var atts=0;atts<attcustomattribs.length;atts++){
						if(attcustomattribs[atts]['response']!='')
							html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+attcustomattribs[atts]['question']+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+attcustomattribs[atts]['response']+' </div><div style="clear:both;"></div></div>';
                         }
                     }
                     html+='</div><table class="table table-responsive" id="edit_'+pk+'" style="display:none;">';
                     html+='<tr><td>';
                     html+='<div id="attedeedData_'+pk+'"></div></td></tr><table>';
            	}
            	
            	
        	}catch(err){
        		//alert(err);
        	}
        	
        	
        }
        
    	// html+='</table>';	
    }
	
//	console.log(html);
	return html;
}




$(document).ready(function(){
	
	$(document).on('click','.manage',function(){
		var tid=$(this).data('manage');
		//alert(tid);
		
	if($(this).parent().find('ul').is(':hidden')){
		$('.dropdown-menu').hide();
		$(this).parent().find('ul').show();
	}else{
		$('.dropdown-menu').hide();
	}
	});
	
	$(document).on('click', function(e) {
		if (!$(e.target).is('.manage') && !$(e.target).parents().is('.manage')) {
	    	$('.dropdown-menu').hide();
	    }
	});
	
	
	$(document).on('click','.resendemail',function(){
		$('tr.nowell').remove();
		var tid=$(this).data('tid');
		globalTrId='reports_'+tid;
		
		var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=Ticketing';
		var dynatimestamp = new Date();
		url += '&dynatimestamp='+dynatimestamp.getTime();
		$.ajax({
			type:'get',
			url:url,
			success:function(result){
				if(!isValidActionMessage(result)) return;
			}
		});
		
		var html='<tr class="nowell"><td colspan="6"><div class="row well" style="margin:0px;"><div class="row"><div class="col-md-3"></div><div class="col-md-6"><span id="mailsent"><center><img src="../images/ajax-loader.gif"></center><br/></span></div></div><center><button onclick="closeTransactionTickets();" class="btn btn-cancel"><i class="fa fa-times"></i></button></center></div></td></tr>';
		$('.closeTransactionDetails').trigger("click");
	    var parent = $(this).parents('tr');
	  //  parent.addClass('info');
	    $("html, body").animate({scrollTop: $(parent).offset().top - 60}, "slow",function(){});
	    $(html).insertAfter(parent);
	    $('.dropdown-menu').hide();
	    $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
		
	    setTimeout(function(){
	    	$('#mailsent').html('');
	    	//$('#mailsent').html('<div class="alert alert-success alert-dismissable page-alert" style=""><button class="close close-notification" type="button"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Mail sent successfully.</div>');
	   notification(props.trans_mail_sent_msg,'success');
	    },1000);
	});
	
	
	$(document).on('click','.changestatus',function(){
		element=$(this);
		$('tr.nowell').remove();
		var paymentType='Added Manually';
		var tid=$(this).data('tid');
		globalTrId='reports_'+tid;
		var url='TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid;
		$.ajax({
			type:"POST",
			url:url,
			async:false,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				Obj=JSON.parse(result);
				paymentType=Obj.transdetails.paymenttype;
			}
		});
		
		
		
		var tid=$(this).data('tid');
		var html='<tr class="nowell"><td colspan="6">'+
		    '<div class="row well" style="margin:0px;"><div class="row" id="statuschange" style="display:none;"><div class="col-md-1"></div><div class="col-md-7"><div class="alert alert-success alert-dismissable page-alert" style=""><button class="close close-notification" type="button"><span aria-hidden="true">x</span><span class="sr-only">'+props.global_close_btn_lbl+'</span></button>'+props.trans_status_updated_msg+'</div></div></div>'+
			'<div class="row" id="changestatus"></div></div></td></tr>';
		
		$('.closeTransactionDetails').trigger("click");
	    var parent = $(this).parents('tr');
	   // parent.addClass('info');
	    $("html, body").animate({scrollTop: $(parent).offset().top - 60}, "slow",function(){});
	    $(html).insertAfter(parent);
	    $('.dropdown-menu').hide();
	    $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
	    var url='TransactionDetails!changeStatus?eid='+eid+'&tid='+tid+'&payType='+paymentType;
	    $.ajax({
			type:"POST",
			url:url,
			async:false,
			success:function(result){
				$('#mailsent').hide();
				if(!isValidActionMessage(result)) return;
				$('#changestatus').html(result);
			}
		});
	}); 
	
	
	$(document).on('click','.refundtransaction',function(){
		closeTransactionTickets();
		var tid=$(this).data('tid');
		globalTrId='reports_'+tid;
		var html='<tr class="nowell"><td colspan="6"><div class="row well" style="margin:0px;">'+
	    '<div class="row"><div class="col-md-4"></div><div class="col-md-4"><span id="mailsent"><center><img src="../images/ajax-loader.gif"></center></span></div></div>'+
		'<div class="row"><div class="col-md-12" id="changerefund"></div></div><br/><div class="row" id="refundbtn" style="display:none;"><div class="col-md-12"><center><button onclick="closeTransactionTickets();" class="btn btn-cancel"><i class="fa fa-times"></i></button></center></div></div></div></td></tr>';
	
	$('.closeTransactionDetails').trigger("click");
    var parent = $(this).parents('tr');
    //parent.addClass('info');
    $("html, body").animate({scrollTop: $(parent).offset().top - 60}, "slow",function(){});
    $(html).insertAfter(parent);
    $('.dropdown-menu').hide();
    $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
   var Obj;
   
   var refund='No';
   
    var url='TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid;
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			Obj=JSON.parse(result);
			refund=Obj.transdetails.refund
		}
	});
	
	//var refund='Yes';
    
    if(refund=='Yes' && Obj.transdetails.paymentstatus=='Completed'){
    	
    	var refundurl='PaymentsRefund?eid='+eid+'&tid='+tid+'&paymentType='+Obj.transdetails.paymenttype;
    	var dynatimestamp = new Date();
    	refundurl += '&dynatimestamp='+dynatimestamp.getTime();
    	
    	 $.ajax({
 			type:"POST",
 			url:refundurl,
 			async:false,
 			success:function(result){
 				$('#mailsent').hide();
 				if(!isValidActionMessage(result)) return;
 				$('#changerefund').html(result);
 			}
 		});
    }else{
    	$('#mailsent').hide();
    	if(Obj.transdetails.paymenttype=='Eventbee')
		$('#changerefund').html('<center>'+props.trans_refund_not_applicable+'</center>');
    	else
    	$('#changerefund').html('<center>'+props.transa_refund_applicable+'</center>');	
		$('#refundbtn').show();
    }
    
	});
	
	
	
	$(document).on( "click",".clickk",function(e) {
		element=$(this);
		$('tr.nowell').remove();
		divOpen=false;
	//	loadingPopup();
		var purpose=$(this).data('type');
		var tidObj;
		var chkboxlen;
		if(actionType=='attendee'){
			chkboxlen=$('input[name="attFields"]:checked').length;
		}else if(actionType=='registration'){
			chkboxlen=$('input[name="Fields"]:checked').length;
		}else{
			chkboxlen=$('input[name="checkinFields"]:checked').length;
		}
		var isrecurring=$('#isrecur').val();
		var powertype='Tickeing';
		var paymenttype='${paymentType}';
		
		var tid=$(this).attr('data-tid');
		globalTrId='reports_'+tid;
		var url='TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid;
		$.ajax({
			type:"POST",
			url:url,
			async:false,
			success:function(result){
				if(!isValidActionMessage(result)) return;
			   tidObj=JSON.parse(result);
			}
		});
		$('.closeTransactionDetails').trigger("click");
		var len=$.map(tidObj.transdetails, function(n, i) { return i; }).length;
		e.preventDefault();
	    if (divOpen) return;
	    var html = '<tr class="nowell">' +  '<td colspan="6">' +  '<div class="row well" style="margin:0px;">' ;
	      
	    
	  /*  html+='<div class="pull-right" style="position: relative;">'+
        '<button class="btn btn-active btn-xs closeTransactionDetails"><i class="fa fa-times"></i></button></div>';*/
	    
	    
	    
	       if(tidObj!=undefined && len>0){
	           	if(purpose=='transactiondetails'){
	    	    	 html+=getTransactionDetails(tidObj);
	           	     //html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"></div><div class="col-md-8 col-sm-6 col-xs-6 aFname"> <label><button class="btn btn-active" onclick="closeTransactionTickets();">Cancel</button></label></div><div style="clear:both;"></div></div>';
	    	    	   html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"></div><div class="col-md-8 col-sm-6 col-xs-6 aFname"> <label><button class="btn btn-cancel" onclick="closeTransactionTickets();"><i class="fa fa-times"></i></button></label></div><div style="clear:both;"></div></div>';
	           	}else if(purpose=='ticketdetails')
	           		html+=getTicketDetails(tidObj);
	           	else if(purpose=='buyerdetails')           
	           		html+=getBuyerDetails(tidObj);
	           	else if(purpose=='attendeedetails'){       
	           		html+=getAttendeeDetails(tidObj,tid);
	           	 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><center><button class="btn btn-cancel" onclick="closeTransactionTickets();"><i class="fa fa-times"></i></button></center></div>';
	           	}
	       }
	        html+='</div>' +
	        '</td>' +
	        '</tr>';
	    $('.closeTransactionDetails').trigger("click");
	    var parent = $(this).parents('tr');
	    //parent.addClass('info');
	   
	    $("html, body").animate({
	        scrollTop: $(parent).offset().top - 60
	    }, "slow",function(){
	    	 hidePopup();
	        });
	    $(html).insertAfter(parent);
	    divOpen = true;
	    
	    if(purpose=='ticketdetails'){
	    	ticketdetails(''+eid+'',''+tid+'');
	    }else if(purpose=='buyerdetails'){
	    	 profilefunc(''+eid+'',''+tid+'',''+tidObj.buyerdetails.profilekey+'');
	    }
	    
	    $('.dropdown-menu').hide();
	    $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
	});
	
	
	
	
});