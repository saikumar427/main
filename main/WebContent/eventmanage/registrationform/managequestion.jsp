<%@taglib uri="/struts-tags" prefix="s"%>
<s:form name="addquestion" action="ManageRegistrationForm!saveQuestion" method="post" id="questionform">

	<div class="well" id="addquestiontemplate" style="margin-top: 5px;">
			<div id="transavewarning" class="alert alert-warning" style="display:none;margin-top: -14px;">Make sure to save this question for Sub Questions updates</div>
			<div id="questionformerrormessages" class='alert alert-danger col-md-12' style='display: none'></div>
			<s:hidden name="eid"></s:hidden>
			<s:hidden name="purpose"></s:hidden>
			<s:hidden name="attributeid"></s:hidden>
			<s:hidden name="powertype"></s:hidden>
			<s:hidden name="qlevel"></s:hidden>
			<s:hidden name="selqs"></s:hidden>
			<s:hidden name="subquestionsinfo" id="subquestionsinfoid"></s:hidden>
			<input type="hidden" name="ctype" id="ctype" value="add" /> <input
				type="hidden" name="cpos" id="cpos" value="0" />
<div class="row">
   <div class="col-md-7 col-sm-7 bottom-gap">
      <s:textfield name="customattribute.name" cssClass="form-control"
					size="52" placeholder="%{getText('qn.name.placeholder.lbl')}" id="question"></s:textfield>
   </div>
  	<div class="col-md-3 col-sm-3 form-group bottom-gap" style="padding-top:5px;">
      <div class="btn-group btn-group-xs btn-toggle sm-font" data-toggle="buttons">
					<label id="lbl_qn_req"
						class="btn col-xs-6 add_qn_req_opt <s:if test="%{customattribute.isRequired=='Required'}">btn-default active</s:if><s:else>btn-active</s:else>">
						<input class="add_qn_req_opt" id="qn_inp_req"
						name="customattribute.isRequired" value="Required" type="radio"
						<s:if test="%{customattribute.isRequired=='Required'}">checked=true</s:if>>
						<s:text name="global.required.lbl"/>
					</label> <label id="lbl_qn_opt"
						class="btn col-xs-6 add_qn_req_opt <s:if test="%{customattribute.isRequired=='Optional'}">btn-default active</s:if><s:else>btn-active</s:else>">
						<input class="add_qn_req_opt" id="qn_inp_opt"
						name="customattribute.isRequired" value="Optional" type="radio"
						<s:if test="%{customattribute.isRequired=='Optional'}">checked=true</s:if>>
						<s:text name="global.optional.lbl"/>
					</label>
				</div>
   </div>
