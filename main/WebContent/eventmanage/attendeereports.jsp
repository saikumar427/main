<%@taglib uri="/struts-tags" prefix="s"%>
			<div class="">
				<label class="datesel" id="sel_date_range" style="cursor: pointer;">
					<input type="checkbox" id="datesel_id" name="reportsData.attdatetype" />
				</label>
				<span id="datesel_lbl" class="checkbox-space"><s:text name='global.select.date.range.lbl'/></span> 	
				<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="daterangespin" style="cursor: pointer;"></span> --%>
			</div>
	   
	   <div class="background-options" id="datepicker" style="display:none;">
	   <div class="form-inline">
	   		<div class="form-group">
	   			<s:set name="attStartDate" value="reportsData.eventStartDate"/>
	   			<div><s:textfield style="width:100px" id="attfromdt" name="reportsData.eventStartDate" data-type="date" cssClass="form-control date" size="12"></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   		<div class="form-group"><label><s:text name='global.to.lbl'/></label></div>
	   		<div class="form-group">
	   			<s:set name="attEndDate" value="reportsData.eventEndDate"/>
	   			<div><s:textfield id="atttodt" style="width:100px" name="reportsData.eventEndDate" data-type="date" cssClass="form-control date" size="12" ></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   </div>
	   </div>
		<div class="">
		  <label class="att-display-fields" style="cursor: pointer;">
		  	<input type="checkbox" id="displayflds_id" name="reportsData.attDispFields"/>
		 </label>
		 <span id="attendeeDisFlds" class="checkbox-space"><s:text name='rep.display.flds.lbl'/></span>
		<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="disfldsspin"></span> --%>
	   </div>

<div class="row attnddisplayflds display-fields-div" id="attendeeDisFldsData">
<div class="background-options xsm-font">
<div class="col-md-6 col-sm-6"> 
<div class="fields-select-all links-div sm-font">
<a href="javascript:;" class="attselect"><s:text name='global.select.all.lbl'/></a>
<a href="javascript:;" class="attclear"><s:text name='global.clear.all.lbl'/></a>
</div>
</div>  
<div style="clear:both;"></div>                                     
 <div>                                    
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="TD"
					<s:if test="%{attFields.contains('TD')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.trans.date.lbl'/></span></div>
                                  				
                                  				  <div class="col-md-6 col-sm-6">
                                        			
                                        		 		<input type="checkbox" name="attFields" id="attendeetid" value="TID"
					<s:if test="%{attFields.contains('TID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.transaction.id.lbl'/></span></div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="attFields" value="FN" 
					<s:if test="%{attFields.contains('FN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.first.name.lbl'/></span></div>
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="attFields" value="LN" 
					<s:if test="%{attFields.contains('LN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.last.name.lbl'/></span></div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="attFields" value="TN" 
					<s:if test="%{attFields.contains('TN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.ticket.name.lbl'/></span></div>
                                         
                                        <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="attFields" value="TP"
					<s:if test="%{attFields.contains('TP')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.ticket.price.lbl'/></span></div>
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="attFields" value="BE" 
					<s:if test="%{attFields.contains('BE')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.buyer.email.lbl'/></span></div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                        		<input type="checkbox" name="attFields" value="Em" 
					<s:if test="%{attFields.contains('Em')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.email.lbl'/></span>
                                  				 </div>
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="Ph" 
					<s:if test="%{attFields.contains('Ph')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.phone.lbl'/></span></div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="ON" 
					<s:if test="%{attFields.contains('ON')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.order.number.lbl'/></span></div>
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="AK" 
					<s:if test="%{attFields.contains('AK')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.attendee.key.lbl'/></span></div>
                                        
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="BN" 
					<s:if test="%{attFields.contains('BN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.buyer.name.lbl'/></span></div>
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="BP"
					<s:if test="%{attFields.contains('BP')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.buyer.phone.lbl'/></span></div>
                                        
                    <s:if test="%{isrecurring==true}">
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="ED" 
					<s:if test="%{attFields.contains('ED')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.event.date.lbl'/></span></div>
					</s:if>
                    <s:if test="%{configValue == 'YES'}">
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="attFields" value="SC" 
						<s:if test="%{attFields.contains('SC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.seat.code.lbl'/></span></div>
					</s:if>
             </div>
             <div style="clear:both;"></div>
             <div>                           
            <s:set name="count" value="%{customattributes.size()}"/>
			<s:if test="%{#count>0}">
               <div><span class="col-md-6 col-sm-6 sm-font"><s:text name='rep.disflds.custom.questions.lbl'/></span></div>
               <div style="clear:both;"></div>
                <s:iterator value="%{customattributes}" var="attributes" status="stat">
                 	<div class="col-md-6 col-sm-6">
                 	<s:checkbox name="attFields"  fieldValue="%{key}" value="%{attQuestionFields.contains(key)}" id="c_qn_%{key}" onclick="checkSubQn('%{key}');"/>${value}<s:if test="%{attribOptionMap[key].size()>0}">&nbsp;<span><img class="subquestion" id="qn_<s:property value='%{key}'/>" src="../images/closed.gif" width="12px"/></span></s:if>
          			<div style="display:none;padding-left:20px;" id="sub_qn_<s:property value='%{key}' />">
				<table><tr><td>
				<a href="javascript:;" onclick="selectAllSub('sub_qn_<s:property value='%{key}' />');"><s:text name='global.select.all.lbl'/></a>&nbsp;&nbsp;<a href="javascript:;" onclick="selectNoneSub('sub_qn_<s:property value='%{key}'/>');"><s:text name='global.clear.all.lbl'/></a>
				</td></tr></table>		
				<s:iterator value="%{attribOptionMap[key]}" var="attriboption" status="attopt">
					<s:iterator value="%{#attriboption}" var="option" status="optionst">
						<s:property value="%{optionLabelMap[#option]}" /><br/>
						<s:iterator value="%{subQuestionsMap[#option]}" var="subquestion" status="subqnst">
							<s:checkbox name="attFields"  fieldValue="%{key}" value="%{attQuestionFields.contains(key)}"/>${value}<br/>
						</s:iterator>
					</s:iterator>
				</s:iterator>
				</div>
          			
          			</div>
          		</s:iterator>
            </s:if>
            </div>
            <div style="clear:both;"></div>
