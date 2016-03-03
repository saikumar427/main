var offsetTop=196;
var tktOffsetTop=196;
var n=0;
$(document).ready(function () {
	
	startHoverEfects();
	
    $('input.qninfo_radiotype').change(function() {
		var selectedRadio=$(this).attr('id');
            $('input[name="questionsinfo"][value="'+selectedRadio+'"]').prop("checked",true);
			$(".qninfo").each(function() {
				$(this).removeClass('active'); 
				$(this).removeClass('btn-default'); 
				$(this).removeClass('btn-active'); 
			});

		if(selectedRadio=='transaction'){
			//$('#tickets_pop_up').hide();
			$('#transaction_block').addClass('btn-default').addClass('active');
			$('#ticket_block').addClass('btn-active');
		}else{
			$('#transaction_block').addClass('btn-active');
			$('#ticket_block').addClass('btn-default').addClass('active');
		}

	var val = $('input[name="questionsinfo"]:checked').val();
	if (val=='transaction') {
	 	  $('#transactionqns').show();
		  $('#ticketqns').hide();
		  $('#transaction_qn_div').show();
		  $('#ticket_qn_div').hide();
		  $('#tran_qn_help_msg').show();
		  $('#tkt_qn_help_msg').hide();
	}else{
	    $('#transactionqns').hide();
	    $('#ticketqns').show();
	    $('#transaction_qn_div').hide();
	    $('#ticket_qn_div').show();
	    $('#tran_qn_help_msg').hide();
		  $('#tkt_qn_help_msg').show();
	}
	});
	
	$('#addtransactionquestion').click(function(){
		//offsetTop=$(this).offset().top;
		if(powertype=='RSVP' && $("ul#transactionquestions li").length <= 0){
    		$("#noInfoMsg").html('');
    		$("#noInfoMsg").removeClass("not-found");
    		$("#noInfoMsg").hide();
    	}
		
		$("#addtransactionquestion").attr("disabled",true);
		stopHoverEfects('','Transaction');
		showProcessing('transactionquestions');
		var url = 'ManageRegistrationForm!addQuestion?eid='+eid+'&powertype='+powertype+'&qlevel=transaction';
    	openNewQuestion(url,'');
	});
	
    $('#addticketquestion').click(function () {
    	//$('#tickets_pop_up').hide();
    	//tktOffsetTop=$(this).offset().top;
    	$("#addticketquestion").attr("disabled",true);
    	stopHoverEfects('','Ticket');
    	$el = $(this);
    	showProcessing('ticketquestions');
    	var url = 'ManageRegistrationForm!addQuestion?eid='+eid+'&powertype='+powertype+'&qlevel=ticket';
    	openNewTktQuestion(url,'',$el);
    });
	
    $('#questionbox').on('click','#qnsettingsicon',function(){
	        $('#quessettings').slideToggle("slow");
    });
    
    $('#tktquestionbox').on('click','#tktqnsettingsicon',function(){
        $('#tktquessettings').slideToggle("slow");
    });
	    
    $(document).on('click', '.editTransactionQuestion', function () {
    	offsetTop=$(this).offset().top;
    	$("#addtransactionquestion").attr("disabled",true);
    	$el = $(this);
    	var qid=$el.parents('.question').data('questionid');
    	//stopHoverEfects('trqnid_'+qid,'Transaction');
    	showProcessing('trqnid_'+qid);
    	var url = 'ManageRegistrationForm!editQuestion?eid='+eid+'&attributeid='+qid+'&powertype='+powertype+'&qlevel=transaction';
    	openNewQuestion(url,qid);
    	//$('#questionbox').css({"margin-top":"-3px"});
    });
    
    $(document).on('click', '.editTicketQuestion', function () {
    	//$('#tickets_pop_up').hide();
    	tktOffsetTop=$(this).offset().top;
    	$("#addticketquestion").attr("disabled",true);
    	$el = $(this);
        var qid=$el.parents('.tktquestion').data('questionid');
        //stopHoverEfects('tktqnid_'+qid,'Ticket');
        var url = 'ManageRegistrationForm!editQuestion?eid='+eid+'&attributeid='+qid+'&powertype='+powertype+'&qlevel=ticket';
	    showProcessing('tktqnid_'+qid);
	    openNewTktQuestion(url,qid,$el);
	    //$('#tktquestionbox').css({"margin-top":"-3px"});
	 });
    
    
    $(document).on('click', '.selectTickets', function () {
    	//tktOffsetTop=$(this).offset().top;
    	$("#addticketquestion").attr("disabled",true);
        var qid= $(this).parents('li').data('questionid');
        //stopHoverEfects('basicpf'+qid,'Ticket');
        var url='ManageRegistrationForm!applyTicketsToQuestion?eid='+eid+'&attributeid='+qid+'&powertype='+powertype+'&qlevel=ticket';
        showProcessing('basicpf'+qid);
        openBasicProfileQuestion(url,qid);
        //$('#tktquestionbox').css({"margin-top":"-3px"});
    });
    
    $('#questionbox').on('click','.confirmaddquestion',function(e){
    	$(".confirmaddquestion").attr("disabled",true);
    	if(e.handled !== true){
    	saveQuestion();
    	e.handled = true;
        }
    });
    
    $('#tktquestionbox').on('click','.confirmaddtktquestion',function(e){
    	$(".confirmaddtktquestion").attr("disabled",true);
    	if(e.handled !== true){
		 saveTktQuestion();
		 e.handled = true;
        }
	 });
    
    $('#tktquestionbox').on('click','.confirmaddbasicquestion',function(){
		 saveTktBasicQuestion();
	 });
	    
    $('#questionbox').on('click','.canceladdquestion',function(){
    	$("#addtransactionquestion").attr("disabled",false);
    	if(powertype=='RSVP' && $("ul#transactionquestions li").length <= 0){
    		$("#noInfoMsg").show();
    	}
    	$('#addquestiontemplate').fadeOut();
    	offsetTop=$("#questionbox").offset().top;
    	$('#questionbox').html('');
    	//$('#questionbox').css({"margin-top":"0px"});
    	$("#transactionqns li").last().css("border-bottom", "0px");
    	//alert(offsetTop);
    	$('html, body').animate({
	        scrollTop: offsetTop-190
	    }, 1000);
    	restartHoverEfects('Transaction');
    	
    });
    
    $('#tktquestionbox').on('click','.canceladdtktquestion',function(){
    	tktOffsetTop=$('#tktquestionbox').offset().top;
		 $('#addtktquestiontemplate').fadeOut();
		 $('#tktquestionbox').html('');
		 $("#addticketquestion").attr("disabled",false);
		 //$('#tktquestionbox').css({"margin-top":"0px"});
		 $("#ticketqns li").last().css("border-bottom", "0px");
		 $('html, body').animate({
		        scrollTop: tktOffsetTop-190
		    }, 1000);
		 restartHoverEfects('Ticket');
    });
    
    $('#tktquestionbox').on('click','.canceladdbasicquestion',function(){
    	tktOffsetTop=$('#tktquestionbox').offset().top;
		 $('#addbasicquestiontemplate').fadeOut();
		 $('#tktquestionbox').html('');
		 $("#addticketquestion").attr("disabled",false);
		 //$('#tktquestionbox').css({"margin-top":"0px"});
		 $('html, body').animate({
		        scrollTop: tktOffsetTop-190
		    }, 1000);
		 restartHoverEfects('Ticket');
   });
	 
    $(document).on('click', '.deleteTransactionQuestion', function () {
    	++n;
    	$el = $(this);
    	var attribid=$el.parents('.question').data('questionid');
    	var confirmurl='ManageRegistrationForm!deleteQuestionConfirm?eid='+eid+'&attributeid='+attribid;
    	var message='';
    	if(n==1)
    	$.ajax({
    		type:"POST",
    		url:confirmurl,
    		error: function(){n=0;alert("System cant process at this time");},
    		success:function(res){
    			if(!isValidActionMessage(res)) return;
    			n=0;
    			if(res.indexOf("exist")>-1)
    				message=props.qn_delete_confirm_msg_with_responses;
    			else
    				message=props.qn_delete_confirm_msg_no_responses;
    			bootbox.confirm(message, function (result) {
    				if (result){
    					deleteQuestion(attribid,'transaction');
    				}
    			});
    		}
    	});
  
    });
    
    $(document).on('click', '.deleteTicketQuestion', function () {
    	++n;
        $el = $(this);
    	var attribid=$el.parents('.tktquestion').data('questionid');
    	var confirmurl='ManageRegistrationForm!deleteQuestionConfirm?eid='+eid+'&attributeid='+attribid;
    	var message='';
    	if(n==1)
    	$.ajax({
    		type:"POST",
    		url:confirmurl,
    		error: function(){n=0;alert("System cant process at this time");},
    		success:function(res){
    			if(!isValidActionMessage(res)) return;
    			n=0;
    			if(res.indexOf("exist")>-1)
    				message=props.qn_delete_confirm_msg_with_responses;
    			else
    				message=props.qn_delete_confirm_msg_no_responses;
				        bootbox.confirm(message, function (result) {
				        	if (result){
				        		deleteQuestion(attribid,'ticket');
				        	}
				        });
    		}
    	});
    });
	 
	 //sortable function starts
	    $('#transactionquestions').sortable({
	        group: 'nested',
	        handle: '.dragHandle',
	        onDrop: function  (item, targetContainer, _super) {
	            var clonedItem = $('<li/>').css({height: 0});
	            item.before(clonedItem);
	            clonedItem.animate({'height': item.height()});
	            
	            item.animate(clonedItem.position(), function  () {
	              clonedItem.detach();
	              _super(item);
	            });
	          },
	          // set item relative to cursor position
	          onDragStart: function ($item, container, _super) {
	            var offset = $item.offset(),
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
	          },
	          onDrop: function  (item, targetContainer, _super) {
	        	    var clonedItem = $('<li/>').css({height: 0});
	        	    item.before(clonedItem);
	        	    clonedItem.animate({'height': item.height()});
	        	    
	        	    item.animate(clonedItem.position(), function  () {
	        	      clonedItem.detach();
	        	      _super(item);
	        	      $("#addtransactionquestion").attr("disabled",false);
	        	      $('#addquestiontemplate').fadeOut();
	        	      $('#questionbox').html('');
	        	      //$('#questionbox').css({"margin-top":"0px"});
	        	      $('#questionbox').hide();
	        	      getTransactionQuestionsPosition();
	        	      restartHoverEfects('Transaction');
	        	      
	        	    });
	        	    
	        	  }
	    });
	    
	    $('#ticketquestions').sortable({
	    	handle: '.tktDragHandle',
	        onDrop: function  (item, targetContainer, _super) {
	            var clonedItem = $('<li/>').css({height: 0});
	            item.before(clonedItem);
	            clonedItem.animate({'height': item.height()});
	            
	            item.animate(clonedItem.position(), function  () {
	              clonedItem.detach();
	              _super(item);
	            });
	        },

	        // set item relative to cursor position
	        onDragStart: function ($item, container, _super) {
	        	var offset = $item.offset(),
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
	        },
	        onDrop: function  (item, targetContainer, _super) {
	        	var clonedItem = $('<li/>').css({height: 0});
	        	item.before(clonedItem);
	        	clonedItem.animate({'height': item.height()});
	    
	        	item.animate(clonedItem.position(), function  () {
	        		clonedItem.detach();
	        		_super(item);
	        		$("#addticketquestion").attr("disabled",false);
	        		$('#addbasicquestiontemplate').fadeOut();
	       		 	$('#tktquestionbox').html('');
	       		 	//$('#questionbox').css({"margin-top":"0px"});
	       		 	$('#tktquestionbox').hide();
	        		getTicketQuestionsPosition();
	        		restartHoverEfects('Ticket');
	        	});
	    
	        }
	    });
	    
});



