 <%@taglib uri="/struts-tags" prefix="s" %>
 <%@include file="../getresourcespath.jsp" %>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/eventmanage/specialfee.js?timestamp=<%=new java.util.Date()%>"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/braintreestates.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/states.js"></script>

<script>
var poweredbyEB='<s:text name="poweredbyEB" />';

function handleManageTickets(){
	var powertype='<s:text name="powertype" />';

	var eventstatus='<s:text name="eventstatus" />';
	if(poweredbyEB=='yes'){
	window.location.href='ManageTickets?eid=${eid}';
	}else{
		  bootbox.confirm("<h3>"+props.myevents_update_payment_msg1+"</h3>"+props.myevents_update_payment_msg2, function (result) {
	           if (result){
	        	   window.location.href='PaymentSettings?eid=${eid}';
	          } 
	          });
	
	}
	}



function handlePaymentSettings(){
	//alert('Please update your Payment Settings first, then add Tickets to perform all other event management tasks.');

	  bootbox.confirm("<h3>"+props.myevents_update_payment_msg1+"</h3>"+props.myevents_update_payment_msg2, function (result) {
          /* if (result){
          	alert("result::"+result);
          } */
          });

	
	//window.location.href='PaymentSettings?eid=${eid}';
	}

</script>

<style>
.left-item-active{
	margin-right: 15px;
}
.left-item-active .arrow_box {
	position: relative;
	background: #5388C4;
	#border: 4px solid #c2e1f5;
	    padding: 4px 7px 4px 10px;
	    line-height: 22px !important;
	    #margin-right: 10px;
}
.left-item-active .arrow_box:after, .left-item-active .arrow_box:before {
	left: 100%;
	top: 50%;
	border: solid transparent;
	content: " ";
	height: 0;
	width: 0;
	position: absolute;
	pointer-events: none;
}

.left-item-active .arrow_box:after {
	border-color: rgba(136, 183, 213, 0);
    border-left-color: #5388C4;
    border-width: 13px;
    margin-top: -13px;
}
.left-item-active .arrow_box:before {
	#border-color: rgba(194, 225, 245, 0);
	#border-left-color: #c2e1f5;
	#border-width: 36px;
	#margin-top: -36px;
}
.left-item-active a{
	color:#fff !important;;
}
.pro-orange{
	background-color: #F90;
    padding: 1px 5px;
    margin-left: 5px;
    font-size:13px;
    opacity: 0.2;
    color: #fff;
}

li .pro-orange:hover{
	background-color: #F90;
    padding: 1px 5px;
    margin-left: 5px;
    font-size:13px;
    color: #fff;
    opacity: 100;
}
li{
	margin-right: 22px;
}
</style>

<s:set name="poweredbyEB" value="poweredbyEB"></s:set>
<s:set name="eventstatus" value="eventstatus"></s:set>
<s:set name="currentlevel" value="currentLevel"></s:set>
<s:set name="currentfee" value="currentFee"></s:set>
 <s:set name="actionName" value="menuMap[#attr['currentaction']]"/>
 <s:set name="powertype" value="powertype"></s:set>
<s:hidden value="%{actionName}" id="actionClassName"/>
<s:set var="ManageList" value="{'Snapshot','EventPageContent','EmailAttendees','EventDetails','ManageTickets','DisplayTickets','RSVPSettings','PaymentSettings','ManageRegistrationForm','ManageDiscounts','integrationeventurl','integrationbuttons','integrationwidget','AddAttendee','TicketURLs','NetworkTicketSelling','ScanIDs','SubManager','WaitList','PriorityRegistration','PrintedTickets','ManagerAppSettings','EditEvent','Badges','RegWordCustomize','RSVPWordCustomize','OrderConfirmation','Seating','IntegrationLinks','TrackURL','TicketSettings','TicketingRules'}" />
<s:set var= "designList" value="{'LooknFeel','LayOutManager'}" />
<s:set var="reportList" value="{'Reports','RSVPReports','TransactionDetails', 'searchattendee'}" />
<a id="menu-toggle" href="javascript:;" class="btn-lg toggle"><i class="glyphicon glyphicon-chevron-right"></i></a>
	<div id="sidebar-wrapper" class="active" style="overflow: hidden;">
	<ul class="sidebar-nav" id="featureslist">
	
	
	<s:if test="%{#powertype=='Ticketing'}">
	<div class="side-menu-header ticketHead" style="cursor:pointer; color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl1"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>
	<span class="openFirstDiv" style="display:none;">
	</s:if>
	<s:if test="%{#powertype=='RSVP'}">
	<div class="side-menu-header ticketHead" style="cursor:pointer;color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl3"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>
	<span class="openFirstDiv" style="display:none;">
	</s:if>
	
	
	
