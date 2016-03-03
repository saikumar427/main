<script type="text/javascript">
YAHOO.ebee.event.responseQdata = ${responseQuestionJsonData};
</script>
<div class="taskcontent"><script>setHelpContent("rsvp_registrationform_transactionlevel_helpcontent")</script></div>
<div style="height: 13px;"></div>
<div class="box" align="left">
	<div class="boxheader">Transaction Level Questions</div>
	<div class="boxcontent">
		<div id="transaction_questions_table"></div>
	</div>
	<div class="boxfooter">
		<input id="transactionquestion"	type="button" value="Add New Question" />
	</div>
</div>
<br />
<div class="taskcontent"><script>setHelpContent("rsvp_registrationform_responselevel_helpcontent")</script></div>
<div style="height: 13px;"></div>
<div class="box" align="left">
	<div class="boxheader">Attendee Level Questions</div>
	<div class="boxcontent">
		<div id="response_questions_table"></div>
	</div>
	<div class="boxfooter">
		<input id="responsequestion" type="button" value="Add New Question" />
       
	</div>
</div>
<div id="popupdialog">
<div class="hd"></div>
<div class="bd"></div>
</div>
<script>
var dt_table_rf_xQ = instantiateTransactionQTable(YAHOO.ebee.event.transactionQdata.questions,"transaction_questions_table",${eid});
var responsequestion = new YAHOO.widget.Button("responsequestion", { onclick: { fn: addNewTicketQuestion,obj:{"eid":"${eid}","powertype":"${powertype}"} } });
var transactionquestion = new YAHOO.widget.Button("transactionquestion", { onclick: { fn: addNewTransactionQuestion,obj:{"eid":"${eid}","powertype":"${powertype}"} } });
var dt_table_rf_resQ = instantiateResponseQTable(YAHOO.ebee.event.responseQdata.questions,"response_questions_table",${eid});
</script>