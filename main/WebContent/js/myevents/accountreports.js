function getfields(type){
	if(type==undefined){
		type='<s:text name="reportsData.reportsradio"></s:text>';
		}
	var radioattchecked=document.getElementById('reportsattradio').checked;
	var radiotranschecked=document.getElementById('reportstrnsradio').checked;
		if(radioattchecked){
		document.getElementById("attendeecontent").style.display='block';
		//document.getElementById("attendeecontentreports").style.display='block';
		document.getElementById("registrationcontent").style.display='none';
		//document.getElementById("registrationreport").style.display='none';
        if(document.getElementById('accounterrormsg'))
		  document.getElementById('accounterrormsg').style.display='none';
        }
		else {			
			document.getElementById("registrationcontent").style.display='block';
			//document.getElementById("registrationreport").style.display='block';
			document.getElementById("attendeecontent").style.display='none';
           // document.getElementById("attendeecontentreports").style.display='none';	
			if(document.getElementById('accounterrormsg'))
		     document.getElementById('accounterrormsg').style.display='none';
		}
}


var jdata="";
var reporttype="";
var ab="";
function formaction(type){

 /* if(type=='attendee'){
   document.getElementById('nav1').innerHTML='';
   document.getElementById('nav2').innerHTML='';
   }else{
      document.getElementById('nav3').innerHTML='';
      document.getElementById('nav4').innerHTML='';
   }*/
var values=new Array();
var resultdiv="";
/*if(type=="attendee")resultdiv="attreportcontent";
else resultdiv="tranreportcontent";*/

var startdate=document.getElementById('from').value;
var enddate=document.getElementById('to').value;
document.getElementById('eventstartdate').value=startdate;
document.getElementById('eventenddate').value=enddate;
var size=document.getElementById('eventlistsize').value;
if(size==0){alert("No events found");return;}
var val=document.getElementsByName("chekedeventslist");
document.getElementById('chekedlist').value='';
if(val.length==0){alert("No selected events");return;}
for(i=0;i<val.length;i++){
	      if(val[i].checked)
	         values.push(val[i].value);
}
if(values.length==0){ 
  alert("Select atleast one event");
  return;
}

document.getElementById('chekedlist').value=values;	     	
//removeDoubleScroll();
reporttype=type;
	var value=0;
    var count=0;
    if(type=='attendee'){	
		options=document.reportsform.attFields;	 
	     for(i=0;i<options.length;i++){
	        	if(options[i].checked){
	     		value=options[i].value;
	     		count++;
	     	}
	     }
	     if(value==0){
	     	alert('Select Atleast One Display Fields Filter');
	    	 return false;
	   }
		reportsFromSubmit('accountreports!getAttendeeInfo?rtype=attendee','attreportcontent');

	}else if(type=='registration'){
	    var radTID = document.getElementById('radtid').checked;
		var radON = document.getElementById('radonid').checked;
		var online = document.getElementById('onlineid').checked;
		var manual = document.getElementById('manualid').checked; 
		var isvolumesale = document.getElementById('isvolumesale').value;
		
		if(isvolumesale=='Y' && !radTID && !radON){
			var voltickets = document.getElementById('volticketsid').checked;
			if(online || manual || voltickets){
			}else{
			alert('Select Online Regular Tickets or Manual or Volume Tickets Field(s) From Search Filter');
	    	return false;
			}
		}else if(!radTID && !radON){
			if(online || manual){
			}else{
				alert('Select Online or Manual Field(s) From Search Filter');
		    	return false;
			}
		}
					
		options=document.reportsform.Fields;
	     for(i=0;i<options.length;i++){
	        	if(options[i].checked){
	     		value=options[i].value;
	     		count++;
	     	}
	     }
	     if(value==0){
	     	alert('Select Atleast One Display Fields Filter');
	    	 return false;
	   }		
	   var transid=document.getElementById("reportsform").elements["reportsData.transactionID"].value;
	   transid=transid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	   document.getElementById("reportsform").elements["reportsData.transactionID"].value=transid;
	   
	   var orderno=document.getElementById("reportsform").elements["reportsData.orderNumber"].value;
	   orderno=orderno.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	   document.getElementById("reportsform").elements["reportsData.orderNumber"].value=orderno;
	   
	   var attname=document.getElementById("reportsform").elements["reportsData.attendeeName"].value;
	   attname=attname.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	  document.getElementById("reportsform").elements["reportsData.attendeeName"].value=attname;
		reportsFromSubmit('accountreports!getTransactionInfo?rtype=transaction','tranreportcontent');
	}
}


