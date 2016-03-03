<%@taglib uri="/struts-tags" prefix="s" %>
<s:set name="powertype" value="powertype"></s:set>
<table width="800"  >
<tr>
<td width="50%" valign="top">
 <div class="taskbox">
 <span class="taskheader">Event Page</span>
  <table width="100%" valign="top" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top">
  <div class="box" align="left">
   <div class="boxheader">Look & Feel</div>
   <div class="boxcontent">To Customize your event
   </div>
   <div class="boxfooter" align="right">[<a href="EventCustomization?eid=${eid}">Configure</a>] </div>

  </div>
  </td>
  </tr>
  <tr height="7"></tr>
  <tr>
  <td valign="top">
   <div class="box" align="left">
    <div class="boxheader">Theme HTML & CSS</div>
    <div class="boxcontent">Here you can customize your event by editing HTML & CSS</div>
    <div class="boxfooter" align="right">[<a href="HTMLnCSSCustomization?eid=${eid}">HTML & CSS</a>] </div>

   </div>
   </td>
  </tr>
  <tr height="7"></tr>
  <tr>
    <td valign="top">
     <div class="box" align="left">
      <div class="boxheader">Page Content</div>
      <div class="boxcontent">Here you can set content to your event like photo, logo and message etc.
      </div>
      <div class="boxfooter" align="right">[<a href="EventPageContent?eid=${eid}">Set Page Content</a>] </div>
     </div>
     </td>
  </tr>
  <s:if test="%{#powertype=='Ticketing'}">
  <tr>
  <td valign="top">
	<div class="box" align="left">
	<div class="boxheader">Displaying Tickets</div>
	<div class="boxcontent">Here you can set display options to your tickets.
	</div>
	<div class="boxfooter" align="right">[<a href="DisplayTickets?eid=${eid}">Manage Tickets Display</a> ]</div>
	</div>
  </td>
  </tr>
  </s:if>
  </table>
 </div>
</td>
<td width="10"></td>
<td width="50%" valign="top">
 <div class="taskbox">
 <span class="taskheader">Registration Page</span>
  <table width="100%" valign="top" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top">
  <div class="box" align="left">
   <div class="boxheader">Wordings</div>
   <div class="boxcontent">All the wordings in registration flow can be customized as per your needs. By default, Eventbee displays simple US English wordings most commonly used </div>
   <div class="boxfooter" align="right">
    <s:if test="%{#powertype=='Ticketing'}">
   [<a href="RegWordCustomize?eid=${eid}">Configure Wordings</a> ]
   </s:if>
   <s:else>
   [<a href="RSVPWordCustomize?eid=${eid}">Configure Wordings</a> ]
   </s:else>
   </div>
  </div>
  </td>
  </tr>
  <tr height="7"></tr>
  <tr>
  <td valign="top">
    <div class="box" align="left">
     <div class="boxheader">Confirmation Email</div>
     <div class="boxcontent">Here you can customize the confirmation email for attendee </div>
     <div class="boxfooter" align="right">     
   [<a href="RegEmailSettings?eid=${eid}">Customize Email</a> ]    
     </div>
    </div>
  </td>
  </tr>
  </table>
 </div>
</td>
</tr>
</table>