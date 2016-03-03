<%@taglib uri="/struts-tags" prefix="s" %>

<div id="ticketformerrormessages"></div>

<form action="VolumeTicketDetails!saveVolumeTicket" name="volTicketingForm" id="volTicketingForm" method="post">
<div style="overflow:auto;width:650px;border:0px solid #336699;padding-left:5px">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="volTicketData.ticketId"></s:hidden>
<table width="100%">
<tr>
<td width="32%">Ticket Name *</td>
<td><s:textfield name="volTicketData.ticketName" theme="simple" size="52" placeholder="Enter Ticket Name"></s:textfield></td>
</tr>
<tr>
<td>Ticket Price&nbsp;*</td>
<td><s:text name='volTicketData.currency'></s:text>&nbsp;<s:textfield name="volTicketData.ticketPrice" theme="simple" size="12" placeholder="Enter Number"></s:textfield></td>
</tr>
<tr>
<td valign="top">Volume Discount&nbsp;*</td>
<td>
<s:set name="discounttype" value="volTicketData.discountType"></s:set>
<input type="radio" name="volTicketData.discountType"  value="A" onclick="changeType('Absolute')" <s:if test='%{#discounttype == "A"}'>checked="checked"</s:if>>Amount&nbsp;
<span id="discountprefixlabel" <s:if test='%{#discounttype == "A"}'>style="display:inline"</s:if><s:else>style="display:none"</s:else>>
<s:text name='volTicketData.currency'></s:text>&nbsp;<s:textfield  name="volTicketData.amountDiscount" theme="simple" size="12" placeholder="Enter Number"></s:textfield></span><br/>
<input type="radio" name="volTicketData.discountType" value="P" onclick="changeType('Percentage')" <s:if test='%{#discounttype == "P"}'>checked="checked"</s:if>>Percentage&nbsp;
<span id="discountsufixlabel" <s:if test='%{#discounttype == "P"}'>style="display:inline"</s:if><s:else>style="display:none"</s:else>>
<s:textfield  name="volTicketData.percentageDiscount" theme="simple" size="12" placeholder="Enter Number"></s:textfield>&nbsp;%</span>
</td>
</tr>
<tr>
<td>Trigger Quantity *&nbsp;<a class="helpboxlink" onClick="javascript:callTriggerQtyHelp()"><img src="../images/questionMark.gif" /></a></td>
<td><s:textfield id="tktavailid" name="volTicketData.triggerQty" theme="simple" theme="simple" size="12" onkeyup="setTicketMaxQty()" placeholder="Enter Number"></s:textfield></td>
</tr>

<tr>
<td valign="top" style="padding-bottom:0px">Sale Date & Time&nbsp;*</td>
<td style="padding-bottom:0px">
<table cellpadding="0" cellspacing="0">
<tr>
<td>Starts&nbsp;</td>
<td><s:textfield id="start_month_field" name="volTicketData.stdateTimeBeanObj.monthPart" theme="simple" size="2" maxlength="2" ></s:textfield>&nbsp;</td>
<td><s:textfield id="start_day_field" name="volTicketData.stdateTimeBeanObj.ddPart" theme="simple" size="2" maxlength="2" ></s:textfield>&nbsp;</td>
<td><s:textfield id="start_year_field" name="volTicketData.stdateTimeBeanObj.yyPart" theme="simple" size="6" maxlength="4" ></s:textfield>&nbsp;</td>
<td><img src="../images/calendar_icon.gif" id="start_btn_cal" onclick="showDateCal('start');cal_overlay.cfg.setProperty('xy',[600,390])">&nbsp;</td>
<td><div id="overlayContainer_start"></div></td>
<td>
<s:set name="hhpart" value="volTicketData.stdateTimeBeanObj.hhPart"/>
<select name="volTicketData.stdateTimeBeanObj.hhPart" id="volTicketData.stdateTimeBeanObj.hhPart" >
<s:iterator value="%{hours}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#hhpart==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
<s:set name="minspart" value="volTicketData.stdateTimeBeanObj.mmPart"/>
<select name="volTicketData.stdateTimeBeanObj.mmPart" id="volTicketData.stdateTimeBeanObj.mmPart" >
<s:iterator value="%{minutes}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#minspart==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
<s:set name="ampm" value="volTicketData.stdateTimeBeanObj.ampm"/>
<select name="volTicketData.stdateTimeBeanObj.ampm">
<option value="AM" <s:if test="%{#ampm == 'AM'}">selected='selected'</s:if>>AM</option>
<option value="PM" <s:if test="%{#ampm == 'PM'}">selected='selected'</s:if>>PM</option>
</select>
</td>
</tr>
</table>
</td>
</tr>
<tr><td></td>
<td><table cellpadding="0" cellspacing="0"><tr>
<td align="left">Duration&nbsp;</td>
<td><s:textfield name="volTicketData.activeDuration" theme="simple" theme="simple" size="12" placeholder="Enter Number"></s:textfield>&nbsp;<span class="smallestfont">hour(s)</span></td>
</tr></table></td>
</tr>
<tr><td><span><font color="grey">More Options</font>&nbsp;<img id="showhide" src="../images/dn.gif"/></span></td><td></td></tr>
</table>
<div id='moreblock' style="display:none">
<table width="100%">
<tr>
<td valign="top" width="32%">Ticket Description</td>
<td><s:textarea rows="3" cols="49" name="volTicketData.ticketDescription" theme="simple" placeholder="Enter Ticket Description"></s:textarea></td>
</tr>
<tr>
<td>Purchase Quantity *</td>
<td>Minimum&nbsp;<s:textfield name="volTicketData.minQty" theme="simple" theme="simple" size="12" placeholder="Enter Number" style="margin-right:10px"></s:textfield>
Maximum&nbsp;<s:textfield id="tktmaxid" name="volTicketData.maxQty" theme="simple" theme="simple" size="12" placeholder="Enter Number"></s:textfield></td>
</tr>
<tr>
<s:set name="posttriggertype" value="volTicketData.postTriggerType"></s:set>
<td valign="top">Trigger Success Action&nbsp;<a class="helpboxlink" onClick="javascript:callTriggerSuccessHelp()"><img src="../images/questionMark.gif" /></a></td>
<td align="left">
<input type="radio" name="volTicketData.postTriggerType"  id="" value="1" <s:if test="%{#posttriggertype == 1}">checked="checked"</s:if>>Stop volume sale<br/>
<input type="radio" name="volTicketData.postTriggerType" id="" value="2" <s:if test="%{#posttriggertype == 2}">checked="checked"</s:if>>Continue selling&nbsp;
<s:textfield name="volTicketData.upperLimit" theme="simple" size="12" placeholder="Enter Number"></s:textfield>&nbsp;additional tickets<br/>
<input type="radio" name="volTicketData.postTriggerType" id="" value="3" <s:if test="%{#posttriggertype == 3}">checked="checked"</s:if>>Start new cycle for&nbsp;
<s:textfield name="volTicketData.cycles" theme="simple" size="12" placeholder="Enter Number"></s:textfield>&nbsp;times
 </td>
