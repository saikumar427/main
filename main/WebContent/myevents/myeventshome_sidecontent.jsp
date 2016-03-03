<script type="text/javascript" src="//www.eventbee.com/home/js/customfonts/cufon-yui.js"></script>
	<script src="//www.eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
	<script type="text/javascript">
   try{Cufon.replace('.large_title,.main_title,.featuredeventsheader,.rectboxheader');
   }catch(err){alert(err);}
   </script>
<script type="text/javascript">
var openpopupclick=false;
function openPopUp(url,width,height,classname){
openpopupclick=true;
var dynatimestamp = new Date().getTime();
url=url+"?dyntimestamp="+dynatimestamp;
if($(classname)){
 }
else{
var cell=$('header');
var div=document.createElement("div");
div.setAttribute('id',classname);
div.className=classname;
cell.appendChild(div);
}
var layout="<a href='javascript:closepopuplayout();'><img src='/main/images/close.png' class='imgclose'></a><iframe width='"+width+"' height='"+height+"' src='"+url+"' id='"+dynatimestamp+"' frameborder='0' allowfullscreen></iframe>";
document.getElementById(classname).innerHTML=layout;
document.getElementById(classname).style.display='block';
if(classname=='layoutwidget'){
document.getElementById(classname).style.top='10%';
document.getElementById(classname).style.left='26%';
}else if(classname=='layoutwidget2'){
document.getElementById(classname).style.top='65%';
document.getElementById(classname).style.left='40%';
}
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display='block';



}

function closepopuplayout(){
openpopupclick=false;
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	if($('layoutwidget2')){
		$('layoutwidget2').style.display='none';
		$('layoutwidget2').innerHTML='';
	}
    if($('layoutwidget')){
		$('layoutwidget').style.display='none';
		$('layoutwidget').innerHTML='';
	}
}
</script>

<div id="backgroundPopup"></div>
<table width='100%' cellpadding="5px" cellspacing="5px" style="border: 1px solid #C0C0C0;">
<tr><td>

 
  <table border="0" cellpadding="0" cellspacing="0" class1="more" bordercolor="#FFCC33">
<tr>
    <td valign="top">
   <span class="rectboxheader"><h2>What's  New</h2></span> 
      <p>We are excited to announce the new Eventbee Ticketing App for Facebook Fan Page. Now your fans can purchase tickets directly from your 
      Fan Page without having to leave Facebook.</p>
     <p>Just click on the following link and follow the quick steps to install the app and start selling tickets right from your Fan Page: <a href="http://bit.ly/15IQGFa" target="_blank">http://bit.ly/15IQGFa</a>

     </p>
      <span class="rectboxheader"><h2>
        Tutorial Videos</h2></span>
      <p>
      <a href=" http://www.youtube.com/user/eventbee/videos" target="new">http://www.youtube.com<br/>/user/eventbee/videos</a></p>
      <span class="rectboxheader"><h2>
        Connect With Us </h2></span>
      <p>
<a href="http://www.facebook.com/eventbeeinc" target="new"><img src="http://www.eventbee.com/main/images/home/icon_facebook.png" border="0"/> Click here to fan us on Facebook</a>
<br/><br/>
<a href="http://www.twitter.com/eventbee" target="new"><img src="http://www.eventbee.com/main/images/home/icon_twitter.png"  border="0"/> Click here to follow us on Twitter</a>

  <tr>
    <td valign="top">
      If you have any questions, please <a href="#"  onClick="openPopUp('/main/user/supportemail.jsp','350','330','layoutwidget2')">email us</a>

    </td>
  </tr>

</table>

		</td></tr>

		</table>


			 
		    
