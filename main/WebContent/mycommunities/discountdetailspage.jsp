<%@taglib uri="/struts-tags" prefix="s"%>
<s:url id="discountsURL" action="showdiscounts">
	<s:param name="groupId" value="%{groupId}"></s:param>
</s:url>
<s:form name="discountform" action="DiscountDetails!save" method="post"
	theme="simple" id="discountform">
	<div id="discounterrormessages"></div>
	<s:hidden name="groupId"></s:hidden>
	<s:hidden name="discountData.id"></s:hidden>
	<table width="100%">
		<tr>
			<td>Discount Name *</td>
			<td><s:textfield name="discountData.name" theme="simple"
				size="55"></s:textfield></td>
		</tr>
		<%-- <tr>
			<td valign="top">Description</td>
			<td><s:textarea name="discountData.description" rows="1"
				cols="52" theme="simple"></s:textarea></td>
		</tr> --%>
				<tr>
			<td valign="top">Discount Codes *<br />
			<span class="smallestfont">(Comma Separated)</span></td>
			<td><s:textfield name="discountData.discountCodescsv" size="55"></s:textfield></td>
		</tr>
		
		<tr>
			<td valign="top">Discount Type</td>
			<td><s:iterator value="%{typelist}" var="type">
				<s:radio name="discountData.discountType" list="#{key: value}"
					value="%{discountData.discountType}" onclick="changeType('%{key}')" />&nbsp;<br/>
			</s:iterator></td>
		</tr>

		<tr>
			<td valign="top">Discount Value *</td>
			<td><span id="discountprefixlabel" style="display: inline">${currency}</span>
			<s:textfield name="discountData.discountVal" theme="simple"></s:textfield>
			<span id="discountsufixlabel" style="display: none">%</span></td>
		</tr>

		<tr>
<td valign="top">Total Available *</td>
<td>
<s:set name="limitType" value="discountData.limitType"/>
<input type="radio" name="discountData.limitType" value="1" id="nolimit" <s:if test="%{#limitType==1}">checked='checked'</s:if> />No Limit
<br>
<input type="radio" name="discountData.limitType" value="2" id="maxlimit"  <s:if test="%{#limitType==2}">checked='checked'</s:if> />Limit Count
<span id='limitvaluespan' >
<s:textfield name="discountData.limitValue" id="limitValue" theme="simple"></s:textfield></span>
</td>
</tr>


		<tr>

			<td valign="top">Valid for MemberShips *</td>
			<td>
			<div
				STYLE="height: 100px; width: 95%; padding-left: 10px; font-size: 12px; overflow: auto;">
			<s:iterator value="%{allitems}" var="ticket">
				<s:checkbox name="selitems" fieldValue="%{key}"
					value="%{selitems.contains(key)}" />${value}<br />
			</s:iterator></div>
			</td>
		</tr>


	</table>

</s:form>
<script>
var btn = new YAHOO.widget.Button("submitBtn", { onclick: { } });
var btn1 = new YAHOO.widget.Button("cancelBtn", { onclick: {fn: cancelThisEvent } });
function cancelThisEvent(){
	backToList();
}
changeLimitType();
changeType();
</script>
