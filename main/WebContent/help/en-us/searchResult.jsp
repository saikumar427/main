<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<div class="col-md-12">

<s:set name="sorttype" value="%{sortedtype}"></s:set>
<s:if test="%{eventlist.size>0}">
<div  align="left">

			<table id="eventsearch" width="100%">	
<s:iterator value="eventlist" var="evetat"
			status="eventStatus">
			
			
			
			<s:set name="cnt" value='%{#eventStatus.index}'></s:set>
			<s:if test="%{#cnt<50}">
			<tr class="edata" style="display:none">
			<td width="100%">
			<div style="cursor: pointer;background-color:#EDEDED;padding-bottom: 12px;padding-top: 12px;" onmouseover="this.style.backgroundColor='#DBDDDE'" onmouseout="this.style.backgroundColor='#E5E5E5'">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr >
				<td width="12%">
				<s:set name="config" value="#evetat['configid']"/>
				<s:set name="configmap" value="configMap[#config]"/>
				<s:set name="hostmap" value="hosetedMap[#config]"/>
				<s:set name="venue" value="#evetat['venue']"/>
				<s:set name="address1" value="#evetat['address1']"/>
				<s:set name="address2" value="#evetat['address2']"/>
				<s:set name="city" value="#evetat['city']"/>
				<s:set name="country" value="#evetat['country']"/>
				<a href="<s:property value="#evetat['eventid']" />" target="_blank" style="text-decoration: none">
				<s:if test="#configmap['event.eventphotoURL']">
				<div style="padding-left:10px"><img src="<s:property value="#configmap['event.eventphotoURL']"/>" width="90px" height="90px"/>
				</div>
				</s:if><s:else>
				<s:if test="#configmap['event.eventphotoURL'].contains('http')">
				<div style="padding-left:10px"><img src="<s:property value="#configmap['event.eventphotoURL']"/>" width="90px" height="90px"/>
				</div>
				</s:if><s:else>
				<div style="padding-left:10px"><img src="/main/images/nopic.gif" width="90px" height="90px"/>
				</div>
				</s:else>
				</s:else></a>
				</td>
				<td width="88%" align="left" class="featuredeventsheader" style="padding-bottom:20px">
					<b><a href="<s:property value="#evetat['eventid']" />" target="_blank" style="text-decoration: none"><h2><s:property value="#evetat['eventname']" /></h2></a></b>
					<s:property value="#evetat['stfuldate']" /><br/>
					<s:if test="%{#venue!=''}">
					<s:property value="#evetat['venue']" />,
				</s:if>
				<s:if test="%{#address1!=''}">
					<s:property value="#evetat['address1']" />,
				</s:if>
				<s:if test="%{#address2!=''}">
					<s:property value="#evetat['address2']" />,
				</s:if>
				<s:if test="%{#city!=''}">
					<s:property value="#evetat['city']" />,
				</s:if>
				<s:if test="%{#country!=''}">
				<s:property value="#evetat['country']"/>
				</s:if>
				</td>
			<!--<td width="19%" valign="top">
			<s:property value="#evetat['stdate']" />
			</td>	
				
			--><!--<td align="left" width="13%">
			<table align="right"><tr><td>
			<div class="buyticketssubmit">
			
			<input type="button" id="<s:property value="#evetat['eventid']" />"
			class="buyticketsbutton" onclick='validateTickets(this);' value="Register" id="orderbutton" name="submit"></div>
			</td></tr></table>
			</td>
			--></tr>
			
		</table>
		</div>
		<br/>
		</td></tr>
		</s:if>
</s:iterator>
</table>
<br/>
</div>
</s:if>
<div id="scroll"></div>
<table width="100%" >
<tr>
<td width="35%" align="left">
<s:if test="%{eventlist.size>0}">
<font color="grey"> Sort By</font> <s:select id="sort" list="sortlist" name="sortedtype" listKey="key" listValue="value" onchange="sort();" theme="simple" cssStyle="color:grey"></s:select>
</s:if>
</td>
<td align="right" width="40%">
<s:if test="%{eventlist.size>0}">
<s:if test="%{eventlist.size>50}"><font color="grey">50 Results</font></s:if>
<s:else><font color="grey"><s:property value="%{eventlist.size}"/> Results</font></s:else>
</s:if>
</td>
<td width="25%" align="right">
<s:if test="%{eventlist.size>0}">
<span id='pageNavPosition2'></span>
</s:if>
</td></tr></table>

<s:if test="%{eventlist.size==0}">
<s:if test="%{searchcontent!=''}">
<div style="padding-left:4px">No events found!</div>
</s:if>
</s:if>

<script>
var cont=0;
var pcount=0;
jQuery("#eventsearch .edata").each(function(){
	if(cont%20==0 && cont>1) pcount=pcount+1;
	jQuery(this).attr("class","edata"+pcount);
	cont++;
});
function pageselectCallback(page_index, jq){
document.getElementById('pageNavPosition2').focus();
jQuery("[class*=edata]").each(function(){
	jQuery(this).css("display","none");
});
jQuery("#eventsearch .edata"+page_index).each(function(){	
	jQuery(this).css("display","table-row");
});
return false;
}
var num_of_entries=5;
var num_entries=0;
	 if(document.getElementById('eventsearch'))
	num_entries=document.getElementById('eventsearch').rows.length;
jQuery("#pageNavPosition2").pagination(num_entries/20, {
	callback: pageselectCallback,
	items_per_page:1 // Show only one item per page
});
function validateTickets(link){
window.open(link.id,'_blank');
}
</script>