<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.event.helpers.I18n"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />

 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" />
 <%@include file="../../../getresourcespath.jsp"%>
<script src="<%=resourceaddress%>/main/js/i18n/<%=I18n.getLanguageFromSession()%>/properties.js" ></script>
 <style>
/*  .my-custom-theme {
	border-radius: 5px; 
	border: 2px solid #5388C4;
	background: #fbfcfd;
	color: #000;
	width:25%
}
.my-custom-theme .tooltipster-content {
    font-family: Tahoma, sans-serif;
    width:100%;
	font-size: 13px;
	line-height: 16px;
	padding: 8px 10px;
	#opacity:0;
}  */

table{
border-collapse: initial !important;
border-spacing: 2px !important;
}

.tempClass{
background-size:242% 252% !important;
border-spacing: 3px !important;
}

 </style>
 <div class="container">
 <div class="row" id="sectiondiv">
 <!-- <div id="sectiondiv"></div> -->
 <div class="col-xs-3" style="margin-top: 7px;"><s:text name="sea.select.sec.lbl"/>:</div>
 <div class="col-xs-4">
	  <s:select list="venueSections" cssClass="form-control" name="seatcodeshelp" id="section" listKey="key" listValue="value" value="sectionid" onchange="getSection()"  />
</div>
 </div>
 
<s:form id="attendeeseatform" action="ManageAttendeeSeats!saveAttendeeSeat">
<s:hidden name="eid" id="eid"/>
<s:hidden name="profilekey" id="pid" />
<s:hidden name="tid" id="transid"/>
<s:hidden name="eventdate" id="evtdate"/>
<s:hidden name="seatcode" id="seatcode" />
<s:hidden name="seatindex" id="seatindex" />
<s:hidden name="sectionid" id="sectionid"/>
<s:hidden name="seataction" id="seataction"/>
<s:hidden name="venueid" id="venueid"/>
</s:form>
<div id="maincontent"></div>
<div class="row">
<div class="col-xs-12">
		<img height="17px" width="17px" src="/main/images/seatingimages/lightgray_sold.png">&nbsp;<s:text name="sea.sold.seat.lbl"/>
		<img height="17px" width="17px" src="/main/images/seatingimages/darkpink_blank.png">&nbsp;<s:text name="sea.cuurrent.seat.lbl"/>
		<img height="17px" width="17px" src="/main/images/seatingimages/darkgreen_blank.png">&nbsp;<s:text name="sea.selected.seat.lbl"/>
</div>
</div>
<br/><div class="row">
<div class="col-md-12">
<div id="seatingsection"></div>
</div>
</div>


<hr/>
<!-- <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="submit" id="submitseat" class="btn btn-primary">
                                Continue</button>
                            <button class="btn btn-cancel" id="cancel">
                                <i class="fa fa-times"></i></i></button>
                        </div>
                    </div> -->
                    
                    <div class="row"><center>
                        
                            <button type="submit" id="submitseat" class="btn btn-primary"><s:text name="global.continue.btn.lbl"/></button>
                            <button class="btn btn-cancel" id="cancel">
                                <i class="fa fa-times"></i></button>
  </center>
                    </div>
                    

</div>
    <script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
    <script src="/main/bootstrap/js/bootstrap.js"></script>
    <script src="/main/js/jquery.tooltipster.js"></script>
    <script src="/main/bootstrap/js/bootbox.min.js"></script>
<script>
var profilekey="";
var seatingresponse="";
var eventdate="";
var profileseats=new Object();
var scodes=new Object();
var selected="";
var selectedsection="";
var previoussoldseat="";
getseatingsection();

