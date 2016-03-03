<%@taglib uri="/struts-tags" prefix="s"%>
YAHOO.events.Data = {
 
    active:[
    <s:iterator value="%{suspendedEventsList}" var="event" status="rowstatus">
  <s:if test="#rowstatus.first == true"></s:if>
    <s:else> ,</s:else>
      
    {title:"${event.eventName}",id:${event.eventId},startdate:"02-25-2010",enddate:new Date(1980, 2, 24),action:"Manage"}
    
  </s:iterator>
     ],
    closed:[
    <s:iterator value="%{suspendedEventsList}" var="event" status="rowstatus">
  <s:if test="#rowstatus.first == true"></s:if>
    <s:else> ,</s:else>
   {title:"${event.eventName}",id:${event.eventId},startdate:new Date(1980, 2, 24),enddate:new Date(1980, 2, 24),action:"Manage"}
    
  </s:iterator>
  ],
    suspended:[
     <s:iterator value="%{suspendedEventsList}" var="event" status="rowstatus">
  <s:if test="#rowstatus.first == true"></s:if>
    <s:else> ,</s:else>
   {title:"${event.eventName}",id:${event.eventId},startdate:new Date(1980, 2, 24),enddate:new Date(1980, 2, 24),action:"Manage"}
    
  </s:iterator>
  ]
    
};

YAHOO.eventgroups.Data = {groups:[
<s:iterator value="%{groupEvents}" var="gevents" status="rowstatus">
<s:if test="#rowstatus.first == true"></s:if><s:else> ,</s:else>
{title:"${gevents['group_title']}",count:${gevents['eventscount']},id:${gevents['event_groupid']},action:"Manage"}
</s:iterator>]};

