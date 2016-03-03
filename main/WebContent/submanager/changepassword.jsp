<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
<style>
body {
margin-top: 0px;
}

ul.errorMessage{
margin-left: -36px;
}
</style>
</body>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
		<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
		<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
		<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
		<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
		
        
    </head>
      <body>
       <div class="container">
            <div class="row">
                <div class="col-md-12">
  			<s:form name="changepwdform" id="changepwdform" action="changepassword!saveSubMgrChangePwd" method="post">                 
                            &nbsp;&nbsp;&nbsp;<div id="errormsg" class="alert alert-danger" style="display:none; padding-left: 50px;"></div>
                             <div class="form-group">
                            <div class="col-sm-6">
							<s:textfield name="email" cssClass="form-control" placeholder='<%=I18n.getString("global.email.lbl")%>' autofocus="autofocus"></s:textfield>                            
                            </div>
                            </div>
                            
                             <div class="form-group">
                            <div class="col-sm-6">
							<s:password name="password" cssClass="form-control" placeholder='<%=I18n.getString("sub.old.pwd.lbl")%>'></s:password>                            
                            </div>
                            </div>
                            
                             <div class="form-group">
                            <div class="col-sm-6">
							<s:password name="newPassword" cssClass="form-control" placeholder='<%=I18n.getString("sub.new.pwd.lbl")%>'></s:password>                            
                            </div>
                            </div>
                            
                            <div class="form-group">
                            <div class="col-sm-6">
							<s:password name="confirmNewPassword" cssClass="form-control" placeholder='<%=I18n.getString("sub.confirm.new.password")%>'></s:password>                            
                            </div>
                            </div>
                     </s:form>               
                        <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="loginpblmbtn" class="btn btn-primary"><%=I18n.getString("global.submit.btn.lbl")%></button>
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
    	$("input.form-control").ezpz_hint();
//$('#popup', window.parent.document).css("height","150px");        
$('#loginpblmbtn').click(function(){
	$('#errormsg').hide();
	//$('#loginpblmbtn').html('Sending..');
	$('#loginpblmbtn').attr('disabled','disabled');
	$.ajax( {
    	   type: "POST",
    	   url: 'changepassword!saveSubMgrChangePwd',
    	   data: $("#changepwdform").serialize(),
    	   success: function( result ) {
        	   $('#popup', window.parent.document).css("height","400px"); 
    		   $('#errormsg').show();
    		   $('#loginpblmbtn').html(props.global_submit_lbl);
    		   $('#loginpblmbtn').removeAttr('disabled');
    		   if(result.indexOf('errorMessage')>-1){
    			   $('#errormsg').html(result);
    			   $('#errormsg').removeClass('alert-success');
    			   $('#errormsg').addClass('alert-danger');
    		   } else{
    			   //$('#errormsg').removeClass('alert-danger');
    			   $('#loginpblmbtn').hide();
    			   /* $('#errormsg').addClass('alert-success'); 
    			   $('#errormsg').html('Your password have been updated successfully.'); */
    		        $('#changepwdform').html(props.la_sub_mgr_password_success_lbl1+'<br/>'+props.la_sub_mgr_password_success_lbl2+'<a href="javascript:submanagerrloginpage();">'+props.la_sub_mgr_password_success_lbl3+'</a>'+props.la_sub_mgr_password_success_lbl4);
    			   parent.submanagerLogout();
            	   }
    	   }});
        });
    });
    function submanagerrloginpage(){
    	   parent.window.location.href='/main/submanager/logout';
    }
    </script>
      </html>