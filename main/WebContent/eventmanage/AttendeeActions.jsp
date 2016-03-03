<%@taglib uri="/struts-tags" prefix="s" %>
<s:set name="powertype" value="powertype"></s:set>
<table width="100%"  >
<tr>
<td width="50%" valign="top">
  <table width="100%" valign="top" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top">
  <div class="box" align="left">
   <div class="boxheader">Email Attendee</div>
   <div class="boxcontent">To send mails in bulk to all your attendees for this event. Also can send mails at your scheduled time.
   </div>
   <div class="boxfooter" align="right">[<a href="EmailAttendees?eid=${eid}">Send Email</a>] </div>

  </div>
  </td>
  </tr>
  <s:if test="%{#powertype=='Ticketing'}">
  <tr height="7"></tr>
  <tr>
  <td valign="top">
   <div class="box" align="left">
    <div class="boxheader">Add Attendee</div>
    <div class="boxcontent">To add attendees manually</div>
    <div class="boxfooter" align="right">[<a href="AddAttendee?eid=${eid}&purpose=manageradd">Add Attendee</a>] </div>

   </div>
   </td>
  </tr>
  </s:if>
  <tr height="7"></tr>
  <tr>
    <td valign="top">
     <div class="box" align="left">
      <div class="boxheader">Badges</div>
      <div class="boxcontent">Design badges for your event
      </div>
      <div class="boxfooter" align="right"><a href="Badges?eid=${eid}">[Design Badges]</a> </div>
     </div>
     </td>
  </tr>
  </table>
</td>
<td width="10"></td>
<td width="50%" valign="top">
  <table width="100%" valign="top" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top">
  <div class="box" align="left">
   <div class="boxheader">Mail Blasts</div>
   <div class="boxcontent">Data table having name and status fields</div>
  </div>
  </td>
  </tr>

  </table>
</td>
</tr>
</table>