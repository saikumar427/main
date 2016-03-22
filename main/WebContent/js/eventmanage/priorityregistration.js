var fields='';      
//var currentlistid='';
var global_listid='';
var uploadlists=false;
$(document).ready(function(){
	
	$(document).on('click','#createprioritylist',function(){
	var curLevel= $('#currentLevel').val();
		if(curLevel==400){
			$('#nodata').hide();
		//$('#priority_table').html('');
		createnewPriorityList();
		setTimeout(function(){
			$('#listcount').remove();	
		},500);
		}else{
			specialFee(eid,"400","PriorityRegistration","Ticketing");
		}
	});
	function showLinks(listId){
		$('.linkDiv_'+listId+' a').each(function(){
			$(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
		        $(this).prop("disabled",false);
		});
		
	}
	
	

	$(document).on('click','#savepriority',function(){
		fields=$('#fieldlevel').val();
		var listId=$('#listid').val();
		savePriorityList(listId);
		
		/*$('#removeTR').remove();
		$('#dataDiv').html('');
		
		if(flag){
			$('#loadEditDiv').show();
		}else{
			$('#loadEditDiv').html('');
			$('#loadEditDiv').hide();
		}*/
		
		
		 	
	});
	
	
	
	
	/*$(document).on('click','#savepriorityfields',function(){
		 fields=$('#fieldlevel').val();
		savePriorityFields();
	});*/
	
	
	$(document).on('click','#closepriority',function(){
		//alert('vbdxgbds,jgv........');
		
		var count=$('#priority_table tr').length;
		//alert('count: '+count);
		if(count==1 && $('#nodata').length!=0)
			{
			//alert('in count');
			//$('#no-data').html('<div class="col-md-12 not-found">'+props.pr_no_data_found+'</div>');
			$("#nodata").show();
			}
		$('#dataDiv').html('');
		$('#loadEditDiv').html('');
		$('#loadEditDiv').hide();
		showLinks(globalVar);
		$('#removeTR').remove();
		
	});
	
	$(document).on('click','.editthislist',function(){
		var listId = $(this).data('listid');
		var keyId = $(this).data('key');
		hideLinks(keyId);
		editPriorityList(listId);
		slideUpDetails(listId);
		/*$('#sub_'+listId).slideUp();
		$(this).find('.glyphicon').addClass('original').removeClass('down');*/
		/*setTimeout(function(){
			fillEditListDetails(listId);	
		},500);*/
	});
	
	$(document).on('click','.addrows',function(){
		var rowId=$(this).data('row');
		//alert(rowId);
		hideLinks(rowId);
		//console.log(hideLinks(rowId));
		addOrEditRecords(listId,rowId,noOfFlds,filed1,field2,recCount);
		
	});
	
	

var globalVar='';
function hideLinks(id){
	$('.linkDiv_'+globalVar+' a').each(function(){
		$(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
	        $(this).prop("disabled",false);
	});
	globalVar = id;
	$('.linkDiv_'+globalVar+' a').each(function(){
		  $(this).css({"pointer-events": "none","cursor": "default","color":"#a5c7ed"});
	        $(this).prop('disabled',true);
	});
}

var globalrow='';
function hideLinksrow(ids){
	$('.linkDiv_'+globalrow+' a').each(function(){
		$(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
	        $(this).prop("disabled",false);
	});
	globalrow = ids;
	$('.linkDiv_'+globalrow+' a').each(function(){
		  $(this).css({"pointer-events": "none","cursor": "default","color":"#a5c7ed"});
	        $(this).prop('disabled',true);
	});
}
});

function slideUpDetails(listId){
	$('.clickable').each(function() {
		var divid= $(this).data('arrow');
        if($('#sub_'+divid).is(":visible") && listId==divid){
        	$('#sub_'+divid).slideUp();
			$(this).find('.glyphicon').addClass('original').removeClass('down');
        }
    });
}


/*function createPriorityFields(){
	
	$('#loadingimage').show();
	 var url='PriorityRegistration!createPriorityFields?eid='+eid;
		 $.ajax({url:url})
        .done(function(result){
       	 if(!isValidActionMessage(result)) return;
       	$('#prioritylistbox').hide();
       	 $('#loadingimage').hide();
       	 $("#dataDiv").html(result);
            $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow'); 
        })
        .complete(function(){});
}*/


function createnewPriorityList(){
	//alert('the createnew prioritylist in the old list');
	$('#dataDiv').html('');
	$('#removeTR').remove();
	$('#loadingimage').show();
	var url='PriorityRegistration!createPriorityList?eid='+eid;
	$.ajax({url:url})
        .done(function(result){
       	 if(!isValidActionMessage(result)) return;
       	 //console.log(result);
       	 $('#loadingimage').hide();
       	 $("#dataDiv").html(result);
       	 //$('#listid').val('');
            $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow'); 
           $('.tktsapplicable').iCheck({
           	 checkboxClass: 'icheckbox_square-grey', 
           	 radioClass: 'iradio_square-grey' 
           });
           $('.tktsapplicable').iCheck('uncheck');
        })
        .complete(function(){});
}

function editPriorityList(listId){
	//alert('in new createnewprioritylistid');
	//$('[class^=deatils]').slideUp();
	$('#removeTR').remove();
	$('#dataDiv').html('');
	showProcessing('loadImg_'+listId);
	//$('#loadingimage').show();
	var eidDiv = '<tr id="removeTR"><td><div class="" id="loadEditDiv" ></div></td></tr>';
	var url='PriorityRegistration!getEditPriorityListData?eid='+eid+'&listId='+listId;
	$.ajax({url:url})
         .done(function(result){
        	 if(!isValidActionMessage(result)) return;
        	// alert(result);
        	// $('#loadingimage').hide();
        	 hideProcessing(); 
        	 $("#"+listId).after(eidDiv);
        	 $("#loadEditDiv").html(result);
        	 //$('#listid').val('');
             $('#dataDiv').css("display","block").animate({scrollDown:100}, 'slow');
            // notification("List Updated successfully..","success");
             /*$('.tktsapplicable').iCheck({
            	 checkboxClass: 'icheckbox_square-grey', 
            	 radioClass: 'iradio_square-grey' 
            });*/
             //$('.tktsapplicable').iCheck('uncheck');
         })  
         .complete(function(){
        	 $('#savepriority').html(props.global_save_btn);
         });
		
}

/*function fillEditListDetails(listId){
	var url='PriorityRegistration!getEditPriorityListData?eid='+eid+'&listId='+listId;
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			result=JSON.parse(result);
			$('#listname').val(result.listname);
			$('#firstlbl').val(result.field1);
			$('#secondlbl').val(result.field2);
			$('#listid').val(result.listid);
			console.log(result.field1);
			console.log(result.field2);
			
			var tickets=result.tickets.split(',');
			
			$('#fieldlevel').val(result.nooffields);
			if(Number(result.nooffields)==2)
				$('#secondlabel').show();

			$('.tktsapplicable').iCheck('uncheck');
			$.each(tickets,function(key,value){
				$('input[value="'+value+'"]').iCheck('check');
			});
		}
	});

	}*/

function getPrioritList(listid){
	//$('[class^=deatils]').slideUp();
	//$('#sub_'+listid).toggle();
	//var listid=$('#prioritylist').val();
	//$('#dataDiv').html('');
	if(listid==''){
		$('#prioritylistbox').html('');
		$('#editlink').hide();
	}else{
		var url='PriorityRegistration!getPriorityListData?eid='+eid+'&listId='+listid;
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				//alert(result);
				$('#editlink').show();
				result=JSON.parse(result);
				generatePriorityListTable(result,listid);
				
			}
		});
	}
}

