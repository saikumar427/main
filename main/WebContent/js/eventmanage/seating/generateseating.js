var sel_seatcodes=new Array();
var sel_ticket=new Object();
var ticket_count=new Object();
var ids="";
var ticketnameids=new Object();
var seating_ticketids=new Array();
var seatposition="";
var section_sel_seats=new Object();
var seatinfo=new Object();
var button="<center><input type='button' value='"+props.sea_select_ticket_lbl+"' id='accept' onclick='closeit(\"Y\")'><input type='button' value='"+props.global_cancel_lbl+"' id='cancel' onclick='closeit(\"N\")'></center>";
var sel_select=new Object();	
var seatcode_ticid=new Object();
var sel=false;
var count=0;
var reg_timeout='';
var layoutcount=0;
var dialogclose;

function generateSeating(data){
	var idarr=[];
	var inc=0;
	json=data;
	var sectionid=document.getElementById('section').value;
	var header="headers_"+sectionid;
	var headerobj=json[header];
	var row_header=""+headerobj.rowheader;
	var col_header=""+headerobj.columnheader;
	var rowheader=row_header.split(",");
	var colheader=col_header.split(",");
	var finalseats=json.completeseats;
	//ticketnameids=finalseats["s_1_1"].ticketnameids;
	ticketnameids=finalseats.ticketnameids;//modified on may 26th
	
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
	sel_select[sectionid+"_"+i+"_"+j]=false;
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
	
		var ttHtml="";
		var sid="s_"+i+"_"+j;
		
		/*var type=finalseats[sid].type;
		var ticketname=finalseats[sid].ticketname;
		
		var ticketid=finalseats[sid].ticketids;*///modified on may 26th
		
		//added on may 26th
		var type="noseat";
		var ticketname="NA";
		var sc_index_tic="0";
		var scode="";
		var ticketid=[];
		if(finalseats[sid]){
				type=finalseats[sid].type;
				ticketid=finalseats[sid].ticketids;
				scode=finalseats[sid].seatcode;
				sc_index_tic=sectionid+"_"+i+"_"+j;//finalseats[sid].seat_ind;
				}
		
		
		if(ticketid==undefined)ticketid=[];
		else
			ticketid=getavailableticketid(ticketid);
		//alert("avaass::"+ticketid);
			
	
		//var scode=finalseats[sid].seatcode;//modified on may 26th
		if(scode=="EMPTY") scode="";
		var imgsrc="";
			
		ttHtml=ttHtml+""+props.aa_seating_tooltip_seatnumber_txt+": "+scode;
		if(type){
			imgsrc="/main/images/seatingimages/"+type+".png";
			col.className=type;
			
			if(ticketid==undefined||ticketid.length==0){
				
				if(type.indexOf("SO")>-1){
				
						ttHtml=ttHtml+" <br><b><font color='red'>"+props.sea_sold_out+"</font></b>";
						//var curtype=type.split("_");
						//imgsrc="/main/images/seatingimages/"+curtype[0]+"_sold.png";
						imgsrc="/main/images/seatingimages/lightgray_sold.png";
					}
				else{
					col.className='unassign';
					imgsrc="/main/images/seatingimages/lightgray_blank.png";
					ttHtml=ttHtml+"<br><b><font color='red'>"+props.sea_not_avail_lbl+"</font></b>";
					}
				}
			else{
				if(type.indexOf("SO")>-1){
						ttHtml=ttHtml+" <br><b><font color='red'>"+props.sea_sold_out+"</font></b>";
						//var curtype=type.split("_");
						//imgsrc="/main/images/seatingimages/"+curtype[0]+"_sold.png";
						imgsrc="/main/images/seatingimages/lightgray_sold.png";
					}
				else if(type.indexOf("Hold")>-1){
					ttHtml=ttHtml+"<br><b><font color='black'>"+props.sea_currently_on_hold_lbl+"</font></b>";
					//var curtype=type.split("_");
					//imgsrc="/main/images/seatingimages/"+curtype[0]+"_exclaimation.png";
					imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
				}
				else if(type.indexOf("NA")>-1 ){
					ttHtml=ttHtml+"<br><b><font color='red'>"+props.sea_not_avail_lbl+"</font></b>";
				}
				else if(type=="noseat"){
						//ttHtml="<b>No Seat</b>";
						ttHtml='';
						seatposition="";
						}
			}
		}
		else{
		col.className='unassign';
		imgsrc="/main/images/seatingimages/lightgray_blank.png";
		ttHtml=ttHtml+"<br><b><font color='red'>"+props.sea_not_avail_lbl+"</font></b>";
		}
		
	
		ttHtml=ttHtml+seatposition;
		if(type!="noseat"){
		var img=document.createElement("img");
		img.setAttribute("src",imgsrc);
		img.setAttribute("height",imgheight);
		img.setAttribute("width",imgwidth);
		
		//var sc_index_tic=finalseats[sid].seat_ind;//modified on may 26th
		
			var a=new Array();
		if(seatcode_ticid[sc_index_tic]!=undefined){
			seatcode_ticid[sc_index_tic]=a;
		}if(ticketid!=undefined)
			a.push(ticketid);	
		seatcode_ticid[sc_index_tic]=a;
			
		
		col.appendChild(img);
		
		col.setAttribute('align','center');
		
		if(!ticketname || ticketname==''){
			}
		else{
		
		if(ticketid!=undefined && ticketid.length>0){
			ttHtml=ttHtml+'<br><br><u><b>'+props.sea_available_tickets_lbl+' :</b></u>';
			ttHtml=ttHtml+"<ul>";
			for(k=0;k<ticketid.length;k++){
				var tktID=ticketid[k];
				var groupname='';
				if(document.getElementById('ticketGroup_'+tktID)){
				var groupid=$('#ticketGroup_'+tktID).val();
				
			if(ticket_groupnames[groupid]!=undefined){
				groupname='&nbsp;('+ticket_groupnames[groupid]+')';
			}
				ttHtml=ttHtml+"<li>"+ticketnameids[tktID]+groupname+"</li>";
				}else{
					ttHtml=ttHtml+"<li>"+ticketnameids[tktID]+"</li>";
				}
			}
		}
		ttHtml=ttHtml+"</ul>";
		
		}
		
			//ttHtml=ttHtml+"<br>Row: <b>"+i+"</b>"+" Column: <b>"+j+"</b>";
		
			idarr[inc]=col;
			inc++;
		}
		
		//col.setAttribute('id',finalseats[sid].seat_ind);	
		col.setAttribute('id',sc_index_tic);//modified on may 26th
		//seatinfo[finalseats[sid].seat_ind]=ttHtml;
		seatinfo[sc_index_tic]=ttHtml;//modified on may 26th
			
		
	}
	row.appendChild(col);
	}
	tbody.appendChild(row);
	}
	table.appendChild(tbody);
	cell.appendChild(table);
	var div=document.createElement("div");
	div.setAttribute('id','divpopup');
	div.className='ticketwidget';
	div.setAttribute('style','display:none;');
	cell.appendChild(div);
	
	settooltip(idarr);
