var _eventid='';
var _tickettype='';
var _urlcode='';
var _purpose='';
var _powertype='';      
function specialFee(eventid,tickettype,urlcode,powertype){
  _eventid=eventid;
  _tickettype=tickettype;
  _urlcode=urlcode;
  _powertype=powertype;
  var url='SpecialFee?eid='+eventid+'&ticketingtype='+tickettype+'&source='+urlcode+'&powertype='+powertype;
  $.ajax({
	  type:'get',
	  url:url,
	  success:function(result){
		  if(!isValidActionMessage(result)) return;
		  if(result && result.indexOf("specialfee") > -1){
			  var popuptitle=props.popup_ticketing_title;
			 	if(_powertype=='RSVP') popuptitle=props.popup_rsvp_title;
			 	$('#loadIMG').show();
			 	$(function(){
			 	    $('iframe#upgradepopup').load(function(){
			 	    	$('#loadIMG').hide();
			 	    });
			 	   $('iframe#upgradepopup').attr("src",url);
			 	});
			 	$('#upgradespecialfee').modal('show');
				
		  }else if(result && result.indexOf("blank") > -1){
			  
			  if(_tickettype){
				  //alert(_urlcode);
				  if(_urlcode=='upgradeevent'){
					  parent.closeUpgradePopup();
				  }else if(_urlcode=='LooknFeel_HtmlCssSubmit'){
						//YAHOO.ebee.popup.wait.hide();
						submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
					}else if(_urlcode=='LooknFeel_HtmlCssLoad'){
					//	YAHOO.ebee.popup.wait.hide();
						//submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
					}else if(_urlcode=='integrationwidget'){
						//window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
						integrationWidget();
					}else if(_urlcode=='TrackURL'){
						window.location.href='TrackURL?eid='+_eventid+'&purpose=eventurl';
						//createTrackUrl(_eventid);
					}else if(_urlcode=='integrationeventurl'){
						window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
					}else if(_urlcode=='AddAttendee' && _powertype=='RSVP'){
						window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
					}else if(_urlcode=='TransactionDetails' && _powertype=='RSVP'){
						window.location.href='TransactionDetails?eid='+_eventid+'&type=rsvp';
					}else if(_urlcode=='Badges' && _powertype=='RSVP'){
						window.location.href='Badges?eid='+_eventid+'&purpose=rsvpbadges';
					}else if(_urlcode=='ConfirmationPageSettings' && _powertype=='RSVP'){
						window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
					}else if(_urlcode=='SocialNetworking'){
						changeCurrentLevel();
						submitSocialNetworking();// in eventpagecontent.js submit the form for socialnetworking
					}else if(_urlcode=='EventCustomURL' || _urlcode=='PasswordProtection'){
						if(_urlcode=='EventCustomURL')
							$('#showEdit').slideToggle();
						//openSinglePopUp($('#customurl').offset(),eventurlsuccess,eventurlcancel,$('#customname').val());
						else
						window.location.reload();
					}else if(_urlcode=='RSVPReports'){
					    exportRSVPReports();//in rsvpreports.js for export rsvp reports
					}else if(_urlcode=='managediscounts'){
						//alert("ManageDiscounts?eid=${eid}");
						window.location.href='ManageDiscounts?eid='+_eventid;
					}else if(_urlcode=='eventcapacity'){
					/*	openSinglePopUp($('#seteventcap').offset(),capacitySuccess,capacitycancel,$('#eventcapacity').val());
						$('#onefieldtext').show();						
						$('#singleValuePopUp').css({"left":"964px"});*/
						//alert("hi");
						showEventCapacity($('#eventcapacity').val());
					}else if(_urlcode=='questions')	{
					addtransactionqno();
					}
					else if(_urlcode=='tktquestions')
						{
						addticketqno();
					}else if(_urlcode=='TicketURLs'){
						//createPrivateTicketURLs();
						window.location.href='TicketURLs?eid='+_eventid;
					}else if(_urlcode=='showhidetickets'){
						//showHideTicketScreen(_eventid);
						showHideTickets();
					}else if(_urlcode=='DisplayTickets'){
						DisplayTickets();
					}else if(_urlcode=='TicketingRules'){
						//afterSpcialFeePopup();
						window.location.href='TicketingRules?eid='+_eventid;
					}else if(_urlcode=='BuyerPage'){
						window.location.href='LayOutManager?eid='+_eventid;
					}
					else
						window.location.href=_urlcode+'?eid='+_eventid;
				}
				}else{
				
				}
	  }
  });
}
/*var specialfeescreen = function (responseObj){
	if(!isValidActionMessage(responseObj.responseText)) return;
	_profeature=responseObj.responseText;
	if(responseObj.responseText && responseObj.responseText.indexOf("specialfee") > -1){
		var myButtons = [
	    					{ text: "Agree", handler: submitSpecialFeeForm },
	                 		{ text: "Cancel", handler: handleCancel }
	             		];
		var popuptitle="This Feature Requires Ticketing Upgrade";
	   	if(_powertype=='RSVP') popuptitle="This Feature Requires RSVP Upgrade";
	   	showPopUpDialogWithCustomButtons(responseObj, popuptitle, myButtons);
	}else if(responseObj.responseText && responseObj.responseText.indexOf("blank") > -1){
	if(_tickettype){
		if(_urlcode=='LooknFeel_HtmlCssSubmit'){
			YAHOO.ebee.popup.wait.hide();
			submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
		}else if(_urlcode=='LooknFeel_HtmlCssLoad'){
			YAHOO.ebee.popup.wait.hide();
			//submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
		}else if(_urlcode=='integrationwidget'){
			window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
		}else if(_urlcode=='integrationeventurl'){
			window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
		}else if(_urlcode=='AddAttendee' && _powertype=='RSVP'){
			window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
		}else if(_urlcode=='TransactionDetails' && _powertype=='RSVP'){
			window.location.href='TransactionDetails?eid='+_eventid+'&type=rsvp';
		}else if(_urlcode=='Badges' && _powertype=='RSVP'){
			window.location.href='Badges?eid='+_eventid+'&purpose=rsvpbadges';
		}else if(_urlcode=='ConfirmationPageSettings' && _powertype=='RSVP'){
			window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
		}else if(_urlcode=='SocialNetworking'){
			changeCurrentLevel();
			submitSocialNetworking();// in eventpagecontent.js submit the form for socialnetworking
		}else if(_urlcode=='EventCustomURL' || _urlcode=='PasswordProtection'){
			window.location.reload();
		}else if(_urlcode=='RSVPReports'){
		    exportRSVPReports();//in rsvpreports.js for export rsvp reports
		}else if(_urlcode=='managediscounts'){
			//alert("ManageDiscounts?eid=${eid}");
			window.location.href='ManageDiscounts?eid='+_eventid;
		}else
			window.location.href=_urlcode+'?eid='+_eventid;
	}
	}else{
	
	}
}*/	



