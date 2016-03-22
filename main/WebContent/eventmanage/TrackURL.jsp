<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script>
function searchKeyPress(e) {
	   
	   if (e.keyCode === 13) {
	           e.preventDefault();
	           return false;
	   }
	   return true;
	}
</script>

<div id="sMassage"></div>

<%-- <div class="section-main-header">
	<s:text name="turl.section.header" />&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="trackurlinfo" title="<s:text name="turl.help.msg"/>"></i>
</div> --%>
<div class="row sticky-out-button pull-right">
	<!--panel footer goes here-->
	<button class="btn btn-primary" id="createTracking" type="button">
					<s:text name="turl.create.turl.btn.lbl" />
				</button>
				<%-- &nbsp;
				<button class="btn btn-primary" id="importTracking" type="button">
					<s:text name="turl.import.turl.partners.btn.lbl" />
				</button> --%>
	
</div>
<s:form name="trackURLform" cssClass="white-box" action="TrackURL!trackURLexist" id="trackURLform" method="post">
<s:hidden name="eid"></s:hidden>
<s:if test="%{powertype=='RSVP' && currentLevel=='90'}">
<s:text name="trul.upgrade.msg1"/> <a href="/main/pricing"><s:text name="trul.upgrade.msg2"/></a> <s:text name="trul.upgrade.msg3"/><br/>
</s:if>


<s:set name="exist" value="TrackURLData.alreadyexists"></s:set>
<div  id="trackurllist" style="display:none;" >
    <div id="getdata" >
    <div class="table-responsive">
    		<table class="table table-hover"  id="trackdatatable">
    			 <thead style="display:none;">
                     <th><s:text name="global.name.lbl"/></th>
                 </thead>
                 <tbody id="trackbody">
                   <tr class='nodata'>   
                  	 <td class='dataMsg'><s:text name="global.loading.msg"/></td>                                         
                   </tr>
                  </tbody>
    		</table>
    </div>                      	
    </div> 
                                	<div id="forload"></div>
                                	<div id="createTrack" class="col-md-12 col-sm-12 row"></div>
                                	<div id="importTrack" class="col-md-12 col-sm-12 row"></div>
</div>   
<br>
<%-- <div class="col-md-12">
		<textarea cols="90" rows="7"  class="form-control" onClick="this.select()"><script type='text/javascript' language='JavaScript' src='<s:text name='eventData.serverAddress'></s:text>/home/js/widget/eventregistration.js'></script><iframe  id='_EbeeIFrame' name='_EbeeIFrame'  src='<s:text name='eventData.serverAddress'></s:text>/eregister?eid=${eid}&track=${trackcode}&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='700'></iframe>
	   </textarea>
	</div> --%>
<div class="row">
	<div class="col-sm-12 col.md-12 col-xs-12 text-center img-responsive">
		<div id="chart_tracksales" style="width:100%;"></div>
	</div>
</div>
</s:form>
 <script type="text/javascript" src="/main/js/dataTables.js"></script>
<%--  <script type="text/javascript" src="IntegrationLinks!populateTrackurlsList?eid=${eid}"></script> --%>
 <script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<style>
tr#editTrackURL:hover {
  background-color: #FFFFFF !important;
  
}
.form-horizontal .control-label{
	padding-top: 0px !important;
}
</style>
<script>
var eid = ${eid};	
var json = ${jsonData};
var codeData = json['codeData'];
/* delete json['codeData']; */

$(document).ready(function(){
	prepareTrackURLTable(json);
});


function createTrackUrl(eid){
if($('#editTrackURL').is(':visible'))
	$('#editTrackURL').remove();
createTrackingURL(eid);
}

