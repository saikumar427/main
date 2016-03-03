var currentDivId="";
     
/*function loadEventPreview(divid){
	serveraddress='http://localhost';
	var url=serveraddress+"/event?eid="+_eid+"&preview_pwd=no";
//alert(url);

var layout="<div id='"+divid+"imgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div><iframe scrolling='no' id='"+divid+"eventpreview' frameborder='0' width='100%' height='100%' src='"+url+"' style='display:none' onload='onpreviewload(\""+divid+"\");resizeIframe();'></iframe>";
document.getElementById(divid+'previd').innerHTML=layout;
document.getElementById(divid+'previd').style.display='block';
document.getElementById('previewbtn').style.display='block';
$('#'+divid).iCheck('check');
}*/

     
function loadEventPreview(divid){
	//serveraddress='http://localhost';
var url=serveraddress+"/event?eid="+_eid+"&preview_pwd=no";
//alert(url);

var layout="<div id='"+divid+"imgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div><iframe scrolling='no' id='"+divid+"eventpreview' frameborder='0' width='100%' height='100%' src='"+url+"' style='display:none' onload='onpreviewload(\""+divid+"\");resizeIframe();'></iframe>";
document.getElementById(divid+'previd').innerHTML=layout;

document.getElementById(divid+'previd').style.display='block';
//document.getElementById(divid+'previd').style.height='50px';
document.getElementById('previewbtn').style.display='block';
$('#'+divid).iCheck('check');
}



function colorSubmitForm(){
checkRadioBtn();
colorsubbtn.set('disabled', true);
colorsubbtn.set('label', 'Updating..');
 	
 	$('sform').request({
  		onComplete: function(o){
  		if(!isValidActionMessage(o.responseText)) return;
			window.location.href="/main/eventmanage/LooknFeel?eid="+_eid;	  		
  		}
	});
}

/*function getColorContentPreview(){	
	$('sform').action='EventCustomization!preview';
	$('sform').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;
	       // var url='../membertasks/FileRead?readFileName='+''+previewFileName+'';
	       var url='/event?eid='+_eid+'&preview=show';
	        showYUIinIframe("Colors &amp; Text",url,600,500);
	    }
    });
	$('sform').action='EventCustomization!save';
}*/

function colorsTextPreview(){
document.getElementById('colorprevid').innerHTML="<div id='colorimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div>";
checkRadioBtn();
var url="/event?eid="+_eid+"&preview=show&preview_pwd=no";
var layout="<div id='colorimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div><iframe scrolling='no' id='coloreventpreview' width='100%' height='100%' src='"+url+"' style='display:none' onload='onpreviewload(\"color\");resizeIframe();'></iframe>";
$.ajax({
	type:'POST',
	data:$('#sform').serialize(),
	url:'EventCustomization!preview',
	success:function(result){
		//alert(result);
		document.getElementById('colorprevid').innerHTML=layout;
		document.getElementById('colorprevid').style.display='block';
		//document.getElementById('colorprevid').style.height='50px';
	}
});


	/*$('sform').action='EventCustomization!preview';
	$('sform').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;
	        document.getElementById('colorprevid').innerHTML=layout;
			document.getElementById('colorprevid').style.display='block';
			document.getElementById('colorprevid').style.height='50px';
	    }
    });
	$('sform').action='EventCustomization!save';*/

}

function onpreviewload(id){
document.getElementById(id+'imgid').style.display='none';
document.getElementById(id+'eventpreview').style.display='block';
//document.getElementById(id+'previd').style.height='200px';
/*var x=document.getElementById(id+'eventpreview');
var y=(x.contentWindow || x.contentDocument);
if (y.document)y=y.document;
var text='.main-container {width:1100px;}';
var h = y.getElementsByTagName('head').item(0);
var s = y.createElement("style");
	s.type = "text/css"; 
	
	if (s.styleSheet) { // IE
		s.styleSheet.cssText = text;
	} else { // others
		s.appendChild(document.createTextNode(text));
	}
	h.appendChild(s);*/
}