function addtransactionqno(){
	
	if(powertype=='RSVP' && $("ul#transactionquestions li").length <= 0){
		$("#noInfoMsg").html('');
		$("#noInfoMsg").removeClass("not-found");
		$("#noInfoMsg").hide();
	}
	
	$("#addtransactionquestion").attr("disabled",true);
	stopHoverEfects('','Transaction');
	showProcessing('transactionquestions');
	var url = 'ManageRegistrationForm!addQuestion?eid='+eid+'&powertype='+powertype+'&qlevel=transaction';
	openNewQuestion(url,'');
}

function addticketqno() {
	//$('#tickets_pop_up').hide();
	//tktOffsetTop=$(this).offset().top;
	$("#addticketquestion").attr("disabled",true);
	stopHoverEfects('','Ticket');
	$el = $(this);
	showProcessing('ticketquestions');
	var url = 'ManageRegistrationForm!addQuestion?eid='+eid+'&powertype='+powertype+'&qlevel=ticket';
	openNewTktQuestion(url,'',$el);
}


function openNewQuestion(url,qId){
	/*$('#questionbox').load(url,function(){
		hideProcessing();
		$('#questionbox').fadeIn();
		if(qId!='') $('#questionbox').insertAfter($('#trqnid_'+qId));
		else $('#questionbox').insertAfter($('#transactionquestions'));
		$('input[name="customattribute.name"]').focus();
	});*/
	
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			hideProcessing();
			if(qId!='') 
				$('#questionbox').insertAfter($('#trqnid_'+qId));
			else{
				$("#transactionqns li").last().css("border-bottom", "1px solid #ddd");
				$('#questionbox').insertAfter($('#transactionquestions'));
			}
			$('#questionbox').html(result);
			$('#questionbox').fadeIn();
			$('input[name="customattribute.name"]').focus();
			$('html,body').animate({
		          scrollTop: $("#questionbox").offset().top-120
		        }, 1000);
		}
	});
}

