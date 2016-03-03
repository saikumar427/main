var onSelectSettings = function(event,jsonobj){
	var paramslist = eval(jsonobj);	
	changeDefaultQuestionSettings(paramslist.groupId);
}
var oneditQes = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	editQuestion(paramslist.groupId,paramslist.attribid);
}
var ondeleteQes = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	deleteQuestion(paramslist.groupId,paramslist.attribid)
}
function ManageQuestion_ChangeUI(selindex) {
	if (selindex == "selection") {
		$("smalltextarea").hide(1000);
		$("multilinearea").hide(1000);
		$("selectionarea").show(1000);
	
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

}

function ManageQuestion_submitOption() {
	var question = document.getElementById("coption").value;
	var question = trim(question);
	if (question == '') {
		alert("Enter text for the answer choice");
		return;
	}
	if (document.getElementById("ctype").value == 'edit') {
		selindex = document.getElementById("cpos").value;
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
YAHOO.namespace("addquestion");
var handleChangeSettings = function(){
	document.addquestion.submit();	
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
	showPopUpDialogWindow(responseObj, "Question Details", handleSubmit, handleCancel);		
}


var handleSubmit = function() {
	var question = document.getElementById("question").value;
	var question = trim(question);
	if (question == "") {
			alert("Enter text for the question.");
			return false;
	}
	var parent = document.getElementById("optlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("optionslist");	
	for (var i = 1; i < selectBox.options.length; i++) {		
		index = i-1;		
		var obj1 = createHiddenTextElement(index,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(index,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	document.addquestion.submit();
};
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}


function addQuestion(groupId){
	var url = 'ManageRegistrationForm!addQuestion?groupId='+groupId;
	loadURLBasedWindow(url, handleLoadSuccess);
}
function editQuestion(groupId,attribid){
	var url = 'ManageRegistrationForm!editQuestion?groupId='+groupId+'&attributeid='+attribid;
	loadURLBasedWindow(url, handleLoadSuccess);
}
function deleteQuestion(groupId,attribid){
	var url='ManageRegistrationForm!deleteQuestion?groupId='+groupId+'&attributeid='+attribid;
	callURLandReload(url);	
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
