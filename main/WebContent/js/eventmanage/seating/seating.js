var eid='';
var timestamp=new Date();
var color="";
var oldcolor="";
var selected=new Object();
var seatcolors="";
var colorchoice="";
var venueid="";
var section_id="";
YAHOO.namespace("ebee.seating");
function getSeating(){
	color="";
	oldcolor="";
	selected=new Object();
	colorchoice="";
	var sectionid=document.getElementById("section").value;
	document.getElementById("loading").style.display="block";
	document.getElementById("seatsdiv").style.display="none";
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
      h.appendChild(s);
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
		//var type=finalseats[sid].type;
		//var scode=finalseats[sid].seatcode;
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
		s=s+"Seat Number: "+scode;
		var seatposition="<br>Row: "+i+" "+"Column: "+j;
		if(type){
		imgsrc="../images/seatingimages/"+type+".png";
		col.className=type;
		if(type.indexOf("sold")>-1){
				s=s+"<br><b>Sold Out</b>";
				nsandso[finalseats[sid].seat_ind]="yes";
				}
		else if(type.indexOf("NA")>-1)
				s=s+"<br><b>Not Available</b>";
		else if(type.indexOf("lightgray_exclaimation")>-1){
			nsandso[finalseats[sid].seat_ind]="yes";
			s=s+"<br><b>Currently on hold</b>";
		}
		else if(type=="noseat"){
				s="";
				//nsandso[finalseats[sid].seat_ind]="yes";
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
		oldcolor=$(this).attr("class");
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
	
	seatsTooltip = new YAHOO.widget.Tooltip("seatsTooltip", { 
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
 	try{
	displayTcktsColors(seatcolors);
}catch(err){}
 	document.getElementById("loading").style.display="none";
	document.getElementById("seatsdiv").style.display="block";
}

function saveseats(){
	document.getElementById("loading").style.display="block";
	document.getElementById("seatsdiv").style.display="none";
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
			if(isValidActionMessage(result))
			getSeating();
		}
	);
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

function getSeatColors(){
	eid=document.getElementById("eid").value;
	$.get("Seating!getSeatColors?eid="+eid+"&powerType="+powertype+"&timestamp="+(new Date()).getTime(),function(result){
		displayYUISeatTypesTable(result);
		seatcolors=result;
	});
}
function displayYUISeatTypesTable(jsondata){
	
	YAHOO.ebee.seating.Data=eval('('+jsondata+')');
	var dt_table_dd=instantiateSeatTypesTable(YAHOO.ebee.seating.Data.seattypes,"seattypesdiv");
}
function instantiateSeatTypesTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["color","tickets"]
        }
    });
    var myCustomSeatTypeColorFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var color = oRecord.getData("color");
         elLiner.innerHTML = "<img src='../images/seatingimages/"+color+".png'>";                   
    };
    var myCustomSeatTypeTicketsFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var color = oRecord.getData("color");
    	var ticketsdisplaycontent="<span>";
    	var tickets = oRecord.getData("tickets")
    	if(tickets){
    	if(tickets.length==0) ticketsdisplaycontent="No Tickets";
    	for(var i=0;i<tickets.length;i++){
        	ticketsdisplaycontent+="<li> "+tickets[i]+"</li>";
        	}
    	}
    	if(color=='lightgray_question') ticketsdisplaycontent="Unassigned";
    	else if(color=='lightgray_blank') ticketsdisplaycontent="Not Available";
    	else if(color=='lightgray_sold') ticketsdisplaycontent="Sold Out";
    	else if(powertype=='RSVP') ticketsdisplaycontent="Seat";
	   	ticketsdisplaycontent+="</span>";
         elLiner.innerHTML = ticketsdisplaycontent;                   
    };
    var myCustomSeatTypeActionFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var color = oRecord.getData("color");
    	 if(color!='lightgray_question' && color!='lightgray_blank' & color!='lightgray_sold') {
    	 var deletelink=' | <a href="#delete" onclick="deleteSeatType(\''+color+'\')">Delete</a>';
         elLiner.innerHTML = '<a href="#edit" onclick="selectTickets(\''+color+'\')">Edit</a>'+deletelink;
         }                   
    };
    YAHOO.widget.DataTable.Formatter.mycolordisplay = myCustomSeatTypeColorFormatter;
    YAHOO.widget.DataTable.Formatter.myticketsdisplay = myCustomSeatTypeTicketsFormatter;
    if(powertype!='RSVP'){
    YAHOO.widget.DataTable.Formatter.myactiondisplay = myCustomSeatTypeActionFormatter;
    var myColumnDefs = [            
            {key:"color",label:"",formatter:"mycolordisplay"},
            {key:"Tickets",label:"Selected Tickets",formatter:"myticketsdisplay"},
            {key:"Actions", label:"",formatter:"myactiondisplay"}            
        ];
     }
     else{
     	var myColumnDefs = [            
            {key:"color", label:"Type",formatter:"mycolordisplay"},
            {key:"Tickets", label:"Legend",formatter:"myticketsdisplay"},            
        ];
     }
    var myDataTable =   new YAHOO.widget.ScrollingDataTable(cname, myColumnDefs, myDataSource,{height:"15em"},{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
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
	col4.innerHTML="Seat Types <span class='helpboxlink' id='seattypeselecthelp' ><img	src='../images/questionMark.gif' /></span>: ";
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
			ticks[color]="Unassigned";
		else if(color=="lightgray_blank")
			ticks[color]="Not Available";
		else
		ticks[color]=ttl;
		row1.appendChild(col1);
		if(tckts && tckts.length<1)
		row1.removeChild(col1);
		seltabbody.appendChild(row1);
	}
	seltable.appendChild(seltabbody);
	colorsTooltip = new YAHOO.widget.Tooltip("colorsTooltip", { 
   		 context: arr
 	});
 	colorsTooltip.contextTriggerEvent.subscribe(
	    function(type, args) { 
	        var context = args[0]; 
	        if(context.id=="lightgray_question" || context.id=="lightgray_blank")
	        	this.cfg.setProperty("text", ticks[context.id]);
	        else
	        this.cfg.setProperty("text", "<b>Tickets:</b><br> " +ticks[context.id]); 
	    } 
	); 
	loadHelpPanel("seattypeselecthelppanel", "seattypeselecthelp", "500px");
	createListoner();
}

