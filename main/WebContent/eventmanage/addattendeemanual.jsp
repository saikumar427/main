<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.smallestfont {
    color: #666666;
    font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 10px;
    font-weight: lighter;
}

table{
border-collapse: unset !important;
border-spacing:2px !important;
}

</style>
<s:set name="recurringdates" value="recurringeventsVec"/>
<s:set name="eventdate" value="eventdate"/>

<link href="../css/seatingaddmanual.css" type="text/css" rel="stylesheet">
<link href="../css/listing.css" type="text/css" rel="stylesheet">
<script type="text/javascript" language="JavaScript" src="../js/common/json.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/ticketwidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/textboxWidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/textareaWidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/selectWidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/radioWidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/checkboxWidget.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/controls/buildcontrol.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/eventmanage/addattendee.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/eventmanage/addrsvpattendee.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/eventmanage/seating/generateseating.js"></script>

<script type="text/javascript" language="JavaScript" src="../js/states.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/creditcardpayment.js"></script>
 <script type="text/javascript" language="JavaScript" src="/main/js/creditcardpopupscreen.js"></script>

<jsp:include page="/help/eventmanage_messages.jsp"></jsp:include>
<s:if test="%{powertype=='RSVP' && currentLevel=='90'}">
<div class="prohelpcontent"><script>setHelpContent("RSVP_Profeature_helpcontent")</script></div><br/>
</s:if>
<input type="hidden" id="tktdetails">
<div id="container" style="display:none;padding:0px"></div>
<div id="backgroundPopup"></div>
<div class="taskbox white-box" width="100%" style="overflow:auto;">
<%-- <s:if test="%{type=='rsvp'}">
<span class="taskheader section-main-header" id='titleDiv'>RSVP</span><br/>
</s:if>
<s:else>
<span class="taskheader section-main-header" id='titleDiv'>Select Tickets</span><br/>
</s:else>
<hr/> --%>
<div>
<table width="100%" cellpadding="0" cellspacing="0">
<s:if test="#recurringdates!=null&&#recurringdates.size()>0">
<tr><td id='recurringblock' style='dislay:block' width="100%">
<div class="row">
	
	<s:if test="%{recurrdatestxtlabel!=''}">
	<div class="col-md-3 col-sm-4 col-xs-5" style=" padding-top: 8px;" ><span> ${recurrdatestxtlabel} </span></div>
	</s:if>
	<s:else>
	<div class="col-md-3 col-sm-4 col-xs-5" style=" padding-top: 8px;"><span><s:text name="aa.select.date.time.lbl"/>:</span></div>
	</s:else> 
	
	<s:if test="%{type=='rsvp'}">
	<div class="col-md-4 col-sm-4 col-xs-7">
	<select name='eventdate' id='event_date' onChange='getRSVPPageforselectedDates()' class="form-control">
	<option value=''><s:text name="aa.select.date.time.default.lbl"/></option>
	<s:iterator value="#recurringdates" var="index">
	<option value='<s:property  value="#index" />'>
	<s:property  value="index" /></option>
	</s:iterator>
	</select>
	</div>
	</s:if>
	<s:else>
	
	<div class="col-md-4 col-sm-4 col-xs-7">
	<select name='eventdate' id='event_date' onChange='getTicketsPageforselectedDates()' class="form-control">
	<option value=''><s:text name="aa.select.date.time.default.lbl"/></option>
	<s:iterator value="#recurringdates" var="index">
	<option value='<s:property  value="#index" />' >
	<s:property  value="index" /></option>
	</s:iterator>
	</select>
	</div>
	</s:else>
	<div class="col-md-5 col-sm-4"></div>
</div>
</td></tr>
</s:if>
<tr><td><div style="height:10px;"></div></td></tr>
<tr><td id="addattendeeStatus" style="display:none;"></td></tr>

<s:if test="%{type=='rsvp'}">
<tr><td id="rsvpregistration"  width="100%" >
<br>
<div class="">
<div class="col-md-1 col-xs-2"  style="padding-top: 8px;">${attendingLabel}</div> 
<div class="col-md-2 col-xs-3"><s:textfield cssClass="form-control" id="attendeecount" theme="simple" size="10" maxlength="5" onkeypress="return isNumberKey(event)" />
</div>
<div class="col-md-2 col-xs-4">
	<input type="button" name="Continue" id="continue" value="<s:text name="global.continue.btn.lbl"/>" class="btn btn-primary" onclick="validateRSVPSelectCount();">
</div><div class="col-md-7 col-xs-3"></div>
<%-- <table cellpadding="0" cellspacing="0" align="center" width="100%" style="position:relative;">
<tr><td>
<div style="position:relative;"><span style="position:absolute;top:40px;">
<input type="button" name="Continue" id="continue" value="Continue" class="btn btn-primary" onclick="validateRSVPSelectCount();">
</span>
</div>
</td></tr></table> --%>
</div>

</td>


</tr>
<tr><td id="rsvpprofile"  width="100%" style="padding:0px;margin:0px;"></td></tr>
<tr><td id="imageLoad" width="100%" style="display:none;padding:0px;margin:0px;"></td></tr>

<script>
//var btn1 = new YAHOO.widget.Button("continue", { onclick: { fn: validateRSVPSelectCount } });
AssignRSVPJsonData(${eid});
</script>
</s:if>
<s:else>
<tr><td id="registration"  width="100%" style="padding:0px;margin:0px;"></td></tr>
<tr><td id="profile"  width="100%" style="padding:0px;margin:0px;"></td></tr>
<tr><td id="payment"  width="100%" style="padding:0px;margin:0px;"></td></tr>
<tr><td id="imageLoad" width="100%" style="display:none;padding:0px;margin:0px;" ></td></tr>
<script>
AssignTicketsJsonData(${eid},${ticketsdata});
</script>
</s:else>
</table>
</div>
</div>
<div style="height:120px;"></div>
<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
</div>
