function getTicketsAvailabilityMsg(){
	if(tktsData.availibilitymsg==undefined)
		return;
	var data=tktsData.availibilitymsg;
	for(i=0;i<ticketsArray.length;i++){
		var tktid=ticketsArray[i];
		if(data[tktid]!=undefined){
			var str=data[tktid];
			var capacity=data["capacity_"+tktid];
			var soldqty=data["soldqty_"+tktid];
			var holdqty=data["hold_"+tktid];
			var remainingqty=Number(data["remaining_"+tktid])-Number(holdqty);
			if(remainingqty<0)remainingqty=0;
			str = str.replace(/\$capacity/g,capacity);
			str=str.replace(/\$soldOutQty/g,soldqty);
			str=str.replace(/\$remainingQty/g,remainingqty);
			str=str.replace(/\$onHoldQty/g,holdqty);
			str="<pre  style='font-family: verdana; font-size:10px; line-height:100%; padding:0px; margin:0px; white-space: pre-wrap; white-space: -moz-pre-wrap !important; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;color:#666666;'>"+str+"</pre>";
			//str="<span class='small' >"+str+"</span>";
			if($(tktid)){
				var availmsg="availmsg_"+tktid;
				var cellid="divmsg_"+tktid;
				var cell=$(cellid);
				var div=document.createElement("div");	
				div.setAttribute("id",availmsg);
				div.setAttribute("class","small");
				div.setAttribute("width","55%");
				cell.appendChild(div);
				
				if($(availmsg))
					$(availmsg).innerHTML=str;
			}
		}	
	}

}
function getProfileJson(tid,eid){
tranid=tid;
new Ajax.Request('/volume/embedded_reg/profilejsondata.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{eid:eid,tid:tranid},
onSuccess: ProfileJsonResponse,
onFailure:  failureJsonResponse
});


}

function ProfileJsonResponse(response){
profileJsondata=new Object();
profileJsondata=response.responseText;
getProfileData(tranid,eventid);
}

var profilepageclickcount=0;
function validateProfiles(tid){
profilepageclickcount++;

if(profilepageclickcount>1){
return;
}
	if(document.getElementById('enablepromotion')){
		promotionenable=document.getElementById('enablepromotion').checked;
		if(document.getElementById('enablepromotion').checked){
			document.getElementById('enablepromotion').value="yes";
		}
	}
	document.getElementById('profilesubmitbtn').style.display='none';
	var count=0;
	for (var p=0;p<ctrlwidget.length;p++){
		id=ctrlwidget[p];
		if(CtrlWidgets[id].Validate())
		{
			if(id=='buyer_fname_1')
				bfname=CtrlWidgets[id].GetValue();
			else if(id=='buyer_lname_1')
				blname=CtrlWidgets[id].GetValue();
			else if(id=='buyer_email_1'){
				bemail=CtrlWidgets[id].GetValue();
				var emailtest=Validate_email(bemail);
				if(emailtest){
					getemailmessage(true);
				}
				else{
					getemailmessage(false);
					count++;
				}
			}
			else if(id=='buyer_phone_1')
				bphone=CtrlWidgets[id].GetValue();
		}
		else{
			count++;
		}
		
		if(document.getElementById("q_"+id)){
			if(document.getElementById("q_"+id).type=="text" || document.getElementById("q_"+id).type=="textarea"){
				document.getElementById("q_"+id).value=jQuery.trim(CtrlWidgets[id].GetValue());
			}
		}	
	}
	if(count==0){
		if($('profile'))
			$('profile').hide();
		showTicLoadingImage("Loading...");
		document.getElementById('profileerr').innerHTML='';
		//if($('seatingsection')){
		//	checkseatsavailibilityatprofilesubmit(tid);
		//}
		//else
		{
			//submitProfiles(tid);
			checkticketsstatusprofilelevel(tid);
			
		}
		//checkticketsstatusprofilelevel(tid);
	}
	else{
	clickcount=0;
	profilepageclickcount=0;
	document.getElementById('profilesubmitbtn').style.display='block';
	if(parseInt(count)>1)
	document.getElementById('profileerr').innerHTML="<font color='red'>There are "+count+" errors</font>";
	else
	document.getElementById('profileerr').innerHTML="<font color='red'>There is  "+count+" error</font>";
	}
}


function checkseatsavailibilityatprofilesubmit(tid){
if(discountfalgpro=='false')
if(checkdisc()){return;}
var check="level2";
	var jsonFormat="{";
	var sec_ids="";
	for(i=0;i<allsectionid.length;i++){
		if(sec_ids==""){
			sec_ids=allsectionid[i];
		}
		else{
			sec_ids=sec_ids+"_"+allsectionid[i];
		}
		if(section_sel_seats[allsectionid[i]]!=undefined){
			jsonFormat=jsonFormat+'"'+allsectionid[i]+'":['+section_sel_seats[allsectionid[i]]+']';
				if(i!=allsectionid.length-1){
				jsonFormat=jsonFormat+",";
				}
		}
	}
	
	jsonFormat=jsonFormat+"}";
	new Ajax.Request('/volume/embedded_reg/seating/checkseatstatus.jsp?timestamp='+(new Date()).getTime(),{
	method: 'get',
	parameters:{eid:eventid,tid:tid,eventdate:evtdate,check:check,selected_seats:jsonFormat,all_section_ids:sec_ids},
	onSuccess: seatavailableprofileresponse,
	onFailure: seatunavailable
	});
		
}

