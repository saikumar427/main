var selectedWidList=new Array();
var activationLinkType='';
var waitlist_home_reload=false;
var detlsDivOpen = false;
var datatable=undefined;
var offsetTop=600;
$(document).ready(function(){
	
	$.extend( $.fn.dataTableExt.oSort, {
	    "requested-on-pre": function ( a ) {
	        return $(a).attr('data-tdate-sort');
	    },
	    "requested-on-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	    "requested-on-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	$.extend( $.fn.dataTableExt.oSort, {
	    "expiration-time-pre": function ( a ) {
	        return $(a).attr('data-exp-sort');
	    },
	    "expiration-time-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	    "expiration-time-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	$('input[name="waitlist_id_all"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$('input[name="purpose"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$('input[name="waitListData.tomanager"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	//customizeEmailTemplate(templateJsonData);
	/*if(waitlistdata.length>0)
		instantiateWaitListTable(waitlistdata.WaitListSummary);*/
	
	getWaitlistTicketData('All');
	
	$(document).on('ifChecked','.waitlistchk',function(){
		 callMethod($(this).attr('value'),true);
 	});
	
	$(document).on('ifUnchecked','.waitlistchk',function(){
		 callMethod($(this).attr('value'),false);
 	});
	
	$(document).on('ifChecked','#waitlistchkall',function(){
		checkAll(true);
	});
	
	$(document).on('ifUnchecked','#waitlistchkall',function(){
		checkAll(false);
	});
	
	$(document).on('click','#wl_sel_all',function(){
		checkAll(true);
	});
	
	$(document).on('click','#wl_sel_none',function(){
		checkAll(false);
	});
	
	$(document).on('ifChecked','.contenttype',function(){
		getTemplate($(this).attr('value'));
	});
	
	$(document).on("click",".deleteWaitList",function(){
		var wid=$(this).attr('data-wid');
		deleteWLTransaction(eid,wid);
	});
	
	$(document).on( "click",".waitListDetls",function(e) {
		
		$('.background-options').each(function() {
			var divid=($(this).attr("id"));
	        if($(this).is(":visible")){
	        	$(".waitliststatus").find("span#"+divid+"_img").removeClass("down").addClass("original");
	        	$(".waitliststatus").find("span#"+divid+"_lbl").removeClass("highlighted-text");
	        	$(this).slideUp();
	        }
	    });
		
		checkAll(false);
		var parent = $(this).parents('tr');
		var trId = $(this).parents('tr').attr('id');
		showProcessing(trId);
		$("#edit_wl_tr").next('br').remove();
		$("#gap_tr").remove();
		$("#edit_wl_tr").remove();
		detlsDivOpen=false;
		 var html = '<tr id="gap_tr" height="8px;"></tr><tr class="well" id="edit_wl_tr">' +
	        		'<td><div id="wl_details_div" style="display:none;"></div></td></tr>';
		var wid=$(this).attr('data-wid');
		var url='WaitList!getWLTransactionInfo?eid='+eid+'&wid='+wid;
		$.ajax({
			type:"POST",
			url:url,
			async:false,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				hideProcessing();
				$(html).insertAfter(parent);
				$('<br/>').insertAfter($("#wl_details_div").parents('tr'));
				$('#wl_details_div').html(result);
				$('#wl_details_div').fadeIn();
				$('html,body').animate({
			          scrollTop: $("#wl_details_div").offset().top-150
			        }, 1000);
			}
		});
		
		detlsDivOpen=true;
	});
	
	$(document).on('click', '.closeWaitlistDetails, .cancelactivationlink', function() {
		offsetTop=$(this).offset().top;
		console.log(offsetTop);
		detlsDivOpen = false;
		$("#gap_tr").remove();
		$(this).parents('tr').next('br').remove();
	    $(this).parents('tr').remove();
	    $('html, body').animate({
	        scrollTop: offsetTop-600
	    }, 1000);
	});
	
	
	$(document).on("click",".waitliststatus",function(e) {
		var divid=$(this).attr('data-waitliststatus');
		var arry=divid.split('_');
		var divType=arry[0];
		var wid=arry[1];
		if($("#"+divid).is(":visible")){
			$(this).find("span#"+divid+"_img").removeClass("down").addClass("original");
        	$(this).find("span#"+divid+"_lbl").removeClass("highlighted-text");
        	$("#"+divid).slideUp();
		}else{
			if(divType=='name'){ 
				$("#actlink_"+wid+"_img").removeClass("down").addClass("original");
		     	$("#actlink_"+wid+"_lbl").removeClass("highlighted-text");
		     	$("#actlink_"+wid).hide();
			}else{
				$("#name_"+wid+"_img").removeClass("down").addClass("original");
		     	$("#name_"+wid+"_lbl").removeClass("highlighted-text");
		     	$("#name_"+wid).hide();
			}
			$(this).find("span#"+divid+"_img").removeClass("original").addClass("down");
	     	$(this).find("span#"+divid+"_lbl").addClass("highlighted-text");
			$("#"+divid).slideDown();
		}
		
		triggerCopy(); 
	});
	
	$(document).on("click",".copylink",function(){
		triggerCopy(); 
	});
	
	$(document).on('mouseover','.activation-link',function(){
		var divid=$(this).attr("id");
		if($("#update_"+divid).html().indexOf("http")>-1)
			$(this).find("span.tdcopylink").show();
	});
	 
	 $(document).on('mouseleave','.activation-link',function(){
		 $(this).find("span.tdcopylink").hide();
	});
	 
	$(document).on('mouseenter','.waitlist',function(){
		if($(this).find('.wl-status').attr('data-status')!='Completed'){
			$(this).find('.waitListDetls').removeClass('hidden').addClass('visible');
			$(this).find('.deleteWaitList').removeClass('hidden').addClass('visible');
		}
	});
		 
	 $(document).on('mouseleave','.waitlist',function(){
		 $(this).find('.waitListDetls').removeClass('visible').addClass('hidden');
		 $(this).find('.deleteWaitList').removeClass('visible').addClass('hidden');	
	});
	 
	 $('input.wl-st-radiotype').change(function() {
			var selectedRadio = $(this).attr('value');
			$('input[name="type"][value="'+ selectedRadio + '"]').prop("checked", true);
			$(".wl-status-lbl").each(function() {
				$(this).removeClass('active');
				$(this).removeClass('btn-default');
				$(this).removeClass('btn-active');
			});

			if (selectedRadio == 'Waiting') {
				getWaitlistTicketData('Waiting');
				$('#wl_status_all').addClass('btn-active');
				$('#wl_status_wait').addClass('btn-default').addClass('active');
				$('#wl_status_inpro').addClass('btn-active');
				$('#wl_status_comp').addClass('btn-active');
				$('#wl_status_exp').addClass('btn-active');
			} else if(selectedRadio == 'In Process'){
				getWaitlistTicketData('In Process');
				$('#wl_status_all').addClass('btn-active');
				$('#wl_status_wait').addClass('btn-active');
				$('#wl_status_inpro').addClass('btn-default').addClass('active');
				$('#wl_status_comp').addClass('btn-active');
				$('#wl_status_exp').addClass('btn-active');
			}else if(selectedRadio == 'Completed'){
				getWaitlistTicketData('Completed');
				$('#wl_status_all').addClass('btn-active');
				$('#wl_status_wait').addClass('btn-active');
				$('#wl_status_inpro').addClass('btn-active');
				$('#wl_status_comp').addClass('btn-default').addClass('active');
				$('#wl_status_exp').addClass('btn-active');
			}else if(selectedRadio == 'Expired'){
				getWaitlistTicketData('Expired');
				$('#wl_status_all').addClass('btn-active');
				$('#wl_status_wait').addClass('btn-active');
				$('#wl_status_inpro').addClass('btn-active');
				$('#wl_status_comp').addClass('btn-active');
				$('#wl_status_exp').addClass('btn-default').addClass('active');
			}else{
				getWaitlistTicketData('All');
				$('#wl_status_all').addClass('btn-default').addClass('active');
				$('#wl_status_wait').addClass('btn-active');
				$('#wl_status_inpro').addClass('btn-active');
				$('#wl_status_comp').addClass('btn-active');
				$('#wl_status_exp').addClass('btn-active');
			}
		});
	
});


function instantiateWaitListTable(waitlistsummarydata, status){
	//console.log('instantiateWaitListTable');
	/*$("#mulActLink").removeClass('hidden').addClass('visible');
	$("#mulActLink").show();
	$("#mulActLink").attr("disabled",true);*/
	$("#wait_list_table").empty();
	var waitlistChkAllDisable=true;
	//console.log('length: '+waitlistsummarydata.length);
	if(waitlistsummarydata.length==0){
		//$("#sel_clear_all").hide();
		var html='<tr><td><div class="not-found">'+props.wl_no_data_display_msg+'</div></td></tr>';
		$('#wait_list_table').append($(html));
		//$("#mulActLink").hide();
		removePagination('wait_list_table');
		return false;
	}
	$.each(waitlistsummarydata,function(outkey,valueobj){
		if(status=='All' || status=='Waiting')
			$("#sel_clear_all").show();
		var wid=valueobj.wid;
		var chk='&nbsp;<input type="checkbox" value='+wid+' name="waitlist_id_name" class="waitlistchk" disabled>';
		if(valueobj.status=='Waiting'){
			waitlistChkAllDisable=false;
			chk='&nbsp;<input type="checkbox" value='+wid+' name="waitlist_id_name" class="waitlistchk">';
		}//<div class="col-md-1 col-sm-1 col-xs-1">'+chk+'</div>
		var html='<tr class="waitlist" id="tr_'+wid+'"><td><div class="row">'+
				'<div class="col-md-4 col-sm-4">'+chk+'&nbsp;&nbsp;'+valueobj.tdate+'</div>'+
				//'<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'">'+valueobj.wid+'</a></td>'+
				
				'<div class="col-md-3 col-sm-3 sm-font"><lable class="waitliststatus" data-waitliststatus="name_'+wid+'" style="cursor: pointer;">'+
				'<span id="name_'+wid+'_lbl">'+valueobj.name+
				'<span style="cursor: pointer;  margin-top: 3px;" id="name_'+wid+'_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></span></lable></div>';
				//'<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'">'+valueobj.email+'</a></td>';
				
		/*if ($( "#waitlistdates" ).length)
			html+='<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'">'+valueobj.eventdate+'</a></td>';*/
				
			//'<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'">'+valueobj.tickets+'</a></td>'+
				//'<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'">'+valueobj.phone+'</a></td>'+
				//'<div class="col-md-3 sm-font"><a href="'+valueobj.activationlink+'" target="_blank"><span id="actlink_'+wid+'">'+valueobj.activationlink+'</span></a></div>'+
				//'<div class="col-md-2 sm-font"><span>'+wid+'</span></div>'+
				var link='';
				//if(valueobj.status!='Waiting'){
					link+='<lable class="waitliststatus" data-waitliststatus="actlink_'+wid+'" style="cursor: pointer;">'+
					'<span id="actlink_'+wid+'_lbl">Link</span>'+
					'<span style="cursor: pointer; margin-top: 3px;" id="actlink_'+wid+'_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></lable>';
				/*}else{
					link+='<span id="actlink_'+wid+'_lbl">Link<span style="margin-top: 3px;cursor: text;" class="glyphicon glyphicon-menu-right original arrow-gap"></span></span>';
				}*/
				var statusLabel=valueobj.status;
				if(statusLabel=='Waiting') statusLabel=props.wl_waiting_status_lbl;
				else if(statusLabel=='In Process') statusLabel=props.wl_in_process_status_lbl;
				else if(statusLabel=='Completed') statusLabel=props.wl_completed_status_lbl;
				else if(statusLabel=='Expired') statusLabel=props.wl_expired_status_lbl;
			html+='<div class="col-md-2 col-sm-2 sm-font">'+link+'</div>'+
				'<div class="col-md-2 col-sm-2 sm-font"><span class="wl-status" id="status_'+wid+'" data-status="'+valueobj.status+'">'+statusLabel+'</span></div>'+
				'<div class="col-md-1 col-sm-1 sm-font links-div"><span class="pull-right">'+
				'<a href="javascript:;" class="hidden waitListDetls" data-wid="'+wid+'">'+props.global_edit_lnk_lbl+'</a><a href="javascript:;" class="hidden deleteWaitList" data-wid="'+wid+'">'+props.global_delete_lnk_lbl+'</a></span></div></div>'+
				'<div style="clear:both"></div>'+
				
				'<div class="background-options sm-font form-horizontal" id="name_'+wid+'" style="display: none;">'+
				'<div class="row "><div class="col-md-3 col-sm-3 control-label">'+props.wl_waitlist_id_lbl+':</div><div class="col-md-6 col-sm-6">'+wid+'</div></div>'+
				'<br/><div class="row"><div class="col-md-3 col-sm-3 control-label">'+props.global_email_lbl+':</div><div class="col-md-6 col-sm-6">'+valueobj.email+'</div></div>'+
				'<br/><div class="row"><div class="col-md-3 col-sm-3 control-label">'+props.global_tickets_lbl+':</div><div class="col-md-6 col-sm-6">'+valueobj.tickets+'</div></div>';
			
			if(valueobj.expirationtime!='')
				html+='<br/><div class="row"><div class="col-md-3 col-sm-3 control-label">'+props.wl_exp_date_time_lbl+':</div><div class="col-md-6 col-sm-6"><span id="expdt_'+wid+'">'+valueobj.expirationtime+'</span></div></div>';
			
			html+='</div>';
			
			var activationLink=valueobj.activationlink;
			if(activationLink=='') activationLink=props.wl_no_activation_link_lbl;
			
			html+='<div class="background-options activation-link sm-font" id="actlink_'+wid+'" style="display: none;">'+
				'<span><div class="row"><div class="col-md-10 col-sm-10"><span id="update_actlink_'+wid+'">'+activationLink+'</span></div>'+
				'<div class="col-md-2 col-sm-2"><span class="tdcopylink" style="display:none;">'+
				'<a id="link_'+wid+'" class="copylink" data-link="'+activationLink+'" style="cursor:pointer;">'+props.global_copy_link+'</a></span></div></div><div style="height:5px;"></div></span></div>'+
				'</td></tr>';
				//'<td><a href="javascript:;" class="waitListDetls '+wid+'" data-wid="'+wid+'" data-exp-sort="'+valueobj.userExpirationTime+'"><span id="expdt_'+wid+'">'+valueobj.expirationtime+'</span></a></td></tr>';
				
        $('#wait_list_table .nodata').remove();
		$('#wait_list_table').append($(html));
	});
	
	if(waitlistChkAllDisable){
		$('input[name="waitlist_id_all"]').iCheck(('disable'));
		$("#waitlistchkall").attr('disabled','disabled');
	}
	else {
		$('input[name="waitlist_id_all"]').iCheck(('enable'));
		$("#waitlistchkall").removeAttr('disabled');
	}
	
	$('input[name="waitlist_id_name"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	if(waitlistsummarydata.length>5)
		datatable=$('#wait_list_table').dataTable( {
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "bLengthChange": false,
            //"aoColumns": [{ "bSortable": false },{"sType": "requested-on"},null,null,null,null,null,null,null,{"sType": "expiration-time"}],  
            "bSort" : false,
    	    bFilter:false,
    	    "aoColumns": [{ "bSortable": true}] 
        } );
	else datatable=undefined;
	
	$("#wait_list_table thead").remove();
	if(waitlistsummarydata.length<=5)
        removePagination('wait_list_table');
	
}

function triggerCopy(){
	$("[id^='link']").zclip({
		path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
        copy:function(){ return $(this).data('link');}
	});	
   $("[id^='link']").trigger('mouseenter');
}


function getWaitlistTicketData(status){
	$("#sel_clear_all").hide();
	//console.log('getWaitlistTicketData');
	if(status==undefined)
		status=$('input[name=status]:checked').val();
	$("#mulActLink").removeClass('visible').addClass('hidden');
	if($("#wl_details_div").is(":visible"))
		$('.closeWaitlistDetails').trigger("click");
	if(waitlistdata.WaitListSummary==undefined){
		$("#wait_list_table").empty();
		var html='<tr><td><div class="not-found">'+props.wl_no_data_display_msg+'</div></td></tr>';
		$('#wait_list_table').append($(html));
		if(datatable !=undefined)
			$('#wait_list_table').dataTable().fnDestroy(false);
		return false;
	}
	var ticketid=document.getElementById('waitlistticket').value;
	var jsObjects=[];
	//var all=0;inp=0,comp=0,exp=0,wait=0;
	if(ticketid==0){
		jsObjects = waitlistdata.WaitListSummary;
	}else{
		jsObjects = waitlistdata[ticketid];
	}
	
	if(status!=undefined && status!='All' && jsObjects!=undefined){
		jsObjects=jsObjects.filter(function( obj ) {
			  return obj.status == status;
		});
	}
	/*if(document.getElementById('waitlistdates')){ // for recurring dates
		var eventdate=document.getElementById('waitlistdates').value;
		if(jsObjects!=undefined && eventdate!=0){
			jsObjects=jsObjects.filter(function( obj ){
				  return obj.eventdate == eventdate;
			});
		}
	}*/
	
	/*if(jsObjects!=undefined && (status==undefined || status=='All')){
		for(var j=0; j<jsObjects.length; j++){
			if(jsObjects[j].status=='In Process') inp+=1;
			if(jsObjects[j].status=='Completed') comp+=1;
			if(jsObjects[j].status=='Waiting') wait+=1;
			if(jsObjects[j].status=='Expired') exp+=1;
	    }
		all=jsObjects.length; 
		document.getElementById('st_all').innerHTML=all;
		document.getElementById('st_comp').innerHTML=comp;
		document.getElementById('st_inp').innerHTML=inp;
		document.getElementById('st_wat').innerHTML=wait;
		document.getElementById('st_exp').innerHTML=exp;
	}*/
	if(datatable !=undefined && jsObjects.length>0)
		$('#wait_list_table').dataTable().fnDestroy(false);
	instantiateWaitListTable(jsObjects, status);
}

function checkAll(ischecked) {
    var checkboxes = document.getElementsByName('waitlist_id_name');
    if (ischecked) {
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type == 'checkbox') {
            	if(checkboxes[i].disabled==false){
	                $(checkboxes[i]).iCheck('check');
	                if(!isInArray(checkboxes[i].value,selectedWidList))
	        			selectedWidList.push(checkboxes[i].value);
            	}
            }
        }
        if(selectedWidList.length>0){
        	//$("#mulActLink").attr("disabled",false);
        	$("#mulActLink").removeClass('hidden').addClass('visible');
        	//$("#mulActLink").show();
        }
    } else {
    	selectedWidList=new Array();
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type == 'checkbox') {
            	$(checkboxes[i]).iCheck('uncheck');
            }
        }
        //$("#mulActLink").attr('disabled', true);
        $("#mulActLink").removeClass('visible').addClass('hidden');
    	//$("#mulActLink").hide();
    }
}

function callMethod(value,checked){
	if(checked){
			if(!isInArray(value,selectedWidList))
				selectedWidList.push(value);
			
		}else{
			for (var i = 0; i < selectedWidList.length; i++) {
		        if (selectedWidList[i] === value) {
		        	selectedWidList.splice(i, 1);
		            i--;
		        }
		    }
		}
	if(selectedWidList.length>0){
		//$("#mulActLink").attr("disabled",false);
		$("#mulActLink").removeClass('hidden').addClass('visible');
	}else{
		//$("#mulActLink").attr('disabled', true);
		$("#mulActLink").removeClass('visible').addClass('hidden');
	}
}

var n=0;
function sendMultipleActivationLink(){
	loadingPopup();
	if(selectedWidList.length==0) {
		bootbox.alert(props.wl_pls_chk_wl_to_send_actlnk_msg);
		return;
	}
	activationLinkType='Multiple';
	var url='WaitList!checkMultipleSoldStatus?eid='+eid+'&widAry='+selectedWidList;
	$.ajax({
		url : url,
		type : "post",
		success : function(t){
			if(!isValidActionMessage(t)) return;
			hidePopup();
			if(t.indexOf("multitktmap") > -1){
				openHTMLPopUp(props.wl_send_actlnk_popup_header,t,'updateTicketQty',true);
			}else{
				if(activationLinkType=='Multiple'){
					openHTMLPopUp(props.wl_send_actlnk_popup_header,t,'sendMultipleActivation',true);
				}
				else
					sendActivation();
			}
		}
	});
}

var updateTicketQty = function(){
	var url='WaitList!updateTicketQty';
	var activationtype=$("#activationtypeid").val();
	if(activationtype=='Multiple'){
		var multiTktMap=$("#multitktmap").val();
		url='WaitList!updateTicketQty?multiTktMap='+multiTktMap;
	}
	var expdatetime=$("#expdatetime").val();
	var datetimeobj = expdatetime.split(" ");
	var dateobj=datetimeobj[0].split("/");
	var hrmin=datetimeobj[1].split(":");
	$("#exp_month_field").val(dateobj[0]);
	$("#exp_day_field").val(dateobj[1]);
	$("#exp_year_field").val(dateobj[2]);
	$("#exp_hour_field").val(hrmin[0]);
	$("#exp_min_field").val(hrmin[1]);
	$("#exp_ampm_field").val(datetimeobj[2]);
	$.ajax({
     	type: "POST",
         url:url,
         data: $("#waitlistsoldstatus").serialize(), 
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 if(result.indexOf("success")>-1){
	        	 $('#myModalhtmlin').hide();
	        	 if(activationLinkType=='Multiple'){
	        		 //window.location.href='WaitList?eid='+eid;
	        		 $.ajax({
		        	     	type: "POST",
		        	         url:'WaitList!waitListSummary?eid='+eid,
		        	         success:function(response){
		        	        	 if(!isValidActionMessage(response)) return;
		        	        	 waitlistdata=JSON.parse(response);
		        	        	 var status=$('input[name=status]:checked').val();
		        	        	 getWaitlistTicketData(status);
		        	         }
		        	     });
	        	 }else sendActivation();
        	 }else{
	        	if(activationLinkType=='Multiple' && result.indexOf("error")>-1){
	        		$('#waitlistsoldstatuserrormessages').show().html(result);
					n=0;
	        	}
	        }
         }
     });
};

function sendMultipleActivation(){
	var url='WaitList!sendMultipleActivation';
	var expdatetime=$("#expdatetime").val();
	var datetimeobj = expdatetime.split(" ");
	var dateobj=datetimeobj[0].split("/");
	var hrmin=datetimeobj[1].split(":");
	$("#exp_month_field").val(dateobj[0]);
	$("#exp_day_field").val(dateobj[1]);
	$("#exp_year_field").val(dateobj[2]);
	$("#exp_hour_field").val(hrmin[0]);
	$("#exp_min_field").val(hrmin[1]);
	$("#exp_ampm_field").val(datetimeobj[2]);
	$.ajax({
     	type: "POST",
         url:url,
         data: $("#waitlistsoldstatus").serialize(), 
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 if(result.indexOf("success")>-1){
	        	 $('#myModalhtmlin').hide();
	        	 //window.location.href='WaitList?eid='+eid;
	        	 $.ajax({
	        	     	type: "POST",
	        	         url:'WaitList!waitListSummary?eid='+eid,
	        	         success:function(response){
	        	        	 if(!isValidActionMessage(response)) return;
	        	        	 //console.log("sendmultiple: "+JSON.stringify(JSON.parse(response),undefined,2));
	        	        	 waitlistdata=JSON.parse(response);
	        	        	 var status=$('input[name=status]:checked').val();
	        	        	 getWaitlistTicketData(status);
	        	        	 notification(props.wl_activation_link_sent_status_msg,"success");
	        	         }
	        	     });
        	 }else{
	        	if(activationLinkType=='Multiple' && result.indexOf("error")>-1){
	        		if(result.indexOf("expiration")>-1) result=props.wl_select_valid_exp_date_errmsg;
	        		$('#waitlistsoldstatuserrormessages').show().html(result);
					n=0;
	        	}
	        }
         }
     });
}

function sendActivationLink(wid,ticketQty,ticketid){
	loadingPopup();
	$("#waitlisterrors").hide();
	++n;
	if(n==1){
		validateExpiration(wid,ticketQty,ticketid);
	}
}

function validateExpiration(wid,ticketQty,ticketid){
	var expdatetime=$("#detls_expdatetime").val();
	var datetimeobj = expdatetime.split(" ");
	var dateobj=datetimeobj[0].split("/");
	var hrmin=datetimeobj[1].split(":");
	$("#detls_exp_month_field").val(dateobj[0]);
	$("#detls_exp_day_field").val(dateobj[1]);
	$("#detls_exp_year_field").val(dateobj[2]);
	$("#detls_exp_hour_field").val(hrmin[0]);
	$("#detls_exp_min_field").val(hrmin[1]);
	$("#detls_exp_ampm_field").val(datetimeobj[2]);
	var url ='WaitList!validateExpiration';
	$.ajax({
     	type: "POST",
         url:url,
         data: $("#waitListDetails").serialize(), 
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 if(result.indexOf("expiration")>-1){
        		 hidePopup();
        		 result=props.wl_select_valid_exp_date_errmsg;
        		 $('#waitlisterrors').show().html(result);
        		 //$('html, body').animate({scrollTop:$('#waitlisterrors').position().top}, 'slow');
 			    n=0;
        	 }else{
				var datejson=eval('('+result+')');
 		        $('#expDateId').val(datejson.expDate);
 		        $('#expTimeId').val(datejson.expTime);
 				url='WaitList!checkSoldQtyAndUpdate?ticketid='+ticketid+'&ticketQty='+ticketQty;
        		 $.ajax({
        		     	type: "POST",
        		         url:url,
        		         data: $("#waitListDetails").serialize(), 
        		         success:function(result){
        		        	 if(!isValidActionMessage(result)) return;
        		        	 if(result.indexOf("success")>-1){
     				        	sendActivation();
     				        }else{
     				        	
     				        }
        		         }
        		     });
	        }
         }
     });
}

