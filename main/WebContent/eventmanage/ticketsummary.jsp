<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<div class="box" align="left">
<div class="boxheader">Ticket Sales</div>
<div class="boxcontent">
<script type="text/javascript">
	function drawPieChart() {
    	//drawChart(eventchartdata, 'chart_tickets');
    }
      
</script>
<script>
YAHOO.namespace('event');
YAHOO.event.Data = ${jsondata};
var eventchartdata = ${chartJsondata};
var eventdate='${eventDate}';
var isrecur=${isrecur};




</script>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<s:if test="%{isrecurring==true}">
<tr>
<td>
<form id="recurringEventDateChartForm" action="Snapshot" method="post">
<s:hidden name="eid"></s:hidden>
<div id="scroll"></div>
Recurring Event Date &nbsp; <s:select list="recurDates" listKey="key" listValue="value" 
name="eventDate" headerKey="all" headerValue="All Dates" theme="simple" onchange="getRecurringEventChart()" id="datelist"/>
</form>
</td>
</tr>
</s:if>
<tr>
<s:if test="%{isrecurring==false}">
<td colspan="3" height="50">
Total Tickets Sold: <b><s:property value="totalSales.total"/> </b>(Online - <b><s:property value="totalSales.online"/></b>, Manual - <b><s:property value="totalSales.manual"/></b>)
</td>
</s:if><s:else>
<td colspan="3" height="50">
Total Tickets Sold: <b><span id="ttotal"><s:property value="totalSales.total"/></span><span id="total"></span> </b> (Online - <b><span id="tonline"><s:property value="totalSales.online"/></span><span id="online"></span></b>, Manual - <b><span id="tmanual"><s:property value="totalSales.manual"/></span><span id="manual"></span></b>)
</td>
</s:else>

</tr>

<tr><td><div id="event_ticketsummary"></div></td></tr>


<s:if test="%{totalSales.total!=0}">
<tr><td><div id="chart_tickets" style="width:100%;height:0px;"></div></td></tr>
</s:if>
</table>
</div>
<div class="boxfooter">
<s:if test="%{subMgr==null || submgr_permissions['Reports']=='yes'}">
<a id="detailedrepbtn" href="Reports?eid=${eid}" >Detailed Reports</a> 
</s:if>
<script>
var detailedrepBtn = new YAHOO.widget.Button("detailedrepbtn", { type:"link"});
/*<s:if test='%{eventDate=="" && isrecurring==true }'>
YAHOO.event.Data = {"ticketssummary":[]};
</s:if>*/
var ticketsummary_datatable = instantiateTicketsSummaryTable(YAHOO.event.Data.ticketssummary,"event_ticketsummary");
function instantiateTicketsSummaryTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["status","ticket_name","sold_qty","total_qty","onlinesales","offlinesales"]
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
                   
                   // YAHOO.util.Dom.replaceClass(elLiner.parentNode, "down", "up");
                   if(eventdate=='' && isrecur==true)
                    elLiner.innerHTML = oRecord.getData("sold_qty")+ '<br/><span class="smallestfont">Online: '+oRecord.getData("onlinesales")+', Manual: '+oRecord.getData("offlinesales")+'</span>';
                   else
                    elLiner.innerHTML = oRecord.getData("sold_qty")+ '/' + oRecord.getData("total_qty") + '<br/><span class="smallestfont">Online: '+oRecord.getData("onlinesales")+', Manual: '+oRecord.getData("offlinesales")+'</span>';
               
            };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var conf = {
        MSG_EMPTY:"&nbsp;",
        sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
    };
    var myColumnDefs;
    if(isrecur == true){
    		myColumnDefs = [
            	{key:"ticket_name", label:"Ticket Name", sortable:true},
            	{key:"sold_qty", label:"Total Sold", sortable:true, formatter:"mydisplay"}];
            	conf.sortedBy= {key:'ticket_name', dir:YAHOO.widget.DataTable.CLASS_DESC};
    }else{
    	myColumnDefs = [
    	            	{key:"status", sortable:true, label:"Status"},
    	            	{key:"ticket_name", label:"Ticket Name", sortable:true},
    	            	{key:"sold_qty", label:"Total Sold", sortable:true, formatter:"mydisplay"}];
    	            	conf.sortedBy= {key:'status', dir:YAHOO.widget.DataTable.CLASS_DESC};
    }
    
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, conf);
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);
                
    return myDataTable;
}
google.load('visualization', '1', {packages: ['corechart']});
if(document.getElementById("chart_tickets"))
	google.setOnLoadCallback(drawPieChart);

function getTicketData(json){
var data=eval('('+json.jsondata+')'); 
eventchartdata=eval('('+json.chartdata+')');
var date=document.getElementById('datelist').value;
document.getElementById('ttotal').style.display='none';
document.getElementById('tonline').style.display='none';
document.getElementById('tmanual').style.display='none';
if(date=='all'){
eventdate='';
}else 
eventdate=date;
YAHOO.event.Data=data;
var total=json.sales.total;
var online=json.sales.online;
var manual=json.sales.manual;
document.getElementById('total').innerHTML=total;
document.getElementById('online').innerHTML=online;
document.getElementById('manual').innerHTML=manual;
if(total==0)
document.getElementById('chart_tickets').style.display='none';
else
document.getElementById('chart_tickets').style.display='block';

instantiateTicketsSummaryTable(YAHOO.event.Data.ticketssummary,"event_ticketsummary");
//drawChart(eventchartdata, 'chart_tickets');
YAHOO.ebee.popup.wait.hide();
/* if(document.getElementById('scroll'))
document.getElementById('scroll').scrollTo(); */
}
</script>
</div>
</div>
