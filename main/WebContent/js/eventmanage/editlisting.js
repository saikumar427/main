var formOnLoadData="";
var prevSaveRecDates={};
$(document).ready(function(){
	
	//var submitcount=0;
    $('.nonrecurringtimes #start').datetimepicker({
        //mask:true,
        format:'m/d/Y g:i A',
        formatTime:'g:i A',
        onSelectTime:function(dp,$input){
            $('.nonrecurringtimes #end').datetimepicker('show');
        }
    });
    $('.nonrecurringtimes #end').datetimepicker({
        format:'m/d/Y g:i A',
        formatTime:'g:i A'
    });
    $('.recurringtimes #fromtime').datetimepicker({
        format:'g:i A',
        formatTime:'g:i A',
        datepicker:false,
        onChangeDateTime:function(dp,$input){
            $('.recurringtimes #totime').datetimepicker('show');
        }
    });
    $('.recurringtimes #totime').datetimepicker({
        format:'g:i A',
        formatTime:'g:i A',
        datepicker:false,
        onChangeDateTime:function(dp,$input){
            $('.recurringtimes #date').datetimepicker('show');
        }
    });
	
	 $('.recurringtimes #date').datetimepicker({
	        format:'m/d/Y',
	        timepicker:false,
	        inline:true,
	        step:5,
	        onSelectDate:function(dp,$input) {
	            
	            var weekday=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
	            var shrtweekday= ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	            var shrmonths=['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec'];
	            var date = $('#date').val();
	            var fromtime = $('#fromtime').val();
	            var totime = $('#totime').val();
	            var stamp = date+' '+fromtime+' '+totime;
	           
	            var datearr = date.split('/');
	            var d = new Date(datearr[2],datearr[0]-1,datearr[1]);
	            
	            var key=datearr[2]+'-'+datearr[0]+'-'+datearr[1]+'/'+fromtime.split(" ")[0]+'/'+fromtime+'/'+datearr[2]+'-'+datearr[0]+'-'+datearr[1]+'/'+totime.split(" ")[0]+'/'+totime;
	        
	            var val=d.getDate()+" "+shrmonths[d.getMonth()]+" "+d.getFullYear()+" "+fromtime+"-"+totime+" "+"("+shrtweekday[d.getDay()]+")" ;
	            
	            var html ='';
	            datedisplayobj={};
				datekeyarray=[];    
				 html = '<tr  data-stampval="'+val+'" data-stampkey="'+key+'"  data-stamp="'+stamp+'">'+
                 '<td>'+val+'</td>'+                                
                 '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
                 '</tr>';
				datedisplayobj[val]=html;
	    		datekeyarray.push(val);
	    		
	    		if($('#recurringdates tr').length>0){
                	$('.table #recurringdates tr').each(function() {
                	         html = '<tr  data-stampval="'+$(this).data('stampval')+'" data-stampkey="'+$(this).data('stampkey')+'"  data-stamp="'+$(this).data('stamp')+'">'+
                             '<td>'+$(this).data('stampval')+'</td>'+                                
                             '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
                             '</tr>';
                	         datedisplayobj[$(this).data('stampval')]=html;
                 			 datekeyarray.push($(this).data('stampval'));
                	 
                	  });
                }
	    		
	    		/*var sorted = datekeyarray.sort(function( a, b ) {
    				 var tempa=new Date(a.split('-')[0]).getTime();
    				 var tempb=new Date(b.split('-')[0]).getTime();
                     var order = "ASC";
                     if( order == "ASC" ) {
                             return tempa>tempb;
                     } else {
                             return tempb > tempa;
                     } 
                     
             });
	    	 datekeyarray=sorted;
   			 sorted=[];*/
	    		
	    		var tempAryByTime=[];
	    		var tempJsonByTime={};
	    		for(var i=0;i<datekeyarray.length;i++){
	    			tempAryByTime.push(new Date(datekeyarray[i].split('-')[0]).getTime());
	    			tempJsonByTime[new Date(datekeyarray[i].split('-')[0]).getTime()]=datekeyarray[i];
	    		}
	    		
	    		var tempSortedAryByTime=tempAryByTime.sort(function(a, b){return a-b});
	    		
	    		datekeyarray=[];
	    		for(var i=0;i<tempSortedAryByTime.length;i++){
	    			datekeyarray.push(tempJsonByTime[tempSortedAryByTime[i]]);
	    		}
   			 
	            if($.inArray(stamp,getrecurringdates())==-1) {
	            	$('#recheight').attr('style','border:1px solid #ccc');
	            	$('#recheight').tooltipster('hide');
	                $('#recurringdates').html("");
	                $.each(datekeyarray,function(index,val){
	                $('#recurringdates').append(datedisplayobj[datekeyarray[index]]);
	                });
	               
	            } else {
	                $('.recurringtimes #date').datetimepicker('hide');
	            }
	            
	            $('.removedate').tooltipster({
	                theme: 'my-custom-theme',
	                fixedWidth:'300px',
	                position:'right',
	                trigger: 'click',
	                interactive:true,
	                animation:'grow',
	                functionInit: function(origin, content) {
	                    return $('<a onClick="removetr()" class="confirmremovedate">'+props.global_delete_lnk_lbl+'</a> <span style="color:#5388c4">|</span> <a class="cancelremovedate" onClick="$(\'.removedate\').tooltipster(\'hide\')">'+props.global_cancel_lbl+'</a>')
	                },
	                functionBefore: function(origin, continueTooltip) {
	                    continueTooltip();
	                    window.removetr = function() {
	                        $(origin).parent().parent().fadeOut("fast",function(){
	                        	if(getrecurringdates().length==1){
	                        		$('#recheight').tooltipster('hide');
	                        		$('#recheight').attr('style','border:1px solid #ccc;height:100px');
	                        	}
	                            $(this).remove();
	                            $('#totalrecurringinstances').html(getrecurringdates().length);
	                        });
	                        $('.removedate').tooltipster('hide');
	                    }
	                }
	            });
	            
	            $('.recurringtimes #date').datetimepicker('show');
	        }
	    });
	 
	    $('#recheight').tooltipster({
	        theme: 'form-validate-theme',
	        trigger: 'custom',
	        onlyOne: false,
		    fixedWidth:'100px',
		        content: $('<span>'+props.event_listing_sel_two_dates+'</span>'),
		    });
	    
	 // tooltip
	    $('.tooltipster').tooltipster({
	        theme: 'my-custom-theme',
	        fixedWidth:'300px',
	        position:'top',
	        animation:'grow',
	        functionInit: function(origin, content) {
	            return $(origin).data('tooltiptext');
	        }
	    });
	
	    $('input.regtype').iCheck({
	        checkboxClass: 'icheckbox_square-blue',
	        radioClass: 'iradio_square-blue'
	    });
	    $('input.recurring').iCheck({
	        checkboxClass: 'icheckbox_square-blue',
	        radioClass: 'iradio_square-blue'
	    });
	 
	    $('a#recurlink').click(function(){
	    	var evttype=$('#recurchk').attr('data-recur');
	    	if(evttype=='no'){
	    		showRecurringEvent();
	    	}else showRegularEvent();
	    	
	    	//alert(evttype);
	    	/*if(evttype=='no'){
	    		$('a#recurlink').html('Regular Event');
	    		 $('.nonrecurringtimes #start').datetimepicker('hide');
	        	 $('.nonrecurringtimes #end').datetimepicker('hide');    	
	            $('.nonrecurringtimes').fadeOut(200);        
	            $('.recurringtimes').delay(200).fadeIn(function(){
	            $('.recurringtimes #date').datetimepicker('show');
	            $('#recurchk').attr('data-recur','yes');
	            $('#isRecurring').val("on");
	            
	            });
	    	}else{
	    		 $('a#recurlink').html('Recurring Event');
	    		 $('.recurringtimes #fromtime').datetimepicker('hide');
	        	 $('.recurringtimes #totime').datetimepicker('hide');
	        	$('.recurringtimes #date').datetimepicker('hide');
	            $('.recurringtimes').fadeOut(200);
	            $('.nonrecurringtimes').delay(200).fadeIn();
	            $('#recurchk').attr('data-recur','no');
	            $('#isRecurring').val("false");
	           
	    	}*/
	    	
	    });
	    
	    $('#timezonechange').click(function(){
	        var temptimezone=$('#timezoneval').attr('data-timezone');
	        openHTMLPopUp('Change Timezone','',changeTimeZone,true);
	        $('#timezone').val(temptimezone);
	    });
	    
	    var autocomplete;
		   var templat,templng;
		    var componentForm = {
		street_number: 'short_name',
		route: 'long_name',
		locality: 'long_name',
		administrative_area_level_1: 'short_name',
		country: 'long_name',
		postal_code: 'short_name',
		sublocality_level_2:'long_name',
		sublocality_level_1:"long_name"
		};
		    
		var addesscomponents = {
		    street_number: 'short_name',
		    route: 'long_name',
		    sublocality_level_2:'long_name',
		    sublocality_level_1:"long_name"
		};
	    
		autocomplete = new google.maps.places.Autocomplete(
	            /** @type {HTMLInputElement} */(document.getElementById('search')),
	            { types: [] });
	    google.maps.event.addListener(autocomplete, 'place_changed', function() {
	             $('#venue').val('');           
	            $('#addr').val('');
	            $('#city').val('');
	            $('#state').val('');
	            $('#zip').val('');
	            $('#country').val('1');
	            var place = autocomplete.getPlace();   
	          // alert(JSON.stringify(place));
	          //  console.log(JSON.stringify(place));
	            if(place.formatted_address.indexOf(place.name)==-1)                           
	                $('#venue').val(place.name);
	       
	            var  address=[];       
	        for (var i = 0; i < place.address_components.length; i++){
	        	var addressType = place.address_components[i].types[0];
	        	if(componentForm[addressType]){
	        		var val = place.address_components[i][componentForm[addressType]];       
	        		if(addressType=='postal_code')           
	        			$('#zip').val(val);               
	        		else if(addressType=='administrative_area_level_1')
	        			$('#state').val(val);
	        		else if(addressType=='locality')
	        			$('#city').val(val);
	        		else  if(addressType=='country'){
	        			if(val=='United Kingdom')val='UK';
	        			else if(val=='United States')val='USA';               
	        			$('#country').val(val);
	        		}          
		            if(addesscomponents[addressType]){
		               address.push(val);
		            }
	        	}
	        }
	
	        $('#addr').val(address.join(' ').trim());
			if(place.geometry){
				templat = place.geometry.location.k;
				templng = place.geometry.location.B;			
				if(templng=='' || templng==undefined)templng = place.geometry.location.A;
				$('#googlemap').attr('src','/main/myevents/googlemap.jsp?lat=' +templat+'&lng='+templng);
				calGettimzone(templat,templng);
			}
	
		    $('#country').focus();
	        $('#city').focus();
	        $('#zip').focus();
	
	    });
		
	    $('#search').on('blur',function(){
	        //setaddr();
	    });
	    
	    $('#venue').on('blur',function(){    	
	    	if($('#country').val()!='1')
	    		getCountry();  	
	    });
	    
	    $('#addr').on('blur',function(){    	
	    	if($('#country').val()!='1')
	    		getCountry();  	
	    });
	    
	    $('#city').on('blur',function(){    	
	    	if($('#country').val()!='1')
	    		getCountry();  	
	    });
	    
	    $('#search').on('focus',function(){
	        //$("html, body").delay(400).animate({ scrollTop: $('#wherescroll').offset().top-50 }, "slow");
	    });
	    
	    var setaddr = function(){
	        
	        // empty all fields
	    	if($('#search').val()=='')return;
	        $('#venue').val('');
	        $('#addr').val('');
	        $('#city').val('');
	        $('#state').val('');
	        $('#zip').val('');
	        $('#country').val('');
	        
	        setTimeout(function(){
	            var address = $('#search').val().split(',');
	            
	            $('#country').val(address[address.length-1].trim());
	            var statewithzip = address[address.length-2];            
	            if(statewithzip.match(".*\\d.*")) {
	                var zip = statewithzip.substring(statewithzip.lastIndexOf(" ")+1,statewithzip.length);
	                var state = statewithzip.substring(0,statewithzip.lastIndexOf(" "));
	                $('#state').val(state.trim());
	                $('#zip').val(zip);
	            } else  {
	                $('#state').val(statewithzip.trim());
	            }
	            $('#city').val(address[address.length-3].trim());
	            
	            address.pop();
	            address.pop();
	            address.pop();
	            if($('#search').val().split(',').length<5){  
	            	//alert(address[0]);
	            	$('#addr').val(address[0]);            	
	             }else{
		            $('#venue').val(address[0]);
		            address.shift();
		            $('#addr').val(address.join(',').trim());
	             }
	            //trigger change event for validate by focusing and blurring fields
	            $('#venue').focus().blur();
	            $('#addr').focus().blur();
	            $('#city').focus().blur();
	            $('#state').focus().blur();
	            $('#zip').focus().blur();
	            $('#country').focus().blur();
	            var timezone=$('#timezone').find('option:selected').text();
	            if(timezone=='-- Select Time Zone --')
	            	timezone='(GMT-08:00) Pacific Time';
	            $('#timezoneval').html(timezone);
	        },1);
	    };
	    
	    // timezone picker
	    $('#timezone-image').timezonePicker({
	        target: '#timezone',
	        countryTarget: '#edit-site-default-country'
	    });	    

	$('.tooltipster').tooltipster({
        theme: 'my-custom-theme',
        fixedWidth:'300px',
        position:'top',
        animation:'grow',
        functionInit: function(origin, content) {
            return $(origin).data('tooltiptext');
        }
    });
    
    
    if ($(window).width() < 768) {
        $('#editeventform input[type="text"], #editeventform textarea, #editeventform select').tooltipster({
            trigger: 'custom',
            onlyOne: false,
            fixedWidth:'300px',
            position: 'top-right',
            theme:'form-validate-theme'
        });
    } else {
        $('#editeventform input[type="text"], #editeventform textarea, #editeventform select').tooltipster({
            trigger: 'custom',
            onlyOne: false,
            fixedWidth:'300px',
            position: 'top-right',
            theme:'form-validate-theme'
        });
    }
    
    $('#edit_title_section,.cancel_title_section').click(function () {
    	editTitleSection();
    });
    
	$('#edit_where_section,.cancel_where_section').click(function () {
		editWhereSection();
	});
	
	$('#edit_when_section, .cancel_when_section').click(function () {
		editWhenSection();
	});

	$('#edit_list_option_section,.cancel_list_option_section').click(function () {
		editListingOptionSection();
	});

    
});

