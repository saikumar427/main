<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="../build/menu/assets/skins/sam/menu.css" />
<script type="text/javascript" src="../build/container/container_core-min.js"></script>
<script type="text/javascript" src="../build/menu/menu-min.js"></script>
<script type="text/javascript" src="../js/communities/managequestion.js"></script>

<script type="text/javascript">
YAHOO.namespace("ebee.community");
YAHOO.namespace("ebee.communityregistrationForm");
YAHOO.ebee.community.data = ${questionJsonData};
</script>
<div class="box" align="left">
<div class="boxheader">Base Profile</div>
<div class="boxcontent">
<table width="100%">
<tr>
<td >First Name, Last Name, Phone, Email Id
of the Member will be collected by default at the time of Signup.</td>
</tr>
</table>
</div>
<!-- 
<div class="boxfooter">
<input type="button" id="selectionSettings" value="Manage Settings" />
</div>
 -->

</div>
<p/>
<div class="box" align="left">
<div class="boxheader">Additional Questions</div>
<div class="boxcontent">

<table>
<tr>
<td >
In addition to Base Profile collection, a pool of Custom Questions
may be created.</td>
</tr>
<!-- 
<script>
var selectionSettingsBtn = new YAHOO.widget.Button("selectionSettings", { onclick: { fn: onSelectSettings ,obj: {"eid":"${eid}"} } });
</script> 
-->

<tr>
<td>
<div id="questions_registrationForm"></div>
</td>
</tr>
</table>
</div>
<div class="boxfooter">
<input id="addnewquestion" type="button" value="Add New Question" />
</div>
</div>
<br/><br/>
<!-- <a href="CommunityManage!showTermCond?groupId=${groupId}" id="termcondlnk" >Terms &amp; Conditions</a> 
<script>
var termcondlnk = new YAHOO.widget.Button("termcondlnk",  {type: "link"});
</script>
 -->
<div id="popupdialog">
<div class="hd"></div>
<div class="bd"></div>
</div>
<script>
/*Add New question YUI Button Code Start*/
var newquestionBtn = new YAHOO.widget.Button("addnewquestion", { onclick: { fn: addNewQuestion,obj:{"groupId":"${groupId}"} } });
function addNewQuestion(event,jsonObj){
	var jsondata = eval(jsonObj);
	addQuestion(jsondata.groupId);
}
/*end*/
/*Registration Form Question YUI Data Table Satart*/

var dt_table_rf = instantiateRegistrationFormQTable(YAHOO.ebee.community.data.questions,"questions_registrationForm")
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

YAHOO.ebee.communityregistrationForm.init = function () {
for(var i=0;i<YAHOO.ebee.community.data.questions.length;i++){
	var jsonobj = YAHOO.ebee.community.data.questions[i];
	var divid = "manageQuestion"+jsonobj.attrib_id;
	//YAHOO.util.Event.onContentReady(divid,function(){
		var Questions_ManageMenu = [			
			{ text: "Edit", value: 1, onclick: { fn: oneditQes ,obj: {"groupId":"${groupId}","attribid":jsonobj.attrib_id} } },
			{ text: "Delete", value: 2, onclick: { fn: ondeleteQes ,obj: {"groupId":"${groupId}","attribid":jsonobj.attrib_id} } }
		];
		var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: Questions_ManageMenu, container: divid });
	//});
}

}();
/*end*/

//var selectionSettingsBtn = new YAHOO.widget.Button("selectionSettings", { onclick: { fn: onSelectSettings ,obj: {"eid":"${eid}"} } });

</script>