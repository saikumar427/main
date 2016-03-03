<%@taglib uri="/struts-tags" prefix="s" %>
<div id="errormessages" class="errorMessage" style="display:none"></div>
<script type="text/javascript">
<!--
var eid=${eid};
//-->
</script>
<s:form action="GroupTicketDetails!save" name="grptcktform" id="grptcktform">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="grptcktData.groupticketid"></s:hidden>
<s:hidden name="grptcktData.soldqty"></s:hidden>
<table>
	<tr>
		<td>Group Ticket Name * </td>
		<td><s:textfield name="grptcktData.ticketName" theme="simple" size="52" ></s:textfield> </td>
	</tr>
	<tr>
		<td>Group Ticket Description *</td>
		<td><s:textarea rows="3" cols="52" name="grptcktData.description"></s:textarea> </td>
	</tr>
	<s:set name="soldqty" value="grptcktData.soldqty"></s:set>
	<s:if test='%{#soldqty=="0"}'>
	<tr>
		<td>Group Ticket Price * </td>
		<td><s:text name="grptcktData.currency"></s:text><s:textfield name="grptcktData.price" id="price" theme="simple" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" onkeyup="calculatePrice()"></s:textfield>
		<span id="discountprice"></span> 
		 </td>
	</tr>
	<!--<s:set name="disctype" value="grptcktData.discounttype"></s:set>
	
	<tr>
		<td valign="top">Discount Type</td>
		<td>
			<input type="radio" name="grptcktData.discounttype" 
			<s:if test='%{#disctype == "A"}'>checked='checked'</s:if> id="absoluteradio" onclick="changeType()" value="A">Absolute
			<input type="radio" name="grptcktData.discounttype" 
			<s:if test='%{#disctype == "P"}'>checked='checked'</s:if> id="percentradio" onclick="changeType()" value="P">Percentage
		</td>
	</tr>
	-->
	<tr>
		<td>Group Discount * <a class="helpboxlink" onClick="javascript:callDiscountHelp()">
<img src="../images/questionMark.gif" /></a></td>
		<td><span id="discountprefixlabel" style="display:inline"></span>
		<s:textfield name="grptcktData.discountvalue" size="5" id="discount" maxlength="5" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" onkeyup="calculatePrice()" ></s:textfield>
		%</td>
	</tr>
	</s:if>
	<s:else>
		<s:hidden name="grptcktData.price"></s:hidden>
		<s:hidden name="grptcktData.discountvalue"></s:hidden>
	</s:else>
	<tr>
		<td>Group Sale Trigger Quantity *  <a class="helpboxlink" onClick="javascript:callTriggerQtyHelp()">
