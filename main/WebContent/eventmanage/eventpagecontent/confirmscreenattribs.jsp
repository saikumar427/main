<%@taglib uri="/struts-tags" prefix="s"%>
<div id="attendeeqlist"></div>
<div class="row">
	<s:if test='%{chkbuyerquestions=="Y"}'>
	<div class="col-md-12">
		<div class="col-md-12" style="margin-bottom: 10px;">
			<span class="confirmation sm-font"><s:text name="cps.buyer.qno.lbl"/></span>&nbsp;&nbsp;
			<s:if test="%{powertype!='RSVP'}">
				<i class="fa fa-info-circle"  style="cursor: pointer" id="applicableinfo1"></i>
			</s:if>
		</div>
		<div class="col-md-12 row">
			<div class="col-md-5 col-sm-5 col-xs-5">
				<s:select name="buyattribs" list="buyattribs" listKey="key" listValue="value"  cssClass="form-control" multiple="true" size="10"  theme="simple" id="buyattribs"/>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-center" style="   margin: 65px -25px 0px -25px;">
				<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveTransactionQRight();"></i><br>
				<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveTransactionQLeft();"></i>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-5" >
				<s:select name="buyselattribs" list="buyselattribs"  listKey="key" listValue="value"  cssClass="form-control"
     			 multiple="true" size="10"  theme="simple" id="buyselattribs" onkeyup="call(document.getElementById('buyselattribs'),'up1','down1');" onclick="call(document.getElementById('buyselattribs'),'up1','down1');"/>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-left" style="margin: 65px 0px 0px -15px;">
				<div id="up1"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('buyselattribs'))"></i></div>
				<div id="down1"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('buyselattribs'))"></i></div>
			</div>		
		</div>
	</div>
	</s:if>
	<s:if test='%{chkattendeequestions=="Y"}'>
		<div class="col-md-12">
			<div class="col-md-12" style="  margin: 10px 0px 10px 0px;">
				<s:if test="%{powertype!='RSVP'}">
					<span class="confirmation sm-font"><s:text name="cps.ticketing.attendee.lbl"/></span>&nbsp;&nbsp;
					<i class="fa fa-info-circle " style="cursor: pointer" id="applicableinfo2"></i>
				</s:if><s:else>
					<span class="confirmation sm-font"><s:text name="cps.rsvp.attendee.lbl"/></span>
				</s:else>
			</div>
		<div class="col-md-12 row">
			<div class="col-md-5 col-sm-5 col-xs-5">
				<s:select name="attribs" list="attribs" listKey="key" listValue="value"  cssClass="form-control"
				multiple="true" size="10" theme="simple" id="attribs"/>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-center" style="  margin: 65px -25px 0px -25px;">
				<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveRight();"></i><br>
				<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveLeft();"></i>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-5">
				<s:select name="selattribs" list="selattribs"  listKey="key" listValue="value" cssClass="form-control"
      			multiple="true" size="10"  theme="simple" id="selattribs" onkeyup="call(document.getElementById('selattribs'),'up2','down2');" onclick="call(document.getElementById('selattribs'),'up2','down2');"/>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-left" style=" margin: 65px 0px 0px -15px;">
				<div id="up2"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('selattribs'))"></i></div>
				<div id="down2"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('selattribs'))"></i></div>
			</div>
		</div>
		</div>
	</s:if>
</div>


<style>
.fa-arrow-up:hover, .fa-arrow-down:hover, .fa-arrow-right:hover, .fa-arrow-left:hover{
	color:#6D6D6D;
}
</style>
<%-- 
<div class="col-md-12">
 <s:if test='%{chkbuyerquestions=="Y"}'>

<table border="0">
	<tr><td class="confirmation section-header" >Transaction Level Questions</td></tr>
	<tr>
		<td>
			<s:select name="buyattribs" list="buyattribs" listKey="key" listValue="value"  cssClass="form-control"
			multiple="true" size="10" style="width:300px;" theme="simple" id="buyattribs"/>
		</td>
		<td align="center" valign="middle" style="padding: 15px;">
			<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveOption(document.confirmationpageform.buyattribs, document.confirmationpageform.buyselattribs);"></i><br>
			<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveOption(document.confirmationpageform.buyselattribs, document.confirmationpageform.buyattribs);"></i>
			
			 <!-- <input type="button" value="&gt;" onclick="moveOption(this.form.buyattribs, this.form.buyselattribs);" /> <br />
			<input type="button" value="&lt;" onclick="moveOption(this.form.buyselattribs, this.form.buyattribs);" /> -->
        </td>
		<td>
			<s:select name="buyselattribs" list="buyselattribs"  listKey="key" listValue="value"  cssClass="form-control"
     		 multiple="true" size="10" style="width:300px;" theme="simple" id="buyselattribs" onkeyup="call(document.getElementById('buyselattribs'),'up1','down1');" onclick="call(document.getElementById('buyselattribs'),'up1','down1');"/>
       </td>
		<td style="padding: 15px;">
			<div id="up1"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('buyselattribs'))"></i></div>
			<div id="down1"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('buyselattribs'))"></i></div>
		</td>
	</tr>
