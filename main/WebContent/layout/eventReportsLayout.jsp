<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<tiles:importAttribute toName="currentaction" name="action"
	ignore="true" scope="request" />
<tiles:importAttribute name="menucontext" ignore="true" scope="request" />

<title><s:text name="%{currentaction_original}" /></title>
 <s:set name="actionName" value="menuMap[#attr['currentaction']]" />
  <s:set name="powertype" value="powertype"></s:set>
 <s:set name="poweredbyEB" value="poweredbyEB"></s:set>
 <s:set name="eventstatus" value="eventstatus"></s:set>


<s:set var="ManageList" value="{'Snapshot','EventDetails','ManageTickets','DisplayTickets','EmailAttendees','RSVPSettings','PaymentSettings','ManageRegistrationForm','ManageDiscounts','integrationeventurl','integrationbuttons','integrationwidget','AddAttendee','TicketURLs','NetworkTicketSelling','ScanIDs','SubManager','WaitList','PriorityRegistration','PrintedTickets','ManagerAppSettings'}" />
<s:set var= "designList" value="{'EditEvent','Badges','LooknFeel','EventPageContent','RegWordCustomize','RSVPWordCustomize','OrderConfirmation','Seating','LayOutManager','IntegrationLinks','TrackURL'}" />
<s:set var="reportList" value="{'Reports','RSVPReports','TransactionDetails', 'searchattendee'}" />
<style>


#toggling-menu:hover{
    background-color: #2F4978;
}
#controls{
   font-size:12px;
    position: absolute;
    right: 62px;
    top: 25px;
}
@media ( max-width : 1000px) {
	#controls{
		font-size:12px;
		position: absolute;
	   # left: 13px;
	    top: 70px;
		

	}
}
@media ( max-width : 768px) {
	#controls{
		font-size:12px;
		position: absolute;
	    left: 13px;
	    top: 80px;
		

	}
}
.switch-full {
     padding:2px;
	width: 81px;
	height: 26px;
	background-color: #f3f6fa;
	border-radius: 75px;
	
}
.switch-active {
	width: 76px;
    height: 22px;
    background-color: #FF9900;
    border-radius: 150px;
	color:white
	
}
.switch-active-preview{
	width: 76px;
    height: 22px;
    background-color: #FF9900;
    border-radius: 150px;
	color:white;
	background:#5388C4 !important;
}
.switch-active-preview:hover{
background: #3c60AA !important;
}
#status .switch-full .switch-active {	
    background-color: red;
}
.inactive-text{
	display:none;
	float:right;
	margin-top:3px;
	color:#5388c4;
	width:75px;
	text-align:center;
        cursor:pointer;
}
.green{	
	background-color:green !important;
}
.openSwitch{
width:157px;
}
.openSwitch .inactive-text{
display:block;
}

</style>
</head>
<script type="text/javascript" src="<%=resourceaddress%>/main/js/eventmanage/specialfee.js?id=1"></script>
<s:hidden name="currentLevel" id="currentLevel"></s:hidden>		
<body style="background-color: #f3f6fa">
<div class="mainContentDiv">
<div>
	<div id="notification-holder"></div>
