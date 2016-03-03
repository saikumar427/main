<%@taglib uri="/struts-tags" prefix="s"%>
<s:form theme="simple" id="EventsPageContent" name="EventsPageContent" action="CustomizeEventsPageTheme!updatePreferences" method="post">
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td>Upcoming Events</td>
<td><s:text name="userThemes.upcomingEvents"></s:text></td></tr>
<tr>
<td>Past Events</td>
<td><s:text name="userThemes.pastEvents"></s:text></td></tr>
<tr>
<td>Photo</td>
<td><s:text name="userThemes.photoDisplay"></s:text></td></tr>
<tr>
<td>Profile</td>
<td><s:text name="userThemes.ProfileDisplay"></s:text></td></tr>
</table>
</s:form>