function themesSubmitForm(){
var ebeechk = document.getElementById('ebeethemeid').checked;
var mychk=false;
if(document.getElementById('mythemeid'))
	mychk = document.getElementById('mythemeid').checked;
if(ebeechk==true || mychk==true){
themessubbtn.set('disabled', true);
themessubbtn.set('label', 'Updating..');
//themesprebtn.set('disabled', true);
$('themesform').request({
    onFailure: function() {alert("in failure"); $('errormsg').update('Failed to get results') },
    onSuccess: function(t) {
        if(!isValidActionMessage(t.responseText)) return;
			window.location.href="/main/eventmanage/LooknFeel?eid="+_eid;
    }
});
}else{
 alert("Please select Theme");
}
}
function getThemesContentPreview(){
var mychk=false;
if(document.getElementById('mythemeid'))
	mychk = document.getElementById('mythemeid').checked;
if(mychk==false)
	document.getElementById('ebeethemeid').checked=true;
	
document.getElementById('themesprevid').innerHTML="<div id='themesimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div>";
var url=serveraddress+"/event?eid="+_eid+"&preview=show&preview_pwd=no";
var layout="<div id='themesimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div><iframe id='themeseventpreview' width='100%' height='100%' src='"+url+"' style='display:none' onload='javascript:onpreviewload(\"themes\");'></iframe>";
	

		$.ajax({
			type:'get',
			data:$('#themesform').serialize(),
			url:'EventThemes!preview',
			success:function(result){
				   document.getElementById('themesprevid').innerHTML=layout;
				   document.getElementById('themesprevid').style.display='block';
				 //  document.getElementById('themesprevid').style.height='50px';
				
			}
		});
}

function checkProTicketing(){
var powertype = document.getElementById('powertypeid').value;
var currlevel = document.getElementById('currlevelid').value;
var currfee = document.getElementById('currfeeid').value;
if(powertype=='RSVP'){
	//getCCStatus(_eid,'LooknFeel_HtmlCssSubmit','RSVP',currlevel,currfee);
	specialFee(_eid,'150','LooknFeel_HtmlCssSubmit','RSVP');
}else 
	specialFee(_eid,'300','LooknFeel_HtmlCssSubmit','Ticketing');
}

function submitHtmlnCss(){
	
	 $.ajax({
	type:'GET',
	data:$('#htmlncss').serialize(),
	url:'HTMLnCSSCustomization!save',
	success:function(result){
if(!isValidActionMessage(result))
	return;
window.location.reload();
}
	});
	/*htmlsubbtn.set('disabled', true);
	htmlsubbtn.set('label', 'Updating..');
	$('htmlncss').request({
  		onComplete: function(o){
  		if(!isValidActionMessage(o.responseText)){ window.location.href="../user/login"; return;}
			window.location.href="/main/eventmanage/LooknFeel?eid="+_eid;		
  		}
	});*/
}

function getHtmlnCssContentPreview(){
document.getElementById('htmlcssprevid').innerHTML="<div id='htmlcssimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div>";
var url=serveraddress+"/event?eid="+_eid+"&preview=show&preview_pwd=no";
var layout="<div id='htmlcssimgid'><br/>Preview loading...<br/><img src='../images/ajax-loader.gif'></div><iframe id='htmlcsseventpreview' width='100%' height='100%' src='"+url+"' style='display:none' onload='javascript:onpreviewload(\"htmlcss\");'></iframe>";
	
	
	$('htmlncss').action='HTMLnCSSCustomization!preview';
	$('htmlncss').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;		
			document.getElementById('htmlcssprevid').innerHTML=layout;
		    document.getElementById('htmlcssprevid').style.display='block';
		   // document.getElementById('htmlcssprevid').style.height='50px';
		    }
    });
	$('htmlncss').action='HTMLnCSSCustomization!save';
}

function getImageURL(divId,imgrad){
	//alert('new mssa');
	document.getElementById(imgrad).checked=true;
	currentDivId=divId;
	url='../membertasks/ImageUpload';
	$('.modal-title').html('Image Upload');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","220px"); 
	});
	$('#myModal').modal('show');
}

function setWebPath(fullPath){
	var temp=currentDivId;
	currentDivId=currentDivId+'_IMAGE';
	getImagePreview(currentDivId,fullPath);
	$('#'+temp+'_COLOR').css({"background-image":"url('"+fullPath+"')","background-size":"20px 13px"});
	$('#myModal').modal('hide');
}
function closeFileUploadWindow(){
	//YAHOO.ebee.popup.dialog1.cancel();
	$('#myModal').modal('hide');
}

