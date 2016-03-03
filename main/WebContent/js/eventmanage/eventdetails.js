$(document).ready(function(){
	
	$(document).on('click','.toggle',function(){
		var tid=$(this).attr('id');
		if($('#background_'+tid).is(':hidden')){
			$('#background_'+tid).slideDown();
			$(this).find('.glyphicon').addClass('down').removeClass('original');
			$(this).find('.text').addClass('highlighted-text');
		}else{
			$('#background_'+tid).slideUp();
			$(this).find('.glyphicon').addClass('original').removeClass('down');
			$(this).find('.text').removeClass('highlighted-text');
		}
		
	});
	
	
	$(document).on('click','#viewalllink',function(){
		window.location.href='/main/eventmanage/Reports?eid='+eid+'&purpose=summary';
	});
	
});

function dropDownClick(){
	  if($("#eventOptions").is(':hidden'))
	 	 $("#eventOptions").show();
	  else
		  $("#eventOptions").hide();
}
function deleteEvent(eid){
	var url='Snapshot!chanageEventStatus?eid='+eid+'&purpose=CANCEL';
	   bootbox.confirm(props.sum_event_deleted_from_list_msg, function (result) {
       	if (result){
       		$.ajax({
       	 		url:url,
       	 		success:function(result){
       			  if(!isValidActionMessage(result)) return;		
       			  notification(props.sum_delete_event_success,'success');
       			   window.location.href="/main/myevents/home";
       	 		}
       		});
       	}
    });	
}

//[["tkt1",{"f":"tkt1",v:"2"}],["tkt2",{}],[]]

function drawNormalEventChart(){
	totalTicketSold=0;
	 chartData=new Array();
	$.each(jsonData.ticketssummary,function(key,value){
		var eachtktArray=new Array();
		eachtktArray.push(value.ticket_name);
		eachtktArray.push({"f":value.ticket_name+"  "+value.sold_qty+"/"+value.total_qty,"v":Number(value.sold_qty)});
		totalTicketSold+=Number(value.sold_qty);
		chartData.push(eachtktArray);
	});
	
	
	if(totalTicketSold>0){
		drawChart();
		 }else{
		$('#donutchart').html('');	 
		$('#donutdiv').remove();
		$('#donuttext').remove();
		$('#donutchart').append('<div style="position: relative;text-align:center;" id="donutdiv"><img src="/main/images/ticketsnosales.png"></img></div>'+
		 '<div style="position: relative; top: -257px;" id="donuttext"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;margin-left: -6px;">0</span><br><span style="font-size: 17px;" class="section-header"> '+props.spt_tickets_sold_lbl+'</span></div>');
		$('#donutdiv').css("margin","82px auto 48px");
		 }
}


 
 function getEventDetails(eventDate){
	 
	 
	 if(eventDate=='all'){
		 $('#recurevent').html(props.spt_from_all_dates);
		 if(powertype=='Ticketing')eventDate='';
	 }else
		 $('#recurevent').html(eventDate);
	 
		loadingPopup();
		dropDownClick();
		var url='';
	 
	 if(powertype=='Ticketing'){
		url='Snapshot!populateTicketJson?eid='+eid+'&eventDate='+eventDate;
		$.ajax({
		  url : url,
		  type: 'get',
		  error: function(){},
		  success: function(transport) {
			var result=transport;
			hidePopup();
			jsonData=JSON.parse(result);
			drawCharts();
	      }
	}); 
	 }else{
			url='EventDetails!getRSVPChartData?eid='+eid+'&eventDate='+eventDate;
			$.ajax({
			  url : url,
			  type: 'get',
			  error: function(){},
			  success: function(transport) {
				var result=transport;
				hidePopup();
				rsvpjsonData=JSON.parse(result);
				drawRSVPCharts();
		      }
		});
	 }
	}




