<%@taglib uri="/struts-tags" prefix="s"%>

<div  style="padding-top:20px;margin-left:-15px;">
<div class="row">
<div class="col-sm-12">
<s:if test='%{status == "1"}'><s:text name="global.tickets.registration.sent.lbl"/>.</div></div><br>
<div class="row">
<div class="col-sm-12"><b><s:text name="global.reg.details.lbl"/></b></div></div>
<div class="row">
<div class="col-sm-4">
<s:text name="ea.date.header.lbl"/>:&nbsp;${transactionData.tdate}<br>
<s:text name="global.transaction.id.lbl"/>:&nbsp;${transactionData.tid}<br>
<s:text name="global.event.lbl"/>:&nbsp;${transactionData.ename}<br><br>
</div></div>
<div class="row">
<div class="col-sm-12">
<s:text name="global.want.info.mail.address.lbl"/> <b>${transactionData.mgrEmail}</b></div></div>
</s:if>
<s:elseif test='%{status == "2"}'><div class="row">
<div class="col-sm-12"><s:text name="global.please.enter.id.lbl"/>.</div></div></s:elseif>
<s:else><div class="row">
<div class="col-sm-12"><s:text name="global.mail.send.fail.lbl"/>.</div></div></s:else>
</div>

