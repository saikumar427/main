<%@taglib uri="/struts-tags" prefix="s"%>
	<div class=" row col-md-6 col-sm-6" id="attendeereportsfilters" style="display:none;">
		<div class="row">			
			<h4>Search Filter</h4>			
		</div>
		<div class="row">
			<s:if test="%{pendingApproval > 0 || recPendingAppSize > 0}">		
			<div id="showattdiv" class="taskcontent" <s:if test="%{pendingApproval == 0 && recPendingAppSize > 0}">style="display:none;"</s:if><s:else>style="display:block;"</s:else>>
			<s:if test="%{pendingApproval == 1 && recPendingAppSize == 0}">
			1 transaction is waiting for your approval
			</s:if>
			<s:elseif test="%{pendingApproval > 1 && recPendingAppSize == 0}"><s:property value="pendingApproval"/> transactions are waiting for your approval</s:elseif>
			<s:elseif test="%{pendingApproval == 0 && recPendingAppSize > 0}"><span id="attpacount"></span></s:elseif>
			<input type="button" name="Submit" id="showattbtn" value="Show"/>
			</div>		
			</s:if>
		</div>
		
		<div class="row">Source<s:set name="attendeecheck" value="reportsData.attselindex"></s:set></div>
		<div style="height:5px"></div>
		<div class="">
		<div class="col-sm-12">
			<input type="radio" name="attselindex" value="1" class="allsource"
					<s:if test="%{#attendeecheck == 1}">checked='checked'</s:if> />&nbsp;All
		</div>
		<div class="col-sm-12">
			<input type="radio" name="attselindex" value="2" class="purchase"
					<s:if test="%{#attendeecheck == 2}">checked='checked'</s:if> />&nbsp;Purchased Online
		</div>
		<div class="col-sm-12">
			<input type="radio" name="attselindex" value="3" class="addmanual"
					<s:if test="%{#attendeecheck == 3}">checked='checked'</s:if> />&nbsp;Added Manually
		</div>
		
		<div class="row">Dates</div>
		<div class="col-sm-12">
			<input type="radio" name="reportsData.attdatetype" value="1" class="alldates" 
			<s:if test="%{reportsData.attdatetype == 1}">checked='checked'</s:if> />&nbsp;All
		</div>
		<div class="col-sm-6">
			<input type="radio" id="startdateradio" name="reportsData.attdatetype" value="2" class="sdate"/>
			<s:if test="%{reportsData.attdatetype == 2}">checked='checked'</s:if> Start Date
		</div>
		<div class="col-sm-6" style="margin-bottom: 5px;">
			<input class="form-control" id="start" type="text" name="reportsData.eventAttStartDate" />
		</div>
		<div class="col-sm-6" >
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End Date
		</div>
		<div class="col-sm-6">
			<input type="text" class="form-control" id="end" name="reportsData.eventAttEndDate"/>
		</div>
		
		<div class="row">More Options &nbsp;<span id="showhide"><i class="fa fa-chevron-circle-down"></i></span></div>
			<div class="col-xs-12 col-sm-12" id="advanced" style="display:none;">
				<s:set name="typecheck" value="reportsData.typeindex"></s:set>
				<div class="row">Ticket Type</div>
				<div class="col-xs-12 col-sm-12">
					<input type="radio" name="typeindex" value="1" class="alltickets"
					<s:if test="%{#typecheck == 1}">checked='checked'</s:if> />&nbsp;All Tickets
				</div>
				<div class="col-xs-12 col-sm-12">
					<input type="radio" name="typeindex" value="2" class="allattdtckts"
					<s:if test="%{#typecheck == 2}">checked='checked'</s:if> />&nbsp;All Attendee Tickets
				</div>
			</div>
			
	</div>
	</div>
	<div class="  col-md-6 col-sm-6" id="attendeeReportsDisplayFields" style="display:none;">
		<div class="row">
		<div class="col-sm-6 col-xs-6">
			<h4>Display Fields Filter</h4>
		</div>
		<div class="col-sm-6 col-xs-6" style="padding-top:7px">
			 <a  name="CheckAll" href="javascript:;" id="selectall">Select All</a>  
			<a  name="UnCheckAll" href="javascript:;" id="clearall">Clear All</a>
		</div>
		</div>
	<div class="row col-xs-6">
		<input type="checkbox" name="attFields"  class="attFields tdate" value="TD"
					<s:if test="%{attFields.contains('TD')}">checked</s:if>></input>&nbsp;Transaction	Date
	</div>
	<div class="col-xs-6">
		<input type="checkbox" name="attFields" id="attendeetid" class="attFields tid" value="TID"
					<s:if test="%{attFields.contains('TID')}">checked</s:if>></input>&nbsp;Transaction ID
	</div>
	<div class="row col-xs-6">
		<input type="checkbox" name="attFields" value="FN"  class="attFields fname"
					<s:if test="%{attFields.contains('FN')}">checked</s:if>></input>&nbsp;First Name
	</div>
	<div class="col-xs-6">
		<input type="checkbox" name="attFields" value="LN"  class="attFields lname"
					<s:if test="%{attFields.contains('LN')}">checked</s:if>></input>&nbsp;Last Name
	</div>
	<div class="row col-xs-6">
		<input type="checkbox" name="attFields" value="EN" class="attFields ename"
					<s:if test="%{attFields.contains('EN')}">checked</s:if>></input>&nbsp;Event Name
	</div>
	<div class="col-xs-6">
		<input type="checkbox" name="attFields" value="TN"  class="attFields tname"
					<s:if test="%{attFields.contains('TN')}">checked</s:if>></input>&nbsp;Ticket Name
	</div>
	<div class=" row col-sm-12 col-xs-12">
		More Options &nbsp;<span id="show"><i class="fa fa-chevron-circle-down"></i></span>
	</div>
	
	<div class="row col-sm-12 col-xs-12" id="adv" style="display: none;">
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields" class="attFields tp" value="TP"	<s:if test="%{attFields.contains('TP')}">checked</s:if>></input>&nbsp;Ticket Price
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields"  class="attFields be" value="BE" <s:if test="%{attFields.contains('BE')}">checked</s:if>></input>&nbsp;Buyer Email
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields" class="attFields em" value="Em" 	<s:if test="%{attFields.contains('Em')}">checked</s:if>></input>&nbsp;Email
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields"  class="attFields ph" value="Ph" <s:if test="%{attFields.contains('Ph')}">checked</s:if>></input>&nbsp;Phone
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields"	 class="attFields onumb"  value="ON" 	<s:if test="%{attFields.contains('ON')}">checked</s:if>></input>&nbsp;Order Number
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields"  class="attFields ak" value="AK" <s:if test="%{attFields.contains('AK')}">checked</s:if>></input>&nbsp;Attendee	Key
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields" class="attFields bn" value="BN" 	<s:if test="%{attFields.contains('BN')}">checked</s:if>></input>&nbsp;Buyer Name
		</div>
		<div class="col-sx-6 col-sm-6">
			<input type="checkbox" name="attFields" class="attFields bp" value="BP"	<s:if test="%{attFields.contains('BP')}">checked</s:if>></input>&nbsp;Buyer Phone
		</div>	

		<s:if test="%{configValue == 'YES'}">
		<div class="col-sx-12 col-sm-12">
		<input type="checkbox" name="attFields"  class="attFields sc" value="SC" 	<s:if test="%{attFields.contains('SC')}">checked</s:if>></input>&nbsp;Seat	Code
		</div>
		</s:if>	
	
	</div>
	
	</div>
		 