function editTitleSection(){
	$('.toggleTitleSection').toggle(function(){
		if($("#titleSection").is(":visible")){
			$("#titleSection #title").val(formOnLoadData["editEventData.eventTitle"]);
			if(formOnLoadData["editEventData.descriptiontype"]=="wysiwyg"){
				tinymce.activeEditor.setContent(formOnLoadData["editEventData.description"]);
			}else{
				$("#titleSection #textmsg").html(formOnLoadData["editEventData.description"]);
			}
		}
	});
}

function editWhereSection(){
	$('.toggleWhereSection').toggle(function(){
		if($("#whereSection").is(":visible")){
			$('#googlemap').attr('src','/main/myevents/googlemap.jsp?lat='+formOnLoadData["editEventData.latitude"]+'&lng='+formOnLoadData["editEventData.longitude"]);
			$("#whereSection #search").val("");
			$("#whereSection #venue").val(formOnLoadData["editEventData.venue"]);
			$("#whereSection #addr").val(formOnLoadData["editEventData.address1"]);
			$("#whereSection #city").val(formOnLoadData["editEventData.city"]);
			$("#whereSection #country").val(formOnLoadData["editEventData.country"]);
			$("#whereSection #state").val(formOnLoadData["editEventData.state"]);
			$("#whereSection #zip").val(formOnLoadData["editEventData.zip"]);
		}
	});
}

