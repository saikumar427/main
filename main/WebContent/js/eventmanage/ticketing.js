function startHoverEfects(){
	 $(document).on('mouseover','.eachtktli',function() {
		 $(this).find('.temp').removeClass('hidden').addClass('visible');
	 });
	 
	 $(document).on('mouseout','.eachtktli',function() {
		 $(this).find('.temp').removeClass('visible').addClass('hidden');
});
	 
	 $(document).on('mouseover','.eachgrpli',function() {
		 $(this).find('.grptemp').removeClass('hidden').addClass('visible');
	 });
	 
	 $(document).on('mouseout','.eachgrpli',function() {
		 $(this).find('.grptemp').removeClass('visible').addClass('hidden');
});
	 
	 
	 $(document).on('click','.tkttoggle',function(){
		 if($('#tktdetails').is(':hidden')){
			 $(this).find('span.glyphicon').addClass('down').removeClass('original');
			 $('#tktdetails').slideDown();
		 }else{
			 $('#tktdetails').slideUp();
			$(this).find('span.glyphicon').addClass('original').removeClass('down');
		 }
	 });
}
var editTktId='';
var totalTktCount=0;

function stopHoverEfects(tktid){
	/*$('ul#tickets li').each(function(){
		$(this).removeClass('eachtktli').removeClass('hover-item');
	});
	$('div.tempgrpdiv').each(function(){
		$(this).removeClass('eachgrpli');
	});
	 $('#'+tktid).addClass('backgroundclr');
	 $('#'+tktid).find('.temp').addClass('visible');*/
}

function restartHoverEfects(){
	/*$('ul#tickets li').each(function(){
		if(!$(this).hasClass("category")){
		$(this).addClass('eachtktli').addClass('hover-item').removeClass('backgroundclr');
		$(this).find('.temp').removeClass('visible').addClass('hidden');
		}
		$('div.tempgrpdiv').each(function(){
			$(this).addClass('eachgrpli');
		});
	});*/
}

function showHideTickets(){
	var tktId=showHideId.split('_')[1];
	var divid=showHideId;
	var url='';
	/*var flag=false;
	flag=isTicketHasRule(tktId);
	if(flag)
		bootbox.alert("Ticket has ticketing rules, so ticket can't be hide.");
	else{*/	
		// if($('#'+divid).hasClass("btn-active")){
			 loadingPopup();
			 var type='Y';
			 if(divid.indexOf('hide')>-1)
				 type='N';
			 if(isrecuring=='true')
				 	url='TicketShowHide!updateRecurring?eid='+eid+'&ticketid='+tktId+'&show='+type+"&eventdate="+document.getElementById('source').value;	
				 else
					url='TicketShowHide!update?eid='+eid+'&seltickets='+tktId+'&show='+type;
			 
			 	$.ajax({
			 		url:url,
			 		success:function(result){
			 			hidePopup();
			 			 if(!isValidActionMessage(result)) return;
			 			if(type=='Y'){
			 				$('#dataShowHide_'+tktId).html('<a href="javascript:;" class="showhide" id="hide_'+tktId+'"> '+props.global_hide_btn_lbl+' </a>  &nbsp;');
			 				notification(props.widgets_status_msg,'success'); 
			 				// $('#show_'+tktId).addClass('btn-default').removeClass('btn-active');
				 			 // $('#hide_'+tktId).removeClass('btn-default').addClass('btn-active');
				 			  $('ul#'+tktId).find("[data-tktid="+tktId+"]").html(props.global_active_lbl);
			 			}else{
			 				$('#dataShowHide_'+tktId).html('<a href="javascript:;" class="showhide" id="show_'+tktId+'"> '+props.global_show_btn_lbl+' </a>  &nbsp;');
			 				notification(props.widgets_status_msg,'success'); 
			 				//	$('#show_'+tktId).removeClass('btn-default').addClass('btn-active');
						 	//	$('#hide_'+tktId).addClass('btn-default').removeClass('btn-active');
			 			}
           			    /*getReloadedData();*/
			 		}
			 	});
		// }
   //}
}

var showHideId='';

