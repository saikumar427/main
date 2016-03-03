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
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
    </head>
      <body>
       <div class="container">
            <div class="row">
                <div class="col-xs-12">
                <form name="myothereventsform" action="ManageTrackingPartner!insertMyOtherEvents" id="myothereventsform" method="post">
<s:hidden name="trackid"></s:hidden>
<s:hidden name="eid"></s:hidden>
<div style="text-align: right">
<a href="javascript:;" id="checkall"><s:text name="global.select.all.lbl"/></a>&nbsp;<a href="javascript:;" id="uncheckall"><s:text name="global.clear.all.lbl"/></a><br/>
</div>
<div style="overflow:auto;height:200px;border:0px solid #336699;padding-left:8px">
<br/>
<s:set name="size1"  value="eventsList.size()" ></s:set>
<s:if test="%{#size1==0}">
<s:text name="mtp.noevents.to.add.lbl"/>
</s:if>
<s:else>
<s:iterator value="%{eventsList}" var="myothereventslist" >
<s:checkbox name="myOtherEventsList" id="myOtherEventsList" fieldValue="%{eid}" value="true" theme="simple" cssClass="events"/>&nbsp;${eventName}
<div style="height:5px"></div>
</s:iterator>
</s:else>
</div>
</form>
 <!--  <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="addevents" class="btn btn-primary">Submit</button>
                            </div>
                        </div> -->
                        
                        <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button id="addevents" class="btn btn-primary">
                                 <s:text name="global.submit.btn.lbl"/></button>
                            <button class="btn btn-cancel" onclick="closepopup();">
                                 <s:text name="global.cancel.btn.lbl"/></button>
                        </div>
                    </div>
                        
                        
                </div>
                </div>
                </div>
      
      </body>
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
      <script src="/main/bootstrap/js/icheck.min.js"></script>
    <script src="/main/bootstrap/js/bootstrap.js"></script>
      <script src="/main/js/jquery.ezpz_hint.js"></script>
    <script>
    $(document).ready(function() {

    	 $('.events').iCheck({  
        	 checkboxClass: 'icheckbox_square-grey', 
        	 radioClass: 'iradio_square-grey'});
        
        $('#addevents').click(function(){
		$.ajax({
			method:'POST',
			url:'ManageTrackingPartner!insertMyOtherEvents',
			data:$('#myothereventsform').serialize(),
			success: function( result ) {
				if(!parent.isValidActionMessage(result)) return;
				
				parent.rebildtracktable(result);
				parent.$('#myModal').modal('hide'); 
					}
						});
        });

$('#checkall').click(function(){
	$('.events').iCheck('check');
});

$('#uncheckall').click(function(){
	$('.events').iCheck('uncheck');	
});

        
    });

function closepopup(){
	parent.$('#myModal').modal('hide');  
}
    
    /* function checkAll(field) {
		for (var i = 0; i < field.length; i++)
			field[i].checked = true;
	}
	function uncheckAll(field) {
		for (var i = 0; i < field.length; i++)
			field[i].checked = false;
	} */
    </script>
      </html>