function sendActivation(){
	var url ='WaitList!sendActivationLink';
	var waitListId=$("#waitListDetails #waitListID").val();
	var expdt=$("#waitListDetails #detls_expdatetime").val();
	var expdatary=expdt.split(" ");
	var expdate=expdatary[0].split("/");
	$.ajax({
     	type: "POST",
         url:url,
         data: $("#waitListDetails").serialize(), 
         success:function(result){
        	 hidePopup();
        	 if(!isValidActionMessage(result)) return;
        	 if(result.indexOf("success")>-1){
        		$('#statusid').html("In Process");
        		$('#status_'+waitListId).html("In Process");
        		var expdt=getDateFormat(expdate[2]+"/"+expdate[0]+"/"+expdate[1])+" "+expdatary[1]+" "+expdatary[2];
        		var userexpdt=expdate[2]+"-"+expdate[0]+"-"+expdate[1]+" "+expdatary[1]+" "+expdatary[2];
        		if($("#expdt_" +waitListId).length > 0)
        			$('#expdt_'+waitListId).html(expdt);
        		else{
	        		var html='<br/><div class="row"><div class="col-md-3 col-sm-3 control-label">'+props.wl_exp_date_time_lbl+':</div><div class="col-md-6 col-sm-6"><span id="expdt_'+waitListId+'">'+expdt+'</span></div></div>';
	        		$("#name_"+waitListId).append(html);
        		}
        		$('#update_actlink_'+waitListId).html(serveraddress+"/event?eid="+eid+"&wid="+waitListId);
        		$('#link_'+waitListId).attr("data-link",serveraddress+"/event?eid="+eid+"&wid="+waitListId);
        		notification(props.wl_activation_link_sent_status_msg,"success");
        		//$('#activationLinkStatusMsg').show().html(result);
        		//$('html, body').animate({scrollTop:$('#activationLinkStatusMsg').position().top}, 'slow');
 			   	$("#activationlinkbtn").attr('disabled', true);
 			    waitlist_home_reload=true;
 			    n=0;
 			   updateWaitlistData(waitListId,expdt,userexpdt);
        	 }else{
				$('#waitlisterrors').show().html(result);
 			    n=0;
	        }
         }
     });
}

