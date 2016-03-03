
var j = 0;
var data1;
var data2;
var data3;

function closeBox(blastid) { /* alert(blastid + "in close"); */ 
	$('#createemail').prop("disabled", false);
	
	//$('.well').remove();
	
	$('.tempRow').remove();
	
	$('html, body').animate({scrollTop : $("#createemail").height()}, 1000);
	
	$('#links_' + blastid + ' a').each(function() {
		$(this).css({
			"pointer-events" : "visible",
			"cursor" : "pointer",
			"color" : "#5388C4"
		});
		$(this).prop("disabled", false);
	});
	
	
	
}

var tabView;

var globalEditEmail;
function editEmails(blastid, eventid) {
$('#newEmailBox').html('');
	$('#createemail').prop("disabled",true);
	var htmlProcessing = '<tr id="loading">  <td >  <div class="row"> <center>  <img src="../images/ajax-loader.gif">     </center>      </div>    </td></tr>';
	$('#email_' + blastid).after(htmlProcessing);
	$('.tempRow').remove();
	//$('#newEmailBox').remove();
	$('#links_' + globalEditEmail + ' a').each(function() {
		$(this).css({
			"pointer-events" : "visible",
			"cursor" : "pointer",
			"color" : "#5388C4"
		});
		$(this).prop("disabled", false);
	});
	globalEditEmail = blastid;

	$('<tr class="tempRow"><td><div id="emailEdit"></div></td></tr>').insertAfter($('#email_' + blastid));

	var url = 'EmailAttendees!editEmailAttendee?blastid='+blastid+'&eid='+eventid;
	$('#emailEdit').load(url, function() {
		$(this).css("display", "block").animate({scrollDown : 100 }, 'slow');
		$('emailEdit').fadeIn(slideTime);
		$('#attendeesubmit').val(props.global_save_btn);
		$('#loading').remove();
	});

	$("#links_" + blastid + " a").each(function() {
		$(this).css({
			"pointer-events" : "none",
			"cursor" : "default",
			"color" : "#a5c7ed"
		});
		$(this).prop('disabled', true);
	});
}

function showContent(blastid, eventid) {
	var url = 'EmailAttendees!showEmailContent?blastid='+blastid+'&eid='+eventid;
	$('.modal-title').html(props.globa_preview);
	$('#myModal').on('show.bs.modal', function() {
		$('iframe#popup').attr("src", url);
		$('iframe#popup').css("height", "250px");
	});
	$('#myModal').modal('show');
}

function sendTestMail(blastid, eventid) {
	var url = 'EmailAttendees!sendTestmail?blastid='+blastid+'&eid='+eventid;
	$('.modal-title').html(props.ea_test_mail_hdr);
	$('#myModal').on('show.bs.modal', function() {
		$('iframe#popup').attr("src", url);
		$('iframe#popup').css("height", "230px");
	});
	$('#myModal').modal('show');
}

function deleteMail(blastid, eventid) {
	bootbox.confirm('<h3> '+props.ea_del_email_confirm+'  </h3>'+props.ea_are_u_sure_lbl   , function(agree) {
		if (agree) {
			/*alert("in delete");*/
			var url = 'EmailAttendees!deleteMail?blastid=' + blastid + '&eid='+ eventid;
			$.ajax({
				type : "GET",
				url : url,
				success : function(result) {
					$('#notification-holder').html('');
					$('html, body').animate({scrollTop : $("#notification-holder").height()}, 1000);
					notification(props.ea_email_sel_succ_lbl, "success");
					// console.log(""+JSON.stringify(result));
					/* $('#email_'+blastid).remove(); */
					/*$('#tabl1').dataTable().fnDestroy(false);
					$('#tabl2').dataTable().fnDestroy(false);
					$('#tabl3').dataTable().fnDestroy(false);*/
					generateEmailAtteendeeTable(JSON.parse(result));
				}
			});
		}
	});
}

