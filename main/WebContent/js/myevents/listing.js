$(document).ready(function(){
	var submitcount=0;
    //datetimepicker
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
   // var datesArray=[];
    $('.recurringtimes #date').datetimepicker({
        format:'m/d/Y',
        timepicker:false,
        inline:true,
        step:5,
        onSelectDate:function(dp,$input) {
            var weekday=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
            var shrtweekday= ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
            var shrmonths=['Jan',
                           'Feb',
                           'Mar',
                           'Apr',
                           'May',
                           'June',
                           'July',
                           'Aug',
                           'Sept',
                           'Oct',
                           'Nov',
                           'Dec'];
            var date = $('#date').val();
            var fromtime = $('#fromtime').val();
            var totime = $('#totime').val();
            var stamp = date+' '+fromtime+' '+totime;
            //2014-04-24/09:00/09:00 AM/2014-04-24/10:00/10:00 AM
           
            var datearr = date.split('/');
            var d = new Date(datearr[2],datearr[0]-1,datearr[1]);
            
            var key=datearr[2]+'-'+datearr[0]+'-'+datearr[1]+'/'+fromtime.split(" ")[0]+'/'+fromtime+'/'+datearr[2]+'-'+datearr[0]+'-'+datearr[1]+'/'+totime.split(" ")[0]+'/'+totime;
       // alert(st);
        
            //24 Apr 2014 09:00 AM-10:00 AM (Thu)
            var val=d.getDate()+" "+shrmonths[d.getMonth()]+" "+d.getFullYear()+" "+fromtime+"-"+totime+" "+"("+shrtweekday[d.getDay()]+")" ;
            //new Date("October 13, 2014 11:13 PM");
           /* datesArray.push(new Date(d.getMonth()+" "+d.getDate()+", "+d.getFullYear()+" "+fromtime));
            var dupDates=dates.slice(0);
            dupDates.sort(date_sort_asc);	
            for (var i = 0; i < dupDates.length; i++) {
            	
            }
*/            
            var html ='';
			datedisplayobj={};
			datekeyarray=[];
              
                	//alert("length zero");
                	 html = '<tr  data-stampval="'+val+'" data-stampkey="'+key+'"  data-stamp="'+stamp+'">'+
                     '<td>'+val+'</td>'+                                
                     '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
                     '</tr>';
                datedisplayobj[val]=html;
    			datekeyarray.push(val);
    			
    			
    			  if($('#recurringdates tr').length>0){
                	//alert('lenght>1');
                	$('.table #recurringdates tr').each(function() {
                	             /*   alert($(this).data('stampval')); 
                	               alert($(this).data('stampkey')); 
                	               alert($(this).data('stamp'));  */                	              
                	         html = '<tr  data-stampval="'+$(this).data('stampval')+'" data-stampkey="'+$(this).data('stampkey')+'"  data-stamp="'+$(this).data('stamp')+'">'+
                             '<td>'+$(this).data('stampval')+'</td>'+                                
                             '<td><a class="btn btn-sm btn-danger removedate">'+props.global_delete_lnk_lbl+'</a></td>'+
                             '</tr>';
                	         datedisplayobj[$(this).data('stampval')]=html;
                 			 datekeyarray.push($(this).data('stampval'));
                	 
                	  });
                }
    			//alert("datedisplayobj::"+JSON.stringify(datedisplayobj));
    			//alert("datekeyarray::"+JSON.stringify(datekeyarray));
    			// alert(JSON.stringify(datekeyarray));
    			 var sorted = datekeyarray.sort(function( a, b ) {
    				// alert("a::"+a.split('-')[0]);
    				// alert("b::"+b.split('-')[0]);
    				 var tempa=new Date(a.split('-')[0]).getTime();
    				 var tempb=new Date(b.split('-')[0]).getTime();
    				// alert("a::"+tempa);
    				//// alert("b::"+tempb);
                     var order = "ASC";
                     if( order == "ASC" ) {
                             return tempa>tempb;
                     } else {
                             return tempb > tempa;
                     } 
                     
             });
    			// alert(JSON.stringify(sorted));
    			 datekeyarray=sorted;
    			 sorted=[];
            if($.inArray(stamp,getrecurringdates())==-1) {            	
            	$('#recheight').attr('style','border:1px solid #ccc');
            	$('#recheight').tooltipster('hide');
                $('#recurringdates').html("");
                $.each(datekeyarray,function(index,val){
                $('#recurringdates').append(datedisplayobj[datekeyarray[index]]);
                });
                $('#totalrecurringinstances').html(getrecurringdates().length);
               // if(getrecurringdates().length<2)alert("Select atleast two dates for recurring event.");
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
                        	//alert(getrecurringdates().length);
                        	if(getrecurringdates().length==1){
                        		//alert("hi");
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
    
    //iCheck
    $('input.regtype').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
    $('input.regtypersvp').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
    $('input.recurring').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
    /*$('input.regtypersvp').on('ifChecked', function(event){
    	$('.continue').html('Create RSVP Event');
    });
    $('input.regtype').on('ifChecked', function(event){
    	$('.continue').html('Continue to Payment settings');
    });
    */
    $('input.recurring').on('ifChecked', function(event){
    	// $('.recurringtimes #date').datetimepicker('show');
    	 $('.nonrecurringtimes #start').datetimepicker('hide');
    	 $('.nonrecurringtimes #end').datetimepicker('hide');    	
        $('.nonrecurringtimes').fadeOut(200);        
        $('.recurringtimes').delay(200).fadeIn(function(){
        	 $('.recurringtimes #date').datetimepicker('show');
        });       
       // $("html, body").delay(400).animate({ scrollTop: $('#whenscroll').offset().top-50 }, "slow");
    });
    $('input.recurring').on('ifUnchecked', function(event){
    	 $('.recurringtimes #fromtime').datetimepicker('hide');
    	 $('.recurringtimes #totime').datetimepicker('hide');
    	$('.recurringtimes #date').datetimepicker('hide');
        $('.recurringtimes').fadeOut(200);
        $('.nonrecurringtimes').delay(200).fadeIn();
    });
    
    $('a#recurlink').click(function(){
    	var evttype=$('#recurchk').attr('data-recur');
		$('#recheight').tooltipster('hide');
    	//alert(evttype);
    	if(evttype=='no'){
    		$('a#recurlink').html(props.el_regular_event_lbl);
    		 $('.nonrecurringtimes #start').datetimepicker('hide');
        	 $('.nonrecurringtimes #end').datetimepicker('hide');    	
            $('.nonrecurringtimes').fadeOut(200);        
            $('.recurringtimes').delay(200).fadeIn(function(){
            $('.recurringtimes #date').datetimepicker('show');
            $('#recurchk').attr('data-recur','yes');
            $('#isRecurring').val("true");
            
            });
    	}else{
    		 $('a#recurlink').html(props.el_recc_evt_lbl);
    		 $('.recurringtimes #fromtime').datetimepicker('hide');
        	 $('.recurringtimes #totime').datetimepicker('hide');
        	$('.recurringtimes #date').datetimepicker('hide');
            $('.recurringtimes').fadeOut(200);
            $('.nonrecurringtimes').delay(200).fadeIn();
            $('#recurchk').attr('data-recur','no');
            $('#isRecurring').val("false");
           
    	}
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
		        for (var i = 0; i < place.address_components.length; i++) {
		            var addressType = place.address_components[i].types[0];
		          if (componentForm[addressType])
		          {
		           var val = place.address_components[i][componentForm[addressType]];       
		            if(addressType=='postal_code')           
		            $('#zip').val(val);               
		            else if(addressType=='administrative_area_level_1')
		            $('#state').val(val);
		            else if(addressType=='locality')
		            $('#city').val(val);
		            else  if(addressType=='country')
		            {    if(val=='United Kingdom')val='UK';
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
		$('#googlemap').attr('src','googlemap.jsp?lat=' +templat+'&lng='+templng);
		calGettimzone(templat,templng);
		}
		
		  $('#country').focus();
          $('#city').focus();
          $('#zip').focus();
		
		        });
    
    
     /*  $('#timezonechange').click(function(){
    	   alert($('#timezoneval').val());
    	var temptimezone=$('#timezoneval').val();
    	openHTMLPopUp('Change Timezone','',changeTimeZone,true); 
    	$('#timezone').val(temptimezone);    	
    });
    */
    
    
    
    // map
    /*$('#map').locationpicker({
        inputBinding: {
           // latitudeInput: $('#lat'),
          //  longitudeInput: $('#lon'),
            //radiusInput: $('#radius'),
            locationNameInput: $('#search')
        },
        enableAutocomplete: true,
        scrollwheel: true,
        radius: 200,
        onchanged: function(currentLocation, radius, isMarkerDropped) {
           // alert(JSON.stringify(isMarkerDropped));
            // empty all fields
           // $('#venue').val('');
        	//alert( currentLocation);
            $('#addr').val('');
            $('#city').val('');
            $('#state').val('');
            $('#zip').val('');
            $('#country').val('1');
            
            var address = $('#search').val().split(',');
            $('#country').val(address[address.length-1].trim());
            var statewithzip = address[address.length-2];            
            if(statewithzip.match(".*\\d.*")) {
                var zip = statewithzip.substring(statewithzip.lastIndexOf(" ")+1,statewithzip.length)
                var state = statewithzip.substring(0,statewithzip.lastIndexOf(" "))
                $('#state').val(state.trim())
                $('#zip').val(zip);
            } else  {
                $('#state').val(statewithzip.trim())
            }
            
            $('#city').val(address[address.length-3].trim());
            address.length = address.length-3;
            $('#googlemap').attr('src','googlemap.jsp?lat=' +currentLocation.latitude+'&lng='+currentLocation.longitude);
            calGettimzone(currentLocation.latitude,currentLocation.longitude);
            //$('#searchblock').hide();
            //$('#venue').val(address[0]);
            if($('#search').val().split(',').length<5){  
            	//alert(address[0]);
            	$('#addr').val(address[0]);            	
             }else{            	
             	$('#venue').val(address[0]);      
             	 address.shift();
                 $('#addr').val(address.join(',').trim());

             }
            
            $('#country').focus();
            $('#city').focus();
            $('#zip').focus();
            
            //setaddr();
        }
    });*/
    
    $('#search').on('blur',function(){
     //   setaddr();
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
      //  $("html, body").delay(400).animate({ scrollTop: $('#wherescroll').offset().top-50 }, "slow");
    });
    
    var setaddr = function(){
        
        // empty all fields
    	if($('#search').val()=='')return;
        $('#venue').val('');
        $('#addr').val('');
        $('#city').val('');
        $('#state').val('');
        $('#zip').val('');
        $('#country').val('1');
        
        setTimeout(function(){        	        
            var address = $('#search').val().split(',');            
            $('#country').val(address[address.length-1].trim());
            var statewithzip = address[address.length-2];            
            if(statewithzip.match(".*\\d.*")) {
                var zip = statewithzip.substring(statewithzip.lastIndexOf(" ")+1,statewithzip.length)
                var state = statewithzip.substring(0,statewithzip.lastIndexOf(" "))
                $('#state').val(state.trim())
                $('#zip').val(zip);
            } else  {
                $('#state').val(statewithzip.trim())
            }
            $('#city').val(address[address.length-3].trim());
            
            address.pop()
            address.pop()
            address.pop()
            //alert(address);
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
            $('#addr').focus().blur()
            $('#city').focus().blur()
            $('#state').focus().blur()
            $('#zip').focus().blur()
            $('#country').focus().blur();
            //alert($('#timezone').find('option:selected').text());
            var timezone=$('#timezone').find('option:selected').text();
           // alert(timezone);$('#timezone').val('SystemV/PST8 Pacific Time');
            if(timezone=='-- Select Time Zone --')
            	timezone='(GMT-08:00) Pacific Time';
           // alert(timezone);
           // if( $('#timezoneval').val('(GMT-08:00) Pacific Time'))
            	//timezone='(GMT-08:00) Pacific Time';
            $('#timezoneval').html(timezone);
            
        },1);
    }
    
    // timezone picker
    $('#timezone-image').timezonePicker({
        target: '#timezone',
        countryTarget: '#edit-site-default-country'
    });
        
   
    if ($(window).width() < 768) {    	
        $('#myform input[type="text"], #myform textarea, #myform select').tooltipster({
            trigger: 'custom',
            onlyOne: false,
            fixedWidth:'300px',
            position: 'bottom',
            theme:'form-validate-theme'
        });
    } else {    	
        $('#myform input[type="text"], #myform textarea, #myform select').tooltipster({
            trigger: 'custom',
            onlyOne: false,
            fixedWidth:'300px',
            position: 'right',
            theme:'form-validate-theme'
        });
    }
    
/*    $(window).resize(function(){    	
    	if ($(window).width() < 768) {        	
            $('#myform input[type="text"], #myform textarea, #myform select').tooltipster({
                trigger: 'custom',
                onlyOne: false,
                fixedWidth:'300px',
                position: 'bottom',
                theme:'form-validate-theme'
            });
        } else {        	
            $('#myform input[type="text"], #myform textarea, #myform select').tooltipster({
                trigger: 'custom',
                onlyOne: false,
                fixedWidth:'300px',
                position: 'right',
                theme:'form-validate-theme'
            });
        }
    });*/
        
    $('#myform').validate({
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
        	'addEventData.eventTitle': {required: true},
            'addEventData.email': {
                required: true,
                email: true
            },
            'addEventData.name': {required: true},
            //'addEventData.description': {required: true},
            //search: {required: true},
           // 'addEventData.venue': {required: true},
           // 'addEventData.address1': {required: true},
            'addEventData.city': {required: true},
          //  'addEventData.state': {required: true},
          //  'addEventData.zip': {required: true},
            'addEventData.country': {codesRequired : true},
        },
        messages: {
        	'addEventData.eventTitle': props.el_evt_title_empty,
			'addEventData.email': {
                required:props.el_email_empty,
                email: props.el_enter_valid_mail
            },
            'addEventData.name': props.el_host_empty,
          //  'addEventData.description':"Describe your event",
          //  'addEventData.venue':"Please enter venue",
          //  'addEventData.address1':"Please enter address",
            'addEventData.city':props.el_city_empty,
         //   'addEventData.state':"Please enter state",
         //   'addEventData.zip':"Please enter ZIP",
            //'addEventData.country':"Please enter country",
		},
        submitHandler: function (form) {
        	//if(submitcount>0)return;
        	  /*$('.continue').hide();	   
        	$('.processing').show();*/
			loadingPopup(); 
        	$(".continue").attr("disabled",true);
        	var url='/main/myevents/createevent!getEventsCount';
        	
          $.getJSON('/main/myevents/createevent!getEventsCount',function(data){		 
		  if(!isValidActionMessage(JSON.stringify(data))) {
			  submitcount=0;  return false;	       
		  }
	       var listingStatus = data.listingStatus;				      		      
	       var mgrid = data.mgrid;
	       if(listingStatus=='donot_allow')
	       { 
	    	   getListingPopup(mgrid,listingStatus);
			   $(".continue").attr("disabled",false);
	       /*$('.continue').show();	   
       	   $('.processing').hide();*/
	       return false;
	       }    	             
	         $('#eventerrors').hide();
	        listEvent(selectedtype);
           });
                       
            
        }
    });
    
    $.validator.addMethod("codesRequired", function(value, element) {
        if(element.selectedIndex <= 0) return element.selectedIndex;
                else return value;
    }, props.country_is_not_selected_errmsg);

    
    
   function getrecurringdates() {
        var dates = [];
        $('#recurringdates tr').each(function(){
            dates.push($(this).data('stamp'));
        });
        return dates;
     }
    
    
    function prePareDates(){
    	var isrecurval=$('#isRecurring').val();
    	if(isrecurval=='true') {    	
        	$('#isRecurring').val("true");
        	$('#optlist').html();        	
            $('#recurringdates tr').each(function(index,value){
            $('#optlist').append(createHiddenTextElement(index,"key",$(this).data('stampkey')));
           	$('#optlist').append(createHiddenTextElement(index,"value",$(this).data('stampval')));
            });
            if(getrecurringdates().length<2){
            	$('.processing').hide();
            	$('.continue').show();            	
            	$('#recheight').tooltipster('show');
            	//$('#recheight').focus();
            	$(".continue").attr("disabled",false);
            	$("html, body").delay(400).animate({ scrollTop: $('#recheight').offset().top-200 }, "slow");
                //bootbox.alert('Select atleast two dates for recurring event.', function (result) {});
            	//alert("Select atleast two dates for recurring event.");
            	return false;
            }
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
		
		// alert("starttd::"+strtd);
        }
    	return true;
 }
    
    function listEvent(selectedtype){    
    	if(!prePareDates()){
    		parent.hidePopup();
    		return;}    
   	   // prePareDates();
    	//alert(selectedtype);
    	if(selectedtype=='wysiwyg'){
    		var content=tinymce.get('editor').getContent();
    		var description=replaceSpecialChars(content);
    		var finaldescription=convert(description);
    		document.getElementById('descmsg').value=finaldescription;
    	}else{
    	 	var textdes=document.getElementById('textmsg').value;
    		var textdescription=replaceSpecialChars(textdes);
    		var finaltextdescription=convert(textdescription);
    		document.getElementById('textmsg').value=finaltextdescription;
    	 	}
    			var niceeditorhtml = tinymce.get('editor').getContent();    	
    			var content=niceeditorhtml;
    			var description=replaceSpecialChars(content);
    			var finaldescription=convert(description);
    			document.getElementById('msg').value=finaldescription;
    	/*var niceeditorhtml = document.getElementById('editor').innerHTML;    	
    		var content=niceeditorhtml;
    		var description=replaceSpecialChars(content);
    		var finaldescription=convert(description);
    		document.getElementById('msg').value=finaldescription;*/
    		var timezone=$('#timezone').val();
    		if(timezone=='1'){
    			$('#timezone').val('SystemV/PST8 Pacific Time');
    		}
    		
   		
        $.ajax( {
   type: "POST",
   url: 'createevent!listevent',
   data: $("#myform").serialize(),
   success: function( t ) {
   	   var json='',powertype='',eventid=''; 	      
       if(!isValidActionMessage(t)) return;
       if(t.indexOf('error')>-1){   	    
           $('#eventerrors').show();
           $(document).scrollTop('0');
           $('#eventerrors').html(t);
           $('.processing').hide();
           $('.continue').show();
           submitcount=0;
           return;
        }
       json=JSON.parse(t);
       powertype=json.powertype;
       eventid=json.eventid; 	
       document.getElementById('eventid').value=eventid;
       if(powertype=='ticketing'){ 	
    	// var country =  $('#country').val();
       var country = document.getElementById('country').value;
       window.location.href='/main/eventmanage/EventListPaymentSettings?eid='+eventid+'&msgKey=welcomeToPaymentSettings&purpose=eventlist&msg='+country;
       }
       else if(powertype=='rsvp'){
       window.location.href='/main/eventmanage/RSVPSettings?eid='+eventid;
       }
   
   }
        } );
 }
    
    
    $(document).on('click','.close,.cls',function(){
    	$('#timezonedata').hide();
    	$('#timezoneselect').html($('#timezonepopup').html());
    	$('#myModalhtmlin').hide();    
	});

});

function changeTimeZone(){
    //alert("hi this is::"+$('#timezone').val());
    $('#timezoneval').html($('#timezone').find('option:selected').text());
    var tempval=$('#timezone').val();
    //alert("before::");
    $('.close').trigger("click");
    
    $('#timezone').val(tempval);
    $('#timezoneval').attr('data-timezone',tempval);
    //alert($('#timezone').val());
}

/*function changeTimeZone(){
	//alert("hi this is::"+$('#timezone').val());
	$('#timezoneval').html($('#timezone option:selected').text());
	var tempval=$('#timezone').val();
	//alert("before::");
	$('.close').trigger("click");
	$('#timezone').val(tempval);
	//alert($('#timezone').val());
}*/

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


function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";	
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}
	
	var isValidActionMessage=function(messageText){		
		if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
		    	alert('You need to login to perform this action');
		    	window.location.href="../user/login";
		    	return false;
		    }
		 else if(messageText.indexOf('Something went wrong')>-1){
		 		alert('Sorry! You do not have permission to perform this action');
		 		YAHOO.ebee.popup.wait.hide();
		 		return false;
		 	} 
		    return true;	
		}
	function getListingPopup(mgrid,listingStatus){
		var url='/main/myevents/createevent!getListingPopup?mgrId='+mgrid+"&msgType="+listingStatus;
		getIframePopup(url,'listpopupscreen');
		}
	function getIframePopup(url,purpose){
		 $('#myModal').on('show.bs.modal', function () {
			 $('#myModal .modal-header').html('<button type="button" >&times;</button><h3>Message</h3>');
			 $('iframe#popup').attr("src",url);
	});

         $('#myModal').modal('show');    
    	    
		 
		        $('#myModal').on('hide.bs.modal', function () { 
		        $('iframe#popup').attr("src",'');			        
		        $('iframe#popup').css("height","300px");
		       // $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
		    });
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
		$('#myModalhtmlin .modal-footer').html($('<button class="btn btn-primary" onclick="changeTimeZone();" > <i class="fa fa-pencil-square-o"></i>'+props.global_submit_lbl+'</button><button class="btn btn-danger cls" ><i class="fa fa-times"></i>'+props.global_cancel_lbl+'</button>'));
		
	};
	
	
	var date_sort_asc = function (date1, date2) {
		  if (date1 > date2) return 1;
		  if (date1 < date2) return -1;
		  return 0;
		};
	  
	
	
	

			
		

