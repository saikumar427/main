$(document).ready(function(){
	$('#submgrid').click(function(){
		$('html, body').animate({ scrollTop: $("#forload").offset().top-scrollTo}, 1000);
		showProcessing('forload');
		arrangeSubBoxes();
		createSubManager(eid);
		
	});
	/*$('#subMgrBox').on('click','#permissionsSettings',function(){
        $('#permissionDetails').slideToggle(slideTime);
    });*/
	/*$(document).on('click','.canceladdSubMgr',function(){
		$('#submgrid').prop("disabled",false);
		$('html, body').animate({ scrollTop: $("#submgrid").height()}, 1000);
	    $('#mainSubMgrBox').slideUp();
	    $('#mainSubMgrBox').html('');
		$('#subMgrBox').parent().remove();
		
	    
	});*/
	/*$('#mainSubMgrBox').on('click','.confirmaddSubMgr',function(){
		 //trimSubMgrInputValues();
		// submitSubManagerForm();
		 alert('ok');
	});*/
		
	
});
function showLinks(sID){
	$("#displayManage_"+sID+" a").each(function(){
		 $(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
	        $(this).prop("disabled",false);
	});
	
}
function arrangeSubBoxes(){
	$('#subMgrBox').html('');
	$('#subMgrBox').parent().remove();
}
function createSubManager(eid){
	var powertype = document.getElementById('powertype').value;
	var url = 'SubManager!getSubManager?eid='+eid+'&powertype='+powertype;
	/*$('#mainSubMgrBox').load(url,function(result){
		if(!isValidActionMessage(result)) return;
		$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
		$('mainSubMgrBox').fadeIn(slideTime);
		$('input[name="subManagerData.name"]').focus();
		 hideProcessing();		 
	});*/
	
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			//$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
			$('#mainSubMgrBox').html(result);
			$('#mainSubMgrBox').fadeIn(slideTime);
			$('input[name="subManagerData.name"]').focus();
			 hideProcessing();
		},
		error:function(){
			alert('error');
		}
	});
	
}

var globalEditMgr='';
function editSubManager(eid,subMgrId){ 
	$('#displayManage_'+globalEditMgr+' a').each(function(){
		 $(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
	        $(this).prop("disabled",false);
	});
	globalEditMgr = subMgrId;
	$('#mainSubMgrBox').hide();
	$('.editRow').remove();
	$("#displayManage_"+subMgrId+" a").each(function(){
		  $(this).css({"pointer-events": "none","cursor": "default","color":"#a5c7ed"});
	        $(this).prop('disabled',true);
	});
	    var htmlProcessing='<tr id="loading"><td ><center><img src="../images/ajax-loader.gif"></center></div>';
	    $("#"+subMgrId+"_row").after(htmlProcessing);
	
	var powertype=document.getElementById('powertype').value;
	var url='SubManager!getSubManager?eid='+eid+'&subMgrId='+subMgrId+'&powertype='+powertype;
	$('<tr class="editRow"><td style="border-top:none;" id="subMgrBox"></td></tr>').insertAfter($('#'+subMgrId+'_row'));
	$('html, body').animate({ scrollTop: $(".editRow").offset().top-scrollTo}, 1000);
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#subMgrBox').html(result);
			$('#changeBtnName').val(props.global_save_btn);
		}
	});
	/*$('#subMgrBox').load(url,function(){
		$('#changeBtnName').val('Save');
	});*/
	
	hideProcessing();
}
function removeSubManager(eid,subMgrId,id){
	
		 var  url='SubManager!subManagerRemoveScreen?eid='+eid+'&subMgrId='+subMgrId;
		 var remurl = 'SubManager!removeSubManager?eid='+eid+'&subMgrId='+subMgrId;
		  var dynatimestamp = new Date();
		  url += "&dynatimestamp="+dynatimestamp.getTime();		
			 bootbox.confirm('<h3>'+props.sm_confrim_delete_lbl+'</h3>'+props.sm_submgr_delete_confirm_msg, function (result) {
			     if (result){	    	
		           	$.ajax({
		           		type: "POST",
		                url:remurl,
		                success:function(result){	
		                	if(!isValidActionMessage(result)) return;
		                	//rebuildData();
		                	/*parent.hidePopup();*/	
		                	$('#submanagerdata').dataTable().fnDestroy(false);
		                	$('#'+subMgrId+'_row').remove();
		                	$('#subMgrBox').parent().remove();
		                	var count =$("#submanagerdata > tbody > tr").length;
		    				callDataTableReload();
		    			      if(count<=5)
		    			    	  removePagination('submanagerdata');
		    			    	  /*$('#submnagerdata_paginate').hide();*/
		    			      $('#notification-holder').html('');
							$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
							notification(props.sm_submgr_delete_status_msg,"success");
							
							var trCount = $('#subBody tr').length;
							if(trCount == '1' && $('#subBody tr td').hasClass('dataTables_empty')){
								//$('.table-responsive').html('');
								$('#submgrid').trigger('click');
							}
								
		                }
		           	});
			     	}
			     });
			 
             }
function manageSubManager(eid,subMgrId,status,jsub){	
	
	var url='SubManager!manageSubManager?eid='+eid+'&subMgrId='+subMgrId+'&status='+status;	
	showProcessing('reactiveload');
	$.ajax({
		url : url,
		type : 'POST',
		success : function(response){	
			if(!isValidActionMessage(response)) return;
			var links='',statusdata = '';
			links +='<span class="hideThis" style="display:none;">';
			if(status=='Active'){
				statusdata=props.sum_status_active_lbl;
			    links +='<a href="javascript:;" onclick=manageSubManager(\''+eid+'\',\''+subMgrId+'\',"Inactive",\''+jsub+'\');>'+props.sm_deactivate_lnk_lbl+'</a>';
			    $('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.sm_submgr_actived_msg,"success");
			}else{
				statusdata=props.sm_inactive_lbl;
		    	links +='<a href="javascript:;" onclick=manageSubManager(\''+eid+'\',\''+subMgrId+'\',"Active",\''+jsub+'\');>'+props.sm_reactivate_lnk_lbl+'</a>'; 
		    	$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.sm_submgr_deactivated_msg,"success");
			}
				links +='&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick=removeSubManager(\''+eid+'\',\''+subMgrId+'\',\''+jsub+'\');>'+props.global_delete_lnk_lbl+'</a>';
		    	links +='&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick=editSubManager(\''+eid+'\',\''+subMgrId+'\');  >' +props.global_edit_lnk_lbl+ '</a>';
		    	links +='</span>';
		    	hideProcessing();
		    	$('#status_'+subMgrId).html(statusdata);
				$('#manage_'+subMgrId).html(links);	
		}
	});
}


function trimSubMgrInputValues(){
	$('#name').val($.trim($('#name').val()));
	$('#email').val($.trim($('#email').val()));
}


function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}