<s:if test="%{#actionName in #ManageList}">
	<li class="<s:if test='%{#actionName=="Snapshot"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="Snapshot"}' >Snapshot?eid=${eid}</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.summary.lbl"/></span></a></li>
</s:if>
		
<s:if test="%{(#powertype=='Ticketing' && #poweredbyEB=='yes') ||(#powertype=='RSVP')}">	
	<s:if test="%{#actionName in #ManageList}">
		<s:if test="%{#powertype=='Ticketing'}">
		
		<%-- tickets --%>
		    	<s:if test="%{submgr_permissions['ManageTickets']=='yes'}">
		 			<li class="<s:if test='%{#actionName=="ManageTickets"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="ManageTickets"}' >javascript:handleManageTickets();</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.tkts.lbl"/></span></a></li>
				</s:if>
		<%-- discounts --%>
				<s:if test="%{submgr_permissions['ManageDiscounts']=='yes'}">
					<li class="<s:if test='%{#actionName=="ManageDiscounts"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="ManageDiscounts"}' >specialFee("${eid}","200","managediscounts","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.disc.lbl"/></span></a></li>
				</s:if>
		<%-- Integration --%>
				<s:if test="%{submgr_permissions['IntegrationLinks']=='yes'}">
					<li class="<s:if test='%{#actionName=="IntegrationLinks"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="IntegrationLinks"}' >specialFee("${eid}","200","IntegrationLinks","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.integrations.lbl"/></span></a></li>
				</s:if>
		<%-- waitlist --%>
				<%-- <s:if test="%{isrecurring==false}">
					<s:if test="%{submgr_permissions['WaitList']=='yes'}">
			   			<li class="<s:if test='%{#actionName=="WaitList"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="WaitList"}' >specialFee("${eid}","300","WaitList","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.waitlist.lbl"/></span></a></li>
					</s:if>
	      		</s:if> --%>
	    <%-- TrackUrls --%>
				<s:if test="%{submgr_permissions['TrackURL']=='yes'}">
					<li class="<s:if test='%{#actionName=="TrackURL"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TrackURL"}' >specialFee("${eid}","300","TrackURL","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.trackurl.lbl"/></span></a></li>
				</s:if>
		<%-- TicketURLs --%>
				<s:if test="%{submgr_permissions['TicketURLs']=='yes'}">
					<li class="<s:if test='%{#actionName=="TicketURLs"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TicketURLs"}' >specialFee("${eid}","300","TicketURLs","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.private.tkturl.lbl"/></span></a> </li>
				</s:if>
		<%-- TicketSettings --%>
					<s:if test="%{submgr_permissions['DisplayTickets']=='yes' || submgr_permissions['ManageTickets']=='yes'}">
						<li class="<s:if test='%{#actionName=="TicketSettings"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TicketSettings"}' >specialFee("${eid}","200","TicketSettings","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.tkt.settingg.lbl"/></span></a> </li>
					</s:if>
		<%-- PaymentSettings --%>
				 <s:if test="%{submgr_permissions['PaymentSettings']=='yes'}">
					 <li class="<s:if test='%{#actionName=="PaymentSettings"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="PaymentSettings"}' >PaymentSettings?eid=${eid}</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.payment.settings.lbl"/></span></a></li>
				 </s:if>
		</span>
			
		<div class="side-menu-header customizationHead" style="cursor:pointer;color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>	 
		<span class="openSecondDiv" style="display:none;">
		<%-- Eventdetails --%>
				<s:if test="%{submgr_permissions['EditEvent']=='yes' || submgr_permissions['EventPageContent']=='yes'}">
					<li class="<s:if test='%{#actionName=="EditEvent"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="EditEvent"}' >EditEvent?eid=${eid}</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.event.details.lbl"/></span></a></li>
				</s:if>	
		<%-- Questions --%>
				<s:if test="%{submgr_permissions['ManageRegistrationForm']=='yes'}">	 
					<li class="<s:if test='%{#actionName=="ManageRegistrationForm"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="ManageRegistrationForm"}' >specialFee("${eid}","300","ManageRegistrationForm","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.questions.lbl"/></span></a></li>
				</s:if>
		<%-- RegWordCustomize --%>		
				<s:if test="%{submgr_permissions['RegWordCustomize']=='yes'}">	
					<li class="<s:if test='%{#actionName=="RegWordCustomize"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="RegWordCustomize"}' >specialFee('${eid}','200','RegWordCustomize','Ticketing');</s:if>" title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.wording.lbl"/></span></a></li>
				</s:if>
		<%-- OrderConfirmation --%>
				<s:if test="%{submgr_permissions['OrderConfirmation']=='yes'}">
					<li class="<s:if test='%{#actionName=="OrderConfirmation"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="OrderConfirmation"}' >specialFee('${eid}','300','OrderConfirmation','Ticketing');</s:if>" title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.orderconfirmation.lbl"/></span></a></li>
				</s:if>
		<%-- PriorityRegistration --%>
				<s:if test="%{submgr_permissions['PriorityRegistration']=='yes'}">
					<li class="<s:if test='%{#actionName=="PriorityRegistration"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="PriorityRegistration"}' >specialFee('${eid}','300','PriorityRegistration','Ticketing');</s:if>" title="<s:text name="submanager.bus.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.priority.reg.lbl"/></span></a></li>
				</s:if>
		<%-- Seating --%>
				<s:if test="%{submgr_permissions['Seating']=='yes'}">
				 	<li class="<s:if test='%{#actionName=="Seating"}' >left-item-active</s:if>"><a href="javascript:;"   onclick="<s:if test='%{#actionName!="Seating"}' >specialFee('${eid}','300','Seating','Ticketing');</s:if>" title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.seating.lbl"/></span></a></li>
				 </s:if>
		</span>
		
		
		<div class="side-menu-header adminHead" style="cursor:pointer;color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl2"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>	 
		<span class="openThiredDiv" style="display:none;">	
		<%-- AddAttendee --%>
				<s:if test="%{submgr_permissions['AddAttendee']=='yes'}">
				 	<li class="<s:if test='%{#actionName=="AddAttendee"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="AddAttendee"}' >AddAttendee?eid=${eid}&purpose=manageradd</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.add.attendee.lbl"/></span></a></li>
				 </s:if>
		<%-- EmailAttendees --%>
				<s:if test="%{submgr_permissions['EmailAttendees']=='yes'}">
					<li class="<s:if test='%{#actionName=="EmailAttendees"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="EmailAttendees"}' >specialFee("${eid}","300","EmailAttendees","Ticketing")</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.email.attendees.lbl"/></span></a></li>
				</s:if>
		<%-- Badges --%>
				<s:if test="%{submgr_permissions['Badges']=='yes'}">
					<li class="<s:if test='%{#actionName=="Badges"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="Badges"}' >specialFee("${eid}","300","Badges","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.badges.lbl"/></span></a></li>
				</s:if>
		<%-- ScanIDs --%>
				<s:if test="%{submgr_permissions['ScanIDs']=='yes'}">
					<li class="<s:if test='%{#actionName=="ScanIDs"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="ScanIDs"}' >specialFee("${eid}","300","ScanIDs","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.adv.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.scanids.lbl"/></span></a></li>
				</s:if>
		</span>
		
		</s:if>
		
		<%-- RSVP --%>
		
		<s:if test="%{#powertype=='RSVP'}">
		<%-- RSVPSettings --%>
			<s:if test="%{submgr_permissions['RSVPSettings']=='yes'}">
		    	<li class="<s:if test='%{#actionName=="RSVPSettings"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="RSVPSettings"}'>RSVPSettings?eid=${eid}</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.rsvp.settings.lbl"/></span></a></li>
		 	</s:if>
		<%-- Integration --%>
		 	<s:if test="%{submgr_permissions['IntegrationLinks']=='yes'}">
		 		<li class="<s:if test='%{#actionName=="IntegrationLinks"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="IntegrationLinks"}' >specialFee("${eid}","150","IntegrationLinks","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.integrations.lbl"/></span></a></li>
		 	</s:if>
		<%-- TrackUrls --%>
			<s:if test="%{submgr_permissions['TrackURL']=='yes'}">
		 		<li class="<s:if test='%{#actionName=="TrackURL"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TrackURL"}' >specialFee("${eid}","150","TrackURL","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.trackurl.lbl"/></span></a></li>
		 	</s:if>
		 	</span>
		 	
		 	
		<div class="side-menu-header customizationHead" style="cursor:pointer;color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>	 
		<span class="openSecondDiv" style="display:none;">
		<%-- EditEvent --%>
		 	<s:if test="%{submgr_permissions['EditEvent']=='yes' || %{submgr_permissions['EventPageContent']=='yes'}}">
				<li class="<s:if test='%{#actionName=="EditEvent"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="EditEvent"}' >EditEvent?eid=${eid}</s:if><s:else>javascript:;</s:else>'><span class='arrow_box'><s:text name="em.actions.event.details.lbl"/></span></a></li>
			</s:if>
		<%-- ManageRegistrationForm --%>
		 	<s:if test="%{submgr_permissions['ManageRegistrationForm']=='yes'}">
				<li class="<s:if test='%{#actionName=="ManageRegistrationForm"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="ManageRegistrationForm"}' >specialFee("${eid}","150","ManageRegistrationForm","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.questions.lbl"/></span></a></li>
			</s:if>
		<%-- RSVPWordCustomize --%>
		 	<s:if test="%{submgr_permissions['RSVPWordCustomize']=='yes'}">
				<li class="<s:if test='%{#actionName=="RSVPWordCustomize"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="RSVPWordCustomize"}'>specialFee('${eid}','150','RSVPWordCustomize','RSVP');</s:if>" title="<s:text name="submanager.pro.rsvp"/>"><span class='arrow_box'><s:text name="em.actions.wording.lbl"/></span></a></li>
			</s:if>
		<%-- OrderConfirmation --%>
			<s:if test="%{submgr_permissions['OrderConfirmation']=='yes'}">
				<li class="<s:if test='%{#actionName=="OrderConfirmation"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="OrderConfirmation"}' >specialFee('${eid}','150','OrderConfirmation','RSVP');</s:if>" title="<s:text name="submanager.pro.rsvp"/>"><span class='arrow_box'><s:text name="em.actions.orderconfirmation.lbl"/></span></a></li>
			</s:if>
		</span>
		
		<div class="side-menu-header adminHead" style="cursor:pointer;color:#5388C4 !important;margin-bottom: 5px;"><s:text name="em.action.head.lbl2"/><i class="glyphicon glyphicon-menu-right original" style="font-size: 15px; margin-left: 5px;"></i></div>	 
		<span class="openThiredDiv" style="display:none;">
		<%-- Addattendee --%>
			<s:if test="%{submgr_permissions['AddAttendee']=='yes'}">
				<li class="<s:if test='%{#actionName=="AddAttendee"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="AddAttendee"}' >specialFee("${eid}","150","AddAttendee","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.add.attendee.lbl"/></span></a></li>
			</s:if>
		<%-- EmailAttendees --%>
			<s:if test="%{submgr_permissions['EmailAttendees']=='yes'}">
				<li class="<s:if test='%{#actionName=="EmailAttendees"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="EmailAttendees"}' >specialFee("${eid}","150","EmailAttendees","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.rsvp"/>"><span class='arrow_box'><s:text name="em.actions.email.attendees.lbl"/></span></a></li>
			</s:if>
		<%-- Badges --%>
			<s:if test="%{submgr_permissions['Badges']=='yes'}">
				<li class="<s:if test='%{#actionName=="Badges"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="Badges"}' >specialFee("${eid}","150","Badges","RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.rsvp"/>"><span class='arrow_box'><s:text name="em.actions.badges.lbl"/></span></a></li>
			</s:if>
		<%-- ScanIDs --%>
			<s:if test="%{submgr_permissions['ScanIDs']=='yes'}">
				<li class="<s:if test='%{#actionName=="ScanIDs"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="ScanIDs"}' >specialFee("${eid}","150","ScanIDs","Ticketing");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.ticketing"/>"><span class='arrow_box'><s:text name="em.actions.scanids.lbl"/></span></a></li>
			</s:if>
		</span>
		 </s:if>
