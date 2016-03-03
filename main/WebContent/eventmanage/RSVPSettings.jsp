<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="../js/rsvpsettings.js"></script>
 <s:if test="%{errorExists==true}">
	<s:fielderror theme="simple" />
  </s:if>
  <style>
  ul.errorMessage{
  	margin: 0 !important; 
  }

.top-align{
	margin-top:15px;
}
  </style>
  <div class="section-main-header">
  	<span class="">   <s:text name="rsvps.opt.todisplay"/>  </span> <span class="required">*</span>&nbsp;
	<i class="fa fa-info-circle info" id="rsvpoptionsinfo"  style="cursor:pointer" ></i>
  </div>
  
  
  
  
  
  <div class="white-box panel-body">
  <div id="rsvpStatusMsg" class="alert alert-danger" style="display:none;"></div>
  
  	<s:form id="rsvpsettingsform" name="rsvpsettingsform" action="RSVPSettings!updateRsvp" method="post" theme="simple" >
    <s:hidden name="eid"></s:hidden>
    <div class="row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
    		<input type="checkbox" name="AttendeeType" id="atttype"  class="atttype" checked="checked">
    		&nbsp;<span><s:text name="rsvps.attendeing.lbl"/></span>
    	</div>
    	<div style="height:15px;"></div>
    	<div class="sm-font background-options row" >
    		<div class="col-md-12 col-xs-12 col-sm-12 row top-align">
					<div class="col-sm-6 col-md-4 col-xs-8"><span><s:text name="rsvps.max.attendeingin.lbl"/> <span class="required">*</span></span>&nbsp;
						<i class="fa fa-info-circle info" id="maxattinfo"  style="cursor:pointer" ></i>
					</div>
					<div class="col-sm-3 col-md-2 col-xs-4">
						<s:textfield name="eventData.rsvpattendeelimit"  cssClass="form-control" theme="simple" size="5" maxlength="5"></s:textfield>
					</div>
					<div class="col-sm-3 col-md-5"></div>
				</div>
				<div class="col-md-12 col-xs-12 col-sm-12 row top-align">
					<s:set name="type" value="eventData.rsvptype"></s:set>
					<div class="col-sm-12 col-md-4 col-xs-12"><span><s:text name="rsvps.tot.att.count.lbl"/> <span class="required">*</span></span>&nbsp;
						<i class="fa fa-info-circle info" id="totalrsvpinfo"  style="cursor:pointer" ></i>
					</div>
					<div class="col-sm-12 col-md-6 col-xs-12">
					<div class="col-sm-4 col-md-4 col-xs-4">
						<input	type="radio" name="eventData.rsvptype" value="0" id="radio1" class="nolimit" onchange="checkRadio();"
						<s:if test="%{#type == 0}">checked="checked"</s:if> />&nbsp;<s:text name="rsvps.no.limit.lbl"/>
					</div>
					<div class="col-sm-4 col-md-4 col-xs-4">
						<input type="radio" name="eventData.rsvptype" value="1" id="radio2"
						<s:if test="%{#type == 1}">checked="checked"</s:if> onclick="checkRadio();"  class="limit"/>&nbsp;<s:text name="rsvps.limit.lbl"/>	
					</div>
					<div class="col-sm-4 col-md-4 col-xs-4" style="margin-left: -10px;">
						<s:textfield name="eventData.rsvplimit" id="rsvplimit" theme="simple" size="5" maxlength="5" cssClass="form-control"></s:textfield>
					</div>
					</div><div class="col-md-2"></div>
				</div>
    	</div>
    	
    	<div class="col-sm-12 md-12 colxs-12">
    		<input type="checkbox"  value="Y" name="eventData.notatttype"  class="ntatt" <s:if test='%{eventData.notatttype=="Y"}'>checked="checked"</s:if>>
    		&nbsp;
    		<span><s:text name="rsvps.not.attending.lbl"/></span>
    	</div>
    	
    	<div class="col-sm-12 md-12 colxs-12">
    		<input type="checkbox" name="eventData.notsuretype"  class="ntsure" value="notsure"<s:if test="%{eventData.rsvpnotsurelimit==0}"></s:if><s:else>checked="checked"</s:else>id="notsure" onclick="checknotsure();">
    		&nbsp;
    		<span><s:text name="rsvps.not.sure.lbl"/></span>
    	</div><div style="clear:both;"></div>
    	<div class="sm-font background-options row" id="openDiv" style="display:none;">
    	<div class="col-md-12 col-xs-12 col-sm-12 row top-align">
					<div class="col-sm-6 col-md-4 col-xs-8"><span><s:text name="rsvps.max.count.per.rsvp"/> <span class="required">*</span></span>&nbsp;
						<i class="fa fa-info-circle info" id="maxnotsureinfo"  style="cursor:pointer" ></i>
					</div>
					<div class="col-sm-3 col-md-2 col-xs-4">
					<s:textfield name="eventData.rsvpnotsurelimit" id="notsurelimit" cssClass="form-control ntsurelimit" theme="simple" size="5" maxlength="5"></s:textfield>
					</div>
					<div class="col-sm-2 col-md-6"></div>
				</div>
    	</div>
		<div class="text-center">
		 		<input id="rsvpsettingsupdatebtn" type="button" name="button" value="<s:text name='rsvps.update.settings.btn'/>"  class="btn btn-primary"/>
		 	</div>
    </div>
    </s:form>
  </div>
  
