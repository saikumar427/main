var regtype='';
var anstype="";
var j=0;
var onSelectSettings = function(event,jsonobj){
	var paramslist = eval(jsonobj);	
	changeDefaultQuestionSettings(paramslist.eid);
}
var oneditQes = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	editQuestion(paramslist.eid,paramslist.attribid,paramslist.powertype, paramslist.qlevel);
}
var ondeleteQes = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	deleteQuestion(paramslist.eid,paramslist.attribid, paramslist.qlevel)
}
function addNewTransactionQuestion(event,jsonObj){
	var jsondata = eval(jsonObj);
	addQuestion(jsondata.eid,jsondata.powertype, "transaction");
}
function addNewTicketQuestion(event,jsonObj){
	var jsondata = eval(jsonObj);
	addQuestion(jsondata.eid,jsondata.powertype, "ticket");
}
function editTransactionQuestion(eid,attribid){
editQuestion(eid,attribid,regtype, "transaction");
}
function editTicketQuestion(eid,attribid){
editQuestion(eid,attribid,regtype, "ticket");
}
function editTransactionPhoneQuestion(eid,attribid){
editQuestion(eid,attribid,regtype, "transaction");
}
function deleteTransactionQuestion(eid,attribid){
deleteQuestion(eid,attribid,"transaction");
}
function deleteTicketQuestion(eid,attribid){
deleteQuestion(eid,attribid,"ticket");
}
/*
function applyTicketsToQuestion(eid,attribid){
applyTicketsToQuestion(eid,attribid,regtype, "ticket");
}
*/
function showQuestionsOfTicket(eid, ticketid){
}
function convertIntoYuiButtons(){
	/*var editBtn = new YAHOO.widget.Button("editbtn", { onclick: { fn: ManageQuestion_showEditOption } });
	var delBtn = new YAHOO.widget.Button("delbtn", { onclick: { fn: ManageQuestion_deleteOption } });
	var addoptBtn = new YAHOO.widget.Button("addoptbtn", { onclick: { fn: ManageQuestion_addNewOption } });
	var goBtn = new YAHOO.widget.Button("gobtn", { onclick: { fn: ManageQuestion_submitOption } });
	var cancelBtn = new YAHOO.widget.Button("cancelbtn", { onclick: { fn: ManageQuestion_cancelOption } });*/
}
function ManageQuestion_ChangeUI(selindex) {
	anstype=selindex;
	if (selindex == "selection") {
		$("smalltextarea").hide(1000);
		$("multilinearea").hide(1000);
		$("selectionarea").show(1000);
		convertIntoYuiButtons();
	} else if (selindex == "textarea") {
		$("smalltextarea").hide(1000);
		$("selectionarea").hide(1000);
		$("multilinearea").show(1000);
	} else {
		$("multilinearea").hide(1000);
		$("selectionarea").hide(1000);
		$("smalltextarea").show(1000);		
	}
}

function ManageQuestion_cancelOption() {
	$("gobtn").value="Add";	
	$("ctype").value="add";
	$("cancelButtd").hide();
	$("optionslist").selectedIndex = -1;
	$("coption").value="";
	$("coption").focus();
	
}
function ManageQuestion_addNewOption() {
	document.getElementById("coption").value = '';
	document.getElementById("ctype").value = 'add';
}
function ManageQuestion_showEditOption() {
	var selindex = document.getElementById("optionslist").selectedIndex;	
	if(selindex==0){
			ManageQuestion_cancelOption();
			return;
	}
	document.getElementById("coption").value = '';
	document.getElementById("ctype").value = 'edit';
	selindex = document.getElementById("optionslist").selectedIndex;

	if (selindex < 1) {
		alert("Select an answer choice to edit");		
		//document.getElementById("updatearea").style.display = 'none';
		return;
	}
	document.getElementById("cpos").value = selindex;
	document.getElementById("coption").value = document
			.getElementById("optionslist").options[selindex].text;
	document.getElementById("gobtn").value="Update";
	//$("gobtn").value="Update";
	$("cancelButtd").show();
	$("coption").focus();
	//$("updatearea").show(1000);
	
}

