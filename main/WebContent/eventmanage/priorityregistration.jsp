<style>
.table-responsive {
	overflow-x: unset !important;
}
.hideThis{
	display:none;
	cursor:pointer;
}
tr#removeTR:hover {
  background-color: #FFFFFF !important;
  
}
 #priority_table > tbody > tr:first-child > td {
    border-top:  0px solid #e4e4e4;
    line-height: 1.42857;
    padding: 14px;
    vertical-align: top;
}

#priority_table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {
    border-top: 1px solid #e4e4e4;
    line-height: 1.42857;
    padding: 14px;
    vertical-align: top;
}
</style>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="clist" value="priorityList"></s:set>
<s:hidden value="%{powertype}" id="powertype" />
<s:hidden value="%{currentLevel}" id="currentLevel" />
<%-- <s:property value="%{priorityList}"/> --%>

<s:hidden value="%{fieldcount}" id="myfieldcount"/>


<div class="row sticky-out-button pull-right">
	<!--panel footer goes here-->
	<div class="col-md-12">
	<input type="button" class="btn btn-primary" id="createprioritylist"
		value="+&nbsp;<s:text name="pr.create.priority.list.btn.lbl"/>"
		>
	<!-- <input type="button" class="btn btn-primary" id="createpriorityfields"
		<s:if test="%{fieldexists=='no'}">value="+&nbsp;<s:text name="pr.create.fields.btn.lbl"/>"</s:if>
		<s:else>value="&nbsp;<s:text name="pr.edit.fields.btn.lbl"/>" </s:else>
		value="Create Fields"> -->
		</div>
		<div style="clear:both"></div>
</div>

<div class="white-box">
<div class="row">
<div class="col-md-12">
<div class="table-responsive">
	
	
	<table class="table table-responsive table-hover dataTable no-margin-bottom" id="priority_table" style="table-layout: fixed;">
		<tr id="nodata"><td class=" "><div class="col-md-12 " id="no-data"><span class="not-found"><s:text name="global.loading.msg" /></span></div></td></tr>
	</table>
</div>

	<div class="row">
		<div id="prioritylistbox" class="not-found"></div>
		<div id="loadingimage" style="display: none;">
			<center><img src="/main/images/ajax-loader.gif"></img></center>
		</div>
		<div id="dataDiv" class="table-responsive col-md-12" style="display: block;"></div>
	</div>
</div>
<div style="clear:both"></div>
</div>
</div>



<script src="/main/js/eventmanage/priorityregistration.js"></script>

