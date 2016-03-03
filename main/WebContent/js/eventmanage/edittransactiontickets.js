function getAmount(amount){
	var tamount=""+amount;
		if(tamount.indexOf(".",0)>-1){
			var l=tamount.length-1;
			var k=tamount.indexOf(".",0);
			if((l-k)==1){
			tamount=tamount+"0";
			}
			
		}else{
		tamount=tamount+".00";
	}

	return tamount;
	}
function getcurrencyAmount(amount){
	var tamount=""+amount;
		if(tamount.indexOf(".",0)>-1){
			var l=tamount.length-1;
			var k=tamount.indexOf(".",0);
			if((l-k)==1){
			tamount=tamount+"0";
			}
			if(l-k> 2){
				//tamount=tamount.substr(0,k+3);
				tamount=Math.round(tamount*100)/100
			}
			
		}else{
		tamount=tamount+".00";
	}

	return tamount;
	}


function RemoveDecimals(amount){
	var tempamount=""+amount;
	var idx=tempamount.indexOf(".",0);
	if(idx<0){	
		}
	else{
		tempamount=tempamount.substr(0,idx);
		}
	return tempamount;
	}
function netcalculation(totalamt,totdiscount){
	document.getElementById('totalamount1').value=getAmount(totalamt);
	document.getElementById('totalamount').innerHTML=numberWithCommas(getAmount(totalamt));
	document.getElementById('totaldiscount1').value=getAmount(totdiscount);
	document.getElementById('totaldiscount').innerHTML=numberWithCommas(getAmount(totdiscount));
	var tax=document.getElementById('tax').value;

	document.getElementById('tax').value=getAmount(tax);
	if(tax<0){
	document.getElementById('tax').value="0.00";
	}
	
	var net=Number(totalamt)+Number(tax);
	//net =getAmount(net);
	document.getElementById('net').innerHTML=numberWithCommas(getcurrencyAmount(net));

	}
function calculateamount(index,totalindex){
	if(document.getElementById('error'))
		document.getElementById('error').innerHTML="";
	var tprice=document.getElementById('ticketprices'+index).value;
	var discount=document.getElementById('discount'+index).value;
	document.getElementById('ticketprices'+index).value=getAmount(tprice);
	document.getElementById('discount'+index).value=getAmount(discount);
    var qtyoriginal=document.getElementById('qtyoriginal'+index).value;
    var qty=document.getElementById('qty'+index).value;
	if(qty<qtyoriginal){
	  //alert("If you want to reduce the ticket quantity to delete attendee(s), you can use individual delete attendee feature in Atttendee Details"); 
	  bootbox.alert(props.ed_reduce_tckt_quant_lbl);
		document.getElementById('qty'+index).value=qtyoriginal;
	}else{
	var tqty=RemoveDecimals(qty);
	document.getElementById('qty'+index).value=tqty;
	qty=document.getElementById('qty'+index).value;
	}
	if(qty<0)
		{
			document.getElementById('qty'+index).value=qtyoriginal;
		}
	if(discount<0)
		{
			document.getElementById('discount'+index).value="0.00";
		}
	discount=document.getElementById('discount'+index).value;
	qty=document.getElementById('qty'+index).value;

	if((isNaN(discount))||((isNaN(qty))) || (discount<0) || (qty<0))
		{
			document.getElementById('error').innerHTML='<font color="red">'+props.ed_enter_vald_value_lbl+'</font>';
			return false;
		}
	
	else
		{
			total=(tprice*qty)-discount;	
			document.getElementById('totalval'+index).value=getAmount(total);
			if(total<0)
			{
				document.getElementById('totalval'+index).value=getAmount(0);
				document.getElementById('discount'+index).value=getAmount(0);


			}
			var totalamt=0;
			var totdiscount=0;
			var i;
			for (i=1;i<=totalindex;i++){
				var discount1=Number(document.getElementById('discount'+i).value);
				var totalval1=Number(document.getElementById('totalval'+i).value);
				totalamt=Number(totalamt)+Number(totalval1);
				totdiscount=Number(totdiscount)+Number(discount1);
			}
			netcalculation(totalamt,totdiscount);
			

		}

	}
	function changedonationtotal(index,totalindex){
		var total=Number(document.getElementById('totalval'+index).value);
		(document.getElementById('ticketprices'+index).value)=total;
		
		if(total>0)
			document.getElementById('qty'+index).value=1;
		else
			document.getElementById('qty'+index).value=0;
		
		changetotal(index,totalindex);
		
	}
function changetotal(index,totalindex){
	if(document.getElementById('error'))
	document.getElementById('error').innerHTML="";
	var totalamt=0;
	var totdiscount=0;
	var i;
	for (i=1;i<=totalindex;i++){
	var discount1=Number(document.getElementById('discount'+i).value);
	var totalval1=Number(document.getElementById('totalval'+i).value);
	if(totalval1<0){
	document.getElementById('totalval'+i).value=getAmount(0);
	}else{
	document.getElementById('totalval'+i).value=getAmount(totalval1);
	}
	totalval1=Number(document.getElementById('totalval'+i).value);
	totalamt=Number(totalamt)+Number(totalval1);
	totdiscount=Number(totdiscount)+Number(discount1);
	}

	netcalculation(totalamt,totdiscount);


	}
