<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<style type="text/css">
</style>
</head>
<s:form name="subquestionform" cssClass="form-horizontal"
	action="ManageRegistrationForm!saveSubQuestion" method="post"
	id="subquestionform">
	<div class="well" id="addsubquestiontemplate">
		<div>
			<div id="subquestionformerrormessages"
				class='alert alert-danger col-md-12' style='display: none'></div>

			<s:hidden name="purpose"></s:hidden>
			<s:hidden name="subquestionid" id="subquestionid"></s:hidden>
			<s:hidden name="attributeid" id="attribid" value=""></s:hidden>

			<div class="form-group">
				<!-- <label for="name" class="col-xs-10 control-label">
                            <h4>Sub Question</h4>
                        </label> -->
				<div class="col-xs-7">
					<!-- <input type="text"  name="subCustomAttribute.name" id="question" autofocus /> -->
					<s:textfield id="question" cssClass="form-control"
						name="subCustomAttribute.name" placeholder="Sub Question"></s:textfield>
				</div>

				<div class="col-xs-5">



					<div class="btn-group" data-toggle="buttons">
						<label id="Required_block"
							class="btn requiredoptional <s:if test="%{subCustomAttribute.isRequired=='Required'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="requiredoptional" id="Required"
							name="subCustomAttribute.isRequired" value="Required"
							type="radio"
							<s:if test="%{subCustomAttribute.isRequired=='Required'}">checked=true</s:if>>
							Required
						</label> <label id="Optional_block"
							class="btn requiredoptional <s:if test="%{subCustomAttribute.isRequired=='Optional'}">btn-default active</s:if><s:else>btn-active</s:else>">
							<input class="requiredoptional" id="Optional"
							name="subCustomAttribute.isRequired" value="Optional"
							type="radio"
							<s:if test="%{subCustomAttribute.isRequired=='Optional'}">checked=true</s:if>>
							Optional
						</label>
					</div>
				</div>
			</div>
			<br/>
				<div class="row">	
			<div>
			<div class="" style="margin-bottom:0px;">
				<div class="col-sm-12 col-xs-12 form-inline">
				<div class="moveDown">
					<div class="row">
						<div class="col-md-5 col-xs-5">
						 <div class="btn-group">
							<label id="text_block"
								class="qtntype btn <s:if test="%{type=='text'}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype" id="text" name="type" value="text"
								type="radio" <s:if test="%{type=='text'}">checked=true</s:if>
								style="visibility: hidden !important; margin: 0 !important;">&nbsp;
								<span style="margin-left: -18px !important">Text&nbsp;</span>
							</label> <label id="textarea_block"
								class="qtntype btn <s:if test="%{type=='textarea'}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype" id="textarea" name="type"
								value="textarea" type="radio"
								<s:if test="%{type=='textarea'}">checked=true</s:if>
								style="visibility: hidden !important; margin: 0 !important;">&nbsp;
								<span style="margin-left: -18px !important">Multiline Text&nbsp;</span>
							</label> <label id="selection_block"
								class="qtntype btn <s:if test="%{type=='selection'}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype" id="selection" name="type"
								value="selection" type="radio"
								<s:if test="%{type=='selection'}">checked=true</s:if>
								style="visibility: hidden !important; margin: 0 !important;">&nbsp;
								<span style="margin-left: -18px !important">Multiple Choice&nbsp;</span>
							</label>
							</div>
						</div>
						<div id="mult_choice_type" class="col-xs-7" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
							<div class="col-xs-5">
								<div class="btn-group btn-multiple" data-toggle="buttons">
									<label id="single_block"
										class="optiontype btn <s:if test="%{subCustomAttribute.qtype!='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="formtype" id="single"
										name="subCustomAttribute.qtype" value="single" type="radio"
										<s:if test="%{subCustomAttribute.qtype!='select'}">checked=true</s:if>>
										List
									</label> <label id="pulldown_block"
										class="optiontype btn <s:if test="%{subCustomAttribute.qtype=='select'}">btn-default active</s:if><s:else>btn-active</s:else>">
										<input class="formtype" id="pulldown"
										name="subCustomAttribute.qtype" value="pulldown" type="radio"
										<s:if test="%{subCustomAttribute.qtype=='select'}">checked=true</s:if>>Pull Down
									</label>
								</div>

							</div>
							<s:property value="%{subCustomAttribute.qtype}"/>
							<div class="col-xs-7" style="<s:if test="%{subCustomAttribute.qtype!='select'}">display:block</s:if><s:else>display:none</s:else>">
								<label class="checkbox-inline">
									<input type="checkbox" value="multiple" name="multiple"
									class="qtype" <s:if test="%{multiple}">checked="checked"</s:if> />&nbsp;Select multiple answers
								</label>
							</div>
						</div>
					</div>
					<div id="smalltext" style="<s:if test="%{type=='text'}">display:block</s:if><s:else>display:none</s:else>">
						<div class="form-inline">
							<div class="extramarginform" style="display: inline-block;">
								<label for="textchars">Chars</label>
							 </div>
							<div class="extramarginform" style="display: inline-block;">
								<s:textfield name="subCustomAttribute.size" cssClass="form-control" size="3" />
							</div>
						</div>
					</div>
					<div id="smalltextarea" style="<s:if test="%{type=='textarea'}">display:block</s:if><s:else>display:none</s:else>">
					<div class="form-inline">
							 <div class="extramarginform" style="display: inline-block;">
								<label for="textarealines">Lines</label>
							 </div>
							  <div class="extramarginform" style="display: inline-block;">
								<s:textfield name="subCustomAttribute.rows" cssClass="form-control" size="3" />
							 </div>
							 <div class="extramarginform" style="display: inline-block;">
								<label for="textareachars">Chars</label>
							 </div>
							  <div class="extramarginform" style="display: inline-block;">
								<s:textfield name="subCustomAttribute.columns" cssClass="form-control" size="3" />
							 </div>
						</div>
					</div>
					<div id="options" style="<s:if test="%{type=='selection'}">display:block</s:if><s:else>display:none</s:else>">
						<ul class="list-group" id="sortableOptions">
							<li class="multisort">
								<div class="row">
									<div class="col-xs-5 extramargin" style="margin-top:-10px;">
										<img class="subopthandle dragicon" src="/main/bootstrap/img/grippy_large.png">
										<input style="margin-left: 10px;" type="text" name="multipleSubOptionsMap" class="form-control" placeholder="+ Add an answer" data-suboptionid="100001" />
									</div>
									<div class="col-xs-3 multidata extramargin" style="padding-top: 6px;">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;">Delete</a></span>
									</div>
								</div>
							</li>
							<li class="multisort">
								<div class="row">
									<div class="col-xs-5 extramargin" style="margin-top:-10px;">
									<img class="subopthandle dragicon" src="/main/bootstrap/img/grippy_large.png">
										<input style="margin-left: 10px;" type="text" name="multipleSubOptionsMap" class="form-control" placeholder="+ Add an answer" data-suboptionid="100002" />
									</div>
									<div class="col-xs-3 multidata extramargin" style="padding-top: 6px;">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;">Delete</a></span>
									</div>
								</div>
							</li>
							<li class="multisort">
								<div class="row">
									<div class="col-xs-5 extramargin" style="margin-top:-10px;">
									<img class="subopthandle dragicon" src="/main/bootstrap/img/grippy_large.png">
										<input style="margin-left: 10px;" type="text" name="multipleSubOptionsMap" class="form-control" placeholder="+ Add an answer" data-suboptionid="100003" />
									</div>
									<div class="col-xs-3 multidata extramargin" style="padding-top: 6px;">
										<span class="dragiconDelete"><a class="deleteOption" href="javascript:;">Delete</a></span>
									</div>
								</div>
							</li>
						</ul>
						<div class="row">
							<div class="col-xs-6" style="padding-left: 20px;">
								<span class="pull-left"><a href="javascript:;" class="addrow">Add Row</a></span>
							</div>
						</div>
						
					</div>
				</div>
				</div>
			</div>
			</div>
			<div class="" style="text-align: center">
				<span>
					<button type="submit" class="btn btn-primary confirmaddsubquestion"><span id="subqn_btn_title">Add Sub Question</span></button>
					<button type="button" class="btn btn-cancel canceladdsubquestion">
						<i class="fa fa-times"></i>
					</button>
				</span>
			</div>
			</div>
		</div>
	</div>
</s:form>
<script>
var purpose='${purpose}';
var type='${type}';
var newsuboptionkey=100004;

$(document).ready(function() {
		
	if(purpose=="edit") $("#subqn_btn_title").html("Save Sub Question");
    	
        $(document).on('click','.deleteOption',function(e){
        	if(e.handled !== true){
        	var clslen=$(".deleteOption").length;
        	if(clslen>1) $(this).parents('li').remove();
        	if(clslen==2) $(".dragiconDelete").hide();
        	e.handled = true;
        	}
        	parent.resizeIframe();
        });
        
        $("#subquestionform").on('click','.addrow',function(){
        	$(".dragiconDelete").show();
		var parentObj=$(this).parents('li');
		 $('<li class="multisort"><div class="row"><div class="col-xs-5 extramargin" style="margin-top: -10px;"><img class="subopthandle dragicon" src="/main/bootstrap/img/grippy_large.png">'+
		 '<input style="margin-left: 10px;" type="text" name="multipleSubOptionsMap" class="form-control" placeholder="+ Add an answer" data-suboptionid='+newsuboptionkey+'></div>'+
		 '<div class="col-xs-3 multidata extramargin" style="padding-top: 6px;"><span class="dragiconDelete"><a href="javascript:;" class="deleteOption">Delete</a></span></div></div></li>'
).insertAfter($( "#sortableOptions li" ).last());
        parent.resizeIframe();
        newsuboptionkey++;
            });


    	$('input.requiredoptional').change(function() {
    		var selectedRadio=$(this).attr('id');
    		$('input[name="subCustomAttribute.isRequired"][value="'+selectedRadio+'"]').prop("checked",true);
    		$(".requiredoptional").each(function() {
				$(this).removeClass('active'); 
				$(this).removeClass('btn-default'); 
				$(this).removeClass('btn-active'); 
			});
			if(selectedRadio=='Required'){
    			$('#Required_block').addClass('btn-default');
    			$('#Optional_block').addClass('btn-active');
   			 }else{
   				$('#Optional_block').addClass('btn-default');
    			$('#Required_block').addClass('btn-active');
    			
   			 }
    	});

    	$('input.formtype').change(function() {
    		var selectedRadio=$(this).attr('id');
            $('input[name="subCustomAttribute.qtype"][value="'+selectedRadio+'"]').prop("checked",true);
			$(".optiontype").each(function() {
				$(this).removeClass('active'); 
				$(this).removeClass('btn-default'); 
				$(this).removeClass('btn-active'); 
			});
			if(selectedRadio=='single'){
    			$('#single_block').addClass('btn-default');
    			$('#pulldown_block').addClass('btn-active');
    			$('.checkbox-inline').show();
   			 }else{
   				$('#pulldown_block').addClass('btn-default');
    			$('#single_block').addClass('btn-active');
    			$('.checkbox-inline').hide();
   			 }
    	});
        

    	$('input.qtype').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

    	$('input.anstype').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
		

        $('#cancel').click(function() {
            parent.closepopup();
        });

        $('#sortableOptions input').on('keyup', function() {
            //validator.element('input[name=type]');
        });

        /* $("#sortableOptions").sortable({
            //tolerance: 'pointer',
            revert: 'invalid',
            forceHelperSize: true,
            handle: '.handle',
            appendTo: 'body',
            containment: 'window',
            scroll: false,
            helper: 'clone'
        }); */
        
        $( "#sortableOptions" ).sortable({
            //connectWith: ".connectedSortable",
            handle: '.subopthandle',
            onDrop: function  (item, targetContainer, _super) {
                var clonedItem = $('<li/>').css({height: 0});
                item.before(clonedItem);
                clonedItem.animate({'height': item.height()});
                
                item.animate(clonedItem.position(), function  () {
                  clonedItem.detach();
                  _super(item);
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

        /* $('#subquestionform input').tooltipster({
            trigger: 'custom',
            onlyOne: false,
            fixedWidth: '300px',
            position: 'top-right',
            theme: 'form-validate-theme',
            animation: 'grow'
        }); */

        /* var validator = $('#subquestionform').validate({
            errorPlacement: function(error, element) {

                var lastError = $(element).data('lastError'),
                    newError = $(error).text();

                $(element).data('lastError', newError);

                if (newError !== '' && newError !== lastError) {
                    //$(element).tooltipster('content', newError);
                    //$(element).tooltipster('show');
                }
            },
            success: function(label, element) {
                //$(element).tooltipster('hide');
            },
            debug: true,
            rules: {
            	'subCustomAttribute.name': {
                    required: true
                },
                type: {
                    answersRequired: {
                        depends: function(element) {
                            return ($("input[name=type]:checked").val() == 'selection');
                        }
                    }
                }
            },
            messages: {
            	'subCustomAttribute.name': "Please enter your question"
            },
            submitHandler: function(form) {
				parent.global_eb_savewarning=true;
                var question = getQuestionData();
                    	
                    	if($('#subquestionid').val()==''){
                    		addSubQuestion(question);
                    		subquestionsarray.push(question);
                    	}else{
                    		
                            $('li[data-subquestionid='+$("#subquestionid").val()+'] span.qname').html(question.name);
                            subquestionsarray[$('#subquestionid').val()-1]=question;
                            $('#addsubquestiontemplate').fadeOut();
                            $('#subquestionbox').html('');
                    	} 
                    	
                    	isSubQnSaved=true;
                		done();
                		parent.resizeIframe();
                		
                return false;
            }
        });

        $.validator.addMethod("answersRequired", function(value, element) {
            var isValid = false;
            $('#sortableOptions input').each(function() {
                if (this.value.trim() != '')
                    isValid = true;
            });
            if (isValid)
                return true;
            return false;
        }, "Please add atleast answer for multiple choice question"); */

        $('#sortableOptions input');
        
        $('#subquestionbox').on('click','.confirmaddsubquestion',function(e){
        	$(".confirmaddsubquestion").attr("disabled",true);
        	if(e.handled !== true){
        		var errmsg="";
        		var qnNm=$('input[name="subCustomAttribute.name"]').val();
        		qnNm=myTrim(qnNm);
        		if(qnNm=='') errmsg="Sub Question is empty";
        		
        		if($("input[name=type]:checked").val() == 'selection'){
        			var isValid = false;
                    $('#sortableOptions input').each(function() {
                        if (myTrim(this.value) != '')
                            isValid = true;
                    });
                    if (!isValid){
                    	if(errmsg!='')
                    		errmsg+="<br/>Add at least one answer for Multiple Choice Sub Question";
                    	else
                    		errmsg="Add at least one answer for Multiple Choice Sub Question";
                    }
        		}
        		if(errmsg==""){
        			saveSubQuestion();
        		}else{
        			$("#subquestionformerrormessages").html(errmsg).show();
        			$(".confirmaddsubquestion").attr("disabled",false);
        			parent.resizeIframe();
        			return false;
        		}
        		e.handled = true;
        		
            }
        });
        
        function myTrim(x) {
            return x.replace(/^\s+|\s+$/gm,'');
        }
        
        function saveSubQuestion(){
        	parent.global_eb_savewarning=true;
            var question = getQuestionData();
                	
                	if($('#subquestionid').val()==''){
                		addSubQuestion(question);
                		subquestionsarray.push(question);
                	}else{
                		
                        $('li[data-subquestionid='+$("#subquestionid").val()+'] span.qname').html(question.name);
                        subquestionsarray[$('#subquestionid').val()-1]=question;
                        $('#addsubquestiontemplate').fadeOut();
                        $('#subquestionbox').html('');
                	} 
                	
            		/* $('#addsubquestion').show();
            		$('#done').show(); */
                	
                	isSubQnSaved=true;
            		done();
            		parent.resizeIframe();
        }

        $('input.anstype').on('ifChecked', function(event){
            //$("input[name=type]").tooltipster('show');

            var val = $("input[name=type]:checked").val();

            if (val == 'text' || val == 'textarea') {

                $('#options').slideUp();

                /* $("input[name=type]").tooltipster('hide');
                validator.element(this); */

            } else if (val == 'selection') {
                $('#options').slideDown(function() {
                    parent.resizeIframe();
                });

                $('#sortableOptions :first-child .input-group input').focus();

                /* $("input[name=type]").tooltipster('show');
                validator.element(this); */
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
    		$('#textarea_block').addClass('btn-default').addClass('active');;
    		$('#selection_block').addClass('btn-active');
   		}else{
    		$('#text_block').addClass('btn-active');
    		$('#textarea_block').addClass('btn-active');
    		$('#selection_block').addClass('btn-default').addClass('active');;
    	}

    	//$('input[name="type"]').tooltipster('show');
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
        		parent.resizeIframe();
         	});
        	/* $('input[name="type"]').tooltipster('hide');
        	validator.element(this); */
        
    	}else if(val=='selection') {
        	$('#smalltext').hide();
        	$('#smalltextarea').hide();
        	$("#mult_choice_type").show();
        	$('#options').slideDown(function() {
            	parent.resizeIframe();
        	});
        	$('#sortableOptions :first-child .input-group input').focus();
        	/* $('input[name="type"]').tooltipster('show');
        	validator.element(this); */
    	}
	});

        

        var getQuestionData = function() {
            var question = {};

            question.name = $('#question').val();
            question.attribid=$('#attribid').val();
            question.type = $("input[name=type]:checked").val();
            question.qtype=$('input[name="subCustomAttribute.qtype"]:checked').val();
            question.reqoptional=$('input[name="subCustomAttribute.isRequired"]:checked').val();
            question.size=$('input[name="subCustomAttribute.size"]').val();
            question.rows=$('input[name="subCustomAttribute.rows"]').val();
            question.columns=$('input[name="subCustomAttribute.columns"]').val();
            //question.multiple=$('input[name=multiple]:checked').val();
            question.multiple = $('input[name=multiple]').is(':checked');
            question.deletestatus=false;
            var answers = [];
            if (question.type == 'selection') {
            	
                $('#sortableOptions input').each(function() {
                    if (this.value.trim() != ''){
                    	var answersjson={};
                    	answersjson["key"]=$(this).data('suboptionid');
                    	answersjson["value"]=this.value.trim();
                        answers.push(answersjson);
                    }
                });
                question.answers = answers;
            }

            
            return question;
        };

    });
    
</script>
<html>