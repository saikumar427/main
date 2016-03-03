<%@taglib uri="/struts-tags" prefix="s"%>

		<div class="">
				<label class="sales-datesel" id="sales_sel_date_range" style="cursor: pointer;">	
					<input type="checkbox" class="salesDateSel" id="sales_datesel_id" name="reportsData.salesDateType"/>
				</label>
				<span id="sales_datesel_lbl" class="checkbox-space"><s:text name='global.select.date.range.lbl'/></span> 
				<%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="salesdaterangespin" style="cursor: pointer;"></span> --%>	
			</div>
		

	   <div class="background-options" id="salesDatepicker" style="display:none;">
	   <div class="form-inline">
	   		<div class="form-group">
	   			<s:set name="salesStartDate" value="reportsData.eventTranStartDate"/>
	   			<div><s:textfield id="salesFromDt" name="reportsData.eventTranStartDate"   data-type="date" cssClass="form-control date date-range-from" size="12"></s:textfield></div>
	  			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   		<div class="form-group"><label><s:text name='global.to.lbl'/></label></div>
	   		<div class="form-group">
	   			<s:set name="salesEndDate" value="reportsData.eventTranEndDate"/>
	   			<div><s:textfield id="salesToDt" name="reportsData.eventTranEndDate" data-type="date" cssClass="form-control date date-range-to" size="12"></s:textfield></div>
	   			<div class="sub-text">&nbsp;<s:text name='global.mm.dd.yyyy.lbl'/></div>
	   		</div>
	   </div>
	   </div>
	   
	   <div class="">
		  <label class="sales-display-fields" style="cursor: pointer;">
		  	<input type="checkbox" id="sales_displayflds_id" name="reportsData.salesDispFields"/>
		 </label>
		 <span id="salesDisFlds" class="checkbox-space"><s:text name='rep.display.flds.lbl'/></span>
		 <%-- <span class="glyphicon glyphicon-menu-right drop-image original" id="salsesdspfldsspin"></span> --%>
	   </div>

		
