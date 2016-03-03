<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" language="JavaScript" src="../js/states.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/categories.js"></script>
<s:form name="communitycreate" action="CommunitesAction!saveCommunityDetails" method="post" theme="simple" id="communitycreate">
<s:hidden name="clubInfo.clubId"/>
<div id="communityerrormessages"></div>
<table width="600" >
<tr>
<td >Community Name *</td>
<td><s:textfield name="clubInfo.clubName" theme="simple" size="55"></s:textfield></td>
</tr> 
<tr>
<td >URL *</td>
<td><s:text name="servername"></s:text>/community/<s:textfield name="clubInfo.communityURL" theme="simple" ></s:textfield></td>
</tr> 
<tr>
<td >Category * </td>
<td align="left" >
<s:select label="Select Category" name="clubInfo.category" id='category' headerKey="1"
headerValue="-- Select Category --"	list="categorytype" />
</td>
</tr>
<tr>
<td >City *</td>
<td><s:textfield name="clubInfo.city" theme="simple" size="55"/></td>
</tr>
<tr>
<td valign="top" >Country *</td>
<td>
<s:select label="Select Country" name="clubInfo.country" id='hcountry' headerKey="1"
headerValue="-- Select Country --"
list="countrylist" listKey="key" listValue="value"  />
</td>
</tr>
<tr>
<td valign="top">Description *</td>
<td><s:textarea name="clubInfo.description"  rows="5" cols="52" theme="simple"></s:textarea></td>
</tr>
</table>

</s:form>