</div>
	<!-------------------------Begin of Header----------------------------->
	<tiles:insertAttribute name="header" ignore="true" />
	<!-------------------------End of Header----------------------------->
	<div style="position:relative;top:80px;margin-bottom: 50px;padding-bottom: 50px;">
		<!-- parent for content and menu -->
	  <div style="background-color: #3c60AA !important;top:53px;left: 0;position: fixed;right: 0;z-index: 100;"  id="menuHead" height="55px">	
	 		<div style="text-align: center; padding: 4px 0px;" class="event-name">
	 		
            <div id="nav_list" style="display:none;">            
              <button class="menubar-toggle">
					<span class="icon-bar" ></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>          
            
            </div>
             <s:if test='%{#actionName in #reportList} '>
	             <script>
	               document.getElementById("nav_list").innerHTML='';
	             </script>            
            </s:if>
           
	 		
				<span id="toggling-menu" style="cursor:pointer; padding: 3px 30px 3px 13px;">	<span 
						style="text-align: right;  padding-right: 2px;" id="eventName"></span>

					
						<span class="glyphicon glyphicon-menu-down" 
							style="cursor: pointer;  margin-top: 3px; margin-right: -17px;"></span>		
							</span>			

				</div>
		  <div style="text-align: center;padding:2px 0px 5px 0px">			
				<div style="clear:both"></div>
				
				<div class="" style="text-align: center;">
						<div class="">					
							<ul style="list-style-type: none;  margin: 0; padding: 2px 0px 0px 0px;" id="main-tab" >
								<li data-tab="design" id ="design" class="<s:if test='%{#actionName in #designList}'>main-tab-li-active</s:if><s:else>main-tab-li</s:else>"><s:text name="eml.cfgr._lbl"></s:text></li>
								<li data-tab="manage"  class="<s:if test='%{#actionName in #ManageList}'>main-tab-li-active</s:if><s:else>main-tab-li</s:else>"><s:text name="eml.mg.lbl"></s:text></li>
								<li data-tab="reports" id="reports" class="<s:if test='%{#actionName in #reportList}'>main-tab-li-active</s:if><s:else>main-tab-li</s:else>"><s:text name="eml.rprt.lbl"></s:text></li>	
							</ul>							
					</div>
					
					<script>
						var designPermission = '';
						var reportPermission='';
						var loginType='mgr';
					</script>
			
		        <s:if test="%{subMgr!=null}">
		           	<s:set var = "breakLoop" value = "%{false}" />
					<s:iterator value="submgr_permissions" var="data">
						<s:if test = "!#breakLoop">
							<s:if test="%{#data.key in #designList}">
							 <s:set var = "breakLoop" value = "%{true}"/>
							   <script>
							   designPermission = '<s:text name="#data.key" />';
							   if(designPermission=='TrackURL')
								   designPermission='IntegrationLinks';
								</script>
							
							</s:if>
						</s:if>
			 		</s:iterator>
			 		<s:set var = "breakLoop" value = "%{false}" />
			 		<s:iterator value="submgr_permissions" var="data">
				 		<s:if test = "!#breakLoop">
							<s:if test="%{#data.key in #reportList}">
							<s:set var = "breakLoop" value = "%{true}"/>
							   <script>
							   reportPermission = '<s:text name="#data.key" />';
								</script>					
							</s:if>
						</s:if>
			 		</s:iterator>      
		        
		        
		               <script>
		                   loginType="submgr";
						   if(designPermission=='')
								$("#design").remove();
						   if(reportPermission=='')
								$("#reports").remove();
						   </script>
		          </s:if>		
					
					<div class="col-md-5 event-search" >
						<div id="search-loading"
							style="padding-top: 35px; text-align: center;"><s:text name="ea.loading.table.lbl"></s:text></div>
						<table id="event-search-table" class="table table-hover"
							width="100%" cellspacing="30">
							<thead>
								<tr>
									<td></td>
								</tr>
							</thead>
							<tbody id="event-search-body">
							</tbody>
						</table>
					</div>
					<div style="clear:both"></div>

				</div>
			</div>
			<!-- level change start -->
			
				<div id="controls" class="pull-right" >
				
				<div style="float: left; display: block; border: 2px solid #FFF; border-radius: 20px;">
							<div class="switch-active-preview" onclick="onLoadHandler();">
								<span style="width:75px;text-align:center;margin-top:3px;position:absolute;cursor:pointer;" ><s:text name="pg.header.preview.lbl"/></span>
							</div>
						</div>
				<s:if test="%{subMgr==null}">
						<div id="status" style="float:left;display:none;margin-left:10px;" >
							<div class="switch-full">								
								   <span class="inactive-text"><a  data-status="active"  href="#"></a></span>
									<div class="switch-active green">
										<span data-status="suspend" style="width:75px;text-align:center;margin-top:3px;position:absolute;"></span>
									</div>														
							</div>
						</div>
						<div id="level" style="float:left;margin-left:10px;display:none">
							<div  class="switch-full openSwitch">
								<span class="inactive-text" id="second" ><a href="#"></a></span>
								<div class="switch-active">
									<span style="width:75px;text-align:center;margin-top:3px;position:absolute;" data-status="do" id="first"></span>
								</div>
							</div>
						</div>
						
						
						<div style="clear:both"></div>
						</s:if>
				</div>
			
			<!-- level change end -->
		</div>
		
		<div style="clear:both"></div>
		
		
		<div class="push-menu pushmenu pushmenu-left" style="float: left">		
		    <nav class="" id="sm-menu">
		 </nav>


		
		</div>
		
	    <!-- menu removed -->
	    
		<div class="reports-content" id="maincontent"
			style="float: left">
			<!-- here is old naviagation tab -->			

			<div class="eventmanage-main-content-body col-md-12">
				
				<!-- HERE IS WHERE THE NOTIFICATION WILL APPEAR-->
				<tiles:insertAttribute name="maincontent" ignore="true" />

			</div>

		</div>
		
		<div style="clear: both"></div>
	    <%-- <s:if test='%{#actionName=="LayOutManager"}'>
			<!-- <div class="pageDisplay" style="display:none; margin:30px 0px;"> -->
			<div class="pageDisplay" style=" margin:30px 0px;">
			<div class="text-center" style="display:none;" id="loadingpreview"><img src="/main/images/layoutmanage/Pspinner.gif" style="width:60px;" /></div>
				<iframe id="previewPage" src="" style="border:0px;width:100%;display:none;" onload="sizeIframe();"></iframe>
			</div>
		</s:if> --%>
	</div>

	<div id="footer" style="z-index: 99; position: relative;">
		<!-------------------------Begin of Footer----------------------------->


		<tiles:insertAttribute name="footer" />
		<!-------------------------End of Footer----------------------------->
	</div>