function updateWaitlistData(waitListId,expdt,userexpdt){
	$.each(waitlistdata.WaitListSummary,function(outkey,valueobj){
		if(valueobj.wid==waitListId){
			valueobj.status="In Process";
			valueobj.expirationtime=expdt;
			valueobj.userExpirationTime=userexpdt;
		}
	});
}

/*function isInArray(value, array) {
	  return array.indexOf(value) > -1;
}*/

function deleteWLTransaction(eid,wid){
	bootbox.confirm(props.global_sure_delete_msg,function(result){
		if (result) {
			var url='WaitList!deleteWLTransaction?eid='+eid+"&wid="+wid;
			$.ajax({
		     	type: "POST",
		         url:url,
		         success:function(result){
		        	 if(!isValidActionMessage(result)) return;
		        	 if(result.indexOf("success")>-1){
		        		 //window.location.href='WaitList?eid='+eid;
		        		 $('#myModalhtmlin').hide();
		        		 $.ajax({
			        	     	type: "POST",
			        	         url:'WaitList!waitListSummary?eid='+eid,
			        	         success:function(response){
			        	        	 if(!isValidActionMessage(response)) return;
			        	        	 //console.log("delete: "+JSON.stringify(JSON.parse(response),undefined,2));
			        	        	 waitlistdata=JSON.parse(response);
			        	        	 //console.log("delete: "+JSON.stringify(waitlistdata,undefined,2));
			        	        	 loadTicketTypes(waitlistdata.ticketTypes);
			        	        	 var status=$('input[name=status]:checked').val();
			        	        	 getWaitlistTicketData(status);
			        	        	 
			        	         }
			        	     });
		        	 }else{
						
			        }
		         }
		     });
		}
	});
}

