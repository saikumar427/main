<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<link href="/main/bootstrap/css/grey.css" rel="stylesheet" type="text/css">
<script src="/main/bootstrap/js/icheck.min.js"></script>

<div id="customurlerrormessages" class="errorMessage" style="display:none"></div>

<form  name="disForm" id="disForm" >

<s:hidden name="eid"></s:hidden>
<s:hidden name="formatId" ></s:hidden>
<s:hidden name="dispFrmt" value="Display"></s:hidden>

		<div class="col-sm-12">
       	<div class="alert alert-danger" id="errormsg"        style="display:none; width:95%;  margin-left:4px">   </div>
       </div>


<div class="col-xs-12 col-sm-12">
		<div class="row">
			<div class="col-md-12" ><s:text name="dt.display.formats.sample"/> $soldOutQty&nbsp;&nbsp;$capacity&nbsp;&nbsp;$remainingQty&nbsp;&nbsp;$onHoldQty	</div> 
		</div>
		<div class="row">
			<div class="col-md-12">
				<textarea name="displayFormat" id="message"  rows="1" cols="60" class="form-control" > <s:property value="displayFormat" />  </textarea>
			</div>
		</div>
		
		<div class="row col-md-8">
			<div class="form-group" style="margin-bottom: 0px;">
					<label for="inputEmail3" class="col-sm-3 col-md-3 control-label"><s:text name="dt.applicable.tkts"/> </label>
					<div class="col-md-8 col-sm-9 extraspace">
						<div class="moveDown">
							<a href="javascript:;" id="selectall"><s:text name="global.select.all.lbl"/></a> &nbsp; <a href="javascript:;" id="selectnone"><s:text name="global.clear.all.lbl"/></a>
						</div>
						
						
						<%-- <s:set name='disctypee' value='discountData.discountType' />  --%>
						<%-- <s:property value="%{alltickets}" />
						<s:property value="%{formatTickets}" /> --%>
						
						<s:iterator value="%{alltickets}" var="ticket">
						<%-- <s:property value="%{key}"/> --%>
							<div>
								 <s:checkbox
										name="formatTickets" fieldValue="%{key}"
										cssClass="tktsapplicable"
										data-ticketid="%{seltickets.contains(key)}"
										value="%{seltickets.contains(key)}" ></s:checkbox>&nbsp;${value}<br />
							<div style="height: 6px;"></div>
							</div>
						</s:iterator>
					</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-offset-4">
				<div class="pull-right" style="margin-bottom:10px;">
					<button class="btn btn-primary" id="formatsubbtn" ><s:text name="global.submit.btn.lbl"/></button>&nbsp;
					<button class="btn btn-cancel" onclick="parent.closePopup();" style="margin-right:25px; "><s:text name="global.cancel.btn.lbl"/></button>
				</div>
			</div>
		</div>
		
		
</div>
</form>





		
<script>
var selectedtkts=${formatTickets};
/* alert(selectedtkts); */

  /* function checkboxes()
    {
     	
var inputElems = document.getElementsByTagName("input"),
 count = 0;

for (var i=0; i<inputElems.length; i++) {
	 alert(inputElems[i].type);
	alert(inputElems[i].checked); 
if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) 
  count++;
	}
alert(count);
if(count>0)
	  saveFormat(); 
    }  */


    




$(document).ready(function()
{
	var fid='${formatId}';
	if(fid=='') 
		$('#message').html('Sold Quantity: $soldOutQty,  Remaining Quantity: $remainingQty');
	
	
	$('#formatsubbtn').click(function(e)
	{     
		e.preventDefault();
		var status="true";
		 $('#errormsg').html('');
		 $('#errormsg').hide();
		
		var data=$('#message').val();
		if(data=='')
		{
			status="false";
			$('#errormsg').append(props.dt_format_required);
		}
		
		var length=document.querySelectorAll('input[type="checkbox"]:checked').length;
		if(length==0){
			status="false";
		$('#errormsg').append(props.dt_select_one_tkt);
		}
		
		if(status=='false'){
		$('#errormsg').show();
		parent.window.resizeIframe(); 
		return;
		}
			if(length>0)
			{
					$.ajax({
						   type: "POST",
						   url: 'DisplayTickets!saveformat',
						   data: $('#disForm').serialize(),
						   success: function( JsonData ) 
						   { 
							  if(!isValidActionMessage(JsonData)) return;
							  
							 /*  if(JsonData.indexOf('errorMessage')>-1){
								  $('#errormsg').append('JsonData');
								  $('#errormsg').show();
								  parent.window.resizeIframe();
							  return;
							  } */
							  
							  parent.rebuildtable(JsonData);
							  parent.closePopup();
						   }
						});
			}
			});
		
$('.tktsapplicable').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'
	}); 
	
	
$('#selectall').click(function(event) {  
    $('.tktsapplicable').iCheck('check');
});

 $('#selectnone').click(function(event) { 
 	 $('.tktsapplicable').iCheck('uncheck');    
});
 
 $.each(selectedtkts,function(index,value){
	 /* alert(value); */
		$('input[value="'+value+'"]').iCheck('check');
	});
 
 var isValidActionMessage = function(messageText) {
		if (messageText.indexOf('nologin') > -1
				|| messageText.indexOf('login!authenticate') > -1) {
			alert(props.global_no_login_msg);
			window.location.href = "/main/user/login";
			return false;
		} else if (messageText.indexOf('Something went wrong') > -1) {
			alert(props.global_not_have_perm_msg);
			closediv();
			return false;
		}
		return true;
	}
 
 /* var x=document.getElementsByTagName("textarea");
 $(x).on('focus', function() {
	 
     $(x).html('');
 }); */
 /* var x=document.getElementsByTagName("input");
 alert($(x).length); */
});
</script>