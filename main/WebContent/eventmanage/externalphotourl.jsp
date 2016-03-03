<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>

<s:form name="externalphoto" id="externalphoto" action="EventPageContent!insertExternalURL">
<s:hidden name="eid" id="eid"></s:hidden>
<div class="col-xs-12">

<div id="errors" class="alert alert-danger" style="display:none">
</div>
	<div class="row">
		<div class="col-xs-2">
			<s:text name="epc.photo.url.tb.lbl"></s:text>
		</div>
		<div class="col-xs-8">
           <s:textfield name="eventexternalphotoURL" size='55' id="eventexternalphotoURL" theme="simple" cssClass="form-control"></s:textfield>
		</div>
	</div>	
	<br>
	<div class="row">
		<div class="col-xs-2">
			<s:text name="epc.photo.width.tb.lbl"></s:text>
		</div>
		<div class="col-xs-8">
            <input type="text" name="photowidth" id="photowidth" size="4" maxlength="3" class="form-control"></input>
		</div>	
		<div class="col-xs-2"  style="padding-top: 10px; margin-left: -23px;">   <s:text name="epc.photo.pixels.txt.ibl"></s:text></div>
	</div>	
	<br><br>	
	<div class="row ">
		<div class="col-xs-offset-3 col-xs-8 ">
		<div class="pull-right" style="margin-bottom:10px;">
			<input type="button" class="btn btn-primary"  onclick="updatePhotoFunc();" value="<s:text name="global.submit.btn.lbl"></s:text>">
			&nbsp;<input type="button" class="btn btn-cancel" onclick="cancelFunc();" value="<s:text name="global.cancel.btn.lbl"></s:text>"></div>
		</div>
	</div>
</div>
</s:form>

<script>
function updatePhotoFunc(){
	document.getElementById('errors').innerHTML="";
	$.ajax( {
  	   url: "EventPageContent!insertExternalURL",
  	   type: 'get',
  	  data: $("#externalphoto").serialize(),
  	   success: function( t ) {	       	   
  		  var result=t;
			    if(result.indexOf("errorMessage")>-1){
			    document.getElementById('errors').style.display='block';
			    document.getElementById('errors').innerHTML=result;
			    parent.window.resizeIframe();
			    }
			    else{
			     parent.hidePopup();
			  	 parent.$('#eventphotourlimage').html('<img src="'+$("#eventexternalphotoURL").val()+'" id="imgupload" width="100px" height="75px">');
				parent.$('#delextphoto').removeClass('disabled');
				 $.ajax({url:'https://graph.facebook.com', type: 'post',data:{id:parent.serveraddress+'event?eid='+$('#eid').val(), scrape:true}});
	        	 parent.closePopup(); 
			    }
  	   }
  	   });
}

function cancelFunc(){	
	parent.closePopup();
}
</script>