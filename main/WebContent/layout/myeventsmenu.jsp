<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<script>
function openMenu(divname){
document.getElementById("attendeepage").style.display="none";
document.getElementById("eventpage").style.display="none";
document.getElementById(divname).style.display="block";
document.getElementById("maincontent").innerHTML=divname;
}
</script>
<s:set name="actionName" value="%{#attr['currentaction']}"/>

<a href="createevent" class="<s:if test="%{#actionName=='createevent'}" >activemenu</s:if><s:else>treehead</s:else>">Create Event</a>
                <a href="home" class="<s:if test="%{#actionName=='Snapshot'}" >activemenu</s:if><s:else>treehead</s:else>">Summary</a>
                <a href="active" class="<s:if test="%{#actionName=='active'}" >childactive</s:if><s:else>childtreemenu</s:else>">Active Events</a>
                <a href="closed" class="<s:if test="%{#actionName=='closed'}" >childactive</s:if><s:else>childtreemenu</s:else>">Closed Events</a>
                <a href="suspended" class="<s:if test="%{#actionName=='Preview'}" >childactive</s:if><s:else>childtreemenu</s:else>">Suspended Events</a>
                <a href="groupevents" class="treehead">Group Events</a>
                <a href="creategroupevent" class="<s:if test="%{#actionName=='active'}" >childactive</s:if><s:else>childtreemenu</s:else>">Create Events Group</a>
                <a href="streamer" class="treehead">My Events Streamer</a>
                <a href="publicpage" class="treehead">My Events Public page</a>
                <a href="themes" class="treehead">My Events Themes</a>
                
                