<!--   <br><br> -->
  
<!-- <div class="col-md-12"> -->
	<%-- <div id="rsvpStatusMsg" style="display:none;"></div>
    <s:form id="rsvpsettingsform" name="rsvpsettingsform" action="RSVPSettings!updateRsvp" method="post" theme="simple" >
    <s:hidden name="eid"></s:hidden>
		 <div class="col-sm-12 col-xs-12 col-md-12 row">
			<div class="col-sm-1 col-xs-1 col-md-1 text-right" style="margin-top: -5px;">
				<input type="checkbox" name="AttendeeType" id="atttype"  class="atttype" checked="checked">
			</div>
		
		</div>
	
		 
		 <div class="col-sm-12 col-xs-12 col-md-12 row top-align">
		 	<div class="col-sm-1 col-xs-1 col-md-1 text-right" style="margin-top: -5px;">
				<input type="checkbox" name="eventData.notsuretype"  class="ntsure" value="notsure"<s:if test="%{eventData.rsvpnotsurelimit==0}"></s:if><s:else>checked="checked"</s:else>id="notsure" onclick="checknotsure();">
			</div>
			<div class="col-sm-11 col-xs-11 col-md-11 row">
				<div class="col-sm-12 col-xs-12 col-md-12"> <span>Not Sure</span> </div>
				<div class="col-md-12 col-xs-12 col-sm-12 row top-align">
					<div class="col-sm-6 col-md-4 col-xs-8"><span>Max Attendee Count Per RSVP <span class="required">*</span></span>&nbsp;
						<i class="fa fa-info-circle info" id="maxnotsureinfo"  style="cursor:pointer" title="Maximum number of Attendees allowed per Not Sure RSVP "></i>
					</div>
					<div class="col-sm-3 col-md-2 col-xs-4">
					<s:textfield name="eventData.rsvpnotsurelimit" id="notsurelimit" cssClass="form-control ntsurelimit" theme="simple" size="5" maxlength="5"></s:textfield>
					</div>
					<div class="col-sm-2 col-md-6"></div>
				</div>
			</div>
		 </div>
		 <div class="col-sm-12 col-xs-12 col-md-12 row top-align">
		 	<div class="text-center">
		 		<input id="rsvpsettingsupdatebtn" type="button" name="button" value="Update Settings"  class="btn btn-primary"/>
		 	</div>
		 </div> --%>
		
		<%-- 
		<div class="row">
			<div class="col-md-1">
				<input type="checkbox" name="AttendeeType" id="atttype"  class="atttype" checked="checked"> 
			</div>
			<div class="col-md-11">Attending</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4 col-md-offset-1 colsm-5">Max Attendee Count Per RSVP * &nbsp;
			<i class="fa fa-info-circle info" id="maxattinfo"  style="cursor:pointer" title="Maximum number of Attendees allowed per RSVP. "></i>
			</div>
			<div class="col-md-2 col-sm-2">
				<s:textfield name="eventData.rsvpattendeelimit"  cssClass="form-control" theme="simple" size="5" maxlength="5"></s:textfield>
			</div><div class="col-md-5 col-sm-5"></div>
		</div>
		<br>
		<div class="row">
		<s:set name="type" value="eventData.rsvptype"></s:set>
			<div class="col-md-1"></div>
			<div class="col-md-4">Total RSVP Attendee Count Limit *&nbsp;
			<i class="fa fa-info-circle info" id="totalrsvpinfo"  style="cursor:pointer" title="Maximum number of Attendees for the event. Once this count is reached, no more RSVPs are accepted on the event. "></i>
			</div>
			<div class="col-md-2">
			<input	type="radio" name="eventData.rsvptype" value="0" id="radio1" class="nolimit" onchange="checkRadio();"
					<s:if test="%{#type == 0}">checked="checked"</s:if> />&nbsp;No Limit								
			</div>
			<div class="col-md-2">
			<input type="radio" name="eventData.rsvptype" value="1" id="radio2"
					<s:if test="%{#type == 1}">checked="checked"</s:if> onclick="checkRadio();"  class="limit"/>&nbsp;Limit	
			</div>
			<div class="col-md-2"><s:textfield name="eventData.rsvplimit" id="rsvplimit" theme="simple" size="5" maxlength="5" cssClass="form-control"></s:textfield></div>
		</div>
		<br> 
		<div class="row">
			<div class="col-md-1"><input type="checkbox"  value="Y" name="eventData.notatttype"  class="ntatt" <s:if test='%{eventData.notatttype=="Y"}'>checked="checked"</s:if>></div>
			<div class="col-md-11">Not Attending</div>
		</div>	
		<br>
		<div class="row">
			<div class="col-md-1"><input type="checkbox" name="eventData.notsuretype"  class="ntsure" value="notsure"<s:if test="%{eventData.rsvpnotsurelimit==0}"></s:if><s:else>checked="checked"</s:else>id="notsure" onclick="checknotsure();"></div>
			<div class="col-md-11">Not Sure</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-4">Max Not Sure Count Per RSVP&nbsp;
			<i class="fa fa-info-circle info" id="maxnotsureinfo"  style="cursor:pointer" title="Maximum number of Attendees allowed per Not Sure RSVP "></i>
			</div>
			<div class="col-md-2"><s:textfield name="eventData.rsvpnotsurelimit" id="notsurelimit" cssClass="form-control ntsurelimit" theme="simple" size="5" maxlength="5"></s:textfield></div>
		</div>
		<hr>
		<div class="row">
			<div class="col-md-offset-5">
				<input id="rsvpsettingsupdatebtn" type="button" name="button" value="Update Settings"  class="btn btn-primary"/>
			</div>
		</div>
		--%>
	<%-- </s:form> --%>
