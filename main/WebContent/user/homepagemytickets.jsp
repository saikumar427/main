<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>

<!doctype html>
<html>
    <head>
        <title><%=I18n.getString("la.my.tlts.title.lbl")%></title>
        <meta name="viewport" content="width=device-width, user-scalable=no">
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
       <link type="text/css" rel="stylesheet"
					href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
	  
    </head>
    
    <body>
        <div class="container">
            
            <div class="row">
                <div class="col-md-12">
                <br>
                     &nbsp;&nbsp;&nbsp;<div class="alert alert-danger" style="display:none"></div>
                    
                        <div class="form-group">
                            <div class="col-sm-6"> <br/>
                            <%=I18n.getString("la.enter.tid.to.get")%><br/><br/>
                                <div class="input-group">
							      <input type="text" class="form-control" name="transaction_id" id="transaction_id"  placeholder="<%=I18n.getString("la.supp.enter.tranid")%>" autofocus />
							      <span class="input-group-btn">
							        <button id="tkts" class="btn btn-primary"><%=I18n.getString("la.email.me.tkts")%></button>
							      </span>
							    </div>
                            </div>
                        </div>
                        
                        
                         <div class="form-group">
                            <div class="col-sm-6">
                            <%=I18n.getString("la.dont.have.tid")%><br/><br/>
                                <div class="input-group">
							      <input type="text" class="form-control" name="email_id" id="email_id"  placeholder="<%=I18n.getString("la.enter.email.lbl")%>" autofocus />
							      <span class="input-group-btn">
							        <button id="transID" class="btn btn-primary"><%=I18n.getString("la.email.me.tids")%></button>
							      </span>
							    </div>
                            </div>
                        </div>
                        
                         <div class="form-group">
                            <div class="col-sm-6">
                           <%=I18n.getString("la.email.tkts.note.content")%>
                            </div>
                        </div>
                </div>
            </div>
            
        </div>
    </body>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <script src="/main/js/jquery.ezpz_hint.js"></script>
    <script>
        
        $(document).ready(function() {

			$('input.form-control').ezpz_hint();
        	
            $('#cancel').click(function(){
            	parent.closediv();
                });

            $('#transID').click(function(){
            	$('.alert').hide();
                var emailid=$('#email_id').val();
              if(emailid==''){
            	  $('.alert').removeClass('alert-success');
      			$('.alert').addClass('alert-danger');
            	  $('.alert').show();
            		 $('.alert').html(props.la_plz_enter_email);
            		 return;
                  }
             var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
           	 if(!emailid.match(emailExp)){
           		$('.alert').removeClass('alert-success');
    			$('.alert').addClass('alert-danger');
           		 $('.alert').show();
           		 $('.alert').html(props.la_get_tkts_popup_ent_mail);
           		   return ;
           		 }
                
            	var url="mytickets!getEmailId?email_id="+emailid;
            	$('#transID').attr('disabled','disabled');
            	 $.ajax( {
               	   type: "POST",
               	   url: url,
               	   success: function( id ) {
               		$('#transID').removeAttr('disabled');
               		  if(id.indexOf("success")>-1){
               			 $('.alert').removeClass('alert-danger');
               			$('.alert').addClass('alert-success'); 
               			$('.alert').show();
               			$('.alert').html(props.la_get_tkts_popup_sent_tids);
                  		// $('.alert-danger').html('Success fully sent');
                   		  }else{
                   			 $('.alert').removeClass('alert-success');
                    			$('.alert').addClass('alert-danger');  
                   			 $('.alert').show();
                       		 $('.alert').html(props.la_get_tkts_email_inreg);
                       		  }
               	   }});
                });


            
            $('#tkts').click(function(){
            	$('.alert').hide();
var transactionid=$('#transaction_id').val();
if(transactionid==''){
	 $('.alert').removeClass('alert-success');
		$('.alert').addClass('alert-danger'); 
	 $('.alert').show();
		 $('.alert').html(props.la_enter_trxn_id);
		 return;
	}$('#tkts').attr('disabled','disabled');
            	var url="mytickets!getTransactionId?transaction_id="+transactionid;
            	 $.ajax( {
               	   type: "POST",
               	   url:url,
               	   success: function( id ) {
               		$('#tkts').removeAttr('disabled');
               		if(id.indexOf("success")>-1){
               			$('.alert').removeClass('alert-danger');
               			$('.alert').addClass('alert-success'); 
               			$('.alert').show();
              			 $('.alert').html(props.la_get_tkts_popup_email_sent);
                  		  }else{
                  			 $('.alert').removeClass('alert-success');
                    			$('.alert').addClass('alert-danger'); 
                  			 $('.alert').show();
                      		 $('.alert').html(props.la_get_tkts_valid_tids);
                      		  }
               	   }});
            	                });
        });
       
    </script>

</html>