function editWhenSection(){
	$('.toggleWhenSection').toggle(function(){
		if($("#whenSection").is(":visible")){
			
			if($('#isRecurring').val()=='on'){
				$("tbody#recurringdates tr").remove();
				
				var tempPrevSaveRecDates = jQuery.extend({}, prevSaveRecDates);
				$.each(tempPrevSaveRecDates,function(key, value){
					selectDate(key,value);
				});
				
				var sthour=formOnLoadData["editEventData.startHour"];
				var stmin=formOnLoadData["editEventData.startminute"];
				var stampm=formOnLoadData["editEventData.startampm"];
				
				var endhour=formOnLoadData["editEventData.endHour"];
				var endmin=formOnLoadData["editEventData.endminute"];
				var endampm=formOnLoadData["editEventData.endampm"];
				
				$("#whenSection #fromtime").val(sthour+":"+stmin+" "+stampm);
    			$("#whenSection #totime").val(endhour+":"+endmin+" "+endampm);
    			
    			showRecurringEvent();
    			removereccdate();
				
			}else{
				showRegularEvent();
				var stmonth=formOnLoadData["editEventData.startmonth"];
				var stday=formOnLoadData["editEventData.startday"];
				var styear=formOnLoadData["editEventData.startyear"];
				var sthour=formOnLoadData["editEventData.startHour"];
				var stmin=formOnLoadData["editEventData.startminute"];
				var stampm=formOnLoadData["editEventData.startampm"];
				
				var endmonth=formOnLoadData["editEventData.endmonth"];
				var endday=formOnLoadData["editEventData.endday"];
				var endyear=formOnLoadData["editEventData.endyear"];
				var endhour=formOnLoadData["editEventData.endHour"];
				var endmin=formOnLoadData["editEventData.endminute"];
				var endampm=formOnLoadData["editEventData.endampm"];
				
				$("#whenSection #start").val(stmonth+"/"+stday+"/"+styear+" "+sthour+":"+stmin+" "+stampm);
    			$("#whenSection #end").val(endmonth+"/"+endday+"/"+endyear+" "+endhour+":"+endmin+" "+endampm);
			}
		}
	});
}

