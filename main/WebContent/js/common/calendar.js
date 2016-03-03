YAHOO.namespace("ebee.calendar");

var Event = YAHOO.util.Event,
Dom = YAHOO.util.Dom;

var cal_overlay;
var calendar_ebee;
function showDateCal(caltag){
	YAHOO.ebee.calendar.init(caltag);
}
YAHOO.ebee.calendar.init = function(caltag) {
            cal_overlay = createOverlayElement("overlayContainer_"+caltag,"ebeecalendar_"+caltag,caltag+"_btn_cal");
			cal_overlay.render();
			var jsondata = {"mid":caltag+"_month_field","did":caltag+"_day_field","yid":caltag+"_year_field"};
			calendar_ebee = createCalendarElement(cal_overlay,"ebeecalendar_"+caltag,jsondata);
			var mstr = document.getElementById(caltag+"_month_field").value;
			var dstr = document.getElementById(caltag+"_day_field").value;
			var ystr = document.getElementById(caltag+"_year_field").value;
			calendar_ebee.select(mstr + "/" + dstr + "/" + ystr);
			//calendar_ebee.cfg.setProperty("pagedate",mstr + "/" + ystr);
			calendar_ebee.render();				
			cal_overlay.show();
					
}
function createCalendarElement(overlayobj,calid,jsonObj){	
	var ebee_cal_obj = new YAHOO.widget.Calendar(calid,{iframe:false});
	/*calendar subscribe for select*/
	ebee_cal_obj.selectEvent.subscribe(function() { 
	      if (ebee_cal_obj.getSelectedDates().length > 0) {             
                   var dStr='',mStr='',yStr='';
                   var selDate = ebee_cal_obj.getSelectedDates()[0];
                   if(selDate=='Invalid Date'){  
                   //var date=new Date();
                   //dStr = date.getDate();
                   //mStr = date.getMonth()+1;
                  // yStr = date.getFullYear();
                   dStr='';
                   mStr='';
                   yStr='';
                   }else{
                   dStr = selDate.getDate();
                   mStr = selDate.getMonth()+1;
                   yStr = selDate.getFullYear();
                   }                   
              //YAHOO.ebee.calendar.cal1.select(mStr+"/"+dStr+"/"+yStr);
                   if(dStr<10 && dStr!='')
       	  			dStr = "0"+dStr;
   	  			   if(mStr<10 && mStr!='')
   		  			 mStr = "0"+mStr;
   		  			document.getElementById(jsonObj.mid).value = mStr;
					document.getElementById(jsonObj.did).value = dStr;
					document.getElementById(jsonObj.yid).value = yStr;
				}     	
               overlayobj.hide();
           }); 
	return ebee_cal_obj;
}
	
function createOverlayElement(overlayid,calid,buttonid){
	/*overlay object*/
	var overlay_cal_obj  = new YAHOO.widget.Overlay(overlayid,{visible:false,close:true});		
	overlay_cal_obj.setBody("<div id='"+calid+"'></div>");
	var showBtn = Dom.get(buttonid);
	Event.on(document, "click", function(e) {
           var el = Event.getTarget(e);
           var dialogEl = overlay_cal_obj.element;
           if (el != dialogEl && !Dom.isAncestor(dialogEl, el) && el != showBtn && !Dom.isAncestor(showBtn, el)) {
               overlay_cal_obj.hide();
           }
       });
	return overlay_cal_obj;		
}
	

	 