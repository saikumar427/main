<%@taglib uri="/struts-tags" prefix="s" %>
<style>
.control-label{
	padding-top:0px !important;
}
</style>
<s:form cssClass="form-horizontal" name="waitListDetails" id="waitListDetails" action="WaitList!sendActivationLink" method="post">
<div>
<div id="activationLinkStatusMsg" class='alert alert-success col-md-12' style='display: none'></div>
<div id='waitlisterrors' class='alert alert-danger col-md-12' style='display: none'></div>
	<s:hidden name="eid"></s:hidden>
	<s:hidden name="wid" id="waitListID"></s:hidden>
	<s:hidden name="waitListData.name"></s:hidden>
	<s:hidden name="waitListData.email"></s:hidden>
	<s:hidden name="waitListData.eventDate"></s:hidden>
	<s:hidden name="waitListData.activationLink"></s:hidden>
	<s:hidden name="waitListData.ticketQty"></s:hidden>
	<s:hidden name="waitListData.expDate" id="expDateId" value=""></s:hidden>
	<s:hidden name="waitListData.expTime" id="expTimeId" value=""></s:hidden>
	<s:hidden id="detls_exp_month_field" name="waitListData.expDateTimeBeanObj.monthPart" ></s:hidden>
	<s:hidden id="detls_exp_day_field" name="waitListData.expDateTimeBeanObj.ddPart" ></s:hidden>
	<s:hidden id="detls_exp_year_field" name="waitListData.expDateTimeBeanObj.yyPart" ></s:hidden>
	<s:hidden id="detls_exp_hour_field" name="waitListData.expDateTimeBeanObj.hhPart"></s:hidden>
	<s:hidden id="detls_exp_min_field" name="waitListData.expDateTimeBeanObj.mmPart"></s:hidden>
	<s:hidden id="detls_exp_ampm_field" name="waitListData.expDateTimeBeanObj.ampm"></s:hidden>
		<div class="col-md-12">
			<div class="sm-font">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="wl.waitlist.id.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6">
					<s:property value="%{waitListData.wid}" />
					<%-- <s:if test="%{waitListData.status !='Completed'}">
					[<a href="javascript:;" onclick="deleteWLTransaction('${eid}','${wid}');"><s:text name="global.delete.lnk.lbl"/></a>]
					</s:if> --%>
					</div>
				</div>
				<br/>
			<s:if test="%{waitListData.name!=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.name.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.name}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.email !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.email.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.email}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.eventDate !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.event.date.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.eventDate}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.phone !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.phone.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.phone}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.status !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.status.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><span id="statusid"><s:property value="%{waitListData.status}" /></span></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.tickets !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="global.tickets.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.tickets}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.notes !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="wl.attendee.msg.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6"><s:property value="%{waitListData.notes}" /></div>
				</div>
				<br/>
			</s:if>
			
			<s:if test="%{waitListData.activationLink !=''}">
				<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="wl.actlink.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-5 col-xs-3"><s:if test="%{waitListData.status =='Completed'}"><s:property value="%{waitListData.activationLink}" /></s:if><s:else><a href='<s:property value="%{waitListData.activationLink}" />' target="_blank"><s:property value="%{waitListData.activationLink}" /></a></s:else></div>
				</div>
				<br/>
			</s:if>
			
			<div class="row">
					<div class="col-md-3 col-sm-3 control-label"><label><s:text name="wl.exp.date.time.lbl"/>:</label></div>
					<div class="col-md-6 col-sm-6 col-xs-8">
						<s:textfield cssClass="form-control" id="detls_expdatetime" type="text" name=""/>
						<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
					</div>
			</div>
			</div>
			<br/>
			<%-- <div class="row">
			<div class="col-md-3 col-sm-3"></div>
			<div class="col-md-6 col-sm-5 col-xs-3">
				<button type="button" class="btn btn-primary" id="activationlinkbtn" onclick="sendActivationLink('${wid}','${waitListData.ticketQty}','${waitListData.ticketId}')"  <s:if test="%{waitListData.status =='Completed'}">disabled</s:if>><s:if test="%{waitListData.activationLink !=''}"><s:text name="wl.send.act.lnk.again.btn.lbl"/></s:if><s:else><s:text name="wl.send.act.lnk.btn.lbl"/></s:else></button>
				<button class="btn btn-active cancelactivationlink" type="button"><s:text name="global.cancel.btn.lbl"/></button>
			</div>
			</div> --%>
			
			<div class="col-md-12 col-sm-12 center">
     			<span>
					<button type="button" class="btn btn-primary" id="activationlinkbtn" onclick="sendActivationLink('${wid}','${waitListData.ticketQty}','${waitListData.ticketId}')" <s:if test="%{waitListData.status =='Completed'}">disabled</s:if>><s:if test="%{waitListData.activationLink !=''}"><s:text name="wl.send.act.lnk.again.btn.lbl"/></s:if><s:else><s:text name="wl.send.act.lnk.btn.lbl"/></s:else></button>
					<button type="button" class="btn btn-cancel cancelactivationlink"><i class="fa fa-times"></i></button>
				</span>
   			</div>
</div>
</div>
</s:form>
<script>
	$('#detls_expdatetime').datetimepicker({
	    format:'m/d/Y g:i A',
	    formatTime:'g:i A'
	});
	$(document).ready(function() {
		var detls_expdatetime=$("#detls_exp_month_field").val()+"/"+$("#detls_exp_day_field").val()+"/"+$("#detls_exp_year_field").val();
		detls_expdatetime+=" "+$("#detls_exp_hour_field").val()+":"+$("#detls_exp_min_field").val()+" "+$("#detls_exp_ampm_field").val();
		$("#detls_expdatetime").val(detls_expdatetime);
	});

</script>