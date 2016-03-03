function skipPriorityRegForm(){
	priRegType="Skip";
	if(document.getElementById('eventdate')){
		document.getElementById('prioerror').innerHTML='';
		var index=document.getElementById('eventdate').selectedIndex;
		evtdate=document.getElementById('eventdate').options[index].value;
		 
		if(evtdate=='Select Date'){
			document.getElementById('prioerror').innerHTML="<font color='red'>"+props.pri_pls_select_event_date_errmsg+"</font>";
			return;
		}
	}
	document.getElementById('prioerror').innerHTML='';
	getTicketsJson(eventid);
}
function priorityRegFormResponse(response){
	var regFormResponseData=eval('(' + response.responseText + ')');
	if(regFormResponseData.status=='fail'){
		document.getElementById('registration').style.display='block';
		document.getElementById('prioerror').innerHTML="<font color='red'>"+regFormResponseData.reason+"</font>";
	}
	
	else if(regFormResponseData.status=='success'){
		priRegToken=regFormResponseData.prireg_token;
		priRegType="Continue";
		priListId=regFormResponseData.pri_list_id;
		priLimitType=regFormResponseData.limit_type;
		getTicketsJson(eventid);
	}
}

function validatePriorityRegForm(){
	var errmsg='';
	if(document.getElementById('eventdate')){
		 var index=document.getElementById('eventdate').selectedIndex;
		 evtdate=document.getElementById('eventdate').options[index].value;
		 
		 if(evtdate=='Select Date')
			 errmsg="<font color='red'>"+props.pri_pls_select_event_date_errmsg+"</font>";
	}
	
	var selListVal='';
	if(document.getElementById('sel_pri_list'))
		selListVal=document.getElementById('sel_pri_list').value;
	if(priorityListData.list.length>1){
		if(selListVal=='0'){
			if(errmsg=='')
				errmsg="<font color='red'>"+props.pri_pls_sel_pri_list_errmsg+"</font>";
			else
				errmsg="<font color='red'>"+props.pri_pls_sel_pri_list_event_date_errmsg+"</font>";
		}
	}
	
	if(errmsg!=''){
		document.getElementById('prioerror').innerHTML=errmsg;
		return;
	}
	
	var listid=document.getElementById('pri_listId').value;
	document.getElementById('prioerror').innerHTML='';
	var key1='',key2='';
	key1=document.getElementById("prikey1_"+listid).value;
	var noofflds=document.getElementById("noof_fields_"+listid).value;
	if(document.getElementById("prikey2_"+listid) && noofflds==2)
		key2=document.getElementById("prikey2_"+listid).value;
	document.getElementById('registration').style.display='none';
	$('ebee_priority_frm').action='/embedded_reg/PriorityRegFormAction.jsp?timestamp='+(new Date()).getTime();
	$('ebee_priority_frm').request({
	parameters:{eid:eventid,evtdate:evtdate,prioritykey1:key1,prioritykey2:key2,noofflds:noofflds},
	onComplete:priorityRegFormResponse
	});
}

function getPriorityLabels(){
	document.getElementById('prioerror').innerHTML='';
	var selListVal=document.getElementById('sel_pri_list').value;
	for(var i=0;i<priorityListData.list.length;i++)
		document.getElementById('auth_fields_'+priorityListData.list[i].list_id).style.display='none';
	document.getElementById('auth_fields_'+selListVal).style.display='block';
	document.getElementById('pri_btns_section').style.display='block';
	document.getElementById('pri_listId').value=selListVal;
}