function selecttoggle(contenttype) {
	if ("wysiwyg" == contenttype) {
		$('#selection_block').addClass("btn-default").removeClass("btn-active");
		$('#textarea_block').addClass('btn-active').removeClass("btn-default");
		$('#text_block').addClass('btn-active').removeClass("btn-default");
		$('#wysiwygboxx').show();
		$('#textboxx').hide();
		$('#wysiwyg').attr('checked', true);
		$('#html').attr('checked', false);
		$('#text').attr('checked', false);
	}

	if ("html" == contenttype) {
		$('#selection_block').addClass("btn-active").removeClass("btn-default");
		$('#textarea_block').addClass('btn-default').removeClass("btn-active");
		$('#text_block').addClass('btn-active').removeClass("btn-default");
		$('#wysiwygboxx').hide();
		$('#textboxx').show();
		$('#wysiwyg').attr('checked', false);
		$('#html').attr('checked', true);
		$('#text').attr('checked', false);
	}

	if ("text" == contenttype) {
		$('#selection_block').addClass('btn-active').removeClass("btn-default");
		$('#textarea_block').addClass('btn-active').removeClass("btn-default");
		$('#text_block').addClass("btn-default").removeClass("btn-active");
		$('#wysiwygboxx').hide();
		$('#textboxx').show();
		$('#wysiwyg').attr('checked', false);
		$('#html').attr('checked', false);
		$('#text').attr('checked', true);
	}
}

function getBlast() {
	var blastId = document.getElementById("scheduleblast").value;
	var eid = document.getElementById("eid").value;
	url = "EmailAttendees!getBlastDetails?blastid="+blastId+"&eid="+eid;
	$.ajax({
		type : "GET",
		url : url,
		success : function(result) {
			if (!isValidActionMessage(result))
				return;
			fillBlastDetails(result);
		}
	});
}

var fillBlastDetails = function(response) {
	var obj = eval('(' + response + ')');
	/* alert(JSON.stringify(obj, undefined,2) ); */
	var powertype = document.getElementById('powertype').value;
	document.getElementById("subject").value = obj.blast.subject;
	var contenttype = obj.blast.contenttype;
	if ("wysiwyg" == contenttype)
		$('#editor').html(obj.blast.content);
	else
		$('#textmsg').val(obj.blast.content);

	selecttoggle(contenttype);
	evt_date = obj.blast.evt_date;
	if (document.getElementById('rlist')) {
		/*
		 * var rlist=document.getElementById('rlist'); alert(rlist+"123");
		 * for(var i=0;i<rlist.options.length;i++) { if
		 * (rlist.options[i].value==evt_date) rlist.options[i].selected = true;
		 * if(evt_date==undefined) rlist.options[0].selected = true; }
		 */
	}

	if (obj.blast.buyer == 'Y') {
		$('#buyer').iCheck('check');
	} else {
		$('#buyer').iCheck('uncheck');
	}
	if (obj.blast.attendee == 'Y') {

		$('#attendee').iCheck('check');
		if (powertype == 'Ticketing') {

			$("#sendto_attendees").slideDown('slow');
			if (obj.blast.attendeestype == 'ALL')
				$('#ALL').iCheck('check');
			else 
			{
				$('#selatt').iCheck('check');
				if (obj.blast.attendeestype == 'TICKET'  ||  (obj.blast.attendeestype == '')) 
				{
					$('#alltickets').iCheck('check');
					//alert(JSON.stringify(obj,undefined,2))
					var tempdata=obj.blast.selectedTkts;
					var result = tempdata.split(",");
					$.each(result,function(index,value){
						
						$('input[value="'+value+'"]').iCheck('check');
						});
				} 
				else if (obj.blast.attendeestype == 'TID')
				{
					$('#tids_type').iCheck('check');
					$('#tid_list').val(obj.blast.selectedTkts);
				}
			}
		}
	} else {
		$('#attendee').iCheck('uncheck');
		if (powertype == 'Ticketing') {
			
			$("#sendto_attendees").hide();
			$("#attendeecheck1").hide();
		}
	}
}