$(document).ready(function(){
	startHoverEfects();
	 $(document).on('click','.showhide',function(){
		 showHideId=$(this).attr('id');
		 specialFee(eid,"200","showhidetickets","Ticketing");
	 });
	 
	 $(document).on('keyup','#tktavailid',function(){
		 if($.trim($('#ticketid').val())=='' && !isNaN($(this).val())){
			 if(parseInt($(this).val())<=5)
				 $('#tktmaxid').val($(this).val());
			 else
				 $('#tktmaxid').val('5');
		 }
	 });
	 
	$('#addticket').click(function(){
		showProcessing('tickets');
		arrangeTktDonationBoxes();
		openNewTicket('');
		disableTicketDonationButtons();
		$('#ticketData_ticketName').focus();
	});
	
	 $('#tktbox').on('click','#tktsettings',function(){
	        $('#tktdetails').slideToggle("slow");
	    });
	 
	 $('#tktbox').on('click','.canceladdticket',function(){
	       $('#addtickettemplate').fadeOut();
	       $('#tktbox').html('');
	       $('#tktbox').css({"margin-top":"0px"});
	       restartHoverEfects();
	       enableTicketDonationButtons();
	       $('html, body').animate({ scrollTop: $("#tickets").offset().top-200 },1000);
	   });
	 
	 $('#groupbox').on('click','.cancelgroupbox',function(){
		 $('#addgrouptemplate').fadeOut();
	       $('#groupbox').html('');
	       $('#groupbox').css("margin-top","0px");
	       enableTicketDonationButtons();
	       $('html, body').animate({ scrollTop: $("#tickets").offset().top-200 },1000);
	 });
	 
	 $(document).on('click','.canceladddonation',function(){
		 $('#adddonationtemplate').fadeOut();
	       $('#donationbox').html('');
	       $('#donationbox').css({"margin-top":"0px"});
	       restartHoverEfects();
	       enableTicketDonationButtons();
	       $('html, body').animate({ scrollTop: $("#tickets").offset().top-200 },1000);
	 });
	 
	 $(document).on('click','#donationsettings',function(){
		 $('#donationdetails').slideToggle("slow");
	 });
	 
	 
	 $('#tktbox').on('click','.confirmaddticket',function(){
		 trimAllTicketInputValues();
		 $(this).prop("disabled",true);
		 addTicket();
		
	 });
	 
	 $('#groupbox').on('click','.confirmgroupticket',function(){
		 $('#grpname').val($.trim($('#grpname').val()));
		 $('#grpdesc').val($.trim($('#grpdesc').val()));
		 var grpName=$('#grpname').val();
		 $(this).prop('disabled',true);
		 var grpDesc=$('#grpdesc').val();
		 var grpId=$('#grpId').val();
		 if(grpName==''){
			var html='<ul class="errorMessage"><li><span>'+props.mt_enter_group_name+'</span></li></ul>';
			$('#groupformerrormessages').html(html);
			$('#groupformerrormessages').show();
			 $(this).prop('disabled',false);
		 }else
			 addGroup(grpName,grpDesc,grpId);
	 });
	 
	 $(document).on('click','.confirmadddonation',function(){
		 trimAllDonationInputValues();
		 $(this).prop('disabled',true);
		addDonation(); 
	 });
	 
	 $(document).on('click','.editticket',function(){
		//loadingPopup();
		 var tktid=$(this).parents('li').attr('id');
		 stopHoverEfects(tktid);
		 editTktId=tktid;
		 showProcessing(tktid);
		 arrangeTktDonationBoxes();
		 openEditTicket(tktid,'edit');
		 disableTicketDonationButtons();
		 //alert("hi this");
		 $('html, body').animate({ scrollTop: $("#"+tktid).offset().top-55 },1000);
	 });
	 
	 $(document).on('click','.duplicateticket',function(){
		 var tktid=$(this).parents('li').attr('id');
		 editTktId=tktid
		 stopHoverEfects(tktid);
		 showProcessing(tktid);
		 arrangeTktDonationBoxes();
		 openEditTicket(tktid,'copy');
		 hideProcessing();
		// $('#tktbox').css({"margin-top":"-3px"});
		 disableTicketDonationButtons();
		 $('html, body').animate({ scrollTop: $("#"+tktid).offset().top-55 },1000);
	 });
	 
	 
	 $('#createticketcategory').on('click',function() { 
		openGroupBox('');
		disableTicketDonationButtons();
		//$('html, body').animate({ scrollTop: $("#tickets").offset().top }, 'slow');
	 });
	 
	 $(document).on('click','.deleteticketcategory',function() {
	        if(isAdded==false && emptyGrps.length>0){
	        	if(emptyGrps[0]==$(this).data('na'))
	        		isAdded=true;
	        }
	        deleteCategory($(this).data('na'));
	    });
	 
	 $(document).on('click','.deleteticket',function(){
		 arrangeTktDonationBoxes();
		 var tktid=$(this).parents('li').attr('id');
		 deleteTicket(tktid,$(this).parents('li').data('type'));
	 });
	 
	 $(document).on('click','.addticketundercategory',function(){
		 var grpid=$(this).data('na');
		 showProcessing(grpid);
		 //loadingPopup();
		 $('#donationbox').html('');
		 $('#tktbox').html('');
		 $('#groupbox').html('');
		 openNewTicket(grpid);
		 disableTicketDonationButtons();
		 hideProcessing();
	 });
	 
	 $(document).on('click','.editgrp',function(){
		 var grpid=$(this).data('na');
		 showProcessing(grpid);
		 editCategory(grpid);
		 disableTicketDonationButtons();
	//	 $('#groupbox').css({"margin-top":"-3px"});
	 });
	 
	 $(document).on('click','#adddonation',function(){
		//loadingPopup();
		 showProcessing('tickets');
		arrangeTktDonationBoxes();
		openDonationTicket(); 
		hideProcessing();
		disableTicketDonationButtons();
	 });
	 
	 $(document).on('click','.editdonation',function(){
		 var tktid=$(this).parents('li').attr('id');
		 editTktId=tktid;
		// loadingPopup();
		 showProcessing(tktid);
		 stopHoverEfects(tktid);
		 arrangeTktDonationBoxes();
		 openEditDonation(tktid);
		 disableTicketDonationButtons();
	 //   $('#donationbox').css({"margin-top":"-3px"});
	 });
	 
	 /*$('#seteventcap').on('click',function(){
		 if($('#showCapacity').is(':visible')){
			 $('#showCapacity').slideUp();
			 $('#evtCapacitySpin').removeClass('down').addClass('original');
		 }else{
			specialFee(eid,'200','eventcapacity','Ticketing');
			$('#onefieldtext').attr("placeholder", props.mt_enter_event_capacity);
		 }
	});*/
	 
	 
	 $('#shwhidetkt').on('click',function(){
		 specialFee(eid,'200','showhidetickets','Ticketing');
	});
	 
	 $(document).on('click','#tktshowhidechk',function(){
		  $('.tktshowhide').iCheck('check');
	   });
	   
	   
	   $(document).on('click','#tktshowhideunchk',function(){
			  $('.tktshowhide').iCheck('uncheck');
		   });
	 
	 $('#myModalhtmlin').on('click','.close,.cls',function(){$('#myModalhtmlin').hide();    
		});
	 
	 $('.myTooltip').tooltipster({
	    	maxWidth:'100px',
	    	position:'right'
	    });
	 
	 $(document).on('ifChecked','.waitlists',function(){
		 getWaitListAgreeDiv($(this).attr('value'));
	 });
	 
	 $(document).on('ifChecked','.tktshowhide',function(event){
			if(isrecuring=='true'){
				updaterecurrshowhide($(this),true);
			}
			});
		
		$(document).on('ifUnchecked','.tktshowhide',function(event){
			if(isrecuring=='true'){
				updaterecurrshowhide($(this),false);
			}
			});
	 
	 //Sortable functionality starts 
	    var srcel='';  
	    var srcid='';
	 var tickets = $("ul#tickets").sortable({
	        group: 'nested',
	        handle: '.dragHandle',
	        isValidTarget: function  (item, container) {
	            if(item.is(".category") && container.el[0].id!='tickets')
	              return false;              
	             else 
	              return true;             
	            },
	            onDrop: function  (item, targetContainer, _super) {
	                var clonedItem = $('<li/>').css({height: 0});
	                item.before(clonedItem);
	                clonedItem.animate({'height': item.height()});
	                
	                item.animate(clonedItem.position(), function  () {
	                  clonedItem.detach();
	                  _super(item);
	                });
	              },
	            
	           onDragStart: function(item, container, _super) {
	        	   var offset = item.offset(),
                   pointer = container.rootGroup.pointer;
                   adjustment = {
                     left: pointer.left - offset.left,
                     top: pointer.top - offset.top
                   };
                   _super(item, container);
	            	srcel='';srcid='';
	            	if(item.is(".ticket") && item.parent("ul").is('.categorytickets')){
	            		srcel="g";srcid=item.parent("ul").attr('id').replace('_','');
	            	}            
	            	else if(item.is(".category"))
	            		srcel="gs";            	
	            	else srcel="l";
	               
	              },
	              
	             onDrag: function (item, position) {
	            	
		                item.css({
		                  left: position.left - adjustment.left,
		                  top: position.top - adjustment.top
		                });
		                
		                $('#tktbox').html('');
		                $('#donationbox').html('');
		                $('#groupbox').html('');
		                restartHoverEfects();
		              },    
	              
	          onDrop: function (item, container, _super) {
	        	  var clonedItem = $('<li/>').css({height: 0});
                  item.before(clonedItem);
                  clonedItem.animate({'height': item.height()});
                  item.animate(clonedItem.position(), function  () {
                    clonedItem.detach();
                  });
	        	_super(item, container);
	        	var targetel='';var descid=''; var itemid='';
	        	if(item.is(".ticket") && item.parent("ul").is('.categorytickets')){
	        		targetel="g";
	        		itemid=item.data('id');
	        		descid=item.parent("ul").attr('id').replace('_','');   		
	        	}
	        	else if(item.is(".ticket") && !item.parent("ul").is('.categorytickets'))
	        		  {targetel="l";itemid=item.data('id'); descid=item.data('gid');   }     	
	        	else {targetel='gs';itemid=item.attr('id');descid=itemid;}   
	        	updatePos(srcel+targetel,itemid,descid,srcid,item);
	        	enableTicketDonationButtons();
	        }
	    });
	 
 });
 
