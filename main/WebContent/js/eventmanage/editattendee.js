var profilejson;
var responseAray;
var eventid;
var timestamp=new Date();
var responsesdata=[];
var CtrlWidgets=[];
var ctrlwidget=[]
var tid='';
var pid='';
function getProfileBlock(eid,jsondata,ticketid,responsejson,tranid,pkey){
	profilejson=jsondata;
	eventid=eid;
	pid=pkey;
	tid=tranid;
	fillResponses(responsejson);
	if(ticketid==''){		
		getBuyerProfilePage(eid,pid);
	}else{
	getProfilePage(eid,ticketid,pid);
	}
	//alert("final page0"+$('#profile').html());
}

function fillResponses(responsejson){
	responseAray=responsejson;
	var basicres=responsejson.basicres;
	var customres=responsejson.customres;
	
	for(i=0;i<basicres.length;i++){
		var profile=basicres[i];
		
		var qid=profile.qid;
		var response=profile.response;		
		responsesdata[qid]=response;
	}
	for(var j=0;j<customres.length;j++){
		var customprofile=customres[j];
		var qid=customprofile.qid;
		var response=customprofile.response;		
		var qjson=profilejson[qid];	
		if(qjson!=undefined){
		var qtype=qjson.type;
		if(qtype=='select'){
		response=customprofile.response-1;	
		}
		}
		responsesdata[qid]=response;
	}
	
	
}

function getProfilePage(eid,ticketid,pid){
	var url='/main/eventmanage/EditAttendeeProfile?t='+timestamp.getTime();
	$.ajax({
		type:'get',
		data:{eid:eid,ticketid:ticketid,pid:pid},
		url:url,
		success:function(result){
			ProfilesBlockResponse(result);
		}
	});

	
}
function getBuyerProfilePage(eid,pid){
	var url='/main/eventmanage/EditAttendeeProfile!buyerProfile?t='+timestamp.getTime();
	$.ajax({
		type:'get',
		data:{eid:eid,pid:pid},
		url:url,
		success:function(result){
			ProfilesBlockResponse(result);
		}
	});

	
}
var _reselect=[];
function ProfilesBlockResponse(response){
	ctrlwidget=[];
	 _reselect=[]
	document.getElementById('profile').innerHTML=response;
	//var updatebtn= new YAHOO.widget.Button("updaprofile",{onclick:{fn:function(){updateProfileData();}}});
	//var cancelbtn= new YAHOO.widget.Button("cancelbtn",{onclick:{fn:function(){history.back();}}});
	var questions=profilejson.questions;
	for(var index=0;index<questions.length;index++){
		var qid=questions[index];	
		if(profilejson[qid].type!=undefined && profilejson[qid].type=='select')
		_reselect.push(qid);		
		putWidgetIdeal123('0',qid,'0');
	}
		reSelect();
}

function reSelect(){
   if(_reselect!=undefined && _reselect.length>0)
   {  for (var indi=0;indi<_reselect.length;indi++){
        var opt=document.getElementById('q_'+_reselect[indi]).options;
            for(var i=0;i<opt.length;i++)
          {  if(opt[i].value==(responsesdata[_reselect[indi]]+1))
             document.getElementById('q_'+_reselect[indi]).selectedIndex=i;
       
          }
     }
   }
}

function putWidgetIdeal123(ticketid,qid, profileid){
	var jsondata=profilejson;	
	var questionjson=jsondata[qid];	
	var  objWidget = new InitControlWidget(questionjson,qid);
	ctrlwidget.push(qid);
	CtrlWidgets[qid]= objWidget;
	}

function updateProfileData(type){
 // document.getElementById('buttons').innerHTML="<center>Processing...<br><img src='../images/ajax-loader.gif'></center>";
	$('#buttonsloading').show();
	$('#buttons').hide();
var ques=profilejson.questions;
var qarray =[];
for(var index=0;index<ques.length;index++){
var question=ques[index];
var questionjson=profilejson[question];
qtype=questionjson.type;

var ansarray=[];
var obj={qid:question};
var elementid='q_'+question;
if(qtype=='checkbox'||qtype=='radio'||qtype=='select'){
	if(qtype=='select'){
		var x=document.getElementById(elementid).selectedIndex;
		ansarray.push(document.getElementById(elementid).options[x].value);
	}
	else{
var options=document.getElementsByName(elementid);
for(var j=0;j<options.length;j++){
if(options[j].checked==true){
ansarray.push(options[j].value);
}
}
}
obj.response=ansarray;
}
else{
	if(document.getElementById(elementid))
obj.response=document.getElementById(elementid).value;
}
qarray.push(obj);
}
var bobj={pid:pid};
bobj.questions=qarray;
var array=[];
array.push(bobj);
var mybJSONText=JSON.stringify(array);
var jsonObj=JSON.parse(mybJSONText);

document.getElementById('profileresponsejson').value=mybJSONText;
/*$('editattendee').request({
parameter:{pid:pid,tid:tid},	
onComplete: updateDataResponse
});*/

$.ajax({
	type:'get',
	data:$('#editattendee').serialize(),
	url:'/main/eventmanage/EditProfileData',
	success:function(result){
		if(!isValidActionMessage(result)) return;
		updateDataResponse(result,type);
	}
});

}
function updateDataResponse(reponse,type){
	/*if(type=='attendee')
	$('#buyerstatus').show().html('<div class="col-md-1"></div><div class="col-md-6"><div style="" class="alert alert-success alert-dismissable page-alert"><button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Profile updated successfully.</div></div>');
	else
	$('#buyerstatus').show().html('<div class="col-md-8"><div style="" class="alert alert-success alert-dismissable page-alert"><button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Profile updated successfully.</div></div>');*/
	$('#buyerstatus').hide();
	/*if(type=='attendee')
		notification('Profile updated successfully.','success');
	else*/
		notification(props.profile_updated_status_msg,'success');
	
	
	$('#buttonsloading').hide();
	$('#buttons').show();
	 $('html, body').animate({ scrollTop: $("#buyerstatus").height()}, 1000);
	
	//showDetails(tid);
	 //$("html, body").animate({scrollTop: $('#eventName').offset().top}, "slow",function(){});
	/*if(element)
	   element.trigger('click');*/
//window.location.href='TransactionDetails!getTransactionInfo?eid='+eventid+'&tid='+tid+'&purpose=editprofile';	
}

/*YAHOO.util.Event.onContentReady("updaprofile", function () {
	var oSubmitButton2 = new YAHOO.widget.Button("updaprofile",{onclick:{fn:updateProfileData()}});
	});
*/