var eid='';
var timestamp=new Date();
var color="";
var oldcolor="";
var selected=new Object();
var allids=new Object();
var seatcolors="";
var colorchoice="";
var venueid="";
var section_id="";
//YAHOO.namespace("ebee.seating");
function getSeating(){
	color="";
	oldcolor="";
	selected=new Object();
	colorchoice="";
	var sectionid=document.getElementById("section").value;
	var eventdate=document.getElementById("eventdate").value;
	document.getElementById("loading").style.display="block";
	document.getElementById("seatsdiv").style.display="none";
	eid=document.getElementById("eid").value;
	$.get("Seating!getSectionDetails?eid="+eid+"&sectionid="+sectionid+"&eventdate="+eventdate+"&isrecurring="+isrecurring+"&timestamp="+(new Date()).getTime(),function(result){
				if(isValidActionMessage(result))
				{
				generateSeating(result);
				}
	});}
	
function addVenue(){
	eid=document.getElementById("eid").value;
	
	var venueid=document.getElementById("venue").value;
	window.location.href="../eventmanage/Seating!addVenue?eid="+eid+"&venueid="+venueid;
}

function generateSeating(data){
	var clicked=new Object();
	var idarr=[];
	var nsandso=new Object();
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
	for(var i=1;i<=json.noofrows;i++){
	var row=document.createElement("tr");
	for(var j=1;j<=json.noofcols;j++){
		var s="";
		var sid="s_"+i+"_"+j;
		
		//var type=finalseats[sid].type;
		//var scode=finalseats[sid].seatcode;
		//var blocked=finalseats[sid].blocked;
		var type="noseat";
		var scode="";
		var seat_ind="";
		var blocked="";
		if(finalseats[sid]){
		  type=finalseats[sid].type;
		  scode=finalseats[sid].seatcode;
		  seat_ind=finalseats[sid].seat_ind;
		  blocked=finalseats[sid].blocked;
		}
		if(scode=="EMPTY") scode="";
		var col=document.createElement("td");
		var imgsrc="";
		col.className="lightgray_blank";
		s=s+"Seat Number: "+scode;
		var seatposition="<br>Row: "+i+" "+"Column: "+j;
		if(type){
			if(type.indexOf("sold")>-1){
		imgsrc="../images/seatingimages/"+type+".png";
		col.className=type;
		
			}
			else if(type=="noseat"){
				col.className=type;
			}
			else{
				if(blocked){
					imgsrc="../images/seatingimages/lightgray_exclaimation.png";
					col.className="lightgray_exclaimation";
				}else if(type=="lightgray_blank"){
					
				imgsrc="../images/seatingimages/"+type+".png";
				col.className=type;
				}
				else{
					imgsrc="../images/seatingimages/"+type+".png";
					col.className=type;
				}
			}
		if(type.indexOf("sold")>-1){
				s=s+"<br><b>Sold Out</b>";
				nsandso[finalseats[sid].seat_ind]="yes";
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
		col.appendChild(img);
		col.setAttribute('align','center');
		}
		col.setAttribute('id',seat_ind);
		allids[seat_ind]=type;
		seatinfo[seat_ind]=s;
		idarr[inc]=col;
		inc++;
		row.appendChild(col);
	}
	tbody.appendChild(row);
	}
	table.appendChild(tbody);
	cell.appendChild(table);
	
	
	
	$("#seatstab td").click(function() 
	{
		var mytdclass=$(this).attr("class");
		var seatid=$(this).attr("id");
		//alert(tdclass);
		//alert(mytdclass);
		var tdclass=$(this).attr("class").split(' ')[0];
		var id=$(this).attr("id");
			if(tdclass!="lightgray_sold" && tdclass!="lightgray_question" && tdclass!="lightgray_blank")
			{	
				//alert(clicked[id]+"id checked value");
				if(clicked[id]==undefined) clicked[id]=false;
				if(clicked[id]==false)
				{
					if(tdclass=="lightgray_exclaimation")//written on 23-12-2015
					{
						//alert(allids[id]);
						var seatscolor="";
						for(var i = 0; i < colorjson.length; i++) 
						{
						    var obj = colorjson[i];
						    var key=Object.keys(obj);
						    if(key==seatid)
						    {
						     seatscolor=obj[key];
						    // alert(key+"............."+seatscolor);
						    }
						}
						/*$(this).attr("class",allids[id]);
						$(this).children("img").attr("src","../images/seatingimages/"+allids[id]+".png");*/
						$(this).attr("class",seatscolor);
						$(this).children("img").attr("src","../images/seatingimages/"+seatscolor+".png");
						selected[id]="unblock";
						//alert("done");
					}
					else{
						$(this).attr("class","lightgray_exclaimation");
						$(this).children("img").attr("src","../images/seatingimages/lightgray_exclaimation.png");
						selected[id]="block";
					}
					clicked[id]=true;
				}
				else{
					if(tdclass=="lightgray_exclaimation"){
						$(this).attr("class",allids[id]);
						$(this).children("img").attr("src","../images/seatingimages/"+allids[id]+".png");
					}
					else{
						$(this).attr("class","lightgray_exclaimation");
						$(this).children("img").attr("src","../images/seatingimages/lightgray_exclaimation.png");
					}
					clicked[id]=false;
					selected[id]=undefined;
				}
			}
	});
	
	
	
	 $.each(seatinfo,function(key,value){
   		$('#'+key).tooltipster({
              theme: 'my-custom-theme',
			    fixedWidth:'300px',
			        content: $('<span>'+value+'</span>')
			    });
   	});
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
			s=s+'{"sid":"'+id+'","action":"'+c+'"}';
			i++;
		}
	});
	jsonFormat=jsonFormat+s+']};';
	eid=document.getElementById("eid").value;
	section_id=document.getElementById("section").value;
	venueid=document.getElementById("venueid").value;
	var eventdate=document.getElementById("eventdate").value;
	$.post("Seating!saveManual"+"?timestamp="+(new Date()).getTime(),{eid:eid,venueid:venueid,sectionid:section_id,eventdate:eventdate,msg:jsonFormat},
		function(result){
			if(isValidActionMessage(result))
			getSeating();
		}
	);
}

