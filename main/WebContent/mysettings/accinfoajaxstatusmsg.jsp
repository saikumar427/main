<%@taglib uri="/struts-tags" prefix="s"%>
<!--div id="statusMessageBox" class="statusMessageBox">
<div style="float: left;valign: middle;">
<s:set name="msgType" var="msgType" value="%{#parameters.msgType}"/>
<s:if test="%{#msgType[0]=='passwordinfo'}">
    Password Updated
</s:if>
<s:if test="%{#msgType[0]=='contactinfo'}">
    Contact Information Updated
</s:if>
</div>
<div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>
</div-->
<s:set name="msgType" var="msgType" value="%{#parameters.msgType}"/>
<s:if test="%{#msgType[0]=='invoicemessage'}">
 <div id="statusMessageBox" class="statusMessageBox">
   <div style="float: left;valign: middle;">
     Invoice Payment successfully completed
   </div>
   <div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>
   </div>  
</s:if>
<s:else>
    Updated
</s:else>