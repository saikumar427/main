<%@taglib uri="/struts-tags" prefix="s"%>
<script src="../js/prototype.js" language="JavaScript"	type="text/javascript"></script>
<script src="../js/ajaxjson.js" language="JavaScript"	type="text/javascript"></script>
<script language="javascript" src="../js/enterkeypress.js" ></script>
<script>
function AjaxSubmit(action,eid){
if(action=='cancel'){
window.close();
return;
}

document.getElementById('form-register-event').action="ccvalidateaction.jsp";
$('form-register-event').request({
onSuccess : function(obj) {
	data=obj.responseText;
	var statusJson=eval('('+data+')');
	var status=statusJson.status;	
	if(status=='success')
	window.close();
	else{	
	var info ="<table class='error'>";
	for(var i=0;i<statusJson.errors.length;i++){
	info += "<tr><td ><font color='red'>"+statusJson.errors[i]+"</font></td></tr>";
	}
	info +="</table>";
	document.getElementById('paymenterror').innerHTML=info;	
	}
	     

},
onError : function(obj) {       
	alert("Please try again"); 
}
});
}

</script>

<div id="container">
<div id='paymenterror' class='error'></div>
<div id='center'>
<form name='form-register-event'  id="form-register-event" action="ccvalidateaction.jsp"  method="post"   >
<s:hidden name="eid"></s:hidden>
<s:hidden name="listingOptionsData.totalAmount"></s:hidden>
<s:hidden name="listingOptionsData.seqId"></s:hidden>
<table width='100%'>
<tr><td>
<table>
<tr>
<td>
<s:text name="listingOptionsData.creditCardScreenData"></s:text>
</td>
</tr> 
</table> 
</td></tr>
<tr><td>
<table align="center">
<tr><td align="center">
<input type="button" value="Submit" onClick="AjaxSubmit('submit',${eid});"/>
</td>
<td align="center">
<input type="button" value="Cancel" onClick="AjaxSubmit('cancel',${eid});"/>
</td></tr> 
</table> 
</td></tr>  
</table>
</form>
</div>
</div>