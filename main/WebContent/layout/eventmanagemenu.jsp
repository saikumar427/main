<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@include file="../getresourcespath.jsp" %>
<s:set name="powertype" value="powertype"></s:set>
<s:set name="poweredbyEB" value="poweredbyEB"></s:set>
<s:set name="eventstatus" value="eventstatus"></s:set>
<s:set name="currentlevel" value="currentLevel"></s:set>
<s:set name="currentfee" value="currentFee"></s:set>
<s:set name="actionName" value="menuMap[#attr['currentaction']]"/>
<s:hidden value="%{actionName}" id="actionClassName"/>
<s:hidden value="%{currentlevel}" id="currentlevel" />
<head>
<style>
.left-item-active .arrow_box {
	position: relative;
	background: #5388C4;
	#border: 4px solid #c2e1f5;
	    padding: 4px 7px 4px 10px;
	    line-height: 22px !important;
	   margin-right: -7px;
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
        margin-right: -35px;
}
li:hover .pro-orange{
	background-color: #F90;
    padding: 1px 5px;
    margin-left: 5px;
    font-size:13px;
    color: #fff;
    opacity: 100;}
.pro-orange:hover{
	background-color: #F90;
    padding: 1px 5px;
    margin-left: 5px;
    font-size:13px;
    color: #fff;
    opacity: 100;
}
.li-space{
			margin-right: 35px;
		}
/* @media( max-width : 1085px) {		
	.left-item-active .arrow_box:after {
	    border-color: rgba(136, 183, 213, 0);
    border-left-color: rgba(83, 136, 196, 0);
    border-width: 13px;
    margin-top: -13px;
	}
}
@media( max-width : 990px) {		
	.left-item-active .arrow_box:after {
	  border-color: rgba(136, 183, 213, 0);
    border-left-color: #5388C4;
    border-width: 13px;
    margin-top: -13px;
	}
} */
</style>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/braintreestates.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/states.js"></script>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/eventmanage/specialfee.js?timestamp=<%=new java.util.Date()%>"></script>
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
	bootbox.confirm("<h3>"+props.myevents_update_payment_msg1+"</h3>"+props.myevents_update_payment_msg2, function (result) {});
}
</script>
</head>

<script>
var manageList=['Snapshot','EventDetails','ManageTickets','DisplayTickets','RSVPSettings','PaymentSettings','EmailAttendees','ManageRegistrationForm','ManageDiscounts','IntegrationLinks','integrationeventurl','integrationbuttons','integrationwidget','AddAttendee','TicketURLs','NetworkTicketSelling','ScanIDs','SubManager','WaitList','PriorityRegistration','PrintedTickets','ManagerAppSettings','EditEvent','Badges','RegWordCustomize','RSVPWordCustomize','OrderConfirmation','Seating','TrackURL','TicketSettings','TicketingRules'];
var designList=['LooknFeel','EventPageContent','LayOutManager','BuyerPage'];
var reportsList=['Reports','RSVPReports','TransactionDetails', 'searchattendee'];
var actionClassName = $('#actionClassName').val();
var currentlevel = $('#currentlevel').val();

var manageListForTicketing=['Snapshot','ManageTickets','WaitList','ManageDiscounts','IntegrationLinks','PaymentSettings','TicketURLs','RSVPSettings','TrackURL','TicketSettings','TicketingRules'];
var manageListForCustomization=['EventDetails','EditEvent','ManageRegistrationForm','RegWordCustomize','RSVPWordCustomize','OrderConfirmation','Seating','PriorityRegistration'];
var manageListForAdmin=['AddAttendee','EmailAttendees','Badges','SubManager','ManagerAppSettings'];

