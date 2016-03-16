<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js?t=<%= (new java.util.Date()).getTime()%>"></script>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<!-- <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" /> -->
<link type="text/css" rel="stylesheet" href="/main/css/upgradepopup.css?t=<%= (new java.util.Date()).getTime()%>"/>
<script type="text/javascript" src="/main/js/eventmanage/specialfee.js?t=<%= (new java.util.Date()).getTime()%>"></script>
<style>
</style>
<s:form name="specialfee" id="specialfee" action="SpecialFee!specialFee" method="post">
<s:set name="splfee_100" value="specialFeeMap[100]"></s:set>
<s:set name="splfee_200" value="specialFeeMap[200]"></s:set>
<s:set name="splfee_300" value="specialFeeMap[300]"></s:set>
<s:set name="splfee_400" value="specialFeeMap[400]"></s:set>
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketingtype" id="tktingtype"></s:hidden>
<s:hidden name="servicefee" id="splserfee"></s:hidden>
<s:hidden name="source"></s:hidden>
<!-- RSVP START -->
<s:if test="%{powertype=='RSVP'}">
<div class="modal-header">
	<div class="col-sm-12 col-lg-12 col-md-12" style=" margin:0 auto;padding-left:0px;padding-right:0px;">
		<div class="upgrade-popup col-sm-12">
			<div class="col-sm-12 col-lg-12 col-md-12 upgrade_popup" >
				<div class="col-md-6 col-sm-6 col-lg-6 col-xs-6 listname"><s:text name="ugpp.basic.lbl"/></div>
				<div class="col-md-6  col-sm-6 col-lg-6 col-xs-6  listname"><s:text name="ugpp.pro.lbl"/></div>
			</div><div style="clear:both;"></div>
			
			<div class="upgrade_popup_body col-md-12 col-lg-12 col-sm-12">
				<div class="col-md-6 col-sm-6 col-lg-6 ">	
					<div class="col-md-12 col-lg-12 col-sm-12" style="color:#fff; padding:2px;">
						<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[90]}"/></font>
						<div style="height: 5px;"></div>
						<span class="big-font">/<s:text name="ugpp.registration.lbl"/></span>
					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 currentplan">
						<s:text name="ugpp.current.plan.lbl"/>
					</div>
				</div>
				
				<div class="col-md-6 col-sm-6 col-lg-6" >	
				 <div class="col-md-12 col-sm-12 col-lg-12" style="border: 1px solid rgb(255, 255, 255); border-radius: 50px;background-color:#fff;padding: 12px 0px">
					<div class="col-md-12 col-lg-12 col-sm-12" style="color:#003B80">
						<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[150]}"/></font>
						<div style="height: 5px;"></div>
						<span class="big-font" style="color:#003B80">/<s:text name="ugpp.registration.lbl"/></span>
					</div>
					<!-- <div class="col-md-12 col-lg-12 col-sm-12 currentplan" style="color:#003B80">
						<s:text name="ugpp.current.plan.lbl"/>
					</div> -->
				</div>
				</div>
			</div>
			
	<div class="modal-footer rsvp-content1">
		<div class="col-sm-12 col-lg-12 col-md-12 content1" style="padding-left:0px;padding-right:0px;padding-bottom:25px;" id="rsvp_arrow1">
		<div class="col-sm-12 col-lg-12 col-md-12 content1-text" id="rsvp_pro_div">
			<div class="col-sm-6 col-lg-6 col-md-6" style="margin:26px 0px;">
				<div id="pro_carousel" class="carousel slide" data-interval="false" align="center" >
					<!-- Wrapper for slides -->
					<div class="carousel-inner" align="center" >
						<div class="item <s:if test='%{source=="EventCustomURL" || source=="upgradeevent"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.custom.event.url.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="integrationwidget"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.rsvp.widget.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="TrackURL"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.tracking.urls.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="questions" || source=="tktquestions"}'>active</s:if>" >
							<div class="head-title two-lines"><s:text name="ugpp.custom.rsvp.form.question.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="RSVPWordCustomize"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.custom.wording.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="OrderConfirmation"}'>active</s:if>">
							<div class="head-title"><s:text name="ugpp.order.confirmation.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span><br/>
								<span class="med-font"><s:text name="ugpp.custom.confirm.email"/><br/><s:text name="ugpp.custom.confirm.page.lbl"/>
								</span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="AddAttendee"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.add.attendees.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="EmailAttendees"}'>active</s:if>"> 
							<div class="head-title one-line"><s:text name="ugpp.email.attendees.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="Badges"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.attendee.badges.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="SubManager"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.sub.managers.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<%-- <div class="item <s:if test='%{source=="BuyerPage"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="pg.toggle.buyer.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div> --%>
						<div class="item <s:if test='%{source=="EditEvent"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.password.protection.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<!-- Indicators -->
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#pro_carousel" data-slide="prev">
						<span><img  src="/main/images/pre.png" width="30px" style="margin-right:0px !important;padding-right:0px !important;"/></span>
					</a>
					<a class="right carousel-control" href="#pro_carousel" data-slide="next">
						<span><img  src="/main/images/next.png" width="30px"/></span>
					</a>
					<div class="col-lg-12" style="margin-top:50px;padding:0px;">
						<ol class="carousel-indicators" style="margin-top:20px;padding:0px;">
							<li data-target="#pro_carousel" data-slide-to="0" <s:if test='%{source=="EventCustomURL" || source=="upgradeevent"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="1" <s:if test='%{source=="integrationwidget"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="2" <s:if test='%{source=="TrackURL"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="3" <s:if test='%{source=="questions" || source=="tktquestions"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="4" <s:if test='%{source=="RSVPWordCustomize"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="5" <s:if test='%{source=="OrderConfirmation"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="6" <s:if test='%{source=="AddAttendee"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="7" <s:if test='%{source=="EmailAttendees"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="8" <s:if test='%{source=="Badges"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="9" <s:if test='%{source=="SubManager"}'>class="active"</s:if>></li>
							<!-- <li data-target="#pro_carousel" data-slide-to="" <s:if test='%{source=="BuyerPage"}'>class="active"</s:if>></li> -->
							<li data-target="#pro_carousel" data-slide-to="9" <s:if test='%{source=="EditEvent"}'>class="active"</s:if>></li>
						</ol>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-lg-6 col-md-6">
				<div class="col-sm-12 col-lg-12 col-md-12 upgrade-st-cont" style="font-weight:bold;">
					<s:text name="ugpp.gain.access.more.featurs.lbl"/><br/>
					<s:text name="ugpp.upgreding.tickt.level.lbl"/>								
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12 status" >
					(<s:text name="ugpp.collect.service.fee.lbl"/><br/>
					<s:text name="ugpp.attendee.use.eventbee.lbl"/>)
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12" style="margin-top: 10px;">
					<button type="submit" id="upgradesubmitbtn" class="btn upgrade-btn btn-responsive">
						<span id="upgrade_btn_lbl"><s:text name="ugpp.upgrate.pro.lbl"/></span>
					</button>
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12">
					<a data-dismiss="modal" aria-hidden="true" class="nothanks status"><s:text name="ugpp.no.thanks.lbl"/></a>
				</div>
			</div>
			</div>
			</div>		
		
	</div>
</div>
</div>
</div>
</s:if>
<!-- RSVP END -->
<!-- TICKETING START -->
<s:else>
<div class="modal-header">
<div class="row" style="margin:0px;">
<div class="col-sm-12 col-lg-12 col-md-12" style=" margin:0 auto;padding-left:0px;padding-right:0px;">
<div class="upgrade-popup col-sm-12">
<div class="col-sm-12 col-lg-12 col-md-12 upgrade_popup" >
	<div class="col-md-3 col-sm-3 col-lg-3 col-xs-3 listname"><s:text name="ugpp.basic.lbl"/></div>
	<div class="col-md-3  col-sm-3 col-lg-3 col-xs-3  listname"><s:text name="ugpp.pro.lbl"/></div>
	<div class="col-md-3  col-sm-3 col-lg-3 col-xs-3 listname"><s:text name="ugpp.advanced.lbl"/></div>
	<div class="col-md-3  col-sm-3 col-lg-3 col-xs-3 listname">Business</div>
</div>
<div style="clear:both;"></div>
<div class="upgrade_popup_body col-md-12 col-lg-12 col-sm-12">
<div class="col-md-3 col-sm-3 col-lg-3 basic-cont">	
	<div class="col-md-8 col-lg-8 col-sm-8" style="color:#fff; padding:2px;">
		
		<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[100]}"/></font>
		<div style="height: 5px;"></div>
		<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
	</div>
	<s:if test='%{specialFeeMap["cur_level"]==100}'>
	<div class="col-md-8 col-lg-8 col-sm-8 currentplan">
		<s:text name="ugpp.current.plan.lbl"/>
	</div>
	</s:if>
</div>

<div class="col-md-3 col-sm-3 col-lg-3 pro_content_main">
<s:if test='%{specialFeeMap["cur_level"]==200 || specialFeeMap["cur_level"]==300}'>
<div class="col-md-10 col-lg-10 col-sm-10 static-cont">
		<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[200]}"/></font>
		<div style="height: 5px;"></div>
		<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
	</div>
		<s:if test='%{specialFeeMap["cur_level"]==200}'>
			<div class="col-md-10 col-lg-10 col-sm-10 currentplan">
				<s:text name="ugpp.current.plan.lbl"/>
			</div>
		</s:if>
	</s:if>
	<s:else>
		<div class="col-md-8 col-lg-8 col-sm-8 dyna-cont">
		<a onclick="showBusiness('pro');">
			<div class="col-md-10 pro_content <s:if test='%{specialFeeMap["ch_level"]==200}'>open-active</s:if>" id="pro_top_block">
				<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[200]}"/><%-- <sup><font style="font-size:11px;">50</font></sup> --%></font>
				<!-- <br/> -->
				<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
			</div>
			</a>
		</div>
</s:else>
</div>

<div class="col-md-3  col-lg-3 col-sm-3 pro_content_main" style="margin: 0;">
	<s:if test='%{specialFeeMap["cur_level"]==300}'>
	<div class="col-md-10 col-lg-10 col-sm-10 static-cont">
		<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[300]}"/></font>
		<div style="height: 5px;"></div>
		<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
	</div>
		<div class="col-md-10 col-lg-10 col-sm-10 currentplan">
			<s:text name="ugpp.current.plan.lbl"/>
		</div>
	</s:if>
	<s:else>
		<div class="col-md-8 col-lg-8 col-sm-8 adv-dyna-cont">
		<a onclick="showBusiness('advanced');">
			<div class="col-md-10 pro_content <s:if test='%{specialFeeMap["ch_level"]==300}'>open-active</s:if>" id="adv_top_block">
				<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[300]}"/></font>
				<div style="height: 5px;"></div>
				<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
			</div>
			</a>
		</div>
		</s:else>