function customizeEmailTemplate(templatejsondata){
	if(templatejsondata.tomanager=='yes')
		$("#evtMgr").iCheck('check');
	$("#bcc").val(templatejsondata.bcc);
	$("#subject").val(templatejsondata.subject);
	$("#previewcontent").val(templatejsondata.content);
}

function resetContent(){
	bootbox.confirm(props.wl_by_resetting_customization_will_lost_msg,function(result){
		if(result){
			var url='WaitList!resetWaitListEmailSettings';
			 $.ajax({
			     	type: "POST",
			         url:url,
			         data: $("#waitlistemailform").serialize(), 
			         success:function(result){
			        	 if(!isValidActionMessage(result)) return;
			        	 customizeEmailTemplate(eval ("(" + result + ")"));
			        	 var msg='<div id="statusMessageBox" class="statusMessageBox"> <div style="float: left;valign: middle;"><img src="../images/check.png"/>'+
			        		 '&nbsp;'+props.global_update_success_msg+'</div><div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>';
			        	 $('#message').show().html(msg);
			         }
			 });
		}
	});
}

function getTemplate(purpose){
	$("#contentdiv").hide();
	loadSpinnerImg('contentdiv');
	//$("#purpose").val(purpose);
	var url='WaitList!emailTemplate?eid='+eid+'&purpose='+purpose;
	$.ajax({
     	type: "POST",
         url:url,
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 customizeEmailTemplate(eval ("(" + result + ")"));
        	 hideSpinnerImg();
        	 $("#contentdiv").show();
         }
     });
}

