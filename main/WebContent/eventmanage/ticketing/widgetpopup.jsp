<%@taglib uri="/struts-tags" prefix="s" %>
<!-- 
<link rel="stylesheet" type="text/css" href="/main/css/creditpopupcs.css" />
<style>

.submitbtn-style {
    height:28px;
	margin: 0;
    cursor: pointer;
	background: transparent url(/main/images/sprite.png);
	background-repeat:repeat-x;
	border:#808080 1px solid;
   	padding-left: 10px;
	padding-right: 10px;
	font: 12px/1.4 verdana,arial,helvetica,clean,sans-serif;
	valign: bottom;
}

.submitbtn-style a {
	text-decoration: none;
	color: #000;
}

.submitbtn-style:hover {
	border-color:#7D98B8;
	background-position:0 -1300px;
	text-decoration: none;
	color: #000;
}

</style>
<script>
function checkwidgetcode(){
	var widgetcode=document.getElementById("widgetcode").value;
	var customwidgetcode=document.getElementById("customwidgetcode").value;
	if(widgetcode=="selected"){
		alert("Please Select Widgetcode");
		return false;
	}else if(widgetcode=="addnew" && customwidgetcode==""){
		alert("Please Enter Widgetcode");
		return false;
	}else 
		return true;
	
}
function changewidget(value){
	 if(value=='addnew'){
	  document.getElementById("customwidget").style.display='block';
	  document.getElementById("customwidgetcode").value='';
	 }else{
	  document.getElementById("customwidget").style.display='none';
	  document.getElementById("customwidgetcode").value='';
	 }
}
</script>
<script>
function callSubmit(){
document.getElementById('widgetcode').value='wc_'+${ticketId};
document.getElementById('widgetForm').submit();
}
</script>
<form action="VolumeTicketDetails!generateWidgeCode" name="widgetForm" id="widgetForm" method="post" onsubmit="return checkwidgetcode()">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="ticketId" ></s:hidden>
<s:hidden name="widgetCode" id="widgetcode"></s:hidden>-->
<table>
<tr><td><table>
 <!--<tr>
	<td class="bigfont">Widget Code</td>
	<td>
	
	  <s:set name="wcode" value="widgetCode"/>	
	  <select name="widgetCode" id="widgetcode" onchange="changewidget(options[selectedIndex].value);">
	   <option value="selected">Select</option>
	   <option value="addnew">Add New</option>
	   <s:iterator value="%{widgetCodes}" var="type">
	   	 <s:set name="wcval" value="key"/>
		 <option value="<s:property value="key"/>" <s:if test="%{#wcode==#wcval}">selected='selected'</s:if>>${value}</option>
	  </s:iterator>
	 </select>
	</td>
 </tr>--><!--
 <tr><td></td><td><div id="customwidget" style="display:none;">
 <s:textfield name="customWidgetCode" id="customwidgetcode" size="10"></s:textfield></div></td></tr>
 <tr><td></td>
 	--><!--<td><table align="left" cellpadding="0" cellspacing="0"><tr>
 	<td>
 	  <input class="submitbtn-style" type="submit" value="Generate"  id="generate">
 	  <input class="submitbtn-style" type="button" value="Cancel"  id="cancel" onclick="parent.closeWidgetCodePopUp();">
 	</td>
 	</tr></table></td>
 </tr>-->
 <!--tr>
    <td><s:if test="%{script !=''}"><div id="scriptid">
      <s:textarea rows="3" cols="49" name="script"></s:textarea><div></s:if>
      <s:else>
      <script>callSubmit();</script>
      </s:else>
      </td>
</tr-->
 <tr>
    <td><s:textarea rows="3" cols="49" name="script" readonly="readonly" style="background-color: #FFFFFF" onClick="this.select()"></s:textarea></td>
</tr>
 
 </table></td></tr>
</table>
<!-- </form> -->