</tr>
<tr>
<s:set name="triggerfailaction" value="volTicketData.triggerFailAction"></s:set>
<td valign="top">Trigger Fail Action&nbsp;<a class="helpboxlink" onClick="javascript:callTriggerFailHelp()"><img src="../images/questionMark.gif" /></a></td>
<td align="left">
<input type="radio" name="volTicketData.triggerFailAction"  id="" value="1" <s:if test="%{#triggerfailaction == 1}">checked="checked"</s:if>>Void sale<br/>
<input type="radio" name="volTicketData.triggerFailAction" id="" value="2" <s:if test="%{#triggerfailaction == 2}">checked="checked"</s:if>>Attendee must buy at regular price
<!-- Sell at&nbsp;<s:textfield name="volTicketData.triggerFailDiscount" theme="simple" size="12" placeholder="Enter Number"></s:textfield>&nbsp;%&nbsp;discount -->
</td>
</tr>
<tr>
<td valign="top">Service Fee&nbsp;*&nbsp;<a class="helpboxlink" onClick="javascript:callServiceFeeHelp()"><img src="../images/questionMark.gif"/></a></td><td>
<s:set name="processfeepaid" value="volTicketData.processFeePaidBy"></s:set>

<input type="radio" name="volTicketData.processFeePaidBy" id="processFeeAttendee" value="processFeeAttendee" <s:if test="%{#processfeepaid != 'processFeeMgr'}">checked="checked"</s:if>>Collect from Attendee&nbsp;
<s:text name='volTicketData.currency'></s:text>&nbsp;<s:textfield name="volTicketData.processingFee" theme="simple" size="12" placeholder="Enter Number"></s:textfield><br/>
<input type="radio" name="volTicketData.processFeePaidBy"  id="processFeeMgr" value="processFeeMgr" <s:if test="%{#processfeepaid == 'processFeeMgr'}">checked="checked"</s:if>>Paid by manager 
 </td>
</tr>
<!--tr>
<td valign="top">Collect Attendee Name
<a class="helpboxlink" >
<img src="../images/questionMark.gif" /></a></td>
	<td><input type="radio" name="" id="aticketType" value="" checked="checked">Yes, on each ticket sale
	</td>
</tr>
<tr>
<td></td>
	<td>
	<input type="radio" name="" id="nticketType" value="" >No, buyer name collected at transaction level is sufficient</td>
</tr-->
<!--tr>
<td>Is required Bar code and QR code in conformation email</td>
	<td valign="top"><input type="radio" name="" id="aticketType" value="" checked="checked">Yes
	<input type="radio" name="" id="nticketType" value="" >No</td>

</tr>

<tr>
<td>Show this ticket profile in Who's attending</td>
	<td valign="top"><input type="radio" name="" id="aticketType" value="" checked="checked">Yes
	<input type="radio" name="" id="nticketType" value="" >No</td>

</tr-->
<tr><td height="5"></td></tr>
</table>
</div>
</div>
</form>


