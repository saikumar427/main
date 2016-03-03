<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.smallestfont {
    color: #666666;
    font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 10px;
    font-weight: lighter;
}
</style>
<s:form name="addattendeeprofiles"  id='addattendeeprofiles' action='' method="post" theme="simple">
<s:set name="buyervec" value="profileMap['buyer']"/>
<s:set name="attribsetid" value="profileMap['attribsetid']"/>
<input type="hidden"  id='profilejsondata'  name='profilejsondata' value=""/>
<input type="hidden"  id='buyerjsondata'  name='buyerjsondata' value=""/>
<input type="hidden"  id='tid'  name='tid' value="${tid}"/>
<input type="hidden"  id='eid'  name='eid' value="${eid}"/>
<input type="hidden"  id='edate'  name='eventdate' value=""/>
<input type="hidden"  id='ticketids'  name='ticketids' value=""/>
<input type="hidden"  id='seatingenabled'  name='seatingenabled' value=""/>
<table width="100%" cellpadding="0" cellspacing="0">

<s:set name="countMap" value="TicketsQtyMap"/>

<tr><td width="100%" id="profileerr"></td></tr>
<tr>
 <td><b><s:text name="aa.reg.contact.header"/></b><br/><span class="smallestfont"><s:text name="aa.reg.contact.help.txt"/></span>
 <br/><br/>
 </td>
 </tr>

<s:set name="buyerquestions" value="#buyervec['buyerQues']"/>
  <s:set name="id" value="1"/>
  
<s:iterator value="#buyerquestions" var="bquesobj">
  <tr>
 <td width="100%">
   <div id='buyer_<s:property  value="#bquesobj['qId']"/>_<s:property value="#id"/>'>
</div>
<div style="height:8px"></div>
</td>
 </tr>
 
</s:iterator>

<s:set name="customvec" value="profileMap['customProfile']"/>
<tr height="10"><td></td></tr>

<s:iterator value="#customvec" status='stat'  var ="customobj">
<s:set name='ticketid'  value="#customobj['selectedTicket']" />
<s:set name='tickettype'  value="#customobj['type']" />
<s:set name='count1'   value="TicketsQtyMap[#ticketid]"/>
<s:set name='isBaseProfile'   value="baseProfileTickets[#ticketid]"/>
<input type='hidden' id='<s:property  value="#ticketid"/>_type' value='<s:property  value="#tickettype"/>' />
<s:iterator  status='x' value="(#count1).{#this}" >
<tr>
 <td width="100%">

<b><s:property  value="#customobj['ticketName']" escape="false"/> (<s:text name="aa.attendee.lbl"/> #<s:property value="#x.count" />  <s:text name="aa.profile.lbl"/>) 
<s:if test='(#stat.count)==1&&(#x.count==1)&&(#isBaseProfile=="Y")'>
<a href='javascript:;' onClick=copyByuerData('<s:property   value="#ticketid"/>')><s:text name="aa.copyfrom.regcontact.lnk.lbl"/></a><br/><br/>
</s:if>
 </b></td>
 </tr>
<s:set name='questions'  value="#customobj['questions']"/>
<s:iterator value="#questions" var="questionobj">
<tr>
 <td width="100%">
   <div width="100%" id='<s:property  value="#ticketid"/>_<s:property  value="#questionobj['qId']"/>_<s:property value="#x.count" />'>
    </div>
    <div style="height:8px"></div>
    </td>
	 </tr>
</s:iterator>
</s:iterator>
</s:iterator>
</table>


<div class="col-xs-12 text-center">
	 <div id="profilebuttons"><span>
	<input type="button"  id='profilecontinue' name='continue' value="<s:text name="global.continue.btn.lbl"/>" class="btn btn-primary" onclick="validateProfiles();"/></span>
	<span>
	<input type="button"   id='profilepageBack' name='back' value="<s:text name="global.back.btn.lbl"/>" class="btn btn-primary" onClick='getTicketsPage()'/>
</span>
</div>
<div >
<span id="processimg" ></span>
</div>
</div>



<%-- 
<table  border="0" style="position:relative;">
 <tr>
 <td>
 <div id="profilebuttons" style="position:relative;"><span style="position:absolute;top:40px;left:300px;">
	<input type="button"  id='profilecontinue' name='continue' value="Continue" class="btn btn-primary" onclick="validateProfiles();"/></span>
	<span style="position:absolute;top:40px; left:390px;">
	<input type="button"   id='profilepageBack' name='back' value="Back" class="btn btn-primary" onClick='getTicketsPage()'/>
</span>
</div>
<div style="position:relative;">
<span id="processimg"style="position:absolute;top:40px;left:300px;" ></span>
</div>
</td></tr>
</table> --%>
</s:form>