</div>

<div class="col-md-3  col-lg-3 col-sm-3 pro_content_main" style="margin: 0;">
	<s:if test='%{specialFeeMap["cur_level"]==400}'>
	<div class="col-md-8 col-lg-8 col-sm-8 static-cont">
		<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[400]}"/></font>
		<div style="height: 5px;"></div>
		<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
	</div>
		<div class="col-md-8 col-lg-8 col-sm-8 currentplan">
			<s:text name="ugpp.current.plan.lbl"/>
		</div>
	</s:if>
	<s:else>
		<div class="col-md-8 col-lg-8 col-sm-8 adv-dyna-cont">
		<a onclick="showBusiness('business');">
			<div class="col-md-10 pro_content <s:if test='%{specialFeeMap["ch_level"]==400}'>open-active</s:if>" id="bus_top_block">
				<font class="too-big-font">${currencySymbol}<s:property value="%{specialFeeMap[400]}"/></font>
				<div style="height: 5px;"></div>
				<span class="big-font">/<s:text name="ugpp.ticket.lbl"/></span>
			</div>
			</a>
		</div>
		</s:else>
</div>

	
</div>
							
<div class="modal-footer" >
	<div class="col-sm-12 col-lg-12 col-md-12 <s:if test='%{specialFeeMap["ch_level"]==200}'>content1</s:if><s:elseif test='%{specialFeeMap["ch_level"]==300}'>content2</s:elseif><s:else>content3</s:else>"  
	style="padding-left:0px;padding-right:0px;padding-bottom:25px;" id="arrow1">
		<div class="col-sm-12 col-lg-12 col-md-12 content1-text" id="pro-div">
			<!--carousel start-->
			<div class="col-sm-6 col-lg-6 col-md-6" style="margin:26px 0px;">
				<div id="pro_carousel" class="carousel slide" data-interval="false" align="center" style="<s:if test='%{specialFeeMap["ch_level"]==200}'>display:block;</s:if><s:else>display:none;</s:else>">
					<!-- Wrapper for slides -->
					<div class="carousel-inner" align="center" >
						<div class="item <s:if test='%{source=="EventCustomURL" || source=="upgradeevent"}'>active</s:if>" id="pro_first_item">
							<div class="head-title one-line"><s:text name="ugpp.custom.event.url.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="managediscounts"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.discounts.lbl"/> <span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="integrationwidget"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.tickts.widget.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						
						<div class="item <s:if test='%{source=="TicketSettings"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.tickt.setings.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="RegWordCustomize"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.custom.wording.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="showhidetickets"}'>active</s:if>" >
							<div class="head-title one-line"><s:text name="ugpp.show.hide.tickts.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item">
							<div class="head-title one-line"><s:text name="ugpp.custom.service.fee.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="EditEvent"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.password.protection.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item">
							<div class="head-title two-lines"><s:text name="ugpp.custom.creditcard.proess.fee.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<!-- Indicators -->
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#pro_carousel" data-slide="prev">
						<span><img  src="/main/images/pre.png" width="30px" style="margin-right:0px !important;padding-right:0px !important;"/></span>
					</a>
					<a class="right carousel-control" href="#pro_carousel" data-slide="next">
						<span><img  src="/main/images/next.png" width="30px"/></span>
					</a>
					<div class="col-lg-12" style="margin-top:50px;padding:0px;">
						<ol class="carousel-indicators" style="margin-top:20px;padding:0px;">
							<li data-target="#pro_carousel" data-slide-to="0" id="pro_first_li" <s:if test='%{source=="EventCustomURL" || source=="upgradeevent"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="1" <s:if test='%{source=="managediscounts"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="2" <s:if test='%{source=="integrationwidget"}'>class="active"</s:if>></li>
							
							<li data-target="#pro_carousel" data-slide-to="3" <s:if test='%{source=="TicketSettings"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="4" <s:if test='%{source=="RegWordCustomize"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="5" <s:if test='%{source=="showhidetickets"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="6"></li>
							<li data-target="#pro_carousel" data-slide-to="7" <s:if test='%{source=="EditEvent"}'>class="active"</s:if>></li>
							<li data-target="#pro_carousel" data-slide-to="8"></li>
						</ol>
					</div>
				</div>  <!-- pro-carousel end-->
				
				<!-- adv-carousel end--> 
				<div id="adv_carousel" class="carousel slide"  data-interval="false" align="center" style="<s:if test='%{specialFeeMap["ch_level"]==300}'>display:block;</s:if><s:else>display:none;</s:else>">
					<!-- Wrapper for slides -->
					<div class="carousel-inner" align="center" >
						<div class="item <s:if test='%{source=="WaitList" || source=="upgradeevent"}'>active</s:if>" id="adv_first_item">
							<div class="head-title one-line"><s:text name="ugpp.waitlist.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="TrackURL"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.tracking.urls.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="TicketURLs"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.private.urls.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.pro.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="OrderConfirmation"}'>active</s:if>" >
							<div class="head-title"><s:text name="ugpp.order.confirmation.lbl"/> <span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span><br/>
								<span class="status"><s:text name="ugpp.custom.confirm.email"/><br/>
									<s:text name="ugpp.custom.confirm.page.lbl"/><br/>
									<s:text name="ugpp.custom.waitlist.email.lbl"/>
								</span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="Seating"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.venue.seating.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="EmailAttendees"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.email.attendees.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="Badges"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.attendee.badges.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span>
							</div>											
						</div>
						
						<div class="item <s:if test='%{source=="questions" || source=="tktquestions"}'>active</s:if>">
							<div class="head-title two-lines"><s:text name="ugpp.custom.reg.form.questions.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="ugpp.adv.lbl"/></span>
							</div>											
						</div>
						<!-- Indicators -->
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#adv_carousel" data-slide="prev">
					<span><img  src="/main/images/pre.png" width="30px" style="margin-right:0px !important;padding-right:0px !important;"/></span>
					</a>
					<a class="right carousel-control" href="#adv_carousel" data-slide="next">
						<span><img src="/main/images/next.png" width="30px"/></span>
					</a>
					<div class="col-lg-12" style="margin-top:50px;padding:0px;">
						<ol class="carousel-indicators" style="margin-top:20px;padding:0px;">
							<li data-target="#adv_carousel" id="adv_first_li" data-slide-to="0" <s:if test='%{source=="WaitList" || source=="upgradeevent"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="1" <s:if test='%{source=="TrackURL"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="2" <s:if test='%{source=="TicketURLs"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="3" <s:if test='%{source=="OrderConfirmation"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="4" <s:if test='%{source=="Seating"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="5" <s:if test='%{source=="EmailAttendees"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="6" <s:if test='%{source=="Badges"}'>class="active"</s:if>></li>
							<li data-target="#adv_carousel" data-slide-to="7" <s:if test='%{source=="questions" || source=="tktquestions"}'>class="active"</s:if>></li>
						</ol>
					</div>
				</div>
				<!-- adv-carousel end -->
				
				<!-- bus-carousel start -->
				<div id="bus_carousel" class="carousel slide"  data-interval="false" align="center" style="<s:if test='%{specialFeeMap["ch_level"]==400}'>display:block;</s:if><s:else>display:none;</s:else>">
					<!-- Wrapper for slides -->
					<div class="carousel-inner" align="center" >
						<div class="item <s:if test='%{source=="TicketingRules" || source=="upgradeevent"}'>active</s:if>" id="bus_first_item">
							<div class="head-title one-line"><s:text name="ugpp.tickting.rules.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="business.tip"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="PriorityRegistration"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.priority.reg.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="business.tip"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="SubManager"}'>active</s:if>">
							<div class="head-title one-line"><s:text name="ugpp.sub.managers.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="business.tip"/></span>
							</div>											
						</div>
						<div class="item <s:if test='%{source=="BuyerPage"}'>active</s:if>" >
							<div class="head-title two-lines"><s:text name="pg.toggle.buyer.lbl"/><span class="carousel-tkt-btn-lbl"><s:text name="business.tip"/></span>
							</div>											
						</div>
						<!-- Indicators -->
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#bus_carousel" data-slide="prev">
					<span><img  src="/main/images/pre.png" width="30px" style="margin-right:0px !important;padding-right:0px !important;"/></span>
					</a>
					<a class="right carousel-control" href="#bus_carousel" data-slide="next">
						<span><img src="/main/images/next.png" width="30px"/></span>
					</a>
					<div class="col-lg-12" style="margin-top:50px;padding:0px;">
						<ol class="carousel-indicators" style="margin-top:20px;padding:0px;"> 
							<li data-target="#bus_carousel" id="bus_first_li" data-slide-to="0" <s:if test='%{source=="TicketingRules" || source=="upgradeevent"}'>class="active"</s:if>></li>
							<li data-target="#bus_carousel" data-slide-to="1" <s:if test='%{source=="PriorityRegistration"}'>class="active"</s:if>></li>
							<li data-target="#bus_carousel" data-slide-to="2" <s:if test='%{source=="SubManager"}'>class="active"</s:if>></li>
							<li data-target="#bus_carousel" data-slide-to="3" <s:if test='%{source=="BuyerPage"}'>class="active"</s:if>></li>
						</ol>
					</div>
				</div>
				<!-- bus-carousel end -->
				
			</div> <!-- carousel-root -->
			<div class="col-sm-6  col-lg-6 col-md-6">
				<div class="col-sm-12 col-lg-12 col-md-12 upgrade-st-cont" style="font-weight:bold;">
					<s:text name="ugpp.gain.access.more.featurs.lbl"/><br/>
					<s:text name="ugpp.upgreding.tickt.level.lbl"/>								
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12 status" >
					(<s:text name="ugpp.collect.service.fee.lbl"/><br/>
					<s:text name="ugpp.attendee.use.eventbee.lbl"/>)
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12" style="margin-top: 10px;">
					<button type="submit" id="upgradesubmitbtn" class="btn upgrade-btn btn-responsive">
						<span id="upgrade_btn_lbl"><s:if test='%{specialFeeMap["ch_level"]==200}'><s:text name="ugpp.upgrate.pro.lbl"/></s:if><s:elseif test='%{specialFeeMap["ch_level"]==300}'><s:text name="ugpp.upgrate.advanced.lbl"/></s:elseif><s:else>Upgrade to Business</s:else></span>
					</button>
				</div>
				<div class="col-sm-12 col-lg-12 col-md-12">
					<a data-dismiss="modal" aria-hidden="true" class="nothanks status"><s:text name="ugpp.no.thanks.lbl"/></a>
				</div>
			</div>
		</div> <!-- content1-text end -->
	</div> <!-- content1 end -->
</div><!-- modal-footer end -->
</div><!-- upgrade_popup_header -->
</div><!-- root 3 -->
</div><!-- row end -->
</div><!-- modal-header end -->	
</s:else>
<!-- TICKETING END -->
</s:form>	
<script>
var changeLevel='${specialFeeMap["ch_level"]}';
var selector = '.pro_content';
function proArrowBlock(){
	$('#arrow1').addClass('content1');
	$('#arrow1').removeClass('content2');
	$('#arrow1').removeClass('content3');
	if(changeLevel!='200'){
		$("#pro_first_item").addClass("active");
		$("#pro_first_li").addClass("active");
	}
	$('#pro-div').slideDown(500);
	$('#pro_carousel').show();
	$('#adv_carousel').hide();
	$('#bus_carousel').hide();
	$('#upgrade_btn_lbl').html(props.ugpp_upgrate_pro_lbl);
	$("#splserfee").val('${splfee_200}');
	$("#tktingtype").val('200');
	if(changeLevel=='300' || changeLevel=='400'){
		$('#upgradesubmitbtn').attr('disabled','disabled');
	}
}

function advArrowBlock(){
	$('#arrow1').removeClass('content1');
	$('#arrow1').removeClass('content3');
	$('#arrow1').addClass('content2');
	if(changeLevel!='300'){
		$("#adv_first_item").addClass("active");
		$("#adv_first_li").addClass("active");
	}
	$('#pro-div').slideDown(500);
	$('#pro_carousel').hide();
	$('#adv_carousel').show();
	$('#bus_carousel').hide();
	$('#upgrade_btn_lbl').html(props.ugpp_upgrate_advanced);
	$("#splserfee").val('${splfee_300}');
	$("#tktingtype").val('300');
	if(changeLevel=='400'){
		$('#upgradesubmitbtn').attr('disabled','disabled');
	}
}

function busArrowBlock(){
	$('#arrow1').removeClass('content2');
	$('#arrow1').removeClass('content1');
	$('#arrow1').addClass('content3');
	if(changeLevel!='400'){
		$("#bus_first_item").addClass("active");
		$("#bus_first_li").addClass("active");
	}
	$('#pro-div').slideDown(500);
	$('#pro_carousel').hide();
	$('#adv_carousel').hide();
	$('#bus_carousel').show();
	$('#upgrade_btn_lbl').html('Upgrade to Business');
	$("#splserfee").val('${splfee_400}');
	$("#tktingtype").val('400');
	if(changeLevel=='400'){
		$('#upgradesubmitbtn').removeAttr('disabled');
	}
}
function showBusiness(id){
	if(id=='pro'){					
		proArrowBlock();
	}
	else if(id=='advanced'){
		advArrowBlock();
	}
	else if(id=='business'){
		busArrowBlock();
	}
}
$(document).ready(function(){
	var chlevel='${specialFeeMap["ch_level"]}';
	if(chlevel==200){
		
		$("#splserfee").val('${splfee_200}');
		$("#tktingtype").val('200');
	}else if(chlevel==300){
		
		$("#splserfee").val('${splfee_300}');
		$("#tktingtype").val('300');
	}else if(chlevel==400){
		
		$("#splserfee").val('${splfee_400}');
		$("#tktingtype").val('400');
	}
	
	$('.nothanks').click(function() {
        parent.closeUpgradePopup();
    });
	
	$('#upgradesubmitbtn').click(function(){
		parent.submitform($('#specialfee').serialize());
	});
});
$(selector).on('click', function(){
	$(selector).removeClass('open-active');
	$(this).addClass('open-active');
});
</script>														