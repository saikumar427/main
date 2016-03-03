<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/js/i18n/en/properties.js"></script>
<div class="col-xs-12 col-sm-9" align="center">
	<form id="attendeelist" name="attendeelist" action="AttendeeListDisplayFields!saveDisplayInformation" method="post" >
	<s:hidden name="eid"></s:hidden>
	<div id="attendeeqlist" ></div>	     
			
			<table border="0">
				<tr>
					<td>					
					<s:select name="selattribs" list="questions" listKey="key" listValue="value" cssClass="form-control"
					multiple="true" size="10" style="width:200px;" theme="simple"/>
					</td>
					<td width="10px"></td>
					<!-- <td align="center" valign="middle">					
						<input type="button" value="&gt;" onclick="moveOptions(this.form.selattribs, this.form.selattendeeattribs);" /><br />
						<input type="button" value="&lt;" onclick="moveOptions(this.form.selattendeeattribs, this.form.selattribs);" />								
					</td> -->
					
						<td align="center" valign="middle">					
						<div  style="cursor:pointer"> <i class="fa fa-arrow-right moves-arrow" style="color:#AAAAAA" onclick="moveOptions(document.attendeelist.selattribs, document.attendeelist.selattendeeattribs);" > </i><br/> </div>
						<div  style="cursor:pointer"> <i class="fa fa-arrow-left moves-arrow" style="color:#AAAAAA" onclick="moveOptions(document.attendeelist.selattendeeattribs, document.attendeelist.selattribs);" >	</i>	</div>						
					</td>
					
					<td width="10px"></td>
					<td>
					<s:select name="selattendeeattribs" list="attendeeQuestions" listKey="key" listValue="value" cssClass="form-control"
				     multiple="true" size="10" style="width:200px;" theme="simple" id="selattendeeattribs" onkeyup="call(document.getElementById('selattendeeattribs'));" onclick="call(document.getElementById('selattendeeattribs'));"/>
					</td>
					<td width="10px"></td>
					 <!-- <td>
					<div id="up"><img src="../images/up.gif" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selattendeeattribs'))"></div>
					<div id="down"><img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selattendeeattribs'))"></div>
					</td>  -->
					 
					 <td>              
    	 			<div id="up" style="cursor:pointer">	<i class="fa fa-arrow-up moves-arrow" style="color:#AAAAAA" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selattendeeattribs'))" >   </i></div>
    	 			<div id="down" style="cursor:pointer">	<i class="fa fa-arrow-down moves-arrow" style="color:#AAAAAA" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selattendeeattribs'))">    </i></div>
    				</td> 					
					
				</tr>
			</table>	
	</form>
	
		<div class="row">
		<div class="col-xs-offset-4">
		<div class="pull-right">
		<button class="btn btn-primary submit"><s:text name="global.submit.btn.lbl"></s:text></button>&nbsp;
		<button class="btn btn-active" onclick="parent.closePopup();" style="margin-right:25px;"><s:text name="global.cancel.btn.lbl"></s:text></button>
		</div>
		</div>
		</div>
</div>	
 
 
<script>
var NS4 = (navigator.appName == "Netscape" && parseInt(navigator.appVersion) < 5);
function addOption(theSel, theText, theValue)
{
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}

function call(elem)
{
var sel = elem.selectedIndex;
var ind=elem.options[sel].index;
var len=elem.length;
if(ind==0)
document.getElementById('up').style.opacity='0.4';
else
document.getElementById('up').style.opacity='1.0';
if(ind==len-1)
document.getElementById('down').style.opacity='0.4';
else
document.getElementById('down').style.opacity='1.0';
}


function moveOptions(theSelFrom, theSelTo)
{  
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  var attname=props.epc_displayfields_attname;
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      if(theSelFrom.options[i].text=="Attendee Name"){
    	  return;
      }
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }  
 
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  if(NS4) history.go(0);
}

function ManageQuestion_mvUpOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex <= 0) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	elem.options[selindex].text = elem.options[selindex - 1].text;
	elem.options[selindex].value = elem.options[selindex - 1].value;
	
	elem.options[selindex - 1].text = temp;
	elem.options[selindex - 1].value = val;
	elem.selectedIndex = selindex - 1;
}

function ManageQuestion_mvDnOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex == elem.length - 1) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	
	elem.options[selindex].text = elem.options[selindex + 1].text;
	elem.options[selindex].value = elem.options[selindex + 1].value;
	
	elem.options[selindex + 1].text = temp;
	elem.options[selindex + 1].value = val;
	elem.selectedIndex = selindex + 1;
}




 $(".submit").on('click', function(){
 		var parent = document.getElementById("attendeeqlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("selattendeeattribs");	
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	submitFormAndCloseWindow('attendeelist', false);
 	
 });
 
 
 function submitFormAndCloseWindow(formId,reloadYN){
	 var url ="AttendeeListDisplayFields!saveDisplayInformation";
	 $.ajax({
		  url : url,
		  type : 'POST',
		  data : $('#'+formId).serialize(),
		  success : function(t){			 
			  if(!isValidActionMessage(t)) return;
			  if(t.indexOf("success")>-1){	
				  parent.closePopup();
		        	if(reloadYN)
		        		window.location.reload( true );	
		        } 
		  }
	 });
 }
 
 
 function isValidActionMessage(messageText){

	 if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
	         alert('You need to login to perform this action');
	         window.location.href="../user/login";
	         return false;
	     }
	  else if(messageText.indexOf('Something went wrong')>-1){
	          alert('Sorry! You do not have permission to perform this action');
	          YAHOO.ebee.popup.wait.hide();
	          return false;
	      }
	     return true;

	 }
 
 function createHiddenTextElement(index,etype,val){
		var pname=document.createElement("input");
		pname.type="hidden";
		pname.name="optionsList["+index+"]."+etype;	
		pname.value=val;
		return pname;
	}
/*

var parent = document.getElementById("attendeeqlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("selattendeeattribs");	
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	submitFormAndCloseWindow('attendeelist', '');

*/
</script> 


