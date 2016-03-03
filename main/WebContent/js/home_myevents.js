
var date='';var arr="";var sdate="",arry="";
var eventid='';
$(function(){		
	$('.tab-content').on('click','.eventmng',function(){		
		var url='/main/myevents/home!getMgrCCStatus?mgrid='+$(this).data('mgrid');
		var eid=$(this).data('eventid');
		//alert(url);
		$.ajax({
			url : url,
	       type: 'post',
	       success: function(t){	
	       if(!isValidActionMessage(t)) return;
	       var data=t;
	       var ccjson=eval('('+data+')');
	      // alert(ccjson);
           var cardstatus=ccjson.cardstatus;
           var userid=ccjson.userid;
           if(cardstatus=='popup1'){
				getDuePopup(userid,eid);
           }else if(cardstatus=='charge'){
           		getChargePopup(userid,eid);
           }else if(cardstatus=='popup2' || cardstatus=='authorize'){
           		getcc(userid,'myeventspage_'+cardstatus);
           }else{
        	 //  alert("gfdg"+eid);
           		window.location.href="/main/eventmanage/Snapshot?eid="+eid;
           }
              
          },
          error:function(){alert("Failure");}
	    });   
		
		/*var url='../eventmanage/Snapshot?eid='+$(this).data('eventid');
		window.location.href=url;*/
	});
	

});   



function isValidActionMessage(messageText){

if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
    	alert(props.global_no_login_msg);
    	window.location.href="../user/login";
    	return false;
    }
 else if(messageText.indexOf('Something went wrong')>-1){
 		alert(props.global_not_have_perm_msg);
 		//YAHOO.ebee.popup.wait.hide();
 		return false;
 	} 
    return true;

}


function copyEvent1(val)
{
/**/
	//alert("load");
$("#eventOptions").hide();
parent.loadingPopup();
var eventid=val.value;
		
	$.ajax({
        url: '/main/myevents/CopyEvent!getMgrCardStatus',
        type: 'post',
        success: function (t) {       
          var jsondata=eval('('+t+')');
	    	       var checkcardstatus=jsondata.cardstatus;
	    	       var mgrid=jsondata.mgrid;
	    	       listingevent(checkcardstatus,eventid);	  	
	        }
    });     
}
	
	
function listingevent(checkcardstatus,eventid){	
		$.ajax({
        url: '/main/myevents/createevent!getEventsCount',
        type: 'post',
        success: function (t) {       
          var jsondata=eval('('+t+')');        
	    	     	if(!isValidActionMessage(t)) return;
	    	        var data=t;
				       var json=eval('('+data+')');
				       var listingStatus = json.listingStatus;
				       var mgrid=jsondata.mgrid;				      
				       if(listingStatus=='donot_allow')
		    	       getListingPopup(mgrid,listingStatus);
		    	       else 
		    	       {
			    	       if(checkcardstatus=="nocard"){			    	    	
			    	 		iframePopup(eventid);
			    	 		
			    	       }else{
			    	       _purpose='CopyExistingEvent';
			    	       _powertype='';
			    	       getcc(mgrid,'copyexistingevent');
			    	      
		    	           }		    	       
		    	       }
				       //parent.hidePopup();
	        }
    });

}	

function getListingPopup(mgrid,listingStatus){
	var url='/main/myevents/createevent!getListingPopup?mgrId='+mgrid+"&msgType="+listingStatus;
	getIframePopup(url,'listpopupscreen');
	}

window.resizeIframe1 = function(id) {
var obj=document.getElementById(id);
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
            }
            
            
function iframePopup(eventid){
      var url = '/main/myevents/CopyEvent?a=1&eid='+eventid;
      $('.modal-title').html(props.copy_event_popup_hdr);		           	
      $('iframe#popup').attr("src",url);
      $('iframe#popup').css("height","50px");		
	  $('#myModal').modal('show');   		    	    
						 
						    
}

function getRecords(){
allEventReports(mgrid);
}