function ManageQuestion_deleteOption() {

	selindex = document.getElementById("optionslist").selectedIndex;

	if (selindex < 1) {
		alert("Select an answer choice to delete");
		return;
	}
	document.getElementById("optionslist").remove(selindex);
	ManageQuestion_cancelOption();
}

function ManageQuestion_mvUpOption() {

	selindex = document.getElementById("optionslist").selectedIndex;

	if (selindex < 2) {
		return;
	}
	temp = document.getElementById("optionslist").options[selindex].text;
	val = document.getElementById("optionslist").options[selindex].value;
	document.getElementById("optionslist").options[selindex].text = document
			.getElementById("optionslist").options[selindex - 1].text;
	document.getElementById("optionslist").options[selindex].value = document
			.getElementById("optionslist").options[selindex - 1].value;
	
	document.getElementById("optionslist").options[selindex - 1].text = temp;
	document.getElementById("optionslist").options[selindex - 1].value = val;
	document.getElementById("optionslist").selectedIndex = selindex - 1;
	//ManageQuestion_showEditOption();
}

function ManageQuestion_mvDnOption() {

	selindex = document.getElementById("optionslist").selectedIndex;

	if (selindex < 1) {
		return;
	}
	if (selindex == document.getElementById("optionslist").length - 1) {
		return;
	}
	temp = document.getElementById("optionslist").options[selindex].text;
	val = document.getElementById("optionslist").options[selindex].value;
	document.getElementById("optionslist").options[selindex].text = document
			.getElementById("optionslist").options[selindex + 1].text;
	document.getElementById("optionslist").options[selindex].value = document
	.getElementById("optionslist").options[selindex + 1].value;
	
	document.getElementById("optionslist").options[selindex + 1].text = temp;
	document.getElementById("optionslist").options[selindex + 1].value = val;
	document.getElementById("optionslist").selectedIndex = selindex + 1;
	//ManageQuestion_showEditOption();

}

function ManageQuestion_submitOption() {
	var question = document.getElementById("coption").value;
	var question = trim(question);
	if (question == '') {
		alert("Enter text for the answer choice");
		j=0;
		return;
	}
	selindex = document.getElementById("optionslist").selectedIndex;
	if (document.getElementById("ctype").value == 'edit' && selindex != -1) {
		//selindex = document.getElementById("cpos").value;
		//selindex = document.getElementById("optionslist").selectedIndex;
		document.getElementById("optionslist").options[selindex].text = document
				.getElementById("coption").value;
		ManageQuestion_cancelOption();
	} else {
		ManageQuestion_addOption(document.getElementById("coption").value);

	}
	$("coption").value = "";
	$("coption").focus();
}
function ManageQuestion_addOption(text) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = "";
	document.getElementById("optionslist").options.add(optn);
}

// manageregistration
function changedQType() {
	if (document.getElementById("qtype").value == "smalltext") {
		document.getElementById("smalltext").style.display = "block";
		document.getElementById("multilinetext").style.display = "none";
	} else {
		document.getElementById("smalltext").style.display = "none";
		document.getElementById("multilinetext").style.display = "block";
	}
}
//YAHOO.namespace("addquestion");
var handleChangeSettings = function(){
	document.qsettings.submit();	
}
var handleDefaultQuestionSettinsgsSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Settings", handleBaseQuestionSettings, handleCancel);	
}

var handleBaseQuestionSettings = function(){
	document.dqsettings.submit();
}
var handleChangeSettinsgsSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Settings", handleChangeSettings, handleCancel);	
}
var handleLoadSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Question Settings", questionSubmit, handleQuestionCancel);	
}
var handleQuestionCancel=function()
{
YAHOO.ebee.popup.wait.show();
window.location.reload(true);
}
function questionSubmit()
{
submit(++j);
}

