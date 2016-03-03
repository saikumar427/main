<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css"
	href="../build/menu/assets/skins/sam/menu.css" />
<link href="../css/seatingaddmanual.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="../build/menu/menu-min.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="../js/eventmanage/seating/manageattendeeseats.js"></script>
<script>
YAHOO.namespace("notes");   
function reactivatetransaction(eid,tid,paymentType){
	
	window.location.href='TransactionDetails!undeleteTransaction?eid='+eid+'&tid='+tid+'&payType='+paymentType;
}
function deletetransaction(eid,tid){
	window.location.href='TransactionDetails!deleteTransaction?eid='+eid+'&tid='+tid;
}
function deletersvptransaction(eid,tid){
	window.location.href='TransactionDetails!deleteRSVPTransaction?eid='+eid+'&tid='+tid;
}
function resendMail(eid,tid){
	var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType='+'${powertype}';
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getMailSuccess,failure:getFailure});
}
var getMailSuccess = function(responseObj){
    
	var myButtons = [{text: "Ok", handler: handleCancel }];
	
	showPopUpDialogWithCustomButtons(responseObj,"Mail Success",myButtons);
	
}
 var getFailure = function(){
	alert("error");
}
function changeStatus(eid,tid,paymentType){
	var url='TransactionDetails!changeStatus?eid='+eid+'&tid='+tid+'&payType='+paymentType;
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:gettransactionstatus,failure:getFailure});

}

var gettransactionstatus=function(responseObj){
	
	showPopUpDialogWindow(responseObj, "Transaction Status",savetransaction, handleCancel);
	
}
var j=0;
var savetransaction=function(){	
	YAHOO.ebee.popup.wait.show();
	var stat=checkStatus();
	if(stat && j==0){
		++j;
	 //submitFormAndReload('transactionstatusform', 'transactionstatuserrormessages');
	 $('transactionstatusform').request({
        onFailure: function() {
        	j=0;
        	$('transactionstatuserrormessages').update('Failed to get results') },
        onSuccess: function(t) {
             
            var result=t.responseText;
	        if(!isValidActionMessage(result)) return;
	        if(result.indexOf("success")>-1){
	        	var eventid=document.getElementById('eventid').value;
	        	var transactionid=document.getElementById('transactionid').value;
	            YAHOO.ebee.popup.wait.hide();
	        	YAHOO.ebee.popup.dialog.cancel();
	        	window.location.href='/main/eventmanage/TransactionDetails!getTransactionInfo?eid='+eventid+'&tid='+transactionid;
	        } 
	         else {
	        	 j=0;
            	$('transactionstatuserrormessages').update(result);
	        }
        }
    });
     }  
		
}
function refund(eid,tid,paymentType){
	var url='PaymentsRefund?eid='+eid+'&tid='+tid+'&paymentType='+paymentType;
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getRefundScreen,failure:getFailure});

}
var getRefundScreen=function(responseObj){
	showPopUpDialogWindow(responseObj, "Refund",submitRefund, handleCancel);
}
function submitRefund(){
$('refundStatusMsg').hide();
YAHOO.ebee.popup.wait.show();
$('refundform').request({
        onFailure: function() { $('refundform').update('Failed to get results') },
        onSuccess: function(t) {
            var result=t.responseText;
	        if(!isValidActionMessage(result)) return;
	        //YAHOO.ebee.popup.wait.hide();
	        $('refundStatusMsg').update(t.responseText);
	        $('refundStatusMsg').show();
	        if(result.indexOf("third-party access")>-1)
	        	$('paypalhelplink').show();
	        
	        if(result.indexOf("success")>-1){
	        	window.location.href='/main/eventmanage/TransactionDetails!getTransactionInfo?eid=${eid}&tid=${tid}';
	            //YAHOO.ebee.popup.wait.hide();
	        	//YAHOO.ebee.popup.dialog.cancel();
	        }else{YAHOO.ebee.popup.wait.hide();}
        }
    });
}