</div>
</div>  

<div class="form-inline bottom-gap" id="att_filters" style="display:none;">
<hr>
							<div class="form-group" style="margin-bottom:10px !important;">
									<select id="attendee_tx_type" class="form-control"  name="attselindex" onchange="filterDataTable('attreportcontent');">
										<option value="1"><s:text name='rep.filter.active.trans.lbl'/></option>
										<option value="online"><s:text name='rep.filter.purchased.online.lbl'/></option>
										<option value="Manager"><s:text name='rep.filter.manually.added.lbl'/></option>
									</select>
							</div>
							<div class="form-group" style="margin-bottom:10px !important;"><s:text name='rep.filter.in.lbl'/></div>
							<div class="form-group" style="margin-bottom:10px !important;">
								<s:select name="reportsData.ticket" id='ticket' headerKey="1" cssStyle="width:200px"
									list="attndtickets" listKey="key" listValue="value" cssClass="form-control" onchange="filterDataTable('attreportcontent');"/>
							</div>
							
							<div id="exportDisplay"  class="form-group" style="margin-bottom:10px !important;">
							<span class="links-div">
							<a href="javascript:reportexport('excel')"><s:text name='rep.export.excel.lnk.lbl'/></a>
							<a href="javascript:reportexport('csv')"><s:text name='rep.export.csv.lnk.lbl'/></a>
							</span>
							</div>
						</div>
											
<script type="text/javascript">
var defAttDispFlds = ['FN', 'LN', 'TN', 'TID', 'TD'];
$(".att-display-fields").click(function(){
	if($("#attendeeDisFldsData").is(":visible")){
		$(this).find("span.drop-image").removeClass("down").addClass("original");
    	$(this).find("#attendeeDisFlds").removeClass("highlighted-text");
    	$("#attendeeDisFldsData").slideUp();
	}else{
    	$(this).find("span.drop-image").removeClass("original").addClass("down");
    	$(this).find("#attendeeDisFlds").addClass("highlighted-text");
    	$("#attendeeDisFldsData").slideDown();
	}
});