var chartData=new Array();

 function arrangeTickets(tktsdata){
	 tktdata=tktsdata;
	 chartData=new Array();
	 $(tktsdata.TicketDetails).each(function(key,value){ 
	    	var html='';    	
	    	if(value.type=='group')
	    	{ html=createCategoryView(value);
	    	  groupid=value.gid;
	    	  $(html).appendTo('#tickets');
	    	  return;
	    	}
	    	else if(value.type=='grpticket')    	 
	    	{html = getTicketViewHtml(value);
	    	$(html).appendTo('#'+groupid +" .categorytickets");
	    	 return;
	    	}
	    	else if(value.type=='looseticket');
	    	{ html = getTicketViewHtml(value);
	    	  $(html).appendTo('#tickets');
	    	  return;
	    	
	    	}
	    });
	if(tktsdata.TicketDetails){
	 if(tktsdata.TicketDetails.length<=0)
	  $("#noTicketsDiv").show();
	 else
		 $("#noTicketsDiv").hide();
	}else
		 $("#noTicketsDiv").show();
	 
	/*if(eventlevelcount=='')
      $('#capacity').html(' '+totalTktCount);*/	 
	
 }
 
 function drawChart(){
	 arrangeTickets(tktdata);
	 if(totalSoldCount>0){
	 $('#pie_global').html(totalSoldCount+'');
	 }else{
	$('#donutchart').html('');	 
	$('#donutdiv').remove();
	$('#donuttext').remove();
	 $('#donutchart').append('<div  style="left:69px; position: relative; text-align: center; top: 65px; width: 100px; z-index: 99;" id="donutdiv"><img src="/main/images/ticketsnosales.png"></img></div>'+
			 '<div style="position: relative; top: -138px; left: 202px;" id="donuttext"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;margin-left: 37px;">0</span><br><span style="font-size: 17px;" class="section-header"> '+props.mt_tkt_sold_msg+'  </span></div>');
	 }
 }
 
 
 
 function getTicketViewHtml(dataObj){
	 var eachtktarr=new Array();
	 var sales=new Object();
	 
	 if(dataObj.isdonation=='N'){
	 totalTktCount=totalTktCount+parseInt(dataObj.maxticket);
	 totalSoldCount=totalSoldCount+parseInt(dataObj.soldqty);
	 sales["f"]=dataObj.name+"   "+dataObj.soldqty+"/"+dataObj.maxticket;
	 sales["v"]=parseInt(dataObj.soldqty);
	 eachtktarr.push(dataObj.name);
	 eachtktarr.push(sales);
	 chartData.push(eachtktarr);
	 }
	 var ticketPrice=dataObj.price;
	 var ticketId=dataObj.tid;
	 var grpId=dataObj.gid;
     var tempprice=Number(ticketPrice).toFixed(2);
     var isDonation=dataObj.isdonation;
     var tktName=dataObj.name;
     var html='';
     
     var tktstatus=tktdata.TicketStatus[ticketId]==undefined?"Hide":tktdata.TicketStatus[ticketId];
     
     var tktsubtextstatus=tktstatus;
     if(tktsubtextstatus=='Hide') tktsubtextstatus=props.tkt_stat_hide;
     else if(tktsubtextstatus=='Closed') tktsubtextstatus=props.global_stat_closed;
     else if(tktsubtextstatus=='Active') tktsubtextstatus=props.global_active_lbl;
     else if(tktsubtextstatus=='Sold Out') tktsubtextstatus=props.tkt_stat_sold_out;
     else if(tktsubtextstatus=='NA') tktsubtextstatus=props.global_status_not_applicable;
     
     var format=removePresentYear(getDateFormat(dataObj.startdate))+', '+dataObj.starttime+' - '+removePresentYear(getDateFormat(dataObj.enddate))+', '+dataObj.endtime;
     if(isDonation=='Y')
     html = '<li class="lists eachtktli ticket hover-item disable-select" id="'+ticketId+'" data-gid="'+grpId+'" data-tktstatus="'+tktstatus+'" data-id="'+ticketId+'" data-type="Donation">';
     else
     html = '<li class="lists eachtktli ticket hover-item disable-select" id="'+ticketId+'" data-gid="'+grpId+'"  data-tktstatus="'+tktstatus+'" data-id="'+ticketId+'" data-type="Ticket">';
     
     if(isDonation=='Y' || isrecuring=='true')
     html+='<div class="row"><div class="col-md-5 col-sm-5 col-xs-12 bottom-gap"><div class="col-md-1 dragHandle row"><img src="/main/bootstrap/img/grippy_large.png" ></div><div class="col-md-11 tktname">'+tktName+'<br><span class="sub-text"></span></div></div><div class="col-md-7 col-sm-7 col-xs-12"><div class="row">';	 
     else
    	 html+='<div class="row"><div class="col-md-5 col-sm-5 col-xs-12 bottom-gap"><div class="col-md-1 dragHandle row"><img src="/main/bootstrap/img/grippy_large.png"></div><div class="col-md-11 tktname">'+tktName+'<br><span class="sub-text" >'+format+'</span></div></div><div class="col-md-7 col-sm-7 col-xs-12" ><div class="row">';
     
     
     
    /* <div class="row"><div class="col-md-5"><img src="http://ssl.gstatic.com/ui/v1/icons/mail/grippy_large.png" 
    	 class="dragHandle">&nbsp;'+tktName+'<br><span class="sub-text sort-el">&nbsp;&nbsp;&nbsp;Available: '+dataObj.startdate+'-'+dataObj.enddate+'<span>
    	 &nbsp;<span class="sub-text">&nbsp;&nbsp;Sold:'+dataObj.soldqty+'<span></div><div class="col-md-7"  style=" margin-top: 8px;"><span class="pull-right">*/
     var showyn=dataObj.showyn;
     var cls='';
     var othercls='';
     if(showyn=='Y'){
    	 cls='btn-default';
    	 othercls='btn-active';
     }else{
    	 cls='btn-active';
    	 othercls='btn-default';
     }
     	if(isDonation=='N')
     		html+='<div class="col-md-3 col-sm-3 col-xs-4 tktprice sm-font">'+currenySymbol+''+numberWithCommas(Number(dataObj.price))+'<br/><span class="sub-text tktstatus" data-tktid="'+ticketId+'">'+tktsubtextstatus+'</span></div>';
      var donationsold='0px';
      var donationshowhide=0;
      var tktsold=0;
      var tktshowhide=0;
      if(dataObj.type=='grpticket'){
    	  //tktsold="38px";tktshowhide="13px";donationsold="42px";donationshowhide="12px";
    	  tktsold="50px";tktshowhide="28px";donationsold="56px";donationshowhide="28px";
      }else{
    	  tktsold="45px";tktshowhide="22px";donationsold="51px";donationshowhide="22px";
      }
     	
    //removed for Hide/Show link (toggleButton to link) start
      if(isDonation=='N')
     	 html+='<div  class="col-md-4 col-sm-5 col-xs-12 bottom-gap sm-font"><div style="margin-left:'+tktsold+';" id="sold_'+ticketId+'">'+dataObj.soldqty+'/'+dataObj.maxticket+'</div>'+
     	 '<div style="margin-left: '+tktshowhide+';display:none;" data-toggle="buttons" class="btn-group btn-multiple btn-group-xs" id="showhide_'+ticketId+'"><label id="show_'+ticketId+'" class="showhide col-xs-6 col-md-6 col-sm-6 btn '+cls+'">'+props.global_show_btn_lbl+'&nbsp;</label> <label id="hide_'+ticketId+'" class="btn showhide col-xs-6 col-md-6 col-sm-6  '+othercls+' active"> '+props.global_hide_btn_lbl+' &nbsp;</label></div></div>';
      else
     	 html+='<div  class="col-md-3 col-sm-3 col-xs-12 bottom-gap"><span class="sub-text tktstatus" data-tktstatus="'+tktstatus+'">'+tktsubtextstatus+'</span></div><div class="col-md-4 col-xs-12 col-sm-5"><div style="margin-left: '+donationsold+'; id="sold_'+ticketId+'">'+dataObj.soldqty+'</div>'+
     	 '<div style="margin-left: '+donationshowhide+'; display:none;" data-toggle="buttons" class="btn-group btn-multiple btn-group-xs" id="showhide_'+ticketId+'"><label id="show_'+ticketId+'" class="showhide col-xs-6 col-md-6 col-sm-6 btn '+cls+'">'+props.global_show_btn_lbl+'&nbsp;</label> <label id="hide_'+ticketId+'" class="btn showhide col-xs-6 col-md-6 col-sm-6  '+othercls+' active"> '+props.global_hide_btn_lbl+' &nbsp;</label></div></div>';
      
      //removed for Hide/Show link (toggleButton to link) end
      
     	html+='<div class="col-md-5 col-sm-4 col-sm-12 bottom-gap sm-font">';
     	html+='<span class="hidden temp pull-right" style="margin-top:13px;">';
     	if(isDonation=='Y')
     	 		html+='<a href="javascript:;" class="editdonation"> '+props.global_edit_lnk_lbl+' </a>   &nbsp;';
     			else	
     			html+='<a href="javascript:;" class="editticket"> '+props.global_edit_lnk_lbl+' </a>   &nbsp;';
     	
     	

     	if('btn-active'==othercls)
     		html+='<span id="dataShowHide_'+ticketId+'"><a href="javascript:;" class="showhide" id="hide_'+ticketId+'"> '+props.global_hide_btn_lbl+' </a>  &nbsp;</span>';
     	else
     		html+='<span id="dataShowHide_'+ticketId+'"><a href="javascript:;" class="showhide" id="show_'+ticketId+'"> '+props.global_show_btn_lbl+' </a>  &nbsp;</span>';
    
     	
    
     html+='<a href="javascript:;" class="deleteticket"> '+props.global_delete_lnk_lbl+' </a>   &nbsp;';
     if(isDonation=='N')
         html+='<a href="javascript:;" class="duplicateticket">'+props.global_copy_lbl+'</a>   &nbsp;';
     
     //html+='<a class="dragHandle" href="javascript:;"> Move<a/>&nbsp;';
     html+='</span></div>';
     html+='</div></li>';        
     return html;
 }
 
