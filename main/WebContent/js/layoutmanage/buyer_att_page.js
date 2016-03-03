var GetURLParameter = function(sParam) {
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		for (var i = 0; i < sURLVariables.length; i++) {
			var sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] == sParam)
				return sParameterName[1];
		}
	};
	var eid=GetURLParameter("eid");
	var disablePopup = null;
	var isChnage=false;
	var showHelpBuyer = ['whosPromoting', 'whosAttending', 'RSVPList'];
	$(function() {
	var divtextBuyer='',outputBuyer='',addedItemBuyer='';
	var allWidgets = { 
			editprofile: props.pg_widget_name_editprofile,
			description: props.pg_widget_name_description,
			fbcomments: props.pg_widget_name_facebookcomments,
			files : props.pg_widget_name_files,
			mytickets:props.pg_widget_name_mytickets,
			socialLikes: props.pg_widget_name_sociallikes,
			when: props.pg_widget_name_when,
			where: props.pg_widget_name_where,
			organizer: props.pg_widget_name_organizer,
			shareBox: props.pg_widget_name_share,
			whosAttending: props.pg_widget_name_whosattending,
			whosPromoting: props.pg_widget_name_whospromoting,
			RSVPList: props.pg_widget_name_rsvplist,
			trackPartner: props.pg_widget_name_trackpartner
			
			//attendeezone: "Attendee Zone"
	};
	var titleschange={};
	var addedWidgetsBuyer = (function() {
		return $.extend({}, addedBuyer);
	})();
	var targetinsert='';
	$(document).ready(function(){
		//$('.dragbox-content-BA').hide();
	
	$(document).on('mouseover','.dragbox',function() {
	$(this).find('.dragbox-content-BA').show();
	});

	$(document).on('mouseout','.dragbox',function() {
	$(this).find('.dragbox-content-BA').hide();
	});
	
	$("div[id^='text_'] .chtitleBA .layouthelp").hide();
	$("div[id^='photo_'] .chtitleBA .layouthelp").hide();
	$("div[id^='video_'] .chtitleBA .layouthelp").hide();
	});
	$(document).on('click', '.dropdown-widgets-BA', function(event) {
			event.preventDefault();
					targetdiv = $(this).parent();
			targetinsert=$(this).parent().attr('id');
			targetinsert=targetinsert.replace("addwidget-", "");
			var targetdivid=targetinsert;
			targetinsert=$('#'+ targetinsert +" .insertA-BA");
			$('.addwidgetBA').html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
			dropWidgets(targetdiv,targetdivid);
		}); 
	var dropWidgets = function(targetdiv,targetdivid) {
		outputBuyer = '<option value="empty">'+props.pg_choosebox_lbl+'</option>';
		if(pType=='RSVP')
			delete allWidgets['whosPromoting'];
		if(isRecurring=='true')
			delete allWidgets['when'];
		for(key in allWidgets){
			if(!addedWidgetsBuyer.hasOwnProperty(key)){
				outputBuyer += '<optgroup label="'+props.pg_eventbee_boxes_lbl+'">';
				break;
			}
		}
			
		$.each(allWidgets,function(key,value) {
			if(addedWidgetsBuyer.hasOwnProperty(key))
				outputBuyer += '';//'<option value="'+key+'" disabled>(&#x2713;) '+value+'</option>';
			else{
				if(targetdivid=='column-narrow-BA' && (key=='fbcomments' || key=='description'))
					{}
				else{
				outputBuyer += '<option value="'+key+'">'+value+'</option>';}
			}
			});
		outputBuyer += '</optgroup>';    
		outputBuyer += '<optgroup label="'+props.pg_custom_boxes_lbl+'">';
		outputBuyer += '<option value="text">'+props.pg_new_text_widget+'</option>';
		outputBuyer += '<option value="photo">'+props.pg_new_photo_widget+'</option>';
		outputBuyer += '<option value="video">'+props.pg_new_video_widget+'</option>';
		outputBuyer += '</optgroup>';
		outputBuyer = '<div class="col-sm-6 col-xs-8" style="margin-top:2px;"><select class="chooseWidgetDropDown form-control" >'+outputBuyer+'</select></div>';
		outputBuyer += '<div class="col-sm-6 col-xs-4" style="margin-top:2px;"><button id="cancelAddWidgetBA" class="btn btn-sm btn-cancel" style="margin-top: 3px; color:#fff;">'+props.pg_cancel_btn_lbl+'</button></div>';
		targetdiv.html(outputBuyer);
	};
	$(document).on('click', '#cancelAddWidgetBA', function(){
		$('.addwidgetBA').html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
		buyerToolTip();
	});
	$(document).on('change','.chooseWidgetDropDown',function() {
		addedItemBuyer = $(this).find('option:selected').val();
		if((addedItemBuyer=='editprofile'||addedItemBuyer=='description'||addedItemBuyer=='fbcomments')&&$(this).parent().parent().hasClass('narrow')) {
			$(targetdiv).html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
			return alert(props.pg_widget_cont_add);
		}
		if($.inArray(addedItemBuyer, showHelpBuyer)>-1){
			divtextBuyer = '<div class="dragbox well" id="'+addedItemBuyer+'"><div style="cursor: move;"><span  class="chtitleBA">'+allWidgets[addedItemBuyer]+'\
			&nbsp;<span class="layouthelp"><i class="fa fa-info-circle" id="toolTip_'+addedItemBuyer+'" style="position: relative; top: 3px;"></i></span>\
			</span><div class="dragbox-content-BA pull-right" style="display:block;">\
			<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="	" ></i>\
			<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options-BA" data="'+addedItemBuyer+'" data-name="'+allWidgets[addedItemBuyer]+'"></i>\
			<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>\
			<i class="fa fa-eye-slash hideW-BA"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div>\
			<div id="'+addedItemBuyer+'_dialog" title="Basic dialog">\
			</div></div>';
		}else{
			divtextBuyer = '<div class="dragbox well" id="'+addedItemBuyer+'"><div style="cursor: move;"><span  class="chtitleBA">'+allWidgets[addedItemBuyer]+'\
			&nbsp;</span><div class="dragbox-content-BA pull-right" style="display:block;">\
			<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="loolookandfeel-DB"></i>\
			<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options-BA" data="'+addedItemBuyer+'" data-name="'+allWidgets[addedItemBuyer]+'"></i>\
			<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>\
			<i class="fa fa-eye-slash hideW-BA"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div>\
			<div id="'+addedItemBuyer+'_dialog" title="'+props.pg_basic_title+'">\
			</div></div>';
		}
		var title = allWidgets[addedItemBuyer];
		addedWidgetsBuyer[addedItemBuyer] = allWidgets[addedItemBuyer];
		var tempid=addedItemBuyer;
		if(addedItemBuyer=='photo'||addedItemBuyer=='text'||addedItemBuyer=='video' ||$.trim(title)=="") { 
			//title = prompt("Enter title for new "+addedItemBuyer+" Box");
			var widgetPopupName = '';
			if(addedItemBuyer=='photo')
				widgetPopupName = props.pg_photo_widget_msg3;
			if(addedItemBuyer=='text')
				widgetPopupName = props.pg_content_widget_msg1;
			if(addedItemBuyer=='video')
				widgetPopupName = props.pg_video_widget_msg2;
			    bootbox.prompt(widgetPopupName, function(title) {  
			    		  if(title == null || title=="") {
							if(""==title) {
								$(targetdiv).html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
								alert(props.pg_title_not_empty_lbl);
								$('.bootbox-input').focus();
								return false;
							}
							$(targetdiv).html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
							return;
						}					
						var id=addedItemBuyer+"_"+$.now();
						tempid=id;
						titleschange[id]=title;
						addedWidgetsBuyer[id] = title;
						divtextBuyer = '<div class="dragbox well" id="'+(addedItemBuyer+"_"+$.now())+'"><div style="cursor: move;"><span  class="chtitleBA">'+title+'\
						&nbsp;<span class="layouthelp"></span>\
						</span><div class="dragbox-content-BA pull-right" style="display:block;">\
						<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="lookandfeel-DB"></i>\
						<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options-BA" data="'+(addedItemBuyer+"_"+$.now())+'" data-name="'+title+'"></i>\
						<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>\
						<i class="fa fa-eye-slash hideW-BA"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';                             
						commonForAllWodgets(tempid,title);
				 
				});
			    // Text Video Photo Title's should not with spaces
			    $(document).on('change', '.bootbox-form .bootbox-input', function() {
			    	var name = $.trim($('.bootbox-input').val());
			    	if(name==''){$('.bootbox-input').val(''); $('.bootbox-input').focus();}
		    	});
		}
		else
			 commonForAllWodgets(tempid,title);
	});
	$("div.close").click(function() {
		disablePopup();
	});
	var popupStatus_BA = 0;
	disablePopup = function () {
		$('.close').trigger('click');
		if(popupStatus_BA == 1) {
			$("#toPopup").fadeOut("normal");
			$("#backgroundPopup").fadeOut("normal");
			$('body').css('overflow','auto');
			$('#popup_content').find('iframe').attr('src','/main/eventmanage/layoutmanage/processing');
			popupStatus_BA = 0;
		}
	};
	
	$(document).on('click', '#options-BA', function(){
		var widgetid = $(this).attr('data');
		var widgetName = $(this).attr('data-name');
		dynamicOptions(widgetid, widgetName,layout_type);
	});
	var dynamicOptions=function(widgetid, widgetName,layout_type){ 
		var widgettype;
		if(widgetid.startsWith('text')||widgetid.startsWith('photo')||widgetid.startsWith('video'))
		widgettype= 'custom';
		else widgettype= 'factory';
		var eventid = GetURLParameter("eid");
		var url="/main/eventmanage/layoutmanage/BuyerAttOption?widgetid="+widgetid+"&eid="+eventid+"&title="+escape(addedWidgetsBuyer[widgetid])+"&type="+widgettype+"&widget_ref_name="+widgetName+"&action=WidgetOptions&layout_type="+layout_type;
		$('.modal-title').html('<h4><b>'+props.pg_widget_options_lbl+''+widgetName+'</b></h4>');
		$('#myModalLayout .modal-dialog').addClass('modal-lg');
		$('iframe#popupLayout').attr("src",url);
		$('iframe#popupLayout').css("height","150px"); 
		$('#myModalLayout').modal('show');
	};
	
	$(document).on('click','#delete-BA',function(){
		var thisdiv = $(this).parent().parent().parent();
		var thisid = $(thisdiv).attr('id');
		var sortorderCheck = {};
		$('.columnBA').each(function () {
			var itemorder = $(this).sortable('toArray');
				itemorder = itemorder.filter(function(n) {
				return n;
			});
			var columnId = $(this).attr('id');
			columnId = columnId.replace("-BA", "");
			var arr = [];
				$.each(itemorder,function(i){
				var itemid = itemorder[i];				
				var obj = '{' + itemid + ':' + '"' + addedWidgetsBuyer[itemid] + '"' + '}';
				
				arr.push(eval('('+obj+')'));
			});
				sortorderCheck[columnId] = arr;
		});
		var top = sortorderCheck["column-single"].length;
		var left = sortorderCheck["column-wide"].length;
		var right = sortorderCheck["column-narrow"].length;
		var bottom = sortorderCheck["column-single-bottom"].length;
		var widgetcount = top+left+right+bottom;
		console.log(widgetcount);
		if(widgetcount>1){
			bootbox.confirm(props.pg_widget_delete_lbl, function (result) {	
				if(result){
					saveDraft();
					$(thisdiv).animate({
						"opacity" : "0"
					},200,function() {
						$(thisdiv).wrap('<div/>').parent().slideUp(300,function() {
							try{delete titleschange[thisid];}catch(err){};
							delete addedWidgetsBuyer[$(thisdiv).attr('id')];
							deleteWidgetOpt(thisid,layout_type);
							$(thisdiv).remove();
							saveDraft();
							$('.addwidgetBA').html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
						});
					});
				}
	        });
		}else{
			bootbox.confirm("There need to be atleast one widget.", function (result) {});
		}
	});
	
	
	function commonForAllWodgets(tempid,title){
		$(targetdiv).html('<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');		
		$(divtextBuyer).hide().insertBefore(targetinsert).slideDown('200',function() {
			window.setTimeout(function (){dynamicOptions(tempid,title,layout_type);},400);
			
		});
		buyerToolTip();
		
		//IMP  this SHOW or HIDE BUTTON, LOOKnFEEL in ALL Widget 
		
		$('.hideW-BA').hide();
		$('.fa-paint-brush').hide();
		saveDraft();
	}
	
	 changeTitle=function(id,title){		
			try{
			$("#"+id+" .chtitleBA").html(title);
			addedWidgetsBuyer[id]=title;
			}catch(err){}
		};
	var saveDraft =function(){
		isChnage=true;
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
		  
		  var data = getLayoutBuyer();
			data["stage"]='draft';
			data["layout_type"]=layout_type;
			//console.log(JSON.stringify(data));
			$.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=buyerPage&eid='+eid,{
				data:convert(JSON.stringify(data))
			}).done(function(response){
				if(!isValidActionMessage(JSON.stringify(response))) return;
				if(response.status=="success") {
					//alert("draft Layout Saved");
				} else {
					//alert("draft Layou failed");
				}
			});
	};
	var deleteWidgetOpt =function(id,layout_type){
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
		  var eid=GetURLParameter("eid");
		var deleteWidgetUrl = '/main/eventmanage/layoutmanage/SaveSettings?type=deleteBAOptions&eid='+eid+"&widgetid="+id+"&layout_type="+layout_type;
		$.ajax({
			type:'POST',
			url:deleteWidgetUrl,
			success:function(response){
				if(!isValidActionMessage(JSON.stringify(response))) return;
				if(response.status=="success"){
					//alert(response);
				}
			},
			error:function(){
				alert('error');
			}
		});
	};
	
	var resetLastFinal =function(){
		isChnage=false;
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
		  var eid=GetURLParameter("eid");
		  
		  $.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=resetLastFinalBuyer&eid='+eid+'&layout_type='+layout_type,{
					}).done(function(response){
				if(response.status=="success") {
					window.location.href=window.location.href;
				} else {
					//alert(" failed");
				}
			});
		
	};
	
	$(document).on('click', '#saveLayoutBA', function() {
		$('#loadingwait').show();
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
		var data = getLayoutBuyer();
		
		data["stage"]='final';
		data["layout_type"]=layout_type;
		var url = '/main/eventmanage/layoutmanage/SaveSettings?type=buyerPage&data='+convert(JSON.stringify(data))+'&eid='+eid+'&layout_type='+layout_type;
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				if(!isValidActionMessage(JSON.stringify(result))) return;
				var statusmsg='';
				if(result.status=="success") {
					$('#notification-holder').html('');
					$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
					//$('#statusmsg').html('');
					$('#loadingwait').hide();
					notification(props.pg_layout_saved_msg,"success");
				}else{
					$('#notification-holder').html('');
					$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
					$('#loadingwait').hide();
					notification(props.pg_layout_not_saved_lbl,"warning");
				}
			},
			error:function(){
				//alert('error');
			}
		});
		
	});
	/*$('#saveLayoutBA').click(function(){
		var url = '/main/eventmanage/layoutmanage/SaveSettings?type=finalSave&layout_type='+layout_type+'&eid='+eid;
		$.ajax({
			url:url,
			type:'post',
			success:function(result){
				alert('success');
			},
			error:function(){
				alert('error');
			}
		});
	});*/
	$(document).on('click', '#resetLayoutBA', function(){
		bootbox.confirm(props.pg_layout_reset_lbl, function (result){
			if(result){
				resetLastFinal();
			}
		});
	});
	
	
