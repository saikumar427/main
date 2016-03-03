<script type="text/javascript" src="../js/colorPicker.js"></script> 
<script type="text/javascript" src="../js/popup.js">
function popupdummy(){}
</script>
<link rel="stylesheet" type="text/css" href="../css/backgroundDisablePopup.css" />
<script language='Javascript' >
var currentDivId="";
var popupWin;
var previewFileName="";
function setWebPath(fullPath){
	document.getElementById(currentDivId).value=fullPath;
}
function getImageURL(divId){
	currentDivId=divId;
	popupwindow('../membertasks/ImageUpload','Map','300','300');
}
function closeIt(){
	if (!popupWin.closed){
	setTimeout("closeIt()",1)//adjust timing
	}else{	
	document.getElementById("backgroundPopup").style.display="none";
	}
}
function windowOpener(content,url,name,args){
	if (typeof(popupWin) != "object"){
	popupWin = window.open('',name,args);
	alert("Content"+content);
	popupWin.location.href = '';
	var tmp = popupWin.document;
	tmp.write(content);
	tmp.close();
	closeIt();
	} 
	else{
		if (!popupWin.closed){
			alert("Content"+content);
			popupWin.location.href = '';
			var tmp = popupWin.document;
			tmp.write(content);
			tmp.close();
		}
		else{
			alert("Content"+content);
		popupWin = window.open('', name,args);
		var tmp = popupWin.document;
		tmp.write(content);
		tmp.close();
			
		closeIt();
		}
	}
	popupWin.focus();
}
function saveStreamerData(){
	document.streamerdataform.action='../myevents/ManageStreamer!saveStreamerData';
	document.streamerdataform.target="_self";
	document.streamerdataform.submit();
}
function getStreamerPreview(){	
	
	$('streamerdataform').action='../myevents/ManageStreamer!preview';
	$('streamerdataform').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;		       
	        alert("previewFileName"+previewFileName);
	        var temp="<html><body>"+previewFileName+"</body></html>";
	        
			windowOpener(temp,previewFileName,'WIDTH=200,HEIGHT=300,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
	    }
    });
	$('streamerdataform').action='ManageStreamer!saveStreamerData';
	//document.streamerdataform.action='../myevents/ManageStreamer!preview';
	//document.streamerdataform.action.target="_blank";
	//document.streamerdataform.submit();
}
function getStreamerCode(){
		popupwindow('../myevents/GetStreamerCode','Map','300','300');
}
</script>
<script>
var cp = new ColorPicker('window'); // Popup window
var cpDiv = new ColorPicker(); // DIV style

</script>
<div id="backgroundPopup" ></div>
<%@taglib uri="/struts-tags" prefix="s"%>
<form  name="streamerdataform" id="streamerdataform" method="POST" action="ManageStreamer!saveStreamerData">
<br/>
<div class="taskbox">
<span class="taskheader">Settings</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width='25%'>
Title
</td>
<td>
<s:textfield name="streamerData['TITLE']"  size='60' id="title" theme="simple"></s:textfield>
</td>
</tr>
</table>

<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width='25%'>
Events Count
</td>
<td>
<s:set name="cnt" value="streamerData['NO_OF_ITEMS']"></s:set>
<select name="streamerData['NO_OF_ITEMS']" >
<s:iterator value="count" >
<s:set name="cval" value="key" />
<option value="<s:property value="key"/>" <s:if test="%{#cnt==#cval}">selected='selected'</s:if> >${value}</option>
</s:iterator>
</select>
</td>
</tr>
</table>