function checkStatus(){

	var status=document.getElementById("statusType").value;
	if(status==""){
		YAHOO.ebee.popup.wait.hide();
		alert("Please select status.");
		return false;
	}else 
		return true;
}
</script>

<s:set name="transInfo" value="TransInfo" />
<s:set name="tickets" value="ticketsList" />
<s:set name="profilesList" value="ProfilesList" />
<s:set name="buyerInfoMap" value="buyerInfoMap" />
<s:set name='ticketdetails' value="selectedTicketsDetails" />
<s:set name='attribresponse' value="attribMap" />
<jsp:include page="/help/eventmanage_messages.jsp"></jsp:include>
<s:if test="%{purpose==''}">
<div align="right"><a href="javascript:history.back()" ><b>Back</b></a></div>
<div style="height: 3px;"></div></s:if>
<div id="detailsblock">
<s:if test="#transInfo!=null&&#transInfo.size()>0">
<s:set value="%{isrecurring}" name="isrecurring"></s:set>
	<s:set name="paymentType" value="#transInfo['paymenttype']" />
	<div class="taskbox"><span class="taskheader"><s:text name="trans.info.msg.lbl"/></span><br />

	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		
	<s:set value="#transInfo['paymentstatus']" name="paymentstatus"></s:set>
	<tr>
	
	<td align="left"><table width="100%" cellpadding="0" cellspacing="0"><tr>
	<s:if test="powertype!='RSVP' && #isrecurring==true">
	<td><b>Event Date:</b>&nbsp;<s:property value="#transInfo['eventdate']" /></td>
	</s:if>
	<td colspan="5" align="right">
		<s:if test="%{#paymentstatus=='Cancelled' && #transInfo['bookingdomain']!='VBEE'}">
			[<a href='#' onclick='reactivatetransaction("${eid}","${tid}","${paymentType}")'><s:text name="trans.undel.trans.lbl"/></a>]
		</s:if> 
		<s:else>
			<s:if test="powertype!='RSVP'">
				<s:if test="%{#paymentstatus!='Deleted'}">[<a href='#' onclick='resendMail("${eid}","${tid}")' align="left"><s:text name="trans.resnd.mail.lbl"/></a>]</s:if>
           </s:if>
		   <s:else>
 			[<s:if test="%{#transInfo['bookingdomain']!='VBEE'}">
 				<a href='#' onclick='deletersvptransaction("${eid}","${tid}")'><s:text name="trans.del.tran.lbl"/></a>|</s:if>
			<a href='#' onclick='resendMail("${eid}","${tid}")'><s:text name="trans.resnd.mail.lbl"/></a>]
			</s:else>

		</s:else>
	</td>
	
	</tr></table></td>
	
	</tr>
<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<th align='left'><s:text name="rep.ordr.numb.lbl"/></th>
			<th align='left'><s:text name="rep.trs.id.lbl"/></th>
			<th align='left'><s:text name="rep.pur.date.lbl"/>&nbsp;<s:text name="trans.amp.symb.lbl"/>&nbsp;<s:text name="trans.time.lbl"/>&nbsp;<s:text name="trans.est.lbl"/></th>
			<th align='left'><s:text name="trans.tran.sourc.lbl"/></th>
			<th align='left'><s:text name="trans.tran.status.lbl"/></th>
		</tr>
		<tr>
			<td align='left'><s:property value="#transInfo['orderNumber']" /></td>
			<td align='left'><s:property value="#transInfo['tid']" /></td>
			<td align='left'><s:property
				value="#transInfo['transaction_date']" /></td>
			<td align='left'><s:property value="#transInfo['bookingsource']" /></td>
			<td align='left'><s:property value="#transInfo['paymentstatus']" />
			
     <s:if test="powertype!='RSVP' && #transInfo['bookingdomain']!='VBEE' && (#transInfo['paymentstatus']=='Completed' || #transInfo['paymentstatus']=='Pending Approval')"> 

         [<a href="#" onclick='changeStatus("${eid}","${tid}","${paymentType}")'>Change Status</a>]
 </s:if>

 </td>

		<tr>
			<td colspan="5">
			<hr>
			</td>
		</tr>
	
	</table></td></tr></table>
	