function submitform(data){
	 //loadingPopup();
	$.ajax({
		type:'post',
		data:data,
		url:'SpecialFee!specialFee',
		success:function(result){
		       // var result=t.responseText;
		        if(!isValidActionMessage(result)) return;
		        if(result.indexOf("success")>-1){
		        	if(_urlcode=='upgradeevent'){
		        		var params = data.split("&");
		        		var upgradeLvl="";
		        		for (var i = 0; i < params.length; i++){
		        			if (params[i].indexOf("ticketingtype")>-1){
		        				upgradeLvl=params[i].split("=")[1];
		        			}
		        		}
		        		if(upgradeLvl=='200' || upgradeLvl=='150') 
		        			levelChanged('pro');
		        		if(upgradeLvl=='300')
		        			levelChanged('advanced');
		        		if(upgradeLvl=='400')
		        			levelChanged('business');
						  parent.closeUpgradePopup();
					  }
		        	else if(_urlcode=='questions')	{
						parent.closeUpgradePopup();
		        		 addtransactionqno();
		        	}else if(_urlcode=='tktquestions'){
		        		 parent.closeUpgradePopup();
		        		 addticketqno();
		        	}else if(_urlcode=='LooknFeel_HtmlCssSubmit'){
						submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
					}else if(_urlcode=='LooknFeel_HtmlCssLoad'){
					}else if(_urlcode=='integrationwidget'){
						//window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
						integrationWidget();
						parent.closeUpgradePopup();
					}else if(_urlcode=='TrackURL'){
						parent.closeUpgradePopup();
						window.location.href='TrackURL?eid='+_eventid+'&purpose=eventurl';
							//createTrackUrl(_eventid);
					}else if(_urlcode=='integrationeventurl'){
						window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
					}else if(_urlcode=='AddAttendee' && _powertype=='RSVP'){
						parent.closeUpgradePopup();
						window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
					}else if(_urlcode=='TransactionDetails' && _powertype=='RSVP'){
						window.location.href='TransactionDetails?eid='+_eventid+'&type=rsvp';
					}else if(_urlcode=='Badges' && _powertype=='RSVP'){
						parent.closeUpgradePopup();
						window.location.href='Badges?eid='+_eventid+'&purpose=rsvpbadges';
					}else if(_urlcode=='ConfirmationPageSettings' && _powertype=='RSVP'){
						window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
					}else if(_urlcode=='SocialNetworking'){
						changeCurrentLevel();
						submitSocialNetworking(); // in eventpagecontent.js submit the form for socialnetworking
					}else if(_urlcode=='eventcapacity'){
					//	openSinglePopUp($('#seteventcap').offset(),capacitySuccess,capacitycancel,$('#eventcapacity').val());
					//	$('#singleValuePopUp').css({"left":"964px"});
                        showEventCapacity($('#eventcapacity').val());
                        parent.closeUpgradePopup();
					}else if(_urlcode=='TicketURLs'){
						//createPrivateTicketURLs();
						parent.closeUpgradePopup();
						window.location.href='TicketURLs?eid='+_eventid;
					}else if(_urlcode=='showhidetickets'){
						showHideTickets();
						parent.closeUpgradePopup();
					}else if(_urlcode=='EventCustomURL' || _urlcode=='PasswordProtection' || _urlcode=='NoticeBoard'){
						if(_urlcode=='EventCustomURL'){
							if(_powertype=='RSVP')
								$('#evt_cur_lvl').val('150');
							else
								$('#evt_cur_lvl').val('200');
							
							/*if($('#isnonprofit').val()=='N')
									$('#upgrade_level').html('Pro');
								else
									$('#upgrade_level').html('Pro-NonProfit');*/
							$('#showEdit').slideToggle();
							//openSinglePopUp($('#customurl').offset(),eventurlsuccess,eventurlcancel,$('#customname').val());
							parent.closeUpgradePopup();
						}else{
							//window.location.reload();
							parent.closeUpgradePopup();
							parent.enableButtons();
						}
												
					}else if(_urlcode=='RSVPReports'){
						exportRSVPReports();//in rsvpreports.js for export rsvp reports
					}else if(_urlcode=='managediscounts'){
						parent.closeUpgradePopup();
						window.location.href='ManageDiscounts?eid='+_eventid;
					}else if(_urlcode=='DisplayTickets'){
						DisplayTickets();
						parent.closeUpgradePopup();
					}else if(_urlcode=='TicketingRules'){
						parent.closeUpgradePopup();
						window.location.href='TicketingRules?eid='+_eventid;
					}else if(_urlcode=='BuyerPage'){
						window.location.href='LayOutManager?eid='+_eventid;
					}else{
						parent.closeUpgradePopup();
						window.location.href=_urlcode+'?eid='+_eventid;
					}
		        		
		        }else {
	            	$('specialfeeerrormessages').update(result);
		        }
		}
	    });

/*if(_tickettype){
	if(_urlcode=='LooknFeel_HtmlCssSubmit'){
		YAHOO.ebee.popup.dialog.cancel();
		submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
	}else if(_urlcode=='LooknFeel_HtmlCssLoad'){
		YAHOO.ebee.popup.dialog.cancel();
		//submitHtmlnCss();//in looknfeel.js submit the form for htmlcustomization
	}else if(_urlcode=='integrationwidget')
		window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
	else
		window.location.href=_urlcode+'?eid='+_eventid;
}*/
	
}
	
    