function reportsFromSubmit(action,contentblock){
	var loadhtml='<center><img src="../images/ajax-loader.gif"></center>';
	$('.showatt').hide();
$('.loadingbar').html(loadhtml);
   	document.reportsform.action = action;
		 $.ajax( {
 	   url: action,
 	   data:$('#reportsform').serialize(),
 	   success: function( t ) {
 	  			        var result=t;
 	  			      
				        if(!isValidActionMessage(result)) return;				        
				        if(result==''){						       		        
					   if($("#accounterrormsg"))
					   $("#accounterrormsg").hide();					 
					  return ;
			         }else if(result.indexOf('errorMessage')>-1){
			          if(!isValidActionMessage(result.trim())) return; 
			          $('.loadingbar').html('');
			          $('.showatt').show();
				          $("#accounterrormsg").show();				        
				           window.scrollTo(0,0);
				          $("#accounterrormsg").html(result);		
				          return;
				        }
				        getAttendeeTable(result,contentblock);
				      
  		}
	});
	
    
}

var shortname = [];
function getAttendeeTable(data,contentblock){
var json = eval('('+data+')');
data = json;
var content = '';
var params = [];
shortname = [];
$('.showatt').show();
$.each(data,function(outkey,resObj){	
	
//alert(attreports.length);
	try{
		
			 if(outkey=="coldefs" && data['attreports'].length>0){
				 content+="<table><tr><td>Export To: <a href='javascript:reportexport(\"excel\")'>Excel</a>&nbsp;|&nbsp;<a href='javascript:reportexport(\"csv\")'>CSV</a></td></tr></table>";
				 content+='<table class="table" id="attendeedata" border="1" bordercolor="lightgrey"> ';
				// alert(resObj.length);
			 	  content+='<thead>';	
					$.each( resObj, function( inkey, valueobj ) {	
						if(valueobj.label=='EID')return;
						var mul = 10*valueobj.label.length;
						 content+= '<th><div style="width:'+mul+'px;">'+valueobj.label+'</div></th>';	
						 shortname.push(valueobj.key);						
							 params.push('null');
						
						// 
					});		
					
					  content+='</thead><tbody>';	
			 }
		
	    }catch(Er){console.log("coldefs log:::"+Er);}
    try{		 
			 if(outkey=='attreports')	{					 	
				 	if(resObj.length>0){				 		
					  $.each( resObj, function( inkey1, valueobj1 ) {	
					  content+='<tr>';	
					  //document.getElementById('attendeetid').checked="checked";
					  $('.tid').iCheck('check');
							if(contentblock=='attreportcontent'){
					  			  $.each( data.fields, function( index, value ) {
					  			  var temp = valueobj1[value+''];
					  			  if(value=='EID')return;
					  			  var tid=valueobj1.TID;
					  			  var eid=valueobj1.EID;
					  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
					  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
					  			  			if(data.glbres[temp+'']!=undefined)	
					  			  			content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+data.glbres[temp+'']+'</a></td>';
					  			  			else if(temp!=undefined)				  			  
					  			  			content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+temp+'</a></td>';
					  			  			else 
					  			  				content+='<td></td>';
					  			  				
					  			  });	
					  			  
					  		}else if(contentblock=='tranreportcontent'){
					  			//	alert(shortname);
					  			 $.each( shortname, function( index, value ) {
					  				 var temp1 = valueobj1[value+''];
					  				 var tid=valueobj1.TID;
						  			  var eid=valueobj1.EID;
						  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
						  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
				  			  			if(data.glbres[temp1+'']!=undefined)	
				  			  			content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+data.glbres[temp1+'']+'</a></td>';
				  			  		else if(temp1!=undefined)	 				  			  
				  			  			content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+temp1+'</a></td>';
				  			  	else 
		  			  				content+='<td></td>';
					  				});	
					  		}
					  			  content+='</tr>';
					  	});
				 	}else {
				 		
				 		content+='<div style="text-align:center"><b>None Found</b></div>';
				 		
				 	}
					  	
					  }  
			}catch(Er){console.log("attreports log::::"+Er);}		 
		 
	});
	 content+='</tbody></table>';
	
	 //console.log(content);
	 if(contentblock=='tranreportcontent')
		$('#tranreportcontent').html(content);
	else
		$('#attreportcontent').html(content);
	 sortTable(params);

}




function getEvents(){
document.getElementById('chked').value='dates';
document.getElementById('getevents').action='accountreports!getEvents';
document.getevents.submit();
}

function displayAllEvents(){
document.getElementById('from').value='';
document.getElementById('to').value='';
document.getElementById('chked').value='all';
document.getElementById('getevents').action='accountreports!getEvents';
document.getevents.submit();
}