function openNewTktQuestion(url,qId,ele){
	/*$('#tktquestionbox').load(url,function(){
		hideProcessing();
		$('#tktquestionbox').fadeIn();
		if(qId!='') $('#tktquestionbox').insertAfter($('#tktqnid_'+qId));
		else $('#tktquestionbox').insertAfter($('#ticketquestions'));
		$('input[name="customattribute.name"]').focus();
	});*/
	
	
	$('.background-options').each(function() {
		var divid=($(this).attr("id"));
        if($(this).is(":visible")){
        	$(".shown-to").find("span#"+divid+"_img").removeClass("down").addClass("original");
        	$(".shown-to").find("span#"+divid+"_lbl").removeClass("highlighted-text");
        	$(this).slideUp();
        }
    });
	
	
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			hideProcessing();
			if(qId!='') 
				$('#tktquestionbox').insertAfter($('#tktqnid_'+qId));
			else{
				$("#ticketqns li").last().css("border-bottom", "1px solid #ddd");
				$('#tktquestionbox').insertAfter($('#ticketquestions'));
			}
			$('#tktquestionbox').html(result);
			$('#tktquestionbox').fadeIn();
			$('input[name="customattribute.name"]').focus();
			$('html,body').animate({
		          scrollTop: $("#tktquestionbox").offset().top-120
		        }, 1000);
		}
	});
}