function priorityRegBlock(response){
	isPriority=true;
	var priorityResponseData=response.responseText;
	priorityListData=eval('(' + priorityResponseData + ')');
	var priorityheader="Tickets",sellistlabel="Select Priority List",selddlistlabel="Select Priority List",skipbtnlabel="Skip",continuebtnlabel="Continue";
	if(priorityListData.PriorityRegWordings["event.prio.reg.continue.btn.label"]!=undefined)
		continuebtnlabel=priorityListData.PriorityRegWordings["event.prio.reg.continue.btn.label"];
	if(priorityListData.PriorityRegWordings["event.prio.reg.skip.btn.label"]!=undefined)
		skipbtnlabel=priorityListData.PriorityRegWordings["event.prio.reg.skip.btn.label"];
	if(priorityListData.PriorityRegWordings["event.prio.reg.sel.list.dd.label"]!=undefined)
		selddlistlabel=priorityListData.PriorityRegWordings["event.prio.reg.sel.list.dd.label"];
	if(priorityListData.PriorityRegWordings["event.prio.reg.sel.list.label"]!=undefined)
		sellistlabel=priorityListData.PriorityRegWordings["event.prio.reg.sel.list.label"];
	if(priorityListData.PriorityRegWordings["event.prio.reg.page.header"]!=undefined)
		priorityheader=priorityListData.PriorityRegWordings["event.prio.reg.page.header"];
	
	if(document.getElementById('eventdate')){//if eventdate id exists it is recurring event.
		insertOptionBefore('tickets');
	}
	
	document.getElementById('registration').style.display='block';
	if(document.getElementById('pageheader')){
		document.getElementById('pageheader').innerHTML=priorityheader;
		document.getElementById('pageheader').style.display='block';
	}
		var htmldata='<form action="/embedded_reg/PriorityRegFormAction.jsp" name="ebee_priority_frm" id="ebee_priority_frm" method="post" onsubmit="validatePriorityRegForm();return false;"/>'+
		'<input type="hidden" id="pri_eventid" name="eid" value="" /><input type="hidden" id="pri_listId" name="listId" value="" />'+
			'<table width="100%" cellpadding="0" cellspacing="0" valign="top" class="tableborder">'+
			'<tr><td width="100%" cellspacing="0" cellpadding="0">';
			//'<div id="prioerror"></div>';
			
		var listsCount=priorityListData.list.length;
		if(listsCount>1){
			htmldata+='<div><table width="100%" cellpadding="0" cellspacing="0"><tr><td>'+sellistlabel+':&nbsp;<select id="sel_pri_list" onChange="getPriorityLabels();"><option value="0">'+props.pri_select_list_lbl+'</option>';
		for(var i=0;i<listsCount;i++){
			var priorityList=priorityListData.list[i];
			htmldata+='<option value="'+priorityList.list_id+'">'+priorityList.list_name+'</option>';
		}
			htmldata+='</select></td></tr><tr><td><div style="height:5px"></div></td></tr></table></div>';
		}
		
		for(var i=0;i<listsCount;i++){
			var priorityList=priorityListData.list[i];
			htmldata+='<div id="auth_fields_'+priorityList.list_id+'" style="display:none;"><input type="hidden" id="noof_fields_'+priorityList.list_id+'" value="'+priorityList.no_of_flds+'" /><table width="100%" cellpadding="0" cellspacing="0">'+
			'<tr><td width="28%"><span id="list_label1_'+priorityList.list_id+'">'+priorityListData.list_labels[priorityList.list_id].label1+'</span></td><td><input type="text" id="prikey1_'+priorityList.list_id+'" size="30"></td></tr>';
			if(priorityList.no_of_flds==2)
				htmldata+='<tr><td><div style="height:5px"></div></td></tr>'+
				'<tr><td width="27%"><span id="list_label2_"'+priorityList.list_id+'">'+priorityListData.list_labels[priorityList.list_id].label2+'</span></td><td><input type="text" id="prikey2_'+priorityList.list_id+'" size="30"></td></tr>';
			htmldata+='</table></div>';
		}
		htmldata+='<div id="pri_btns_section" style="display:block;"><table width="100%" cellpadding="0" cellspacing="0"><tr><td><div style="height:5px"></div></td></tr>'+
			'<tr><td width="100%"><table width="100%" cellpadding="0" cellspacing="0"><tr><td width="28%"></td>'+
			'<td><input type="button" name="pri_continue" id="pri_continue" value="'+continuebtnlabel+'" onClick="validatePriorityRegForm();">';
		if(priorityListData.skip_btn_req=='Y')
			htmldata+='&nbsp;<input type="button" name="pri_skip" id="pri_skip" value="'+skipbtnlabel+'" onClick="skipPriorityRegForm();">';
		
		htmldata+='</td></tr></td></tr></table></div>';
		
		htmldata+='</td></tr></table></form>';
		document.getElementById("registration").innerHTML="";
		document.getElementById("registration").innerHTML=htmldata;
		document.getElementById('pri_eventid').value=eventid;
		if(listsCount==1){
			document.getElementById('pri_listId').value=priorityListData.list[0].list_id;
			document.getElementById('auth_fields_'+priorityListData.list[0].list_id).style.display='block';
			document.getElementById('pri_btns_section').style.display='block';
		}
}

function getPriorityRegistration(eid){
	eventid=eid;
	new Ajax.Request('/embedded_reg/PriorityRegBlock.jsp?timestamp='+(new Date()).getTime(), {
		  method: 'get',
		  parameters:{eid:eid},
		  onSuccess: priorityRegBlock,
		  onFailure:  failureJsonResponse
	});
}

function priorityTimeoutPopup(){
	var divpopupcontent=props.sorry_time_out+"<br><input type='button' value='"+props.try_again+"' onclick='timeoutTryAgain()'><a href=# onclick=timeouttkt()><img src='/home/images/images/close.png' class='divimage'></a>";
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	
	if(document.getElementById('ticketpoup_div')){
		
	}else{
		var divpopup=document.createElement("div");
		divpopup.setAttribute('id','ticketpoup_div');
		divpopup.className='ticketpoup_div';
		var cell=$('container');
		cell.appendChild(divpopup);
	}
	
	document.getElementById('ticketpoup_div').innerHTML=divpopupcontent;
	document.getElementById('ticketpoup_div').style.display="block";
	var pos=jQuery(document).scrollTop()+130;
		document.getElementById('ticketpoup_div').style.top=pos+'px';	
	document.getElementById('ticketpoup_div').style.left='26%';
}

function timeoutTryAgain(){
	$('ticketpoup_div').hide();
	document.getElementById('ticketpoup_div').innerHTML='';
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	window.location.reload(true);
} 