</div>
</body>
<!-- preview -->
	<div class="pageDisplay" style="display:none;">
	<div style="height: 40px; height: 50px;  padding: 8px; background-color: #3c60AA;">
		<div class="text-center">
		<button class="btn btn-primary btn-sm" style="border: 2px solid #f5f5f5; border-radius: 20px;" onclick="closeP();"><s:text name="pg.close.preview.lbl"/></button>
	</div>
	</div>
	<div class="text-center" style="display:none; margin-top: 10%;" id="loadingpreview"><img src="/main/images/layoutmanage/Pspinner.gif" style="width:60px;" /></div>
		<iframe id="previewPage" src="" style="border:0px;width:100%;display:none;" onload="sizeIframe();"></iframe>
	</div>
<!-- preview close -->

<script>
var action='<s:text name="actionName" />';
var iFrameLoad=false;
var globLayoutPage='event-layout';
$('#previewPage').load(function(){
   	if(iFrameLoad){
       	$(this).show();
       	$('#loadingpreview').hide();
   	}
   });
function onLoadHandler(){
	if(!iFrameLoad){
		iFrameLoad=true;
		$('.pageDisplay').css('display','block');
		$('.mainContentDiv').slideUp(1500);
		$('#loadingpreview').show();
		//$('#previewPage').css('display','block');
		if(action=='LayOutManager'){
			if(globLayoutPage=='buyer-layout'){
				//$('#previewPage').attr('src', '/buyerpage?eid=${eid}&preview=draft');
				$('#previewPage').attr('src', '/buyerpage?eid=${eid}&preview=draft');
			}else{
				$('#previewPage').attr('src', '/event?eid=${eid}&preview=draft');
			}
			$('#previewPage').hide();
		}else{
			var url = 'LayOutManager!updateDraftWithFinal?eid='+${eid};
				$.ajax({
					type:'POST',
					url:url,
					success:function(result){
						$('#previewPage').attr('src', '/event?eid=${eid}&preview=draft');
						$('#previewPage').hide();
					}
				});
		}
		
	}
	
}