function openBasicProfileQuestion(url,qId){
	/*$('#tktquestionbox').load(url,function(){
		hideProcessing();
		$('#tktquestionbox').fadeIn();
		$('#tktquestionbox').insertAfter($('#basicpf'+qId));
	});*/
	
	$('.background-options').each(function() {
		var divid=($(this).attr("id"));
        if($(this).is(":visible")){
        	$(".shown-to").find("span#"+divid+"_img").removeClass("down").addClass("original");
        	$(".shown-to").find("span#"+divid+"_lbl").removeClass("highlighted-text");
        	$(this).slideUp();
        }
    });
	
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			hideProcessing();
			$('#tktquestionbox').html(result);
			$('#tktquestionbox').fadeIn();
			$('#tktquestionbox').insertAfter($('#basicpf'+qId));
			$('html,body').animate({
		          scrollTop: $("#tktquestionbox").offset().top-120
		        }, 1000);
		}
	});
}

function saveQuestion(){
	var url='ManageRegistrationForm!saveQuestion';
	$("#subquestionsinfoid").val(JSON.stringify(buyersubquestionsinfo));
	$.ajax({
		type:'POST',
		data:$('#questionform').serialize(),
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			if(result.indexOf('errorMessage')>-1){
				$(".confirmaddquestion").attr("disabled",false);
				$('#questionformerrormessages').show().html(result);
				$('html, body').animate({
			        scrollTop: $("#questionformerrormessages").offset().top-100
			    }, 1000);
			}else{
				showProcessing('transactionquestions');
				getReloadedData();
				restartHoverEfects('Transaction');
				hideProcessing();
				var statusmsg=props.qn_added_statsus_msg;
				if(global_eb_buyer_purpose=='edit') statusmsg=props.qn_updated_status_msg;
				notification(statusmsg,"success");
				$("#transaction_basic_qns li").last().css("border-bottom", "0px");
			}
		}
	});
}

