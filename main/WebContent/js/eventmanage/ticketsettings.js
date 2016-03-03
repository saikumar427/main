$(document).ready(function(){ 
$('#seteventcap').on('click',function(){
		 if($('#showCapacity').is(':visible')){
			 $('#showCapacity').slideUp();
			 $('#evtCapacitySpin').removeClass('down').addClass('original');
		 }else{
			specialFee(eid,'200','eventcapacity','Ticketing');
			$('#onefieldtext').attr("placeholder", props.mt_enter_event_capacity);
		 }
	});

});
 
 function showEventCapacity(capacity){
		$('#existError').hide();
		 $('#showCapacity').slideDown();
		 $('#enteredValue').val(capacity);
		 $('#evtCapacitySpin').removeClass('original').addClass('down');
		 
	}

	function success(){
		capacitySuccess($('#enteredValue').val());
	}


	function closeCapacity(){
		$('#showCapacity').slideUp();
		 $('#evtCapacitySpin').addClass('original').removeClass('down');
	}
	
	var capacitySuccess = function(val){
		
//		$('#saveEventCapacity').
		  //$("#dest").val($.trim($("#dest").val()));
		var evtCapacity=$.trim(val);
		var tkttype='limited';
		$('#existError').css({"color":"red"});
		$('#existError').html('').show();
		if(Math.floor(evtCapacity) == evtCapacity && $.isNumeric(evtCapacity)) {
			if(evtCapacity=='0'){
				tkttype='unlimited';
				//$('#existError').html('Setting Event Capacity to Default');
				$('#existError').css({"color":"green"});
			}
		}else{
			if(evtCapacity!=''){
			$('#existError').html(props.mt_invalid_event_capacity);
			return;
			}else{
				tkttype='unlimited';
				evtCapacity='0';
				//$('#existError').html('Setting Event Capacity to Default');
				$('#existError').css({"color":"green"});
			}
		}
		
		$.ajax({
			url:'TicketDetails!saveEventCapacity?eid='+eid+'&tkttype='+tkttype+'&tktcount='+evtCapacity,
			success:function(result){
				 if(!isValidActionMessage(result)) return;
				if(result.indexOf('Invalid Number')>-1){
					$('#existError').show().html(props.mt_invalid_num_tkt_capacity);
				}else if(result.indexOf('success')>-1){
					 $('#showCapacity').slideUp();
					 /*if(tkttype=='limited')*/
					 notification(props.mt_event_capacity_successfully,'success');
					 /*else
					 notification('','success');*/	 
					 
					 $('#evtCapacitySpin').addClass('original').removeClass('down');
					if(evtCapacity=='' || evtCapacity=='0'){
					$('#eventcapacity').val('');
					//$('#capacity').html();
						eventlevelcount='';
					 $('#capacity').html(' '+totalTktCount);	 
					}else{	
					eventlevelcount=val;
					$('#eventcapacity').val(evtCapacity);
					$('#capacity').html(evtCapacity);
					}
					
				}
			}
			
			
		});
		
	};