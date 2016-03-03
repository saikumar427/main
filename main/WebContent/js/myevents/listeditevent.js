var _type='',_isrecurring='',_selectedtype='',_purpose='';
YAHOO.namespace("ebee.recurringevent.calendar");
	YAHOO.ebee.recurringevent.calendar.init = function() {
	
		if(YAHOO.ebee.recurringevent.calendar.cal1)
			YAHOO.ebee.recurringevent.calendar.cal1.close();
		YAHOO.ebee.recurringevent.calendar.cal1 = new YAHOO.widget.CalendarGroup("cal1","cal1Container", {PAGES:2, MULTI_SELECT: false});				
		
		function myClickHandler(type,args,obj) {			
			showdates();
		};

		YAHOO.ebee.recurringevent.calendar.cal1.selectEvent.subscribe(myClickHandler, YAHOO.ebee.recurringevent.calendar.cal1, true);
		YAHOO.ebee.recurringevent.calendar.cal1.deselectEvent.subscribe(myClickHandler, YAHOO.ebee.recurringevent.calendar.cal1, true);
		
	}
	
	YAHOO.util.Event.onDOMReady(YAHOO.ebee.recurringevent.calendar.init);
	
	

	function showCal(){
		
		if(document.getElementById('isRecurring').checked==true){
			//document.getElementById('EditEventData.seeRecurring').value="YES";
			$("startdate").hide();
			$("enddate").hide();
			$("recurdates").show();
			//$("rsvp_id").hide();
			//$("rsvpcontent").hide();
			//$('powerWithTypeTicketing').checked=true;
			
			//$("submitBtn").update('Continue To Payment Processing Settings');
			
			//$("recurdates").effect("bounce", { times:3 }, 400);	
			
			YAHOO.ebee.recurringevent.calendar.cal1.render();
		}
		else{
			//document.getElementById('EditEventData.seeRecurring').value="NO";
			$("startdate").show();
			$("enddate").show();
			$("recurdates").hide();
			$("rsvp_id").show();
		}	
	}
	function showRecurringEventCalendar(isrecurring){
		if(isrecurring=="true"){
			document.getElementById('isRecurring').checked='checked';
			$("startdate").hide();
			$("enddate").hide();
			$("recurdates").show();						
			YAHOO.ebee.recurringevent.calendar.cal1.render();
		}
	}
	selectedDatehash=[];
	
	var keysofsdh=[];
	
	function showdates(){		
		var selcount=YAHOO.ebee.recurringevent.calendar.cal1.getSelectedDates().length;
		selctOptions();
		var datesInfokey="";
		var datesInfovalue="";
		var starthour=document.getElementById('editEventData.startHour').value;
		var startminute=document.getElementById('editEventData.startminute').value;
		var startampm=document.getElementById('editEventData.startampm').value;
		var endhour=document.getElementById('editEventData.endHour').value;
		var endminute=document.getElementById('editEventData.endminute').value;
		var endampm=document.getElementById('editEventData.endampm').value;
		
		var st_time,ed_time;
		$("recurdates").show();
		
		if(startampm == "AM")
		{	
			if(starthour == '12')
			{
				st_time = 00+":"+startminute;
			}
			else
			{
				st_time=starthour+":"+startminute;
			}
			
		}
		else
		{
			starthour=starthour + 12;
			if(starthour == 24)
				starthour="00";
			
			st_time = starthour +":"+startminute;
			
		}
		if(endampm == "AM")
		{	
			if(endhour == '12')
			{
				ed_time = 00+":"+endminute;
			}
			else
			{
				ed_time=endhour+":"+endminute;
			}
			
		}
		else
		{
			endhour=endhour + 12;
			if(endhour == 24)
				endhour="12";
			
			ed_time = endhour +":"+endminute;
			
		}
		
		//removeOptionSelected();
		var start_time = document.getElementById('editEventData.startHour').value+":"+document.getElementById('editEventData.startminute').value+" "+document.getElementById('editEventData.startampm').value;
		var end_time = document.getElementById('editEventData.endHour').value+":"+document.getElementById('editEventData.endminute').value+" "+document.getElementById('editEventData.endampm').value;
		
				 
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
          return ; 
          
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
	function retainoptions()
	{
		//document.getElementById('editEventData.hours').value=09;
		//document.getElementById('editEventData.endhours').value=10;
		var selectBox = document.getElementById("seldates");
		for (var i = 0; i < selectBox.options.length; i++) {
			datesInfovalue = selectBox.options[i].text;			
			datesInfokey = selectBox.options[i].value;
		if(selectedDatehash[datesInfokey]) {
	          return;
	          
	          }
	          keysofsdh.push(datesInfokey);
	          selectedDatehash[datesInfokey]=datesInfovalue;
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
	showCal();
	sellist = document.getElementById("seldates");	
	for(i=0;i<sellist.options.length;i++){
		 keysofsdh.push(sellist.options[i].value);
         selectedDatehash[sellist.options[i].value]=sellist.options[i].text;
         	
	}
	
}
/*
function checkrspv(powertype)
{	
	
	if(powertype == 'RSVP')
		document.getElementById('isRecurring').disabled = true;
		
	else
		document.getElementById('isRecurring').disabled = false;
	
	
}*/
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

function listEvent(type,isrecurring,selectedtype){
	var parent = document.getElementById("optlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("seldates");	
	var eventid=document.getElementById('eventid').value;
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
			return;
		}
	}
	if(type=="editevent")
	{
	if(selectedtype=='wysiwyg'){
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
	$('editeventform').request({
        onSuccess: function(t) {
 	       var json='',status='';
 	       var msg=t.responseText;
 	       if(!isValidActionMessage(t.responseText)) return;
 	       if(msg.indexOf('errorMessage')>-1){
 	       if(msg.indexOf('askcard')>-1){
 	       YAHOO.ebee.popup.wait.hide();
 	       var mgrid=document.getElementById('managerid').value;
 	       _purpose="EditEvent";
 	       _type=type;
	       _isrecurring=isrecurring;
	       _selectedtype=selectedtype;
 	       getcc(mgrid,'editevent');
 	       }
 	       }else{
 	       json=eval('('+t.responseText+')');
 	       status=json.status;
 	       }
 	       if(status=='success'){
 	       YAHOO.ebee.popup.wait.hide();
 	       window.location.href='/main/eventmanage/Snapshot?eid='+eventid;
 	       }else{
 	       if(msg.indexOf('askcard')>-1){}
 	       else{
 	       YAHOO.ebee.popup.wait.hide();
 	       $('eventerrors').scrollTo();
 	       $('eventerrors').update(t.responseText);}
 	       }
 	        },onFailure:function(t){
 	          YAHOO.ebee.popup.wait.hide();
 	          alert("Failure");
 	        }
    });
	}
} 
function selectDate(seldate){
	YAHOO.ebee.recurringevent.calendar.cal1.select(seldate);
	YAHOO.ebee.recurringevent.calendar.cal1.render();
}
function reloadeditors(eventmanagetype){
	var selectedtype='';
	if(eventmanagetype=="addevent"){
		options=document.forms['addeventform'].elements['addEventData.descriptiontype'];
	}else{
		options=document.forms['editeventform'].elements['editEventData.descriptiontype'];
	}
	
	for(i=0;i<options.length;i++){
	opt=options[i];
	if(opt.checked) selectedtype=opt.value;
	}
	if(selectedtype=='text' || selectedtype=='html'){		
			$("fckdesccontent").hide(1000);
			$("fckdesctxtcontent").show(1000);
	}else{		
			$("fckdesctxtcontent").hide(1000);
	  		$("fckdesccontent").show(1000);
			
	}
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
	$("submitBtn").update('Continue To Payment Processing Settings');
	
		$("rsvpcontent").hide(1000);
		$("ticketingcontent").show(1000);
		
	}else{
	$("submitBtn").update(' Create RSVP Event ');
	
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