/*function submitForm(event, jsonObj) {
	submitEmail(event, jsonObj, ++j);
}

function submitEmail(event, jsonObj, j) {
	if (j == 1)
		submit(event, jsonObj);
}

function submit(event, jsonObj) {
	var isrecur = document.getElementById('recurring').value;
	var powertype = document.getElementById('powertype').value;
	if (document.getElementById('rlist'))
		var rlist = document.getElementById('rlist').value;
	if (isrecur) {
		if (rlist == '') {
			alert("Please Select Event Date");
			j = 0;
			return;
		}
	}
	var count = 0;
	if (powertype == 'Ticketing') {
		var alltickets = document.getElementById('alltickets').checked;
		var attchk = document.getElementById('attendee').checked;
		if (document.getElementById('tlist')) {
			var obj = document.emailattendeepopupform.chkedTickets;
			if (obj.length == undefined) {
				if (obj.checked == true)
					count++;
			} else {
				for (var i = 0; i < obj.length; i++) {
					if (obj[i].checked == true)
						count++;
				}
			}
			if (attchk == true && alltickets == false && count == 0) {
				document.getElementById('tasktitlebar').scrollTo();
				alert("Please select atleast one ticket");
				
				j = 0;
				return;
			}
		}
	}

	var content = $('#textmsg').val();
	
	var description = replaceSpecialChars(content);
	var finaldescription = convert(description);
	document.getElementById('msg').value = finaldescription;

	$('emailattendeepopupform').request(
			{
				onFailure : function() {

					$('emailerrors').update('Failed to get results')
				},
				onSuccess : function(t) {
					var result = t.responseText;
					if (!isValidActionMessage(t.responseText)) {
						return;
					}
					if (result.indexOf("errorMessage") > -1) {

						$('tasktitlebar').scrollTo();
						$('emailerrors').update(result);
						j = 0;
					} else {
						var statusJson = eval('(' + result + ')');
						var success = statusJson.success;
						if (success.indexOf("success") > -1) {
							window.location.href = 'EmailAttendees?eid='
									+ jsonObj.eid + '&blastschtype='
									+ statusJson.blastschtype;
						}
					}
				}
			});
	return true;
}*/

function replaceSpecialChars(content) {
	var s = content;
	s = s.replace(/[\u2018|\u2019|\u201A]/g, "\'");
	s = s.replace(/[\u201C|\u201D|\u201E]/g, "\"");
	s = s.replace(/\u2026/g, "...");
	s = s.replace(/[\u2013|\u2014]/g, "-");
	s = s.replace(/\u02C6/g, "^");
	s = s.replace(/\u2039/g, "<");
	s = s.replace(/\u203A/g, ">");
	s = s.replace(/[\u02DC|\u00A0]/g, " ");
	return s;
}

/*
 * $('#CheckAll').click(function(event) { $('.tkts').iCheck('check'); });
 * 
 * $('#UnCheckAll').click(function(event) { $('.tkts').iCheck('uncheck'); });
 */
/*
 * function selecteAll(type){ var
 * obj=document.emailattendeepopupform.chkedTickets; if(obj.length==undefined){
 * if(type=='select') obj.checked=true; else obj.checked=false; }else{ for(var
 * i=0;i<obj.length;i++){ if(type=='select') obj[i].checked=true; else
 * obj[i].checked=false; } } }
 */

/*function attendeeCheck() {
	var powertype = document.getElementById('powertype').value;
	if (powertype == 'Ticketing') {
		var attchk = document.getElementById('attendee').checked;
		if (attchk == true) {
			document.getElementById('attendeecheck1').style.display = 'block';
			document.getElementById('attendeecheck2').style.display = 'block';
		} else {
			document.getElementById('attendeecheck1').style.display = 'none';
			document.getElementById('attendeecheck2').style.display = 'none';
		}
	}
}*/

function showhide() {

	$(document).on('mouseover', 'tr', function() {
		$(this).find('.hideThis').show();
	});
	$(document).on('mouseout', 'tr', function() {
		$(this).find('.hideThis').hide();
	});
}