var resourceaddress="<%=resourceaddress%>";
var actionName='<s:text name="actionName" />';
var powerType='<s:text name="powertype" />';
var poweredByEB='<s:text name="poweredbyEB" />';
var isRecurring='${isrecurring}';
var featuresJSON={'manage':[
	                           {'type':'ticketing',
	                            	'items':[	
	                            	         
	                       /* TICKETING */   /* {'label':'<s:text name="em.action.head.lbl1"/>','type':'menu-header-ticketing'}, */
	                            	         {'label':'<s:text name="em.actions.tkts.lbl"/>','action_name':'ManageTickets','level':'basic','action':'ManageTickets?eid=${eid}'},
	                            	         {'label':'<s:text name="em.actions.disc.lbl"/>','action_name':'ManageDiscounts','level':'pro','action':'specialFee("${eid}","200","managediscounts","Ticketing")'},	                          
	                            	         {'label':'<s:text name="em.actions.integrations.lbl"/>','action_name':'IntegrationLinks','level':'basic','action':'IntegrationLinks?eid=${eid}'},
	                            	         {'label':'<s:text name="em.actions.waitlist.lbl"/>','action_name':'WaitList','level':'advanced','action':'specialFee("${eid}","300","WaitList","Ticketing")','recurring':false},
	                            	         {'label':'<s:text name="em.actions.trackurl.lbl"/>','action_name':'TrackURL','level':'advanced','action':'specialFee("${eid}","300","TrackURL","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.private.tkturl.lbl"/>','action_name':'TicketURLs','level':'advanced','action':'specialFee("${eid}","300","TicketURLs","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.tkt.settingg.lbl"/>','action_name':'TicketSettings','level':'pro','action':'specialFee("${eid}","200","TicketSettings","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.ticketing.rules.lbl"/>','action_name':'TicketingRules','level':'business','action':'specialFee("${eid}","400","TicketingRules","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.payment.settings.lbl"/>','action_name':'PaymentSettings','level':'basic','action':'PaymentSettings?eid=${eid}'},
	                            	        
	                  /* CUSTOMIZATION */  	 {'label':'<s:text name="em.action.head.lbl"/>','type':'menu-header-customization'},
	                            	         {'label':'<s:text name="em.actions.event.details.lbl"/>','action_name':'EditEvent','level':'basic','action':'EditEvent?eid=${eid}'},
	                            	         {'label':'<s:text name="em.actions.questions.lbl"/>','action_name':'ManageRegistrationForm','level':'advanced','action':'ManageRegistrationForm?eid=${eid}'},
	                            	         {'label':'<s:text name="em.actions.wording.lbl"/>','action_name':'RegWordCustomize','level':'pro','action':'specialFee("${eid}","200","RegWordCustomize","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.orderconfirmation.lbl"/>','action_name':'OrderConfirmation','level':'advanced','action':'specialFee("${eid}","300","OrderConfirmation","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.priority.reg.lbl"/>','action_name':'PriorityRegistration','level':'business','action':'specialFee("${eid}","400","PriorityRegistration","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.seating.lbl"/>','action_name':'Seating','level':'advanced','action':'specialFee("${eid}","300","Seating","Ticketing")'},
	                            	         
	                         /* ADMIN */     {'label':'<s:text name="em.action.head.lbl2"/>','type':'menu-header-admin'},
	                            	         {'label':'<s:text name="em.actions.add.attendee.lbl"/>','action_name':'AddAttendee','level':'basic','action':'AddAttendee?eid=${eid}&purpose=manageradd'},
	                            	         {'label':'<s:text name="em.actions.email.attendees.lbl"/>','action_name':'EmailAttendees','level':'advanced','action':'specialFee("${eid}","300","EmailAttendees","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.badges.lbl"/>','action_name':'Badges','level':'advanced','action':'specialFee("${eid}","300","Badges","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.submgr.lbl"/>','action_name':'SubManager','level':'business','action':'specialFee("${eid}","400","SubManager","Ticketing")'},
	                            	        /*  {'label':'<s:text name="em.actions.priority.reg.lbl"/>','action_name':'PriorityRegistration','level':'advanced','action':'specialFee("${eid}","300","PriorityRegistration","Ticketing")'}, */
	                            	         {'label':'<s:text name="em.actions.mrg.app.settings.lbl" />','action_name':'ManagerAppSettings','level':'basic','action':'ManagerAppSettings?eid=${eid}'},
	                            	        
	                            	         /*{'label':'<s:text name="em.actions.nts.lbl"/>','action_name':'NetworkTicketSelling','level':'basic','action':'NetworkSelling?eid=${eid}'},
	                            	         {'label':'<s:text name="em.actions.scanids.lbl"/>','action_name':'ScanIDs','level':'advanced','action':'specialFee("${eid}","300","ScanIDs","Ticketing")'},
	                            	         {'label':'<s:text name="em.actions.printed.tkts.lbl"/>','action_name':'PrintedTickets','level':'advanced','action':'specialFee("${eid}","300","PrintedTickets","Ticketing")'}*/
	                            	         {'type':'manageDivClose'},
	                            	]
	                            },
	                            {'type':'rsvp',
	                                   'items':[
	                           /* RSVP */   	{'label':'<s:text name="em.action.head.lbl1"/>','type':'menu-header-ticketing'},
	                                            {'label':'<s:text name="em.actions.rsvp.settings.lbl"/>','action_name':'RSVPSettings','level':'basic','action':'RSVPSettings?eid=${eid}'},
	                                            {'label':'<s:text name="em.actions.integrations.lbl"/>','action_name':'IntegrationLinks','level':'basic','action':'IntegrationLinks?eid=${eid}'},
	                                            {'label':'<s:text name="em.actions.trackurl.lbl"/>','action_name':'TrackURL','level':'pro','action':'specialFee("${eid}","150","TrackURL","RSVP")'},
	                                            
	                     /* CUSTOMIZATION */    {'label':'<s:text name="em.action.head.lbl"/>','type':'menu-header-customization'},
	                     						{'label':'<s:text name="em.actions.event.details.lbl"/>','action_name':'EditEvent','level':'basic','action':'EditEvent?eid=${eid}'},
	                     						{'label':'<s:text name="em.actions.questions.lbl"/>','action_name':'ManageRegistrationForm','level':'pro','action':'ManageRegistrationForm?eid=${eid}'},
	                     						{'label':'<s:text name="em.actions.wording.lbl"/>','action_name':'RSVPWordCustomize','level':'pro','action':'specialFee("${eid}","150","RSVPWordCustomize","RSVP")'},
	                     						{'label':'<s:text name="em.actions.orderconfirmation.lbl"/>','action_name':'OrderConfirmation','level':'pro','action':'specialFee("${eid}","150","OrderConfirmation","RSVP")'},
	                     						
	                     	    /* ADMIN */     {'label':'<s:text name="em.action.head.lbl2"/>','type':'menu-header-admin'},
	                     	   					{'label':'<s:text name="em.actions.add.attendee.lbl"/>','action_name':'AddAttendee','level':'pro','action':'specialFee("${eid}","150","AddAttendee","RSVP")'},
	                     	   					{'label':'<s:text name="em.actions.email.attendees.lbl"/>','action_name':'EmailAttendees','level':'pro','action':'specialFee("${eid}","150","EmailAttendees","RSVP")'},
	                     	   					{'label':'<s:text name="em.actions.badges.lbl"/>','action_name':'Badges','level':'pro','action':'specialFee("${eid}","150","Badges","RSVP")'},
	                     	   					{'label':'<s:text name="em.actions.submgr.lbl"/>','action_name':'SubManager','level':'pro','action':'specialFee("${eid}","150","SubManager","RSVP")'}
	                     	   				 
	                                			/*{'label':'<s:text name="em.actions.scanids.lbl"/>','action_name':'ScanIDs','level':'pro','action':'specialFee("${eid}","150","ScanIDs","RSVP")'}*/
	                                       ]
	                              }
	                     ],
			     	'design':[
			                    {'type':'ticketing',
			                           'items':[
											/*	{'label':'<s:text name="em.actions.eventurl.lbl"/>','action_name':'integrationeventurl','level':'pro','action':'specialFee("${eid}","200","integrationeventurl","Ticketing")'},
												{'label':'<s:text name="em.actions.buttons.code.lbl"/>','action_name':'integrationbuttons','level':'basic','action':'IntegrationLinks?eid=${eid}&purpose=buttons'},
												{'label':'<s:text name="em.actions.widget.code.lbl"/>','action_name':'integrationwidget','level':'pro','action':'specialFee("${eid}","200","integrationwidget","Ticketing")'}, */
												{'label':'<s:text name="em.actions.page.design.lbl"/>','action_name':'LayOutManager','level':'basic','action':'LayOutManager?eid=${eid}'}
												/* {'label':'Buyer Page','action_name':'BuyerPage','level':'basic','action':'BuyerPage?eid=${eid}'} */
			                                	/*{'label':'<s:text name="em.actions.tkt.display.lbl"/>','action_name':'DisplayTickets','level':'pro','action':'specialFee("${eid}","200","DisplayTickets","Ticketing")'},*/
			                                    /* {'label':'<s:text name="em.actions.confirmation.pg.lbl"/>','action_name':'ConfirmationPageSettings','level':'advanced','action':'specialFee("${eid}","300","ConfirmationPageSettings","Ticketing")'},
			                    				{'label':'<s:text name="em.actions.regemail.lbl"/>','action_name':'RegEmailSettings','level':'advanced','action':'specialFee("${eid}","300","RegEmailSettings","Ticketing")'}, 
			                            		{'label':'<s:text name="em.actions.content.lbl"/>','action_name':'EventPageContent','level':'basic','action':'EventPageContent?eid=${eid}'}*/
			                    				]
			                       },
			   
							   {'type':'rsvp',
							          'items':[
												/* {'label':'<s:text name="em.actions.eventurl.lbl"/>','action_name':'integrationeventurl','level':'pro','action':'specialFee("${eid}","150","integrationeventurl","RSVP");'},
												{'label':'<s:text name="em.actions.buttons.code.lbl"/>','action_name':'integrationbuttons','level':'basic','action':'IntegrationLinks?eid=${eid}&purpose=buttons'},
												{'label':'<s:text name="em.actions.widget.code.lbl"/>','action_name':'integrationwidget','level':'pro','action':'specialFee("${eid}","150","integrationwidget","RSVP")'}, */
												{'label':'<s:text name="em.actions.page.design.lbl"/>','action_name':'LayOutManager','level':'basic','action':'LayOutManager?eid=${eid}'}
												/* {'label':'Integration','action_name':'IntegrationLinks','level':'pro','action':'specialFee("${eid}","150","IntegrationLinks","RSVP")'}, */
							                    /* {'label':'<s:text name="em.actions.confirmation.pg.lbl"/>','action_name':'ConfirmationPageSettings','level':'pro','action':'specialFee("${eid}","150","ConfirmationPageSettings","RSVP");'},
							                    {'label':'<s:text name="em.actions.regemail.lbl"/>','action_name':'RegEmailSettings','level':'pro','action':'specialFee("${eid}","150","RegEmailSettings","RSVP")'}, */
							                    /* {'label':'<s:text name="em.actions.looknfeel.lbl"/>','action_name':'LooknFeel','level':'basic','action':'LooknFeel?eid=${eid}'}, 
								                {'label':'<s:text name="em.actions.content.lbl"/>','action_name':'EventPageContent','level':'basic','action':'EventPageContent?eid=${eid}'}*/
							                    ]
							      }
						],
                     
                   'reports':[
	                             {'type':'ticketing',
		                            	'items':[ 
		                            	          {'label':'<s:text name="em.actions.reports.lbl"/>','action_name':'Reports','level':'basic','action':'Reports?eid=${eid}'},
		                            	          {'label':'<s:text name="em.actions.attendee.list.lbl"/>','action_name':'Reports','level':'basic','action':'Reports?eid=${eid}'},
		                            	          {'label':'<s:text name="em.actions.searchattendee.lbl"/>','action_name':'TransactionDetails','level':'basic','action':'TransactionDetails?eid=${eid}'}
		                            	]
	                             },
	                             {'type':'rsvp',
		                            	'items':[
		                            	         {'label':'<s:text name="em.actions.rsvp.reports.lbl"/>','action_name':'RSVPReports','level':'basic','action':'RSVPReports?eid=${eid}'},
		                            	         {'label':'<s:text name="em.actions.searchattendee.lbl"/>','action_name':'TransactionDetails','level':'pro','action':'specialFee("${eid}","150","TransactionDetails","RSVP")'}
		                            	         
		                            	         ]
		                          }
	                            
                             ]  
                   };