function fillRecentTransactionData(){
	var word=props.spt_tkts_lbl;
	var transactions=props.spt_no_tids_lbl;
	if(powertype=='RSVP'){
		word=props.spt_response_lbl;
		transactions=props.spt_no_response_lbl;
	}

	var html='';
	if(Object.keys(recenttrnsactions.recenttransactions).length>0){
		
		var counts=Number(Object.keys(recenttrnsactions.recenttransactions).length);
		if(counts>=5)
			counts=5;
		
		var keyword=props.spt_transaction_lbl;
		
		if(powertype=='RSVP' && counts==1)
			keyword=props.spt_response_lbl;
		else if(powertype=='RSVP' && counts>1)
			keyword=props.spt_responses_lbl;
		else if(counts>1)
			keyword=props.spt_trans_lbl;
		
		$('#transactioncount').html(counts+' '+keyword);
		
		$.each(recenttrnsactions.recenttransactions,function(key,value){
			var i=0;
			var tktdata='<ul >';
			var tktcount=0;
			$.each(value.tickets,function(tktkey,tktvalue){	
				tktdata+='<li class="li_disc">';
				tktdata+=tktvalue+' - ';				
				
				
				if(tktkey=="Maybe")
					tktkey=props.sp_maybe_lbl;
				else if(tktkey=="Attending")
					tktkey=props.sp_attending_lbl;
				else if(tktkey=="Not Attending")
					tktkey=props.sp_not_attending_lbl;
				
				
				tktdata+=tktkey;				
				tktcount+=Number(tktvalue);
				tktdata+='</li>';
			});
			
			
			if(tktcount==1 && powertype!='RSVP')
				word=props.spt_single_tkts_lbl;
			else if(tktcount==1 && powertype=='RSVP')
				word=props.spt_single_response_lbl;
			else if(powertype=='RSVP')
				word=props.spt_response_lbl;
			else 
				 word=props.spt_tkts_lbl;
			
			
			tktdata+='</ul>';
			html='<tr class="temp"><td><div class="row"><div class="col-md-4">'+value.fname+' '+value.lname+'</div><div class="col-md-4 sm-font">'+value.tdate+'</div><div class="col-md-4 sm-font" ><lable class="toggle" style="cursor: pointer;" id="'+key+'"><span class="text">'+tktcount+' '+word+'<span><span style="cursor: pointer;  margin-top: 3px;"  class="arrow-gap glyphicon glyphicon-menu-right"></span></lable></div></div>';
			html+='<div style="clear:both"></div>';
			html+='<div class="background-options sm-font" style="display:none;" id="background_'+key+'">'+
			        '<span class="discounttkts"'+
    		      ' style="">'+tktdata+'</span>'+
			'</div></td></tr>';
			
			 $('#tktsales_body').append(html);
			// $('.temp').tooltipster({ content:tktdata,contentAsHTML:true,position: 'top-right',left:'850px',Width:800});
		});
		if(powertype!='RSVP')
		$('#viewall').html('<span class="sm-font"><a href="javascript:;" id="viewalllink">'+props.spt_view_all_lbl+'</a></span>');
	}else{
		$('#viewall').html('');
		html='<tr class="temp"><td><div class="not-found" id="noTicketsDiv" style="">'+transactions+'</div></td><td></td><td></td></tr>';
		$('#tktsales_body').html(html);
	}
}


function drawChart() {
	  var data = new google.visualization.DataTable();
      data.addColumn('string', 'a');
      data.addColumn('number', 'b');
      data.addRows(chartData);
	  var options = {
	    pieHole: 0.4,
	    hAxis: {textPosition: 'in', showTextEvery: 5, slantedText: false, text:'',textStyle: { color: '#FFFFFF', fontSize: 10 } },
	    backgroundColor: 'transparent',
	    'legend':'none',
	    'width':500,
	    'height':500,
	    'top':'80%',
	     pieSliceText: 'value',
	     allowHtml:true,
	     pieSliceBorderColor:'transparent'
	  };

	  var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
	  chart.draw(data, options);
	  
	  
	  var word=props.spt_multi_tkt_sold;
	  
	  var inttotalTicketSold=Number(totalTicketSold);
	  
	  if(inttotalTicketSold==1)
		  word=props.spt_single_tkt_sold;
	  
	  
	  if(powertype=='RSVP' && inttotalTicketSold==1)
		  word=props.spt_single_response;
	  else if(powertype=='RSVP')
		  word=props.spt_multiple_responses;
	  
	  $('#donutdiv').remove();
	  $('#donuttext').remove();
	 $('#donutchart').append('<div style="left: 201px; position: relative; text-align: center; top: -272px; width: 100px; z-index: 99;" id="donutdiv"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;">'+totalTicketSold+'</span><br><span style="font-size: 17px;" class="section-header">'+word+' </span></div>');
	}