$('#createTracking').click(function(){
	if($('#editTrackURL').is(':visible'))
		$('#editTrackURL').remove();
	createTrackingURL(eid);
	//specialFee("${eid}","200","TrackURL","Ticketing");
	/* var _powertype = $('#powertype').val();
	if(_powertype=='RSVP')
	specialFee("${eid}","150","TrackURL","RSVP");
	else
	specialFee("${eid}","200","TrackURL","Ticketing"); */
	
});
function createTrackingURL(eid){
	//loadingPopup();
	//$('#createTrack').slideUp();
	$('#importTracking').prop("disabled",false);
	$('#importTrack').html('');
	showProcessing('forload');
	var url='TrackURL!createTrackingURL?eid='+eid;	    	
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#createTrack').html(result);
			$('html, body').animate({ scrollTop: $("#createTrack").offset().top-scrollTo}, 1000);
			$('#createTrack').fadeIn(slideTime);
			hidePopup();
			$('input[name="name"]').focus();
			 hideProcessing();	
		}
	});
	/* 
	$('#createTrack').load(url,function(){
		$('html, body').animate({ scrollTop: $("#createTrack").offset().top-scrollTo}, 1000);
		//$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
		$('#createTrack').fadeIn(slideTime);
		hidePopup();
		$('input[name="name"]').focus();
		 hideProcessing();		
	});
	*/
	
	
}
function closeWell(){
	$('#createTrack').html('');
	$('#createTracking').prop("disabled",false);
	$('#importTrack').html('');
	$('#importTracking').prop("disabled",false);
	$('html, body').animate({ scrollTop: $("#createTracking").offset().top-scrollTo}, 1000);
}



	

function getReloadedTrackURLData(state){
	
	$('#createTrack').html('');
	$('#createTracking').prop("disabled",false);
	 var url='TrackURL!getReloadedTrackURLData?eid='+eid;
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			$('#trackdatatable').dataTable().fnDestroy(false);
			$('#trackbody').html('');
			
			prepareTrackURLTable(JSON.parse(result));
			$('#sMassage').html('');
			if(state=='add'){
				notification(props.turl_added_successfully_msg,'success');
				//$('html, body').animate({ scrollTop: $("#sMassage").height()}, 1000);
				//statusMessage("sMassage", props.turl_added_successfully_msg,"success");
			}else if(state=='delete'){
				notification(props.turl_deleted_successfully_msg,'success');
				//$('html, body').animate({ scrollTop: $("#sMassage").height()}, 1000);
				//statusMessage("sMassage",props.turl_deleted_successfully_msg,"success");
			}
			
			var count =$("#trackdatatable > tbody > tr").length;
			callDataTable();
		      if(count<=5)
		    	  removePagination('trackdatatable');
		    	 /*  $('#trackdatatable_paginate').hide(); */
		}
}); 
}


$(function(){
	  $('#trackurlinfo').tooltipster({
			 position: 'bottom',
		    fixedWidth:'100px',
		    content: $('<span>'+props.turl_help_msg+'</span>'),
		    });
	  
	  
	  $(document).on('keyup','.dynamic',function(){
		  $('#lable').html("<s:property value='TrackURLData.eventURL'/>");
		 $('#lable').append($(this).val());
	  });
});

google.load('visualization', '1', {packages: ['corechart']});
if(document.getElementById("chart_tracksales"))
	google.setOnLoadCallback(drawPieChart);
trackUrlList();

function trackUrlList(){
   document.getElementById('trackurllist').style.display='block';
   document.getElementById('chart_tracksales').style.display='block';
}

var tracksaleschartdata;
function drawPieChart() {
	if(tracksaleschartdata)
	if(tracksaleschartdata.length>0)
		drawChart(tracksaleschartdata, 'chart_tracksales');
    }
    
function drawChart(chartdata, divid) {
	if(document.getElementById(divid)){
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'a');
        data.addColumn('number', 'b');
        data.addRows(chartdata);
		var options = {
          is3D: 'true',
		  legend: {
		  position: 'right',
		  textStyle:{
		  fontName: 'verdana,arial,helvetica,clean,sans-serif',
          fontSize: 12
		  }
        }
		};
        new google.visualization.PieChart(document.getElementById(divid)).draw(data, options);
        }
}

filltracksaleschartdata();

function filltracksaleschartdata(){
tracksaleschartdata = [];
var totalSold=0;
citems=json.citems;
for(var i=0;i<citems.length;i++){
ticketsold=citems[i].tickets;

var qty=0;
for(var j=0;j<ticketsold.length;j++){
qty+=Number(ticketsold[j].tqty);
totalSold+=qty;
totalSold=10;
}
trackData=[citems[i].trackingcode, {v:qty, f:props.trul_image_tooltip_sold_lbl+' - '+qty}]
tracksaleschartdata.push(trackData);
} 
if(totalSold==0) tracksaleschartdata = [];

}

var html='<div style="width:105px;"></div>';