function submit(j)
{
if(j==1)
handleSubmit();
}
var handleSubmit = function() {
YAHOO.ebee.popup.wait.show();
   if(document.getElementById("question")){
	var question = document.getElementById("question").value;
	var question = trim(question);
	if (question == "") {
			YAHOO.ebee.popup.wait.hide();
			alert("Enter text for the question.");
			j=0;
			return false;
	}
	var parent = document.getElementById("optlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("optionslist");
	if(anstype=='' && document.getElementById("addqform_typeselection").checked==true){anstype="selection";}
	if(selectBox.options.length<2 && anstype=="selection"){
	
		YAHOO.ebee.popup.wait.hide();
		alert("Add atleast one answer choice");
		j=0;
		return false;
	}
	for (var i = 1; i < selectBox.options.length; i++) {		
		index = i-1;		
		var obj1 = createHiddenTextElement(index,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(index,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	}
	//document.addquestion.submit();
	$('addqform').request({
	onSuccess: function(t)
	{
	//url='../myevents/home';
	window.location.reload();
	}	
});
}
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}


function changeDefaultQuestionSettings(eid){
	var url='ManageRegistrationForm!defaultQuestionSettings?eid='+eid;
	loadURLBasedWindow(url, handleDefaultQuestionSettinsgsSuccess);
}
function changeQuestionSettings(eid,attribid){
	var url='ManageRegistrationForm!questionSettings?eid='+eid+'&attributeid='+attribid;
	loadURLBasedWindow(url, handleChangeSettinsgsSuccess);
}
function addQuestion(eid,powertype, qlevel){
	var url = 'ManageRegistrationForm!addQuestion?eid='+eid+'&powertype='+powertype+'&qlevel='+qlevel;
	loadURLBasedWindow(url, handleLoadSuccess);	
}
function editQuestion(eid,attribid,powertype, qlevel){
	var url = 'ManageRegistrationForm!editQuestion?eid='+eid+'&attributeid='+attribid+'&powertype='+powertype+'&qlevel='+qlevel;
	loadURLBasedWindow(url, handleLoadSuccess);	
}
function applyTicketsToQuestion(eid,attribid,powertype, qlevel){
   	var url = 'ManageRegistrationForm!applyTicketsToQuestion?eid='+eid+'&attributeid='+attribid+'&powertype='+regtype+'&qlevel='+qlevel;
	loadURLBasedWindow(url, handleLoadSuccess);	
}
function deleteQuestion(eid,attribid, qlevel){
var url='ManageRegistrationForm!deleteQuestionConfirm?eid='+eid+'&attributeid='+attribid;
new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			if(result.indexOf("exist")>-1){
				var agree=confirm("There are some registrations that already answered this question. Deleting the question will remove those responses. Do you want to delete the question anyway?");
				if(agree){
					YAHOO.ebee.popup.wait.show();
					var url='ManageRegistrationForm!deleteQuestion?eid='+eid+'&attributeid='+attribid+'&qlevel='+qlevel;
					callURLandReload(url);
				}
			}else{	
				var agree=confirm("Do yout want to delete?");
				if(agree){			
					YAHOO.ebee.popup.wait.show();
					var url='ManageRegistrationForm!deleteQuestion?eid='+eid+'&attributeid='+attribid+'&qlevel='+qlevel;
					callURLandReload(url);
				}
			}
	      }
	});	
}
function swapQuestions(attribid1,attribid2,eid ){
	YAHOO.ebee.popup.wait.show();
	var url='ManageRegistrationForm!swapQuestions?eid='+eid+'&attributeid='+attribid1+'&swapattributeid='+attribid2;
	//callURLandReload(url);
	window.location.href=url;	
}
function SetAllCheckBoxes(FormName, FieldName, CheckValue){
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
		for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
}
function trim(s1){
		s1=ltrim(s1);
		s1=rtrim(s1);
		return s1;
 	}
function ltrim(s1){
	for(i=0;i<s1.length;i++){
		if(s1.charAt(i)==' ' || s1.charAt(i)=='\t' || s1.charAt(i)=='\n'){

		}
		else{
			s1=s1.substr(i)
			break;
		}
		if(i==s1.length-1)
		s1="";
	}

	return s1;
 }
function rtrim(s1){
	for(i=s1.length-1;i>=0;i--){
		if(s1.charAt(i)==' ' || s1.charAt(i)=='\t' || s1.charAt(i)=='\n'){
		}
		else{
			s1=s1.substring(0,i+1);
			break;
		}
		if(i==0)
		s1="";
	}
	return s1;
 }