var sizeIframe = function(){				
setTimeout(function(){
	var mydiv = $("#previewPage").contents().find("#rootDiv");
	var obj=document.getElementById('previewPage');
	obj.style.height =(mydiv.height()+80)+ 'px';
},2000);
$('#previewPage').hide();
};
function closeP(){
	iFrameLoad=false;
	$('.mainContentDiv').slideDown(1500);
	setTimeout(function(){
		$('#previewPage').attr('src', '');
		$('#previewPage').css('display','none');
		$('.pageDisplay').css('display','none');}
	,1000);
	
}
var typeOfEvent = '<s:text name="powertype" />';
var poweredbyEB = '<s:text name="poweredbyEB" />';
var eventName="<s:property value="eventname" />";
var eventStatus="<s:text name="eventstatus" />";

var TicketingactionObj={"AddAttendee":"AddAttendee?eid=##eid##&purpose=manageradd",
         "SearchAttendee":"TransactionDetails?eid=##eid##","NetworkTicketSelling":"NetworkSelling?eid=##eid##","LayOutManager":"LayOutManager?eid=##eid##","manageappsettings":"ManagerAppSettings?eid=##eid##"};
         
var TicketingUpgradeObj={"ManageRegistrationForm":"300","ManageDiscounts":"200","integrationeventurl":"200","IntegrationLinks":"200"
          ,"integrationwidget":"200","SubManager":"300","TrackURL":"200","TicketURLs":"200","ScanIDs":"300",
          "WaitList":"300","PriorityRegistration":"300","PrintedTickets":"300","EmailAttendees":"300","Badges":"300",
          "DisplayTickets":"200","RegWordCustomize":"200","OrderConfirmation":"300","Seating":"300","TicketSettings":"200","TicketingRules":"300"};

var RSVPUpgradeObj=["ManageRegistrationForm","integrationeventurl","integrationwidget","SubManager","TrackURL","ScanIDs","EmailAttendees",
     "Badges","RSVPWordCustomize","ConfirmationPageSettings","RegEmailSettings","TransactionDetails"];

var RSVPactionObj={"ConfirmationPageSettings":"ConfirmationPageSettings?eid=##eid##&type=rsvp",
    "AddAttendee":"AddAttendee?eid=##eid##&purpose=manageradd&type=rsvp","SearchAttendee":"TransactionDetails?eid=##eid##&type=rsvp",
    "Badges":"Badges?eid=##eid##&purpose=rsvpbadges"};

if(poweredbyEB!='yes' && typeOfEvent!='RSVP' && action!='PaymentSettings'){// for the event which dont has payment settings
	window.location.href='../eventmanage/PaymentSettings?eid='+${eid};	
}

if(eventName.length>55){
	if(window.innerWidth < 768)// for small screen devices
	   document.getElementById("eventName").innerHTML=eventName.substring(0,20-3)+'...';
	else
		 document.getElementById("eventName").innerHTML=eventName.substring(0,55-3)+'...';
}
else{
	if(window.innerWidth < 768){// for small screen devices
		if(eventName.length>17)
		   document.getElementById("eventName").innerHTML=eventName.substring(0,20-3)+'...';
		else
			  document.getElementById("eventName").innerHTML=eventName;
	}
	else 
	 document.getElementById("eventName").innerHTML=eventName;
}

var SearchObj=new Object();
var flag = true;
var classNames=["eventmng each-row","event-title","dataTables_wrapper","glyphicon glyphicon-search search-result","event-search","col-md-5 event-search","glyphicon glyphicon-menu-down","paginate_disabled_previous","paginate_disabled_next","paginate_enabled_next","dataTables_paginate col-md-4 col-sm-6 col-xs-12 sub-text paging_two_button","paginate_enabled_previous","dataTables_info col-md-6 col-sm-6 col-xs-12 sub-text","status","dataTables_filter","form-control"];	

var level=document.getElementById("currentLevel").value;
var presentStatus="basic";
if(level==200 || level==150)presentStatus="pro";else if(level==300 )presentStatus="advanced";
var stopLevelChange=false;


