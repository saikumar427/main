<%@taglib uri="/struts-tags" prefix="s"%>

<link rel="stylesheet" type="text/css"
	href="../build/menu/assets/skins/sam/menu.css" />
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script>
	function addmessage() {
		document.getElementById('custmsg').style.display = "block";
	}
	function addphoto() {
		document.getElementById('photo').style.display = "block";
	}
	function trackpwd() {
		document.getElementById('pwdprotect').style.display = "block";
	}
	function back(msg) {
		if ('msg' == msg) {
			document.getElementById('custmsg').style.display = "none";
		} else if ('image' == msg) {
			document.getElementById('photo').style.display = "none";
		} else
			document.getElementById('pwdprotect').style.display = "none";
	}
	function getTrackMessage(tid) {
		YAHOO.ebee.popup.wait.show();
		var url = "ManageTrackingPartner!getTrackPartnerMessage?trackid=" + tid;
		var dynatimestamp = new Date();
		url += "&dynatimestamp=" + dynatimestamp.getTime();
		var request = YAHOO.util.Connect.asyncRequest('GET', url, {
			success :handleMessageSuccess,
			failure :handleFailure
		});
	}
	/*
	 * function getOtherEvents(tid){ YAHOO.ebee.popup.wait.show(); var
	 * url="ManageTrackingPartner!getMyOtherEvents?trackid="+tid; var dynatimestamp =
	 * new Date(); url += "&dynatimestamp="+dynatimestamp.getTime(); var
	 * request=YAHOO.util.Connect.asyncRequest('GET',url,{success:handleOtherEventsSuccess,failure:handleFailure}); }
	 */
	function getTrackDelete(tid) {
		YAHOO.ebee.popup.wait.show();
		var url = "ManageTrackingPartner!deleteTrackPartner?trackid=" + tid;
		var dynatimestamp = new Date();
		url += "&dynatimestamp=" + dynatimestamp.getTime();
		var request = YAHOO.util.Connect.asyncRequest('GET', url, {
			success :handleDeleteSuccess,
			failure :handleFailure
		});
	}
	function getTrackPhoto(tid) {
		YAHOO.ebee.popup.wait.show();
		var url = "ManageTrackingPartner!getTrackPartnerPhotoURL?trackid="
				+ tid;
		var dynatimestamp = new Date();
		url += "&dynatimestamp=" + dynatimestamp.getTime();
		var request = YAHOO.util.Connect.asyncRequest('GET', url, {
			success :handlePhotoSuccess,
			failure :handleFailure
		});
	}
	var handlePhotoSuccess = function(responseObj) {

		showPopUpDialogWindow(responseObj, "Photo URL", inserttrackingimage,
				handleCancel);
	}
	function getTrackStatus(tid) {
		YAHOO.ebee.popup.wait.show();
		var url = "ManageTrackingPartner!getTrackPartnerStatus?trackid=" + tid;
		var dynatimestamp = new Date();
		url += "&dynatimestamp=" + dynatimestamp.getTime();
		var request = YAHOO.util.Connect.asyncRequest('GET', url, {
			success :handleStatusSuccess,
			failure :handleFailure
		});
	}
	var handleStatusSuccess = function(responseObj) {

		showPopUpDialogWindow(responseObj, "Status", inserttrackingstatus,
				handleCancel);
	}
	var handleFailure = function() {
		alert("error");
	}
	function getTrackPassword(tid) {

		YAHOO.ebee.popup.wait.show();
		var url = "ManageTrackingPartner!getTrackPartnerPassword?trackid="
				+ tid;
		var dynatimestamp = new Date();
		url += "&dynatimestamp=" + dynatimestamp.getTime();
		var request = YAHOO.util.Connect.asyncRequest('GET', url, {
			success :handleSuccess,
			failure :handleFailure
		});
	}
	var handleSuccess = function(responseObj) {
		showPopUpDialogWindow(responseObj, "Password", inserttrackingpwd,
				handleCancel);
	}
	var handleFailure = function() {
		alert("error");
	}

	function inserttrackmsg() {
		var message = document.getElementById('message').value;
		var trackid = document.getElementById('trackid').value;
		var ischecked = document.getElementById('statuscheck').checked;
		var url = "ManageTrackingPartner!changeTrackPartnerMessage?trackid="
				+ trackid + "&message=" + message + "&checked=" + ischecked;
		new Ajax.Request(url, {
			method :'get',
			onSuccess : function(transport) {
				var result = transport.responseText;
				window.location.href = "ManageTrackingPartner?trackid="
						+ trackid;
			}
		});
	}
	function inserttrackingpwd() {
		var password = document.getElementById('password').value;
		var trackid = document.getElementById('trackid').value;
		var ischecked = document.getElementById('statuscheck').checked;
		var url = "ManageTrackingPartner!changeTrackPartnerPassword?trackid="
				+ trackid + "&password=" + password + "&checked=" + ischecked;

		new Ajax.Request(url, {
			method :'get',
			onSuccess : function(transport) {
				var result = transport.responseText;
				window.location.href = "ManageTrackingPartner?trackid="
						+ trackid;
			}
		});

	}
	function inserttrackingimage() {

		var photoURL = document.getElementById('photoURL').value;
		var trackid = document.getElementById('trackid').value;
		var ischecked = document.getElementById('statuscheck').checked;
		var url = "ManageTrackingPartner!changeTrackPartnerPhotoURL?trackid="
				+ trackid + "&photoURL=" + photoURL + "&checked=" + ischecked;
		new Ajax.Request(url, {
			method :'get',
			onSuccess : function(transport) {
				var result = transport.responseText;
				window.location.href = "ManageTrackingPartner?trackid="
						+ trackid;
			}
		});
	}
	function inserttrackingstatus() {

		var status = document.getElementById('status').value;
		var trackid = document.getElementById('trackid').value;
		var ischecked = document.getElementById('statuscheck').checked;
		var url = "ManageTrackingPartner!changeTrackPartnerStatus?trackid="
				+ trackid + "&status=" + status + "&checked=" + ischecked;
		new Ajax.Request(url, {
			method :'get',
			onSuccess : function(transport) {
				var result = transport.responseText;
				window.location.href = "ManageTrackingPartner?trackid="
						+ trackid;
			}
		});
	}
	function deletefun() {

		var trackid = document.getElementById('trackid').value;
		var ischecked = document.getElementById('statuscheck').checked;
		var url = "ManageTrackingPartner!deleteTrackPartnerSuccess?trackid="
				+ trackid + "&checked=" + ischecked;
		new Ajax.Request(url, {
			method :'get',
			onSuccess : function(transport) {
				var result = transport.responseText;
				window.location.href = "home";
			}
		});
	}
	/*
	 * function insertOtherEventsfun(){ alert("hi"); var
	 * trackid=document.getElementById('trackid').value; alert("trackid"); var
	 * ischecked=document.getElementById('statuscheck').checked; var
	 * url="ManageTrackingPartner!insertMyOtherEvents?trackid="+trackid; new
	 * Ajax.Request(url, { method: 'get', onSuccess: function(transport) {
	 * alert(url); var result=transport.responseText; window.location.href="home"; }
	 * }); }
	 */
	var handleMessageSuccess = function(responseObj) {

		showPopUpDialogWindow(responseObj, "Message", inserttrackmsg,
				handleCancel);

	}
	var handleDeleteSuccess = function(responseObj) {

		showPopUpDialogWindow(responseObj, "Delete", deletefun, handleCancel);

	}
	/*
	 * var handleOtherEventsSuccess = function(responseObj) {
	 * 
	 * showPopUpDialogWindow(responseObj, "My Other Events", insertOtherEventsfun,
	 * handleCancel);
	 *  }
	 */
	var handleFailure = function() {
		alert("error");
	}

	function importeventsfn() {
		$('managetrackingPartnerform')
				.request(
						{
							onFailure : function() {
								$('error').update('Failed to get results')
							},
							onSuccess : function(t) {
								var result = t.responseText;
								if (result.indexOf("Success") > -1) {
									var trackid = '${trackid}';
									var url = 'ManageTrackingPartner!getMyOtherEvents?trackid=' + trackid;
									YAHOO.ebee.popup.wait.show();
									var dynatimestamp = new Date();
									url += '&dynatimestamp=' + dynatimestamp
											.getTime();
									var request = YAHOO.util.Connect
											.asyncRequest(
													'GET',
													url,
													{
														success :getImportEventsSuccess,
														failure :getImportEventsFailure
													});
								}
							}
						});
	}

	var getImportEventsSuccess = function(responseObj) {
		showPopUpDialogWindow(responseObj, "My Other Events", save,
				handleCancel);
		changeLimitType();
		changeType();
	}
	var j=0;
	var save = function() {
		++j;
		if(j==1)
		submitForm1();
	}
	function submitForm1() {
		$('myothereventsform').request( {
			onFailure : function() {
				alert("in failure");
				$('errormsg').update('Failed to get results')
				j=0;
			},
			onSuccess : function(t) {
				var result = t.responseText;
				YAHOO.ebee.popup.dialog.cancel();
				YAHOO.ebee.popup.wait.show();
				window.location.reload(true);
				this.cancel();

			}
		});

	}
	var getImportEventsFailure = function() {
		alert("error");
	}
	function checkAll(field) {
		for (i = 0; i < field.length; i++)
			field[i].checked = true;
	}
	function uncheckAll(field) {
		for (i = 0; i < field.length; i++)
			field[i].checked = false;
	}