function generatePriorityListTable(result,listid){
    if(result.status=="false")
        {
        var data='<table class="table background-options" style="margin:0px 0px 20px 0px !important;" id="discountsData" ><tr><td style="padding:14px !important;"><span class="not-found">'+props.pr_no_reg_msg_lbl+'</span></td></tr></table>';
        $('#sub_'+listid).html(data);
        }
    else
        {
        var data='<table class="table background-options" style="margin:0px 0px 20px 0px !important;" id="discountsData" ><thead><th>'+result.fieldname+'</th><th>'+props.global_status_lbl+'</th></thead><tbody>';
        $(result.listdata).each(function (keys,values){
        data+='<tr><td style="padding:14px !important;">'+values.id+'</td><td style="padding:14px !important;">'+values.status+'</td></tr>';
        });
        data+='</tbody></table>';
        $('#sub_'+listid).html(data);
        }
}
var recordlist=true;
function addOrEditRecords(listId,rowId,noOfFlds,filed1,field2,recCount){
	slideUpDetails(listId);
	$('#removeTR').remove();
	showProcessing('loadImg_'+listId);
	var eidDiv = '<tr id="removeTR"><td><div class="" id="addrowdiv" ></div></td></tr>';
	 var url='PriorityRegistration!addOrEditRecords?eid='+eid+'&listId='+listId+'&noOfFields='+noOfFlds + '&t=' + Date.now();
		 $.ajax({url:url})
         .done(function(result){
        	 if(!isValidActionMessage(result)) return;
        	 hideProcessing(); 
        	 $("#"+listId).after(eidDiv);
        	
        	
        	// alert('hi:::'+temp+$('#fieldnames'));
        	
        	 $("#addrowdiv").html(result);
         })
     
         .complete(function(){
        	 /*var temp='<tr><td>'+filed1+'</td>';
        	 if(noOfFlds==2)
        		 temp+='<td>'+field2+'</td>';
        	 temp+='</tr>';
        	 $("#fieldnames").html(temp);*/
        	 
        	 $('#readonlyfield1').val(filed1);
        	 $('#readonlyfield2').val(field2);
        	 
         });
		 
		 if(recCount==0){
			recordlist=false;
		}
		
}

