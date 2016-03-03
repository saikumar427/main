function showBoth(){	
	document.getElementById("inp").style.display = 'block';
	document.getElementById("outp").style.display = 'block';
}
function showInp(){	
	document.getElementById("inp").style.display = 'block';
	document.getElementById("outp").style.display = 'none';
	document.getElementById("outpnumber").value = "0";	
}
function automate(){	
	autoProcess();
}
function autoProcess(){	
	document.getElementById("dynaquery").action = "autoprocess.jsp";
	jsonAJAX.submit(document.getElementById("dynaquery"), {
	    onSuccess : function(obj) {
		var data=obj.responseText;
		var info="";
		var url='';		
		eval("myData="+data);
		if(myData.status=="error"){	
			info +="<table>";			
			for(var i=0;i<myData.errors.length;i++){			
				info += "<tr><td>"+myData.errors[i]+"</td></tr>";
			}
			info +="</table>";			
			document.getElementById("errors").innerHTML=info;
		}else{
			document.getElementById("errors").innerHTML = "";			
			if(myData.inphtml){
				document.getElementById("inparams").innerHTML = myData.inphtml;
			}
			if(myData.outphtml)
				document.getElementById("outparams").innerHTML = myData.outphtml;
			}
		},
	    onError : function(obj) { alert("Error: " + obj.status); }
	});
}
function dbProcess(){
	document.getElementById("dynaquery").action = "dbprocess.jsp";
	jsonAJAX.submit(document.getElementById("dynaquery"), {
	    onSuccess : function(obj) {
			var data=obj.responseText;
			var info="";
			var url='';			
			eval("myData="+data);
			if(myData.status=="error"){	
				info +="<table>";			
				for(var i=0;i<myData.errors.length;i++){			
					info += "<tr><td>"+myData.errors[i]+"</td></tr>";
				}
				info +="</table>";			
				document.getElementById("errors").innerHTML=info;
			}else{
				info +="<table>";
				if(!myData.statobjstatus){
					info += "<tr><td>"+myData.errormsg+"</td></tr></table>";					
					document.getElementById("errors").innerHTML=info;
				}else{	
					info += "<tr><td>Successfully executed.</td></tr></table>";
					document.getElementById("errors").innerHTML="";
					document.getElementById("success").innerHTML=info;
					setTimeout("hide()",5000);
				}
				
			}
		},
	    onError : function(obj) { alert("Error: " + obj.status); }
	});
}
function reload(){
	document.getElementById("reloadqueries").action = "reloadnamedquries.jsp";
	jsonAJAX.submit(document.getElementById("reloadqueries"), {
	    onSuccess : function(obj) {
			var data=obj.responseText;
			var info="";
			var url='';			
			eval("myData="+data);
			if(myData.status=="error"){	
				info +="<table>";			
				for(var i=0;i<myData.errors.length;i++){			
					info += "<tr><td>"+myData.errors[i]+"</td></tr>";
				}
				info +="</table>";			
				document.getElementById("errors").innerHTML=info;
			}else{								
				info += "<center>Successfully reloaded.</center>";
				document.getElementById("reloadstatus").innerHTML=info;
				setTimeout("hide()",5000);
			}
		},
	    onError : function(obj) { alert("Error: " + obj.status); }
	});
}
function hide(){
	document.getElementById("success").innerHTML="";
	document.getElementById("reloadstatus").innerHTML="";
}