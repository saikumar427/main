var picker="";
var textfield="";
var imgbutton="";
var textArr=["BODYTEXTCOLOR","LARGETEXTCOLOR","MEDIUMTEXTCOLOR","SMALLTEXTCOLOR","LINKTEXTCOLOR"];
function loadColorPicker(textfieldid, imgid, cpheader, cprad,color){
if(document.getElementById(cprad)){
		 $('#'+cprad).iCheck('check');
}
	
textfield=textfieldid;
imgbutton=imgid;
onRgbChange(color);
}


function onRgbChange(color){
	if($.inArray(imgbutton,textArr)>-1)
	onchaneFontColors(imgbutton, color);
	else	
	onchangeCP(imgbutton, color);
}

function submitColorPicker(){
	document.getElementById(textfield).value='#'+picker.get("hex");
	document.getElementById(imgbutton).style.backgroundColor='#'+picker.get("hex");
	//YAHOO.cp.popup.dialog.cancel();
	//alert(y.getElementById('pageheader'));y.getElementById('pageheader').style.color="#0000ff";
	//handleCPCancel();
	//colorsTextPreview();//in looknfeel.js
}

function handleCPCancel(){
	onchangeCP(imgbutton, document.getElementById(textfield).value);
	//YAHOO.cp.popup.dialog.cancel();
}