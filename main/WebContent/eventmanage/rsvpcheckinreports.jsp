<%@taglib uri="/struts-tags" prefix="s"%>

		<div class="">
				<label class="check-datesel" id="check_sel_date_range" style="cursor: pointer;">	
					<input type="checkbox" class="checkDateSel" id="check_datesel_id" name="rsvpReportsData.checkDateRadio" value="1"/>
				</label>
				<span id="check_datesel_lbl" class="checkbox-space"><s:text name='global.select.date.range.lbl'/></span>
				<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="checkdaterangespin" style="cursor: pointer;"></span> --%>	
			</div>
			
	<div class="background-options" id="chkinDatepicker" style="display:none;">
	   <div class="form-inline">
	   		<div class="form-group">
	   			<s:set name="chkinStartDate" value="rsvpReportsData.checkInStartDate"/>
	   			<div><s:textfield id="chkinFromDt" name="rsvpReportsData.checkInStartDate" data-type="date" cssClass="form-control date date-range-from" size="12"></s:textfield></div>
	  			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   		<div class="form-group"><label><s:text name='global.to.lbl'/></label></div>
	   		<div class="form-group">
	   			<s:set name="chkinEndDate" value="rsvpReportsData.checkInEndDate"/>
	   			<div><s:textfield id="chkinToDt" name="rsvpReportsData.checkInEndDate" data-type="date" cssClass="form-control date date-range-to" size="12"></s:textfield></div>
				<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   </div>
	   </div>
	   
	   <div class="">
		  <label class="check-display-fields" style="cursor: pointer;">
		  	<input type="checkbox" id="check_displayflds_id" name="displayflds"/>
		 </label>
		 <span id="chkinDisFlds" class="checkbox-space"><s:text name='rep.display.flds.lbl'/></span>
			<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="checkdispfldsspin"></span> --%>
	   </div>