function doStats(){
	 if(isrecurring==true){
			$('#eventOptions').append('<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="getEventDetails(\'all\');">'+props.spt_all_dates_lbl+'</a></li>')
			$.each(recenttrnsactions.dateList,function(key,value){
			var html='<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="getEventDetails(\''+value+'\');">'+value+'</a></li>';
			$('#eventOptions').append(html);
		});
		}
	 if(powertype=='RSVP')
	 drawRSVPCharts();
	 else
	 drawCharts();
}

function drawRSVPCharts(){
	 totalTicketSold=0;
	 chartData=new Array();
	 
	 $(rsvpjsonData).each(function(key,val){ 
	    	$.each(val,function(type,count){
	    	 var eachtktarr=new Array();
    		 var sales=new Object();
    		 totalTicketSold=totalTicketSold+parseInt(count);
    		 sales["f"]=type +' '+ count;
    		 sales["v"]=parseInt(count);
    		 eachtktarr.push(type);
    		 eachtktarr.push(sales);
    		 chartData.push(eachtktarr);
	    	});
	    });
	 if(totalTicketSold>0){
		   $("#deleteEvent").remove();
			drawChart();
			 }else{
				$("#deleteEvent").show();
				$('#donutchart').html('');	 
				$('#donutdiv').remove();
				$('#donuttext').remove();
				$('#donutchart').append('<div style="position: relative;text-align:center;" id="donutdiv"><img src="/main/images/ticketsnosales.png"></img></div>'+
				 '<div style="position: relative; top: -257px;" id="donuttext"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;margin-left: -6px;">0</span><br><span style="font-size: 17px;" class="section-header"> '+props.spt_responses_lbl+'</span></div>');
				$('#donutdiv').css("margin","82px auto 48px");
			 }
}




function drawCharts(){
	 totalTicketSold=0;
	 chartData=new Array();
	 $(jsonData.TicketDetails).each(function(key,dataObj){ 
	    	if(dataObj.type!='group' && dataObj.isdonation=='N'){
	    		 var eachtktarr=new Array();
	    		 var sales=new Object();
	    		 totalTicketSold=totalTicketSold+parseInt(dataObj.soldqty);
	    		 sales["f"]=dataObj.soldqty+"/"+dataObj.maxticket;
	    		 sales["v"]=parseInt(dataObj.soldqty);
	    		 eachtktarr.push(dataObj.name);
	    		 eachtktarr.push(sales);
	    		 chartData.push(eachtktarr);
	    	}
	    });
	 
	 	if(totalTicketSold>0){
		 		 $("#deleteEvent").remove();
				 drawChart();
			 }else{
				$("#deleteEvent").show();
				$('#donutchart').html('');	 
				$('#donutdiv').remove();
				$('#donuttext').remove();
				$('#donutchart').append('<div style="position: relative;text-align:center;" id="donutdiv"><img src="/main/images/ticketsnosales.png"></img></div>'+
				 '<div style="position: relative; top: -257px;" id="donuttext"><span id="pie_global" class="section-header" style="font-size: 20px; font-weight: 600;margin-left: -6px;">0</span><br><span style="font-size: 17px;" class="section-header"> '+props.zero_spt_tickets_sold_lbl+'</span></div>');
				$('#donutdiv').css("margin","82px auto 48px");
			 }
}

