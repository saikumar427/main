var purpose=''; 
function deleteMailList(listid){
	bootbox.confirm('<h3>Confirm delete</h3>MailList will be deleted from this list for ever. This operation cannot be revert back.', function (result) {
        if (result){
        	var url='home!deleteMailList?listid='+listid;
           	$.ajax({
           		type: "POST",
                url:url,
                success:function(result){
                	window.location.reload();
                },failure:function(){
  		  		  alert("fail");
 		  	   }
           	});
        } 
    });
	
}

function closediv(){
	$('#myModal').modal('hide');
	}

function closepopup(){
	$('#myModal').modal('hide');
	window.location.reload();
}

function prepareSummary(jsondata,msgDivid){
	var duplicates=jsondata.duplicates;
	var invalid=jsondata.invalidcount;
	var total=jsondata.totallines;
	var valid=jsondata.validcount;
	var listid=jsondata.listid;
	$('#listid').val(listid);
	var summary="&nbsp;Total Emails: "+total+"<br>";
	var x=Number(valid)-Number(duplicates);
	if(duplicates>0 && x>0){
	summary=summary+"&nbsp;Valid Emails: "+valid+", Added Emails: "+(valid-duplicates)+", Duplicates: "+duplicates+"<br>";
	}else if(duplicates>0 && x<=0){
	summary=summary+"&nbsp;Valid Emails: "+valid+", Duplicates: "+duplicates+"<br>";
	}else if(valid>0 && duplicates<1){
				summary=summary+"&nbsp;Valid Emails: "+valid+", Added Emails: "+valid+"<br>";
			}
	if(invalid>0){
	summary=summary+"&nbsp;Invalid Emails: "+invalid+" (Displayed in the Emails box below)"+"<br>";
	}
	$('#'+msgDivid).html(summary);
}

function getEmailsList(){
  var invalidmaillist='',validbulkmailslist='',validcount='0',invalidcount='0',totalemails='',filterdomainslist='';
  var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
  var leftemails=$("#bulktextarealeft").val();
  var rightemails=$("#bulktextarearight").val();
  var listname=$('#listname').val();
  var fdomains=$('#filterdomains').val();
  if(fdomains!='' && fdomains!='undefined' && fdomains!='null')
  fdomains=fdomains.substring(1,fdomains.length-1);
  listname=listname.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
  leftemails=leftemails.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
  rightemails=rightemails.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
  $('#listname').val(listname);
  $('#bulktextarealeft').val(leftemails);
  $('#bulktextarearight').val(rightemails);
  rightemails=rightemails.replace('Invalid mails are:','');
  leftemails=processingMailList(leftemails);
  rightemails=processingMailList(rightemails); 
  if(rightemails!='')
  totalemails=leftemails+","+rightemails;
  else
  totalemails=leftemails;
  var maillist=totalemails.split(",");
  var nonduplicatelist=removeduplicates(maillist);
  if(nonduplicatelist.length>1000){
  alert("maillist should be less than 1000 only");
  return false;
  }
  var duplicatemailscount=Number(maillist.length)-Number(nonduplicatelist.length);
  for(var i=0;i<nonduplicatelist.length;i++){
    if(!nonduplicatelist[i].match(emailExp)){
	 if(invalidmaillist!='' && nonduplicatelist[i]!=''){
   invalidmaillist=invalidmaillist+'\n'+nonduplicatelist[i];
   invalidcount++;
   }
   else{
   invalidmaillist=invalidmaillist+nonduplicatelist[i];
   invalidcount++;
   }
   }
   else{
	  if(validbulkmailslist!='' && nonduplicatelist[i]!=''){
	  var partdomain=nonduplicatelist[i].substring(parseInt(nonduplicatelist[i].indexOf('@'))+1,nonduplicatelist[i].length);
	  if(fdomains.indexOf(partdomain)==-1){
		  validbulkmailslist=validbulkmailslist+'\n'+nonduplicatelist[i];
        validcount++;
	}else{
		if(filterdomainslist!='')
			filterdomainslist=filterdomainslist+'\n'+nonduplicatelist[i];
		else
			filterdomainslist=nonduplicatelist[i];
	}
		
   }else{
	   var partdomain=nonduplicatelist[i].substring(nonduplicatelist[i].indexOf('@')+1,nonduplicatelist[i].length);
	   
	   if(fdomains.indexOf(partdomain)==-1){
	     validbulkmailslist=validbulkmailslist+nonduplicatelist[i];
       validcount++;
		}else{
			if(filterdomainslist!='')
				filterdomainslist=filterdomainslist+'\n'+nonduplicatelist[i];
			else
				filterdomainslist=nonduplicatelist[i];
		}
			
   } 
  }
  }
  $('#bulktextarealeft').val(validbulkmailslist);
  var msg='';
  if((invalidmaillist!='' && invalidmaillist.length>0) && (filterdomainslist!='' && filterdomainslist.length>0)){
	  msg="Invalid mails are:\n"+invalidmaillist+"\nFilter mails are:\n"+filterdomainslist;
	  $('#bulktextarearight').val(msg);
  }else if(filterdomainslist.length>0){
	  msg="Filter mails are:\n"+filterdomainslist;
	  $('#bulktextarearight').val(msg);
  }else if(invalidmaillist.length>0){
	  msg="Invalid mails are:\n"+invalidmaillist;
	  $('#bulktextarearight').val(msg);
  }else
	  $('#bulktextarearight').val(invalidmaillist);
  $('#validcount').val(validcount);
  $('#invalidcount').val(invalidcount);
  $('#addedmailscount').val(maillist.length);
  $('#dupliactemailscount').val(duplicatemailscount);
  return true;
}


function removeduplicates(maillist){
  var newmaillist=new Array(); 
  label:for(var i=0;i<maillist.length;i++){
   maillist[i]=maillist[i].toLowerCase().replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
  for(var j=0;j<newmaillist.length;j++)
  {
   if(newmaillist[j]==maillist[i]) 
   continue label;
  }
  newmaillist[newmaillist.length] = maillist[i];
  }
  return newmaillist;
}

function processingMailList(emaillist){
emailsarray=emaillist.split("\n");
var emailadd='';
for(var i=0;i<emailsarray.length;i++){
 var temp=emailsarray[i];
 columnsarry=temp.split(","); 
 if(columnsarry.length>1){
 for(var j=0;j<columnsarry.length;j++){
  if(emailadd!='')
  emailadd=emailadd+','+columnsarry[j];
  else
  emailadd=columnsarry[j];
 }
 }else{
 if(temp!=''){
 if(emailadd!='')
 emailadd=emailadd+','+temp;
 else
 emailadd=temp;
 }
 }  
}
return emailadd;
}


$('#myModal .close').click(function(){
    $('#myModal').modal('hide');
	if(purpose=='maillist')
	window.location.reload();
});