function prepareTrackURLTable(json){
	 if(json.citems.length == 0){
		 $('.nodata .dataMsg').html('<s:text name="turl.nodata.msg" />');
		// $('#createTracking').trigger( "click" );
	 }
	//$.each(json,function(outkey,resObj){
		$.each( json.citems, function( inkey, valueobj ) {
			//if(valueobj.tickets.length==0)tickets=html;
			var tickets='<ul style="list-style-type: circle">';
			//alert(valueobj.tickets.length);
			if(valueobj.tickets.length>0){
				$.each( valueobj.tickets, function( keytck, valuetck ) {
					tickets+='<li class="li_disc">'+valuetck.tqty+' - '+valuetck.tname+'</li>';
					//tickets+='<div class="col-md-2 col-sm-3 col-xs-12 control-label">'+valuetck.tname+':</div><div class="col-md-20 col-sm-9 col-xs-12">'+valuetck.tqty+'</div>';
					//tickets+= valuetck.tname+':&nbsp;&nbsp;'+valuetck.tqty+'<br>';
				});
			}else
				tickets+='<span class="not-found">'+props.turl_no_reg_msg_lbl+'</span>';
				
				tickets+='</ul>';
			
		/* var tempentry=$('<tr><td>'+valueobj.trackingcode+'<br>[<a href="javascript:;" onclick=TrackManagePage('+eid+',"'+valueobj.trackingcode+'","'+valueobj.count+'","'+valueobj.id+'")>'+props.turl_manage_lnk_lbl+'</a>&nbsp;&nbsp;<a href="javascript:;" onclick=Reports('+eid+',"'+valueobj.trackingcode+'","'+valueobj.count+'","'+valueobj.id+'")>'+props.turl_report_lnk_lbl+'</a>&nbsp;&nbsp;<a href="javascript:;" onclick=Widget('+eid+',"'+valueobj.trackingcode+'","'+valueobj.count+'","'+valueobj.id+'")>'+props.turl_widget_code_lnk_lbl+'</a>]</td>'+
				'<td><a href='+valueobj.url+' target="_blank">'+valueobj.url+'</a></td><td><div style="width:150px">'+tickets+'</div></td><td><div style="width:50px">'+valueobj.count+'</div></td></tr>'); */
		
			var tempentry =$('<tr class="tr_'+inkey+'"><td><div class="col-md-9 col-sm-9 col-xs-12 row">'+
					'<div class="col-sm-3 col-md-3 row" id="trackUrlName_'+inkey+'"></div>'+
					
					'<div class="col-sm-2 col-md-2 sm-font"><label class="codeToggle" id="code_'+inkey+'"  data-index="'+inkey+'" style="cursor:pointer;">'+props.turl_code_lbl+'<span class="glyphicon glyphicon-menu-right arrow-gap"></span></label></div>'+
					
					'<div class="col-sm-2 col-md-2 sm-font">'+valueobj.count+'&nbsp;'+props.trul_visits_lbl+'</div>'+
					
					'<div class="col-sm-3 col-md-3 sm-font"><label class="regToggle" id="reg_'+inkey+'"  data-index="'+inkey+'" style="cursor:pointer;">'+props.turl_registrations_lbl+'<span class="glyphicon glyphicon-menu-right arrow-gap"></span></label></div>'+
					
					'<div class="col-sm-2 col-md-2 sm-font" id="status_'+valueobj.trackingcode+'">'+valueobj.status+'</div>'+
					
					'</div><div class="col-md-3 col-sm-3 col-xs-12 sm-font">'+
					'<div class="col-xs-12 col-sm-12 col-md-12 hideThis" style="display:none;">'+
					'<span style="cursor:pointer; margin-right:10px;"><a href="javascript:;" onclick=TrackManagePage('+eid+',"'+valueobj.trackingcode+'","'+valueobj.count+'","'+valueobj.id+'",'+inkey+')>'+props.global_edit_lnk_lbl+'</a></span>'+
					'<span style="cursor:pointe; margin-right:10px;" ><a href="javascript:;" class="delete_'+valueobj.trackingcode+'" onclick=deleteTrack('+eid+',"'+valueobj.trackingcode+'","'+valueobj.id+'")>'+props.global_delete_lnk_lbl+'</a></span>'+
/* we are not giving report Link */	'<span style="display:none"><a href="javascript:;" onclick=Reports('+eid+',"'+valueobj.trackingcode+'","'+valueobj.count+'","'+valueobj.id+'")>'+props.turl_report_lnk_lbl+'</a></span></div><div style="clear:both;"></div></div>'+
					
					'<div style="clear:both;"></div><div class="background-options sm-font '+inkey+'codeDiv" id="" style="display:none;">'+
					'<div class="linkDiv row"><div class="col-sm-10 col-md-10 col-xs-12 copyLink_'+inkey+'" style="margin-bottom: 10px;" >'+valueobj.url+'</div><div class="col-md-2 col-sm-2 col-xs-12 subLinkHide" style="display:none;"><a href="javascript:;" id="'+inkey+'_copyLink" data-index="'+inkey+'" data-link="'+valueobj.url+'">'+props.turl_copy_link_lbl+'</a></div></div>'+
					'<div class="linkDiv row"><div class="col-sm-10 col-md-10 col-xs-12 copyMLink_'+inkey+'" style="margin-bottom: 10px;">'+valueobj.url+'&manage=manage</div><div class="col-md-2 col-sm-2 col-xs-12 subLinkHide"style="display:none;"><a href="javascript:;" id="'+inkey+'_copyMLink" data-index="'+inkey+'" data-link="'+valueobj.url+'&manage=manage">'+props.trul_copy_manage_link_lbl+'</a></div></div>'+
					'<div class="linkDiv row"><div class="col-sm-10 col-md-10 col-xs-12 copyWLink_'+inkey+'"><div id="trackWidget_'+inkey+'"></div></div><div class="col-md-2 col-sm-2 col-xs-12 subLinkHide" style="display:none;"><a href="javascript:;" id="'+inkey+'_copyWLink" data-index="'+inkey+'">'+props.trul_copy_widget_code_lbl+'</a></div></div>'+
					'<div style="clear:both;"></div></div>'+
					
					'<div style="clear:both;"></div><div class="sm-font background-options '+inkey+'regDiv" style="display:none;">'+tickets+'<div style="clear:both;"></div></div>'+
					'<div style="clear:both;"></div><div class="editTrack'+inkey+'"></div>'+
					'</td></tr>');	
				$('#getdata .table .nodata').remove();
				$('#getdata .table').append(tempentry);	
				$('#getdata .table #trackWidget_'+inkey).text('<script type=\'text/javascript\' language=\'JavaScript\' src =\''+json['codeData']+'/home/js/widget/eventregistration.js\'><\/script>'+
				'<iframe  id=\'_EbeeIFrame\' name=\'_EbeeIFrame\' src=\''+json['codeData']+'/eregister?eid='+eid+'&track='+valueobj.trackingcode+'&viewType=iframe;resizeIFrame=true&context=web\''+
				' height=\'0\' width=\'700\'></iframe>');
				
				$('#'+inkey+'_copyWLink').attr('data-widget',$('#getdata .table #trackWidget_'+inkey).text());
				
				var html = reducedTrackName(valueobj.trackingcode);
				$('#getdata .table #trackUrlName_'+inkey).html(html);
		
				function reducedTrackName(Trackname){
					if(Trackname.length>10){
						Trackname = Trackname.substring(0, 6);
						return Trackname+'...';
					}
					return Trackname;
				}
		});
	//});	
		
	hideLink();
	triggerCopy();
	triggerCopyManage();
	triggerCopyWidget();
}


