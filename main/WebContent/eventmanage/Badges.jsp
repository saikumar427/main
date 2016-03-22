<%@taglib uri="/struts-tags" prefix="s" %>
<div class="white-box">
<s:form method="post" name="report" action="Badges!attendeeLabels"  id="badgesform" cssClass="form-horizontal">
<s:hidden name="eid"></s:hidden>
<s:hidden name="purpose"></s:hidden>
<s:set value="%{isrecurring}"  id="isrecurring" name="isrecurring"></s:set>
<s:set name="radiot" value="%{radioType}"></s:set>
<input type="hidden" id="isrecurr"  value="<s:property value="isrecurring"/>"/>
<div id="errormsgid"  >
<s:fielderror  cssClass="alert alert-danger"/>
</div>


<s:if test="%{isrecurring==true}">
<div class="row">
<div  class="col-md-2 col-sm-2"><s:text name="bad.event.dates.lbl"></s:text></div>
<div class="col-md-10 col-sm-6">
<div class="col-md-6">
 <s:select name="badgesData.eventDate" id='source' headerKey=""
    		      headerValue="%{getText('bad.sel.recc.date')}" 
    		      list="dates" listKey="key" listValue="value" cssClass="form-control"/>
</div>
</div>
</div>
<hr/>
</s:if>


<div class="row bottom-gap">

<div  class="col-md-2 col-sm-12"><s:text name="bad.attendees.field.header.lbl"></s:text></div>
<div class="col-md-10 col-sm-12">

<div class="col-md-3 col-sm-3"><input type="radio" value="1" name="badgesData.transactionradio" id="allradio" <s:if test='%{#radiot=="1"}'>checked='checked'</s:if>><span class="checkbox-space"><s:text name="bad.all.rb.lbl"></s:text></span></div>
<div class="col-md-3 col-sm-3"><input type="radio" value="2" name="badgesData.transactionradio" id="dateradio" <s:if test='%{#radiot=="2"}'>checked='checked'</s:if>><span class="checkbox-space"><s:text name="bad.in.between.rb.lbl"></s:text></span></div>
<div class="col-md-3 col-sm-3"><input type="radio"  value="3" name="badgesData.transactionradio" id="tidradio" <s:if test='%{#radiot=="3"}'>checked='checked'</s:if>><span class="checkbox-space"><s:text name="bad.transaction.id.rb.lbl"></s:text></span></div>
<s:if test="%{badgesData.ticketsList.size>0 && powertype=='Ticketing'}">

<div class="col-md-3 col-sm-3"><input type="radio"  value="4" name="badgesData.transactionradio" id="tktsradio" <s:if test='%{#radiot=="4"}'>checked='checked'</s:if>><span class="checkbox-space"><s:text name="bad.tkt.type.lbl"></s:text></span></div>
</s:if>
</div>
</div>


