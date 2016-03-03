var actionType='';
var rsvpresponsedata='';
var rsvpcheckindata='';
var buyerProfileId='';
var attendeeProfileId='';
$(document).ready(function() {
	
	 $('#search_filter').tooltipster({
		 trigger: 'custom',
	        onlyOne: false,
	        fixedWidth: '300px',
	        position: 'bottom',
	        theme: 'form-validate-theme',
	        animation: 'grow'
	    });
	 
	 $("#search_filter").focusout(function(){
			$('#search_filter').tooltipster('hide');
		});
	 
	$.extend( $.fn.dataTableExt.oSort, {
	    "transaction-date-pre": function ( a ) {
	        return $(a).attr('data-calval');
	    },
	    "transaction-date-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	    "transaction-date-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	$(document).on('click','.chkunchkbtn',function(){
		var tid=$(this).attr('data-tid');
		var pkey=$(this).attr('data-pkey');
		var chkstatus=$(this).attr('data-chkstatus');
		var index=$(this).attr('data-index');
		getCheckInStatus(tid,pkey,chkstatus,index);
	});
	
	$(document).on('ifChecked','.srctype',function(){
		filterRSVPDataTable('rsvpresponsecontentreports');
	});
	
	$(document).on('ifUnchecked','.srctype',function(){
		filterRSVPDataTable('rsvpresponsecontentreports');
	});
	
	$('#rsvpresponsesubmitbtn').click(function(){
		$('#rsvprepsearchid').val(false);
		actionType='response';
		formaction('response');
	});

	$('#rsvpcheckinsubmitbtn').click(function(){
		$('#rsvprepsearchid').val(false);
		actionType='checkedin';
		formaction('checkedin');
	});
	
	$('.rsvpresselect').click(function(){
		 $('input[name="resFields"]').iCheck('check');
		
	});
	
	$('.rsvpresclear').click(function(){
		 $('input[name="resFields"]').iCheck('uncheck');
		
	});
	
	$('.rsvpcheckselect').click(function(){
		 $('input[name="checkinFields"]').iCheck('check');
	});
	
	$('.rsvpcheckclear').click(function(){
		$('input[name="checkinFields"]').iCheck('uncheck');
	});
	
	$(document).on('click','#rsvprecursubmit',function(){
		$('#rsvprecursubmit').attr('disabled','disabled');
	$.ajax({
		type:'POST',
		data:$('#transactiondateform').serialize(),
	    url:'TransactionDetails!changeDate',
		success:function(result){
			if(!isValidActionMessage(result)) return;
			if(result.indexOf('success')>-1){
				$('#rsvprecursubmit').removeAttr('disabled');
				if($('#source').val()!='')
				$('#rsvprecurring').html('For Event Date : '+$('#source').val());
				
				var exists = false;
				$('#recurringdate option').each(function(){
				    if (this.value == $('#source').val()) {
				        exists = true;
				        return false;
				    }
				});
				
				if((!exists) && $('#source').val()!='') 
					$('#recurringdate').append('<option value="'+$('#source').val()+'">'+$('#source').val()+'</option>');
				}
			}
	});
	});
	
	var divOpen = false;

	


	$(document).on('click', '.closeTransactionDetails', function() {
	    divOpen = false;
	    $(this).parents('tr').prev().removeClass('info');
	    $(this).parents('tr').remove();
	});
	
	$(document).on('click','#cancelregdetails',function(){
		$('#'+buyerProfileId).show();
		$('#edit_'+buyerProfileId).hide();
		$('#'+attendeeProfileId).show();
		$('#edit_'+attendeeProfileId).hide();
		$("div.loadedData").remove();	
	});
	
	$(document).on('click','#cancelnotes',function(){
		 $("div.loadedData").remove();
	});
	
});

function formaction(type){
	if(!checkDateSelect())
		return;

		removeHiddenEle();
		if(type=='response'){
			$('#rsvprestid').iCheck('check');

			if(document.getElementById("trackurls")){
			   if(!(document.getElementById("mainurl").checked) && !(document.getElementById("addmanualurl").checked) && !(document.getElementById("trackurls").checked))
				 $("#mainurl").iCheck('check');
			}else{
				if(!(document.getElementById("mainurl").checked) && !(document.getElementById("addmanualurl").checked))
					$("#mainurl").iCheck('check');
			}
		rsvpReportsFromSubmit('RSVPReports!getRSVPResponseInfo','rsvpresponsecontentreports');
		}else if(type=='checkedin'){
			$('#rsvpchktid').iCheck('check');
			$('#rsvpchkinid').iCheck('check');
			rsvpReportsFromSubmit('RSVPReports!getRSVPCheckedInReportsInfo','rsvpcheckinreport');
		}
	}


function rsvpReportsFromSubmit(action,contentblock){
	
	var loadhtml='<div id="loading" align="center" style="color:#747170;margin:81px 0 0 3px">'
				+'<table><tr><td align="center"><b>Processing...</b></td></tr>'+
		'<tr><td align="center"><img src="../images/loading.gif"></td></tr></table></div>';
		loadhtml='<center><img src="../images/ajax-loader.gif"></center>';

	 $('#'+contentblock).html(loadhtml);
	document.rsvpreportsform.action = action;
	$.ajax({
		  type:'get',
		  data:$('#rsvpreportsform').serialize(),
		  url:action,
		  success:function(result){
			  if(!isValidActionMessage(result)) return;
			  var data=eval('('+result+')');
			  if(data.rsvpResponseMap.length==0){
				  var nhtml='<div style="text-align:center"><b>'+props.global_none_found_msg2+'</b></div>';
				  $('#'+contentblock).html(nhtml);
				  return ;
			  }
			  if(contentblock=='rsvpresponsecontentreports') rsvpresponsedata = jQuery.extend(true, {}, data);
			  if(contentblock=='rsvpcheckinreport') rsvpcheckindata=jQuery.extend(true, {}, data);
			  getRsvpYui(data,contentblock);
		  }
	});
	}

function checkDateSelect(){
	if(document.getElementById('recurringdate')){
		var index=document.getElementById('recurringdate').value;
		if(index==1){
			alert(props.global_pls_select_event_date_msg);
			return false;
		}
	}
	return true;
}
function removeHiddenEle(){
	var a=document.getElementById("exportreport");
		if(a) document.rsvpreportsform.removeChild(a);
	var b=document.getElementById("sortdir");
		if(b) document.rsvpreportsform.removeChild(b);
	var c=document.getElementById("sortfield");
		if(c) document.rsvpreportsform.removeChild(c);
	var d=document.getElementById("exptyp");
		if(d) document.rsvpreportsform.removeChild(d);

}

var reptype="";
var resMaptemp=new Array();
var attrespsortfields = [];
var checkinsortfields = [];
function filterRSVPDataTable(contentblock){
	if(contentblock=='rsvpresponsecontentreports') {
		if($("#rsvpExportDisplay").is(':visible')){$("#rsvpExportDisplay").hide();}
		if(rsvpresponsedata=='') return;
		getRsvpYui(jQuery.extend(true, {}, rsvpresponsedata),contentblock);
	}if(contentblock=='rsvpcheckinreport'){ 
		if($("#chkinExportDisplay").is(':visible')){$("#exportDisplay").hide();}
		if(rsvpcheckindata=='') return;
		getRsvpYui(jQuery.extend(true, {}, rsvpcheckindata),contentblock);
	}
}

var filterResFields=["SUR","RST","BKS","AK"];
var attRepDispFields=["Transaction Date","Transaction ID","First Name","Last Name","Email","Response","Tracking URL","Notes","Transaction Source"];
var i18nMapping={"Transaction Date":props.rep_disflds_trans_date_lbl,"Transaction ID":props.trans_tid_lbl,"First Name":props.trans_first_name_lbl,
		"Last Name":props.trans_last_name_lbl,"Email":props.trans_email_lbl,"Response":props.spt_response_lbl,"Notes":props.rep_disflds_notes_lbl,
		"Tracking URL":props.rep_disflds_tracking_url_lbl,"Transaction Source":props.trans_transaction_source_lbl,
		"Event Date":props.rep_disflds_event_date_lbl,"Checked In":props.rep_filter_checkedin_lbl,"Check In Time":props.rep_disflds_checkin_time_lbl,
		"Scan ID":props.rep_disflds_scan_id_lbl,"Attendee Key":props.rep_disflds_attendee_key_lbl,"Attending":props.rsvp_attending_response,
		"Online":props.global_online_lbl,"Manager":props.rep_trans_source_lbl,"Not Sure":props.rsvp_not_sure_response,"Not Attending":props.rsvp_not_attending_response};

function getRsvpYui(jsondata,divid){
	//console.log(JSON.stringify(jsondata));
	var params = [];
	var jdata=jsondata;
   reptype=divid;
   var restemp=new Object();
   resMaptemp=new Array();
   var mapkeyfiled=jdata.mapkeyfiled;   
   var objn=new Object();
   for(var i=0;i<jdata.filedlen;i++){
	   var tempval=mapkeyfiled['q_'+i];
	   if(filterResFields.indexOf(tempval)<0)
		   resMaptemp.push(tempval);
       objn['q_'+i]=tempval;      
   } 
   
      
   var maptemp=new Array();
   for(var j=0;j<jdata.rsvpResponseMap.length;j++){
	   var intem=jdata.rsvpResponseMap[j];
            var obj=new Object();
            for(var k=0;k<jdata.filedlen;k++){
            	var tepval=intem['q_'+k];
                if(tepval==undefined)
                obj[(objn['q_'+k])]="";    
                else{
                	if(jdata.mapkeyfiled[tepval]!=undefined)
                   obj[(objn['q_'+k])]=jdata.mapkeyfiled[tepval];
                   else
                   obj[(objn['q_'+k])]=tepval;
                }    
            }
           maptemp.push(obj); 
  }
      jdata.rsvpResponseMap=maptemp;
      jdata.resfields=resMaptemp;
      //console.log("jsondata::"+JSON.stringify(jdata));
    var content='';
      $.each(jdata,function(outkey,resObj){
    	  if(outkey=="resfields" && jdata['rsvpResponseMap'].length>0){
    		  if(divid=='rsvpresponsecontentreports')
 				 $("#rsvpExportDisplay").show();
    		  else $("#rsvpChkExportDisplay").show();
    		  content+='<table class="table table-hover" id="results_'+divid+'" border="0" style="margin-bottom:0px !important;"> ';
 			 	  content+='<thead>';
 			 	 $.each( resObj, function( inkey, valueobj ) {	
						var mul = 10*valueobj.length;
						var headerLabel=valueobj;
						if (valueobj in i18nMapping) headerLabel=i18nMapping[valueobj];
						if(divid=='rsvpresponsecontentreports'){
							if(attRepDispFields.indexOf(valueobj)>-1)
								content+= '<th style="cursor: pointer;"><div style="width:'+mul+'px;">'+headerLabel+'</div></th>';
							else
								content+= '<th><div style="width:'+mul+'px;">'+headerLabel+'</div></th>';	
						}else
							content+= '<th style="cursor: pointer;"><div style="width:'+mul+'px;">'+headerLabel+'</div></th>';	
						 
 			 	 	if(divid=='rsvpresponsecontentreports'){
 			 	 		if(attRepDispFields.indexOf(valueobj)>-1){
 			 	 			if(valueobj=='Transaction Date')
 		 			 	 		params.push({"sType": "transaction-date"});
 			 	 			else params.push(null);
 			 	 			attrespsortfields.push(valueobj);
 			 	 		}else params.push({ "bSortable": false }); 
 			 	 	}else{
 			 	 		checkinsortfields.push(valueobj);
 			 	 		params.push(null);
 			 	 	}
				});
 			 	content+='<th></th></thead><tbody>';
 			 	params.push({ "bSortable": false });
    	  }
    	 	
      });	
      
      if(divid=='rsvpresponsecontentreports'){
    	  var res_type=$("#responseTypeId").val();
    	  var att_type=$("#attndTypeId").val();
			 if(res_type!=1){
				 jdata.rsvpResponseMap=jdata.rsvpResponseMap.filter(function( obj ){
					  return obj.RST == res_type;
				 });
				 if(res_type=='Yes' && att_type!=1){
					 jdata.rsvpResponseMap=jdata.rsvpResponseMap.filter(function( obj ){
						  return obj.SUR == att_type;
					 });
				 }
			 } 
			 
			 if(!$('#mainurl').is(':checked')){
				 jdata.rsvpResponseMap=jdata.rsvpResponseMap.filter(function( obj ){
 					  return obj.BKS != 'Online';
 				 });
			 }
			 
			 if(!$('#addmanualurl').is(':checked')){
				 jdata.rsvpResponseMap=jdata.rsvpResponseMap.filter(function( obj ){
 					  return obj.BKS != 'Manager';
 			});
			}
			 
      }
      
      if(divid=='rsvpcheckinreport'){
    	  var chk_status=$("#checkinstatusid").val();
			 if(chk_status!=1){
				 jdata.rsvpResponseMap=jdata.rsvpResponseMap.filter(function( obj ){
					  return obj['Checked In'] == chk_status;
				 });
								 
				 
			 } 
      }
      
      //console.log(JSON.stringify(jdata.rsvpResponseMap));
      $.each(jdata.rsvpResponseMap,function(outkey,resObj){
    	  var paymentstatus='Completed';
    	  content+='<tr id="reports_'+resObj["Transaction ID"]+'">';	
    	  $.each(jdata.resfields,function(i,inval){
    		  var tid=resObj["Transaction ID"];
    		  var pkey=resObj.AK;
    		  var btnlbl=props.rep_checkin_btn_lbl;
    		  var fieldDataLbl='';
    		  if(inval=='Transaction Date')
    			  content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+resObj[inval]+'>'+getDateFormat(resObj[inval])+'</a></td>';
    		  else if(inval=='Checked In'){
    			  if(resObj[inval]=='Yes') {
    				  btnlbl=props.rep_uncheck_btn_lbl;
    				  fieldDataLbl=props.gllobal_yes_lbl;
    			  }else if(resObj[inval]=='No') fieldDataLbl=props.global_no_lbl;
    			  else fieldDataLbl=resObj[inval];
    			  content+='<td><span id="cis_'+pkey+'">'+fieldDataLbl+'</span>&nbsp;<button id="cis_btn_'+pkey+'" data-tid="'+tid+'" data-pkey="'+pkey+'" data-index="'+outkey+'" class="btn btn-xs btn-primary chkunchkbtn" data-chkstatus="'+resObj[inval]+'"><span id="btn_lbl_'+pkey+'">'+btnlbl+'</span></button></td>';
    		  }else{
    			  fieldDataLbl=resObj[inval];
    			  if (fieldDataLbl in i18nMapping) fieldDataLbl=i18nMapping[resObj[inval]];
    			  content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+resObj[inval]+'>'+fieldDataLbl+'</a></td>';
    		  }
    		  if(jdata.resfields.length-1==i){
		  			//content+='<td><a class="clickk '+tid+'" data-tid="'+tid+'">Manage</a></td>';
    			  
    			  content+='<td><div style="position: relative;"><ul style="display:none; margin-left: -94px;" class="dropdown-menu" id="ul_'+tid+'">'+
					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="transactiondetails">'+props.rep_manage_trans_details_lbl+'</a></li>';
					//if(paymentstatus=='Completed' || paymentstatus=='Pending Approval')
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-type="changestatus" data-tid="'+tid+'">'+props.rep_manage_change_response_lbl+'</a></li>';
					
					if(paymentstatus!='Cancelled')
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="resendemail" data-tid="'+tid+'">'+props.rep_manage_resend_email_lbl+'</a></li>';
					
					
					if(paymentstatus!='Deleted')
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="buyerdetails" >'+props.rep_manage_registrant_details+'</a></li>';
					
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="attendeedetails">'+props.rep_manage_attendee_details_lbl+'</a></li>'+
					'</ul><a style="cursor:pointer" class="manage" data-manage="'+tid+'">'+props.rep_manage_btn_lbl+'</a></div></td>';
    			  
    			  
    		  }
    		  });
    	  content+='</tr>';
    	  
      });
      content+='</tbody></table>';
      if(divid=='rsvpresponsecontentreports'){
    	  if(jdata.rsvpResponseMap.length>0){
    		  $("#rsvp_res_filters").show();
    	  }else $("#rsvpExportDisplay").hide();
    	  $('#rsvpresponsecontentreports').html(content);
      }else{
    	  if(jdata.rsvpResponseMap.length>0){
    		  $("#rsvp_chek_filters").show();
    	  }else $("#rsvpChkExportDisplay").hide();
    	  $('#rsvpcheckinreport').html(content);
      }
      sortTable(divid,params,jdata.rsvpResponseMap.length);
}
var attrepdTable;
var chkrepdTable;
function sortTable(divid,params,reccount){	 
	var dTable=$('#results_'+divid).dataTableReports( {
			"sPaginationType": "full_numbers",
			"iDisplayLength":10,
		    "bLengthChange": false,
		    "bFilter": false,   
		    "scrollX": true,
		    "aoColumns": params
		});
	dTable.fnSort( [ [0,'desc'] ] );
	
	if(divid=='rsvpcheckinreport')
		chkrepdTable=dTable;
	else
		attrepdTable=dTable;
	
	if(reccount<=10)
        removePagination('results_'+divid);
}

function reportexport(exporttype){
	var dir="asc";
	var colno='0';//JSON.stringify((dTable.fnSettings().aaSorting[0])[0]);
	var dirtype='desc';//JSON.stringify((dTable.fnSettings().aaSorting[0])[1]);
	var sortfield='';//resMaptemp[colno];
	var formele=document.rsvpreportsform;
	if(reptype=='rsvpcheckinreport'){
		colno=JSON.stringify((chkrepdTable.fnSettings().aaSorting[0])[0]);
		dirtype=JSON.stringify((chkrepdTable.fnSettings().aaSorting[0])[1]);
		sortfield=checkinsortfields[parseInt(colno,10)];
		formele.action='RSVPReports!getRSVPCheckedInReportsInfo';
	}else{
		colno=JSON.stringify((attrepdTable.fnSettings().aaSorting[0])[0]);
		dirtype=JSON.stringify((attrepdTable.fnSettings().aaSorting[0])[1]);
		sortfield=attrespsortfields[parseInt(colno,10)];
		formele.action="RSVPReports!getRSVPResponseInfo";
	}	
	
	if(dirtype.indexOf('desc')>-1) dir="desc";
	removeHiddenEle();
	var input=document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","export");
	input.setAttribute("id","export_id");
	input.setAttribute("value",true);
	formele.appendChild(input);
	var input1=document.createElement("input");
	input1.setAttribute("type","hidden");
	input1.setAttribute("name","sortDirection");
	input1.setAttribute("id","sortdir");
	input1.setAttribute("value",dir);
	formele.appendChild(input1);
	var input2=document.createElement("input");
	input2.setAttribute("type","hidden");
	input2.setAttribute("name","sortField");
	input2.setAttribute("id","sortfield");
	input2.setAttribute("value",sortfield);
	formele.appendChild(input2);
	var input3=document.createElement("input");
	input3.setAttribute("type","hidden");
	input3.setAttribute("name","exporttype");
	input3.setAttribute("id","exptyp");
	input3.setAttribute("value",exporttype);
	formele.appendChild(input3);

	formele.submit();

}

function removeHiddenEle(){
	var a=document.getElementById("export_id");
		if(a) document.rsvpreportsform.removeChild(a);
	var b=document.getElementById("sortdir");
		if(b) document.rsvpreportsform.removeChild(b);
	var c=document.getElementById("sortfield");
		if(c) document.rsvpreportsform.removeChild(c);
	var d=document.getElementById("exptyp");
		if(d) document.rsvpreportsform.removeChild(d);
}

function getDateCount(){

	if(document.getElementById('recurringdate')){
		var index=document.getElementById('recurringdate').value;
			if(index=='all'){
				$("#totalsure").show();
				$("#eventdatetotalsure").hide();
				$("#totalnotsure").show();
				$("#eventdatetotalnotsure").hide();
				$("#totalchkin").show();
				$("#eventdatetotalchkin").hide();
			}else{
				
				if(recattndjson[index+"_sure"] != undefined){
					$("#eventdatetotalsure").html(recattndjson[index+"_sure"]);
				}else{
					$("#eventdatetotalsure").html("0");
				}
				
				$("#totalsure").hide();
				$("#eventdatetotalsure").show();
				
				if(recattndjson[index+"_notsure"] != undefined){
					$("#eventdatetotalnotsure").html(recattndjson[index+"_notsure"]);
				}else{
					$("#eventdatetotalnotsure").html("0");
				}
				$("#totalnotsure").hide();
				$("#eventdatetotalnotsure").show();
				
				if(recattndjson[index+"_chkcnt"] != undefined){
					$("#eventdatetotalchkin").html(recattndjson[index+"_chkcnt"]);
				}else{
					$("#eventdatetotalchkin").html("0");
				}
				$("#totalchkin").hide();
				$("#eventdatetotalchkin").show();
				
			}
			
		}
}

function validateEmail(email){
	 var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	 if (reg.test(email)){
	 return true; }
	 else{
	 return false;
	 }
	} 

function getCheckInStatus(tid,pkey,chkstatus,index){
	var val="Yes";
	var label="Uncheck";
	if(chkstatus=='Yes'){
		val="No";
		label="Check In";
		bootbox.confirm(props.rep_sure_to_uncheck_errmsg,function(result){
			if(result)
				updateCheckInStatus(tid,pkey,chkstatus,val,label,index);
		});
	}else{
		updateCheckInStatus(tid,pkey,chkstatus,val,label,index);
	}
}

function updateCheckInStatus(tid,pkey,chkstatus,val,label,index){
	var url='TransactionDetails!changeCheckInStatus?eid='+eid+'&tid='+tid+'&profilekey='+pkey+'&checkInStatus='+chkstatus;
	$.ajax({
		type:'get',
		url:url,
		error: function(){alert(props.rep_sys_cnt_prc_lbl);},
		success:function(result){
			if(!isValidActionMessage(result)) return;
			
			var localVal=val, localBtnLbl=label;
			if(localVal=='Yes'){
				localVal=props.gllobal_yes_lbl;
				localBtnLbl=props.rep_uncheck_btn_lbl;
			}else if(localVal=='No') {
				localVal=props.global_no_lbl;
				localBtnLbl=props.rep_checkin_btn_lbl;
			}
			
			$("#cis_"+pkey).html(localVal);
			$("#btn_lbl_"+pkey).html(localBtnLbl);
			$("#cis_btn_"+pkey).attr("data-chkstatus",val);
			
			var jsonObj = rsvpcheckindata.mapkeyfiled;
			var compressedkey='';
		    for(var key in jsonObj) {
		      if(jsonObj[key]=='Checked In'){ 
		    	  compressedkey=key;
		    	  break;
		      }
		    }
			rsvpcheckindata.rsvpResponseMap[index][compressedkey]=val;
			var checkedIn=rsvpcheckindata.rsvpResponseMap.filter(function( obj ){
				  return obj[compressedkey] == 'Yes';
			 });
			$("#totalchkin").html(checkedIn.length);
		}
	});
}

function searchRSVPReports(){
	if ($("#rsvpAttendeeDisFldsData").is(':visible'))
		$(".rsvpattnddisplayflds").slideToggle(800);
	if ($("#rsvpCheckInDisFldsData").is(':visible'))
		$(".rsvpcheckindisplayflds").slideToggle(800);
	var searchcontent=$('input[name="rsvpReportsData.searchContent"]').val();
	searchcontent=searchcontent.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	$('input[name="rsvpReportsData.searchContent"]').val(searchcontent);
	
	if($("#searchlist").val()=="email"){

		var email = searchcontent;
		 if ($.trim(email).length == 0) {
			 $("#search_filter").focus();
			 $('#search_filter').tooltipster('content',props.rep_email_is_empty_errmsg);
			 $('#search_filter').tooltipster('show');
			 return false;
		 }
		 if (!validateEmail(email)) {
			 $('#search_filter').tooltipster('content', props.la_supp_popup_invalid_mail);
			 $('#search_filter').tooltipster('show');
			 return false;
		 } 
	
	}else if(searchcontent==''){
		var alertmsg="";
		if($("#searchlist").val()=="transactionId")
			alertmsg=props.rep_trans_id_empty_errmsg;
		else if($("#searchlist").val()=="name")
			alertmsg=props.rep_name_is_empty_errmsg;
		else alertmsg=props.rep_search_data_is_empty_errmsg;
		
		$("#search_filter").focus();
		$('#search_filter').tooltipster('content', alertmsg);
		$('#search_filter').tooltipster('show');
		return false;
	}
	$('input[name="rsvpReportsData.searchContent"]').val(searchcontent);
	$('#search_filter').tooltipster('hide');
	$('#rsvprepsearchid').val(true);
	if ($("#rsvpAttendeeRepBlock").is(':visible')) {
		$('#mainurl').iCheck('check');
		$('#addmanualurl').iCheck('check');
		$("#responseTypeId").val("1");
		$("#attndTypeId").val("1");
		$("#sureNotSureId").hide();
		actionType='response';
		formaction('response');
	}else{
		$("#checkinstatusid").val("1");
		actionType='checkedin';
		formaction('checkedin');
	}
}
