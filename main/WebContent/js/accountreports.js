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
if(size==0){
	bootbox.confirm("No events found", function (result) {	   });
	//alert("No events found");
	return;
	}
var val=document.getElementsByName("chekedeventslist");
document.getElementById('chekedlist').value='';
if(val.length==0){
	bootbox.confirm("No selected events", function (result) {	   });
	//alert("No selected events");
	return;
	}
for(i=0;i<val.length;i++){
	      if(val[i].checked)
	         values.push(val[i].value);
}
if(values.length==0){ 
	bootbox.confirm("Select atleast one event", function (result) {	   });
  //alert("Select atleast one event");
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
	    	 bootbox.confirm("Select Atleast One Display Fields Filter", function (result) {	   });
	     	//alert('Select Atleast One Display Fields Filter');
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
				bootbox.confirm("Select Online Regular Tickets or Manual or Volume Tickets Field(s) From Search Filter", function (result) {	   }); 
			//alert('Select Online Regular Tickets or Manual or Volume Tickets Field(s) From Search Filter');
	    	return false;
			}
		}else if(!radTID && !radON){
			if(online || manual){
			}else{
				bootbox.confirm("Select Online or Manual Field(s) From Search Filter", function (result) {	   }); 
				//alert('Select Online or Manual Field(s) From Search Filter');
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
	    	 bootbox.confirm("Select Atleast One Display Fields Filter", function (result) {	   }); 
	     	//alert('Select Atleast One Display Fields Filter');
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
	if(contentblock=='tranreportcontent'){
		$('#accountsalesrep').show();
		$('#accountattendeerep').hide();
	}else{
		$('#accountattendeerep').show();
		$('#accountsalesrep').hide();
	}
	
	
	var loadhtml='<center><img src="../images/ajax-spinner.gif"></center>';
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
	$('.loadingbar').html('');	
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
				content+='<div class="table-responsive">';
				 content+='<table class="table table-hover attendeedataclass stripe" id="attendeedata" border="0" bordercolor="lightgrey" style="margin-bottom: 0 !important;"> ';
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
					
					  content+='</thead><tbody id="allreportstable">';	
			 }
		
	    }catch(Er){console.log("coldefs log:::"+Er);}
    try{		 
			 if(outkey=='attreports')	{					 	
				 	if(resObj.length>0){				 		
					  $.each( resObj, function( inkey1, valueobj1 ) {	
					  content+='<tr>';	
					  //document.getElementById('attendeetid').checked="checked";					
							if(contentblock=='attreportcontent'){								
					  			  $.each( data.fields, function( index, value ) {
					  			  var temp = valueobj1[value+''];
					  			  if(value=='EID')return;
					  			  var tid=valueobj1.TID;
					  			  var eid=valueobj1.EID;
					  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
					  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
					  			  			if(data.glbres[temp+'']!=undefined)	
					  			  			//content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+data.glbres[temp+'']+'</a></td>';
					  			  		     content+='<td><a href="javascript:;"  class="clickk '+tid+'" data-tid="'+tid+'"  data-eid="'+eid+'">'+data.glbres[temp+'']+'</a></td>';
					  			  			else if(temp!=undefined)				  			  
					  			  			//content+='<td><a href="/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid+'">'+temp+'</a></td>';
					  			  			content+='<td><a href="javascript:;"  class="clickk '+tid+'" data-tid="'+tid+'"  data-eid="'+eid+'">'+temp+'</a></td>';
					  			  			else 
					  			  				content+='<td></td>';
					  			  				
					  			  });	
					  			  
					  		}else if(contentblock=='tranreportcontent'){
					  			 $.each( shortname, function( index, value ) {
					  				 var temp1 = valueobj1[value+''];
					  				 var tid=valueobj1.TID;
						  			  var eid=valueobj1.EID;
						  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
						  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
				  			  			if(data.glbres[temp1+'']!=undefined)	
				  			  			content+='<td><a href="javascript:;" class="clickk '+tid+'" data-tid="'+tid+'"  data-eid="'+eid+'">'+data.glbres[temp1+'']+'</a></td>';
				  			  		else if(temp1!=undefined)	 				  			  
				  			  			content+='<td><a href="javascript:;" class="clickk '+tid+'" data-tid="'+tid+'"  data-eid="'+eid+'">'+temp1+'</a></td>';
				  			  	    else 
		  			  				content+='<td></td>';
					  				});	
					  		}
					  			  content+='</tr>';
					  	});
				 	}else {				 		
				 		content+='<div style="text-align:center;margin-right:-27px;"><b>None Found</b></div>';				 		
				 	}					  	
					  }  
			}catch(Er){console.log("attreports log::::"+Er);}		 
		 
	});
	 content+='</tbody></table></div>';
	
	 //console.log(content);
	 if(contentblock=='tranreportcontent')
		$('#tranreportcontent').html(content);
	else
		$('#attreportcontent').html(content);
	 sortTable(params);

}




