<%@taglib uri="/struts-tags" prefix="s"%>
<table border="1">
<tr>
<td>Subject</td>
<td>Blasted on Date</td>
<td>Recipient Mailing Lists</td>
<td>Number of Recipients</td>
<td>Number of Emails Opened</td>
<td>Number of Emails Bounced</td>
<td>Click-Throughs</td>
</tr>
<tr>
<td>${emailScheduleData.campName}</td>
<td>${emailScheduleData.campScheduledDate}</td>
<td>${listNamesofCampaign}</td>
<td>${blastReportData["SENT"]}</td>
<td>${blastReportData["OPEN"]}</td>
<td>${blastReportData["BOUNCE"]}</td>
<td>${blastReportData["CLICK"]}</td>
</tr>
</table>