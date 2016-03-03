function configureEventView(eid,val,updateto){
	var url="";
	var date = new Date();
	var resultdiv='';
	$('#googleSettingUpdateMessage').hide();
	$('#showattendeesSettingUpdateMessage').hide();
	if(updateto=='googlemap'){
	resultdiv='googleSettingUpdateMessage';
		url="EventPageContent!updateshowgooglemap?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();		
	}else if(updateto=='questions'){
		resultdiv="questionsSettingUpdateMessage";
		url="EventPageContent!updateshowquestions?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();
	}else if(updateto=='socialpromotions'){
	   resultdiv="socialPromotionsUpdateMessage";
	   url="EventPageContent!updateshowsocialpromotions?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime(); 
	}else if(updateto=='voltickets'){
	   resultdiv="volticketsUpdateMessage";
	   url="EventPageContent!updateshowvolumetickets?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime(); 
	}else{
	resultdiv='showattendeesSettingUpdateMessage';
	url="EventPageContent!updateshowattendee?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();
	}
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();
	$.ajax({
		url : url,
		type : 'get',
		success:function(transport) {
			$("#"+resultdiv).html("Updated");
			$("#"+resultdiv).show();
		}
	});
}

function uploadExternalPhotoImage(){
	$.ajax({
		type:'POST',
		url:'ImageUpload!save',
		data:$('#imageupload').serialize(),
		success:function(result){
			/* alert(result); */
			}
			}); 
}

function logoimgurl(response){
	 $('#logoimageurl').show();
	 $('#logoimageurl').prop("src",response); 
	  $('#dellogoimgbtn').removeClass('disabled'); 
}

function photoURL(result){
	
	$('#eventphotourlimage').html('<img src="'+result+'" id="imgupload" width="100px" height="75px">')
	 $('#delextphoto').removeClass('disabled');
}

function openNewNotice(){
	
	 url='NoticeBoard!newNotice?eid='+eid,   
	$('#noticeboardadd').load(url,function(){
		$('#noticeboardadd').fadeIn();
		$('#noticeid').focus();
		$('#addNoticeBtn').attr('disabled','disabled');
});
}

