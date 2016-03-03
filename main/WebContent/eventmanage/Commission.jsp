<%@taglib uri="/struts-tags" prefix="s"%>
<div id="commissionerrormessages"></div>
<form action="NetworkSelling!save" name="tCommissionForm" id="tCommissionForm" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="tid"></s:hidden>
<s:hidden name="nts_commission"></s:hidden>
<div style="overflow:auto;height:100px;width:400px;border:0px solid #336699;padding-left:5px">
<table>
	<tr>
		<td valign="top">Ticket&nbsp;Name:&nbsp;&nbsp;</td>
		<td><s:property value="ticketData.ticketName"></s:property></td>
	</tr>
	<tr>
		<td>Commission:&nbsp;&nbsp;</td>
		<td><s:textfield name="ticketData.netWorkCommission" theme="simple" size="5"></s:textfield><s:text name='ticketData.percentage'></s:text></td>
	</tr>
</table>
</div>
</form>