function seatavailableprofileresponse(response){
var data=response.responseText;
var availablejson=eval('('+data+')');
if(availablejson.status == "success"){
	var tid=availablejson.tid;
	isavailableseat="yes";
	submitProfiles(tid);
}
else{
	isavailableseat="no";
	// for(i=0;i<ticketsArray.length;i++){
		// var ticketd=ticketsArray[i];
		// if(document.getElementById("qty_"+ticketd).type=='hidden'){
			// document.getElementById("qty_"+ticketd).value=0;
			// if(document.getElementById("show_"+ticketd))
			// document.getElementById("show_"+ticketd).innerHTML=0;
		// }
	// }
	if(clickcount!=undefined){clickcount=0;}
      profilepageclickcount=0;
	  
	alert("Few of the seats you are trying to book are currently on hold or already soldout");
	if($('profile')){
$('profile').hide();
		
	}
	if($('paymentsection')){

$('paymentsection').hide();
	}
	
	if($('registration')){
		$('registration').show();
	}
	
	
	
	if($('eventdate')){
		$('eventdate').disabled=false;
	}
	if($('ticket_div')){
		$('ticket_div').hide();
	}
	if($('ticket_timer')){
		$('ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}
	if($('seatingsection')){
		hideTicLoadingImage();
		$('seatingsection').innerHTML='';
		getseatingsection();
	}
	updatecurrentaction("tickets page");
}
}

function submitProfiles(tid){
$('ebee_profile_frm').action='/volume/embedded_reg/profileformaction.jsp?tid='+tid+'&seatingenabled='+seating_enabled_tkt_wedget+'&ticketids='+ticketsArray+'&eventdate='+evtdate+'&timestamp='+(new Date()).getTime();
$('ebee_profile_frm').request( {
onSuccess: ProfileActionResponse,
onFailure:  failureJsonResponse
});

}

function ProfileActionResponse(response){
data=response.responseText;
var statusJson=eval('('+data+')');
var status=statusJson.status;
fbsharepopup=statusJson.showfbsharepopup;
if(status=='Success'){
getPaymentSection(tranid,eventid);
}

if(status!='Success'){
getProfilePage();
return;
}

if(!$("eventname")){
	var divroot=document.createElement("div");
	divroot.setAttribute('id','eventname');
	divroot.setAttribute('style','display:none;');
	var cell=$('container');
	cell.appendChild(divroot);
	document.getElementById('eventname').style.display="none";
	if(statusJson.eventname!=undefined)
		$('eventname').innerHTML=statusJson.eventname;
}
if(!$("fb-description")){
	var divroot=document.createElement("div");
	divroot.setAttribute('id','fb-description');
	divroot.setAttribute('style','display:none;');
	var cell=$('container');
	cell.appendChild(divroot);
	document.getElementById('fb-description').style.display="none";
	var fbdesc="";
	if(statusJson.eventwhen!=undefined)
		fbdesc+="When: "+statusJson.eventwhen+".";
	if(statusJson.eventwhere!=undefined)
		fbdesc+="&nbsp;Where: "+statusJson.eventwhere+"";
	$('fb-description').innerHTML=fbdesc;	
}
}

function updatecurrentaction(cur_action){
new Ajax.Request('/volume/embedded_reg/updatetempaction.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tranid,current_action:cur_action}
	});
}


function copyByuerData(ticketid){
var phone='';
var fname=document.getElementById('q_buyer_fname_1').value;
var lname=document.getElementById('q_buyer_lname_1').value;
var email=document.getElementById('q_buyer_email_1').value;
if(document.getElementById('q_buyer_phone_1'))
phone=document.getElementById('q_buyer_phone_1').value;
if(document.getElementById('q_'+ticketid+'_fname_1'))
document.getElementById('q_'+ticketid+'_fname_1').value=fname;
if(document.getElementById('q_'+ticketid+'_lname_1'))
document.getElementById('q_'+ticketid+'_lname_1').value=lname;
if(document.getElementById('q_'+ticketid+'_email_1'))
document.getElementById('q_'+ticketid+'_email_1').value=email;
if(document.getElementById('q_'+ticketid+'_phone_1'))
document.getElementById('q_'+ticketid+'_phone_1').value=phone;
}


function emailcheck(str) {
		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    return false
		 }
		var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		//if(!email_regex.test(str)){
			return email_regex.test(str);
		//}
 		// return true					
	}

function Validate_email(emailID){
//alert("b4 trim:"+emailID+":");
//alert("after trim:"+jQuery.trim(emailID)+":");
	emailID=jQuery.trim(emailID);
	$('q_buyer_email_1').value=emailID;
	if ((emailID==null)||(emailID=="")){
		return 
	}
	if (emailcheck(emailID)==false){
		return false
	}
	return true
 }

function getemailmessage(type){
	if(type){
		if($('email_err_msg')){
			$('email_err_msg').innerHTML='';
			$('email_err_msg').style.display='none';
		}
	}
	else{
		if($('email_err_msg')){
		}
		else{
			var cell=$('buyer_email_1');
			var div=document.createElement('div');
			div.setAttribute('id','email_err_msg');
			div.setAttribute("style","color: red;");
			cell.appendChild(div);
		}
		$('email_err_msg').innerHTML='Invalid Email Address';
		$('email_err_msg').style.display='block';
	}
}

function getresponses(){
for (var p=0;p<ctrlwidget.length;p++){
var id=ctrlwidget[p];
responsesdata[id]=CtrlWidgets[id].GetValue();
}
}