function changetax1()
{
var tax=document.getElementById('tax').value;

document.getElementById('tax').value=getAmount(tax);
if(tax<0){
document.getElementById('tax').value="0.00";
}
netcalculation(document.getElementById('totalamount').innerHTML,document.getElementById('totaldiscount').innerHTML);
}
var n=0;
function finalSubmit(eventid,totindex,transactionid){
	document.getElementById('buttons').style.display="none";
	document.getElementById('error').style.display="none";
	document.getElementById('processingbar').style.display="block";
	document.getElementById('processingbar').innerHTML="<center>"+props.rep_procsng_lbl+"<br><img src='../images/ajax-loader.gif'></center>";
	var index;
	var newqty="",totalqty=0,totaloriginalqty=0,remqty=''; 
	for (index=1;index<=totindex;index++){
	var discount=document.getElementById('discount'+index).value;
	var originalqty=document.getElementById('qtyoriginal'+index).value;
	totaloriginalqty=Number(totaloriginalqty)+Number(originalqty);
	var qty=document.getElementById('qty'+index).value;
	totalqty=Number(totalqty)+Number(qty);
	var totalval=document.getElementById('totalval'+index).value;
    if((isNaN(discount))||((isNaN(qty))) || (discount<0) || (qty<0) || (totalval<0))
	{
    document.getElementById('error').style.display="block";
	document.getElementById('error').innerHTML=props.ed_enter_vald_value_lbl;
	document.getElementById('buttons').style.display="block";
	document.getElementById('processingbar').style.display="none";
	return false;
	} 	
	}
    var flag=true;
	for (index=1;index<=totindex;index++){
	qty=document.getElementById('qty'+index).value;
	if(qty>0){
	flag=true;
	break;
	}
	else {
	flag=false;
	}
	}
	if(!flag){
	//alert("Quantities for all tickets can not be zero. To totally remove all tickets, use Delete Transaction feature");
		bootbox.alert(props.ed_quant_all_tckt_lbl);
	return false;

	}
	
	var totalamt=(document.getElementById('totalamount').innerHTML).replace(",","");
    
    var totdiscount=(document.getElementById('totaldiscount').innerHTML).replace(",","");
	
	if((isNaN(totalamt))||((isNaN(totdiscount))) || (totalamt<0) || (totdiscount<0))
	{		 	
	//	alert("in conditation");
	document.getElementById('error').innerHTML=props.ed_valid_amnt_lbl;
	return false;
	} 
	if(document.getElementById('error'))
	document.getElementById('error').innerHTML="";
	var tax=document.getElementById('tax').value;
	var net=Number(totalamt)+Number(tax);
	//document.getElementById('net').innerHTML=getAmount(net);
	document.getElementById('net').innerHTML=getcurrencyAmount(net);
	remqty=Number(totalqty)-Number(totaloriginalqty);
	document.getElementById('totalamount1').value=getAmount(totalamt);
	document.getElementById('totaldiscount1').value=getAmount(totdiscount);
	document.getElementById('totalnetamount').value=getcurrencyAmount(net);
	document.getElementById('remqty').value=remqty;
	++n;
	if(n==1){
	//loadingPopup();
	/*$('ticketedit').request({		
	//document.getElementById('ticketedit').submit({	
		  onSuccess: function(transport) {
			var result=transport.responseText;			
			if(result.indexOf("Transaction Tickets Updated")>-1){
				//document.getElementById('statusMessageBox').style.display='block';
				//document.getElementById('EditTransactionTicketsStatusMsg').innerHTML='Transaction Tickets Updated';
				window.location.href='TransactionDetails!getTransactionInfo?eid='+eventid+'&tid='+transactionid+'&purpose=edittran';
			}
			//window.location.href="TransactionDetails?eid="+eventid;
	      },onFailure: function(err){
	       n=0;
	       document.getElementById('buttons').style.display="block"
	       document.getElementById('processingbar').style.display="none";
	       alert(err.status);
	      }
	});	*/
		
		$.ajax({
			type:'POST',
			data:$('#ticketedit').serialize(),
			url:'EditTransactionTickets!updateTickets',
			success:function(result){
				if(!isValidActionMessage(result)) return;
				if(result.indexOf("Transaction Tickets Updated")>-1){
					 $('#tktdetailsdata').hide("slow");
				//window.location.href='TransactionDetails!getTransactionInfo?eid='+eventid+'&tid='+transactionid+'&purpose=edittran';
					//showDetails(transactionid);
					 /*if(element)
						 element.trigger('click');*/
					 
					// $('#statusMessageBox').show().html('<div class="col-md-12"><div style="" class="alert alert-success alert-dismissable page-alert"><button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Status updated successfully.</div></div>');
					notification(props.ed_tckt_detls_updtd_lbl,'success');
					 //$('html, body').animate({ scrollTop: $("#ticketedit").height()}, 1000);
					 document.getElementById('buttons').style.display="block";
					 document.getElementById('processingbar').style.display="none"; 
						n=0;
					 
				}
				},
			error:function(){
				n=0;
			       document.getElementById('buttons').style.display="block"
			       document.getElementById('processingbar').style.display="none";
			}
		});
		
	}
	}


function updateRSVPProfileData(){
	var url='EditTransactionTickets!rsvpUpdateResponses';
	$('#EditResponseStatusMsg').hide();
	$('#EditResponseStatusMsg').html('');
	$('#loadingimg').show();
	$('#submitBtn').hide();
	$('#closeBtn').hide();
	$.ajax({
		type:'get',
		data:$('#responseedit').serialize(),
		url:url,
		success:function(result){
			if(result.indexOf('errorMessage')>-1){
				$('#EditResponseStatusMsg').show();
				$('#EditResponseStatusMsg').html(result);
				$('#submitBtn').show();
				$('#loadingimg').hide();
				$('#closeBtn').show();
			}else{
				var tid=$('#transid').val();
				element.trigger('click');
				//showDetails(tid);
			}
			}
	});
	

	}
	
  