$(document).on('click','.codeToggle', function(){	
	var index=$(this).data('index');
	if($('.'+index+'codeDiv').is(':hidden')){
		$('.'+index+'codeDiv').slideDown();
		$(this).find('.glyphicon').addClass('down').removeClass('original');
		$('.'+index+'regDiv').slideUp();
		$('#reg_'+index+' .glyphicon').addClass('original').removeClass('down');
		hideLink();
	}
	else{
		$('.'+index+'codeDiv').slideUp();
		$(this).find('.glyphicon').addClass('original').removeClass('down');
	}
	triggerCopy();
	triggerCopyManage();
	triggerCopyWidget();
});
$(document).on('click','.regToggle', function(){	
	var index=$(this).data('index');
	if($('.'+index+'regDiv').is(':hidden')){
		$('.'+index+'regDiv').slideDown();
		$(this).find('.glyphicon').addClass('down').removeClass('original');
		$('.'+index+'codeDiv').slideUp();
		$('#code_'+index+' .glyphicon').addClass('original').removeClass('down');
	}
	else{
		$('.'+index+'regDiv').slideUp();
		$(this).find('.glyphicon').addClass('original').removeClass('down');
	}
});



function hideLink(){

	$(document).on('mouseover','tr',function() {
	$(this).find('.hideThis').show();
	});
	$(document).on('mouseout','tr',function() {
	$(this).find('.hideThis').hide();
	});
	
	$(document).on('mouseover', '.linkDiv', function(){
		$(this).find('.subLinkHide').show();
	});
	$(document).on('mouseout', '.linkDiv', function(){
		$(this).find('.subLinkHide').hide();
	});
	
}