var match = '';               
function getHTMLString(eachPowerType,eachFeature,action,match){
	match=match;
	var featuresHTML="";
	if(eachFeature.type=='menu-header-customization'){
		featuresHTML+="</span>";
		featuresHTML+="<li class='side-menu-header customizationToggle' id='' style='line-height: 0px; text-indent: 0px;margin:25px 0px 10px 0px;cursor:pointer;padding: 0 15px 0 0;'><span style='color: #5388C4;'>"+eachFeature.label+"<i class='glyphicon glyphicon-menu-right' style='font-size: 15px; margin-left: 5px;'></i></span></li>";
		featuresHTML+="<span class='openSecondDiv' style='display:none;'>";
	}
	else if(eachFeature.type=='menu-header-admin'){
		featuresHTML+="</span>";
		featuresHTML+="<li class='side-menu-header adminToggle' id='' style='line-height: 0px; text-indent: 0px; margin:25px 0px 10px 0px;cursor:pointer;padding: 0 15px 0 0;'><span style='color: #5388C4;'>"+eachFeature.label+"<i class='glyphicon glyphicon-menu-right' style='font-size: 15px; margin-left: 5px;'></i></span></li>";
		featuresHTML+="<span class='openThirdDiv' style='display:none;'>";
	}else if(eachFeature.type=='manageDivClose')
		featuresHTML+="</span>";				
	else if(!(isRecurring == 'true' && eachFeature.recurring == false)){
		if(eachFeature.level=="basic")
		 featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a href='"+action+"' ><span class='arrow_box'>"+eachFeature.label+"</span></a></li>";
		else if(eachFeature.level=="pro"){
			 if(eachPowerType.type=="ticketing")
			 	featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a  href='javascript:' title='"+props.global_pro_tkting_lbl+"' onclick='"+action+"'><span class='arrow_box'>"+eachFeature.label +"</span><span class='pro-orange removePro'>"+props.global_pro_lbl+"</span></a></li>";
			 if(eachPowerType.type=="rsvp"){
				 if('ManageRegistrationForm'==eachFeature.action_name)
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a href='"+action+"' ><span class='arrow_box'>"+eachFeature.label+"</span></a></li>";
				 else
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a  href='javascript:' title='"+props.global_pro_rsvp+"' onclick='"+action+"'><span class='arrow_box'>"+eachFeature.label+"</span><span class='pro-orange removePro'>"+props.global_pro_lbl+"</span></a></li>";
			 }
		 }
		 else if(eachFeature.level=="advanced"){
			 if(eachPowerType.type=="ticketing"){
				 if('ManageRegistrationForm'==eachFeature.action_name)
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a href='"+action+"' ><span class='arrow_box'>"+eachFeature.label+"</span></a></li>";
				 else
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a  href='javascript:' title='"+props.global_adv_ticketing+"' onclick='"+action+"'><span class='arrow_box'>"+eachFeature.label+"</span><span class='pro-orange removeAdv'>"+props.global_adv_lbl+"</span></a></li>";	 
			 }
			 if(eachPowerType.type=="rsvp"){
				 if('ManageRegistrationForm'==eachFeature.action_name)
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+"'><a href='"+action+"' ><span class='arrow_box'>"+eachFeature.label+"</span></a></li>";
				 else
					 featuresHTML+="<li class='"+(match?"left-item-active":"")+"'><a  href='javascript:' title='"+props.global_adv_rsvp+"' onclick='"+action+"'><span class='arrow_box'>"+eachFeature.label+"</span><span class='pro-orange removeAdv'>"+props.global_adv_lbl+"</span></a></li>"; 
			 }
		 }
		 else if(eachFeature.level=="business"){
			 if(eachPowerType.type=="ticketing"){
				 if('ManageRegistrationForm'==eachFeature.action_name)
					featuresHTML+="<li class='"+(match?"left-item-active":"")+"'><a href='"+action+"' ><span class='arrow_box'>"+eachFeature.label+"</span></a></li>";
				else
					featuresHTML+="<li class='"+(match?"left-item-active":"")+" li-space'><a  href='javascript:' title='"+props.global_bus_ticketing+"' onclick='"+action+"'><span class='arrow_box'>"+eachFeature.label+"</span><span class='pro-orange removeBus'>"+props.business_tip+"</span></a></li>";
			 }
		 }
	}
	return featuresHTML;
}                

