<%@taglib uri="/struts-tags" prefix="s" %>
 <form action="GroupDetails!save" name="ticketingForm" id="ticketingForm" method="post">
 <div class="well" id="addgrouptemplate">
			 <div class="row">
						<div id="groupformerrormessages"  class='alert alert-danger col-md-12' style='display:none'></div>
						
						<s:hidden name="eid" id="eid"></s:hidden>
						<s:hidden value="" id="grpId"></s:hidden>
						
						 <div class="col-md-6 col-sm-6 col-xs-12 bottom-gap">
						    <input type='text' id='grpname' name='group_name' class='form-control placeholder' placeholder='<s:text name="mt.enter.group.name.ph.lbl"/>'  ></input>
						   </div>
						   <div class="col-md-6 col-sm-6 col-xs-12  bottom-gap">
						     <s:textarea id='grpdesc' name="group_desc" cssClass='form-control' placeholder="%{getText('mt.enter.group.desc.ph.lbl')}" style="height:33px !important"> </s:textarea>
						    </div>
						                                   
						                                    
						  <div class="col-md-12 col-xs-12 col-sm-12 center">
						          <button type="button" class="btn btn-primary confirmgroupticket"><b><s:text name="global.add.btn.lbl"/></b></button>
						          <button type="button"  class="btn btn-cancel cancelgroupbox"><i class="fa fa-times" ></i></button>
						  </div>
			   </div>
</div>


</form>
<script>
$('#tktstart').datetimepicker({
    format:'m/d/Y g:i A',
    formatTime:'g:i A'
});

$('#tktend').datetimepicker({
    format:'m/d/Y g:i A',
    formatTime:'g:i A'
});

$('input.tktichk').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey' 
	
		});

$('input.scancode').iCheck({  
	checkboxClass: 'icheckbox_square-blue', 
	radioClass: 'iradio_square-blue'});
</script>