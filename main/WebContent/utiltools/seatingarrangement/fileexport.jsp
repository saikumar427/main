
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<link rel="stylesheet" type="text/css" href="../css/displaytag.css">



<%
String section_id=request.getParameter("section_id");
String venue_id=request.getParameter("venue_id");
String venuename=request.getParameter("venuename");

String url="/main/utiltools/seatingarrangement/sectionsmanage.jsp?section_id="+section_id+"&venue_id="+venue_id+"&venuename="+venuename;
%>

<a href="<%=url%>">Back</a>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >

<s:set value="%{rowmap.size()}" name="rowsize"></s:set>
<s:if test="%{#rowsize!=0}">
	<tr><td><div STYLE="width: 760px; font-size: 12px; overflow: auto;">
<s:set name="seatsinfo" value="rowmap" scope="request"/>
<display:table name="seatsinfo" id="item" export="true" requestURI="" class="dataTable" pagesize="50"  size="10">
<s:iterator value="Fields" var="itemh">
<display:column  title='${itemh}' sortable="true" property='${itemh}'>
</display:column>
</s:iterator>
</display:table>
</div>
</td></tr>
</s:if>


</table>
