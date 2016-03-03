<%@taglib uri="/struts-tags" prefix="s"%>
<s:form id="showemailattendeepopupform" name="showemailattendeepopupform" action="EmailAttendees!contentShow" method="post" theme="simple">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="blastid" id="blastid"></s:hidden>
<s:set name="buyer" value="emailAttendeesData.buyer"></s:set>
<s:set name="attendee" value="emailAttendeesData.attendee"></s:set>
<table  width="100%">
		<tr>
			<td></td>
		</tr>
		<tr>
		<td  >
		${emailAttendeesData.content} 
		<!--  <s:property value="emailAttendeesData.content" ></s:property> -->
		</td>
		</tr>
</table>
		</s:form>