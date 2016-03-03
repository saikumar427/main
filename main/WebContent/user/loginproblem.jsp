<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
<style>
body {
margin-top: 0px;
}
</style>
</body>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" 	href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
		<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet" />
    </head>
      <body>
       <div class="container">
            <div class="row">
                <div class="col-md-12">&nbsp;&nbsp;&nbsp;<div id="errormsg" class="alert alert-danger" style="display:none"></div>
   					<s:form name="signupform" id="signupform" action="loginproblem!loginHelp" method="post"  >                  
             			<s:hidden name='role'></s:hidden>
                			<div class="form-group">
                            <div class="col-sm-6">
								<s:text name="la.get.pwd.info"/><br/><br/>
								<s:textfield name="userData.email" cssClass="form-control" placeholder="%{getText('la.enter.email.ph.lbl')}" autofoucs="autofocus"></s:textfield>                            
                            </div>
                            </div>
                     </s:form>               
                        <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                            <span class="alert alert-danger" style="display:none"></span>
                                <button id="loginpblmbtn" class="btn btn-primary"><s:text name="la.send.btn.lbl"/></button>
                            </div>
                        </div>
                </div>
                </div>
                </div>
      
      </body>
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
       <script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js"></script>
    <script src="/main/bootstrap/js/bootstrap.js"></script>
    <script src="/main/js/jquery.ezpz_hint.js"></script>
    <script>
    $(document).ready(function() {
    	$("input.form-control").ezpz_hint();
$('#popup', window.parent.document).css("height","170px");        
$('#loginpblmbtn').click(function(){
	$('#errormsg').hide();
	$('#loginpblmbtn').html(props.la_sub_mgr_sending_lbl);
	$('#loginpblmbtn').attr('disabled','disabled');
	$.ajax( {
    	   type: "POST",
    	   url: 'loginproblem!loginHelp',
    	   data: $("#signupform").serialize(),
    	   success: function( result ) {
        	   $('#popup', window.parent.document).css("height","280px"); 
    		   $('#errormsg').show();
    		   $('#loginpblmbtn').html(props.la_send_label);
    		   $('#loginpblmbtn').removeAttr('disabled');
    		   if(result.indexOf('errorMessage')>-1){
    			   $('#errormsg').html(result);
    			   $('#errormsg').removeClass('alert-success');
    			   $('#errormsg').addClass('alert-danger');	   
        	   	   
    		   } else{
    			   $('#errormsg').removeClass('alert-danger');
    			   $('#errormsg').addClass('alert-success'); 
    			   $('#errormsg').html(props.la_login_info_mailed);
            	   }
    	   
    	   }});


        });
    });
    </script>
      </html>