<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="../build/menu/assets/skins/sam/menu.css" />
<script type="text/javascript" src="../build/container/container_core-min.js"></script>
<script type="text/javascript" src="../build/menu/menu-min.js"></script>
<script type="text/javascript" src="../js/managequestion.js"></script>

<script type="text/javascript">
YAHOO.namespace("ebee.event");
YAHOO.namespace("ebee.registrationForm");
YAHOO.ebee.event.data = ${questionJsonData};
</script>
<div class="box" align="left">
<div class="boxheader">Basic Questions</div>
<div class="boxcontent">
<table width="100%">
	<tr>
		<s:if test="%{#powertype=='RSVP'}">
		<td >First Name, Last Name, Phone, Email
		will be collected by default at the time of RSVP.</td>
	</s:if>
	<s:else>
		<td >First Name, Last Name, Phone, Email
		of the Buyer will be collected by default at the time of
		Registration.</td>
		
	</s:else>
	</tr>
</table>
</div>
<div class="boxfooter">
<s:if test="%{#powertype=='RSVP'}"></s:if>
<s:else>
<input type="button" id="selectionSettings" value="Manage Settings" />
</s:else>
</div>
</div>
<p/>
<div class="box" align="left">
<div class="boxheader">Additional Questions</div>
<div class="boxcontent">

<table>
<tr>
		<s:if test="%{#powertype=='RSVP'}">
		<td >
		In addition to Base Profile collection, a pool of Custom Questions
		may be created and assigned to response type.</td>
	</s:if>
	<s:else>
		<td >
		In addition to Base Profile collection, a pool of Custom Questions
		may be created and assigned to Buyer and/or Tickets</td>
		<script>
var selectionSettingsBtn = new YAHOO.widget.Button("selectionSettings", { onclick: { fn: onSelectSettings ,obj: {"eid":"${eid}"} } });
</script>
	</s:else>
	</tr>
<tr>
<td>
<div id="questions_registrationForm"></div>
</td>
</tr>
</table>
</div>
<div class="boxfooter">
<input id="addnewquestion" type="button" value="Add New Question" onclick="addQuestion(${eid},'${powertype}')" />
</div>
</div>


<div id="dialog1">
<div class="hd"></div>
<div class="bd"></div>
</div>
<script>
/*Add New question YUI Button Code Start*/
var newquestionBtn = new YAHOO.widget.Button("addnewquestion", { onclick: { fn: addNewQuestion,obj:{"eid":"${eid}","powertype":"${powertype}"} } });
function addNewQuestion(event,jsonObj){
	var jsondata = eval(jsonObj);
	addQuestion(jsondata.eid,jsondata.powertype);
}
/*end*/
/*Registration Form Question YUI Data Table Satart*/

var dt_table_rf = instantiateRegistrationFormQTable(YAHOO.ebee.event.data.questions,"questions_registrationForm")
function instantiateRegistrationFormQTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["attribname","attrib_id","attribtype"]
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("attrib_id");
         elLiner.innerHTML = "<div id='manageQuestion"+ qid +"'></div>";                   
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var myColumnDefs = [
            {key:"attribname", label:"Name"},
            {key:"attribtype", label:"Type"},
            {key:"Manage", label:"",formatter:"mydisplay"}            
        ];
    var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}

YAHOO.ebee.registrationForm.init = function () {
//var qdata = eval(YAHOO.ebee.event.data.questions);
for(var i=0;i<YAHOO.ebee.event.data.questions.length;i++){
	var jsonobj = YAHOO.ebee.event.data.questions[i];
	var divid = "manageQuestion"+jsonobj.attrib_id;
	//YAHOO.util.Event.onContentReady(divid,function(){
		var Questions_ManageMenu = [			
			{ text: "Edit", value: 1, onclick: { fn: oneditQes ,obj: {"eid":"${eid}","attribid":jsonobj.attrib_id,"powertype":"${powertype}"} } },
			{ text: "Delete", value: 2, onclick: { fn: ondeleteQes ,obj: {"eid":"${eid}","attribid":jsonobj.attrib_id,"powertype":"${powertype}"} } }
		];
		var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: Questions_ManageMenu, container: divid });
	//});
}

}();
/*end*/

var selectionSettingsBtn = new YAHOO.widget.Button("selectionSettings", { onclick: { fn: onSelectSettings ,obj: {"eid":"${eid}"} } });

</script>