function editListingOptionSection(){
	$('.toggleListOptSection').toggle(function(){
		if($("#listOptionSection").is(":visible")){
			$("#listOptionSection #hostname").val(formOnLoadData["editEventData.name"]);
			$("#listOptionSection #hostemail").val(formOnLoadData["editEventData.email"]);
			$('input[name="editEventData.listingprivacy"][value="'+formOnLoadData["editEventData.listingprivacy"]+'"]').iCheck("check");
		}
	});
}

function showRecurringEvent(){
	$('a#recurlink').html(props.el_regular_event_lbl);
	$('.nonrecurringtimes #start').datetimepicker('hide');
	$('.nonrecurringtimes #end').datetimepicker('hide');    	
    $('.nonrecurringtimes').fadeOut(200);        
    $('.recurringtimes').delay(200).fadeIn(function(){
    $('.recurringtimes #date').datetimepicker('show');
    $('#recurchk').attr('data-recur','yes');
    $('#isRecurring').val("on");
    });
}

function showRegularEvent(){
	$('a#recurlink').html(props.el_recc_evt_lbl);
	$('.recurringtimes #fromtime').datetimepicker('hide');
	$('.recurringtimes #totime').datetimepicker('hide');
	$('.recurringtimes #date').datetimepicker('hide');
	$('.recurringtimes').fadeOut(200);
	$('.nonrecurringtimes').delay(200).fadeIn();
	$('#recurchk').attr('data-recur','no');
	$('#isRecurring').val("false");
}

