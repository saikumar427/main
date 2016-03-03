<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.form-control{
	margin-bottom:10px;
}
</style>
<s:form name="addrsvpattendeeprofiles"  id='addrsvpattendeeprofiles' action='' method="post" theme="simple">

<s:set name="attribsetid" value="profileMap['attribsetid']"/>

<input type="hidden"  id='transactionjsondata'  name='transactionjsondata' value=""/>
<input type="hidden"  id='responsejsondata'  name='responsejsondata' value=""/>
<input type="hidden"  id='eid'  name='eid' value="${eid}"/>
<input type="hidden"  id='sure'  name='sure' value="${sure}"/>
<input type="hidden" id="eventdate" name="eventdate" value="${eventdate}">
<s:set name='count1'   value="sureQty"/>

<table width="100%" cellpadding="0" cellspacing="0">
<tr><td width="100%" id="profileerr"></td></tr>
<s:set name="surequestions"  value="sureattribMap['SurecustomProfile']"/>


<s:iterator  status='x' value="(#count1).{#this}" >
<s:if test="#x.count!=1">
<tr><td height='10'></td></tr>
</s:if>
<tr>
 <td><b><s:text name="aa.profile.lbl"/> <s:if test="%{sure>1}">#<s:property value="#x.count" /></s:if></b>

<s:if test="#x.count==1">
<br/> 
  <span class="smallestfont"><s:text name="aa.reg.contact.help.txt"/></span></s:if>


 </td>
 </tr>

<s:iterator value="sureattribMap['SurecustomProfile']" var="questionobj">

<tr>
 <td width="100%">
   <div id='s_<s:property  value="#questionobj['qId']"/>_<s:property value="#x.count" />'>
    </div>
    </td>
	 </tr><tr><!-- <td  height='10'></td></tr> -->
	 
</s:iterator>

</s:iterator>
 
<s:if test="%{statmap['translevelquestions']=='YES'}">
<tr><td height='10'></td></tr>

<tr>
 <td><b><s:text name="aa.rsvp.other.info.header"/></b></td></tr>
</s:if>
<s:iterator value="attribMap['customProfile']" var="transquestionobj">

<tr>
 <td width="100%">
   <div id='p_<s:property  value="#transquestionobj['qId']"/>'>
    </div>
    </td>
	 </tr>
</s:iterator>

</table>
<div class="col-sm-12" style="  margin-top: 15px;">
	<div class="text-center" id="profilebuttons">
		<input type="button"  id='profilecontinue' name='continue' class="btn btn-primary" value="<s:text name="global.submit.btn.lbl"/>" onclick="validateRSVPProfiles();"/>
		<input type="button"  id='profilepageBack' name='back' class="btn btn-primary" value="<s:text name="global.back.btn.lbl"/>" onclick="getRSVPPage();"/>
	</div>
	<div class="text-center">
		<div id="processimg"></div>
	</div>
</div>

<%-- <table width="100%" align='center' cellpadding="0" cellspacing="0">
<tr>
 <td>
 <div id="profilebuttons" style="position:relative;">
 <span style="position:absolute;top:40px;left:300px;">
	<input type="button"  id='profilecontinue' name='continue' class="btn btn-primary" value="Submit" onclick="validateRSVPProfiles();"/>
</span>
<span style="position:absolute;top:40px; left:390px;">
<input type="button"  id='profilepageBack' name='back' class="btn btn-primary" value="Back" onclick="getRSVPPage();"/>
</span>
</div>
<div style="position:relative;">
<span id="processimg"style="position:absolute;top:40px;left:300px;" ></span>
</div>
</td></tr>
</table> --%>
</s:form>
