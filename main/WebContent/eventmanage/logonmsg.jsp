<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>


<div id="customurlerrormessages" class="alert alert-danger" style="display:none;width:95%;margin-left:4px;" ></div>

<div class="col-xs-12 col-sm-9">
		<div class="row">
			<div class="col-md-8">
			<s:text name="epc.logo.tb.lbl"></s:text>
			</div>
		</div>
		<div class="row">
		<div class="col-md-8">
			<input type="text" id="image" value="<s:property value="imageurl" />"  class="form-control"/>
			<div>
		</div>
		<br/>
		<div class="row">
		<div class="col-md-8">
			<s:text name="epc.message.tb.lbl"></s:text>
			</div>
		</div>
		<div class="row">
		<div class="col-md-8">
			<textarea name="message" id="message"  rows="2" cols="81" class="form-control" ><s:property value="message" /></textarea></div>
		</div>	
		<br><br>
		<div class="row">
			<div class="col-xs-offset-4">
				<div class="pull-right" style="margin-bottom:10px;">
					<button class="btn btn-primary submit"><s:text name="global.submit.btn.lbl"></s:text></button>&nbsp;
					<button class="btn btn-cancel" onclick="parent.closePopup();" style="margin-right:25px; "><s:text name="global.cancel.btn.lbl"></s:text></button>
				</div>
			</div>
		</div>
</div>
</div></div>
<script>
 $(".submit").on('click', function(e) {
	 parent.loadingPopup();
	 	var image=document.getElementById('image').value;
		var message=document.getElementById('message').value;	
		
		if(image.trim()=='' || image.trim()==null){
			 parent.hidePopup();
			 $('#customurlerrormessages').show();
			$('#customurlerrormessages').html("Logo URL is empty.");
			parent.window.resizeIframe();
			return;
		}
		var eid = ${eid};
		
		
		var url="EventPageContent!insertlogoandmessage?eid="+eid;
			$.ajax({
	        url: url,
	        type: 'POST',
	        data: { message:message,image:image} ,       
	        success: function (response) {  
	        	  parent.hidePopup();
	        	  
	        	  parent.$('#logourlimage').show();
	        	  parent.$('#logourlimage').html('<img id="imgupload" src="'+$('#image').val()+'" width="100px" height="75px">');
	        	  parent.$('#dellogoimgbtn').removeClass("disabled");
	        	  parent.$('#logomsg').html(message);
				 $.ajax({url:'https://graph.facebook.com', type: 'post',data:{id:parent.serveraddress+'event?eid='+$('#eid').val(), scrape:true}});
	        	 parent.closePopup(); 
	           /*  parent.window.location.reload(true);  */           
	        }       
	    });
});



</script>