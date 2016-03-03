
function settooltip(idarr){
var seatsTooltip = new YAHOO.widget.Tooltip("seatsTooltip", { 
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
	); 
}

function addticketidinall(ticketid){
if(seating_ticketids.length>1){
}
else{
seating_ticketids[0]=ticketid;
}
}


function fillticketqty(id){
	
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
		//tic_dropdown.selectedIndex=tic_dropdown.selectedIndex+1;
		add_seats(ids,id);
	}
	else{
		if(count<min_qty){
			tic_dropdown=Number(tic_dropdown)+1;
			document.getElementById(cur_id).value=tic_dropdown;
			//alert("for \""+ticketnameids[id]+"\" ticket, you need to select minimum of "+min_qty+" seats");
			add_seats(ids,id);
		}
		else if(count>max_qty){
			alert(props.sea_max_qty_reached_msg+' "'+ticketnameids[id]+'" '+props.sea_max_qty_reached_msg2);
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
		var containsseat=false;
		for(arrlen=0;arrlen<a.length;arrlen++){
			if(a[arrlen]==seatid){
				containsseat=true;
				break;
			}
		}
		if(!containsseat)
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
	/*jQuery("#"+seatid+" img").css("border","0px none");
	jQuery("#"+seatid).css("border","0px none");*/
	
	if(eventid==seatingeid)
		jQuery('#'+seatid+' .checkmark').remove();
		else
		jQuery(this).css("border","0px none");
	
	
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
	
	var drop_down=document.getElementById(drop_down_id).value;
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
	//alert("ticidticid:::::::::::::::::::::"+ticid.length);
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

