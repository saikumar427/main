var element;
var globalTrId='';

function addRSVPResponses(eid,tid){
	$('#statusdiv').hide();
	$("div.loadedData").remove();
	var url='EditTransactionTickets!rsvpEditResponses?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#responseDetailsData').append(temp);
	$('#filledData').load(url,function(){
   		 $('.imgload').hide();
   		$('#filledData').show("slide", {direction: "top" },"slow");
	});
}

function deletersvptransaction(eid,tid){
	loadingPopup();
	var url='TransactionDetails!deleteRSVPTransaction?eid='+eid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
    		window.location.reload();
			}
	});
}

function changeToNotattendingProfile(eid,pk,tktid,tid){
	loadingPopup();
	var url='EditAttendee!changeToNotattending?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
	$.ajax({
	type:'POST',
	url:url,
	success:function(result){
		if(!isValidActionMessage(result)) return;
		element.trigger('click');
		}
	});
	}

function showDetails(tid){
	loadingPopup();
	
	$("."+tid).each(function() {
		 $(this).trigger("click",function(){
			 $('.closeTransactionDetails').trigger("click");
	 });
		 return false;
    });
	
}



function resendMail(eid,tid){
	var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=RSVP';
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	/*$('.modal-title').html('Mail Success');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');*/
	
	$.ajax({
		type:'get',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			openSinglePopUpWithOptions($('#resend_'+tid).offset(),resendsuccess,resendcancel,'',props.trans_mail_sent_msg,'');
			$('#onefieldtextoption').hide();
			$('#singlecancel').hide();
			$('#singlesubmit').attr('value','OK');
			}
	});
	//var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getMailSuccess,failure:getFailure});
}



function changeToNotAttending(eid,tid){
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#responseDetailsData').append(temp);
	var url='EditTransactionTickets!changeToNotAttending?eid='+eid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			element.trigger('click');
			}
	});
	}


function profilefunc(eid,tid,pkey){
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
    	$('#filledData').load(url,function(){
   		 $('.imgload').hide();
   		//$('#filledData').show("slide", {direction: "top" },"slow");
   		$('#filledData').show();
   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
       	 }); 
	}


function editattendeenotes(eventid,profileid,ticketid,transactionid){
	attendeeProfileId=profileid;
	$('#edit_'+profileid).show();
	$('#'+profileid).hide();
	$("div.loadedData").remove();
	var url='EditAttendee?eid='+eventid+'&pid='+profileid+'&ticketid='+ticketid+'&tid='+transactionid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#attedeedData_'+profileid).append(temp);
	$('#filledData').load(url,function(){
		//alert("loaded..");
   		 $('.imgload').hide();
   		//$('#filledData').show("slide", {direction: "top" },"slow");
   		$('#filledData').show();
   		$('#profile_header').show("slide", {direction: "bottom" },"slow");
   		//$('#profile_header').show();
	});
}

function addNotes(eid,tid){
	
	$("div.loadedData").remove();	
	var url='TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#notesData').append(temp);
	  $('#filledData').load(url,function(result){
		  if(!isValidActionMessage(result)) return;
		 $('.imgload').hide();
		 $('#filledData').show("slide", {direction: "top" },"slow");
    	 });
}

function updateNotes(){
	$.ajax({
		type:'get',
		data:$('#notesform').serialize(),
		url:'TransactionDetails!saveNotes',
		success:function(result){
			if(!isValidActionMessage(result)) return;
			var tid=$('#transactionid').val();
			showDetails(tid);
			}
	});
}




function getTransactionDetails(tidObj){
	
	var tdate=tidObj.transdetails.transaction_date;
	var datepart=tdate.split(" ")[0];
	
	
	var dateformat=getDateFormat(datepart.split('/')[2]+'/'+datepart.split('/')[0]+'/'+datepart.split('/')[1])+' '+tdate.split(" ")[1]+' '+tdate.split(" ")[2];
	
	var traSourceLbl=tidObj.transdetails.bookingsource;
	if(traSourceLbl=='Added Manually') traSourceLbl=props.trans_source_added_manually_lbl;
	else if(traSourceLbl=='Online') traSourceLbl=props.global_online_lbl;
	
	var paymentStatusLbl=tidObj.transdetails.paymentstatus;
	if(paymentStatusLbl=='Deleted') paymentStatusLbl=props.trans_deleted_status_lbl;
	else if(paymentStatusLbl=='Completed') paymentStatusLbl=props.trans_completed_status_lbl;
	
	var currencySymbol=tidObj.currencySymbol;
	      var content='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_tid_lbl+'&nbsp;:</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+tidObj.transdetails.tid+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.rsvp_trans_response_date+'&nbsp;:</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+dateformat+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_transaction_source_lbl+'&nbsp;:</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+traSourceLbl+' </div><div style="clear:both;"></div></div>'+
	            '<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+props.trans_transaction_status+'&nbsp;:</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+paymentStatusLbl+' </div><div style="clear:both;"></div></div>'
	return content;
}


