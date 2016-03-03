	var _type='',_isrecurring='',_selectedtype='',_purpose='';
	
	YAHOO.namespace("ebee.recurringevent.calendar");
	YAHOO.ebee.recurringevent.calendar.init = function() {
	
		if(YAHOO.ebee.recurringevent.calendar.cal1)
			YAHOO.ebee.recurringevent.calendar.cal1.close();
		YAHOO.ebee.recurringevent.calendar.cal1 = new YAHOO.widget.CalendarGroup("cal1","cal1Container", {PAGES:3, MULTI_SELECT: false});				
		
		function myClickHandler(type,args,obj) {			
			showdates();
		};

		YAHOO.ebee.recurringevent.calendar.cal1.selectEvent.subscribe(myClickHandler, YAHOO.ebee.recurringevent.calendar.cal1, true);
		YAHOO.ebee.recurringevent.calendar.cal1.deselectEvent.subscribe(myClickHandler, YAHOO.ebee.recurringevent.calendar.cal1, true);
		
	}
	
	YAHOO.util.Event.onDOMReady(YAHOO.ebee.recurringevent.calendar.init);
	
	

	function showCal(){
		
		if(document.getElementById('isRecurring').checked==true){
			
			$("startdate").hide();
			$("enddate").hide();
			$("recurdates").show();
			//$("rsvp_id").hide();
			//$("rsvpcontent").hide();
			//$('powerWithTypeTicketing').checked=true;
			
			$("submitBtn").update('Continue To Payment Processing Settings');
			
			//$("recurdates").effect("bounce", { times:3 }, 400);	
			
			YAHOO.ebee.recurringevent.calendar.cal1.render();
		}
		else{
			$("startdate").show();
			$("enddate").show();
			$("recurdates").hide();
			$("rsvp_id").show();
		}	
	}
	function showRecurringEventCalendar(isrecurring){
		if(isrecurring=="true"){
			$("startdate").hide();
			$("enddate").hide();
			$("recurdates").show();						
			//YAHOO.ebee.recurringevent.calendar.cal1.render();
		}
	}
	
	function showdates(){		
		var selcount=YAHOO.ebee.recurringevent.calendar.cal1.getSelectedDates().length;
		selctOptions();
		
		var options=document.getElementById('seldates');
		var st_time,ed_time;
		$("recurdates").show();
		var starthour=document.getElementById('addEventData.starthour').value;
		var startminute=document.getElementById('addEventData.startminute').value;
		var startampm=document.getElementById('addEventData.stampm').value;
		var endhour=document.getElementById('addEventData.endhour').value;
		var endminute=document.getElementById('addEventData.endminute').value;
		var endampm=document.getElementById('addEventData.endampm').value;
		
		//var st_time,ed_time;
		$("recurdates").show();
		
		if(startampm == "AM")
		{	
			if(starthour == '12')
			{
				st_time = starthour+":"+startminute;
			}
			else
			{
				st_time=starthour+":"+startminute;
			}
			
		}
		else
		{
			starthour=Number(starthour) + 12;
			if(starthour == 24)
				starthour="00";
			
			st_time = starthour +":"+startminute;
			
		}
		if(endampm == "AM")
		{	
			if(endhour == '12')
			{
				ed_time = endhour+":"+endminute;
			}
			else
			{
				ed_time=endhour+":"+endminute;
			}
			
		}
		else
		{
			endhour=Number(endhour) + 12;
			if(endhour == 24)
				endhour="12";
			
			ed_time = endhour +":"+endminute;
			
		}
		
		/*
		if(document.getElementById('addEventData.stampm').value == "AM")
		{
			 st_time=document.getElementById('addEventData.starthour').value+":"+document.getElementById('addEventData.startminute').value;
		}
		else
		{
			var st_hour = document.getElementById('addEventData.starthour').value;
			for(i=1;i<=12;i++)
			{
				st_hour++;
			}
			if(st_hour == 24)
				st_hour="00";
			
			st_time = st_hour +":"+document.getElementById('addEventData.startminute').value;
			
		}
		if(document.getElementById('addEventData.endampm').value == "AM")
		{
			 ed_time=document.getElementById('addEventData.endhour').value+":"+document.getElementById('addEventData.endminute').value;
		}
		else
		{
			var ed_hour = document.getElementById('addEventData.endhour').value;
			for(i=1;i<=12;i++)
			{
				ed_hour++;
			}
			
			ed_time = ed_hour +":"+document.getElementById('addEventData.endminute').value;
		}*/
		//removeOptionSelected();
		var start_time = document.getElementById('addEventData.starthour').value+":"+document.getElementById('addEventData.startminute').value+" "+document.getElementById('addEventData.stampm').value;
		var end_time = document.getElementById('addEventData.endhour').value+":"+document.getElementById('addEventData.endminute').value+" "+document.getElementById('addEventData.endampm').value;
		
		var datesInfokey="";
		var datesInfovalue="";		 
       for(var selindex=0;selindex<selcount;selindex++){
          var selDate = YAHOO.ebee.recurringevent.calendar.cal1.getSelectedDates()[selindex];
          var wStr = YAHOO.ebee.recurringevent.calendar.cal1.cfg.getProperty("WEEKDAYS_LONG")[selDate.getDay()];
          var dStr = selDate.getDate();          
          var monthStr = YAHOO.ebee.recurringevent.calendar.cal1.cfg.getProperty("MONTHS_LONG")[selDate.getMonth()];
          var mStr = selDate.getMonth() + 1;
          var yStr = selDate.getFullYear();
          var seldate = mStr + "-" + dStr + "-" + yStr;
          if(dStr<10)
        	  dStr = "0"+dStr;
    	  if(mStr<10)
    		  mStr = "0"+mStr;
          
          var st_date = yStr+"-"+mStr+"-"+dStr;
          datesInfokey = st_date+"/"+st_time+"/"+start_time+"/"+st_date+"/"+ed_time+"/"+end_time;
          datesInfovalue = dStr + " " + monthStr.substring(0,3) + " " + yStr +"  " + start_time +"-" + end_time +" ("+wStr.substring(0,3) + ")" ;        
          //alert(datesInfokey);
          
          if(selectedDatehash[datesInfokey]) {
          return;
          
          }
          
          keysofsdh.push(datesInfokey);
          selectedDatehash[datesInfokey]=datesInfovalue;
          
          
          
          keysofsdh.sort();
          removeallOptions();
          var elSel = document.getElementById('seldates');
          for(i=0; i<keysofsdh.length;i++)
          {
        	  addOption(keysofsdh[i],selectedDatehash[keysofsdh[i]],elSel.length);  
          }
          /*
          var elSel = document.getElementById('seldates');
          var i;
          
    	  var isalreadyadded=false;
    	for (i=0;i<elSel.length;i++)
    	{
    	if (elSel.options[i].value == datesInfokey)       
    		isalreadyadded=true; 
    	}
    	if(!isalreadyadded)
    		
    		addOption(datesInfokey,datesInfovalue,elSel.length);	
      */
           
       }
	}
	