$(document).ready(function() {
	
	$(document).click(function(evt) {
		if(classNames.indexOf(evt.target.className)>-1 || evt.target.id=='eventName')
			return;
			else
			$('.event-search').slideUp();	
			
		});
	
	
    
	$menuLeft = $('.pushmenu-left');
	$nav_list = $('#nav_list');
	$conent=$('#maincontent');
    $eachLink=$('.pushmenu a');
	$nav_list.click(function() {
		$(this).toggleClass('active');
		$menuLeft.toggleClass('pushmenu-open');
	});
    $conent.click(function(){		    
        closeMenu();
     });
    $eachLink.click(function(){
        closeMenu();    
    });
    
    $('#sidemenu').click(function() {
    	$('#slideview').slideToggle(400);
    	
    });
     
    $('#toggling-menu').click(function(){
    	var url='/main/myevents/eventslist!populateEventsList';
    	if($('.event-search').is(':hidden')){
    	 if(isEmpty(SearchObj)){
    		 <s:if test="%{subMgr!=null}">		
    		 	url='/main/submanager/events!populateEventsList';		
    		 </s:if>
    		$.ajax({
    			url:url,
    			success:function(result){
    				result=result.replace("var data=", "");
    				result=result.replace("data =    ", "");		
    				result=result.replace(";", "");
    				var json=JSON.parse(result);
    				SearchObj=json;
    				fillSearchTable(json);
    			}
    		
    		});
    	   }else{
    	        $('#event-search-table').dataTable().fnDestroy(false);
    			fillSearchTable(SearchObj);
    		}
        } 
    	$('.event-search').slideToggle("slow");
    });
    		
    $("#main-tab li").click(function(){
    	if(poweredbyEB!='yes' && typeOfEvent!='RSVP'){// for the event which dont has payment settings
    		window.location.href='../eventmanage/PaymentSettings?eid='+${eid};	
    	}
	    	if($(this).data('tab')=='reports'){
				if(reportPermission!='' && loginType=='submgr'){    				
					   window.location.href='../eventmanage/'+reportPermission+'?eid='+${eid};    					   
				}else{
					if(typeOfEvent!='RSVP')
					     window.location.href='../eventmanage/Reports?eid='+${eid};
					else
						 window.location.href='../eventmanage/RSVPReports?eid='+${eid};
				}
			}else if($(this).data('tab')=='manage'){
    			window.location.href='../eventmanage/Snapshot?eid='+${eid};
    		}else if($(this).data('tab')=='design'){
    			if(designPermission!='' && loginType=='submgr')
    			    window.location.href='../eventmanage/'+designPermission+'?eid='+${eid};
    			else
    				window.location.href='../eventmanage/LayOutManager?eid='+${eid};
    		}
    	});
    
    $("#status .inactive-text a").click(function(){
    	changeEventStatus($(this).parent());    	
	});
	$("#level .switch-full").hover(function() {
		if($("#first").data('status')=='do')
			mouseHover("level");
		}, function() {			
			//mouseOut("level");
	});
	$("#status .switch-full").hover(function() {
		if($(this).find('.switch-active').find('span').data('status').toLowerCase()!='closed'){
			mouseHover("status");	
	        mouseOut("level");		
		}
		}, function() {
			if($(this).find('.switch-active').find('span').data('status').toLowerCase()!='closed'){
				mouseOut("status");
				if(!stopLevelChange)
					mouseHover("level");	
			}
	});
	$("#second").click(function(){
		//upgradEventLevel(${eid},level); 
		var tickettype="200";
		if(level=="100") tickettype="200";
		if(level=="200") tickettype="300";
		if(level=="90") tickettype="150";
			specialFee(${eid},tickettype,'upgradeevent','${powertype}');
	});
    
    
});