function saveTktQuestion(){
	var url='ManageRegistrationForm!saveQuestion';
	$("#tktsubquestionsinfoid").val(JSON.stringify(ticketsubquestionsinfo));
	$.ajax({
		type:'POST',
		data:$('#tktquestionform').serialize(),
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			if(result.indexOf('errorMessage')>-1){
				$(".confirmaddtktquestion").attr("disabled",false);
				$('#tktquestionformerrormessages').show().html(result);
				$('html, body').animate({
			        scrollTop: $("#tktquestionformerrormessages").offset().top-100
			    }, 1000);
			}else{
				showProcessing('ticketquestions');
				getReloadedTktData();
				restartHoverEfects('Ticket');
				hideProcessing();
				var statusmsg=props.qn_added_statsus_msg;
				if(global_eb_ticket_purpose=='edit') statusmsg=props.qn_updated_status_msg;
				notification(statusmsg,"success");
				$('html, body').animate({
			        scrollTop: $("#questions_notification_holder").offset().top-100
			    }, 1000);
				
				if(powertype=='RSVP')
					$("#rsvp_tktbasic_qns li").last().css("border-bottom", "0px");
				else 
					$("#ticket_basic_qns li").last().css("border-bottom", "0px");
				
			}
		}
	});
}

function  saveTktBasicQuestion(){
	
	/*if($('input[name=seltickets]:checked').val()==undefined){
		$("#assignticketsformerrormessages").html("Select at least one ticket").show();
		return false;
	}*/
	 $.ajax({
     	type: "POST",
         url:'ManageRegistrationForm!saveQSettings',
         data: $("#addbasicpfquestion").serialize(), 
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 getReloadedTktData();
        	 $('#addbasicquestiontemplate').fadeOut();
    		 $('#tktquestionbox').html('');
         }
     });
}

function deleteQuestion(attribid,qlevel){
	showProcessing(attribid);
	$.ajax( {
		url:'ManageRegistrationForm!deleteQuestion?eid='+eid+'&attributeid='+attribid+'&qlevel='+qlevel,                   	   
		success: function( t ) { 
			if(!isValidActionMessage(t)) return;
			if(t.indexOf('success'>-1)){
				//loadingPopup();
        		hideProcessing();
        		if(qlevel=='ticket') getReloadedTktData();
        		else getReloadedData();
        		notification(props.qn_deleted_status_msg,"success");
        	}
        }
	});
}

function getReloadedData(){
	$.ajax({
		type:'POST',
		url:'ManageRegistrationForm!getTransactionQnData?eid='+eid,
		async:false,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#questionbox').html('');
			$('#questionbox').insertAfter($('#transactionquestions'));
			$('#transactionquestions').html('');
			arrangeQuestions(JSON.parse(result));
			//$('html, body').animate({ scrollTop: 100 }, 'slow');
		}
	});
}

function getReloadedTktData(){
	$.ajax({
		type:'POST',
		url:'ManageRegistrationForm!getTicketQnData?eid='+eid,
		async:false,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#tktquestionbox').html('');
			$('#tktquestionbox').insertAfter($('#ticketquestions'));
			$('#ticketquestions').html('');
			questionTicketJson=JSON.parse(result).questionTicketJson;
			arrangeTktQuestions(JSON.parse(result).responseQuestionJsonData);
			//$('html, body').animate({ scrollTop: 100 }, 'slow');
		}
	});
}

function arrangeQuestions(qndata){
	$("#addtransactionquestion").attr("disabled",false);
	$('#transactioncount').html(qndata.questions.length);
	if(powertype=='RSVP' && qndata.questions.length<=0){
		//$('#addtransactionquestion').trigger('click');
		$("#noInfoMsg").show();
	}
	$(qndata.questions).each(function(key,value){ 
		var html='';    	
	    html=transactionQnsView(value);
	    $(html).appendTo('#transactionquestions');
	    if(powertype=='RSVP') $("#transactionqns li").first().css("border-top", "0px");
	});
	
	$('html, body').animate({
        scrollTop: offsetTop-196
    }, 1000);
}