</div>
		<div id="quessettings" style="display: block;">
				<div class="row">				
                <div>
					<div class="" style="margin-bottom:0px;">
					<div class="col-sm-12 col-md-12 form-inline">
					<div class="moveDown">
					<div class="row">
					<div class="col-md-5 col-sm-12 bottom-gap">
					<div class="btn-group btn-group-sm sm-font">
						<label id="text_block"
							class="qtntype btn <s:if test="%{type=='text'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="radiotype" id="text" name="type" value="text"
							type="radio" <s:if test="%{type=='text'}">checked=true</s:if>
							style="visibility: hidden !important; margin: 0 !important;">
							<span style="margin-left: -18px !important"><s:text name="qn.type.text.lbl"/></span>
						</label> <label id="textarea_block"
							class="qtntype btn  <s:if test="%{type=='textarea'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="radiotype" id="textarea" name="type"
							value="textarea" type="radio"
							<s:if test="%{type=='textarea'}">checked=true</s:if>
							style="visibility: hidden !important; margin: 0 !important;">
							<span style="margin-left: -18px !important"><s:text name="qn.type.multiline.text.lbl"/></span>
						</label> <label id="selection_block"
							class="qtntype btn <s:if test="%{type=='selection'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="radiotype" id="selection" name="type"
							value="selection" type="radio"
							<s:if test="%{type=='selection'}">checked=true</s:if>
							style="visibility: hidden !important; margin: 0 !important;">
							<span style="margin-left: -18px !important"><s:text name="qn.type.multiple.choice.lbl"/></span>
						</label>
					</div>
					</div>
					<div id="mult_choice_type" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
							<div class="col-md-3 col-sm-3 bottom-gap sm-font" style="padding-top:3px;">
								<div class="btn-group btn-multiple btn-group-xs" data-toggle="buttons">
									<label id="single_block"
										class="optiontype btn <s:if test="%{customattribute.qtype!='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="formtype" id="single"
										name="customattribute.qtype" value="single" type="radio"
										<s:if test="%{customattribute.qtype!='select'}">checked=true</s:if>>
										<s:text name="qn.type.list.lbl"/>
									</label> <label id="pulldown_block"
										class="optiontype btn <s:if test="%{customattribute.qtype=='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="formtype" id="pulldown"
										name="customattribute.qtype" value="pulldown" type="radio"
										<s:if test="%{customattribute.qtype=='select'}">checked=true</s:if>><s:text name="qn.type.pulldown.lbl"/>
									</label>
								</div>
							</div>
							<div id="selmul_ans_chkbox" class="col-md-4 col-sm-4 bottom-gap labelMiddle sm-font" style="<s:if test="%{customattribute.qtype!='select'}">display:block</s:if><s:else>display:none</s:else>">
								<label class="checkbox-inline" style="padding-left:0px;">
									<input type="checkbox" value="multiple" name="multiple" class="qtype"
									<s:if test="%{multiple=='multiple'}">checked="checked"</s:if> />&nbsp;<s:text name="qn.type.multiple.answers.lbl"/>
								</label>
							</div>
						</div>
					</div>
					<div id="smalltext" style="<s:if test="%{type=='text'}">display:block</s:if><s:else>display:none</s:else>">
						<div class="form-inline sm-font bottom-gap">
							 <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
								<label for="textchars"><s:text name="qn.chars.lbl"/></label>
							 </div>
							  <div class="form-group" style="margin-bottom:0px;padding-right:10px;">
								<s:textfield name="customattribute.size" cssClass="form-control" size="3" />
							 </div>
						</div>
					</div>
					<div id="smalltextarea" style="<s:if test="%{type=='textarea'}">display:block</s:if><s:else>display:none</s:else>">
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
					<div id="options" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
						<ul class="list-group sm-font" id="sortableOptions">
							<li class="multisort bottom-gap">
								<div class="row">
									<div class="col-md-5 col-sm-5" style="margin-top:-18px;">
										<img class="multisorthandle dragicon " src="/main/bootstrap/img/grippy_large.png">
										<input type="hidden" name="attribOptions" value="1" />
										<input type="hidden" name="multipleOptionsList" value="100001" />
										<input style="width:100% !important;margin-left: 18px;" type="text" name="multipleOptionsMap[100001]" class="form-control focus-sq"
											placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
									</div>
									<div class="col-md-3 col-sm-3 multidata  labelRow top-gap links-div">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a>
										</span>
										<span class="sub-questions">
										<a href="javascript:;" class="addcondsubquestions" data-optionid="1">Sub&nbsp;Questions</a>
										</span>

									</div>
								</div>
							</li>
							<li class="multisort bottom-gap">
								<div class="row">
									<div class="col-md-5 col-sm-5" style="margin-top:-18px;">
										<img class="multisorthandle dragicon " src="/main/bootstrap/img/grippy_large.png">
										<input type="hidden" name="attribOptions" value="2" />
										<input type="hidden" name="multipleOptionsList" value="100002" />
										<input style="width:100% !important;margin-left: 18px;" type="text" name="multipleOptionsMap[100002]" class="form-control focus-sq" 
										placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
									</div>
									<div class="col-md-3 col-sm-3 multidata  labelRow top-gap links-div">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a>
										</span>
										<span class="sub-questions">
										<a href="javascript:;" class="addcondsubquestions" data-optionid="2">Sub&nbsp;Questions</a>
										</span>

									</div>
								</div>
							</li>
							<li class="multisort bottom-gap">
								<div class="row">
									<div class="col-md-5 col-sm-5" style="margin-top:-18px;">
										<img class="multisorthandle dragicon" src="/main/bootstrap/img/grippy_large.png">
										<input type="hidden" name="attribOptions" value="3" />
										<input type="hidden" name="multipleOptionsList" value="100003" />
										<input style="width:100% !important;margin-left: 18px;" type="text" name="multipleOptionsMap[100003]" class="form-control focus-sq"
											placeholder="<s:text name='qn.add.answer.placeholder.lbl'/>" onkeyup="showSubQnsLabel(this);"/>
									</div>
									<div class="col-md-3 col-sm-3 multidata labelRow top-gap links-div">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;"><s:text name="global.delete.lnk.lbl"/></a>
										</span>
										<span class="sub-questions">
										<a href="javascript:;" class="addcondsubquestions" data-optionid="3">Sub&nbsp;Questions</a>
										</span>

									</div>
								</div>
							</li>
						</ul>
						<div class="row sm-font bottom-top-gap">
							<div class="col-md-5 col-sm-5 col-xs-5">
  								<span class="pull-left"><a class="addrow" href="javascript:;"><s:text name="qn.addrow.lnk.lbl"/></a></span>
							</div>
						</div>
						
						<div id="subqnsdiv" style="display:none;">
							<iframe id="subqns_iframe" src="" width="100%" frameborder="0" scrolling="no"></iframe>
						</div>
					</div>
				</div>
				</div>
				</div>
				</div>
				<div class="col-md-12 col-sm-12 center">
	     			<span>
						<button class="btn btn-primary confirmaddquestion" type="button"><span id="btn_title"><s:text name="global.add.btn.lbl"/></span></button>
						<button class="btn btn-cancel canceladdquestion" type="button">
							<i class="fa fa-times"></i>
						</button>
					</span>
   				</div>

				</div>
			</div>
	</div>
</s:form>
<script>
	var buyAttribPurpose='${purpose}';
	global_eb_buyer_purpose=buyAttribPurpose;
	var buyAttribType='${type}';
	var buyAttribId='${attributeid}';
	var attributesetid='${attributesetid}';
	var buyersubquestionsinfo = {};
	var buynewmultoptkey=100004;
	if(buyAttribPurpose=="edit") $("#btn_title").html(props.global_save_btn);
	if(buyAttribType=='selection' && buyAttribPurpose=='edit'){
		var buyAttribOptionlist=${customOptions};
		var arr=buyAttribOptionlist.multilpledata;
		$('#sortableOptions').html('');
		for(var i=0;i<arr.length;i++){
			global_eb_counter++;
			var htmldata='<li class="multisort bottom-gap"><div class="row"><div class="col-md-5 col-sm-5" style="margin-top:-18px;"><img src="/main/bootstrap/img/grippy_large.png" class="multisorthandle dragicon">'+
			' <input type="hidden" name="attribOptions" value="'+global_eb_counter+'"/><input type="hidden" name="multipleOptionsList" value="'+arr[i]["key"]+'"/>'+
			'<input style="width:100% !important;margin-left: 18px;" type="text" value="'+arr[i]["value"]+'" placeholder="'+props.qn_add_answer_placeholder_lbl+'" onkeyup="showSubQnsLabel(this);" class="form-control focus-sq"'+
	          ' name="multipleOptionsMap['+arr[i]["key"]+']"></div><div class="col-md-3 col-sm-3 multidata  labelRow top-gap links-div"><span class="dragiconDelete"><a href="javascript:;" class="deleteOption">'+
			''+props.global_delete_lnk_lbl+'</a></span>'+
			'<span class="sub-questions"><a href="javascript:;" class="addcondsubquestions" data-attriboptionid="'+arr[i]["key"]+'" data-optionid="'+global_eb_counter+'" >Sub&nbsp;Questions</a></span>'+
			'</div></div></li>';
			$('#sortableOptions').append(htmldata);
		}
		var clslen=$(".deleteOption").length;
    	if(clslen==1) $(".dragiconDelete").hide();
	}
</script>
<script>
var prevOptionId=0;
$(document).ready(function (){
	$('input.add_qn_req_opt').change(function() {
		var selectedRadio=$(this).attr('id');
		$('input[name="customattribute.isRequired"][value="'+selectedRadio+'"]').prop("checked",true);
		$(".add_qn_req_opt").each(function() {
			$(this).removeClass('active'); 
			$(this).removeClass('btn-default'); 
			$(this).removeClass('btn-active'); 
		});
		if(selectedRadio=='qn_inp_req'){
			$('#lbl_qn_req').addClass('btn-default');
			$('#lbl_qn_opt').addClass('btn-active');
			 }else{
				$('#lbl_qn_opt').addClass('btn-default');
			$('#lbl_qn_req').addClass('btn-active');
			 }
	});

    $('input.anstype').on('ifChecked', function(event){
        $("input[name=type]").tooltipster('show');
        var val = $("input[name=type]:checked").val();
        if (val == 'text' || val == 'textarea') {
            $('#options').slideUp();
        } else if (val == 'selection') {
            $('#options').slideDown(function() {
            });
        }
    });

    $('input.radiotype').change(function() {
		var selectedRadio=$(this).attr('id');
             $('input[name="type"][value="'+selectedRadio+'"]').prop("checked",true);
			$(".qtntype").each(function() {
				$(this).removeClass('active'); 
				$(this).removeClass('btn-default'); 
				$(this).removeClass('btn-active'); 
			});

		if(selectedRadio=='text'){
			$('#text_block').addClass('btn-default').addClass('active');
			$('#textarea_block').addClass('btn-active');
			$('#selection_block').addClass('btn-active');
		}else if(selectedRadio=='textarea'){
			$('#text_block').addClass('btn-active');
			$('#textarea_block').addClass('btn-default').addClass('active');
			$('#selection_block').addClass('btn-active');
		}else{
			$('#text_block').addClass('btn-active');
			$('#textarea_block').addClass('btn-active');
			$('#selection_block').addClass('btn-default').addClass('active');
		}

	var val = $('input[name="type"]:checked').val();
	if (val=='text' || val=='textarea') {
		if(val=='text'){
	 	  $('#smalltext').show();
		  $('#smalltextarea').hide();
			}else{
	 		$('#smalltext').hide();
	  		$('#smalltextarea').show();
			}
		$("#mult_choice_type").hide();
	    $('#options').slideUp(function(){
	     });
	} else if(val=='selection') {
	    $('#smalltext').hide();
	    $('#smalltextarea').hide();
	    $("#mult_choice_type").show();
	    $('#options').slideDown(function() {
	    });
	}
	});

	$(document).on('click','.addrow',function(e){
		if($('#subqnsdiv').is(":visible")){
			$('#subqnsdiv').slideUp(function() {
			});
		}
		$(".dragiconDelete").show();
		//var parentObj=$(this).parents('li');
		if(e.handled !== true){
			global_eb_counter++;
	 	$('<li class="multisort bottom-gap"><div class="row"><div class="col-md-5 col-sm-5" style="margin-top:-18px;"><img src="/main/bootstrap/img/grippy_large.png" class="multisorthandle dragicon">'+
	 		 '<input type="hidden" name="attribOptions" value="'+global_eb_counter+'"/><input type="hidden" name="multipleOptionsList" value="'+buynewmultoptkey+'"/>'+
	 		 '<input style="width:100% !important;margin-left: 18px;" type="text" placeholder="'+props.qn_add_answer_placeholder_lbl+'" onkeyup="showSubQnsLabel(this);" class="form-control focus-sq"'+
             ' name="multipleOptionsMap['+buynewmultoptkey+']"></div><div class="col-md-3 col-sm-3 multidata labelRow top-gap links-div"><span class="dragiconDelete"><a href="javascript:;" class="deleteOption">'+
			''+props.global_delete_lnk_lbl+'</a></span>'+
			'<span class="sub-questions"><a href="javascript:;" class="addcondsubquestions" data-optionid="'+global_eb_counter+'">Sub&nbsp;Questions</a></span>'+
			'</div></div></li>').insertAfter($( "#sortableOptions li" ).last());
	 	e.handled = true;
	 	buynewmultoptkey++;
		}
    });

	$(document).on('click','.deleteOption',function(e){
		if(e.handled !== true){
            var clslen=$(".deleteOption").length;
        	if(clslen>1) $(this).parents('li').remove();
        	if(clslen==2) $(".dragiconDelete").hide();
            e.handled = true;
            var data_optionid=$(this).parents('li').find('.addcondsubquestions').data("optionid");
           	if(data_optionid==prevOptionId && $('#subqnsdiv').is(":visible")){
   				$('#subqnsdiv').hide();
   			}
        }
	});

    $( "#sortableOptions" ).sortable({
        //connectWith: ".connectedSortable",
        handle: '.multisorthandle',
        onDrop: function  (item, targetContainer, _super) {
            var clonedItem = $('<li/>').css({height: 0});
            item.before(clonedItem);
            clonedItem.animate({'height': item.height()});
            
            item.animate(clonedItem.position(), function  () {
              clonedItem.detach();
              _super(item);
              //var data_optionid=item.find('.addcondsubquestions').data("optionid");
              if($('#subqnsdiv').is(":visible")){
     				$('#subqnsdiv').slideUp();
     			}
            });
        },
        onDragStart: function ($item, container, _super) {
            var offset = $item.offset();
            pointer = container.rootGroup.pointer;

            adjustment = {
              left: pointer.left - offset.left,
              top: pointer.top - offset.top
            };

            _super($item, container);
          },
          onDrag: function ($item, position) {
              $item.css({
                left: position.left - adjustment.left,
                top: position.top - adjustment.top
              });
            }
      });

	$('input.formtype').change(function() {
		var selectedRadio=$(this).attr('id');
        $('input[name="customattribute.qtype"][value="'+selectedRadio+'"]').prop("checked",true);
		$(".optiontype").each(function() {
			$(this).removeClass('active'); 
			$(this).removeClass('btn-default'); 
			$(this).removeClass('btn-active'); 
		});
		if(selectedRadio=='single'){
			$('#single_block').addClass('btn-default');
			$('#pulldown_block').addClass('btn-active');
			$('#selmul_ans_chkbox').show();
			 }else{
				$('#pulldown_block').addClass('btn-default');
			$('#single_block').addClass('btn-active');
			$('#selmul_ans_chkbox').hide();
			 }
	});   

    $('input.qtype').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
    
    $('#questionform').on('click','ul#sortableOptions li .addcondsubquestions',function () {
    	var optionVal=$.trim($(this).parents('li').find('.focus-sq').val());
    	var data_optionid=$(this).data('optionid');
    	if(optionVal==''){
    		bootbox.alert("Enter option to add sub question", function() {
					//Example.show("Hello world callback");
			});
    	}else{
    		if(data_optionid==prevOptionId && $('#subqnsdiv').is(":visible")){
    			$('#subqnsdiv').slideUp("slow",function(){
            		//resizeIframe();
             	});
    		}else{
    			if($('#subqnsdiv').is(":visible")){
    				$('#subqnsdiv').hide();
    			}
		    	global_eb_condQnLevel='TransactionLevel';
		    	global_eb_savewarning=false;
		    	var subqnurl='';
		    	if(!buyersubquestionsinfo[$(this).data('optionid')] && buyAttribPurpose=='edit')
		    		subqnurl = 'ManageRegistrationForm!customSubQuestions?eid='+eid+'&optionId='+$(this).data('optionid')+'&attribOptionId='+$(this).data('attriboptionid')+'&attributeid='+buyAttribId+'&getDBData=yes&optionVal='+optionVal;
		    	else subqnurl = 'ManageRegistrationForm!customSubQuestions?eid='+eid+'&optionId='+$(this).data('optionid')+'&getDBData=no&optionVal='+optionVal;
		    	/* $('.modal-title').html('Sub Question Settings');
		        $('iframe#popup').attr("src", subqnurl);
		        $('#myModal').modal('show'); */
		        
		        $('iframe#subqns_iframe').attr("src", subqnurl);
		        $('#subqnsdiv').insertAfter($(this).parents('li'));
		        //$('#subqnsdiv').show();
		        $('#subqnsdiv').slideDown("slow",function() {
                	//parent.resizeIframe();
            	});
		        prevOptionId=data_optionid;
    		}
    	}
        
    });
    
})
</script>
