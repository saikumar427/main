<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   
<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js?idj=5"></script>
   
   
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">

<meta name="verify-v1"
	content="/2obWcBcvVqISVfmAFOvfTgJIfdxFfmMOe+TE8pbDJg=" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META Http-Equiv="Cache-Control" Content="no-cache">
	<META Http-Equiv="Pragma" Content="no-cache">
		<META Http-Equiv="Expires" Content="0">
			<title><s:text name="mth.eventbee.title"/></title>
			<meta name="Description"
				content="Easy to use online Event Management solution that includes Online Registration, Event Ticketing, Event Promotion and Membership Management" />
			<meta name="Keywords"
				content="events, sell tickets, online registration, event ticketing, social ticketing, event promotion, paypal payments, google check out, facebook events, ning events, party tickets, sports tickets, venue seating" />
			<meta name="viewport" content="width=device-width, initial-scale=1.0">



	<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
	<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet" /> 
	<link href="<%=resourceaddress%>/main/bootstrap/css/custom.css?idc=2" rel="stylesheet">

	<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/jquery.datetimepicker.css" />
	<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/jquery.datetimepicker.css" />
	<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/grey.css" />

	<link rel="icon" href="<%=resourceaddress%>/main/images/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="<%=resourceaddress%>/main/images/favicon.ico" type="image/x-icon">
	<link type="text/css" rel="stylesheet" href="<%=resourceaddress%>/main/bootstrap/css/tooltipster.css" />
<style>
#notify-wrapper {
    height: 40px;
    left: 43%;
    background-color: #E8F7ED;
    top: 120px;
    text-align: center;
    position: fixed;
    z-index: 1001;
    border: 1px solid #abe2bd;
    border-radius: 6px;
    width: 200px;
    padding: 8px;
} 

.navbar-nav {
	#margin: 18px -8px;
}

.signup {
	margin-top: -8px
}

#loadingwait {
    background-color: #f0f0f0 !important;
    bottom: 0;
    left: 0;
    opacity: 0.55;
    position: fixed;
    right: 0;
    top: 0;
    z-index: 1050;
}


</style>

</head>

<s:set name="menucontextH" value="#attr['menucontext']" />
<s:set name="othercontextH" value="#attr['othercontext']" />
<s:set name="actionName" value="menuMap[#attr['currentaction']]"/>
<s:set name="I18N_CODE_PATH" value="I18N_CODE_PATH"></s:set>
<script src="<%=resourceaddress%>/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="<%=resourceaddress%>/main/bootstrap/js/custom.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<s:if test='%{#actionName=="Reports" || #actionName=="RSVPReports"}'>
	<script type="text/javascript" src="/main/js/reportsDatatable.js"></script>
</s:if>
<script>
	window.resizeIframe = function() {
		var obj = document.getElementById('popup');
		obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
	}
</script>
<body>
	<!-- responsive navbar
