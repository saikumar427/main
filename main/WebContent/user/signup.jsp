<%@taglib uri="/struts-tags" prefix="s"%>
    <title><s:text name="global.signup.btn.lbl"></s:text></title>
    
    <style>
        .form-validate-theme {
            border-radius: 5px; 
            border: 2px solid #ff4747;
            background: #fff4f4;
            color: #000;
            z-index: 9 !important;
        }
        .form-validate-theme .tooltipster-content {
            width:100%;
            font-size: 14px;
            line-height: 16px;
            padding: 8px 10px;
        }
        .alert-danger{
        width:355px;
        padding-left: 40px;
        }
      
    </style>
       
              <div class="row"  style="background-color: white;">
              <div class="col-md-2"></div>
			  <div class="col-md-8"> 
                 <div class="col-md-2"></div>
                     <div class="col-md-8">
                     <div class="col-md-1"></div>
                     <div class="col-md-10">
                     <div style="text-align: center"><h1><span class=" text-muted"><s:text name="global.signup.btn.lbl"></s:text> </span></h1></div>     
                     <s:if test="%{errorsExists==true}">
									<div class="form-group"  id="errors">
										<s:fielderror theme="simple"  cssClass="alert alert-danger"   />
									</div>
					</s:if>
                        <form action="signup!signUpProcess" class="form-horizontal" id="signupform" name="signupform" method="post">
                          		<div style="height:8px;"></div>
                          			<s:textfield id="email" name="userData.email" type="text" placeholder="%{getText('la.signup.email.ph')}" cssClass="form-control" autofocus="autofocus"/>
                                <div style="height:15px;"></div>
                                    <s:textfield id="beeid" name="userData.beeId"  type="text" placeholder="%{getText('la.signup.beeid.ph')}" cssClass="form-control" />
                         		<div style="height:15px;"></div>
                                    <s:password id="password" name="userData.password" type="password" placeholder="%{getText('la.signup.pwd.ph')}" cssClass="form-control"/>
                                <div style="height:15px;"></div>
                                    <button type="submit" id="signupbtn" class="btn btn-block btn-primary pull-right" data-style="expand-right"data-size="l"><s:text name="global.signup.btn.lbl"/></button>
                                <div style="height:50px;"></div>
                                    <small>
                                        <s:text name="la.signup.terms.of.service.lbl"></s:text>
                                        <a href="/main/termsofservice<s:property value="I18N_CODE_PATH"/>" target="_blank"><s:text name="la.signup.terms.link.lbl"></s:text></a>
                                    </small>
                                    
                        </form>
                        <br><br><br>
                    </div>
                   </div>
                   </div>
                  </div>
          
        <script>
            
            $(document).ready(function(){
                
            	if(navigator.userAgent.indexOf('MSIE')>-1)
             	   $("input.form-control").ezpz_hint();
                
                if ($(window).width() < 768) {
                    $('#signupform input').tooltipster({
                        trigger: 'custom',
                        onlyOne: false,
                        fixedWidth:'300px',
                        position: 'top-right',
                        theme:'form-validate-theme',
                        animation:'grow'
                    });
                }  else {
                    $('#signupform input').tooltipster({
                        trigger: 'custom',
                        onlyOne: false,
                        fixedWidth:'300px',
                        position: 'right',
                        theme:'form-validate-theme',
                        animation:'grow'
                    });
                } 
                
                 $.validator.addMethod("beeid", function(value, element) {
                    return this.optional(element) || /^\w+$/i.test(value);
                });

                var validator = $('#signupform').validate({

invalidHandler:function(){
		$('#signupbtn').html(props.su_plz_wait_msg);
		$('#signupbtn').attr('disabled','disabled');
},

                    
                    errorPlacement: function (error, element) {
                    	 $('#signupbtn').html(props.su_signup_lbll);
                 		$('#signupbtn').removeAttr('disabled');
                 		$('#errors').hide();
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
                    rules: {
                        'userData.email':{
                            required:true,
                            email:true
                        },
                        'userData.password': {
                        	minlength:4,
                            required:true
                        },
                        'userData.beeId': {
                            required: true,
                            minlength:4,
                            maxlength:20,
                            beeid:true,
                        }
                    },
                    messages: {
                    	'userData.beeId': {
                            required:props.la_choose_beeid_lbl,
                            beeid:props.la_spaces_not_lbl
                        },
                        'userData.email':{
                            required:props.la_enter_email_error_lbl,
                            email:props.la_enter_valid_email_lbl
                        },
                        'userData.password':{
                        	required:props.la_choose_pwd,
                        	minlength:props.la_enter_valid_pwd
                                	}
                    },
                    submitHandler: function (form) {
                    	$('#signupbtn').attr('disabled','disabled');
                      	 form.submit();
                    }

                    
                });
                 /* $('#signupbtn').click(function(){
            		$('#signupbtn').attr('disabled','disabled');
               	 $('#signupbtn').html('Please Wait...');
                	});  */
            });
        </script>
   
