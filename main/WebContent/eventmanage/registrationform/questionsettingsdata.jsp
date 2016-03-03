<!doctype html>
<%@taglib uri="/struts-tags" prefix="s" %>
<s:set value="%{gentickets.size()}" name="gensize"></s:set>
<s:set value="%{voltickets.size()}" name="volsize"></s:set>

<html>

<head>
    
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
 <link rel="stylesheet" type="text/css" href="/main/bootstrap/css/bootstrap-switch.min.css" />
 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/blue.css" />
    
    
    <style>
        .ticketname {
            cursor: pointer;
        }
    </style>
</head>

<body>
    <div class="container">

        <div class="row">
            <div class="col-md-12">
            
            <s:if test="%{gentickets.size>0}">
                <table class="table">
                    <thead>
                        <th>
                            <input type="checkbox" id="checkall" class="alltkts"/>
                        </th>
                        <th>Name</th>
                        <th>Required (
                            <a href="javascript:;" id="requiredAll">All</a>|
                            <a href="javascript:;" id="requiredNone">None</a>)</th>
                    </thead>
                    <tbody id="tickets">
                    
                    <s:iterator value="%{gentickets}" var="ticket" status="stat">
                    
                        <tr data-ticketid="<s:property value="key" />">
<td><s:checkbox cssClass="eachtkt" name="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}" theme="simple" data-type="ticket"/></td>
<td><label for="<s:property value="key" />" class="ticketname"><b>${value}</b></label></td>
<s:if test='%{attributeid=="-5"||attributeid=="-6"||attributeid=="-7"||attributeid=="-8"}'>
<td>
 <input type="checkbox" name="selticketsOption[<s:property value="#stat.index" />]" 
value="Y_<s:property value="key" />" <s:if test='%{reqtickets.contains(key)}'>checked=true</s:if> class="required" data-size="small" data-on-text="<i class='fa fa-check'></i>" data-off-text="<i class='fa fa-times'></i>" data-on-color="success" />

<input type="hidden" name="selticketsOption[<s:property value="#stat.index" />]" 
value="N_<s:property value="key" />"/>
</td>
</s:if>
</tr>
</s:iterator>
                    </tbody>
                </table>

                <hr>
                </s:if><s:else>
                
                Currently, there are no tickets to assign this question
                </s:else>

            </div>
        </div>
    </div>
</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/main/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootstrap-switch.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>

<script>
    $(document).ready(function() {

    	$('input.eachtkt').iCheck({  
    		checkboxClass: 'icheckbox_square-blue', 
    		radioClass: 'iradio_square-blue'});

    	$('input.alltkts').iCheck({  
    		checkboxClass: 'icheckbox_square-blue', 
    		radioClass: 'iradio_square-blue'});

        $('#cancel').click(function() {
            parent.closepopup();
        });

        $('#apply').click(function() {
          
            $.ajax({
            	type: "POST",
                url:'ManageRegistrationForm!saveQSettings',
                data: $("#addqform").serialize(), 
                success:function(result){
                	parent.$('#myModal').modal('hide');
                }
            });
            
        });

        $(".required").bootstrapSwitch();

        	$('#checkall').on('ifChecked', function(event){
        	  $('.eachtkt').iCheck('check');
        });

        	$('#checkall').on('ifUnchecked', function(event){
          	  $('.eachtkt').iCheck('uncheck');
          });
              
        
        $('#requiredAll').click(function() {
            $('#tickets .required').bootstrapSwitch('state', true);
        });

        $('#requiredNone').click(function() {
            $('#tickets .required').bootstrapSwitch('state', false);
        });

    });
</script>

</html>