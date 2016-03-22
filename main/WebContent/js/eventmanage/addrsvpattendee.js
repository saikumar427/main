var eventid='';
var jsonprofile='';
var timestamp=new Date();
var eventdate='';
var attendingcount='';
var rsvpprofileJsondata='';
var questionsobj="";
var surequestions="";
var responsejsondatavm="";
var rsvpjson="";
var rsvpctrlwidget=[];
var RSVPCtrlWidgets=[];
var rsvpresponsesdata=[];
function AssignRSVPJsonData(eid){
	eventid=eid;
}

function getRSVPPageforselectedDates(){
	getrsvpresponses();
	var index=document.getElementById('event_date').selectedIndex;
	eventdate=document.getElementById('event_date').options[index].value;
	document.getElementById('attendeecount').value="";
	document.getElementById('rsvpprofile').style.display="none";
}


function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}


function validateRSVPSelectCount(){
	if(document.getElementById('event_date')){
		var index=document.getElementById('event_date').selectedIndex;
		eventdate=document.getElementById('event_date').options[index].value;
		if(eventdate == ''){
			//alert("Select a date and time to Attend");
			bootbox.confirm(props.aa_select_date_time_lbl,function(result){});
			return false;
		}
	}
	attendingcount=document.getElementById('attendeecount').value;
	attendingcount=Number(attendingcount);
	if(attendingcount == 0){
		//alert("Please Enter Attendee Count");
		bootbox.confirm(props.aa_enter_attendee_count_lbl,function(result){});
		return false;
	}
	else
		showimageLoad();
		getRSVPProfileJsonData(attendingcount);
	
	
	return true;
}

function getRSVPProfileJsonData(count){
	var url='RSVPProfileJsonData?t='+timestamp.getTime();
	/* new Ajax.Request(url,{
	method: 'get',
	parameters:{eid:eventid,sure:attendingcount},
	onSuccess: RSVPProfileJsonResponse,
	onFailure:  failurersvp
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eventid,sure:attendingcount},
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			RSVPProfileJsonResponse(result);
		}
	});
}

function  RSVPProfileJsonResponse(response){
	var data=response;
	rsvpjson=data;
	rsvpprofileJsondata=eval('('+data+')');
	questionsobj=rsvpprofileJsondata.transactionquestions;
	surequestions=rsvpprofileJsondata.responsequestions;
	getRSVPProfilesBlock(eventid);
}
function getRSVPProfilesBlock(eid){
	var url='RSVPProfileVmData?t='+timestamp.getTime();
	/* new Ajax.Request(url,{
	method: 'get',
	parameters:{eid:eid,sure:attendingcount,eventdate:eventdate},
	onSuccess: RSVPProfilesBlockResponse,
	onFailure: failurersvp
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eid,sure:attendingcount,eventdate:eventdate},
		url:url,
		success:function(result){
			RSVPProfilesBlockResponse(result);
		}
	});
	
	
}
function RSVPProfilesBlockResponse(response){
	rsvpctrlwidget=[];
	var data=response;
	document.getElementById('rsvpregistration').style.display='none';
	//document.getElementById('addattendeeStatus').style.display='none';
	document.getElementById('rsvpprofile').style.display='none';
	if(document.getElementById('titleDiv'))
	document.getElementById('titleDiv').innerHTML=props.aa_attendee_info_section_header;
	if(document.getElementById('recurringblock'))
	document.getElementById('recurringblock').style.display='none';
	document.getElementById('rsvpprofile').innerHTML=response;
	rsvploadbuttons();	
	if(isNaN(attendingcount)){
		attendingcount =1;
	}
	 for(var p=1;p<=attendingcount;p++){
		
			for(i=0;i<surequestions.length;i++){
				var qid=surequestions[i];
				putsureWidget('s',qid, p);
			}
		}
	 putcustomattend(questionsobj);
	 
	 document.getElementById('rsvpprofile').style.display='block';
	 document.getElementById('imageLoad').style.display='none';	
	 document.getElementById('q_s_fname_1').focus();


}
	

function putcustomattend(questionsdata){
	for(i=0;i<questionsdata.length;i++){
			var qid=questionsdata[i];
			if(qid != 'fname' && qid != 'email' && qid != 'phone' && qid != 'lname')
				putrsvpWidget('p',qid, '1');
		}
}
reqelms=[];
function putrsvpWidget(ticketid,qid, profileid){
	var elmid=ticketid+'_'+qid;
	var responsejsondata=eval('(' + rsvpjson + ')');
	var questionjson=responsejsondata[ticketid+'_'+qid];
	var  objWidget = new InitControlWidget(questionjson,elmid);
	$('input.radiochk').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	reqelms.push(elmid);
	rsvpctrlwidget.push(elmid);
	RSVPCtrlWidgets[elmid] = objWidget;
	objWidget.get
	}

function putsureWidget(ticketid,qid, profileid){
	var elmid=ticketid+'_'+qid+'_'+profileid;
	var responsejsondata=eval('(' + rsvpjson + ')');
	var questionjson=responsejsondata[ticketid+'_'+qid];
	var  objWidget = new InitControlWidget(questionjson,elmid);
	
	reqelms.push(elmid);
	rsvpctrlwidget.push(elmid);
	RSVPCtrlWidgets[elmid] = objWidget;
	objWidget.get
	}

function rsvploadbuttons(){

	/*YAHOO.util.Event.onContentReady("profilecontinue", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("profilecontinue",{onclick:{fn:validateRSVPProfiles}});
		});
	YAHOO.util.Event.onContentReady("profilepageBack", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("profilepageBack",{onclick:{fn:getRSVPPage}});
		});*/
	$('input.payment').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$('input.payment').on('ifChecked', function(event){
		selectedpctype=$(this).attr('id');
	});
	}

