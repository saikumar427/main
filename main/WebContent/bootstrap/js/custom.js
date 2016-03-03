var monthsArr = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
var days = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
var scrollTo = 100;
var copyLinkLimit=30;
var regExpForTID=/^(RK)[A-Z0-9]{1,}$/;
  $(document).ready(function(){
	  $("#menu-close").click(function(e) {
		e.preventDefault();
		$("#menu-toggle").show(1000);
		$(".menu").toggleClass("active");
		$("#maincontent").addClass("sidebar-close");
		$("#maincontent").removeClass("sidebar-open");
		if($(".breadcrumb")){
			$(".breadcrumb").addClass("breadcrumb-close");
			$(".breadcrumb").removeClass("breadcrumb-open");
		}
	  });
	  $("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#menu-toggle").hide(1000);
		$(".menu").toggleClass("active");
		$("#maincontent").addClass("sidebar-open");
		$("#maincontent").removeClass("sidebar-close");
		$(".breadcrumb").addClass("breadcrumb-open");
		$(".breadcrumb").removeClass("breadcrumb-close");
	  });

/*listener for closing notificaion*/
	$(document).on("click",".close-notification",function(){
		var thisElement=this;
		$(this).parent().slideUp(slideTime,function(){$(thisElement).parent().remove();});		  
	});


  });
  var slideTime = "200";
  
  function openSinglePopUp(position,okFunction,cancelFunction,value){
		if(value)
	   	 $('#onefieldtext').val(value);
		else
			$('#onefieldtext').val('');
		 $('#singleerror').html("");
		 $('#singleerror').hide();
   $('#singleValuePopUp').css({ top: position.top-20, left:  position.left-20}).fadeIn(); 
   $('#singleValuePopUp').find("[key='OK']").off();
    $('#singleValuePopUp').find("[key='CANCEL']").off();
  if(!okFunction)
      okFunction=function(){};
  if(!cancelFunction)
      cancelFunction=function(){};
  $('#singleValuePopUp').find("[key='OK']").click(function(){okFunction($('#singleValuePopUp').find("[key='VALUE']").val());});
  $('#singleValuePopUp').find("[key='CANCEL']").click(function(){ $('#singleValuePopUp').hide();cancelFunction();});
  
}

function openSinglePopUpWithOptions(position,okFunction,cancelFunction,value,lable,defoption){
              $('#defoption').html(defoption);
		if(value)
		   	 $('#onefieldtextoption').val(value);
			else
				$('#onefieldtextoption').val('');
			 $('#singleerroroptions').html("");
			 $('#singleerroroptions').hide();
	  $('#singleValuePopUpOptions').css({ top: position.top-20, left:  position.left-20}).fadeIn(); 
	  $('#singleValuePopUpOptions').find("[key='YES']").off();
	   $('#singleValuePopUpOptions').find("[key='NO']").off();
	   $('#lable').html(lable);
	 if(!okFunction)
	     okFunction=function(){};
	 if(!cancelFunction)
	     cancelFunction=function(){};
	 $('#singleValuePopUpOptions').find("[key='YES']").click(function(){okFunction($('#singleValuePopUpOptions').find("[key='VALUEOPTION']").val());});
	 $('#singleValuePopUpOptions').find("[key='NO']").click(function(){ $('#singleValuePopUpOptions').hide();cancelFunction();});

	} 


	
function loadSpinnerImg(divid){
	var html='<div id="loadingSpinner"><center><img src="../images/ajax-spinner.gif"></center></div>';
	$('#'+divid).after(html);
}

function hideSpinnerImg(){
	$('#loadingSpinner').remove();
}

var isValidActionMessage=function(messageText){
	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
		bootbox.confirm(props.global_no_login_msg,function (result){
			
			 window.location.href="../user/login";
		});
		$('.btn-cancel').hide();
	    return false;
	}else if(messageText.indexOf('Something went wrong')>-1){
		 bootbox.alert(props.global_not_have_perm_msg);
		 return false;
	} 
	return true;	
};

