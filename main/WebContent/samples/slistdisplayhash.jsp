<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<link rel="stylesheet" type="text/css" href="../css/displaytag.css">

<s:set value="{#{'make1':'Ford', 'make2':'Honda'},#{'make1':'Toyota', 'make2':'Benz'}}" name="eventsListtemp2"></s:set>
<s:set value="{'make1', 'make2'}" name="attribs" />
<display:table name="eventsListtemp2" id="item"  requestURI="" class="dataTable" >
<s:iterator value="attribs" var="itemh">
<display:column title="${itemh}" sortable="true" property='${itemh}' ></display:column>
</s:iterator>

</display:table>