function getCCStatus(eventid,actionname,powertype,curlevel,curfee){
   // YAHOO.ebee.popup.wait.show();
	_purpose=actionname;
	_eventid=eventid;
	_powertype=powertype;
	var url='SpecialFee!getCCStatus?eid='+eventid;
	var cardrequired='';
	if(powertype=='RSVP' && curlevel == 150 && curfee == 0)
	  cardrequired='no';
	else if(powertype=='Ticketing' && curfee == 0)
	  cardrequired='no';
	
	if(cardrequired!='no'){
      /*new Ajax.Request(url, {
    	       method: 'post',
    	        onSuccess: function(t){
    	        if(!isValidActionMessage(t.responseText)) return;
			    data=t.responseText;
			    var ccjson=eval('('+data+')');
		        var cardstatus=ccjson.cardstatus;
		        var userid=ccjson.userid;
		        if(cardstatus=='Y'){
		          loadingPage();
		        }else{
		          var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_'+actionname;
                 
                       
                 getcc(userid,"invoice_hold_"+actionname);
                 // loadURLBasedWindow(url,handlesuccessccform);
			    }
			 } 
    	});*/
		
		$.ajax({
			type:'post',
			url:url,
			success:function(t){
				if(!isValidActionMessage(t)) return;
				data=t;
				var ccjson=eval('('+data+')');
		        var cardstatus=ccjson.cardstatus;
		        var userid=ccjson.userid;
		        if(cardstatus=='Y'){
		          loadingPage();
		        }else{
		          var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_'+actionname;
                  getcc(userid,"invoice_hold_"+actionname);
			    }
			}
			
		});
		
	}else{
		//YAHOO.ebee.popup.wait.hide();
		loadingPage();
		
	}
}
	