function uploadlist(listId,priFldCount,recCount){
	//alert(priFldCount);
	global_listid=listId;
	var	url="../membertasks/FileUpload?listId="+listId+"&eid="+eid+"&purpose=priority&priFldCount="+priFldCount;
		$('.modal-title').html('<b>'+props.pr_file_upld_lbl+'</b>');
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","114px"); 
		$('#myModal').modal('show');
		if(recCount==0){
			uploadlists=true;
		}
}

function setFilePath(ext){
	$('[class^=deatils]').slideUp();
	$('#removeTR').html('');
	//alert('mgrid: '+mgrid);
	var eidDiv = '<tr id="removeTR"><td><div class="" id="addrowdiv" ></div></td></tr>';
	var url='PriorityRegistration!saveXlformat?eid='+eid+'&mgrid='+mgrid+'&listId='+global_listid+"&ext="+ext;
	$.ajax({
	url:url,
	type:'POST',
	success:function(result){
	//result=JSON.parse(result);
		//alert(result);
		
	//console.log(result);
	hideProcessing(); 
	$("#"+global_listid).after(eidDiv);
	$("#addrowdiv").html(result);
	}
});
	closepopup();
}

$(document).on('click','#addrowsdata',function(){
	//alert("hiiiiiiiiiii add rows");
	$('#dataDiv').html('');
	var count=0;
//alert(count);
	$('table#listfiles tr').not(':first').each(function(index, element){
		if($(this).find(".firstfield").val()=='' || $(this).find(".secondfield").val()=='') {
			count++;
			if($(this).find(".firstfield").val()=='') {
				$(this).find(".firstfield").focus();
			} else {
				$(this).find(".secondfield").focus();
			}
			return false;
		}
	});
	if(count==0){
	
	var jsonformat='[';
	var len=$('table#listfiles tr').length;
	$('table#listfiles tr').not(':first').each(function(index, element){
		//alert($(this).find(".firstfield").val());
		jsonformat+='{"userid":"'+$(this).find(".firstfield").val()+'","password":"'+$(this).find(".secondfield").val()+'"}';
		if (index!=len - 1) 
			jsonformat+=',';
	});
	jsonformat+=']';
	var params=new Object();
	params.eid=eid;
	params.listId=$('#listid').val();
	params.credentials=jsonformat;
	var url='PriorityRegistration!savePriorityListRecords';
	$.ajax({
			url:url,
			type:'POST',
			data:params,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				var resp=JSON.parse(result);
				//console.log(resp);
				if(resp.status=="success"){
					$('#dataDiv').html('');
					$('#loadEditDiv').html('');
					$('#loadEditDiv').hide();
					$('#removeTR').remove();
					rebuildtable(result);
					if(uploadlists==true){
						notification(props.pr_rec_uplded_scess_lbl,"success");
						$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
						uploadlists=false;
						showLinks(globalVar);
					}
					else if(recordlist==false){
						notification(props.pr_rec_add_success_lbl,"success");//"Records added successfully"
						$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
						recordlist=true;
						showLinks(globalVar);
					}
					else if(recordlist==true && uploadlists==false){
						notification(props.pr_rec_updtd_success_lbl,"success");//"Records updated successfully"
						$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
						showLinks(globalVar);
					}
				}else if(resp.status=="failure"){
					//console.log("we failed");
					$('#priorityerrors').show();
					$('#priorityerrors').html(props.pr_dup_rec_exists_lbl);
					$('html, body').animate({ scrollTop: $("#priority_table").offset().top-100}, 1000);
				}
			}
		});
	}else{
		$('#priorityerrors').show();
		$('#priorityerrors').html(props.pr_plz_entr_val_txt_feld_lbl);
		$('html, body').animate({ scrollTop: $("#priority_table").offset().top-100}, 1000);
		
	}
});


