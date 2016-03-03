<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" language="JavaScript"
	src="../js/eventmanage/edittransactiontickets.js">
</script>

<script>
function backPage(){
	window.location.href='${transactionURL}';
	//window.location.href='TransactionDetails!getTransactionInfo?tid='+tid+'&eid='+eid;
}


</script>

<div class="row">
<div class="col-md-12">
<div id="EditResponseStatusMsg" class="alert alert-danger" style="display: none;"></div>
</div>
</div>

<div class="taskbox"><span class="taskheader"><b><s:text name="rsvp.edit.responses.lbl"/></b></span></div>


<div style="height:15px"></div>
<s:form name="responseedit" id="responseedit"
	action="EditTransactionTickets!rsvpUpdateResponses" method="post"
	theme="simple">
	<s:hidden name="eid" id="eventid"></s:hidden>
	<s:hidden name="tid" id="transid"></s:hidden>

<div class="row">
<div class="col-md-3" style="margin-top:8px"><s:text name="rsvp.sure.to.attend.lbl"/></div>

<s:set value="reportsData.surecount" name="surecount"></s:set>
                 <s:if test='%{#surecount != "0"}'> 
                 <div class="col-md-1" style="width:1%;margin-top:8px">
                 <s:property
					value="reportsData.surecount" />+
					</div>
					</s:if>
  <div class="col-md-1" style="width: 13%;">
                 <s:textfield name="reportsData.addsure" size="5"  cssClass="form-control" value="0"/>
                 </div>
                 </div>

<div style="height:15px"></div>
<div class="row">
<div class="col-md-3" style="margin-top:8px"><s:text name="rsvp.not.sure.to.attend.lbl"/></div>
<s:set value="reportsData.notsurecount" name="notsurecount"></s:set>
                 <s:if test='%{#notsurecount != "0"}'> 
                 <div class="col-md-1" style="width:1%;margin-top:8px">
                 <s:property
					value="reportsData.notsurecount" />+
					</div>
					</s:if>
<div class="col-md-2">
				<s:textfield name="reportsData.addnotsure"  cssClass="form-control" value="0"
		                         size="5" />
		                         </div>
</div>
<div style="height:5px;"></div>
	</s:form>
	
	<div class="row">
	<div class="col-md-12" align="center">
	
	<input class="btn btn-primary" type='button' id='submitBtn' value="<s:text name="global.update.btn.lbl"/>" onclick="updateRSVPProfileData();"/>
	<button onclick="closeTransactionTickets();" id="closeBtn" class="btn btn-cancel"><i class="fa fa-times"></i></button>
	<div id="loadingimg" style="display:none;"><center><img src="../images/ajax-loader.gif"></img></center></div>
	</div>
	
	</div>

<%-- <script>
var submit_Btn = new YAHOO.widget.Button("submitBtn");
var cancel_Btn = new YAHOO.widget.Button("cancelBtn",{onclick:{fn:cancelResponse}});
function cancelResponse(){
	window.location.href='TransactionDetails!getTransactionInfo?tid='+'${tid}'+'&eid='+'${eid}';
	}

</script> --%>