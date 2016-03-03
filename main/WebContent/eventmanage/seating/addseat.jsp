<%@taglib uri="/struts-tags" prefix="s" %>


<%@include file="../../../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>


   <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<style>
body{
background-color:white;
}
/*  .seatpositions{
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
}  */
</style>

<s:form name="addseatform" action="Seating!addSeat" id="addseatform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="colorCode" id="color"></s:hidden>
<s:hidden name="venueid"></s:hidden>

<div class="alert alert-danger" id="seaterrors" style="display:none;"></div>

<div class="col-xs-12 col-sm-12">
<div class="row">
<div  class="seatpositions">

  <table  cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td ><s:text name="sea.select.seat.type.txt.lbl"/>: </td>
<td align="right">
</td>
</tr>
<tr><td><div style="height:5px;"></div></td></tr>
<tr>
	<td>
		<table id="coloroptions">
		<s:iterator var="mapiter" value="%{supportedcolors}" status="mstat">
		  <tr>
		 	<td width="3%"><s:property value="key"/></td>
		 		<s:iterator var="listiter" value="%{value}" status="cstat">
		 			<s:if test="#listiter=='blank'">
		 				<td width="3%"><img src="../images/seatingimages/noimg.png"></td>
		 			</s:if>
		 			<s:else>
		 	 		<td width="3%" id="<s:property/>"><img src="../images/seatingimages/<s:property/>.png"></td>
		 	 		</s:else>
		 		</s:iterator>
		 		
		  </tr>  
		  <tr><td><div style="height:5px;"></div></td></tr>
		  </s:iterator>
		  
		</table>		
	</td>
</tr>
</table>  


<table align="right">
<tr>
		<td ><img src="../images/seatingimages/noimg.png"></td>
		<td> <s:text name="sea.seat.type.added.cb.lbl"/></td>
</tr>
</table> 
</div>

<!-- <table align="center">
<tr>
	<td><input type="button" value="Submit" class="btn btn-primary" onclick="submitSeatColorsForm();">&nbsp;<input type="button" value="Cancel" class="btn btn-primary"   onclick="parent.closepopup();"></td>
</tr>
</table> -->
</div>
<!--  <hr> -->
                    <div class="form-group" align="center">
                      
                            <button type="button" class="btn btn-primary" onclick="submitSeatColorsForm();">
                                 <s:text name="global.continue.btn.lbl"/></button>
                            <button class="btn btn-cancel" onclick="parent.closepopup();">
                                <i class="fa fa-times"></i></button>
                       
                    </div>
</div>
</s:form>

<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>


<script>
var colorchoice='';
var k=0;
function submitSeatColorsForm(){	
 	k=++k;
 	$('#seaterrors').hide();
 	if(colorchoice!=""){
 	var value=0;
 	var count=0;
 	if(document.addseatform.alltickets){
 	options=document.addseatform.alltickets;	 
 	     for(i=0;i<options.length;i++){
 	        	if(options[i].checked){
 	     		value=options[i].value;
 	     		count++;
 	     	}
 	     }
      	if(options.length==undefined)
 	     {
 	     	if(options.checked){}
 	     	else{
 	     		 //bootbox.confirm('Select Atleast One Ticket', function (result) { });
 	     		 $('#seaterrors').show().html(props.sea_select_one_ticket_lbl);
 	     		 k=0;
 	     		return;			
 	     	}
 	   }
 	   else
 	   {
 	   		if(value==0){
 	   		 //bootbox.confirm('Select Atleast One Ticket', function (result) { });
 	   		  $('#seaterrors').show().html(props.sea_select_one_ticket_lbl);
 	     		 k=0;
 	     		return;
 	   		}
 	   }}
 	document.getElementById("color").value=colorchoice;	
 	if(k==1){
 		parent.loadingPopup();
 		var url = 'Seating!addSeat'; 
	 	$.ajax({
	 		url : url,
	 		type : 'post',
	 		data : $('#addseatform').serialize(),
	 		success : function(){
	 			parent.hidePopup();
	 			var	image="<img src='../images/seatingimages/"+colorchoice+".png'>"; 
	 			var ticketsdisplaycontent=props.sea_no_ticket_lbl;
	 			var customize = '<a href="javascript:;" onclick="selectTickets(\''+colorchoice+'\')">'+props.global_edit_lnk_lbl+'</a>&nbsp;&nbsp;<a href="javascript:;" onclick="deleteSeatType(\''+colorchoice+'\')">'+props.global_delete_lnk_lbl+'</a>';
	 			

	 			var htmldata='<div class="row"><div class="col-md-2">'+image+'</div><div class="col-md-4 sm-font" id="'+colorchoice+'_image">'+ticketsdisplaycontent+'</div><div class="col-md-4 sm-font">'+customize+'</div></div>';

	 			var tempentry=$('<tr class="hover-item"><td>'+htmldata+'</td></tr>'); 
		 		parent.$('#seattypes').append(tempentry);
	 			parent.$('#myModal').modal('hide');
	 		}
	 	});
 	}
 	}
 	else{
 		$('#seaterrors').show().html(props.sea_select_seat_type);
 		k=0;
 		parent.resizeIframe();
 		//bootbox.alert('Select a seat type');
 	}	
 }	 
 

$(function(){
function colorSelect(){	
	 $(".seatpositions").on('click','#coloroptions  td',function(){
	 		var tdid=$(this).attr("id");
	 		if(tdid==undefined || tdid==""){
	 		}
	 		else{
	 		$("#coloroptions td").each(function(){
	 			$(this).children("img").css("border","0px none");
	 		});
	 		$(this).children("img").css("border","1px solid");
	 		colorchoice=$(this).attr("id");	 		
	 		}
	 	});
	 }
colorSelect();
});
   
</script>