<div style="height: 3px;"></div> 
		<table width="100%">
			<tr>
				<td valign='top'><b><s:text name="trans.notes.lbl"/></b> [<a href='#'
					onclick="addNotes();">Edit</a>] <pre>${transactionNotesData.notes}</pre></td>
			</tr>
			
</table>
</div>

	<br />
	<s:if test="powertype!='RSVP'">
		<div class="taskbox"><span class="taskheader"><s:text name="trans.paymnt.details.lbl"/></span><br />
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<s:if test="powertype!='RSVP' && #transInfo['bookingdomain']!='VBEE' && #transInfo['refund']=='Yes' && #transInfo['paymentstatus']=='Completed' "> 
         		<tr><td colspan="5" align='right'>
         		[<a href="#" onclick='refund("${eid}","${tid}","${paymentType}")'><s:text name="trans.ref.lbl"/></a>]
         		</td><tr>
 			</s:if>
			<tr>
				<th align='left'><s:text name="trans.disc.code.lbl"/></th>
				<th align='left'><s:text name="trans.totaL.disc.lbl"/>&nbsp;(${currencySymbol})</th>
				<th align='left'><s:text name="trans.credit.crd.lbl"/>&nbsp;(${currencySymbol})</th>
				<th align='left'><s:text name="trans.totaL.amnt.lbl"/>&nbsp;(${currencySymbol})</th>
				<th align='left'><s:text name="trans.paymnt.mthd.lbl"/></th>
			</tr>
			<tr>
				<td align='left'><s:property value="#transInfo['discountcode']" /></td>
				<td align='left'><s:property value="#transInfo['discount']" /></td>
				<td align='left'><s:property value="#transInfo['tax']" /></td>
				<td align='left'><s:property value="#transInfo['totalAmount']" /></td>
				<td align='left'><s:property value="#transInfo['paymenttype']" /></td>
		</tr>
				<s:if test="#transInfo['refund_amt']>0">
				<tr>
			<td colspan="5"> 
			<hr>
			</td>
		</tr>
		</s:if>
		</table>
		<s:if test="#transInfo['refund_amt']>0">
		<div style="height: 3px;"></div> 
		<table width="100%">
			<tr>
				<td align='left'><b><s:text name="trans.refnd.amnt.lbl"/>&nbsp;(${currencySymbol})</b> </td>
			</tr>
			<tr>
			<td align='left'><s:property value="#transInfo['refund_amt']" /></td>
			</tr>