function createCategoryView(dataObj){
	 var grpId=dataObj.gid;
	 var name=dataObj.name;
 	var html = '<li class="lists category" data-gid="'+grpId+'"  id="'+grpId+'">'+
     '&nbsp;<div class="eachgrpli tempgrpdiv" style=" margin-top:-18px;"><label class="_grp_head">&nbsp;<img src="/main/bootstrap/img/grippy_large.png" class="dragHandle" class="dragHandle">&nbsp;'+name+'</label><span class="pull-right hidden grptemp sm-font"><div class="btn-group">'+
         '<a data-na="'+grpId+'" class="editgrp"  href="javascript:;">  '+props.global_edit_lnk_lbl+'  </a> &nbsp;  '+
         ' <a data-na="'+grpId+'" href="javascript:;" class="addticketundercategory">  '+props.mt_add_tkt_btn+' </a> &nbsp;  '+
         ' <a data-na="'+grpId+'" href="javascript:;" class="deleteticketcategory">  '+props.global_delete_lnk_lbl+'  </a> &nbsp;  '+
         '</div>'+
     '</span></div>'+
     //'<br>'+
     '<ul class="list-group categorytickets" id=_'+grpId+'></ul>'+
     '</li>';
 	return html;
 	}

function openNewTicket(groupId){
	var url='TicketDetails?eid='+eid+'&groupId='+groupId+'&evtlevel=200';
	$('#tktbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#tktbox').fadeIn();
		if(groupId!='')
		$('#'+groupId).append($('#tktbox'));
		else
		$('#tktbox').insertAfter($('#tickets'));
		hideProcessing();
		hidePopup();
		$('html, body').animate({ scrollTop: $("#tktbox").offset().top-100}, 1000);
		$('input[name="ticketData.ticketName"]').focus();
	});
}


function openDonationTicket(){
	$('#tktbox').html('');
	var url='TicketDetails?eid='+eid+'&ticketType=Donation&&evtlevel=200';
	$('#donationbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#donationbox').fadeIn();
		$('#donationbox').insertAfter($('#tktbox'));
		hideProcessing();
		hidePopup();
		$('html, body').animate({ scrollTop: $("#donationbox").offset().top-100}, 1000);
		$('#donationname').focus();
	});
}


function openEditTicket(tktid,type){
	var url='';
	if(type=='edit'){
		url='TicketDetails!edit?eid='+eid+'&ticketId='+tktid+'&evtlevel=200';
	}else
	url='TicketDetails!edit?eid='+eid+'&ticketId='+tktid+'&purpose=selecttkt&evtlevel=200';
	$('#tktbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#tktbox').fadeIn();
		$('#tktbox').insertAfter($('#'+tktid));
		hideProcessing();
		hidePopup();
		if(type=='copy')
			$('.confirmaddticket').html(props.global_add_btn);
		$('input[name="ticketData.ticketName"]').focus();
	});
}


function openEditDonation(tktid){
	url='TicketDetails!edit?eid='+eid+'&ticketId='+tktid+'&ticketType=Donation';
	$('tktbox').html('');
	$('#donationbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#donationbox').fadeIn();
		$('#donationbox').insertAfter($('#'+tktid));
		hideProcessing();
		hidePopup();
		$('#donationname').focus();
	});
}


