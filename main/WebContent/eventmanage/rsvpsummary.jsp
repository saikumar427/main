<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<script type="text/javascript">
function drawPieChart() {
    //drawChart(rsvpeventchartdata, 'chart_rsvp');
    }
var rsvpeventchartdata = ${chartJsondata};
</script>
<%@taglib uri="/struts-tags" prefix="s"%>
<div class="box" align="left">
<div class="boxheader">RSVP Responses</div>
<div class="boxcontent"> 
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:if test="%{isrecurring==true}">
<tr>
	<td colspan="1" width="20%">
		<form id="rsvpRecurringEventDateChartForm" action="Snapshot!getRSVPChartData?eid=${eid}" method="post">
		<div id="scroll"></div>
		<table width="60%" border="0"><tr><td width="30%">Recurring Event Date </td><td> <s:select list="recurDates" listKey="key" listValue="value" 
		name="eventDate" headerKey="all" headerValue="All Dates" theme="simple" onchange="getRSVPRecurringEventChart()"/>
		</td></tr>
		</table>
		</form>
	</td>
</tr>
</s:if>
</table>
<div id="default">
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:if test="%{eventDate==''}">
<s:if test="%{RSVPCount.size == 0}">

	<tr><td>None</td></tr>	
	</s:if>
	<s:else>
	<tr><td>
	<table border="0" width="90%">
		<s:iterator value="RSVPCount" var="data" status="status">
			<tr>
				<td width="20%"><s:property value="attendingevent" /></td>
				<td align="left"><s:property value="rsvpcount" /></td>
			</tr>
		</s:iterator>
		</table>
		</td>
	</tr>
		</s:else>
		</s:if>
		</table>
		</div>
		<table>
		<tr><td>
		<div id="ajaxcontent">
		<span id="attending"></span>
		<span id="notsure"></span>
		<span id="notattending"></span>
		</div>
		</td></tr>
		</table>
		<table width="100%">
		<tr><td colspan="1" width="100%"><div id="chart_rsvp" style="width:100%;height:0px;"></div></td></tr>
		
	</table>
</div>
</div>
	<script>
		google.load('visualization', '1', {packages: ['corechart']});
		google.setOnLoadCallback(drawPieChart);
		function UpdateRSVPtoTicketing(jsonObj){
			var jsondata = eval(jsonObj);
			UpdateRSVPtoTicketingEvent(jsonObj.eid,<s:property value="count" />);
		}
   
   function getRSVPData(json){
   rsvpeventchartdata=eval('('+json.chart+')');
    document.getElementById('default').style.display='none';
   document.getElementById('default').style.display='none';
    var attending=json.Attending;
     var notattending=json.NotAttending;
      var notsure=json.NotSure;
      if(attending==undefined)
      document.getElementById('attending').innerHTML="";
      else
      document.getElementById('attending').innerHTML="&nbsp;Attending&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+attending+"<br/>";
 	  if(notattending==undefined)
      document.getElementById('notattending').innerHTML="";
      else
      document.getElementById('notattending').innerHTML="&nbsp;Not Attending&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+notattending+"<br/>";
      if(notsure==undefined)
      document.getElementById('notsure').innerHTML="";
      else
      document.getElementById('notsure').innerHTML="&nbsp;Not Sure&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+notsure;
  	  //drawChart(rsvpeventchartdata, 'chart_rsvp');
  	/*  if(document.getElementById('scroll'))
	document.getElementById('scroll').scrollTo(); */
	YAHOO.ebee.popup.wait.hide();
   }		
		
		
	</script>