var mgrid = '';
var eventid='';
var _eventid='';
var _purpose='';

function getRecurringEventChart(){
	parent.loadingPopup();
	var eventdate=$("#datelist").val();
	var url='Snapshot!getTicketingChartData?eid='+eid+'&eventDate='+eventdate+'&isrecurring='+isrecurring;
	$.ajax({
	  url : url,
	  type: 'get',
	  error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
	  success: function(transport) {
		var result=transport;
		parent.hidePopup();
		if(!isValidActionMessage(result)){
			return;
		}			
		var jsondata=eval('('+result+')');
		if(eventdate=='all')
			getAllRecDatesData(jsondata);
		else
			getTicketData(jsondata);		
      }
});
}

function getTicketData(jsondata){
	//jsondata={"ticketssummary":[{"total_qty":"20","ticket_name":"ticket 1 (kk)","status":"","alldates":"yes","sold_qty":"1","offlinesales":"3","onlinesales":"0"},{"total_qty":"20","ticket_name":"ticket 2 (kk)","status":"","alldates":"yes","sold_qty":"4","offlinesales":"2","onlinesales":"0"},{"total_qty":"20","ticket_name":"ticket 3 (kk)","status":"","alldates":"yes","sold_qty":"8","offlinesales":"3","onlinesales":"0"}]}
	$("#total").html(jsondata.sales.total);$("#ttotal").hide();$("#total").show();
	$("#online").html(jsondata.sales.online);$("#tonline").hide();$("#online").show();
	$("#manual").html(jsondata.sales.manual);$("#tmanual").hide();$("#manual").show();
	$("#ticketsales #tktsales_body tr").remove();
	$("#ticketsales #tktsales_head tr").remove();
	var html='<tr><th>'+props.global_ticket_name_lbl+'</th><th>'+props.sum_total_sold_lbl+'</th></tr>';
	$('#ticketsales #tktsales_head').append($(html));
	$.each(jsondata.ticketsummary,function(outkey,resObj){			
		$.each( resObj, function( inkey, valueobj ) {
		var tempentry=$('<tr><td>'+valueobj.ticket_name+'</td><td>'+valueobj.sold_qty+'/'+valueobj.total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+valueobj.onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+valueobj.offlinesales+'</span></td></tr>');			
           	$('#ticketsales .nodata').remove();
			$('#ticketsales #tktsales_body').append(tempentry);
		});
	});
	
	if(jsondata.ticketsummary.length>5)
        $('#ticketsales').dataTable({
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "bLengthChange": false,
            "aoColumns": [null,null],                                                                                        
        "bFilter": false  
        });
}

function getAllRecDatesData(jsondata){
	//jsondata={"ticketssummary":[{"total_qty":"20","ticket_name":"ticket 1 (kk)","status":"","alldates":"yes","sold_qty":"1","offlinesales":"3","onlinesales":"0"},{"total_qty":"20","ticket_name":"ticket 2 (kk)","status":"","alldates":"yes","sold_qty":"4","offlinesales":"2","onlinesales":"0"},{"total_qty":"20","ticket_name":"ticket 3 (kk)","status":"","alldates":"yes","sold_qty":"8","offlinesales":"3","onlinesales":"0"}]}
	$("#total").html(jsondata.sales.total);$("#ttotal").hide();$("#total").show();
	$("#online").html(jsondata.sales.online);$("#tonline").hide();$("#online").show();
	$("#manual").html(jsondata.sales.manual);$("#tmanual").hide();$("#manual").show();
	$("#ticketsales #tktsales_head tr").remove();
	$("#ticketsales #tktsales_body tr").remove();
		
		var html='<tr><th>'+props.global_ticket_name_lbl+'</th>';
		$.each(jsondata.recTicketDetails.dates,function(outkey,resObj){		
    		html+='<th>'+resObj+'</th>';
		});
		html+='</tr>';
		$('#ticketsales #tktsales_head').append($(html));
		$.each(jsondata.ticketsummary,function(outkey,resObj){	
			var html='<tr><td>'+jsondata.recTicketDetails[outkey].ticket_name+'</td>';
			$.each(jsondata.recTicketDetails.dates,function(dateskey,datesObj){		
				var offlinesales=0,onlinesales=0,sold_qty=0;
				if(resObj[datesObj]!=undefined){
					console.log(resObj[datesObj]);
					if(resObj[datesObj].Manager!=undefined) offlinesales=resObj[datesObj].Manager;
					if(resObj[datesObj].online!=undefined) onlinesales=resObj[datesObj].online;
					if(resObj[datesObj].soldqty!=undefined) sold_qty=resObj[datesObj].soldqty;
					console.log(jsondata.recTicketDetails[outkey].ticket_name);
					html+='<td>'+sold_qty+'/'+jsondata.recTicketDetails[outkey].total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+offlinesales+'</span></td>';			
				}else{
					html+='<td>'+sold_qty+'/'+jsondata.recTicketDetails[outkey].total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+offlinesales+'</span></td>';			
				}
    		});
			html+='</tr>';
			 $('#ticketsales .nodata').remove();
			$('#ticketsales #tktsales_body').append($(html));
		});
		
	
	if(jsondata.ticketsummary.length>0)
        $('#ticketsales').dataTable({
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "bLengthChange": false,
            "aoColumns": [null,null,null,null,null,null],                                                                                        
        "bFilter": false  
        });
}