$("#sel_date_range").click(function(){
	if($("#datesel_id").is(":checked")){
		$("#datesel_id").val("2");
	}else{
		if($("#datepicker").is(":visible")){
			$("#daterangespin").removeClass("down").addClass("original");
	    	//$(this).find("#datesel_lbl").removeClass("highlighted-text");
	    	$("#datepicker").slideUp();
	    	$("#datesel_id").val("1");
		}else{
	    	$("#daterangespin").removeClass("original").addClass("down");
	    	//$(this).find("#datesel_lbl").addClass("highlighted-text");
	    	$("#datepicker").slideDown();
	    	$("#datesel_id").val("2");
		}
	}
});

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
    $("#daterangespin").addClass('down').removeClass('original');
    $("#datepicker").slideDown();
    //$("#datesel_lbl").addClass("highlighted-text");
});

$('#datesel_id').on('ifUnchecked',function(){
	$("#datesel_id").val("1");
    $("#daterangespin").removeClass('down').addClass('original');
    $("#datepicker").slideUp();
    $("#datesel_lbl").removeClass("highlighted-text");
    $("#attfromdt").val('${attStartDate}');
    $("#atttodt").val('${attEndDate}');
});

$('#displayflds_id').on('ifChecked',function(){
	$("#displayflds_id").val("2");
	checkDefaultAttFields();
	$("#attendeeDisFldsData").slideDown();
});

$('#displayflds_id').on('ifUnchecked',function(){
	$("#displayflds_id").val("1");
	checkDefaultAttFields();
	$("#attendeeDisFldsData").slideUp();
});

$('#attendeesubmitbtn').click(function(){
	$("#disfldsspin").removeClass("down").addClass("original");
	$("#attendeeDisFlds").removeClass("highlighted-text");
	if($("#search_list").is(":visible")){
		$("#search_list").hide();
		$(".dataTables_filter").removeClass("open").addClass("closeClass");
	}
	
	if($("#att_filters").is(":visible")){
		$("#att_filters").hide();
	}
	
	/* if ($("#attendeeDisFldsData").is(':visible')) {
		$('.attnddisplayflds').slideToggle(200);
	} */
	
	if(!$("#displayflds_id").is(":checked")){
		checkDefaultAttFields();
	}
	
	if ($("#daterangelbl").hasClass("active")) {
		$("#attDateDisplay").show();
		$('#attFromDateDisplay').html(getDateFormat($('#attfromdt').val()));
		$('#attToDateDisplay').html(getDateFormat($('#atttodt').val()));
	}else{
		$('#attFromDateDisplay').html('');
		$('#attToDateDisplay').html('');
		$("#attDateDisplay").hide();
	}
});

$(".closeAttndDisFlds").click(function(){
	$(".attnddisplayflds").slideToggle(800);
});

$(".subquestion").click(function(){
	var qnid=$(this).attr('id');
	$("#sub_"+qnid).slideToggle(function(){
		var sub=document.getElementById(this.id).style.display;
        if(sub=="none")
        	$("#"+qnid).attr("src","../images/closed.gif");
        else
        	$("#"+qnid).attr("src","../images/open.gif");
	});
});

function checkDefaultAttFields(){
	$('#attendeeDisFldsData input[name=attFields]').each(function() {
		if(isInArray($(this).val(),defAttDispFlds))
   	 		$(this).iCheck('check');
		else
			$(this).iCheck('uncheck');
   	});
}

function selectAllSub(divid) {
	  $("#"+divid+" :checkbox").iCheck('check');
}

function selectNoneSub(divid) {
	$("#"+divid+" :checkbox").iCheck('uncheck');
}
$(document).ready(function() {
	$('#datesel_id').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$("#displayflds_id").iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
});
</script>