var checklist=true;
function savePriorityList(listId)
{
	//alert('entered the list....'+$('#firstlbl').val());
	//var listId = currentlistid;
	var listname=$.trim($('#listname').val());
	$('#listname').val(listname);
	var flag=false;
	$(priorityerrors).html('');
	$(priorityerrors).hide();	
	
	$('#priorityerrors').hide();
	var selectedTkts=new Array();
	var params=new Object();
	
	
		params.eid=eid;
		params.noOfFields=$('#fieldlevel').val();
		params.firstLabel=$('#firstlbl').val();
		//params.listId=$('#listid').val();
		params.secondLabel=$('#secondlbl').val();
		params.listName=$('#listname').val();
		params.listId=listId;
		params.deleteold=false;
		params.seltickets=selectedTkts.join();
	if(listname==''){
		flag=true;
		$('#priorityerrors').append('<ul><li><span>'+props.pr_plz_entr_list_name_lbl+'</span></li> </ul>');
	}
	if(params.noOfFields==2){
	if(params.firstLabel==''||params.secondLabel=='')
		{
		flag=true;
		$('#priorityerrors').append('<ul><li><span>'+props.pr_plz_entr_feld_val_lbl+'</span></li> </ul>');
		}
	}else{
		if(params.firstLabel=='')
		{
		flag=true;
		$('#priorityerrors').append('<ul><li><span>'+props.pr_plz_entr_feld_val_lbl+'</span></li> </ul>');
		}
	}
	
	if(!$("#specific-check").is(":checked")){
		$('.tktsapplicable').iCheck('check');
	}
	
	$('.tktsapplicable').each(function(){
		if($(this).is(":checked"))
			selectedTkts.push($(this).val());
	});
	
	if(selectedTkts.length<=0){
		flag=true;
		$('#priorityerrors').append('<ul><li><span>'+props.pr_plz_slct_atlst_one_tkt_lbl+'</span></li> </ul>');
	}
	
	/*console.log(nooffieldss+"::old");
	console.log(params.listId+"::iddd");
	console.log(params.noOfFields+"::new");*/
	
	
	
	if(flag){
		$('#priorityerrors').show();
		//$('html, body').animate({ scrollTop: $('#errormsg').offset().top-200 },1000); 
	}
	else
		{
		
		if(params.listId!="" && params.listId!=null)
			{
			checklist=false;
			//console.log("we are in edit");
			var accepted=false;
			if(params.noOfFields!=nooffieldss)
				{ 
				//console.log("user change dropdown");
					bootbox.confirm("Do you want to change number of fields?",function(result)
						{
						if(result){
						params.deleteold=true;
						params.seltickets=selectedTkts.join();
						savelistdata(params);
						}
						else 
							return;
						}
					);
				}	
			else
				{
				//console.log("user didnot changed");
				params.seltickets=selectedTkts.join();
				savelistdata(params);
				}
			
			}
		else{
			params.seltickets=selectedTkts.join();
			savelistdata(params);
		}			
	}
	
}



