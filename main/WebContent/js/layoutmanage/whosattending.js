function showAttendeesList(groupid){
var attdate='';
var eventtype='';
var showlist=true;
if(jQuery('#attendeeinfo'));
	jQuery('#attendeeinfo').hide();
if(document.getElementById('event_date')){
	insertOptionBeforeAttending('#attendeelist');
	document.getElementById('event_date').style.display="none";
	var index=document.getElementById('event_date').selectedIndex;
	var attdate=document.getElementById('event_date').options[index].value;
	if(jQuery('#whosattendingimageload')){
		jQuery('#whosattendingimageload').show();
	}		
	eventtype ='rsvp';
	if(attdate=='Select Date'){
		attdate=' ';
		showlist=false;
		document.getElementById('attendeeinfo').innerHTML='';	
	document.getElementById('event_date').style.display="block";
		if(jQuery('#whosattendingimageload'))
			jQuery('#whosattendingimageload').hide();
	}

}
if(showlist){
	var url = '/main/eventmanage/layoutmanage/whosattendee/showattendeelist.jsp?timestamp='+(new Date()).getTime()+'&stage='+stage;
	 	 jQuery.ajax( {
	 		   type: 'POST',
	 		   url: url,
	 		  data:{groupid:groupid,eventdate:attdate,eventtype:eventtype},
	 		  success: function(result){
	 			  AttendeesListResponse(result);
	 		  }
	 		  //error:  failureJsonResponse
	 		   });
	 	 }
/*new Ajax.Request('/main/eventmanage/layoutmanage/whosattendee/showattendeelist.jsp?timestamp='+(new Date()).getTime(), {
	  method: 'get',
	  parameters:{groupid:groupid,eventdate:attdate,eventtype:eventtype},
	  onSuccess: AttendeesListResponse,
	  onFailure:  failureJsonResponse
  });*/



else{

if(jQuery('#attendeeinfo'))
jQuery('#attendeeinfo').innerHTML='';;
}
}


function AttendeesListResponse(response){
if(jQuery('#attendeeinfo')){
	//jQuery('attendeeinfo').update(response.responseText);	
	document.getElementById('attendeeinfo').innerHTML='<ul>'+response+'</ul>';
	jQuery('#attendeeinfo').show();	
}
if(jQuery('#whosattendingimageload'))
		jQuery('#whosattendingimageload').hide();	

if(jQuery('#event_date')){
	document.getElementById('event_date').style.display="block";
}		
}

function getRsvpAttendeeList(groupid){
	if(document.getElementById('event_date')){
		insertOptionBeforeAttending('#attendeelist');
	}
	var url = '/customevents/rsvpattendeelist.jsp?timestamp='+(new Date()).getTime();
	jQuery.ajax({
		url: url,
		type : 'POST',
		data: {groupid:groupid},
		success:function(result){
			AttendeesListResponse(result);
		}
	})
	/*new Ajax.Request('/customevents/rsvpattendeelist.jsp?timestamp='+(new Date()).getTime(), {
	  method: 'get',
	  parameters:{groupid:groupid},
	  onSuccess: AttendeesListResponse,
	  onFailure:  failureJsonResponse
  });*/
	
	
}

function showRSVPAttendeesList(groupid){
var attdate='';
var eventtype='';
var showlist=true;
if(jQuery('#attendeeinfo'));
	jQuery('#attendeeinfo').hide();
if(document.getElementById('event_date')){
	document.getElementById('event_date').style.display="none";
var index=document.getElementById('event_date').selectedIndex;
var attdate=document.getElementById('event_date').options[index].value;
if(jQuery('#whosattendingimageload')){
		jQuery('#whosattendingimageload').show();
	}
eventtype ='rsvp';
if(attdate=='Select Date'){
attdate=' ';
showlist=false;
	document.getElementById('event_date').style.display="block";
if(jQuery('#whosattendingimageload'))
			jQuery('#whosattendingimageload').hide();
}

}

if(showlist){
	var url = '/main/eventmanage/layoutmanage/whosattendee/showattendeelist.jsp?timestamp='+(new Date()).getTime();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data:{groupid:groupid,eventdate:attdate,eventtype:eventtype},
		success : function(result){
			AttendeesListResponse(result);
		}
	});
/*new Ajax.Request('/main/eventmanage/layoutmanage/whosattendee/showattendeelist.jsp?timestamp='+(new Date()).getTime(), {
	  method: 'get',
	  parameters:{groupid:groupid,eventdate:attdate,eventtype:eventtype},
	  onSuccess: AttendeesListResponse,
	  onFailure:  failureJsonResponse
  });

*/
}
else{

if(jQuery('#attendeeinfo'))
jQuery('#attendeeinfo').innerHTML='';;
}
}

function failureJsonResponse(){
//alert("failed");
}

function insertOptionBeforeAttending(purpose){ 
  var elSel;
if(purpose=='tickets')
  elSel = document.getElementById('eventdate');
  else
    elSel = document.getElementById('event_date');

	var elOptNew = document.createElement('option');
    elOptNew.text = props.rec_select_date_lbl;
    elOptNew.value = 'Select Date';
    if(elSel.options.length==0){
		try{
			elSel.add(elOptNew,elSel.options[0]);
		}
		catch(ex){
			elSel.add(elOptNew,0);
		}	
	}
  if(elSel.options.length>0){
  var elOptOld = elSel.options[0]; 
  if(elOptOld.value!='Select Date'){
    try {
      elSel.add(elOptNew, elOptOld); // standards compliant; doesn't work in IE
    }
    catch(ex) {
      elSel.add(elOptNew, 0); // IE only
    }
  
  elSel.selectedIndex=0;
}}
}