function addseat()
{
	colorchoice="";
	eid=document.getElementById("eid").value;
	section_id=document.getElementById("section").value;
	venueid=document.getElementById("venueid").value;
	var url='Seating!getTicketsandColors?eid='+eid+"&sectionid="+section_id+"&venueid="+venueid;
	loadURLBasedWindow(url, getseatColorsSuccess);
}

var getseatColorsSuccess = function(responseObj){
	var result=responseObj.responseText;
	if(isValidActionMessage(result)){
	if(result.length==1 && result==0){
		YAHOO.ebee.popup.wait.hide();
		alert("No more seat types to add.You have added all available seat types");
	}else{
	showPopUpDialogWindow(responseObj, "Seat Types", submitSeatColorsForm, handleCancel);
	colorSelect();
	}	
	}
}
var k=0;
var submitSeatColorsForm=function(){
	k=++k;
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
	     		alert('Select Atleast One Ticket');
	     		return;			
	     	}
	   }
	   else
	   {
	   		if(value==0){
	   			alert('Select Atleast One Ticket');
	     		return;
	   		}
	   }}
	document.getElementById("color").value=colorchoice;	
	if(k==1)
	$('#addseatform').submit();
	}
	else{
		alert("Select a seat type");
	}	
}

function selectTickets(color){
	if(color!="" && color!="unassign" && color!="whiteNA"){
	eid=document.getElementById("eid").value;
	section_id=document.getElementById("section").value;
	venueid=document.getElementById("venueid").value;
	var url='Seating!getTicketsForColor?eid='+eid+"&sectionid="+section_id+"&venueid="+venueid+"&colorCode="+color;
	loadURLBasedWindow(url, seatTicketsSuccess);
	}
	else 
		alert("Select a seat type");	
}
function deleteSeatType(color){
	var agree=confirm("Do you really want to delete?");
	eid=document.getElementById("eid").value;
	venueid=document.getElementById("venueid").value;
	if (agree){	
		var url='Seating!deleteSeat?eid='+eid+'&venueid='+venueid+"&colorCode="+color;
		var ajax= new Ajax.Request(url, { method:'get',
      	onSuccess: function(){
     	 window.location.href="../eventmanage/Seating?eid="+eid; }
 		 });
	}
}
var seatTicketsSuccess = function(responseObj){
	if(responseObj.responseText=="notickets"){
		YAHOO.ebee.popup.wait.hide();
		alert("Currently there are no tickets added to the event.");
		return;
	}
	showPopUpDialogWindow(responseObj, "Tickets", submitSeatTicketsForm, handleCancel);	
}

var submitSeatTicketsForm=function(){
	$('#seatticketsform').submit();
}

function colorSelect(){
$("#coloroptions td").click(function() {
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

function clearAllcolors(){
	$("#coloroptions td").each(function(){
			$(this).children("img").css("border","0px none");
	});
	colorchoice="";
}


function checkAll(field)
{	
	var len = field.length;
	if(len == undefined) {
		field.checked = true ;
	}else{
		for (i = 0; i < field.length; i++){			
			field[i].checked = true ;
		}
	}
}

function uncheckAll(field)
{
var len=field.length;
if(len==undefined)
{
	field.checked=false;
}
else{
for (i = 0; i < len; i++){	
	if(len==1){		
		field.checked = false ;		
	}else{
	field[i].checked = false ;
	}
}
}
}

function addVenue(){
	eid=document.getElementById("eid").value;
	var vid=document.getElementById("venues").value;
	if(vid=='' || (curvenueid==vid && curvenueid!='')){
	}
	else{
	var ind=document.getElementById("venues").selectedIndex;
	var selven=document.getElementById("venues").options[ind].text;
	var rep={responseText:"<center>Importing "+selven+" layout. Do you want to continue?</center>"};
	var myButtons = [
	                 { text: "Continue", handler: addvenuecont },
	                 { text: "Cancel", handler: canceladd }
	             ];
	showPopUpDialogWithCustomButtons(rep, "Import Venue", myButtons);
	}
}
function addvenuecont(){
	var vid=document.getElementById("venues").value;
	window.location.href="../eventmanage/Seating!addVenue?eid="+eid+"&venueid="+vid;
}
function canceladd(){
		var dropdown=document.getElementById('venues');
		for(i=0;i<dropdown.options.length;i++){
   		var currentsel=dropdown.options[i].value;
   		if(currentsel==curvenueid){
      	dropdown.options[i].selected=true;
    	  break;
    	 }
	}
	handleCancel();
}
function showvenue(val){
	YAHOO.ebee.popup.wait.show();
	eid=document.getElementById("eid").value;
	var url='Seating!showvenue?eid='+eid+'&showvenue='+val;
		var ajax= new Ajax.Request(url, { method:'get',
      	onSuccess: function(t){
      		YAHOO.ebee.popup.wait.hide();
 		 }});
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
}