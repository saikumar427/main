<%@taglib uri="/struts-tags" prefix="s"%>


<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>

<html>
<head>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<script type="text/javascript" src="/main/js/eventmanage/snapshot.js"></script>

</head>
<body>
<s:form name="upgradeevent" id="upgradeevent" action="SpecialFee!saveUpgradeEvent" method="post">
<div class="col-md-12">
<div id="specialfeeerrormessages"></div>
<s:hidden name="source"></s:hidden>
<s:hidden name="mgrid"></s:hidden>
<s:hidden name="eid"></s:hidden>
<s:if test="%{ticketingtype.contains('200')}">
<div class="row">
	<div class="col-md-12">
         <input type="radio" class="upgradelevel" name="ticketingtype" checked="checked" value="200"/>&nbsp;<s:text name="up.pro.ticketing.lbl"/>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
         <input type="radio" class="upgradelevel" name="ticketingtype" value="300">&nbsp;<s:text name="up.adv.ticketing.lbl"/>
    </div>
</div>
</s:if>
<s:elseif test="%{ticketingtype.contains('300')}">
<div class="row">
<div class="col-md-5">
<input type="hidden" name="ticketingtype" value="300">
 <s:text name="up.adv.ticketing.lbl"/>
</div>
</div>
</s:elseif>
<s:elseif test="%{ticketingtype.contains('150')}">
<input type="hidden" name="ticketingtype" value="150">
<div class="row">
<div class="col-md-5">
<s:text name="up.pro.rsvp.event.lbl"/>
</div>
</div>
</s:elseif>
</div>
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
</body>
</html>
<script>
$(document).ready(function(){
	
	$('#upgradesubmitbtn').click(function(){
		submitUpgradeFeeForm();
	});

	$('#cancel').click(function() {
	    parent.closepopup();
	});
	
	$('.upgradelevel').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
});

var submitUpgradeFeeForm = function(){
	var currentlevel=parent.$("#evt_cur_lvl").val();
	
	if(currentlevel==undefined)
		currentlevel=parent.level;
	
	var isnonprofit=parent.$("#isnonprofit").val();
    var selected= $("input[type='radio'][name='ticketingtype']:checked").val();
	var url="SpecialFee!saveUpgradeEvent";
	$.ajax({
		url : url,
		data : $('#upgradeevent').serialize(),
		type : "post",
		error: function(){alert(props.global_system_failure);},
		success : function(t){
			if(!parent.isValidActionMessage(t)) return;
			if(selected!=undefined) parent.$("#evt_cur_lvl").val(selected);
			if(isnonprofit=='Y'){
				if(selected!=undefined && selected=='200'){
					parent.$("#upgrade_level").html(props.up_pro_non_profit_lbl);
					parent.levelChanged('pro');
				}else if(selected!=undefined && selected=='300'){
					parent.$("#upgradebtn").hide();
					parent.$("#upgrade_level").html(props.up_adv_non_profit_lbl);
					parent.levelChanged('advanced');
				}else if(currentlevel=='90'){
					parent.$("#upgradebtn").hide();
					parent.$("#upgrade_level").html(props.up_pro_non_profit_lbl);
					parent.levelChanged('pro');
				}else if(currentlevel=='200'){
					parent.$("#upgradebtn").hide();
					parent.$("#upgrade_level").html(props.up_adv_non_profit_lbl);
					parent.levelChanged('advanced');
				}
			}else{
				if(selected!=undefined && selected=='200'){
					parent.$("#upgrade_level").html(props.up_pro_lbl);
					parent.levelChanged('pro');
				}else if(selected!=undefined && selected=='300'){
					parent.$("#upgrade_level").html(props.up_adv_lbl);
					parent.levelChanged('advanced');
					parent.$("#upgradebtn").hide();
				}if(currentlevel=='90'){
					parent.$("#upgradebtn").hide();
					parent.levelChanged('pro');
					parent.$("#upgrade_level").html(props.up_pro_lbl);
				}if(currentlevel=='200'){
					parent.$("#upgradebtn").hide();
					parent.levelChanged('advanced');
					parent.$("#upgrade_level").html(props.up_adv_lbl);
				}
			}
			parent.closepopup();
		}
	});

};
</script>
