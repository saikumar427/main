function generateSeating(data){
	var idarr=[];
	var inc=0;
	json=data;
	var sectionid=document.getElementById('section').value;
	//alert(sectionid);
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
				var type="noseat";
				var ticketname="NA";
				var sc_index_tic="0";
				var scode="";
				var ticketid=[];
				if(finalseats[sid]){
				type=finalseats[sid].type;
				ticketid=finalseats[sid].ticketids;
				scode=finalseats[sid].seatcode;
				sc_index_tic=sectionid+"_"+i+"_"+j//finalseats[sid].seat_ind;
				}
				
				if(ticketid==undefined)ticketid=[];
				else
					ticketid=getavailableticketid(ticketid);
				if(scode=="EMPTY") scode="";
				var imgsrc="";
				ttHtml=ttHtml+"Seat Number: "+scode;
				if(type){
					imgsrc="/main/images/seatingimages/"+type+".png";
					col.className=type;
					if(ticketid==undefined||ticketid.length==0){
						if(type.indexOf("SO")>-1){
								ttHtml=ttHtml+" <br><b><font color='red'>Sold Out</font></b>";
								imgsrc="/main/images/seatingimages/lightgray_sold.png";
							}
						else if(type.indexOf("Hold")>-1){
							ttHtml=ttHtml+"<br><b><font color='black'>This seat is currently on hold</font></b>";
							imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
						}else if(type.indexOf("NA")>-1 ){
							imgsrc="/main/images/seatingimages/lightgray_blank.png";
							if($('notavailimage')){
							var naimage=$('notavailimage').value;
								if(naimage!=undefined && naimage!='')
							imgsrc="/main/images/seatingimages/"+naimage;
							}
							var ttmsg="Not Available";
							if($('notavailmsg')){
							var namsg=$('notavailmsg').value;
								if(namsg!=undefined && namsg!='') ttmsg=namsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg+"</font></b>";
						}
						else{
						
							col.className='unassign';
							imgsrc="/main/images/seatingimages/"+type+".png";
							var ttmsg1="Not Available";
							if($('unassignmsg')){
							var unamsg=$('unassignmsg').value;
								if(unamsg!=undefined && unamsg!='')
							ttmsg1=unamsg;
							}
							ttHtml=ttHtml+"<br><b><font color='red'>"+ttmsg1+"</font></b>";
							
							}
						}
					else{
						if(type.indexOf("SO")>-1){
								ttHtml=ttHtml+" <br><b><font color='red'>Sold Out</font></b>";
								imgsrc="/main/images/seatingimages/lightgray_sold.png";
							}
						else if(type.indexOf("Hold")>-1){
							ttHtml=ttHtml+"<br><b><font color='black'>This seat is currently on hold</font></b>";
							imgsrc="/main/images/seatingimages/lightgray_exclaimation.png";
						}
						else if(type.indexOf("NA")>-1 ){
							imgsrc="/main/images/seatingimages/lightgray_blank.png";
							if($('notavailimage')){
							var naimage=$('notavailimage').value;
								if(naimage!=undefined && naimage!='')
							imgsrc="/main/images/seatingimages/"+naimage;
							}
							var ttmsg="Not Available";
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
				var ttmsg1="Not Available";
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
					ttHtml=ttHtml+'<br><br><u><b>Available for Tickets:</b></u>';
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
	if(sel_select[ids]==true){
		sel_select[ids]=false;
		jQuery(this).css("border","0px none");
		jQuery(this).parent("td").attr("style","");
		var allticids=seatcode_ticid[ids];
		//var allticids=jQuery(this).attr("ticketid");
		ticid=getTicketIdforSelectedSeat(ids,allticids);
		remove_seats(ids,ticid);
		restoreoldtooltip(ids);
	}
	else{
		sel_select[ids]=true;
		var chkclass=jQuery(this).parent("td").attr('class');
		if(chkclass!=""&&chkclass!="noseat"&&chkclass!="unassign"&&chkclass!="white_NA"&&chkclass.indexOf('SO')==-1&&chkclass.indexOf('Hold')==-1){
			ids=jQuery(this).parent('td').attr('id');
			var sel_ticid=seatcode_ticid[ids];
			//var sel_ticid=jQuery(this).attr('ticketid');
			sel_ticid=""+sel_ticid;
			var selticketid=new Array();
			selticketid=sel_ticid.split(",");
			if(selticketid.length==1){
				jQuery(this).css("border","1px solid red");
				
				var cur_title=seatcode_ticid[ids];
				//var cur_title=jQuery(this).attr('ticketid');
				var cur_id=jQuery(this).attr('id');
				sel_seatcodes.push(cur_id);
				
				fillticketqty(cur_title);
			}
			else if(selticketid.length > 1){
				ids=jQuery(this).parent('td').attr('id');
				var radio="<p style='text-align:left; margin-top:-15px;'>This seat is assigned to multiple Ticket Types, select one</p><a href='javascript:closeit(\"N\");'><img src='/home/images/images/close.png' class='imgclose'></a>";
				radio=radio+"<table>"
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
				alert("not available or no seats assigned to this seat");
			}
		}
		}
});
}

function changeBg(radio){
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	//jQuery('#divpopup').bgiframe();
	document.getElementById('divpopup').innerHTML=radio;
	document.getElementById('divpopup').style.display="block";
	document.getElementById('divpopup').style.top='50%';
	document.getElementById('divpopup').style.left='30%';
	scrollTo(0,150);
	//jQuery( "#divpopup" ).draggable();
	
}
function closeit(type){
	if(type=='Y' && document.getElementById('accept').value=="Select Ticket"){
		var selticktemp=document.getElementsByName('selticketid');
		for(var i = 0; i < selticktemp.length; i++){
			if(selticktemp[i].checked){
				//jQuery("#"+ids).attr("style","border:1px solid black;");
				jQuery("#"+ids+" img").attr("style","border:1px solid red;");
				sel_select[ids]=true;
				sel_seatcodes.push(selticktemp[i].value);
				fillticketqty(selticktemp[i].value);
				break;
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
		$("sectionexpand").innerHTML=link;
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