var pwdsuccess=function(value){
	
	var password = value;
	var powertype = $('#powertype').val();
	/* alert(eid+"::"+password+"::"+powertype); */
	var url="EventPageContent!insertEventPassword?eid="+eid;
	var dynatimestamp=new Date();
	url+="&dynatimestamp="+dynatimestamp.getTime();
	var params={password:password,powertype:powertype};
	$.ajax({
		url: url,
		type: "POST",
		data: params,
		success: function(result){
			$('#changepwd').html(value);
			$('#hiddenpassword').val(value);
			$('#singleValuePopUpOptions').hide();
		}
		});
	};
	
	var cancel=function(){  };
	
	function builddata(data){
		var curlevel= $('#clevel').val();
		var temp='',temp1='',temp2='',html='';
		 /*if(data.citems.length==0 &&  (curlevel!= '90' && curlevel!='100'))
			 $('#addNoticeBtn').trigger('click');
		  else{*/
		$.each(data,function(outkey,resObj){
			$.each( resObj, function( inkey, valueobj ) {
				temp=valueobj.id;                                      /* \''+eid+'\',\''+subMgrId+'\' */
		    	temp1='&nbsp;<a  href="javascript:;" onclick=submiteditNotice('+temp+','+eid+')>';
		    	temp2='<a href="javascript:;" onclick=deleteNotice('+temp+')>';
		       
				var tempentry=$('<tr id="notice_'+temp+'"><td style="max-width: 100px;">'+valueobj.noticetype+'</td><td>'+valueobj.notice+'</td><td>'+valueobj.postedat+'</td>' 
								+'<td><span id="manage_'+temp+'">'+temp1+'Edit</a>&nbsp; &nbsp;'+temp2+'Delete</a></span></td></tr>');	
				
	           $('#noticeboarddata  .nodata').remove();
				$('#noticeboarddata').append(tempentry);
			});
		});
			/* }//else
*/	}
	
	function handleAddNoteSubmit(){
	    var noticevalue=document.getElementById("noticeid").value;   
	    if(noticevalue.trim()=="") {       	
	    document.getElementById("noticedataformerrormsg").style.display="block";    
	    document.getElementById("noticedataformerrormsg").innerHTML="Notice should not be empty";
	      
	    } 
	    else  
	    	submitAddNotice();
	}
	
	
	function submitAddNotice(){
		var url = 'NoticeBoard!saveNoticeData';
		$.ajax({
			type:'POST',
			url :url,
			data : $('#noticedataform').serialize(),
			success: function(t) {
				$('#addNoticeBtn').prop("disabled",false);
			$('#noticeboardadd').fadeOut();
			getReloadedData(); 
			$('#rebuild').html('');
			}
		});
	}
	
	function getReloadedData(){
		$.ajax({
			type:'POST',
			url:'NoticeBoard!getNoticeItemsAfterAdd?eid='+eid,
			success:function(data){
				builddata(JSON.parse(data));
			}
		});
	}
	
	function submiteditNotice(noticeId,eid){
		$('#noticeboardadd').hide();
		 $('.editNoticeRow').remove();      
		$('#noticeid').html('');
		
		  $('#manage_'+globalEditNotice+" a").each(function(){
			 $(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
		        $(this).prop("disabled",false);
		});
		globalEditNotice = noticeId;
		$('#manage_'+noticeId+" a").each(function(){
			 $(this).css({"pointer-events": "none","cursor":"default","color":"#a5c7ed"});
		        $(this).prop("disabled",true);
		});
		$('#addNoticeBtn').prop("disabled",true); 
		 var url='NoticeBoard!editNoticeData?eid='+eid+'&noticeId='+noticeId
	      $('<tr class="editNoticeRow"><td colspan="4" ><div id="noticeboardadd">   </div></td></tr>').insertAfter($('#notice_'+noticeId)); 
		 $('#noticeboardadd').load(url,function(){
			$('#noticeboardadd').fadeIn();
		}); 
	}
	
	function deleteNotice(noticeId){ 
		$('#noticeboardadd').hide();
		var agree;
	    agree="Do you really want to delete?"; 
	    var url='NoticeBoard!deleteNotice?eid='+eid+'&noticeId='+noticeId;
	    bootbox.confirm('<h3>Delete Notice</h3>'+agree, function (result) {
	     	if (result){	           	
	       parent.loadingPopup();
	    	$.ajax({
	    			url : url,
	    		   type: 'get',
	    		  success: function(transport) {
	    			var result=transport;
	    			parent.hidePopup();
	    			if(!isValidActionMessage(result)){
	    				return;
	    			}			
	    			$('#notice_'+noticeId).remove();
	    			var rowCount = $('#noticeboarddata tr').length;
	    			
	    			if(rowCount==1)
	    				{
	    				$('#addNoticeBtn').trigger('click');
	    				}
	    	      }
	    	});	
	    }

	     });
	    
	}
	
	function updateSocialnw(){
		var ptype=document.getElementById("ptype").value;
		var clevel=document.getElementById("clevel").value;
		var eventid=document.getElementById("eid").value;
		if(ptype=='RSVP' && clevel=='90'){
		  if(document.getElementById("twitter").checked || document.getElementById("fblike").checked || document.getElementById("googleplusone").checked || document.getElementById("fbcomment").checked || document.getElementById("fbattending").checked)
			  specialFee(eventid,'150','SocialNetworking','RSVP');
		  else
			  submitSocialNetworking(); 
		}else
			submitSocialNetworking();
		}
	
	function submitSocialNetworking(){
		var url = 'EventPageContent!updateSocialNwSettings';
		$.ajax({
			url :url,
			data : $('#socialnwForm').serialize(),		
	        success: function(t) {
	        	if(!isValidActionMessage(t)){return;}
	        	var msg=t;
	  	      	if(msg.indexOf("You have no cards")>-1){
	  	      		 _powertype='RSVP'//global variable declared in speciafee.js
	  	      		 _purpose='EventPageContentSocial';//global variable declared in speciafee.js
		  	         var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+_mgrId+'&purpose=invoice_hold_socialnetworking';
		        getccContent(_mgrId,'invoice_hold_socialnetworking');      
	             }else{
					var fbeid=document.getElementById('facebookeventid').value;
					fbeid=fbeid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
					document.getElementById('facebookeventid').value=fbeid;
				   /*  $("#fbeventidvalidationmessage").hide(); */
					$("#UpdateMessage").hide();
		        	$("#socialNetworkUpdateMessage").html(t); 
	        	}
	        }
		});
		
	}
	
	
	function updatePromotionSettings(){
		var url = 'EventPageContent!updatePromoSettings';
		$.ajax({
			url :url,
			data : $('#socialsettings').serialize(),
			success: function(t) {
				$("#promoupdateMessage").html(t);	
		}
		});
	}
	
	function processFacebookId(facebookeventid) {
		 var msg = "Please enter  valid Facebook event id only.";
		 var msg1="There are some errors";
		 var fbeid=document.getElementById('facebookeventid').value;
		 var inpVal = Number(document.getElementById('facebookeventid').value);
		 $('input.fbattending').on('ifChecked', function(event){
		  if(fbeid=='')
		  {
			  $("#fbeventidvalidationmessage").show();
		        $("#fbeventidvalidationmessage").html(msg);
		        document.getElementById("UpdateMessage").innerHTML="";
		        $("#UpdateMessage").show();
		        $("#UpdateMessage").update(msg1);
		        document.getElementById("socialNetworkUpdateMessage").innerHTML="";
		        return false;
		   }
		 });
		   if(isNaN(inpVal)) {
			  try{
		            var err = new Error(msg);
		            if (!err.message) {
		                err.message = msg;
		            }
		            throw err;
		        }
		       catch (e) {
		    	 $("#fbeventidvalidationmessage").show();
		         $("#fbeventidvalidationmessage").html(e.message);
		         document.getElementById("UpdateMessage").innerHTML="";
		         $("#UpdateMessage").show();
		         $("#UpdateMessage").update(msg1);
		         document.getElementById("socialNetworkUpdateMessage").innerHTML="";
		         
		    	facebookeventid.focus();
		        facebookeventid.select();
		    }
		   }
		   else
			   return true;
	}
	
	
	function editdisplayfn(){
		 var url = 'AttendeeListDisplayFields!displayInformation?eid='+eid;
			$('#myModal').on('show.bs.modal', function () {	
				 $('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.epc_display_information_header_lbl+'</span></h3>');	  
			        $('iframe#popup').attr("src",url);
			         });		
			        
			         $('#myModal').modal('show');    
					        $('#myModal').on('hide.bs.modal', function () { 
					        $('iframe#popup').attr("src",'');			        
					        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
					    });	
	 }
	 
	function getLogoAndMessage(eid){ 	
	    var url="EventPageContent!getlogoandmessage?eid="+eid;
		$('#myModal').on('show.bs.modal', function () {	
		 $('#myModal .modal-title').html(props.epc_logo_message_section_header_lbl);	  
	        $('iframe#popup').attr("src",url);
	         });		
	        
	         $('#myModal').modal('show');    
			        $('#myModal').on('hide.bs.modal', function () { 
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
			    });	

	}
	
	function getEventPassword(){
		
		_powertype="${powertype}";
		var curlevel= "${currentLevel}";
		
		var curfee="${currentFee}";	var cardrequired='';
		if(_powertype=='RSVP' && curlevel == 150 && curfee == 0)
			  cardrequired='no';
		
		var url='SpecialFee!getCCStatus?eid='+eid;
		if(_powertype=='RSVP' && cardrequired!='no'){
			$.ajax({
				    url: url,
		            type: "post",
		            success: function( t ) 
		            {
		            	
		            	 var ccjson=eval('('+t+')');
		 		        var cardstatus=ccjson.cardstatus;
		 		        var userid=ccjson.userid;
		 		        if(cardstatus=='Y')
		 		        {
		 		        	var url="EventPageContent!geteventpassword?eid="+eid;
		 		        	$('#myModal').on('show.bs.modal', function () {	
			   	       			   $('#myModal .modal-title').html('<h3><span style="padding-left:10px">Password Protection</span></h3>');		
				                   $('iframe#popup').attr("src",url);
						            });		
				                    $('#myModal').modal('show'); 
								        $('#myModal').on('hide.bs.modal', function () 
								    { 
								        $('iframe#popup').attr("src",'');			        
								        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
								    });	

		            }else {
		            	_eventid=eid;	//global variable declared in speciafee.js
				          _purpose='EventPageContent';//global variable declared in speciafee.js
				          var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_eventpassword';
		                 // loadURLBasedWindow(url,handlesuccessccform);     
			             getccContent(userid,'invoice_hold_eventpassword'); 
		            }
		        }
			});
	      	}else{
			var url="EventPageContent!geteventpassword?eid="+eid;
			$('#myModal').on('show.bs.modal', function (){	
	   			   		$('#myModal .modal-title').html('Password Protection');		
	            		$('iframe#popup').attr("src",url);
		            });		           
	             		$('#myModal').modal('show');   			 
				        $('#myModal').on('hide.bs.modal', function () 
				     { 
				        $('iframe#popup').attr("src",'');			        
				        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
				    });	
			}
		
	}	
	
	function deletePhotoURL(){	
		bootbox.confirm('Do you want to delete photo', function (result) {
			if(result){
				parent.loadingPopup();
				var url="EventPageContent!deletePhotoURL?eid="+eid;
				$.ajax({
	    			method:'GET',
	    			url:url,
	    			success: function( result ){
	    				parent.hidePopup();
	    				$('#imgupload').hide();
	    				$('#delextphoto').addClass("disabled");
	    				}
	    			  });
			}
		});
	}
	
	function addexternalphoto(){	
		var url="EventPageContent!getexternalphotourl?eid="+eid;
		$('#myModal').on('show.bs.modal', function () 
				{	
					$('#myModal .modal-title').html(props.epc_external_photo_popup_header);	
			     	$('iframe#popup').attr("src",url);
			    });		         
		   			parent.window.resizeIframe();
	      			$('#myModal').modal('show');     	    
			        $('#myModal').on('hide.bs.modal', function () 
			        		{ 
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
			    			});	

	}
	
	function uploadphoto(){
		uploadpurpose='imageupload';
		var url='../membertasks/ImageUpload';

			  $('#myModal').on('show.bs.modal', function () {	
		      $('#myModal .modal-title').html(props.epc_upload_image_header_lbl);  
			  $('iframe#popup').attr("src",url);
			      });		
			   $('#myModal').modal('show');    
			        $('#myModal').on('hide.bs.modal', function () { 
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad();" frameborder="0"></iframe>');
			    });	
	}
	function uploadlogobtn(){
		uploadpurpose='logoupload';
		var url='../membertasks/ImageUpload';
			  $('#myModal').on('show.bs.modal', function () {	
		      $('#myModal .modal-title').html('Upload Image');  
			  $('iframe#popup').attr("src",url);
			      });		
			   $('#myModal').modal('show');    
			        $('#myModal').on('hide.bs.modal', function () { 
			        $('iframe#popup').attr("src",'');			        
			        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad();" frameborder="0"></iframe>');
			    });	
	}
	
	function deleteLogoURL(){
		bootbox.confirm('Do you want to delete logo', function (result){
			if(result)
			{
				parent.loadingPopup();
				var url="EventPageContent!deleteLogoURL?eid="+eid; 
				$.ajax({
	    			method:'GET',
	    			url:url,
	    			success: function( result ) 
	    			{
	    				$('#logourlimage').hide();
	    				$('#dellogoimgbtn').addClass("disabled");
	    				parent.hidePopup();
	    			}
	    			});
				
			}
	    });
	}
	function enableButtons(){
		$('#upgradenoticebtn').hide();
		$('#noticebtn').show();
		$('#addNoticeBtn').trigger('click');
		$('#description').hide();
		$('#noticedatatable').show();
		$('#pwddesc').hide();
		$('#pwdfield').show();
		$('#pwdenablebtn').hide();
		$('#setpwdbtn').show();
	}
	function closePopup(){
		 parent.$('#myModal').modal('hide');    
	}
	function enableProtection(){	
		var pwtype="${powertype}";
		
		if(pwtype=='RSVP')
		specialFee(eid,'150','PasswordProtection','RSVP');
		else
		specialFee(eid,'200','PasswordProtection','Ticketing');	
	}
	function enableNotice(){	
		var pwtype="${powertype}";
	
		if(pwtype=='RSVP')
		specialFee(eid,'150','NoticeBoard','RSVP');
		else
		specialFee(eid,'200','NoticeBoard','Ticketing');	
	}
