<%@taglib uri="/struts-tags" prefix="s"%>

<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>


<html>
<head>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
 
<%-- <script src="/main/js/bootstrap.min.js"></script> --%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<script type="text/javascript" src="/main/js/eventmanage/specialfee.js"></script>

<style>

#specialfee li{
list-style: outside !important;
}
</style>

</head>
<body>
<div class="container">
<s:form name="specialfee" id="specialfee" action="SpecialFee!specialFee" method="post">
<div id="specialfeeerrormessages"></div>
<s:if test="%{ticketingtype.contains('150')}">    
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketingtype"></s:hidden>
<s:hidden name="servicefee"></s:hidden>
<s:hidden name="source"></s:hidden>
<div class="row">

<div class="col-xs-2"></div>
<div class="col-xs-8" align="center"><b><s:text name="up.thisis.rsvp.pro.lbl"/></b></div>
<div class="col-xs-2"></div>
</div>
<div style="height:5px;"></div>
<div class="row">
<div class="col-xs-3"></div>
<div class="col-xs-6" align="center">${currencySymbol}${servicefee}<s:text name="up.flat.ser.fee.lbl"/></div>
<div class="col-xs-3"></div>
</div>
<div style="height:15px;"></div>
<div class="row">

<div class="col-xs-12" align="center">
<ul>
    <li class="pull-left"><s:text name="up.access.to.evt.lbl"/></li>
	<li class="pull-left"><s:text name="up.customize.conf.page"/></li>
	<li class="pull-left"><s:text name="up.customize.word.page"/></li>
	<li class="pull-left"><s:text name="up.trackurl.monitor"/></li>
	<li class="pull-left"><s:text name="up.get.tkt.widget"/></li>
	<!-- <li class="pull-left">And any other item having 'P' icon is Pro feature</li> -->
</ul>
</div>
<div class="col-xs-1"></div>
</div>
<!-- <table align="center">
<tr><th align="center">Pro RSVP</th></tr>
<tr><td align="center">$${servicefee} Flat Service Fee/Response</td></tr>
<tr><td>
<ul>
    <li>Access to event page HTML & CSS to build your branded event page</li>
	<li>Customize confirmation page & confirmation email</li>
	<li>Custom wording for event page</li>
	<li>Tracking URLs to monitor visits and responses from different sources</li>
	<li>Get ticket widgets to embed in web pages</li>
	<li>And any other item having 'P' icon is Pro feature</li>
</ul>
</td></tr>
</table> -->
</s:if>
<s:elseif test="%{ticketingtype.contains('200')}">
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketingtype"></s:hidden>
<s:hidden name="servicefee"></s:hidden>
<s:hidden name="source"></s:hidden>

<div class="row">

<div class="col-xs-2"></div>
<div class="col-xs-8" align="center"><b><s:text name="up.thid.belongs.pro.tkting"/></b></div>
<div class="col-xs-2"></div>
</div>
<div style="height:10px;"></div>
<div class="row">
<div class="col-xs-3"></div>
<div class="col-xs-6" align="center">${currencySymbol}${servicefee} <s:text name="up.flat.fee.per.tkt"/> </div>
<div class="col-xs-3"></div>
</div>
<div style="height:3px;"></div>
<div class="row">
<div class="col-xs-12" align="center"><s:text name="up.tip.lbl"/></div>
</div>


<!-- <table align="center">
<tr><th align="center"> Pro Ticketing</th></tr>
<tr><td align="center">$${servicefee} Flat Service Fee Per Ticket<br/>Collect this fee from your attendee and use Eventbee for free!</td></tr>
<tr><td align="center"></td></tr>
</table> -->
<!-- <ul>
	<li>Add discounts to tickets</li>
    <li>Tracking URLs to monitor visits and sales from different sources</li>
    <li>Private ticketing URLs to sell tickets privately</li>
    <li>Email attendees at any time</li>
    <li>Get ticket widgets to embed in web pages</li>
</ul> -->


</s:elseif>
<s:else>
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketingtype"></s:hidden>
<s:hidden name="servicefee"></s:hidden>
<s:hidden name="source"></s:hidden>
<!-- <table align="center">
<tr><th align="center"> Advanced Ticketing</th></tr>
<tr><td align="center">$${servicefee} Flat Service Fee Per Ticket<br/>Collect this fee from your attendee and use Eventbee for free!</td></tr>
<tr><td align="center"></td></tr>
</table> -->

<div class="row">
<div class="col-xs-2"></div>
<div class="col-xs-8" align="center"><b><s:text name="up.thisis.adv.tkting"/> </b></div>
<div class="col-xs-2"></div>
</div>
<div style="height:10px;"></div>
<div class="row">
<div class="col-xs-3"></div>
<div class="col-xs-6" align="center">${currencySymbol}${servicefee} <s:text name="up.flat.fee.per.tkt"/> </div>
<div class="col-xs-3"></div>
</div>
<div style="height:5px;"></div>
<div class="row">
<div class="col-xs-12" align="center"><s:text name="up.tip.lbl"/></div>
</div>

<!-- <tr><td>
<ul>
    <li>Venue and seating chart digitization</li>
    <li>Attendees can reserve seats while buying tickets</li>
    <li>Add discounts to tickets</li>
    <li>Add custom questions at transaction and ticket level</li>
    <li>Access to event page HTML & CSS to build your branded event page</li>
    <li>Customize confirmation page & confirmation email</li>
    <li>Tracking URLs to monitor visits and sales from different sources</li>
    <li>Private ticketing URLs to sell tickets privately</li>
    <li>Email attendees at any time</li>
    <li>Get ticket widgets to embed in web pages</li>
    <li>Get attendee check-in module</li>
</ul>
</td></tr> -->

</s:else>
</s:form>
<hr>
<div class="form-group">
                        <div class="center">
                            <button type="submit" class="btn btn-primary" id="upgradesubmitbtn">
                               <s:text name="up.upgrade.btn.lbl"/></button>
                            <button class="btn btn-active" id="cancel">
                               <s:text name="up.nothanks.btn.lbl"/></button>
                        </div>
                    </div>
</div>
<script>
$(document).ready(function(){
	$('#upgradesubmitbtn').click(function(){
		parent.submitform($('#specialfee').serialize());
	});

	  $('#cancel').click(function() {
          parent.closepopup();
      });
		
		
			
});

</script>


</body>

</html>
