<%@taglib uri="/struts-tags" prefix="s" %>
<link rel="stylesheet" type="text/css" href="../css/seating.css"/>
<script type="text/javascript">
 var powertype='${powertype}';
 var isrecurring='${isrecurring}';
 var curvenueid='${venue.venueid}';
 var lockedData='${lockedData}';
//var lockedData = {"citems":[{"recurdate": "", "status": "Profile Page", "scodes": [{"seatcodes": "Row-V: seat-12"},{"seatcodes": "Row-X: seat-16"}, {"seatcodes": "Row-DD: seat-24"}, {"seatcodes": "Row-L: seat-103"},{"seatcodes": "Row-R: seat-16"}], "action": "Manage", "tid": "RK6BUEXHSB", "date": "2"}]};
 var seatdata="{}";
 </script>
<style>
/* tooltip custom theme */
.my-custom-theme {
	border-radius: 5px; 
	border: 2px solid #5388C4;
	background: #fbfcfd;
	color: #000;
}
.my-custom-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
	#opacity:0;
}

/* tooltip custom theme */
.form-validate-theme {
	border-radius: 5px; 
	border: 2px solid #ff4747;
	background: #fff4f4;
	color: #000;
}
.form-validate-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
}


/* .dataTables_filter {
     float: right !important;
    margin-top: -13px;
    text-align: right !important;
} */

/* .dataTables_filter span.search-result {
    left:auto !important;
    top:-14px !important;
    } */

.open {
    border-radius: 0;
    width:19%;
}
table{
 border-collapse: initial !important;
 border-spacing: 2px !important;
}


</style>

 	<div class="row">
 		<div class="col-md-12">
 		<s:hidden name="eid" id="eid"></s:hidden>
 		<s:hidden name="venueid" id="venueid"></s:hidden>
 	 		</div>
 			 <s:if test="%{venueList.size<1}">			
				
					 <div class="alert alert-info hidden-xs">
				              <s:text name="sea.venue.msg1.lbl"></s:text><a href="mailto:support@eventbee.com">support@eventbee.com</a><s:text name="sea.venue.msg2.lbl"></s:text>
				        </div> 
					
			</s:if> 
			
			<s:else>
			
				<s:if test="%{regcount==0}">
					 <div class="alert alert-info hidden-xs">
				              <s:text name="sea.venue.msg1.lbl"></s:text> 
				              	<a href="mailto:support@eventbee.com">support@eventbee.com</a>
				              			<s:text name="sea.venue.msg2.lbl"></s:text>
				        </div> 
					<div class="col-md-2" style="margin-top:8px" ><s:text name="sea.select.venue.dd.lbl"></s:text>:</div>
					<div class="col-md-4">
						<s:select headerKey="" headerValue="%{getText('sea.select.venue.dd.lbl')}"  list="venueList" name="venueid" id="venues" listKey="key" listValue="value" onchange="addVenue()"  cssClass="form-control"></s:select>
					</div>
					<br/><br/><br/>
				</s:if>
				
				<s:if test="%{venue.venueid!=''}">				
					<div class="row">
					  <div class="col-md-12">
						<div class="col-md-2"><s:text name="sea.show.venue.dd.lbl"></s:text></div>
						<div class="col-md-3">
						<input type="radio" name="showvenue" class="showradioenable"  <s:if test="%{showvenue}">checked="checked"</s:if>  /><span class="checkbox-space"><s:text name="sea.enable.seating.lbl"></s:text></span>
						<input type="radio" name="showvenue" class="showradiodisable"  <s:if test="%{!showvenue}">checked="checked"</s:if>  /><span class="checkbox-space"><s:text name="sea.disable.seating.lbl"></s:text></span>           
						</div>
						<div class="col-md-3"></div>
					  </div>					
					</div>
				
				</s:if>
				
			</s:else>
			
			
 		</div><!-- div col-md-12 close -->
 	<!-- div row close -->
 	 <br/>
 	 <s:if test="%{hasvenue}">
 	 <%-- <div class="row">
 	 	<div class="col-md-12">
 	 		<div class="col-md-8">
 	 		<b><s:text name="sea.venue.txt.lbl"></s:text> ${venue.venuename}</b>
 	 	</div>
 	 </div>
 	 <br/> --%>
 	 	<s:if test="powertype!='RSVP'">
 	 	
 	 	<div class="section-main-header">
 	 	<s:text name="sea.seat.type.th.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="seattypeinfo" ></i>
</div>


<div class="row sticky-out-button pull-right">
	<div class="col-md-12"> 
		 <div class="pull-right">
			 <button class="btn btn-primary" type="button" onclick="addSeatType();"><s:text name="sea.add.seat.type.btn.lbl"></s:text></button> 
		 </div> 
	 </div> 
	<div style="clear:both"></div>