function getEvents(){
document.getElementById('chked').value='dates';
/*document.getElementById('getevents').action='accountreports!getEvents';
document.getevents.submit();*/
loadingPopup();
url='accountreports!getEvents';
$.ajax({
	type:"GET",
	url: url,
	data : $('#getevents').serialize(),
	success: function( result ) {
		hidePopup();
		if(!isValidActionMessage(result)) return;
		
		fillallevents(JSON.parse(result));
		
}
});


}

function displayAllEvents(){
document.getElementById('from').value='';
document.getElementById('to').value='';
document.getElementById('chked').value='all';
/*document.getElementById('getevents').action='accountreports!getEvents';
document.getevents.submit();*/

loadingPopup();
url='accountreports!getEvents';
$.ajax({
	type:"GET",
	url: url,
	data : $('#getevents').serialize(),
	success: function( result ) {
		hidePopup();
		if(!isValidActionMessage(result)) return;
		
		fillallevents(JSON.parse(result));
		
		}
});
}


function fillallevents(events)
{
	var html="";
	$.each(events,function(key,Obj){
		for (var key in Obj)
			html+='<input class="allevtchk" type="checkbox" id="'+key+'"   value="'+key+'"   name="chekedeventslist"> &nbsp;&nbsp;'+Obj[key]+'</input><br/>' ;
	})
	$('#allevtchckbox').html(html);


$('input.allevtchk').iCheck({
    checkboxClass: 'icheckbox_square-grey',
    radioClass: 'iradio_square-grey'
});

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

/*function sortTable(params){	 
	//alert(params);
    dTable=$('#attendeedata').dataTable( {
    "sPaginationType": "full_numbers",
    "iDisplayLength":5,
    "bLengthChange": false,
    "aoColumns": params,
        "bFilter": false                  
});
}*/

function sortTable(params){	 
	var rowCount = $('#allreportstable tr').length;
	//alert(params);
    dTable=$('#attendeedata').dataTable( {
    "sPaginationType": "full_numbers",
    "scrollX": true,
    "iDisplayLength":10,
    "bLengthChange": false, 
        "bFilter": false                  
});
    
    if(rowCount <= 10)
		removePagination('attendeedata');
}

$(document).on('click', '.closeTransactionDetails', function() {
    divOpen = false;
    $(this).parents('tr').prev().removeClass('info');
    $(this).parents('tr').remove();
});


function updateNotes(){
	$.ajax({
		type:'get',
		data:$('#notesform').serialize(),
		url:'/main/eventmanage/TransactionDetails!saveNotes',
		success:function(result){
			if(!isValidActionMessage(result)) return;
			var tid=$('#transactionid').val();
			showDetails(tid);
			}
	});
}



function addNotes(eid,tid){
	/*var url='TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Notes');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');*/
	
	$("div.loadedData").remove();	
	var url='/main/eventmanage/TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#notesData').append(temp);
	  $('#filledData').load(url,function(){
		 $('.imgload').hide();
		 $('#filledData').show("slide", {direction: "top" },"slow");
    	 });
}