===============================-->

	<div
		style="background: none repeat scroll 0px 0px rgb(243, 246, 250); min-height: 53px;display:block;"
		class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button data-target=".navbar-collapse" data-toggle="collapse"
					class="navbar-toggle">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<br /> 
			
					
					<a style="margin-bottom: -16px; margin-top: -23px;"
					class="navbar-brand" href="/main/myevents/home"><img height="36px" alt="Eventbee"
					src="http://www.eventbee.com/main/images/logo.png" /></a>
				<%-- <s:if test="%{#I18N_CODE_PATH != ''}">
				<a style="margin-bottom: -16px; margin-top: -23px;"
					class="navbar-brand" href="<s:property value="I18N_CODE_PATH"/>"><img height="36px" alt="Eventbee"
					src="http://www.eventbee.com/main/images/logo.png" /></a>
				</s:if>
				<s:else>
				<a style="margin-bottom: -16px; margin-top: -23px;"
					class="navbar-brand" href="/"><img height="36px" alt="Eventbee"
					src="http://www.eventbee.com/main/images/logo.png" /></a>
				</s:else> --%>
					
					
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<s:if test="%{subMgr==null}">
					<li><a href="../myevents/home" class="<s:if test="%{#menucontextH=='events'}">header-li-active</s:if><s:else>header-li</s:else>"> <s:text name="hdr.myevents.lbl"/></a></li>
					<li><a href="../myaccount/home" class="<s:if test="%{#menucontextH=='settings'}">header-li-active</s:if><s:else>header-li</s:else>"><s:text name="hdr.account.lbl"/></a></li>
				<!--	<li><a href="../mysettings/home" class="<s:if test="%{#menucontextH=='account'}">header-li-active</s:if><s:else>header-li</s:else>"><s:text name="hdr.settings.lbl"/></a></li>-->	
                                    <s:if test='%{marketingUser=="Y"}'>
							<li><a href="../mymarketing/home" class="<s:if test="%{#menucontextH=='marketing'}">header-li-active</s:if><s:else>header-li</s:else>"><s:text name="hdr.marketing.lbl"/></a></li>
					   </s:if>
					</s:if>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<!-- <li><a id="getTickets" href="javascript:;">Get my tickets</a></li> -->
					<s:if test="%{subMgr==null}">
						<li class="nav-btn" id="contact"><a  href="#"><button
						class="btn btn-sm btn-border"><s:text name="hdr.contact.lbl"/></button></a></li>
						<li class="nav-btn"><a href="/main/user/logout"><button
									class="btn btn-sm btn-border"><s:text name="global.logout.btn.lbl"/></button></a></li>
					
					</s:if>
					<s:else>
						<li style="display: inline;"><a
							href="/main/submanager/events"><s:text name="hdr.myhome.lbl"/></a></li>
						<li class="nav-btn" id="contact"><a href="#"><button
					       class="btn btn-sm btn-border"><s:text name="hdr.contact.lbl"/></button></a></li>
						<li class="nav-btn"><a
							href="/main/submanager/logout"><button
									class="btn  btn-sm btn-border"><s:text name="global.logout.btn.lbl"/></button></a></li>
					</s:else>
				</ul>
			</div>
			<div id="notify-wrapper" style="display:none;"><s:text name="hdr.copy.clip"/></div>
		</div>
	</div>
	<%-- <s:if test="%{subMgr==null}">
		<div class="nav-top-eb-fixed">
			<div class="container eb-container">
				<div class="eb-row">
					<div id="topmenusection" width="100%">
						<ul id="topmenu" class="nav nav-pills  header">
							<li><a href='../myevents/home' class="nav-menu-top active">
									&nbsp;My Events </a></li>
							<li><a href='../mysettings/home' class="nav-menu-top">My
									Settings</a></li>
							<li><a href='../myaccount/home' class="nav-menu-top">My
									Account</a></li>
							<li><a href='../mymarketing/home' class="nav-menu-top">My
									Marketing</a></li>
							<li style="float: right; margin-top: 10px;">
								<div id="othermenu">
									<table align="right" cellspacing="0" cellpadding="0"
										valign="top">
										<tbody>
											<tr>
												<td align="right"><a class="nav-menu-top-right"
													target="_blank" href="#">Fundraising</a></td>
												<td valign="top"><span class="font">&nbsp;&nbsp;</span>
												</td>
												<td align="right"><a class="nav-menu-top-right"
													href="#"> <span>Communities</span>
												</a></td>
											</tr>
										</tbody>
									</table>
									<div></div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</s:if> --%>
	<!-- modal dialog
===========================-->
	<div class="col-md-12">
		<!-- Modal -->
		<div class="modal" id="myModal" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<script>
							function hidePopup() {
								$('#myModalhtml').hide();
								$('.background').hide();
								$('.container input').removeAttr("tabindex");
								$('.container button').removeAttr("tabindex")
								$('.container a').removeAttr("tabindex");
								$('.container select').removeAttr("tabindex");
								var obj = document.getElementById('popup');
								obj.style.height = obj.contentWindow.document.body.scrollHeight
										+ 'px';
							}

							function modalOnLoad() {
								//alert("mu modalonload");
								hidePopup();
							}
						</script>
						<iframe id="popup" src="" width="100%" style="height: 430px"
							frameborder="0" onload="modalOnLoad()"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- loading modal dialog
 ===========================-->
	<div class="modal" id="myModalhtml" tabindex="-1" role="dialog"
		aria-hidden="true" style="height: 1700px;">
		<div class="modal-dialog  modal-sm"
			style="width: 100px; margin: 30% auto; z-index: 2147483690">
			<div class="modal-content">
				<div class="modal-body">
					<img src="/main/images/ajax-loader-bt.gif" style="width: 100%"></img>
				</div>
			</div>
		</div>
		<div class="modal-backdrop fade in"></div>

 </div> 
	
	
	<div  id="loadingwait" style="display:none;" >
		<div id="loadingspinner" style="margin-top:310px;"><center><img src="/main/images/spinner.gif" style="width:51px;"></img><br/>&nbsp;<s:text name="global.loading.msg"/>  </center></div>
	</div>
	
	
	
	<div id="singleValuePopUp">
	<div id="singleerror" style="display:none; color: red;"></div>
	    <input type="text" key="VALUE" class="form-control" style="width:55%;" id="onefieldtext"/>
	    <input type="button" value="Submit" class="btn btn-sm btn-primary" key="OK"/>
	    <input type="button" value="Cancel" class="btn btn-sm btn-cancel" key="CANCEL"/>
	</div>
         
<div id="singleValuePopUpOptions">
	<div id="singleerroroptions" style="display:none; color: red;"></div>
<span id="defoption"></span>	
	    <input  key="VALUEOPTION" class="form-control" style="width:100%;" id="onefieldtextoption"/>
	    <span id="lable"></span><div style="height:10px;"></div>
	    <input type="button" value="Submit" class="btn btn-sm btn-primary" key="YES"/>
	    <input type="button" value="Cancel" class="btn btn-sm btn-cancel" key="NO"/><br/>
	    
	</div>
     <div>
	   <div style="clear:both;min-height:36px" ></div>
	</div>

	<!-- Html modal -->
		<div class="modal" id="myModalhtmlin" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog" style="z-index:9999;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3><label class='headlbl'></label></h3>
                        </div>
                        <div class="modal-body">
                         </div>
                        <div class="modal-footer center" style="border-top:0px;">
                           
                        </div>
                    </div>
                   
                </div>
                 <div class="modal-backdrop fade in"></div>
     </div>
     <!-- Html modal end -->
     
<div class="col-md-12">
		<div class="modal" id="upgradespecialfee" tabindex="-1" role="dialog" aria-hidden="true"> <!-- data-backdrop="static" data-keyboard="false" -->
			<div class="modal-dialog modal-lg modal-md modal-sm RSVPremoveWidth" style="width:80%; height: 500px;">
				<div class="modal-content">
				<div class="text-center" id="loadIMG" style="display:none;"><img src="/main/images/layoutmanage/Pspinner.gif" style="width:60px; margin-bottom: -160px; padding-top: 100px;"/></div>
					<iframe id="upgradepopup" src="" width="100%" style="height:500px;" frameborder="0" ></iframe>
				</div>
			</div>
		</div>
</div>
<script>
	var typeOfEvent = '<s:text name="powertype" />';
	if('RSVP'==typeOfEvent){
		$('.RSVPremoveWidth').css('width','70%');
	}
	$('#upgradespecialfee').on('hide.bs.modal', function () {
		 $('iframe#upgradepopup').attr("src","");
		if($('#currentlevel').val()=='100' || $('#currentlevel').val()=='90')
			$('.password').iCheck('unCheck');
	});
	function closeUpgradePopup(){
		//$('#upgradepopup').modal('hide');
		$('#upgradespecialfee').modal('hide');
		$('iframe#upgradepopup').attr("src","");
	}
</script>
<style type="text/css" title="currentStyle">
@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}
</style> 

	<script src="<%=resourceaddress%>/main/js/jquery-ui-1.9.2.js"></script>
	<script src="<%=resourceaddress%>/main/js/jquery-sortable.js"></script>
	<script src="<%=resourceaddress%>/main/js/jquery.tooltipster.js"></script>
	<script src="<%=resourceaddress%>/main/js/jquery.validate.min.js"></script>
	<script src="<%=resourceaddress%>/main/js/jquery.ezpz_hint.js"></script>

	<script
		src="<%=resourceaddress%>/main/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="<%=resourceaddress%>/main/bootstrap/js/jquery.datetimepicker.js"></script>
	<script src="<%=resourceaddress%>/main/bootstrap/js/icheck.min.js"></script>
	<script
		src="<%=resourceaddress%>/main/bootstrap/js/bootstrap-switch.min.js"></script>
	<script src="<%=resourceaddress%>/main/bootstrap/js/bootbox.min.js"></script>

	<script>
		$(function() {
			$('#contact,#uiFeedback').click(
					function() {
						$('.modal-title').html(props.la_supp_popup_hdr);
						//$('#myModal').on('show.bs.modal', function () {
						$('iframe#popup').attr("src",
								'/main/user/homepagesupportemail.jsp');
						$('iframe#popup').css("height", "440px");
						//});
						$('#myModal').modal('show');
					});
			$('#getTickets').click(
					function() {
						$('.modal-title').html(props.la_get_tkts_popup_hdr);
						//$('#myModal').on('show.bs.modal', function () {
						//	alert("get my tickS");
						$('iframe#popup').attr("src",
								'/main/user/homepagemytickets.jsp');
						$('iframe#popup').css("height", "435px");
						//});
						$('#myModal').modal('show');
					});
			$('#myModal').modal({
				backdrop : 'static',
				keyboard : false,
				show : false
			});
			$('#myModal')
					.on(
							'hide.bs.modal',
							function() {
								$('iframe#popup').attr("src", '');
								$('#myModal .modal-body')
										.html(
												'<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0" onload="modalOnLoad()"></ifg____');
							});
			
			$('#myModalhtmlin').on('click','.close,.cls',function(){$('#myModalhtmlin').hide();    
             });
		});

		/* var isValidActionMessage = function(messageText) {
			if (messageText.indexOf('nologin') > -1
					|| messageText.indexOf('login!authenticate') > -1) {
				alert('You need to login to perform this action');
				window.location.href = "/main/user/login";
				return false;
			} else if (messageText.indexOf('Something went wrong') > -1) {
				alert('Sorry! You do not have permission to perform this action');
				closediv();
				return false;
			}
			return true;
		} */

		function loadingPopup() {

			//$('#myModalhtml').show();
			$('#loadingwait').show();
			$('.background').show();
			$('.container input').attr("tabindex", "-1");
			$('.container button').attr("tabindex", "-1");
			$('.container a').attr("tabindex", "-1");
			$('.container select').attr("tabindex", "-1");
		}


function closepopup(){
	$('#myModal').modal('hide');
	$('#loadingwait').hide();
}



		function hidePopup() { 
			$(".dtsavebtn").removeAttr("disabled");
            $('#loadingwait').hide();
			$('#myModalhtml').hide();
			$('.background').hide();
			$('.container input').removeAttr("tabindex");
			$('.container button').removeAttr("tabindex")
			$('.container a').removeAttr("tabindex");
			$('.container select').removeAttr("tabindex");
			var obj = document.getElementById('popup');
			obj.style.height = obj.contentWindow.document.body.scrollHeight
					+ 'px';
		}

		function modalOnLoad() {
			//alert("on load modal");
			hidePopup();
		}
		var openHTMLPopUp=function (header,content,calback,footernreq){
		    $('#myModalhtmlin .headlbl').html('');
		    $('#myModalhtmlin .modal-body').html('');
		    $('#myModalhtmlin .modal-footer').html('');
		    $('#myModalhtmlin').show();
		    $('#myModalhtmlin .headlbl').html(header);
		    $('#myModalhtmlin .modal-body').html(content);
		    $('#myModalhtmlin .modal-header').css({padding:0});
		    $('#myModalhtmlin .close').css({padding:"3px 25px"});
		    $('#myModalhtmlin .headlbl').css({"margin-left":"19px"});
		    $('#myModalhtmlin .headlbl').css({"color":"#b0b0b0","font-size":"22px","font-weight":"500"});
		    if(footernreq!=false)
		    $('#myModalhtmlin .modal-footer').html($('<button class="btn btn-primary" onclick='+calback+'(); ><s:text name="global.continue.btn.lbl"/></button><button class="btn btn-cancel cls" ><i class="fa fa-times"></i></button>'));
		    
		};
	</script>
	
