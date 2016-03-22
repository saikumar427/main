<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<meta name="verify-v1"
	content="/2obWcBcvVqISVfmAFOvfTgJIfdxFfmMOe+TE8pbDJg=" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META Http-Equiv="Cache-Control" Content="no-cache">
<META Http-Equiv="Pragma" Content="no-cache">
<META Http-Equiv="Expires" Content="0">
<html>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/jquery.datetimepicker.js"></script>
<link rel="stylesheet" type="text/css" 	href="/main/bootstrap/css/jquery.datetimepicker.css" />
<script src="/main/js/jquery.tooltipster.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>

<link rel="stylesheet" type="text/css" href="/main/bootstrap/css/jquery.datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="/main/css/tooltipster.css">
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<head>
<style>
.modal-header {
	border-bottom: 0px !important;
	color: #999 !important;
}

/* tooltip custom theme */
.form-validate-theme {
	border-radius: 5px;
	border: 2px solid #ff4747;
	background: #fff4f4;
	color: #000;
}

.form-validate-theme .tooltipster-content {
	/*font-family: Tahoma, sans-serif;*/
	width: 100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
}
</style>

</head>
<body style="padding-bottom:150px">
	<div id="copyeventerrormessage" style="display: none"></div>
	<s:form name="copyevent" id="copyevent" action="CopyEvent!copyEvent"
		cssClass="form-horizontal">
		<s:hidden id="startmth" name="addEventData.startmonth"></s:hidden>
		<s:hidden id="startyr" name="addEventData.startyear"></s:hidden>
		<s:hidden id="startday" name="addEventData.startday"></s:hidden>
		<s:hidden id="endmth" name="addEventData.endmonth"></s:hidden>
		<s:hidden id="endyr" name="addEventData.endyear"></s:hidden>
		<s:hidden id="endday" name="addEventData.endday"></s:hidden>


		<s:hidden id="endampm" name="addEventData.endampm"></s:hidden>
		<s:hidden id="endhr" name="addEventData.endhour"></s:hidden>
		<s:hidden id="endmnt" name="addEventData.endminute"></s:hidden>
		<s:hidden id="stampm" name="addEventData.stampm"></s:hidden>
		<s:hidden id="startmnt" name="addEventData.startminute"></s:hidden>
		<s:hidden id="starthr" name="addEventData.starthour"></s:hidden>
		<div class="col-md-12 col-sm-9">
			<s:hidden name="purpose" id="purpose" />
			<s:if test="%{oldeventdates['eventstatus']!=''}">
				<s:hidden name="oldeventdates['eventstatus']" id="eventstatus" />
			</s:if>
			<s:if test="%{purpose=='true'}">

				<div class="form-group">
					<div class="col-md-3 col-xs-4 control-label"><s:text name="copy.event.title.lbl"/></div>
					<div class="col-md-6 col-xs-8">
						<s:property value="eventName" />
						<s:hidden name="eid" id="eid" />
						<s:hidden name="selectedEvent"></s:hidden>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-3 col-xs-4 control-label "><s:text name="copy.new.event.title.lbl"/><span class="required">*</span></div>
					<div class="col-md-6 col-xs-8">
						<s:textfield name="newEventName" size="60"
							cssClass="form-control"></s:textfield>
					</div>
				</div>
			</s:if>
			<s:else>

				<div class="form-group">
					<div class="col-md-3 col-xs-4 control-label"><s:text name="copy.existing.event.lbl"/></div>
					<div class="col-md-6 col-xs-8">
						<s:select name="selectedEvent" cssClass="form-control"
							id="selectedEvent" list="eventsList" listKey="key"
							listValue="value" headerKey=""
							headerValue="%{getText('copy.event.select.exit.evt')}"
							onchange='parent.copyEvent1(this);'></s:select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-3 col-xs-4 control-label "><s:text name="copy.new.event.title.lbl"/><span class="required">*</span></div>
					<div class="col-md-6 col-xs-8">
						<s:textfield name="newEventName" size="60"
							cssClass="form-control"></s:textfield>
					</div>
				</div>

			</s:else>
			<s:set name="isRecurring" value="oldeventdates['eventstatus']" />

			<s:if test='%{#isRecurring=="Y"}'>
				<div id="recurringdates" style="display: none;">
					
					<div class="form-group">
						<div class="col-md-3 col-xs-4 control-label "><s:text name="new.event.first.instance.lbl"/><span class="required">*</span></div>
						<div class="col-md-6 col-xs-8">
							<input class="form-control" type="text" id="date" name="date" value="<s:property value="addEventData.startmonth"/>/<s:property value="addEventData.startday"/>/<s:property value="addEventData.startyear"/>">
						<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-3 col-xs-4 control-label"><s:text name="new.event.first.instance.start.time.lbl"/></div>
						<div class="col-md-6 col-xs-8">
							<input class="form-control" id="fromtime" type="text" 	name="fromtime" value="9:00 AM" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-3 col-xs-4 control-label"><s:text name="new.event.first.instance.end.time.lbl"/></div>
						<div class="col-md-6 col-xs-8">
							<input class="form-control" id="totime" type="text" name="totime" value="10:00 AM" />
						</div>
					</div>
				</div>
			</s:if>
			<s:else>
				<div id="normaldates" style="display: none;">
				
					<div class="form-group">
						<div class="col-md-3 col-xs-4 control-label "><s:text name="new.event.start.time.lbl"/><span class="required">*</span></div>
						<div class="col-md-6 col-xs-8">
							<input type="text" class="form-control" id="newstart_month_field" name="neweventstart" value="<s:property value="addEventData.startmonth"/>/<s:property value="addEventData.startday"/>/<s:property value="addEventData.startyear"/>&nbsp;<s:property value="addEventData.starthour"/>:<s:property value="addEventData.startminute"/>&nbsp;<s:property value="addEventData.stampm"/>">
						<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
						</div>
					</div>
					
					
					<div class="form-group">
						<div class="col-md-3 col-xs-4 control-label "><s:text name="new.event.end.time.lbl"/><span class="required">*</span></div>
						<div class="col-md-6 col-xs-8">
							<input type="text" class="form-control" id="newend_month_field"
								name="neweventend"
								value="<s:property value="addEventData.endmonth"/>/<s:property value="addEventData.endday"/>/<s:property value="addEventData.endyear"/>&nbsp;<s:property value="addEventData.endhour"/>:<s:property value="addEventData.endminute"/>&nbsp;<s:property value="addEventData.endampm"/>">
						<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
						</div>
					</div>


				</div>
			</s:else>
			<div>
				<s:if test="%{oldeventdates['accounttype']!=''}">
					<s:hidden name="oldeventdates['accounttype']"></s:hidden>
				</s:if>
			</div>
			<s:hidden name="oldeventdates['currentLevel']"/>
			<s:set name="currentlevel" value="oldeventdates['currentLevel']"/>
			<s:set name="accounttype" value="oldeventdates['accounttype']"/>
			<s:if test="%{#currentlevel!=90 && #currentlevel!=100 && #accounttype!='Gold' && #accounttype!='Platinum'}">
			<div id="upgrade_div" style="display: none;">
			<s:text name="copy.event.copy.as"/> <s:if test='%{#currentlevel==150 || #currentlevel==200}'><s:text name="copy.event.pro.lbl"/> </s:if><s:elseif test='%{#currentlevel==300}'><s:text name="copy.event.adv.lbl"/> </s:elseif><s:else><s:text name="business.lbl"/> </s:else><s:text name="copy.event.event.at.lbl"/> ${currencySymbol}<s:property value="%{oldeventdates['currentFee']}"/> <s:text name="copy.event.pricing.lbl"/>
			<div class="form-group">
				<%-- <div class="col-md-3 col-xs-4 control-label"><s:if test="%{#currentlevel==150}">RSVP Level</s:if><s:else>Ticketing Level</s:else></div> --%>
				<div class="col-md-6 col-xs-8">
					<span  style="margin-right:30px"><input  type="radio"  class="upgrade-level" value="basic" name="upgradeLevel" <s:if test='%{#currentlevel==90 || #currentlevel==100}'>checked='checked'</s:if>>&nbsp;<s:text name="copy.as.basic.lbl"/></span>
					<s:if test="%{#currentlevel==200 || #currentlevel==150}">
					<input type="radio"  class="upgrade-level" value="pro" name="upgradeLevel" <s:if test='%{#currentlevel==150 || #currentlevel==200}'>checked='checked'</s:if>>&nbsp;<s:text name="copy.as.pro.lbl"/>
					</s:if>
					<s:elseif test="%{#currentlevel==300}">
					<input type="radio"  class="upgrade-level" value="advance" name="upgradeLevel" <s:if test='%{#currentlevel==300}'>checked='checked'</s:if>>&nbsp;<s:text name="copy.as.advanced.lbl"/>
					</s:elseif>
					<s:if test="%{#currentlevel==400}">
					<input type="radio"  class="upgrade-level" value="business" name="upgradeLevel" <s:if test='%{#currentlevel==400}'>checked='checked'</s:if>>&nbsp;<s:text name="copy.as.businees.lbl"/>
					</s:if>
				</div>
			</div>
			</div>
			</s:if> 
			<s:else><s:hidden name="upgradeLevel" value="basic"></s:hidden></s:else>
			<div class="form-group"  >
				<div class="center">
					<button type="submit" class="btn btn-primary" id="copysubmit"> <s:text name="global.continue.btn.lbl"/> </button>
					<button class="btn btn-cancel cancel"> <i class="fa fa-times"></i> </button>
				</div>
			</div>


		</div>

	</s:form>

