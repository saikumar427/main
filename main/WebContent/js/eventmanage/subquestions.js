var subquestionscount=0;
var tempsubqid='';
var isSubQnSaved=false;
$(document).ready(function () {
	
startHoverEfects();
$('#addsubquestion').click(function (){
	showProcessing('#subquestions');
	$("#noInfoMsg").hide();
	stopHoverEfects('');
    	var url = 'ManageRegistrationForm!addSubQuestion?eid='+eid+'&optionId='+optionId;
    	/*$('#subquestionbox').load(url,function(){
    		$('#subquestionbox').fadeIn();
    		$('#subquestionbox').insertBefore($( "#tempsubquestionbox" ));
    		parent.resizeIframe();
    	});*/
    	
    	$.ajax({
    		type:'POST',
    		url:url,
    		success:function(result){
    			if(!parent.isValidActionMessage(result)) return;
    			hideProcessing();
    			$('#subquestionbox').html(result);
    			$('#subquestionbox').fadeIn();
        		$('#subquestionbox').insertBefore($("#tempsubquestionbox" ));
        		$("#addsubquestion").attr("disabled",true);
        		parent.resizeIframe();
        		
    		}
    	});
    });

$('#subquestions').on('click','li .editsubquestion',function (){
	$("#addsubquestion").attr("disabled",true);
	$el = $(this);
    var qid=$el.parents('.question').data('subquestionid');
    showProcessing("ul#subquestions li#subqnid_"+qid);
    stopHoverEfects(qid);
    var url = 'ManageRegistrationForm!addSubQuestion?eid='+eid+'&optionId='+optionId+'&purpose=edit';
	
	 if(tempsubqid==qid){
	    	if(isSubQnSaved){
	    		if($('#subquestionbox').is(":visible"))
		    	$('#subquestionbox').hide();
	    		loadSubQuestion(url, qid);
	    		isSubQnSaved=false;
	    	}else{
	    	
	    	var subqnbox=document.getElementById('subquestionbox').scrollHeight;
	    	if(!$('#subquestionbox').is(":visible")){
	    		$('#subquestionbox').show();
	    		subqnbox=document.getElementById('subquestionbox').scrollHeight;
	    		$('#subquestionbox').hide();
	    	}
	    	
	    	
	    	/*if($('#subquestionbox').is(":visible"))
	             parent.resizeIframePopup(subqnbox,'remove');
	    	else
	    		 parent.resizeIframePopup(subqnbox,'add');*/
	    	parent.resizeIframe();
	    	}
	    	
	    	
	    }else{
	    	if($('#subquestionbox').is(":visible"))
	    		$('#subquestionbox').hide();
	    	    loadSubQuestion(url, qid);
	    }
	 tempsubqid=qid;
	
});

function loadSubQuestion(url, qid){
	
	$.ajax({
        type:"POST",
        url:url,
        async:false,
        success:function(result){
        	
            $('#subquestionbox').html(result);
            ///////////////

    		var subquestion=subquestionsarray[qid-1];
    		$('#subquestionid').val(qid);
    		$('#question').val(subquestion.name);
            $('#attribid').val(subquestion.attribid);
             if(subquestion.reqoptional=='Required'){
            	 $(".requiredoptional").each(function() {
     				$(this).removeClass('active'); 
     				$(this).removeClass('btn-default'); 
     				$(this).removeClass('btn-active'); 
     			});
            	 $('#Required_block').addClass('btn-default');
            	 $('#Optional_block').addClass('btn-active');
             }
             
             $(".qtntype").each(function() {
         		$(this).removeClass('active'); 
         		$(this).removeClass('btn-default'); 
         		$(this).removeClass('btn-active'); 
         	});
             
             $('input[name="type"][value="'+subquestion.type+'"]').prop("checked",true);
         	if(subquestion.type=='text'){
         		$('#text_block').addClass('btn-default').addClass('active');
         		$('#textarea_block').addClass('btn-active');
         		$('#selection_block').addClass('btn-active');
        	}else if(subquestion.type=='textarea'){
         		$('#text_block').addClass('btn-active');
         		$('#textarea_block').addClass('btn-default').addClass('active');
         		$('#selection_block').addClass('btn-active');
        	}else{
         		$('#text_block').addClass('btn-active');
         		$('#textarea_block').addClass('btn-active');
         		$('#selection_block').addClass('btn-default').addClass('active');
         	}
         	
         	if (subquestion.type=='text' || subquestion.type=='textarea') {
    			if(subquestion.type=='text'){
    				$('#smalltext').show();
        	  		$('#smalltextarea').hide();
    			}else{
    	 			$('#smalltext').hide();
    	  			$('#smalltextarea').show();
    			}
            	$('#options').slideUp(function(){
            		parent.resizeIframe();
             	});
            	//$('input[name="type"]').tooltipster('hide');
            	//validator.element(this);
            
        	}else if(subquestion.type=='selection') {
            	$('#smalltext').hide();
            	$('#smalltextarea').hide();
            	$("#mult_choice_type").show();
            	$('#sortableOptions').html('');
            	for(var i=0;i<subquestion.answers.length;i++){
            		var htmldata='<li class="multisort"><div class="row"><div class="col-xs-5 extramargin" style="margin-top:-10px;"><img class="subopthandle dragicon" src="/main/bootstrap/img/grippy_large.png">'+
            		'<input style="margin-left: 10px;" type="text" name="multipleSubOptionsMap" value="'+subquestion.answers[i]["value"]+'" placeholder="+ Add an answer" class="form-control" data-suboptionid="'+subquestion.answers[i]["key"]+'"/>'+
            		'</div><div class="col-xs-3 multidata extramargin" style="padding-top: 6px;"><span class="dragiconDelete"><a href="javascript:;" class="deleteOption">Delete</a></span></div></div></li>';
            		$('#sortableOptions').append(htmldata);
            	}
            	
            	var clslen=$(".deleteOption").length;
            	if(clslen==1) $(".dragiconDelete").hide();
            	
            	$('#options').slideDown(function() {
                	parent.resizeIframe();
            	});
            	$('#sortableOptions :first-child .input-group input').focus();
            	//$('input[name="type"]').tooltipster('show');
            	//validator.element(this);
            	
        	}
            
            	 $(".optiontype").each(function() {
     				$(this).removeClass('active'); 
     				$(this).removeClass('btn-default'); 
     				$(this).removeClass('btn-active'); 
     			});
     			if(subquestion.qtype=='single'){
         			$('#single_block').addClass('btn-default');
         			$('#pulldown_block').addClass('btn-active');
         			$('.checkbox-inline').show();
        			 }else{
        				$('#pulldown_block').addClass('btn-default');
         			$('#single_block').addClass('btn-active');
         			$('.checkbox-inline').hide();
        		}
     			
     			
             
             $('input[name="subCustomAttribute.size"]').val(subquestion.size);
             $('input[name="subCustomAttribute.rows"]').val(subquestion.rows);
             $('input[name="subCustomAttribute.columns"]').val(subquestion.columns);
             if(subquestion.multiple)
            	 $('input[name="multiple"]').iCheck('check');
    		
    		/*$('#subquestionbox').fadeIn();
    		$('#subquestionbox').insertAfter($("li[data-subquestionid="+qid+"]"));*/
             
             
             $('#subquestionbox').insertAfter($("li[data-subquestionid="+qid+"]"));
             hideProcessing();
    		/* if(isSubQnSaved && $('#subquestionbox').is(":visible")){
    			 $('#subquestionbox').slideToggle( "slow");
    			 isSubQnSaved=false;
    		 }
    		 else $('#subquestionbox').slideToggle( "slow");*/
    		 
            //////////////
            setTimeout(function(){
                var subqnbox=document.getElementById('subquestionbox').scrollHeight;
             //   alert(subqnbox);
                $('#subquestionbox').slideToggle("slow",function(){
                	
                	if($('#subquestionbox').is(":visible")){
                		/*$('#addsubquestion').hide();
                		$('#done').hide();*/
            		}else{ 
            			/*$('#addsubquestion').show();
            			$('#done').show();*/
            		}
                	
                	//parent.resizeIframePopup(subqnbox,'add');
                	parent.resizeIframe();
                });
                
            },150);
            $('#subquestionbox').css({"margin-top":"-3px"});
        }
    });
	
	
	//$('#subquestionbox').load(url,function(){});
	
}

$('#subquestionbox').on('click','.canceladdsubquestion',function(){
	
	if($("ul#subquestions li").length <= 0){
		$("#noInfoMsg").show();
	}
	
	$("#addsubquestion").attr("disabled",false);
    $('#addsubquestiontemplate').fadeOut();
    //var ht=$('#subquestionbox').height();
    $('#subquestionbox').html('');
    //parent.resizeIframe();
    isSubQnSaved=true;
    //parent.customResizeIframe(ht);
    
    parent.resizeIframe();
     
		/*$('#addsubquestion').show();
		$('#done').show();*/
		//$('#subquestionbox').css({"margin-top":"0px"});
    	restartHoverEfects();
});


addSubQuestion = function (question) {
	//$("#addsubquestion").attr("disabled",false);
	$('#addsubquestiontemplate').fadeOut();
	var subqnboxheight=$('#subquestionbox').height();
	subqnboxheight-=50;
    $('#subquestionbox').html('');
    createSubQuestionsView(question,subqnboxheight);
};

var createSubQuestionsView = function (question,subqnboxheight) {
	subquestionscount+=1;
         var html = '<li class="question lists hover-item each-subqn" data-subquestionid="'+subquestionscount+'" id="subqnid_'+subquestionscount+'">' +
         '<div class="row"><div class="col-xs-9 extramarginques"><img src="/main/bootstrap/img/grippy_large.png" style="top: 2px;" class="dragHandle dragicon">&nbsp;<span class="qname">'+question.name+'</span></div>'+
    	 '<div class="col-xs-3 extramarginques moveDown links-div"><span class="manage-subqn pull-right">' +
         '<a href="javascript:;" class="editsubquestion">Edit</a><a href="javascript:;" class="deleteSubQuestion">Delete</a>'; 
         html += '</span></div></div></li>';
        $(html).appendTo('#subquestions');
        //$('#ticketcount').html($("ul#ticketquestions").children().length);
        $('#lastticket').css("border-bottom","1");
        $('#lastticket').css({"border-bottom-left-radius":"0px","border-bottom-right-radius":"0px"});
        $("ul#subquestions li").each(function(){
        	$(this).css({"border-bottom-left-radius":"0px","border-bottom-right-radius":"0px"});
            });
        $("ul#subquestions li:last-child").css({"border-bottom-left-radius":"4px","border-bottom-right-radius":"4px"});
        //parent.customResizeIframe(subqnboxheight);
        parent.resizeIframe();
    };
    
$(document).on('click', '.deleteSubQuestion', function () {
    $el = $(this);
    var subquestionid=$el.parents('.question').data('subquestionid');
  	var attribid=$el.parents('.question').data('attribid');
  	var confirmurl='ManageRegistrationForm!deleteQuestionConfirm?eid='+eid+'&attributeid='+attribid;
  	var message='';
  	if(attribid!=undefined && attribid!=''){
	  	$.ajax({
	  		type:"POST",
	  		url:confirmurl,
	  		success:function(res){
	  			if(res.indexOf("exist")>-1)
	  				message='There are some registrations that already answered this question. Deleting the question will remove those responses. Do you want to delete the question anyway?';
	  			else
	  				message='<h3>Confirm delete</h3>Are you sure?';
	  				parent.bootbox.confirm(message, function (result) {
	  				if (result){
	  					/*var url='ManageRegistrationForm!deleteQuestion?eid='+eid+'&attributeid='+attribid+'&qlevel=transaction';
					     $.ajax({
					    	 type: "POST",
					         url:url,
					         success:function(result){
					        	 $el.parents('.question').remove();
					         }
					      });*/
	  					 $el.parents('.question').hide();
	  					if($('#subquestionbox').is(":visible")){
	                		$('#subquestionbox').hide();
	                		$('#subquestionbox').html('');
	  					}
	  					 subquestionsarray[subquestionid-1].deletestatus=true;
	  					 //parent.customResizeIframe(30);
	  					done();
	  					parent.resizeIframe();
					}
				});
	  		}
	  	});
  		
  	}else{
  		
  		message='<h3>Confirm delete</h3>Are you sure?';
		parent.bootbox.confirm(message, function (result) {
			if (result){
				//$el.parents('.question').remove();
				//subquestionsarray.splice(subquestionid-1, 1);
				$el.parents('.question').remove();
				subquestionsarray[subquestionid-1].deletestatus=true;
				//parent.customResizeIframe(30);
				parent.resizeIframe();
			}
		});
  		
  	}
      
  });

});

function startHoverEfects(){
	 $(document).on('mouseover','.each-subqn',function() {
		 $(this).find('.manage-subqn').removeClass('hidden').addClass('visible');
	 });
	 
	 $(document).on('mouseout','.each-subqn',function() {
		 $(this).find('.manage-subqn').removeClass('visible').addClass('hidden');
	 });	
}

function stopHoverEfects(qnid){
	$('#subquestions li').each(function(){
		$(this).removeClass('each-subqn').removeClass('hover-item');
	});
	
	$('#subqnid_'+qnid).addClass('backgroundclr');
	$('#subqnid_'+qnid).find('.manage-subqn').addClass('visible');
}

function restartHoverEfects(){
	$('#subquestions li').each(function(){
		$(this).addClass('each-subqn').addClass('hover-item').removeClass('backgroundclr');
		$(this).find('.manage-subqn').removeClass('visible').addClass('hidden');
	});
	
	$('#subquestionbox').css({"margin-top":"0px"});
}

function showProcessing(divid){
	var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	$(divid).after(html);
}

function hideProcessing(){
	$('#loading').remove();
}