function arrangeTktQuestions(qndata){
	$("#addticketquestion").attr("disabled",false);
	$('#ticketcount').html(qndata.questions.length);
	$(qndata.questions).each(function(key,value){ 
		var html='';    	
	    html=ticketQnsView(value);
	    $(html).appendTo('#ticketquestions');
	});
	
	$('html, body').animate({
        scrollTop: tktOffsetTop-196
    }, 1000);
}

function transactionQnsView(valueobj){
	var subqns='';
	var html = '<li class="question lists hover-item each-qn" data-questionid="' + valueobj.attrib_id + '" id="trqnid_'+valueobj.attrib_id +'">' +
	   '<div class="row"><div class="col-md-5 col-sm-5"><div class="row col-md-1 col-sm-1 col-xs-1"><img src="/main/bootstrap/img/grippy_large.png" style="top: 0px;" class="dragHandle dragicon"></div><div class="col-md-11 col-sm-11 col-xs-11 qn-name"><span class="qname">'+valueobj.attribname+'</span></div></div>'+
	   /*'<div class="col-md-5 col-sm-5 extramarginques">'+
	   '<div class="btn-group btn-multiple btn-group-lg btn-group-sm" data-toggle="buttons">'+
	   '<label id="question_Required_'+valueobj.attrib_id+'" class="question_'+valueobj.attrib_id+'_block btn  col-xs-6 col-md-6  col-sm-6';
	if(valueobj.isrequired=='Required')
		html+= ' btn-default active" ';
	else
		html+=' btn-active" ';   

	html+='onclick="customRequired('+valueobj.attrib_id+',\'Required\')">'+
		'<input type="radio" value="Required" >Required</label>'+
		'<label id="question_Optional_'+valueobj.attrib_id+'" class="question_'+valueobj.attrib_id+'_block btn  col-xs-6 col-md-6 col-sm-6';

	if(valueobj.isrequired=='Optional')
		html+= ' btn-default active" ';
	else
		html+=' btn-active" '; 

	html+='onclick="customRequired('+valueobj.attrib_id+',\'Optional\')">'+
		'<input type="radio" value="Optional" >Optional</label></div>'+
		'</div>'+*/
	   	'<div class="col-md-4 col-sm-4 sm-font" >';
	   	/*if(valueobj.attribtype=='radio'|| valueobj.attribtype=='checkbox' || valueobj.attribtype=='select'){
	   		
	   		var subQnlbl='Sub-Questions';
	   		var subCnt=valueobj.subQnsCount;
			if(subCnt==0){ subQnlbl=''; subCnt='';};
			if(subCnt==1) subQnlbl='Sub-Question';
			html+= 'Selection';
			subqns='<span class="manage-txsubqn pull-right">'+subCnt+' '+subQnlbl+'</span>';
		}else
			html+= '';*/
	   	html+='</div>'+
	   	'<div class="col-md-2 col-sm-3 sm-font links-div pull-right"><span class="hidden manage-qn">' +
		'<a href="javascript:;" class="editTransactionQuestion">'+props.global_edit_lnk_lbl+'</a>'+
		'<a href="javascript:;" class="deleteTransactionQuestion">'+props.global_delete_lnk_lbl+'</a>'+
		'</span>'+subqns+'</div>';
	html += '</div></li>';
	return html;
}