if(document.getElementById('orderbutton'))$('orderbutton').disabled=false;
var sed=document.getElementById("seatstab").getElementsByTagName("td");
//$("#seatstab img").click(function(){
var tdclick=function(){
dialogclose=false;
//ids=$(this).parent('td').attr('id');
ids=this.id;

if(sel_select[ids]==undefined){
	var a=new Array();
		a.push(false);
		sel_select[ids]=a;
		
		
}
	if(sel_select[ids]==true){
		sel_select[ids]=false;
		this.childNodes[0].style.border="0px none";
		var allticids=seatcode_ticid[ids];
		//var allticids=$(this).attr("ticketid");
		ticid=getTicketIdforSelectedSeat(ids,allticids);
		remove_seats(ids,ticid);
		restoreoldtooltip(ids);
	}
	else{
		sel_select[ids]=true;
		//var chkclass=$(this).parent("td").attr('class');
		var chkclass=this.className;
		if(chkclass!=""&&chkclass!="noseat"&&chkclass!="unassign"&&chkclass!="white_NA"&&chkclass.indexOf('SO')==-1&&chkclass.indexOf('Hold')==-1&&chkclass!='layoutheader'){
			ids=this.id;
			var sel_ticid=seatcode_ticid[ids];
			//var sel_ticid=$(this).attr('ticketid');
			sel_ticid=""+sel_ticid;
			var selticketid=new Array();
			selticketid=sel_ticid.split(",");
			if(selticketid.length==1){
				//$(this).css("border","1px solid red");
				this.childNodes[0].style.border="1px solid red";
				var cur_title=seatcode_ticid[ids];
				//var cur_title=$(this).attr('ticketid');
				var cur_id=this.id;
				sel_seatcodes.push(cur_id);
				
				fillticketqty(cur_title);
			}
			else if(selticketid.length > 1){
				ids=this.id;
				var radio="<p>"+props.sea_assigned_multi_select_one_lbl+"</p>";
				radio=radio+"<table>";
				for(i=0;i<selticketid.length;i++){
					var tktID=selticketid[i];
					var groupname='';
					var groupid=$('#ticketGroup_'+tktID).val();
					if(ticket_groupnames[groupid]!=undefined){
						groupname='&nbsp;('+ticket_groupnames[groupid]+')';
					}	
					radio=radio+"<tr><td valign='top' align='left'><input type='radio' name='selticketid' id='selticketid' value='"+selticketid[i]+"'></td><td valign='top' align='left'><div style='margin: 1px 0px 0px 5px;'> "+ticketnameids[tktID]+groupname+"</div></td></tr>";
				}
				radio=radio+"</table>";
			//showYUIpopUp(radio);
				
				$('.modal-title').html(props.aa_sel_tkts_section_header);
			//	$('#myModal').on('show.bs.modal', function () {
				var url='/main/eventmanage/addattendee/selectticket.jsp';
				$('iframe#popup').attr("src",url);
				$('iframe#popup').css("height","200px"); 
				$('.modal-dialog').css('width','500px');
				$('#tktdetails').val(radio);
			//	});
				$('#myModal').modal('show');
				//alert("ids::"+ids);
				
				
			}
			else{
				//alert("not available or no seats assigned to this seat");
				bootbox.confirm(props.sea_noseats_assigned_lbl,function(result){});
			}
		}
		}
}
for(var b=0;b<sed.length;b++){
	sed[b].onclick=tdclick;
}
}

