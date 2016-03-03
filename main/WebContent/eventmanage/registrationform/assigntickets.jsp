<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="addbasicpfquestion" action="ManageRegistrationForm!saveQSettings" method="post" id="addbasicpfquestion">
<div class="well" id="addbasicquestiontemplate" style="margin-top: 8px;">
<s:hidden name="eid"></s:hidden>
<s:hidden name="purpose"></s:hidden>
<s:hidden name="attributeid" id="selattribid"></s:hidden>
<s:hidden name="powertype"></s:hidden>
<s:hidden name="qlevel"></s:hidden>
<s:hidden name="selqs"></s:hidden>
<div id="optlist"></div>
<input type="hidden" name="ctype" id="ctype" value="add"/>
<input type="hidden" name="cpos" id="cpos" value="0"/>
<div id="assignticketsformerrormessages" class='alert alert-danger col-md-12' style='margin-bottom: 15px;display: none'></div>
<%-- <div class="row">
<div class="col-md-7 col-sm-5  extramarginques">
	<s:textfield name="" cssClass="form-control" value="First Name"
					size="52" id="basicpfquestion"></s:textfield>
</div>
</div> --%>
 <div id="basicpfquessettings"> 
 	<div class="row">
 		<div class="col-sm-12 col-md-12 form-inline">
						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12 bottom-gap sm-font" data-div="specific-tickets" style="margin-bottom: 0px;">
								<label class="basic-applicable-tickets">
									<input id="basic_chk_app_tkts" type="checkbox" name="" class="eachtkt"/>&nbsp;
								</label>
								<span id="basic_specific"><s:text name="global.applicable.tickets.lbl"/></span>
									<%-- <span class="glyphicon glyphicon-menu-right drop-image down"></span> --%>
							</div>
							</div>
							<s:set name="basicticketsCount" value="gentickets.size"/>
							<div class="row">
							<div id="basic_applicable_tkts" style="display:none;">	
								<s:if test="%{gentickets.size>0}">
									<%-- <div class="sm-font">
										<div class="col-md-12">
											<span><s:text name="global.required.lbl"/></span>
											<span>
												[<a href="javascript:;" id="requiredAll"><s:text name="global.all.lbl"/></a>&nbsp;
												<a href="javascript:;" id="requiredNone"><s:text name="global.none.lbl"/></a>]
											</span>
										</div>
									</div>
									<div style="clear:both;"></div> --%>
									<div class="background-options">
										<div class="col-md-2 sm-font"><s:text name="global.selected.tickets.lbl"/></div>
										<div class="col-md-10 xsm-font">
										<s:iterator value="%{gentickets}" var="ticket" status="stat">
											<div class="row">
												<div class="col-md-7 col-sm-6">
													<label class="checkbox-inline" style="padding-left: 0px;">
														<s:checkbox name="seltickets" cssClass="eachtkt"
															fieldValue="%{key}" value="%{seltickets.contains(key)}" />&nbsp;${value}
													</label>
												</div>
													
												<div class="col-md-5 col-sm-6  extramarginques">
													<div class="btn-group btn-multiple btn-group-xs " data-toggle="buttons">
														<label id="assign_req_block_<s:property value='key' />"
															onclick="assignRequired(<s:property value="key" />,'Required','selticketsOption[<s:property value="#stat.index" />]','Y_<s:property value="key" />')"
															class="requiredlbl assignReqOptional_<s:property value='key' /> btn <s:if test='%{reqtickets.contains(key)}'>btn-default active</s:if><s:else>btn-active</s:else>">
															<input class="assTktsReqRadio"
															name="selticketsOption[<s:property value="#stat.index" />]"
															type="radio" value="Y_<s:property value="key" />"
															<s:if test='%{reqtickets.contains(key)}'>checked=true</s:if>><s:text name="global.required.lbl"/>
														</label> 
														<label id="assign_opt_block_<s:property value="key" />"
															onclick="assignRequired(<s:property value="key" />,'Optional','selticketsOption[<s:property value="#stat.index" />]','N_<s:property value="key" />')"
															class="optionallbl assignReqOptional_<s:property value='key' /> btn <s:if test='%{!reqtickets.contains(key)}'>btn-default active</s:if><s:else>btn-active</s:else>">
															<input class="assTktsOptRadio"
															name="selticketsOption[<s:property value="#stat.index" />]"
															type="radio" value="N_<s:property value="key" />"
															<s:if test='%{!reqtickets.contains(key)}'>checked=true</s:if>><s:text name="global.optional.lbl"/>
														</label>
														</div>
												</div>
											</div>
										</s:iterator>
										</div>
										<div style="clear:both;"></div>
									</div>
								</s:if>
								<s:else>
                					<div class="background-options sub-text"><s:text name="qn.no.applicable.tickets.msg"/></div>
                				</s:else>
                				</div>
				</div>
				</div>
				
				<div class="col-md-12 col-sm-12 center">
	     			<span>
						<button class="btn btn-primary confirmaddbasicquestion" type="button" <s:if test="%{gentickets.size>0}"></s:if><s:else>disabled</s:else>><s:text name="global.save.btn.lbl"/></button>
						<button class="btn btn-cancel canceladdbasicquestion" type="button">
							<i class="fa fa-times"></i>
						</button>
					</span>
   				</div>
			</div>	
		</div>
 </div>
