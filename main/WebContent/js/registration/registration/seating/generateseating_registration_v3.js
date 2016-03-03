function generateSeating(data){
	var idarr=[];
	var inc=0;
	json=data;
	var sectionid=document.getElementById('section').value;
	//alert(sectionid);
	var showFlag=false;
	var header="headers_"+sectionid;
	var headerobj=json[header];
	var row_header=""+headerobj.rowheader;
	var col_header=""+headerobj.columnheader;
	var rowheader=row_header.split(",");
	var colheader=col_header.split(",");
	var finalseats=json.completeseats;
	ticketnameids=finalseats.ticketnameids;
	var cell=document.getElementById("seatcell");
	
	while(cell.hasChildNodes()){
		cell.removeChild(cell.lastChild);
	}
	
	var table=document.createElement("table");
	table.setAttribute('border','0');
	table.setAttribute('align','center');
	table.setAttribute('id','seatstab');
	if(json["background_image"]!="" && json["background_image"]!=undefined){
	var bgimage=new Image();
	bgimage.src=json["background_image"];
	table.style.cssText='background:url("'+bgimage.src+'") no-repeat  scroll 0 0 transparent; background-image:url("'+json["background_image"]+'") no-repeat  scroll 0 0 transparent;';
	}
	var tbody=document.createElement("tbody");
	for(var i=0;i<=json.noofrows;i++){
		var row=document.createElement("tr");
		row.setAttribute("id","row_tr_"+i);
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
				var type="noseat";
				var ticketname="NA";
				var sc_index_tic="0";
				var scode="";
				var ticketid=[];
				if(finalseats[sid]){
				type=finalseats[sid].type;
				ticketid=finalseats[sid].ticketids;
				showFlag=false;
				
				
				if(ticketid!=undefined && ticketid.length>0){
				for(var tl=0;tl<ticketid.length;tl++){
				
					if(tktsData[ticketid[tl]]!=undefined){
							showFlag=true;break;
							}
							}
				}

				scode=finalseats[sid].seatcode;
				sc_index_tic=sectionid+"_"+i+"_"+j//finalseats[sid].seat_ind;
				}
				
				if(ticketid==undefined)ticketid=[];
				else
					ticketid=getavailableticketid(ticketid);
				if(scode=="EMPTY") scode="";
				var imgsrc="";
				ttHtml=ttHtml+props.sea_tooltip_seatnum_lbl+": "+scode;
				if(type){
					imgsrc="/main/images/seatingimages/"+type+".png";
					col.className=type;
					if(ticketid==undefined||ticketid.length==0){
						if(type.indexOf("SO")>-1){
								ttHtml=ttHtml+" <br><b><font color='red'>"+props.sea_tooltip_soldout_lbl+"</font></b>";
								imgsrc="/main/images/seatingimages/lightgray_sold.png";
								soldOutTickets[sc_index_tic]="SOLD";
							}
						else if(type.indexOf("Hold")>-1){
							ttHtml=ttHtml+"<br><b><font color='black'>"+props.sea_currntly_on_hold_lbl+"</font></b>";
							imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
						}else if(type.indexOf("NA")>-1 ){
							imgsrc="/main/images/seatingimages/lightgray_blank.png";
							if($('notavailimage')){
							var naimage=$('notavailimage').value;
								if(naimage!=undefined && naimage!='')
							imgsrc="/main/images/seatingimages/"+naimage;
							}
							var ttmsg=props.sea_not_avail_lbl;
							if($('notavailmsg')){
							var namsg=$('notavailmsg').value;
								if(namsg!=undefined && namsg!='') ttmsg=namsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg+"</font></b>";
						}
						else{
						
							col.className='NA';
							imgsrc="/main/images/seatingimages/"+type+".png";
							if(!showFlag){
								imgsrc="/main/images/seatingimages/lightgray_blank.png";
								if($('notavailimage')){
								var naimage=$('notavailimage').value;
								if(naimage!=undefined && naimage!='')
									imgsrc="/main/images/seatingimages/"+naimage;
									}
							}
							var ttmsg1=props.sea_not_avail_lbl;
							if($('notavailmsg')){
							var namsg=$('notavailmsg').value;
								if(namsg!=undefined && namsg!='') ttmsg1=namsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg1+"</font></b>";
							
							}
						}
					else{
						if(type.indexOf("SO")>-1){
								ttHtml=ttHtml+" <br><b><font color='red'>"+props.sea_tooltip_soldout_lbl+"</font></b>";
								imgsrc="/main/images/seatingimages/lightgray_sold.png";
								soldOutTickets[sc_index_tic]="SOLD";
							}
						else if(type.indexOf("Hold")>-1){
							ttHtml=ttHtml+"<br><b><font color='black'>"+props.sea_currntly_on_hold_lbl+"</font></b>";
							imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
						}
						else if(type.indexOf("NA")>-1 ){
							imgsrc="/main/images/seatingimages/lightgray_blank.png";
							if($('notavailimage')){
							var naimage=$('notavailimage').value;
								if(naimage!=undefined && naimage!='')
							imgsrc="/main/images/seatingimages/"+naimage;
							}
							var ttmsg=props.sea_not_avail_lbl;
							if($('notavailmsg')){
							var namsg=$('notavailmsg').value;
								if(namsg!=undefined && namsg!='')
							ttmsg=namsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg+"</font></b>";
						}
						else if(type=="noseat"){
								
								ttHtml='';
								seatposition="";
								}
					}
				}
				else{
				col.className='unassign';
				imgsrc="/main/images/seatingimages/lightgray_blank.png";
				if($('unassign')){
					var unassign=$('unassign').value;
					if(unassign!=undefined && unassign!='')
					imgsrc="/main/images/seatingimages/"+unassign;
				}
				var ttmsg1=props.sea_not_avail_lbl;
							if($('unassignmsg')){
							var unamsg=$('unassignmsg').value;
								if(unamsg!=undefined && unamsg!='')
							ttmsg1=unamsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg1+"</font></b>";
				}
				ttHtml=ttHtml+seatposition;
				if(type!="noseat"){
				var img=document.createElement("img");
				var imgwidth="17px";
				var imgheight="17px";
				if(json.seat_image_width!=undefined && json.seat_image_width!='')
					imgwidth=json.seat_image_width+"px"
				if(json.seat_image_height!=undefined && json.seat_image_height!='')
					imgheight=json.seat_image_height+"px"	
				img.setAttribute("src",imgsrc);
				img.style.cssText='height:'+imgheight+';width:'+imgwidth+';';
				
				
				
				
				
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
				if(ticketid!=undefined && ticketid.length>0 && type.indexOf("SO")==-1 && type.indexOf("Hold")==-1){
					ttHtml=ttHtml+'<br><br><u><b>'+props.sea_avail_for_tkts_lbl+':</b></u>';
					ttHtml=ttHtml+"<ul>";
					for(k=0;k<ticketid.length;k++){
						var tktID=ticketid[k];
						var groupname='';
						if($('ticketGroup_'+tktID)){
						var groupid=$('ticketGroup_'+tktID).value;
						
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
				
					idarr[inc]=col;
					inc++;
				}
				else{
					
				}				
							
				col.setAttribute('id',sc_index_tic);//modified on May 24, 2012
								
				seatinfo[sc_index_tic]=ttHtml;
					
				
			}
			row.appendChild(col);
		}
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	cell.appendChild(table);
	/*
	for(k=1;k<=json.noofrows;k++){
		if(k%2==0){
			var row="#row_td_"+k;
			alert(row);
			jQuery(row).animate({"left": "+=8px"}, 1000, function() {
			alert("s"+row);
	  		//var div=$('ticket_timer');
		   // div.className='ticket_timer';
	    });
			
			// jQuery(row).animate({"left": "+=8px"}, "fast");
		}
	}
	*/
	cell=$('container');
	var div=document.createElement("div");
	div.setAttribute('id','divpopup');
	div.className='ticketwidget';
	div.setAttribute('style','display:none;');
	cell.appendChild(div);
	settooltip(idarr);
	setVenueLayoutExpandLink(json.noofrows,json.noofcols,json.lheight,json.lwidth);
if($('orderbutton'))$('orderbutton').disabled=false;

jQuery("#seatstab img").click(function(){
ids=jQuery(this).parent('td').attr('id');
if(sel_select[ids]==undefined){
	var a=new Array();
		a.push(false);
		sel_select[ids]=a;
		
}


	var allticids=seatcode_ticid[ids];
	var	alltkts=new Array();
	
	
	if(sel_select[ids]==true){
		ticid=getTicketIdforSelectedSeat(ids,allticids);
		allticids=""+allticids;
		alltkts=allticids.split(",");
		if(eventid==seatingeid && alltkts.length>1 && GroupSeats.indexOf(ids)>-1 && seattickets.indexOf(ticid)>-1 && !(isSoldOutTickektsinGroup(ids))){
			deselectGroupTickets(ids,ticid,allticids);
		}else{
		sel_select[ids]=false;
		if(eventid==seatingeid)
		jQuery('#'+ids+' .checkmark').remove();
		else
		jQuery(this).css("border","0px none");	
		jQuery(this).parent("td").attr("style","");
		remove_seats(ids,ticid);
		restoreoldtooltip(ids);
		}
	}
	else{
		
		sel_select[ids]=true;
		var chkclass=jQuery(this).parent("td").attr('class');
		if(chkclass!=""&&chkclass!="noseat"&&chkclass!="NA"&&chkclass!="unassign"&&chkclass!="white_NA"&&chkclass.indexOf('SO')==-1&&chkclass.indexOf('Hold')==-1){
			ids=jQuery(this).parent('td').attr('id');
			var sel_ticid=seatcode_ticid[ids];
			//var sel_ticid=jQuery(this).attr('ticketid');
			sel_ticid=""+sel_ticid;
			var selticketid=new Array();
			var	alltkts=new Array();
			selticketid=sel_ticid.split(",");
			alltkts.push.apply(alltkts, selticketid);
			
			if(selticketid.length==1){
				
				if(eventid==seatingeid)
				jQuery('#'+ids).append('<span class="checkmark"><div class="checkmark_stem"></div><div class="checkmark_kick"></div></span>');
				else
				jQuery(this).css("border","1px solid red");	
				
				var cur_title=seatcode_ticid[ids];
				//var cur_title=jQuery(this).attr('ticketid');
				var cur_id=jQuery(this).attr('id');
				sel_seatcodes.push(cur_id);
				fillticketqty(cur_title);
			}
			else if(selticketid.length > 1){
				ids=jQuery(this).parent('td').attr('id');
				var radio="<p style='text-align:left; margin-top:-15px;'>"+props.sea_select_one_lbl+"</p><a href='javascript:closeit(\"N\");'><img src='/home/images/images/close.png' class='imgclose'></a>";
				radio=radio+"<table>";
				for(i=0;i<selticketid.length;i++){
					var tktID=selticketid[i];
					var groupname='';
					var groupid=$('ticketGroup_'+tktID).value;
					if(ticket_groupnames[groupid]!=undefined){
						groupname='&nbsp;('+ticket_groupnames[groupid]+')';
					}	
					radio=radio+"<tr><td valign='top' align='left'><input type='radio' name='selticketid' id='selticketid' value='"+selticketid[i]+"'></td><td valign='top' align='left'> "+ticketnameids[tktID]+groupname+"</td></tr>";
				}
				radio=radio+"</table>";
				changeBg(radio+button);
			}
			else{
				alert(props.sea_not_avail_msg);
			}
		}
		}
	if(eventid==seatingeid && sel_select[ids]==true && loop==0 && GroupSeats.indexOf(ids)>-1 && alltkts!=undefined && alltkts.length==1){
		selectAll(ids);
		deselloop=0;
	}
	if(eventid==seatingeid && sel_select[ids]==false && deselloop==0 && GroupSeats.indexOf(ids)>-1 && alltkts!=undefined && alltkts.length==1){
		deselloop++;
		selectAll(ids);
		loop=0;
	}
});

jQuery('.checkmark').live("click",function(){
	var seatindex=jQuery(this).parent().attr('id');
	jQuery('#'+seatindex+' img').trigger('click');
});


}

var loop=0;
var deselloop=0;

function selectAll(id){
//	alert(seatIndex);
	if(GroupSeats.indexOf(id)>-1){
		for(var i=0; i<seattables.length;i++){
			if(window[seattables[i]].indexOf(id)>-1){
				  loop++;
				  for(var ite=0;ite<window[seattables[i]].length;ite++){
						if(window[seattables[i]][ite]!=id)
							callMethodBY(window[seattables[i]][ite]);
						if(ite==window[seattables[i]].length-1){
							loop=0;deselloop=0;
						}
				}
				  break;
			}
		}
		/*if(table1.indexOf(id)>-1){
			loop++;
			for(var ite=0;ite<table1.length;ite++){
					if(table1[ite]!=id)
						callMethodBY(table1[ite]);
					if(ite==table1.length-1){
						loop=0;deselloop=0;
					}
			}
		}*/
	}
}


function callMethodBY(ids){
	jQuery('#'+ids+' img').trigger('click');
}


function deselectGroupTickets(seatIndex,ticketId,allticketIDs){
	var tablename=getGroupName(seatIndex);
	var tabArray=window[tablename];
	if(tablename!="doesnotexits"){
		for(var ite=0;ite<tabArray.length;ite++){
			ticid=getTicketIdforSelectedSeat(tabArray[ite],allticketIDs);
			if(seattickets.indexOf(ticid)>-1){
			sel_select[tabArray[ite]]=false;
			if(eventid==seatingeid)
			jQuery('#'+tabArray[ite]+' .checkmark').remove();
			else
			jQuery('#'+tabArray[ite]).css("border","0px none");	
			jQuery('#'+tabArray[ite]).parent("td").attr("style","");
			remove_seats(tabArray[ite],ticketId);
			restoreoldtooltip(tabArray[ite]);
			}
		}
		
	}
}


function changeBg(radio){
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	//jQuery('#divpopup').bgiframe();
	document.getElementById('divpopup').innerHTML=radio;
	var pos=jQuery(document).scrollTop()+130;
	document.getElementById('divpopup').style.top=pos+'px';	
	document.getElementById('divpopup').style.display="block";
	//document.getElementById('divpopup').style.top='50%';
	document.getElementById('divpopup').style.left='30%';
	//scrollTo(0,150);
	//jQuery( "#divpopup" ).draggable();
	
}


function closeit(type){
	if(type=='Y' && document.getElementById('accept').value==props.sea_sel_tkt_btn_lbl){
		var selticktemp=document.getElementsByName('selticketid');
		
		for(var i = 0; i < selticktemp.length; i++){
			if(selticktemp[i].checked){
				if(eventid==seatingeid && seattickets.indexOf(selticktemp[i].value)>-1 && GroupSeats.indexOf(ids)>-1 && !(isSoldOutTickektsinGroup(ids))){
					selectGroupTicketsPopup(ids,selticktemp[i].value);
					break;
				}else{
					if(eventid==seatingeid)
						jQuery('#'+ids).append('<span class="checkmark"><div class="checkmark_stem"></div><div class="checkmark_kick"></div></span>');
						else
						jQuery('#'+ids).css("border","1px solid red");	
					sel_select[ids]=true;
					sel_seatcodes.push(selticktemp[i].value);
					fillticketqty(selticktemp[i].value);
					break;
				}
			}
			else{
				sel_select[ids]=false;
			}
		}
	}
	else{
		sel_select[ids]=false;
	}
	$("divpopup").innerHTML='';
	$("divpopup").hide();
	if(document.getElementById("backgroundPopup")){
			document.getElementById("backgroundPopup").style.display='none';
	}
}



function selectGroupTicketsPopup(seatIndex,ticketId){
	var tablename=getGroupName(seatIndex);
	var tabArray=window[tablename];
	if(tablename!="doesnotexits"){
			for(var ite=0;ite<tabArray.length;ite++){
				//alert(tabArray.length+"::"+tabArray[ite]);
				isGroupTicketsSelectedLoose(seatIndex);
				ids=tabArray[ite];
				sel_select[tabArray[ite]]=true;
				if(eventid==seatingeid)
					jQuery('#'+tabArray[ite]).append('<span class="checkmark"><div class="checkmark_stem"></div><div class="checkmark_kick"></div></span>');
					else
					jQuery('#'+tabArray[ite]).css("border","1px solid red");	
				sel_seatcodes.push(ticketId);
				fillticketqty(ticketId);
			}
	}
}


function setVenueLayoutExpandLink(rows,columns,height1,width1){
	var expanded=false;
	var height=Number(rows)*17;
	var width=Number(columns)*17;
	if(height1==undefined || height1=='') 
		height1='300';
	if(width1==undefined || width1=='') 
		width1='600';
	if(height<=300){
		height=Number(height)+20;
		jQuery("#seatcell").attr("style","height:"+height+" px");
	}
	else{
		if(width<600){
			width=600;
		}
		var link="<a id='expandseatsection'><span style='vertical-align:top'>Click to zoom in&nbsp;</span><img src='/main/images/arrow-out-icon.png' border='0' width='17px' height='17px'></a>"
		//$("sectionexpand").innerHTML=link;
		 $("sectionexpand").innerHTML='';
		jQuery("#expandseatsection img").attr("src","/main/images/arrow-out-icon.png");	
		document.getElementById("seatcell").style.height=height1+'px';
				document.getElementById("seatcell").style.width=width1+'px';
		document.getElementById("seatcell").style.position='';
		$('orderbutton').style.position='';
		jQuery("#expandseatsection").click(function(){
			if(!expanded){
				jQuery("#expandseatsection img").attr("src","/main/images/arrow-in-icon.png");
				jQuery("#expandseatsection span").text('Click to zoom out ');
				expanded=true;
				document.getElementById("seatcell").style.height="100%";
				document.getElementById("seatcell").style.width="100%";
				document.getElementById("seatcell").style.position='relative';
				$('orderbutton').style.position='absolute';
			}
			else{
				jQuery("#expandseatsection span").text('Click to zoom in ');
				jQuery("#expandseatsection img").attr("src","/main/images/arrow-out-icon.png");	
				expanded=false;
				document.getElementById("seatcell").style.height=height1+'px';
				document.getElementById("seatcell").style.width=width1+'px';
				document.getElementById("seatcell").style.position='';
				$('orderbutton').style.position='';
			}
		}).attr("style","cursor:pointer");
	}
}


function isGroupTicketsSelectedLoose(seatIndex){
	var tablename=getGroupName(seatIndex);
	var tabArray=window[tablename];
	if(tablename!="doesnotexits"){
		for(var i=0;i<tabArray.length;i++){
			var allticids=seatcode_ticid[tabArray[i]];
			ticid=getTicketIdforSelectedSeat(tabArray[i],allticids);
			if(ticid!='' && !(seattickets.indexOf(ticid)>-1)){
				remove_seats(tabArray[i],ticid);
				restoreoldtooltip(tabArray[i]);
			}
		}
	}
}

function isSoldOutTickektsinGroup(seatIndex){
	var tablename=getGroupName(seatIndex);
	var tabArray=window[tablename];
	if(tablename!="doesnotexits"){
		for(var i=0;i<tabArray.length;i++){
			if(soldOutTickets[tabArray[i]]=="SOLD")
				return true;
		}
	}
	return false;
}


function getGroupName(seatIndex){
	for(var i=0; i<seattables.length;i++){
		if(window[seattables[i]].indexOf(seatIndex)>-1){
			return seattables[i];
		}
	}
   return "doesnotexits";
}