function showYUIpopUp(radio){
	YAHOO.ebee.popup.wait.show();
	YAHOO.ebee.popup.wait.hide();
	var taskButtons = [
	                 { text: props.aa_sel_tkts_section_header, handler: selectTicket },
	                 { text: props.global_cancel_lbl, handler: cancelTicketSelection }
	             ];
	 YAHOO.ebee.popup.dialog.setHeader(props.sea_multiple_tickets_lbl);
    YAHOO.ebee.popup.dialog.setBody(radio);
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", taskButtons);
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		if(!dialogclose)
			sel_select[ids]=false;		
	});
	adjustYUIpopup();
}


function getSelectedTicket(tktid){
	
	$('#myModal').modal('hide');
	if(tktid!=null && tktid!=undefined){
	sel_select[ids]=true;
	document.getElementById(ids).childNodes[0].style.border="1px solid red";
	sel_seatcodes.push(tktid);
	fillticketqty(tktid);
	}else{
		sel_select[ids]=false;
	}
}

var cancelTicketSelection=function(){
	dialogclose=true;
	sel_select[ids]=false;
	handleCancel();
}
function settooltip(idarr){
	$.each( seatinfo, function( key, value ) {
		$('#'+key).tooltipster({
			theme: 'my-custom-theme',
            fixedWidth:'300px',
	        content: $('<span>'+value+'</span>')
	    });
		});
	
	
	
	
	
	
	/*seatsTooltip = new YAHOO.widget.Tooltip("seatsTooltip", { 
   		 context: idarr,
		 disabled: false,
		 showdelay: 0,
		 hidedelay:0
 	});
 	seatsTooltip.contextTriggerEvent.subscribe( 
	    function(type, args) { 
	        var context = args[0]; 
			
	        this.cfg.setProperty("text",  seatinfo[context.id]); 
			
	    } 
	); */
}




