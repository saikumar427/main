<%@taglib uri="/struts-tags" prefix="s"%>
<s:form name="printedticketsform" action="PriorityRegistration!cratePriorityList" id="printedticketsform" cssClass="form-horizontal">
<s:hidden name="eid" id="evtid"></s:hidden>
<s:hidden name="isrecur"></s:hidden>
<%-- <div class="taskbox">
<span class="taskheader">Generate Tickets</span><br/>

<div id="preprinterrors" ></div>
<div id="printStatusMsg" style="display:none;"></div>
<s:if test="%{isErrorExist=='yes'}">
	<s:fielderror cssClass="errorMessage">
	</s:fielderror>
</s:if>
<s:elseif test="%{isErrorExist=='no'}">
	
</s:elseif>
<div style="height:10px"></div>
<table width="100%">

<s:if test="%{isrecur==true}">
<tr>
<td>Date</td>
<td>
<s:select name="selecteddate" id="selecteddate" list="datesList"  listKey="key" listValue="value"></s:select>
</td>
</tr>
</s:if>


<tr>
<td>Ticket Type *</td>
<td><s:select name="printedData.selectedtkt" id="oldticket" list="ticketsList" headerKey="1" headerValue="-- Select Ticket --" listKey="key" listValue="value"></s:select></td>
</tr>

<tr>
<td>Agent Code</td>
<td>
<s:textfield name="printedData.agentcode" size="20"></s:textfield>
</td>
</tr>

<tr>
<td>
Series Prefix
</td>
<td>
<s:textfield name="printedData.prefix" size="20"></s:textfield>
</td>
</tr>


<tr>
<td>Starts From *</td>
<td>
<s:textfield name="printedData.fromfix" size="7"></s:textfield>&nbsp;&nbsp;to&nbsp;<s:textfield name="printedData.tofix" size="7"></s:textfield>
</td>
</tr>

<s:if test="%{templateList.size()>1}">
<tr>
<td>Template</td>
<td><s:select name="printedData.selectedtemplate" id="template" list="templateList"  listKey="key" listValue="value"></s:select></td>
</tr>
</s:if>

</table>
</div> --%>


<div class="well">
		<div class="row">
			<div class="col-md-12">

<div id="preprinterrors" style="display:none;" class="alert alert-danger"></div>
<div id="printStatusMsg" style="display:none;"></div>

<div class="row">
<div class="col-md-2 col-sm-2 control-label"><s:text name="global.ticket.type.lbl"/> *</div>
<div class="col-md-4 col-md-4">
<s:select name="printedData.selectedtkt" cssClass="form-control" id="oldticket" list="ticketsList" headerKey="1" headerValue="-- Select Ticket --" listKey="key" listValue="value"></s:select>
</div>

<div class="col-md-3 col-sm-3">
								<span class="pull-right">
									<button type="button" class="btn btn-primary" id="saveprintedtickets" data-loading-text="Saving...">
										<b><s:text name="global.add.btn.lbl"/></b>
									</button>
									<button type="button" class="btn btn-active" id="closeprintedtickets">
										<i class="fa fa-times"></i>
									</button>
								</span>
							</div>
</div>


<s:if test="%{isrecur==true}">
<div style="height:12px;"></div>

<div class="row">
<div class="col-md-2 col-sm-2 control-label"><s:text name="pt.date.lbl"/></div>
<div class="col-md-4 col-md-4">
<s:select name="selecteddate" id="selecteddateval" list="datesList"  listKey="key" listValue="value" cssClass="form-control"></s:select>
</div>
</div>
</s:if>


<div style="height:12px;"></div>

<div class="row">
<div class="col-md-2 col-sm-2 control-label"><s:text name="pt.agent.code.lbl"/></div>
<div class="col-md-4 col-md-4">
<s:textfield name="printedData.agentcode" size="20" cssClass="form-control"></s:textfield>
</div>
</div>
<div style="height:12px;"></div>

<div class="row">
<div class="col-md-2 col-sm-2 control-label"><s:text name="pt.series.prefix.lbl"/></div>
<div class="col-md-4 col-md-4">
<s:textfield name="printedData.prefix" size="20" cssClass="form-control"></s:textfield>
</div>
</div>

<div style="height:12px;"></div>


<div class="row">
<div class="col-md-2 col-sm-2 control-label"><s:text name="pt.starts.from.lbl"/> *</div>
<div class="col-md-4 col-md-4">

<s:textfield name="printedData.fromfix" size="7" cssClass="form-control"></s:textfield>&nbsp;&nbsp;<s:text name="global.to.lbl"/>&nbsp;<s:textfield name="printedData.tofix" size="7" cssClass="form-control"></s:textfield>
</div>
</div>

<%-- <div style="height:12px;"></div>
<s:if test="%{templateList.size()>1}">
<div class="row">
<div class="col-md-2 col-sm-2 control-label">Template</div>
<div class="col-md-4 col-sm-4">

<s:select name="printedData.selectedtemplate" id="template" list="templateList"  listKey="key" listValue="value" cssClass="form-control"></s:select>



</div> 


</div>
</s:if> --%>
				
			</div>
		</div>

	</div>

</s:form>
<br/>
<script>

var isErrorExists='${isErrorExist}';
var eid='${eid}';
/* var createbtn = new YAHOO.widget.Button("printedtickets",{onclick:{fn:savePrintedTickets,obj:{"eid":'${eid}'}}});
var cancelbtn = new YAHOO.widget.Button("cancelprintedtickets",{onclick:{fn:cancelPrintedTickets}}); */

/* function savePrintedTickets(){
	alert("hi in printed tickets");
} */

function cancelPrintedTickets(){
  window.location.href='PrintedTickets?eid='+eid;
}

</script>