var isValidActionMessageIFRAME=function(messageText){
	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
		bootbox.confirm(props.global_no_login_msg,function (result){
			
			 parent.window.location.href="/main/user/login";
		});
		$('.btn-cancel').hide();
	    return false;
	}else if(messageText.indexOf('Something went wrong')>-1){
		 bootbox.alert(props.global_not_have_perm_msg);
		 return false;
	} 
	return true;	
};

function notification(message, type) {
    var html = '<div class="alert alert-' + type + ' alert-dismissable page-alert">';    
    html += '<button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">'+props.global_close_btn_lbl+'</span></button>';
    html += message;
    html += '</div>';    
    var htmlObject=$.parseHTML(html);
    $("#notification-holder").show();
    $(htmlObject).hide().prependTo('#notification-holder').slideDown();
    setTimeout(function(){
    	$(htmlObject).remove();
    },5000);
};


function statusMessage(id, message, type) {
    var html = '<div class="alert alert-' + type + ' alert-dismissable page-alert">';    
    html += '<button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">'+props.global_close_btn_lbl+'</span></button>';
    html += message;
    html += '</div>';   
    $("#"+id).html('');
    $("#"+id).show();
    $(html).hide().prependTo('#'+id).slideDown();
$('html, body').animate({ scrollTop: $("#"+id).offset().top-100}, 1000);
};

var getDateFormat=function(dateformat){
	//console.log(dateformat);
	var dateary;
	if(dateformat.indexOf("/")>-1){
		dateary=dateformat.split('/');
		return monthsArr[parseInt(dateary[1],10)-1]+' '+dateary[2]+', '+dateary[0];
	}else if(dateformat.indexOf("-")>-1){
		dateary=dateformat.split('-');
		return monthsArr[parseInt(dateary[1],10)-1]+' '+dateary[2]+', '+dateary[0];
	}else return dateformat;
	
};

function getWeekDay(dateForDay) { //var date = new Date('2015/1/31');
	dateForDay=dateForDay.replace(/,/g , "/");//replace all commas with /
	var date = new Date(dateForDay);
	return days[ date.getDay() ];
}

function convertTime24to12(time24){
	var tmpArr = time24.split(':'), time12;	
	if(!tmpArr[1])
		tmpArr.push("00");
	if(+tmpArr[0] == 12) {
	time12 = tmpArr[0] + ':' + tmpArr[1] + ' pm';
	} else {
	if(+tmpArr[0] == 00) {
	time12 = '12:' + tmpArr[1] + ' am';
	} else {
	if(+tmpArr[0] > 12) {
	time12 = (+tmpArr[0]-12) + ':' + tmpArr[1] + ' pm';
	} else {
	time12 = (+tmpArr[0]) + ':' + tmpArr[1] + ' am';
	}
	}
	}
	return time12;
}
function convertTime12to24(time){ //11:00 pm (or) 11:00 PM (or) 11 pm (or) 11 PM (or) 11:0 pm (or) 11:0 PM	
	var hours = Number(time.match(/^(\d+)/)[1]);
	var minutes="0";
	if(time.match(/:(\d+)/))
		minutes = Number(time.match(/:(\d+)/)[1]);
	var AMPM = "AM";
	if(time.match(/\s(.*)$/))
		AMPM=time.match(/\s(.*)$/)[1];
	if((AMPM == "PM" || AMPM == "pm") && hours<12) hours = hours+12;
	if((AMPM == "AM" || AMPM == "am") && hours==12) hours = hours-12;
	var sHours = hours.toString();
	var sMinutes = minutes.toString();
	if(hours<10) sHours = "0" + sHours;
	if(minutes<10) sMinutes = "0" + sMinutes;
	return sHours + ":" + sMinutes;
}


var todatDate=new Date();
var presentYear=todatDate.getFullYear();