var divOpen = false;
$(document).ready(function(){

$(document).on( "click",".clickk",function(e) {
	$('.closeTransactionDetails').trigger("click");
	divOpen=false;
	loadingPopup();
	var tidObj;
	var chkboxlen;
	var eid=$(this).data('eid');
	var actionType='attendee';
	if(actionType=='attendee'){
		chkboxlen=$('input[name="attFields"]:checked').length;
	}else if(actionType=='registration'){
		chkboxlen=$('input[name="Fields"]:checked').length;
	}else{
		chkboxlen=$('input[name="checkinFields"]:checked').length;
	}
	
	var isrecurring=$('#isrecur').val();
	var powertype='Tickeing';
	var paymenttype='${paymentType}';
	
	var tid=$(this).attr('data-tid');
	var url='/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid;
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		success:function(result){
			if(!isValidActionMessage(result)) return;
		   tidObj=JSON.parse(result);
		}
	});
	
	var len=$.map(tidObj.transdetails, function(n, i) { return i; }).length;
	var tktlen=$.map(tidObj.tktdetails, function(n, i) { return i; }).length;
	var buyerlen=$.map(tidObj.buyerdetails, function(n, i) { return i; }).length;
	var attlen=$.map(tidObj.attendeedetails, function(n, i) { return i; }).length;
	var paymentstatus=tidObj.transdetails.paymentstatus;
	var bookingdomain=tidObj.transdetails.bookingdomain;
	var refund=tidObj.transdetails.refund;
	var profilecount=tidObj.profilecount;
	var currencySymbol=tidObj.currencySymbol;
	var currentTransactionDate=tidObj.currentTransactionDate;
	e.preventDefault();
    if (divOpen) return;
    chkboxlen=2;
    var html = '<tr class="well">' +
        '<td colspan="6" >' +
        '<div >' +
        '<span class="pull-right">' +
        '<button class="btn btn-active btn-xs closeTransactionDetails">' +
        '<i class="fa fa-times"></i>' +
        '</button>' +
        '</span>' ;
       if(tidObj!=undefined && len>0){

    	  
           	html+= '<h4 class="section-header">Transaction Info</h4><hr>';
          
        if(powertype!='RSVP' && isrecurring=='true')
       html+='Event Date: '+tidObj.transdetails.eventdate;
      
        html+= '<span class="pull-right">';

        if(paymentstatus=='Cancelled' && bookingdomain!='VBEE'){
        	html+='[<a href="javascript:;" onclick="reactivatetransaction(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Undelete Transaction</a>]<br/><br/>';
            }else{
        if(powertype!='RSVP'){
        	if(paymentstatus!='Deleted')
        html+= '[<a href="javascript:;" id="resend_'+tid+'" onclick="resendMail(\''+eid+'\',\''+tid+'\')">Resend email</a>]<br/><br/>' ;
        }else{
        	if(bookingdomain!='VBEE')
        		html+='[<a href="javascript:;" onclick="deletersvptransaction(\''+eid+'\',\''+tid+'\')">Delete Transaction</a>]';
        html+='[<a href="javascript:;" id="resend_'+tid+'" onclick="resendMail(\''+eid+'\',\''+tid+'\')">Resend email</a>]<br/><br/>';	
        }
            }
        html+='</span>';
        
        html+='<table class="table table-responsive">' +
        '<thead>' +
        '<th>Order Number</th>' +
        '<th>Transaction ID</th>' +
        '<th>Purchase Date & Time (EST)</th>' +
        '<th>Transaction Source</th>' +
        '<th>Transaction Status</th>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<td>'+tidObj.transdetails.orderNumber+'</td>' +
        '<td>'+tid+'</td>' +
        '<td>'+tidObj.transdetails.transaction_date+'</td>' +
        '<td>'+tidObj.transdetails.bookingsource+'</td>' +
        '<td>'+tidObj.transdetails.paymentstatus;
       

        if(powertype!='RSVP' && bookingdomain!='VBEE' && (paymentstatus=='Completed' || paymentstatus=='Pending Approval')){
        html+='&nbsp;[<a href="javascript:;" onclick="changeStatus(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Change Status</a>]' ;
        }

        html+='</td></tr></tbody></table>'; 

       html+='<div>&nbsp;&nbsp; <b>Notes </b>[<a href="javascript:;" onclick="addNotes(\''+eid+'\',\''+tid+'\');">Edit</a>]&nbsp;&nbsp;&nbsp;'+tidObj.notes+'</div><br/>';
       html+='<div id="notesData"></div>'; 
        if(powertype!='RSVP'){
        html+='<h4 class="section-header">Payment Details</h4>';
        if(powertype!='RSVP' && bookingdomain!='VBEE' && refund=='Yes' && paymentstatus=='Completed')
        	html+='<span class="pull-right">[<a href="javascript:;" onclick="refund(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Refund</a>]</span>' ;
        
        
        html+='<hr><table class="table table-responsive">'+
        '<thead><th>Discount Code</th>'+
        '<th>Total Discount ('+currencySymbol+')</th>' +
        '<th>Credit Card Processing Fee/Tax ('+currencySymbol+')</th>' +
        '<th>Total Amount ('+currencySymbol+')</th>' +
        '<th>Payment Method</th>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<td>'+tidObj.transdetails.discountcode+'</td>' +
        '<td>'+tidObj.transdetails.discount+'</td>' +
        '<td>'+tidObj.transdetails.tax+'</td>' +
        '<td>'+tidObj.transdetails.totalAmount+'</td>' +
        '<td>'+tidObj.transdetails.paymenttype+'</td>' +
        '</tr></tbody></table>' ;


		if(tidObj.transdetails.refund_amt>0){                
        html+='<hr><table><tr>';
        html+='<td><b>Refunded Amount&nbsp; ('+currencySymbol+')</b></tr><tr>';
        html+='<td><div style="height: 8px;"></div>'+tidObj.transdetails.refund_amt+'</td></tr></table><br/>';
		}
       }
        
        html+='<div id="tktData">';
        if(powertype!='RSVP'){
        	
        	if(tktlen!=undefined && tktlen>0){
        		html+='<h4 class="section-header">Ticket Details</h4><hr>' ;

           if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' )
                   html+='<span class="pull-right">[<a href="javascript:;" onclick="ticketdetails(\''+eid+'\',\''+tid+'\')";>Edit</a>]</span>' ;
                
                html+='<table class="table table-responsive">'+
                '<thead><th>Ticket Name</th>'+
                '<th>Price ('+currencySymbol+')</th>' +
                '<th>Count</th>' +
                '<th>Discount ('+currencySymbol+')</th>' +
                '<th>Total ('+currencySymbol+')</th>' +
                '</thead>' +
                '<tbody>' ;
                
                for(var i=0;i<tktlen;i++){
                html+='<tr>' +
                '<td>'+tidObj.tktdetails[i].ticketName+'</td>' +
                '<td>'+tidObj.tktdetails[i].ticketprice+'</td>' +
                '<td>'+tidObj.tktdetails[i].qty+'</td>' +
                '<td>'+tidObj.tktdetails[i].discount+'</td>' +
                '<td>'+tidObj.tktdetails[i].ticketstotal+'</td>' +
                '</tr>' ;
                }
                html+='<tr><td colspan="5">';
                html+='<div id="ticketDetailsData"></div></td></tr>';
                html+='</tbody>'+ 
                '</table>';
        	}

        }else{

        	if(tktlen!=undefined && tktlen>0){
        		html+='<h4 class="section-header">Response Details</h4><hr>' ;
        		if(isrecurring=='true'){
            		html+='<form action="TransactionDetails!changeDate" id="transactiondateform" name="transactiondateform" method="post" theme="simple">';
            		html+='<span><span id="rsvprecurring">For Event Date : '+tidObj.currentTransactionDate+'</span></br><br/>';
            		html+='ChangeDate <select id="source" name="reportsData.date">';
            		html+='<option selected="selected" value="">--Select Date--</option>';
            		for(var i=0;i<tidObj.recurdates.length;i++)
            			html+='<option value="'+tidObj.recurdates[i]+'">'+tidObj.recurdates[i]+'</option>';
            		html+='<input type="hidden" name="eid" value="'+eid+'" /><input type="hidden" name="tid" value="'+tid+'" />';
            		html+='</select>&nbsp;<input type="button" id="rsvprecursubmit" value="Submit" class="btn btn-primary btn-xs"/></span></form>';
            		}

        		if(tidObj.response1=="Y" && bookingdomain!='VBEE'){
        			 html+='<span class="pull-right">[<a href="javascript:;" onclick="changeToNotAttending(\''+eid+'\',\''+tid+'\')">Change To Not Attending</a> |'+
            			  '<a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">Add More Attendees</a>]</span>'; 
            		}else if(bookingdomain!='VBEE'){
                		html+='<span class="pull-right">[<a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">Change To Attending</a>]</span>'
                		}

        		html+='<br/><table class="table table-responsive">'+
                '<thead><th>Response Type</th>'+
                '<th>Count</th>'+
                 '</thead>' +
                '<tbody>' ;
        		for(var i=0;i<tktlen;i++){
                    html+='<tr>' +
                    '<td>'+tidObj.tktdetails[i].ticketName+'</td>' +
                    '<td>'+tidObj.tktdetails[i].qty+'</td>' +
                    '</tr>' ;
                    }
                    html+='</tbody>'+ 
                    '</table>';
                    
            	}
            }
        html+='</div>';

        html+='<div id="buyerData">';
        if(tidObj.buyerdetails!=null && tidObj.buyerdetails!=undefined && buyerlen>0){
        	html+='<h4 class="section-header">Registrant Details</h4>' +
            '<table class="table table-responsive" id="'+tidObj.buyerdetails.profilekey+'">';
            html+='<tr><td><span><b>Profile&nbsp;</b></span>';
           
            if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' )
            html+='[<a href="javascript:;" onclick="profilefunc(\''+eid+'\',\''+tid+'\',\''+tidObj.buyerdetails.profilekey+'\');">Edit</a>]';
            html+='</td></tr>';
            if(tidObj.buyerdetails.fname!='')
            html+='<tr><td>First Name : '+tidObj.buyerdetails.fname+'</td></tr>';
            if(tidObj.buyerdetails.lname!='')
            html+='<tr><td>Last Name : '+tidObj.buyerdetails.lname+'</td></tr>';
            if(tidObj.buyerdetails.email!='')
            html+='<tr><td>Email : '+tidObj.buyerdetails.email+'</td></tr>';
            if(tidObj.buyerdetails.phone!='')
            html+='<tr><td>Phone : '+tidObj.buyerdetails.phone+'</td></tr>';

            var buyerkey=tidObj.buyerdetails.profilekey;
            var customattribs=tidObj.attribmap[buyerkey];
            if(customattribs!=undefined && customattribs!='undefined' && customattribs.length>0 ){
            for(var att=0;att<customattribs.length;att++){
            	if(customattribs[att]['response']!='')
               html+='<tr><td>'+customattribs[att]['question']+' : '+customattribs[att]['response']+'</td></tr>';
                }
            }
            html+='</table><table class="table table-responsive" id="edit_'+tidObj.buyerdetails.profilekey+'" style="display:none;">';
            html+='<tr><td>';
            html+='<div id="buyerProfileData"></div></td></tr>';
            html+='</table>';
        
        }
        html+='</div>';
       /*  html+='<div id="buyerloading" style="display:none;"><center><img src="../images/ajax-loader.gif"></center></div>';
        html+='<div id="buyerdetailsdata" class="profiles" style="display:none;"></div><br/>'; */
        
        if(attlen>0){
        	html+='<h4 class="section-header">Attendee Details</h4>' ;
            
        
            for(var i=0;i<tktlen;i++){
            	try{
            		var tktdata=tidObj.tktdetails[i];
                	var tktid=tktdata.tktnumber;
                	var tktname=tktdata.ticketName;
                	var attendeedetailsObj=tidObj.attendeedetails; 
                	var tktattendeedetails=attendeedetailsObj[tktid];
                	for(var att=0;att<tktattendeedetails.length;att++){
                		var c=att+1;
                		var eachtktdetails=tktattendeedetails[att];
                		var pk=eachtktdetails.profilekey;
                		html+='<table class="table table-responsive" id="'+pk+'">';
                		if(powertype!='RSVP'){
                			 if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' ){
                      		 html+='<tr><td><span><b>'+tktname+' - Profile   # '+c+'</b></span> [<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit<a/> '+ 
                      		 '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete<a/>]</td></tr>';
                			 }
                		}else{
                               if(tktname!='Not Sure' &&  bookingdomain!='VBEE'){
                            	   html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                            	   html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit</a> '; 
                            	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete</a>';
                            	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotsure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Sure</a>';
                            	   html+='&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';
                                }else if(bookingdomain!='VBEE'){
                                   html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                                   html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit</a> '; 
                              	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="deleteattendeenotes('+eid+','+pk+','+tktid+','+tid+','+profilecount+');">Delete</a>';
                              	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="changeToSure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Sure</a>';
                              	   html+='&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';
                                }
                    		}


                		 if(eachtktdetails.fname!='')
                         html+='<tr><td>First Name : '+eachtktdetails.fname+'</td></tr>';
                         if(eachtktdetails.lname!='')
                         html+='<tr><td>Last Name : '+eachtktdetails.lname+'</td></tr>';
                         if(eachtktdetails.email!='')
                         html+='<tr><td>Email : '+eachtktdetails.email+'</td></tr>';
                         var pkey=eachtktdetails.profilekey;
                         var seatevt=tidObj.seatingevt;
                         
                         if(seatevt=='Y'){
                             if(eachtktdetails.seatcode!=''){
                             html+='<tr><td>Seat Code : '+eachtktdetails.seatcode;
                             if(bookingdomain!='VBEE')   
                             html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'changeseat\')">Change Seat</a>]';
                             html+='</td></tr>';   
                             }else{
                            	 if(paymentstatus!='Deleted' && eachtktdetails.tickettype!='donationType'){
                                	 html+='<tr><td>Seat Code : ';
                                	    if(bookingdomain!='VBEE')
                                	    	 html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'addseat\')">Add Seat</a>]';
                                	    html+='</td></tr>';  
                                	 }
                                 }
                             }
                         
                         
                         
                         var attcustomattribs=tidObj.attribmap[pkey];
                         if(attcustomattribs!=undefined && attcustomattribs!='undefined' && attcustomattribs.length>0 ){
                         for(var atts=0;atts<attcustomattribs.length;atts++){
							if(attcustomattribs[atts]['response']!='')
                            html+='<tr><td>'+attcustomattribs[atts]['question']+' : '+attcustomattribs[atts]['response']+'</td></tr>';
                             }
                         }
                         html+='</table><table class="table table-responsive" id="edit_'+pk+'" style="display:none;">';
                         html+='<tr><td>';
                         html+='<div id="attedeedData_'+pk+'"></div></td></tr><table>';
                	}
                	
                	
            	}catch(err){
            		//alert(err);
            	}
            	
            	
            }
            
        	 html+='</table>';	
        }
       }
        html+='</div>' +
        '</td>' +
        '</tr>';
    $('.closeTransactionDetails').trigger("click");
    var parent = $(this).parents('tr');
    parent.addClass('info');
   
    $("html, body").animate({
        scrollTop: $(parent).offset().top - 60
    }, "slow",function(){
    	 hidePopup();
        });
    $(html).insertAfter(parent);
    divOpen = true;
    $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
});
});


