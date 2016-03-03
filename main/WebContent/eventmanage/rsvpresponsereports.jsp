<%@taglib uri="/struts-tags" prefix="s"%>

<div class="">
			
				<label class="datesel" id="sel_date_range" style="cursor: pointer;">
					<input type="checkbox" id="datesel_id" name="rsvpReportsData.resptdateradio" />
				</label>	
				<span id="datesel_lbl" class="checkbox-space"><s:text name='global.select.date.range.lbl'/></span>
				<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="daterangespin" style="cursor: pointer;"></span> --%>
			</div>
			
	<div class="background-options" id="datepicker" style="display:none;">
	   <div class="form-inline">
	   		<div class="form-group">
	   			<s:set name="attStartDate" value="rsvpReportsData.eventStartDate"/>
	   			<div><s:textfield style="width:100px" id="attfromdt" name="rsvpReportsData.eventStartDate" data-type="date" cssClass="form-control date" size="12"></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   		<div class="form-group"><label><s:text name='global.to.lbl'/></label></div>
	   		<div class="form-group">
	   			<s:set name="attEndDate" value="rsvpReportsData.eventEndDate"/>
	   			<div><s:textfield id="atttodt" style="width:100px" name="rsvpReportsData.eventEndDate" data-type="date" cssClass="form-control date" size="12" ></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   </div>
	</div>
	   
	   <div class="">
		  <label class="att-display-fields" style="cursor: pointer;">
		  	<input type="checkbox" id="displayflds_id" name="displayflds"/>
		 </label>
		 <span id="attendeeDisFlds" class="checkbox-space"><s:text name='rep.display.flds.lbl'/></span>
			<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="disfldsspin"></span> --%>
	   </div>
	
<div class="row rsvpattnddisplayflds display-fields-div" id="rsvpAttendeeDisFldsData">
<div class="background-options xsm-font">
<div class=""> 
<div class="fields-select-all links-div sm-font">
	<a href="javascript:;" class="rsvpresselect"><s:text name='global.select.all.lbl'/></a>
   <a href="javascript:;" class="rsvpresclear"><s:text name='global.clear.all.lbl'/></a>
</div>
</div> 
<div style="clear:both;"></div>                                     
 <div>                                       
                                        
                                        <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="resFields" value="Transaction Date"
					<s:if test="%{resFields.contains('Transaction Date')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.trans.date.lbl'/></div>
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" id="rsvprestid" name="resFields" value="Transaction ID"
					<s:if test="%{resFields.contains('Transaction ID')}">checked</s:if>></input>&nbsp;<s:text name='global.transaction.id.lbl'/>
                                  				 	</div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="resFields" value="First Name"
					<s:if test="%{resFields.contains('First Name')}">checked</s:if>></input>&nbsp;<s:text name='global.first.name.lbl'/></div>
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="resFields"
					value="Last Name"
					<s:if test="%{resFields.contains('Last Name')}">checked</s:if>></input>&nbsp;<s:text name='global.last.name.lbl'/></div>
                                        		
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="resFields" value="Email"
					<s:if test="%{resFields.contains('Email')}">checked</s:if>></input>&nbsp;<s:text name='global.email.lbl'/></div>
                                  				  <div class="col-md-6 col-sm-6">
                                        			
                                        		 		<input type="checkbox" name="resFields" value="Response"
					<s:if test="%{resFields.contains('Response')}">checked</s:if>></input>&nbsp;<s:text name="rep.disflds.response.lbl"/>
                                        		</div>
                                         		<div class="col-md-6 col-sm-6">
                                         		<input type="checkbox" name="resFields" value="Tracking URL"
					<s:if test="%{resFields.contains('Tracking URL')}">checked</s:if>></input>&nbsp;<s:text name="rep.disflds.tracking.url.lbl"/></div>
                                  				 <div class="col-md-6 col-sm-6">
                                        		<input type="checkbox" name="resFields"
					value="Notes"
					<s:if test="%{resFields.contains('Notes')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.notes.lbl'/>
                                  				 </div>
                                  				 
                                         <s:if test="%{isrecurring==true}">
                                        		 <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="resFields" value="Event Date"
					<s:if test="%{resFields.contains('Event Date')}">checked</s:if>></input>&nbsp;<s:text name='global.event.date.lbl'/>
                                        		</div>
                                        </s:if>
                                         		
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="resFields" value="Transaction Source"
					<s:if test="%{resFields.contains('Transaction Source')}">checked</s:if>></input>&nbsp;<s:text name="rep.disflds.trans.source.lbl" />
                                        		</div>
