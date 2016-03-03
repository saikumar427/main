<%@taglib uri="/struts-tags" prefix="s" %>
<s:url id="discountsURL" action="ManageDiscounts">
<s:param name="eid" value="%{eid}"></s:param>
</s:url>
<script>
function changeType(){
var selectedtype='';
options=document.forms['discountform'].elements['discountData.discountType'];
for(i=0;i<options.length;i++){
opt=options[i];
if(opt.checked) selectedtype=opt.value;
}
if(selectedtype=='ABSOLUTE'){
document.getElementById('discountprefixlabel').style.display='inline';
document.getElementById('discountsufixlabel').style.display='none';
}else{
document.getElementById('discountprefixlabel').style.display='none';
document.getElementById('discountsufixlabel').style.display='inline';
}
}
function backToList(){
window.location.href='${discountsURL}';
}
function changeLimitType(){
idx=document.getElementById('limitselector').selectedIndex;
if(idx==2){
document.getElementById('limitvaluespan').style.display='inline';
}else{
document.getElementById('limitValue').value="";
document.getElementById('limitvaluespan').style.display='none';
}
}
</script>
<s:form name="discountform" action="DiscountDetails!save" method="post" theme="simple">
<s:hidden name="eid"></s:hidden>
<s:hidden name="discountData.id"></s:hidden>
<br/>
<div class="taskbox">
<span class="taskheader">Discount Details</span><br/>
<div>
<s:fielderror cssErrorClass="errorMessage" theme="simple">
</s:fielderror>
</div>
<table width="100%" >
<tr>
<td >Discount Name *</td>
<td><s:textfield name="discountData.name" theme="simple" size="43"></s:textfield></td>
</tr> 
<tr>
<td valign="top">Description</td>
<td><s:textarea name="discountData.description"  rows="5" cols="40" theme="simple"></s:textarea></td>
</tr>

<tr>
<td valign="top">Discount Type</td>
<td><s:iterator value="%{typelist}" var="type">
<s:radio name="discountData.discountType" list="#{key: value}" 
value="%{discountData.discountType}" 
onclick="changeType('%{key}')" />&nbsp;<br/>
</s:iterator>
</td>
</tr>

<tr>
<td valign="top">Discount Value *</td>
<td>
<span id="discountprefixlabel" style="display:inline">${currency}</span>
<s:textfield  name="discountData.discountVal" theme="simple"></s:textfield>
<span id="discountsufixlabel" style="display:none">%</span>
</td>
</tr>

<tr>
<td valign="top">Discount Limit *</td>
<td>
<s:set name="limitType" value="discountData.limitType"/>
<select name="discountData.limitType" id="limitselector" onchange="changeLimitType()">
<option value="">--Select Limit Type--</option>
<s:iterator value="%{limitTypelist}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#limitType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
<span id='limitvaluespan'>
<s:textfield name="discountData.limitValue" id="limitValue" theme="simple"></s:textfield></span>
</td>
</tr>

 
<tr>
<td valign="top">Codes List (Comma Separated) *</td>
<td><s:textarea name="discountData.discountCodescsv"  rows="5" cols="40" theme="simple"></s:textarea></td>
</tr>
<tr>

<td valign="top">Valid for MemberShips *</td>
<td><s:iterator value="%{alltickets}" var="ticket">
<s:checkbox name="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}" />${value}<br/>
</s:iterator>
</td>
</tr>

<tr>
<td colspan="2" align="center">
<s:submit theme="simple" id="submitBtn"></s:submit>
<input type="button" value="Cancel" id="cancelBtn" onclick="backToList();"  />
</td>
</tr>

</table>
</div>
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