var SortableOptions = { 
		init: function() { 
			$('.columnBA').sortable({
				connectWith: '.columnBA',
				handle: 'div',
				cursor: 'move',
				placeholder: 'placeholder',
				forcePlaceholderSize: true,
				opacity: 0.7,
				revert:300,
				start: function(event, ui){ },
				stop: function(event,ui){ saveDraft();},
				receive: function(event, ui) {
					if ((ui.item[0].id=='editprofile'||ui.item[0].id=='description'||ui.item[0].id=='fbcomments') && this.id=='column-narrow') {
						bootbox.confirm(props.pg_widget_cont_move_lbl, function (result) {	});
						ui.sender.sortable('cancel');
					}
				}
			});
		}
	};
SortableOptions.init();
var isSave=false;
var getLayoutBuyer = function() {
	var sortorder = {};
	$('.columnBA').each(function () {
		var itemorder = $(this).sortable('toArray');
			itemorder = itemorder.filter(function(n) {
			return n;
		});
		var columnId = $(this).attr('id');
		columnId = columnId.replace("-BA", "");
		var arr = [];
			$.each(itemorder,function(i){
			var itemid = itemorder[i];				
			var obj = '{' + itemid + ':' + '"' + addedWidgetsBuyer[itemid] + '"' + '}';
			
			arr.push(eval('('+obj+')'));
		});
		sortorder[columnId] = arr;
	});
	sortorder["eventid"]=GetURLParameter("eid");
	sortorder["titles"]=titleschange;
	return sortorder;
};
});
function getSingleWidgetsBuyer(jsonArr,hideWidget,id){
	var html='';
	$.each(jsonArr,function(key,Obj){
		$.each(Obj,function(k,val){
			if(false){
				html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitleBA">'+val+'&nbsp;</span><div class="dragbox-content-BA pull-right" style="display:none;" >'+
			'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel-DB"></i><i class="fa fa-cog" data-toggle="tooltip"'+
			'title="'+props.pg_options_title+'" id="options-BA" data="'+k+'" data-name="'+val+'"></i></div></div></div>';
			}else{
				if($.inArray(k, hideWidget)>-1){
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitleBA">'+val+'&nbsp;'+
					'</span><div class="dragbox-content-BA pull-right" style="display:none;"  pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel-DB"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options-BA" data="'+k+'" data-name="'+val+'"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>'+
				'<i class="fa fa-eye hideW-BA" data-toggle="tooltip" title="'+props.pg_show_title+'" ></i></div></div></div>';
					
				}else if($.inArray(k,showHelpBuyer)>-1){
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitleBA">'+val+'&nbsp;<span class="layouthelp">'+
					'<i class="fa fa-info-circle" id="toolTip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content-BA pull-right" style="display:none;"  pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel-DB"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options-BA" data="'+k+'" data-name="'+val+'"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>'+
				'<i class="fa fa-eye-slash hideW-BA" data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';
				}else{
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitleBA">'+val+'&nbsp;'+
					'</span><div class="dragbox-content-BA pull-right" style="display:none;"  pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel-DB"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options-BA" data="'+k+'" data-name="'+val+'"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete-BA"></i>'+
				'<i class="fa fa-eye-slash hideW-BA" data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';
				}
				
			}
		});
	});
	html = html+'<div class="insertA-BA"></div>';
	
	$('#'+id).html(html);
}
function convert(text){
	  var chars = ["ÃƒÆ’Ã‚Â²","ÃƒÅ½Ã‚Â´","Ãƒï¿½Ã†â€™","ÃƒÅ½Ã‚Â£","Ãƒï¿½Ã¯Â¿Â½","Ãƒï¿½Ã¢â‚¬Â°","ÃƒÅ½Ã‚Â©","ÃƒÅ½Ã‚Â¨","ÃƒÅ½Ã…Â¸","ÃƒÅ½Ã‚Â¾","ÃƒÅ½Ã…Â¾","ÃƒÅ½Ã‚Â½","ÃƒÅ½Ã‚Â¼","ÃƒÅ½Ã¢â‚¬Âº","ÃƒÅ½Ã‚Âº","ÃƒÅ½Ã‚Â¹","ÃƒÅ½Ã‚Â¸","ÃƒÅ½Ã‹Å“","ÃƒÅ½Ã‚Â·","ÃƒÅ½Ã‚Â¶","Ãƒï¿½Ã¢â€šÂ¬","ÃƒÂ¢Ã‹Å“Ã‚Â»","ÃƒÂ¢Ã‹Å“Ã‚Âº","ÃƒÂ¢Ã¢â€žÂ¢Ã‚Â ","ÃƒÂ¢Ã¢â€žÂ¢Ã‚Â£","ÃƒÂ¢Ã¢â€žÂ¢Ã‚Â¦","ÃƒÂ¢Ã¢â€žÂ¢Ã‚Â¥","Ãƒâ€šÃ‚Â©","ÃƒÆ’Ã¢â‚¬Âº","Ãƒâ€šÃ‚Â®","Ãƒâ€¦Ã‚Â¾","ÃƒÆ’Ã…â€œ","Ãƒâ€¦Ã‚Â¸","ÃƒÆ’Ã¯Â¿Â½","ÃƒÆ’Ã…Â¾","%","Ãƒâ€šÃ‚Â¡","ÃƒÆ’Ã…Â¸","Ãƒâ€šÃ‚Â¢","ÃƒÆ’Ã‚Â ","Ãƒâ€šÃ‚Â£","ÃƒÆ’Ã‚Â¡","ÃƒÆ’Ã¢â€šÂ¬","Ãƒâ€šÃ‚Â¤","ÃƒÆ’Ã‚Â¢","ÃƒÆ’Ã¯Â¿Â½","Ãƒâ€šÃ‚Â¥","ÃƒÆ’Ã‚Â£","ÃƒÆ’Ã¢â‚¬Å¡","Ãƒâ€šÃ‚Â¦","ÃƒÆ’Ã‚Â¤","ÃƒÆ’Ã†â€™","Ãƒâ€šÃ‚Â§","ÃƒÆ’Ã‚Â¥","ÃƒÆ’Ã¢â‚¬Å¾","Ãƒâ€šÃ‚Â¨","ÃƒÆ’Ã‚Â¦","ÃƒÆ’Ã¢â‚¬Â¦","Ãƒâ€šÃ‚Â©","ÃƒÆ’Ã‚Â§","ÃƒÆ’Ã¢â‚¬Â ","Ãƒâ€šÃ‚Âª","ÃƒÆ’Ã‚Â¨","ÃƒÆ’Ã¢â‚¬Â¡","Ãƒâ€šÃ‚Â«","ÃƒÆ’Ã‚Â©","ÃƒÆ’Ã‹â€ ","Ãƒâ€šÃ‚Â¬","ÃƒÆ’Ã‚Âª","ÃƒÆ’Ã¢â‚¬Â°","Ãƒâ€šÃ‚Â­","ÃƒÆ’Ã‚Â«","ÃƒÆ’Ã…Â ","Ãƒâ€šÃ‚Â®","ÃƒÆ’Ã‚Â¬","ÃƒÆ’Ã¢â‚¬Â¹","Ãƒâ€šÃ‚Â¯","ÃƒÆ’Ã‚Â­","ÃƒÆ’Ã…â€™","Ãƒâ€šÃ‚Â°","ÃƒÆ’Ã‚Â®","ÃƒÆ’Ã¯Â¿Â½","Ãƒâ€šÃ‚Â±","ÃƒÆ’Ã‚Â¯","ÃƒÆ’Ã…Â½","Ãƒâ€šÃ‚Â²","ÃƒÆ’Ã‚Â°","ÃƒÆ’Ã¯Â¿Â½","Ãƒâ€šÃ‚Â³","ÃƒÆ’Ã‚Â±","ÃƒÆ’Ã¯Â¿Â½","Ãƒâ€šÃ‚Â´","ÃƒÆ’Ã‚Â²","ÃƒÆ’Ã¢â‚¬Ëœ","Ãƒâ€šÃ‚Âµ","ÃƒÆ’Ã‚Â³","ÃƒÆ’Ã¢â‚¬Â¢","Ãƒâ€šÃ‚Â¶","ÃƒÆ’Ã‚Â´","ÃƒÆ’Ã¢â‚¬â€œ","Ãƒâ€šÃ‚Â·","ÃƒÆ’Ã‚Âµ","ÃƒÆ’Ã‹Å“","Ãƒâ€šÃ‚Â¸","ÃƒÆ’Ã‚Â¶","ÃƒÆ’Ã¢â€žÂ¢","Ãƒâ€šÃ‚Â¹","ÃƒÆ’Ã‚Â·","ÃƒÆ’Ã…Â¡","Ãƒâ€šÃ‚Âº","ÃƒÆ’Ã‚Â¸","ÃƒÆ’Ã¢â‚¬Âº","Ãƒâ€šÃ‚Â»","ÃƒÆ’Ã‚Â¹","ÃƒÆ’Ã…â€œ","@","Ãƒâ€šÃ‚Â¼","ÃƒÆ’Ã‚Âº","ÃƒÆ’Ã¯Â¿Â½","Ãƒâ€šÃ‚Â½","ÃƒÆ’Ã‚Â»","ÃƒÆ’Ã…Â¾","ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬","Ãƒâ€šÃ‚Â¾","ÃƒÆ’Ã‚Â¼","ÃƒÆ’Ã…Â¸","Ãƒâ€šÃ‚Â¿","ÃƒÆ’Ã‚Â½","ÃƒÆ’Ã‚Â ","ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡","ÃƒÆ’Ã¢â€šÂ¬","ÃƒÆ’Ã‚Â¾","ÃƒÆ’Ã‚Â¡","Ãƒâ€ Ã¢â‚¬â„¢","ÃƒÆ’Ã¯Â¿Â½","ÃƒÆ’Ã‚Â¿","ÃƒÆ’Ã‚Â¥","ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¾","ÃƒÆ’Ã¢â‚¬Å¡","ÃƒÆ’Ã‚Â¦","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¦","ÃƒÆ’Ã†â€™","ÃƒÆ’Ã‚Â§","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â ","ÃƒÆ’Ã¢â‚¬Å¾","ÃƒÆ’Ã‚Â¨","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¡","ÃƒÆ’Ã¢â‚¬Â¦","ÃƒÆ’Ã‚Â©","Ãƒâ€¹Ã¢â‚¬Â ","ÃƒÆ’Ã¢â‚¬Â ","ÃƒÆ’Ã‚Âª","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â°","ÃƒÆ’Ã¢â‚¬Â¡","ÃƒÆ’Ã‚Â«","Ãƒâ€¦Ã‚Â ","ÃƒÆ’Ã‹â€ ","ÃƒÆ’Ã‚Â¬","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¹","ÃƒÆ’Ã¢â‚¬Â°","ÃƒÆ’Ã‚Â­","Ãƒâ€¦Ã¢â‚¬â„¢","ÃƒÆ’Ã…Â ","ÃƒÆ’Ã‚Â®","ÃƒÆ’Ã¢â‚¬Â¹","ÃƒÆ’Ã‚Â¯","Ãƒâ€¦Ã‚Â½","ÃƒÆ’Ã…â€™","ÃƒÆ’Ã‚Â°","ÃƒÆ’Ã¯Â¿Â½","ÃƒÆ’Ã‚Â±","ÃƒÆ’Ã…Â½","ÃƒÆ’Ã‚Â²","ÃƒÂ¢Ã¢â€šÂ¬Ã‹Å“","ÃƒÆ’Ã¯Â¿Â½","ÃƒÆ’Ã‚Â³","ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢","ÃƒÆ’Ã¯Â¿Â½","ÃƒÆ’Ã‚Â´","ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œ","ÃƒÆ’Ã¢â‚¬Ëœ","ÃƒÆ’Ã‚Âµ","ÃƒÂ¢Ã¢â€šÂ¬Ã¯Â¿Â½","ÃƒÆ’Ã¢â‚¬â„¢","ÃƒÆ’Ã‚Â¶","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¢","ÃƒÆ’Ã¢â‚¬Å“","ÃƒÆ’Ã‚Â¸","ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Å“","ÃƒÆ’Ã¢â‚¬ï¿½","ÃƒÆ’Ã‚Â¹","ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬ï¿½","ÃƒÆ’Ã¢â‚¬Â¢","ÃƒÆ’Ã‚Âº","Ãƒâ€¹Ã…â€œ","ÃƒÆ’Ã¢â‚¬â€œ","ÃƒÆ’Ã‚Â»","ÃƒÂ¢Ã¢â‚¬Å¾Ã‚Â¢","ÃƒÆ’Ã¢â‚¬â€�","ÃƒÆ’Ã‚Â½","Ãƒâ€¦Ã‚Â¡","ÃƒÆ’Ã‹Å“","ÃƒÆ’Ã‚Â¾","ÃƒÂ¢Ã¢â€šÂ¬Ã‚Âº","ÃƒÆ’Ã¢â€žÂ¢","ÃƒÆ’Ã‚Â¿","Ãƒâ€¦Ã¢â‚¬Å“","ÃƒÆ’Ã…Â¡"];
	  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
	  for(var x=0; x<chars.length; x++){
	  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
	  }
	  return text;
	  }
$(document).ready(function(){
	
	setTimeout(function(){
		buyerToolTip();
		$('.hideW-BA').hide();
		$('.fa-paint-brush').hide();
	}, 50);
	
});
function buyerToolTip(){
	/*$('.fa-eye, .fa-eye-slash, .fa-trash-o, .fa-cog, .fa-paint-brush').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });*/
	
	$('.btn-floating-BA').tooltipster({
		fixedWidth:'100px',
	    position: 'bottom'
	});
	$('.hideW-BA').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	
	$('.fa-trash-o, .fa-cog, .fa-paint-brush').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });

	$('#toolTip_editprofile').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_when').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_attendeezone').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_trackPartner').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_shareBox').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_socialLikes').tooltipster({
		 content:$('<span>'+props.pg_social_like_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_description').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_organizer').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_where').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_whosPromoting').tooltipster({
		 content:$('<span>'+props.pg_whos_promoting_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_whosAttending').tooltipster({
		 content:$('<span>'+props.pg_whos_attending_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_RSVPList').tooltipster({
		 content:$('<span>'+props.pg_rsvp_list_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#toolTip_fbcomments').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	}