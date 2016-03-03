<%@taglib uri="/struts-tags" prefix="s"%>

<tr><td><div STYLE="height : 100px; width: 95%; padding-left: 10px; font-size: 12px; overflow: auto; border-style:outset;  ">
<!-- 
border-style: dotted;
border-style: dashed;
border-style: solid;
border-style: double;
border-style: groove;
border-style: ridge;
border-style: inset;
border-style: outset; 
 -->
<s:iterator value="userEventList" var="evtMap">


<table width='98%'>

	<tr>
	<td class='inputlabel' width='100'>Event Name:</td>
	<td class='subheader' colspan='2'><s:property value="#evtMap['evtname']"/></td>
	</tr>

	<tr>
	<td class='inputlabel' width='100'>Event URL:</td>
	<td class='inputvalue'><s:property value="#evtMap['evntinfolink']"/></td>
	</tr>
				
	<!-- <td class='inputlabel'>Starts</td><td class='inputvalue'><s:property value="#evtMap['stdt']"/></td>
	</tr> --><tr>
	
</table>
<hr style="border-top: 1px solid rgb(204, 204, 204); border-bottom: 0px solid rgb(204, 204, 204); height: 0.1em; color: rgb(204, 204, 204); background-color: transparent; width: 100%;">
</s:iterator>
</div>
</td></tr>