<%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
    <head>
        <title>Bootstrap</title>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
     <style>
            .btn-default{background-color: #D1D0CE}
			 
     </style>
    </head>
    <body>
    <div class="container">
    <div class="row">
    <div id="errormsg" class="alert alert-danger" style="display:none;width: 90%;padding-left: 40px;margin-left: 27px;margin-bottom: 3px;" ></div>
        <s:form class="form-horizontal" name="createtrackingpartnerform"
	action="CreateTrackingPartner!save" id="createtrackingpartnerform"
	method="get">
        <input type="hidden" name="mgrId" value="<s:property value="%{mgrId}"/>"/>
        
        <div class="col-md-1"></div>
        <div class="col-md-11">
  <div class="form-group">
  <label for="inputPassword3" class="col-xs-3">Name</label>
    <div class="col-xs-9">
      <input type="text" class="form-control" id="trackpartnercode" name="trackingPartnerData.trackname" placeholder="Name">
    </div>
  </div>
  <br/><br/>
  <div class="form-group"> <label for="inputPassword3" class="col-xs-3 ">URL Format</label>
    <div class="col-xs-9">
   
      <div id="trackcode">http://[Event Short URL]/t/<span id="generatedcode"></span></div>
    </div>
  </div>
  
   
  </div>
  
</s:form>
<br/>
<div class="form-group">
<div class="col-xs-5"></div>
                            <div class="col-xs-7">
                                 <button id="trackcodesubmit" class="btn btn-primary">Submit</button>
                           </div>
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
	$('#trackpartnercode').keyup(function(){
	$('#generatedcode').html($('#trackpartnercode').val());
//alert("hi:"+$('#trackpartnercode').val());
		});
$('#trackcodesubmit').click(function(){
	 $('#trackcodesubmit').attr('disabled','disabled');
	 $.ajax( {
   	   type: "GET",
   	   url: 'CreateTrackingPartner!save',
   	   data: $("#createtrackingpartnerform").serialize(),
   	   success: function( result ) {
   	   	   if(result.indexOf('success')>-1){
   	   		if(!parent.isValidActionMessage(result)) return;
   	   	   	   parent.closediv();
   	   	   }else{
   	   	 $('#trackcodesubmit').removeAttr('disabled');
   			$('#errormsg').show();
       	   $('#errormsg').html(result);
   	   	   }
   	   }});
});
	</script>
</html>