</table>

</s:if> 


<s:if test='%{chkattendeequestions=="Y"}'>
<table border="0" style="margin-top: 15px;">
<s:if test="%{powertype!='RSVP'}">
	<tr><td class="confirmation section-header" >Ticket Level Questions</td></tr>
</s:if><s:else>
    <tr><td class="confirmation section-header" >Attendee Level Questions</td></tr>
</s:else>	
	<tr>
		<td>
			<s:select name="attribs" list="attribs" listKey="key" listValue="value"  cssClass="form-control"
			multiple="true" size="10" style="width:300px;" theme="simple" id="attribs"/>
		</td>
		<td align="center" valign="middle" style="padding: 15px">
			<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveOption(document.confirmationpageform.attribs, document.confirmationpageform.selattribs);"></i><br>
			<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveOption(document.confirmationpageform.selattribs, document.confirmationpageform.attribs);"></i>
		
			<!-- <input type="button" value="&gt;" onclick="moveOption(this.form.attribs, this.form.selattribs);" /><br />
			<input type="button" value="&lt;" onclick="moveOption(this.form.selattribs, this.form.attribs);" /> -->
		</td>
		<td>
			<s:select name="selattribs" list="selattribs"  listKey="key" listValue="value" cssClass="form-control"
      		multiple="true" size="10" style="width:300px;" theme="simple" id="selattribs" onkeyup="call(document.getElementById('selattribs'),'up2','down2');" onclick="call(document.getElementById('selattribs'),'up2','down2');"/>
		</td>
		<td style="padding: 15px;">
			<div id="up2"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('selattribs'))"></i></div>
			<div id="down2"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('selattribs'))"></i></div>
		</td>
	</tr>
</table>
</s:if>
</div></div>
 --%>
<script>
function call(elem,upimg,downimg)
{
var sel = elem.selectedIndex;
var ind=elem.options[sel].index;
var len=elem.length;
if(ind==0)
document.getElementById('upimg').style.opacity='0.4';
else
document.getElementById('upimg').style.opacity='1.0';
if(ind==len-1)
document.getElementById('downimg').style.opacity='0.4';
else
document.getElementById('downimg').style.opacity='1.0';
}


function ManageQuestion_mvUp(elem) {
	selindex = elem.selectedIndex;
	if (selindex <= 0) {
		return;
	}
	  if (elem.options[selindex-1].value=='-1' || elem.options[selindex-1].value=='-2') {
     	//alert( props.sq_cannot_be_empty_msg1 + elem.options[selindex-1].text + props.sq_cannot_be_empty_msg2);
     	bootbox.alert(props.sq_cannot_be_empty_msg1+" "+ elem.options[selindex-1].text + props.sq_cannot_be_empty_msg2);
		
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


function ManageQuestion_mvDn(elem) {

	selindex = elem.selectedIndex;
	if (selindex == elem.length - 1) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	if(val=='-1' || val=='-2'){
	//alert(props.sq_cannot_be_empty_msg1 + temp + props.sq_cannot_be_empty_msg2);
	bootbox.alert(props.sq_cannot_be_empty_msg1+" " + temp + props.sq_cannot_be_empty_msg2);
	
	return;
	}
	
	elem.options[selindex].text = elem.options[selindex + 1].text;
	elem.options[selindex].value = elem.options[selindex + 1].value;
	
	elem.options[selindex + 1].text = temp;
	elem.options[selindex + 1].value = val;
	elem.selectedIndex = selindex + 1;
}


/*  function moveOption(theSelFrom, theSelTo)
{
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  
  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
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
  
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  if(NS4) history.go(0);
}  */

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}
function addOption(theSel, theText, theValue)
{
  var allquestions=new Array();
  var defaultquestions=new Array();
  var customquestions=new Array();
  var defaultvalues=new Array();
  var customvalues=new Array();
  var allvalues=new Array();
  var obj=new Object();
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
  for(var i=0;i<=selLength;i++){
   obj[theSel.options[i].value]=theSel.options[i].text;
    if(theSel.options[i].text=='Email' || theSel.options[i].text=='Phone'){
      defaultquestions.push(theSel.options[i].text);
      defaultvalues.push(theSel.options[i].value);
    }else{
      customquestions.push(theSel.options[i].text); 
      customvalues.push(theSel.options[i].value);
    } 
  }
   allquestions=defaultquestions.concat(customquestions);
   allvalues=defaultvalues.concat(customvalues);
   allvalues=allvalues.sort();
   for(var i=0;i<allquestions.length;i++){
     theSel.options[i].text=obj[allvalues[i]];
     theSel.options[i].value=allvalues[i];
  }
  
}
</script>