function addticketidinall(ticketid){
if(seating_ticketids.length>1){

}
else{
seating_ticketids[0]=ticketid;
}
}

var selectTicket=function(){
		dialogclose=true;
		var selticktemp=document.getElementsByName('selticketid');
		for(var i = 0; i < selticktemp.length; i++){
			if(selticktemp[i].checked){
				sel_select[ids]=true;
				document.getElementById(ids).childNodes[0].style.border="1px solid red";
				sel_seatcodes.push(selticktemp[i].value);
				fillticketqty(selticktemp[i].value);
				break;
			}
			else
				sel_select[ids]=false;
		}
	//YAHOO.ebee.popup.dialog.cancel();
}

function fillticketqty(id){
	//alert("id is::"+id);
	var cur_id="qty_"+id;
	var show_id="show_"+id;
	var count=0;
	var tic_dropdown=document.getElementById(cur_id).value;
	var min_qty=min_ticketid[id];
	var max_qty=max_ticketid[id];
	count=ticket_count[cur_id];

	if(count==undefined){
		count=0;
	}
	count++;
	ticket_count[cur_id]=count;
	if(count>=min_qty && count<=max_qty){
		tic_dropdown=Number(tic_dropdown)+1;
		
		document.getElementById(cur_id).value=tic_dropdown;
		document.getElementById(show_id).innerHTML=tic_dropdown;
		add_seats(ids,id);
	}
	else{
		if(count<min_qty){
			tic_dropdown=Number(tic_dropdown)+1;
			document.getElementById(cur_id).value=tic_dropdown;
			//alert("for \""+ticketnameids[id]+"\" ticket, you need to select minimum of "+min_qty+" seats");
			bootbox.confirm(props.sea_min_bootbox_lbl1+" \""+ticketnameids[id]+"\" "+props.sea_min_bootbox_lbl2+" "+min_qty+" "+props.sea_min_bootbox_lbl3,function(result){});
			add_seats(ids,id);
		}
		else if(count>max_qty){
			//alert("maximum quantity reached for \""+ticketnameids[id]+"\" ticket type");
			bootbox.confirm(props.sea_max_bootbox_lbl1+" \""+ticketnameids[id]+"\" "+props.sea_max_bootbox_lbl2,function(result){});
			remove_extra(ids,id);
		}
		
	}

}

function add_seats(seatid,ticketid){
/*start-ticket level seats selection */
	if(sel_ticket[ticketid]==undefined){
		var a=new Array();
		a.push(seatid);
		sel_ticket[ticketid]=a;
		
	}
	else{
		var a=sel_ticket[ticketid];
		a.push(seatid);
		sel_ticket[ticketid]=a;
	}
	/*End-ticket level seats selection*/
	/*Start Section level selected seats*/
	if(section_sel_seats[sectionid]==undefined){
		var a=new Array();
		a.push(seatid);
		section_sel_seats[sectionid]=a;
	}
	else{
		var a=section_sel_seats[sectionid];
		a.push(seatid);
		section_sel_seats[sectionid]=a;
	}
	/*End-section level selected seats*/
	addselectiontotooltip(seatid,ticketid);
}

function remove_extra(seatid,ticketid){
	var drop_down_id="qty_"+ticketid;
	var	removecount=ticket_count[drop_down_id];

	if(removecount!=undefined){
		removecount--;
		ticket_count[drop_down_id]=removecount;	
	}
	//$("#"+seatid+" img").css("border","0px none");
	document.getElementById(seatid).childNodes[0].style.border="0px none";
	//$("#"+seatid).css("border","0px none");
	sel_select[seatid]=false;
}

