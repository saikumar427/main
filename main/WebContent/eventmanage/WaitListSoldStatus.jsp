<%@taglib uri="/struts-tags" prefix="s"%>
<s:form name="waitlistsoldstatus" id="waitlistsoldstatus" action="WaitList!updateTicketQty" method="post">
<div id="waitlistsoldstatuserrormessages" class='alert alert-danger col-md-12' style='display: none'></div>
<div class="row">
<s:hidden name="eid"></s:hidden>
<s:hidden name="activationType" id="activationtypeid"></s:hidden>
<s:hidden name="ticketid"></s:hidden>
<s:hidden name="ticketQty"></s:hidden>
<s:if test="%{activationType=='Multiple'}">
<s:hidden name="widAry"></s:hidden>
<s:if test="%{multipleTicketReqMap.size>0}">
<s:hidden value="%{multipleTicketJson}" id="multitktmap"></s:hidden>
</s:if>
<s:hidden id="exp_month_field" name="waitListData.expDateTimeBeanObj.monthPart" ></s:hidden>
<s:hidden id="exp_day_field" name="waitListData.expDateTimeBeanObj.ddPart" ></s:hidden>
<s:hidden id="exp_year_field" name="waitListData.expDateTimeBeanObj.yyPart" ></s:hidden>
<s:hidden id="exp_hour_field" name="waitListData.expDateTimeBeanObj.hhPart"></s:hidden>
<s:hidden id="exp_min_field" name="waitListData.expDateTimeBeanObj.mmPart"></s:hidden>
<s:hidden id="exp_ampm_field" name="waitListData.expDateTimeBeanObj.ampm"></s:hidden>
<div class="col-md-4"><s:text name="wl.exp.date.time.lbl"/>:</div>
<div class="col-md-7">
<s:textfield cssClass="form-control" id="expdatetime" type="text" name=""/>
<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
</div>
</s:if>
</div>
</s:form>
<script>
$('#expdatetime').datetimepicker({
    format:'m/d/Y g:i A',
    formatTime:'g:i A'
});

$(document).ready(function() {
	var expdatetime=$("#exp_month_field").val()+"/"+$("#exp_day_field").val()+"/"+$("#exp_year_field").val();
	expdatetime+=" "+$("#exp_hour_field").val()+":"+$("#exp_min_field").val()+" "+$("#exp_ampm_field").val();
	$("#expdatetime").val(expdatetime);
});
</script>
