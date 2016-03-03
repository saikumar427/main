<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel='stylesheet' type='text/css' href='http://eventbee.com/home/css/pagination.css' />
<script type="text/javascript" src="http://eventbee.com/home/js/customfonts/cufon-yui.js"></script>
<script type='text/javascript' language='JavaScript' src='/main/js/jquery.pagination.js'></script>
<script src="http://eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
<%-- <script type="text/javascript">

Cufon.replace('.featuredeventsheader h2');</script> --%>

</head>
<body>

<style>

.featuredeventsheader h2 {
margin-top:0px;
margin-bottom:0px;
font-family: Myraid Pro;
color:#3a3a3a;
font-size: 18px;
font-weight: 1200;
}
  #promotionsbox{
  font-family:Lucida Grande,Lucida Sans Unicode,sans-serif;
font-size:12px;
color: black;
line-height: 20px;
  }

#promotionsbox a{
color: blue;
text-decoration:none;
}

  .pg-normal {
                
                font-weight: normal;
                text-decoration: none;    
                cursor: pointer;
				background: none repeat scroll 0 0 #FFFFFF;
				border-color: #999999;
				color: #999999;
				border: 1px solid 
				margin-bottom: 5px;
				margin-right: 5px;
				min-width: 1em;
				padding: 0.3em 0.5em;
				text-align: center;
            }
            .pg-selected {
                font-weight: bold;        
                cursor: pointer;
				background: none repeat scroll 0 0 #2266BB;
				border: 1px solid #AAAAEE;
				color: #FFFFFF;
    margin-bottom: 5px;
    margin-right: 5px;
    min-width: 1em;
    padding: 0.3em 0.5em;
    text-align: center;
            }
  .taskcontent .td{
  padding-bottom:10px; 
  }

.searchform {
    background: transparent;
    border: 0px solid rgba(0, 0, 0, 0.25);
    border-radius: 6px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.4) inset, 0 1px 0 rgba(255, 255, 255, 0.1);
    color: #FFFFFF;
    position: relative;
	#padding:3px;
    margin-bottom:20px;
}

.searchform .icon-search {
    background: url(/main/images/home/icon_search.png) no-repeat;
	border: 0px;
    bottom: 3px;
    color: #FFFFFF;
    padding: 10px;
    position: absolute;
    right: 3px;
    cursor:pointer;
    top:100px;
	top:11px;
}

.visuallyhidden {
    border: 0 none;
    clip: rect(0px, 0px, 0px, 0px);
    height: 1px;
    margin: -1px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    width: 1px;
}


.searchform input {
    background: transparent;
	border: 0px solid #ccc;
    box-shadow: none;
    font: 15px "Lucida Grande",Lucida,Verdana,sans-serif;
    margin: 0;
    padding: 5px 10px;
    text-decoration: none;
   width: 100%;
   margin:4px;
}

.searchform input:-moz-placeholder {
    color: #ccc;
}

.input-group {
border-top-left-radius: 0px;
}
 </style>


<div class="row" style="margin-left:-30px;padding-top:25px;">
<div class="col-md-12">


<form name="searchevent" action="search" method="post" class="searchform" id="eventsearchform">

<div class="input-group">
<input type="text"  name="searchcontent" id="val" placeholder="Enter event name or venue...">
<span class="input-group-btn">
<button class="btn btn-primary" type="button" id="searchevtbtn" onclick="searchevt();">Find Events</button>
</span>
</div>




<!--<button class="icon-search" onclick="searchevt();" type="button"></button>
<label>
		<span class="visuallyhidden">Enter Event Name or Venue</span>
		<input type="text" placeholder="Enter event name or venue!" value="" name="searchcontent" id="val" size="150">
	</label>
--></form>



<s:set name="sorttype" value="%{sortedtype}"></s:set>
<s:if test="%{eventlist.size>0}">
<div  align="left">

			<!--<div class="boxheader"><span style="padding-left:25px">Event</span> 
			<span style="padding-left:580px">Date</span>&nbsp;
			<span style="paddig-bottom:10px">
			<s:if test="%{#sorttype=='desc'}">
			<img src="/main/images/home/down_small_icon.png" onclick="call('asc')"  style="cursor: pointer;"></img>
			</s:if><s:else>
			<img src="/main/images/home/up_small_icon.png" onclick="call('desc')"  style="cursor: pointer;"></img>
			</s:else>
			</span>
			</div>
	--><table id="eventsearch" width="100%">	
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

</div></div>

</body>
</html>

<script>


function sort(){
var sortkey=document.getElementById('sort').value;
document.searchevent.action='search?sortedtype='+sortkey;
document.searchevent.submit();

}


/*if(document.getElementById('pageNavPosition2')){
if(document.getElementById("eventsearch")){
        var pager = new Pager('eventsearch',20); 
        pager.init(); 
        //pager.showPageNav('pager', 'pageNavPosition'); 
         pager.showPageNav('pager', 'pageNavPosition2'); 
         pager.showPage(1);
		}
		}*/
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
function searchevt(){
var str=document.getElementById('val').value;
str=str.replace(/^\s+|\s+$/g,'');
if(str.length<=2){
alert("Please Enter atleast 3 characters to search");
}else{
document.getElementById('val').value=str;
document.searchevent.submit();
}
}

function call(val){
document.getElementById('stype').value=val;
document.search.submit();
}
call2();
function call2(){
var serach='${searchcontent}';
document.getElementById('val').value=serach;
}

function openReg(link){
window.open(link,'_blank');
}

</script>