function addOption(key,value,index){
	var optn = document.createElement("OPTION");
	if(index%2==0)
		optn.className = "opteven";
	else
		optn.className = "optodd";
	optn.text = value;
	optn.value = key;	
	document.getElementById("seldates").options.add(optn);
}
function selctOptions() {
	sellist = document.getElementById("seldates");	
	for(i=0;i<sellist.options.length;i++){
		sellist.options[i].selected = true;
	}
}
function fillRecurringdates()
{
	sellist = document.getElementById("seldates");	
	for(i=0;i<sellist.options.length;i++){
		 
		 keysofsdh.push(sellist.options[i].value);
         selectedDatehash[sellist.options[i].value]=sellist.options[i].text;
        	
	}
	
}
function removeOptionSelected(){
	var elSel = document.getElementById('seldates');
	var i;
	for (i = elSel.length - 1; i>=0; i--) {
	if (elSel.options[i].selected) {  
		
		delete selectedDatehash[elSel.options[i].value];
	  elSel.remove(i);
	  	
		keysofsdh.splice(i,1);
	}
	}
	}
function removeallOptions(){
	var elSel = document.getElementById('seldates');
	var i;
	for (i = elSel.length - 1; i>=0; i--) {
	  elSel.remove(i);
	  

	}
	}

function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	//alert(index);
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}
/*
function toggleMonthInCal(){
	var selectBox = document.getElementById("seldates");
	var date = selectBox.options[selectBox.selectedIndex].value;
	var dates_array =  date.split("/");
	YAHOO.ebee.recurringevent.calendar.cal1.cfg.setProperty("pagedate",dates_array[0] + "/" + dates_array[2]);
	YAHOO.ebee.recurringevent.calendar.cal1.render();	
}*/
function selectDate(seldate){
	YAHOO.ebee.recurringevent.calendar.cal1.select(seldate);
	YAHOO.ebee.recurringevent.calendar.cal1.render();
}