function getImagePreview(currentDivId, fullPath){
var text='';
if(currentDivId=='BG_IMAGE'){
//text="body {background: url("+fullPath+");} #container {border-top: 2px solid url("+fullPath+");}";
text="body {background: url("+fullPath+") !important;} #container {border-top: 2px solid url("+fullPath+") !important;}";
console.log("the current div is::"+currentDivId+"::text::"+text);
}

if(currentDivId=='CONTAINERBG_IMAGE'){
text="#container {background: url("+fullPath+") !important;}";
}

if(currentDivId=='BOXBG_IMAGE'){
text=".rightboxcontent {background: url("+fullPath+") !important;} .leftboxcontent {background: url("+fullPath+") !important;}";
}

if(currentDivId=='BOXHEADER_IMAGE'){
text=".rightboxheader {background: url("+fullPath+") 30% 20% repeat-x !important;} .leftboxheader {background: url("+fullPath+") 30% 20% repeat-x !important;}";
}
if(document.getElementById('coloreventpreview')){	
var x=document.getElementById('coloreventpreview');
var y=(x.contentWindow || x.contentDocument);
if (y.document)y=y.document;
if(text != ''){
	//y.documentElement.innerHTML=y.documentElement.innerHTML+"<style type='text/css'>"+text+"</style>";
	var h = y.getElementsByTagName('head').item(0);
	var s = y.createElement("style");
	s.type = "text/css"; 
	if (s.styleSheet) { // IE
		s.styleSheet.cssText = text;
	} else { // others
		s.appendChild(document.createTextNode(text));
	}
	h.appendChild(s);
}	
if(currentDivId=='HEADER_IMAGE' && y.getElementById('headertop'))	
	y.getElementById('headertop').innerHTML="<img src="+fullPath+">";

if(currentDivId=='FOOTER_IMAGE')	
	y.getElementById('footer').innerHTML="<img src="+fullPath+">";
	
/*var iframeheight=y.body.scrollHeight+100;
document.getElementById('colorprevid').style.height=iframeheight+"px";*/
}
}

function HTMLnCSSblock(){
	document.getElementById("colorntextcontent").style.display='none';
	document.getElementById("themescontent").style.display='none';
	document.getElementById("htmlcsscontent").style.display='block';
	//getHtmlnCssContentPreview();
	loadEventPreview('htmlcss');
}

function onchangeFonts(elementid,ftype){
var text='';
if(elementid=='BODYTEXTFONTTYPE'){
	text="body {font: "+font+" !important;}";
	text=text+"td {font:"+font+ "!important;}";
}

if(elementid=='BODYTEXTFONTSIZE'){
	text="body {fon-size: "+ftype+" !important;}";
	text=text+"td {font-size:"+ftype+ "!important;}";
}

if(elementid=='LARGETEXTFONTTYPE'){
	//var ftype=document.getElementById('LARGETEXTFONTTYPE').value;
	text=".large {font-family: "+ftype+" !important;}";
}

if(elementid=='LARGETEXTFONTSIZE'){
	//var fsize=document.getElementById('LARGETEXTFONTSIZE').value;
	text=".large {font-size: "+ftype+" !important;}";
}

if(elementid=='MEDIUMTEXTFONTYPE'){
	//var ftype=document.getElementById('MEDIUMTEXTFONTYPE').value;
	text=".medium {font-family: "+ftype+" !important;}";
}

if(elementid=='MEDIUMTEXTFONTSIZE'){
	//var fsize=document.getElementById('MEDIUMTEXTFONTSIZE').value;
	text=".medium {font-size: "+ftype+" !important;}";
}

if(elementid=='SMALLTEXTFONTTYPE'){
	//var ftype=document.getElementById('SMALLTEXTFONTTYPE').value;
	text=".small {font-family: "+ftype+" !important;}";
}

if(elementid=='SMALLTEXTFONTSIZE'){
	//var fsize=document.getElementById('SMALLTEXTFONTSIZE').value;
	text=".small {font-size: "+ftype+" !important;}";
}

if(elementid=='LINKTEXTFONTTYPE'){
	//var ftype=document.getElementById('LINKTEXTFONTTYPE').value;
	text="#container a {font-family: "+ftype+" !important;} .rightcol a {font-family: "+ftype+" !important;}";
}

if(elementid=='LINKTEXTFONTSIZE'){
	//var fsize=document.getElementById('LINKTEXTFONTSIZE').value;
	text="#container a {font-size: "+ftype+" !important;} .rightcol a {font-size: "+ftype+" !important;}";
}

if(document.getElementById('coloreventpreview')){
	var x=document.getElementById('coloreventpreview');
	var y=(x.contentWindow || x.contentDocument);
	if (y.document)y=y.document;
	var h = y.getElementsByTagName('head').item(0);
	var s = y.createElement("style");
	s.type = "text/css"; 
	
	if (s.styleSheet) { // IE
		s.styleSheet.cssText = text;
	} else { // others
		s.appendChild(document.createTextNode(text));
	}
	h.appendChild(s);
}	
}

