<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="../js/colorPicker.js"></script>
<script type="text/javascript" src="../js/popup.js"></script>
<link rel="stylesheet" type="text/css" href="../css/backgroundDisablePopup.css" />
<script type="text/javascript">
var previewFileName="";
var j=0;
var previewContent='';
function getContentPreview(){	
	
	$('sform').action='eventslist!previewLnF';
	$('sform').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;		
	        var url='../membertasks/FileRead?readFileName='+''+previewFileName+'';
	        showYUIinIframe("Preview",url,600,500);
	        
	    }
    });
	$('sform').action='eventslist!saveTheme';
}
function submitForm(j){
	YAHOO.ebee.popup.wait.show();
	var themename=document.getElementById('themename').value;
	if(trim(themename)==""){		
		YAHOO.ebee.popup.wait.hide();
		setErrorMsg("<li>Theme Name is empty</li>");		
		return;
}
	if(j==1){
	$('sform').request({
	onSuccess: function(t)
	{
	url='../myevents/home';
	window.location.href=url;
	}	
});
}
//document.sform.submit();
}
function setErrorMsg(msg){
	$('createThemeStatusMsg').update(msg);
	$('createThemeStatusMsg').className = "errorMessage";
	$('break').show();
	$('createThemeStatusMsg').scrollTo();
}
var cp = new ColorPicker('window'); // Popup window
var cpDiv = new ColorPicker(); // DIV style
var currentDivId="";
function setWebPath(fullPath){
	document.getElementById(currentDivId).value=fullPath;
	YAHOO.ebee.popup.dialog1.cancel();
}
function closeFileUploadWindow(){
	YAHOO.ebee.popup.dialog1.cancel();
}
function getImageURL(divId){
	currentDivId=divId;
	url='../membertasks/ImageUpload';
	showYUIinIframe("Photo Upload",url,400,200);
	
}
</script>

