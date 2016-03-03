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
       <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet"/>
    </head>
      <body>
       <div id="customurlerrormessages" class="alert alert-danger" style="display:none;width: 450px;padding-left: 40px;margin-left: 27px;"></div>
       <br/>
       <div class="container">
            <div class="row">
                <div class="col-xs-12">
               <div class="col-xs-1">http://</div>
              <div class="col-xs-7 pull-left"><input Type="text" class="form-control" id="shorturl" name="shorturl" value="<s:text name="eventGroupData.customURL"></s:text>"/></div>
              <div class="col-xs-3 pull-left" >.eventbee.com</div>
<s:form name="customURLform" id="customURLform" method="post">
<s:hidden name="gid" id="gid"></s:hidden>
<s:hidden name="mgrId" id="mgrid"></s:hidden>	
<s:hidden name="eventGroupData.customURL" id="customURL"></s:hidden>	
<p/>
</s:form> </div>
                </div><br/><br/>
                <div class="row">
                
                <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="customurl" class="btn btn-primary">Submit</button>
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
        $('#customurl').click(function(){
        	var userid=document.getElementById('mgrid').value;
        	var userurl=document.getElementById('customURL').value;	
        	var newurl=document.getElementById('shorturl').value;
        	var eid=document.getElementById('gid').value;
        	var url="ManageGroup!setCustomURL?gid="+eid+"&mgrId="+userid+"&userurl="+userurl+"&customURL="+newurl;

    		$.ajax({
			method:'POST',
			url:url,
			success: function( result ) {
				 if(result.indexOf("URL Exists")>-1){
					 parent.$('iframe#popup').css("height","220px"); 
					$("#customurlerrormessages").show();
					$("#customurlerrormessages").html('This URL is not available, please enter new URL');
				}
				else if(result.indexOf("Invalid")>-1){
					parent.$('iframe#popup').css("height","220px");				
					$("#customurlerrormessages").show();
					$("#customurlerrormessages").html('Enter Valid Text');
				}
				else if(result.indexOf("spacesInUrl")>-1){
					parent.$('iframe#popup').css("height","220px");
					$("customurlerrormessages").show();
					$("customurlerrormessages").html('Use alphanumeric characters only');
				}else{
					parent.window.location.reload(true);			
			}
					}
						});
        });
    });
    </script>
      </html>