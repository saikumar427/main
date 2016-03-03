<!doctype html>
<html>
    <head>
        <title>Bootstrap</title>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css" />
        <link href="../assets/bootstrap/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
        
        <!-- tooltip css -->
        <link rel="stylesheet" type="text/css" href="../assets/tooltipster/tooltipster.css" />
        
        <!-- tokenfield-->
        <link rel="stylesheet" type="text/css" href="../assets/tokenfield/css/bootstrap-tokenfield.min.css" />
        
        <!-- iCheck -->
        <link rel="stylesheet" type="text/css" href="../assets/iCheck-master/skins/square/blue.css" />
        
        <style>
            select {
                border-radius: 5px;
                padding: 6px;
                outline: none;
                width:100%;
                border-color: #ccc
            }
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
        </style>
    
    </head>
    
    <body>
        <div class="container">
            
            <div class="row">
                <div class="col-md-12">
                    <h3>Add Discount</h3>
                    <hr/>
                    <form class="form-horizontal" id="discountform">
                        
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label"><h4>Discount name</h4></label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="name" name="name" autofocus />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="codes" class="col-sm-2 control-label">
                                <h4>
                                    Discount codes&nbsp;
                                    <i class="fa fa-info-circle info" title="You can also copy and paste comma separated discount codes directly"></i>
                                </h4>
                            </label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="codes" placeholder="Enter a discount code and hit return" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><h4>Discount type</h4></label>
                            <div class="col-sm-6">
                                &nbsp;&nbsp;&nbsp;
                                <label class="radio-inline">
                                    <input type="radio" name="type" value="amount" checked /> Amount
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="type" value="percentage" /> Percentage
                                </label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><h4>Discount value</h4></label>
                            <div class="col-sm-6">
                                <div class="input-group" id="amountfield">
                                    <span class="input-group-addon">$</span>
                                    <input type="text" class="form-control" id="amount" name="amount" />
                                </div>
                                <div class="input-group" id="percentagefield" style="display:none">
                                    <input type="text" class="form-control" id="percentage" name="percentage" />
                                    <span class="input-group-addon">%</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><h4>Total available</h4></label>
                            <div class="col-sm-6">
                                
                                <div class="input-group">
                                    &nbsp;&nbsp;&nbsp;
                                    <label class="radio-inline">
                                        <input type="radio" name="limit" value="nolimit" checked /> No limit
                                    </label>
                                </div>
                                <br>
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <label class="radio-inline">
                                            <input type="radio" name="limit" value="limitall" /> Limit count for all codes
                                        </label>
                                    </span>
                                    <input type="text" class="form-control" name="limitall" id="limitall" />
                                </div>
                                <br>
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <label class="radio-inline">
                                            <input type="radio" name="limit" value="limitone" /> Limit count per code
                                        </label>
                                    </span>
                                    <input type="text" class="form-control" name="limitone" id="limitone">
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="website" class="col-sm-2 control-label"><h4>Tickets applicable</h4></label>
                            <div class="col-sm-6">
                                <a href="javascript:;" id="all">Select all</a> | <a href="javascript:;" id="none">Clear all</a><br>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="tickets" data-ticketid="1" /> ticket 1
                                </label><br>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="tickets" data-ticketid="2" /> ticket 2
                                </label><br>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="tickets" data-ticketid="3" /> ticket 3
                                </label>
                            </div>
                        </div>
                        
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-6 pull-right">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-plus"></i> Add</button>
                                <button class="btn btn-danger" id="cancel"><i class="fa fa-times"></i> Cancel</button>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
            
        </div>
    </body>
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/tooltipster/jquery.tooltipster.js"></script>
    <script src="../assets/jquery.validate.min.js"></script>
    <script src="../assets/tokenfield/bootstrap-tokenfield.js"></script>
    <script>
        
        function getType() {
            var value = $("#discountform").find("input[name=type]:checked").val();
            if(value == 'amount') {
                $('#amount').tooltipster('show');
                $('#percentage').tooltipster('hide');
            } else {
                $('#amount').tooltipster('hide');
                $('#percentage').tooltipster('show');
            }
            return value;
        }
        
        function getLimit() {
            var value = $("#discountform").find("input[name=limit]:checked").val();
            if(value == 'nolimit') {
                $('#limitall').tooltipster('hide');
                $('#limitone').tooltipster('hide');
            } else if(value == 'limitall') {
                $('#limitall').tooltipster('show');
                $('#limitone').tooltipster('hide');
            } else if(value == 'limitone') {
                $('#limitall').tooltipster('hide');
                $('#limitone').tooltipster('show');
            }
            return value;
        }
        
        $(document).ready(function() {
            
            $('#cancel').click(function(){
                parent.closepopup();
            });
            
            $('#all').click(function(){
                $('#discountform input[name=tickets]').each(function() {
                    this.checked = true;
                });
            });
            
            $('#none').click(function(){
                $('#discountform input[name=tickets]').each(function() {
                    this.checked = false;
                    // validation
                    validator.element('#discountform input[name=tickets]')
                });
            });
            
            /*  tokenfield plugin
            ===========================*/
            $('#codes')
                .on('tokenfield:preparetoken', function (e) {})
                .on('tokenfield:createtoken', function (e) {
                    
                    var re = /^\w+$/
                    var valid = re.test(e.token.value)
                    if (!valid)
                        $(e.relatedTarget).addClass('invalid');
                        
                    // validation
                    validator.element('#codes-tokenfield')
                    
                })
                .on("tokenfield:edittoken",function(e){})
                .on("tokenfield:removetoken",function(e) {
                    
                    // validation
                    validator.element('#codes-tokenfield')
                })
                .on('tokenfield:preventduplicate', function (e) {})
                .tokenfield();
            
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
            
            var validator = $('#discountform').validate({
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
                rules: {
                    name: {required: true},
                    codes: {
                        codesRequired : true,
                        codesValid : true
                    },
                    amount:{
                        required: function(element) {
                            return (getType() == 'amount');
                        },
                        number:true,
                        min:0
                    },
                    percentage:{
                        required: function(element) {
                            return (getType() == 'percentage');
                        },
                        number:true,
                        min:0
                    },
                    limitall:{
                        required: function(element) {
                            return (getLimit() == 'limitall');
                        },
                        number:true,
                        min:0
                    },
                    limitone:{
                        required: function(element) {
                            return (getLimit() == 'limitone');
                        },
                        number:true,
                        min:0
                    },
                    tickets:{required:true}
                },
                messages: {
                    name: "Please enter discount name",
                    amount: {
                        required:"Please enter discount amount",
                        number:"Invalid discount amount",
                        min:"Amount cannot be negative"
                    },
                    percentage: {
                        required:"Please enter discount percentage",
                        number:"Invalid discount percentage",
                        min:"Discount percentage cannot be negative"
                    },
                    limitall:{
                        required:"Please enter limit count for all codes",
                        number:"Invalid limit value",
                        min:"Limit cannot be negative"
                    },
                    limitone:{
                        required:"Please enter limit count per code",
                        number:"Invalid limit value",
                        min:"Limit cannot be negative"
                    },
                    tickets:"Please select atleast one ticket"
                },
                submitHandler: function (form) {
                    
                    var discount = getDiscountData();
                    
                    /* perform ajax request here and get discount id from server */
                    discount.id = Math.floor((Math.random()*100)+1);
                    
                    parent.addDiscount(discount);
                    
                    return false;
                }
            });
            
            /*  custom rules for discount codes
            =====================================*/
            $.validator.addMethod("codesRequired", function(value, element) {
                
                var codes = $('#codes').tokenfield('getTokens');
                if(codes.length == 0)
                    return false;
                return true;
                
            }, "Please enter atleast one discount code");
            
            $.validator.addMethod("codesValid", function(value, element) {
                
                var codes = $('#codes').tokenfield('getTokens');
                var isValid = true;
                $.each(codes,function(index,obj){
                    var re = /^\w+$/
                    var valid = re.test(obj.value)
                    if(!valid)
                        isValid = false;
                });
                
                if (!isValid)
                    return false;
                return true;
                
            }, "Invalid discount codes are present");
            
            $('input[name=type]:radio').change(function(evt) {
                var val = $("input[name=type]:checked").val();
                if (val == 'amount') {
                    $('#percentagefield').hide();
                    $('#amountfield').show();
                                    
                    validator.element('#amount')
                } else {
                    $('#amountfield').hide();
                    $('#percentagefield').show();
                    
                    validator.element('#percentage')
                }
            });
            
            $('input[name=limit]:radio').change(function(evt) {
                var val = $("input[name=limit]:checked").val();
                if (val == 'nolimit') {
                    validator.element('#limitall,#limitone')
                } else if (val == 'limitall') {
                    validator.element('#limitall')
                } else if (val == 'limitone') {
                    validator.element('#limitone')
                }
            });
            
            $('.info').tooltip({
                placement:'top',
                container:'body'
            });
            
            var getDiscountData = function(){
                
                var discount = {};
                discount.name = $('#name').val();
                var codes = [];
                $.each($('#codes').tokenfield('getTokens'),function(index,obj) {
                    codes.push(obj.value);
                })
                discount.codes = codes;
                
                discount.type = $('#discountform input[name=type]:checked').val();
                if(discount.type == 'amount')
                    discount.value = $('#amount').val();
                else
                    discount.value = $('#percentage').val();
                
                discount.limittype = $('#discountform input[name=limit]:checked').val();
                if(discount.limittype == 'limitall')
                    discount.limitvalue = $('#limitall').val();
                else if(discount.limittype == 'limitone')
                    discount.limitvalue = $('#limitone').val();
                
                var tickets = [];
                $('#discountform input[name=tickets]').each(function() {
                    if($(this).is(':checked'))
                        tickets.push($(this).data('ticketid'))
                });
                
                discount.tickets = tickets;
                
                return discount;
            
            };
        
        });
    </script>

</html>