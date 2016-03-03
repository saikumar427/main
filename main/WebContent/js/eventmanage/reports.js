var attendeedata='';
var salesdata='';
var checkindata='';
var filteronchange=false;
var actionType='';
var buyerProfileId='';
var attendeeProfileId='';
$(document).ready(function() {
	
	$(document).on('ifChecked','.srctype',function(){
		filterDataTable('tranreportcontent');
	});
	
	$(document).on('ifUnchecked','.srctype',function(){
		filterDataTable('tranreportcontent');
	});
	
	$.extend( $.fn.dataTableExt.oSort, {
	    "transaction-date-pre": function ( a ) {
	    	
	    	//console.log($(a).attr('data-calval'));
	        return $(a).attr('data-calval');
	    },
	    "transaction-date-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	    "transaction-date-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	$(document).on('click','.chkunchkbtn',function(){
		//alert($(this).attr('value')+' '+$(this).attr('data-tid')+' '+$(this).attr('data-pkey'));
		var tid=$(this).attr('data-tid');
		var pkey=$(this).attr('data-pkey');
		var chkstatus=$(this).attr('data-chkstatus');
		var index=$(this).attr('data-index');
		getCheckInStatus(tid,pkey,chkstatus,index);
	});
	
	$("#search_filter").focusout(function(){
		$('#search_filter').tooltipster('hide');
	});
	
 $('#search_filter').tooltipster({
	 trigger: 'custom',
        onlyOne: false,
        fixedWidth: '300px',
        position: 'bottom',
        theme: 'form-validate-theme',
        animation: 'grow'
    });

$('#attendeesubmitbtn').click(function(){
	$('#repsearchid').val(false);
	actionType='attendee';
	formaction('attendee');
});

$('#transactionsubmitbtn').click(function(){
	$('#repsearchid').val(false);
	actionType='registration';
	formaction('registration');
});

$('#checkinsubmitbtn').click(function(){
	$('#repsearchid').val(false);
	actionType='checkinreports';
	formaction('checkinreports');
});


$('#showattbtn').click(function(){
	$('input[name="reports"][value="Sales"]').prop("checked",true);
	toggleReports('Sales');
	showSalesRep();
	showPendingApproval();
});

/*$('#showregbtn').click(function(){
	showPendingApproval();
});*/




$('.attselect').click(function(){
	$('input[name="attFields"]').each(function(){
	   $(this).iCheck('check');
	});
	
});

$('.attclear').click(function(){
	$('input[name="attFields"]').each(function(){
		   $(this).iCheck('uncheck');
		});
});


$('.salesselect').click(function(){
	$('input[name="Fields"]').each(function(){
		   $(this).iCheck('check');
		});
	
});

$('.salesclear').click(function(){
	$('input[name="Fields"]').each(function(){
		   $(this).iCheck('uncheck');
		});
});


$('.checkselect').click(function(){
	$('input[name="checkinFields"]').each(function(){
		   $(this).iCheck('check');
		});
});

$('.checkclear').click(function(){
	$('input[name="checkinFields"]').each(function(){
		   $(this).iCheck('uncheck');
		});
});


$("#showhide").click(function(){
    $("#attenddeeadv1").slideToggle(function(){
    	var a=document.getElementById('attenddeeadv1').style.display;
        if(a=="none")
        $("#showhide").attr("src","../images/dn.gif");
        else
        $("#showhide").attr("src","../images/up.gif");
    }
    );    
});
var attexpand=false;
var attedeefields={"TP":1,"BE":2,"Em":3,"Ph":4,"ON":5,"AK":6,"BN":7,"SC":8,"1":9,"2":10,"3":11,"4":12,"5":13,"6":14,"7":15,"8":16,"9":17,"10":18,"11":19,"12":20,"13":21,"14":22,"15":23,"16":24,"17":25,"18":26,"19":27,"20":28,"21":29,"22":30,"23":31,"24":32,"25":33,"26":34,"27":35,"28":36,"29":37,"30":38,"BP":39,"ED":40};
var salesexpand=false;
var salesfields={"FieldsFilter":1,"VTSC":2,"PID":3,"No":4,"EPID":5,"DC":6,"So":7,"TU":8,"Ph":9,"Em":10,"St":11,"ON":12,"1":13,"2":14,"3":15,"4":16,"5":17,"6":18,"7":19,"8":20,"9":21,"10":22,"11":23,"12":24,"13":25,"14":26,"15":27,"16":28,"17":29,"18":30,"19":31,"20":32,"ED":33};

var chkinfields={"SC":1,"ON":3,"SID":4,"CIT":5,"TT":7,"Em":8};
var chkinexpand=false;

$("#show").click(function(){
    $("#attenddeeadv2").slideToggle(function(){
    	var b=document.getElementById('attenddeeadv2').style.display;
        if(b=="none")
        { $("#show").attr("src","../images/dn.gif");
        attexpand=false;
        }
        else
        {  
        $("#show").attr("src","../images/up.gif");
        attexpand=true;
        }
    }
    );    
});


$("#salesimg1").click(function(){
    $("#salesadv1").slideToggle(function(){
    	var c=document.getElementById('salesadv1').style.display;
        if(c=="none")
        $("#salesimg1").attr("src","../images/dn.gif");
        else
        $("#salesimg1").attr("src","../images/up.gif");
    }
    );    
});

$("#salesimg2").click(function(){
    $("#salesadv2").slideToggle(function(){
    	var d=document.getElementById('salesadv2').style.display;
        if(d=="none"){
        $("#salesimg2").attr("src","../images/dn.gif");
        salesexpand=false;
        }else{
        $("#salesimg2").attr("src","../images/up.gif");
        salesexpand=true;
        }
    }
    );    
});

$("#checkinimg1").click(function(){
    $("#checkinadv1").slideToggle(function(){
    	var e=document.getElementById('checkinadv1').style.display;
        if(e=="none"){
        $("#checkinimg1").attr("src","../images/dn.gif");
        chkinexpand=false;
        }else{
        $("#checkinimg1").attr("src","../images/up.gif");
        chkinexpand=true;
        }
    }
    );    
});


var divOpen = false;


$(document).on('click', '.closeTransactionDetails', function() {
    divOpen = false;
    $(this).parents('tr').prev().removeClass('info');
    $(this).parents('tr').remove();
});

$(document).on('click','#cancelnotes',function(){
	 $("div.loadedData").remove();
});

});

var jdata="";
var reporttype="";


function closeProfile(){
	$('#'+buyerProfileId).show();
	$('#edit_'+buyerProfileId).hide();
	$('#'+attendeeProfileId).show();
	$('#edit_'+attendeeProfileId).hide();
	$("div.loadedData").remove();
}

function formaction(type){
	if(!checkDateSelect())
		return;
		
	removeHiddenEle();
	reporttype=type;
		var value=0;
	    var count=0;
	  
		if(type=='attendee'){
			$('#attendeetid').iCheck('check');
			options=document.reportsform.attFields;	 
		     for(i=0;i<options.length;i++){
		        	if(options[i].checked){
		     		value=options[i].value;
		     		count++;
		     	}
		     }

			reportsFromSubmit('Reports!getAttendeeInfo','attreportcontent');

		}else if(type=='registration'){
			$('#transtid').iCheck('check');
			var online = document.getElementById('onlineid').checked;
			var manual = document.getElementById('manualid').checked; 
			
			if(online || manual){
			}else{
				bootbox.confirm(props.rep_select_online_manual_errmsg,function(result){});
		    	return false;
			}
			options=document.reportsform.Fields;
		     for(i=0;i<options.length;i++){
		        	if(options[i].checked){
		     		value=options[i].value;
		     		count++;
		     	}
		     }
		     
			reportsFromSubmit('Reports!getTransactionInfo','tranreportcontent');
		}else{
			$('#checktid').iCheck('check');
			if(document.getElementById('chkstatus').checked)
				  $('#attkey').iCheck('check');
			
			options=document.reportsform.checkinFields;
		     for(i=0;i<options.length;i++){
		        	if(options[i].checked){
		     		value=options[i].value;
		     		count++;
		     	}
		     }
			reportsFromSubmit('Reports!getCheckedInReportsInfo','checkinreportcontent');
		}
	}

function checkDateSelect(){
	if(document.getElementById('recurringdate')){
		var index=document.getElementById('recurringdate').value;
		if(index==1){
			bootbox.confirm(props.global_pls_select_event_date_msg,function(result){});
			return false;
		}
	}
	return true;
}

function reportsFromSubmit(action,contentblock){
	
var loadhtml='<div id="loading" align="center" style="color:#747170;margin:81px 0 0 3px">'
			+'<table><tr><td align="center"><b>Processing...</b></td></tr>'+
	'<tr><td align="center"><img src="../images/loading.gif"></td></tr></table></div>';
	loadhtml='<center><img src="../images/ajax-loader.gif"></center>';
	

 $('#'+contentblock).html(loadhtml);
 
document.reportsform.action = action;

$.ajax({
	  type:'get',
	  data:$('#reportsform').serialize(),
	  url:action,
	  success:function(result){
		  if(!isValidActionMessage(result)) return;
		  var data=eval('('+result+')');
		  if(contentblock=='attreportcontent') attendeedata=data;
		  if(contentblock=='tranreportcontent') salesdata=data;
		  if(contentblock=='checkinreportcontent') checkindata=data;
		  getDataTable(data,contentblock);
	  }
});
}

var attrepsortfields=[];
var	salrespsortfields=[];
var chkrepsortfields=[];
var ab="";
var shortname = [];
var reptype="";
var totalFields=['CCPF','TTC','ESF','ECCF','NTSC','Bal'];
var indexValues=[];
var rows='';
var summCols=new Object();
summCols["TTC"]="yes";
summCols["TNet"]="yes";
summCols["CCPF"]="yes";
summCols["ESF"]="yes";
summCols["ECCF"]="yes";
summCols["NTSC"]="yes";
summCols["Bal"]="yes";
summCols["OCCF"]="yes";

var currecyFields=new Object();
currecyFields["TP"]="yes";
currecyFields["SF"]="yes";
currecyFields["TTC"]="yes";
currecyFields["TNet"]="yes";
currecyFields["CCPF"]="yes";
currecyFields["CSF"]="yes";
currecyFields["NTSC"]="yes";
currecyFields["ESF"]="yes";
currecyFields["ECCF"]="yes";
currecyFields["Bal"]="yes";
currecyFields["OCCF"]="yes";
currecyFields["RA"]="yes";

var i18nMapping={"Completed":props.trans_completed_status_lbl,"Pending Approval":props.trans_pending_approval_status_lbl,"Refunded":props.trans_refunded_status_lbl,
		"Chargeback":props.trans_chback_status_lbl,"Deleted":props.trans_deleted_status_lbl,"Main URL":props.rep_main_url_lbl,"Other":props.trans_other_paymentmethod_lbl,
		"Other - Pending Approval":props.trans_other_pending_paymentmethod_lbl,"No Payment":props.trans_no_payment_status_lbl,
		"Other - Approved":props.trans_other_approved_paymentmethod_lbl,"Track URL":props.rep_track_url_lbl};

function filterDataTable(contentblock){
	if(contentblock=='attreportcontent'){ 
		if(attendeedata=='') return;
		getDataTable(attendeedata,contentblock);
	}if(contentblock=='tranreportcontent'){
		if(salesdata=='') return;
		getDataTable(salesdata,contentblock);
	}if(contentblock=='checkinreportcontent'){
		if(checkindata=='') return;
		getDataTable(checkindata,contentblock);
	}
}

function getDataTable(jdata,divid){
	reptype=divid;
	var params = [];
	shortname = [];
	var test=jdata;
    var data=test;
    if(test.glbres!=undefined){
  
    var fields=test.fields;
     var attreports=test.attreports;
     var tempattreports="";
     for(var i=0;i<attreports.length;i++)
     {  for(var j=0;j<test.fields.length;j++)
        {   var filed=fields[j];
          if(divid=='tranreportcontent')
           filed=fields[j].key;       
            if(attreports[i][filed]==undefined)
               attreports[i][filed]="";
            else{
                 var val=attreports[i][filed];
                 if(test.glbres[val]!=undefined)
                 attreports[i][filed]=test.glbres[val];      
                 }
          }
      }
      test.attreports=attreports;
    }
    
    var content='';
    var reccount=0;
    $.each(data,function(outkey,resObj){	
    	
    	if(outkey=="coldefs" && data['attreports'].length>0){
    		
    		 //content+="<table><tr><td style='padding-left:28px'>Export To: <a href='javascript:reportexport(\"excel\")'>Excel</a>&nbsp;|&nbsp;<a href='javascript:reportexport(\"csv\")'>CSV</a></td></tr></table>";
    		 content+='<table class="table table-hover" id="results_'+divid+'" border="0" style="margin-bottom:0px !important;"> ';
			 	  content+='<thead>';
			 	 rows='<tfoot><tr id="tablefooter">';
			 	 $.each( resObj, function( inkey, valueobj ) {	
						var mul = 10*valueobj.label.length;
						var thLabel=valueobj.label;
						if(currecyFields[valueobj.key]=="yes"){
							thLabel=thLabel+'&nbsp;('+currencySymbol+')';
						}
						
						var flag=totalFields.indexOf(valueobj.key)>-1;
						if(flag)
						 content+= '<th class="sum"><div style="width:'+mul+'px;">'+thLabel+'</div></th>';	
						else{
							
							if(valueobj.sortable=="true")
								content+= '<th style="cursor: pointer;"><div style="width:'+mul+'px;">'+thLabel+'</div></th>';
							else
								content+= '<th><div style="width:'+mul+'px;">'+thLabel+'</div></th>';
						}
						shortname.push(valueobj.key);
						
						if(summCols[valueobj.key]=="yes"){
							//indexValues.push(inkey);
							indexValues.push({"key": inkey,"width":mul});
							
						}
						
						
						if(valueobj.sortable=="true"){ 
							//console.log(valueobj.key);
							if(valueobj.key=='TD')
								params.push({"sType": "transaction-date"});
							else params.push(null);
							
							if(divid=='attreportcontent')
								attrepsortfields.push(valueobj.key);
							else if(divid=='tranreportcontent')
								salrespsortfields.push(valueobj.key);
							else
								chkrepsortfields.push(valueobj.key);
						}
						else params.push({ "bSortable": false });
						
							 if(inkey==0)
		  			  				rows+='<th><div style="width:'+mul+'px;">'+props.rep_total_lbl+':</div></th>';
		  			  			else
		  			  				rows+='<th><div style="width:'+mul+'px;"></div></th>';
					});		
					
					  content+='<th></th></thead><tbody>';	
					  rows+='<th></th></tr></tfoot>';
					  params.push({ "bSortable": false });
    	}
    	
    	 if(outkey=='attreports')	{
    		 var noneFound=false;
    		 if(resObj.length>0){
    			 
    			 if(divid=='attreportcontent'){
    				var tx_type=$("#attendee_tx_type").val();
    				var tkt_type=$("#ticket").val();
    				
    				 if(tx_type!=1){
	    				 resObj=resObj.filter(function( obj ){
	    					  return obj.BKS == tx_type;
	    				 });
    				 } 
    				 
    				 if(tkt_type==2){
    					 resObj=resObj.filter(function( obj ){
	    					  return obj.ATT == tkt_type;
	    				 });
    				 }else if(tkt_type!=1){
    					 resObj=resObj.filter(function( obj ){
	    					  return obj.TKID == tkt_type;
	    				 });
    				 }
    				 reccount=resObj.length;
    		 $.each(resObj,function(inkey1,valueObj1){
    			 content+='<tr id="reports_'+valueObj1.TID+'">';	
    			 $.each(data.fields, function( index, value ) {
		  			  var temp = valueObj1[value+''];
		  			  var tid=valueObj1.TID;
		  			  var paymentstatus=valueObj1.ST;
		  			  
		  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
		  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
		  			if(data.glbres[paymentstatus+'']!=undefined)paymentstatus=data.glbres[paymentstatus+''];
		  			
		  			var fieldData='';
   		  			if(data.glbres[temp+'']!=undefined) fieldData=data.glbres[temp+''];
   		  			else if(temp!=undefined) fieldData=temp;
   		  			
   		  			if(fieldData!=''){
   		  				if(value=='TD'){
   		  					content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+fieldData+'>'+getDateFormat(fieldData)+'</a></td>';
   		  				}else {
   		  					var tempFieldData=fieldData;
   		  					tempFieldData=html2text(tempFieldData);
   		  					content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+tempFieldData+'>'+fieldData+'</a></td>';
   		  				}
   		  			}else 
			  				content+='<td></td>';
   		  			
   		  			if(data.fields.length-1==index){
	  					content+='<td><div style="position: relative;"><ul style="display:none; margin-left: -94px;" class="dropdown-menu" id="ul_'+tid+'">'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="transactiondetails">'+props.rep_manage_trans_details_lbl+'</a></li>';
	  					if(paymentstatus=='Completed' || paymentstatus=='Pending Approval')
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="changestatus" data-tid="'+tid+'">'+props.rep_manage_change_status_lbl+'</a></li>';
	  					
	  					 if(paymentstatus!='Cancelled' && paymentstatus!='Deleted')
	  					content+='<li><a role="menuitem" tabindex="-1" href="#" class="resendemail" data-tid="'+tid+'">'+props.rep_manage_resend_email_lbl+'</a></li>';
	  					 
	                    content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="refundtransaction" data-tid="'+tid+'">'+props.rep_manage_refund_trans_lbl+'</a></li>'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="ticketdetails">'+props.rep_manage_tkt_details_lbl+'</a></li>'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="buyerdetails" >'+props.rep_manage_buyer_details_lbl+'</a></li>'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="attendeedetails">'+props.rep_manage_attendee_details_lbl+'</a></li>'+
	  					'</ul><a style="cursor:pointer" class="manage" data-manage="'+tid+'">'+props.rep_manage_btn_lbl+'</a></div></td>';
   		  			}		  			
		  			  
					  });
    			 content+='</tr>';
    			 });
    		 		$("#exportDisplay").show();
    		 		$("#att_filters").show();
    			 }else if(divid=='tranreportcontent'){
    				 var tx_type=$("#sales_tx_type").val();
     				 var tkt_id=$("#transticket").val();
     				 var ptype=$("#pti").val();
     				 var transsrc=$("#transsource").val();
     				 if(transsrc!=1){
     					 
     					 if(transsrc=='direct'){
     						 resObj=resObj.filter(function( obj ){
     							 var src=obj.So;
     							 if(data.glbres[src+'']!=undefined) src=data.glbres[src+''];
     							 return src == 'Main URL';
     						 });
     					 }else if(transsrc=='alltrackcodes'){
     						 resObj=resObj.filter(function( obj ){
     							 var src=obj.So;
    							 if(data.glbres[src+'']!=undefined) src=data.glbres[src+''];
     							 return src == 'Track URL';
     						 });
     					 }else if(transsrc=='nts'){
     						resObj=resObj.filter(function( obj ){
     							var src=obj.So;
     							if(data.glbres[src+'']!=undefined) src=data.glbres[src+''];
    							 return src == 'NTS';
    						 });
     					 }else if(transsrc=='fbapp'){
     						resObj=resObj.filter(function( obj ){
     							var src=obj.So;
     							if(data.glbres[src+'']!=undefined) src=data.glbres[src+''];
     							return src == 'Facebook Application';
     						});
     					 }else{
     						 
     						resObj=resObj.filter(function( obj ){
     							var trackcode=obj.TU;
     							if(trackcode==undefined) return false;
     							if(data.glbres[trackcode+'']!=undefined) trackcode=data.glbres[trackcode+''];
     							return trackcode == transsrc;
     						});
     					 }
     				 }

     				 if(tx_type!=1){
 	    				 resObj=resObj.filter(function( obj ){
 	    					  return obj.St == tx_type;
 	    				 });
     				 } 
     				 
     				 if(tkt_id!=1){
     					 resObj=resObj.filter(function( obj ){
 	    					 return obj.TKTID.indexOf(tkt_id)>-1;
 	    				 });
     				 }
     				 
     				 if(ptype!=7){
     					 resObj=resObj.filter(function( obj ){
 	    					  return obj.PT == ptype;
 	    				 });
     				 }
     				
     				if(!$('#onlineid').is(':checked')){
     					resObj=resObj.filter(function( obj ){
	    					  return obj.BKS != 'online';
	    				 });
     				}
     				if(!$('#manualid').is(':checked')){
     					resObj=resObj.filter(function( obj ){
	    					  return obj.BKS != 'Manager';
	    			});
     				}
     				reccount=resObj.length;
    				 $.each(resObj,function(inkey1,valueObj1){
    					 var paymentstatus=valueObj1.St;
    					 content+='<tr id="reports_'+valueObj1.TID+'">';
    				 $.each( shortname, function( index, value ) {
		  				 var temp1 = valueObj1[value+''];
		  				//alert();
		  				 var tid=valueObj1.TID;
			  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
			  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
			  			
			  			var fieldData='';
	   		  			if(data.glbres[temp1+'']!=undefined) fieldData=data.glbres[temp1+''];
	   		  			else if(temp1!=undefined) fieldData=temp1;
	   		  			
	   		  			if(fieldData!=''){
	   		  			if(value=='TD'){
	   		  				content+='<td ><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+fieldData+'>'+getDateFormat(fieldData)+'</a></td>';
	   		  			}else{
	   		  				var fieldDataLabel=fieldData;
	   		  				if (fieldData in i18nMapping) fieldDataLabel=i18nMapping[fieldData];
	   		  				var tempFieldData=fieldData;
		  					tempFieldData=html2text(tempFieldData);
	   		  				content+='<td ><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+tempFieldData+'>'+fieldDataLabel+'</a></td>';
	   		  			}


	   		  			}else 
			  				content+='<td></td>';
	   		  			
	   		  			if(shortname.length-1==index){
	   		  				
	   		  				
	   		  				//content+='<td><a class="clickk '+tid+'" data-tid="'+tid+'">Manage</a></td>';
	   		  			content+='<td><div style="position: relative;">';
	   		  			
	   		  			
	   		  			if(paymentstatus!='Deleted'){
	   		  			content+='<ul style="display:none; margin-left: -94px;" class="dropdown-menu" id="ul_'+tid+'">'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="transactiondetails">'+props.rep_manage_trans_details_lbl+'</a></li>';
	  					if(paymentstatus!='Refunded' && paymentstatus!='Chargeback' && (paymentstatus=='Completed' || paymentstatus=='Pending Approval'))
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="changestatus" data-tid="'+tid+'">'+props.rep_manage_change_status_lbl+'</a></li>';
	  					
	  					if(paymentstatus!='Cancelled' && paymentstatus!='Refunded' && paymentstatus!='Chargeback')
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="resendemail" data-tid="'+tid+'">'+props.rep_manage_resend_email_lbl+'</a></li>';
	  					
	  					if(paymentstatus!='Refunded' && paymentstatus!='Chargeback')
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="refundtransaction" data-tid="'+tid+'">'+props.rep_manage_refund_trans_lbl+'</a></li>';
	  					
	  					if(paymentstatus!='Refunded' && paymentstatus!='Chargeback')
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="ticketdetails">'+props.rep_manage_tkt_details_lbl+'</a></li>';
	  					
	  					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="buyerdetails" >'+props.rep_manage_buyer_details_lbl+'</a></li>'+
	  					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="attendeedetails">'+props.rep_manage_attendee_details_lbl+'</a></li>'+
	  					'</ul>';
	   		  			}
	  					content+='<a style="cursor:pointer" class="manage" data-manage="'+tid+'">'+props.rep_manage_btn_lbl+'</a></div></td>';
	   		  			}
	   		  			});	
    				 content+='</tr>';
    				 });
    				 $("#salesExportDisplay").show();
    				 $("#salse_filters").show();
    			 }else {
    				 var tx_type=$("#checkin_tx_type").val();
     				
     				 if(tx_type!=1){
 	    				 resObj=resObj.filter(function( obj ){
 	    					  return obj.CIS == tx_type;
 	    				 });
     				 }
     				 
     				reccount=resObj.length;
    				 $.each(resObj,function(inkey1,valueObj1){
    					 content+='<tr id="reports_'+valueObj1.TID+'">';
    				 $.each(data.fields, function( index, value ) {
   		  			  var temp = valueObj1[value+''];
   		  			  var tid=valueObj1.TID;
   		  			  var pkey=valueObj1.AK;
   		  			  var paymentstatus=valueObj1.ST;
   		  			if(data.glbres[tid+'']!=undefined)tid=data.glbres[tid+''];	
   		  			if(data.glbres[eid+'']!=undefined)eid=data.glbres[eid+''];
   		  			if(data.glbres[paymentstatus+'']!=undefined)paymentstatus=data.glbres[paymentstatus+''];
   		  			var btnlbl=props.rep_checkin_btn_lbl;
   		  			   		  			
   		  			var fieldData='', fieldDataLbl='';
   		  			if(data.glbres[temp+'']!=undefined) fieldData=data.glbres[temp+''];
   		  			else if(temp!=undefined) fieldData=temp;
   		  			
   		  			if(fieldData!=''){

   		  				if(value=='CIS'){
   		  					if(fieldData=='Yes') {
   		  						btnlbl=props.rep_uncheck_btn_lbl;
   		  						fieldDataLbl=props.gllobal_yes_lbl;
   		  					}else if(fieldData=='No') fieldDataLbl=props.global_no_lbl;
   		  					else fieldDataLbl=fieldData;
			  				content+='<td><span id="cis_'+pkey+'">'+fieldDataLbl+'</span>&nbsp;<button id="cis_btn_'+pkey+'" data-tid="'+tid+'" data-pkey="'+pkey+'" data-index="'+inkey1+'" class="btn btn-xs btn-primary chkunchkbtn" data-chkstatus="'+fieldData+'"><span id="btn_lbl_'+pkey+'">'+btnlbl+'</span></button></td>';
			  			}else if(value=='TD'){
			  				content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+fieldData+'>'+getDateFormat(fieldData)+'</a></td>';
			  			}else
			  				content+='<td><a style="color:#333 !important;" class="'+tid+'" data-tid="'+tid+'" data-calval='+fieldData+'>'+fieldData+'</a></td>';
   		  				
   		  			}else content+='<td></td>';
   		  			
   		  			if(data.fields.length-1==index){
   		  			
   		  			//var paymentstatus='Completed';
		  				//content+='<td><a class="clickk '+tid+'" data-tid="'+tid+'">Manage</a></td>';
		  			content+='<td><div style="position: relative;"><ul style="display:none; margin-left: -94px;" class="dropdown-menu" id="ul_'+tid+'">'+
					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="transactiondetails">'+props.rep_manage_trans_details_lbl+'</a></li>';
					if(paymentstatus=='Completed' || paymentstatus=='Pending Approval')
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="changestatus" data-tid="'+tid+'">'+props.rep_manage_change_status_lbl+'</a></li>';
					
					content+='<li><a role="menuitem" tabindex="-1" href="#" class="resendemail" data-tid="'+tid+'">'+props.rep_manage_resend_email_lbl+'</a></li>'+
					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="refundtransaction" data-tid="'+tid+'">'+props.rep_manage_refund_trans_lbl+'</a></li>'+
					'<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="ticketdetails">'+props.rep_manage_tkt_details_lbl+'</a></li>';
					
					if(paymentstatus!='Deleted')
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="buyerdetails" >'+props.rep_manage_buyer_details_lbl+'</a></li>';
					
					content+='<li><a role="menuitem" tabindex="-1" href="javascript:;" class="clickk" data-tid="'+tid+'" data-type="attendeedetails">'+props.rep_manage_attendee_details_lbl+'</a></li>'+
					          '</ul><a style="cursor:pointer" class="manage" data-manage="'+tid+'">'+props.rep_manage_btn_lbl+'</a></div></td>';
   		  				
   		  				
   		  				//content+='<td><a class="clickk '+tid+'" data-tid="'+tid+'">Manage</a></td>';
   		  			}  		  			  });
    				 content+='</tr>';
    				 });
    				 $("#chkinExportDisplay").show();
    				 $("#check_filters").show();
    			 }
    			 //content+='</tr>';
    		 //});
    		 if(divid=='tranreportcontent'){
    			 if(reccount>0 && indexValues.length>0)
    				 content+='</tbody>'+rows+'</table>';
    			 else content+='</tbody></table>';
    		 }else
    			 content+='</tbody></table>';
    			/*'<nav><ul class="pagination"><li><a href="#" aria-label="Previous"><span aria-hidden="true"><<</span></a></li>'+
    			'<li><a href="#">1</a></li><li><a href="#">2</a></li><li><a href="#">3</a></li><li><a href="#">4</a></li>'+
    			'<li><a href="#">5</a></li><li><a href="#" aria-label="Next"><span aria-hidden="true">>></span></a></li></ul></nav>';*/
    		 }
    	else{
    		noneFound=true;
    		var none_found_id='attnd_none_found';
    		if(divid=='attreportcontent') none_found_id='attnd_none_found';
    		else if(divid=='tranreportcontent') none_found_id='sales_none_found';
    		else none_found_id='checkin_none_found';
    		 content+='<div id="'+none_found_id+'" style="text-align:center"><b>'+props.global_none_found_msg+'</b></div>';
    	 }
    		 
    		 if(divid=='attreportcontent'){
    			 $('#attreportcontent').html(content);
    			 if(reccount==0) $("#exportDisplay").hide();
    			 if(noneFound){
    				 noneFound=false;
    				 $('html,body').animate({
     			        scrollTop: $("#attnd_none_found").offset().top-180
    				 }, 1000);
    			 }else{
    				 $('html,body').animate({
	    			        scrollTop: $("#att_filters").offset().top-120
	    			 }, 1000);
    			 }
    		 }else if(divid=='tranreportcontent'){
    			 $('#tranreportcontent').html(content);
    			 if(reccount==0) $("#salesExportDisplay").hide();
    			 if(noneFound){
    				 noneFound=false;
    				 $('html,body').animate({
     			        scrollTop: $("#sales_none_found").offset().top-180
    				 }, 1000);
    			 }else{
    				 $('html,body').animate({
	    			        scrollTop: $("#salse_filters").offset().top-120
	    			 }, 1000);
    			 }
    		 }else{
    			 $('#checkinreportcontent').html(content);
    			 if(reccount==0) $("#chkinExportDisplay").hide();
    			 if(noneFound){
    				 noneFound=false;
    				 $('html,body').animate({
     			        scrollTop: $("#checkin_none_found").offset().top-180
    				 }, 1000);
    			 }else{
    				 $('html,body').animate({
	    			        scrollTop: $("#check_filters").offset().top-120
	    			 }, 1000);
    			 }
    		 }
    	 }
    });
    sortTable(params,divid,reccount);
}


function getCheckInStatus(tid,pkey,chkstatus,index){
	var val="Yes";
	var label="Uncheck";
	if(chkstatus=='Yes'){
		val="No";
		label="Check In";
		bootbox.confirm(props.rep_sure_to_uncheck_errmsg,function(result){
			if(result)
				updateCheckInStatus(tid,pkey,chkstatus,val,label,index);
		});
	}else{
		updateCheckInStatus(tid,pkey,chkstatus,val,label,index);
	}

}

function updateCheckInStatus(tid,pkey,chkstatus,val,label,index){
	var url='TransactionDetails!changeCheckInStatus?eid='+eid+'&tid='+tid+'&profilekey='+pkey+'&checkInStatus='+chkstatus;
	$.ajax({
		type:'get',
		url:url,
		error: function(){alert("System cant process at this time");},
		success:function(result){
			if(!isValidActionMessage(result)) return;
			var localVal=val, localBtnLbl=label;
			if(localVal=='Yes'){
				localVal=props.gllobal_yes_lbl;
				localBtnLbl=props.rep_uncheck_btn_lbl;
			}else if(localVal=='No') {
				localVal=props.global_no_lbl;
				localBtnLbl=props.rep_checkin_btn_lbl;
			}
			
			$("#cis_"+pkey).html(localVal);
			$("#btn_lbl_"+pkey).html(localBtnLbl);
			$("#cis_btn_"+pkey).attr("data-chkstatus",val);
			//checkindata.attreports[index].CIS=val;
			
			$.each(checkindata.attreports, function(idx, obj) {
				if(obj.AK==pkey){ 
					checkindata.attreports[idx].CIS=val;
					return false;
				}
			});
			var checkedIn=checkindata.attreports.filter(function( obj ){
				  return obj.CIS == 'Yes';
			 });
			$("#totalchckns").html(checkedIn.length);
		}
	});
}

var attrepdTable;
var chkrepdTable;
var salrepdTable;

function sortTable(params,divid,reccount){	 
	if(divid=='tranreportcontent'){
		salrepdTable=$('#results_'+divid).dataTableReports( {
			"sPaginationType": "full_numbers",
			"iDisplayLength":10,
		    "bLengthChange": false,
		    "bFilter": false,   
		    "scrollX": true,
		    "aoColumns": params ,
		    "order": [[ 0, "desc" ]],
		    "footerCallback": function ( row, data, start, end, display ) {
		    	if(reccount==0) return;
		        var api = this.api();
		        // Remove the formatting to get integer data for summation
		        var intVal = function ( i ) {
		            return typeof i === 'string' ?
		                i.replace(/[\$,]/g, '')*1 :
		                typeof i === 'number' ?
		                    i : 0;
		        };
		        
		        var formatCurrency=function(num) {
		            num = isNaN(num) || num === '' || num === null ? 0.00 : num;
		            return parseFloat(num).toFixed(2);
		        };
		        
		        $.each(indexValues, function(i,value) {
		        	var total;
		        	if(data.length==1){
		           		var b=0;
		        		total = api.column(value.key).data().reduce( function (a,b){
		        			return a;
		        		}); 
		        			if(isNaN(total))
		        			total= $(total).data('calval');
		        	}else{
		        	
		        	total = api.column(value.key).data().reduce( function (a, b) {
		            	var tempa=a;
		            	if(isNaN(a))
		            	tempa=$(a).data('calval');
		            	var tempb=$(b).data('calval');
		                return intVal(tempa) + intVal(tempb);
		            } );
		        	}
		        	 $( api.column(value.key).footer() ).html(' <div style="width:'+value.width+'px;">'+formatCurrency(total)+'</div>');
		        	 indexValues=[];
 		  			  });
		    }
		});
		
	}else{
		var dTable=$('#results_'+divid).dataTableReports({
	    "sPaginationType": "full_numbers",
	    "iDisplayLength":10,
	    "bLengthChange": false,
	        "bFilter": false,
	        "scrollX": true,
	        "aoColumns": params ,
		    "order": [[ 0, "desc" ]]
		});
		//$('.dataTables_scrollBody').css("overflow","unset");
		if(divid=='checkinreportcontent')
			chkrepdTable=dTable;
		else
			attrepdTable=dTable;
	}
	
	if(reccount<=10)
        removePagination('results_'+divid);
	//dTable.fnSort( [ [0,'desc'] ] );
}


function reportexport(exporttype){
	
	var dir="asc";
	var colno='0';//JSON.stringify((dTable.fnSettings().aaSorting[0])[0]);
	var dirtype='desc';//JSON.stringify((dTable.fnSettings().aaSorting[0])[1]);
	
	var sortfield='';//shortname[colno];
	var formele=document.reportsform;
	
	if(reptype=='tranreportcontent'){
		colno=JSON.stringify((salrepdTable.fnSettings().aaSorting[0])[0]);
		dirtype=JSON.stringify((salrepdTable.fnSettings().aaSorting[0])[1]);
		sortfield=salrespsortfields[parseInt(colno,10)];
		formele.action="Reports!getTransactionInfo";
	}else if(reptype=='checkinreportcontent'){
		colno=JSON.stringify((chkrepdTable.fnSettings().aaSorting[0])[0]);
		dirtype=JSON.stringify((chkrepdTable.fnSettings().aaSorting[0])[1]);
		sortfield=chkrepsortfields[parseInt(colno,10)];
		formele.action='Reports!getCheckedInReportsInfo';
	}else{
		colno=JSON.stringify((attrepdTable.fnSettings().aaSorting[0])[0]);
		dirtype=JSON.stringify((attrepdTable.fnSettings().aaSorting[0])[1]);
		sortfield=attrepsortfields[parseInt(colno,10)];
	    formele.action="Reports!getAttendeeInfo";
	}
	if(dirtype.indexOf('desc')>-1) dir="desc";
	removeHiddenEle();
	var input=document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","export");
	input.setAttribute("id","exportreport");
	input.setAttribute("value","true");
	formele.appendChild(input);
	var input1=document.createElement("input");
	input1.setAttribute("type","hidden");
	input1.setAttribute("name","sortDirection");
	input1.setAttribute("id","sortdir");
	input1.setAttribute("value",dir);
	formele.appendChild(input1);
	var input2=document.createElement("input");
	input2.setAttribute("type","hidden");
	input2.setAttribute("name","sortField");
	input2.setAttribute("id","sortfield");
	input2.setAttribute("value",sortfield);
	formele.appendChild(input2);
	var input3=document.createElement("input");
	input3.setAttribute("type","hidden");
	input3.setAttribute("name","exporttype");
	input3.setAttribute("id","exptyp");
	input3.setAttribute("value",exporttype);
	formele.appendChild(input3);
	formele.submit();
}

function removeHiddenEle(){
	var a=document.getElementById("exportreport");
		if(a) document.reportsform.removeChild(a);
	var b=document.getElementById("sortdir");
		if(b) document.reportsform.removeChild(b);
	var c=document.getElementById("sortfield");
		if(c) document.reportsform.removeChild(c);
	var d=document.getElementById("exptyp");
		if(d) document.reportsform.removeChild(d);
}


function showPendingApproval(){
	//$('a[href="#tab2"]').tab('show');
	///$('input[name="questionsinfo"][value="'+selectedRadio+'"]').prop("checked",true);
	$('#onlineid').iCheck('check');
	$('#manualid').iCheck('check');
	document.getElementById('selpapid').selected=true;
	$("#transticket").val("1");
	$("#pti").val("7");
	$("#transsource").val("1");
	formaction('registration');
}

function showDetails(tid){
	loadingPopup();
	/*$("."+tid).each(function() {
		 $(this).trigger("click",function(){
			 $('.closeTransactionDetails').trigger("click");
	 });
		 return false;
    });*/
	
}

function getDate(){
	if(document.getElementById('recurringdate')){
		var index=document.getElementById('recurringdate').value;
			//pendingApproval start.
		
			if(index !=1 && pajson[index+"_needapcnt"] != undefined){
				//alert(pajson[index+"_needapcnt"]);
				var appcount=pajson[index+"_needapcnt"];
			if(appcount>0){
				$("#alldatespacount").hide();
				document.getElementById('showattdiv').style.display="block";
				if(pajson[index+"_needapcnt"] == 1)
					$("#attpacount").html(pajson[index+"_needapcnt"]+props.rep_one_trans_pending_approval_lbl);
				if(pajson[index+"_needapcnt"] > 1)
					$("#attpacount").html(pajson[index+"_needapcnt"]+props.rep_trans_pending_approval_lbl);
			}
			//$("#pendingaprow").css("margin-top", "47px");
			}else{
				//$("#pendingaprow").css("margin-top", "91px");
			}
			if(index == 1 || pajson[index+"_needapcnt"] == undefined){
				if(document.getElementById('showattdiv'))
					document.getElementById('showattdiv').style.display="none";
			}
			//pendingApproval end.
			
			if(index=='all'){
				$("#totalattnds").show();
				$("#eventdatetotalattnds").hide();
				$("#totalsals").show();
				$("#eventdatetotalsals").hide();
				$("#totalchckns").show();
				$("#eventdatetotalchckns").hide();
				//$("#attpacount").hide();
				//$("#alldatespacount").show();
			}else{
				
				if(pajson[index+"_atndtotal"] != undefined){
					$("#eventdatetotalattnds").html(pajson[index+"_atndtotal"]+'<br/><span class="sub-text">Attendees</span>');
				}else{
					$("#eventdatetotalattnds").html("0"+'<br/><span class="sub-text">Attendees</span>');
				}
				
				$("#totalattnds").hide();
				$("#eventdatetotalattnds").show();
				
				if(pajson[index+"_salestotal"] != undefined){
					$("#eventdatetotalsals").html(pajson[index+"_salestotal"]+'<br/><span class="sub-text">Recieved</span>');
				}else{
					$("#eventdatetotalsals").html("0"+'<br/><span class="sub-text">Recieved</span>');
				}
				$("#totalsals").hide();
				$("#eventdatetotalsals").show();
				if(pajson[index+"_chkcnt"] != undefined){
					$("#eventdatetotalchckns").html(pajson[index+"_chkcnt"]+'<br/><span class="sub-text">Checked in</span>');
				}else{
					$("#eventdatetotalchckns").html("0"+'<br/><span class="sub-text">Checked in</span>');
				}
				$("#totalchckns").hide();
				$("#eventdatetotalchckns").show();
			}
			
		}
	}

function validateEmail(email){
 var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
 if (reg.test(email)){
 return true; }
 else{
 return false;
 }
} 

function searchReports(){
	if ($("#attendeeDisFldsData").is(':visible'))
		$(".attnddisplayflds").slideToggle(800);
	if ($("#salesDisFldsData").is(':visible'))
		$(".salesdisplayflds").slideToggle(800);
	if ($("#checkInDisFldsData").is(':visible'))
		$(".chkindisplayflds").slideToggle(800);
	
	var searchcontent=$('input[name="reportsData.searchContent"]').val();
	searchcontent=searchcontent.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	
	if($("#searchlist").val()=="email"){

		var email = searchcontent;
		 if ($.trim(email).length == 0) {
			 $("#search_filter").focus();
			 $('#search_filter').tooltipster('content', props.rep_email_is_empty_errmsg);
			 $('#search_filter').tooltipster('show');
			 return false;
		 }
		 if (!validateEmail(email)) {
			 $('#search_filter').tooltipster('content', props.rep_invalid_email_errmsg);
			 $('#search_filter').tooltipster('show');
			 return false;
		 } 
	
	}else if(searchcontent==''){
		var alertmsg="";
		if($("#searchlist").val()=="transactionId")
			alertmsg=props.rep_trans_id_empty_errmsg;
		else if($("#searchlist").val()=="orderNumber")
			alertmsg=props.rep_order_number_is_empty_errmsg;
		else if($("#searchlist").val()=="name")
			alertmsg=props.rep_name_is_empty_errmsg;
		else alertmsg=props.rep_search_data_is_empty_errmsg;
		$("#search_filter").focus();
		$('#search_filter').tooltipster('content', alertmsg);
		$('#search_filter').tooltipster('show');
		return false;
	}
	$('#search_filter').tooltipster('hide');
	$('input[name="reportsData.searchContent"]').val(searchcontent);
	  
	$('#repsearchid').val(true);
	if ($("#attendeeRepBlock").is(':visible')) {
		$("#attendee_tx_type").val("1");
		$("#ticket").val("1");
		actionType='attendee';
		formaction('attendee');
	}else if ($("#salesRepBlock").is(':visible')) {
		$('#onlineid').iCheck('check');
		$('#manualid').iCheck('check');
		$("#sales_tx_type").val("1");
		$("#transticket").val("1");
		$("#pti").val("7");
		$("#transsource").val("1");
		actionType='registration';
		formaction('registration');
	}else{
		$("#checkin_tx_type").val("1");
		actionType='checkinreports';
		formaction('checkinreports');
	}

}