function optionsHelper(allActions,powerType,paymentSet){
	var featuresHTML="";
	var eachPowerType={};
	for(var i=0;i<allActions.length;i++){
		if(powerType.toLowerCase()==allActions[i].type)
			eachPowerType=allActions[i];					
	}				
	
	for(var j=0;j<eachPowerType.items.length;j++){
		var eachFeature=eachPowerType.items[j];
		if(eachFeature.action_name==actionName){
			featuresHTML+=getHTMLString(eachPowerType,eachFeature,"javascript:",true);						
		}
		else{
			if(paymentSet)
				featuresHTML+=getHTMLString(eachPowerType,eachFeature,eachFeature.action,false);
			else{
				eachFeature.level="basic"; // this helps to keep link as href instead of click listener
				featuresHTML+=getHTMLString(eachPowerType,eachFeature,"PaymentSettings?eid=${eid}",false);
			}
		}					
	}// for j close
	return featuresHTML;
}
 function fillHTML(){     
		var featuresHTML="";
		var allActions={};
		if(manageList.indexOf(actionName)>-1){
			allActions=featuresJSON.manage;
			if(powerType=='Ticketing')
				featuresHTML+="<li class='side-menu-header ticketingToggle' id='' style='line-height: 0px; text-indent: 0px;cursor:pointer;margin-bottom: 10px;padding: 0 15px 0 0;'><span style='color: #5388C4;'>"+props.em_action_head_lbl1+"<i class='glyphicon glyphicon-menu-right' style='font-size: 15px; margin-left: 5px;'></i></span></li><span class='openFirstDiv' style='display:none;'>";
			else
				featuresHTML+="<li class='side-menu-header ticketingToggle' id='' style='line-height: 0px; text-indent: 0px;cursor:pointer;margin-bottom: 10px;padding: 0 15px 0 0;'><span style='color: #5388C4;'>"+props.em_action_head_lbl3+"<i class='glyphicon glyphicon-menu-right' style='font-size: 15px; margin-left: 5px;'></i></span></li><span class='openFirstDiv' style='display:none;'>";	
			
				if("Snapshot"==actionName)
				featuresHTML+="<li class='left-item-active li-space' id='navigation' ><a href='javascript:' class=' active arrow-link' ><span class='arrow_box'><s:text name='em.actions.summary.lbl'/></span></a></li>";
			else
				featuresHTML+="<li class='li-space'><a href='Snapshot?eid=${eid}' ><span class='arrow_box'><s:text name='em.actions.summary.lbl'/></span></a></li>";
				
		}
		else if(designList.indexOf(actionName)>-1)
			allActions=featuresJSON.design;
		else if(reportsList.indexOf(actionName)>-1)
			allActions=featuresJSON.reports;		
		if((powerType=='Ticketing' && poweredByEB=='yes') ||(powerType=='RSVP')){	
			  featuresHTML+=optionsHelper(allActions,powerType,true);
			 
			} // close
			else{
				featuresHTML+=optionsHelper(allActions,powerType,false);				
			}
	  /*  featuresHTML+="<li><a href='../myevents/home!generateClassicToken' target='_blank' style='color:#ff9900;font-size:12px' >"+props.evt_mnge_old_ui_lbl+"</a></li>";	 */
	   document.getElementById("featureslist").innerHTML=featuresHTML;
	   document.getElementById('sm-menu').innerHTML=featuresHTML;
	   var eventID=${eid};
	   
	   setTimeout(function(){
		   if($('.ticketingToggle').find("i").hasClass('original'))
			   document.cookie=eventID+"_ticketing=N";
		   else
			   document.cookie=eventID+"_ticketing=Y";
	   }, 1000);	   
	   $('.ticketingToggle').click(function(){
	   		if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
	   			$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');	 
	   			$('.openFirstDiv').slideDown();
	   			$('.openFirstDiv').css("display","block");
	   			document.cookie=eventID+"_ticketing=Y";
	   		}else{
	   			$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
	   			$('.openFirstDiv').slideUp();
	   			document.cookie=eventID+"_ticketing=N";
	   		}
	   });
	   $('.customizationToggle').click(function(){
	   		if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
	   			$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');	
	   			$('.openSecondDiv').slideDown();
	   			$('.openSecondDiv').css("display","block");
	   			document.cookie=eventID+"_customization=Y";
	   		}
	   		else{
	   			$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
	   			$('.openSecondDiv').slideUp();
	   			document.cookie=eventID+"_customization=N";
	   		}
	   });
	   $('.adminToggle').click(function(){
	   		if($(this).find("i").hasClass('original') || !$(this).find("i").hasClass('down')){
	   			$(this).find('.glyphicon-menu-right').addClass('down').removeClass('original');	
	   			$('.openThirdDiv').slideDown();
	   			$('.openThirdDiv').css("display","block");
	   			document.cookie=eventID+"_admin=Y";
	   		}
	   		else{
	   			$(this).find('.glyphicon-menu-right').addClass('original').removeClass('down');
	   			$('.openThirdDiv').slideUp();
	   			document.cookie=eventID+"_admin=N";
	   		}
	   });
	   
	   function getCookie(cname) {
		    var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i=0; i<ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0)==' ') c = c.substring(1);
		        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
		    }
		    return "";
		}
	  
		var adminToggleValue=getCookie(eventID+'_admin');
		var customizationToggleValue=getCookie(eventID+'_customization');
		var ticketingToggleValue=getCookie(eventID+'_ticketing');
		//console.log(adminToggleValue+' - '+customizationToggleValue+' - '+ticketingToggleValue);
		if('Y'==adminToggleValue){
			$('.adminToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
		    $('.openThirdDiv').slideDown();
		    $('.openThirdDiv').css("display","block");
		}else{
			$('.adminToggle').find('.glyphicon-menu-right').addClass('original').removeClass('down');
		    $('.openThirdDiv').slideUp();
		}
		if('Y'==customizationToggleValue){
			 $('.customizationToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
			 $('.openSecondDiv').slideDown();
			 $('.openSecondDiv').css("display","block");
		}else{
			$('.customizationToggle').find('.glyphicon-menu-right').addClass('original').removeClass('down');
			 $('.openSecondDiv').slideUp();
		}
		if('Y'==ticketingToggleValue){
			$('.ticketingToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
			$('.openFirstDiv').slideDown();
			$('.openFirstDiv').css("display","block");
		}else{
			$('.ticketingToggle').find('.glyphicon-menu-right').addClass('original').removeClass('down');
			$('.openFirstDiv').slideUp();
		}
		
	   if($.inArray(actionClassName, manageListForTicketing) !== -1){
		   $('.ticketingToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
		   $('.openFirstDiv').slideDown();
		   $('.openFirstDiv').css("display","block");
	   }else if($.inArray(actionClassName, manageListForCustomization) !== -1){
		   $('.customizationToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
		   $('.openSecondDiv').slideDown();
		   $('.openSecondDiv').css("display","block");
	   }else{
		   $('.adminToggle').find('.glyphicon-menu-right').addClass('down').removeClass('original');
		   $('.openThirdDiv').slideDown();
		   $('.openThirdDiv').css("display","block");
	   }
	   
	  if('200'==currentlevel || '150'==currentlevel){
		  $('.removePro').remove();
	  }else if('300'==currentlevel){
		  $('.removePro').remove();
		  $('.removeAdv').remove();
		  //$('.li-space').removeClass('li-space');
	  }else if('400'==currentlevel){
		  $('.removePro').remove();
		  $('.removeAdv').remove();
		  $('.removeBus').remove();
	  }
	  
	  
	  
		 
 }

</script>

<a id="menu-toggle" href="javascript:;" class="btn-lg toggle"> <i class="glyphicon glyphicon-chevron-right"> </i> </a>

<div id="sidebar-wrapper" class="active" style="overflow: hidden;">

	<ul class="sidebar-nav" id="featureslist">		
		
   </ul>
   <script> fillHTML(); </script>
</div>