<!-- </div> -->

<script>
$(document).ready(function(){
	if($('input[name="eventData.notsuretype"]').is(':checked'))
		$('#openDiv').slideDown();
	else
		$('#openDiv').slideUp();
});
$('#rsvpsettingsupdatebtn').click(function(){	
	updateRSVPSettings();
});

$(function(){
	
	$('#maxnotsureinfo').attr('title',props.rsvps_max_no_atte_sure);
	$('#rsvpoptionsinfo').attr('title',props.rsvps_attending_info_process);    
	$('#maxattinfo').attr('title',props.rsvps_allowed_rsvp);
	$('#totalrsvpinfo').attr('title',props.rsvps_max_att_reached);
	
	
	 $('input.atttype').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	 $('input.ntatt').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.ntsure').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.ntsurelimit').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.atttype').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.limit').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.nolimit').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 
	 
	 $('input.nolimit').on('ifChecked', function(event){
		 checkRadio();
	 });
	 $('input.limit').on('ifChecked', function(event){
		 checkRadio();
	 });
	 
	 $('input.ntsure').on('ifChecked', function(event){
		 $('#openDiv').slideDown();
		 checknotsure();
	 });
	 $('input.ntsure').on('ifUnchecked', function(event){
		 $('#openDiv').slideUp();
		 checknotsure();
	 });
	 
	 $('#rsvpoptionsinfo').tooltipster({
		    fixedWidth:'100px',
		    position: 'bottom',
		 //   content:$('<span>By default Attending information is collected in the RSVP process. <br>If you would like to collect Not Attending and Not Sure information, select those options here.</span>'),
	 });
	 $('#maxattinfo').tooltipster({
		    fixedWidth:'100px',
		    position: 'bottom',
		  //  content:$('<span>Maximum number of Attendees allowed per RSVP.</span>'),
	 });
	 $('#maxnotsureinfo').tooltipster({
		    fixedWidth:'100px',
		    position: 'bottom',
		  //  content:$('<span>Maximum number of Attendees allowed per Not Sure RSVP</span>'),
	 });
	 $('#totalrsvpinfo').tooltipster({
		    fixedWidth:'100px',
		    position: 'bottom',
		   // content:$('<span>Maximum number of Attendees for the event. <br>Once this count is reached, no more RSVPs are accepted on the event.</span>'),
	 });
});

init();
</script>