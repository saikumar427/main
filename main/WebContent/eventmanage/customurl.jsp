<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
<%-- <div id="customurlerrormessages" class="errorMessage" style="display:none"></div>
<div align="left">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="70">
<tr>
<td >
<div id="customurlblock" >http://<input
	Type="text" id="shorturl" name="shorturl" size="30"
			value="<s:text name="eventData.userurl"></s:text>" />.eventbee.com 
			<s:form	name="customURLform" id="customURLform" method="post">
			<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="eventData.mgrId" id="mgrid"></s:hidden>	
<s:hidden name="eventData.userurl" id="userurl"></s:hidden>				
		    </s:form></div>
		</td>
	</tr>	
	
</table>
</div>

<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
		var customurlBtn = new YAHOO.widget.Button("customurlbtn", { onclick: { fn: getCustomUrlBlock ,obj:{"eid":"${eid}"}} });
	</script> --%>
<div class="col-xs-12">
	<div class="row">
			 <div id="customurlerrormessages" class="alert alert-danger" style="display:none"></div>
			 <div id="customurlblock" >
			 <div class="col-xs-1" style="padding-top:7px" >
			 http://</div>
			 <div class="col-xs-8">
			 <input Type="text" id="shorturl" name="shorturl" size="30" class="form-control" value="<s:text name="eventData.userurl"></s:text>" />
			 </div>
			 <div class="col-xs-3" style="margin-left:-25px;padding-top:7px;">
			 .eventbee.com </div>
			<s:form	name="customURLform" id="customURLform" method="post">
			<s:hidden name="eid" id="eid"></s:hidden>
			<s:hidden name="eventData.mgrId" id="mgrid"></s:hidden>	
			<s:hidden name="eventData.userurl" id="userurl"></s:hidden>				
		    </s:form></div>
	</div>
	<br><br>
	<div class="row">
		<!-- <div class="col-xs-4"></div>
		<div class="col-xs-8"><input type="button" class="btn btn-primary" value="Submit"  onclick="setCustomURL();">&nbsp;
		<input type="button" class="btn btn-primary cancel" value="Cancel"></div> -->
		<div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="button" class="btn btn-primary" onclick="setCustomURL();">
                                <i class="fa fa-pencil-square-o"></i> Submit</button>
                            <button class="btn btn-danger cancel">
                                <i class="fa fa-times"></i> Cancel</button>
                        </div>
	</div><br>
</div>	

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/main/js/bootstrap.min.js"></script>
<script>
$('.cancel').click(function(){
	parent.$('#myModal').modal('hide');    
});

function setCustomURL(){	
	parent.loadingPopup();
	document.getElementById("customurlerrormessages").style.display="none";
    var _powertype='${powertype}';
	var userid=document.getElementById('mgrid').value;
	var userurl=document.getElementById('userurl').value;	
	var newurl=document.getElementById('shorturl').value;
	var eid=document.getElementById('eid').value;
	var url="Snapshot!setCustomURL?eid="+eid+"&mgrId="+userid+"&userurl="+userurl+"&newurl="+newurl+"&powertype="+_powertype;
	//parent.loadingPopup();
	$.ajax({
		  url : url,
		  type: 'get',
		  success: function(transport) {		  	
			var result=transport;
			parent.hidePopup();
			if(result.indexOf("URL Exists")>-1){
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='This URL is not available, please enter new URL';
			}
			else if(result.indexOf("Invalid")>-1){				
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='Enter Valid Text';
			}
			else if(result.indexOf("spacesInUrl")>-1){
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='Use alphanumeric characters only';
			}else{
				document.getElementById('customurlblock').style.display="none";							
				parent.window.location.reload(true);	
				//parent.$('#myModal').modal('hide'); 
		}
			parent.resizeIframe();
	      }
	});	
}
</script>