function openGroupBox(groupId){
	arrangeTktDonationBoxes();
	var url='GroupDetails!createGroup?eid='+eid+'&groupId='+groupId;
	$('#groupbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#groupbox').fadeIn();
		$('#groupbox').insertAfter($('#tickets'));
		hidePopup();
		$('html, body').animate({ scrollTop: $("#groupbox").offset().top-100}, 1000);
		$('input[name="group_name"]').focus();
	});
	
}


function addTicket(){
	var url='TicketDetails!save';
	
	
	//alert(JSON.stringify(paramObj,undefined,2));
	//alert(paramObj['ticketData.newStartDate']); 
	
	//alert(isrecuring);
	
	if(isrecuring=='false' && $('#advspecialfee').val()!='')
		$('#waitlist').iCheck('uncheck');
		
	if(Number($('#ticketprice').val())==0)
		$('#processingfee').val('0');
	
	
	if(!$('#description').is(':checked'))
		$('#tktdescription').val('');
	
	if(!$('#quantity').is(':checked')){
		//alert($('#tktavailid').val());
		var quantity=Number($('#tktavailid').val());
		if(quantity==0)quantity=99;
		quantity=quantity<100?quantity:99;
		$('#minqty').val(paramObj['ticketData.minQty']);
		$('#maxqty').val(quantity);
	}
	
	$('#hidminqty').val($('#minqty').val());
	$('#hidmaxqty').val($('#maxqty').val());
	
	
	
	
	if(!$('#datetime').is(':checked')){
		if(isrecuring=='false'){
		$('#tktstart').val(paramObj['ticketData.newStartDate']);	
		$('#tktend').val(paramObj['ticketData.newEndDate']);
		}else{
			$('#ticketData_startBefore').val(paramObj['ticketData.startBefore']);
			$('#ticketData_startHoursBefore').val(paramObj['ticketData.startHoursBefore']);
			$('#ticketData_startMinutesBefore').val(paramObj['ticketData.startMinutesBefore']);
			$('#ticketData_endBefore').val(paramObj['ticketData.endBefore']);
			$('#ticketData_endHoursBefore').val(paramObj['ticketData.endHoursBefore']);
			$('#ticketData_endMinutesBefore').val(paramObj['ticketData.endMinutesBefore']);
		}
	}
	
	$.ajax({
		type:'POST',
		data:$('#ticketingForm').serialize(),
		url:url,
		success:function(result){
			 if(!isValidActionMessage(result)) return;
			if(result.indexOf('errorMessage')>-1){
				$('#ticketformerrormessages').show().html(result);
				$('html, body').animate({ scrollTop: $("#ticketformerrormessages").offset().top-100 }, 'slow');
				$('.confirmaddticket').prop("disabled",false);
			}else{
				showProcessing('tickets');
				enableTicketDonationButtons();
				arrangeTktDonationBoxes();
				getReloadedData();
				hideProcessing();
			}
		}
	});
}


function addDonation(){
	$.ajax( {
		 type:'POST',
		 data:$('#DonationAddForm').serialize(),
		 url: 'TicketDetails!donationSave',
		 success: function( result ) {
			 if(!isValidActionMessage(result)) return;
			 hideProcessing();
			 if(result.indexOf('errorMessage')>-1){   
				 $('#donationformerrormessages').show().html(result);
				 $('html, body').animate({ scrollTop: $("#donationformerrormessages").offset().top-100 }, 'slow');
				 $('.confirmadddonation').prop("disabled",false);
			 }else{
				    enableTicketDonationButtons();
				 	arrangeTktDonationBoxes();
					getReloadedData();
			 }
		 }	 
		 });
}


function createCategory(){
	bootbox.dialog({
		  message: "<input type='text' id='grpname' name='group_name' class='form-control' placeholder= "+props.mt_enter_group_name+" ></input><br/><input id='grpdesc' type='text' name='group_desc' class='form-control' placeholder='Enter Group Description'></input>",
		  title: "Grouped Tickets",
		  buttons: {
			  Close: {
    		      label: "Cancel",
    		      callback: function() {
    		      }
    		    },
    		    Save: {
    		    	label: "Save",
    		    	className: "btn-primary",
    		    	callback: function() {
    		    		addGroup($('#grpname').val(),$('#grpdesc').val(),'');  
  		      }
		    }
		    
		  }
		});
}



var addGroup=function(grpname,grpdesc,id){
	showProcessing('tickets');
	if(grpname!=''){
		var data='';
		if(id!=''){
	data=[{"eid":eid
		   ,"groupData.groupId":id
		   ,"groupData.groupName":grpname
		   ,"groupData.groupDescription":grpdesc
		   }];
		}else{
	data=[{"eid":eid
			   ,"groupData.groupId":""
			   ,"groupData.groupName":grpname
			   ,"groupData.groupDescription":grpdesc
			   }];
	}
	
	$.ajax( {
 	   type: "POST",
 	   url: 'GroupDetails!save',
 	   data:data[0],
 	   async: false,
 	   success: function( t ) {
 		  if(!isValidActionMessage(t)) return;
 		  //loadingPopup();
 		   hideProcessing();
 		  getReloadedData();
 		 enableTicketDonationButtons();
 		 $('.confirmgroupticket').prop('disabled',false);
 		  $('#groupbox').html('');
 		 /* if(id==''){
 			  var JsonObj=JSON.parse(t);
 			 var grpid=JsonObj.id;
 			$('#'+grpid).append($('#tktbox'));
 			 var url='TicketDetails?eid='+eid+'&groupId='+grpid;
 			 $('#tktbox').load(url,function(result){
 				if(!isValidActionMessage(result)) return;
 				$('#tktbox').fadeIn(function(){
 					
 				});
 				
 		  });
 	   }*/
 	   }
  });
	}
};


function deleteCategory(grpid){
	 var flag=false;
	  $('ul#_'+grpid+' li').each(function(){
		  flag=isTicketHasRule($(this).attr('id'));
		  if(flag)
			 return false;
	  });
	
	  if(flag)
		bootbox.alert("Ticket(s) in group has ticketing rules, so group can't be deleted.");
	  else{
		 bootbox.confirm(props.mt_group_delete_msg, function(result) {
	         if(result)
	         	   $.ajax( {
	                	   url:"TicketDetails!deleteGroup?eid="+eid+"&groupId="+grpid,                   	   
	                	  success: function( t ) { 
	                		  if(!isValidActionMessage(t)) return;
	                		  if(t.indexOf('success'>-1)){
	                			  loadingPopup();
	                			  getReloadedData();
	                		  }
	                		  }
	         	   });
	        
	 }); 
	}
}