</body>
<script src="/main/bootstrap/js/bootstrap.js"></script>
<script src="/main/js/jquery.tooltipster.js"></script>
<script src="/main/js/jquery.validate.min.js"></script>
<script src="/main/js/jquery.ezpz_hint.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>

<script>
var j = 0;
	$(document).ready(function() {

						$('.cancel').click(function() {
							parent.$('#myModal').modal('hide');
						});

						$('#newstart_month_field').datetimepicker({
							format : 'm/d/Y g:i A',
							formatTime : 'g:i A'
						});
						$('#newend_month_field').datetimepicker({
							format : 'm/d/Y g:i A',
							formatTime : 'g:i A'
						});
						$('#fromtime').datetimepicker({
							format : 'g:i A',
							formatTime : 'g:i A',
							datepicker : false
						});
						$('#totime').datetimepicker({
							format : 'g:i A',
							formatTime : 'g:i A',
							datepicker : false
						});

						$('#date').datetimepicker({
							format : 'm/d/Y',
							timepicker : false,
							step : 5
						});
						
						$('input.upgrade-level').iCheck({
                    		checkboxClass: 'icheckbox_square-grey', 
                    		radioClass: 'iradio_square-grey'
                    	});
						
						$(
								'#copyevent input[type="text"],input[type="password"], #copyevent textarea, #copyevent select')
								.tooltipster({
									trigger : 'custom',
									onlyOne : false,
									fixedWidth : '300px',
									position : 'left',
									theme : 'form-validate-theme'
								});

						$('#copyevent')
								.validate(
										{
											errorPlacement : function(error,
													element) {
												var lastError = $(element)
														.data('lastError'), newError = $(
														error).text();
												$(element).data('lastError',
														newError);

												if (newError !== ''
														&& newError !== lastError) {
													$(element)
															.tooltipster(
																	'content',
																	newError);
													$(element).tooltipster(
															'show');
												}
											},
											success : function(label, element) {
												$(element).tooltipster('hide');
											},
											rules : {
												'selectedEvent' : {
													required : true
												},
												'newEventName' : {
													required : true
												},
												'date' : {
													required : true
												},
												'neweventstart' : {
													required : true
												},
												'neweventend' : {
													required : true
												},
											},
											messages : {
												'selectedEvent' : props.copy_event_sel_exit_evt_cpy,
												'newEventName' : props.copy_event_enter_title,
												'date' : props.copy_event_firt_inst_empty,
												'neweventstart' : props.copy_event_start_inst_empty,
												'neweventend' : props.copy_event_end_time_inst_empty,
											},

											submitHandler : function(form) {
												j++;
												if (j > 1)
													return;
												setDates($('#eventstatus').val());
												//makeProcessing('copysubmit');
												 parent. loadingPopup();
												
														$.ajax({
															type : "POST",
															url : 'CopyEvent!copyEvent',
															data : $(
																	"#copyevent")
																	.serialize(),
															success : function(
																	t) {
																// alert(t);
																/* if(!isValidActionMessage(t)){
																     return;
																 }   */
																var result = t;
																j = 0;
																//parent.$('#myModal').modal('hide');
																if (result.indexOf("success") > -1) {
																	//parent.$('#myModal').modal('hide');
																	//parent.notification("Event copied sucessfully.","success");
																	parent.window.location.href="../myevents/home";
																	
																//	parent.$('#myModal').modal('hide');
																//	parent.window.location.href='/main/myevents/home';
																//	parent.notification("Event copied sucessfully.","success");
																	
																}
															}
														});
											}

										});

					});

	$('.close').click(function() {
		$('#myModal').modal('hide');
	});

	if (document.getElementById('selectedEvent'))
		var eventid = document.getElementById('selectedEvent').value;
	if (eventid != '') {
		if (document.getElementById("recurringdates")) {
			document.getElementById("recurringdates").style.display = 'block';
		} else if (document.getElementById("normaldates")) {
			document.getElementById("normaldates").style.display = 'block';
		}
if(document.getElementById("upgrade_div"))		
document.getElementById("upgrade_div").style.display='block';
	} else {
		if (document.getElementById("recurringdates"))
			document.getElementById("recurringdates").innerHTML = '';
		if (document.getElementById("normaldates"))
			document.getElementById("normaldates").innerHTML = '';
	}

	var isValidActionMessage = function(messageText) {
		alert("nologin" + messageText.indexOf('nologin') + "authenticate"
				+ messageText.indexOf('login!authenticate'));
		if (messageText.indexOf('nologin') > -1
				|| messageText.indexOf('login!authenticate') > -1) {
			alert('You need to login to perform this action');
			parent.window.location.href = "../user/login";
			return false;
		} else if (messageText.indexOf('Something went wrong') > -1) {
			alert('Sorry! You do not have permission to perform this action');
			YAHOO.ebee.popup.wait.hide();
			return false;
		}
		return true;
	}

	function setDates(isrecurring) {
		if(isrecurring=="N"){
		var startDate = document.getElementById('newstart_month_field').value;
		var new_event_end = document.getElementById('newend_month_field').value;
		try {
			
//			var stm = new_event_start.split(' ');
var stm = startDate.split(startDate.charAt(startDate.length-3));
			var stm1 = stm[0].split('/');
			var stm2 = stm[1].split(':');
			$('#startyr').val(stm1[2]);
			$('#startday').val(stm1[1]);
			$('#starthr').val(stm2[0]);
			$('#startmnt').val(stm2[1]);
			$('#stampm').val(stm[2]);
			$('#startmth').val(stm1[0]);
			//var etm = new_event_end.split(' ');
            var etm = new_event_end.split(new_event_end.charAt(new_event_end.length-3));
			var etm1 = etm[0].split('/');
			var etm2 = etm[1].split(':');
			
			$('#endyr').val(etm1[2]);
			$('#endday').val(etm1[1]);
			$('#endhr').val(etm2[0]);
			$('#endmnt').val(etm2[1]);
			$('#endampm').val(etm[2]);
			$('#endmth').val(etm1[0]);
		} catch (error) {
		}
}else{
	var startdate=document.getElementById('date').value;
	var starttime = document.getElementById('fromtime').value;
	var endtime=document.getElementById('totime').value;

try{
	var stm = startdate.split('/');
	
	$('#startyr').val(stm[2]);
	$('#startmth').val(stm[0]);
	$('#startday').val(stm[1]);
	$('#endyr').val(stm[2]);
	$('#endmth').val(stm[0]);
	$('#endday').val(stm[1]);
	var stime=starttime.split(' ');
	var stimehour=stime[0].split(':');
	$('#starthr').val(stimehour[0]);
	$('#startmnt').val(stimehour[1]);
	$('#stampm').val(stime[1]);
	var etime=endtime.split(' ');
	var etimehour=etime[0].split(':');
	$('#endhr').val(etimehour[0]);
	$('#endmnt').val(etimehour[1]);
	$('#endampm').val(etime[1]);
}catch(error){
	
}
	
}
	}
</script>
</html>
