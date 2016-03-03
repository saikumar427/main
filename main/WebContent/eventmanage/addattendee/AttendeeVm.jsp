
<style>
@media (max-width: 1200px) {
	.respMedia{
	width:55% ;
	transition: all 0.5s ease-in 0s;
    -webkit-transition: all 0.5s ease-in 0s;
    -moz-transition: all 0.5s ease-in 0s;
    -ms-transition: all 0.5s ease-in 0s;
    -o-transition: all 0.5s ease-in 0s;
}
}
  @media (max-width: 992px) {
 .respMedia{
	width:65% ;
	transition: all 0.5s ease-in 0s;
    -webkit-transition: all 0.5s ease-in 0s;
    -moz-transition: all 0.5s ease-in 0s;
    -ms-transition: all 0.5s ease-in 0s;
    -o-transition: all 0.5s ease-in 0s;
}
}
@media (max-width: 768px) {
	.respMedia{
	width:90% ;
	transition: all 0.5s ease-in 0s;
    -webkit-transition: all 0.5s ease-in 0s;
    -moz-transition: all 0.5s ease-in 0s;
    -ms-transition: all 0.5s ease-in 0s;
    -o-transition: all 0.5s ease-in 0s;
}
}



</style>
<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="ticketvec" value="ticketsVm['ticketVec']"/>
<s:set name="recurringdates" value="recurringeventsVec"/>
<s:set name="eventdate" value="eventdate"/>
<s:set name='currencyformat' value='currencyFormat'/>
<s:if test="#ticketvec!=null&&#ticketvec.size()>0">
<s:form name="addattendeetickets"  id='addattendeetickets' action='' method="post" theme="simple">
<input type="hidden"  id='jsondata'  name='jsondata' value=""/>
<input type="hidden"  id='selseatsjson'  name='selseatsjson' value=""/>
<input type="hidden"  id='tid'  name='tid' value=""/>
<input type="hidden"  id='discountcode'  name='discountcode' value=""/>
<input type="hidden"  id='eventdate'  name='eventdate' value=""/>
<div style="height:3px;"></div>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td width="100%">
<table width="100%" cellpadding="0" cellspacing="0">
<s:set name="ticketNameLabel" value="labelsMap['event.reg.ticket.name.label']"/>
<s:set name="ticketPriceLabel" value="labelsMap['event.reg.ticket.price.label']"/>
<s:set name="ticketQtyLabel" value="labelsMap['event.reg.ticket.qty.label']"/>
<s:set name="processFeeLabel" value="labelsMap['event.reg.processfee.label']"/>
<tr>
<td width="45%"><b><s:property  value="#ticketNameLabel"/></b></td>
<td width="20%" align="right"><b><s:property  value="#ticketPriceLabel" />(${currencyFormat})</b></td>
<td width="15%" align="right"><b><s:property  value="#processFeeLabel"/>(${currencyFormat})</b></td>
<td width="15%" align="right" ><b><s:property  value="#ticketQtyLabel" /> </b></td>
</tr>
</table>
</td>
</tr>
<tr><td height="2"><hr/></td></tr>
<s:iterator value="#ticketvec" var="ticobj">
<s:set name="groupname" value="#ticobj['ticketGroupName']"/>
<s:set name="groupDesc" value="#ticobj['ticketGroupDesc']"/>
<tr>
<td>
<s:property  value="#groupname" escape="false"/>
<br/><s:property  value="#groupDesc" />
</td>
</tr>
<s:set name="tickets" value="#ticobj['tickets']" />
<s:iterator value="#tickets"  var="ticket" >
<tr>
<td width="100%">
<div id='<s:property  value="#ticket['ticketid']"/>'  class='tktWedgetClass'> </div>
</td>
</tr>
<tr><td height="2"><hr/></td></tr>
</s:iterator>
</s:iterator>
<tr><td id='discountblock'></td></tr>
<!-- <tr>
<td align='right'><a href='#' onClick='getTotals();'>Show Total</a></td>
</tr>
<tr><td align='right'>
<div id='totalamounts' style='display:none'>
<table align='right'><tr><td>
<div id='totalblock' >Total(<s:property  value="#currencyformat" />):</div></td>
<td id='total' align='right'></td></tr>
<tr><td><div id='discountsblock' >Discount(<s:property  value="#currencyformat" />):</div></td><td id='discount' align='right'> </td></tr>
<tr><td><div id='netamountblock' >Net Amount(<s:property  value="#currencyformat" />):</div></td><td id='netamount' align='right'></td></tr>
<tr><td><div id='taxblock' >Tax(<s:property  value="#currencyformat" />):</div></td><td id='tax' align='right'></td></tr>
<tr><td><div id='gtotalblock' >Grand Total(<s:property  value="#currencyformat" />):</div></td><td id='grandtotal' align='right'></td></tr>
</table>
</div>
</td></tr> -->
<s:if test="%{seatingenabled}">
<tr>
	<td id="seatingsection"></td>
</tr>
</s:if>


</table>
<div class="col-xs-12 col-md-12 text-center">
	<br><br><input type="button"  id='addattendeeticketsave' class="btn btn-primary" name='continue' value="<s:text name="global.continue.btn.lbl"/>" onClick='validateTickets()'/>
</div>
<%-- 
<table align="center" width="100%" style="position:relative;">
<tr>
<td colspan="2" align="center">
<div style="position:relative;"><span style="position:absolute;top:40px;left:335px;">
<input type="button"  id='addattendeeticketsave' class="btn btn-primary" name='continue' value="Continue" onClick='validateTickets()'/>
</span>
</div>
</td></tr>
</table> --%>
</s:form>
</s:if>
<s:else><s:text name="global.no.tkts.available.msg"/></s:else>