</s:if>

<s:if test="%{#actionName in #designList}">
	<s:if test="%{submgr_permissions['LooknFeel']=='yes'}">
	<li class="<s:if test='%{#actionName=="LooknFeel"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="LooknFeel"}' >LooknFeel?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.looknfeel.lbl"/></a></li>
	</s:if>
	
	<s:if test="%{submgr_permissions['LayOutManager']=='yes'}">
	<li class="<s:if test='%{#actionName=="LayOutManager"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="LayOutManager"}' >LayOutManager?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.page.design.lbl"/></a></li>
	</s:if>
	
	<s:if test="%{submgr_permissions['EventPageContent']=='yes'}">
	<li class="<s:if test='%{#actionName=="EventPageContent"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="EventPageContent"}' >EventPageContent?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.content.lbl"/></a></li>
	</s:if>

</s:if>
<%-- design options end --%>
<s:if test="%{#actionName in #reportList}">
	<%-- reports options start --%>
	<s:if test="%{#powertype=='Ticketing'}">
		<s:if test="%{submgr_permissions['Reports']=='yes'}">
			 <li class="<s:if test='%{#actionName=="Reports"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="Reports"}' >Reports?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.reports.lbl"/></a></li>
			 <li class="<s:if test='%{#actionName=="Reports"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="Reports"}' >Reports?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.attendee.list.lbl"/></a></li>
		</s:if>
		 <s:if test="%{submgr_permissions['TransactionDetails']=='yes'}">
			<li class="<s:if test='%{#actionName=="TransactionDetails"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="TransactionDetails"}' >TransactionDetails?eid=${eid}</s:if><s:else>javascript:;</s:else>'>>s:text name="em.actions.searchattendee.lbl"/></a></li>
		</s:if>
	</s:if>
	<s:if test="%{#powertype=='RSVP'}">
		<s:if test="%{submgr_permissions['RSVPReports']=='yes'}">
			<li class="<s:if test='%{#actionName=="RSVPReports"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="RSVPReports"}' >RSVPReports?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.rsvp.reports.lbl"/></a></li>
		</s:if>
		<s:if test="%{submgr_permissions['TransactionDetails']=='yes'}">
			<li class="<s:if test='%{#actionName=="TransactionDetails"}'>left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TransactionDetails"}'>specialFee("${eid}","150", "TransactionDetails", "RSVP");</s:if><s:else>javascript:;</s:else>' title="<s:text name="submanager.pro.rsvp"/>"><s:text name="em.actions.searchattendee.lbl"/></a></li>
		</s:if>
	</s:if>