</table>
</s:if>
		</div>
		<br />
	</s:if>

	<s:if test="powertype!='RSVP'">
		<s:if test="#tickets!=null&&#tickets.size()>0">
			<div class="taskbox"><span class="taskheader"><s:text name="trans.tckt.dtls.lbl"/> </span><br />
			<!-- <a href='EditTransactionTickets?eid=${eid}&tid=${tid}'>Edit</a> -->
			<table border="0" cellpadding="3" cellspacing="0" width="100%">
				<s:set value="#transInfo['paymentstatus']" name="paymentstatus"></s:set>
				<tr>
					<td colspan="5" align="right">
					<s:if test="%{#paymentstatus!='Deleted' && #transInfo['bookingdomain']!='VBEE'}"> [<a href='#' onclick="ticketdetails()";><s:text name="trans.edit.lbl"/></a>]
					</s:if>
					</td>

				</tr>
				<tr>
					<th align='left'><s:text name="trans.tckt.name.lbl"/></th>
					<th align='left'><s:text name="trans.price.lbl"/>&nbsp;(${currencySymbol})</th>
					<th align='left'><s:text name="trans.cnt.lbl"/></th>
					<th align='left'><s:text name="trans.dis.lbl"/>&nbsp;(${currencySymbol})</th>
					<th align='left'><s:text name="trans.total.lbl"/>&nbsp;(${currencySymbol})</th>
				</tr>
				<s:iterator value="#tickets" var="ticketobj">
					<tr>
						<td align='left'><s:property value="#ticketobj['ticketName']" /></td>
						<td align='left'><s:property
							value="#ticketobj['ticketprice']" /></td>
						<td align='left'><s:property value="#ticketobj['qty']" /></td>
						<td align='left'><s:property value="#ticketobj['discount']" /></td>
						<td align='left'><s:property
							value="#ticketobj['ticketstotal']" /></td>
					</tr>
				</s:iterator>
			</table>
			</div>
       
		</s:if>
		<br />
	</s:if>
	<s:else>
		<s:if test="#tickets!=null&&#tickets.size()>0">
			<div class="taskbox"><span class="taskheader"><s:text name="trans.rsp.dtls.lbl"/></span><br />
			<!-- <a href='EditTransactionTickets?eid=${eid}&tid=${tid}'>Edit</a> -->


			<s:if
				test="%{isrecurring==true}">
                <s:form action='TransactionDetails!changeDate'
					id='transactiondateform' name='transactiondateform' method="post"
					theme="simple">
					<table>
						<tr>
							<td><s:text name="trans.event.for.date.lbl"/> <s:property
								value="currentTransactionDate" /></td>
						</tr>
						<tr>
							<td valign='top' class='subheader'><s:text name="trans.chng.date.lbl"/><s:select
								name="reportsData.date" id='source' headerKey=""
								headerValue="--Select Date--" theme="simple" list="dates"
								listKey="key" listValue="value" /></td>

							<td align='center'><input type='hidden' name='eid'
								value='${eid}' /> <input type='hidden' name='tid'
								value='${tid}' /> <input type='submit' name='submit'
								id='transactiondatesubmit' value='Submit' /></td>
						</tr>
						<tr>
					<td>
					<hr width="100%">
					</td>
				</tr>
					</table>

				</s:form>
				
			</s:if>

			<table border="0" cellpadding="3" cellspacing="0" width="100%">
				<tr>

					<td colspan="5" align="right"><s:set
						value="reportsData.responsetype" name="response1"></s:set> <s:if
						test='%{#response1 == "Y" && #transInfo["bookingdomain"]!="VBEE"}'>
                 
                 [<a href='EditTransactionTickets!changeToNotAttending?eid=${eid}&tid=${tid}'><s:text name="trans.chnge.to.nt.attndng.lbl"/></a> | 
                 <a href='EditTransactionTickets!rsvpEditResponses?eid=${eid}&tid=${tid}'><s:text name="trans.add.mre.attndes.lbl"/></a>] 
                </s:if> <s:elseif test="%{#transInfo['bookingdomain']!='VBEE'}"> 
                 [<a href='EditTransactionTickets!rsvpEditResponses?eid=${eid}&tid=${tid}'><s:text name="trans.chng.to.attndng.lbl"/></a>]
                  </s:elseif></td>

				</tr>

				<tr>
					<th align='left' width="40%"><s:text name="trans.resp.type.lbl"/></th>

					<th align='left' width="40%"><s:text name="trans.cnt.lbl"/></th>

				</tr>
				<s:iterator value="#tickets" var="ticketobj">
					<tr>
						<td align='left' width="40%"><s:property
							value="#ticketobj['ticketName']" /></td>

						<td align='left' width="40%"><s:property
							value="#ticketobj['qty']" /></td>


					</tr>
				</s:iterator>
			</table>
			</div>
		</s:if>
		<br />
	</s:else>

	<s:if test="#buyerInfoMap!=null&&#buyerInfoMap.size()>0">
		<div class="taskbox"><span class="taskheader"><s:text name="trans.reg.dtls.lbl"/></span><br />
   
		<table width="100%">
			<tr>
				<td><b>Profile</b> <s:if test="paymentStatus!='Deleted' && #transInfo['bookingdomain']!='VBEE'">[<a href="#" onclick="profilefunc();"><s:text name="trans.edit.lbl"/></a>]</s:if>
				</td>
			</tr>

			<s:set name='fname' value="#buyerInfoMap['fname']" />
			<s:if test="#buyerInfoMap['fname']!=''">
				<tr>
					<td><s:text name="trans.fstnm.lbl"/><s:property value="#buyerInfoMap['fname']" /></td>
				</tr>
			</s:if>
			<s:if test="#buyerInfoMap['lname']!=''">
				<tr>
					<td><s:text name="trans.lastnm.lbl"/><s:property value="#buyerInfoMap['lname']" /></td>
				</tr>
			</s:if>
			<s:if test="#buyerInfoMap['email']!=''">
				<tr>
					<td><s:text name="trans.email.lbl"/><s:property value="#buyerInfoMap['email']" /></td>
				</tr>
			</s:if>
			<s:if test="#buyerInfoMap['phone']!=''">
				<tr>
					<td><s:text name="trans.phn.lbl"/> <s:property value="#buyerInfoMap['phone']" /></td>
				</tr>
			</s:if>
			<s:set name='customList'
				value="#attribresponse[#buyerInfoMap['profilekey']]" />
			<s:if test="#customList.size()>0">
				<s:iterator value="#customList" var='customobj'>
				 <s:if test="#customobj['response']!=''">
					<tr>
						<td>
							<s:property value="#customobj['question']" />: <s:property value="#customobj['response']" />
						</td>
					</tr>
					</s:if>
				</s:iterator>
			</s:if>

		</table>

		</div>
	</s:if>
	<br />
	<s:if test="#profilesList!=null&&#profilesList.size()>0">

		<div class="taskbox"><span class="taskheader"><s:text name="trans.attnde.dtls.lbl"/></span><br />
		<s:iterator value='ticketdetails' var='ticketsobj'>
			<s:set name='ticketid' value="#ticketsobj['selectedTicket']" />

			<s:set name='attendeeList' value="#profilesList[#ticketid]" />

			<s:iterator value='attendeeList' var='attobj' status='stat'>

				<b><s:property value="#ticketsobj['ticketName']" /> <s:text name="trans.prfle.lbl"/><s:property
					value="#stat.count" /></b>
				<s:if test="powertype!='RSVP'">
                 <s:if test="paymentStatus!='Deleted' && #transInfo['bookingdomain']!='VBEE'">
                [<a href="#" onclick="editattendeenotes('${eid}','<s:property value="#attobj['profilekey']"/>','<s:property value="#ticketid"/>','${tid}');"> <s:text name="trans.edit.lbl"/></a>&nbsp;|&nbsp;
						<a href="#" onclick="deleteattendeenotes('${eid}','<s:property value="#attobj['profilekey']"/>','<s:property value="#ticketid"/>','${tid}','${profilecount}');"><s:text name="trans.del.lbl"/></a>]
            </s:if>
            </s:if>
				<s:else>

					<s:if test="#ticketsobj['ticketName']!='Not Sure' && #transInfo['bookingdomain']!='VBEE'">
           [<a href='EditAttendee?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'><s:text name="trans.edit.lbl"/></a>|
            <a href="#" onclick="deleteattendeenotes('${eid}','<s:property value="#attobj['profilekey']"/>','<s:property value="#ticketid"/>','${tid}','${profilecount}');"><s:text name="trans.del.lbl"/></a>&nbsp;|
            <a
							href='EditAttendee!changeToNotsure?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'><s:text name="trans.not.sre.lbl"/>
							</a>|
            <a
							href='EditAttendee!changeToNotattending?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'>
						<s:text name="trans.ntatn.lbl"/></a>]
               </s:if>

					<s:elseif test="%{#transInfo['bookingdomain']!='VBEE'}">
          [<a
							href='EditAttendee?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'><s:text name="trans.edit.lbl"/></a>|
              <a href="#" onclick="deleteattendeenotes('${eid}','<s:property value="#attobj['profilekey']"/>','<s:property value="#ticketid"/>','${tid}','${profilecount}');"><s:text name="trans.del.lbl"/></a>&nbsp;|
            <a
							href='EditAttendee!changeToSure?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'><s:text name="trans.sure.lbl"/>
						</a>|
            <a
							href='EditAttendee!changeToNotattending?eid=${eid}&pid=<s:property value="#attobj['profilekey']"/>&ticketid=<s:property value="#ticketid"/>&tid=${tid}'>
						<s:text name="trans.ntatn.lbl"/></a>]
	
             </s:elseif>

				</s:else>

				<table style="padding-top:5px">


					<s:set name='fname' value="#attobj['fname']" />
					<s:if test="#attobj['fname']!=''">
						<tr>
							<td><s:text name="trans.fstnm.lbl"/><s:property value="#attobj['fname']" /></td>
						</tr>
					</s:if>
					<s:if test="#attobj['lname']!=''">
						<tr>
							<td><s:text name="trans.lastnm.lbl"/><s:property value="#attobj['lname']" /></td>
						</tr>
					</s:if>
					<s:if test="#attobj['email']!=''">
						<tr>
							<td><s:text name="trans.email.lbl"/><s:property value="#attobj['email']" /></td>
						</tr>
					</s:if>
					<s:if test="#attobj['phone']!=''">
						<tr>
							<td><s:text name="trans.phn.lbl"/><s:property value="#attobj['phone']" /></td>
						</tr>
					</s:if>
					<s:if test='%{seatingevent=="Y"}'>
					<s:if test="#attobj['seatcode']!=''">
						<tr>
							<td>
							<s:set var="pkey" value="#attobj['profilekey']"></s:set>
							<s:text name="trans.seat.cde.lbl"/><span id="scode_<s:property value='#pkey'/>"><s:property value="#attobj['seatcode']" /></span>
							<s:if test="%{#transInfo['bookingdomain']!='VBEE'}">
							[<a href="#" onclick="getSeatingChart('${eid}','${tid}','<s:property value='#pkey'/>','${currentTransactionDate}','changeseat')"><s:text name="trans.chnge.seat.lbl"/></a>]
							</s:if>
							</td>
					   </tr>
					</s:if>
					<s:else>
					 <tr>
					    <td>
						<s:set var="tickettype" value="#attobj['tickettype']"></s:set>
						<s:if test="paymentStatus!='Deleted' && tickettype!='donationType'">
						<s:set var="pkey" value="#attobj['profilekey']"></s:set>
						<s:text name="trans.seat.cde.lbl"/><span id="scode_<s:property value='#pkey'/>"></span>&nbsp;
						<s:if test="%{#transInfo['bookingdomain']!='VBEE'}">
						[<a href="#" onclick="getSeatingChart('${eid}','${tid}','<s:property value='#pkey'/>','${currentTransactionDate}','addseat')"><s:text name="trans.addseat.lbl"/></a>]
						</s:if>
						</s:if>
						</td>
					</tr>
					</s:else>
					</s:if>
					<s:set name='customList'
						value="#attribresponse[#attobj['profilekey']]" />
					<s:if test="#customList.size()>0">
						<s:iterator value="#customList" var='customobj'>
						<s:if test="#customobj['response']!=''">
							<tr>
								<td>
						
						<s:property value="#customobj['question']" />: <s:property value="#customobj['response']" />
						
								</td>
							</tr>
						</s:if>
						</s:iterator>
					</s:if>
					
				</table>

			</s:iterator>

		</s:iterator></div>

	</s:if>
</s:if>
<s:else>
	    <s:if test="%{purpose=='transaction'}">
		<tr>
			<td><s:text name="trans.not.valid.transid.lbl"/></td>
		</tr>
		</s:if>
		<s:elseif test="%{purpose=='ordernumber'}">
		<tr>
			<td><s:text name="trans.not.valid.odr.num"/></td>
		</tr>
		</s:elseif>
	
</s:else>
</div>
<div id="transactionreport"></div>
<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
</div>
<script>
var btn = new YAHOO.widget.Button("addNotesbtn", { onclick: {  fn: addNotes } });
function addNotes(){
	var eid='${eid}';
	var tid='${tid}';
	
	var url='TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
	
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getNotesSuccess,failure:getFailure});
	  	
}