function onchaneFontColors(elementid, color){
	var text='';
	if(elementid=='BODYTEXTCOLOR')
		text="td {color:"+document.getElementById('bodytextcolor').value+ "!important;}";
	if(elementid=='LARGETEXTCOLOR')
		text=".large {color: "+document.getElementById('largetextcolor').value+" !important;}";
	if(elementid=='MEDIUMTEXTCOLOR')
		text=".medium {color: "+document.getElementById('medtextcolor').value+" !important;}";
	if(elementid=='SMALLTEXTCOLOR')
		text=".small {color: "+document.getElementById('smalltextcolor').value+" !important;}";
	if(elementid=='LINKTEXTCOLOR')
		text="#container a {color: "+document.getElementById('linktextcolor').value+" !important;} .rightcol a {color: "+document.getElementById('linktextcolor').value+" !important;}";

	if(document.getElementById('coloreventpreview')){
		var x=document.getElementById('coloreventpreview');
		var y=(x.contentWindow || x.contentDocument);
		if (y.document)y=y.document;
		var h = y.getElementsByTagName('head').item(0);
		var s = y.createElement("style");
		s.type = "text/css"; 
		if (s.styleSheet)  // IE
			s.styleSheet.cssText = text;
		 else  // others
			s.appendChild(document.createTextNode(text));
		h.appendChild(s);
	}	
}





function enableCP(cpbtn,imgbtn,color){
//document.getElementById(cpbtn).disabled=false;
//document.getElementById(imgbtn).disabled=true;
var colour = document.getElementById(color).value;
onchangeCP(cpbtn, colour);
}

function enableIMG(imgbtn,cpbtn,img){
//document.getElementById(imgbtn).disabled=false;
//document.getElementById(cpbtn).disabled=true;
var imgpath = document.getElementById(img).value;
getImagePreview(img, imgpath);
}

function htmlBlock(radcheck){
		if(radcheck=='headerdef'){
			document.getElementById("hdrhtml").style.display='none';
		}
		if(radcheck=='headerimage'){
			document.getElementById("hdrhtml").style.display='none';
		}
		if(radcheck=='headerhtml'){
			document.getElementById("hdrhtml").style.display='block';
		}
		if(radcheck=='footerdef'){
			document.getElementById("ftrhtml").style.display='none';
		}
		if(radcheck=='footerimage'){
			document.getElementById("ftrhtml").style.display='none';
		}
		if(radcheck=='footerhtml'){
			document.getElementById("ftrhtml").style.display='block';
		}
}

function getHTMLPreview(event,jsonObj){
var text='';
text =document.getElementById(jsonObj.htmlid).value;
if(document.getElementById('coloreventpreview')){
	var x=document.getElementById('coloreventpreview');
	var y=(x.contentWindow || x.contentDocument);
	if (y.document)y=y.document;
	y.getElementById(jsonObj.divid).innerHTML=text;
	/*var iframeheight=y.body.scrollHeight+100;
	document.getElementById('colorprevid').style.height=iframeheight+"px";*/
}
}

