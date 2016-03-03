<%@taglib uri="/struts-tags" prefix="s" %>
<form action="TicketDetails!donationSave" name="DonationAddForm"
	id="DonationAddForm" method="post" class="form-horizontal">
	<div class="well" id="adddonationtemplate">
		<div class="row">
			<div id="donationformerrormessages"
				class='alert alert-danger col-md-12' style='display: none'></div>
			<s:hidden name="ticketType" value="Donation"></s:hidden>
			<s:hidden name="eid"></s:hidden>
			<s:hidden name="ticketData.ticketId" id="donationtktid"></s:hidden>
			<s:set name="grplen" value="groupId.length()"></s:set>
			<s:if test="%{#grplen==0}">
				<s:hidden name="groupId" value="0"></s:hidden>
			</s:if>
			<s:else>
				<s:hidden name="groupId"></s:hidden>
			</s:else>
			<div class="col-md-10">
				<s:textfield name="ticketData.ticketName"
					placeholder="%{getText('mt.donation.name.lbl')}" cssClass='form-control placeholder'
					size="52" id="donationname"></s:textfield>
			</div>



		</div>



		<div id="donationdetails" style="display: block;">
			<br />
			<!-- <h2 class="section-main-header">Settings</h2>
                                <hr/> -->


			<div class="row">
				<!-- <div class="col-xs-1"></div> -->
				<div class="col-xs-10">
					<div class="row">
						<label for="name" class="col-xs-4 control-label"><s:text name="mt.donation.desc.lbl"/></label>
						<div class="col-xs-8">
							<s:textarea rows="3" id="donationdesc" cols="49"
								name="ticketData.ticketDescription" cssClass='form-control'
								></s:textarea>

						</div>
					</div>
					<div style="height: 13px"></div>
					<s:set name="scancoderequired" value="ticketData.isScanCode"></s:set>

					<div class="row">
						<label for="message" class="col-xs-4 control-label"> <s:text name="mt.scan.req.lbl"/> <br>
						<span class="sub-text"><s:text name="mt.inc.qcode.lbl"/></span>
						</label>

						<div class="col-xs-8">
							<label class="checkbox-inline"> <input type="radio"
								class="scancode" name="ticketData.isScanCode"
								id="donationyscancode" value="Yes"
								<s:if test="%{#scancoderequired == 'Yes'}">checked="checked"</s:if>>
								<s:text name="gllobal.yes.lbl"/>
							</label> <label class="checkbox-inline"> <input type="radio"
								class="scancode" name="ticketData.isScanCode"
								id="donationnscancode" value="No"
								<s:if test="%{#scancoderequired != 'Yes'}">checked="checked"</s:if>>
								<s:text name="global.no.lbl"/>
							</label>

						</div>
					</div>
					<div style="height: 10px"></div>

					<div class="row">
						<label class="col-xs-4 control-label"> <s:text name="mt.sale.date.time.lbl"/> <span class="required">*</span></label>
						<div class="col-xs-8">
							<div class='row'>

								<div class="col-md-12">

									<s:if test="%{isrecurring==true}">
										<div class='col-md-2' style="padding-left: 0px;">
											<label class="col-md-3 control-label"> <s:text name="mt.starts.lbl"/> </label>
										</div>
										<s:if test="%{ticketData.ticketId==''}">
											<s:set name="startdays" value="100"></s:set>
										</s:if>
										<s:else>
											<s:set name="startdays" value="ticketData.startBefore"></s:set>
										</s:else>
										<div class="col-md-3">
											<s:textfield name="ticketData.startBefore" id="donationData_startBefore" size="2" theme="simple" 
												value="%{#startdays}" cssClass="form-control"></s:textfield>
											<span class="sub-text"> <s:text name="mt.days.lbl"/> </span>
										</div>
										<s:hidden name="ticketData.stdateTimeBeanObj.monthPart"></s:hidden>
										<s:hidden name="ticketData.stdateTimeBeanObj.ddPart"></s:hidden>
										<s:hidden name="ticketData.stdateTimeBeanObj.yyPart"></s:hidden>
										<div class="col-md-3">
											<s:textfield name="ticketData.startHoursBefore"
												id="donationData_startHoursBefore" size="2" theme="simple"
												cssClass="form-control"></s:textfield>
											<span class="sub-text">  <s:text name="mt.hours.lbl"/>  </span>
											<s:hidden name="ticketData.stdateTimeBeanObj.hhPart"></s:hidden>
										</div>
										<div class="col-md-4">
											<s:textfield name="ticketData.startMinutesBefore"
												id="donationData_startMinutesBefore" size="2" theme="simple"
												cssClass="form-control"></s:textfield>
											<span class="sub-text">  <s:text name="mt.min.before.lbl"/>  </span>
											<s:hidden name="ticketData.stdateTimeBeanObj.mmPart"></s:hidden>
											<s:hidden name="ticketData.stdateTimeBeanObj.ampm"></s:hidden>
										</div>
									</s:if>
									<s:else>

										<div class="row">

											<div class="col-xs-12">
												<s:textfield cssClass="form-control" id="donationstart" type="text" name="ticketData.newStartDate" />
											<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
											</div>
											<div class="col-xs-12" style="padding-left: 141px; padding-top: 12px;">&nbsp;&nbsp; <s:text name="global.to.lbl"/>  </div>
											<div class="col-xs-12" style="padding-top: 10px">
												<s:textfield cssClass="form-control" id="donationend" type="text" name="ticketData.newEndDate" />
											<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
											</div>
										</div>
									</s:else>
								</div>
								<div class="col-md-12">
									<s:if test="%{isrecurring==true}">
										<div class="col-md-2" style="padding-left: 0px;">
											<label class="col-md-3 control-label"> <s:text name="mt.ends.lbl"/>  </label>
										</div>
										<div class="col-md-3">
											<s:textfield cssClass='form-control'
												name="ticketData.endBefore" id="donationData_endBefore"
												size="2" theme="simple"></s:textfield>
											<span class="sub-text">  <s:text name="mt.days.lbl"/></span>
										</div>
										<s:hidden name="ticketData.enddateTimeBeanObj.monthPart"></s:hidden>
										<s:hidden name="ticketData.enddateTimeBeanObj.ddPart"></s:hidden>
										<s:hidden name="ticketData.enddateTimeBeanObj.yyPart"></s:hidden>
										<div class="col-md-3">
											<s:textfield cssClass='form-control'
												name="ticketData.endHoursBefore"
												id="donationData_endHoursBefore" size="2" theme="simple"></s:textfield>
											<span class="sub-text"><s:text name="mt.hours.lbl"/></span>
											<s:hidden name="ticketData.enddateTimeBeanObj.hhPart"></s:hidden>
										</div>
										<div class="col-md-4">
											<s:textfield cssClass='form-control'
												name="ticketData.endMinutesBefore"
												id="donationData_endMinutesBefore" size="2" theme="simple"></s:textfield>
											<span class="sub-text">  <s:text name="mt.min.before.lbl"/>   </span>
											<s:hidden name="ticketData.enddateTimeBeanObj.mmPart"></s:hidden>
											<s:hidden name="ticketData.enddateTimeBeanObj.ampm"></s:hidden>
										</div>
									</s:if>
									<s:else>
									</s:else>

								</div>

							</div>

						</div>

					</div>
				</div>
			</div>
			<br />
			<div class="row center">
				<span>
					<button type="button" class="btn btn-primary confirmadddonation">
						<b><s:text name="global.add.btn.lbl"/></b>
					</button>
					<button type="button" class="btn btn-cancel canceladddonation">
						<i class="fa fa-times"></i>
					</button>
				</span>

			</div>
		</div>
	</div>


</form>
<script>

$('input.scancode').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});

 $('#donationstart').datetimepicker({
       format:'m/d/Y g:i A',
       formatTime:'g:i A'
   });
 
 $('#donationend').datetimepicker({
       format:'m/d/Y g:i A',
       formatTime:'g:i A'
   });

if($('#donationtktid').val()!=''){
	$('.confirmadddonation').html('<b>'+props.global_save_btn+'</b>');
}
 
</script>