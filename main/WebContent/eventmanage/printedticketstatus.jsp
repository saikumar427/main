<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
  <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />

<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>

</head>
<body>
<s:form name="printedticketstatus" action="PrintedTickets!saveTicketStatus" id="printedticketstatus">
<s:hidden name="eid" />
<s:hidden name="attendees"></s:hidden>
<s:hidden name="isrecur"></s:hidden>
<s:hidden name="selecteddate"></s:hidden>
<s:hidden name="listid" id="listid"></s:hidden>
<div id="printedticketerror">
</div>
		  <!-- <table>
		  <tr><td>Note</td><td><textarea cols="35" rows="2" id="notes" name="notes"></textarea></td></tr>
		  </table>
		  
		  <table>
		   <tr><td> <input type="checkbox" id="treattransaction" checked="checked" name="transaction"> Treating all as same transaction
		  </td></tr>
		  </table> -->
		<div class="col-md-12 col-sm-9">
		 <!--  <div class="col-sm-2">
		  Note
		  </div>
		  <div class="col-sm-4">
		<textarea cols="35" rows="2" id="notes" name="notes" class="form-control"></textarea>
		  </div> -->
		  
		  <div class="form-group">
					<div class="col-md-2 col-xs-2 control-label"><s:text name="pt.note.lbl"/></div>
					<div class="col-md-8 col-xs-8">
						<textarea cols="35" rows="2" id="notes" name="notes" class="form-control"></textarea>
					</div>
				</div>
		  
		  
		 
		<!--   <div class="row">
		  
		  <div class="col-sm-4">
		<input type="checkbox" id="treattransaction" checked="checked" name="transaction" class="form-control"> Treating all as same transaction
		  </div>
		  
		  
		  </div>
		  </div> -->
		  
		  
		   <!-- <div class="form-group">
					<div class="col-md-3 col-xs-4 control-label">
<input type="checkbox" id="treattransaction" checked="checked" name="transaction" class="form-control"> Treating all as same transaction
</div>
					
				</div> -->
				
				<div class="form-group">
					<div class="col-md-12 col-xs-12 control-label">
<div style="float: left; padding-right: 10px;"><input type="checkbox" id="treattransaction" checked="checked" name="transaction" class="form-control"></div> 
  <div style="float: left; margin-top: 10px;">
  <s:text name="pt.treating.all.same.transaction"/>
  </div>
  <div style="clear:both;">
</div></div>
					
				</div>
				
				
				
		  
		  </div>
		  
		  
		  <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="button" class="btn btn-primary" id="savenotes">
                                 <s:text name="global.submit.btn.lbl"/></button>
                            <button class="btn btn-active" onclick="parent.closepopup();">
                                 <s:text name="global.cancel.btn.lbl"/></button>
                        </div>
                    </div>
		  
		  
		</s:form>
<script>

$(document).ready(function(){

$('input#treattransaction').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey' 
		});

$('#savenotes').click(function(){
	$.ajax({
		url:'PrintedTickets!saveTicketStatus',
		data:$('#printedticketstatus').serialize(),
		success:function(result){
			/* result=JSON.parse(result);
			parent.jsondata=result; */
			parent.selectedAttendees=new Array();
			parent.editThisPrint($('#listid').val());
			parent.closepopup();
			}
		});
});


});


</script>



</body>




</html>