function showDetails(tid){
	// closepopup();
	//hidePopup();
	$('.closeTransactionDetails').trigger("click");
	//alert("in show details method::"+tid);
	loadingPopup();
	$("."+tid).each(function() {
		 $(this).trigger("click",function(){
	 });
	   	 
		 return false;
    });
	 //hidePopup();
}


/*function resendMail(eid,tid){
	var url='/main/eventmanage/TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=Ticketing';
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Mail Success');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
	//var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getMailSuccess,failure:getFailure});
}*/


function resendMail(eid,tid){
	var url='/main/eventmanage/TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=Ticketing';
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$.ajax({
		type:'get',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			openSinglePopUpWithOptions($('#resend_'+tid).offset(),resendsuccess,resendcancel,'','Mail Sent Successfully','');
			$('#onefieldtextoption').hide();
			$('#singlecancel').hide();
			$('#singlesubmit').attr('value','OK');
			}
	});
}

$(document).on('click','#cancelnotes',function(){
	 $("div.loadedData").remove();
});



function changeStatus(eid,tid,paymentType){
	var url='/main/eventmanage/TransactionDetails!changeStatus?eid='+eid+'&tid='+tid+'&payType='+paymentType;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Transaction Status');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	});
	$('#myModal').modal('show');
	    	
}