<script>
var expan=false;
var uncheckfields={"TP":1,"BE":2,"Em":3,"Ph":4,"ON":5,"AK":6,"BN":7,"SC":8,"1":9,"2":10,"3":11,"4":12,"5":13,"6":14,"7":15,"8":16,"9":17,"10":18,"11":19,"12":20,"13":21,"14":22,"15":23,"16":24,"17":25,"18":26,"19":27,"20":28,"21":29,"22":30,"23":31,"24":32,"25":33,"26":34,"27":35,"28":36,"29":37,"30":38,"BP":39};


$(document).ready(function()
		{
	$('#start').on('focus', function() {
        $('#startdateradio').iCheck('check');
    });
	
	$('#end').on('focus', function() {
        $('#startdateradio').iCheck('check');
    });
		});

     $('#selectall').click(function(event) {  //on click
        //$('.attFields').iCheck('check');
        $('input[name="attFields"]').each(function(){
            if(!expan){
                    if(uncheckfields[$(this).val()]==undefined)
                         $(this).iCheck('check');
                 }
                 else
                         $(this).iCheck('check');
         });
    });
    
     $('#clearall').click(function(event) { 
     	// $('.attFields').iCheck('uncheck'); 
         $('input[name="attFields"]').each(function(){
             if(!expan){
                     if(uncheckfields[$(this).val()]==undefined)
                          $(this).iCheck('uncheck');
                  }
                  else
                          $(this).iCheck('uncheck');
          });
    }); 

    
