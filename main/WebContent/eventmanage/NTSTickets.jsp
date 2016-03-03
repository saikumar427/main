<%@taglib uri="/struts-tags" prefix="s" %>
<script>
var jsondata =${jsonData};
var nts_commission=${eventData.ntsCommission};

var fbpromodata=${promotionsJson};
var ntsenable='${ntsEnable}';
var feeshowstatus='${feeshowstatus}';
function updatentsstatus(){
//if(ntsenable=='N'){
$.ajax({
	url: 'NetworkSelling!updateNTSStatus?eid=${eid}&status=Y',
	type: 'get',
	success: function (t){
			var result = t;
			if(!isValidActionMessage(result)) return;
			//window.location.href="/main/eventmanage/NetworkSelling?eid=${eid}";	
			$("#ntspgenbbtn").hide();
			$("#ntspgdisbtn").show();
	}
});
}


</script>

<div class="alert alert-info row">
	<i class="fa fa-info-circle"></i>&nbsp;<s:text name="global.nts.help.msg1" />
	<a href='#' onclick='loadNTSVideo();'>&nbsp;<s:text name="global.click.here"/></a>
	<s:text name="global.nts.help.msg2" />
	
</div>

<s:form name="ntsform" id="ntsform" action="NetworkSelling!updateCommission" method="post">
<s:hidden name="eid"></s:hidden>
	<!-- <div class="alert alert-info hidden-xs">
         <i class="fa fa-info-circle"></i>&nbsp;Our patent pending technology encourages users to share your event on the web and social media. We generate unique URLs for users to track page visits and ticket sales. The fees to use this feature are 10 cents per visit + 10% of total ticket sale proceeds sold through unique URLs.&nbsp;<a href='javascript:;' onclick='loadNTSVideo();'><b>Click here</b></a>&nbsp;to watch brief introduction video.
    </div> -->
	<br>
	
	<div class="row sticky-out-button pull-right  box-top-gap">
	<div class="col-md-12"> 
		 <div class="pull-right">
			<div id="ntspgdisbtn" style="<s:if test='%{ntsEnable=="N"}'>display:none;</s:if>"><input id="disable" type="button" onclick="disableNTS();" value="<s:text name="global.nts.disable.btn.lbl"/>" class="btn btn-primary" /></div>
			<div id="ntspgenbbtn" style="<s:if test='%{ntsEnable=="Y"}'>display:none;</s:if>"><input id="enable" type="button" onclick="updatentsstatus();" value="<s:text name="global.nts.enable.btn.lbl"/>" class="btn btn-primary"/></div>
		 </div> 
	 </div> 
</div>
	
	
	<div class="row white-box no-button">
<div id="ntsdiv" >
</div>	
<!-- <div id="nts_br" style="height:33px;"></div> -->
<div id="loading" class="box-top-gap" style="display:none;"><center><img src="../images/ajax-loader.gif"/></center></div>


</div> 
	
	
	
	
   <!--  <div class="panel panel-primary"> -->
        <%--  <div class="panel-heading">
             <h3 class="panel-title"><s:text name="global.nts.section.header"/>&nbsp;
                 <i class="fa fa-info-circle"  style="cursor:pointer" id="nts_pg_info" 
                 title="<s:text name="global.nts.help.msg1"/><a href='#' onclick='loadNTSVideo();'>&nbsp;<s:text name="global.click.here"/></a>&nbsp;<s:text name="global.nts.help.msg2"/>"></i>
             </h3>
         </div> --%>
        <!--  <div class="panel-body table-responsive" style="padding-top: 0px;"> -->
			<%-- <table id="mypromo" class='table table-hover'>
				<thead>
					<tr>
						<th align="center"><s:text name="global.nts.who.lbl"/></th>
						<th align="center"><s:text name="global.nts.when.lbl"/></th>
						<th align="center"><s:text name="global.nts.event.page.visits.lbl"/></th>
						<th align="center"><s:text name="global.nts.ticket.sales.lbl"/></th>
					</tr>
				</thead>
				<tbody id="ntspartnerlist">
				<tr id="nodata"><td><s:text name="global.nts.nodata.msg"/></td><td></td><td></td> <td></td></tr>
				</tbody>
				<s:if test="%{promotedMembers.size>0}">
				</s:if>
				<s:else>&nbsp;</s:else>
			</table> --%>
		<!-- </div> -->
             
       <%--  <div class="panel-footer">
			<div id="ntspgdisbtn" style="<s:if test='%{ntsEnable=="N"}'>display:none;</s:if>"><input id="disable" type="button" onclick="disableNTS();" value="<s:text name="global.nts.disable.btn.lbl"/>" class="btn btn-primary" /></div>
			<div id="ntspgenbbtn" style="<s:if test='%{ntsEnable=="Y"}'>display:none;</s:if>"><input id="enable" type="button" onclick="updatentsstatus();" value="<s:text name="global.nts.enable.btn.lbl"/>" class="btn btn-primary"/></div>
		</div> --%>
	<!-- </div>     -->