function UpdateRSVPtoTicketingEvent(eid,count){
	var agree='';
	if(count>0){
		if(count==1){
			agree=props.sum_there_is_help_msg+" "+count+" "+props.sum_change_rsvp_to_tkting_help_msg;

		}else{
			agree=props.sum_there_are_help_msg+" "+count+" "+props.sum_change_rsvps_to_tkting_help_msg;

		}
	}
	else{
		agree=props.sum_rsvp_to_tkting_help_msg;

	}
	var url="Snapshot!RSVPtoTicketing?eid="+eid; 
	 bootbox.confirm(agree, function (result) {
     	if (result){	           	
	parent.loadingPopup();
		$.ajax({
				url : url,
			  type: 'get',
			  error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
			  success: function(transport) {
				var result=transport;
				parent.hidePopup();
				if(!isValidActionMessage(result)){
    				return;
    			}			
					window.location.href="../eventmanage/Snapshot?eid="+eid;
				
		      }
		});	
	}
	
	 });
}

function getRSVPRecurringEventChart(){	
	parent.loadingPopup();
	var eventdate=$("#rsvprecdate").val();
	var url='Snapshot!getRSVPChartData?eid='+eid+'&eventDate='+eventdate;
	$.ajax({
		url : url,
		type: 'get',
		error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
		success: function(t){
			var result=t;
			var jsondata=eval('('+result+')');
			getRSVPData(jsondata);    
		}
    });
}

function getRSVPData(json){
  //console.log("json: "+JSON.stringify(json));
  
   if(json.Attending){
		$("#attending").html(json.Attending);
		$("#rsvp_att").show();
	}else $("#rsvp_att").hide();
	if(json.NotSure){
		$("#notsure").html(json.NotSure);
		$("#rsvp_notsur").show();
	}else $("#rsvp_notsur").hide();
	if(json.NotAttending){
		$("#notattending").html(json.NotAttending);
		$("#rsvp_notatt").show();
	}else $("#rsvp_notatt").hide();
   
  	parent.hidePopup();
}

function copyEventSnapshot(purpose,eid){
parent.loadingPopup();
	$.ajax({
        url: '../myevents/CopyEvent!getMgrCardStatus',
        type: 'post',
        success: function (t) {       
          var jsondata=eval('('+t+')');       	     
	       var checkcardstatus=jsondata.cardstatus;
	       var mgrid=jsondata.mgrid;
	       _eventid=eid;
	       listingevent(checkcardstatus,eid,purpose); 	
	        }
    });     
}
	
	
function listingevent(checkcardstatus,eventid,purpose){	
		$.ajax({
        url: '../myevents/createevent!getEventsCount',
        type: 'post',
        error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
        success: function (t) {       
          var jsondata=eval('('+t+')');        
	    	     	if(!isValidActionMessage(t)) return;
	    	        var data=t;
				       var json=eval('('+data+')');
				       var listingStatus = json.listingStatus;
				       if(listingStatus.indexOf('donot_allow')>-1)
				       		 getListingPopup(mgrid,listingStatus);	
		    	       else 
		    	       {
		    	    	   if(checkcardstatus=="nocard"){			    	     
				    	       var url = 'CopyEvent!copyFromEvent?eid='+eid+'&purpose='+purpose;	
				    	       //alert(url);
			    	 		iframePopup(url);
			    	 		
			    	       }else{
			    	    	   _purpose='CopyEvent';
				    	       _eventid=eid;
				    	       _powertype='';
				    	       getccSnap(mgrid,'copyevent');
		    	           }		    	       
		    	       }
				       parent.hidePopup();
	        }
    });

}	