/*      $('#selectall').click(function(event){
    	 $('.attFields').iCheck('check');
     });
    $('#clearall').click(function(event) { 
    	 $('.attFields').iCheck('uncheck');
    });
      */
     
      
/*       $("#showhide").click(function(){ alert('hi');
    		var imgid=this.id;
    	    $("#advanced").slideToggle(
    	    function(){
    	    	var a=document.getElementById(this.id).style.display;
    	        if(a=="none"){
    	        $("#showhide").attr("src","../images/dn.gif");       
    	        }
    	        else{
    	        $("#showhide").attr("src","../images/up.gif");
    	        }
    	    }
    	    );    
    	});  */
    	
    	$("#show").click(function(){
    	    var imageid=this.id;
    	    $("#adv").slideToggle(
    	    function(){
    	    	var b=document.getElementById(this.id).style.display;
    	        if(b=="none")
    	        { $("#show").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer"> </i>');   
    	        expan=false;
    	        }
    	        else
    	        {  
    	        	$("#show").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer"> </i>');     
    	        expan=true;
    	        }
    	    }
    	    );    
    	});
      
 $('#showhide').click(function(){
	$("#advanced").slideToggle(
		    function(){
		    	var a=document.getElementById(this.id).style.display;
		        if(a=="none")
		        $("#showhide").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer"> </i>');
		        else
		       $("#showhide").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer"> </i>');
		    }
		    ); 
});
/* $('#show').click(function(){
	 $("#adv").slideToggle(
			 function(){
				 var b=document.getElementById(this.id).style.display;
				 if(b=="none"){
					 $("#show").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer" id="showhide"> </i>');
				 }else{
					 $("#show").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer" id="showhide"> </i>');
				 }
			 }
			 );
}); */




$(function(){
 $('input.fname').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.tname').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.tid').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.lname').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.tdate').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.ename').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'}); 
  $('input.allsource').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.purchase').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   $('input.alldates').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.addmanual').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.sdate').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
$('input.allattdtckts').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.alltickets').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 
  $('input.tp').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.be').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.em').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.ph').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.onumb').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.ak').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.bn').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   $('input.bp').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   $('input.sc').iCheck({  
   checkboxClass: 'icheckbox_square-grey', 
   radioClass: 'iradio_square-grey'});
 
 
        $('#start').datetimepicker({
        format:'m/d/Y',
        timepicker:false,
        step:5
        
    });
    $('#end').datetimepicker({
        format:'m/d/Y',
        timepicker:false,
        step:5
    });
 });
</script>


