<jsp:include page="/help/eventmanage_messages.jsp"></jsp:include>
<script type="text/javascript">
YAHOO.ebee.event.ticketQdata = ${ticketQuestionJsonData};
</script>
<div style="height:3px;"></div>
<div class="box" align="left">
<div class="boxheader">Transaction Level Questions  <span class="helpboxlink" id="transactionlevelquestionshelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="transactionlevelquestionshelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(registrationform_transactionlevel_helptitle)</script></div>
<div class="bd"><script>setHelpContent("registrationform_transactionlevel_helpcontent")</script></div>
</div>
<div class="boxcontent">
<div id="transaction_questions_table"></div>
</div>
<div class="boxfooter">
<input id="transactionquestion" type="button" value="Add New Question"  /> 
</div>
</div>
<br/>

<div style="height:3px;"></div>
<div class="box" align="left">
<div class="boxheader">Ticket Level Questions <span class="helpboxlink" id="ticketlevelquestionshelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="ticketlevelquestionshelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(registrationform_ticketlevel_helptitle)</script></div>
<div class="bd"><script>setHelpContent("registrationform_ticketlevel_helpcontent")</script></div>
</div>
<div class="boxcontent">
<div id="ticket_questions_table"></div>
</div>
<div class="boxfooter">
<input id="ticketquestion" type="button" value="Add New Question" />
</div>
</div>
<div id="popupdialog"><div class="hd"></div><div class="bd"></div>
</div>
<script>
loadHelpPanel("transactionlevelquestionshelppanel", "transactionlevelquestionshelp", "500px");
loadHelpPanel("ticketlevelquestionshelppanel", "ticketlevelquestionshelp", "500px");
var newtranquestionBtn = new YAHOO.widget.Button("transactionquestion", { onclick: { fn: addNewTransactionQuestion,obj:{"eid":"${eid}","powertype":"${powertype}"} } });
var dt_table_rf_xQ = instantiateTransactionQTable(YAHOO.ebee.event.transactionQdata.questions,"transaction_questions_table",${eid});
var newtktquestionBtn = new YAHOO.widget.Button("ticketquestion", { onclick: { fn: addNewTicketQuestion,obj:{"eid":"${eid}","powertype":"${powertype}"} } });
var dt_table_rf_tktQ = instantiateTicketQTable(YAHOO.ebee.event.ticketQdata.questions,"ticket_questions_table",${eid});
</script>