function savelistdata(params)
{
	//console.log("everything is correct");
	//params.seltickets=selectedTkts.join();
	var url='PriorityRegistration!savePriorityList';
	$.ajax({
		url:url,
		type:'POST',
		data:params,
		success:function(result)
		{
			if(!isValidActionMessage(result)) return;
			
			//console.log(JSON.parse(result).status);
			if(JSON.parse(result).status!="failure")
			{
				//console.log("in success");
			$('#priority_table').html('');
			$('#dataDiv').html('');
	   	 	$('#loadEditDiv').html('<td style="border-top:0px !important;">'+result+'</td>');
			rebuildtable(result);
			$('#notification-holder').html('');
           $('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
            
			if(checklist==true){
				
				notification(props.pr_list_add_sucesfly_lbl,"success");
				$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
				showLinks(globalVar);
			}
            else{
            	notification(props.pr_lst_updtd_sucesfly_lbl,"success");
				$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
            	checklist=true;
            	showLinks(globalVar);
            	
            }
            $('#removeTR').remove();
    		$('#dataDiv').html('');
    		$('#loadEditDiv').show();
			}
			else
				{
				
				//console.log("duplicate records exists");
				$('#priorityerrors').show();
				$('#priorityerrors').html(props.pr_lst_name_alrdy_exists_lbl);
				$('html, body').animate({ scrollTop: $("#notification-holder").offset().top-200}, 1000);
	           $('#loadEditDiv').html('');
				$('#loadEditDiv').hide();
				}
		}
	});	

}
	




/*function savePriorityFields(){

	$.ajax({
		type:'POST',
		url:'PriorityRegistration!createPriorityList',
		data:$('#prioritylisting').serialize(),
		success:function(result){
			//$('#priorityerror').hide();
			$('#dataDiv').html('');
			$('#createpriorityfields').val('Edit Fields');
			$('#createprioritylist').attr('disabled',false);
			$('#notification-holder').html('');
           $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
             notification("fields saved successfully..","success");
            
		}
	
	});
}*/

function deletePriority(listid,eid){
	var url='PriorityRegistration!deletePriorityList?listId='+listid+'&eid='+eid;
	//alert("in delete");
	bootbox.confirm(props.pr_sure_del_msg_lbl, function(result) {
		if(result){
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				var temp=JSON.parse(result);
				 if(!isValidActionMessage(result)) return;
				
				
				 $('#notification-holder').html('');
                $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
                notification(props.pr_lst_deltd_succfly_lbl,"success");
               // removeNotificationHeader();
                
                $('#priority_table').html('');
				//$('.table #sub_'+key).html('');
				//console.log(temp.lists.length);
                
				if(temp.lists.length=="0")
					{
					//<tr id="nodata"><td class=" "><div class="col-md-12 " id="no-data"><span class="not-found"><s:text name="global.loading.msg" /></span></div></td></tr>
					$('#priority_table').html('<tr id="nodata"><td class=" "><div class="col-md-12 " id="no-data"><span class="not-found"><s:text name="global.loading.msg" /></span></div></td></tr>');
					//$('#priority_table').show();
					//$("#nodata").show();
					}
				rebuildtable(result);
				
			
			}
		});
	}	
});
}

function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}


$(document).on('click', '.removelink', function() {
	var count = $('.removelink').length;
	//alert(count);
	if(count == 2)
		$('.removelink').css('visibility','hidden');
	//return false;
	$(this).parent().parent().remove();
	return true;
});