<script>

	var eid = '${eid}';
	var mgrid = '${mgrid}';
	var json = ${jsonData};
	//alert(JSON.stringify(json,undefined,2));
	function fillTable(jsonData){   
		//console.log(JSON.stringify(jsonData));
		if(jsonData.lists.length == 0){
			$('#no-data').html('<div class="col-md-12 not-found">'+props.pr_no_data_found+'</div>');
			$('#savepriority').prop('disabled', true);
		}else{
			$('#priority_table').html('');
			$.each(jsonData.lists, function(key, valueobj){
				html='';
				//alert(key);
				$.each(valueobj, function(keyVal, value){
					subHtml='';
						var recCount=0;var usedCount=0;
						if(value.recCount!=undefined){
							recCount=value.recCount;
						}
						if(value.usdCount!=undefined){
							usedCount=value.usdCount;
						}
						var field1=value.field1,field2=value.field2;
						html+='<tr id="'+keyVal+'"><td><div class="row">';
						html+='<div class="col-md-2">'+value.listNm+'</div>';
						var recordLabel=props.pr_recrds_count_lbl;
						if(recCount==1) recordLabel=props.pr_recrd_count_lbl;
						html+='<div class="col-md-3 sm-font" style="cursor:pointer;" id="code_'+key+'"><span  class=record data-toggle="code_'+key+'">'+recCount+'&nbsp;'+recordLabel+'</span></div>';
						html+='<div class="col-md-2 sm-font" style="cursor:pointer;" id="codes_'+key+'"><span  class="clickable" data-arrow="'+keyVal+'" onclick="getPrioritList('+keyVal+')">'+usedCount+'&nbsp;'+props.pr_used_count_lbl+'<span class="glyphicon glyphicon-menu-right arrow-gap"></span></span></div>';
						html+='<div class="col=md-4 col-sm-4 col-xs-12 linkDiv linkDiv_'+key+'">';
						html+='<span class="sm-font hideThis" style="margin-right:10px;"><a href="javascript:;" class="editthislist" data-listid="'+keyVal+'" data-key="'+key+'">'+props.global_edit_lnk_lbl+'</a></span>';
						html+='<span class="sm-font hideThis" style="margin-right:10px;"><a href="javascript:;"  class="addrows" data-row="'+key+'" onclick="addOrEditRecords('+keyVal+','+key+','+value.noOfFlds+',\''+field1+'\',\''+field2+'\',\''+recCount+'\')">'+props.pr_add_or_edit_rcrds_lbl+'</a></span>';
						html+='<span class="sm-font hideThis" style="margin-right:10px;"><a href="javascript:;"  onclick="uploadlist('+keyVal+","+value.noOfFlds+","+recCount+')">'+props.pr_upload_popup_rcrds_lbl+'</a></span>';
						//html+='<span class=" sm-font hideThis" style="margin-right:10px;"><a href="javascript:;" onclick="getPrioritList('+keyVal+')">Details</a></span>';
						html+='<span class="sm-font hideThis"><a href="javascript:;" onclick="deletePriority('+keyVal+","+eid+')">'+props.global_delete_lnk_lbl+'</a></span></div>';
						html+='</div>';
						//html+='<div class="sm-font subdiv" id="sub_'+keyVal+'" style="display:none;"></div>';
						//alert(keyVal+"after");
						html+='<div id="loadImg_'+keyVal+'"></div>';
						html+='<div class="" id="edit_'+keyVal+'"></div>';
						html+='<div class="" id="records_'+keyVal+'"></div>';
						html+='<div class="" id="used_'+keyVal+'"></div>';
						html+='<div class="" id="add_'+keyVal+'"></div>';
						html+='<div class="" id="sub_'+keyVal+'" style="display:none; margin-top:10px;"></div>';
						
						html+='</td></tr>';
						//console.log(html);
						//tickets block
						/* subHtml+='<ul>';
						$.each(value.tkts, function(subKey, subValue){
							subHtml+='<li class="li_disc">'+subValue+'</li>';
							
						});
						subHtml+='</ul>';
						$('.table #sub_'+key).html(subHtml); */
				});
				$('#priority_table').append(html);
				
				//$('.table #sub_'+key).append(subHtml);
			});
		}
		
	}
	fillTable(json);
	
	
	
	function rebuildtable(result)
	{    
		try {
			fillTable(JSON.parse(result));
		}
		catch(err) {
		    //alert("parse error");
		}
		
		
	}
	$(document).on('click','.clickable',function(){
		var listid=$(this).data('arrow');
		 if($('#sub_'+listid).is(':visible')){
			 $('#sub_'+listid).slideUp();
				$(this).find('.glyphicon').addClass('original').removeClass('down');
		}else{
			$('#sub_'+listid).slideDown();
			$(this).find('.glyphicon').addClass('down').removeClass('original');
	     } 
	});
	
	
	$(document).on('mouseover','tr',function() {
		$(this).find('.hideThis').show();
		});
	$(document).on('mouseout','tr',function() {
		$(this).find('.hideThis').hide();
		});
	
	
	
		
		
		


	function loadUploadedData() { 
		var htmldata = '<table>';
		for (var i = 0; i < 5; i++)
			htmldata += '<tr><td><input type="text" placeholder="'+props.pr_userid_ph+'" class="firstfield"></td><td><input type="text" placeholder="'+props.pr_pwd_ph+'" class="secondfield" style="display:none"></td><td><a style="cursor:pointer" class="remove"><img width="18px" src="/main/images/delete.png" style="padding-bottom:8px"></a></td></tr>';
		htmldata += '</table>';
		$('#uploadeddata').html(htmldata);
	}


	function saveData() {
		var datasource = $("input[name='datasource']:checked").val();
		var params = {};
		params.eid = eid;
		params.fieldcount = $('#fieldlevel').val();
		//alert(fieldlevel);
		params.firstlbl = $('#firstlabel').val();
		params.secondlbl = $('#secondlabel').val();
		if (datasource == 'rsvp') {
			if ($.trim($('#uploadevt').val()) == '') {
				$('#statusmsg').show().html('event id is empty');
				return flase;
			}
			params.uploadedevent = $.trim($('#uploadevt').val());
			params.uploadtype = 'rsvp';
		} else {
			params.uploadtype = 'upload';
			var jsonformat = '[';
			var jsonArray = new Array();
			var len = $('div#uploadeddata tr').length;
			$('div#uploadeddata tr').each(
					function(index, element) {
						jsonformat += '{"userid":"'
								+ $(this).find(".firstfield").val()
								+ '","password":"'
								+ $(this).find(".secondfield").val() + '"}';
						if (index != len - 1)
							jsonformat += ',';
					});
			jsonformat += ']';
			params.credentials = jsonformat;
		}
		$('#statusmsg').hide().html('');

		/*  new Ajax.Request('PriorityRegistration!uploadRsvpEvent', {
			method: 'get',
			parameters:params,
			onSuccess: function(t){
				 YAHOO.ebee.popup.wait.hide();
				 var result=t.responseText;
				 alert(result);
				 if(result.indexOf('statusMessageBox')>-1){
					$('#StatusMsg').html(result);
					 }
			}
			});  */

		$.ajax({
			url : 'PriorityRegistration!uploadRsvpEvent',
			type : 'POST',
			data : params,
			success : function(result) {
			}
		});

	}

	function changefirstLabel() {
		$('.firstfield').attr("placeholder",
				$("input[name='firstlabel']").val());
	}

	function changesecondLabel() {
		$('.secondfield').attr("placeholder",
				$("input[name='secondlabel']").val());
	}
	
	
	/* function changeLabel(type) {
		var val = document.getElementById('fieldlevel').value;
		if (val == '1') {
			$('#firstlabel').show();
			$('.firstfield').show();
		} else {
			$('#firstlabel').hide();
			$('.firstfield').hide();
		}
	}
	

	function changeLabel(type) {
		var val = document.getElementById('fieldlevel').value;
		if (val == '2') {
			$('#secondlabel').show();
			$('.secondfield').show();
		} else {
			$('#secondlabel').hide();
			$('.secondfield').hide();
		}
	} */

	function hidestatusmessage() {
		$('#StatusMsg').html("");
		$('#StatusMsg').hide();
	}

	function closeFileUploadWindow() {
		//alert("in close");
	}

	$(document).ready(function() {

				$('.uploadtype').iCheck({
					checkboxClass : 'icheckbox_square-grey',
					radioClass : 'iradio_square-grey'
				});

				$(document).on('ifChecked', '.uploadtype', 	function() {
							var type = $(this).val();
							if (type == 'upload') 
							{
								$('#statusmsg').hide().html('');
								url = "../membertasks/FileUpload?listId="
										+ mgrid + "&eid=" + eid;
								$('.modal-title').html('File Upload');
								$('iframe#popup').attr("src", url);
								$('iframe#popup').css("height", "220px");
								$('#myModal').modal('show');

								$('#uploadeddata').show();
							} 
							else 
							{
								$('#uploadeddata').hide();
							}

						});

				$(document).on('click', '.remove', function() {
					$(this).parent().parent().remove();
				});

				$('#uploadevt').keypress(function(e) {
					if (e.which != 8 && e.which != 0 && (e.which<48 || e.which>57))
						return false;
				});

			});
	
	
	
	
</script>




