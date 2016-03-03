<%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
<head>
<style>
.extraspace{
margin-left: 15px;
}

.options{
    margin-left: 7px;
}
.drop-image{
cursor:pointer;
display: none;
}




</style>
<link rel="stylesheet" type="text/css"
	href="/main/bootstrap/css/bootstrap-tokenfield.min.css" />
</head>
<body>
	<div class="disocunteditbox">
		<div class="row">
			<div class="col-md-12">
				
				<s:form cssClass="form-horizontal" name='discfrm' id="discountform"
					action="DiscountDetails!save" method="post">
					<s:hidden name="eid" id="evtid"></s:hidden>
					<s:hidden name="discountData.id" id="discId"></s:hidden>
					
					<s:hidden value="%{alltickets.size()}" id="ticketslength"></s:hidden>
					<s:hidden value="%{seltickets.size()}" id="selectedlength"/>
					
					<s:hidden name="discountData.expType" id="exptype"></s:hidden>
					<s:hidden name="discountData.limitType" id="limittype"></s:hidden>
					<s:hidden name="discountData.allLimitValue" id="alllimitval"></s:hidden>
					<s:hidden name="discountData.eachLimitValue" id="eachlimitval"></s:hidden>
					<s:hidden name="discountData.discountType" id="discountType"></s:hidden>
					<s:hidden name="discountData.discountVal" id="discountVal"></s:hidden>
					
					<s:hidden name="discountData.expDateTimeBeanObj.monthPart" id="monthpart"></s:hidden>
					<s:hidden name="discountData.expDateTimeBeanObj.ddPart" id="datepart"></s:hidden>
					<s:hidden name="discountData.expDateTimeBeanObj.yyPart" id="yearpart"></s:hidden>
					<s:hidden name="discountData.expDateTimeBeanObj.hhPart" id="hourspart"></s:hidden>
					<s:hidden name="discountData.expDateTimeBeanObj.mmPart" id="minspart"></s:hidden>
					<s:hidden name="discountData.expDateTimeBeanObj.ampm" id="ampmpart"></s:hidden>
					<s:hidden id="disctype" value="%{discountData.discountType}"></s:hidden>
					

					<div class="well well-no-margin">
					<div id="discounterrormessages" class="alert alert-danger" style="display: none;"></div>
						<div class="row">
							<div class="col-md-3 col-sm-3 extramargin">
								<s:textfield name="discountData.name" cssClass="form-control"
									id="name" placeholder="%{getText('disc.discount.name.ph')}"></s:textfield>
							</div>
							
							<%-- <div class="col-md-2 col-sm-2 extramargin">
								<div class="input-group" id="amountfield">
									<span class="input-group-addon">${currency}
									</span>
									<s:textfield cssClass="form-control" name="discountData.discountVal" id="amount"/>
								</div>
								<div class="input-group" id="percentagefield" style="display: none">
									<s:textfield cssClass="form-control" name="discountData.discountVal" id="percentage"/>
									<span class="input-group-addon">%</span>
								</div>
							</div> --%>
							
							<div class="col-md-2 extramargin">
									     
									        <div class="input-group">
									        <div class="input-group-btn" >
									            <div class="btn-group"> 
									                <button class="btn btn-select dropdown-toggle" type="button" data-toggle="dropdown">
									                    <span data-bind="label" id="currencyLabel">${currency}</span> <span class="caret"></span>
									                </button> 
									                <ul class="dropdown-menu" role="menu">
									                    <li ><a href="javascript:;" onclick="changeDiscountType('currency','${currency}');">${currency}</a></li>
									                    <li><a href="javascript:;"onclick="changeDiscountType('percentage','%');">%</a></li>
									                    
									                </ul>
									            </div>
									        </div>  
									        <input type="search"  id="searchBy" class="form-control" />
									      
									        </div>
									     
								</div>
							
							
							<div class="col-md-6 col-sm-6 extramargin">
									<s:textfield name="discountData.discountCodescsv" id="codes" 
										cssClass="form-control"
										placeholder="%{getText('disc.discount.code.ph')}"></s:textfield>
							</div>
							
							
							<%-- <div class="col-md-2 col-sm-2 extramargin">
								<span class="pull-right">
									<button type="button" class="btn btn-primary" id="savediscount" data-loading-text="Saving...">
										<b><s:text name="global.add.btn.lbl"/></b>
									</button>
									<button type="button" class="btn btn-active" id="closediscount">
										<i class="fa fa-times"></i>
									</button>
								</span>
							</div> --%>
							<div class="row options sm-font">
							<div class="col-md-12 col-xs-12 col-sm-12" data-div="specific-tickets">
							
							<label style="cursor: pointer;">
							<s:checkbox id="specific-check" name="" cssClass="dicount-options" data-val="specific-tickets" data-high="specific"> </s:checkbox>
							</label>
							<span id="specific" class="checkbox-space"><s:text name="global.applicable.tickets.lbl"/></span>
							
							
							<span class="glyphicon glyphicon-menu-right drop-image"></span>
							</div>
							</div>
							
							
							
							<div class="background-options" id="specific-tickets" style="display: none;">
							
							
							<div class="col-md-2 sm-font"><s:text name="global.selected.tickets.lbl"/></div>
							<div class="col-md-6 xsm-font">
							<s:iterator value="%{alltickets}" var="ticket" status="stat">   
											<%-- <s:if test="%{#stat.count==2}">hi this</s:if> --%>
											<label style="cursor: pointer;">	 <s:checkbox
														name="seltickets" fieldValue="%{key}"
														cssClass="tktsapplicable"
														data-ticketid="%{seltickets.contains(key)}"
														value="%{seltickets.contains(key)}" /><span class="checkbox-space">${value}</span></label>
												
												<%-- <s:if test="%{#stat.count%2==0}"> --%>
												<br />
												<div style="height: 6px;"></div>
												<%-- </s:if> --%>
										</s:iterator>
							</div>
							<div style="clear: both"></div>
							</div>
							
							
							<div class="row options  sm-font">
							<div class="col-md-12 col-xs-12 col-sm-12" data-div="expiration-dates">
							<label style="cursor: pointer;">
							<s:checkbox id="expiration-check" name="" cssClass="dicount-options" data-val="expiration-dates" data-high="expiration"> </s:checkbox>
							</label>
					    	<span id="expiration" class="checkbox-space">	<s:text name="disc.expiration.date"/></span>
							<span  class="glyphicon glyphicon-menu-right drop-image"></span>
							</div>
							</div>
							
							<div class="row options background-options" id="expiration-dates"  style="display: none;">
								<div class="col-md-2 bottom-gap sm-font"><s:text name="disc.choode.date.lbl"/></div>
								<div class="col-md-3 xsm-font">
								<input class="form-control" type="text" value="" size="52" id="enddate" style="margin-top: -6px;">
								<div class="sub-text"><s:text name="global.mm.dd.yyyy.lbl"/></div>
								</div>
								<div class="col-md-5" ></div>
							</div>
							
							
							
							<div class="row options sm-font">
							<div class="col-md-12 col-xs-12 col-sm-12" data-div="limit-count">
							<label style="cursor: pointer;">
							<s:checkbox id="limit-check" name="" cssClass="dicount-options" data-val="limit-count" data-high="limit"> </s:checkbox>
							</label>
						<span id="limit" class="checkbox-space"><s:text name="disc.limit.count.lbl"/></span>
							<span class="glyphicon glyphicon-menu-right drop-image "></span>
							</div>
							
							</div>
							
							<div class="row options background-options" id="limit-count" style="display: none;" >
								<div class="col-md-2 bottom-gap sm-font"><s:text name="dt.limit.count.lbl"/></div>
								<div class="col-md-2 bottom-gap xsm-font"><s:textfield
														 cssClass="form-control"
														id="limitone"></s:textfield></div>
														
														<div class="col-md-5 bottom-gap">
														<select id="limittypeselect" class="form-control">
														<option value="2"><s:text name="disc.for.all.disc.codes"/> </option>
														<option value="3"><s:text name="disc.per.dic.code"/> </option>
														
														</select>
														
														
														</div><div class="col-md-4"></div>
							</div>
							<br/>
					
							<div class="center">
							<button type="button" class="btn btn-primary" id="savediscount" data-loading-text="Saving...">
										<s:text name="global.add.btn.lbl"/>
									</button>
									<button type="button" class="btn btn-cancel" id="closediscount">
										 <i class="fa fa-times"></i>
									</button>
							</div>
				
						</div>
					</div>
				</s:form>
			</div>
		</div>

	</div>