/*function addNotes(eid,tid){
	var url='/main/eventmanage/TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Notes');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
}*/

function refund(eid,tid,paymentType){
	var url='/main/eventmanage/PaymentsRefund?eid='+eid+'&tid='+tid+'&paymentType='+paymentType;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Refund');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');

}


function ticketdetails(eid,tid){
	$("div.loadedData").remove();	
    $('#tktloading').show();	
	var url='/main/eventmanage/EditTransactionTickets!editTickets?eid='+eid+'&tid='+tid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#tktData').append(temp);
	  $('#filledData').load(url,function(){
		 $('.imgload').hide();
		 $('#filledData').show("slide", {direction: "top" },"slow");
    	 }); 
	}

function profilefunc(eid,tid,pkey){
	$("div.loadedData").remove();
	$('#buyerloading').show();
	var url='/main/eventmanage/EditAttendee!editBuyerInfo?eid='+eid+'&tid='+tid+'&pid='+pkey;
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('#buyerData').append(temp);
    	$('#filledData').load(url,function(){
   		 $('.imgload').hide();
   		$('#filledData').show("slide", {direction: "top" },"slow");
       	 }); 
	}

function editattendeenotes(eventid,profileid,ticketid,transactionid){
	$("div.loadedData").remove();
	var url='/main/eventmanage/EditAttendee?eid='+eventid+'&pid='+profileid+'&ticketid='+ticketid+'&tid='+transactionid;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
	$('#attedeedData_'+profileid).append(temp);
	$('#filledData').load(url,function(){
   		 $('.imgload').hide();
   		$('#filledData').show("slide", {direction: "top" },"slow");
	});
}


function deleteattendeenotes(eventid,profileid,ticketid,transactionid,profilecount){
    var url='/main/eventmanage/EditAttendee!deleteattendeeNotes?eid='+eventid+'&tid='+transactionid+'&ticketid='+ticketid+'&pid='+profileid+'&profilecount='+profilecount;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Delete Attendee Notes');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
}


function refund(eid,tid,paymentType){
	var url='/main/eventmanage/PaymentsRefund?eid='+eid+'&tid='+tid+'&paymentType='+paymentType;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Refund');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');

}

function getSeatingChart(eventid,tranid,pk,edate,seataction){
	if(pk!='')
	profilekey=pk;
	var dynatimestamp = new Date();
	var url='/main/eventmanage/ManageAttendeeSeats!getSeatingChart?eid='+eventid+'&profilekey='+pk+'&tid='+tranid+'&seataction='+seataction;
	url += '&dynatimestamp='+dynatimestamp.getTime();
	if(seataction=='changeseat')
	$('.modal-title').html('Change Seat');
	else
	$('.modal-title').html('Add Seat');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
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