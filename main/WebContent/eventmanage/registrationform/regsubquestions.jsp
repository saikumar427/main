<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css">
<link type="text/css" rel="stylesheet" href="/main/css/questions.css" />

<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/js/jquery-ui-1.9.2.js"></script>
<script src="/main/js/jquery-sortable.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/js/jquery.tooltipster.js"></script>
<script src="/main/js/jquery.validate.min.js"></script>

<script src="/main/bootstrap/js/custom.js"></script>
<script src="/main/js/eventmanage/subquestions.js"></script>
</head>
<body style="padding:20px">
 <div class="row" style="background-color:white" id="subqns_content">
	<div class="form-group pull-right" style="margin:-20px 20px 0px 0px">
		<button class="btn btn-primary" id="addsubquestion">+ Sub Question</button>
        <!-- <button class="btn btn-primary" id="done">Done</button> -->
	</div>
	<div style="clear:both"></div>
	
    	<div class="col-xs-12">
        	<div  style="border-color:#ccc;padding: 20px 0px 20px 0px;" id="subqns">
        	<%-- <div class="alert alert-info">Sub questions for the option "<s:property value="%{optionVal}"/>"</div> --%>
        		<div id='noInfoMsg' class="not-found" style="display:none;">No Sub Questions to display</div>
            	<ul class="list-group example" id="subquestions"></ul>
                <div id='subquestionbox' style="display:none;"></div>
                <div id='tempsubquestionbox' style="display:none;"></div>
            </div>
		</div>
	</div>
	
</body>
<script>
var eid='${eid}';
var optionId='${optionId}';
var subQuestionData='${subQuestionsJsonData}';
var subquestionsarray = [];
var subquestionsinfo = {};
if(subQuestionData!=''){ // data will come from db for attribid and option specific
	subquestionsarray=JSON.parse(subQuestionData);
	subquestionsinfo[''+optionId]=subquestionsarray;
	if(parent.global_eb_condQnLevel=='TicketLevel')
		parent.ticketsubquestionsinfo[''+optionId]=subquestionsarray;
	else
		parent.buyersubquestionsinfo[''+optionId]=subquestionsarray;
	
}else{
	if(parent.global_eb_condQnLevel=='TicketLevel')
		subquestionsinfo=parent.ticketsubquestionsinfo;
	else
		subquestionsinfo=parent.buyersubquestionsinfo;
}
if(subquestionsinfo[optionId]!='' && subquestionsinfo[optionId]!=undefined){
	subquestionsarray=subquestionsinfo[optionId];
$.each(subquestionsinfo[optionId],function(inkey,subquestion){
	subquestionscount+=1;
	if(typeof subquestion.deletestatus=='undefined' || subquestion.deletestatus==false){
	var html = '<li class="question lists hover-item each-subqn" data-subquestionid="'+subquestionscount+'" data-attribid="'+subquestion.attribid+'" id="subqnid_'+subquestionscount+'">' +
			   '<div class="row"><div class="col-xs-9 extramarginques"><img src="/main/bootstrap/img/grippy_large.png" style="top: 2px;" class="dragHandle dragicon">&nbsp;<span class="qname">'+subquestion.name+'</span></div>'+
	'<div class="col-xs-3 extramarginques moveDown links-div"><span class="hidden manage-subqn pull-right">' +
    '<a href="javascript:;" class="editsubquestion">Edit </a>'+
    '<a href="javascript:;" class="deleteSubQuestion">Delete </a></span></div>';
	html += '</div></li>';
	$('#subquestions').append(html);
	}
});
}else{
	$(function() {
		//$('#addsubquestion').trigger('click');
		$("#noInfoMsg").show();
	});
}
$(document).ready(function () {
	 $( "#subquestions" ).sortable({
         handle: '.dragHandle',
         onDrop: function  (item, targetContainer, _super) {
             var clonedItem = $('<li/>').css({height: 0});
             item.before(clonedItem);
             clonedItem.animate({'height': item.height()});
             
             item.animate(clonedItem.position(), function  () {
               	clonedItem.detach();
               	_super(item);
	            $("#addsubquestion").attr("disabled",false);
	     	    $('#addsubquestiontemplate').fadeOut();
	     	    $('#subquestionbox').html('');
     	     	isSubQnSaved=true;
     	     	restartHoverEfects();
     	     	parent.resizeIframe();
     	     	done();
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
	
	/* $('#done').click(function(){
		 var tempsubquestionsarray = [];
		$("ul#subquestions li").each(function(){
			var tempsubqnid=$(this).data('subquestionid');
			tempsubquestionsarray.push(subquestionsarray[tempsubqnid-1]);
		}); 
		$.each(subquestionsarray,function(inkey,subquestion){
			if(subquestion.attribid !='' && subquestion.deletestatus)
				tempsubquestionsarray.push(subquestion);
		});
		parent.subquestionsinfo[''+optionId]=tempsubquestionsarray;
		parent.$('#myModal').modal('hide');
	}); */
	
	 parent.resizeIframe();
}); 

function done(){
	$("#addsubquestion").attr("disabled",false);
	 var tempsubquestionsarray = [];
	$("ul#subquestions li").each(function(){
		var tempsubqnid=$(this).data('subquestionid');
		tempsubquestionsarray.push(subquestionsarray[tempsubqnid-1]);
	}); 
	$.each(subquestionsarray,function(inkey,subquestion){
		if(subquestion.attribid !='' && subquestion.deletestatus)
			tempsubquestionsarray.push(subquestion);
	});
	//parent.subquestionsinfo[''+optionId]=tempsubquestionsarray;
	if(parent.global_eb_condQnLevel=='TicketLevel')
		parent.ticketsubquestionsinfo[''+optionId]=tempsubquestionsarray;
	else
		parent.buyersubquestionsinfo[''+optionId]=tempsubquestionsarray;
	restartHoverEfects();
}
</script>
</html>