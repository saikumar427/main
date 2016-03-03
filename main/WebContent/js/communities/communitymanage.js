var addMembership = function(event,jsonobj){
	var paramslist = eval(jsonobj);	
	addMembershipType(paramslist.groupId);
}
function editMembership(groupId,membershipId){
	var url='CommunityManage!editMembershipType?groupId='+groupId+'&membershipId='+membershipId;
	loadURLBasedWindow(url, getMembershipTypeSuccess);
}
function deleteMembership(groupId,membershipId){
	var url='CommunityManage!deleteMembershipType?groupId='+groupId+'&membershipId='+membershipId;
	var ajax= new Ajax.Updater(
			{success: window.location.reload( true )},
			url,
			{method:'get',paramaeters: {}, onFailure: getFailure});
			return false;
}

function editMembershipType(funcevent,event,jsonObj){
	var jsonData = eval(jsonObj);
	var groupId = jsonData.groupId;
	var id=jsonData.id;
  var url='CommunityManage!editMembershipType?groupId='+groupId+'&membershipId='+id;
  loadURLBasedWindow(url, getMembershipTypeSuccess);
}
function addMembershipType(groupId){
	var url='CommunityManage!createMembershipType?groupId='+groupId;
	loadURLBasedWindow(url, getMembershipTypeSuccess);		  	
}
var getMembershipTypeSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Membership Details", save, handleCancel);
}
var getFailure = function(){
	alert("error");
}
var save=function(){	
	submitFormAndReload('addmembershiptypeform', 'membershiperrormessages');	
}