</script>

<script type="text/javascript">
	YAHOO.namespace("trackURL");
</script>
<script type="text/javascript"
	src="ManageTrackingPartner!populateTrackURLList?trackid=${trackid}"></script>

<s:form name="managetrackingPartnerform" id="managetrackingPartnerform">
<s:hidden name="trackid" ></s:hidden>
	<s:iterator value="indivualTrackingpartnerList"
		var="TrackingPartnerData" status="indivualTrackingpartnerListstatus">
		<div><s:set name="trackcode"
			value="%{#TrackingPartnerData.getTrackname()}"></s:set>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			class="taskcontent">
			<tr>
				<td>Manage Tracking Partner <b><s:property
					value="%{#TrackingPartnerData.getTrackname()}" /></b></td>
				<td align="right"><a href="home">Back to My Events Home</a></td>
			</tr>
		</table>
		</div>
		<br />
		<div class="box" align="left">
		<div class="boxheader">Partner Events</div>
		<div class="boxcontent">
		<table width="100%">
			<tr>
				<td>
				<div id="trackurldetails"></div>
				</td>
			</tr>
		</table>
		</div>
		<div class="boxfooter"><input type="button"
			name="Add To My Other Events" value="Add To My Other Events"
			id="myothereventsbtn"></div>
		</div>
		<br />
		<div class="box" align="left">
		<div class="boxheader">Status</div>
		<div class="boxcontent"><br />
		<s:set name="stat" value="%{#TrackingPartnerData.getStatus()}"></s:set>
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tr>
				<td width="12%">Status:</td>
				<td width="70%"><s:if test="%{#stat == 'Suspended'}">Suspended</s:if><s:else>Approved</s:else></td>
				<td><s:if test="%{#stat == 'Suspended'}">
					<input type="button" value="Activate" id="activestatusbtn" />
				</s:if> <s:else>
					<input type="button" id="suspendstatusbtn" value="Suspend" />
				</s:else></td>
				<td><input type="button" value="Delete" id="deletebtn" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><span class="smallestfont"> Suspending
				tracking URL redirects all traffic to the main URL, resulting no
				visits and ticket sales credited to the tracking URL</span></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		</div>
		</div>
		<br />
		<div class="box" align="left">
		<div class="boxheader">Manage URL</div>
		<div class="boxcontent"><br />
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tr>
				<td width="12%">Manage URL:</td>
				<!-- <td width="70%">http://<s:property value="userName" />.eventbee.com/tu/<s:property
					value="%{#TrackingPartnerData.getTrackname()}" /></td>-->
				<td width="70%"><s:property value="manageURL" /><s:property
					value="%{#TrackingPartnerData.getTrackname()}" /></td>	
				<td><input type="button" value="Password" id="trckpwd" /></td>
			</tr>

			<tr>
				<td></td>
				<td colspan="2"><span class="smallestfont">Partners/Affiliates
				can visit this URL to view reports, set photo, and post message. You
				can protect this page with password, and share it with
				Partner/Affiliate </span></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		</div>
		</div>

		<br />
		<div class="box" align="left">
		<div class="boxheader">Photo</div>
		<div class="boxcontent"><br />
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tr>
				<td width="12%">Photo:</td>
				<td width="70%">
               <s:if test="%{#TrackingPartnerData.getPhotoURL()!=''}">
              <img src='<s:property value="%{#TrackingPartnerData.getPhotoURL()}" />'
					width="200">
             </s:if>
             </td>
				<td><input type="button" value="Add/Change" id="addphotobtn" /></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		</div>
		</div>
		<br />
		<div class="box" align="left">
		<div class="boxheader">Message</div>
		<div class="boxcontent"><br />
		<table border="0" cellpadding="3" cellspacing="0" width="100%">

			<tr>
				<td width="12%">Message:</td>
				<td width="70%"><s:property
					value="%{#TrackingPartnerData.getMessage()}" /></td>
				<td><input type="button" value="Add/Change" id="addmsgbtn" /></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		</div>
		</div>
	</s:iterator>


