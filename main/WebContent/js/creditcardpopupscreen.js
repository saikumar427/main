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
  	
  	
  /*	$.ajax({
        url: keyurl+'?timestamp='+(new Date()).getTime(),
        type: 'post',
         async: false,
         data:{callbackurl:this.callbackurl,purpose:this.purpose,paymode:this.paymode,message:this.message,amount:this.amount,currency:this.currency,merchantid:this.merchantid,refId:this.refid,title:this.title,softdis:this.softdis,lang:this.lang,vendor:this.vendor,Internalref:this.Internalref,month:this.month},
         success:this.showkey,
         error:this.failureKey 
         
         });*/
        
        
       
        
 /*	 $.ajax({
	      url: keyurl+'?timestamp='+(new Date()).getTime(),
	      type: 'post',
	       async: false,
	       data:{callbackurl:callbackurl,purpose:purpose,paymode:paymode,message:message,amount:amount,currency:currency,merchantid:merchantid,refId:refid,title:this.title,softdis:softdis,lang:lang,vendor:vendor,Internalref:Internalref,month:month},
	       success:function(response){
	               showkey(response);
	               },
	               
	       error:function(){
	                       failureKey();
	               }
	       
	       });*/
        
        
        
        
        
	/*new Ajax.Request(keyurl+'?timestamp='+(new Date()).getTime(), {
	method: 'post',
	asynchronous:false,
    parameters:{callbackurl:this.callbackurl,purpose:this.purpose,paymode:this.paymode,message:this.message,amount:this.amount,currency:this.currency,merchantid:this.merchantid,refId:this.refid,title:this.title,softdis:this.softdis,lang:this.lang,vendor:this.vendor,Internalref:this.Internalref,month:this.month},
    onSuccess:this.showkey,
	onFailure:this.failureKey
	});*/	
	$.ajax({
		
		url : keyurl+'?timestamp='+(new Date()).getTime(),
		type : 'post',		
		async:false,
		data : {callbackurl:this.callbackurl,purpose:this.purpose,paymode:this.paymode,message:this.message,amount:this.amount,currency:this.currency,merchantid:this.merchantid,refId:this.refid,title:this.title,softdis:this.softdis,lang:this.lang,vendor:this.vendor,Internalref:this.Internalref,month:this.month},
		success: this.showkey,
		error : this.failureKey
	});
	
	return lkey;
	
}	
this.showkey=function(response)
	{	
	 //var data=response.responseText;
	//alert(response);
      var objdata=eval('('+response+')');  
  	
      
      lkey=objdata.paytoken;
      ssl=objdata.sslserv;  
      //ssl='http://localhost';
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
		   ssl='http://www.eventbee.com';
	       url=ssl+url;		    
	       popcode(url,Paykey,div,header);
				
			function popcode(url,Paykey,divat,header)
			{ 
				parent.hidePopup();
				ss=url+"?paytoken="+Paykey+"&month="+mnth+"&t="+(new Date()).getTime();
				var url=ss;	
				$('#myModal .modal-dialog').removeClass('modal-lg');
				$('#myModal .modal-title').html(header);
	           /*	$('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3 style="padding-left: 15px;" class="section-main-header">'+header+'</h3>');*/
	           	$('#myModal .modal-footer').removeClass('modal-footer');		           	
	            $('iframe#popup').attr("src",url);				
		        $('#myModal').modal('show'); 
		        $('iframe#popup').css('height','600px !important;');
			    $('#myModal').on('hide.bs.modal', function () {
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="600px" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
			        
			    });
    
			   // onsucessload();
	
				
				
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
 //window.scrollTo("0","0");
			   
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
      $('#myModal').modal('hide');     
        $('iframe#popup').attr("src",'');			        
	    $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');   
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
	 
	 		/*	window.resizeIframe = function() {
				var obj=document.getElementById('popup');
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
            }*/
	 
	 	
	