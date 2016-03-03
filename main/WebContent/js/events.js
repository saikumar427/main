$(function(){
	$.each(data,function(outkey,resObj){
		 var tab=outkey=="active"?"tab1":"tab2";
		$('.'+tab+'Count').text(resObj.length);
		if(resObj.length==0) $('#'+tab+' .table .nodata').html('<td></td><td colspan="2" >No data</td> ');
		$.each( resObj, function( inkey, valueobj ) {				
			$('#'+tab+' .table .nodata').remove();
			var tempentry=$('<tr><td   class="eventmng each-row" data-eventid="'+valueobj.id+'"  data-mgrid="'+valueobj.mgrid+'"><div ><div  style="float:left;width:80%"><span style="color:#428bca">'+valueobj.title+'</span><br><span class="sub-text">'+removePresentYear(valueobj.startdate)+', '+convertTime24to12(valueobj.start_time)+'</span></div><span  class="i-right-link pull-right"><a href="javascript:;"><span class="glyphicon  glyphicon-menu-right hidden-eb" ></span></a></span><div style="clear:both"></div></div></td></tr>');
            var tempentry2=$('<tr><td class="eventmng each-row" data-eventid="'+valueobj.id+'"  data-mgrid="'+valueobj.mgrid+'"><div ><div  style="float:left;width:80%"><span  style="color:#428bca" >'+valueobj.title+' </span><br><span class="sub-text">'+removePresentYear(valueobj.startdate)+', '+convertTime24to12(valueobj.start_time)+'</span></div><span  class="i-right-link pull-right"><span class="status">'+getStatusFormat(outkey)+'&nbsp;&nbsp;&nbsp;</span ><a href="javascript:;"><span class="glyphicon glyphicon-menu-right hidden-eb" ></span></a></span><div style="clear:both"></div></div></td></tr>');
   			$('#'+tab+' .table .nodata').remove();
   			if(tab=="tab1")    
        	 $('#tab1 .table').append(tempentry);                    	
		    
         $('#tab2 .table').append(tempentry2);
         $('#tab2 .table .nodata').remove();  
			
		});
	});
	
	
	 prepareActiveEventsTable();
     prepareAllEventsTable();
     
     
     $('#tab2 .table thead').remove();
     $('#tab1 .table thead').remove();
      	
      	
      	if(data.active.length<=5)
      		removePagination('tabl1');  
         	if((data.closed.length+data.suspended.length+data.active.length)<=5)
         		removePagination('tabl2'); 
	
	
	$('#tab1 .table tr ,#tab2 .table tr ').hover(function() {
		$(this).prev().find('td  div  .dummy-row-div').css('border-bottom','0px');
		    $(this).find('td').find('.glyphicon').addClass('visible-eb').removeClass('hidden-eb');
		},function() {
			$(this).find('td').find('.glyphicon').addClass('hidden-eb').removeClass('visible-eb');
			$(this).prev().find('td  div .dummy-row-div').css('border-bottom','1px solid #f4f4f4');
		});
	
	
	$("#tab1 .dataTables_filter span, #tab2  .dataTables_filter span").click(function(){
	  	$(".dataTables_filter").toggleClass("open closeClass");
	  	$(".dataTables_filter").find("[name='search']").val('').focus().keyup();
});
	
	
	
	$('.dataTables_filter input').attr('class','form-control');
	
	$('.tab-content').on('click','.eventmng',function(){
		
		var url='../eventmanage/Snapshot?eid='+$(this).data('eventid');
		window.location.href=url;
	});
});



function removePagination(id){
	  $('#'+id+'_paginate').hide();
		$('#'+id+'_info').hide();
		$('#'+id+'_br').remove();
		$('#'+id).addClass('no-margin-bottom');
		//$('#'+id+' tr:last-child td  div  .dummy-row-div').css('border-bottom',' 0px');
		
}
function prepareAllEventsTable(){
	if(data.closed.length>0 || data.suspended.length>0 || data.active.length>0){
        dTable=$('#tabl2').dataTable( {
         "sPaginationType": "full_numbers",
         "aaSorting": [],
         "iDisplayLength":5,
         "bLengthChange": false,
         "aoColumns": [null
                     ] ,
         "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
         $('.dataTables_filter').addClass('searchResponsive'); 
         }                    
     } );           
 	}
}

function prepareActiveEventsTable(){
    if(data.active.length>0){
		//alert(data.active.length);
         dTable=$('#tabl1').dataTable( {
            "sPaginationType": "full_numbers",
            "iDisplayLength":5,
            "aaSorting": [],
            "bLengthChange": false,
            "aoColumns": [null
                        ] ,
                        "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
                        $('.dataTables_filter').addClass('searchResponsive'); 
                        $(".dataTables_filter").addClass("closeClass");
                        }
        } );
    	}
}

