<%@taglib uri="/struts-tags" prefix="s"%>
    
    <style>
        .form-validate-theme {
            border-radius: 5px; 
            border: 2px solid #ff4747;
            background: #fff4f4;
            color: #000;
        }
        .form-validate-theme .tooltipster-content {
            width:100%;
            font-size: 14px;
            line-height: 16px;
            padding: 8px 10px;
        }
        .alert-danger{
        padding-left: 40px;
        width:369px;
        }
      
    </style>
        
        <!-- responsive navbar
        ===============================-->
        
        
                 <div class="row" style="background-color: white;">
<div class="col-md-3"></div>
<div class="col-md-6">     
<div class="col-md-2"></div>
<div class="col-md-8">       
 <div style="text-align: center"><h1><span class=" text-muted"> <s:text name="ac.user.name.lbl"/> </span></h1> </div>     

<s:if test="%{errorsExists==true}">
<div class="error" style="margin-left: -15px;">
<s:fielderror theme="simple"  cssClass="alert alert-danger"/>
</div>
</s:if>
                        <form action="signup!activateProcess" class="form-horizontal" id="activatemgrform" name="activatemgrform" method="post">
            <s:hidden name="token"></s:hidden>
                                 <div style="height:4px;"></div>
                                    <s:textfield id="fname" name="userData.firstName" type="text" placeholder="%{getText('ac.place.hldr.fname.lbl')}" cssClass="form-control" autofocus="autofocus"/>
                                   <%--  <s:textfield id="beeid" name="userData.beeId"  type="text" placeholder="Choose a Bee ID" cssClass="form-control"/> --%>
                                   <div style="height:15px;"></div>
                                  <s:textfield id="lname" name="userData.lastName" type="text" placeholder="%{getText('ac.place.hldr.lname.lbl')}" cssClass="form-control" />
                            <div style="height:15px;"></div>
                                    <s:textfield id="phone" name="userData.phone"  placeholder="%{getText('ac.place.hldr.phno.lbl')}" cssClass="form-control"/>
 <div style="height:17px;"></div>
                                    <s:textfield id="twitter" name="userData.twitterpage"  placeholder="%{getText('ac.place.hldr.twtr.lbl')}" cssClass="form-control"/>
                                  <div style="height:15px;"></div>
                                    <s:textfield id="facebook" name="userData.fbfanpage" type="text" placeholder="%{getText('ac.place.hldr.facbuk.lbl')}" cssClass="form-control"/>
                             <div style="height:15px;"></div>
                                    <s:textfield id="promocode" name="userData.referBy"  placeholder="%{getText('ac.place.hldr.rfrd.lbl')}" cssClass="form-control"/>
                            
                             <div style="height:15px;"></div>
                                    <s:textfield id="promocode" name="userData.promotionCode"  placeholder="%{getText('ac.place.hldr.prmcode.lbl')}" cssClass="form-control"/>
                             <div style="height:15px;"></div>
                                    <button type="submit" id="activatebtn" class="btn btn-block btn-primary pull-right" data-style="expand-right"data-size="l" style="height:36px;"> <s:text name="ac.user.name.lbl"/>  </button>
                        </form>
                        </div>
                         <div class="col-md-2"></div>
                        <div class="col-md-3"></div>
                        <br><br><br>
                    </div>
                    </div>
                
        <script>
            
            $(document).ready(function(){
                
            	if(navigator.userAgent.indexOf('MSIE')>-1)
             	   $("input.form-control").ezpz_hint();
                if ($(window).width() < 768) {
                    $('#activatemgrform input').tooltipster({
                        trigger: 'custom',
                        onlyOne: false,
                        fixedWidth:'300px',
                        position: 'top-right',
                        theme:'form-validate-theme',
                        animation:'grow'
                    });
                }  else {
                    $('#activatemgrform input').tooltipster({
                        trigger: 'custom',
                        onlyOne: false,
                        fixedWidth:'300px',
                        position: 'right',
                        theme:'form-validate-theme',
                        animation:'grow'
                    });
                } 
                
                 

                var validator = $('#activatemgrform').validate({

invalidHandler:function(){
		//$('#activatebtn').html('Please Wait...');
		$('#activatebtn').attr('disabled','disabled');
},

                    
                    errorPlacement: function (error, element) {
                    	$('#activatebtn').html(props.acc_btn_lbl);
                 		$('#activatebtn').removeAttr('disabled');
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
                        'userData.firstName':{
                            required:true
                        },
                        'userData.lastName': {
                        	required:true
                        },
                        'userData.phone': {
                            required:true
                         
                        }
                    },
                    messages: {
                    	'userData.firstName': {
                            required:props.act_usr_entr_frtnme_lbl
                        },
                        'userData.lastName':{
                            required:props.act_usr_ltname_lbl
                        },
                        'userData.phone':{
                        	required:props.act_usr_phno_lbl,
                                	},
                    },
                    submitHandler: function (form) {
                      	 form.submit();
                    }

                    
                });
                 /* $('#signupbtn').click(function(){
            		$('#signupbtn').attr('disabled','disabled');
               	 $('#signupbtn').html('Please Wait...');
                	});  */
            });
        </script>
   
