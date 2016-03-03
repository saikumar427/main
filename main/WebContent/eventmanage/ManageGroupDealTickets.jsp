
<%@taglib uri="/struts-tags" prefix="s" %>
<link rel="stylesheet" type="text/css" href="../build/menu/assets/skins/sam/menu.css" />
<link rel="stylesheet" type="text/css" href="../build/calendar/assets/skins/sam/calendar.css" />
<script type="text/javascript" src="../build/menu/menu-min.js"></script>
<script type="text/javascript" src="../js/common/calendar.js"></script>
<script type="text/javascript" src="../build/calendar/calendar-min.js"></script>
<script type="text/javascript" src="../build/container/container_core-min.js"></script>
<script type="text/javascript" src="../js/eventmanage/managegrouptickets.js"></script>
<script>
YAHOO.namespace("ebee.event");
YAHOO.namespace("ebee.groupticketdetails");


</script>
 <jsp:include page="../help/eventmanage_messages.jsp" />
<script type="text/javascript" src="ManageGroupDealTickets!populateGroupTicketJson?eid=${eid}"></script>
<div class="box" align="left">
<div class="boxheader">Group Tickets</div>

<div class="boxcontent">
<table width="100%">
<tr><td>
<div id="event_groupticketdetails_dt"></div>
</td></tr>
</table>
</div>

<div class="boxfooter">
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td><input type="button" name="Add Group Ticket" value="Add GroupTicket" id="addGroupTicketbtn"></td>
<td align="right"> [<a href="#" onClick="showtickets('${eid}')" style="a:{text-decoration:underline}" >Show/Hide Tickets</a>
<a class="helpboxlink" id="showhidehelp">
<img src="../images/questionMark.gif" /></a>]</td>
</tr>
</table>
</div>
</div>
<br>

<div class="box" align="left">
<div class="boxheader">Settings</div>
<div class="boxcontent">
<table width="100%" cellpadding="0" cellspacing="0">
<tr><td><b>Confirmation Page :</b>&nbsp;[<a href="#" onClick="ConfirmationPage('${eid}');">Edit</a>]</td></tr>

<tr><th>Confirmation Email</th></tr>
<tr><td>On Purchase       : &nbsp;   [<a href="#" onClick="OnPurchase('${eid}')" style="a:{text-decoration:underline}"> Edit</a>]</td></tr>
<tr><td>On Trigger Reach  :&nbsp; [<a href="#" onClick="OnTriggerReach('${eid}')" style="a:{text-decoration:underline}">Edit</a>]</td></tr>
<tr><td>On Trigger Fail   :&nbsp;  [<a href="#" onClick="OnTriggerFail('${eid}')" style="a:{text-decoration:underline}">Edit</a>]</td></tr>
</table>
</div>
<div class="boxfooter"></div>
</div>


<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
</div>
<div id="popinpopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>

/*Discount Details YUI Data Table Satart*/

var dt_table_dd = instantiateGroupTicketDetailsTable(YAHOO.ebee.event.Data.GroupTicketDetails,"event_groupticketdetails_dt",${eid},"${currency}","${percentageSymbol}");

function instantiateGroupTicketDetailsTable(ds, cname,eid,currency,percentageSymbol){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["ticketName","groupticketid","price","discountvalue","triggerqty","currentcycle",
                     "soldqty","approvalstatus"]
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("groupticketid");
         elLiner.innerHTML = "<div id='evtgroupticketid"+ qid +"'></div>";                   
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var myColumnDefs = [            
            {key:"ticketName", label:"Name"},
            {key:"price", label:"Price("+currency+")"},
            {key:"discountvalue", label:"Discount("+percentageSymbol+")"},
            {key:"triggerqty", label:"Trigger"},
            {key:"soldqty", label:"SoldQty"},
            {key:"currentcycle", label:"Sale Cycle"},
            {key:"approvalstatus", label:"Status"},
            {key:"Manage", label:"",formatter:"mydisplay"}            
        ];
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}
YAHOO.ebee.groupticketdetails.init = function () {
	   
	for(var i=0;i<YAHOO.ebee.event.Data.GroupTicketDetails.length;i++){
		var jsonobj = YAHOO.ebee.event.Data.GroupTicketDetails[i];
		var divid = "evtgroupticketid"+jsonobj.groupticketid;	
		
			var GroupTicketDetails_ManageMenu = [	
                    					
				{ text: "Edit", value: 1, onclick: { fn: editGroupTicket ,obj: {"eid":${eid},"groupticketid":jsonobj.groupticketid} } },
				{ text: "Delete",value: 2, onclick: { fn: deleteGroupTicket ,obj: {"eid":${eid},"groupticketid":jsonobj.groupticketid} } }
			];
			var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: GroupTicketDetails_ManageMenu, container: divid });
	}
}();
var btn = new YAHOO.widget.Button("addGroupTicketbtn", { onclick: { fn: addGrpTicket,obj:{"eid":${eid}} } });

</script>
