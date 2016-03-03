function callPaykey()
{

	var callbackurl=""
	var purpose="";
	var paymode="";
	var message="";
	var amount="";
	var currency="";
	var merchantid="";
	var refid="";
	var key="";
	var lang="";
	var title="";
	var softdis="";
	var appenddiv="";
	var vendor="";
	var Internalref="";
	var month="";
	
	this.setMonth=function(mnth){
		this.month=mnth;
	}
	
	this.setInternalref= function(ierf){
	 this.Internalref=this.base64encode(ierf);
   }
	
	this.setVendor= function(ven){
	 this.vendor=this.base64encode(ven);
   }
	
   this.setLang= function(sl){
    this.lang=this.base64encode(sl);
   }
   this.setSoftdis= function(sd){
    this.softdis=this.base64encode(sd);
   }
   this.setTitle= function(tt){
    this.title=tt;
   }
   this.setAppendDiv= function(div){
    this.appenddiv=div;
   }
  
   
   
    this.setCallbackurl= function(curl){
    this.callbackurl=this.base64encode(curl);
   }
    this.setPurpose= function(pur){ this.purpose=this.base64encode(pur);}
    this.setPaymode= function(mode){this.paymode=this.base64encode(mode);}
    this.setMessage= function(msg){this.message=this.base64encode(msg);}
    this.setAmount= function(amt){this.amount=this.base64encode(amt);}
    this.setCurrency= function(cur){this.currency=this.base64encode(cur);}
    this.setMerchantid= function(mid){this.merchantid=this.base64encode(mid);}
    this.setRefid= function(rid){this.refid=this.base64encode(rid);}
    this.setKey=function(ky){this.key=ky;}
    this.setSfalg=function(flag){this.sfalg=flag;}
    this.getKey=function(){return this.key;}
   
     var k="";
     this.getPaykey=function() {
     showload(this.appenddiv);
     k=this.submitPaydet();
     closekeyload();
     return k;
     }
    
    
    var lkey="";
    var ssl="";
    var mnth="";
    this.submitPaydet=function(){
    sflag="false";
  	var keyurl='/main/eventbeepayments!getPaykey';
	new Ajax.Request(keyurl+'?timestamp='+(new Date()).getTime(), {
	method: 'post',
	asynchronous:false,
    parameters:{callbackurl:this.callbackurl,purpose:this.purpose,paymode:this.paymode,message:this.message,amount:this.amount,currency:this.currency,merchantid:this.merchantid,refId:this.refid,title:this.title,softdis:this.softdis,lang:this.lang,vendor:this.vendor,Internalref:this.Internalref,month:this.month},
    onSuccess:this.showkey,
	onFailure:this.failureKey
	});
	
	return lkey;
	
}	
this.showkey=function(response)
	{
	  var data=response.responseText;
      var objdata=eval('('+data+')');
      lkey=objdata.paytoken;
      ssl=objdata.sslserv;  
      mnth=objdata.month;
	}
this.failureKey=function()
	{  lkey="";
	    ssl="";
	}


/*****cccpopup*****/

this.ccpopup=function(Paykey)
{
		   var ssurl='/main/payments!gethtppsserveraddress';
		   var url="/main/payments!getccPaymentScreen";
		   var div=this.appenddiv;
		   var header=this.title; 
	       url=ssl+url;	
	       popcode(url,Paykey,div,header);
				
			function popcode(url,Paykey,divat,header)
			{ 
				showload(divat);
				
				if($('creditcardpopup_div')){}
				else{
						var divpopup=document.createElement("div");
						divpopup.setAttribute('id','creditcardpopup_div');
						divpopup.className='creditcardpopup_div';
						var cell=$(divat);
					   	cell.appendChild(divpopup);
				    }
							
				ss=url+"?paytoken="+Paykey+"&month="+mnth+"&t="+(new Date()).getTime();
				var message="<div class='header-style' id='title' style='display:none'><b>&nbsp;&nbsp;"+header+"</b><span style=''><span class='divimage'><img src='/main/images/close.png' onclick='closeEBpopup();' id='divimage'></span></div><div style='padding:0px;margin:0px;'><iframe  id='_EbeeIFrame' name='_EbeeIFrame'  style='display:none;' src="+ss+" '&viewType=iframe;resizeIFrame=true' frameborder='no' height='550' width='610' onload='closeloading()'></iframe></div>";
			    document.getElementById('creditcardpopup_div').innerHTML=message;
				document.getElementById('creditcardpopup_div').style.cssText="top:5%;left:20%;background:#F5F5F5;width:610px;float:left;padding: 0px;z-index: 100003;position:absolute;margin-top:0px;color:black;/*--CSS3 Box Shadows--*/-webkit-box-shadow: 0px 0px 20px #000;-moz-box-shadow: 0px 0px 20px #000;box-shadow: 0px 0px 20px #000;";
			    onsucessload();
			
			   }
					
}	

	
this.base64encode=function(s)
{
if(s==""){return s;}

var base64chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.split("");
  // the result/encoded string, the padding string, and the pad count
  var r = ""; 
  var p = ""; 
  s=""+s;
  var c = s.length % 3;
  // add a right zero pad to make this string a multiple of 3 characters
  if (c > 0) { 
    for (; c < 3; c++) { 
      p += '='; 
      s += "\0"; 
    } 
  }
  // increment over the length of the string, three characters at a time
  for (c = 0; c < s.length; c += 3) { 
    // we add newlines after every 76 output characters, according to the MIME specs
    if (c > 0 && (c / 3 * 4) % 76 == 0) {   
      r += "\r\n"; 
    }
    // these three 8-bit (ASCII) characters become one 24-bit number
    var n = (s.charCodeAt(c) << 16) + (s.charCodeAt(c+1) << 8) + s.charCodeAt(c+2); 
    // this 24-bit number gets separated into four 6-bit numbers
    n = [(n >>> 18) & 63, (n >>> 12) & 63, (n >>> 6) & 63, n & 63];
    // those four 6-bit numbers are used as indices into the base64 character list
    r += base64chars[n[0]] + base64chars[n[1]] + base64chars[n[2]] + base64chars[n[3]];   
  }   
   // add the actual padding string, after removing the zero pad    
  return r.substring(0, r.length - p.length) + p;
 }	
 
function showload(divat)
{
 window.scrollTo("0","0");
			   
			     if($('backgroundPopup')){}
			     else{
						var backgroundPopup=document.createElement("div");
						backgroundPopup.setAttribute('id','backgroundPopup');
						backgroundPopup.className='backgroundPopup';
						var cell=$(divat);
						cell.appendChild(backgroundPopup);
			         }
			         
			  if(document.getElementById("backgroundPopup"))
			    document.getElementById("backgroundPopup").style.display="block";  
			    
			    if($('load')){}
				else{
						var load=document.createElement("div");
						load.setAttribute('id','load');
						var cellload=$(divat);
					    cellload.appendChild(load);
			       }
			    
			    if(document.getElementById('load'))
			    {    
			    document.getElementById("load").style.display="block";
			    document.getElementById('load').innerHTML="<center>Processing...<br><img src='/main/images/loading.gif'></center>";
			    document.getElementById('load').style.cssText="top:70%;left:40%;background:#F5F5F5;float:left;padding: 0px;z-index: 100003;position:absolute;margin-top:-50px;"
			    }

} 
 
	
}

	function closeEBpopup(){

         if(document.getElementById("backgroundPopup")){
		 document.getElementById("backgroundPopup").style.display='none';
	     }
	     document.getElementById('creditcardpopup_div').style.display="none";
	     CloseAction();  
	}
	
	
	
	 function closeloading(){
	    if(document.getElementById("load")){
	    document.getElementById("load").style.display='none';
	    }
	    if(document.getElementById("title")){
	    document.getElementById("title").style.display='block';
	    }
	    if(document.getElementById("_EbeeIFrame")){
	     document.getElementById("_EbeeIFrame").style.display='block';
	     }
	
	 }
	function closekeyload()
	{    if(document.getElementById("load")){
	      document.getElementById("load").style.display='none';
	      }
	     if(document.getElementById("backgroundPopup")){
		  document.getElementById("backgroundPopup").style.display='none';
	      }
	 }
	