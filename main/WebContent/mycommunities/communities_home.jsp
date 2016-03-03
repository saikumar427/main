<%@taglib uri="/struts-tags" prefix="s"%>
<script>
YAHOO.namespace('communitiesList');

var getSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Create Community", save, handleCancel);	
}
var getFailure = function(){
	alert("error");
}
var save=function(){	
	submitFormAndReload('communitycreate', 'communityerrormessages');	
}
</script>
<style>
.yui-dt table{
width:100%;
}

</style>
<div id="backgroundPopup" ></div>
<s:hidden name="groupId"/>
<!--<script type="text/javascript" src="../js/communities/communitieslist.js"></script>-->
<script type="text/javascript" src="CommunitesAction!getCommunitiesList"></script> 
<script type="text/javascript" src="../js/communities/communitieslistloader.js"></script>
<jsp:include page="/help/mycommunities_messages.jsp"></jsp:include>
<s:set name="config" value="configentry"/>
<s:if test="%{#config=='no'}">
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
	<td valign="top" width="644">To avail this feature email us at <a style="cursor: pointer;" onclick="openPopUp('/main/user/supportemail.jsp','350','330','layoutwidget2')">support@eventbee.com</a></td>
</tr></table></s:if>
<s:else>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
	<td valign="top" width="644" style="padding-right:20px">
	  <table cellspacing="0" cellpadding="0" width="100%">
	  <tr>
	  <td>
        <div class="box" align="left">
        <div class="boxheader">My Member Communites <span class="helpboxlink" id="Communiteshelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="Communiteshelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(home_Communites_helptitle)</script></div>
<div class="bd"><script>setHelpContent("home_Communites_helpcontent")</script></div>
</div>
        <div class="boxcontent">
          <table width="100%">
          <tr><td>
          <div align='right'>Search By Community Name <input type='text' id='communityfilter' value=''></div>
          <div id="communitieslisttable"></div>
          </td></tr>
          </table>
        </div>
 <div class="boxfooter">
    <input class="button" type="button" value="Create Member Community" id="communitybtn" ></input>
    </div>
        </div>
      </td></tr>
      </table>
    </td>
    
   <td valign="top" >
    <jsp:include page="mycommunitieshome_sidecontent.jsp"></jsp:include>
   </td>
    </tr>
  </table>
  </s:else>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
loadHelpPanel("Communiteshelppanel", "Communiteshelp", "500px");
var btn1 = new YAHOO.widget.Button("communitybtn", { onclick: {fn: createCommunity} });
function createCommunity(){	
	var url='CommunitesAction!createCommunity?purpose=create';	
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();	
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getSuccess,failure:getFailure});
	  	
}

createCommunitieslistTable();	
</script>