function generateEmailAtteendeeTable(json) {
	var scheduledLn = json.scheduled.length;
	var draftsLn = json.drafts.length;
	var sentLn = json.sent.length;
	/* alert(JSON.stringify(json,undefined,2)); */

	/*alert(JSON.stringify(json.scheduled)+"scheduled");
	alert(JSON.stringify(json.drafts)+"drafts");
	alert(JSON.stringify(json.sent)+"sent");*/

	/*$('#tabl1').empty();
	$('#tabl2').empty();
	$('#tabl3').empty();*/
	
	
	/**/
	
	var tempentry='';
	
	$.each(json.scheduled, function(key, valueobj) {
	   tempentry += '<tr id="email_'+ valueobj.blastid+'">   <td> <div class="row">  <div class="col-md-5"> '+ valueobj.date+'</div>   <div class="col-md-3 sm-font" >'+ valueobj.subject+'</div>'
		
		    + '<div class="col-md-4" > <span  class="hideThis sm-font" style="display:none;">  <a href="javascript:;" onclick=editEmails(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')>  '+props.global_edit_lnk_lbl+' </a> </span>&nbsp;&nbsp;   <span  class="hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=deleteMail(\''+ valueobj.blastid+ '\','+ eid+ ')> '+props.global_delete_lnk_lbl+'  </a></span>&nbsp;&nbsp;    <span class="hideThis sm-font" style="display:none;"><a href="javascript:;" onclick=showContent(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')>  '+props.globa_preview+' </a></span>&nbsp;&nbsp;     <span class="hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=sendTestMail(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')>'+props.global_test_mail_lbl+'</a> </span></div>    </div> </td>  </tr>';
	});
	$('#tabl1').html(tempentry);
	if(json.scheduled.length>5)
	{
	$('#tabl1').after('</br>');
	}
	
	
	tempentry='';
	$.each(json.drafts, function(key, valueobj) {
		 tempentry += '<tr id="email_'+ valueobj.blastid+'">   <td> <div class="row">  <div class="col-md-5"> '+ valueobj.date+'</div>   <div class="col-md-3 sm-font" >'+ valueobj.subject+'</div>'
		           + '<div class="col-md-4" > <span  class="hideThis sm-font" style="display:none;">  <a href="javascript:;" onclick=editEmails(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')> '+props.global_edit_lnk_lbl+'  </a> </span>&nbsp;&nbsp;   <span  class="hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=deleteMail(\''+ valueobj.blastid+ '\','+ eid+ ')> '+props.global_delete_lnk_lbl+' </a></span>&nbsp;&nbsp;    <span class="hideThis sm-font" style="display:none;"><a href="javascript:;" onclick=showContent(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')> '+props.globa_preview+'  </a></span>&nbsp;&nbsp;     <span class="hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=sendTestMail(\''
			+ valueobj.blastid+ '\','+ eid
			+ ')> '+props.global_test_mail_lbl+' </a> </span></div>    </div> </td>  </tr>';
	});
	$('#tabl2').html(tempentry);
	if(json.drafts.length>5)
	{
	$('#tabl2').after('</br>');
	}
	
	
	tempentry='';
	$.each(json.sent, function(key, valueobj) {
		tempentry += '<tr id="email_'+ valueobj.blastid+'">   <td> <div class="row">  <div class="col-md-5"> '+ valueobj.date+'</div>   <div class="col-md-3 sm-font" >'+ valueobj.subject+'</div>'
		
		tempentry += '<div class="col-md-3"></div>   <div class="col-md-1 hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=deleteMail(\''+valueobj.blastid+ '\','+ eid+ ')>  '+props.global_delete_lnk_lbl+'  </a></div>   </div> </td>  </tr> ';
	});
	$('#tabl3').html(tempentry);
	if(json.sent.length>5)
		{
		$('#tabl3').after('</br>');
		}
	
	
	
	
	
	/*$.each(json,function(outkey, resObj) {
	var tab = outkey == "scheduled" ? "tab1": outkey == "drafts" ? "tab2" : "tab3";
	 $('.'+tab+'Count').text(resObj.length); 
	 alert(tab); 

	$.each(resObj,function(inkey, valueobj) {
	var tempentry = '<tr id="email_'+ valueobj.blastid+'">   <td> <div class="row">  <div class="col-md-5"> '+ valueobj.date+'</div>   <div class="col-md-3 sm-font" >'+ valueobj.subject+'</div>';

	if (outkey == 'sent')
	tempentry += '<div class="col-md-3"></div>   <div class="col-md-1 hideThis sm-font" style="display:none;"> <a href="javascript:;" onclick=deleteMail(\''+valueobj.blastid+ '\','+ eid+ ')>Delete</a></div>   </div> </td>  </tr> ';
	else
	tempentry += '<div class="col-md-4" > <span  class="hideThis sm-font" style="display:none;">  <a href="javascript:;" onclick=editEmails(\''
														+ valueobj.blastid+ '\','+ eid
														+ ')>Edit</a> </span>&nbsp;&nbsp;   <span  class="hideThis" style="display:none;"> <a href="javascript:;" onclick=deleteMail(\''+ valueobj.blastid+ '\','+ eid+ ')>Delete</a></span>&nbsp;&nbsp;    <span class="hideThis" style="display:none;"><a href="javascript:;" onclick=showContent(\''
														+ valueobj.blastid+ '\','+ eid
														+ ')>Preview</a></span>&nbsp;&nbsp;     <span class="hideThis" style="display:none;"> <a href="javascript:;" onclick=sendTestMail(\''
														+ valueobj.blastid+ '\','+ eid
														+ ')>Test Mail</a> </span></div>    </div> </td>  </tr>';

	$('#' + tab + ' .table').append(tempentry);});
						
     });  */
						if (scheduledLn==0)
							$('#tabl1').html(
									'<tr><td><span class="not-found">'+props.ea_no_sch_mails+'</span></td></tr>');

						if (draftsLn == 0)
							$('#tabl2').html(
									'<tr><td><span class="not-found">'+props.ea_no_drafts_mail+'</span></td></tr>');

						if (sentLn == 0)
							$('#tabl3').html(
									'<tr><td><span class="not-found">'+props.ea_no_sent_mails+'</span></td></tr>');

					

	showhide();
	
	if(data1)
	$('#tabl1').dataTable().fnDestroy(false);
	if(data2)
		$('#tabl2').dataTable().fnDestroy(false);
	if(data3)
		$('#tabl3').dataTable().fnDestroy(false);
	

	if (json.scheduled.length > 0) {
	 data1=$('#tabl1').dataTable({
			"sPaginationType" : "full_numbers",
			"iDisplayLength" : 5,
			"bLengthChange" : false,
			"bFilter" : false,
			'bAutoWidth' : false,
			"aoColumns" : [null],
			"bFilter" : false
		});
	 
	 $('#tabl1').css("width","");
	}

	if (scheduledLn <= 5) {
		removePagination('tabl1');
	}
	
	//alert(json.drafts.length);

	if (json.drafts.length > 0) {
	data2=$('#tabl2').dataTable({
			 "sPaginationType": "full_numbers",
             "iDisplayLength":5,                
             "bLengthChange": false,
             "bSort" : false,
             "bFilter":false,
             "aoColumns": [null],
             "bFilter" : false
		});
	$('#tabl2').css("width","");
	}

	if (draftsLn <= 5) {
		removePagination('tabl2');
	}

	if (json.sent.length > 0) {
		data3=$('#tabl3').dataTable({
			"sPaginationType" : "full_numbers",
			"iDisplayLength" : 5,
			"bLengthChange" : false,
			'bAutoWidth' : false,
			"bFilter" : false,
			"aoColumns" : [null],
			"bSortable" : false
		});
		$('#tabl3').css("width","");
	}

	if (sentLn <= 5) {
		removePagination('tabl3');
	}
	
	$('#tabl1 thead').remove();
	$('#tabl2 thead').remove();
	$('#tabl3 thead').remove();
	
	
}

function showProcessing(divid) {
	var html = '<div id="loading" >  <center>      <img src="../images/ajax-loader.gif"> </center>                    </div>';
	$('#' + divid).after(html);
}

function hideProcessing() {
	$('#loading').remove();
}
