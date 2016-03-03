<%@taglib uri="/struts-tags" prefix="s"%>
			
		<div class="">
				<label class="check-datesel" id="check_sel_date_range" style="cursor: pointer;">	
					<input type="checkbox" class="checkDateSel" id="check_datesel_id" name="reportsData.chkinDateType" value="1"/>
				</label>
				<span id="check_datesel_lbl" class="checkbox-space"><s:text name='global.select.date.range.lbl'/></span> 
				<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="checkdaterangespin" style="cursor: pointer;"></span> --%>	
			</div>
		
	   <div class="background-options" id="chkinDatepicker" style="display:none;">
	   <div class="form-inline">
	   		<div class="form-group">
	   			<s:set name="chkinStartDate" value="reportsData.chkInStartDate"/>
	   			<div><s:textfield id="chkinFromDt" name="reportsData.chkInStartDate" data-type="date" cssClass="form-control date date-range-from" size="12"></s:textfield></div>
	  			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   		<div class="form-group"><label><s:text name='global.to.lbl'/></label></div>
	   		<div class="form-group">
	   			<s:set name="chkinEndDate" value="reportsData.chkInEndDate"/>
	   			<div><s:textfield id="chkinToDt" name="reportsData.chkInEndDate" data-type="date" cssClass="form-control date date-range-to" size="12"></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   </div>
	   </div>
	   <!--<button type="submit" class="btn btn-default">View</button>-->
	   <!-- <div class="form-group extramarginreportin" >
	     <input type="button" class="btn btn-primary" id="checkinsubmitbtn" value="View" />
							</div> -->
	   
	   <div class="">
		  <label class="check-display-fields" style="cursor: pointer;">
		  	<input type="checkbox" id="check_displayflds_id" name="reportsData.chkinDispFields"/>
		 </label>
		 <span id="chkinDisFlds" class="checkbox-space"><s:text name='rep.display.flds.lbl'/></span>
		 <%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="checkdispfldsspin"></span> --%>
	   </div>



<div class="row chkindisplayflds display-fields-div" id="checkInDisFldsData">
	<div class="background-options xsm-font">
		<div class="col-md-6 col-sm-6">
			<div class="fields-select-all links-div sm-font">
				<a href="javascript:;" class="checkselect"><s:text name='global.select.all.lbl'/></a>
				<a href="javascript:;" class="checkclear"><s:text name='global.clear.all.lbl'/></a>
			</div>
		</div>
		<div style="clear:both;"></div>
			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="FN"
					<s:if test="%{checkinFields.contains('FN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.first.name.lbl'/></span></div>

			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="LN"
					<s:if test="%{checkinFields.contains('LN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.last.name.lbl'/></span></div>
		
			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="TID" id="checktid"
					<s:if test="%{checkinFields.contains('TID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.transaction.id.lbl'/></span></div>

			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="CIS" id="chkstatus"
					<s:if test="%{checkinFields.contains('CIS')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.checkin.status.lbl'/></span></div>

			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="AK" id="attkey"
					<s:if test="%{checkinFields.contains('AK')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.attendee.key.lbl'/></span></div>
		
			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="Em"
					<s:if test="%{checkinFields.contains('Em')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.email.lbl'/></span>
			</div>

			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="TT"
					<s:if test="%{checkinFields.contains('TT')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.ticket.type.lbl'/></span></div>
			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="CIT"
					<s:if test="%{checkinFields.contains('CIT')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.checkin.time.lbl'/></span></div>

			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="ON"
					<s:if test="%{checkinFields.contains('ON')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.order.number.lbl'/></span></div>
			<div class="col-md-6 col-sm-6">
				<input type="checkbox" name="checkinFields" value="SID"
					<s:if test="%{checkinFields.contains('SID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.scan.id.lbl'/></span></div>
		
		<s:if test="%{configValue == 'YES'}">
			<div class="col-md-6 col-sm-6">
					<input type="checkbox" name="checkinFields" value="SC"
						<s:if test="%{checkinFields.contains('SC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.seat.code.lbl'/></span></div>
		</s:if>
		<div style="clear:both;"></div>
	</div>
</div>

