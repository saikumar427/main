
function calcage(secs, num1, num2) {
  s = ((Math.floor(secs/num1))%num2).toString();
  if (LeadingZero && s.length < 2)
    s = "0" + s;
  return  s;
}

function CountBack(secs) {
  if (secs < 0) {
    document.getElementById("cntdwn").innerHTML=FinishMessage;
if(parent.document.getElementById("timetag"))
	parent.document.getElementById("timetag").innerHTML="<div></div>";
	if(slns!=""){ document.getElementById("slns").innerHTML="";
	parent.parent.salestrtinfo();}
    return;
  }
  if(isNaN(secs)){ 
  //alert("not");
  document.getElementById("cntdwn").innerHTML = FinishMessage;
    return;}

  DisplayStr = DisplayFormat.replace(/%%D%%/g, calcage(secs,86400,100000));
  DisplayStr = DisplayStr.replace(/%%H%%/g, calcage(secs,3600,24));
  DisplayStr = DisplayStr.replace(/%%M%%/g, calcage(secs,60,60));
  DisplayStr = DisplayStr.replace(/%%S%%/g, calcage(secs,1,60));

  document.getElementById("cntdwn").innerHTML = DisplayStr;
  if (CountActive)
    setTimeout("CountBack(" + (secs+CountStepper) + ")", SetTimeOutPeriod);
}

function putspan(backcolor, forecolor) {
 document.write("<span  id='cntdwn' style='margin:0px'></span>");
}

if (typeof(BackColor)=="undefined"){
  BackColor = "";
 }
if (typeof(ForeColor)=="undefined")
  ForeColor= "black";
if (typeof(TargetDate)=="undefined")
  TargetDate = "12/31/2020 5:00 AM";
  if (typeof(StartDate)=="undefined")
  StartDate = new Date();
if (typeof(DisplayFormat)=="undefined")
  DisplayFormat = "%%D%% Days, %%H%% Hours, %%M%% Minutes, %%S%% Seconds.";
if (typeof(CountActive)=="undefined")
  CountActive = true;
if (typeof(FinishMessage)=="undefined")
  FinishMessage = "";
if (typeof(CountStepper)!="number")
  CountStepper = -1;
if (typeof(LeadingZero)=="undefined")
  LeadingZero = true;


CountStepper = Math.ceil(CountStepper);
if (CountStepper == 0)
  CountActive = false;
var SetTimeOutPeriod = (Math.abs(CountStepper)-1)*1000 + 990;
putspan(BackColor, ForeColor);
var dthen = new Date(TargetDate);
var dnow = new Date(StartDate);
if(CountStepper>0)
  ddiff = new Date(dnow-dthen);
else
  ddiff = new Date(dthen-dnow);
gsecs = Math.floor(ddiff.valueOf()/1000);
//alert("gsecs"+gsecs);
CountBack(gsecs);


function x(){
//alert(document.getElementById('enddate').value);
return '12/31/2011 18:07';
}
