YAHOO.namespace("ebee.recurringevent.calendar");
	YAHOO.ebee.recurringevent.calendar.init = function() {
	
		if(YAHOO.ebee.recurringevent.calendar.cal1)
			YAHOO.ebee.recurringevent.calendar.cal1.close();
		YAHOO.ebee.recurringevent.calendar.cal1 = new YAHOO.widget.CalendarGroup("cal1","cal1Container", {PAGES:3, MULTI_SELECT: true});				
		
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
			$("rsvp_id").hide();
			//$("rsvpcontent").hide();
			$('powerWithTypeTicketing').checked=true;
			
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
			YAHOO.ebee.recurringevent.calendar.cal1.render();
		}
	}
	function showdates(){		
		var selcount=YAHOO.ebee.recurringevent.calendar.cal1.getSelectedDates().length;
		//selctOptions();
		removeOptionSelected();
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
          datesInfokey = mStr+"/"+dStr+"/"+yStr;
          datesInfovalue = wStr.substring(0,3) + ", " + dStr + " " + monthStr.substring(0,3) + " " + yStr ;        
          addOption(datesInfokey,datesInfovalue,selindex);
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
function removeOptionSelected(){
  var elSel = document.getElementById('seldates');
  var i;
  for (i = elSel.length - 1; i>=0; i--) {
    //if (elSel.options[i].selected) {        
      elSel.remove(i);
    //}
  }
}

function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}
function toggleMonthInCal(){
	var selectBox = document.getElementById("seldates");
	var date = selectBox.options[selectBox.selectedIndex].value;
	var dates_array =  date.split("/");
	YAHOO.ebee.recurringevent.calendar.cal1.cfg.setProperty("pagedate",dates_array[0] + "/" + dates_array[2]);
	YAHOO.ebee.recurringevent.calendar.cal1.render();	
}
function listEvent(type,isrecurring){
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
			return;
		}
	}
	if(type=="addevent")
		document.addeventform.submit();
	else
	    document.editeventform.submit();
	
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
	
		//$("rsvpcontent").hide(1000);
		$("ticketingcontent").show(1000);
		
	}else{
	$("submitBtn").update(' Create RSVP Event ');
	
		$("ticketingcontent").hide(1000);
		//$("rsvpcontent").show(1000);
		
	}
	
		
}

function removeSelectedDateFromCal(){
	
    var selectBox = document.getElementById("seldates");
    var seldate = selectBox.options[selectBox.selectedIndex].value;
    YAHOO.ebee.recurringevent.calendar.cal1.deselect(seldate);
    YAHOO.ebee.recurringevent.calendar.cal1.render();
    showdates();   
}