</s:form>

<script>
var basicticketsCount='${basicticketsCount}';
var basicapplicableTktCount=0;
var ifbasicchecked=true;
$('#basic_chk_app_tkts').on('ifChecked',function(){
	$("#basic_applicable_tkts").slideDown();
	if(ifbasicchecked)
		checkAllBasicTickets();
});

$('#basic_chk_app_tkts').on('ifUnchecked',function(){
	$("#basic_applicable_tkts").slideUp();
	checkAllBasicTickets();
});

function checkAllBasicTickets() {
	$('#addbasicpfquestion input[name=seltickets]').each(function() {
   	 	$(this).iCheck('check');
   });
}

$(document).ready(function() {

	$('input.eachtkt').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	
	$('input.alltkts').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
    	
	if($("#selattribid").val()==-5)
		$("#basicpfquestion").val("First Name");
	else if($("#selattribid").val()==-6)
		$("#basicpfquestion").val("Last Name");
	else if($("#selattribid").val()==-7)
		$("#basicpfquestion").val("Email");
	else $("#basicpfquestion").val("Phone");

	$("#basicpfquestion").attr("disabled",true);

	$('#requiredAll').click(function() {
		$('.assTktsReqRadio').prop("checked",true);
		$(".optionallbl").removeClass('active').removeClass('btn-default').addClass('btn-active');
		$(".requiredlbl").removeClass('btn-active').addClass('btn-default').addClass('active');
	});

	$('#requiredNone').click(function() {
		$('.assTktsOptRadio').prop("checked",true);
		$(".requiredlbl").removeClass('active').removeClass('btn-default').addClass('btn-active');
		$(".optionallbl").removeClass('btn-active').addClass('btn-default').addClass('active');
	});

	/* $('#selectalltkts').click(function() {
        $('#addbasicpfquestion input[name=seltickets]').each(function() {
        	 $(this).iCheck('check');
        });
    });

    $('#clearalltkts').click(function() {
        $('#addbasicpfquestion input[name=seltickets]').each(function() {
        	 $(this).iCheck('uncheck');
        });
    }); */
    
    /* $(".basic-applicable-tickets").click(function(){
		if($("#basic_applicable_tkts").is(":visible")){
			$(this).find("span.drop-image").removeClass("down").addClass("original");
        	$(this).find("#basic_specific").removeClass("highlighted-text");
        	$("#basic_applicable_tkts").slideUp();
		}else{
        	$(this).find("span.drop-image").removeClass("original").addClass("down");
        	$(this).find("#basic_specific").addClass("highlighted-text");
        	$("#basic_applicable_tkts").slideDown();
		}
    }); */
    
        $('#addbasicpfquestion input[name=seltickets]').each(function() {
			if($(this).is(":checked")){ 
				basicapplicableTktCount++;
			}
       	});
       if(basicticketsCount!=basicapplicableTktCount){
    	   ifbasicchecked=false;
    	   $("#basic_chk_app_tkts").iCheck('check');
       }
       
       //alert(basicapplicableTktCount+"--"+basicticketsCount);

});
</script>
