<%@taglib uri="/struts-tags" prefix="s"%>
<table width='100%' border="0" cellpadding="2" cellspacing="2">
<tr><td>
<div >
${campaddeventsinfo}
</div>
</td></tr>
<tr><td height="5"></td></tr>
<tr><td colspan='2' class='topsubheader'>Salutation Tokens:</td></tr>
<tr><td valign="top">
	<table width='100%'  cellpadding="0" cellspacing="0">
	<tr><td class='inputlabel' width='90' >First Name - </td><td  align="left"> #*FIRSTNAME*#</td></tr>
	<tr><td class='inputlabel' width='90'>Last Name - </td><td class='inputvalue' align="left">#*LASTNAME*#</td></tr>
	</table>
</td></tr>
<tr><td height="5"></td></tr>
<tr><td>Example: Dear #*FIRSTNAME*#</td></tr>
<tr><td height="5"></td></tr>
<tr><td>Active Events Tokens:</td></tr>
<tr><td valign="top">
	<table width='100%' cellpadding="0" cellspacing="0">
	<tr>
    <td>
    <jsp:include page='campuserevents.jsp' />
   </td>
   </tr> 
   <tr><td height="10"></td></tr>
   <s:set name="temp" value="userEventList"/>
   <tr><td><s:set name="temp1" value="#temp[0]"/>
   Example: Visit <s:property value="#temp1['evntinfolink']"/> to buy tickets</td></tr>
   <tr><td height="5"></td></tr>
	</table>
</td></tr>


</table>