</div>
             <div style="clear:both;"></div>
             <div>                           
                                        	<s:set name="respattributes" value="%{respattributes.size()}"/>
			<s:if test="%{#respattributes>0}">
                                        <div><span class="col-md-6 col-sm-6 sm-font"><s:text name='rep.disflds.custom.questions.lbl'/></span></div>
               <div style="clear:both;"></div>
                                        
                                       <s:iterator value="%{respattributes}" var="buyer" status="stat">
                                         		<div class="col-md-6 col-sm-6">
			<s:checkbox name="resFields"  fieldValue="%{key}" value="%{responseQuestionFields.contains(key)}"  theme="simple"/>&nbsp;${value}
                                  				 </div>
                                  				  </s:iterator>
                                  				  </s:if>
                                  				  </div>
                                  				  <div style="clear:both;"></div>
</div>
</div>                                      
<div class="form-inline bottom-gap" id="rsvp_res_filters" style="display:none;" >
					<hr>
						<div class="form-group">
							<select class="form-control" id="responseTypeId"  name="rsvpReportsData.attselindex" onchange="showSureNotSure(this.value);filterRSVPDataTable('rsvpresponsecontentreports');">
								<option value="1"><s:text name="rep.filter.all.responses.lbl"/></option>
								<option value="Yes"><s:text name="rep.filter.attending.lbl"/></option>
								<option value="No"><s:text name="rep.filter.not.attending.lbl"/></option>
							</select>
						</div>
						<div class="form-group" style="display:none;" id="sureNotSureId">
						<div>
							<select class="form-control" id="attndTypeId"  name="rsvpReportsData.attendeeType" onchange="filterRSVPDataTable('rsvpresponsecontentreports');">
								<option value="1"><s:text name="rep.filter.sure.notsure.lbl"/></option>
								<option value="sure"><s:text name="rep.filter.sure.lbl"/></option>
								<option value="notsure"><s:text name="rep.filter.not.sure.lbl"/></option>
							</select>
						</div>
						
						<%-- <div id="repsearchsource">
							<s:select  name="rsvpReportsData.trackCode" id='sourcelist' headerKey="alltrackcodes" headerValue="All Tracking URLs"
							list="trackCodes" listKey="key" listValue="value"  cssClass="form-control" style="border-top-left-radius: 4px;border-bottom-left-radius: 4px;"/>
						</div> --%>
						</div>
						<div class="form-group">
							<input type="checkbox" class="srctype" name="rsvpReportsData.bookingSourceType" value="Online" id="mainurl" checked="checked"></input>&nbsp;<s:text name="rep.filter.online.lbl"/>&nbsp;
							<input type="checkbox" class="srctype" name="rsvpReportsData.bookingSourceType" value="Manager" id="addmanualurl" checked="checked"></input>&nbsp;<s:text name="rep.filter.manual.lbl"/>&nbsp;
						</div>
						
						<div class="form-group">	
							<div  id="rsvpExportDisplay" style="margin-bottom: 3px;">
							<span class="links-div">
							<a href="javascript:reportexport('excel')"><s:text name='rep.export.excel.lnk.lbl'/></a>
							<a href="javascript:reportexport('csv')"><s:text name='rep.export.csv.lnk.lbl'/></a>
							</span>
							</div>
						</div>
						
						
					</div>
					

<script type="text/javascript">
/* $(".att-display-fields").click(function(){
	if($("#rsvpAttendeeDisFldsData").is(":visible")){
		$(this).find("span.drop-image").removeClass("down").addClass("original");
    	$(this).find("#attendeeDisFlds").removeClass("highlighted-text");
    	$("#rsvpAttendeeDisFldsData").slideUp();
	}else{
    	$(this).find("span.drop-image").removeClass("original").addClass("down");
    	$(this).find("#attendeeDisFlds").addClass("highlighted-text");
    	$("#rsvpAttendeeDisFldsData").slideDown();
	}
}); */

var defAttDispFlds = ['Transaction ID', 'First Name', 'Last Name', 'Transaction Date'];