function listEvent(type,isrecurring,selectedtype){
	var parent = document.getElementById("optlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("seldates");	
	if(isrecurring==true){
		if(selectBox.options.length > 1){
			for (var i = 0; i < selectBox.options.length; i++) {
				var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
				var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
				parent.appendChild(obj1);
				parent.appendChild(obj2);
			}			
		}else{
			document.getElementById("seldates").focus();
			alert("Select atleast two dates for recurring event.");
			YAHOO.ebee.popup.wait.hide();
			document.getElementById('submitBtn').style.display='block';
	        document.getElementById('submitBtn2').style.display='none';
			return;
		}
	}
	if(type=="addevent"){
	if(selectedtype=='wysiwyg'){
	  if(document.getElementById("fckdesccontent"))
       { var inputs = document.getElementById("fckdesccontent").getElementsByTagName("img");
           for(var i=0;i<inputs.length;i++)
            {
            if(inputs[i].getAttribute("width")>600)
            inputs[i].setAttribute("width","625");    
            }
       }
    var content=nicEditors.findEditor('msg').getContent();
	var description=replaceSpecialChars(content);
	var finaldescription=convert(description);
	document.getElementById('msg').value=finaldescription;
	}else{
 	var textdes=document.getElementById('textmsg').value;
	var textdescription=replaceSpecialChars(textdes);
	var finaltextdescription=convert(textdescription);
	document.getElementById('textmsg').value=finaltextdescription;
 	}
 	//document.addeventform.submit();
 	
 	  var url='/main/myevents/createevent!getEventsCount';
				   new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	        if(!isValidActionMessage(t.responseText)) return;
	    	        var data=t.responseText;
			       var json=eval('('+data+')');
			       var listingStatus = json.listingStatus;				      		      
			       var mgrid = json.mgrid;
			      if(listingStatus.indexOf('donot_allow')>-1)
			       	 getListingPopup(mgrid,listingStatus);	
	    	       else
	    	       addevent();
	    	      },onFailure:function(){alert("Failure");}
	    	    }); 
 	
 	/*$('addeventform').request({
 	       onSuccess:function(t){
 	       var msg=t.responseText;
 	       var json='',powertype='',eventid='';
 	       //YAHOO.ebee.popup.wait.hide();
 	       if(!isValidActionMessage(t.responseText)) return;
 	       if(msg.indexOf('errorMessage')>-1){
 	       if(msg.indexOf('askcard')>-1){
 	        var mgrid=document.getElementById('managerid').value;
 	        _purpose='ListEvent';
 	        _type=type;
	        _isrecurring=isrecurring;
	        _selectedtype=selectedtype;
 	        document.getElementById('addeventform').action='createevent!insertEventListTrack'
 	         $('addeventform').request({
 	         onSuccess:function(t){
 	         
 	         document.getElementById('addeventform').action='createevent!listevent';
 	         },onFailure:function(){
 	         j=0;
 	         alert("failure");
 	        }
 	        });
 	         getcc(mgrid,'eventlisting');
 	         
 	       }
 	       }else{
 	       json=eval('('+t.responseText+')');
 	       powertype=json.powertype;
 	       eventid=json.eventid;
 	       }
 	       document.getElementById('eventid').value=eventid;
 	       if(powertype=='ticketing'){ 	     
 	       window.location.href='/main/eventmanage/EventListPaymentSettings?eid='+eventid+'&msgKey=welcomeToPaymentSettings&purpose=eventlist';
 	       }
 	       else if(powertype=='rsvp'){
 	       window.location.href='/main/eventmanage/RSVPSettings?eid='+eventid;
 	       }
 	       else{
 	       j=0;
 	       $('eventerrors').scrollTo();
 	       $('eventerrors').update(t.responseText);
 	       document.getElementById('submitBtn2').style.display='none';
 	       document.getElementById('submitBtn').style.display='block';}
 	       },onFailure:function(){
 	         j=0;
 	         //YAHOO.ebee.popup.wait.hide();
 	         alert("failure");
 	        }
    });*/
	}
}

/*function checklisting(){
  var url='/main/myevents/createevent!getEventsCount';
				   new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	        if(!isValidActionMessage(t.responseText)) return;
	    	        var data=t.responseText;
			       var json=eval('('+data+')');
			       var listingStatus = json.listingStatus;
			       var mgrid = json.mgrid;
			       if(listingStatus=='donot_allow')
	    	       getListingPopup(mgrid);
	    	       else
	    	       addevent();
	    	      },onFailure:function(){alert("Failure");}
	    	    }); 
}*/