function ticketdetails(){
YAHOO.ebee.popup.wait.show();
window.location.href='EditTransactionTickets!editTickets?eid=${eid}&tid=${tid}';
}

function profilefunc(){
YAHOO.ebee.popup.wait.show();
var value='<s:property value="#buyerInfoMap['profilekey']"/>';
window.location.href='EditAttendee!editBuyerInfo?eid=${eid}&tid=${tid}&pid='+value;
}

function editattendeenotes(eventid,profileid,ticketid,transactionid){
YAHOO.ebee.popup.wait.show();
window.location.href='EditAttendee?eid='+eventid+'&pid='+profileid+'&ticketid='+ticketid+'&tid='+transactionid;
}

var getNotesSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Notes", save, handleCancel);	
}

var getFailure = function(){
	alert("error");
	
}
var save=function(){
    YAHOO.ebee.popup.wait.show();
	submitFormAndReload('notesform', 'noteserrormessages');	
}

function deleteAttendee(){
    YAHOO.ebee.popup.wait.setHeader("Deleting...");
	YAHOO.ebee.popup.wait.show();
	$('deleteattendeenotes').request({
        onFailure: function() { $('deleteattendeenotes').update('Failed to get results') },
        onSuccess: function(t) {
           var profilecount=document.getElementById('profilecount').value;
           var eventid=document.getElementById('eventid').value;
           var transactionid=document.getElementById('transactionid').value;
           if(profilecount==1)
           document.getElementById('statusmsg').innerHTML='Total transaction deleted';
           else(profilecount>1)
           document.getElementById('statusmsg').innerHTML='Attendee information compleately deleted';
           YAHOO.ebee.popup.wait.hide();
           YAHOO.ebee.popup.dialog.cancel();
			if(!isValidActionMessage(t.responseText)){
    		return;
    		}
    		if(profilecount==1)
		    window.location.href="../eventmanage/Reports?eid="+eventid;
		    else if(profilecount>1)  
		    window.location.href="../eventmanage/TransactionDetails!getTransactionInfo?tid="+transactionid+"&eid="+eventid;
		}	
         });	
	
	
}

