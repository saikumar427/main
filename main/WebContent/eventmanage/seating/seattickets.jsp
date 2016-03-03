<%@taglib uri="/struts-tags" prefix="s" %>

<%@include file="../../../getresourcespath.jsp"%>

<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>


<style>
body{
background-color:white;
}
/* .ticketslist{
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
} */



</style>
<s:form name="seatticketsform" action="Seating!updateSeatTickets" id="seatticketsform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="colorCode"></s:hidden>
<s:hidden name="venueid"></s:hidden>
<div class="col-xs-12 col-sm-12">
<div style="overflow:hidden;" class="ticketslist"> 
<s:set value="%{alltickets.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
<div class="row"><div class="col-xs-12">
<s:text name="sea.no.tickts.toevent"/>
</div></div>
</s:if>
<s:else>
<div class="row">
  
	<div class="col-xs-3"><s:text name="sea.seat.typ.head.lbl"/>:</div>
	<div class="col-xs-9"><img src="../images/seatingimages/${colorCode}.png"></div>
</div>
<br/>
<div class="row">
  
	<div class="col-xs-3"><s:text name="sea.sel.tkt.lbl"/></div>
	<div class="col-xs-9">
	<div class="pull-right"><a href="#check" id="selectall"><s:text name="global.select.all.lbl"/></a> &nbsp;<a href="#check" id="clearall"><s:text name="global.clear.all.lbl"/></a>
	</div>
	</div>
</div>
<br/>
<div class="row">
   
	<div class="col-xs-12">
		<s:iterator value="%{alltickets}" var="ticket">
		<s:hidden id="%{key}" value="%{value}"></s:hidden>
		<s:checkbox name="seltickets" id="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}"  cssClass="checkTickets"/>&nbsp;${value}<br/><br/>
		</s:iterator> 
	</div>
</div>
</s:else>
</div>
<!-- <br/>
<div class="row">
<div class="col-xs-4"></div>
<div class="col-xs-8">
<input type="button" class="btn btn-primary" value="Submit" onclick="submitSeatTicketsForm();">&nbsp;
<input type="button" class="btn btn-primary" value="Cancel" onclick="parent.closepopup();">
</div>
</div> -->
<!--  <hr> -->
                    <div class="form-group" align="center">
                      
                            <button type="button" class="btn btn-primary" onclick="submitSeatTicketsForm();">
                                 <s:text name="global.continue.btn.lbl"/></button>
                            <button class="btn btn-cancel" onclick="parent.closepopup();">
                                 <i class="fa fa-times"></i></button>
                       
                    </div>
</div>
</s:form>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>

<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script>

var colorcode='${colorCode}';
var seattickt=0;
function submitSeatTicketsForm(){
	++seattickt;

	var selectedtkts=new Array();
	 $.each($("input[name='seltickets']:checked"), function(){            
         selectedtkts.push($(this).val());
     });
	 if(seattickt==1){
 		var url = 'Seating!updateSeatTickets';
 		parent.loadingPopup();
	 	$.ajax({
	 		url : url,
	 		type : 'post',
	 		data : $('#seatticketsform').serialize(),
	 		success : function(){
		 		parent.hidePopup();
                var ticketsdisplaycontent="<ul>";
                if(selectedtkts.length==0)ticketsdisplaycontent=props.sea_no_ticket_lbl;
	 			$.each(selectedtkts, function( key, value ) {
	 				ticketsdisplaycontent+="<li class='li_disc'> "+$('#'+value).val()+"</li>";
	 				});

	 			ticketsdisplaycontent+='</ul>';
 				
	 			parent.$('#'+colorcode).remove();

	 			if(selectedtkts.length>0){
					parent.$('#seltable').find('tr:first').append("<td id='"+colorcode+"'><img src='../images/seatingimages/"+colorcode+".png'></td>");
	 				//$("<td id='"+colorcode+"'><img src='../images/seatingimages/"+colorcode+".png'></td>").insertAfter( parent.$('#lightgray_blank') );
	 				parent.$('#'+colorcode).tooltipster({fixedWidth:'300px',
	 				        content: $('<b>'+props.global_tickets_lbl+':</b><br><span style="margin-left:33px;">'+ticketsdisplaycontent+'</span></b>')
	 				    });
	 				parent.createListoner();
		 			}
	 			
                 var tempcontent='';
	 			if(ticketsdisplaycontent==props.sea_no_ticket_lbl){
	 				tempcontent=props.sea_no_ticket_lbl;
		 			}else{

			 			  if(Number(selectedtkts.length)==1)
			 				tempcontent=selectedtkts.length+' '+props.global_single_tkts_lbl;
			 			  else
                            tempcontent=selectedtkts.length+' '+props.global_single_tkts_lbl+'(s)';

							tempcontent='<lable data-toggle="background_'+colorcode+'" class="seatstoggle" style="cursor: pointer;"><span>'+tempcontent+'</span><span style="cursor: pointer;  margin-top: 3px; margin-left: 7px;" class="glyphicon glyphicon-menu-right" data-toggle="'+colorcode+'"></span></lable>';
			 			}
			var htmlcontent='<div class="row"><div class="col-md-2"><img src="../images/seatingimages/'+colorcode+'.png"></div><div id="'+colorcode+'_image" class="col-md-4 sm-font">'+tempcontent+'</div><div class="col-md-4 sm-font"><span style="display: none;" class="seattypes"><a onclick="selectTickets(\''+colorcode+'\')" href="javascript:;">'+props.global_edit_lnk_lbl+'</a>&nbsp;&nbsp;<a onclick="deleteSeatType(\''+colorcode+'\')" href="javascript:;">'+props.global_delete_lnk_lbl+'</a></span></div></div><div id="background_'+colorcode+'" style="display:none;" class="background-options sm-font">'+ticketsdisplaycontent+'</div>';

			parent.$('#'+colorcode+'_image').parent().parent().html(htmlcontent);
	 			//parent.$('#'+colorcode+'_image').html(ticketsdisplaycontent);
	 			parent.$('#myModal').modal('hide');
	 		}
	 	});
 	}
}


$('#selectall').click(function(event) {  //on click
    $('.checkTickets').iCheck('check');
});

 $('#clearall').click(function(event) { 
 	 $('.checkTickets').iCheck('uncheck');      
});


$(function(){
	 $('input.checkTickets').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	 });
	 
	 
</script>