function getChangeStatusDetails(tidObj,tid){
	
	var isrecurring=$('#isrecur').val();
	var tktlen=$.map(tidObj.tktdetails, function(n, i) { return i; }).length;
	var bookingdomain=tidObj.transdetails.bookingdomain;
	var html='';
	
	if(tktlen!=undefined && tktlen>0){
    		//html+='<h4><strong>Response Details</strong></h4><hr>' ;
    		if(isrecurring=='true'){
        		html+=' <form action="TransactionDetails!changeDate" id="transactiondateform" name="transactiondateform" method="post" theme="simple">';
        		html+='<span><span id="rsvprecurring">'+props.trans_event_for_date_lbl+' '+tidObj.currentTransactionDate+'</span></br><br/>';
        		html+='<table class="table table-responsive"><tr>';
        		html+='<td width="15%" style="vertical-align: middle !important;">'+props.rsvp_change_recc_date+'</td><td width="42%"> <select id="source" name="reportsData.date" class="form-control">';
        		html+='<option selected="selected" value="">'+props.rsvp_select_recc_date+'</option>';
        		for(var i=0;i<tidObj.recurdates.length;i++)
        			html+='<option value="'+tidObj.recurdates[i]+'">'+tidObj.recurdates[i]+'</option>';
        		html+='<input type="hidden" name="eid" value="'+eid+'" /><input type="hidden" name="tid" value="'+tid+'" />';
        		html+='</select></td>&nbsp;<td style="vertical-align: middle !important;"><input type="button" id="rsvprecursubmit" value="'+props.global_submit_lbl+'" class="btn btn-primary btn-xs" /></td></tr></table></span></form>';
        		}

    		if(tidObj.response1=="Y" && bookingdomain!='VBEE'){
    			 html+='<span class="pull-right sm-font"><a href="javascript:;" onclick="changeToNotAttending(\''+eid+'\',\''+tid+'\')">'+props.rsvp_change_tonot_attend+'</a>&nbsp;'+
        			  '<a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">&nbsp;'+props.trans_add_mre_attndes_lbl+'</a></span>'; 
        		}else if(bookingdomain!='VBEE'){
            		html+='<span class="pull-right sm-font"><a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">'+props.rsvp_change_to_attend+'</a></span>'
            		}

    		html+='<br/><table class="table table-responsive">'+
            '<thead><th>'+props.rsvp_response_type+'</th>'+
            '<th>'+props.rsvp_count_lbl+'</th>'+
             '</thead>' +
            '<tbody>' ;
    		for(var i=0;i<tktlen;i++){
    			
    			var reponseType=tidObj.tktdetails[i].ticketName;
    			if(reponseType=='Attending') reponseType=props.rsvp_attending_response;
    			else if(reponseType=='Not Sure') reponseType=props.rsvp_not_sure_response;
    			else if(reponseType=='Not Attending') reponseType=props.rsvp_not_attending_response;
    			else if(reponseType=='Maybe') reponseType=props.rsvp_maybe_response;
                html+='<tr>' +
                '<td>&nbsp;&nbsp;&nbsp;'+reponseType+'</td>' +
                '<td>&nbsp;&nbsp;&nbsp;'+tidObj.tktdetails[i].qty+'</td>' +
                '</tr>' ;
                }
    			html+='<tr><td colspan="2">';
    			html+='<div id="responseDetailsData"></div><br/></td></tr>';
                html+='</tbody>'+ 
                '</table>';
                
	}
	
	return html;
}