<div class="row salesdisplayflds display-fields-div" id="salesDisFldsData">
<div class="background-options xsm-font">
<div class="col-md-6 col-sm-6">
<div class="fields-select-all links-div sm-font">
<a href="javascript:;" class="salesselect"><s:text name='global.select.all.lbl'/></a>
<a href="javascript:;" class="salesclear"><s:text name='global.clear.all.lbl'/></a>
</div>
</div>
<div style="clear:both;"></div>   
<div>                                       
                                         		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="TD" <s:if test="%{Fields.contains('TD')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.trans.date.lbl'/></span>
                                        			</div>
                                  				
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="Fields" value="TID" id="transtid"<s:if test="%{Fields.contains('TID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.transaction.id.lbl'/></span>
                                        		 		</div>
                                        
                                         	<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="Fields" value="FN" <s:if test="%{Fields.contains('FN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.first.name.lbl'/></span>
                                            		</div>
                                  			<div class="col-md-6 col-sm-6">
                                        		 <input type="checkbox" name="Fields" value="LN" <s:if test="%{Fields.contains('LN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.last.name.lbl'/></span>
                                        		 </div>
                                         
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="Fields" value="TN" <s:if test="%{Fields.contains('TN')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.ticket.name.lbl'/></span>
                                            		</div>
                                  				  <div class="col-md-6 col-sm-6">
                                        		 		<input type="checkbox" name="Fields" value="TP" <s:if test="%{Fields.contains('TP')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.ticket.price.lbl'/></span>
                                        		 		</div>
                                         
                                         		<div class="col-md-6 col-sm-6">
                                            		<input type="checkbox" name="Fields" value="SF" <s:if test="%{Fields.contains('SF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.service.fee.lbl'/></span>
                                  				 </div>
                                  				 <div class="col-md-6 col-sm-6">
                                        		<input type="checkbox" name="Fields" value="Di" <s:if test="%{Fields.contains('Di')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.discount.lbl'/></span>
                                  				 </div>
                                         
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="TC" <s:if test="%{Fields.contains('TC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.tickets.count.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="CCPF" <s:if test="%{Fields.contains('CCPF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.cc.pro.fee.lbl'/></span>
                                  				 </div>
                                        
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="TTC" <s:if test="%{Fields.contains('TTC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.total.tkt.price.lbl'/></span>
                                        		</div>
                                        			<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="PT" <s:if test="%{Fields.contains('PT')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.payment.type.lbl'/></span>
                                  				 </div>
                                         
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="ESF" <s:if test="%{Fields.contains('ESF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.ebee.service.fee.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="ECCF" <s:if test="%{Fields.contains('ECCF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.ebee.cc.pro.fee.lbl'/></span>
                                        		</div>
                                         
                                       <s:if test="%{otherCCPFee=='Yes'}" >
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="OCCF" <s:if test="%{Fields.contains('OCCF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.other.cc.pro.fee.lbl'/></span>
                                        		</div>
                                         
										</s:if>
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="NTSC" <s:if test="%{Fields.contains('NTSC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.nts.comm.lbl'/></span>
                                        		</div>
                                          
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="TNet" <s:if test="%{Fields.contains('TNet')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.net.tkt.price.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="CSF" <s:if test="%{Fields.contains('CSF')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.collected.service.fee.lbl'/></span>
                                        		</div>
                                          
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="RA" <s:if test="%{Fields.contains('RA')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.refunded.amt.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="Bal" <s:if test="%{Fields.contains('Bal')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.balance.lbl'/></span>
                                        		</div>
                                         
                                        
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="ON" <s:if test="%{Fields.contains('ON')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.order.number.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="St"  <s:if test="%{Fields.contains('St')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.status.lbl'/></span>
                                        		</div>
                                          
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="Em" <s:if test="%{Fields.contains('Em')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.email.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="Ph" <s:if test="%{Fields.contains('Ph')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.phone.lbl'/></span>
                                        		</div>
                                          
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="TU" <s:if test="%{Fields.contains('TU')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.tracking.url.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="So" <s:if test="%{Fields.contains('So')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.source.lbl'/></span>
                                        		</div>
                                          
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="No" <s:if test="%{Fields.contains('No')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.notes.lbl'/></span>
                                        		</div>
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="DC" <s:if test="%{Fields.contains('DC')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.discount.code.lbl'/></span>
                                        		</div>
                                  				  <div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="EPID" <s:if test="%{Fields.contains('EPID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.ext.pay.id.lbl'/></span>
                                        		</div>
                                        
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="PID" <s:if test="%{Fields.contains('PID')}">checked</s:if>></input><span class="checkbox-space"><s:text name='rep.disflds.nts.partner.id.lbl'/></span>
                                        		</div>
                                          
                                        <s:if test="%{isrecurring==true}">
                                        		<div class="col-md-6 col-sm-6">
                                        			<input type="checkbox" name="Fields" value="ED" <s:if test="%{Fields.contains('ED')}">checked</s:if>></input><span class="checkbox-space"><s:text name='global.event.date.lbl'/></span>
                                        		</div>
                                        </s:if>
                                       </div>
                                       <div style="clear:both;"></div>
                                       <div>
                                        	<s:set name="count1" value="%{buyerAttribList.size()}"/>
								<s:if test="%{#count1>0}">
                                        <div><span class="col-md-6 col-sm-6 sm-font"><s:text name='rep.disflds.custom.questions.lbl'/></span></div>
                                        <div style="clear:both;"></div>
                                       <s:iterator value="%{buyerAttribList}" var="buyer" status="stat">
                                         		<div class="col-md-6 col-sm-6">
						<s:checkbox name="Fields"  fieldValue="%{key}" value="%{buyerQuestionFields.contains(key)}"  id="bc_bqn_%{key}" onclick="checkBuyerSubQn('%{key}');"/>${value}<s:if test="%{buyerAttribOptionMap[key].size()>0}">&nbsp;<span><img class="buyersubquestion" id="bqn_<s:property value='%{key}'/>" src="../images/closed.gif" width="12px"/></span></s:if>
<div style="display:none;padding-left:20px;" id="bsub_bqn_<s:property value='%{key}' />">
				<table><tr><td>
				<a href="javascript:;" onclick="selectAllBuyerSub('bsub_bqn_<s:property value='%{key}' />');"><s:text name='global.select.all.lbl'/></a>&nbsp;&nbsp;<a href="javascript:;" onclick="selectNoneBuyerSub('bsub_bqn_<s:property value='%{key}'/>');"><s:text name='global.clear.all.lbl'/></a>
				</td></tr></table>		
				<s:iterator value="%{buyerAttribOptionMap[key]}" var="attriboption" status="attopt">
					<s:iterator value="%{#attriboption}" var="option" status="optionst">
						<s:property value="%{buyerOptionLabelMap[#option]}" /><br/>
						<s:iterator value="%{buyerSubQuestionsMap[#option]}" var="subquestion" status="subqnst">
							<s:checkbox name="Fields"  fieldValue="%{key}" value="%{buyerQuestionFields.contains(key)}"/>${value}<br/>
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

<div class="form-inline bottom-gap" id="salse_filters" style="display:none;">
<hr>
					<div class="form-group" style="margin-bottom:10px !important;">
						<select id="sales_tx_type" class="form-control"  name="paymentstaus" onchange="filterDataTable('tranreportcontent');">
							<option value="Completed"><s:text name='rep.filter.active.trans.lbl'/></option>
							<s:if test="%{pendingApproval > 0 || recPendingAppSize > 0}">
							    <option id='selpapid' value='Pending Approval' <s:if test="%{#paymentstaus == 'Need Approval'}"> selected</s:if>><s:text name='rep.filter.pending.approval.lbl'/></option>
							</s:if>
							<option value='Deleted'><s:text name='rep.filter.deleted.trans.lbl'/></option>
							<option value='Refunded'><s:text name='rep.filter.refunded.trans.lbl'/></option>
							<option value='Chargeback'><s:text name='rep.filter.chargeback.trans.lbl'/></option>
							<option value="1"><s:text name='rep.filter.all.trans.lbl'/></option>
						</select>
					</div>
					<div class="form-group" style="margin-bottom:10px !important;"><s:text name='rep.filter.of.lbl'/></div>
					<div class="form-group" style="margin-bottom:10px !important;">
						<s:select name="reportsData.ticketid" id='transticket' cssClass="form-control" headerKey="1" onchange="filterDataTable('tranreportcontent');"
    								headerValue="%{getText('global.all.tickets.lbl')}" list="tickets" listKey="key" listValue="value" cssStyle="width:200px"/>
					</div>
					<div class="form-group" style="margin-bottom:10px !important;"><s:text name='rep.filter.with.lbl'/></div>
					<div class="form-group" style="margin-bottom:10px !important;">
						<s:select cssClass="form-control" name="reportsData.paymenttypeindex" id='pti' headerKey="7" onchange="filterDataTable('tranreportcontent');"
								headerValue="%{getText('rep.filter.any.payment.lbl')}" list="paymentTypes"  listKey="key" listValue="value"/>
					</div><%-- list="#{'Eventbee':'Eventbee CC','Authorize.Net':'Authorize.Net','Braintree':'Braintree','Stripe':'Stripe','PayPal':'PayPal','ebeecredits':'Eventbee Credits','No Payment':'No Payment','Other':'Other Payment'}" --%>
					<div class="form-group" style="margin-bottom:10px !important;"><s:text name='rep.filter.from.lbl'/></div>
					<div id="repsearchsource" class="form-group" style="margin-bottom:10px !important;">
							<s:select  name="reportsData.source" id='transsource' headerKey="1" onchange="filterDataTable('tranreportcontent');"
							headerValue="%{getText('rep.filter.all.urls.lbl')}" list="bookingSource" listKey="key" listValue="value"  cssClass="form-control" style="border-top-left-radius: 4px;border-bottom-left-radius: 4px;"/>
						</div>
				
					<div class="form-group" style="margin-bottom:10px !important;">
						<input type="checkbox" class="srctype" name="reportsData.online" id="onlineid" checked="checked" value="online"/>&nbsp;<s:text name='rep.filter.online.lbl'/>&nbsp;
						<input type="checkbox" class="srctype" name="reportsData.manual" id="manualid" checked="checked" value="Manager"/>&nbsp;<s:text name='rep.filter.manual.lbl'/>&nbsp;
					</div>
					<div id="salesExportDisplay" class="form-group" style="margin-bottom:12px !important;">
							<span class="links-div">
							<a href="javascript:reportexport('excel')"><s:text name='rep.export.excel.lnk.lbl'/></a>
							<a href="javascript:reportexport('csv')"><s:text name='rep.export.csv.lnk.lbl'/></a>
							</span>
					</div>
				</div>
<script type="text/javascript">
var defSalesDispFlds = ['FN', 'LN', 'TID', 'TD','TNet','Bal'];
$("#sales_sel_date_range").click(function(){
if($("#sales_datesel_id").is(":checked")){
	$("#sales_datesel_id").val("2");
	}else{
		if($("#salesDatepicker").is(":visible")){
			$("#salesdaterangespin").removeClass("down").addClass("original");
	    	//$(this).find("#sales_datesel_lbl").removeClass("highlighted-text");
	    	$("#salesDatepicker").slideUp();
	    	$("#sales_datesel_id").val("1");
		}else{
	    	$("#salesdaterangespin").removeClass("original").addClass("down");
	    	//$(this).find("#sales_datesel_lbl").addClass("highlighted-text");
	    	$("#salesDatepicker").slideDown();
	    	$("#sales_datesel_id").val("2");
		}
	}
});

$("#salesdaterangespin").click(function(){
	if($("#salesDatepicker").is(":visible")){
		$("#salesdaterangespin").removeClass("down").addClass("original");
    	$("#sales_datesel_lbl").removeClass("highlighted-text");
    	$("#salesDatepicker").slideUp();
	}else{
    	$("#salesdaterangespin").removeClass("original").addClass("down");
    	$("#sales_datesel_lbl").addClass("highlighted-text");
    	$("#salesDatepicker").slideDown();
    	$('#sales_datesel_id').iCheck("check");
    	$("#sales_datesel_id").val("2");
	}
});

$('#sales_datesel_id').on('ifChecked',function(){
	$("#sales_datesel_id").val("2");
    $("#salesdaterangespin").addClass('down').removeClass('original');
    $("#salesDatepicker").slideDown();
    //$("#sales_datesel_lbl").addClass("highlighted-text");
    
});

$('#sales_datesel_id').on('ifUnchecked',function(){
	$("#sales_datesel_id").val("1");
    $("#salesdaterangespin").removeClass('down').addClass('original');
    $("#salesDatepicker").slideUp();
    $("#salesFromDt").val('${salesStartDate}');
    $("#salesToDt").val('${salesEndDate}');
    
    //$("#sales_datesel_lbl").removeClass("highlighted-text");
    
});

$('#transactionsubmitbtn').click(function(){
	$("#salsesdspfldsspin").removeClass("down").addClass("original");
	$("#salesDisFlds").removeClass("highlighted-text");
	if($("#search_list").is(":visible")){
		$("#search_list").hide();
		$(".dataTables_filter").removeClass("open").addClass("closeClass");
	}
	
	if($("#salse_filters").is(":visible")){
		$("#salse_filters").hide();
	}
	
	/* if ($("#salesDisFldsData").is(':visible')) {
		$('.salesdisplayflds').slideToggle(200);
	} */
	if(!$("#sales_displayflds_id").is(":checked")){
		checkDefaultSalesFields();
	}
	
	if($("#salesDateRangeLbl").hasClass("active")){
		$("#salesDateDisplay").show();
		$('#salesFromDateDisplay').html($('#salesFromDt').val());
		$('#salesToDateDisplay').html($('#salesToDt').val());
	}else{
		$('#salesFromDateDisplay').html('');
		$('#salesToDateDisplay').html('');
		$("#salesDateDisplay").hide();
	}
	
});


$(".sales-display-fields").click(function(){
	if($("#salesDisFldsData").is(":visible")){
		$(this).find("span.drop-image").removeClass("down").addClass("original");
    	$(this).find("#salesDisFlds").removeClass("highlighted-text");
    	$("#salesDisFldsData").slideUp();
	}else{
    	$(this).find("span.drop-image").removeClass("original").addClass("down");
    	$(this).find("#salesDisFlds").addClass("highlighted-text");
    	$("#salesDisFldsData").slideDown();
	}
});

$('#sales_displayflds_id').on('ifChecked',function(){
	$("#sales_displayflds_id").val("2");
	checkDefaultSalesFields();
	$("#salesDisFldsData").slideDown();    
});

$('#sales_displayflds_id').on('ifUnchecked',function(){
	$("#sales_displayflds_id").val("1");
	checkDefaultSalesFields();
	$("#salesDisFldsData").slideUp();
});

$('.srctype').on('ifChecked', function(event){
	if(salesdata!='')
		filterDataTable('tranreportcontent');
});

$('.srctype').on('ifUnchecked', function(event){  
	if(salesdata!='')
		filterDataTable('tranreportcontent');
});

$(".buyersubquestion").click(function(){
	var qnid=$(this).attr('id');
	$("#bsub_"+qnid).slideToggle(function(){
		var sub=document.getElementById(this.id).style.display;
        if(sub=="none")
        	$("#"+qnid).attr("src","../images/closed.gif");
        else
        	$("#"+qnid).attr("src","../images/open.gif");
	});
});

function checkDefaultSalesFields(){
	
	$('#salesDisFldsData input[name=Fields]').each(function() {
		if(isInArray($(this).val(),defSalesDispFlds))
   	 		$(this).iCheck('check');
		else
			$(this).iCheck('uncheck');
   	});
}

function selectAllBuyerSub(divid) {
	  $("#"+divid+" :checkbox").iCheck('check');
}

function selectNoneBuyerSub(divid) {
	$("#"+divid+" :checkbox").iCheck('uncheck');
}

$(document).ready(function() {
	$('#sales_datesel_id').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
	
	$("#sales_displayflds_id").iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
});

</script>