function deleteTicket(tktid,type){
	var flag=isTicketHasRule(tktid);
	
	if(flag)
		bootbox.alert(props.tkt_has_rules_cont_delete);
	else{
	if(type=="Group")
		type=props.mt_group_lbl;
	else if(type=="Ticket")
		type=props.mt_ticket_lbl;
	else if(type=="Donation")
		type=props.mt_donation_lbl;
	
		bootbox.confirm(type+" "+props.mt_delete_list_msg, function(result) {
        if(result)    { 
        	showProcessing(tktid);
        $.ajax( {
        	   url:"TicketDetails!deleteTicket?eid="+eid+"&ticketId="+tktid,                   	   
        	   success: function( t ) { 
        		   if(!isValidActionMessage(t)) return;
        		  if(t.indexOf('success'>-1)){
        			  enableTicketDonationButtons();
        			  //loadingPopup();
        			  hideProcessing();
        			  getReloadedData();        			  
        		  }
        		  }
 	   });
	}
    });
  }
}


function editCategory(groupId){
	arrangeTktDonationBoxes();
	var url='GroupDetails!createGroup?eid='+eid+'&groupId='+groupId;
	$('#groupbox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$('#groupbox').fadeIn();
		$('#groupbox').insertAfter($('#'+groupId));
		$('.confirmgroupticket').html('Save');
		hidePopup();
		hideProcessing();
	setTimeout(function(){	
		fillEditGrpData(groupId);
	},100);
		$('input[name="group_name"]').focus();
	});
	
	
}

function fillEditGrpData(groupId){
	var data=[];
	 var url='GroupDetails!edit?eid='+eid+'&groupId='+groupId;
	 $.ajax({
	     type: "GET",
	     url: url,
	     async: false,
	     success:function(result){
	    	 if(!isValidActionMessage(result)) return;
	    	data=JSON.parse(result);
	    	 $('#grpId').val(groupId);
	    	$('#grpname').val(data[0]['groupData.groupName']);
	    	$('#grpdesc').val(data[0]['groupData.groupDescription']);
	     }
	     });
}



function arrangeTktDonationBoxes(){
	$('#tktbox').html('');
	$('#donationbox').html('');
	$('#groupbox').html('');
	$('#tktbox').insertAfter($('#tickets'));
	$('#donationbox').insertAfter($('#tktbox'));
	$('#groupbox').insertAfter($('#donationbox'));
}


var openHTMLPopUp=function (header,content,calback,footernreq){
	$('#myModalhtmlin .headlbl').html('');
	$('#myModalhtmlin .modal-body').html('');
	$('#myModalhtmlin .modal-footer').html('');
	$('#myModalhtmlin').show();
	$('#myModalhtmlin .headlbl').html(header);
	$('#myModalhtmlin .modal-body').html(content);
	$('#myModalhtmlin .modal-header').css({padding:0});
	$('#myModalhtmlin .close').css({padding:"3px 25px"});
	$('#myModalhtmlin .headlbl').css({"margin-left":"19px"});
	$('#myModalhtmlin .headlbl').css({"color":"#b0b0b0","font-size":"22px","font-weight":"500"});
	if(footernreq!=false)
	$('#myModalhtmlin .modal-footer').html($('<button class="btn btn-primary" onclick='+calback+'(); >   '+props.global_submit_lbl+'  </button><button class="btn btn-active cls" > '+props.global_cancel_lbl+'  </button>'));
	
};


function setCapcity(){
	 $.ajax( {
	   url: 'TicketDetails!saveEventCapacity',
	   data:$('#EventCapacityForm').serialize(),
	   success: function( t ) {
		   if(!isValidActionMessage(t)) return;
		   if(t.indexOf('errorMessage')>-1){
			   $('#setcapacityerror').show();
			   $('#setcapacityerror').html(t);
			   }else{
				  $('#myModalhtmlin .close').trigger('click');
			   }
	   }
	   });
}


function saveShowHide(){
	 $.ajax( {
  	   url: 'TicketShowHide!update',
  	   data:$('#ticketshowhideform').serialize(),
  	   success: function( t ) {
  		 if(!isValidActionMessage(t)) return;
  		  $('#myModalhtmlin .close').trigger('click');
  	   }
  	   });
	
}


var updatePos =function(type,itemid,descid,srcid,item){
	//alert("the type is::"+type);
	//type="ll";
	var data=[];var obj={};
	if(type=='ll' || type=='gsgs' ){
		obj['grps']=getBelowElemnts(itemid,type);
		obj['type']="ll";    		
	
	}    
    else if(type=='gl'){	    	
    	obj['grps']=getBelowElemnts(itemid,"ll");
		obj['type']=type;
	}
	else if(type=='gg' || type=='lg'){
		if(srcid!=descid){type='ogg';}
		obj['tkts']=getBelowGroupEle(descid,itemid);
		obj['type']=type;
		
	}
	obj['itemid']=itemid;
	obj['descid']=descid;
	obj['srcid']=srcid;
	data.push(obj);
	  $.ajax( {
   	   type: "POST",
   	   url: 'TicketDetails!updatePositions',
   	   data:{"tktJson":JSON.stringify(data[0]),"eid":eid},
   	   success: function( t ) {
   		 if(!isValidActionMessage(t)) return;
   		   var gid=(JSON.parse(t)).gid; 
   			item.attr('data-gid',gid);     		   
   	   }
   	   });
};

var getBelowGroupEle=function(gropid,itemid){
	var flag=false;
	var tkts=[];
	$("#"+gropid).find('[data-id]').each(function(){  
		var id=$(this).data('id');
    	 if(id==itemid)
		 {flag=true;return true;}
		if(flag){tkts.push($(this).data('id'));}
	});
	return tkts;
};

var getBelowElemnts=function(itemid,type){  
	var flag=false;
	var grps=[];
	$("#tickets").find('[data-gid]').each(function(){      		
		var id=$(this).data('gid');
		if(type=='ll'){id=$(this).data('id');}
		if(id==itemid)
		{flag=true;return true;}
		if(flag){grps.push($(this).data('gid'));}
	});
	return grps;
};

function getRecurringDateTickets(){
	if(document.getElementById("recurringdate")){			
		recurringshowtickets(eid);
	}
}

function recurringshowtickets(eid){
	var url='TicketShowHide?eid='+eid;
	if(document.getElementById("recurringdate"))
		url=url+"&eventdate="+document.getElementById("recurringdate").value;
	 $.ajax( {
       	   url: url,
       	   success: function( t ) {
       		 if(!isValidActionMessage(t)) return;
       		$('#myModalhtmlin .modal-body').html(t);
       	 $('input.tktshowhide').iCheck({  
				checkboxClass: 'icheckbox_square-grey', 
				radioClass: 'iradio_square-grey'});
       	   }
       	   });
	
}

function updaterecurrshowhide(checkbox,flag){	
	$('#recurimgload').show();
	var edate=document.getElementById("recurringdate").value;
	var ticketid=checkbox.val();
	var url='TicketShowHide!updateRecurring?eid='+eid+"&eventdate="+edate;
	if(flag)
		url=url+"&ticketid="+ticketid+"&show=Y";
	else
		url=url+"&ticketid="+ticketid+"&show=N";
	
	$.ajax( {
    	   url: url,
    	   success: function( t ) {
    		   if(!isValidActionMessage(t)) return;
    		   $('#recurimgload').hide();
    		}
	});
}