function alignChangeSeatUIForSpecificEvent(eid){
	if(eid=='175736263'){
		document.getElementById('seatcell').style.width='100%';
		document.getElementById('seatstab').style.width='830px';
		document.getElementById('seatstab').style.paddingLeft='17px';
		document.getElementById('seatstab').style.paddingTop='33px';
	}
	
}

 function getseatingsection(){
	//alert("get seating section");
	var eid=$('#eid').val();
	var tid=$('#transid').val();
	var eventdate=$('#evtdate').val();
	$('#seatingsection').html('');
	$('#seatingsection').html("<div id='seatcell' style='overflow: auto; width: 500px; border: 0px solid rgb(51, 102, 153); padding: 10px;'>"+
		"</div>");
		$('#seatcell').html("<center>Processing...<br><img src='../images/ajax-loader.gif'></center>");

		var url='SeatingAddManual!getSeatingSection?eid='+eid+'&eventdate='+eventdate+'&tid='+tid+'&timestamp='+(new Date()).getTime();
		$.ajax({
			type:'get',
			url:url,
			success:function(result){
				seatingsectionresponse(result);
				parent.resizeIframe();
				alignChangeSeatUIForSpecificEvent(eid);
				}
			
			});
		
	}


 function seatingsectionresponse(response){
		var data=response;
		var responsedata=eval('(' + data + ')');
		seatingresponse=responsedata;
		var sectionid=$('#section').val();
		//alert(responsedata.allsections[sectionid]);
		generateSeating(responsedata.allsections[sectionid]);
		if($('#section > option').length==1) $("#sectiondiv").hide(); 
		
	}


 function generateSeating(data){
		var idarr=[];
		var inc=0;
		var seatinfo=new Object();
		json=data;
		var sectionid=$('#section').val();
		var header="headers_"+sectionid;
		var headerobj=json[header];
		var row_header=""+headerobj.rowheader;
		var col_header=""+headerobj.columnheader;
		var rowheader=row_header.split(",");
		var colheader=col_header.split(",");
		var finalseats=json.completeseats;
		//ticketnameids=finalseats["s_1_1"].ticketnameids;
		ticketnameids=finalseats.ticketnameids;//modified on may 28th
		var cursind=$("#seatindex").val();
		var cell=document.getElementById("seatcell");
		while(cell.hasChildNodes()){
			cell.removeChild(cell.lastChild);
		}
		var table=document.createElement("table");
		table.setAttribute('border','0');
		table.setAttribute('align','center');
		table.setAttribute('id','seatstab');	
		var tbody=document.createElement("tbody");
		
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
	      h.appendChild(s);
	    }
		
		
		if(json.background_image!=undefined && json.background_image!=""){
		var bgimage=json.background_image;
		table.style.cssText='background:url("'+bgimage+'") no-repeat  scroll 0 0 transparent; background-image:url() no-repeat  scroll 0 0 transparent;';
		}
		
		
		
		
		for(var i=0;i<=json.noofrows;i++){
		var row=document.createElement("tr");
		row.setAttribute("id","row_tr_"+i)
		for(var j=0;j<=json.noofcols;j++){
		var col=document.createElement("td");
		if(i==0||j==0){
			col.setAttribute('align','center');
			col.setAttribute('id','header_'+sectionid+"_"+i+"_"+j);
			
			var p=document.createElement("div");
			if(i==0&&j>0&&colheader[j-1]!=undefined&&colheader[j-1]!=""){
			p.innerHTML=colheader[j-1];
			col.className="layoutheader";
			}
			if(i>0&&j==0&&(rowheader[i-1]!=undefined&&rowheader[i-1]!="")){
			p.innerHTML=rowheader[i-1];
			col.className="layoutheader";
			}
			col.appendChild(p);
		}else{
			var sid="s_"+i+"_"+j;
			//var type=finalseats[sid].type;
			//if(type==undefined) type="";
			//var scode=finalseats[sid].seatcode;
			
			//modified on may 28th
			var type="noseat";
			var ticketname="NA";
			var sc_index_tic="0";
			var scode="";
			if(finalseats[sid]){
					type=finalseats[sid].type;
					scode=finalseats[sid].seatcode;
					sc_index_tic=sectionid+"_"+i+"_"+j;//finalseats[sid].seat_ind;
					}
			if(type==undefined) type="";
			if(scode=="EMPTY") scode="";
			scodes[sc_index_tic]=scode;
			seatinfo[sc_index_tic]="Seat Number: "+scode;
			var imgsrc="";
					 if(sc_index_tic==cursind){
						imgsrc="/main/images/seatingimages/darkpink_blank.png";
					 }else if(sc_index_tic==selected){
		    			imgsrc="/main/images/seatingimages/darkgreen_blank.png";
					}
					else if(type.indexOf("SO")>-1){
						    col.className="sold";
							imgsrc="/main/images/seatingimages/lightgray_sold.png";
							profileseats[sc_index_tic+'_type']="sold";
							profileseats[sc_index_tic+'_src']=imgsrc;
						}
					else if(type.indexOf("Hold")>-1){
							col.className="hold";
							imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
						}
					else{
						imgsrc="/main/images/seatingimages/lightgray_blank.png";
						profileseats[sc_index_tic+'_src']=imgsrc;
					}

			if(type=="noseat"){
				col.className='noseat';
				}
			if(type!="noseat"){
			var img=document.createElement("img");
			img.setAttribute("src",imgsrc);
			img.setAttribute("height",imgheight);
			img.setAttribute("width",imgwidth);
			
			col.appendChild(img);
			
			col.setAttribute('align','center');
			
			
				idarr[inc]=col;
				inc++;
			}	
			col.setAttribute('id',sc_index_tic);

			
		}
		row.appendChild(col);
		}
		tbody.appendChild(row);
		}
		table.appendChild(tbody);
		cell.appendChild(table);


	var sed=document.getElementById("seatstab").getElementsByTagName("td");

	for(var b=0;b<idarr.length;b++){
		
		if(idarr[b].className!="noseat" && idarr[b].className!="hold" && idarr[b].id!=cursind){
		idarr[b].onclick=changeseat;
		}
	}
	/* seatsTooltip = new YAHOO.widget.Tooltip("seatsTooltip", { 
			container: YAHOO.ebee.popup.dialog.element,
	   		 context: idarr,
	   		 showdelay: 0,
			 hidedelay:0 
	 	});
	 	seatsTooltip.contextTriggerEvent.subscribe( 
		    function(type, args) { 
		        var context = args[0];
		        this.cfg.setProperty("text",  seatinfo[context.id]); 
		    } 
		); */
	settooltip(seatinfo);

	var eid=$('#eid').val();
	if(eid=='183338054'){
	$('#seatstab').removeClass('tempClass').addClass('tempClass');
	}
	
	}


 var changeseat=function(){
	 //var btns=YAHOO.ebee.popup.dialog.cfg.getProperty("buttons");
	 //alert(this.className);


	 var clsName=$(this).attr('class').split(' ')[0];
	 	if(clsName=="sold"){
	 		previoussoldseat="alreadysold";
	 		//var agree=confirm("This seat is alloted. Do you want to Continue?");
	 		bootbox.confirm('This seat is alloted. Do you want to Continue?', function (result) {
	 		if(!result){
	 			return;
	 		}
	 		});
	 	}
	 	unselectdata(selected);
	 	selected=$(this).attr('id');
	 	selectedsection=$("#section").val();
	 	$(this).children().attr("src","../images/seatingimages/darkgreen_blank.png");
	 };

 function unselectdata(tempid){
	 if(tempid!=undefined){
	 	var tempsrc=profileseats[tempid+'_src'];
	 	var temptype=profileseats[tempid+'_type'];
	 	if($('#'+tempid))
	 	$('#'+tempid).children().attr("src",tempsrc);
	 		if(temptype=="sold")
	 		$('#'+tempid).attr("class","sold");
	 	}
	 }


 function settooltip(seatinfo){
		$.each( seatinfo, function( key, value ) {
			$('#'+key).tooltipster({
				//theme: 'my-custom-theme',
	            fixedWidth:'300px',
		        content: $('<span>'+value+'</span>')
		    });
			});
 }


 function assignseat(){
     $("#seatcode").val(scodes[selected]);
	$("#seatindex").val(selected);
	$("#sectionid").val(selectedsection);
	//YAHOO.ebee.popup.wait.show();
	/* $("attendeeseatform").request({
		onFailure: function() { YAHOO.ebee.popup.wait.hide();alert('Failed to get results'); },
        onSuccess: function(t) {
        	var result=t.responseText;
	        var seattype=document.getElementById('seataction').value;
	        if(!isValidActionMessage(result)) return;
	        if(result.indexOf("success")>-1){
	        	$("scode_"+profilekey).update(scodes[selected]);
	            YAHOO.ebee.popup.wait.hide();
	        	handleCancel();
	        	window.location.reload();
	        }
        }
	}); */

$.ajax({
	type:'POST',
	data:$('#attendeeseatform').serialize(),
	url:'ManageAttendeeSeats!saveAttendeeSeat',
	success:function(result){
		var seattype=document.getElementById('seataction').value;
		var pk=$('#pid').val();
		  if(!parent.isValidActionMessage(result)) return;
		  if(result.indexOf("success")>-1){
			 //alert("seected::::"+scodes[selected]+"::"+pk);
	        	parent.$("#scode_"+pk).html(scodes[selected]);
	        	var tid=$('#transid').val();
				//parent.showDetails(tid);
				parent.$('#myModal').modal('hide');
	        }
		}
});
	
}

 function getSection(){
		var sectionid=$('#section').val();
		generateSeating(seatingresponse.allsections[sectionid]);
	}
 

 $(document).ready(function(){
	 
	 $('#cancel').click(function(){
		 parent.$('#myModal').modal('hide');
		});

	 

$('#submitseat').click(function(){

	
	if(selected!=""){
		var eventid=document.getElementById('eid').value;
		if(previoussoldseat==''){
		var url="ManageAttendeeSeats!checkSeatStatus?eid="+eventid+"&selectedseat="+selected;
	    $.ajax( {
		    	       type: 'post',
		    	       url:url,
		    	       success: function(data){
		               //var data=t.responseText;
		               if(!parent.isValidActionMessage(data)) return;
		               var seatjson=eval('('+data+')');
		               var seatstatus=seatjson.seatstatus;
		               if(seatstatus=='hold'){
		                   var agree=confirm("This seat is on hold. Do you want to Continue?");
			               if(!agree)
				           return;
				           else
				           assignseat();
				         }  
		                 if(seatstatus=='sold'){
		                   var agree=confirm("This seat is sold. Do you want to Continue?");
			               if(!agree)
				           return;
				           else
				           assignseat();
		                 }
		                 if(seatstatus==''){
		                  assignseat();  
		                 } 
		              }
		});               
		   }else
		     assignseat();
		}
		else{
			if(document.getElementById('seataction').value=='changeseat')
			bootbox.alert('<s:text name="sel.seat.to.change.msg"/>');
			else if(document.getElementById('seataction').value=='addseat')
				bootbox.alert('<s:text name="sel.seat.to.add.msg"/>');
		}


	
});



	 });


 
 
	

</script>