var presentcnt=1;
var posi=0;
var slval=550;
function slidel(){slide('l');}
function slider(){slide('r');}
var lsetfalsg=true;
var rsetfalsg=true;
function slide(pur)
{

var cou=document.getElementById('countid').value;
if(pur=='l')
{   	if(!rsetfalsg){rsetfalsg=true;
        document.getElementById('dleftbtn').style.visibility="visible";	  
	    }
		if(presentcnt==1){ 
		document.getElementById('dleftbtn').style.visibility = "visible";
		document.getElementById('dleftbtn').style.cursor="pointer";
		}

	if(presentcnt>=cou){return;}
	posi=posi-slval;
	jQuery("#slide").animate({marginLeft:posi},300); 
	presentcnt=presentcnt+1;
	 if(presentcnt==cou)
	{ lsetfalsg=false;
	 document.getElementById('drightbtn').style.visibility ="hidden";
	 document.getElementById('drightbtn').style.cursor="default";
 	}
	 
}
else
	{ 
		if(!lsetfalsg){lsetfalsg=true;
		document.getElementById('drightbtn').style.visibility ="visible";
		document.getElementById('drightbtn').style.cursor="pointer";
		}
		if(presentcnt==1){return;}
		 posi=posi+slval;
		 jQuery("#slide").animate({marginLeft:posi},300); 
         presentcnt=parseInt(presentcnt)-1;		 
		 if(presentcnt==1)
	    {  
		 rsetfalsg=false;
		 document.getElementById('dleftbtn').style.visibility ="hidden";
		 document.getElementById('dleftbtn').style.cursor="default";
        
		}
	      
		 
	} 
		 
}
/* function onMouser()
{
document.getElementById('drightbtn').style.opacity="1.0";
}
function outMouser()
{
document.getElementById('drightbtn').style.opacity="0.3";
}
function onMousel()
{
document.getElementById('dleftbtn').style.opacity="1.0";
}
function outMousel()
{
document.getElementById('dleftbtn').style.opacity="0.3";
} */
function eventTicketsLoad()
{
if(document.getElementById('reg_tic_load'))
document.getElementById('reg_tic_load').innerHTML="";
}
function volumeEventLoad(eid)
{

new Ajax.Request('/volume/volume_widget/volumescriptEvent.jsp?eventid='+eid+'&timestamp='+(new Date()).getTime(),{
method:'get',
onSuccess: function(t){
document.getElementById('vol_id').innerHTML=t.responseText;
},
onFailure:function(){alert("fail");}  
});
}



