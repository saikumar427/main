<%@taglib uri="/struts-tags" prefix="s" %>

<style>
.ticketslist{
	background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #CCCCCC;
    border-radius: 4px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    color: #555555;
    display: block;
    font-size: 14px;
    height: 308px;
    line-height: 1.42857;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    vertical-align: middle;
    #width: 500px;
}
</style>
<s:form name="seatticketsform" action="Seating!updateSeatTickets" id="seatticketsform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="colorCode"></s:hidden>
<s:hidden name="venueid"></s:hidden>
<div class="col-xs-12 col-sm-12">
<div style="overflow:auto;" class="ticketslist"> 
<s:set value="%{alltickets.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
<div class="row"><div class="col-xs-12">
Currently there are no tickets added to the event.
</div></div>
</s:if>
<s:else>
<div class="row">
  
	<div class="col-xs-3">Seat type:</div>
	<div class="col-xs-9"><img src="../images/seatingimages/${colorCode}.png"></div>
</div>
<br/>
<div class="row">
  
	<div class="col-xs-3">Select Tickets</div>
	<div class="col-xs-9 sm-font">
	<div class="pull-right"><a href="#check" id="selectall">Select All</a> | <a href="#check" id="clearall">Clear All</a>
	</div>
	</div>
</div>
<br/>
<div class="row">
   
	<div class="col-xs-12">
		<s:iterator value="%{alltickets}" var="ticket" >
		<s:checkbox name="seltickets" id="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}"  cssClass="checkTickets"/>&nbsp;${value}<br/><br/>
		</s:iterator> 
	</div>
</div>
</s:else>
</div>
<br/>
<div class="row">
<div class="col-xs-4"></div>
<div class="col-xs-8">
<input type="button" class="btn btn-primary" value="Submit" onclick="submitSeatTicketsForm();">&nbsp;
<input type="button" class="btn btn-primary" value="Cancel">
</div>
</div>
</div>
</s:form>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/blue.css" />
<script>
function submitSeatTicketsForm(){
	$('#seatticketsform').submit();
}


$('#selectall').click(function(event) {  //on click
    $('.attFields').iCheck('check');
});

 $('#clearall').click(function(event) { 
 	 $('.attFields').iCheck('uncheck');      
});


$(function(){
	 $('input.checkTickets').iCheck({  
	 checkboxClass: 'icheckbox_square-blue', 
	 radioClass: 'iradio_square-blue'});
	 });
	 
	 
</script>
