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
var showHelp = ['whosPromoting', 'whosAttending', 'RSVPList'];	
$(function() {	
	var divtext='',output='',addedItem='';
	var widgets = { 
			tickets: props.pg_widget_name_registration,
			description: props.pg_widget_name_description,
			fbcomments: props.pg_widget_name_facebookcomments,
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
	  

	var addedWidgets = (function() {
		//alert("added::"+JSON.stringify(added));
		return $.extend({}, added);
	})();
	
	/*$(function () {
		$('[data-toggle="tooltip"]').tooltip({'placement': 'top'});
		});*/
	
	$(document).ready(function(){
		$('.dragbox-content').hide();
	
	$(document).on('mouseover','.dragbox',function() {
	$(this).find('.dragbox-content').show();
	});

	$(document).on('mouseout','.dragbox',function() {
	$(this).find('.dragbox-content').hide();
	});
	
	$("div[id^='text_'] .chtitle .layouthelp").hide();
	$("div[id^='photo_'] .chtitle .layouthelp").hide();
	$("div[id^='video_'] .chtitle .layouthelp").hide();
	});
	
	
	

var targetinsert='';

$(document).on('click', '.dropdown-widgets', function(event) {
		event.preventDefault();
				targetdiv = $(this).parent();
		targetinsert=$(this).parent().attr('id');
		targetinsert=targetinsert.replace("addwidget-", "");
		var targetdivid=targetinsert;
		targetinsert=$('#'+ targetinsert +" .insertA");
		$('.addwidget').html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
		dropWidgets(targetdiv,targetdivid);
	});
	
	//get all remaining widgets, prepare dropdown list, and show them
	var dropWidgets = function(targetdiv,targetdivid) {
		output = '<option value="empty">'+props.pg_choosebox_lbl+'</option>';
		//alert(Object.keys(widgets).length+':::'+JSON.stringify(addedWidgets));
		//if(Object.keys(widgets).length==Object.keys(addedWidgets).length)
		//	output += '';
		//else
		
		if(pType=='RSVP')
			delete widgets['whosPromoting'];
		if(isRecurring=='true')
			delete widgets['when'];
		
		
		for(key in widgets){
			if(!addedWidgets.hasOwnProperty(key)){
				output += '<optgroup label="'+props.pg_eventbee_boxes_lbl+'">';
				break;
			}
		}
		
			
		$.each(widgets,function(key,value) {
			if(addedWidgets.hasOwnProperty(key))
				output += '';//'<option value="'+key+'" disabled>(&#x2713;) '+value+'</option>';
			else{
				if(targetdivid=='column-narrow' && (key=='fbcomments' || key=='description'))
					{}
				else{
				output += '<option value="'+key+'">'+value+'</option>';}
			}
			});
		output += '</optgroup>';    
		output += '<optgroup label="'+props.pg_custom_boxes_lbl+'">';
		output += '<option value="text">'+props.pg_new_text_widget+'</option>';
		output += '<option value="photo">'+props.pg_new_photo_widget+'</option>';
		output += '<option value="video">'+props.pg_new_video_widget+'</option>';
		output += '</optgroup>';
		output = '<div class="col-sm-6 col-xs-8" style="margin-top:2px;"><select class="chooseWidget form-control" >'+output+'</select></div>';
		output += '<div class="col-sm-6 col-xs-4" style="margin-top:2px;"><button id="cancelAddWidget" class="btn btn-sm btn-cancel" style="margin-top: 3px; color:#fff;">'+props.pg_cancel_btn_lbl+'</button></div>';
		targetdiv.html(output);
	};
	
	$(document).on('click', '#cancelAddWidget', function(){
		$('.addwidget').html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
		toolTip();
	});
	/*$('#cancelAddWidget').on('click',function() {
		$('.addwidget').html('<a href="#" class="dropdown-widgets">Add a Box</a>');
	});*/

	
		
	$(document).on('change','.chooseWidget',function() { 

		addedItem = $(this).find('option:selected').val();
	
		if((addedItem=='tickets'||addedItem=='description'||addedItem=='fbcomments')&&$(this).parent().parent().hasClass('narrow')) {
			$(targetdiv).html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
			return alert(props.pg_widget_cont_add);
		}
		if($.inArray(addedItem, showHelp)>-1){
			divtext = '<div class="dragbox well" id="'+addedItem+'"><div style="cursor: move;"><span  class="chtitle">'+widgets[addedItem]+'\
			&nbsp;<span class="layouthelp"><i class="fa fa-info-circle" id="tip_'+addedItem+'" style="position: relative; top: 3px;"></i></span>\
			</span><div class="dragbox-content pull-right" style="display:block;">\
			<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="lookandfeel"></i>\
			<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options"></i>\
			<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>\
			<i class="fa fa-eye-slash hideW"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div>\
			<div id="'+addedItem+'_dialog" title="Basic dialog">\
			</div></div>';
		}else{
			divtext = '<div class="dragbox well" id="'+addedItem+'"><div style="cursor: move;"><span  class="chtitle">'+widgets[addedItem]+'\
			&nbsp;</span><div class="dragbox-content pull-right" style="display:block;">\
			<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="lookandfeel"></i>\
			<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options"></i>\
			<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>\
			<i class="fa fa-eye-slash hideW"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div>\
			<div id="'+addedItem+'_dialog" title="'+props.pg_basic_title+'">\
			</div></div>';
		}
		var title = widgets[addedItem];
		
		addedWidgets[addedItem] = widgets[addedItem];
		var tempid=addedItem;
		if(addedItem=='photo'||addedItem=='text'||addedItem=='video' ||$.trim(title)=="") { 
			//title = prompt("Enter title for new "+addedItem+" Box");
			var widgetPopupName = '';
			if(addedItem=='photo')
				widgetPopupName = props.pg_photo_widget_msg3;
			if(addedItem=='text')
				widgetPopupName = props.pg_content_widget_msg1;
			if(addedItem=='video')
				widgetPopupName = props.pg_video_widget_msg2;
			    bootbox.prompt(widgetPopupName, function(title) {  
			    		  if(title == null || title=="") {
							if(""==title) {
								$(targetdiv).html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
								alert(props.pg_title_not_empty_lbl);
								$('.bootbox-input').focus();
								return false;
							}
							$(targetdiv).html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
							return;
						}					
						var id=addedItem+"_"+$.now();
						tempid=id;
						titleschange[id]=title;
						addedWidgets[id] = title;
						divtext = '<div class="dragbox well" id="'+(addedItem+"_"+$.now())+'"><div style="cursor: move;"><span  class="chtitle">'+title+'\
						&nbsp;<span class="layouthelp"></span>\
						</span><div class="dragbox-content pull-right" style="display:block;">\
						<i class="fa fa-paint-brush" data-toggle="tooltip"  title="'+props.pg_look_feel_title+'" id="lookandfeel"></i>\
						<i class="fa fa-cog" data-toggle="tooltip" title="'+props.pg_options_title+'" id="options"></i>\
						<i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>\
						<i class="fa fa-eye-slash hideW"  data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';                             
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
	
	function commonForAllWodgets(tempid,title){
		//alert(JSON.stringify(targetinsert));
		$(targetdiv).html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');		
		$(divtext).hide().insertBefore(targetinsert).slideDown('200',function() {
			window.setTimeout(function (){dynamicOptions(tempid,title);},400);
			
		});
		toolTip();
		
		//IMP  this SHOW or HIDE BUTTON, LOOKnFEEL in ALL Widget 
		$('.hideW').hide();
		$('.fa-paint-brush').hide();
		
		saveDraft();
	}
	
	$(document).on('click', '.hideW', function() {
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
	    //$(this).text()=='Hide'? $(this).text('Show'):$(this).text('Hide');
		  $(this).toggleClass("fa-eye-slash fa-eye");
		var thisdiv = $(this).parent().parent().parent();
		var thisid = $(thisdiv).attr('id');
		var eid=GetURLParameter("eid");
		if($(this).hasClass("fa-eye")){
			 hidewidgets.push(thisid);	
			 
			 $(this).tooltipster('content',props.pg_show_title);
			
		}
		else{
			$(this).tooltipster('content',props.pg_hide_title);
			
			 var index = hidewidgets.indexOf(thisid);
		       if (index > -1) 
		      hidewidgets.splice(index, 1);		       
	       }
		  $.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=hideWidget&eid='+eid,{
				data:JSON.stringify(hidewidgets)}).done(function(response){
				if(response.status=="success") {
					
					toolTip();
					//alert(" sucess");
				} else {
					//alert(" failed");
				}
			});
		
	});
	
	$(document).on('click', '#delete', function() {
		
		var thisdiv = $(this).parent().parent().parent();
		var thisid = $(thisdiv).attr('id');
		//alert(thisid);
		//var decision = false;
		//if(thisid.startsWith('text')||thisid.startsWith('photo')||thisid.startsWith('video')){
			//decision = confirm('This action can not be revert back. do you want to delete this widget?');
		//} else  {
			//decision = true;//confirm('Are you sure you want to delete this widget?'); 
		//}
		
		
		bootbox.confirm(props.pg_widget_delete_lbl, function (result) {	
			if(result){
				saveDraft();
				$(thisdiv).animate({
					"opacity" : "0"
				},200,function() {
					$(thisdiv).wrap('<div/>').parent().slideUp(300,function() {
						try{delete titleschange[thisid];}catch(err){};
						delete addedWidgets[$(thisdiv).attr('id')];
						deleteWidgetOpt(thisid);
						$(thisdiv).remove();
						saveDraft();
						$('.addwidget').html('<a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="'+props.pg_add_new_widget+'"><i class="fa fa-plus"></i></span></a>');
					});
				});
			}
			
        });
		
		/*if(decision) {
			saveDraft();
			$(thisdiv).animate({
				"opacity" : "0"
			},200,function() {
				$(thisdiv).wrap('<div/>').parent().slideUp(300,function() {
					try{delete titleschange[thisid];}catch(err){};
					delete addedWidgets[$(thisdiv).attr('id')];
					deleteWidgetOpt(thisid);
					$(thisdiv).remove();
					saveDraft();
					$('.addwidget').html('<a href="#" class="dropdown-widgets">Add a Box</a>');
				});
			});
		}*/
	});

	var optionsWindow = undefined;
	
	$(document).on('click', '#options', function(){
		var widgetid = $(this).parent().parent().parent().attr('id'); 
		var widgetName = $(this).parent().prev().html();
		if(widgetName.indexOf('&nbsp')>-1)
			widgetName = widgetName.substring(0, widgetName.indexOf('&nbsp;'));
		/*widgetName = widgetName.substring(0, widgetName.indexOf('&nbsp;'));*/
		/*widgetName = widgetName.capitalize();*/
		dynamicOptions(widgetid, widgetName);
		
	
	});
	var dynamicOptions=function(widgetid, widgetName){ 
		var widgettype;
		if(widgetid.startsWith('text')||widgetid.startsWith('photo')||widgetid.startsWith('video'))
		widgettype= 'custom';
		else widgettype= 'factory';
		var eventid = GetURLParameter("eid");
		var url="/main/eventmanage/layoutmanage/WidgetOptions?widgetid="+widgetid+"&eid="+eventid+"&title="+escape(addedWidgets[widgetid])+"&type="+widgettype+"&widget_ref_name="+widgetName+"&action=WidgetOptions";
		$('.modal-title').html('<h4><b>'+props.pg_widget_options_lbl+''+widgetName+'</b></h4>');
		$('#myModalLayout .modal-dialog').addClass('modal-lg');
		$('iframe#popupLayout').attr("src",url);
		$('iframe#popupLayout').css("height","150px");
		$('#myModalLayout').modal('show');
		//$('#myModal').modal('show');
		//optionsWindow = window.open("/main/eventmanage/layoutmanage/WidgetOptions?widgetid="+widgetid+"&eventid="+eventid+"&title="+addedWidgets[widgetid]+"&type="+widgettype,"","width=800,height=600");
		//loadPopup(url);	
	};

	$(optionsWindow).bind('beforeunload',function(){
		alert('window closed');
		return "sdsss";
	});
	
	$(document).on('click', '#lookandfeel', function(){
		var widgetid = $(this).parent().parent().parent().attr('id');
		//alert("widget id is::"+widgetid);
		var widgettype;
		if(widgetid.startsWith('text')||widgetid.startsWith('photo')||widgetid.startsWith('video'))
			widgettype="custom";
		else widgettype="factory";
		var eventid = GetURLParameter("eid");
		//optionsWindow = window.open("/main/eventmanage/layoutmanage/WidgetStyle?widgetid="+widgetid+"&eventid="+eventid+"&title="+addedWidgets[widgetid],"","width=510,height=630");
	var url="/main/eventmanage/layoutmanage/WidgetStyle?widgetid="+widgetid+"&eventid="+eventid+"&title="+addedWidgets[widgetid]+"&action=WidgetStyle";
	
	$('.modal-title').html('<h4>'+props.pg_look_feel_title+'</h4>');
	$('#myModalLayout .modal-dialog').addClass('modal-lg');
	$('iframe#popupLayout').attr("src",url);
	$('iframe#popupLayout').css("height","150px"); 
	$('#myModalLayout').modal('show');
	
	//loadPopup(url);
	
	});

	if (typeof String.prototype.startsWith != 'function') {
		String.prototype.startsWith = function (str) {
			return this.slice(0, str.length) == str;
		};
	}

	var SortableOptions = { 
		init: function() { 
			$('.column').sortable({
				connectWith: '.column',
				handle: 'div',
				cursor: 'move',
				placeholder: 'placeholder',
				forcePlaceholderSize: true,
				opacity: 0.7,
				revert:300,
				start: function(event, ui){ },
				stop: function(event,ui){ saveDraft();},
				receive: function(event, ui) {
					//console.log("item " + ui.item[0].id); // Which item
					//console.log("from " + ui.sender[0].id); // Where it came from
					//console.log("  to " + this.id); // Where the item is dropped
					if ((ui.item[0].id=='tickets'||ui.item[0].id=='description'||ui.item[0].id=='fbcomments') && this.id=='column-narrow') {
						//alert('This widget cannot be moved to sidebar');
						bootbox.confirm(props.pg_widget_cont_move_lbl, function (result) {	});
						ui.sender.sortable('cancel');
					}
				}
			});
		}
	};
	var SortableOptionsSeating = {
			init: function() { 
				$('.column').sortable({
					connectWith: '.column',
					handle: 'div',
					cursor: 'move',
					placeholder: 'placeholder',
					forcePlaceholderSize: true,
					opacity: 0.7,
					revert:300,
					start: function(event, ui){ },
					stop: function(event,ui){ saveDraft();},
					receive: function(event, ui) {
						if ((ui.item[0].id=='description'||ui.item[0].id=='fbcomments') && this.id=='column-narrow') {
							bootbox.confirm(props.pg_widget_cont_move_lbl, function (result) {	});
							ui.sender.sortable('cancel');
						}
						
						if (ui.item[0].id=='tickets' && (this.id=='column-narrow' || this.id=='column-wide')) {
							bootbox.confirm(props.pg_widget_cont_move_lbl, function (result) {	});
							ui.sender.sortable('cancel');
						}
					}
				});
			}	
	};
	
	if(isSeatingEvent){
		//this is for seating event
		SortableOptionsSeating.init();
	}else{
		SortableOptions.init();
	}

	
	
	var getLayout = function() {
		var sortorder = {};
		$('.column').each(function () {
			var itemorder = $(this).sortable('toArray');
				itemorder = itemorder.filter(function(n) {
				return n;
			});
			var columnId = $(this).attr('id');
			var arr = [];
				$.each(itemorder,function(i){
				var itemid = itemorder[i];				
				var obj = '{' + itemid + ':' + '"' + addedWidgets[itemid] + '"' + '}';
				
				arr.push(eval('('+obj+')'));
			});
			sortorder[columnId] = arr;
		});
		sortorder["eventid"]=GetURLParameter("eid");
		sortorder["titles"]=titleschange;
		return sortorder;
	};
	
	// popup start
$(".styleEditor").click(function() {
		var url = '/main/eventmanage/layoutmanage/StyleEditor?eid='+GetURLParameter("eid")+"&action=WidgetStyle";
		$('.modal-title').html('<h4><b>'+props.pg_style_edit_lbl+'</b></h4>');
		$('#myModalLayout .modal-dialog').addClass('modal-lg');
		$('iframe#popupLayout').attr("src",url);
		/*$('iframe#popupLayout').css("height","150px"); */
		$('iframe#popupLayout').css("height","550px");
		$('#myModalLayout').modal('show');
		/*loadPopup(url);
		return false;*/
	});
	
	$(".configureHeaderLink").click(function() {
	
		var url = '/main/eventmanage/layoutmanage/layoutHeaderSettings?eid='+GetURLParameter("eid")+'&action=ChooseHeaderTheme';
		
		// This is for next version this support Choose HeaderStyle & Customize HeaderStyle
		//var url = '/main/eventmanage/layoutmanage/headerThemeSettings?eid='+GetURLParameter("eid")+'&action=ChooseHeaderTheme';
		$('.modal-title').html('<h4>'+props.pg_configure_header_lbl+'</h4>');
		$('#myModalLayout .modal-dialog').addClass('modal-lg');
		$('iframe#popupLayout').attr("src",url);
		$('iframe#popupLayout').css("height","250px"); 
		$('#myModalLayout').modal('show');
		/*var url = '/main/eventmanage/layoutmanage/HeaderSettings?eid='+GetURLParameter("eid");
		loadPopup(url);
		return false;*/
	});
	
	$("div.close").click(function() {
				disablePopup();
	});
	
	var popupStatus = 0;
	
	function loadPopup(url) {
		
		window.scrollTo(0,0);
		if(popupStatus == 0) {
			//alert("alert::"+url);
			$("#toPopup").fadeIn(0500);
			$("#backgroundPopup").css("opacity", "0.7");
			$("#backgroundPopup").fadeIn(0001);
			//$('body').css('overflow','hidden');
			$('#popup_content').find('iframe').attr('src',url);
			document.body.style.overflow = "hidden";
			popupStatus = 1;
		}
	}
	
	disablePopup = function () {
		$('.close').trigger('click');
		if(popupStatus == 1) {
			$("#toPopup").fadeOut("normal");
			$("#backgroundPopup").fadeOut("normal");
			$('body').css('overflow','auto');
			$('#popup_content').find('iframe').attr('src','/main/eventmanage/layoutmanage/processing');
			popupStatus = 0;
		}
	};
	// popup end
	
	 changeTitle=function(id,title){		
		try{
		$("#"+id+" .chtitle").html(title);
		addedWidgets[id]=title;
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
	  
	  var data = getLayout();
		data["stage"]='draft';
		$.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=layout&eid='+eid,{
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


var deleteWidgetOpt =function(id){
	  if(window.Prototype) {
		    delete Object.prototype.toJSON;
		    delete Array.prototype.toJSON;
		    delete Hash.prototype.toJSON;
		    delete String.prototype.toJSON;
		}
	  var eid=GetURLParameter("eid");
	var deleteUrl = '/main/eventmanage/layoutmanage/SaveSettings?type=deleteWOp&eid='+eid+"&widgetid="+id;
	$.ajax({
		type:'POST',
		url:deleteUrl,
		success:function(response){
			if(!isValidActionMessage(response)) return;
			if(response.status=="success")
				alert(response);
		},
		error:function(){
			alert('error');
		}
	});
	  /*$.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=deleteWOp&eid='+eid+"&widgetid="+id,{
				}).done(function(response){
					alert(response);
					if(!isValidActionMessage(response)) return;
			if(response.status=="success") {
				//alert("delete  success");
			} else {
				//alert(" failed");
			}
		});*/
	
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
	
	  $.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=resetLastFinal&eid='+eid,{
				}).done(function(response){
			if(response.status=="success") {
				window.location.href=window.location.href;
			} else {
				//alert(" failed");
			}
		});
	
};
	
$(document).on('click', '#saveLayout', function() {
	$("#errormsg").html('');
	$("#errormsg").hide();
	$('#loadingwait').show();
	if ($("#tickets").length == 0){
		$('html, body').animate({ scrollTop: $("#errormsg").height()}, 1000);
			 $('#errormsg').append('<ul class="errorMessage">  <li><span> '+props.pg_layout_not_saved_lbl+'</span></li> </ul>');
			 $('#errormsg').show();
			return;
		}
	
		  if(window.Prototype) {
			    delete Object.prototype.toJSON;
			    delete Array.prototype.toJSON;
			    delete Hash.prototype.toJSON;
			    delete String.prototype.toJSON;
			}
		var data = getLayout();
		
		data["stage"]='final';
		
		var url = '/main/eventmanage/layoutmanage/SaveSettings?type=layout&data='+convert(JSON.stringify(data))+'&eid='+eid;
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				if(!isValidActionMessage(JSON.stringify(result))) return;
				var statusmsg='';
				if(result.status=="success") {
					$('#notification-holder').html('');
					$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
					$('#statusmsg').html('');
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
		
		
		/*$.getJSON('/main/eventmanage/layoutmanage/SaveSettings?type=layout',{
			data:convert(JSON.stringify(data))
		}).done(function(response){
			var statusmsg='';
			if(response.status=="success") {
				//alert("Layout Saved");
				
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				$('#statusmsg').html('');
				notification(props.pg_layout_saved_msg,"success");
			}else {
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.pg_layout_not_saved_lbl,"warning");
			}
		});*/
	});
	

$(document).on('click', '#eventpagebtn', function(){
		var eid=GetURLParameter("eid");
		window.open('/main/eventmanage/layoutmanage/Event?eventid='+eid, '_blank');
	});

$(document).on('click', '#resetlastbtn', function(){
	bootbox.confirm(props.pg_layout_reset_lbl, function (result){
		if(result){
			resetLastFinal();
		}
	});
		//var cnfrm=confirm('Are you sure to reset last changes ? ');
		//if(cnfrm){resetLastFinal();}
	});

$(document).on('click', '#previewbtn', function(){
		/*var eid=GetURLParameter("eid");
		alert('/main/eventmanage/layoutmanage/Event?eventid='+eid+'&preview=draft', '_blank');*/
		
		var eid=GetURLParameter("eid");
		window.open('/main/eventmanage/layoutmanage/Event?eventid='+eid+'&preview=draft', '_blank');
	});
	
	
	
	
	/*$(window).bind('beforeunload', function() {
		return '>>>>>Before You Go<<<<<<<< \n Your custom message go here';
	});*/
	

	/*window.onbeforeunload = confirmExit;
	function confirmExit()
	{if(isChnage)
		{
	var decision=false;
	var url='/main/eventmanage/layoutmanage/Askpermission';
	 decision = confirm('If you have made any changes to the layout without clicking the Save Layout button, your changes will be lost.  Are you sure you want to exit this page?');
	if(!decision)
	return false;}

	}*/
	
	
	$(document).on('click', '.layouthelp', function(){
		var widgetid = $(this).parent().parent().attr('id');
		var div;
		
		 $(function() {
			 if(widgetid.indexOf("text")>-1)
				 div="text";
			 else if(widgetid.indexOf("photo")>-1)
				 div="photo";
			 else if(widgetid.indexOf("video")>-1)
				 div="video";
			 else div=widgetid;
			 if(document.getElementById(div+"_dialog")){
				 document.getElementById(div+"_dialog").style.display="block";	
				 $( "#"+div+"_dialog" ).dialog({
						position: {
							my: "center center",
							at: "center bottom",
							of: $("#"+widgetid)
						}
					});
			 	}
			 });
	
	});
	
	
});
var fourWidgets = ["addwidget-column-single","addwidget-column-wide","addwidget-column-narrow","addwidget-column-single-bottom"];
function getSingleWidgets(jsonArr,hideWidget,id){
	//alert(hideWidget);
	var html='';
	/*if(jsonArr.length<=0)
		html=html+'<div class="insertA"></div>';*/
	$.each(jsonArr,function(key,Obj){
		$.each(Obj,function(k,val){
			if(k=='tickets'){
				html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;</span><div class="dragbox-content pull-right">'+
			'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
			'title="'+props.pg_options_title+'" id="options"></i></div></div></div>';
				/*html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;<span class="layouthelp">'+
				'<i class="fa fa-info-circle " id="tip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content pull-right">'+
			'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="Look & Feel" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
			'title="Options" id="options"></i></div></div></div>';*/
			}else{
				if($.inArray(k, hideWidget)>-1){
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;'+
					'</span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>'+
				'<i class="fa fa-eye hideW" data-toggle="tooltip" title="'+props.pg_show_title+'" ></i></div></div></div>';
					/*html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;<span class="layouthelp">'+
					'<i class="fa fa-info-circle" id="tip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="Look & Feel" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="Options" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="Delete" id="delete"></i>'+
				'<i class="fa fa-eye" data-toggle="tooltip" title="Show" id="hide"></i></div></div></div>';*/
					
				}else if($.inArray(k,showHelp)>-1){
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;<span class="layouthelp">'+
					'<i class="fa fa-info-circle" id="tip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>'+
				'<i class="fa fa-eye-slash hideW" data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';
					/*html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;<span class="layouthelp">'+
					'<i class="fa fa-info-circle" id="tip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="Look & Feel" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="Options" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="Delete" id="delete"></i>'+
				'<i class="fa fa-eye" data-toggle="tooltip" title="Show" id="hide"></i></div></div></div>';*/
				}else{
					html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;'+
					'</span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="'+props.pg_look_feel_title+'" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="'+props.pg_options_title+'" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="'+props.pg_delete_widget_title+'" id="delete"></i>'+
				'<i class="fa fa-eye-slash hideW" data-toggle="tooltip" title="'+props.pg_hide_title+'" ></i></div></div></div>';
					/*html=html+'<div class="dragbox well" id="'+k+'"><div style="cursor: move;"><span class="chtitle">'+val+'&nbsp;<span class="layouthelp">'+
					'<i class="fa fa-info-circle" id="tip_'+k+'" style="position: relative; top: 3px;"></i></span></span><div class="dragbox-content pull-right">'+
				'<i class="fa fa-paint-brush fa-3" data-toggle="tooltip" title="Look & Feel" id="lookandfeel"></i><i class="fa fa-cog" data-toggle="tooltip"'+
				'title="Options" id="options"></i><i class="fa fa-trash-o" data-toggle="tooltip" title="Delete" id="delete"></i>'+
				'<i class="fa fa-eye-slash" data-toggle="tooltip" title="Hide" id="hide"></i></div></div></div>';*/
				}
				
			}
		});
	});
	html = html+'<div class="insertA"></div>';
	/*html = html+'<div class="insertA"></div><div class="vBox"><span class="text-center openSelect"  style="display:block; margin-top: 3px;"><span class="btn-floating" title="Add a Widget"><i class="fa fa-plus"></i></span></span>';*/
	/*html = html+'<div style="display:none;" class="text-center selectBox"  ><div class="col-sm-11" style="margin-top:5px">'+
	'<select class="form-control sel1" id="sel1"><option>1</option><option>2</option></select>'+
	'</div><div class="col-sm-1 text-left" style="margin-top:12px"><span class="closeThis" ><i class="fa fa-times" ></i></span></div></div></div>';*/
	
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
	toolTip();
	
	//IMP  this SHOW or HIDE BUTTON, LOOKnFEEL in ALL Widget 
	$('.hideW').hide();
	$('.fa-paint-brush').hide();
	$('#tickets #options').hide();
	
});
function toolTip(){
	/*$('.fa-eye, .fa-eye-slash, .fa-trash-o, .fa-cog, .fa-paint-brush').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });*/
	
	$('.btn-floating').tooltipster({
		fixedWidth:'100px',
	    position: 'bottom'
	});
	$('.hideW').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	
	$('.fa-trash-o, .fa-cog, .fa-paint-brush').tooltipster({
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_tickets').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_when').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_attendeezone').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_trackPartner').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_shareBox').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_socialLikes').tooltipster({
		 content:$('<span>'+props.pg_social_like_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_description').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_organizer').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_where').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_whosPromoting').tooltipster({
		 content:$('<span>'+props.pg_whos_promoting_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_whosAttending').tooltipster({
		 content:$('<span>'+props.pg_whos_attending_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_RSVPList').tooltipster({
		 content:$('<span>'+props.pg_rsvp_list_msg+'</span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	$('#tip_fbcomments').tooltipster({
		 content:$('<span></span>'),
	    fixedWidth:'100px',
	    position: 'bottom'
	    });
	
	}