function remove_seats(seatid,ticketid){
	var drop_down_id="qty_"+ticketid;
	var drop_down_id1="show_"+ticketid;
	var	removecount=ticket_count[drop_down_id];
	if(removecount!=undefined){
		removecount--;
		ticket_count[drop_down_id]=removecount;	
		
		
	}
	var drop_down=0;
	if(document.getElementById(drop_down_id))
		drop_down=document.getElementById(drop_down_id).value;
	var seat_temp=section_sel_seats[sectionid];
	if(seat_temp!=undefined){
		for(l=0;l<seat_temp.length;l++){
			if(seat_temp[l] == seatid){
				seat_temp.splice(l,1);
				section_sel_seats[sectionid]=seat_temp;
			}
		}
	}
	var temp=sel_ticket[ticketid];
		if(temp!=undefined){
			
			for( var k = 0; k < temp.length; k++ )
				{
					if( temp[ k ] == seatid )
					{
						temp.splice(k,1); 
						sel_ticket[ticketid]=temp;
						if(drop_down!=0){
							drop_down=Number(drop_down)-1;
							document.getElementById(drop_down_id).value=drop_down;
							document.getElementById(drop_down_id1).innerHTML=drop_down;
							if(drop_down!=0 && drop_down<min_ticketid[ticketid]){
								document.getElementById(drop_down_id1).innerHTML=0;
								//alert("you need to select mininmum of "+min_ticketid[ticketid]+" for \""+ticketnameids[ticketid]+"\" ticket type");
								bootbox.confirm(props.sea_need_min_bootbox_lbl1+" "+min_ticketid[ticketid]+" "+props.sea_need_min_bootbox_lbl2+"  \""+ticketnameids[ticketid]+"\" "+props.sea_need_min_bootbox_lbl3,function(result){});
							}
							
							}
						break;
					}
				}
		}
}


function getTicketIdforSelectedSeat(sid,allticketid){
	var selticid="";
	allticketid=""+allticketid;
	var ticid=allticketid.split(",");
	for(i=0;i<ticid.length;i++){
		var id=ticid[i];
		var temp=sel_ticket[id];
		if(temp!=undefined){
			for( var k = 0; k < temp.length; k++ )
				{
					if( temp[ k ] == sid )
					{
						selticid = id;
						break;
					}
				}
		}

	}
	return selticid;
}


function generate_Sectiondropdown(allsectionid,allsectionname){
var select="<select name='section' id='section' onchange='getsection()' >"
for(i=0;i<allsectionid.length;i++){
select=select+"<option value="+allsectionid[i]+">"+allsectionname[i]+"</option>";
}
select=select+"</select>";
return select;
}


function getsection(){
var sec_id=document.getElementById("section").value;
sectionid=document.getElementById("section").value;
generateSeating(seatingsectionresponsedata.allsections[sec_id]);
getsectionlevelselectedseats();
}


function getsectionlevelselectedseats(){
	var temp=new Array();
	temp=section_sel_seats[sectionid];
	if(temp!=undefined){
		for(i=0;i<temp.length;i++){
			//$("#"+temp[i]+" img").attr("style","border:1px solid black;");
			//$("#"+temp[i]+" img").attr("style","border:1px solid red;");
			document.getElementById(temp[i]).childNodes[0].style.border="1px solid red";
			sel_select[temp[i]]=true;
			
			
		}
	}
	
}




