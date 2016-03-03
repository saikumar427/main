<%@taglib uri="/struts-tags" prefix="s"%>
<s:form name="addquestion" action="ManageRegistrationForm!saveQuestion" method="post" id="tktquestionform">
<div class="well" id="addtktquestiontemplate" style="margin-top: 5px;">
			<div id="tktquestionformerrormessages"
				class='alert alert-danger col-md-12' style='display: none'></div>

			<s:hidden name="eid"></s:hidden>
			<s:hidden name="purpose"></s:hidden>
			<s:hidden name="attributeid"></s:hidden>
			<s:hidden name="powertype"></s:hidden>
			<s:hidden name="qlevel"></s:hidden>
			<s:hidden name="selqs"></s:hidden>
			<s:hidden name="subquestionsinfo" id="tktsubquestionsinfoid"></s:hidden>
			<input type="hidden" name="ctype" id="ctype" value="add" /> <input
				type="hidden" name="cpos" id="cpos" value="0" />

	<div class="row">
	   <div class="col-md-7 col-sm-7 bottom-gap">
	      <s:textfield name="customattribute.name" cssClass="form-control"
						size="52" placeholder="%{getText('qn.name.placeholder.lbl')}" id="question"></s:textfield>
	   </div>
	   <div class="col-md-3 col-sm-3 form-group bottom-gap" style="padding-top:5px;">
	      <div class="btn-group btn-toggle btn-group-xs sm-font" data-toggle="buttons">
						<label id="lbl_tktqn_req"
							class="btn col-xs-6 add_tktqn_req_opt <s:if test="%{customattribute.isRequired=='Required'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="add_tktqn_req_opt" id="tktqn_inp_req"
							name="customattribute.isRequired" value="Required" type="radio"
							<s:if test="%{customattribute.isRequired=='Required'}">checked=true</s:if>>
							<s:text name="global.required.lbl"/>
						</label> <label id="lbl_tktqn_opt"
							class="btn col-xs-6 add_tktqn_req_opt <s:if test="%{customattribute.isRequired=='Optional'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="add_tktqn_req_opt" id="tktqn_inp_opt"
							name="customattribute.isRequired" value="Optional" type="radio"
							<s:if test="%{customattribute.isRequired=='Optional'}">checked=true</s:if>>
							<s:text name="global.optional.lbl"/>
						</label>
					</div>
	   </div> 
	</div>
		<div id="tktquessettings" style="display: block;">
			<div class="row">
				<div>
					<div class="" style="margin-bottom: 0px;">						
						<div class="col-sm-12 col-md-12 form-inline">
							<div class="moveDown">
							<div class="row">
							<div class="col-md-5 col-sm-12 bottom-gap">
								<div class="btn-group btn-group-sm sm-font">
									<label id="tkt_text_block"
										class="tktqtntype btn <s:if test="%{type=='text'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="tktradiotype" id="tkttext" name="type"
										value="text" type="radio"
										<s:if test="%{type=='text'}">checked=true</s:if>
										style="visibility: hidden !important; margin: 0 !important;">
										<span style="margin-left: -18px !important"><s:text name="qn.type.text.lbl"/></span>
									</label> <label id="tkt_textarea_block"
										class="tktqtntype btn <s:if test="%{type=='textarea'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="tktradiotype" id="tkttextarea" name="type"
										value="textarea" type="radio"
										<s:if test="%{type=='textarea'}">checked=true</s:if>
										style="visibility: hidden !important; margin: 0 !important;">
										<span style="margin-left: -18px !important"><s:text name="qn.type.multiline.text.lbl"/></span>
									</label> <label id="tkt_selection_block"
										class="tktqtntype btn <s:if test="%{type=='selection'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="tktradiotype" id="tktselection" name="type"
										value="selection" type="radio"
										<s:if test="%{type=='selection'}">checked=true</s:if>
										style="visibility: hidden !important; margin: 0 !important;">
										<span style="margin-left: -18px !important"><s:text name="qn.type.multiple.choice.lbl"/></span>
									</label>
								</div>
								</div>
									<div id="tkt_mult_choice_type" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="col-md-3 col-sm-3 bottom-gap" style="padding-top:3px;">
											<div class="btn-group btn-multiple btn-group-xs sm-font" data-toggle="buttons">
												<label id="tkt_single_block"
													class="tktoptiontype btn <s:if test="%{customattribute.qtype!='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
													<input class="tktformtype" id="single"
													name="customattribute.qtype" value="single" type="radio"
													<s:if test="%{customattribute.qtype!='select'}">checked=true</s:if>>
													<s:text name="qn.type.list.lbl"/>
												</label> 
												<label id="tkt_pulldown_block"
													class="tktoptiontype btn <s:if test="%{customattribute.qtype=='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
													<input class="tktformtype" id="tktpulldown"
													name="customattribute.qtype" value="pulldown" type="radio"
													<s:if test="%{customattribute.qtype=='select'}">checked=true</s:if>><s:text name="qn.type.pulldown.lbl"/>
												</label>
											</div>
										</div>
										<div id="tkt_selmul_ans_chkbox" class="col-md-4 col-sm-4 bottom-gap labelMiddle sm-font" style="<s:if test="%{customattribute.qtype!='select'}">display:block</s:if><s:else>display:none</s:else>">
											<label class="checkbox-inline" style="padding-left:0px;">
												<input type="checkbox" value="multiple" name="multiple" class="tktqtype"
												<s:if test="%{multiple=='multiple'}">checked="checked"</s:if> />&nbsp;<s:text name="qn.type.multiple.answers.lbl"/>
											</label>
										</div>
										</div>
									</div>
								
								<div id="tkt_smalltext" style="<s:if test="%{type=='text'}">display:block</s:if><s:else>display:none</s:else>">
									<div class="form-inline sm-font bottom-gap">
										 <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
										 <label for="textchars"><s:text name="qn.chars.lbl"/></label>
										 </div>
										  <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
										 <s:textfield name="customattribute.size" cssClass="form-control" size="3" />
										 </div>
	 								</div>
									
								</div>
								<div id="tkt_smalltextarea" style="<s:if test="%{type=='textarea'}">display:block</s:if><s:else>display:none</s:else>">
									<div class="form-inline sm-font bottom-gap">
										 <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
											<label for="textarealines"><s:text name="qn.lines.lbl"/></label>
										 </div>
										 <div class="form-group" style="padding-right:10px;">
											<s:textfield name="customattribute.rows" cssClass="form-control" size="3" />
										 </div>
										 <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
											<label for="textareachars"><s:text name="qn.chars.lbl"/></label>
										</div>
										  <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
											<s:textfield name="customattribute.columns" cssClass="form-control" size="3" />
										 </div>
	 								</div>
									
								</div>

								<div id="tkt_options" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
									<ul class="list-group sm-font" id="tktSortableOptions">
										<li class="tktmultisort bottom-gap">
											<div class="row">
												<div class="col-md-5 col-sm-5" style="margin-top: -18px;">
													<img class="tktmultisorthandle dragicon " src="/main/bootstrap/img/grippy_large.png">
														<input type="hidden" name="attribOptions" value="4"/>
														<input type="hidden" name="multipleOptionsList" value="100001" />
													<input style="width: 100% !important;margin-left: 18px;" type="text"
														name="multipleOptionsMap[100001]" class="form-control focus-tsq"
														placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
												</div>
												<div class="col-md-3 col-sm-3 multidata  labelRow links-div top-gap">
													<span class="dragiconDelete tktMulOptDelete"><a class="tktDeleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a></span>
													<%-- <span class="sub-questions" style="display:none">
													<a href="javascript:;" class="addtktcondsubquestions" data-optionid="4">Sub&nbsp;Questions</a></span> --%>
													
												</div>
											</div>

										</li>
										<li class="tktmultisort bottom-gap">
											<div class="row">
												<div class="col-md-5 col-sm-5"
													style="margin-top: -18px;">
													<img class="tktmultisorthandle dragicon " src="/main/bootstrap/img/grippy_large.png">
														<input type="hidden" name="attribOptions" value="5"/>
														<input type="hidden" name="multipleOptionsList" value="100002" />
													<input style="width: 100% !important;margin-left: 18px;" type="text"
														name="multipleOptionsMap[100002]" class="form-control focus-tsq"
														placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
												</div>
												<div class="col-md-3 col-sm-3 multidata  labelRow links-div top-gap">
													<span class="dragiconDelete tktMulOptDelete">
													<a class="tktDeleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a></span>
													<%-- <span class="sub-questions" style="display:none">
													<a href="javascript:;" class="addtktcondsubquestions" data-optionid="5">Sub&nbsp;Questions</a></span> --%>
													
												</div>
											</div>
										</li>
										<li class="tktmultisort bottom-gap">
											<div class="row">
												<div class="col-md-5 col-sm-5"
													style="margin-top: -18px;">
													<img class="tktmultisorthandle dragicon "
														src="/main/bootstrap/img/grippy_large.png">
														<input type="hidden" name="attribOptions" value="6"/>
														<input type="hidden" name="multipleOptionsList" value="100003" />
													<input style="width: 100% !important;margin-left: 18px;" type="text"
														name="multipleOptionsMap[100003]" class="form-control focus-tsq"
														placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
												</div>
												<div class="col-md-3 col-sm-3 multidata  labelRow links-div top-gap">
													<span class="dragiconDelete tktMulOptDelete">
													<a class="tktDeleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a></span>
													<%-- <span class="sub-questions" style="display:none">
													<a href="javascript:;" class="addtktcondsubquestions" data-optionid="6">Sub&nbsp;Questions</a></span> --%>
													
												</div>
											</div>
										</li>
									</ul>
									<!-- <div id="tktsubqnsdiv" style="display:none;" >
										<iframe id="tktsubqns_iframe" src="" width="100%" frameborder="0" scrolling="no"></iframe>
									</div> -->

									<div class="row sm-font bottom-top-gap">
										<div class="col-md-5 col-sm-5 col-xs-5">
											<span class="pull-left"><a class="tktaddrow"
												href="javascript:;"><s:text name="qn.addrow.lnk.lbl"/></a></span>
										</div>
									</div>
								</div>
								<s:if test="%{powertype=='Ticketing'}">
								<div class="row">
									<div class="col-md-12 col-xs-12 col-sm-12 sm-font" data-div="specific-tickets" style="margin-bottom: 0px;">
										<label class="applicable-tickets" style="cursor: pointer;">
											<input id="chk_app_tkts" type="checkbox" name="" class="tktqtype"/>&nbsp;
										</label>
										<span id="specific"><s:text name="global.applicable.tickets.lbl"/></span>
										<%-- <span class="glyphicon glyphicon-menu-right drop-image original"></span> --%>
									</div>
									</div>
									<s:set name="ticketsCount" value="gentickets.size"/>
									<div class="row">
									<div class="background-options" id="applicable_tkts" style="display:none;">
										<%-- <div class="row" id="applicableTkts">
										<s:if test="%{gentickets.size>0}">
											<div class="col-md-9 col-sm-7 links-div"
												style="margin-top: 10px;">
												<span><a href="javascript:;" id="all">Select&nbsp;All</a><a
													href="javascript:;" id="none">Clear&nbsp;All</a> </span>
											</div>
										</s:if>

									</div> --%> 
									<!-- <div class="row"> -->
										<s:if test="%{gentickets.size>0}">
												<div class="">
												<div class="col-md-2 sm-font"><s:text name="global.selected.tickets.lbl"/></div>
												<div class="col-md-6 xsm-font"> <!-- style="max-height: 150px; height: 150px; overflow-y: auto;" -->
													<s:iterator value="%{gentickets}" var="ticket" status="stat">
														<label class="checkbox-inline" style="padding-left: 0px;">
															<s:checkbox name="seltickets" cssClass="tktqtype"
																fieldValue="%{key}" value="%{seltickets.contains(key)}"
																 />&nbsp;${value}
														</label>
														<br />
													</s:iterator>
												</div>
												<div style="clear:both;"></div>
											</div>
										</s:if>
										<s:else>
											<div class="background-options sub-text"><s:text name="qn.no.applicable.tickets.msg"/></div>
										</s:else>
									<!-- </div> -->
									</div>
									</div>
								</s:if>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 center">
     			<span>
					<button class="btn btn-primary confirmaddtktquestion" type="button"><span id="tkt_btn_title"><s:text name="global.add.btn.lbl"/></span></button>
					<button class="btn btn-cancel canceladdtktquestion" type="button">
						<i class="fa fa-times"></i>
					</button>
				</span>
   			</div>
			</div>
		</div>
	</div>
</s:form>
<script>
	var ticketsCount='${ticketsCount}';
	var applicableTktCount=0;
	var tktAttribPurpose = '${purpose}';
	global_eb_ticket_purpose=tktAttribPurpose;
	var tktAttribType = '${type}';
	var tktAttribId='${attributeid}';
	var ticketsubquestionsinfo = {};
	var tktnewmultoptkey=100004;
	if(tktAttribPurpose=="edit") $("#tkt_btn_title").html(props.global_save_btn);
	if (tktAttribType == 'selection' && tktAttribPurpose == 'edit') {
		var tktAttribOptionlist = ${customOptions};
		var arr = tktAttribOptionlist.multilpledata;
		$('#tktSortableOptions').html('');
		for ( var i = 0; i < arr.length; i++) {
			global_eb_counter++;
			var tktAttribHtmldata = '<li class="tktmultisort bottom-gap"><div class="row"><div class="col-md-5 col-sm-5" style="margin-top:-18px;"><img src="/main/bootstrap/img/grippy_large.png" class="tktmultisorthandle dragicon">'
					+ '<input type="hidden" name="attribOptions" value="'+global_eb_counter+'"/><input type="hidden" name="multipleOptionsList" value="'+arr[i]["key"]+'"/><input style="width:100% !important;margin-left: 18px;" type="text" value="'+ arr[i]["value"]+ '" placeholder="'+props.qn_add_answer_placeholder_lbl+'" onkeyup="showSubQnsLabel(this);" class="form-control focus-tsq"'
					+ ' name="multipleOptionsMap['+arr[i]["key"]+']"></div><div class="col-md-3 col-sm-3 multidata  labelRow links-div top-gap"><span class="dragiconDelete tktMulOptDelete"><a href="javascript:;" class="tktDeleteOption">'
					+ ' '+props.global_delete_lnk_lbl+'</a></span>'
					//+ '<span class="sub-questions"><a href="javascript:;" class="addtktcondsubquestions" data-attriboptionid="'+arr[i]["key"]+'" data-optionid="'+global_eb_counter+'" >Sub&nbsp;Questions</a></span>'
					+ '</div></div></li>';
			$('#tktSortableOptions').append(tktAttribHtmldata);
		}
		var tktAttribclslen = $(".tktDeleteOption").length;
		if(tktAttribclslen==1) $(".tktMulOptDelete").hide();
	}
</script>

<script>
var tktPrevOptionId=0;
var ifchecked=true;

$('#chk_app_tkts').on('ifChecked',function(){
	$("#applicable_tkts").slideDown();
	if(ifchecked)
		checkAllTickets();
});

$('#chk_app_tkts').on('ifUnchecked',function(){
	$("#applicable_tkts").slideUp();
	checkAllTickets();
});
	$(document).ready(function() {
		
						$('input.add_tktqn_req_opt').change(function() {
									var selectedRadio = $(this).attr('id');
									$('input[name="customattribute.isRequired"][value="'+ selectedRadio + '"]').prop("checked", true);
									$(".add_tktqn_req_opt").each(function() {
										$(this).removeClass('active');
										$(this).removeClass('btn-default');
										$(this).removeClass('btn-active');
									});
									if (selectedRadio == 'tktqn_inp_req') {
										$('#lbl_tktqn_req').addClass('btn-default');
										$('#lbl_tktqn_opt').addClass('btn-active');
									} else {
										$('#lbl_tktqn_opt').addClass('btn-default');
										$('#lbl_tktqn_req').addClass('btn-active');
									}
								});

						$('input.tktradiotype').change(function() {
									var selectedRadio = $(this).attr('value');
									$('input[name="type"][value="'+ selectedRadio + '"]').prop("checked", true);
									$(".tktqtntype").each(function() {
										$(this).removeClass('active');
										$(this).removeClass('btn-default');
										$(this).removeClass('btn-active');
									});

									if (selectedRadio == 'text') {
										$('#tkt_text_block').addClass('btn-default').addClass('active');
										$('#tkt_textarea_block').addClass('btn-active');
										$('#tkt_selection_block').addClass('btn-active');
									} else if (selectedRadio == 'textarea') {
										$('#tkt_text_block').addClass('btn-active');
										$('#tkt_textarea_block').addClass('btn-default').addClass('active');
										$('#tkt_selection_block').addClass('btn-active');
									} else {
										$('#tkt_text_block').addClass('btn-active');
										$('#tkt_textarea_block').addClass('btn-active');
										$('#tkt_selection_block').addClass('btn-default').addClass('active');
									}

									var val = $('input[name="type"]:checked').val();
									if (val == 'text' || val == 'textarea') {
										if (val == 'text') {
											$('#tkt_smalltext').show();
											$('#tkt_smalltextarea').hide();
										} else {
											$('#tkt_smalltext').hide();
											$('#tkt_smalltextarea').show();
										}
										$('#tkt_mult_choice_type').hide();
										$('#tkt_options').slideUp(function() {
										});
									} else if (val == 'selection') {
										$('#tkt_smalltext').hide();
										$('#tkt_smalltextarea').hide();
										$('#tkt_mult_choice_type').show();
										$('#tkt_options').slideDown(function() {
										});
									}
								});

						$(document).on('click','.tktaddrow',function(e) {
							if($('#tktsubqnsdiv').is(":visible")){
		        				$('#tktsubqnsdiv').slideUp(function() {
								});
		        			}
							$(".tktMulOptDelete").show();
							if(e.handled !== true){
								global_eb_counter++;
								$('<li class="tktmultisort bottom-gap"><div class="row"><div class="col-md-5 col-sm-5" style="margin-top:-18px;"><img src="/main/bootstrap/img/grippy_large.png" class="tktmultisorthandle dragicon">'
												+ ' <input type="hidden" name="attribOptions" value="'+global_eb_counter+'"/><input type="hidden" name="multipleOptionsList" value="'+tktnewmultoptkey+'"/><input style="width:100% !important;margin-left: 18px;" type="text" placeholder="'+props.qn_add_answer_placeholder_lbl+'" onkeyup="showSubQnsLabel(this);" class="form-control focus-tsq"'
												+ ' name="multipleOptionsMap['+tktnewmultoptkey+']"></div><div class="col-md-3 col-sm-3 multidata labelRow links-div top-gap"><span class="dragiconDelete tktMulOptDelete"><a href="javascript:;" class="tktDeleteOption">'
												+ ' '+props.global_delete_lnk_lbl+'</a></span>'
												//+ ' <span class="sub-questions" style="display:none"><a href="javascript:;" class="addtktcondsubquestions" data-optionid="'+global_eb_counter+'" >Sub&nbsp;Questions</a></span>'
												+ ' </div></div></li>')
										.insertAfter($("#tktSortableOptions li").last());
								e.handled = true;
								tktnewmultoptkey++;
							}
						});

						$(document).on('click', '.tktDeleteOption', function(e) {
							if(e.handled !== true){
								var tktAttribclslen = $(".tktDeleteOption").length;
								if (tktAttribclslen > 1) $(this).parents('li').remove();
								if(tktAttribclslen==2) $(".tktMulOptDelete").hide();
								e.handled = true;
								var data_optionid=$(this).parents('li').find('.addtktcondsubquestions').data("optionid");
					           	if(data_optionid==tktPrevOptionId && $('#tktsubqnsdiv').is(":visible")){
					   				$('#tktsubqnsdiv').hide();
					   			}
							}
						});

						$("#tktSortableOptions").sortable({
							handle : '.tktmultisorthandle',
							onDrop : function(item, targetContainer, _super) {
								var clonedItem = $('<li/>').css({
									height : 0
								});
								item.before(clonedItem);
								clonedItem.animate({
									'height' : item.height()
								});

								item.animate(clonedItem.position(), function() {
									clonedItem.detach();
									_super(item);
									if($('#tktsubqnsdiv').is(":visible")){
				        				$('#tktsubqnsdiv').slideUp();
				        			}
								});
							},
							onDragStart : function($item, container, _super) {
								var offset = $item.offset();
								pointer = container.rootGroup.pointer;

								adjustment = {
									left : pointer.left - offset.left,
									top : pointer.top - offset.top
								};

								_super($item, container);
							},
							onDrag : function($item, position) {
								$item.css({
									left : position.left - adjustment.left,
									top : position.top - adjustment.top
								});
							}
						});

						$('input.tktformtype').change(function() {
									var selectedRadio = $(this).attr('id');
									$('input[name="customattribute.qtype"][value="'+ selectedRadio + '"]').prop("checked", true);
									$(".tktoptiontype").each(function() {
										$(this).removeClass('active');
										$(this).removeClass('btn-default');
										$(this).removeClass('btn-active');
									});
									if (selectedRadio == 'single') {
										$('#tkt_single_block').addClass('btn-default');
										$('#tkt_pulldown_block').addClass('btn-active');
										$('#tkt_selmul_ans_chkbox').show();
									} else {
										$('#tkt_pulldown_block').addClass('btn-default');
										$('#tkt_single_block').addClass('btn-active');
										$('#tkt_selmul_ans_chkbox').hide();
									}
								});

						$('input.tktqtype').iCheck({
							checkboxClass : 'icheckbox_square-grey',
							radioClass : 'iradio_square-grey'
						});

				       /*  $('#all').click(function() {
				            $('#tktquestionform input[name=seltickets]').each(function() {
				            	 $(this).iCheck('check');
				            });
				        });

				        $('#none').click(function() {
				            $('#tktquestionform input[name=seltickets]').each(function() {
				            	 $(this).iCheck('uncheck');
				            });
				        }); */
				        
				        $("#tktquestionform").on('click','ul#tktSortableOptions li .addtktcondsubquestions',function () {
				        	var tkt_data_optionid=$(this).data('optionid');
				        	if($.trim($(this).parents('li').find('.focus-tsq').val())==''){
				        		bootbox.alert("Enter option to add sub question", function() {
				    					//Example.show("Hello world callback");
				    			});
				        	}else{
				        		if(tkt_data_optionid==tktPrevOptionId && $('#tktsubqnsdiv').is(":visible")){
				        			$('#tktsubqnsdiv').slideUp("slow",function(){
				                		//resizeIframe();
				                 	});
				        		}else{
				        			if($('#tktsubqnsdiv').is(":visible")){
				        				$('#tktsubqnsdiv').hide();
				        			}
					        		global_eb_condQnLevel='TicketLevel';
					        		global_eb_savewarning=false;
					            	var url='';
					            	if(!ticketsubquestionsinfo[$(this).data('optionid')] && tktAttribPurpose=='edit')
					            		url = 'ManageRegistrationForm!customSubQuestions?eid='+eid+'&optionId='+$(this).data('optionid')+'&attribOptionId='+$(this).data('attriboptionid')+'&attributeid='+tktAttribId+'&getDBData=yes';
					            	else url = 'ManageRegistrationForm!customSubQuestions?eid='+eid+'&optionId='+$(this).data('optionid')+'&getDBData=no';
					            	 
					            	$('iframe#tktsubqns_iframe').attr("src", url);
					    	        $('#tktsubqnsdiv').insertAfter($(this).parents('li'));
					    	        //$('#tktsubqnsdiv').show();
					    	        $('#tktsubqnsdiv').slideDown("slow",function() {
					                	//parent.resizeIframe();
					            	});
					    	        tktPrevOptionId=tkt_data_optionid;
					            	/* $('.modal-title').html('Sub Question Settings');
					                    $('iframe#popup').attr("src", url);
					    	            $('#myModal').modal('show'); */
					    	            //$('html, body').animate({scrollTop:0}, 'slow');
					        	}
				        	}
				            
				        });
				        
				        console.log(tktAttribPurpose);
				        if(tktAttribPurpose == 'edit'){
					        $('#tktquestionform input[name=seltickets]').each(function() {
								if($(this).is(":checked")){ 
									applicableTktCount++;
								}
					       	});
					       if(ticketsCount!=applicableTktCount){
					    	   ifchecked=false;
					    	   $("#chk_app_tkts").iCheck('check');
					       }
				        }else{
				        	checkAllTickets();
				        }
				        
					});
	
	function checkAllTickets() {
		$('#tktquestionform input[name=seltickets]').each(function() {
       	 	$(this).iCheck('check');
       });
	} 
	

</script>

</html>