function ticketQnsView(valueobj){
	var subqns='';
	var html = '<li class="tktquestion lists hover-item each-tqn"   data-questionid="' + valueobj.attrib_id + '" id="tktqnid_'+valueobj.attrib_id +'">' +
		'<div class="row"><div class="col-md-5 col-sm-5"><div class="row col-md-1 col-sm-1 col-xs-1"><img src="/main/bootstrap/img/grippy_large.png" style="top: 0px;" class="tktDragHandle  dragicon"></div><div class="col-md-11 col-sm-11 col-xs-11 qn-name"><span class="qname">'+valueobj.attribname+'</span></div></div>';
		/*'<div class="col-md-4 col-sm-5 extramarginques" >'+
		'<div class="btn-group btn-group-lg btn-group-sm btn-multiple" data-toggle="buttons">'+
		'<label id="question_Required_'+valueobj.attrib_id+'" class="question_'+valueobj.attrib_id+'_block btn col-xs-6 col-md-6 col-sm-6';
	if(valueobj.isrequired=='Required')
		html+= ' btn-default active" ';
	else
		html+=' btn-active" '; 

	html+='onclick="customRequired('+valueobj.attrib_id+',\'Required\')">'+
		'<input type="radio" value="Required" >Required</label>'+
		'<label id="question_Optional_'+valueobj.attrib_id+'" class="question_'+valueobj.attrib_id+'_block btn  col-xs-6 col-md-6  col-sm-6';

	if(valueobj.isrequired=='Optional')
		html+= ' btn-default active" ';
	else
		html+=' btn-active" '; 

	html+='onclick="customRequired('+valueobj.attrib_id+',\'Optional\')">'+
		'<input type="radio" value="Optional" >Optional</label></div>'+
		'</div>'+*/
	/*if(valueobj.attribtype=='radio'|| valueobj.attribtype=='checkbox' || valueobj.attribtype=='select'){
		
		var subQnlbl='Sub-Questions';
		var subCnt=valueobj.subQnsCount;
		if(subCnt==0){ subQnlbl=''; subCnt='';};
		if(subCnt==1) subQnlbl='Sub-Question';
		
		html+= 'Selection';
		subqns='<span class="manage-subqn pull-right">'+subCnt+' '+subQnlbl+'</span>';
	}else
		html+= '';*/
	var shownTo='';
	if(powertype=='Ticketing')
		shownTo='<label class="shown-to" style="cursor: pointer;"><span id="'+valueobj.attrib_id+'_seltqns_lbl">'+props.qn_shown_to_lnk_lbl+'</span>'+
		'<span style="cursor: pointer;  margin-top: 3px;" id="'+valueobj.attrib_id+'_seltqns_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></label>';
	html+='<div class="col-md-4 col-sm-3 sm-font" >'+shownTo+'</div>'+
		'<div class="col-md-2 col-sm-3 sm-font links-div pull-right"><span class="hidden manage-tqn">' +
		'<a href="javascript:;" class="editTicketQuestion">'+props.global_edit_lnk_lbl+'</a>'+
		'<a href="javascript:;" class="deleteTicketQuestion">'+props.global_delete_lnk_lbl+'</a>'; 
	//if(powertype=='Ticketing') html += '<a href="javascript:;" class="editTicketQuestion" data-seltks="seltkts"></a>';
	html += '</span>'+subqns+'</div></div><div class="background-options sm-font" id="'+valueobj.attrib_id+'_seltqns" style="display: none;"></div>'+
	'</li>';
	return html;
}

function phoneSettings(selected,unselected,lebelclass,inputname){
	//loadingPopup();
	 $('input[name="'+inputname+'"][value="'+selected+'"]').prop("checked",true);
	 $("."+lebelclass).each(function() {
			$(this).removeClass('active'); 
			$(this).removeClass('btn-default'); 
			$(this).removeClass('btn-active'); 
		});
	    $('#'+selected+'_block').addClass('btn-default');
		$('#'+unselected+'_block').addClass('btn-active');
		var showoption="N";
		var requeriedoption="Optional";
			requeriedoption=$('input[name="customattribute.isRequired"]:checked').val();
			showoption=$('input[name="displayPhoneYN"]:checked').val();
		var url='ManageRegistrationForm!savePhoneSettings?eid='+eid+'&displayPhoneYN='+showoption+'&phoneRequired='+requeriedoption;
		$.ajax({
			type:"POST",
			url:url,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				if(showoption=="Y")
					$('input[name="customattribute.isRequired"]').attr('disabled',false);
				else
					$('input[name="customattribute.isRequired"]').attr('disabled',true);
			}
			
		});
}

function customRequired(qid,type){
	//loadingPopup();
	$(".question_"+qid+"_block").each(function(){
		$(this).removeClass('active'); 
		$(this).removeClass('btn-default'); 
		$(this).removeClass('btn-active'); 
	});
	
	if(type=='Required'){
		$('#question_Required_'+qid).addClass('btn-default');
		$('#question_Optional_'+qid).addClass('btn-active');
	}else{
		$('#question_Optional_'+qid).addClass('btn-default');
		$('#question_Required_'+qid).addClass('btn-active');
	}
	
	var url='ManageRegistrationForm!saveCustomQuestionRequiredOptional?eid='+eid+'&questionRequired='+type+'&attributeid='+qid;
	$.ajax({
		type:"POST",
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
		}
	});
	
}

