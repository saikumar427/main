<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<div id="statusMessageBox" class="statusMessageBox">
<div style="valign: middle;"><img src="../images/check.png"/>
<s:set name="msgType" var="msgType" value="%{#parameters.msgType}"/>
<s:if test="%{#msgType[0] == 'upgradeevent'}">
Updated Successfully
</s:if>
<s:if test="%{#msgType[0] == 'welcomeToPaymentSettings'}">
Congrats, your event is listed now! Take your event live within seconds by updating Payment Settings here <br>and adding Tickets afterwards.
</s:if>
<s:if test="%{msgType=='customization'}">
Look & Feel Updated
</s:if>
<s:if test="%{msgType=='eventThemes'}">
Theme Updated
</s:if>
<s:if test="%{msgType=='htmlncss'}">
HTML & CSS Updated
</s:if>

<s:if test="%{msgType=='displaytickets'}">
Display Tickets Updated
</s:if>

<s:if test="%{msgType=='regword'}">
	Ticketing Words Updated
</s:if>
<s:if test="%{msgType=='RSVPword'}">
	RSVP Words Updated
</s:if>

<s:if test="%{msgType=='regemailsettings'}">
	Confirmation Email Updated
</s:if>

<s:if test="%{msgType=='confirmationpagesettings'}">
	Confirmation Page Updated
</s:if>

<s:if test="%{msgType=='paymentsettings'}">	
		<span id="updatedMsg">Payment Settings updated</span>
</s:if>


<s:if test="%{msgType=='boxofficeapps'}">	
		<span id="updatedMsg">Payment Settings updated</span>
</s:if>

<s:if test="%{msgType=='rsvpsettings'}">	
		<span id="updatedMsg"><%=I18n.getString("rsvps.updated.status.msg") %></span>
		<!-- <s:text name="rsvps.updated.status.msg"/> -->
</s:if>
<s:if test="%{msgType=='emailattendees_testmail'}">
		Test Mail is sent, please review it
</s:if>
<s:if test="%{msgType=='emailattendees_attendeemail'}">
		Email scheduled
</s:if>
<s:if test="%{msgType=='editTransaction'}">
		Transaction Tickets Updated
</s:if>
<s:if test="%{#msgType[0]=='addmanualattendee'}">
	<%=I18n.getString("aa.attendee.added.success.msg") %>
<%-- 	<s:text name="aa.attendee.added.success.msg"/> --%>
</s:if>
<s:if test="%{#msgType[0]=='addrsvpmanualattendee'}">
<%=I18n.getString("aa.attendee.added.success.msg") %>
	<%-- <s:text name="aa.attendee.added.success.msg"/> --%>
</s:if>
<s:if test="%{#msgType[0]=='accountinfo'}">
Account Info Updated
</s:if>
<s:if test='%{#msgType[0]=="Y"}'>
Enabled
</s:if>
<s:if test='%{#msgType[0]=="N"}'>
Disabled
</s:if>
<s:if test="%{msgType=='refundsuccess'}">
Refund successfully completed
</s:if>
<s:if test='%{msgType=="waitlistactivationlink"}'>
Activation link sent successfully
</s:if>
<s:if test='%{msgType=="waitlistsave"}'>
Updated successfully
</s:if>
</div>
<!-- <div class="pull-right"><a href="javascript:hidestatusmessage();"><input type="button" value="X" class="btn btn-xs btn-default" style="margin-top:-21px;"/></a></div> -->
<!-- <div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div> -->

</div>
<script>
function hidestatusmessage(){
	 //$('.alert-success').hide();
	  $('.alert-success').fadeOut();
}
</script>