<div class="row rsvpcheckindisplayflds display-fields-div" id="rsvpCheckInDisFldsData">
<div class="background-options xsm-font">
<div class=""> 
<div class="fields-select-all links-div sm-font">
<a href="javascript:;" class="rsvpcheckselect"><s:text name='global.select.all.lbl'/></a>
<a href="javascript:;" class="rsvpcheckclear"><s:text name='global.clear.all.lbl'/></a></div>
</div>
<div style="clear:both;"></div>  
<div>                                       
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" id="rsvpchktid" name="checkinFields" value="Transaction ID" <s:if test="%{checkinFields.contains('Transaction ID')}">checked</s:if>>&nbsp;<s:text name='global.transaction.id.lbl'/>
                                  				 	</div>
                                  				
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="checkinFields" value="Attendee Key" <s:if test="%{checkinFields.contains('Attendee Key')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.attendee.key.lbl'/>
                                        		</div>
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="checkinFields" value="First Name" <s:if test="%{checkinFields.contains('First Name')}">checked</s:if>></input>&nbsp;<s:text name='global.first.name.lbl'/>
                                  				 	</div>
                                  				
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="checkinFields" value="Last Name" <s:if test="%{checkinFields.contains('Last Name')}">checked</s:if>></input>&nbsp;<s:text name='global.last.name.lbl'/>
                                        		</div>
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="checkinFields" value="Email" <s:if test="%{checkinFields.contains('Email')}">checked</s:if> ></input>&nbsp;<s:text name='global.email.lbl'/>
                                  				 	</div>
                                  				 	<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" id="rsvpchkinid" name="checkinFields" value="Checked In" <s:if test="%{checkinFields.contains('Checked In')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.checkin.status.lbl'/>
                                  				 	</div>
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="checkinFields" value="Check In Time" <s:if test="%{checkinFields.contains('Check In Time')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.checkin.time.lbl'/>
                                  				 	</div>
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="checkinFields" value="Scan ID" <s:if test="%{checkinFields.contains('Scan ID')}">checked</s:if>></input>&nbsp;<s:text name='rep.disflds.scan.id.lbl'/>
                                        		</div>
                                        </div>  
                                        <div style="clear:both;"></div>                               
</div>
</div>                                      


					<div class="form-inline bottom-gap" id="rsvp_chek_filters" style="display:none;">
					<hr>
						<div class="form-group">
									<select class="form-control"  name="rsvpReportsData.checkinreportsindex" id="checkinstatusid" onchange="filterRSVPDataTable('rsvpcheckinreport');">
										<option value="1"><s:text name='rep.filter.all.attendees.lbl'/></option>
										<option value="Yes"><s:text name='rep.filter.checkedin.lbl'/></option>
										<option value="No"><s:text name='rep.filter.not.checkedin.lbl'/></option>
									</select>
						</div>
						
						<div class="form-group">	
							<div  id="rsvpChkExportDisplay">
							<span class="links-div">
							<a href="javascript:reportexport('excel')"><s:text name='rep.export.excel.lnk.lbl'/></a>
							<a href="javascript:reportexport('csv')"><s:text name='rep.export.csv.lnk.lbl'/></a>
							</span>
							</div>
						</div>
						
						
					</div>
					

<script type="text/javascript">
var defChkinDispFlds = ['Transaction ID', 'First Name', 'Last Name', 'Checked In'];
/* $("#check_sel_date_range").click(function(){
	if($("#check_datesel_id").is(":checked")){
		$("#check_datesel_id").val("2");
		}else{
			if($("#chkinDatepicker").is(":visible")){
				$("#checkdaterangespin").removeClass("down").addClass("original");
		    	$(this).find("#check_datesel_lbl").removeClass("highlighted-text");
		    	$("#chkinDatepicker").slideUp();
		    	$("#check_datesel_id").val("1");
			}else{
				$("#check_datesel_id").val("2");
		    	$("#checkdaterangespin").removeClass("original").addClass("down");
		    	$(this).find("#check_datesel_lbl").addClass("highlighted-text");
		    	$("#chkinDatepicker").slideDown();
			}
		}
	}); */

	$("#checkdaterangespin").click(function(){
		if($("#chkinDatepicker").is(":visible")){
			$("#checkdaterangespin").removeClass("down").addClass("original");
	    	$("#check_datesel_lbl").removeClass("highlighted-text");
	    	$("#chkinDatepicker").slideUp();
		}else{
	    	$("#checkdaterangespin").removeClass("original").addClass("down");
	    	$("#check_datesel_lbl").addClass("highlighted-text");
	    	$("#chkinDatepicker").slideDown();
	    	$('#check_datesel_id').iCheck("check");
	    	$("#check_datesel_id").val("2");
		}
	});

	$('#check_datesel_id').on('ifChecked',function(){
		$("#check_datesel_id").val("2");
	   /*  $("#checkdaterangespin").addClass('down').removeClass('original');
	    $("#check_datesel_lbl").addClass("highlighted-text"); */
	    $("#chkinDatepicker").slideDown();
	});

	$('#check_datesel_id').on('ifUnchecked',function(){
		$("#check_datesel_id").val("1");
	    /* $("#checkdaterangespin").removeClass('down').addClass('original');
	    $("#check_datesel_lbl").removeClass("highlighted-text"); */
	    $("#chkinDatepicker").slideUp();
	    $("#chkinFromDt").val('${chkinStartDate}');
	    $("#chkinToDt").val('${chkinEndDate}');
	});
	
	$('#check_displayflds_id').on('ifChecked',function(){
		checkDefaultChkinFields();
	    $("#rsvpCheckInDisFldsData").slideDown();
	    
	});

	$('#check_displayflds_id').on('ifUnchecked',function(){
		checkDefaultChkinFields();
	    $("#rsvpCheckInDisFldsData").slideUp();
	});
	
	/* $(".check-display-fields").click(function(){
		if($("#rsvpCheckInDisFldsData").is(":visible")){
			$(this).find("span.drop-image").removeClass("down").addClass("original");
	    	$(this).find("#chkinDisFlds").removeClass("highlighted-text");
	    	$("#rsvpCheckInDisFldsData").slideUp();
		}else{
	    	$(this).find("span.drop-image").removeClass("original").addClass("down");
	    	$(this).find("#chkinDisFlds").addClass("highlighted-text");
	    	$("#rsvpCheckInDisFldsData").slideDown();
		}
	}); */


$('#rsvpcheckinsubmitbtn').click(function(){
	$("#checkdispfldsspin").removeClass("down").addClass("original");
	$("#chkinDisFlds").removeClass("highlighted-text");
	if($("#search_list").is(":visible")){
		$("#search_list").hide();
		$(".dataTables_filter").removeClass("open").addClass("closeClass");
	}
	$("#rsvp_chek_filters").hide();
	/* if ($("#rsvpCheckInDisFldsData").is(':visible')) {
		$('.rsvpcheckindisplayflds').slideToggle(200);
		//$("#attendeeDisFldsData").hide();
	} */
	
	if(!$("#check_displayflds_id").is(":checked")){
		checkDefaultChkinFields();
	}
	
	if($("#rsvpChkExportDisplay").is(':visible')){$("#rsvpChkExportDisplay").hide();}
	if ($("#rsvpchkdaterangelbl").hasClass("active")) {
		$("#rsvpChkDateDisplay").show();
		$('#rsvpChkFromDateDisplay').html($('#rsvpchkfromdt').val());
		$('#rsvpChkToDateDisplay').html($('#rsvpchktodt').val());
	}else{
		$('#rsvpChkFromDateDisplay').html('');
		$('#rsvpChkToDateDisplay').html('');
		$("#rsvpChkDateDisplay").hide();
	}
	
});
	
	function checkDefaultChkinFields(){
		$('#rsvpCheckInDisFldsData input[name=checkinFields]').each(function() {
			if(isInArray($(this).val(),defChkinDispFlds))
	   	 		$(this).iCheck('check');
			else
				$(this).iCheck('uncheck');
	   	});
	}

function getRSVPCheckInDisFields(){
	
	$(".rsvpcheckindisplayflds").slideToggle(800);
	$(".rsvpcheckindisplayflds").show();
}

$(".closeRSVPChkDisFlds").click(function(){
	$(".rsvpcheckindisplayflds").slideToggle(800);
	//$(".attnddisplayflds").hide();
});

$(document).ready(function() {
	$('#check_datesel_id').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$("#check_displayflds_id").iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
});

</script>