function loadingPage(){
	if(_purpose=='integrationwidget')
		window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
	else if(_purpose=='integrationeventurl')
		window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
	else if(_purpose=='AddAttendee' && _powertype=='Ticketing')
    	window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd';
    else if(_purpose=='AddAttendee' && _powertype=='RSVP')
    	window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
	else if(_purpose=='TransactionDetails' && _powertype=='RSVP')
		window.location.href='TransactionDetails?eid='+_eventid+'&type=rsvp';
	else if(_purpose=='Badges' && _powertype=='RSVP')
		window.location.href='Badges?eid='+_eventid+'&purpose=rsvpbadges';
	else if(_purpose=='ConfirmationPageSettings' && _powertype=='RSVP')
		window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
	else if(_purpose=='LooknFeel_HtmlCssLoad'){
		// in looknfeel.js submit the form for htmlcustomization
	}
	else if(_purpose=='LooknFeel_HtmlCssSubmit'){
    	submitHtmlnCss(); // in looknfeel.js submit the form for htmlcustomization
	}else if(_purpose=='SocialNetworking'){
		changeCurrentLevel();
		submitSocialNetworking(); // in eventpagecontent.js submit the form for socialnetworking
	}else
		window.location.href=_purpose+'?eid='+_eventid;
	
}

function getAttendeeStatus(eventid,purpose){
  url='/main/eventmanage/AddAttendee!getManagerCCStatus?eid='+eventid+'&purpose='+purpose;
  
  $.ajax({
	 type:'POST',
	 url:url,
	 success:function(result){
		 var ccjson=eval('('+result+')');
	       var cardstatus=ccjson.checkcardstatus;
	       var mgrid=ccjson.mgrid;
	       var mgrType=ccjson.mgrType;
	       if(mgrType=="mgr" && cardstatus=="askcard"){
	       _powertype='Ticketing';
	       _purpose='AddAttendee';
	       _eventid=eventid;
     		getcc(mgrid,'AddAttendee');
	       }else
	       window.location.href='/main/eventmanage/AddAttendee?eid='+eventid+'&purpose=manageradd'
	 }
  });
 	
		   /* new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	       var ccjson=eval('('+t.responseText+')');
	    	       var cardstatus=ccjson.checkcardstatus;
	    	       var mgrid=ccjson.mgrid;
	    	       var mgrType=ccjson.mgrType;
	    	       if(mgrType=="mgr" && cardstatus=="askcard"){
	    	       _powertype='Ticketing';
	    	       _purpose='AddAttendee';
	    	       _eventid=eventid;
    	       		getcc(mgrid,'AddAttendee');
	    	       }else
	    	       window.location.href='/main/eventmanage/AddAttendee?eid='+eventid+'&purpose=manageradd'
	    	       },onFailure:function(){alert("fail");}
	    });*/ 
}	

function getcc(rfid,purp)
{
  var Msg=getPopMsg();
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle(props.myevts_popup_hdr);
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
	 alert(props.myevents_cc_fail_error);
	 }

}
function onsucessclose()
	{
	closeEBpopup();	
	responseDatacc();
	}


	