function getrecurringdates() {
    var dates = [];
    $('#recurringdates tr').each(function(){
        dates.push($(this).data('stamp'));
    });
    return dates;
 }

function prePareDates(){
	var isrecurval=$('#isRecurring').val();
	if(isrecurval=='on') {  		
    	$('#isRecurring').val("on");
    	$('#optlist').html();
    	$("#optlist").empty();
        $('#recurringdates tr').each(function(index,value){
        $('#optlist').append(createHiddenTextElement(index,"key",$(this).data('stampkey')));
       	$('#optlist').append(createHiddenTextElement(index,"value",$(this).data('stampval')));
        });
        if(getrecurringdates().length<2){
        	//alert("Select atleast two dates for recurring event.");
        	bootbox.confirm(props.event_listing_sel_two_dates, function (result) { });
        	return false;
        }      
     // var fromtime = $('#fromtime').val();
        var totime = $('#totime').val();
        var fromtime=$('#fromtime').val();
        var strtd =  fromtime.split(" ");
        var end = totime.split(" ");
        $('#starthour').val(strtd[0].split(":")[0]);
        $('#startminute').val(strtd[0].split(":")[1]);
        $('#stampm1').val(strtd[1]);
        $('#endhour').val(end[0].split(":")[0]);
   	    $('#endminute').val(end[0].split(":")[1]);
        $('#endampm1').val(end[1]);
    }
    else{    	
     $('#isRecurring').val("false");
	 var strtd=$('#start').val();
	 var end=$('#end').val();
	
	 $('#endampm1').val(end.split(" ")[2]);
   	 $('#stampm1').val(strtd.split(" ")[2]);
	    
	 $('#startmonth').val(strtd.split("/")[0]);
		 $('#startday').val(strtd.split("/")[1]);
		 $('#startyear').val(strtd.split("/")[2].split(" ")[0]);
		 
		 $('#starthour').val(strtd.split(" ")[1].split(":")[0]);
		 $('#startminute').val(strtd.split(" ")[1].split(":")[1]);
	
	 $('#endmonth').val(end.split("/")[0]);
	 $('#endday').val(end.split("/")[1]);
	 $('#endyear').val(end.split("/")[2].split(" ")[0]);
	 $('#endhour').val(end.split(" ")[1].split(":")[0]);
	 $('#endminute').val(end.split(" ")[1].split(":")[1]);
    }
	return true;
}

function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}


function selectDate(key,value){
try{
		var spaces=value.split(' ');
		var fromdatetime = spaces[0].split('/');
		var fromdate = fromdatetime[0];
		var fdate = fromdate.split('-');
		var date = fdate[1]+'/'+fdate[2]+'/'+fdate[0];
		
		var dayarry = key.split(' ');
		var fromtime = dayarry[3];
		var ampm = dayarry[4].split('-');
		var fromampm = ampm[0];
		var endtime = ampm[1];
		var endampm = dayarry[5];
		var time = fromtime+' '+fromampm+' - '+endtime+' '+endampm;
		var day  = dayarry[6].replace('(',' ').replace(')',' ');
		var json = {"Mon":"Monday","Tue":"Tuesday","Wed":"Wednesday","Thu":"Thursday","Fri":"Friday","Sat":"Saturday","Sun":"Sunday"};

		var dateampm = date+' '+fromtime+' '+fromampm+' '+endtime+' '+endampm;
		var html ='';
        datedisplayobj={};
		datekeyarray=[];
		html = '<tr  data-stampval="'+key+'" data-stampkey="'+value+'"  data-stamp="'+dateampm+'">'+
        '<td>'+key+'</td>'+                                
        '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
        '</tr>';
		 
		datedisplayobj[key]=html;
		datekeyarray.push(key);
		
		if($('#recurringdates tr').length>0){
        	$('.table #recurringdates tr').each(function() {
        	         html = '<tr  data-stampval="'+$(this).data('stampval')+'" data-stampkey="'+$(this).data('stampkey')+'"  data-stamp="'+$(this).data('stamp')+'">'+
                     '<td>'+$(this).data('stampval')+'</td>'+                                
                     '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
                     '</tr>';
        	         datedisplayobj[$(this).data('stampval')]=html;
         			 datekeyarray.push($(this).data('stampval'));
        	 
        	  });
        }
		
		/*var sorted = datekeyarray.sort(function( a, b ) {
			 var tempa=new Date(a.split('-')[0]).getTime();
			 var tempb=new Date(b.split('-')[0]).getTime();
            var order = "ASC";
            if( order == "ASC" ) {
                    return tempa>tempb;
            } else {
                    return tempb > tempa;
            } 
            
		});
		
		datekeyarray=sorted;
		sorted=[];*/
		
		var tempAryByTime=[];
		var tempJsonByTime={};
		for(var i=0;i<datekeyarray.length;i++){
			tempAryByTime.push(new Date(datekeyarray[i].split('-')[0]).getTime());
			tempJsonByTime[new Date(datekeyarray[i].split('-')[0]).getTime()]=datekeyarray[i];
		}
		
		var tempSortedAryByTime=tempAryByTime.sort(function(a, b){return a-b});
		
		datekeyarray=[];
		for(var i=0;i<tempSortedAryByTime.length;i++){
			datekeyarray.push(tempJsonByTime[tempSortedAryByTime[i]]);
		}
		
		if($.inArray(dateampm,getrecurringdates())==-1) {
        	$('#recheight').attr('style','border:1px solid #ccc');
        	//$('#recheight').tooltipster('hide');
            $('#recurringdates').html("");
            $.each(datekeyarray,function(index,val){
            $('#recurringdates').append(datedisplayobj[datekeyarray[index]]);
            });
            //$('#totalrecurringinstances').html(getrecurringdates().length);
           
        } else {
            $('.recurringtimes #date').datetimepicker('hide');
        }
		
}catch(err){
	//alert(err);
	console.log("error::"+err);
}
		 
}

