<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>


    <meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Multi-Page Calendar</title>

<style type="text/css">
/*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body {
	margin:0;
	padding:0;
}
</style>

<link rel="stylesheet" type="text/css" href="../build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="../build/calendar/assets/skins/sam/calendar.css" />
<script type="text/javascript" src="../build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="../build/calendar/calendar-min.js"></script>

<!--there is no custom header content for this example-->

</head>

<body class="yui-skin-sam">


<h1>Multi-Page Calendar</h1>

<div class="exampleIntro">
	<p>The CalendarGroup control is a specialized version of the Calendar control which allows you to display multiple months (pages) together. This example demonstrates how the set up a CalendarGroup control which displays three months at a time.</p><p>The code is very similar to that used to setup a basic Calendar and in general, all of the Calendar examples can be applied to a CalendarGroup also.</p>

</div>

<!--BEGIN SOURCE CODE FOR EXAMPLE =============================== -->

<div id="cal1Container"></div>

<script type="text/javascript">
	YAHOO.namespace("example.calendar");

	YAHOO.example.calendar.init = function() {
		YAHOO.example.calendar.cal1 = new YAHOO.widget.CalendarGroup("cal1","cal1Container", {PAGES:3, MULTI_SELECT: true });		
		YAHOO.example.calendar.cal1.select(3 + "/" + 21 + "/" + 2010); 
		YAHOO.example.calendar.cal1.select(3 + "/" + 25 + "/" + 2010); 
		YAHOO.example.calendar.cal1.select(4 + "/" + 21 + "/" + 2010); 
		YAHOO.example.calendar.cal1.cfg.setProperty("pagedate", 4 + "/" + 2010); 
		YAHOO.example.calendar.cal1.render();
		function myClickHandler(type,args,obj) {
			
		};

		YAHOO.example.calendar.cal1.selectEvent.subscribe(myClickHandler, YAHOO.example.calendar.cal1, true);
		YAHOO.example.calendar.cal1.deselectEvent.subscribe(myClickHandler, YAHOO.example.calendar.cal1, true);
	}

	YAHOO.util.Event.onDOMReady(YAHOO.example.calendar.init);
</script>
<script>
function showdates(){
var selcount=YAHOO.example.calendar.cal1.getSelectedDates().length;



							                      for(var selindex=0;selindex<selcount;selindex++){
							                        var selDate = YAHOO.example.calendar.cal1.getSelectedDates()[selindex];
													//alert(selDate);

							                        var wStr = YAHOO.example.calendar.cal1.cfg.getProperty("WEEKDAYS_LONG")[selDate.getDay()];
							                        var dStr = selDate.getDate();
							                        var mStr = YAHOO.example.calendar.cal1.cfg.getProperty("MONTHS_LONG")[selDate.getMonth()];
							                        var yStr = selDate.getFullYear();

							                        alert( wStr + ", " + dStr + " " + mStr + " " + yStr);
							                        }
}
</script>
<div style="clear:both"></div>
<input type="button" value="show selected Dates" onclick="showdates();"/>  
<!--END SOURCE CODE FOR EXAMPLE =============================== -->

</body>
</html>