</div>



<div class="white-box no-button">
                                    		<table  class="table table-responsive table-hover no-border-bottom" id="seattypes" >    
                                    	</table>
                                    	</div>
 	 
 	 </s:if>
 	 
 	 
 	 
 	 <div id="sectiondiv" style="display: none">
 	 </br>
 	  <div class="row">
		<div class="col-md-4" style="margin-top:8px;"><b><s:text name="sea.select.section.lbl"></s:text></b>:</div> 		
			 <div class="col-md-4">
			 <s:select list="sectionsList" name="section" id="section" listKey="key" listValue="value" onchange="getSeating()" cssClass="form-control"/>  
		 </div>
		 </div>
		</div>
		
		<div id="loading" align="center" class="load">
		<table>
		<tr><td align="center"><s:text name="global.loading.msg"/></td></tr> 
		 <tr><td align="center"><img src="../images/ajax-loader.gif"></td></tr> 
		</table>
		 </div>
		 
		 
		 <div class="section-main-header box-top-gap">
 	 	<s:text name="sea.seat.layout.th.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="seatlayoutinfo" ></i>
		</div>
		                      
		                      <div id="seatsdiv"  align="left" class="white-box">
                                
                                    <div class="selstyle" >
										<table id="seltable" >
										</table>
									</div><br/>
									<div class="bottom-gap" style="overflow:auto;width: 100%;height: 100%;border: 1px solid #336699;padding: 10px;background-color: white;border-radius: 4px;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;">	
										<table bgcolor="#C0C0C0" align="center">
											<tr>
												<td>
													<div id="seatcell" style="padding:4px 4px 4px 4px" ></div>
												</td>
											</tr>	
										</table>
											
									</div>
                                <br/>
                                 <div class="row"> 
                                   <div class="col-md-6" align="right"> 
                                   	<button class="btn btn-primary"  type="button"  onclick="saveseats()"><s:text name="global.save.btn.lbl"></s:text></button>
                                   </div>
                                  <div class="col-md-6" align="right">
                                    <s:if test="isrecurring">
										<a href="Seating!addManual?eid=${eid}"><s:text name="sea.click.here.view.lbl"></s:text></a>
									</s:if>	                 </div>                 
                                 </div> 
		 </div>
		 
		 
		 <div class="section-main-header box-top-gap">
 	 	<s:text name="sea.locked.seats.th.lbl"></s:text>
		</div>
		 
		 
		  <div class="white-box">
		  
		     <table class="table table-responsive table-hover no-border-bottom" id="lockSeatTable">
	                                    <%-- <thead>
	                                    <tr>
	                                    	<th><s:text name="sea.transaction.id.header.lbl"></s:text></th>
	                                    	<th><s:text name="sea.seats.header.lbl"></s:text></th>
	                                    	<th><s:text name="sea.current.status.header.lbl"></s:text></th>
	                                    	<th><s:text name="sea.duration.header.lbl"></s:text></th>
	                                    	<th></th>
	                                    	</tr>
	                                    </thead> --%>
	                                    <tbody>
                                    	 <tr class="nodata">
                                    		<td>
                                    		
                                    		<div class="not-found"><s:text name="sea.no.locked.seats.display"/></div>
                                    		</td>
                                    		<td><%-- <s:text name="global.no.data.lbl"></s:text> --%> </td>
                                    		<td></td>
                                    		<td></td>
                                    		<td></td>
                                    	</tr> 
                                    	</tbody>
                                    </table>
		  
		  
		  </div>
		 
		 
		 <%-- <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="sea.locked.seats.th.lbl"></s:text></h3>
                                </div>
                                <div class="panel-body">
                                    
                                </div>                                
                            </div> --%>
		 
		 