<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width='25%'>
Streamer Width
</td>
<td>
<s:textfield name="streamerData['STREAMERSIZE']"  size='8' id="STREAMERSIZE" theme="simple"></s:textfield>pixels
</td>
</tr>
</table>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width='25%'>
Display Eventbee link?
</td>
<td>
<s:set name="type" value="streamerData['DISPLAYEBEELINK']"></s:set>
<input type="radio" name="streamerData['DISPLAYEBEELINK']" value="yes" <s:if test="%{#type == 'yes'}">checked="checked"</s:if>>Yes
<input type="radio" name="streamerData['DISPLAYEBEELINK']" value="no" <s:if test="%{#type != 'yes'}">checked="checked"</s:if>>No 
</td>
</tr>
</table>
</div>
<br/>
<div class="taskbox">
<span class="taskheader">Look and Feel</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td colspan="3">Background</td></tr>
<tr><td width='25%'></td>
<td ><s:set name="type" value="streamerData['BG_TYPE']"></s:set>
<input type='radio' name="streamerData['BG_TYPE']" value="COLOR" <s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>> Color
</td>
<td>
<s:textfield name="streamerData['BACKGROUND_COLOR']"  size='8' id="bgcolor" theme="simple"></s:textfield>
<a href="#" name="BACKGROUND_COLOR" id="BACKGROUND_COLOR" onclick="cpDiv.select(streamerdataform.bgcolor,'BACKGROUND_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a><script language="JavaScript">cp.writeDiv()</script>
</td>
</tr>
<tr><td></td><td></td><td>or</td></tr>
<tr><td width='25%'></td>
<td width='22%'><input type='radio' name="streamerData['BG_TYPE']" value="IMAGE" <s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>> Image</td>
<td>
<s:textfield name="streamerData['BACKGROUND_IMAGE']"  size='45' id="BACKGROUND_IMAGE" theme="simple"></s:textfield>
<a href="#"  onclick='getImageURL("BACKGROUND_IMAGE")' STYLE="text-decoration: none">
<img border='0' src="../images/image.gif"/></a>
</td>
</tr>
<tr><td  width='25%'></td>
<td width='22%'>Border&nbsp;&nbsp;&nbsp;
<s:textfield name="streamerData['BORDERCOLOR']"  size='8' id="sborder" theme="simple"></s:textfield>
<a href="#" name="BORDERCOLOR"  id="BORDERCOLOR" onclick="cpDiv.select(streamerdataform.sborder,'BORDERCOLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a><script language="JavaScript">cp.writeDiv()</script>
</td>
<td>Links&nbsp;&nbsp;&nbsp;
<s:textfield name="streamerData['LINKCOLOR']"  size='8' id="slinks" theme="simple"></s:textfield>
<a href="#" name="LINKCOLOR" id="LINKCOLOR" onclick="cpDiv.select(streamerdataform.slinks,'LINKCOLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a><script language="JavaScript">cp.writeDiv()</script>
</td>
</tr>
</table>
<br/>
<hr/>
<br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td colspan="3">Large Text Font<br/><span class='smallestfont'>This text style is applied to Event headers</span></td></tr>
<tr><td width='25%'></td>
<td width='22%'>Color&nbsp;&nbsp;&nbsp;<s:textfield name="streamerData['BIGGER_TEXT_COLOR']" theme="simple" size='8' id="largetextcolor"></s:textfield>
<a href="#" name='BIGGER_TEXT_COLOR' id='BIGGER_TEXT_COLOR' onclick="cpDiv.select(streamerdataform.largetextcolor,'BIGGER_TEXT_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a>	  <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
</td>
<td >Type&nbsp;&nbsp;&nbsp;
<s:set name="largefonttype" value="streamerData['BIGGER_FONT_TYPE']" ></s:set>
<select name="streamerData['BIGGER_FONT_TYPE']" >
<s:iterator value="fontTypes" >
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#largefonttype==#cval}">selected='selected'</s:if> >${value}</option>
</s:iterator>
</select>
&nbsp;&nbsp;&nbsp;Size&nbsp;&nbsp;
<s:set name="largefontsize" value="streamerData['BIGGER_FONT_SIZE']"></s:set>
<select name="streamerData['BIGGER_FONT_SIZE']" >
<s:iterator value="fontSizes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#largefontsize==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</tr>
</table>

<br/>
<hr/>
<br/>

<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td colspan="3">Medium Text Font<br/><span class='smallestfont'>This text style is applied to Column headers</span></td></tr>

<tr><td width='25%'></td>
<td width='22%'>Color&nbsp;&nbsp;&nbsp;<s:textfield theme="simple" name="streamerData['MEDIUM_TEXT_COLOR']"  size='8' id="medtextcolor"></s:textfield>
<a href="#" name='MEDIUM_TEXT_COLOR' id='MEDIUM_TEXT_COLOR' 
onclick="cpDiv.select(streamerdataform.medtextcolor,'MEDIUM_TEXT_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a>	  <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
</td>
<td >Type&nbsp;&nbsp;&nbsp;
<s:set name="mediumfonttype" value="streamerData['MEDIUM_FONT_TYPE']"></s:set>
<select name="streamerData['MEDIUM_FONT_TYPE']" >
<s:iterator value="fontTypes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#mediumfonttype==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
&nbsp;&nbsp;&nbsp;Size&nbsp;&nbsp;
<s:set name="mediumfontsize" value="streamerData['MEDIUM_FONT_SIZE']"></s:set>
<select name="streamerData['MEDIUM_FONT_SIZE']" >
<s:iterator value="fontSizes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#mediumfontsize==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</table>

<br/>
<hr/>
<br/>

<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td colspan="3">Small Text Font<br/><span class='smallestfont'>This text style is applied to all other content</span></td></tr>

<tr>
<td  width='25%'></td>
<td width='22%'>Color&nbsp;&nbsp;&nbsp;
<s:textfield name="streamerData['SMALL_TEXT_COLOR']"  size='8' id="smalltextcolor" theme="simple"></s:textfield>
<a href="#" name='SMALL_TEXT_COLOR' id='SMALL_TEXT_COLOR' 
onclick="cpDiv.select(streamerdataform.smalltextcolor,'SMALL_TEXT_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a>	  <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
</td>
<td>Type&nbsp;&nbsp;&nbsp;
<s:set name="smallfonttype" value="streamerData['SMALL_FONT_TYPE']"></s:set>
<select name="streamerData['SMALL_FONT_TYPE']" >
<s:iterator value="fontTypes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#smallfonttype==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
&nbsp;&nbsp;&nbsp;Size&nbsp;&nbsp;
<s:set name="smallfontsize" value="streamerData['SMALL_FONT_SIZE']" ></s:set>
<select name="streamerData['SMALL_FONT_SIZE']" >
<s:iterator value="fontSizes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#smallfontsize==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</tr>
</table>
</div><br/>

<table  width="100%">
<tr>
<td colspan='2' align='center'>
<input type="submit" name="submit" value="Submit" id="submitBtn" />
<input type="button" name="preview" value="Preview" id="previewBtn" />
<input type="button" name="Cancel" value="Cancel" id="cancelBtn" />
</td>
</tr>
</table>
</form>
<script>
var btn = new YAHOO.widget.Button("submitBtn", { onclick: { fn: saveStreamer } });
function saveStreamer(){
	saveStreamerData();
}

var btn1 = new YAHOO.widget.Button("previewBtn", { onclick: { fn: getStreamer } });
function getStreamer(){
	javascript:getStreamerPreview();
}
var btn2 = new YAHOO.widget.Button("cancelBtn", { onclick: { fn: close } });
function close(){
	javascript:history.back();
}
</script>