<div class="form-inline bottom-gap" id="check_filters" style="display:none;">
<hr>
		<div class="form-group">
			<select id="checkin_tx_type" class="form-control" onchange="filterDataTable('checkinreportcontent');"
			name="checkinreportsindex">
			<option value='1'><s:text name='rep.filter.all.attendees.lbl'/></option>
			<option value='Yes'><s:text name='rep.filter.checkedin.lbl'/></option>
			<option value='No'><s:text name='rep.filter.not.checkedin.lbl'/></option>
		</select>
		</div>
		
		<div id="chkinExportDisplay"  class="form-group">
			<span class="links-div"> <a href="javascript:reportexport('excel')"><s:text name='rep.export.excel.lnk.lbl'/></a>
			<a href="javascript:reportexport('csv')"><s:text name='rep.export.csv.lnk.lbl'/></a>
			</span>
		</div>
	</div>

<script type="text/javascript">
var defCheckDispFlds = ['FN', 'LN', 'TID', 'CIS','AK'];
$("#check_sel_date_range").click(function(){
if($("#check_datesel_id").is(":checked")){
	$("#check_datesel_id").val("2");
	}else{
		if($("#chkinDatepicker").is(":visible")){
			$("#checkdaterangespin").removeClass("down").addClass("original");
	    	//$(this).find("#check_datesel_lbl").removeClass("highlighted-text");
	    	$("#chkinDatepicker").slideUp();
	    	$("#check_datesel_id").val("1");
		}else{
			$("#check_datesel_id").val("2");
	    	$("#checkdaterangespin").removeClass("original").addClass("down");
	    	//$(this).find("#check_datesel_lbl").addClass("highlighted-text");
	    	$("#chkinDatepicker").slideDown();
		}
	}
});

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
    $("#checkdaterangespin").addClass('down').removeClass('original');
    $("#chkinDatepicker").slideDown();
    //$("#check_datesel_lbl").addClass("highlighted-text");
});

$('#check_datesel_id').on('ifUnchecked',function(){
	$("#check_datesel_id").val("1");
    $("#checkdaterangespin").removeClass('down').addClass('original');
    $("#chkinDatepicker").slideUp();
    $("#chkinFromDt").val('${chkinStartDate}');
    $("#chkinToDt").val('${chkinEndDate}');
    //$("#check_datesel_lbl").removeClass("highlighted-text");
});

$(".check-display-fields").click(function(){
	if($("#checkInDisFldsData").is(":visible")){
		$(this).find("span.drop-image").removeClass("down").addClass("original");
    	$(this).find("#chkinDisFlds").removeClass("highlighted-text");
    	$("#checkInDisFldsData").slideUp();
	}else{
    	$(this).find("span.drop-image").removeClass("original").addClass("down");
    	$(this).find("#chkinDisFlds").addClass("highlighted-text");
    	$("#checkInDisFldsData").slideDown();
	}
});

$('#check_displayflds_id').on('ifChecked',function(){
	$("#check_displayflds_id").val("2");
	$("#checkInDisFldsData").slideDown();
});

$('#check_displayflds_id').on('ifUnchecked',function(){
	$("#check_displayflds_id").val("1");
	$('#checkInDisFldsData input[name=checkinFields]').each(function() {
		if(isInArray($(this).val(),defCheckDispFlds))
   	 		$(this).iCheck('check');
		else
			$(this).iCheck('uncheck');
   	});
	$("#checkInDisFldsData").slideUp();
});

$('#checkinsubmitbtn').click(function(){
	$("#checkdispfldsspin").removeClass("down").addClass("original");
	$("#chkinDisFlds").removeClass("highlighted-text");
	if($("#search_list").is(":visible")){
		$("#search_list").hide();
		$(".dataTables_filter").removeClass("open").addClass("closeClass");
	}
	if($("#check_filters").is(":visible")){
		$("#check_filters").hide();
	}
	/* if ($("#checkInDisFldsData").is(':visible')) {
		$('.chkindisplayflds').slideToggle(200);
	} */
	if ($("#chkinDateRangeLbl").hasClass("active")) {
		$('#chkinFromDateDisplay').html($('#chkinFromDt').val());
		$('#chkinToDateDisplay').html($('#chkinToDt').val());
	}else{
		$('#chkinFromDateDisplay').html('');
		$('#chkinToDateDisplay').html('');
	}
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