function validateRSVPProfiles(){
		
		var count=0;
		for (var p=0;p<rsvpctrlwidget.length;p++){
		id=rsvpctrlwidget[p];
		if(document.getElementById(id+'_msg')){
			$('#'+id+'_msg').html('');
	}
		if(RSVPCtrlWidgets[id].Validate())
		{
		if(id.indexOf('s_email')>-1){
				var bemail=RSVPCtrlWidgets[id].GetValue();
				if(emailcheck(bemail)){
					getemailmessage(true,id);
				}
				else{
					getemailmessage(false,id);
					count++;
					}
			}
		}
		else{
		count++;
		}
		}
		if(count==0){
			document.getElementById('profileerr').innerHTML="";
			document.getElementById('profilecontinue').style.display='none';
			$('#attendeecount').val('');
			SubmitRSVPProfileData();
		}
		else{
			if(parseInt(count)>1){
			document.getElementById('profileerr').innerHTML="<font color='red'>"+props.aa_there_are_errmsg1+" "+count+" "+props.aa_errors_errmsg2+"</font>";
				$('html, body').animate({ scrollTop: $("profileerr").height()}, 1000);
			}else{
			document.getElementById('profileerr').innerHTML="<font color='red'>"+props.aa_there_is_an_error_lbl1+"  "+count+" "+props.aa_there_is_an_error_lbl2+"</font>";
				$('html, body').animate({ scrollTop: $("profileerr").height()}, 1000);
			}
		}

}

function emailcheck(str) {
 	var emailExp ="^[a-z0-9!#$%&*+/=?^_`:()'{|}~-]+(?:\\.[a-z0-9!#$%&*+/=?^_`:()'{|}~-]+)*@(?:[a-z0-9!#$%&*+/=?^_`:()'{|}~-](?:[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-]*[_a-z0-9-!#$%&*+/=?^_`:(){|}'~-])?\\.)+[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-](?:[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-]*[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-])?$";
 	str=str.toLowerCase();
	var result=str.match(emailExp);
	if(result==null)
	return false;
	else
	return true; 
	}
function getemailmessage(type,id){
	if(type){
		if(document.getElementById(id+'_msg')){
			document.getElementById(id+'_msg').innerHTML='';
			document.getElementById(id+'_msg').style.display='none';
		}
	}
	else{
		if(document.getElementById(id+'_msg')){
		}
		else{
			var cell=document.getElementById(id);
			var div=document.createElement('div');
			div.setAttribute('id',id+'_msg');
			div.setAttribute("style","color: red;");
			cell.appendChild(div);
		}
		document.getElementById(id+'_msg').innerHTML=props.aa_invalid_email_att_lbl;
		document.getElementById(id+'_msg').style.display='block';
		$('html, body').animate({ scrollTop: $("#"+id+"_msg").height()}, 1000);
	}
}





function showLoadImage()
{
document.getElementById('profilecontinue').style.display='none';
document.getElementById('profilepageBack').style.display='none';
document.getElementById('processimg').style.display="block";
/*document.getElementById('processimg').innerHTML="<center>Loading......<br><img src='../images/loading.gif'></center>";*/
document.getElementById('processimg').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
}