function fillSearchTable(json){
			 $('#search-loading').hide();
			 var htmlData='';
			 $.each(json.active,function(outkey,resObj){
					htmlData+='<tr onclick="javascript:eventManagePage('+resObj.id+',\''+resObj.eventtype+'\');"><td class="eventmng each-row "><div><div style="float:left;width:80%"><span >'+resObj.title+'</span></div><span class="pull-right"><span class="status">Active</span></span><div style="clear:both"></div></div></div></td></tr>';
				});
				$.each(json.closed,function(outkey,resObj){
					htmlData+='<tr onclick="javascript:eventManagePage('+resObj.id+',\''+resObj.eventtype+'\');"><td class="eventmng each-row "><div><div style="float:left;width:80%"><span >'+resObj.title+'</span></div><span class="pull-right"><span class="status">'+getYear(resObj.startdate)+'</span></span><div style="clear:both"></div></div></td></tr>';
				});
				$.each(json.suspended,function(outkey,resObj){
					htmlData+='<tr onclick="javascript:eventManagePage('+resObj.id+',\''+resObj.eventtype+'\');"><td class="eventmng each-row "><div><div style="float:left;width:80%"><span >'+resObj.title+'</span></div><span class="pull-right"><span class="status">'+getYear(resObj.startdate)+'</span></span><div style="clear:both"></div></div></td></tr>';
				});

			$('#event-search-body').html(htmlData);
			$('#event-search-table').dataTable( {
			    "sPaginationType": "two_button",
			    "iDisplayLength":3,
			    "aaSorting": [],
			    "bPaginate": true,
			    "ordering": false,
			    "info":     false,
			    "bLengthChange": false,
			    "aoColumns": [null] ,
			                "fnInitComplete": function(oSettings, json) {
			                }
			} );
			$("#event-search-table").find("thead").remove();
			$(".event-search .dataTables_filter").css('width','100%');
	
 }

 function isEmpty(obj) {
		return Object.keys(obj).length === 0;
  }

 function eventManagePage(eid,powertype){ 	
 		var actionVal;
 		var url="";

 		if(typeOfEvent!=powertype){
 			window.location.href='/main/eventmanage/Snapshot?eid='+eid;
 			return;
 		} 		
 		
	 	if(powertype=='RSVP'){ 		
	 	    actionVal=RSVPactionObj[action];
	 		if(!(RSVPUpgradeObj.indexOf(action)>-1)){
	 			if(actionVal==null || actionVal==undefined){
	 				window.location.href='/main/eventmanage/'+action+'?eid='+eid;
	 				return;
	 					}else{
	 						actionVal=actionVal.replace("##eid##",eid);
	 						window.location.href='/main/eventmanage/'+actionVal;
	 						return;
	 						}
	 			}
	 	 
	 	 		url='SpecialFee?eid='+eid+'&ticketingtype=150&powertype=RSVP&source='+action;
	 	}else{
	 		actionVal=TicketingactionObj[action];
	 		if(TicketingUpgradeObj[action]==null || TicketingUpgradeObj[action]==undefined){
	 			if(actionVal==null || actionVal==undefined){
	 				window.location.href='/main/eventmanage/'+action+'?eid='+eid;
	 				return;
	 					}else{
	 						actionVal=actionVal.replace("##eid##",eid);
	 						window.location.href='/main/eventmanage/'+actionVal;
	 						return;
	 						}
	 			}
	 		url='SpecialFee?eid='+eid+'&ticketingtype='+TicketingUpgradeObj[action]+'&powertype=Ticketing&source='+action;
	 	}
 			loadingPopup();

			 	$.ajax({
			 		url:url,
			 		success:function(result){
		 		if(!isValidActionMessage(result)) return;			
		
		 		if(result && result.indexOf("specialfee") > -1){
		 			window.location.href='/main/eventmanage/Snapshot?eid='+eid;
		 			}else if(result && result.indexOf("blank") > -1){
		 				if(actionVal==null || actionVal==undefined){
		 					window.location.href='/main/eventmanage/'+action+'?eid='+eid;
		 						}else{
		 							actionVal=actionVal.replace("##eid##",eid);
		 							window.location.href='/main/eventmanage/'+actionVal;
		 							}
		 				}else{
		 					window.location.href='/main/eventmanage/Snapshot?eid='+eid;
		 					} 		
		 		}
		    });
 	
 	}
 	
 function changeEventStatusText(itemClicked){
	 if(eventStatus.toLowerCase()=='active'){			 
			$(itemClicked).find('a').text(props.eml_suspend_lbl);
			$(itemClicked).find('a').data("status","suspend");
			
			$(itemClicked).next().find("span").text(props.eml_active_lbl);
			$(itemClicked).next().addClass('green');
			$(itemClicked).next().find("span").data("status","active");
		 }
	 
		 else if(eventStatus.toLowerCase()=='suspended'){
			   $(itemClicked).find('a').text(props.eml_active_lbl);
				$(itemClicked).find('a').data("status","active");
				
				$(itemClicked).next().find("span").text(props.eml_suspend_status);
				$(itemClicked).next().removeClass('green');
				$(itemClicked).next().find("span").data("status","suspend");
		 }
		 else{
			 $(itemClicked).hide();
			 $(itemClicked).next().find("span").text(props.eml_closed_lbl);
			 $(itemClicked).next().removeClass('green');
			 $(itemClicked).next().find("span").data("status","closed");
				
		 }
	 document.getElementById("status").style.display="block";
 }
 	
 function levelChanged(status){
 		presentStatus=status;
 		var label="";
 		if(status=='basic')
 			label=props.eml_basic_lvl_lbl;
 		if(status=='pro'){
 			$('.removePro').remove();
 			label=props.eml_pro_lvl_lbl;
 		}if(status=='advanced'){
 			$('.removePro').remove();
            $('.removeAdv').remove();
 			label=props.eml_advanced_lvl_lbl;
 		}
 		$("#first").text(label);
 		//$("#first").text(status.charAt(0).toUpperCase()+status.substring(1,status.length));
 		if(presentStatus=='pro' && typeOfEvent=='RSVP'){
 			presentStatus='advanced';
 		}
 		if(presentStatus.toLowerCase()=="basic" || presentStatus.toLowerCase()=="pro"){
 			$("#first").data('status','do');
 			if(presentStatus.toLowerCase()=="basic" && typeOfEvent=='RSVP')
 				level=90;
 			else if(presentStatus.toLowerCase()=="basic")
 				level=100;
 			else if(presentStatus.toLowerCase()=="pro")
				level=200; 	
 		}else{
 				$("#first").data('status','stop');
 				$("#second").hide();	
 				$("#second").parent().css('width',"81px");
 				$("#level .switch-full").off();
 				stopLevelChange=true;
 			}
 		document.getElementById("level").style.display="block";
 	} 	
 	
 	function mouseOut(id){
 			$("#"+id+" .switch-full").animate({width: '81px'},function(){
 				$("#"+id+" .inactive-text").css("display", "none");
 				});
 			
 	}
 	function mouseHover(id){	
 			$("#"+id+" .switch-full").animate({width: '157px'},function(){
 						$("#"+id+" .inactive-text").css("display", "block");
 					});
 					
 	} 	

	function closeMenu(){
	  $menuLeft.removeClass('pushmenu-open').addClass('pushmenu-left'); 
	}
	
	function changeEventStatus(element){		
		var changeTo="ACTIVE";
		if(eventStatus.trim().toLowerCase()=='active'){		
			eventStatus='Suspended';
			changeTo='PENDING';
			}
		else{
			eventStatus='Active';
			changeTo='ACTIVE';
		}
			
		var url='/main/eventmanage/Snapshot!chanageEventStatus?eid='+${eid}+'&purpose='+changeTo;
		$.ajax({
	 		url:url,
	 		success:function(result){
			  if(!isValidActionMessage(result)) return;			 
			  changeEventStatusText(element);
	 		}
		});
	}
	<s:if test="%{subMgr==null}">	
	$("#second").text(props.eml_upgrade_lbl);
	 levelChanged(presentStatus);
	 changeEventStatusText($("#status .inactive-text"));
   </s:if>
   
 
</script>
</html>
