<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="../css/backgroundDisablePopup.css" />
<script type="text/javascript" src="../js/colorPicker.js"></script> 
<script src="../js/emailmarketing/campaignmanage.js" language="JavaScript"	type="text/javascript"></script>
<script src="../js/emailmarketing/previewdata.js" language="JavaScript"	type="text/javascript"></script>
<script type="text/javascript" src="../js/popup.js">
function popupdummy(){}

</script>
<script type="text/javascript" language="JavaScript" src="../js/nice.js"></script>
<script>
var o = {
buttonList: ['save','bold','italic','underline','left','center','right','justify','ol','ul','fontSize','fontFamily','fontFormat','indent','outdent','image','link','unlink','forecolor','bgcolor','xhtml','textalign','align','removeformat','strikethrough','subscript','superscript','arrow','upload','close'],
iconsPath:('../images/nicEditIcons-latest.gif'),
maxHeight : 250
};
</script>
<script language='Javascript' >
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
	showYUIinIframe("Image Upload",url,400,200);
}
</script>
<script>
var cp = new ColorPicker('window'); // Popup window
var cpDiv = new ColorPicker(); // DIV style
</script>
<s:form name="createcampaign" id="createcampaign" action="createcampaign!saveCampaign" theme="simple">
<s:hidden name="campId"/>
<div id="result" class="errorMessage" style="display:none"></div>
<jsp:include page="/help/myemailmarketing_messages.jsp"></jsp:include>
<div class="taskcontent"><script>setHelpContent("createcampaign_topheader_helpcontent")</script></div>
<div style="height:3px;"></div>
<div class="taskbox"><span class="taskheader">Name</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td  width="30%">Campaign Name *</td>
<td><s:textfield name="campaignData.campName" theme="simple" size="60" id="cName"/></td>
</tr>
</table> 
</div>
<br/>
<div class="taskbox"><span class="taskheader">Content</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td>
<table width="100%"><tr>
<td width="30%" ></td>
<td width="65%">
<s:iterator value="%{descriptiontype}" var="type">
<s:radio name="campaignData.campDescType" list="#{key: value}" value="%{campaignData.campDescType}"
					onclick="reloadeditors();" />
</s:iterator>
</td>
<td align="left" >
<input id="previewBtn" type="button"  value="Preview"/>

</td>
</tr></table>
</td>
</tr>
<tr>
<td align="center" colspan="2">
<div id="fckdesctxtcontent" style="display:block;">
<s:textarea name="campaignData.textcontent" rows="15" cols="101" ></s:textarea>
</div>
</td>
</tr>
<%-- customtextareacontent--%>
<tr>
<td align="center" colspan="2">
<div id="fckdesccontent" style="display:block;width:700px;max-width: 700px;">
<s:textarea name="campaignData.description"  rows="15" cols="120" id="msg"  ></s:textarea>
</div>
</td>
</tr>
</table>
</div>
<div id="contentDetails" style="display: none">
<br/>
<div class="taskbox"><span class="taskheader">Look &amp; Feel</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td width="30%" >Background</td>
<td>
<div id="boder">
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<s:set name="type" value="campaignData.backgroundType"></s:set>
<td  width='15%'>
<input type='radio' name="campaignData.backgroundType" value="COLOR" <s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>> Color</td>
<td>
<s:textfield name="campaignData.backgroundColor"  size='8' id="bgcolor"></s:textfield>
<a href="#" name="BG_COLOR" id="BG_COLOR" onclick="cpDiv.select(createcampaign.bgcolor,'BG_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a><script language="JavaScript">cp.writeDiv()</script>
</td>
</tr>
<!-- 
<tr>
<td colspan='2'>or</td>
</tr>
<tr>
<td  width='15%'><input type='radio' name="campaignData.backgroundType" value="IMAGE" <s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>> Image</td>
<td>
<s:textfield name="campaignData.backgroungImage"  size='45' id="BG_IMAGE" theme="simple"></s:textfield>
<a href="#"  onclick='getImageURL("BG_IMAGE");return false;' STYLE="text-decoration: none">
<img border='0' src="../images/image.gif"/></a>
</td>
</tr>
 -->
	