function removePresentYear(dateStr){	
	if(dateStr.indexOf(presentYear)>-1){
		var index=dateStr.indexOf(presentYear);		
		return dateStr.substring(0, index-2);
	}
	else
		return dateStr;		
}

function getYear(dateStr){
    return dateStr.substring(13,presentYear.length);
}


function getStatusFormat(status){
	
	if(status=="active")
		status=props.myevents_stat_active;
	
	if(status=="closed")
		status=props.myevents_stat_closed; 
	
	if(status=="suspended")	
		status=props.myevents_stat_suspended;
	
	return status.substring(0,1).toUpperCase()+status.substring(1,status.length).toLowerCase();
	
}


function removePagination(id){ 
	  $('#'+id+'_paginate').hide();
		$('#'+id+'_info').hide();
		$('#'+id+'_br').remove();
		$('#'+id).addClass('no-margin-bottom');
		//$('#'+id+' tr:last-child td  div  .dummy-row-div').css('border-bottom',' 0px');
		
}



function numberWithCommas(x) {
	var temp = parseFloat(x).toFixed(2);
	var parts = temp.toString().split(".");
	parts[0] = parts[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	return parts.join(".");
}

function makeProcessing(btnid){
	$('#'+btnid).prop('disabled',true);
	$('#'+btnid).attr('data-text',$('#'+btnid).html());
	$('#'+btnid).html('<i class="fa fa-spinner fa-pulse" style="color:white;"></i>');
}


function closeProcessing(btnid){
	$('#'+btnid).prop('disabled',false);
	$('#'+btnid).html($('#'+btnid).attr('data-text'));
}

function processingTIDList(TIDlist){
		var TIDarray=TIDlist.split("\n");
		var TIDadd='';
		for(var i=0;i<TIDarray.length;i++){
   		var temp=TIDarray[i];
   		var columnsarry=temp.split(","); 
   		if(columnsarry.length>1){
       		for(var j=0;j<columnsarry.length;j++){
       			if(TIDadd!='')
       				TIDadd=TIDadd+','+columnsarry[j];
       		  	else
       				TIDadd=columnsarry[j];
       		}
   		 }else{
       		 if(temp!=''){
       		 if(TIDadd!='')
       			TIDadd=TIDadd+','+temp;
       		 else
       			TIDadd=temp;
       		 }
   		 }
		}
		return TIDadd;
	}
	
function removeDuplicates(TIDlist){
	  var newTIDlist=new Array(); 
	  label:for(var i=0;i<TIDlist.length;i++){
	  	  TIDlist[i]=TIDlist[i].toUpperCase().replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   	  for(var j=0;j<newTIDlist.length;j++){
   	  	if(newTIDlist[j]==TIDlist[i]) 
   	  	continue label;
   	  }
   	newTIDlist[newTIDlist.length] = TIDlist[i];
	  }
	  return newTIDlist;
	
}


function removeNotificationHeader(){
		 setTimeout(function(){
         	$('#notification-holder').slideUp().html('');
         },3000);
	}


function upgradEventLevel(eid,currentlevel){
	
    console.log('eid'+eid);
    console.log('currentlevel'+currentlevel);
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
$.fn.serializeObject = function(){
	  var o = {};
	  var a = this.serializeArray();
	  $.each(a, function() {
	      if (o[this.name] !== undefined) {
	          if (!o[this.name].push) {
	              o[this.name] = [o[this.name]];
	          }
	          o[this.name].push(this.value || '');
	      } else {
	          o[this.name] = this.value || '';
	      }
	  });
	  return o;
	};

	function isInArray(value, array) {
		  return array.indexOf(value) > -1;
	}
	
	function html2text(html) {
	    var tag = document.createElement('div');
	    tag.innerHTML = html;
	    //return tag.innerText;    this is not working for mozilla firefox browser.
	    return jQuery(tag).text();
	}