function removereccdate(){
	   $('.removedate').tooltipster({
           theme: 'my-custom-theme',
           fixedWidth:'200px',
           position:'right',
           trigger: 'click',
           interactive:true,
           animation:'grow',
           functionInit: function(origin, content) {
               return $('<a onClick="removetr()" class="confirmremovedate">'+props.global_delete_lnk_lbl+'</a> <span style="color:#5388c4">|</span> <a class="cancelremovedate" onClick="$(\'.removedate\').tooltipster(\'hide\')">'+props.global_cancel_lbl+'</a>');
           },
           functionBefore: function(origin, continueTooltip) {
               continueTooltip();
               window.removetr = function() {
                   $(origin).parent().parent().fadeOut("fast",function(){
                       $(this).remove();
                       $('#totalrecurringinstances').html(getrecurringdates().length);
                   });
                   $('.removedate').tooltipster('hide');
               };
           }
       });
}

var getrecurringdates = function() {
    var dates = [];
    $('#recurringdates tr').each(function(){
    	if($(this).data('stamp')!='' && $(this).data('stamp')!=undefined)
        dates.push($(this).data('stamp'));
    });
    return dates;
};

$.fn.serializeObject = function(){
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name] !== undefined) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};
 
var deleteRecDate=new RegExp("optionsList");
function editlistEvent(type,isrecurring,selectedtype,subform){
	$("#notification-holder").html("");
	parent.loadingPopup();
	var email=document.getElementById('hostemail').value;
	email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	document.getElementById('hostemail').value=email;
	if(subform=="whenSection" && !prePareDates()){
		parent.hidePopup();
		return;
	}
	//prePareDates();	
	if(type=="editevent"){
		$('#eventerrors').html('');
		$('#eventerrors').hide();
  	   
		var finalStaticDesc="";
		if(selectedtype=='wysiwyg'){
			/*var content=document.getElementById('editor').innerHTML;
			var description=replaceSpecialChars(content);
			var finaldescription=convert(description);*/
			var niceeditorhtml = tinymce.get('editor').getContent();  	
			var content=niceeditorhtml;
			var description=replaceSpecialChars(content);
			var finaldescription=convert(description);
			document.getElementById('msg').value=finaldescription;
			finalStaticDesc=finaldescription;
		}else{
		 	var textdes=document.getElementById('textmsg').value;
			var textdescription=replaceSpecialChars(textdes);
			var finaltextdescription=convert(textdescription);
			document.getElementById('textmsg').value=finaltextdescription;
			finalStaticDesc=finaltextdescription;
	 	}
		
		
		var timezone=$('#timezone').val();
		if(timezone=='1'){
			$('#timezone').val('SystemV/PST8 Pacific Time');
		}
		var prevSavedFormData = jQuery.extend({}, formOnLoadData);
		var subFormData=$('#'+subform).find("select, textarea, input").serializeObject();
		var recDatesData=$('#optlist').find("select, textarea, input").serializeObject();
		//console.log('recDatesData: '+JSON.stringify(recDatesData));
		//console.log('subFormData: '+JSON.stringify(subFormData));
		//console.log('prevSaveRecDates: '+JSON.stringify(prevSaveRecDates));
		if(isrecurring=="on"){
			if(subform=="whenSection"){
				for(key in formOnLoadData)
					if(deleteRecDate.test(key))
						delete formOnLoadData[key];
			}else{
				 var recdatecount=0;
				 for(key in prevSaveRecDates){
					 //console.log(key+" - "+prevSaveRecDates[key]);
						formOnLoadData["optionsList["+recdatecount+"].key"]=prevSaveRecDates[key];
						formOnLoadData["optionsList["+recdatecount+"].value"]=key;
						recdatecount++;
				}
			}
		}
		
		for(var key in subFormData){
			if(formOnLoadData.hasOwnProperty(key)){//updating the last saved data with current form data.  if validation error occurs we have to get back last saved data.
				formOnLoadData[key]=subFormData[key];
			}else{
				formOnLoadData[key]=subFormData[key];
			}
		}
		if(isrecurring=="on"){
			formOnLoadData["seeRecurring"]="on";
		}
		$.ajax({
			url : 'EditEvent!update',
			type : 'POST',
			data : formOnLoadData,
	        success: function(t) {
	 	       var json='',status='';
	 	       var msg=t;
	 	       if(!isValidActionMessage(t)) return;
	 	       if(msg.indexOf('errorMessage')>-1){
		 	       if(msg.indexOf('askcard')>-1){
			 	    	hidePopup();
			 	       var mgrid=document.getElementById('managerid').value;
			 	       _purpose="EditEvent";
			 	       _type=type;
			 	       if(isrecurring=="false") _isrecurring=isrecurring;
			 	       else _isrecurring="true";
				       _selectedtype=selectedtype;
			 	       getcc(mgrid,'editevent');
		 	       }
	 	       }else{
		 	       json=eval('('+t+')');
		 	       status=json.status;
	 	       }
	 	       if(status=='success'){ 
	 	    	   hidePopup();
	 	    	   if(subform=="titleSection"){
	 	    		   
	 	    		   $("#titleSectionStatic #titleStatic").html($("#titleSection #title").val());
	 	    		   $("#titleSectionStatic #descStatic").html(finalStaticDesc);
	 	    		   
		 	    	   $('#edit_title_section').trigger("click", function () {
		 	    		   $('.toggleTitleSection').toggle();
		 	    	   });
		 	    	   	
		 	    	  notification(props.ee_title_section_update_status_msg,'success');
	 	    	   }else if(subform=="whereSection"){
	 	    		  
	 	    		 if($("#whereSection #venue").val()!=''){
		 	    		   $("#whereSectionStatic #venueStatic").html($("#whereSection #venue").val());
		 	    		  $("#whereSectionStatic #staticVenDiv").show();
		 	    		 }else $("#whereSectionStatic #staticVenDiv").hide();
		 	    		   
	 	    		  
	 	    		 if($("#whereSection #addr").val()!=''){
	 	    		   $("#whereSectionStatic #addrStatic").html($("#whereSection #addr").val());
	 	    		  $("#whereSectionStatic #staticAddrDiv").show();
	 	    		 }else $("#whereSectionStatic #staticAddrDiv").hide();
	 	    		 
	 	    		 var cityStateCountry=$("#whereSection #city").val()+",&nbsp";
	 	    		   
	 	    		 //$("#whereSectionStatic #cityStatic").html($("#whereSection #city").val());
	 	    		  
	 	    		  if($("#whereSection #state").val()!=''){
	 	    			 cityStateCountry+=$("#whereSection #state").val()+",&nbsp";
	 	    			  //$("#whereSectionStatic #stateStatic").html($("#whereSection #state").val());
	 	    			 //$("#whereSectionStatic #staticStateDiv").show();
	 	    		  }//else $("#whereSectionStatic #staticStateDiv").hide();
	 	    		  
	 	    		 cityStateCountry+=$("#whereSection #country").val();
	 	    		 
	 	    		$("#whereSectionStatic #city_state_country").html(cityStateCountry);
	 	    		   
	 	    		   $('#edit_where_section').trigger("click", function () {
	 	    			   $('.toggleWhereSection').toggle();
	 	    		   });
	 	    		  notification(props.ee_where_section_update_status_msg,'success');
	 	    	   }else if(subform=="whenSection"){
	 	    		  
	 	    			   var startval=$("#whenSection #start").val();
		 	    		   var stDtTm =startval.split(" ");
		 	    		   var stDt=stDtTm[0].split("/");
		 	    		   var stDate=getDateFormat(stDt[2]+'/'+stDt[0]+'/'+stDt[1]);
		 	    		   var stDay=getWeekDay(''+stDt[2]+'/'+stDt[0]+'/'+stDt[1]+'');
		 	    		   
		 	    		   var endTm="";var endDate="";var endDay="";
		 	    		  if(isrecurring=='on'){
		 	    			  	var firstRecDate=$("#recurringdates tr:first").data("stamp");
		 	    			  	var firstRecDateAry=firstRecDate.split(" ");
		 	    			  	stDtTm[1]=firstRecDateAry[1];
		 	    			  	stDtTm[2]=firstRecDateAry[2];
			 	    			 var lastRecDate=$("#recurringdates tr:last").data("stamp");
			 	    			 var dateAry=lastRecDate.split(" ");
			 	    			 var endDt=dateAry[0].split("/");
			 	    			 endDate=getDateFormat(endDt[2]+'/'+endDt[0]+'/'+endDt[1]);
			 	    			 endDay=getWeekDay(''+endDt[2]+'/'+endDt[0]+'/'+endDt[1]+'');
			 	    			 endTm=dateAry[3]+" "+dateAry[4];
			 	    			prevSaveRecDates={};
			 	    			for(var i=0;i<Object.keys(recDatesData).length/2;i++)
			 	    				prevSaveRecDates[recDatesData["optionsList["+i+"].value"]]=recDatesData["optionsList["+i+"].key"];
			 	    			
			 	    	}else{
		 	    		   var endval=$("#whenSection #end").val();
		 	    		   var endDtTm =endval.split(" ");
		 	    		   var endDt=endDtTm[0].split("/");
		 	    		   endDate=getDateFormat(endDt[2]+'/'+endDt[0]+'/'+endDt[1]);
		 	    		   endDay=getWeekDay(''+endDt[2]+'/'+endDt[0]+'/'+endDt[1]+'');
		 	    		   endTm=endDtTm[1]+" "+endDtTm[2];
		 	    		  $("tbody#recurringdates tr").remove();
			 	    	}
	 	    		   $("#whenSectionStatic #startStatic").html(stDay+", "+stDate+", "+stDtTm[1]+" "+stDtTm[2]);
	 	    		   $("#whenSectionStatic #endStatic").html(endDay+", "+endDate+", "+endTm);
	 	    		   
	 	    		   $('#edit_when_section').trigger("click", function () {
	 	    			   $('.toggleWhenSection').toggle();
	 	    		   });
	 	    		  notification(props.ee_when_section_update_status_msg,'success');
	 	    	   }else{//$('input[name=traninfoindex]:checked').val();
	 	    		   
	 	    		  $("#listOptionSectionStatic #hostNmStatic").html($("#listOptionSection #hostname").val());
	 	    		  //$("#listOptionSectionStatic #i18nLangStatic").html($("#listOptionSection #i18nLang").val());
	 	    		
	 	    		  $("#listOptionSectionStatic #hostEmailStatic").html($("#listOptionSection #hostemail").val());
	 	    		  var privacy=props.el_public_lbl;
	 	    		  if($('input[name="editEventData.listingprivacy"]:checked').val()=="PVT") privacy=props.el_private_lbl;
	 	    		  $("#listOptionSectionStatic #listPrivacyStatic").html(privacy);
		 	    		
	 	    		  $('#edit_list_option_section').trigger("click", function () {
	 	    			  $('.toggleListOptSection').toggle();
	 	    		  });
	 	    		 notification(props.ee_listing_options_section_update_status_msg,'success');
	 	    	   }
	 	    	   
	 	       }else{
		 	       if(msg.indexOf('askcard')>-1){}
		 	       else{
		 	    	   //after validation error we have to get back the last saved data.
		 	    	  formOnLoadData=prevSavedFormData;
		 	    	   
		 	    	   hidePopup(); 	      
		 	    	   $('html, body').animate({ scrollTop: $("#eventerrors").height()}, 1000);   
		 	    	   $('#eventerrors').show();
		 	    	   $('#eventerrors').html(t);
		 	       }
	 	       }
	 	        },error:function(t){
	 	        	hidePopup();
	 	        }
	    });
	}
} 

