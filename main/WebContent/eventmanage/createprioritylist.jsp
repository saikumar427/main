<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.extraspace {
	margin-left: 15px;
}

.options {
	margin-left: 7px;
}

.drop-image {
	cursor: pointer;
	display: none;
}
</style>
<link rel="stylesheet" type="text/css" 	href="/main/bootstrap/css/bootstrap-tokenfield.min.css" />
<s:form name="prioritylisting" 	action="PriorityRegistration!savePriorityList" id="prioritylisting" cssClass="form-horizontal">
		<s:hidden id="listid" name="listId"></s:hidden>
		<s:hidden name="eid"></s:hidden>
		<s:hidden value="%{alltickets.size()}" id="ticketslength"/>
		<s:hidden value="%{selectedTickets.size()}" id="selectedlength" />

<div class="newprioritylist">
	<div class="row">
		<div class="col-md-12">
			<div class="well well-no-margin" style="margin:16px 0px !important">
				<div id="priorityerrors" style="display:none;"class="alert alert-danger"></div>
					<div class="row"></div>
						<div class="col-md-3 col-sm-3 extramargin" >
							<s:textfield name="listName" cssClass="form-control"  id="listname" placeholder="%{getText('pr.list.name.lbl')}"></s:textfield>
						</div>
						<div class="col-md-8 extramargin">
					 		<div class="col-md-4 col-sm-8 col-xs-12" style="padding-right:0px;">		
					    		<div class="col-md-5 col-sm-4 col-xs-12 control-label" style="padding-right:0px;">
									<s:text name="pr.add.fields.lbl" />&nbsp;
								</div>
								<div class="col-md-7 col-sm-4 col-xs-12" style="padding-left:0px;padding-right:0px;">
									<select name="fieldcount" id="fieldlevel" class="form-control" onchange="changeLabel();">
										<option value="1"><s:text name="pr.field.one.lbl"/></option>
										<option value="2"><s:text name="pr.field.two.lbl"/></option>
									</select>
								</div>
					  	</div>
					  	<div class="col-md-8 col-sm-8 col-xs-12">								
							<div class="col-sm-6 col-md-6 col-xs-12">
									<s:textfield id="firstlbl" name="firstLabel" size="10" onkeyup="changefirstLabel(),nospaces(this)" cssClass="form-control" placeholder="%{getText('pr.code.plc.lbl')}" ></s:textfield>
							</div>
							<div id="secondlabel" style="display:none;">
								<div class="col-sm-6 col-md-6 col-xs-12">
									<s:textfield cssClass="form-control" id="secondlbl" type="text" name="secondLabel"  onkeyup="changesecondLabel(),nospaces(this)" placeholder="%{getText('pr.pass.code.plc.lbl')}"></s:textfield>
								</div>
							</div>
						</div>
					</div>
					<div class="row options sm-font">
						<div class="col-md-12 col-xs-12 col-sm-12" 	data-div="specific-tickets">
							<label style="cursor: pointer;"> 
								<s:checkbox id="specific-check" name="" cssClass="dicount-options" data-val="specific-tickets" data-high="specific">
								</s:checkbox>
							</label>
							<span id="specific" class="checkbox-space">
								<s:text name="global.applicable.tickets.lbl" />
							</span> 
							<span class="glyphicon glyphicon-menu-right drop-image"></span>
						</div>
				 </div>
				 <div class="row options background-options" id="specific-tickets" style="display: none;">
					<s:if test="%{alltickets.size>0}">
						<div class="col-md-2 sm-font"><s:text name="global.selected.tickets.lbl" />
						</div>
						<div class="col-md-6 xsm-font">
							<s:iterator value="%{alltickets}" var="ticket" status="stat">
								<div>
									<s:checkbox name="seltickets" fieldValue="%{key}" cssClass="tktsapplicable" data-ticketid="%{seltickets.contains(key)}" value="%{seltickets.contains(key)}" /> 
									&nbsp;${value}<br />
									<div style="height: 6px;"></div>
								</div>
							</s:iterator>
						</div>
						<div style="clear: both"></div>
					</s:if>
					<s:else>
					<div class="background-options sub-text"><s:text name="pr.no.applicable.tickets.msg"/></div>
					</s:else>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary" id="savepriority" data-loading-text="Saving..."><s:text name="global.add.btn.lbl" /></button>
						<button type="button" class="btn btn-cancel" id="closepriority"><i class="fa fa-times"></i></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
<script>

	var applicableTktCount=0;
	var ifchecked=true;
	function changeLabel(type) {
		var val = document.getElementById('fieldlevel').value;
		if (val == '2') {
			$('#secondlabel').show();
			$('.secondfield').show();
		} else {
			$('#secondlabel').hide();
			$('.secondfield').hide();
			$('#secondlbl').val('');
		}
	}
	function nospaces(t){
		if(t.value.match(/\s/g)){
		t.value=t.value.replace(/\s/g,'');
	}
	}
		var nooffieldss = '${noOfFields}';
		if (nooffieldss == 1) {
			document.getElementById('fieldlevel').value = nooffieldss;
			changeLabel();

		} else if (nooffieldss == 2) {
			document.getElementById('fieldlevel').value = nooffieldss;
			changeLabel();
		}
		var divs=["specific-tickets"];
     	var bolddivs=["specific"];
		$('#specific-check').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'
    	});
		$('input.tktsapplicable').iCheck({
			checkboxClass : 'icheckbox_square-grey',
			radioClass : 'iradio_square-grey'
		});
		 $('#specific-check').on('ifChecked',function(){
              $(".drop-image").each(function() {
            	 $(this).addClass('original').removeClass('down');
            	});
              $(this).closest("[data-div]").find('.drop-image').addClass('down').removeClass('original');
              $('#'+$(this).data('val')).slideDown();
              var ids=$(this).data('high');
              if(ifchecked)
          		checkAllTickets();
		 });
		 $('#specific-check').on('ifUnchecked',function(){
			 $('#'+$(this).data('val')).slideUp();
			  $(this).closest("[data-div]").find('.drop-image').addClass('original').removeClass('down');
			 var ids=$(this).data('high');
			 checkAllTickets();
		 }); 
		 $(document).ready(function() {
			 if($("#listid").val() != ''){
			        $('#prioritylisting input[name=seltickets]').each(function() {
						if($(this).is(":checked")){ 
							applicableTktCount++;
						}
			       	});
			       if(Number($('#ticketslength').val())!=applicableTktCount){
			    	   ifchecked=false;
			    	   $("#specific-check").iCheck('check');
			       }
		        }else{
		        	checkAllTickets();
		        }
		 });
			function checkAllTickets() {
				$('#prioritylisting input[name=seltickets]').each(function() {
		       	 	$(this).iCheck('check');
		       });
			}
		 
	</script>