</s:form> 


<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<style type="text/css" title="currentStyle">
@import "/main/bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}
</style> 
<script>
$(document).ready(function(){	
//alert(JSON.stringify(fbpromodat.apromotions,undefined,2));

	$.each(fbpromodata,function(outkey,resObj){
		var content='<table class="table table-responsive table-hover" id="ntspartnerlist">';
		$.each(resObj, function( inkey, valueobj ) {
			if(valueobj.name==undefined)valueobj.name='';
			if(valueobj.day==undefined)valueobj.day='';
			if(valueobj.visits==undefined)valueobj.visits='0';
			if(valueobj.visitstotal==undefined)valueobj.visitstotal='0';
			if(valueobj.sales==undefined)valueobj.sales='0';
			if(valueobj.salestotal==undefined)valueobj.salestotal='0';

			
			image = "<a href='https://www.facebook.com/profile.php?id="+valueobj.fbuid+"' target='_blank'><img title='"+valueobj.name+"' src='https://graph.facebook.com/"+valueobj.fbuid+"/picture' /></a>";
		    // content +='<tr><td>'+image+'<span style="vertical-align: middle !important;">'+valueobj.name+'</span></td><td style="vertical-align: middle !important;">'+valueobj.day+'&nbsp;<img src="/main/images/home/icon_facebook.png"></td><td style="vertical-align: middle !important;">'+props.global_total_lbl+':&nbsp;'+valueobj.visits+'&nbsp;'+props.global_fees_lbl+':&nbsp;'+valueobj.visitstotal+'</td><td style="vertical-align: middle !important;">'+props.global_total_lbl+':&nbsp;'+valueobj.sales+'&nbsp;'+props.global_fees_lbl+':&nbsp;'+valueobj.salestotal+'</td></tr>';
content+='<tr><td>'

content+='<div class="row">';
content+='<div class="col-md-3 col-sm-3 col-xs-3">'+image+'<span style="vertical-align: middle !important;">&nbsp;'+valueobj.name+'</span></div>';
content+='<div class="col-md-3 col-sm-3 col-xs-3 sm-font" style="vertical-align: middle !important;">'+valueobj.day+'&nbsp;<img src="/main/images/home/icon_facebook.png"></div>';
content+='<div class="col-md-3 col-sm-3 col-xs-3 sm-font" style="vertical-align: middle !important;">'+props.global_total_lbl+':&nbsp;'+valueobj.visits+'&nbsp;'+props.global_fees_lbl+':&nbsp;'+valueobj.visitstotal+'</div>';
content+='<div class="col-md-3 col-sm-3 col-xs-3 sm-font" style="vertical-align: middle !important;">'+props.global_total_lbl+':&nbsp;'+valueobj.sales+'&nbsp;'+props.global_fees_lbl+':&nbsp;'+valueobj.salestotal+'</div>';

			content+='</div>';
			content+='</td></tr>';
		   
		});
		 content+='</table>';
		$("#ntsdiv").html(content);
	});

	
	if(fbpromodata.promotions.length>0)
        $('#ntspartnerlist').dataTable({
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "bLengthChange": false,
            "bSort" : false,
            "aoColumns": [null],                                                                                        
        	"bFilter": false  
        });
	else{
		var content='<table class="table table-responsive table-hover" id="ntspartnerlist">';
		content+='<tr><td><div class="not-found">No Network Ticket Selling to display</td></tr>';
		content+='</table>';
		$("#ntsdiv").html(content);
		}
		


	if(fbpromodata.promotions.length<5)
        removePagination('ntspartnerlist');
	
});


function disableNTS(){
	var url='/main/eventmanage/NetworkSelling!disableNTS?eid=${eid}';
	callURLandReload(url);
	}
	
function callURLandReload(url){
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();
	var ajax= 
	$.ajax({
		url: url,
		type: 'get',
		success: function(t){ 
			if(!isValidActionMessage(t)) return;
			//window.location.reload( true ); 
			//displayPromotionsTable('N');
			$("#ntspgdisbtn").hide();
			$("#ntspgenbbtn").show();
		}
	});
	
}	

function loadNTSVideo(){
	   var url='/main/eventmanage/NetworkSelling!loadNtsVideo?eid=${eid}';	 
		   $('iframe#popup').attr("src",url);		 
		   $('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3>'+props.global_nts_lbl+'</h3>');
		   $('#myModal').modal('show');
	}

$(function(){
	$('#nts_pg_info').tooltipster({
		interactive:'true',
		contentAsHTML:'true',
		fixedWidth:'100px',
	    position: 'right'
	    //content: $('<span>Our patent pending technology encourages users to share your event on the web and social media. <br>We generate unique URLs for users to track page visits and ticket sales. <br>The fees to use this feature are 10 cents per visit + 10% of total ticket sale proceeds sold through unique URLs.</span>'),
	    });
});
</script>