/* $("#sel_date_range").click(function(){
	if($("#datesel_id").is(":checked")){
		$("#datesel_id").val("2");
	}else{
		if($("#datepicker").is(":visible")){
			$("#daterangespin").removeClass("down").addClass("original");
	    	$(this).find("#datesel_lbl").removeClass("highlighted-text");
	    	$("#datepicker").slideUp();
	    	$("#datesel_id").val("1");
		}else{
	    	$("#daterangespin").removeClass("original").addClass("down");
	    	$(this).find("#datesel_lbl").addClass("highlighted-text");
	    	$("#datepicker").slideDown();
	    	$("#datesel_id").val("2");
		}
	}
}); */

$("#daterangespin").click(function(){
	if($("#datepicker").is(":visible")){
		$("#daterangespin").removeClass("down").addClass("original");
    	$("#datesel_lbl").removeClass("highlighted-text");
    	$("#datepicker").slideUp();
	}else{
    	$("#daterangespin").removeClass("original").addClass("down");
    	$("#datesel_lbl").addClass("highlighted-text");
    	$("#datepicker").slideDown();
    	$('#datesel_id').iCheck("check");
    	$("#datesel_id").val("2");
	}
});

$('#datesel_id').on('ifChecked',function(){
	$("#datesel_id").val("2");
    //$("#daterangespin").addClass('down').removeClass('original');
    $("#datepicker").slideDown();
});

$('#datesel_id').on('ifUnchecked',function(){
	$("#datesel_id").val("1");
    //$("#daterangespin").removeClass('down').addClass('original');
    $("#datepicker").slideUp();
    //$("#datesel_lbl").removeClass("highlighted-text");
    $("#attfromdt").val('${attStartDate}');
    $("#atttodt").val('${attEndDate}');
});

$('#displayflds_id').on('ifChecked',function(){
	checkDefaultAttFields();
    $("#rsvpAttendeeDisFldsData").slideDown();
    
});

$('#displayflds_id').on('ifUnchecked',function(){
	checkDefaultAttFields();
    $("#rsvpAttendeeDisFldsData").slideUp();
});

$('#rsvpresponsesubmitbtn').click(function(){
	$("#disfldsspin").removeClass("down").addClass("original");
	$("#attendeeDisFlds").removeClass("highlighted-text");
	if($("#search_list").is(":visible")){
		$("#search_list").hide();
		$(".dataTables_filter").removeClass("open").addClass("closeClass");
	}
	$("#rsvp_res_filters").hide();
	/* if ($("#rsvpAttendeeDisFldsData").is(':visible')) {
		$('.rsvpattnddisplayflds').slideToggle(200);
		//$("#attendeeDisFldsData").hide();
	} */
	
	if(!$("#displayflds_id").is(":checked")){
		checkDefaultAttFields();
	}
	
	if($("#rsvpExportDisplay").is(':visible')){$("#rsvpExportDisplay").hide();}
	if ($("#rsvpdaterangelbl").hasClass("active")) {
		$("#rsvpAttDateDisplay").show();
		$('#rsvpAttFromDateDisplay').html($('#rsvpattfromdt').val());
		$('#rsvpAttToDateDisplay').html($('#rsvpatttodt').val());
	}else{
		$('#rsvpAttFromDateDisplay').html('');
		$('#rsvpAttToDateDisplay').html('');
		$("#rsvpAttDateDisplay").hide();
	}
	
});

function checkDefaultAttFields(){
	$('#rsvpAttendeeDisFldsData input[name=resFields]').each(function() {
		if(isInArray($(this).val(),defAttDispFlds))
   	 		$(this).iCheck('check');
		else
			$(this).iCheck('uncheck');
   	});
}

function showSureNotSure(val){
	if(val=='Yes'){
	$("#sureNotSureId").show();
	}else{
		$("#sureNotSureId").hide();
	}
}

function getRSVPAttendeeDisFields(){
	
	$(".rsvpattnddisplayflds").slideToggle(800);
	$(".rsvpattnddisplayflds").show();
}

$(".closeRSVPAttndDisFlds").click(function(){
	$(".rsvpattnddisplayflds").slideToggle(800);
	//$(".attnddisplayflds").hide();
});

$(document).ready(function() {
	$('#datesel_id').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$("#displayflds_id").iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
});

</script>