function deleteattendeenotes(eventid,profileid,ticketid,transactionid,profilecount){
    var url='EditAttendee!deleteattendeeNotes?eid='+eventid+'&tid='+transactionid+'&ticketid='+ticketid+'&pid='+profileid+'&profilecount='+profilecount;
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:deleteAttendeeNotes,failure:getFailure});
}

function deleteAttendeeNotes(responseObj){
    showPopUpDialogWindow(responseObj,props.trans_del_attnde_notes_lbl,deleteAttendee,CancelNote);
    YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		     YAHOO.ebee.popup.dialog.cancel();
             window.location.reload();
		  });
    var profilecount=document.getElementById('profilecount').value;
    if(profilecount==1)
      document.getElementById('statusmsg').innerHTML=props.trans_total_transatn_del_lbl;
    else if(profilecount>1)
      document.getElementById('statusmsg').innerHTML=props.trans_attnd_info_deltd_cmplty; 
}

function CancelNote(){
  YAHOO.ebee.popup.wait.hide();
  YAHOO.ebee.popup.dialog.cancel();
  window.location.reload();
} 

function getHelpPopup(){
	//alert("in get help popup");
	// showYUIinIframe("Steps For Stripe Merchant API Secret Key ",url,775,500);
	showYUIinIframe('Paypal API Third Party Permissions','/main/help/paypalrefundapipermission.html',775,500);
	document.getElementById('iframepopupdialog_mask').style.display='none';
	//YAHOO.ebee.popup.dialog1.cfg.setProperty("z", 1);
}
</script>
	
