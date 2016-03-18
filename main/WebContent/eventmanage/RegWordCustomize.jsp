<%@taglib uri="/struts-tags" prefix="s"%>
<s:hidden value="%{currentLevel}" id="currentLevel" />
<style>
.form-horizontal .control-label{
	padding-top :0px !important;
}
</style>
<form method="post" action="RegWordCustomize!save" id="ticWordingsForm" class="form-horizontal">
	<s:hidden name="eid"></s:hidden>

	<div class="col-md-12">

		<div class="row">
			<div id="regWordStatusMsg"></div>
		</div>

		<div id="gap"></div>
		<%-- <div class="row">
			<div class="alert alert-info hidden-xs">
				<i class="fa fa-info-circle"></i>
				<s:text name="rwc.wordings.help.msg.lbl"></s:text>
			</div>
		</div> --%>
	</div>

	<div>
		<div id="ticketsWording">
			<div class="section-main-header ">
				<s:text name="rwc.tickets.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('ticketsWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>			
			
			<div class="white-box">
				<div id="ticketsWordingContent"></div>
				<div id="ticketsWordingEdit" style="display:none" >
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.wording.section.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6 bottom-gap">
							<s:textfield name="wordAttribs['event.reg.ticket.page.Header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.ticket.name.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.ticket.name.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.price.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.ticket.price.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.fee.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.processfee.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.quantity.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.ticket.qty.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.error.msg.not.selected.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.selectTicket.msg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.registration.button.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.orderbutton.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>


					<s:if test="%{seatingEnabled=='YES'}">
						<div class="form-group">
							<div class="col-md-4 col-sm-6 control-label">
								<s:text name="rwc.seating.lbl"></s:text>
							</div>
							<div class="col-md-7 col-sm-6  bottom-gap">
								<s:textfield name="wordAttribs['event.reg.seat.tooltip.label']"
									size='60' theme="simple" cssClass="form-control"></s:textfield>
							</div>
						</div>
					</s:if>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('ticketsWording')"><s:text name="global.save.btn.lbl"/></button>
							
							<button type="button" class="btn btn-cancel" id="ticketsCancel"><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- tickets wording section close -->
		
     <!-- priorityRegistration Wording section  -->
     
        <s:if test='%{count!="0"}'>
			<div id="priorityRegistrationWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.priority.reg.section.header.lbl"/>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('priorityRegistrationWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="priorityRegistrationWordingContent"></div>
				<div id="priorityRegistrationWordingEdit" style="display:none;">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.sec.header.lbl"/>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.prio.reg.page.header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.select.prity.list.lbl"/>:
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.prio.reg.sel.list.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.skip.lbl"/>:
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.prio.reg.skip.btn.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.contn.btn.lbl"/>:
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.prio.reg.continue.btn.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<%-- <div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.already.used.lbl"/>:
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.prio.reg.limit.errmsg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div> --%>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.invalid.data.lbl"/>:
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.prio.reg.invalid.params.errmsg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>

					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('priorityRegistrationWording')"><s:text name="global.save.btn.lbl"/></button>
							<button type="button" id="priorityCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		</s:if>



		<div id="discountsWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.discounts.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('discountsWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="discountsWordingContent"></div>
				<div id="discountsWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.discount.code.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.discount.box.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.discount.apply.button.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['discount.code.applybutton.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.error.msg.invalid.code.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.discount.invalid.msg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.msg.discount.applied.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.discount.applied.msg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.code.expiration.msg.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.discount.time.exp.msg']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('discountsWording')"><s:text name="global.save.btn.lbl"/></button>
							
							<button type="button" id="discountCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- discounts wording section close -->
<%-- 
		<div id="memberTicketsWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<b><s:text name="rwc.member.tickets.section.header.lbl"></s:text></b>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary"
					onclick="return edit('memberTicketsWording');">Edit</button>
			</div>
			<div class="white-box">
				<div id="memberTicketsWordingContent"></div>
				<div id="memberTicketsWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.member.ticket.hint.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield value="Member Ticket" size='60' theme="simple"
								cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.need.login.link.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield value="Need Login" size='60' theme="simple"
								cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('memberTicketsWording')">Save</button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- member tickets wording section close -->
 --%>
		<div id="totalsWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.total.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('totalsWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="totalsWordingContent"></div>
				<div id="totalsWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.show.total.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.totallink.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.total.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.total.amount.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.discount.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.discount.amount.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.net.amount.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.net.amount.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.tax.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.tax.amount.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.grand.total.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.grandtotal.amount.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>

					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('totalsWording')"><s:text name="global.save.btn.lbl"/></button>
							<button type="button" id="totalCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- totals  wording section close -->

		<div id="profileWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.profile.section.header"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('profileWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="profileWordingContent"></div>
				<div id="profileWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.profile.section.header.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.page.Header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.buyer.profile.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.buyerinfo.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
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
								name="wordAttribs['event.reg.requiredprofile.empty.msg']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.fname.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.fname.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.lname.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.lname.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.email.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.email.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.phone.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.profile.phone.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.copy.from.buyer.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profile.copybuyerinfo.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.promotion.title.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['ebee.promotions.header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					

					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.promotions.message.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['promotion.section.message']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.back.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.backbutton.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.continue.button.label.tb.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.profilepage.continue.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('profileWording')"><s:text name="global.save.btn.lbl"/></button>
							
							<button type="button" id="profileCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- profile  wording section close -->

		<div id="paymentsWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.payment.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary" onclick="return edit('paymentsWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="paymentsWordingContent"></div>
				<div id="paymentsWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.payment.page.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.payments.page.Header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.payment.back.profile.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.backtoprofile.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.payment.section.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.paymentsheader.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.refund.policy.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.refund.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.paypal.payment.button.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.paypal.paybutton.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.eb.cc.button.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.eventbee.paybutton.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.other.payment.button.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.other.paybutton.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.continue.zero.payment.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield name="wordAttribs['event.reg.zero.paybutton.label']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.cc.failure.message.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.payments.failure.label']" size='60'
								theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('paymentsWording')"><s:text name="global.save.btn.lbl"/></button>
							
								<button type="button" id="paymentCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- payments section wording section close -->


		<div id="confirmationWording">
			<div class="section-main-header box-top-gap">
				<!--panel header goes here-->
				<s:text name="rwc.confirmation.section.header.lbl"></s:text>
			</div>
			<div class="row sticky-out-button pull-right">
				<button class="btn btn-primary"
					onclick="return edit('confirmationWording');"><s:text name="global.edit.lnk.lbl"/></button>
			</div>
			<div class="white-box">
				<div id="confirmationWordingContent"></div>
				<div id="confirmationWordingEdit" style="display:none">
					<div class="form-group">
						<div class="col-md-4 col-sm-6 control-label">
							<s:text name="rwc.confirmation.page.header.lbl"></s:text>
						</div>
						<div class="col-md-7 col-sm-6  bottom-gap">
							<s:textfield
								name="wordAttribs['event.reg.confirmation.page.Header']"
								size='60' theme="simple" cssClass="form-control"></s:textfield>
						</div>
					</div>
					<div class="center">
						<button type="button" class="btn btn-primary"
							onclick="save('confirmationWording')"><s:text name="global.save.btn.lbl"/></button>
								<button type="button" id="confirmationCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<!-- confirmation  wording section close -->

		<s:if test="%{isrecurring==true}">
			<div id="recurringDatesWording">
				<div class="section-main-header box-top-gap">
					<!--panel header goes here-->
					<s:text name="rwc.recurring.dates.section.header.lbl"></s:text>
				</div>
				<div class="row sticky-out-button pull-right">
					<button class="btn btn-primary"
						onclick="return edit('recurringDatesWording');"><s:text name="global.edit.lnk.lbl"/></button>
				</div>
				<div class="white-box">
					<div id="recurringDatesWordingContent"></div>
					<div id="recurringDatesWordingEdit" style="display:none">
						<div class="form-group">
							<div class="col-md-4 col-sm-6 control-label">
								<s:text name="rwc.recurring.dates.lbl"></s:text>
							</div>
							<div class="col-md-7 col-sm-6  bottom-gap">
								<s:textfield
									name="wordAttribs['event.reg.recurringdates.label']" size='60'
									theme="simple" cssClass="form-control"></s:textfield>
							</div>
						</div>
						<div class="center">
							<button type="button" class="btn btn-primary"
								onclick="save('recurringDatesWording')">Save</button>
								
								<button type="button" id="recurringDatesCancel" class="btn btn-cancel"
							><i class="fa fa-times"></i></button>
						</div>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>
			<!-- recurring dates label section close -->

		</s:if>

	</div>

</form>


<script>

var labels={"wordAttribs['event.reg.ticket.page.Header']":"<s:text name='rwc.wording.section.header.lbl'></s:text>",
		 "wordAttribs['event.reg.ticket.name.label']":"<s:text name='rwc.ticket.name.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.ticket.price.label']":"<s:text name='rwc.price.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.processfee.label']":"<s:text name='rwc.fee.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.ticket.qty.label']":"<s:text name='rwc.quantity.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.selectTicket.msg']":"<s:text name='rwc.error.msg.not.selected.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.orderbutton.label']":"<s:text name='rwc.registration.button.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.seat.tooltip.label']":"<s:text name='rwc.seating.lbl'></s:text>",
		 /*discounts*/
		 "wordAttribs['event.reg.discount.box.label']":"<s:text name='rwc.discount.code.label.tb.lbl'></s:text>",
		 "wordAttribs['discount.code.applybutton.label']":"<s:text name='rwc.discount.apply.button.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.discount.invalid.msg']":"<s:text name='rwc.error.msg.invalid.code.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.discount.applied.msg']":"<s:text name='rwc.msg.discount.applied.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.discount.time.exp.msg']":"<s:text name='rwc.code.expiration.msg.tb.lbl'></s:text>",
		 /* members ticket wording*/
		 "wordAttribs['event.reg.member.label']":"<s:text name='rwc.member.ticket.hint.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.need.login.label']":"<s:text name='rwc.need.login.link.label.tb.lbl'></s:text>",
		 /*totals wording*/
		 "wordAttribs['event.reg.totallink.label']":"<s:text name='rwc.show.total.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.total.amount.label']":"<s:text name='rwc.total.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.discount.amount.label']":"<s:text name='rwc.discount.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.net.amount.label']":"<s:text name='rwc.net.amount.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.tax.amount.label']":"<s:text name='rwc.tax.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.grandtotal.amount.label']":"<s:text name='rwc.grand.total.label.tb.lbl'></s:text>",
			 /*profile wordings*/
		"wordAttribs['event.reg.profile.page.Header']":"<s:text name='rwc.profile.section.header.tb.lbl'></s:text>","wordAttribs['event.reg.orderbutton.label']":"<s:text name='rwc.registration.button.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.buyerinfo.label']":"<s:text name='rwc.buyer.profile.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.requiredprofile.empty.msg']":"<s:text name='rwc.error.message.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.profile.fname.label']":"<s:text name='rwc.fname.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.profile.lname.label']":"<s:text name='rwc.lname.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.profile.email.label']":"<s:text name='rwc.email.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.profile.phone.label']":"<s:text name='rwc.phone.label.tb.lbl'></s:text>",
		 "wordAttribs['ebee.promotions.header']":"<s:text name='rwc.promotion.title.tb.lbl'></s:text>",
		 "wordAttribs['promotion.section.message']":"<s:text name='rwc.promotions.message.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.backbutton.label']":"<s:text name='rwc.back.label.tb.lbl'></s:text>",
		 "wordAttribs['event.reg.profilepage.continue.label']":"<s:text name='rwc.continue.button.label.tb.lbl'></s:text>",
		 /*payments wording*/
		 "wordAttribs['event.reg.payments.page.Header']":"<s:text name='rwc.payment.page.header.lbl'></s:text>",
		 "wordAttribs['event.reg.backtoprofile.label']":"<s:text name='rwc.payment.back.profile.lbl'></s:text>",
		 "wordAttribs['event.reg.profile.copybuyerinfo.label']":"<s:text name='rwc.copy.from.buyer.lbl'></s:text>",
		 "wordAttribs['event.reg.paymentsheader.label']":"<s:text name='rwc.payment.section.header.lbl'></s:text>",
		 "wordAttribs['event.reg.refund.label']":"<s:text name='rwc.refund.policy.lbl'></s:text>",
		 "wordAttribs['event.reg.paypal.paybutton.label']":"<s:text name='rwc.paypal.payment.button.lbl'></s:text>",
		 "wordAttribs['event.reg.eventbee.paybutton.label']":"<s:text name='rwc.eb.cc.button.lbl'></s:text>",
		 "wordAttribs['event.reg.other.paybutton.label']":"<s:text name='rwc.other.payment.button.lbl'></s:text>",
		 "wordAttribs['event.reg.zero.paybutton.label']":"<s:text name='rwc.continue.zero.payment.lbl'></s:text>",
		 "wordAttribs['event.reg.payments.failure.label']":"<s:text name='rwc.cc.failure.message.lbl'></s:text>",
		 /*confirmation wording*/
		"wordAttribs['event.reg.confirmation.page.Header']":"<s:text name='rwc.confirmation.page.header.lbl'></s:text>",
		/*recurringDatesWording*/
		"wordAttribs['event.reg.recurringdates.label']":"<s:text name='rwc.recurring.dates.lbl'></s:text>",
		
		/*             Priority Registration        */
		"wordAttribs['event.prio.reg.page.header']":"<s:text name='rwc.sec.header.lbl'></s:text>",
		"wordAttribs['event.prio.reg.sel.list.label']":"<s:text name='rwc.select.prity.list.lbl'></s:text>",
		"wordAttribs['event.prio.reg.skip.btn.label']":"<s:text name='rwc.skip.lbl'></s:text>",
		"wordAttribs['event.prio.reg.continue.btn.label']":"<s:text name='rwc.contn.btn.lbl'></s:text>",
		"wordAttribs['event.prio.reg.limit.errmsg']":"<s:text name='rwc.already.used.lbl'></s:text>",
		"wordAttribs['event.prio.reg.invalid.params.errmsg']":"<s:text name='rwc.invalid.data.lbl'></s:text>"
		
		};			
    var formOnLoadData;
	$(document).ready(function() {
		
		$('#priorityCancel').click(function(e){
			 e.preventDefault();
			 edit('priorityRegistrationWording');
		 });

		 $('#ticketsCancel').click(function(e){
			 e.preventDefault();
			 edit('ticketsWording');
		 });

		 $('#recurringDatesCancel').click(function(e){
			 e.preventDefault();
			 edit('recurringDatesWording');
		 });

		 $('#confirmationCancel').click(function(e){
			 e.preventDefault();
			 edit('confirmationWording')
		 });

		 $('#paymentCancel').click(function(e){
			 e.preventDefault();
			 edit('paymentsWording');
		 });

		 $('#profileCancel').click(function(e){
			 e.preventDefault();
			 edit('profileWording');
		 });

		 $('#totalCancel').click(function(e){
			 e.preventDefault();
			 edit('totalsWording');
		 });

		 $('#discountCancel').click(function(e){
			 e.preventDefault();
			 edit('discountsWording');
		 });
		 

		
		$('#submitBtn').attr('data-loading-text', props.global_processing_lbl);
		$.fn.serializeObject = function(){
			   var o = {};
			   var a = this.serializeArray();
			   $.each(a, function() {
			       if (o[this.name] !== undefined) {
			           if (!o[this.name].push) {
			               o[this.name] = [o[this.name]];
			           }
			           o[this.name].push(this.value || '');
			       } else {
			           o[this.name] = this.value || '';
			       }
			   });
			   return o;
			};
		formOnLoadData=$("#ticWordingsForm").serializeObject();
		
		$.each([ 'priorityRegistrationWording','ticketsWording','discountsWording',/*'memberTicketsWording',*/ 'totalsWording','profileWording','paymentsWording','confirmationWording'], function( index, value ) {
				var subFormData=$('#'+value+"Edit").find("select, textarea, input").serializeObject();		
				updateAccountInformation(value+"Content",subFormData);	
			});
		if($("#recurringDatesWording")){
			var subFormData=$('#recurringDatesWordingEdit').find("select, textarea, input").serializeObject();		
			updateAccountInformation("recurringDatesWordingContent",subFormData);
		}
		
		
	});
	function updateAccountInformation(id,obj){ 
		var html="";		
		for(key in obj){	
			if(obj[key]!='')
				html+='<div class="row col-md-12 col-sm-12 col-xs-12" ><div class="col-md-4 col-sm-6 col-xs-6 control-label"><label>'+labels[key]+'&nbsp;:</label></div>'+
				'<div class="col-md-8 col-sm-6 col-xs-6 aFname">'+obj[key]+' </div><div style="clear:both;"></div></div>';
			}		
			$("#"+id).html(html);
		}
	function save(id){
		var subFormData=$('#'+id).find("select, textarea, input").serializeObject();		
		for(var key in subFormData){
			if(formOnLoadData.hasOwnProperty(key)){//updating the last saved data with current form data.  if validation error occurs we have to get back last saved data.
				formOnLoadData[key]=subFormData[key];
			}else{
				formOnLoadData[key]=subFormData[key];
			}
		}
		savingToserver(formOnLoadData,subFormData,id);
		return false;
	}
  
	function edit(id){
		if($("#"+id+"Edit").is(":hidden")){
			$("#"+id+"Edit").show();
			$("#"+id+"Content").hide();
		}else{
			$("#"+id+"Edit").hide();
			$("#"+id+"Content").show();
		}
			
		return false;		
	}

	
	function savingToserver(data,blockData,id){
		var curLevel= $('#currentLevel').val();
		if(curLevel>=200){
			loadingPopup();
			$.ajax({
				url : 'RegWordCustomize!save',
				type : 'post',
				data : data /* $("#ticWordingsForm").serialize() */,
				success : function(t) {
					if (isValidActionMessage(t)) {
						document.getElementById('regWordStatusMsg').scrollIntoView();
						$("#notification-holder").html('');
						var message=props.global_updated_msg;
						if("priorityRegistrationWording"==id)
							message=props.rwc_priority_wording_updated_msg;
						else if("ticketsWording"==id)
							message=props.rwc_tickets_wording_updated_msg;
						else if("discountsWording"==id)
							message=props.rwc_discounts_wording_updated_msg;
						/* else if("memberTicketsWording"==id)
							message=""; */
						else if("totalsWording"==id)
							message=props.rwc_totals_wording_updated_msg;
						else if("profileWording"==id)
							message=props.rwc_profile_wording_updated_msg;
						else if("paymentsWording"==id)
							message=props.rwc_payments_wording_updated_msg;
						else if("confirmationWording"==id)
							message=props.rwc_confirmation_wording_updated_msg;
						else if("recurringDatesWording"==id)
							message=props.rwc_recurring_dates_wording_updated_msg;									
						notification(message,'success');
						updateAccountInformation(id+"Content",blockData);
						edit(id);
						hidePopup();
					}
				}
			});
		}else{
			var eventid = $('#eid').val();
			specialFee(eventid,"200","RegWordCustomize","Ticketing");
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