function closeTR(){
	//alert('colse');
	$('html, body').animate({ scrollTop: $("#createTracking").offset().top-scrollTo}, 1000);
	$('#editTrackURL').slideUp();
	$('#editTrackURL').remove();
}

function changeStatus(code,status){
	if("Approved"==status)
		$('#status_'+code).html(props.trul_approved_lbl);
	else
		$('#status_'+code).html(props.trul_suspended_lbl);
	
	//$('#status_'+code).html(status);
	notification(props.trul_url_updated_success_lbl,'success');
	//$('#sMassage').html('');
	//$('html, body').animate({ scrollTop: $("#sMassage").height()}, 1000);
	//statusMessage("sMassage","Track URL updated successfully","success");
	
}
function TrackManagePage(eid,trackcode,count,secretcode,index){	
	//loadingPopup();
	$('#search').val('');
	$('#editTrackURL').remove();
	$('#createTrack').slideUp();
	$('#createTracking').prop("disabled",false);
	$('#importTrack').slideUp();
	$('#importTracking').prop("disabled",false);
	$('#code_'+index+' .glyphicon').addClass('original').removeClass('down');
	$('#reg_'+index+' .glyphicon').addClass('original').removeClass('down');
	$('.editTrack').slideUp();
	$('.editTrack').remove();
	$('<tr id="editTrackURL"><td class="editTrack" style="border-top:none; display:none;"><center><img src="../images/ajax-loader.gif"></center></td></tr>').insertAfter($('.tr_'+index));
	
	var url= 'TrackURL!editTrackURL?eid='+eid+'&trackcode='+trackcode+'&scode='+secretcode;
	
	 $.ajax({
		type: 'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('.editTrack').slideDown();
			$('.'+index+'codeDiv').slideUp();
			$('.'+index+'regDiv').slideUp();
			$('.editTrack').html(result);
			
			/* scrollTo=scrollTo+50; */
			$('html, body').animate({ scrollTop: $("#editTrackURL").offset().top-scrollTo}, 1000);
			//hidePopup();
		},
		error:function(){
			alert('error at edit trackurls');
		}
	});
	
}

function deleteTrack(eid,trackcode,scode){
		$('.delete_'+trackcode).css({ 'pointer-events': 'none' });
	   var url="TrackURL!checkTrackCodeReport?eid="+eid+"&trackcode="+trackcode;
	   $.ajax({
		   url: url,
		   type: 'get',
		   success: function(t){
				if(!isValidActionMessage(t)) return;
			   var result = t;
				  if(result.indexOf('trackcodereportexists')>-1){
					//  alert("There are some registrations with this Track URL. So this Track URL can't be delete");
					 bootbox.confirm(props.turl_cannot_delete_msg, function (result) {
						 $('.delete_'+trackcode).css({ 'pointer-events': '' });
					 });
				  }else{ 
				     //var agree="Do you want to really delete this Track URL?";
				     bootbox.confirm('<h3>'+props.turl_delete_track_msg+'</h3>'+props.turl_delete_confirm_msg, function (result) {
				    	 	if (result){	
				    	    //parent.loadingPopup();
				    	    var deleteurl="TrackURL!deleteTrackURL?eid="+eid+"&trackcode="+trackcode;
		                	$.ajax({
		                		url : deleteurl,
		                		type : 'get',
		                		success: function(t){
		                			var msg = t;
		                			 if(msg.indexOf('delete')>-1)
		                				 getReloadedTrackURLData('delete');
		                              else
		                            	  getReloadedTrackURLData('delete');
		                		}
		                	});
				    	}else
				    		$('.delete_'+trackcode).css({ 'pointer-events': '' });
				    });		          
				   }
		   }
		   
	   });
}
$('#importTracking').click(function(){
	$('#createTracking').prop("disabled",false);
	$('#createTrack').html('');
	showProcessing('forload');
	var url='TrackURL!importURL?eid='+eid;			
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	scrollTo = scrollTo+20;
		$('#importTrack').load(url,function(){
			$('html, body').animate({ scrollTop: $("#showID").offset().top-scrollTo}, 1000);
			//$(this).animate({ scrollTop: $("#showID").height()}, 1000);
			//$(this).css("display","block").animate({ scrollDown: 100 }, 'slow');
			$('importTrack').fadeIn(slideTime);
			hideProcessing();
		});
	});
