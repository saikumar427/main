var profilekey="";
var seatingresponse="";
var eventdate="";
var profileseats=new Object();
var scodes=new Object();
var eid="";
var tid="";
var selected="";
var selectedsection="";
var previoussoldseat="";
function getSeatingChart(eventid,tranid,pk,edate,seataction){
	//alert("the eventi d::"+eventid+"::transid::"+tranid+"::"+pk+"::edate::"+edate+"::"+seataction);
	selected="";
	eventdate=edate;
	eid=eventid;
	tid=tranid;
	if(pk!='')
	profilekey=pk;
	var dynatimestamp = new Date();
	var url='ManageAttendeeSeats!getSeatingChart?eid='+eid+'&profilekey='+pk+'&tid='+tranid+'&seataction='+seataction;
	url += '&dynatimestamp='+dynatimestamp.getTime();
	if(seataction=='changeseat')
	$('.modal-title').html('Change Seat');
	else
	$('.modal-title').html('Add Seat');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');
}

var getseatChartSuccess = function(responseObj){
  if(document.getElementById("popupdialog_c"))
	{document.getElementById("popupdialog_c").style.left='214px';
	document.getElementById("popupdialog_c").style.top='103px';
	}
	var result=responseObj.responseText;
	var myButtons=[
	                 { text: "Submit", handler: submitSeatChartForm },
	                 { text: "Cancel", handler: handleReset }
	             ];
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
	selected="";		
	});
	
	if(result.indexOf("changeseat")>-1)
	showPopUpDialogWithCustomButtons(responseObj, "Change Seat", myButtons);
	else if(result.indexOf("addseat")>-1)
	showPopUpDialogWithCustomButtons(responseObj, "Add Seat", myButtons);
	getseatingsection();
	var ops=$("section").options;
	if(ops.length==1) $("sectiondiv").hide();
}



var submitSeatChartForm=function(){
	if(selected!=""){
	var eventid=document.getElementById('eid').value;
	if(previoussoldseat==''){
	url="ManageAttendeeSeats!checkSeatStatus?eid="+eventid+"&selectedseat="+selected;
    new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	               var data=t.responseText;
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
		alert("Select a seat to change");
		else if(document.getElementById('seataction').value=='addseat')
		alert("Select a seat to add");
	}
}
function assignseat(){
     $("seatcode").value=scodes[selected];
	$("seatindex").value=selected;
	$("sectionid").value=selectedsection;
	YAHOO.ebee.popup.wait.show();
	$("attendeeseatform").request({
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
	});
}
function handleReset(){
	selected="";
	YAHOO.ebee.popup.dialog.cancel();
}
function unselectdata(tempid){
if(tempid!=undefined){
	var tempsrc=profileseats[tempid+'_src'];
	var temptype=profileseats[tempid+'_type'];
	if($(tempid))
	$(tempid).childNodes[0].setAttribute("src",tempsrc);
		if(temptype=="sold")
		$(tempid).className="sold";
	}
}
function getseatingsection(){
	alert("get seating section");
$('#seatingsection').html('');
$('#seatingsection').html("<div id='seatcell' style='overflow: auto; width: 750px;border: 0px solid rgb(51, 102, 153); padding: 10px;'>"+
	"</div>");
	$('#seatcell').html("<center>Processing...<br><img src='../images/loading.gif'></center>");
	/*new Ajax.Request('SeatingAddManual!getSeatingSection?timestamp='+(new Date()).getTime(), {
	 method: 'get',
	 parameters:{eid:eid,eventdate:eventdate,tid:tid},
	 onSuccess: seatingsectionresponse,
	 onFailure:  failureJsonResponse
	 });*/
}


function failureJsonResponse(){
alert("failed");
}
function seatingsectionresponse(response){
	var data=response.responseText;
	var responsedata=eval('(' + data + ')');
	seatingresponse=responsedata;
	var sectionid=$('section').value;
	generateSeating(responsedata.allsections[sectionid]);
}

function getSection(){
	var sectionid=$('section').value;
	generateSeating(seatingresponse.allsections[sectionid]);
}
function generateSeating(data){
	var idarr=[];
	var inc=0;
	var seatinfo=new Object();
	json=data;
	var sectionid=$('section').value;
	var header="headers_"+sectionid;
	var headerobj=json[header];
	var row_header=""+headerobj.rowheader;
	var col_header=""+headerobj.columnheader;
	var rowheader=row_header.split(",");
	var colheader=col_header.split(",");
	var finalseats=json.completeseats;
	//ticketnameids=finalseats["s_1_1"].ticketnameids;
	ticketnameids=finalseats.ticketnameids;//modified on may 28th
	var cursind=$("seatindex").value;
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
seatsTooltip = new YAHOO.widget.Tooltip("seatsTooltip", { 
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
	);
}
var changeseat=function(){
var btns=YAHOO.ebee.popup.dialog.cfg.getProperty("buttons");
	if(this.className=="sold"){
		previoussoldseat="alreadysold";
		var agree=confirm("This seat is alloted. Do you want to Continue?");
		if(!agree)
			return;
	}
	unselectdata(selected);
	selected=this.id;
	selectedsection=$("section").value;
	this.childNodes[0].setAttribute("src","../images/seatingimages/darkgreen_blank.png");
}
