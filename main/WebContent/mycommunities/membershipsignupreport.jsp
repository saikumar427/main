<%@taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %> 
<link rel="stylesheet" type="text/css" href="../css/displaytag.css">
<script>
function getfields(){
		document.getElementById("signupreport").style.display='block';
}
</script>
<s:form name="signupreportform" id="signupreportform"  action="CommunityManage!getSignupReportData">
<s:hidden name="groupId"/>
<table align="center">
<tr>
<s:set name="selectedVal" value="%{cReportData.selindex}"/>
<td><input type="radio" name="cReportData.selindex" value="1"  <s:if test="%{#selectedVal ==1}">checked="checked"</s:if>></td>
<td>Till Date</td>
</tr>
<tr>
<td ><input type="radio" name="cReportData.selindex" value="2" <s:if test="%{#selectedVal ==2}">checked="checked"</s:if>></td>
<td>Start Date</td>
<td><s:text name="cReportData.startMonth" ></s:text><s:text name="cReportData.startDay"></s:text><s:text name="cReportData.startYear"></s:text>
</td>
</tr>
<tr>
<td></td><td>End Date</td>
<td><s:text name="cReportData.endMonth" ></s:text><s:text name="cReportData.endDay"></s:text><s:text name="cReportData.endYear"></s:text>
</td>
</tr>
</table>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td align="center" colspan="2"><input type="button" name="Submit" id="submitbtn" value="Submit"/>
</td></tr>
</table>
<div id="signupreport" style="display:none">
<div id="dtagdivid">
<table  border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{signupReportMap.size()}" name="attsize"></s:set>
<s:if test="%{#attsize!=0}">
<tr><td>

<div STYLE="width: 760px; font-size: 12px; overflow: auto;">
<s:set name="traninfo" value="signupReportMap" scope="request"/> 
<s:set value="{'Membership Fee','Ebee Fee','Total Amount','Card Fee','Manager Fee','Discount','Net Fee'}" name="showTotalFields" />
<s:set value="{'Transaction ID','Date','First Name','Last Name','Membership Name','Name','Membership Fee','Ebee Fee','Total Amount','Card Fee','Manager Fee','Discount','Discount Code','Net Fee'}" name="Fields" />

<display:table name="traninfo" id="item" export="true" requestURI="" class="dataTable"  decorator="org.displaytag.decorator.CustomTotalTableDecorator">
<s:iterator value="Fields" var="itemh">
<s:if test="%{#showTotalFields.contains(#itemh)}">
<display:column title="${itemh}" total="true" format="{0,number,##,##0.00}" property="${itemh}" ></display:column> 
</s:if>
<s:else>
<display:column  title="${itemh}" sortable="true" property='${itemh}' ></display:column>
</s:else>
</s:iterator>
</display:table>
</div>
</td></tr>
</s:if>
</table>
</div>
</div>
<div style="height:20px;"></div>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var btn = new YAHOO.widget.Button("submitbtn", { onclick: { fn: reportRegSubmit } });
function reportRegSubmit(){
	formaction();
}
</script>
<script>
function formaction(){
	document.signupreportform.submit();
	getfields();
}
getfields();
</script>