<img src="../images/questionMark.gif" /></a></td>
		<td><s:textfield name="grptcktData.triggerqty" size="5" maxlength="5" onblur="extractNumber(this,0,false);" onkeyup="extractNumber(this,0,false);" onkeypress="return blockNonNumbers(this, event, false, false);" ></s:textfield></td>
	</tr>
	<tr>
		<td>Minimum Purchase Quantity Per Registration *</td>
		<td> <s:textfield name="grptcktData.minqty" size="5" maxlength="5" onblur="extractNumber(this,0,false);" onkeyup="extractNumber(this,0,false);" onkeypress="return blockNonNumbers(this, event, false, false);" ></s:textfield> </td>
	</tr>
	<tr>
		<td>Maximum Purchase Quantity Per Registration *</td>
		<td> <s:textfield name="grptcktData.maxqty" size="5" maxlength="5" onblur="extractNumber(this,0,false);" onkeyup="extractNumber(this,0,false);" onkeypress="return blockNonNumbers(this, event, false, false);" ></s:textfield> </td>
	</tr>
	<s:set name="triggertype" value="grptcktData.post_trigger_type"></s:set>
	<tr>
		<td valign="top">Group Sale Trigger Action *</td>
		<td valign="top">
			<table>
			<tr>
				<td><input type="radio" name="grptcktData.post_trigger_type" 
				 <s:if test='%{#triggertype=="1"}'> checked="checked" </s:if>   value="1">Stop Ticket Sales </td>
			</tr>
			<tr>
				<td><input type="radio" name="grptcktData.post_trigger_type" 
				 <s:if test='%{#triggertype=="2"}'> checked="checked" </s:if> value="2">Allow Ticket Sales Till Ticket Count Reaches <s:textfield size="5" name="grptcktData.upperlimit"   maxlength="5" onblur="extractNumber(this,0,false);" onkeyup="extractNumber(this,0,false);" onkeypress="return blockNonNumbers(this, event, false, false);" ></s:textfield></td>
			</tr>
			<tr>
				<td><input type="radio" name="grptcktData.post_trigger_type" 
				<s:if test='%{#triggertype=="3"}'> checked="checked" </s:if> value="3">Repeat Group Sales Up To <s:textfield size="5" name="grptcktData.cycleslimit" maxlength="5" onblur="extractNumber(this,0,false);" onkeyup="extractNumber(this,0,false);" onkeypress="return blockNonNumbers(this, event, false, false);" ></s:textfield> More Times</td>
			</tr>
			</table>
		</td>
	</tr>
	<s:if test='%{#soldqty=="0"}'>
	<tr>
		<td>Sale Start Date *</td>
		<td>
			<table>
			<tr>
				<td><s:textfield id="start_month_field" name="grptcktData.stdateTimeBeanObj.monthPart" theme="simple" size="2" maxlength="2" readonly="true" ></s:textfield></td>
				<td><s:textfield id="start_day_field" name="grptcktData.stdateTimeBeanObj.ddPart" theme="simple" size="2" maxlength="2" readonly="true"></s:textfield></td>
				<td><s:textfield id="start_year_field" name="grptcktData.stdateTimeBeanObj.yyPart" theme="simple" size="4" maxlength="4" readonly="true"></s:textfield></td>
				<td><img src="../images/calendar_icon.gif" id="start_btn_cal" onclick="showDateCal('start');cal_overlay.cfg.setProperty('xy',[600,190])"></td>
				<td><div id="overlayContainer_start"></div></td>
				<td>
				<s:set name="hhpart" value="grptcktData.stdateTimeBeanObj.hhPart"/>
				<select name="grptcktData.stdateTimeBeanObj.hhPart" id="grptcktData.stdateTimeBeanObj.hhPart" >
				<s:iterator value="%{hours}" var="type">
				<s:set name="cval" value="key"/>
				<option value="<s:property value="key"/>" <s:if test="%{#hhpart==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
				</select>
				<s:set name="minspart" value="grptcktData.stdateTimeBeanObj.mmPart"/>
				<select name="grptcktData.stdateTimeBeanObj.mmPart" id="grptcktData.stdateTimeBeanObj.mmPart" >
				<s:iterator value="%{minutes}" var="type">
				<s:set name="cval" value="key"/>
				<option value="<s:property value="key"/>" <s:if test="%{#minspart==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
				</select>
				<s:set name="ampm" value="grptcktData.stdateTimeBeanObj.ampm"/>
				<select name="grptcktData.stdateTimeBeanObj.ampm">
				<option value="AM" <s:if test="%{#ampm == 'AM'}">selected='selected'</s:if>>AM</option>
				<option value="PM" <s:if test="%{#ampm == 'PM'}">selected='selected'</s:if>>PM</option>
				</select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</s:if>
	<s:else>
		<s:hidden name="grptcktData.stdateTimeBeanObj.monthPart"></s:hidden>
		<s:hidden name="grptcktData.stdateTimeBeanObj.ddPart"></s:hidden>
		<s:hidden name="grptcktData.stdateTimeBeanObj.yyPart"></s:hidden>
		<s:hidden name="grptcktData.stdateTimeBeanObj.hhPart"></s:hidden>
		<s:hidden name="grptcktData.stdateTimeBeanObj.mmPart"></s:hidden>
		<s:hidden name="grptcktData.stdateTimeBeanObj.ampm"></s:hidden>
	</s:else>
	<s:set name="triggerfailaction" value="grptcktData.trigger_fail_action"></s:set>
	<tr>
		<td>Allow Attendee to purchase ticket at regular/discounted<br> price if group discount is not triggered *</td>
		<td><input type="radio" name="grptcktData.trigger_fail_action" <s:if test='#triggerfailaction=="1"'> checked="checked"</s:if>   value="1">Yes, with <s:textfield name="grptcktData.triggerfailDicount" size="5" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);"></s:textfield>%
		 discount
		  <input type="radio" name="grptcktData.trigger_fail_action" <s:if test='#triggerfailaction=="0"'> checked="checked"</s:if> value="0">No  </td>
	</tr>
</table>
</s:form>
<script>
	
</script>