function trimAllTicketInputValues(){
	$('#ticketprice').val($.trim($('#ticketprice').val()));
	$('#tktname').val($.trim($('#tktname').val()));
	$('#tktavailid').val($.trim($('#tktavailid').val()));
	$('#tktdescription').val($.trim($('#tktdescription').val()));
	$('#minqty').val($.trim($('#minqty').val()));
	$('#tktmaxid').val($.trim($('#tktmaxid').val()));
	resetFee();
	$('#processingfee').val($.trim($('#processingfee').val()));
	trimRecurringDates();
}

function trimAllDonationInputValues(){
	$('#donationname').val($.trim($('#donationname').val()));
	$('#donationdesc').val($.trim($('#donationdesc').val()));
	trimRecurringDates();
}

function trimRecurringDates(){
	$('input[name="ticketData.startBefore"]').val($.trim($('input[name="ticketData.startBefore"]').val()));
	$('input[name="ticketData.startHoursBefore"]').val($.trim($('input[name="ticketData.startHoursBefore"]').val()));
	$('input[name="ticketData.startMinutesBefore"]').val($.trim($('input[name="ticketData.startMinutesBefore"]').val()));
	$('input[name="ticketData.endBefore"]').val($.trim($('input[name="ticketData.endBefore"]').val()));
	$('input[name="ticketData.endHoursBefore"]').val($.trim($('input[name="ticketData.endHoursBefore"]').val()));
	$('input[name="ticketData.endMinutesBefore"]').val($.trim($('input[name="ticketData.endMinutesBefore"]').val()));
}


function getReloadedData(){
	var eventDate='';
	if(document.getElementById('source'))
		eventDate=document.getElementById('source').value;
	$.ajax({
		type:'POST',
		url:'ManageTickets!getModifiedData',
		data:{eid:eventid,eventDate:eventDate},
		async:false,
		success:function(result){
			 if(!isValidActionMessage(result)) return;
			$('#tktbox').html('');
			$('#donationbox').html('');
			$('#groupbox').html('');
			$('#tktbox').insertAfter($('#tickets'));
			$('#donationbox').insertAfter($('#tktbox'));
			$('#groupbox').insertAfter($('#donationbox'));
			$('#tickets').html('');
			totalTktCount=0;
			totalSoldCount=0;
			arrangeTickets(JSON.parse(result));
			if(typeof ticketsJSONFill =="function") //update tickets in  ticketing rules flow
				ticketsJSONFill(JSON.parse(result).TicketDetails,"2");
			/*to keep chart in tickets page*/
			/*if(totalSoldCount>0){
				 drawCharts(chartData);
				 $('#pie_global').html(totalSoldCount+'');
				 }else{
					 $('#donutchart').html('');
					 $('#donutdiv').remove();
					 $('#donuttext').remove();
				 $('#donutchart').append('<div id="donutdiv" style="left:69px; position: relative; text-align: center; top: 65px; width: 100px; z-index: 99;" ><img src="/main/images/ticketsnosales.png"></img></div>'+
						 '<div style="position: relative; top: -138px; left: 202px;" id="donuttext"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;margin-left: 37px;">0</span><br><span style="font-size: 17px;" class="section-header"> Tickets Sold</span></div>');
				 }*/
			if(JSON.parse(result).TicketDetails.length<=0){
				//$('#addticket').trigger('click');
				
			}
			
			hidePopup();
			$('html, body').animate({ scrollTop: 100 }, 'slow');
		}
	});
}

function setEventCapacity(eid){
	 $.ajax( {
      	   url: 'TicketDetails!getEventCapacity?eid='+eid,
      	   success: function( t ) {
      		 if(!isValidActionMessage(t)) return;
			openHTMLPopUp('Event Level Capacity',t,'setCapcity',true);
			   $('input.evtcapacity').iCheck({  
					checkboxClass: 'icheckbox_square-grey', 
					radioClass: 'iradio_square-grey'});
      	   }
      	   });
}

function showHideTicketScreen(eid){
	 $.ajax({
      	   url: 'TicketShowHide?eid='+eid,
      	   success: function( t ) {
      		 if(!isValidActionMessage(t)) return;
   		if(isrecuring=='true')
   		openHTMLPopUp('Tickets',t,'getRecurringDateTickets',false);
	       		else
			openHTMLPopUp('Tickets',t,'saveShowHide',true);
   		
   		 $('input.tktshowhide').iCheck({  
					checkboxClass: 'icheckbox_square-grey',  
					radioClass: 'iradio_square-grey'});
      	   }
      	   });
}



function showProcessing(divid){
	$('#loading').remove();
	var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$('#'+divid).after(html);
}

function hideProcessing(){
	$('#loading').remove();
}


function responseStatus(id){
	if(id=='agreebtn'){
		var eid=document.getElementById("eid").value;
		var evtlevel="200";
		var splfee=document.getElementById("specialfee").value;
		var url="TicketDetails!agreeServiceFee?eid="+eid+"&evtlevel="+evtlevel+"&specialfee="+splfee;
		$.ajax({
			type:'GET',
			url:url,
			async:false,
			success:function(result){
				 if(!isValidActionMessage(result)) return;
				document.getElementById('evtlevel').value=evtlevel;
				document.getElementById('specialfee').value='';
				document.getElementById('processingfee').removeAttribute('oncopy');
				document.getElementById('processingfee').removeAttribute('onpaste');
				document.getElementById('processingfee').removeAttribute('oncut');
			}
		});
		document.getElementById('tktlevelagree').style.display='none';
	}else if(id=='waitlistagreebtn'){
		var eid=document.getElementById("eid").value;
		var evtlevel="300";
		var splfee=document.getElementById("advspecialfee").value;
		var url="TicketDetails!agreeServiceFee?eid="+eid+"&evtlevel="+evtlevel+"&specialfee="+splfee;
		$.ajax({
			type:'GET',
			url:url,
			async:false,
			success:function(result){
				 if(!isValidActionMessage(result)) return;
				document.getElementById('evtlevel').value=evtlevel;
				document.getElementById('specialfee').value='';
				document.getElementById('advspecialfee').value='';
				document.getElementById('processingfee').removeAttribute('oncopy');
				document.getElementById('processingfee').removeAttribute('onpaste');
				document.getElementById('processingfee').removeAttribute('oncut');
			}
			
		});
		
		document.getElementById('tktlevelagree').style.display='none';
		document.getElementById('waitlistagree').style.display='none';
	}else if(id=='cancelbtn'){
		document.getElementById('tktlevelagree').style.display='none';
		document.getElementById('processingfee').removeAttribute("oncopy");
		document.getElementById('processingfee').removeAttribute("oncut");
		document.getElementById('processingfee').removeAttribute("onpaste");
		resetFee();
	}else if(id=='waitlist_cancelbtn'){
		document.getElementById('waitlistagree').style.display='none';
	}	
}