function changeTimeZone(){
	$('#timezoneval').html($('#timezone option:selected').text());
	var tempval=$('#timezone').val();
	$('.close').trigger("click");
	$('#timezone').val(tempval);
}

function replaceSpecialChars(content){
	   var s = content;
	   s = s.replace(/[\u2018|\u2019|\u201A]/g, "\'");
	   s = s.replace(/[\u201C|\u201D|\u201E]/g, "\"");
	   s = s.replace(/\u2026/g, "...");
	   s = s.replace(/[\u2013|\u2014]/g, "-");
	   s = s.replace(/\u02C6/g, "^");
	   s = s.replace(/\u2039/g, "<");
	   s = s.replace(/\u203A/g, ">");
	   s = s.replace(/[\u02DC|\u00A0]/g, " ");
	    return s;
	}


var openHTMLPopUp=function (header,content,calback,footernreq){
	
	$('#timezonedata').show();
	content=$('#timezoneselect').html();
	//content+='<br/><br/>';
	$('#myModalhtmlin .headlbl').html('');
	$('#myModalhtmlin .modal-body').html('');
	$('#myModalhtmlin .modal-footer').html('');
	$('#myModalhtmlin').show();
	$('#myModalhtmlin .headlbl').html(header);
	$('#myModalhtmlin .modal-body').html(content);
	
	$('#timezoneselect').html('');
	if(footernreq!=false)
	$('#myModalhtmlin .modal-footer').html($('<button class="btn btn-primary" onclick="changeTimeZone();" > <i class="fa fa-pencil-square-o"></i> '+props.global_submit_lbl+'</button><button class="btn btn-danger cls" ><i class="fa fa-times"></i> '+props.global_cancel_lbl+'</button>'));
	
};