function assignRequired(atribid,type,inputname,selected){
	$('input[name="'+inputname+'"][value="'+selected+'"]').prop("checked",true);
	$(".assignReqOptional_"+atribid).each(function(){
		$(this).removeClass('active'); 
		$(this).removeClass('btn-default'); 
		$(this).removeClass('btn-active'); 
	});
	if(type=='Required'){
		$('#assign_req_block_'+atribid).addClass('btn-default');
		$('#assign_opt_block_'+atribid).addClass('btn-active');
	}else{
		$('#assign_opt_block_'+atribid).addClass('btn-default');
		$('#assign_req_block_'+atribid).addClass('btn-active');
	}
}

var getTransactionQuestionsPosition = function () {
    var pos = [];
    $('#transactionquestions li').each(function () {
        pos.push($(this).data('questionid'));
    });
    var url='ManageRegistrationForm!swapQuestions?eid='+eid+'&powertype=Ticketing&questionPostions='+pos+'&qlevel=transaction';
    $.ajax({
    	type:"POST",
    	url:url,
    success:function(result){
    	if(!isValidActionMessage(result)) return;
    }
    });
    
};

var getTicketQuestionsPosition = function () {
    var pos = [];
    $('#ticketquestions li').each(function () {
        pos.push($(this).data('questionid'));
    });
    var url='ManageRegistrationForm!swapQuestions?eid='+eid+'&powertype=Ticketing&questionPostions='+pos+'&qlevel=ticket';
    $.ajax({
    	type:"POST",
    	url:url,
    success:function(result){
    	if(!isValidActionMessage(result)) return;
    }
    });
};

function showProcessing(divid){
	var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$('#'+divid).after(html);
}

function hideProcessing(){
	$('#loading').remove();
}

function startHoverEfects(){
	 $(document).on('mouseover','.each-qn',function() {
		 $(this).find('.manage-qn').removeClass('hidden').addClass('visible');
		 $(this).find('.manage-txsubqn').removeClass('visible').addClass('hidden');
	 });
	 
	 $(document).on('mouseout','.each-qn',function() {
		 $(this).find('.manage-qn').removeClass('visible').addClass('hidden');
		 $(this).find('.manage-txsubqn').removeClass('hidden').addClass('visible');
	 });	
	 
	 $(document).on('mouseover','.each-tqn',function() {
		 $(this).find('.manage-tqn').removeClass('hidden').addClass('visible');
		 //$("#tickets_pop_up .arrow-box-after").css({'border-left-color':'#f6f6fa'});
		 $(this).find('.manage-subqn').removeClass('visible').addClass('hidden');
	 });
	 
	 $(document).on('mouseout','.each-tqn',function() {
		 $(this).find('.manage-tqn').removeClass('visible').addClass('hidden');
		 //$("#tickets_pop_up .arrow-box-after").css({'border-left-color':'#FFF'});
		 $(this).find('.manage-subqn').removeClass('hidden').addClass('visible');
	 });
}

function stopHoverEfects(qnid,qnlevel){
	if(qnlevel=='Ticket'){
		$('#ticketqns li').each(function(){
			$(this).removeClass('each-tqn').removeClass('hover-item');
		});
		
		 $('#'+qnid).addClass('backgroundclr');
		 $('#'+qnid).find('.manage-tqn').addClass('visible');
	}else{
		$('#transactionqns li').each(function(){
			$(this).removeClass('each-qn').removeClass('hover-item');
		});
		$('#'+qnid).addClass('backgroundclr');
		$('#'+qnid).find('.manage-qn').addClass('visible');
	}
}

function restartHoverEfects(qnlevel){
	if(qnlevel=='Ticket'){
		$('#ticketqns li').each(function(){
			$(this).addClass('each-tqn').addClass('hover-item').removeClass('backgroundclr');
			$(this).find('.manage-tqn').removeClass('visible').addClass('hidden');
		});
	}else{
		$('#transactionqns li').each(function(){
			$(this).addClass('each-qn').addClass('hover-item').removeClass('backgroundclr');
			$(this).find('.manage-qn').removeClass('visible').addClass('hidden');
		});
	}
}

function showSubQnsLabel(element){
	if(element.value.trim().length>0)
		$(element).parent().next().find(".sub-questions").show();
	else
		$(element).parent().next().find(".sub-questions").hide();
}