</body>
<script src="/main/bootstrap/js/bootstrap-tokenfield.js"></script>
<script>
loadingPopup();
var currencyVal='${currency}';

    
        function getType() {
            var value = $("#discountform").find("input[name='discountData.discountType']:checked").val();
                if(value == 'ABSOLUTE') {
                $('#amount').tooltipster('show');
                $('#percentage').tooltipster('hide');
            } else {
                $('#amount').tooltipster('hide');
                $('#percentage').tooltipster('show');
            }
            return value;
        }
        
        function getLimit() {
            var value = $("#discountform").find("input[name='discountData.limitType']:checked").val();
            if(value == '1') {
                $('#limitall').tooltipster('hide');
                $('#limitone').tooltipster('hide');
            } else if(value == '2') {
                $('#limitall').tooltipster('show');
                $('#limitone').tooltipster('hide');
            } else if(value == '3') {
                $('#limitall').tooltipster('hide');
                $('#limitone').tooltipster('show');
            }
            return value;
        }
        
        $(document).ready(function() {

        	var val=$('#disctype').val();
        	
        	 if (val == 'ABSOLUTE') {
                 $('#percentagefield').hide();
                 $('#amountfield').show();
                 $('#percentage').prop("disabled", true );
                 $('#amount').prop( "disabled", false );
             } else {
                 $('#amountfield').hide();
                 $('#percentagefield').show();
                 $('#percentage').prop( "disabled", false );
                 $('#amount').prop( "disabled", true );
             }

        	$('#enddate').datetimepicker({
        	    format:'m/d/Y g:i A',
        	    formatTime:'g:i A'
        	});

        	var enddate='';
        	var month=$('#monthpart').val();
        	var date=$('#datepart').val();
        	var year=$('#yearpart').val();
        	var hour=$('#hourspart').val();
        	var mins=$('#minspart').val();
        	var ampm=$('#ampmpart').val();
        	enddate=month+'/'+date+'/'+year+' '+hour+':'+mins+' '+ampm;
        	$('#enddate').val(enddate);

        	$('input.discounttype').iCheck({  
        		checkboxClass: 'icheckbox_square-grey', 
        		radioClass: 'iradio_square-grey'});

    		$('input[name="discountData.expType"]').iCheck({  
        		checkboxClass: 'icheckbox_square-grey', 
        		radioClass: 'iradio_square-grey'});

        	$('input.limittype').iCheck({  
        		checkboxClass: 'icheckbox_square-grey', 
        		radioClass: 'iradio_square-grey'});  

           $('input.tktsapplicable').iCheck({  
            		checkboxClass: 'icheckbox_square-grey', 
            		radioClass: 'iradio_square-grey'}); 
            
            $('#cancel').click(function(){
                parent.closepopup();
            });
            
            $('#selectall').click(function(){
               /*  $('#discountform input[name=seltickets]').each(function() {
                    this.checked = true;
                }); */
		$('.tktsapplicable').iCheck('check');
                
            });
            
            $('#selectnone').click(function(){
              /*   $('#discountform input[name=seltickets]').each(function() {
                    this.checked = false;
                    // validation
                    validator.element('#discountform input[name=seltickets]');
                }); */
            	$('.tktsapplicable').iCheck('uncheck');
            });
            
         
            // add this name for validation to work
            $('#codes-tokenfield').attr('name','codes');
            
            /*  form validation
            =======================*/
            $('#discountform input').tooltipster({
                trigger: 'custom',
                onlyOne: false,
                fixedWidth:'300px',
                position: 'top-right',
                theme:'form-validate-theme',
                animation:'grow'
            });

            $('input.discounttype').on('ifChecked', function(event){   
                var val = $("input[name='discountData.discountType']:checked").val();
                if (val == 'ABSOLUTE') {
                    $('#percentagefield').hide();
                    $('#amountfield').show();
                    $('#percentage').prop("disabled", true );
                    $('#amount').prop( "disabled", false );
                } else {
                    $('#amountfield').hide();
                    $('#percentagefield').show();
                    $('#percentage').prop( "disabled", false );
                    $('#amount').prop( "disabled", true );
                }
            });
            
           
            
            $('.info').tooltipster({
            	theme: 'my-custom-theme',
            	maxWidth:'100px'
            });
            
            var getDiscountData = function(){
                var discount = {};
                discount.name = $('#name').val();
                var codes = [];
                $.each($('#codes').tokenfield('getTokens'),function(index,obj) {
                    codes.push(obj.value);
                })
                discount.codes = codes;
                
                discount.type = $('#discountform input[name="discountData.discountType"]:checked').val();
                if(discount.type == 'ABSOLUTE')
                    discount.value = $('#amount').val();
                else
                    discount.value = $('#percentage').val();
                $('#iddendis').val(discount.value);
                
                discount.limittype = $('#discountform input[name="discountData.limitType"]:checked').val();
                if(discount.limittype == '2')
                    discount.limitvalue = $('#limitall').val();
                else if(discount.limittype == '3')
                    discount.limitvalue = $('#limitone').val();
                
                var tickets = [];
                $('#discountform input[name=seltickets]').each(function() {
                    if($(this).is(':checked'))
                        tickets.push($(this).data('ticketid'));
                });
                
                discount.tickets = tickets;
                return discount;
            };
            /*  tokenfield plugin
            ===========================*/
            $('#codes')
                .on('tokenfield:preparetoken', function (e) {})
                .on('tokenfield:createtoken', function (e) {
                    var re = /^\w+$/
                    var valid = re.test(e.token.value);
                    if (!valid)
                        $(e.relatedTarget).addClass('invalid');
                })
                .on("tokenfield:edittoken",function(e){})
                .on("tokenfield:removetoken",function(e) {
                })
                .on('tokenfield:preventduplicate', function (e) {}).tokenfield();
                
            
            /* if( $("input[name='discountData.discountType']:checked").val()=='ABSOLUTE')
          	  $('#amount').val($('#iddendis').val());
            else
             {$('#percentage').val($('#iddendis').val());$('input[name="discountData.discountType"]:radio').trigger('change');} */





        	var divs=["specific-tickets","expiration-dates","limit-count"];
         	var bolddivs=["specific","expiration","limit"];
        	
    		$('#specific-check,#expiration-check,#limit-check').iCheck({  
        		checkboxClass: 'icheckbox_square-grey', 
        		radioClass: 'iradio_square-grey'});

    		 $('#specific-check,#expiration-check,#limit-check').on('ifChecked',function(){
                  /* $.each(divs,function(key,value){
                          $('#'+value).hide();
                         // $('#'+bolddivs[key]).removeClass('highlighted-text');
	                  }); */


                  $(".drop-image").each(function() {
                	 $(this).addClass('original').removeClass('down');
                	});
                  

                  $(this).closest("[data-div]").find('.drop-image').addClass('down').removeClass('original');
                  
                  $('#'+$(this).data('val')).slideDown();

                  var ids=$(this).data('high');

              //    $('#'+ids).addClass('highlighted-text');

                 if($(this).attr('id')=='expiration-check')
                     $('#exptype').val('Y');


                 if($(this).attr('id')=='limit-check')
                	 $('#limittype').val("2");
                     
                  
             });

    		 $('#specific-check,#expiration-check,#limit-check').on('ifUnchecked',function(){
    			 $('#'+$(this).data('val')).slideUp();

    			  $(this).closest("[data-div]").find('.drop-image').addClass('original').removeClass('down');
    			 
    			 
    			 var ids=$(this).data('high');

               //  $('#'+ids).removeClass('highlighted-text');
    			 
    			 if($(this).attr('id')=='expiration-check')
                     $('#exptype').val('N');

    			 if($(this).attr('id')=='specific-check')
    				 $('.tktsapplicable').iCheck('check');
                     //$('#exptype').val('N');


    			 if($(this).attr('id')=='limit-check')
        			 $('#limittype').val("1");
                 
             }); 


            $('.drop-image').on('click',function(){

                   
					var id=$(this).parent().find('.dicount-options').attr('id');
					var divid=$(this).parent().data('div');
					
				var highId=$('#'+id).data('high');


				/*  $.each(bolddivs,function(key,value){
                     $('#'+value).removeClass('highlighted-text');
                 }); */
				
				 $(".drop-image").each(function() {
                	 $(this).addClass('original').removeClass('down');
                	});

					if($('#'+divid).is(":visible")){
						$(this).addClass('original').removeClass('down');
						$('#'+divid).slideUp();
                       // $('#'+highId).removeClass('highlighted-text');
						}else{
						$(this).addClass('down').removeClass('original');
						$('#'+divid).slideDown();
						// $('#'+highId).addClass('highlighted-text');
						}
					 $.each(divs,function(key,value){
						 if(divid!=value){
                         $('#'+value).slideUp();
						 }
	                  });
					
					 $('#'+id).iCheck('check');
                }); 



            if($.trim($('#discId').val())!=''){

            	$('#searchBy').val($('#discountVal').val());

            if($('#discountType').val()=='ABSOLUTE')
            	changeDiscountType('currency',currencyVal);
            else
            	changeDiscountType('percentage','%');
                
            	

			if($('#exptype').val()=='N')
				 $('#expiration-check').iCheck('uncheck');
				else
			     $('#expiration-check').iCheck('check');
					

           	if($('#limittype').val()=='1')
           		$('#limit-check').iCheck('uncheck');
           	else if($('#limittype').val()=='2'){
           		$('#limit-check').iCheck('check');
           		$('#limittypeselect').val('2');
           		$('#limitone').val($('#alllimitval').val());
           	}else if($('#limittype').val()=='3'){
           		$('#limit-check').iCheck('check');
           		$('#limittypeselect').val('3');
           		$('#limitone').val($('#eachlimitval').val());
           	}

           	if(Number($('#selectedlength').val())==Number($('#ticketslength').val()))
				$('#specific-check').iCheck('uncheck');
				else
				$('#specific-check').iCheck('check');               	
            	
                }else{
                	 $('.tktsapplicable').iCheck('check');
                    }

            
        });

function changeDiscountType(type,symbol){

if(type=='currency')
	$('#discountType').val('ABSOLUTE');
else
	$('#discountType').val('PERCENTAGE');

	$('#currencyLabel').html(symbol);
	
}
hidePopup();
        
    </script>

</html>