function isValidActionMessage(messageText){

if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
    	alert('You need to login to perform this action');
    	window.location.href="../user/login";
    	return false;
    }
 else if(messageText.indexOf('Something went wrong')>-1){
 		alert('Sorry! You do not have permission to perform this action');
 		YAHOO.ebee.popup.wait.hide();
 		return false;
 	} 
    return true;

}

function removeHiddenEle(){
	var a=document.getElementById("exportreport");
		if(a) document.reportsform.removeChild(a);
	var b=document.getElementById("sortdir");
		if(b) document.reportsform.removeChild(b);
	var c=document.getElementById("sortfield");
		if(c) document.reportsform.removeChild(c);
	var d=document.getElementById("exptyp");
		if(d) document.reportsform.removeChild(d);
}

function reportexport(exporttype){
	
	/*var d=ab.get('sortedBy');
	var dir;
	var key;
	if(d==null){
	dir="desc";
	key=firstfield;
	}else{
	dir=d.dir;
	if(dir.indexOf("desc")>-1){
		dir="desc";
		key=d.key;
		}
	else if(dir.indexOf("asc")>-1)
		{dir="asc";
		key=d.key;
		}
	}*/
	var dir="asc";
	var colno=JSON.stringify((dTable.fnSettings().aaSorting[0])[0]);
	var dirtype=JSON.stringify((dTable.fnSettings().aaSorting[0])[1]);
	if(dirtype.indexOf('desc')>-1)
	dir="desc";
	var sortfield=shortname[colno];
	var formele=document.reportsform;
	if(document.getElementById("reportsattradio").checked){
	    formele.action="accountreports!getAttendeeInfo";
	}
	if(document.getElementById("reportstrnsradio").checked){
		formele.action="accountreports!getTransactionInfo";
	}
	removeHiddenEle();
	var input=document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","export");
	input.setAttribute("id","exportreport");
	input.setAttribute("value","true");
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



var dTable;

function sortTable(params){	 
	//alert(params);
    dTable=$('#attendeedata').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    "aoColumns": params,
        "bFilter": false                  
});
}
/*
var data={"currencySymbol":"",
"coldefs":[{"sortable":"true","label":"Transaction Date","key":"TD"},
{"sortable":"true","label":"Transaction ID","key":"TID"},
{"sortable":"true","label":"First Name","key":"FN"},
{"sortable":"true","label":"Last Name","key":"LN"},
{"sortable":"true","label":"Event Name","key":"EN"},
{"label":"Ticket Name","key":"TN"},{"label":"Service Fee","key":"SF"},
{"label":"Discount","key":"Di"},{"label":"Tickets Count","key":"TC"},
{"label":"CC Processing Fee","key":"CCPF"},{"label":"Ticket Price","key":"TP"},
{"label":"Total Ticket Price","key":"TTC"},{"label":"Eventbee Service Fee","key":"ESF"},
{"label":"Eventbee CC Processing Fee","key":"ECCF"},{"label":"Network Ticket Selling Commission","key":"NTSC"},
{"label":"Net Ticket Price","key":"TNet"},{"label":"Collected Service Fee","key":"CSF"},{"label":"Balance","key":"Bal"},
{"sortable":"true","label":"Payment Type","key":"PT"},{"sortable":"true","label":"EID","key":"EID"}],
"glbres":{},
"attreports":[{"TTC":"$20.00","Due":"$1.50","TD":"2012/11/03","Bal":"-1.50","ESF":"$1.50","VTSC":"$0.00","LN":"t","TN":"First Tkt","So":"Main URL","TP":"$20.00","ON":"10000200","CSF":"$0.00","OCCF":"0.00","FN":"srinivas","TC":"1","SF":"$0.00","NTSC":"$0.00","EID":"990646314","CCPF":"$0.00","PT":"Manual","EN":"Copy Of Test for normal flow on aug 23","TID":"RKTOCKKPBQ","TNet":"$18.50","ECCF":"0.00","St":"Completed","Di":"$0.00"}],"fields":[{"key":"TD"},{"key":"TID"},{"key":"FN"},{"key":"LN"},{"key":"EN"},{"key":"TN"},{"key":"SF"},{"key":"Di"},{"key":"TC"},{"key":"CCPF"},{"key":"TP"},{"key":"TTC"},{"key":"ESF"},{"key":"ECCF"},{"key":"NTSC"},{"key":"TNet"},{"key":"CSF"},{"key":"Bal"},{"key":"PT"},{"key":"EID"}]}



*/