</s:if>
<%-- reports options end --%>
</s:if>
	</ul>
	<%-- <li class="<s:if test='%{#actionName=="EventDetails"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="EventDetails"}' >EventDetails?eid=${eid}</s:if><s:else>javascript:;</s:else>'>Event Details</a></li> --%>
	<%-- design options --%>
	<%--<s:if test="%{#powertype=='Ticketing'}">
	<s:if test="%{submgr_permissions['DisplayTickets']=='yes'}">
	<li class="<s:if test='%{#actionName=="DisplayTickets"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="DisplayTickets"}' >specialFee("${eid}","200","DisplayTickets","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Pro Ticketing">Ticket Display</a></li>
	</s:if> 
	
	<s:if test="%{submgr_permissions['ConfirmationPageSettings']=='yes'}">
	<li class="<s:if test='%{#actionName=="ConfirmationPageSettings"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="ConfirmationPageSettings"}' >specialFee('${eid}','300','ConfirmationPageSettings','Ticketing');</s:if>">Confirmation Page&nbsp;<img src="<%=resourceaddress%>/main/images/advanced.jpg" alt="Advanced  Ticketing" title="Advanced Ticketing"/></a></li>
	</s:if>
	
	<s:if test="%{submgr_permissions['RegEmailSettings']=='yes'}">
	<li class="<s:if test='%{#actionName=="RegEmailSettings"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="RegEmailSettings"}' >specialFee('${eid}','300','RegEmailSettings','Ticketing');</s:if>">Confirmation Email&nbsp;<img src="<%=resourceaddress%>/main/images/advanced.jpg" alt="Advanced  Ticketing" title="Advanced Ticketing"/></a></li>
	</s:if> 
	</s:if> --%>
	
	<%-- <s:if test="%{#powertype=='RSVP'}">
	

	</s:if> --%>
	<%-- <s:if test="%{submgr_permissions['IntegrationLinks']=='yes' || submgr_permissions['TrackURL']=='yes'}">
			<li class="<s:if test='%{#actionName=="integrationeventurl"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="integrationeventurl"}' >specialFee("${eid}","200","integrationeventurl","Ticketing");</s:if><s:else>javascript:;</s:else>'>Event URL&nbsp;<img src="<%=resourceaddress%>/main/images/pro.jpg" alt="Pro Ticketing" title="Pro Ticketing"/></a></li>
			<li class="<s:if test='%{#actionName=="integrationbuttons"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="integrationbuttons"}' >IntegrationLinks?eid=${eid}&purpose=buttons</s:if><s:else>javascript:;</s:else>'>Buttons Code</a></li>
			<li class="<s:if test='%{#actionName=="integrationwidget"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="integrationwidget"}' >specialFee("${eid}","200","integrationwidget","Ticketing");</s:if><s:else>javascript:;</s:else>'>Widget Code&nbsp;<img src="<%=resourceaddress%>/main/images/pro.jpg" alt="Pro Ticketing" title="Pro Ticketing"/></a></li>
		 <li class="<s:if test='%{#actionName=="IntegrationLinks"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="IntegrationLinks"}' >specialFee("${eid}","200","IntegrationLinks","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Pro Ticketing"><s:text name="em.actions.integrations.lbl"/></a></li>
		 </s:if> --%>
		
			 
		 <%-- <s:if test="%{submgr_permissions['TrackURL']=='yes'}">
		<li class="<s:if test='%{#actionName=="TrackURL"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TrackURL"}' >specialFee("${eid}","200","TrackURL","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Pro Ticketing">Tracking URLs</a></li>
		</s:if>
		
		<s:if test="%{submgr_permissions['TicketURLs']=='yes'}">
		<li class="<s:if test='%{#actionName=="TicketURLs"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="TicketURLs"}' >specialFee("${eid}","200","TicketURLs","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Pro Ticketing">Private Ticket URLs</a> </li>
		</s:if> 
		
		 <s:if test="%{submgr_permissions['NetworkSelling']=='yes'}">
		<li class="<s:if test='%{#actionName=="NetworkTicketSelling"}' >left-item-active</s:if>"><a href='<s:if test='%{#actionName!="NetworkTicketSelling"}' >NetworkSelling?eid=${eid}</s:if><s:else>javascript:;</s:else>'><s:text name="em.actions.nts.lbl"/></a></li>
		</s:if> 		
		 
		 <s:if test="%{submgr_permissions['PriorityRegistration']=='yes'}">
		<li class="<s:if test='%{#actionName=="PriorityRegistration"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="PriorityRegistration"}' >specialFee("${eid}","300","PriorityRegistration","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Advanced Ticketing">Priority Registration</a></li>
		</s:if>
		
		<s:if test="%{submgr_permissions['PrintedTickets']=='yes'}">
			<li class="<s:if test='%{#actionName=="PrintedTickets"}' >left-item-active</s:if>"><a href="javascript:;" onclick='<s:if test='%{#actionName!="PrintedTickets"}' >specialFee("${eid}","300","PrintedTickets","Ticketing");</s:if><s:else>javascript:;</s:else>' title="Advanced Ticketing">Printed Tickets</a></li>
		</s:if> --%>
		
		<%-- <s:if test="%{submgr_permissions['ConfirmationPageSettings']=='yes'}">
	<li class="<s:if test='%{#actionName=="ConfirmationPageSettings"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="ConfirmationPageSettings"}'>specialFee('${eid}','150','ConfirmationPageSettings','RSVP');</s:if>">Confirmation Page&nbsp;<img src="<%=resourceaddress%>/main/images/pro.jpg" alt="Pro RSVP" title="Pro RSVP"/></a></li>
	</s:if>
	<s:if test="%{submgr_permissions['RegEmailSettings']=='yes'}">
	<li class="<s:if test='%{#actionName=="RegEmailSettings"}' >left-item-active</s:if>"><a href="javascript:;" onclick="<s:if test='%{#actionName!="RegEmailSettings"}' >specialFee('${eid}','150','RegEmailSettings','RSVP');</s:if>">Confirmation Email&nbsp;<img src="<%=resourceaddress%>/main/images/pro.jpg" alt="Pro RSVP" title="Pro RSVP"/></a></li>
	</s:if> --%>
	
	
	</div>
	
	
<script>
var actionClassName = $('#actionClassName').val();
var ticketHeading=['Snapshot','ManageTickets','ManageDiscounts','IntegrationLinks','WaitList','TrackURL','TicketURLs','PaymentSettings','RSVPSettings','TicketSettings'];
var customizationHeading=['EditEvent','ManageRegistrationForm','RegWordCustomize','OrderConfirmation','Seating','RSVPWordCustomize','PriorityRegistration'];
var adminHeading=['AddAttendee','EmailAttendees','Badges','ScanIDs'];

if($.inArray(actionClassName, ticketHeading) !== -1){
	$('.ticketHead').find('.glyphicon-menu-right').addClass('down').removeClass('original');
	$('.openFirstDiv').slideDown();
}else if($.inArray(actionClassName, customizationHeading) !== -1){
	$('.customizationHead').find('.glyphicon-menu-right').addClass('down').removeClass('original');
	$('.openSecondDiv').slideDown();
}else{
	$('.adminHead').find('.glyphicon-menu-right').addClass('down').removeClass('original');
	$('.openThiredDiv').slideDown();
}



if($('.openFirstDiv li').length=='0'){
	$('.ticketHead').remove();
}
if($('.openSecondDiv li').length=='0'){
	$('.customizationHead').remove();
}
if($('.openThiredDiv li').length=='0'){
	$('.adminHead').remove();
}


$(function(){
 $("#sm-menu").html($("#featureslist").html());
});
window.closepopup = function () {
    $('#myModal').modal('hide');
};

$('.ticketHead').click(function(){
	if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
		$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');
		$('.openFirstDiv').slideDown();
	}else{
		$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
		$('.openFirstDiv').slideUp();
	}
});
$('.customizationHead').click(function(){
	if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
		$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');
		$('.openSecondDiv').slideDown();
	}else{
		$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
		$('.openSecondDiv').slideUp();
	}
});
$('.adminHead').click(function(){
	if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
		$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');
		$('.openThiredDiv').slideDown();
	}else{
		$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
		$('.openThiredDiv').slideUp();
	}
});


</script>