function getListingPopup(mgrid,listingStatus){
	var url='../myevents/createevent!getListingPopup?mgrId='+mgrid+"&msgType="+listingStatus;
	getIframePopup(url,'listpopupscreen');
}

window.resizeIframe1 = function(id) {
var obj=document.getElementById(id);
obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
}
            
            
function iframePopup(url){ 
	 $('.modal-title').html('Copy Existing Event');		           	
     $('iframe#popup').attr("src",url);
     $('iframe#popup').css("height","230px");			    	  
     $('#myModal').modal('show');  						    
}

function getRecords(){
var mgrid='${mgrId}';
allEventReports(mgrid);
}

function allEventReports(mgrid){
	parent.loadingPopup();
    var url='../myevents/accountreports!getMgrCCStatus?mgrId='+mgrid;   
    	$.ajax({
        url: url,
        type: 'post',
        success: function (t) {      
       if(!isValidActionMessage(t)) return;
       var data=t;
      // alert(data);
       var ccjson=eval('('+data+')');
       var cardstatus=ccjson.cardstatus;
	   var userid=ccjson.userid;
       _purpose='accountlevelreports';
       //YAHOO.ebee.popup.wait.hide();
      // parent.hidePopup();
       if(cardstatus=='popup1'){
    	   getAllReportsDuePopup(mgrid);
       }else if(cardstatus=='charge'){
		   getChargePopup(mgrid);
	   }else if(cardstatus=='popup2' || cardstatus=='authorize'){
    	   getccSnap(mgrid,'accountlevelreports_'+cardstatus);
       }else
           window.location.href="../myevents/accountreports";
      },error:function(){alert(props.global_sys_cant_process_errmsg);}
    });   
}


function getAllReportsDuePopup(mgrid){
var url='../myevents/accountreports!getDuePopup?mgrId=${mgrId}';
getIframePopup(url,'allreports');		
}

function getChargePopup(mgrid){
var url='../myevents/accountreports!getChargePopup?mgrId=${mgrId}';
getIframePopup(url,'chargepopup');
}

function getDuePopup(mgrid,eid){
	var url='../myevents/accountreports!getDuePopup?mgrId=${mgrId}';
	getIframePopup(url,'duepopup');
}



$(document).on('click','.home',function(){	
window.location.href="../myevents/accountreports";
});

$(document).on('click','.account',function(){
window.location.href="../myaccount/home";
});

$(document).on('click','.dueaccount',function(){
	window.location.href="../myaccount/home";
});

function getIframePopup(url,purpose){
 $('#myModal').on('show.bs.modal', function () {
 						if(purpose=='allreports')	{
 						   $('#myModal .modal-header').html('<button type="button" class="close home">&times;</button><h3>'+props.sum_update_your_account_lbl+'</h3>');
 						   }else if(purpose=='chargepopup')	{
 						    $('#myModal .modal-header').html('<button type="button" class="close account">&times;</button><h3>'+props.sum_update_your_account_lbl+'</h3>');
 						   }else if(purpose=='duepopup'){
 							  $('#myModal .modal-header').html('<button type="button" class="close dueaccount">&times;</button><h3>'+props.sum_update_your_account_lbl+'</h3>');
 						   }	    	      
 						   if(purpose=='duepopup'){
 							  $('#myModal .modal-footer').html('<button class="btn btn-primary proceed">'+props.global_proceed_lbl+'</button>');  
 						   }else if(purpose=='listpopupscreen'){
 							  $('#myModal .modal-header').html('<button type="button" >&times;</button><h3>'+props.global_message_lbl+'</h3>');
 						   }else{
			               $('#myModal .modal-footer').html('<button class="btn btn-primary continue">'+props.global_continue_lbl+'</button>');	
 						   }
		                   $('iframe#popup').attr("src",url);
				            });		
				            
				           
		                    $('#myModal').modal('show');    
		   		    	    
						 
						        $('#myModal').on('hide.bs.modal', function () { 
						        $('iframe#popup').attr("src",'');			        
						        $('iframe#popup').css("height","300px");
						       // $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
						    });
}


