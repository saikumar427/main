<%@taglib uri="/struts-tags" prefix="s"%>
<s:hidden value="%{currentLevel}" id="currentLevel" />
<style>
.form-horizontal .control-label {
	padding-top: 0px !important;
}
</style>
<form method="post" action="RSVPWordCustomize!save" id="RSVPWordingsForm" class="form-horizontal">
	<s:hidden name="eid"></s:hidden>
	<div class="col-md-12">
		<div class="row">
			<div id="RSVPWordStatusMsg"></div>
		</div>
		<div id="gap">
		</div>
		
		<%-- <div class="row" style="display:none">
			<div class="alert alert-info hidden-xs">
				<i class="fa fa-info-circle"></i>
				<s:text name="rwc.rsvp.wordings.help.msg.lbl"></s:text>
			</div>
		</div> --%>
	</div>

	<div>
	
	
	
		<div id="rsvpWording">
			<div class="section-main-header">
				<s:text name="rwc.wording.tp.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary"
					onclick="return edit('rsvpWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="rsvpWordingContent"></div>
				<div id="rsvpWordingEdit" style="display: none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.wording.section.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.rsvppage.header']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- one finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.attending.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.response.attending.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- two finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.not.attending.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.response.notattending.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- three finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.not.sure.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.response.notsuretoattend.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- four finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.error.message.lbl"></s:text>
							<br>
							<div
								style="font-size: 10px; color: #666666; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: lighter;">
								<s:text name="rwc.rsvp.error.message.help.msg.lbl"></s:text>
							</div>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.response.error.message']" size='60'
								cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- five finished -->


					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.continue.button.label.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.response.continuebutton.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- six finished -->
				<div class="center">
					<button type="button" class="btn btn-primary"
						onclick="save('rsvpWording')"><s:text name="global.save.btn.lbl"/></button>
			<button type="button" class="btn btn-cancel" id="rsvpwordingsCancel"><i class="fa fa-times"></i></button>
						
				</div>
				</div>
			
			<div style="clear: both"></div>
			</div>
		</div>








		<div id="profileWording">
			<div class="section-main-header box-top-gap">
				<s:text name="rwc.rsvp.profile.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary"
					onclick="return edit('profileWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>



			<div class="white-box">
				<div id="profileWordingContent"></div>
				<div id="profileWordingEdit" style="display: none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.transaction.profile.lbl"></s:text>
							<br>
							<div class="sub-text">	<s:text name="rwc.rsvp.applicable.only"/></div>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.registrantinfo.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- one finished -->



					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.fname.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.fname.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- two finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.lname.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.lname.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- three finished -->

					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.email.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.email.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- four finished -->
					<%-- <div class="col-md-5">Phone Label</div>
	  	   		<div class="col-md-7"><s:textfield name="wordAttribs['event.reg.profile.phone.label']"  size='60'  cssClass="form-control" theme="simple" cssClass="form-control"></s:textfield></div>
	  	   		<br/><br/> --%>


					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.promotion.title.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.promotions.title.label']"
								cssClass="form-control" size='60' theme="simple"></s:textfield>
						</div>
					</div>

					<!-- five finished -->

					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.promotions.message.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.promotions.message']"
								cssClass="form-control" size='60' theme="simple"></s:textfield>
						</div>
					</div>

					<!-- six finished -->
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.error.message.tb.lbl"></s:text>
							<br>
							
							<div class=sub-text>
								<s:text name="rwc.error.message.help.msg"></s:text>
							</div>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.error.message']" size='60'
								cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<!-- seven finished -->

					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.profile.submit.button.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.submitbutton.label']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('profileWording')"><s:text name="global.save.btn.lbl"/></button>
								<button type="button" id="profilewordingsCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
					<!-- eight finished -->
				</div>
				<div style="clear: both"></div>
			</div>
		</div>


		<div id="confirmationWording">
			<div class="section-main-header box-top-gap">
				<s:text name="rwc.rsvp.confirmation.section.header"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary"
					onclick="return edit('confirmationWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>

			<div class="white-box">
				<div id="confirmationWordingContent"></div>
				<div id="confirmationWordingEdit" style="display: none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.rsvp.confirmation.page.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.confirmationpage.header']"
								size='60' cssClass="form-control" theme="simple"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('confirmationWording')"><s:text name="global.save.btn.lbl"/></button>
								<button type="button" id="confirmationwordingsCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
					<!-- finished -->
				</div>
				<div style="clear: both"></div>
			</div>
		</div>


		<s:if test="%{isrecurring==true}">
			<div id="recurringDates">
				<div class="section-main-header box-top-gap">
					<s:text name="rwc.recurring.dates.section.header.lbl"></s:text>
				</div>
				<div class="row sticky-out-button pull-right">
					<button class="btn btn-primary"
						onclick="return edit('recurringDates');"><s:text name="global.edit.lnk.lbl"/></button>
				</div>

				<div class="white-box">
					<div id="recurringDatesContent"></div>
					<div id="recurringDatesEdit" style="display: none">
						<div class="form-group">
							<div class="col-md-4 col-sm-6 control-label">
								<s:text name="rwc.recurring.dates.lbl"></s:text>
							</div>
							<div class="col-md-7 col-sm-6  bottom-gap">
								<s:textfield
									name="wordAttribs['event.reg.recurringdates.label']" size='60'
									cssClass="form-control" theme="simple"></s:textfield>
							</div>
						</div>
						<div class="center">
							<button type="button" class="btn btn-primary"
								onclick="save('recurringDates')"><s:text name="global.save.btn.lbl"/></button>
									<button type="button" id="recurringdatesCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
						</div>
					</div>
					<div style="clear: both"></div>
				</div>
			</div>
		</s:if>



		<!--  panel body close -->


	</div>


