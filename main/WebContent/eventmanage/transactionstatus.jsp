
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<!-- <head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
</head> -->
<body>

<%--  <s:property value="%{currentStatus}"/>  --%>

 <s:if test="%{currentStatus!='Deleted' && currentStatus!='Chargeback' && currentStatus!='Refunded'}"> 

<%-- <s:if test="%{currentStatus!='Deleted' && currentStatus!='Chargeback'}"> --%>
<div class="col-md-4" >
 <div class="container" id="txnstatus">
<s:form name="transactionstatusform" id="transactionstatusform" action="TransactionDetails!saveStatus" method="post">
<s:hidden name="eid" id="eventid"></s:hidden>
<s:hidden name="tid" id="transactionid"></s:hidden>
<s:hidden name="transactionNotesData.notes_id"></s:hidden>
<s:hidden name="currentStatus"></s:hidden>
<div class="alert alert-danger" style="display:none"></div>

<div class="row">
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-2 col-xs-2"><s:text name="rep.current.status.lbl"/>&nbsp;</div>
<div class="col-xs-9"><span id="currentstatus">
<s:if test="%{currentStatus=='Completed'}"><s:text name="rep.trans.completed.status.lbl"></s:text></s:if>
<s:elseif test="%{currentStatus=='Pending Approval'}"><s:text name="rep.trans.pending.approval.lbl"></s:text></s:elseif>
<s:else><s:label name="currentStatus"></s:label></s:else>
</span></div>
</div>

<br/>

<div class="row">
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-2 col-xs-2 col-sm-2"><s:text name="rep.chnge.status.lbl"/>&nbsp;</div>
<div class="col-xs-4 col-md-4 col-sm-4">
<s:select label="Select" name="reportsData.changeStatus" id="statusType" headerKey="" headerValue="%{getText('rep.select.status.lbl')}" list="statusType"  listKey="key" listValue="value" cssClass="form-control" ></s:select>
</div>
</div>
<br/>

<div class="row">
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-xs-2 col-md-2 col-sm-2"><s:text name="rep.reason.lbl"/>&nbsp;</div>
<div class="col-xs-5 col-md-5 col-sm-5">
<s:textarea name="transactionNotesData.notes"  cssClass="form-control" cols="55" rows="7" ></s:textarea>
</div>
</div>
</s:form>        
</br>
<div class="form-group" id="statusdiv">
 <div class="col-sm-3"></div>
                        <div class="col-sm-6">
                            <button type="submit" class="btn btn-primary" id="statussubmit">
                              <s:text name="global.submit.btn.lbl"></s:text></button>
                            <button class="btn btn-cancel" id="cancel">
                                <i class="fa fa-times"></i></button>
                        </div>
 </div>
 
 
 <div class="form-group" id="statusloading" style="display: none;">
 <div class="col-sm-3"></div>
                        <div class="col-sm-6">
                            <img src="../images/ajax-loader.gif"></img>
                        </div>
 </div>
 
</div>

</div>
</s:if>


<s:else>

<s:if test="%{currentStatus=='Deleted'}">
<div class="row">
<div class="col-md-12"><center><s:text name="rep.trans.alrdy.dltd.lbl"/></center></div></div>
</s:if>

<s:if test="%{currentStatus=='Chargeback'}">
<div class="row">
<div class="col-md-12"><center><s:text name="rep.trans.alrdy.chrgbck.lbl"/></center></div></div>
</s:if>

<s:if test="%{currentStatus=='Refunded'}">
<div class="row">
<div class="col-md-12"><center><s:text name="rep.trans.alrdy.refbk.lbl"/></center></div></div>
</s:if>

<br/>


<div id="refundbtn" class="row"><div class="col-md-12"><center>
<button class="btn btn-cancel" onclick="closeTransactionTickets();"><i class="fa fa-times"></i></button></center></div></div>

</s:else>

<div class="col-md-12 col-sm-12 col-xs-12" id="statussuccessdiv" style="display:none;">
 <div class="col-md-4 col-sm-4 col-xs-4"></div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <button class="btn btn-cancel"  onclick="closeTransactionTickets();">
                                <i class="fa fa-times"></i></button>
                        </div>
 </div>


</body>
</html>
<%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> --%>
<script>

var tempObj={"Complete":"Completed","Delete":"Deleted","Charge Back":"Chargeback","Refund":"Refunded"};

$(document).ready(function(){

	$('#statussubmit').click(function(){
		$('#statusloading').show();
		$('#statusdiv').hide();
                  $.ajax({
						type:'GET',
						data:$('#transactionstatusform').serialize(),
						url:'TransactionDetails!saveStatus',
						success:function(result){
							//alert(result);
							//if(!isValidActionMessage(result)) return;
							var tid=$('#transactionid').val();
							$('tr.info').removeClass('info');
							//$('#statuschange').show().html('<div class="col-md-1"></div><div class="col-md-7"><div style="" class="alert alert-success alert-dismissable page-alert"><button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>Status updated successfully.</div></div>');
							$('#statusloading').hide();
							$('#statusdiv').show();
							$('#txnstatus').slideUp();
							$('#statussuccessdiv').show()
							$('#currentstatus').html(tempObj[$('#statusType').val()]);
							notification(props.tran_stat_updt_succ_lbl,'success');
                          //   $('#attendeesubmitbtn').trigger('click');
							//showDetails(tid);
							//element.trigger('click');
							}
                      });
		});

	$('#cancel').click(function(){
		closeTransactionTickets();
		});
	
});

</script>

</div>