<div id="createThemeStatusMsg"></div>
<div id="break" style="display: none;"><br/></div>
<jsp:include page="/help/myevents_messages.jsp"></jsp:include>
<div class="taskcontent"><script>setHelpContent("mythemes_looknfeel_helpcontent")</script></div>
<div style="height:8px;"></div>
<s:form theme="simple" id="sform" name="sform" action="eventslist!saveTheme" method="post">
<s:hidden name="userThemes.action"></s:hidden>
<input type="hidden" name="themeid" value='<%=request.getParameter("themeid")%>' ></input>

	
	<div class="taskbox"><span class="taskheader">Page Background</span><br />
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
<td>Theme Name *</td>
<td colspan="3">
<s:textfield name="themeAttribs['THEME_NAME']" size="60" id="themename"></s:textfield>
</td></tr>
		<tr>
			<s:set name="type" value="themeAttribs['BG_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['BG_TYPE']" value="COLOR"
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>>
			Color</td>
			<td><s:textfield name="themeAttribs['BG_COLOR']" size='8'
				id="bgcolor"></s:textfield> <a href="#" name="BG_COLOR"
				id="BG_COLOR"
				onclick="cpDiv.select(sform.bgcolor,'BG_COLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a><script language="JavaScript">cp.writeDiv()</script>
			</td>
		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['BG_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['BG_IMAGE']" size='45'
				id="BG_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("BG_IMAGE");return false;' STYLE="text-decoration: none">
			<img border='0' src="../images/image.gif" /></a></td>
		</tr>
	</table>
	</div>
	<br />
<div class="taskbox"><span class="taskheader">Container
	Background</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<s:set name="type" value="themeAttribs['CONTAINERBG_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['CONTAINERBG_TYPE']" value='COLOR'
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if> /> Color</td>
			<td><s:textfield name="themeAttribs['CONTAINERBG_COLOR']"
				size='8' id="CONTAINERBGColor"></s:textfield> <a href="#"
				name='CONTAINERBG_COLOR' id='CONTAINERBG_COLOR'
				onclick="cpDiv.select(sform.CONTAINERBGColor,'CONTAINERBG_COLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a><script language="JavaScript">cp.writeDiv()</script>
			</td>
		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['CONTAINERBG_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['CONTAINERBG_IMAGE']"
				size='45' id="CONTAINERBG_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("CONTAINERBG_IMAGE");return false;'
				STYLE="text-decoration: none"> <img border='0'
				src="../images/image.gif" /></a></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Box Background</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<s:set name="type" value="themeAttribs['BOXBG_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['BOXBG_TYPE']" value='COLOR'
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if> /> Color</td>
			<td><s:textfield name="themeAttribs['BOXBG_COLOR']" size='8'
				id="boxbgcolor"></s:textfield> <a href="#" name='BOXBG_COLOR'
				id='BOXBG_COLOR'
				onclick="cpDiv.select(sform.boxbgcolor,'BOXBG_COLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a><script language="JavaScript">cp.writeDiv()</script>
			</td>
		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['BOXBG_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['BOXBG_IMAGE']" size='45'
				id="BOXBG_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("BOXBG_IMAGE");return false;' STYLE="text-decoration: none">
			<img border='0' src="../images/image.gif" /></a></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Box Header
	Background</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<s:set name="type" value="themeAttribs['BOXHEADER_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['BOXHEADER_TYPE']" value='COLOR'
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if> /> Color</td>
			<td><s:textfield name="themeAttribs['BOXHEADER_COLOR']" size='8'
				id="boxheaderColor"></s:textfield> <a href="#"
				name='BOXHEADER_COLOR' id='BOXHEADER_COLOR'
				onclick="cpDiv.select(sform.boxheaderColor,'BOXHEADER_COLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a><script language="JavaScript">cp.writeDiv()</script>
			</td>

		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['BOXHEADER_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['BOXHEADER_IMAGE']"
				size='45' id="BOXHEADER_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("BOXHEADER_IMAGE");return false;'
				STYLE="text-decoration: none"> <img border='0'
				src="../images/image.gif" /></a></td>
		</tr>
	</table>
	</div>
	<br />
	
	<div class="taskbox"><span class="taskheader">Large Text</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<td colspan='2'>This text
			style is applied to Event headers</td>
		</tr>
		<tr>
			<td width='15%'>Color</td>
			<td><s:textfield name="themeAttribs['LARGETEXTCOLOR']" size='8'
				id="largetextcolor"></s:textfield> <a href="#" name='LARGETEXTCOLOR'
				id='LARGETEXTCOLOR'
				onclick="cpDiv.select(sform.largetextcolor,'LARGETEXTCOLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a> <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
			</td>
		</tr>
		<tr>
			<td width='15%'>Font Type</td>
			<td><s:set name="largefonttype"
				value="themeAttribs['LARGETEXTFONTTYPE']"></s:set> <select
				name="themeAttribs['LARGETEXTFONTTYPE']">
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#largefonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select> &nbsp;&nbsp;&nbsp;Font Size&nbsp;&nbsp; <s:set name="largefontsize"
				value="themeAttribs['LARGETEXTFONTSIZE']"></s:set> <select
				name="themeAttribs['LARGETEXTFONTSIZE']">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#largefontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Medium Text</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<td colspan='2'>This text
			style is applied to Column headers</td>
		</tr>
		<tr>
			<td width='15%'>Color</td>
			<td><s:textfield name="themeAttribs['MEDIUMTEXTCOLOR']" size='8'
				id="medtextcolor"></s:textfield> <a href="#" name='MEDIUMTEXTCOLOR'
				id='MEDIUMTEXTCOLOR'
				onclick="cpDiv.select(sform.medtextcolor,'MEDIUMTEXTCOLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a> <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
			</td>
		</tr>
		<tr>
			<td width='15%'>Font Type</td>
			<td><s:set name="mediumfonttype"
				value="themeAttribs['MEDIUMTEXTFONTYPE']"></s:set> <select
				name="themeAttribs['MEDIUMTEXTFONTYPE']">
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#mediumfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select> &nbsp;&nbsp;&nbsp;Font Size&nbsp;&nbsp; <s:set name="mediumfontsize"
				value="themeAttribs['MEDIUMTEXTFONTSIZE']"></s:set> <select
				name="themeAttribs['MEDIUMTEXTFONTSIZE']">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#mediumfontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Small Text</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<td colspan='2'>This text
			style is applied to all other content</td>
		</tr>
		<tr>
			<td width='15%'>Color</td>
			<td><s:textfield name="themeAttribs['SMALLTEXTCOLOR']" size='8'
				id="smalltextcolor"></s:textfield> <a href="#" name='SMALLTEXTCOLOR'
				id='SMALLTEXTCOLOR'
				onclick="cpDiv.select(sform.smalltextcolor,'SMALLTEXTCOLOR');return false; "
				style='text-decoration: none'> <img border='0'
				src="../images/button.bgcolor.gif" /></a> <SCRIPT language=JavaScript>cp.writeDiv()</SCRIPT>
			</td>
		</tr>

		<tr>
			<td width='15%'>Font Type</td>
			<td><s:set name="smallfonttype"
				value="themeAttribs['SMALLTEXTFONTTYPE']"></s:set> <select
				name="themeAttribs['SMALLTEXTFONTTYPE']">
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#smallfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select> &nbsp;&nbsp;&nbsp;Font Size&nbsp;&nbsp; <s:set name="smallfontsize"
				value="themeAttribs['SMALLTEXTFONTSIZE']"></s:set> <select
				name="themeAttribs['SMALLTEXTFONTSIZE']">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#smallfontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Header</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<s:set name="type" value="themeAttribs['HEADER_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['HEADER_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['HEADER_IMAGE']" size='45'
				id="HEADER_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("HEADER_IMAGE");return false;' STYLE="text-decoration: none">
			<img border='0' src="../images/image.gif" /></a></td>

		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['HEADER_TYPE']" value="HTML"
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>>
			HTML</td>
			<td><s:textarea name="themeAttribs['HEADER_HTML']" rows="8"
				cols="45">
			</s:textarea></td>

		</tr>
	</table>
	</div>
	<br />
	<div class="taskbox"><span class="taskheader">Footer</span><br />
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<s:set name="type" value="themeAttribs['FOOTER_TYPE']"></s:set>
			<td width='15%'><input type='radio'
				name="themeAttribs['FOOTER_TYPE']" value="IMAGE"
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			Image</td>
			<td><s:textfield name="themeAttribs['FOOTER_IMAGE']" size='45'
				id="FOOTER_IMAGE"></s:textfield> <a href="#"
				onclick='getImageURL("FOOTER_IMAGE");return false;' STYLE="text-decoration: none">
			<img border='0' src="../images/image.gif" /></a></td>

		</tr>
		<tr>
			<td colspan='2'>or</td>
		</tr>
		<tr>
			<td width='15%'><input type='radio'
				name="themeAttribs['FOOTER_TYPE']" value="HTML"
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>>
			HTML</td>
			<td><s:textarea name="themeAttribs['FOOTER_HTML']" rows="8"
				cols="45">
			</s:textarea></td>
		</tr>
	</table>
	</div>
	<p />
	<table width="100%">
<tr>
<td colspan='2' align='center'>
<input type="button" name="submit" value="Submit" id="submitBtn" />
<input type="button" name="preview" value="Preview" id="previewBtn" />
</td>
</tr>
</table>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var btn = new YAHOO.widget.Button("submitBtn", {  onclick:{fn:themeSubmit } });

var btn1 = new YAHOO.widget.Button("previewBtn", { onclick: { fn: getContentPreview } });
function getPreview(){
	javascript:getContentPreview();
}

function themeSubmit()
{
submitForm(++j);
}

</script>
		