<script type="text/javascript" src="../js/snapshot/snapshot.js"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="/help/eventmanage_messages.jsp"></jsp:include>
<div class="box" align="left">
<div class="boxheader">Facebook Context URL</div>
<div class="boxcontent">
	<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
		<s:if test="%{powertype=='RSVP'}">
		<tr ><td colspan='2'><script>setHelpContent("rsvp_Integrationlinks_fburl_helpcontent")</script></td></tr>
		</s:if>
		<s:else>
		<tr ><td colspan='2'><script>setHelpContent("Integrationlinks_fburl_helpcontent")</script></td></tr>
		</s:else>	 
	 	<tr ><td colspan="2"><textarea cols="80" rows="2" onClick="this.select()"><s:text name='eventData.faceBookTicketingPageURL'></s:text></textarea></td></tr>
		<tr><td colspan="2" height="5"></td></tr>
     </table>
</div> 
</div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