function submitfm(){
	var url='WaitList!save';
	 $.ajax({
	     	type: "POST",
	         url:url,
	         data: $("#waitlistemailform").serialize(), 
	         success:function(result){
	        	 if(!isValidActionMessage(result)) return;
	        	 if(result.indexOf("success")>-1){
	        		 $('#message').show().html(result);
	        		 $('html, body').animate({scrollTop:$('#message').offset().top- 300}, 'slow');
		        }else{
		        	
		        }
	         }
	   });
}

function getContentPreview(){
	/*var previewContent=$("#previewcontent").val();
	previewContent=previewContent.replace('$eventName',eventname);
	previewContent=previewContent.replace('$buyerName', 'xxx');
	var mgrname=templateJsonData.mgrName;
	if(mgrname!=''){ 
		previewContent=previewContent.replace('$mgrFirstName', mgrname);
		previewContent=previewContent.replace('$mgrLastName', '');
	}
	openHTMLPopUp('Preview',previewContent,'',false);*/
	
	var url='WaitList!preview';
	 $.ajax({
    	 type: "POST",
 		   url: url,
 		   data: $("#waitlistemailform").serialize(),	    	
 		   success: function( result ) {
 			  if(!isValidActionMessage(result)) return;
 			  $('#myModal').on('show.bs.modal', function () {
 		        	$('#myModal .modal-title').html('<span style="color: rgb(176, 176, 176); font-size: 22px;">Preview</span>');	 		        		           	
 		        	$('#myModal .modal-body').html(result);		      	
 			  });	 		     
 		     $('#myModal').modal('show');        
 			    $('#myModal').modal({
 			        backdrop: 'static',
 			        keyboard: false,
 			        show:false
 			    }); 			    
 		
 		   }  	
    	
    });
}