</s:if><!-- hashvenue close -->
 

   
    <script>
    $(document).ready(function(){	
    $('#seatlayoutinfo').attr('title',props.sea_seat_layout_help_msg_lbl);
    $('#seattypeinfo').attr('title',props.sea_seat_types_help_msg_lbl);
    });
    
    
    
    
    var sectionid="${sectionid}";
    var eid='';
    var timestamp=new Date();
    var color="";
    var oldcolor="";
    var selected=new Object();
    var seatcolors="";
    var colorchoice="";
    var venueid="";
    var section_id="";
 function addSeatType(){
	 //parent.loadingPopup();
		colorchoice="";
		eid=document.getElementById("eid").value;
		section_id=document.getElementById("section").value;
		venueid=document.getElementById("venueid").value;
		var url='Seating!getTicketsandColors?eid='+eid+"&sectionid="+section_id+"&venueid="+venueid;
		$.ajax({
			url : url,
			type : 'get',
			success:function(response){				
				if(isValidActionMessage(response)){
				if(response.length==1 && response==0){
					parent.hidePopup();					
					 bootbox.confirm(props.sea_no_more_seats_lbl, function (result) { });
					return;
				}else {					
						 $('#myModal .modal-title').html(props.sea_seat_types_lbl);
						 $('#myModal .modal-footer').html('');
						 $('iframe#popup').attr("src",url);
					     $('#myModal').modal('show');	
					     $('#myModal').on('hide.bs.modal', function () { 
					        $('iframe#popup').attr("src",'');			        
					        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');
					    });	
				}
			}},error : function(){
				//alert("fail");
			}
		});
} 
 
    function addVenue(){
    	eid=document.getElementById("eid").value;
    	var vid=document.getElementById("venues").value;
    	if(vid=='' || (curvenueid==vid && curvenueid!='')){
    	}
    	else{
    	var ind=document.getElementById("venues").selectedIndex;
    	var selven=document.getElementById("venues").options[ind].text;
    	var rep="<center><s:text name="sea.importing.lbl"/> "+selven+" <s:text name="sea.seating.continuee"/></center>";  
    	//alert(props.sea_imports_venue+"::"+rep);
    	//var footer = '<input type="button" class="btn btn-primary" value="Continue" onclick="continueSeating();">&nbsp;<input type="button" class="btn btn-active" value="Cancel" onclick="closepopup();">';
    	openHTMLPopUp(props.sea_imports_venue,rep,'continueSeating',true);
		 /* $('#myModal .modal-title').html('<h3><span style="padding-left:10px">Import Venue</span></h3>');
			  $('#myModal .modal-body').html(rep);
			  $('#myModal .modal-footer').html(footer);
			   $('#myModal').modal('show');    
			        $('#myModal').on('hide.bs.modal', function () { 
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:modalOnLoad()" frameborder="0"></iframe>');
			    });	 */
    	}
    }
    
    function closepopup(){
    	 $('#myModal').modal('hide');  
    }
    
    function continueSeating(){
    	var vid=document.getElementById("venues").value;
    	window.location.href="../eventmanage/Seating!addVenue?eid="+eid+"&venueid="+vid;
    }
    
    function showvenue(val){    	
    	eid=document.getElementById("eid").value;
    	var url='Seating!showvenue?eid='+eid+'&showvenue='+val;
    		$.ajax({
    			url : url,
    			type : 'get',
    			success : function(){    				
    			}
    		});
    }
    
    
    $(function(){

        $(document).on('mouseover','.hover-item',function(){
                $(this).find('span.seattypes').css("display","block"); 
            }); 

        $(document).on('mouseout','.hover-item',function(){
            $(this).find('span.seattypes').css("display","none"); 
        }); 
        

        

    	 $('input.showradioenable').iCheck({  
    	 checkboxClass: 'icheckbox_square-grey', 
    	 radioClass: 'iradio_square-grey'});
    	 $('input.showradiodisable').iCheck({  
        	 checkboxClass: 'icheckbox_square-grey', 
        	 radioClass: 'iradio_square-grey'});
    });
    
    $('input.showradioenable').on('ifChecked', function(event){
    	showvenue("true"); });
    $('input.showradiodisable').on('ifChecked', function(event){
    	showvenue("false"); });
    

    
    function getSeatColors(){
    	eid=document.getElementById("eid").value;    	
    	$.ajax({
    		url : "Seating!getSeatColors?eid="+eid+"&powerType="+powertype+"&timestamp="+(new Date()).getTime(),
    		type : "get",
    		success : function(result){
    			getSeatTypesTable(result);
    		    seatcolors=result;
    		}
    	});
    }
    
    function getSeating(){
    	color="";
    	oldcolor="";
    	selected=new Object();
    	colorchoice="";
    	var sectionid=document.getElementById("section").value;
    	//document.getElementById("loading").style.display="block";
    	//document.getElementById("seatsdiv").style.display="none";
    	eid=document.getElementById("eid").value;

    	if(seatdata=='' || seatdata=='{}' || seatdata==undefined)
    	$.get("Seating!getSectionDetails?eid="+eid+"&sectionid="+sectionid+"&isrecurring="+isrecurring+"&timestamp="+(new Date()).getTime(),function(result){
    				if(isValidActionMessage(result)){
    					generateSeating(result);
    				}
    				});
    	else
    		{generateSeating(seatdata);
    		//alert(1);
    		seatdata='';
    		}
    }	
    
    function generateSeating(data){
    	var idarr=[];
    	var nsandso=new Object();
    	var allids=new Object();
    	var inc=0;
    	section_id=document.getElementById("section").value;
    	var json=eval('('+data+')');
    	var finalseats=json.completeseats;
    	var seatinfo=new Object();
    	var cell=document.getElementById("seatcell");
    	while(cell.hasChildNodes()){
    		cell.removeChild(cell.lastChild);
    	}
    	var table=document.createElement("table");
    	var tbody=document.createElement("tbody");
    	table.setAttribute('border','0');
    	table.setAttribute('align','center');
    	table.setAttribute('id','seatstab');
    	var imgwidth="17px";
        var imgheight="17px";
        var text=json.layout_css.layout_css;
        if(json.seat_image_width!=undefined && json.seat_image_width!="")
        imgwidth=json.seat_image_width;
         if(json.seat_image_height!=undefined && json.seat_image_height!="")
        imgheight=json.seat_image_height;
        if(text!=undefined && text!="")
       {
       	var h =document.getElementById("maincontent");
        var s = document.createElement("style");
           s.type = "text/css";
           
           if (s.styleSheet) { // IE
                   s.styleSheet.cssText = text;
           } else { // others
                   s.appendChild(document.createTextNode(text));
           }
          //h.appendChild(s);
          $('#maincontent').append(s);
        }
    		
    	if(secimgfalg && json.background_image!=undefined && json.background_image!="")
    	{var bgimage=json.background_image;
    	table.style.cssText='background:url("'+bgimage+'") no-repeat  scroll 0 0 transparent; background-image:url() no-repeat  scroll 0 0 transparent;';
    	}
    	for(var i=1;i<=json.noofrows;i++){
    	var row=document.createElement("tr");
    	for(var j=1;j<=json.noofcols;j++){
    		var s="";
    		var sid="s_"+i+"_"+j;    		
    		var type="noseat";
    		var scode="";
    		var seat_ind="";
    		if(finalseats[sid]){
    		  type=finalseats[sid].type;
    		  scode=finalseats[sid].seatcode;
    		  seat_ind=finalseats[sid].seat_ind;
    		}
    		if(scode=="EMPTY") scode="";
    		var col=document.createElement("td");
    		var imgsrc="";
    		s=s+""+props.aa_seating_tooltip_seatnumber_txt+": "+scode;
    		var seatposition="<br>"+props.aa_seating_tooltip_row_lbl+": "+i+" "+props.aa_seating_tooltip_column_lbl+": "+j;
    		if(type){
    		imgsrc="../images/seatingimages/"+type+".png";
    		col.className=type;
    		if(type.indexOf("sold")>-1){
    				s=s+"<br><b>"+props.sea_sold_out_seats_lbl+"</b>";
    				nsandso[finalseats[sid].seat_ind]="yes";
    				}
    		else if(type.indexOf("NA")>-1)
    				s=s+"<br><b>"+props.sea_not_available_lbl+"</b>";
    		else if(type.indexOf("lightgray_exclaimation")>-1){
    			nsandso[finalseats[sid].seat_ind]="yes";
    			s=s+"<br><b>"+props.sea_currently_onhold_lbl+"</b>";
    		}
    		else if(type=="noseat"){
    				s="";    				
    				seatposition="";
    				}
    		}
    		else{
    		col.className="lightgray_question";
    		imgsrc="../images/seatingimages/lightgray_question.png"
    		}
    		s=s+seatposition;
    		if(type!="noseat"){
    		var img=document.createElement("img");
    		img.setAttribute("src",imgsrc);
    		img.setAttribute("width",imgwidth);
    		img.setAttribute("height",imgheight);
    		col.appendChild(img);
    		col.setAttribute('align','center');
    		seatinfo[seat_ind]=s;
    		idarr[inc]=col;
    		inc++;
    		}
    		col.setAttribute('id',seat_ind);
    		allids[seat_ind]="yes";
    		row.appendChild(col);
    	}
    	tbody.appendChild(row);
    	}
    	table.appendChild(tbody);
    	cell.appendChild(table);
    	$("#seatstab td").mouseover(function() {
    		
    	if(color!="" ){
    		oldcolor=$(this).attr("class").replace('tooltipstered','').trim();
    		if(oldcolor!="noseat" && oldcolor.indexOf("SO")==-1 && oldcolor!="lightgray_sold"){
    		var id=$(this).attr("id");
    		if(allids[id] && !(nsandso[id]) ){
    		$(this).attr("class",color);
    		$(this).children("img").attr("src","../images/seatingimages/"+color+".png");
    		}
    		}
    	}
    	});
    	$("#seatstab td").mouseout(function() {
    	if(color!=""){
    		
    		if(oldcolor!="noseat" && oldcolor.indexOf("SO")==-1 && oldcolor!="lightgray_sold"){
    		var id=$(this).attr("id");
    		if(allids[id] && !(nsandso[id])){
    		$(this).attr("class",oldcolor);
    		$(this).children("img").attr("src","../images/seatingimages/"+oldcolor+".png");
    		}
    		}
    	}
    	});
    	$("#seatstab td").click(function() {
    	if(color!="" && (oldcolor!="noseat" && oldcolor.indexOf("SO")==-1) && oldcolor!="lightgray_sold"){
    			var id=$(this).attr("id");
    			if(allids[id] && !(nsandso[id])){
    			$(this).attr("class",color);
    			$(this).children("img").attr("src","../images/seatingimages/"+color+".png");
    			selected[id]=color;
    			oldcolor=color;
    			}
    		}
    	});
    	$("#coloroptions td").click(function() {
    		$("#coloroptions td").each(function(){
    			$(this).children("img").css("border","0px none");
    		});
    		$(this).children("img").css("border","1px solid");
    		colorchoice=$(this).attr("id");
    	});
    	
    	 $.each(seatinfo,function(key,value){
      		$('#'+key).tooltipster({
 			    fixedWidth:'300px',
 			        content: $('<span>'+value+'</span>')
 			    });
      	});
    	 

    	
     	try{
      setTimeout(function(){ 
          displayTcktsColors(seatcolors);},200);   	
     
    }catch(err){}
     	document.getElementById("loading").style.display="none";
    	document.getElementById("seatsdiv").style.display="block";
    }
    
    
    function displayTcktsColors(json){
    	var rawjson=eval('('+json+')');
    	var jsondata=rawjson.seattypes;
    	var seltable=document.getElementById("seltable");
    	var seltabbody=document.createElement("tbody");
    	while(seltable.hasChildNodes()){
    		seltable.removeChild(seltable.lastChild);
    	}
    	var arr=[];
    	var inc=0;
    	var ticks=new Object();
    	var row1=document.createElement("tr");
    	seltable.appendChild(row1);
    	var col4=document.createElement("td");
    	col4.innerHTML= props.sea_seat_types_assign_layout+" <i id='seattypesinfo' style='cursor:pointer' class='fa fa-info-circle'></i> : ";
    	row1.appendChild(col4);
    	for(var i=0;i<jsondata.length;i++)
    	{
    		var a=jsondata[i].color;
    		var tckts=jsondata[i].tickets;
    		var color=a;
    		var col1=document.createElement("td");
    		col1.setAttribute("id",color);
    		arr[inc]=col1;  
    		inc++;
    		var img=document.createElement("img");
    		img.setAttribute("src","../images/seatingimages/"+color+".png");
    		if(color!="lightgray_sold"){
    			col1.appendChild(img);
    		}
    		var ttl="";
    		if(tckts){
    		for(var j=0;j<tckts.length;j++)
    		{
    			var li=document.createElement("li");
    			var text=document.createTextNode(tckts[j]);
    			ttl=ttl+"<li>"+tckts[j];
    			li.appendChild(text);
    		}
    		}
    		if(color=="lightgray_question") 
    			ticks[color]=props.sea_unassigned_lbl;
    		else if(color=="lightgray_blank")
    			ticks[color]=props.sea_not_avail_lbl;
    		else
    		ticks[color]=ttl;
    		row1.appendChild(col1);
    		if(tckts && tckts.length<1)
    		row1.removeChild(col1);
    		seltabbody.appendChild(row1);
    	}
    	seltable.appendChild(seltabbody);
    
     	$.each(ticks,function(key,value){
     		$('#'+key).tooltipster({
			    fixedWidth:'300px',
			        content: $('<b>'+props.sea_tkt_lbl+':</b><br><span style="margin-left:33px;">'+value+'</span></b>')
			    });
     	});
     	
     	
    	createListoner();

    	$('#seattypesinfo').tooltipster({
		    fixedWidth:'300px',
		        content: $('<span> '+props.sea_select_seat_msg_info+'</span>')
		    });
    	
    }
    
    function createListoner(){
    	$("#seltable td").click(function() {
    		$("#seltable td").each(function(){
    			$(this).children("img").css("border","0px none");
    		});
    		$(this).children("img").css("border","1px solid");
    		color=$(this).attr("id");
    	});
    }
    
    
    
    // {"seattypes":[{"color":"lightgray_question"},{"color":"lightgray_blank"},{"color":"lightgray_sold"},{"color":"darkgreen_n","tickets":["Ticket one"]},{"color":"woman","tickets":[]}]}
    
    function getSeatTypesTable(result){
    	var data = eval('('+result+')');
    	var tickets;
    	$.each(data,function(outkey,resObj){    		
    		$.each( resObj, function( inkey, valueobj ) {    	
    			var ticketsdisplaycontent='';var color='';var tickets='';   
    		if(valueobj.color=="lightgray_question")ticketsdisplaycontent=props.sea_unassigned_lbl;
    		else if(valueobj.color=="lightgray_blank")ticketsdisplaycontent=props.sea_not_avail_lbl;
    		else if(valueobj.color=="lightgray_sold") ticketsdisplaycontent=props.sea_sold_out;
    		else if(powertype=="RSVP") ticketsdisplaycontent=props.sea_seat_lbl;  
    		
    		color=valueobj.color;
    		var	image="<img src='../images/seatingimages/"+color+".png'>";   
    		tickets = valueobj.tickets;
        	if(tickets){
        	if(tickets.length==0) ticketsdisplaycontent=props.sea_no_ticket_lbl;
        	ticketsdisplaycontent+='<ul>';
        	for(var i=0;i<tickets.length;i++){
            	ticketsdisplaycontent+="<li class='li_disc'> "+tickets[i]+"</li>";
            	}
        	ticketsdisplaycontent+='</ul>';
        	 var customize = '<span class="seattypes" style="display:none;"><a href="javascript:;" onclick="selectTickets(\''+color+'\')">'+props.global_edit_lnk_lbl+'</a>&nbsp;&nbsp;<a href="javascript:;" onclick="deleteSeatType(\''+color+'\')">'+props.global_delete_lnk_lbl+'</a></span>';
        	}
        	if(customize==undefined)customize='';

        	var backgroundcontent='';

			var message=props.sea_tkt_lbl;
			/* alert(tickets.length);

			if(Number(tickets.length)==1)
				message='Ticket'; */
        	

            if(ticketsdisplaycontent!='Unassigned' && ticketsdisplaycontent!='Not Available' && ticketsdisplaycontent!='Sold Out' && ticketsdisplaycontent!='No Tickets'){
          	  backgroundcontent='<div class="background-options sm-font" style="display:none;" id="background_'+color+'">'+ticketsdisplaycontent+'</div>';
          	  if(tickets!=undefined && tickets!='undefined'){
              	  if(Number(tickets.length)==1)
              	  message=props.sea_tkt_msg_lbl;
          	  ticketsdisplaycontent='<lable style="cursor: pointer;" class="seatstoggle" data-toggle="background_'+color+'"><span>'+tickets.length+' '+message+'</span> <span class="arrow-gap glyphicon glyphicon-menu-right" data-toggle="'+color+'" style="cursor: pointer;  margin-top: 3px;"></span></lable>';
            }
            }         
      	

          var htmldata='<div class="row"><div class="col-md-2">'+image+'</div><div class="col-md-4 sm-font" id="'+color+'_image">'+ticketsdisplaycontent+'</div><div class="col-md-4 sm-font">'+customize+'</div></div>';
/* alert(htmldata); */
      	
  		var tempentry=$('<tr class="hover-item"><td>'+htmldata+backgroundcontent+'</td></tr>');
//	alert(tempentry);


        	
    	//	var tempentry=$('<tr class="hover-item"><td>'+image+'</td><td id="'+color+'_image">'+ticketsdisplaycontent+'</td><td>'+customize+'</td></tr>');
    		$('#seattypes  .nodata').remove();
			$('#seattypes').append(tempentry);    		
    	  });  
       });
    }
    
    
    function saveseats(){
        loadingPopup();
    	//document.getElementById("loading").style.display="block";
    	//document.getElementById("seatsdiv").style.display="none";
    	var jsonFormat='{"seating":[';
    	var s='';
    	var i=0;
     	$("#seatstab td").each(function(){
    		var id= $(this).attr("id");
    		var c=selected[id];
    		if(c!=undefined){
    			if(i!=0) s=s+',';
    			s=s+'{"sid":"'+id+'","color":"'+c+'"}';
    			i++;
    		}
    	});
    	jsonFormat=jsonFormat+s+']};';
    	eid=document.getElementById("eid").value;
    	section_id=document.getElementById("section").value;
    	venueid=document.getElementById("venueid").value;
    	$.post("Seating!saveSeats"+"?timestamp="+(new Date()).getTime(),{eid:eid,venueid:venueid,sectionid:section_id,msg:jsonFormat},
    		function(result){
    		    $('#seatseltkts').html('');
    		    $('#seattypes').html('');
    			getSeatColors();
    			if(isValidActionMessage(result))
    			getSeating();
    			hidePopup();
    			notification(props.sea_seating_success_msg,'success');
    		}
    	);
    }
    
    function selectTickets(color){
    	parent.loadingPopup();
    	$('#background_'+color).slideUp();
    	$('span.highlighted-text').removeClass('highlighted-text');
    	$('span[data-toggle="'+color+'"]').removeClass('down').addClass('original');
    	if(color!="" && color!="unassign" && color!="whiteNA"){
    	eid=document.getElementById("eid").value;
    	section_id=document.getElementById("section").value;
    	venueid=document.getElementById("venueid").value;
    	var url='Seating!getTicketsForColor?eid='+eid+"&sectionid="+section_id+"&venueid="+venueid+"&colorCode="+color;
    	//loadURLBasedWindow(url, seatTicketsSuccess);
    	$.ajax({
    		url : url,
    		type : 'get',
    		success : function(response){
    			if(!isValidActionMessage(response)) return;      
    			if(response=="notickets"){
    				parent.hidePopup();
    				bootbox.confirm(props.sea_no_tickts_toevent, function (result) { });
    				return;
    			}    			
   			 $('#myModal .modal-title').html(props.sea_tkt_lbl);
   			$('#myModal .modal-footer').html('');
   			$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');   
   			 $('iframe#popup').attr("src",url);   		
   		    $('#myModal').modal('show');	   		  
   		        		        
   		        		 
    		}
    	});
    	}
    	else 
    	bootbox.confirm(props.sea_select_seat_type, function (result) { });
    }
    
    function deleteSeatType(color){
    	var agree=props.sea_want_to_delete;
    	eid=document.getElementById("eid").value;
    	venueid=document.getElementById("venueid").value;
    	 bootbox.confirm('<h3>'+props.sea_del_seat_type+'</h3>'+agree, function (result) {			 
	        	if (result){	           	
	        		parent.loadingPopup();
	        		var url='Seating!deleteSeat?eid='+eid+'&venueid='+venueid+"&colorCode="+color;
	       		 $.ajax({		
	         			 url : url,
	         			 type : 'get',
	         			 success : function(){
						$('#seatseltkts').html('');
						$('#seattypes').html('');
	         				$('#'+color+'_image').parent().parent().remove();
	         				$('#'+color).remove();
	         				getSeatColors();
	         				getSeating();
		         		    parent.hidePopup();
	         				 //window.location.href="../eventmanage/Seating?eid="+eid; 
	         			 }
	         		 });
	           }
	        	
	        });
    	/* if (agree){	
    		parent.loadingPopup();
    		var url='Seating!deleteSeat?eid='+eid+'&venueid='+venueid+"&colorCode="+color;
   		 $.ajax({		
     			 url : url,
     			 type : 'get',
     			 success : function(){
     				 parent.hidePopup();
     				 window.location.href="../eventmanage/Seating?eid="+eid; 
     			 }
     		 });
    	} */
    }
    
    
    //{"citems":"[{\"recurdate\": \"\", \"status\": \"Profile Page\", \"scodes\": [{\"seatcodes\": \"Row-V: seat-12\"},
     // {\"seatcodes\": \"Row-X: seat-16\"}, {\"seatcodes\": \"Row-DD: seat-24\"}, {\"seatcodes\": \"Row-L: seat-103\"},
     //{\"seatcodes\": \"Row-R: seat-16\"}], \"action\": \"Manage\", \"tid\": \"RK6BUEXHSB\", \"date\": \"1012\"}]"}
    
    function lockedSeatsTable(){
    	var scodes='';
    	var json = eval('('+lockedData+')');  
    	/* $.each(json.citems, function( inkey, resObj ) { */
    		$.each(json.citems, function(outkey, valueobj ) { 
    			scodes =valueobj.scodes;
    			var scodesdisplay='';
    			$.each(scodes, function(key, value ) {
    				scodesdisplay+="<li> "+value.seatcodes+"</li>";
    			});

               var html='<tr id="'+valueobj.tid+'"><td><div class="row"><div class="col-md-3">'+valueobj.tid+'</div><div class="col-md-3 sm-font"><lable style="cursor:pointer;" class="toggle" data-toggle="locked_'+valueobj.tid+'"><span class="text">'+scodes.length+ props.sea_seat_codes_lbl +' </span> <span class="arrow-gap glyphicon glyphicon-menu-right" data-toggle="locked_'+valueobj.tid+'" style="cursor: pointer;  margin-top: 3px;"></span></lable></div><div class="col-md-2 sm-font">'+valueobj.status+'</div><div class="col-md-2 sm-font">'+valueobj.date+'</div><div class="col-md-2 pull-right sm-font" id="'+valueobj.tid+'"><a href="javascript:;" onclick="releaseSeat(\''+valueobj.tid+'\');return false;">'+props.sea_release_lbl+'</a></div></div>'+
               '<div id="locked_'+valueobj.tid+'" style="display:none;" class="background-options sm-font"><span>'+scodesdisplay+'</span></div>';
                '</td></tr>';
    			
    			//var tempentry=$('<tr class="hover-item"><td style="max-width: 100px;">'+valueobj.tid+'</td><td>'+scodesdisplay+'</td><td>'+valueobj.status+'</td><td>'+valueobj.date+'</td><td id="'+valueobj.tid+'"><a href="javascript:;" onclick="releaseSeat(\''+valueobj.tid+'\');return false;">Release</a></td></tr>');
    			var tempentry=$(html);
    			 $('#lockSeatTable .nodata').remove();
    				$('#lockSeatTable').append(tempentry);
    		});    		
    	/* }); */
    }
    
	function releaseSeat(tid){
		var msg=props.sea_release_sure_popup_lbl;
		 bootbox.confirm(msg, function (result){	
			 if (result){
				 loadingPopup();
				 var eid=document.getElementById('eid').value;
					var url='Seating!releaseSeats?eid='+eid+'&tid='+tid;		
			        $.ajax({
			        	url : url,
			        	type : 'post',
			        	success : function(t){
			        		$('#'+tid).remove();
			        		if($('#lockSeatTable tr').length==0)
				        		$('#lockSeatTable').html('<tr class="nodata"><td><div class="not-found">'+props.sea_no_loked_seats_msg+'</div></td><td> </td><td></td><td></td><td></td></tr>');
			        		hidePopup();
			        	}
			        });
			 }
			 });
	}
    
    var secimgfalg=true;
    function init(){
    	getSeatColors();
    	var secid=document.getElementById("section");	
    	if(secid){
    		var ops=secid.options;
    		if(sectionid==""){
    			secid.selectedIndex=0;
    			getSeating();
    		}
    		else{
    			for ( var i = 0; i <ops.length; i++ ) {
    				if ( ops[i].value == sectionid ) {
    					ops[i].selected = true;
    					getSeating();
    				}
    			}
    		}
    		if(ops.length>1){
    		       secimgfalg=false;
    				document.getElementById("sectiondiv").style.display="block";
    			}
    	}
    	lockedSeatsTable();
    }

    var openHTMLPopUp=function (header,content,calback,footernreq){
    	$('#myModalhtmlin .headlbl').html('');
    	$('#myModalhtmlin .modal-body').html('');
    	$('#myModalhtmlin .modal-footer').html('');
    	$('#myModalhtmlin').show();
    	$('#myModalhtmlin .headlbl').html(header);
    	$('#myModalhtmlin .modal-body').html(content);
    	$('#myModalhtmlin .modal-header').css({padding:0});
    	$('#myModalhtmlin .close').css({padding:"3px 25px"});
    	$('#myModalhtmlin .headlbl').css({"margin-left":"19px"});
    	$('#myModalhtmlin .headlbl').css({"color":"#b0b0b0","font-size":"22px","font-weight":"500"});
    	if(footernreq!=false)
    		$('#myModalhtmlin .modal-footer').html($('<div class="center"> <button class="btn btn-primary" onclick='+calback+'(); >'+props.global_continue_lbl+'</button><button class=" btn btn-cancel cls" ><i class="fa fa-times"></i></button></div>'));
    	
    };

    
    
    $(document).ready(function() { 


    	$(document).on('click','.seatstoggle',function(){
        
	       	var id=$(this).data('toggle');
	   		if($('#'+id).is(':hidden')){
	              $('#'+id).slideDown();
	              $(this).find('span:first').addClass('highlighted-text');
	              $(this).find('span.glyphicon').addClass('down').removeClass('original');
	   	   	   	}else{
	   	   	  	  $('#'+id).slideUp();
	   	   		$(this).find('span:first').removeClass('highlighted-text');
	   	   		$(this).find('span.glyphicon').removeClass('down').addClass('original');
	   	   	   	}
	
         });


    	
        $('.toggle').click(function(){

        	var id=$(this).data('toggle');

        	if($('#'+id).is(':hidden')){
            	$(this).find('span.glyphicon').addClass('down').removeClass('original');
            	$(this).find('span.text').addClass('highlighted-text');
        		$('#'+id).slideDown();
        	}else{
        		$(this).find('span.glyphicon').addClass('original').removeClass('down');
        		$(this).find('span.text').removeClass('highlighted-text');
        		$('#'+id).slideUp();
        	}
            });
            


        
    	if(lockedData.lenght>0)
    	$('#lockSeatTable').dataTable( {
                "sPaginationType": "full_numbers",
                "aaSorting": [],
                "iDisplayLength":5,
                "bLengthChange": false,
                "aoColumns": [null,
                              null,                                 
                              null,
                              null,
                             {"bSortable":false}
                            ] ,
                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
                }
        } );

    	$("#lockSeatTable_filter span").click(function(){
    	  	$(".dataTables_filter").toggleClass("open closeClass");
    	  	$(".dataTables_filter").find("[name='search']").val('').focus().keyup();
    });
    	
    	
    		  $('#seattypeinfo').tooltipster({
    			    fixedWidth:'100px',
    			    position: 'right'
    			    });
    		  $('#seatlayoutinfo').tooltipster({
  			    fixedWidth:'100px',
  			    position: 'right'
  			    });
    	
    });
     init();   
    </script>
    