function closeProfile(){
	$('#'+buyerProfileId).show();
	$('#edit_'+buyerProfileId).hide();
	$('#'+attendeeProfileId).show();
	$('#edit_'+attendeeProfileId).hide();
	$("div.loadedData").remove();
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




function getBuyerDetails(tidObj){
	var content='<div class="row"><div class="col-md-1"></div><div class="col-md-9"><div id="buyerProfileData"><div class="loadedData"> <div class="imgload" style="display: none;">'+
		   '<center><img src="../images/ajax-loader.gif"></center></div><div id="filledData"></div></div></div></div></div>';
		
	return content;
} 


function changeToSure(eid,pk,tktid,tid){
	//loadingPopup();
	var url='EditAttendee!changeToSure?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
	$.ajax({
	type:'POST',
	url:url,
	success:function(result){
		if(!isValidActionMessage(result)) return;
		element.trigger('click');
		}
	});
	}


function changeToNotsure(eid,pk,tktid,tid){
	loadingPopup();
	var url='EditAttendee!changeToNotsure?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			element.trigger('click');
			}
	});
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
	var powertype='RSVP';
	
	
	
	
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
            		
            		
            		if(tktname!='Not Sure' &&  bookingdomain!='VBEE'){
                 	  /* html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                 	   html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit&nbsp;</a> '; 
                 	   html+= ' <a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete&nbsp;</a>';
                 	   html+= ' <a href="javascript:;" onclick="changeToNotsure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Sure&nbsp;</a>';
                 	   html+=' <a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';*/
                  
            			var tktnameLbl=tktname;
            			if(tktnameLbl=='Attending') tktnameLbl=props.rsvp_attending_response;
            			else if(tktnameLbl=='Not Sure') tktnameLbl=props.rsvp_not_sure_response;
            			else if(tktnameLbl=='Not Attending') tktnameLbl=props.rsvp_not_attending_response;
            			else if(tktnameLbl=='Maybe') tktnameLbl=props.rsvp_maybe_response;
            			
                 	  html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-8 col-sm-12 col-xs-12"><label><span><b>'+tktnameLbl+' - '+props.rsvp_profile_lbl+'   # '+c+'</b></span> <span class="sm-font">&nbsp;<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.global_edit_lnk_lbl+'&nbsp;</a> '+ 
         		      '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">'+props.global_delete_lnk_lbl+'&nbsp;&nbsp;</a><a href="javascript:;" onclick="changeToNotsure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.rsvp_not_sure_response+'&nbsp;&nbsp;</a><a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.rsvp_not_attending_response+'</a></span></label></div><div style="clear:both;"></div></div>';	 
            		
            		
            		
            		}else if(bookingdomain!='VBEE'){
                       /* html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                        html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit&nbsp;</a> '; 
                   	   html+= ' <a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete&nbsp;</a>';
                   	   html+= ' <a href="javascript:;" onclick="changeToSure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Sure&nbsp;</a>';
                   	   html+=' <a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';*/
            			var tktnameLbl=tktname;
            			if(tktnameLbl=='Attending') tktnameLbl=props.rsvp_attending_response;
            			else if(tktnameLbl=='Not Sure') tktnameLbl=props.rsvp_not_sure_response;
            			else if(tktnameLbl=='Not Attending') tktnameLbl=props.rsvp_not_attending_response;
            			
            			 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-8 col-sm-12 col-xs-12"><label><span><b>'+tktnameLbl+' - '+props.rsvp_profile_lbl+'   # '+c+'</b></span> <span class="sm-font">&nbsp;<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.global_edit_lnk_lbl+'&nbsp;&nbsp;</a> '+ 
            		      '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">'+props.global_delete_lnk_lbl+'&nbsp;&nbsp;</a><a href="javascript:;" onclick="changeToSure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.rsvp_sure_response+'&nbsp;&nbsp;</a><a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">'+props.rsvp_not_attending_response+'</a></span></label></div><div style="clear:both;"></div></div>';	 
               		
            			
            			
                         }
            		
            		

            		 if(eachtktdetails.fname!='')
            			 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_first_name_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.fname+' </div><div style="clear:both;"></div></div>';
            	    //   html+='<tr><td>First Name : '+eachtktdetails.fname+'</td></tr>';
            		 
                     if(eachtktdetails.lname!='')
                    	 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_last_name_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.lname+' </div><div style="clear:both;"></div></div>';
                    	 // html+='<tr><td>Last Name : '+eachtktdetails.lname+'</td></tr>';
                     
                     if(eachtktdetails.email!='')
                    	 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+props.trans_email_lbl+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+eachtktdetails.email+' </div><div style="clear:both;"></div></div>';
                   //  html+='<tr><td>Email : '+eachtktdetails.email+'</td></tr>';
                     
                     var pkey=eachtktdetails.profilekey;
                     var seatevt=tidObj.seatingevt;
                     
                     
                     
                     var attcustomattribs=tidObj.attribmap[pkey];
                     if(attcustomattribs!=undefined && attcustomattribs!='undefined' && attcustomattribs.length>0 ){
                     for(var atts=0;atts<attcustomattribs.length;atts++){
						if(attcustomattribs[atts]['response']!='')
							html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-2 col-sm-6 col-xs-6 control-label"><label>'+attcustomattribs[atts]['question']+':</label></div><div class="col-md-8 col-sm-6 col-xs-6 aFname" >'+attcustomattribs[atts]['response']+' </div><div style="clear:both;"></div></div>';
							// html+='<tr><td>'+attcustomattribs[atts]['question']+' : '+attcustomattribs[atts]['response']+'</td></tr>';
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
    }else{
    	html+='<table><tr><td>'+props.rsvp_no_attendee_to_display+'</td></tr></table>';
    }
	return html;
}