<div class="row background-options" id="daterange" style="<s:if test="%{#radiot == 2}">display: block;</s:if><s:else>display: none;</s:else>">

<div class="col-md-1"></div>
<div class="col-md-10" >
<div class="col-md-2">
<s:textfield name="badgesData.startDate"   data-type="date" cssClass="form-control date" size="12" ></s:textfield>
<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
</div>

<div class="col-md-1"> <s:text name="global.to.lbl"/>    </div>
<div class="col-md-2"><s:textfield name="badgesData.endDate"   data-type="date" cssClass="form-control date" size="12" ></s:textfield>
<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
</div>
</div>
</div>


<div class="row background-options"  style="<s:if test="%{#radiot == 3}">display: block;</s:if><s:else>display: none;</s:else>" id="tid_list">

<div class="col-md-1"></div>
<div class="col-md-10">
<div class="col-md-6">
<s:textarea name="badgesData.transactionId" cssClass="form-control" rows="4" cols="60" id="transactionid"></s:textarea>

<span class="sub-text"><s:text name="bad.comma.sep.lbl"></s:text></span>
</div>

</div>
</div>



<div class="row background-options" style="<s:if test="%{#radiot == 4}">display: block;</s:if><s:else>display: none;</s:else>" id="tikt_list">
<div class="col-md-1"></div>
<div class="col-md-10" >
<div class="col-md-6">
<s:iterator value="%{badgesData.ticketsList}" var="tickets" status="stat">
	<s:checkbox id="tlist" cssClass="tkts" name="badgesData.selectedTkts"  fieldValue="%{key}"  value="" /><span class="checkbox-space">${value}</span><br/>
 </s:iterator>
</div>

</div>
</div>


<hr/>



<div class="row bottom-gap">

<div class="col-md-2"><s:text name="bad.select.fields.th.lbl"></s:text></div>
<div class="col-md-10">

<div class="col-md-3">
 <input type="checkbox" checked="checked" class="default" disabled="disabled"><span class="checkbox-space"><s:text name="bad.fname.cb.lbl"></s:text></span></div>
 <div class="col-md-3"><input type="checkbox" checked="checked" class="default" disabled="disabled"><span class="checkbox-space"><s:text name="bad.lname.rb.lbl"></s:text></span></div>
 <s:if test="%{configValue == 'YES'}">
 <div class="col-md-3">
	 <input type="checkbox" class="default" checked="checked" disabled="disabled"><span class="checkbox-space"><s:text name="bad.seat.code.lbl"></s:text></span></div>
 </s:if>

</div></div>

<div class="row">
<div class="col-md-2"></div>
<div class="col-md-10">
<div class="col-md-5">

<s:iterator value="%{attributes}" var="attrib" status="stat">
<div style="height: 5px;"></div>
<s:checkbox name="attributeFields" fieldValue="%{key}" value="%{attributeFields.contains(key)}" id="c_qn_%{key}" onclick="checkSubQn('%{key}');"/><span class="checkbox-space">${value}</span><s:if test="%{attribOptionMap[key].size()>0}">&nbsp;<span><img class="subquestion" id="qn_<s:property value='%{key}'/>" src="../images/closed.gif" width="12px"/></span></s:if><br/>
	 <div style="display: none; padding-left: 20px;" id="sub_qn_<s:property value='%{key}' />">
		<a href="javascript:;"	onclick="selectAllSub('sub_qn_<s:property value='%{key}' />');"><s:text name="global.select.all.lbl"></s:text></a>&nbsp;&nbsp;<a href="javascript:;"	onclick="selectNoneSub('sub_qn_<s:property value='%{key}'/>');"><s:text name="global.clear.all.lbl"></s:text></a><br/>
		<s:iterator value="%{attribOptionMap[key]}" var="attriboption" status="attopt">
			<s:iterator value="%{#attriboption}" var="option" status="optionst">
				<s:property value="%{optionLabelMap[#option]}" /><br/>
				<s:iterator value="%{subQuestionsMap[#option]}" var="subquestion" status="subqnst">
					 <s:checkbox name="attributeFields" fieldValue="%{key}" value="%{attributeFields.contains(key)}"/><span class="checkbox-space">${value}</span><br />
				</s:iterator>
			</s:iterator>
		</s:iterator>
	</div> 
</s:iterator>

</div>
</div>


</div>



<div class="row bottom-gap">

<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3">&nbsp;<s:text name="bad.sort.by.dd.lbl"></s:text>&nbsp;&nbsp;</div>

<div class="col-md-3 col-sm-3">
<select name="badgesData.sortBy" class="form-control">
<option value="firstname" ><s:text name="bad.fname.cb.lbl"></s:text></option>
<option value="lastname"><s:text name="bad.lname.rb.lbl"></s:text></option>
</select>
</div>


</div>
</div>

<hr/>


<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"><s:text name="bad.settings.th.lbl"></s:text></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"><s:text name="bad.page.size.header.lbl"></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.width.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:textfield name="badgesData.pagewidth" size='5'  cssClass="form-control"></s:textfield><span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.height.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:textfield name="badgesData.pageheight" size='5'  cssClass="form-control"></s:textfield><span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span> </div>
</div>
</div>



<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"><s:text name="bad.page.margin.header.lbl"></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.top.tb.lbl"/></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:textfield name="badgesData.topmargin" size='5'  cssClass="form-control"></s:textfield><span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.bottom.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:textfield name="badgesData.bottommargin" size='5'  cssClass="form-control"></s:textfield> <span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span> </div>
</div>
</div>



<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.left.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:textfield name="badgesData.leftmargin" size='5'  cssClass="form-control"></s:textfield><span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.right.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:textfield name="badgesData.rightmargin" size='5'  cssClass="form-control"></s:textfield> <span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span> </div>
</div>
</div>


<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"><s:text name="bad.badge.size.header.lbl"></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.width.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:textfield name="badgesData.colwidth" size='5'  cssClass="form-control"></s:textfield><span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.height.tb.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:textfield name="badgesData.colheight" size='5'  cssClass="form-control"></s:textfield> <span class="sub-text"><s:text name="bad.inches.tb.lbl"></s:text></span> </div>
</div>
</div>



<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"><s:text name="bad.name.font.header.lbl"></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.type.dd.lbl"></s:text></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:text name="badgesData.fonttype1" ></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.size.dd.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:text name="badgesData.fontsize1" ></s:text></div>
</div>
</div>




<div class="row bottom-gap">
<div class="col-md-2 col-sm-12"></div>
<div class="col-md-10 col-sm-12">
<div class="col-md-2 col-sm-3"><s:text name="bad.text.font.header.lbl"></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.type.dd.lbl"></s:text></div>
<div class="col-md-2 col-sm-3 bottom-gap"><s:text name="badgesData.fonttype2" ></s:text></div>
<div class="col-md-1 col-sm-1"><s:text name="bad.size.dd.lbl"></s:text></div>
<div class="col-md-2 col-sm-3"><s:text name="badgesData.fontsize2" ></s:text></div>
</div>
</div>




</s:form>
<div style="height:25px"></div>
<div class="row">
<center>
<button class="btn btn-primary" id="generatebadges"><s:text name="bad.generate.pdf.btn"></s:text></button>
</center>
</div>


<%-- 

<div class="row">
<div class="col-md-2 control-label"><s:text name="bad.text.font.header.lbl"></s:text></div>
<div class="col-md-4">
<div class="row">
	<div class="col-md-2"><s:text name="bad.type.dd.lbl"></s:text>&nbsp;&nbsp;</div>
	<div class="col-md-4" style="margin-top:-10px"><s:text name="badgesData.fonttype2" ></s:text>  </div>
</div>
</div>
<div class="col-md-4">
<div class="row"><div class="col-md-2"><s:text name="bad.size.dd.lbl"></s:text>&nbsp;&nbsp;</div>
<div class="col-md-4" style="margin-top:-10px"><s:text name="badgesData.fontsize2" ></s:text> </div>
</div>
</div>
</div>
</s:form>
<br/><br/>
<p class="text-center">
                                    <button class="btn btn-primary" id="generatebadges"><s:text name="global.submit.btn.lbl"></s:text></button>
                                </p>
</div> --%>
</div>
<script>
$(document).ready(function() {
	$('select[name="line1fontfamily"]').addClass("form-control");
	$('select[name="line1fontsize"]').addClass("form-control");
	$('select[name="line2fontfamily"]').addClass("form-control");
	$('select[name="line2fontsize"]').addClass("form-control");

	   $('input[name="badgesData.transactionradio"]').iCheck({  
	  		checkboxClass: 'icheckbox_square-grey', 
	  		radioClass: 'iradio_square-grey'});
 		
	   $('input[name="attributeFields"]').iCheck({  
	  		checkboxClass: 'icheckbox_square-grey', 
	  		radioClass: 'iradio_square-grey'});

	   $('.default').iCheck({  
	  		checkboxClass: 'icheckbox_square-grey', 
	  		radioClass: 'iradio_square-grey'});
	   
	   $('.tkts').iCheck({  
	  		checkboxClass: 'icheckbox_square-grey', 
	  		radioClass: 'iradio_square-grey'});

	   $('.date').datetimepicker({
           format: 'm/d/Y',
           timepicker: false
       });

    $('#generatebadges').click(function(){
    	 var tid=document.getElementById('transactionid').value;
   		 var allradio=document.getElementById('allradio').checked;
   	     var dateradio=document.getElementById('dateradio').checked;
   	    var tidradio=document.getElementById('tidradio').checked;
   	    var isrecurring=document.getElementById('isrecurr').value;

        if(document.getElementById('tktsradio')){

            var tkts=document.getElementById('tktsradio').checked;
            if(tkts && $('.tkts:checked').length==0){
            	 bootbox.confirm(props.global_select_tkt_msg,function(result){});
            	 return;
                }
            }
            
   	    
   	 
   	 if(tidradio==true && tid==""){
	   	bootbox.alert(props.bad_tid_empty_error_lbl);
   	 return false;
   	 }
   	     
        if(isrecurring=="true"){
       	 var eventdate=document.getElementById('source').value;
             if(eventdate=="" && (allradio==true || dateradio==true)){
            	 bootbox.confirm(props.bad_recurring_date_error_lbl,function(result){});
                }else{ generatePDF();
                        }
        }
        else{
             generatePDF();
           }
        });
    
    $('input[type="radio"]').on('ifClicked', function (event) {
        if(this.value==2){
        	$('#daterange').slideDown();
        	$('#tid_list').slideUp();
        	$('#tikt_list').slideUp();
        	$('#tikt_sel').slideUp();
        }else if(this.value==3){
        	$('#tid_list').slideDown();
        	$('#daterange').slideUp();
        	$('#tikt_list').slideUp();
        	$('#tikt_sel').slideUp();
        }else if(this.value==4){
        	$('#tikt_list').slideDown();
        	$('#tikt_sel').slideDown();
        	$('#daterange').slideUp();
        	$('#tid_list').slideUp();
        }else{
        	$('#daterange').slideUp();
        	$('#tid_list').slideUp();
        	$('#tikt_list').slideUp();
        	$('#tikt_sel').slideUp();
        }
    });
       
});

$('#CheckAll').click(function(event) {  
    $('.tkts').iCheck('check');
});

 $('#UnCheckAll').click(function(event) { 
 	 $('.tkts').iCheck('uncheck');      
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

function selectAllSub(divid) {
	  $("#"+divid+" :checkbox").iCheck('check');
}

function selectNoneSub(divid) {
	$("#"+divid+" :checkbox").iCheck('uncheck');
}

function generatePDF(){
    document.getElementById('errormsgid').style.display='none';
	document.report.action ='Badges!attendeeLabels';
	document.report.submit();
	return true;
}
</script>



