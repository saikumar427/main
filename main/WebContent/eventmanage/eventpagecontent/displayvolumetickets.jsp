<%@taglib uri="/struts-tags" prefix="s"%>
<!--form id="volticketsform" action="AttendeeListDisplayFields!savevolumeTickets" method="post">
<s:hidden name="eid"></s:hidden>
<div id="volticketslist"></div>
<table border="0">
	<tr>
		<td>
	<s:select name="voltickets" list="voltickets" listKey="key" listValue="value" 
	multiple="true" size="10" style="width:200px;" theme="simple"/>
		</td>
		<td align="center" valign="middle">
			<input type="button" value="&gt;"
			 onclick="moveOptions(this.form.voltickets, this.form.selvoltickets);" /><br />
			<input type="button" value="&lt;"
			 onclick="moveOptions(this.form.selvoltickets, this.form.voltickets);" />
		</td>
		<td>
			<s:select name="selvoltickets" list="selvoltickets" listKey="key" listValue="value" 
	multiple="true" size="10" style="width:200px;" theme="simple" />
		</td>
		<td><img src="../images/up.gif" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selvoltickets'))"><br/><img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selvoltickets'))"></td>
	</tr>
</table>
</form-->
<s:form name="volticketshowhideform" action="TicketShowHide!savevolumeTickets" id="volticketshowhideform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<div id="boder">
<div style="overflow:auto;height:300px;width:500px;padding-left:5px">
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{voltickets.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
Currently there are no Volume tickets added to the event.
</s:if>
<s:else>
<tr>
<td>
Select Volume Tickets to show (Unselect to hide) in the event page.
</td>
</tr>
<tr>
<td align="right">
<a href="#" name ="CheckAll" onClick="checkAll(document.volticketshowhideform.seltickets)">Select All</a> | <a href="#" name ="UnCheckAll" onClick="uncheckAll(document.volticketshowhideform.seltickets)">Clear All</a>
</td>
</tr>
<tr>
<td>
<s:iterator value="%{voltickets}" var="ticket" >
<s:checkbox name="selvoltickets" id="seltickets" fieldValue="%{key}" value="%{selvoltickets.contains(key)}" />${value}<br/>
</s:iterator>
</td>
</tr>
</s:else>
</table>
</div>
</div>
</s:form>