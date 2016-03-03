<%@taglib uri="/struts-tags" prefix="s"%>

<div>
	<table  border="0" cellpadding="0" cellspacing="0" width="100%">
 		<tr>
			<td colspan="2" class="headline">Ticketing</td>
		</tr>
	</table>
</div>
<s:if test="%{#powertype == 'Ticketing' && #poweredbyEB == 'yes'}">
<div id="boder">
	<table  border="0" cellpadding="3" cellspacing="0" width="100%" class="taskcontent">
		<tr>
              <td colspan="3" height="50">
              Ticket Sales - Total Sold <b><s:property value="totalSales.total"/> </b>(Online: <b><s:property value="totalSales.online"/></b>, Manual: <b><s:property value="totalSales.manual"/></b>)
 			  </td>
        </tr>
<tr>
<td width="10%" align="left" class="tbleft"> <strong>Status</strong></td>
<td width="60%" align="left" class="tbleft"> <strong>Ticket Name</strong></td>
<td width="30%" align="left" class="tbleft"> <strong>Sold/Total Limit</strong></td>
</tr>
<s:iterator value="%{ticketstatusvec}" var="type">            
<tr>
 <td class="tbleft"><s:property value="status"/></td>
<td class="tbleft"><s:property value="ticket_name"/></td>
<td class="tblright"><s:property value="sold_qty"/><br/>&nbsp;Online: <s:property value="onlinesales"/><br/>&nbsp;Manager: <s:property value="offlinesales"/></td>
 </tr>
</s:iterator>   	 
     </table>
</div>
</s:if>
<s:elseif test="%{#powertype == 'Ticketing' && #eventstatus == 'Active'}"> 
<div id="boder">
	<table  border="0" cellpadding="3" cellspacing="0" width="100%" class="taskcontent">
		<tr>
              <td colspan="2" height="50">Please update your Payment Settings first, then add Tickets to perform all other event management tasks.
              <p/>
              <input type="button" onclick="window.location.href='PaymentSettings?eid=${eid}'" value="Payment Settings"/>
              </td>
        </tr>	 
     </table>
</div> 
</s:elseif>






