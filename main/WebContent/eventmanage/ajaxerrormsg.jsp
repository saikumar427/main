<%@taglib uri="/struts-tags" prefix="s"%>

<s:if test="%{msgType=='paymentsettings'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='rsvpsettings'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='passwordinfo'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='contactinfo'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='eventpagecontent'}">        
       <div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='ccpaymentpage'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='copyevent'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='listeventpage'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='editeventpage'}">	
		<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='addmanualattendee'}">
	<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='accounlevelreports'}">
	<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='refundfail'}">
	<div><s:fielderror theme="simple" /></div>
</s:if>
<s:if test="%{msgType=='invoicemessage'}">
	<div><s:fielderror theme="simple" /></div>
</s:if>



