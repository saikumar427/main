<%@taglib uri="/struts-tags" prefix="s"%>
<link href="/main/css/style.css" type="text/css" rel="stylesheet">
<div id="statusMessageBox" class="statusMessageBox">
<div style="float:left;valign:middle;">
<img src="../images/check.png"/>
<s:set name="msgKey" var="msgKey" value="%{#parameters.msgKey}"/>
<s:if test="%{#msgKey[0]=='transactionids'}">
		Transction ID(s) information sent to your registered Email successfully
</s:if>
<s:if test="%{#msgKey[0]=='tickets'}">
		Ticket(s) confirmation email sent to your email address successfully. Make sure to check your Spam/Bulk folder if you don't find confirmation email in your Inbox.
</s:if>
<s:if test="%{#msgKey[0]=='login help1'}">
		Sorry. This request can't be processed at this time
</s:if>
<s:if test="%{#msgKey[0]=='login help2'}">
		Your login information has been sent to your Email.
</s:if>
<s:if test="%{#msgKey[0]=='submgrchangepwd'}">
		Your password have been updated successfully.
</s:if>
</div>
<!--<div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>-->
</div>
