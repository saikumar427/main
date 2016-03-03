$(function(){
	  $('#ticketingForm input').tooltipster({
          trigger: 'custom',
          onlyOne: false,
          fixedWidth:'300px',
          position: 'top-right',
          theme:'form-validate-theme',
          animation:'grow'
      });
	  $('.info').tooltip({
          placement:'top',
          container:'body'
      });
      
   $('#ticketingForm').validate({
        errorPlacement: function (error, element) {	 	        	
            var lastError = $(element).data('lastError'),
                newError = $(error).text();	            
                $(element).data('lastError', newError);
                            
            if(newError !== '' && newError !== lastError){
                $(element).tooltipster('content', newError);
                $(element).tooltipster('show');
            }
        },
	        success: function (label, element) {
	            $(element).tooltipster('hide');
	        },
	        rules: {
	        	'ticketData.ticketName': {required: true},
	        	 'ticketData.ticketPrice':{
	        		 required:true,
	        		 number:true,
                     min:0	 
	        	 },
	        	 'ticketData.totalTicketCount':{
	        		 required:true,
	        		 number:true,
                     min:0	 
	        	 }
	       
	        },
	        messages: {
	        	'ticketData.ticketName': "Please enter ticket name",
				 'ticketData.totalTicketCount':{
								 required:"Please enter tickets available conut",
				                 number:"Invalid Ttickets available count",
				                 min:"Tickets Available  cannot be negative"},
             'ticketData.ticketPrice':{
				 required:"Please enter ticket price",
                 number:"Invalid ticket price",
                 min:"Ticket Price cannot be negative"}
			},
	        submitHandler: function (form) {
	        	$('.btn-block').hide();
	     
	        
	        
	        $.ajax( {
	        	   type: "POST",
	        	   url: 'TicketDetails!save',
	        	   data: $("#ticketingForm").serialize(),
	        	   success: function( t ) {
	        		   $('.btn-block').show();
	        		   if(!isValidActionMessage(t)) return;
	        	       if(t.indexOf('error')>-1){   	    
	        	           $('#ticketformerrormessages').show();
	        	           $(document).scrollTop('0');
	        	           $('#ticketformerrormessages').html(t);
	        	           parent.resizeIframe();
	        	           submitcount=0;
	        	           return;
	        	        }else{
	        	        	var tktid=$('#ticketid').val();
	        	        	var tktname=$('#ticketData_ticketName').val();
	        	        	var tktprice=$('#ticketprice').val();
	        	        	var tktavailable=$('#tktavailid').val();
	        	        	
	        	        	
	        	        	
	        	        	
	        	              var soldqty=parent.$('#'+tktid).children('span').children('span.soldqty').html();
	        	              var beforeprice=parent.$('#'+tktid).children('span').children('span.tktprice').html();
	        	              parent.$('#'+tktid).attr('data-quantity',tktavailable);
	        	              parent.$('#'+tktid).attr('data-ticketprice',tktprice);
	        	              parent.$('#'+tktid).attr('data-ticketname',tktname);
	        	           
	        	              var soldqtysubstr = soldqty.split('of');
	        	              var pricestr=beforeprice.split(' ');
	        	              
	        	              parent.$('#'+tktid).children('span').children('span.soldqty').html(soldqtysubstr[0]+' of '+tktavailable+' sold');
	        	              parent.$('#'+tktid).children('span').children('span.tktprice').html(pricestr[0]+' '+tktprice);
	        	              var span= parent.$('#'+tktid).children('span').html();
	        	             
	        	               var html='<i class="fa fa-ticket dragHandle"></i>&nbsp;'+tktname+'<span class="pull-right">'+span+'</span>';
	        	               
	        	               parent.$('#'+tktid).html(html);
	        	               parent.$('#myModal').modal('hide');
	        	        }
	        	       }
	        	   } );
	        }
	    });
   
   
	var isValidActionMessage=function(messageText){		
		if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
		    	alert('You need to login to perform this action');
		    	window.location.href="../user/login";
		    	return false;
		    }
		 else if(messageText.indexOf('Something went wrong')>-1){
		 		alert('Sorry! You do not have permission to perform this action');
		 		return false;
		 	} 
		    return true;	
		};
		  $('#cancel').click(function(){
              parent.closepopup();
          });
 
	
}	);