</s:form>
<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
</div>
<script>
	var msgbtn = new YAHOO.widget.Button("addmsgbtn", {
		onclick : {
			fn :addmsgbtnfun
		}
	});
	function addmsgbtnfun() {
		// alert("button added");
		getTrackMessage('${trackid}');
	}
	var photobtn = new YAHOO.widget.Button("addphotobtn", {
		onclick : {
			fn :addphotofun
		}
	});
	function addphotofun() {
		getTrackPhoto('${trackid}');
	}
	var pwdbtn = new YAHOO.widget.Button("trckpwd", {
		onclick : {
			fn :trckpwdfun
		}
	});
	function trckpwdfun() {
		getTrackPassword('${trackid}');
	}
	var actbtn = new YAHOO.widget.Button("activestatusbtn", {
		onclick : {
			fn :trckstatusfun
		}
	});
	function trckstatusfun() {
		getTrackStatus('${trackid}');
	}
	var suspbtn = new YAHOO.widget.Button("suspendstatusbtn", {
		onclick : {
			fn :trckstatusfun
		}
	});
	var deletebtn = new YAHOO.widget.Button("deletebtn", {
		onclick : {
			fn :trackdeletefun
		}
	});
	function trackdeletefun() {
		getTrackDelete('${trackid}');
	}
	var myothereventsbutton = new YAHOO.widget.Button("myothereventsbtn", {
		onclick : {
			fn :importfn
		}
	});
	function importfn() {
		// getOtherEvents('${trackid}');
		importeventsfn();
	}

	var trackurltable = instantiateTrackURLDetailsTable(
			YAHOO.trackURL.Data.TrackURLDetails, "trackurldetails");

	function instantiateTrackURLDetailsTable(ds, cname) {
		var myDataSource = new YAHOO.util.DataSource(ds, {
			responseType :YAHOO.util.DataSource.TYPE_JSARRAY,
			responseSchema : {
				fields : [ "eventname", "status", "eventid" ]
			}

		});
		  var mypaginator = new YAHOO.widget.Paginator({
   	        rowsPerPage   : 5,
   	        pageLinks     : 3
   	    });
    var conf = {
   		        paginator : mypaginator,
   		        MSG_EMPTY:"&nbsp;"
   		        //,sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
   		    };

		var myColumnDefs = [ {
			key :"eventname",
			label :"Event Name"
		}, {
			key :"status",
			label :"Status"
		} ];
		var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs,
				myDataSource,conf, {
					MSG_EMPTY :"&nbsp;"
				});
		myDataTable.subscribe("rowMouseoverEvent",
				myDataTable.onEventHighlightRow);
		myDataTable.subscribe("rowMouseoutEvent",
				myDataTable.onEventUnhighlightRow);
		myDataTable
				.on(
						'cellClickEvent',
						function(oArgs) {
							var target = oArgs.target;
							var record = this.getRecord(target);

							window.location.href = "../eventmanage/TrackURL!manageTrackURL?eid="
									+ record.getData('eventid')
									+ "&trackcode=<s:property value='trackcode'/>";
						});
		return myDataTable;
	}
</script>
				