function Reports(eid,trackcode,scode){	
	var url="TrackURL!trackURLReport?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
	window.location.href=url;
}

function Widget(eid,trackcode,scode){
	parent.loadingPopup();
	var url="TrackURL!addWidget?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	 $('#myModal').on('show.bs.modal', function () {				  	
		  $('#myModal .modal-title').html('<h3><span style="padding-left:10px">Widget</span></h3>');
		  $('iframe#popup').attr("src",url);
		      });		
		     
		   $('#myModal').modal('show');    
		        $('#myModal').on('hide.bs.modal', function () { 
		        $('iframe#popup').attr("src",'');			        
		        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:modalOnLoad()" frameborder="0"></iframe>');
		    });	
	}
	
	function closepopup(){
		$('#myModal').modal('hide');
	}
	var k=0;
	function save(){	
		++k;
		if(k==1)
			submitimporttrackurl();	
	}
	
	function checkAll(field){
		for (i = 0; i < field.length; i++)
			field[i].checked = true ;
		k=0;
		}
	function uncheckAll(field){
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;
		k=0;
		}
	
	
	$(document).ready(function(){
	if(json.citems.length>0)
    $('#trackdatatable').dataTable( {
        "sPaginationType": "full_numbers",
        "iDisplayLength":5,
        "bLengthChange": false,
        "aoColumns": [null
                    ],
                    "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
                    
                    }
            /*  "bFilter":false,
             aaSorting: [] */
  
    
	});
	 if(json.citems.length<=5)
		 removePagination('trackdatatable');
			/* $('#trackdatatable_paginate').hide(); */
	});
	function callDataTable(){			
		 $('#trackdatatable').dataTable( {
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "bLengthChange": false,
            "aoColumns": [null
                        ] ,
                        "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
                        
                        }
            /* "bFilter" : false,
            aaSorting: []  */
        } );
	}
	
	function showProcessing(divid){
	    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
	    $('#'+divid).after(html);
	}

	function hideProcessing(){
	    $('#loading').remove();
	}
	

	function triggerCopy(){
		$("[id$='_copyLink']").zclip({
			path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
	        copy:function(){ return $(this).data('link');}
		});
		$("[id$='_copyLink']").trigger('mouseenter');
	}
	function triggerCopyManage(){
		$("[id$='_copyMLink']").zclip({
			path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
	        copy:function(){ return $(this).data('link');}
		});
		$("[id$='_copyMLink']").trigger('mouseenter');
	}
	function triggerCopyWidget(){
		$("[id$='_copyWLink']").zclip({
			path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
	        copy:function(){ return $(this).data('widget');}
		});
		$("[id$='_copyWLink']").trigger('mouseenter');
	}
	

	
	/* 
	$(document).on( "click", "[id$='_copyMLink']", function() {
		$("[id$='_copyMLink']").zclip({
	        path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
	        copy:function(){
	        	var index=$(this).data('index');
	        	return $(".copyMLink_"+index).text();}
	     });
  	});
	$(document).on( "click", "[id$='_copyWLink']", function() {
		$("[id$='_copyWLink']").zclip({
	        path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
	        copy:function(){
	        	var index=$(this).data('index');
	        	return $("#trackWidget_"+index).text();}
	     });
  	}); */
	
	
</script>

<style type="text/css" title="currentStyle">
@import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
            .popover-content {
   color:#000;
   font-size:0.9em;
}
           
        </style>