</table> 
</div>
</td>
</tr>
<tr>
<td width="30%" >Borders </td>
<td>
<s:textfield theme="simple" name="campaignData.bordercolor"  id="bordercolor"></s:textfield>
<a href="#" name="BORDER_COLOR" id="BORDER_COLOR" onclick="cpDiv.select(createcampaign.bordercolor,'BORDER_COLOR');return false; " style='text-decoration:none'> 
<img border='0' src="../images/button.bgcolor.gif"/></a><script language="JavaScript">cp.writeDiv()</script>
</td>
</tr>
<tr>
<td width="30%" >Border width </td>
<td>
<s:set name="borderwidth" value="campaignData.borderwidth"></s:set>
<select name="campaignData.borderwidth" >
<s:iterator value="widthSizes">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#borderwidth==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select>
</td>
</tr>
</table>
</div>
</div>

<br/>
<table  width="100%">
<tr>
<td colspan='1' align='right'>
<input id="submitBtn" type="button"  value="Submit"/>
</td>
<td colspan='1' align='left'>
<a id="cancellink"	href="javascript:history.back(1)" >Cancel</a>
</td>
</tr>
</table>
<br/>
<div id="helpcontent" class="taskcontent">
<jsp:include page="camphelpercontent.jsp" />
</div>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var j=0;
new nicEditor(o).panelInstance('msg');
function submitForm(event,jsonobj)
{
j++;
if(j==1){
submit(event,jsonobj);}
}
function submit(event, jsonobj){
YAHOO.ebee.popup.wait.show();
document.createcampaign.target="_self";
document.getElementById("createcampaign").action="createcampaign!saveCampaign";
var name=document.getElementById("cName").value;
name=trim(name);
if(name.length>0){
var content=nicEditors.findEditor('msg').getContent();
var description=replaceSpecialChars(content);
var finaldescription=convert(description);
document.getElementById('msg').value=finaldescription;	
	$('createcampaign').request({
         onSuccess: function(t) {
	        //var previewFileName=t.responseText;		
	       // alert("previewFileName"+previewFileName);
	       // var url='createcampaign!contentShow';
	       // var result=t.responseText;
    		//showPopUpDialogWithCustomButtons(t, "Preview", "");
    		 json=eval('('+t.responseText+')');
	 	    var campid=json.campid;
	 	    window.location.href='../myemailmarketing/campaignlistmanage!campaignInfo?campId='+campid;
	    },onFailure:function(){
	    	alert("Error");
	    }
    });
	$('createcampaign').action='createcampaign!saveCampaign';
}
else{
	YAHOO.ebee.popup.wait.hide();
	$("result").show();
	$("result").scrollTo();
	document.getElementById("result").innerHTML="Campaign Name is Empty";
j=0;
}
}
function previewThisCampaign(event,jsonobj){
	document.getElementById("createcampaign").action="createcampaign!previewCampaign";
	document.createcampaign.target="_blank";
	document.getElementById("createcampaign").submit();
}
function init() {

var submitBtn = new YAHOO.widget.Button("submitBtn", { onclick: { fn: submitForm } });
var previewBtn = new YAHOO.widget.Button("previewBtn", { onclick: { fn: getContentPreview,obj:{"type":"new"} } });
var cancellink = new YAHOO.widget.Button("cancellink", { type:"link",href:"javascript:history.back(1)" });
setRoundCorners();
}
YAHOO.util.Event.onDOMReady(init);
reloadeditors();

function replaceSpecialChars(content){
   var s = content;
   s = s.replace(/[\u2018|\u2019|\u201A]/g, "\'");
   s = s.replace(/[\u201C|\u201D|\u201E]/g, "\"");
   s = s.replace(/\u2026/g, "...");
   s = s.replace(/[\u2013|\u2014]/g, "-");
   s = s.replace(/\u02C6/g, "^");
   s = s.replace(/\u2039/g, "<");
   s = s.replace(/\u203A/g, ">");
   s = s.replace(/[\u02DC|\u00A0]/g, " ");
    return s;
}

function convert(text){
 var chars = ["ò","δ","σ","Σ","ρ","ω","Ω","Ψ","Ο","ξ","Ξ","ν","μ","Λ","κ","ι","θ","Θ","η","ζ","π","☻","☺","♠","♣","♦","♥","©","Û","®","ž","Ü","Ÿ","Ý","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
  for(x=0; x<chars.length; x++){
  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
  }
  return text;
  }

</script>