</form>


<script>
	var labels = {
		"wordAttribs['event.reg.rsvppage.header']" : "<s:text name="rwc.wording.section.header.lbl"></s:text>",
		"wordAttribs['event.reg.response.attending.label']" : "<s:text name="rwc.rsvp.attending.lbl"></s:text>",
		"wordAttribs['event.reg.response.notattending.label']" : "<s:text name="rwc.rsvp.not.attending.lbl"></s:text>",
		"wordAttribs['event.reg.response.notsuretoattend.label']" : "<s:text name="rwc.rsvp.not.sure.lbl"></s:text>",
		"wordAttribs['event.reg.response.error.message']" : "<s:text name="rwc.rsvp.error.message.lbl"></s:text>",
		"wordAttribs['event.reg.response.continuebutton.label']" : "<s:text name="rwc.rsvp.continue.button.label.lbl"></s:text>",
		/*profile*/
		"wordAttribs['event.reg.profile.registrantinfo.label']" : "<s:text name="rwc.rsvp.transaction.profile.lbl"></s:text>",
		"wordAttribs['event.reg.profile.fname.label']" : "<s:text name="rwc.rsvp.fname.lbl"></s:text>",
		"wordAttribs['event.reg.profile.lname.label']" : "<s:text name="rwc.rsvp.lname.lbl"></s:text>",
		"wordAttribs['event.reg.profile.email.label']" : "<s:text name="rwc.rsvp.email.lbl"></s:text>",
		"wordAttribs['event.reg.profile.promotions.title.label']" : "<s:text name="rwc.rsvp.promotion.title.lbl"></s:text>",
		"wordAttribs['event.reg.profile.promotions.message']" : "	<s:text name="rwc.rsvp.promotions.message.lbl"></s:text>",
		"wordAttribs['event.reg.profile.error.message']" : "<s:text name="rwc.error.message.tb.lbl"></s:text>",
		"wordAttribs['event.reg.profile.submitbutton.label']" : "<s:text name="rwc.rsvp.profile.submit.button.lbl"></s:text>",
		/*confirmationWording*/
		"wordAttribs['event.reg.confirmationpage.header']" : "<s:text name="rwc.rsvp.confirmation.page.header.lbl"></s:text>",
		/*recurringDates*/
		"wordAttribs['event.reg.recurringdates.label']" : "<s:text name="rwc.recurring.dates.lbl"></s:text>",
	};
	var formOnLoadData;
	$(document).ready(
			function() {

				 $('#rsvpwordingsCancel').click(function(e){
					 e.preventDefault();
					 edit('rsvpWording');
				 });

				 $('#profilewordingsCancel').click(function(e){
					 e.preventDefault();
					 edit('profileWording');
				 });

				 $('#confirmationwordingsCancel').click(function(e){
					 e.preventDefault();
					 edit('confirmationWording')
				 });

				 $('#recurringdatesCancel').click(function(e){
					 e.preventDefault();
					 edit('recurringDates');
				 });
							





$('#submitBtn').attr('data-loading-text',props.global_processing_lbl);
				$.fn.serializeObject = function() {
					var o = {};
					var a = this.serializeArray();
					$.each(a, function() {
						if (o[this.name] !== undefined) {
							if (!o[this.name].push) {
								o[this.name] = [ o[this.name] ];
							}
							o[this.name].push(this.value || '');
						} else {
							o[this.name] = this.value || '';
						}
					});
					return o;
				};
				formOnLoadData = $("#RSVPWordingsForm").serializeObject();

				$.each([ 'rsvpWording', 'profileWording',
						'confirmationWording', 'recurringDates' ], function(index, value) {
					var subFormData = $('#' + value + "Edit").find(
							"select, textarea, input").serializeObject();
					updateAccountInformation(value + "Content", subFormData);
				});
				if ($("#recurringDatesWording")) {
					var subFormData = $('#recurringDatesWordingEdit').find(
							"select, textarea, input").serializeObject();
					updateAccountInformation("recurringDatesWordingContent",
							subFormData);
				}

			});
	function updateAccountInformation(id, obj) {
		var html = "";
		for (key in obj) {
			if (obj[key] != '')
				html += '<div class="row col-md-12 col-sm-12 col-xs-12" ><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'
						+ labels[key]
						+ '&nbsp;:</label></div>'
						+ '<div class="col-md-8 col-sm-6 col-xs-6 aFname">'
						+ obj[key]
						+ ' </div><div style="clear:both;"></div></div>';
		}
		$("#" + id).html(html);
	}
	function save(id) {
		var subFormData = $('#' + id).find("select, textarea, input")
				.serializeObject();
		for ( var key in subFormData) {
			if (formOnLoadData.hasOwnProperty(key)) {//updating the last saved data with current form data.  if validation error occurs we have to get back last saved data.
				formOnLoadData[key] = subFormData[key];
			} else {
				formOnLoadData[key] = subFormData[key];
			}
		}
		savingToserver(formOnLoadData, subFormData, id);
		return false;
	}
	function edit(id) {
		if ($("#" + id + "Edit").is(":hidden")) {
			$("#" + id + "Edit").show();
			$("#" + id + "Content").hide();
		} else {
			$("#" + id + "Edit").hide();
			$("#" + id + "Content").show();
		}

		return false;
	}
	function savingToserver(data, blockData, id) {
		if($('#currentLevel').val()==150){
			$.ajax({
				url : 'RSVPWordCustomize!save',
				type : 'post',
				data : data /* $("#ticWordingsForm").serialize() */,
				success : function(t) {
					if (isValidActionMessage(t)) {
						document.getElementById('RSVPWordStatusMsg')
								.scrollIntoView();
						$("#notification-holder").html('');

						var message=props.global_updated_msg;
							if("rsvpWording"==id)
		                        message=props.rwc_rsvp_wording_updated_msg;
		                    else if("profileWording"==id)
		                        message=props.rwc_rsvp_profile_wording_updated_msg;
		                    else if("confirmationWording"==id)
		                        message=props.rwc_rsvp_confirmation_wording_updated_msg;
		                    else if("recurringDates"==id)
		                        message=props.rwc_rsvp_totals_wording_updated_msg;
		                   
						notification(message, 'success');
						updateAccountInformation(id + "Content", blockData);
						edit(id);
						hidePopup();
					}
				}
			});
		}else{
			var eventid = $('#eid').val();
			specialFee(eventid,"150","RSVPWordCustomize","RSVP");
		}
		
		
	}
	/* function isValidActionMessage(messageText) {alert('hi');
		if (messageText.indexOf('nologin') > -1
				|| messageText.indexOf('login!authenticate') > -1) {
			alert(props.global_no_login_msg);
			window.location.href = "../user/login";
			return false;
		} else if (messageText.indexOf(props.global_something_wrong_lbl) > -1) {
			alert(props.global_not_have_perm_msg);
			return false;
		}
		return true;
	} */
</script>