function addevent(){
$('addeventform').request({
 	       onSuccess:function(t){
 	       var msg=t.responseText;
 	       var json='',powertype='',eventid='';
 	       //YAHOO.ebee.popup.wait.hide();
 	       if(!isValidActionMessage(t.responseText)) return;
 	       if(msg.indexOf('errorMessage')>-1){
 	       if(msg.indexOf('askcard')>-1){
 	        var mgrid=document.getElementById('managerid').value;
 	        _purpose='ListEvent';
 	        _type=type;
	        _isrecurring=isrecurring;
	        _selectedtype=selectedtype;
 	        document.getElementById('addeventform').action='createevent!insertEventListTrack'
 	         $('addeventform').request({
 	         onSuccess:function(t){
 	         
 	         document.getElementById('addeventform').action='createevent!listevent';
 	         },onFailure:function(){
 	         j=0;
 	         alert("failure");
 	        }
 	        });
 	         getcc(mgrid,'eventlisting');
 	         
 	       } 
 	       }else{
 	       json=eval('('+t.responseText+')');
 	       powertype=json.powertype;
 	       eventid=json.eventid;
 	       }
 	       document.getElementById('eventid').value=eventid;
 	       if(powertype=='ticketing'){ 	     
 	       window.location.href='/main/eventmanage/EventListPaymentSettings?eid='+eventid+'&msgKey=welcomeToPaymentSettings&purpose=eventlist';
 	       }
 	       else if(powertype=='rsvp'){
 	       window.location.href='/main/eventmanage/RSVPSettings?eid='+eventid;
 	       }
 	       else{
 	       j=0;
 	       $('eventerrors').scrollTo();
 	       $('eventerrors').update(t.responseText);
 	       document.getElementById('submitBtn2').style.display='none';
 	       document.getElementById('submitBtn').style.display='block';}
 	       },onFailure:function(){
 	         j=0;
 	         //YAHOO.ebee.popup.wait.hide();
 	         alert("failure");
 	        }
    });

}


var _ms_XMLHttpRequest_ActiveX;
var year1;
var month1;
var day1;
function displayCalendar(anchor,yearelm,monthelm,dayelm,now){
year1=yearelm;
month1=monthelm;
day1=dayelm;
var cal = new CalendarPopup();
cal.setReturnFunction("setValues");
cal.showCalendar(anchor,now);
}
function setValues(y,m,d) {
  	 document.getElementById(year1).value=y;
	 document.getElementById(month1).value=LZ(m);
	 document.getElementById(day1).value=LZ(d);
}
function registrationsettings(type){
	if(type=='ticketing'){
	//document.getElementById('nts').style.display='block';
	$("submitBtn").update('Continue To Payment Processing Settings');
	if($("rsvpcontent"))
		$("rsvpcontent").hide(1000);
		$("ticketingcontent").show(1000);
		
	}else{
	//document.getElementById('nts').style.display='none';
	$("submitBtn").update('Create RSVP Event');
		if($("ticketingcontent"))
		$("ticketingcontent").hide(1000);
		$("rsvpcontent").show(1000);
	}
	
		
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

function removeSelectedDateFromCal(){
	
    var selectBox = document.getElementById("seldates");
    var seldate = selectBox.options[selectBox.selectedIndex].value;
    YAHOO.ebee.recurringevent.calendar.cal1.deselect(seldate);
    YAHOO.ebee.recurringevent.calendar.cal1.render();
    showdates();   
}

function ntssettings(radio){
if(radio.value=='Y'){
	document.getElementById('ntscomm').style.display='block';
}
if(radio.value=='N'){
	document.getElementById('ntscomm').style.display='none';
}
}

function getcc(rfid,purp)
{
  var Msg="Please fill out your credit card information to activate your account, this is required to prevent hosting fake events using our system!";
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData?refId="+rfid);
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle("Credit Card Details");
  setkey.setSoftdis("");
  setkey.setRefid(rfid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }

}

function onsucessclose(){ 
   closeEBpopup();
   responseDatacc();
   
}

function getListingPopup(mgrid,listingStatus){
var url='/main/myevents/createevent!getListingPopup?mgrId='+mgrid+"&msgType="+listingStatus;
loadURLBasedWindow(url,listpopupscreen);
}



var listpopupscreen = function (responseObj){
	if(!isValidActionMessage(responseObj.responseText)) return;	
	YAHOO.ebee.popup.dialog.setHeader("Message");
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);	
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){		
			window.location.href='/main/myevents/createevent';
	});
	adjustYUIpopup();
}




