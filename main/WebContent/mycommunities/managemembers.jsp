<%@taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %> 
<link rel="stylesheet" type="text/css" href="../css/displaytag.css">
<script>
function getfields(){
		document.getElementById("memberreport").style.display='block';
}
</script>
<s:if test="%{errorsExists==true}">
	<div class="error"><s:fielderror theme="simple" /></div><br>
</s:if>
<s:form name="memberreportform" id="memberreportform"  action="CommunityManage!getMemberManageReport" theme="simple">
<s:hidden name="groupId"/>
<input type="hidden" name="d-49489-s" value="0"/>

<table align="center">
<tr>
<td>
<s:set name="selectedVal" value="%{cReportData.selindex}"/>
<s:set name="subSelectedVal" value="%{cReportData.subSelIndex}"/>
<input type="radio" name="cReportData.selindex" value="1"  <s:if test="%{#selectedVal ==1}">checked="checked"</s:if>></td>
<td>All</td><td><td>
</tr>
<tr>
<td ><input type="radio" name="cReportData.selindex" value="2" <s:if test="%{#selectedVal ==2}">checked="checked"</s:if>></td>
<td>Name</td>
<!--  onkeyup="javascript:this.value=this.value.toUpperCase();" -->
<td><s:textfield name="cReportData.name" size="20"></s:textfield>
</td>
</tr>
<tr>
<td><input type="radio" name="cReportData.selindex" value="3" <s:if test="%{#selectedVal ==3}">checked="checked"</s:if>></td><td>Email</td>
<td><s:textfield  name="cReportData.email" ></s:textfield>
</td>
</tr>
<tr>
<td><input type="radio" name="cReportData.selindex" value="5" <s:if test="%{#selectedVal ==5}">checked="checked"</s:if>></td><td>Membership</td>
<td>
<s:set name="group" value="cReportData.membershipId"/>
<select name="cReportData.membershipId" id="groupId" >
<option value="0">None</option>
<s:iterator value="%{membershipTypes}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#group==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</tr>
<tr>
<td><input type="radio" name="cReportData.selindex" value="6" <s:if test="%{#selectedVal ==6}">checked="checked"</s:if>></td><td>Status</td>
<td>
<s:set name="group" value="cReportData.typeId"/>
<select name="cReportData.typeId" id="groupId" >
<option value="0">None</option>
<s:iterator value="%{statusTypes}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#group==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</tr>
<!--tr>
<td><input type="radio" name="cReportData.selindex" value="4" <s:if test="%{#selectedVal ==4}">checked="checked"</s:if>></td><td>Join Date</td>
<td></td>
</tr-->
<!--tr>
<td></td><td>Between</td>
<td>
<table>
<tr><td><s:textfield name="cReportData.stMonth" id="start_month_field" size="2"/>
<s:textfield name="cReportData.stDay" id="start_day_field" size="2"/>
<s:textfield name="cReportData.stYear" id="start_year_field" size="4"/>
<img src="../images/calendar_icon.gif" id="start_btn_cal" onclick="showDateCal('start')">
<span id="overlayContainer_start"></span> 
</td></tr></table>
</td>
</tr-->
<!--tr>
<td></td><td>&nbsp;and</td>
<td>
<table><tr><td>
<s:textfield name="cReportData.enMonth" id="end_month_field" size="2"/>
<s:textfield name="cReportData.enDay" id="end_day_field" size="2"/>
<s:textfield name="cReportData.enYear" id="end_year_field" size="4"/>
<img src="../images/calendar_icon.gif" id="end_btn_cal" onclick="showDateCal('end')">
<span id="overlayContainer_end"></span>
</td></tr></table>
</td>
</tr--> 
<!--tr>
<td><input type="radio" name="cReportData.selindex" value="7" <s:if test="%{#selectedVal ==7}">checked="checked"</s:if>></td><td>Due</td>
</tr-->
<!--tr>
<td></td><td><input type="radio" name="cReportData.subSelIndex" value="1" <s:if test="%{#subSelectedVal ==1}">checked="checked"</s:if>>Past due </td>
<td>
</td>
</tr-->
<!--tr>
<td></td><td><input type="radio" name="cReportData.subSelIndex" value="2" <s:if test="%{#subSelectedVal ==2}">checked="checked"</s:if>>Past due by</td>
<td>
<table><tr><td>
<s:textfield name="cReportData.subscrdaysmore" size="2"/>&nbsp;Days
</td></tr></table>
</td>
</tr--> 
<!--tr>
<td></td><td><input type="radio" name="cReportData.subSelIndex" value="3" <s:if test="%{#subSelectedVal ==3}">checked="checked"</s:if>>Due in next</td>
<td>
<table><tr><td>
<s:textfield name="cReportData.subscrdays" size="2"/>&nbsp;Days 
</td></tr></table>
</td>
</tr-->
</table>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td align="center" colspan="2"><input type="button" name="Submit" id="submitbtn" value="Submit"/>
</td></tr>
</table>
<div id="memberreport" style="display:none">
<div id="dtagdivid">
<table  border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{memberReportMap.size()}" name="attsize"></s:set>
<s:if test="%{#attsize!=0}">
<tr><td><div  STYLE="width: 760px; font-size: 12px; overflow: auto;">
<s:set name="traninfo" value="memberReportMap" scope="request"/> 
<display:table name="traninfo" id="item" export="true" requestURI="" class="dataTable">
<s:iterator value="attFields" var="itemh">
<display:column title="${itemh}" sortable="true" property='${itemh}' href="AddCommunityMember!settings?groupId=${groupId}" paramId='usrId' paramProperty="usrId"></display:column>
</s:iterator>
</display:table>
</div>
</td></tr>
</s:if>
<s:if test="%{#attsize==0 && noneFound == true}">
<tr><td align="center">None found</td></tr>
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
	document.memberreportform.submit();
	getfields();
}
getfields();
</script>