function onchangeCP(imgbutton, colour){
var text='';
if(imgbutton=='BG_COLOR'){
text="body {background: "+colour+" !important;} #container {border-top: 2px solid "+colour+" !important;}";
}

if(imgbutton=='CONTAINERBG_COLOR'){
text="#container {background: "+colour+"  !important;}";
}

if(imgbutton=='BOXBG_COLOR'){
text=".rightboxcontent {background: "+colour+" !important;} .leftboxcontent {background:"+colour+" !important;}";
}

if(imgbutton=='BOXHEADER_COLOR'){
	console.log("the color in box header is::"+colour);
	
text=".rightboxheader {background: "+colour+" 30% 20% repeat-x  !important;} .leftboxheader {background:"+colour+" 30% 20% repeat-x  !important;}";
}

if(imgbutton=='BODYTEXTCOLOR'){
	text="body {color: "+colour+" !important;} td {color:"+colour+"  !important;}";
}

if(imgbutton=='LARGETEXTCOLOR'){
	text=".large {color: "+colour+" !important;}";
}

if(imgbutton=='MEDIUMTEXTCOLOR'){
	text=".medium {color: "+colour+" !important;}";
}

if(imgbutton=='SMALLTEXTCOLOR'){
	text=".small {color: "+colour+" !important;}";
}

if(imgbutton=='LINKTEXTCOLOR'){
	text="#container a {color: "+colour+" !important;} .color a {font-family: "+colour+" !important;}";
}

if(document.getElementById('coloreventpreview')){	
var x=document.getElementById('coloreventpreview');
var y=(x.contentWindow || x.contentDocument);
if (y.document)y=y.document;
//y.getElementById('main-container').style.backgroundColor=colour;
var h = y.getElementsByTagName('head').item(0);
var s = y.createElement("style");
	s.type = "text/css"; 
	
	if (s.styleSheet) { // IE
		s.styleSheet.cssText = text;
	} else { // others
		s.appendChild(document.createTextNode(text));
	}
	h.appendChild(s);
//if(text != '')
	//y.documentElement.innerHTML=y.documentElement.innerHTML+"<style type='text/css'>"+text+"</style>";
	/*var iframeheight=y.body.scrollHeight+100;
	document.getElementById('colorprevid').style.height=iframeheight+"px";*/
}

}

function checkcp(cprad,cpid,colorid){
/*if(cprad)
	document.getElementById(cprad).checked=true;
var color=document.getElementById(colorid).value;
alert(color);
onchangeCP(cpid,color);
document.getElementById(cpid).style.backgroundColor=color;*/
}

function checkRadioBtn(){
if(document.getElementById('BG_IMAGE').value=='')
	$('#radioPageColor').iCheck('check');
	//document.getElementById('radioPageColor').checked=true;

if(document.getElementById('CONTAINERBG_IMAGE').value=='')
	$('#radioContainerColor').iCheck('check');
	//document.getElementById('radioContainerColor').checked=true;
if(document.getElementById('BOXBG_IMAGE').value=='')
	$('#radioBoxColor').iCheck('check');
	//document.getElementById('radioBoxColor').checked=true;
if(document.getElementById('BOXHEADER_IMAGE').value=='')
	$('#radioBoxHeaderColor').iCheck('check');
	//document.getElementById('radioBoxHeaderColor').checked=true;
if(document.getElementById('radioHeaderImage').checked){
	$('#ShowHeaderHtml').hide();
	//document.getElementById("ShowHeaderHtml").style.display='none';
	if(document.getElementById('HEADER_IMAGE').value=='')
		$('#radioHeaderColor').iCheck('check');
		//document.getElementById('radioHeaderColor').checked=true;
}else if(document.getElementById('radioHeaderHtml').checked){
	if(document.getElementById('hdrhtmltext').value==''){
		$('#radioHeaderColor').iCheck('check');
		//document.getElementById('hdrdef').checked=true;
		$('#ShowHeaderHtml').hide();
		//document.getElementById("hdrhtml").style.display='none';
	}
}
if(document.getElementById('radioFooterImage').checked){
	$('#ShowFooterHtml').hide();
	//document.getElementById("ftrhtml").style.display='none';
	if(document.getElementById('FOOTER_IMAGE').value=='')
		$('#radioFooterColor').iCheck('check');
		//document.getElementById('ftrdef').checked=true;
}else if(document.getElementById('radioFooterHtml').checked){
	if(document.getElementById('ftrhtmltext').value==''){
		$('#radioFooterColor').iCheck('check');
		//document.getElementById('ftrdef').checked=true;
		$('#ShowFooterHtml').hide();
		//document.getElementById("ftrhtml").style.display='none';
	}
}
}