function getseatingtimer(){
	
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','ticketpoup_div');
	divpopup.className='ticketpoup_div';
	var cell=document.getElementById('container');
	var div=document.createElement("div");
	div.setAttribute('id','ticket_timer');
	div.className='ticket_timer';
	//div.className='initial_timer';
	div.setAttribute('style','display:block;');
	cell.appendChild(div);
	cell.appendChild(divpopup);
	$('#ticket_timer').html(props.sea_time_left_buy_lbl+'<br>');
	//window.scrollTo("0","25");
	var divcell=document.getElementById('ticket_timer');
	var span=document.createElement("div");
	span.setAttribute('id','time_left_tobuy');
	span.setAttribute('class','spannormal');
	divcell.appendChild(span);
	//mins_left = 14;
	//mins_left=0;
    s_left = 60;
	//s_left = 10;
	mins_remain=14;
	secs_remain=60;
	/*
	$('#ticket_timer').animate({
	 $("#ticket_timer").animate({"left": "+=50px"}, "slow");
	 
	 });

	$('#ticket_timer').animate({"left": "+=600px"}, 2000, function() {
		$('#ticket_timer').animate({"top": "+=450px"}, 2000, function() {
	  		//var div=$('ticket_timer');
		   // div.className='ticket_timer';
	    });
	});
	*/
	fifteenMinutes();
}



function fifteenMinutes(){
   s_left--;
if(s_left<0) {
   s_left="59";
   mins_left--;
}

if(mins_left==-1) {
	clearTimeout(reg_timeout);
	getconfirmationpopup();
}
else{
if(mins_left<10) {
   mins_remain='0'+mins_left;
}
else {
   mins_remain=mins_left;
}
if(s_left<10) {
   secs_remain='0'+s_left;
}
else {
   secs_remain=s_left;
}

   document.getElementById('time_left_tobuy').innerHTML='<center>'+mins_remain+':'+secs_remain+'</center>';
	
	
  reg_timeout=setTimeout('fifteenMinutes()',1000);
}
   }
   
   
 function getconfirmationpopup(){
	var divpopupcontent="<center><br/>"+props.sea_timeout_lbl+"!<br></br/><input type='button' class='btn btn-sm btn-primary' value='"+props.sea_tryagain_lbl+"' onclick='seatingtryagain()'></center>";

	
	bootbox.alert(divpopupcontent, function(result){
		seatingcancel();
	});
	$('div.bootbox-alert .modal-footer').remove();
	$('div.bootbox-alert .modal-dialog').css({"margin":"206px auto","width":"355px"});
	
	//$("").attr("tabindex", "-1");;;
	/*if($('registration')){
		$("#registration input").attr("tabindex", "-1");
		$("#registration a").attr("tabindex", "-1");
		$("#registration select").attr("tabindex", "-1");
	}
	if($('profile')){
		$("#profile input").attr("tabindex", "-1");
		$("#profile a").attr("tabindex", "-1");
	}
	if($('paymentsection')){
		$("#paymentsection input").attr("tabindex", "-1");
		$("#paymentsection a").attr("tabindex", "-1");
	}*/
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	
	//$('#ticketpoup_div').bgiframe();
	
	if(document.getElementById('load')){
		$('#load').hide();
	}
	window.scrollTo("0","150");
}


function seatingtryagain(){
	bootbox.hideAll();
	$('#ticketpoup_div').hide();
	document.getElementById('ticketpoup_div').innerHTML='';
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	//del_seat_temp(tranid);
	getTicketsPage();
} 
   
function seatingcancel(){
	$('#ticketpoup_div').hide();
	document.getElementById('ticketpoup_div').innerHTML='';
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	del_seat_temp_cancel();
	//var t=setTimeout('window.location.reload()',2000);
} 


