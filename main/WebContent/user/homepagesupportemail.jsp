<%@page import="java.util.ResourceBundle"%>
<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<%@include file="../getresourcespath.jsp"%>
<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<!doctype html>

<html>
    <head>
        <title><s:text name="la.supp.bootstrap"/></title>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
       
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
        
    </head>
    <body>
        <div class="container">

             <div>
              <div class="col-md-12 alert alert-danger" style="display:none" ></div>        
             </div>

            <div class="row">
                <div class="col-md-12">
                        <s:form name="supportemail" id="supportemail" action="/user/supportmailprocessing.jsp"  cssClass="form-horizontal">
						<input type="hidden" name="token" value="1234"/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="username" name="username" placeholder="<%=I18n.getString("la.supp.ph.username")%>" autofocus />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="useremail" name="useremail" placeholder="<%=I18n.getString("la.supp.ph.email")%>" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="phone" name="phone"  placeholder="<%=I18n.getString("la.supp.ph.phone")%>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="eventurl"  name="eventurl" placeholder="<%=I18n.getString("la.supp.ph.eventurl")%>" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea  class="form-control" id="message" name="message"   style="color:black;" rows="5" placeholder="<%=I18n.getString("la.supp.ph.desc")%>"></textarea>
                            </div>
                        </div>
                        <div class="form-group">


                            <div class="col-sm-12 text-center">
                         
                                <button type="submit" class="btn btn-primary"><%=I18n.getString("la.supp.send.btn")%></button>
                                <!--button class="btn btn-cancel" id="cancel"> Cancel</button-->
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>
    </body>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
	<script src="/main/js/jquery.tooltipster.js"></script>
	<script src="/main/js/jquery.validate.min.js"></script>
	<script src="/main/js/jquery.ezpz_hint.js"></script>
    <script>
    activateTextArea();
    function activateTextArea(){
        var textarea=document.getElementById("message");
        textarea.onfocus = function () {
           if (this.value == props.la_type_msg_req_lbl) {
               this.value = "";
               this.style.color ="black";
           } 
          return false; 
        };
        textarea.onblur = function () {
             if (this.value == "") {
               this.value = props.la_type_msg_req_lbl;
               this.style.color ="darkgray";
           } 
       };
   }
        $(document).ready(function() {
        	$("input.form-control").ezpz_hint();
            $('#cancel').click(function(){
            	parent.closediv();
                });
            $('#supportemail input,#supportemail textarea').tooltipster({
                trigger: 'custom',
                onlyOne: false,
                fixedWidth:'300px',
                position: 'top-right',
                theme:'form-validate-theme',
                animation:'grow'
            });
            
            var validator = $('#supportemail').validate({
                errorPlacement: function (error, element) {
                    var lastError = $(element).data('lastError'),
                        newError = $(error).text();
                    $(element).data('lastError', newError);
                    if(newError !== '' && newError !== lastError){
                        $(element).tooltipster('content', newError);
                        $(element).tooltipster('show');
                    }
                },
                success: function (label, element) {
                	  
                    $(element).tooltipster('hide');
                },
                submitHandler: function (form) {
                if($('#username').val()==''){
                	$('.alert-danger').show();
                    $('.alert-danger').html(props.la_supp_popup_ent_name);
                    return;
                      }
      			var email=$('#useremail').val().replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
                $('#useremail').val(email);
      
                if($('#useremail').val()==''){
                	$('.alert-danger').show();
                    $('.alert-danger').html(props.la_supp_popup_ent_mail);
                    return;
                }else{
                	var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
                	 if(!$('#useremail').val().match(emailExp)){
                		 $('.alert-danger').show();
                		 $('.alert-danger').html(props.la_supp_popup_invalid_mail);
                		   return ;
                		 }
                    }

               var message=$('#message').val();
	          if(message=='<%=I18n.getString("la.supp.ph.desc")%>')
            	   $('#message').val('');
                if($('#message').val()==''){
                	$('.alert-danger').show();
                    $('.alert-danger').html(props.la_supp_popup_enter_msg);
                    return;
                }
                  $('.btn-primary').html(props.la_supp_popup_sending_lbl);
                  $('.btn-primary').attr('disabled','disabled');
                  $('.s-danger').hide();
                   $.ajax( {
                 	   type: "POST",
                 	   url: '/main/user/supportmailprocessing.jsp',
                 	   data: $("#supportemail").serialize(),
                 	   success: function( id ) {
                     	   id=JSON.parse(id);
						   if(id.result=='success'){
							   $('.alert-danger').html('');
							   $('.alert-danger').hide();
							   $('#supportemail').html(props.la_supp_popup_succ_msg);
                     		  }
                 	   }});
                    return true;
                }
            });
        });
        function gotoFaq(){
parent.window.location.href='/main/faq';
            }
    </script>
</html>