function SubmitRSVPProfileData(){
showLoadImage();
	var responsejsondata=rsvpprofileJsondata;
	questionsobj=rsvpprofileJsondata.transactionquestions;
	surequestions=rsvpprofileJsondata.responsequestions;
	var questions='';
	var responsename=[];
	var responsearray=[];
	var profilearray=[];
	for(i=0;i<questionsobj.length;i++){
		var bqid=questionsobj[i];
		if(bqid!='phone'){
			var bquestionjson=responsejsondata['p_'+bqid];
			var qtype=bquestionjson.type;
			var bansarray=[];
			var bobj={qid:bqid};
			var belementid='q_p_'+bqid;
			belementid=filltransquestions(belementid);
			if(qtype=='checkbox'||qtype=='radio'){
				var options=document.addrsvpattendeeprofiles.elements[belementid];
				if(options.length){
					for(var j=0;j<options.length;j++){
						if(options[j].checked==true){
							bansarray.push(options[j].value);
						}
					}
				}
				else{
					if(options.checked==true){
						bansarray.push(options.value);
					}	
				}		
				bobj.response=bansarray;
			}
			else if(qtype=='select'){
				var index=document.getElementById(belementid).selectedIndex;
				ans=document.getElementById(belementid).options[index].value;
				bansarray.push(ans);
				bobj.response=bansarray;
			}
			else{
				ans=document.getElementById(belementid).value;
				bobj.response=ans;
			}
			profilearray.push(bobj);
		}
	}

	var mybJSONText=JSON.stringify(profilearray);
	var bjsonObj=JSON.parse(mybJSONText);
	$('#transactionjsondata').val(JSON.stringify(bjsonObj));
	
	for(var l=1;l<=attendingcount;l++){
		for(i=0;i<surequestions.length;i++){
			questions=surequestions[i];
			var rqid=surequestions[i];
			var rquestionjson=responsejsondata['s_'+rqid];
			var rtype=rquestionjson.type;
			var ransarray=[];
			
			//var robj={qid:rqid};
			var ridobj={qid:rqid+'_'+l};
			var relementid='q_s_'+rqid+'_'+l;
			if(rtype=='checkbox'||rtype=='radio'){
				var responseoptions=document.addrsvpattendeeprofiles.elements[relementid];
				if(responseoptions.length){
					for(var k=0;k<responseoptions.length;k++){
						if(responseoptions[k].checked==true){
							ransarray.push(responseoptions[k].value);
						}
					}
				}
				else{
					if(responseoptions.checked==true){
						ransarray.push(responseoptions.value);
					}	
				}		
				ridobj.response=ransarray;
				}
				else if(rtype=='select'){
					var resindex=document.getElementById(relementid).selectedIndex;
					ans=document.getElementById(relementid).options[resindex].value;
					ransarray.push(ans);
					ridobj.response=ransarray;
				}
				else{
					ans=document.getElementById(relementid).value;
					//robj.response=ans;
					ridobj.response=ans;
				}
				//responsearray.push(robj);
				responsearray.push(ridobj);
			}
			
	}
	var myJSONText = JSON.stringify(responsearray);
	var jsonObj=JSON.parse(myJSONText);
	$('#responsejsondata').val(JSON.stringify(jsonObj));
	
	
	var edate=document.getElementById('eventdate').value;
	//$('addrsvpattendeeprofiles').action='SaveRSVPProfileDetails';
	var url='SaveRSVPProfileDetails';
	/*$('addrsvpattendeeprofiles').request({
	parameters:{eid:eventid,sure:attendingcount,eventdate:edate},
	onComplete:SubmitRSVPProfilerespone
	});*/
	
	
	$.ajax({
		type:'POST',
		data:$('#addrsvpattendeeprofiles').serialize(),
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			SubmitRSVPProfilerespone(result);
		}
	});
	
	
}

function SubmitRSVPProfilerespone(response){
	var statusMsg=response;
	getRSVPPage();
	responsesdata=[];
	notification(response,'success');
	/*$('#addattendeeStatus').addClass('alert');
	$('#addattendeeStatus').addClass('alert-success');
	$('#addattendeeStatus').css('height','50px');
	$('#addattendeeStatus').html(statusMsg);
	$('#addattendeeStatus').show();*/
	//$('break').show();
	//$('addattendeeStatus').scrollTo();	
	document.getElementById('imageLoad').style.display="none";
	//YAHOO.ebee.popup.wait.hide();
}


function filltransquestions(belementid){
	
	if(belementid=='q_p_fname')
		belementid='q_s_fname_1';
	else if(belementid=='q_p_lname')
		belementid='q_s_lname_1';
	else if(belementid=='q_p_email')
		belementid='q_s_email_1';
	
	else
		belementid=belementid;
	return belementid;
}

function getRSVPPage(){
	getrsvpresponses();
	document.getElementById('rsvpregistration').style.display='block';
	document.getElementById('addattendeeStatus').style.display='none';
	document.getElementById('rsvpprofile').style.display='none';
	if(document.getElementById('titleDiv'))
	document.getElementById('titleDiv').innerHTML='RSVP'
	if(document.getElementById('recurringblock'))
	document.getElementById('recurringblock').style.display='block';
	
	$('#attendeecount').focus();
}

function getrsvpresponses(){
	for (var p=0;p<rsvpctrlwidget.length;p++){
		var id=rsvpctrlwidget[p];
		rsvpresponsesdata[id]=RSVPCtrlWidgets[id].GetValue();
	}
}

function failurersvp(){
alert("Please try Back Later");
}


function showimageLoad(){
	hideall();
	document.getElementById('imageLoad').style.display="block";
	/*document.getElementById('imageLoad').innerHTML="<center>Loading......<br><img src='../images/loading.gif'></center>";*/	
	document.getElementById('imageLoad').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
}
function hideall(){
	document.getElementById('rsvpregistration').style.display='none';
	document.getElementById('addattendeeStatus').style.display='none';
	document.getElementById('rsvpprofile').style.display='none';
	if(document.getElementById('recurringblock'))
		document.getElementById('recurringblock').style.display='none';
		}