function delete_locked_temp(tid){
	/*new Ajax.Request('/embedded_reg/seating/delete_temp_locked_tickets.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tid},
	onComplete:hidetimer
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eventid,tid:tranid},
		url:'/embedded_reg/seating/delete_temp_locked_tickets.jsp?timestamp='+(new Date()).getTime(),
		success:function(){
			hidetimer();
		}
	});
	
}
function hidetimer(){
if(document.getElementById('paymentsection')){
		$('#paymentsection').hide();
		document.getElementById('paymentsection').innerHTML='';
	}
	if(document.getElementById('ticket_div')){
		$('#ticket_div').hide();
	}
	if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}
}

function del_seat_temp(tid){
	
	/*new Ajax.Request('/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tid}
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eventid,tid:tranid},
		url:'/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(),
		success:function(){
			window.location.reload();
		}
	});
	
}

function del_seat_temp_cancel(){
	
	/*new Ajax.Request('/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tranid},
	onComplete:window.location.reload()
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eventid,tid:tranid},
		url:'/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(),
		success:function(){
			window.location.reload();
		}
	});
	
	
}

function hidetimerpopup(){
	if(document.getElementById('ticketpoup_div')){
		var div=document.getElementById('ticketpoup_div');
		div.className='ticketpoup_div1';
	}
}

function displaydivpopuptimeup(){
	if(document.getElementById('ticketpoup_div')){
		if(mins_left==-1){
			var div=document.getElementById('ticketpoup_div');
			div.className='ticketpoup_div';
			if(document.getElementById("backgroundPopup")){
				document.getElementById("backgroundPopup").style.display='block';
			}
		}
	}
}


function resetobjectdata(){
var a=[];
sel_seatcodes=[];

for(i=0;i<=allsectionid.length;i++){
	if(section_sel_seats[allsectionid[i]]!=undefined){
		section_sel_seats[allsectionid[i]]=a;
	}
}
//alert("tkts::"+JSON.stringify(ticketsArray));
for(i=0;i<ticketsArray.length;i++){
	var drop_down_id="qty_"+ticketsArray[i];
	if(sel_ticket[ticketsArray[i]]!=undefined){
		sel_ticket[ticketsArray[i]]=a;
	}
	if(ticket_count[drop_down_id]!=undefined){
		ticket_count[drop_down_id]=a;
	}
}
	
}

function getvenuelayout(url){
//YAHOO.ebee.popup.dialog.setHeader("Layout");
var cnt="<iframe src='"+url+"' width='"+"550"+"' height='"+"400"+"' frameborder='0'></iframe>";
	/*YAHOO.ebee.popup.dialog.setBody(cnt);
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", "");*/
	//adjustYUIpopup();
$('.modal-title').html(props.sea_iframe_layout_lbl);
$('iframe#popup').attr("src",url);
$('iframe#popup').css("height","450px"); 
$('#myModal').modal('show');


//showYUIinIframe("Venue Layout",url,400,200);
/*
if($('layoutpopup')){
	//var div=document.getElementById('layoutpopup');
	//div.setAttribute('width','550px');
	//div.setAttribute('height','400px');
}
else{
var cell=$('seatcell');
	var div=document.createElement("div");
	div.setAttribute('id','layoutpopup');
	div.className='layoutwidget';
	cell.appendChild(div);
	$( "#layoutpopup" ).draggable();
	
}
var layout="<a href='javascript:closepopuplayout();'><img src='/main/images/close.png' class='imgclose'></a><iframe width='100%' height='100%' src='"+url+"' resizeIFrame=true frameborder='0' allowfullscreen></iframe>";
document.getElementById('layoutpopup').innerHTML=layout;
document.getElementById('layoutpopup').style.display='block';
document.getElementById('layoutpopup').style.top='35%';
	document.getElementById('layoutpopup').style.left='15%';
	//if(document.getElementById("backgroundPopup"))
	//	document.getElementById("backgroundPopup").style.display='block';
	window.scrollTo("0","150");
	$( "#layoutpopup" ).resizable();
	

*/
}

function closepopuplayout(){
	//if(document.getElementById("backgroundPopup")){
	//	document.getElementById("backgroundPopup").style.display='none';
	//}
	if(document.getElementById('layoutpopup')){
		document.getElementById('layoutpopup').style.display='none';
	}
}


function getavailableticketid(ticketid){
for (var i = 0; i<notavailableticketids.length; i++) {
		var arrlen = ticketid.length;
		for (var j = 0; j<arrlen; j++) {
			if (notavailableticketids[i] == ticketid[j]) {
				ticketid = ticketid.slice(0, j).concat(ticketid.slice(j+1, arrlen));
			}
		}
	}
return ticketid;
}