function getccSnap(rfid,purp){
  var Msg=getPopMsg();
  var setkey= new callPaykey();
  setkey.setCallbackurl("../myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle("Credit Card Details");
  setkey.setSoftdis("");
  setkey.setRefid(rfid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }

}

function onsucessclose()
    {
closeEBpopup();	
	if(_purpose=='myeventspage' || _purpose=='groupeventspage')
		window.location.href="../eventmanage/Snapshot?eid="+eventid;
	else if(_purpose=='accountlevelreports')
		window.location.href="../myevents/accountreports";
	else
		responseDatacc();
	}
	
	
	function closeEBpopup(){

      $('#myModal').modal('hide');     
        $('iframe#popup').attr("src",'');			        
	    $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');   
	}
		
//getData();


	function chanageEventStatus(purpose, eid){		
		var msg='';
		var url="Snapshot!chanageEventStatus?eid="+eid+"&purpose=CANCEL"; 
		if(purpose=='delete'){
			msg="<h3>"+props.sum_delete_event_lbl+"</h3>"+props.sum_event_deleted_from_list_msg;		
		}else if(purpose=='actorspnd'){
			var status=$("#eventstsid").val();
			if(status=="Suspended"){
				purpose='Active';
				msg="<h3>"+props.sum_activate_event_lbl+"</h3>"+props.sum_event_visible_msg;

				url="Snapshot!chanageEventStatus?eid="+eid+"&purpose=ACTIVE"; 
				//setSummaryStatus(url,purpose,eid);
			}else{
				purpose='Suspended';
				msg="<h3>"+props.sum_suspend_event_lbl+"</h3>"+props.sum_event_not_visible_msg;

				url="Snapshot!chanageEventStatus?eid="+eid+"&purpose=PENDING"; 
			}
		}
		 bootbox.confirm(msg, function (result) {			 
	        	if (result){	           	
	        		//parent.loadingPopup();
	        		setSummaryStatus(url,purpose,eid);
	           }
	        });
	}
	
	
	function setSummaryStatus(url,purpose,eid){		
		
		$.ajax({
			url : url,
			type : "get",
			error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
			success : function(t){
				var result=t;
				if(!isValidActionMessage(result)){
    				return;
    			}
				if(purpose=='delete'){
					window.location.href="../myevents/home";
				}else{
					//window.location.href="../eventmanage/Snapshot?eid="+eid;
					if(purpose=="Suspended"){
						$("#eventstsid").val('Suspended');
						$("#statusid").html(props.sum_status_suspended_lbl);

						$("#activate_suspend").html(props.sum_activate_event_lbl);
					}else{
						$("#eventstsid").val('Active');
						$("#statusid").html(props.sum_status_active_lbl);
						$("#activate_suspend").html(props.sum_suspend_event_lbl);
					}
				}
			}
			
		});		
	}
	



	function getCustomUrlBlock(eid){				
		_powertype='${powertype}';
		var curlevel='${currentLevel}';
		var curfee='${currentFee}';
		
		var cardrequired='';
		if(_powertype=='RSVP' && curlevel == 150 && curfee == 0)
		  cardrequired='no';
		
		var url='SpecialFee!getCCStatus?eid='+eid;
		if(_powertype=='RSVP' && curlevel==90){
			specialFee(eid,'150','EventCustomURL',_powertype);
		}else if(_powertype=='RSVP' && cardrequired!='no'){
			$.ajax({
				url : url,
				type : "post",
				error: function(){parent.hidePopup();alert(global_sys_cant_process_errmsg);},
				success : function(t){
					data = t;
					var ccjson=eval('('+data+')');
			        var cardstatus=ccjson.cardstatus;
			        var userid=ccjson.userid;
			        if(cardstatus=='Y'){
			        	var url='IntegrationLinks!customURL?eid='+eid;
			        	callPopUp(url);
			        }else{
			          var url='../payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_customURL';	               
	                    getccSnap(userid,'invoice_hold_customURL');
				    }
				}
			});

		}else{
			var url='IntegrationLinks!customURL?eid='+eid;
			callPopUp(url);		
		}
		
	}
	
	function callPopUp(url){
		 $('#myModal').on('show.bs.modal', function () {			    	      
  	       $('.modal-title').html('Custom URL');		           	
             $('iframe#popup').attr("src",url);    
             //$('iframe#popup').css("height","190px"); 
  	       });					            
	           
              $('#myModal').modal('show');   	

		        $('#myModal').on('hide.bs.modal', function () { 
		        $('iframe#popup').attr("src",'');			
		        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
		    });
	}
	
	
	function upgradeLevel(eid){
		var currentlevel=$("#evt_cur_lvl").val();
		var iframeheight='100px';
		if(currentlevel==100) iframeheight='120px';
		var popuptitle=props.sum_upgrade_tkting_popup_title;

		var tickettype="";
		if(currentlevel=="100") tickettype="200";
		if(currentlevel=="200") tickettype="300";
		if(currentlevel=="90") {tickettype="150";popuptitle=props.sum_upgrade_rsvp_popup_title;}
		var url='SpecialFee!upgradeEvent?eid='+eid+'&ticketingtype='+tickettype+'&source=upgradeevent';
		/*$.ajax({
			url : url,
			type : "post",
			error: function(){parent.hidePopup();alert("System cant process at this time");},
			success : function(t){
				openHTMLPopUp(popuptitle,t,'submitUpgradeFeeForm',true);
				$('.upgradelevel').iCheck({  
		    		checkboxClass: 'icheckbox_square-grey', 
		    		radioClass: 'iradio_square-grey'});
			}
		});*/
		
		$('.modal-title').html(popuptitle);
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height",iframeheight); 
		$('#myModal').modal('show');
		
		//callPopUp(url);
		//loadURLBasedWindow(url,upgradescreen);
	}
	
	function detailedReport(){
		window.location.href='Reports?eid='+eid;
	}

		function disableNTS(){
			var url='../eventmanage/NetworkSelling!disableNTS?eid='+eid;
			callURLandReload(url);
		}
			
		function callURLandReload(url){
			var dynatimestamp=new Date();
			url+='&dynatimestamp='+dynatimestamp.getTime();
			var ajax= 
			$.ajax({
				url: url,
				type: 'get',
				error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
				success: function(t){
					if(!isValidActionMessage(t)) return;
					$("#ntsdisbtn").hide();
					$("#ntsenbbtn").show();
				}
			});
		}

		function displayPromotionsTable(ntsenable){	
				var json=fbpromodata;
				var content = '';var image='';
				content = '<table class="table table-hover dataTable">';
				content+='<thead><th>'+props.global_nts_who_lbl+'</th><th>'+props.global_nts_when_lbl+'</th><th>'+props.global_nts_event_pg_visits_lbl+'</th><th>'+props.global_nts_tkt_sales_lbl+'</th></thead><tbody>';

				  if(ntsenable=='N' && json.promotions.length==0){  
						
						   content+="<tr><td align='right' colspan='4'><button type='button' class='btn btn-primary' onclick='updatentsstatus();'>"+props.global_enable_nts_btn_lbl+"</button></td></tr>";
						}
				   else  if(ntsenable=='Y' && json.promotions.length==0){content+='&nbsp;';}
				$.each(json,function(outkey,resObj){
					$.each( resObj, function( inkey, valueobj ) {
						if(valueobj.name==undefined)valueobj.name='';
						if(valueobj.day==undefined)valueobj.day='';
						if(valueobj.visits==undefined)valueobj.visits='0';
						if(valueobj.visitstotal==undefined)valueobj.visitstotal='0';
						if(valueobj.sales==undefined)valueobj.sales='0';
						if(valueobj.salestotal==undefined)valueobj.salestotal='0';
						image = "<a href='https://www.facebook.com/profile.php?id="+valueobj.fbuid+"' target='_blank'><img title='"+valueobj.name+"' src='https://graph.facebook.com/"+valueobj.fbuid+"/picture' /></a>";
							
							   if(ntsenable=='Y'){
								 
					               content+='<tr><td>'+image+valueobj.name+'</td><td style="padding-top:20px">'+valueobj.day+'&nbsp;<img src="/main/images/home/icon_facebook.png"></td><td style="padding-top:20px">Total:'+valueobj.visits+'&nbsp;Fees:'+valueobj.visitstotal+'</td><td style="padding-top:20px">'+props.global_total_lbl+':'+valueobj.sales+'&nbsp;'+props.global_fees_lbl+':'+valueobj.salestotal+'</td></tr>';
							   }
							   if(ntsenable=='N'){
								   
								   content+='<tr><td>'+image+valueobj.name+'</td><td style="padding-top:20px">'+valueobj.day+'&nbsp;<img src="/main/images/home/icon_facebook.png"></td><td colspan="2"><button type="button" class="btn btn-primary" onclick="updatentsstatus();">'+props.global_enable_nts_btn_lbl+'</button></td></tr>';
							   }
					});
				});
				content+='</tbody></table>';		
				$('#promo').html(content);	
			}
			
		function updatentsstatus(){
			parent.loadingPopup();
			if(ntsenable=='N'){		
			$.ajax({
		    url:"../eventmanage/NetworkSelling!updateNTSStatus?status=Y&eid="+eid,
		    type: 'get',	
		    error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
			success: function(transport) { 
			if(!isValidActionMessage(transport)) return;
			closePopup();
			window.location.href="../eventmanage/NetworkSelling?eid="+eid;
		}
		});
		}
		else{
			closePopup();
			window.location.href="../eventmanage/NetworkSelling?eid="+eid;
		}
		}
  