function closeTransactionTickets(){
	$('tr.nowell').remove();
	$('tr.info').removeClass('info');
	 $("html, body").animate({scrollTop: $('#'+globalTrId).offset().top - 60}, "slow",function(){});
}



$(document).ready(function(){
	
	$(document).click(function(evt){  
		if(evt.target.className=='manage')
			return
			else
			$('.dropdown-menu').hide();
	});
	
	
	
	$(document).on('click','.manage',function(){
		var tid=$(this).data('manage');
		
	if($(this).parent().find('ul').is(':hidden')){
		$('.dropdown-menu').hide();
		$(this).parent().find('ul').show();
	}else{
		$('.dropdown-menu').hide();
	}
	});
	
	
	$(document).on('click','.resendemail',function(){
		$('tr.nowell').remove();
		var tid=$(this).data('tid');
		globalTrId='reports_'+tid;
		var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=RSVP';
		var dynatimestamp = new Date();
		url += '&dynatimestamp='+dynatimestamp.getTime();
		$.ajax({
			type:'get',
			url:url,
			success:function(result){
				if(!isValidActionMessage(result)) return;
			}
		});
		
		//var html='<tr class="nowell"><td colspan="6"><div class="row well" style="margin:0px;"><div class="row"><div class="col-md-12"><span id="mailsent"><center><img src="../images/ajax-loader.gif"></center></span></div></div><br/><center><button onclick="closeTransactionTickets();" class="btn btn-cancel"><i class="fa fa-times"></i></button></center></div></td></tr>';
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
	    	notification(props.trans_mail_sent_msg,'success');
	    	//$('#mailsent').html('<div class="alert alert-success alert-dismissable page-alert" style=""><button class="close close-notification" type="button"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Mail sent successfully.</div>');
	    },1000);
	});
	
	
$(document).on( "click",".clickk",function(e) {
		
		divOpen=false;
		element=$(this);
		$('tr.nowell').remove();
		/*$(this).parents('tr').prev().removeClass('info');
	    $(this).parents('tr').remove();*/
		
		//loadingPopup();
		var purpose=$(this).data('type');
		var tidObj;
		var chkboxlen;
		var isrecurring=$('#isrecur').val();
		var powertype='RSVP';
		var paymenttype='${paymentType}';
		
		if(actionType=='response'){
			chkboxlen=$('input[name="resFields"]:checked').length;
		}else{
			chkboxlen=$('input[name="checkinFields"]:checked').length;
		}
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
		
		var len=$.map(tidObj.transdetails, function(n, i) { return i; }).length;
		var tktlen=$.map(tidObj.tktdetails, function(n, i) { return i; }).length;
		var buyerlen=$.map(tidObj.buyerdetails, function(n, i) { return i; }).length;
		var attlen=$.map(tidObj.attendeedetails, function(n, i) { return i; }).length;
		var paymentstatus=tidObj.transdetails.paymentstatus;
		var bookingdomain=tidObj.transdetails.bookingdomain;
		var refund=tidObj.transdetails.refund;
		var profilecount=tidObj.profilecount;
		var currencySymbol=tidObj.currencySymbol;
		e.preventDefault();
	    if (divOpen) return;
	    //chkboxlen=4;
	    var html = '<tr class="nowell">' +
	        '<td colspan="6">' +
	        '<div class="row well" style="margin:0px;">' ;
	        
	        
	      /*html+=  '<span class="pull-right">' +
	        '<button class="btn btn-active btn-xs closeTransactionDetails">' +
	        '<i class="fa fa-times"></i>' +
	        '</button>' +
	        '</span>' ;*/
	    
	    if(tidObj!=undefined && len>0){
           	if(purpose=='transactiondetails'){
    	    	 html+=getTransactionDetails(tidObj);
           	     html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><div class="col-md-4 col-sm-6 col-xs-6 control-label"></div><div class="col-md-8 col-sm-6 col-xs-6 aFname"> <label><button class="btn btn-cancel" onclick="closeTransactionTickets();"><i class="fa fa-times"></i></button></label></div><div style="clear:both;"></div></div>';
           	}else if(purpose=='ticketdetails')
           		html+=getTicketDetails(tidObj);
           	else if(purpose=='buyerdetails')
           		html+=getBuyerDetails(tidObj);
           	else if(purpose=='attendeedetails'){
           		html+=getAttendeeDetails(tidObj,tid);
           	    html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal"><center><button class="btn btn-cancel" onclick="closeTransactionTickets();" ><i class="fa fa-times"></i></button></center></div>';
           	}else if(purpose=='changestatus'){
           		html+=getChangeStatusDetails(tidObj,tid);
           	 html+='<div class="row col-md-12 col-sm-12 col-xs-12 form-horizontal" id="statusdiv"><center><button class="btn btn-cancel" onclick="closeTransactionTickets();" ><i class="fa fa-times"></i></button></center></div>';
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