function showmemberseats(){
for(i=0;i<memberseatticketids.length;i++){

	var cur_id=memberseatticketids[i];
	var dropdown_id='qty_'+cur_id;
	var dropdown_id1='show_'+cur_id;
	var td_id="td_ticketid_"+cur_id;
	$('#'+td_id).html(" ");
	$('#'+td_id).html("<center title='"+props.sea_seats_below_lbl+"'><span id='"+dropdown_id1+"' style='font-size:14px;margin-left:40px' >0</span></center><input value='0' type='hidden' name='"+dropdown_id+"' id='"+dropdown_id+"'>"+props.aa_sel_seats_lbl);
	
}
enablememberseats();
}


function enablememberseats(){
	for (var i = 0; i<memberseatticketids.length; i++) {
		var arrlen = notavailableticketids.length;
		for (var j = 0; j<arrlen; j++) {
			if (memberseatticketids[i] == notavailableticketids[j]) {
				notavailableticketids = notavailableticketids.slice(0, j).concat(notavailableticketids.slice(j+1, arrlen));
			}
		}
	}
	getsection();

}



function addselectiontotooltip(seatid,ticketid){
var tooltip=seatinfo[seatid]+"";
var newtip=tooltip.split("<u>");
tooltip=newtip[0]+"<u><b>"+props.sea_current_sel_lbl+":</b></u><br><ul><li>"+ticketnameids[ticketid]+"</li></ul><br><u>"+newtip[1];
seatinfo[seatid]=tooltip;
$('#'+seatid).tooltipster('destroy');
	$('#'+seatid).tooltipster({
		theme: 'my-custom-theme',
        fixedWidth:'300px',
        content: $('<span>'+tooltip+' </span>')
    });

}


function restoreoldtooltip(seatid){
var tooltip=seatinfo[seatid]+"";
var newtip=tooltip.split("<u>");
tooltip=newtip[0]+"<u>"+newtip[2];
seatinfo[seatid]=tooltip;
$('#'+seatid).tooltipster('destroy');
	$('#'+seatid).tooltipster({
		theme: 'my-custom-theme',
        fixedWidth:'300px',
        content: $('<span>'+tooltip+' </span>')
    });
}


function clearallselections(){
	for(i=0;i<ticketsArray.length;i++){
		var ticketd=ticketsArray[i];
		if(document.getElementById("qty_"+ticketd)){
			document.getElementById("qty_"+ticketd).value=0;
			if(document.getElementById("show_"+ticketd))
				document.getElementById("show_"+ticketd).innerHTML=0;
		}
	}
    if(document.getElemenetById('seatingsection')){
		getseatingsection();
	}
}

function getticketseatsdisplay(){
	//alert("hiiii");
for(i=0;i<ticket_ids_seats.length;i++){
	var tktID=ticket_ids_seats[i];
	
	if(document.getElementById('qty_'+tktID)){
	if(ticketseatcolor[tktID]!=undefined){
		var seatingimg='seatingimg_'+tktID;
		var cell=document.getElementById(tktID);
		var div=document.createElement("div");
		div.setAttribute('id',seatingimg);
		div.className='widgetcontainer';
		cell.appendChild(div);
		
		var allimgs="";
		var groupid=$('#ticketGroup_'+tktID).val();
		if(ticket_groupnames[groupid]!=undefined){
			allimgs="<table><tr><td style='width: 26px;' rowspan='3'></td><td>";
		}
		var ticketcolor=ticketseatcolor[tktID];
		for(j=0;j<ticketcolor.length;j++){
			allimgs=allimgs+"<img src='/main/images/seatingimages/"+ticketcolor[j]+".png' style='padding:5px; border:0px;'> ";
				
		}
		if(ticket_groupnames[groupid]!=undefined){
			allimgs=allimgs+"</td></tr></table>";
		}
		
		if(document.getElementById(seatingimg))
		document.getElementById(seatingimg).innerHTML=allimgs;
	}

	}
	}
	
}