function doubleScroll(element) {
	removeDoubleScroll();
	var scrollbar= document.createElement('div');
    scrollbar.setAttribute('id', 'topscroll');
    var child = document.createElement('div');
    child.setAttribute('id', 'topscrollchild');
    scrollbar.appendChild(child);
    scrollbar.style.overflow= 'auto';
    scrollbar.style.overflowY= 'hidden';
    scrollbar.style.width= '768px';
    scrollbar.firstChild.style.width= element.scrollWidth+'px';
    scrollbar.firstChild.style.height= '0px';
    scrollbar.firstChild.style.paddingTop= '1px';
    scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
    scrollbar.onscroll= function() {
    	element.scrollLeft= scrollbar.scrollLeft;
    };
    element.onscroll= function() {
    	scrollbar.scrollLeft= element.scrollLeft;
    };
    element.parentNode.insertBefore(scrollbar, element);
}

function removeDoubleScroll(){
	if(document.getElementById('topscroll')){
		var parent=document.getElementById(document.getElementById('topscroll').parentNode.getAttribute('id'));
		var child=document.getElementById("topscroll");
		parent.removeChild(child);
	}
}
//getWaitlistTicketData(undefined);
function loadTicketTypes(ticketTypes){
	//console.log('loadTicketTypes');
	$('#ticketTypes').empty();
	var ticketNm=props.global_all_tickets_lbl;
	var selectedTkt=$("#waitlistticket").val();
	var isSelectedTkt=false;
	$('#ticketTypes').append('<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:;" onclick="filterByTicketTypes(0,\''+ticketNm+'\');">'+props.global_all_tickets_lbl+'</a></li>');
	if(ticketTypes!=undefined)
	$.each(ticketTypes, function(key,value){
		ticketNm=value.ticketNm;
		if(ticketNm.length>20)
			ticketNm=ticketNm.substring(0,20-3)+'...';
		var html='<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:;" onclick="filterByTicketTypes('+value.ticketId+',\''+ticketNm+'\');">'+ticketNm+'</a></li>';
		$('#ticketTypes').append(html);
		if(selectedTkt==value.ticketId)
			isSelectedTkt=true;
	});
	if(!isSelectedTkt){
		$("#waitlistticket").val(0);
		$('#ticketNm').html(props.global_all_tickets_lbl);
	}
	//console.log('loadTicketTypes end');
}

function filterByTicketTypes(ticketId,ticketNm){
	//console.log(ticketId+":"+ticketNm);
	$("#waitlistticket").val(ticketId);
	$('#ticketNm').html(ticketNm);
	$("#ticketTypes").hide();
	getWaitlistTicketData(undefined);
}

function allTicketsClick(){
	  if($("#ticketTypes").is(':hidden'))
	 	 $("#ticketTypes").show();
	  else
		  $("#ticketTypes").hide();
}

function showProcessing(divid){
	var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$('#'+divid).after(html);
}

function hideProcessing(){
	$('#loading').remove();
}
