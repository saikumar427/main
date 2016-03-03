<script type="text/javascript" src="../js/myevents/eventsgroup.js"></script>
<script src="../js/myevents/manageeventsgroup.js" type="text/javascript"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<script>
YAHOO.namespace('eventgroup');
YAHOO.eventgroup.Data = ${jsonData};

function getThisCustomUrlBlock(event,jsonObj){
	var jsonData = eval(jsonObj);
	var eid = jsonData.gid;
	var url='ManageGroup!customURL?gid='+eid;
	loadURLBasedWindow(url, handlecustomURLSuccess);
}
var handlecustomURLSuccess = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "Events Group Custom URL", setCustomURL, handleCancel);	
}

</script>


<table cellspacing="0" cellpadding="0" border="0" width="100%">
<tr>
<td valign="top" width="644">
<table cellspacing="0" cellpadding="0" width="100%">
<tr><td>
<div class="box" align="left">
<div class="boxheader">Events In The Group</div>
<div class="boxcontent">
<div id="backgroundPopup" ></div>
<div id="grpeventstable" style="width:100%"></div>
 
</div>
<div class="boxfooter">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>		
		<td >
		<input type="button" id="editGrpBtn"  value="Edit Event Group" onclick="editEventGroup(${gid })"/>
		<input type="button" id="deleteGrpBtn"  value="Delete Event Group" />
		</td>
	</tr>
	</table>
</div>
</div>
</td></tr>
<tr><td height="10"></td></tr>
<tr><td>
<div class="box" align="left">
<div class="boxheader">Event Group Page URL</div>
<div class="boxcontent">
<table  border="0" cellpadding="0" cellspacing="0" width="100%" >

		
<tr ><td colspan='2' id='errormsg' class='error'></td></tr>

<tr><td colspan="2">Publish following URL on your Website, Emails and Print Media:</td></tr> 
<tr><td colspan="2"><s:textfield name="eventGroupData.eventURL" size="60" onClick="this.select()"></s:textfield></td></tr> 
<tr><td colspan="2" align="right">&nbsp;</td></tr>
     </table>
</div>
<div class="boxfooter">
<input type="button" id="editGrpThemeBtn"  name="editGrpThemeBtn" value="Customize Event Group Page" onClick="editGroupTheme('${gid }');">
<input type="button" id="previewlink" name="previewlink" value="Preview"/>
<input type="button" id="customurlbutton" name="customurlbutton" value="Set Custom URL" onClick="getCustomUrlBlock('${gid }');"/>
</div>
</div>
</td></tr>
</table>
</td>
<td width="10"></td>
<td valign="top"  width="300">
<table cellspacing="0" cellpadding="0" width="100%">
<tr><td>
<div class="box" align="left">
<div class="boxheader">Integration Links</div>
<div class="boxcontent">
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
		<tr ><td colspan='2'><a href='<s:text name="eventGroupData.eventURL"></s:text>' ><img border='0' src='../images/register.jpg'/></a></td></tr>
		<tr ><td colspan='2'>Copy and paste the following code into your blog or website:</td></tr>	 
	 	<tr ><td colspan="2"><textarea cols="60" rows="3" onClick="this.select()"><a href="<s:text name='eventGroupData.eventURL'></s:text>" ><img border="0" src="${serveraddress}/home/images/register.jpg"/></a></textarea></td></tr>
<tr><td colspan="2" height="5"></td></tr>
<tr ><td colspan='2'><a href='<s:text name="eventGroupData.eventURL"></s:text>' ><img border='0' src='../images/buyticket.jpg'/></a></td></tr>
		<tr ><td colspan='2'>Copy and paste the following code into your blog or website:</td></tr>	 
	 	<tr ><td colspan="2"><textarea cols="60" rows="3" onClick="this.select()"><a href="<s:text name='eventGroupData.eventURL'></s:text>" ><img border="0" src="<s:text name='serveraddress'></s:text>/home/images/buyticket.jpg"/></a></textarea></td></tr>

     </table>
</div>

</div>
</td>
</tr>
</table>
</td>
</tr>
</table>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var editGrpBtn = new YAHOO.widget.Button("editGrpBtn", { onclick: { fn: editThisGroup,obj:{"gid":"${gid}"} } });
var deleteGrpBtn = new YAHOO.widget.Button("deleteGrpBtn", { onclick: { fn: deleteThisGroup,obj:{"gid":"${gid}"} } });
var customurlbutton = new YAHOO.widget.Button("customurlbutton", { onclick: { fn: getThisCustomUrlBlock, obj:{"gid":"${gid}"} } });
var editGrpThemeBtn = new YAHOO.widget.Button("editGrpThemeBtn", { onclick: { fn: editThisGroupTheme,obj:{"gid":"${gid}"} } });
var previewlink = new YAHOO.widget.Button("previewlink", { onclick: { fn: previewMethod,obj:{"url":"${eventGroupData.eventURL}"} } });
var customurlsubmitbtn = new YAHOO.widget.Button("customurlsubmitbtn", { onclick: { fn: setCustomURLfn,obj:{"gid":"${gid}"} } });
var customurlcancelbtn = new YAHOO.widget.Button("customurlcancelbtn", { onclick: { fn: cancelfn } });
function setCustomURLfn(event,jsonObj){
	setCustomURL(jsonObj.gid);
}
function cancelfn(){
	closebtn();
}

function editThisGroup(event,jsonObj){
	editEventGroup(jsonObj.gid);
}
function deleteThisGroup(event,jsonObj){
	deleteEventGroup(jsonObj.gid);
}
function editThisGroupTheme(event,jsonObj){
	editGroupTheme(jsonObj.gid);
}


var events_datatable = instantiateEventsTable(YAHOO.eventgroup.Data.events,"grpeventstable");
function instantiateEventsTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","id", "action"]
        }        
    });
    var myColumnDefs = [
            {key:"name", label:"Event Name"},
             {key:"action", label:""}
    ];
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, {MSG_EMPTY:"&nbsp;"});
    myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
    myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
     myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			window.location.href="../eventmanage/Snapshot?eid="+record.getData('id');
});  
    return myDataTable;
}

</script>