function resetFee(){
	var specialfee=document.getElementById('specialfee').value;
	if(specialfee!=''){
		var tktservicfee=document.getElementById('tktservicfee').value;
		    document.getElementById('processingfee').value=tktservicfee;
	 }	
}

function getTicketLevelAgreeDiv(){
	var specialfee=document.getElementById('specialfee').value;
	if(specialfee!=''){
		document.getElementById('tktlevelagree').style.display='block';
		document.getElementById('processingfee').setAttribute("oncopy","return false");
		document.getElementById('processingfee').setAttribute("oncut","return false");
		document.getElementById('processingfee').setAttribute("onpaste","return false");
	}else{
		document.getElementById('tktlevelagree').style.display='none';
	}
}

function getWaitListAgreeDiv(type){
	var advspecialfee=document.getElementById('advspecialfee').value;
	if(advspecialfee!='' && type!='NO'){
		document.getElementById('waitlistagree').style.display='block';
	}else{
		document.getElementById('waitlistagree').style.display='none';
	}
}


function drawCharts(chartdata) {
	  
	//alert(JSON.stringify(chartdata,undefined,2));
	/*var chartdata=[["ticket one",{"f":"ticket two - 1","v":16}],["ticket two",{"f":"ticket two - 3","v":24}],
	               ["ticket one",{"f":"ticket two - 1","v":11}],["ticket one",{"f":"ticket two - 1","v":9}],
	               ["ticket one",{"f":"ticket two - 1","v":0}],["ticket one",{"f":"ticket two - 1","v":9}],
	               ["ticket one",{"f":"ticket two - 1","v":5}],["ticket one",{"f":"ticket two - 1","v":0}]
	
	];*/
	
	//alert(JSON.stringify(chartdata,undefined,2));
	
	  var data = new google.visualization.DataTable();
      data.addColumn('string', 'a');
      data.addColumn('number', 'b');
      data.addRows(chartdata);

	  var options = {
	    pieHole: 0.4,
	    hAxis: {textPosition: 'in', showTextEvery: 5, slantedText: false, text:'hi this',textStyle: { color: '#FFFFFF', fontSize: 10 } },
	    backgroundColor: '#f3f6fa',
	    'legend':'none',
	    'width':500,
	    'height':500,
	    'top':'80%',
	     pieSliceText: 'value',
	     allowHtml:true,
	     //colors: ['green', '#aaa', 'blue', '#aaa', 'red','#aaa','yellow', '#aaa'],
	     pieSliceBorderColor:'transparent'
	  };

	  var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
	  chart.draw(data, options);
	  
	  $('#donutdiv').remove();
	  $('#donuttext').remove();
	 $('#donutchart').append('<div style="left: 201px; position: relative; text-align: center; top: -272px; width: 100px; z-index: 99;" id="donutdiv"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;">0</span><br><span style="font-size: 17px;" class="section-header">  '+props.mt_tkt_sold_msg+'  </span></div>');
	}


/*var capacitySuccess = function(val){
	
//	$('#saveEventCapacity').
	  //$("#dest").val($.trim($("#dest").val()));
	var evtCapacity=$.trim(val);
	var tkttype='limited';
	$('#existError').css({"color":"red"});
	$('#existError').html('').show();
	if(Math.floor(evtCapacity) == evtCapacity && $.isNumeric(evtCapacity)) {
		if(evtCapacity=='0'){
			tkttype='unlimited';
			//$('#existError').html('Setting Event Capacity to Default');
			$('#existError').css({"color":"green"});
		}
	}else{
		if(evtCapacity!=''){
		$('#existError').html(props.mt_invalid_event_capacity);
		return;
		}else{
			tkttype='unlimited';
			evtCapacity='0';
			//$('#existError').html('Setting Event Capacity to Default');
			$('#existError').css({"color":"green"});
		}
	}
	
	$.ajax({
		url:'TicketDetails!saveEventCapacity?eid='+eid+'&tkttype='+tkttype+'&tktcount='+evtCapacity,
		success:function(result){
			 if(!isValidActionMessage(result)) return;
			if(result.indexOf('Invalid Number')>-1){
				$('#existError').show().html(props.mt_invalid_num_tkt_capacity);
			}else if(result.indexOf('success')>-1){
				 $('#showCapacity').slideUp();
				 if(tkttype=='limited')
				 notification(props.mt_event_capacity_successfully,'success');
				 else
				 notification('','success');	 
				 
				 $('#evtCapacitySpin').addClass('original').removeClass('down');
				if(evtCapacity=='' || evtCapacity=='0'){
				$('#eventcapacity').val('');
				//$('#capacity').html();
					eventlevelcount='';
				 $('#capacity').html(' '+totalTktCount);	 
				}else{	
				eventlevelcount=val;
				$('#eventcapacity').val(evtCapacity);
				$('#capacity').html(evtCapacity);
				}
				
			}
		}
		
		
	});
	
};*/

var capacitycancel = function(){
	$('#singleValuePopUp').hide();
};


function disableTicketDonationButtons(){
	//alert(editTktId);
	if(editTktId!='')
	$('li#'+editTktId).css("border-bottom","0px");
	
	$('#addticket').prop('disabled',true);
	$('#adddonation').prop('disabled',true);
	$('#createticketcategory').prop('disabled',true);
}


function enableTicketDonationButtons(){
	//alert(editTktId);
	if(editTktId!='')
	$('li#'+editTktId).css("border-bottom","1px solid #ddd");
	$('#addticket').prop('disabled',false);
	$('#adddonation').prop('disabled',false);
	$('#createticketcategory').prop('disabled',false);
}


/*function showEventCapacity(capacity){
	$('#existError').hide();
	 $('#showCapacity').slideDown();
	 $('#enteredValue').val(capacity);
	 $('#evtCapacitySpin').removeClass('original').addClass('down');
	 
}

function success(){
	capacitySuccess($('#enteredValue').val());
}


function closeCapacity(){
	$('#showCapacity').slideUp();
	 $('#evtCapacitySpin').addClass('original').removeClass('down');
}*/

function isTicketHasRule(ticketID){	
	var rules=jQuery.extend(true,[], existingTicketingRules.blockRules);
	if(eachRuleTypeTest(rules,ticketID))
		return true;
	rules=jQuery.extend(true, [], existingTicketingRules.mustRules);
	if(eachRuleTypeTest(rules,ticketID))
		return true;	
	rules=jQuery.extend(true, [], existingTicketingRules.requireRules);
	for(var i=0;i<rules.length;i++){
		for(var j=0;j<rules[i].trg.length;j++){
			if(rules[i].trg[j].id==ticketID)
				return true;			
		}		
	}
	return false;	
}

function eachRuleTypeTest(rules,ticketID){
	for(var i=0;i<rules.length;i++){
		if(rules[i].src.id==ticketID){
			return true;
		}
		for(var j=0;j<rules[i].trg.length;j++){
			if(rules[i].trg[j].id==ticketID){
				return true;
			}
		}		
	}
}




