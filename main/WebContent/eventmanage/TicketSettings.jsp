<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<script src="/main/js/eventmanage/ticketsettings.js"></script>
<s:set name="ticketdisplay" value="%{submgr_permissions['DisplayTickets']}"></s:set>
<s:set name="venue" value="%{venueenable}"></s:set>
<%-- <div class="section-main-header"> <s:text name="mt.tkt.settingg.lbl"/> </div> --%>

	<div class="white-box bottom-gap"> 
	<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['ManageTickets']=='yes')}">
	<s:if test="%{#venue!='YES'}">
		<div class="row bottom-gap" id="eventcapacityDiv">
			<div class="col-md-12 col-sm-12 col-xs-12">
			<s:hidden id="eventcapacity" name="eventleveltktcount"></s:hidden>
			<label id='seteventcap' style="cursor: pointer;">
			<span title="<s:text name="up.pro.ticketing.lbl"/>">&nbsp;<s:text name="mt.event.capacity.lbl"/>  <%-- <span id="capacity">
			<s:property value="%{eventleveltktcount}" /></span> --%>
			</span>
			<span id="evtCapacitySpin" class="sm-font arrow-gap glyphicon glyphicon-menu-right" data-toggle="tickets_poiuytr" style="cursor: pointer;  margin-top: 3px;"></span>
			 		</label>
			</div>
			</div>
			
	<div class="row background-options" id="showCapacity" style="display:none;">
	
	<div class="col-md-12 col-sm-12">
		<div class="col-md-12 row sm-font" id="existError"></div>
		<div class="col-md-6 col-sm-8 row bottom-gap">
			<input type="text" class="form-control" id="enteredValue" placeholder='<s:text name="mt.event.capacity.lbl" />'>
		</div>
		<div class="col-md-4 col-sm-4">
			<span>
				<button type="button" class="btn btn-primary" onclick="success()"> <s:text name="global.save.btn.lbl"/> </button>
			</span>
			<span>
			<button class="btn btn-cancel" onclick="closeCapacity()">
				<i class="fa fa-times"></i>
			</button>
			</span>
		</div>
				
	</div>
	</div>		
	</s:if>		
	</s:if>		
		
		<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['DisplayTickets']=='yes')}">
		<div class="row">
		<div class="col-md-12 col-xs-12 col-xs-12">
		
		<label style="cursor: pointer;" id="ticketDisplay">
		<span title="<s:text name="up.pro.ticketing.lbl"/>">&nbsp;<s:text name="mt.display.tkt.lbl"/> </span>
		
		<span class="arrow-gap glyphicon glyphicon-menu-right sm-font" id="spinner" style="cursor: pointer;  margin-top: 3px;"></span>
		</label>
		
		</div>
		
		</div>
		<div class="row">
		<div class="background-options" id="displaytktdiv" style="display:none;"></div>
		</div>
		
		</s:if>
		
	</div>
<script>
$(document).ready(function(){
	$('#seteventcap').trigger('click');
});
var subTicketDisplay='${ticketdisplay}';
var eventlevelcount = '${eventleveltktcount}';
var eid = '${eid}';
var loaded=false;
$('#ticketDisplayAtttribs').click(function(e){
	specialFee(eventid,'200','DisplayTickets','Ticketing');
});

$('#ticketDisplay').click(function(){
	DisplayTicketsData();
})

function DisplayTicketsData(){

    loadingPopup();

     
       if(!loaded){
           url="DisplayTickets?eid="+${eid};
           $('#displaytktdiv').load(url,function(){
        	   if($('#displaytktdiv').is(':hidden'))
                   $('#spinner').removeClass('original').addClass('down');   
               	 else
               	 $('#spinner').removeClass('down').addClass('original');
                $('#displaytktdiv').slideDown();
                hidePopup();
             });
            loaded=true;
           
            
       }
       else{
           //alert("in loadin")
           if($('#displaytktdiv').is(':visible')){       
               $('#displaytktdiv').slideUp();
               $('#spinner').removeClass('down').addClass('original');
           }else{
               $('#displaytktdiv').slideDown();
               $('#spinner').removeClass('original').addClass('down');   
           }
           hidePopup();
           }
      
       
}
</script>