function allEventReports(mgrid){
    //window.location.href='/main/myevents/accountreports';
	//YAHOO.ebee.popup.wait.show();
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
    	   getcc(mgrid,'accountlevelreports_'+cardstatus);
       }else
    	   window.location.href="../myevents/accountreports";
      },error:function()
      {
    	  alert(props.global_failure_error_msg);
      }
    });   
}


function getAllReportsDuePopup(mgrid){
var url='../myevents/accountreports!getDuePopup?mgrId='+mgrid;
getIframePopup(url,'allreports');		
}

function getChargePopup(mgrid){
var url='../myevents/accountreports!getChargePopup?mgrId='+mgrid;
getIframePopup(url,'chargepopup');
}

function getDuePopup(mgrid,eid){
	eventid=eid;
	var url='../myevents/accountreports!getDuePopup?mgrId='+mgrid;
	getIframePopup(url,'duepopup');
	}



$(document).on('click','.home',function(){	
window.location.href="../myevents/accountreports";
});

$(document).on('click','.account',function(){
window.location.href="../myaccount/home";
});

$(document).on('click','.dueaccount',function(){
	window.location.href="../eventmanage/Snapshot?eid="+eventid;
	});





function getIframePopup(url,purpose){
 $('#myModal').on('show.bs.modal', function () {
 						if(purpose=='allreports')	{
 						   $('#myModal .modal-header').html('<button type="button" class="close home">&times;</button><h3>'+props.myevents_cc_popup_updte_acc+'</h3>');
 						   }else if(purpose=='chargepopup')	{
 						    $('#myModal .modal-header').html('<button type="button" class="close account">&times;</button><h4 class="modal-title">'+props.myevents_cc_popup_updte_acc+'</h4>');
 						   }else if(purpose=='duepopup'){
 							  $('#myModal .modal-header').html('<button type="button" class="close dueaccount">&times;</button><h4 class="modal-title">'+props.myevents_cc_popup_updte_acc+'</h4>');
 						   }	    	      
 						   if(purpose=='duepopup'){
 							  $('#myModal .modal-footer').html('<button class="btn btn-primary proceed">'+props.myevents_proceed_btn_lbl+'</button>');  
 						   }else if(purpose=='listpopupscreen'){ 							 
 							  $('#myModal .modal-header').html('<button type="button" >&times;</button><h3>'+props.myevents_cc_msg_lbl+'</h3>');
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


function getcc(rfid,purp)
{
  var Msg=props.myevents_cc_popup_hdr;
  var setkey= new callPaykey();
  //setkey.setCallbackurl("/main/myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  if(purp.indexOf("authorize")>-1){
  	setkey.setPaymode("authvault");
  	setkey.setAmount("50.00");
  }else{
  	setkey.setPaymode("vault");
  	setkey.setAmount("1.00");
  }
  setkey.setMessage(Msg);
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
	//alert("_purpose::"+_purpose);
closeEBpopup();	
	if(_purpose=='myeventspage' || _purpose=='groupeventspage')
		window.location.href="/main/eventmanage/Snapshot?eid="+eventid;
	else if(_purpose=='accountlevelreports')
		window.location.href="/main/myevents/accountreports";
	else
		responseDatacc();
	}
	
	
	function closeEBpopup(){

      $('#myModal').modal('hide');     
        $('iframe#popup').attr("src",'');			        
	    $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');   
	}
		
//getData();

function drawPieCharts() {
    drawChart(lasteventchartdata, 'chart_latestevent');
    drawChart(recentclosedeventchartdata, 'chart_recentevent');
    
  }
  
  function drawChart(chartdata, divid) {
	if(document.getElementById(divid)){
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'a');
        data.addColumn('number', 'b');
        data.addRows(chartdata);
		var options = {
          is3D: 'true',
		  legend: {
		  position: 'right',
		  textStyle:{
		  fontName: 'verdana,arial,helvetica,clean,sans-serif',
          fontSize: 12
		  }
        }
		};
        new google.visualization.PieChart(document.getElementById(divid)).draw(data, options);
        }
}
  