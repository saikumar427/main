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
       <div class="container">
            <div class="row">
                <div class="col-md-12">
                <s:hidden name="trackid" id="trackid"></s:hidden>
                                  Clicking on submit will delete Tracking Partner at global level. If this tracking partner is assigned to any events, partner will not be removed from those events.<br/><br/>
<s:checkbox name="statuscheck"
			id='statuscheck' fieldValue="true" />Suspend the account in all events of this
		partner

<br/><br/>
                        <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="trackptnrdelete" class="btn btn-primary">Submit</button>
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
        $('#trackptnrdelete').click(function(){
        	var trackid = document.getElementById('trackid').value;
    		var ischecked = document.getElementById('statuscheck').checked;
    		var url = "ManageTrackingPartner!deleteTrackPartnerSuccess?trackid="
    				+ trackid + "&checked=" + ischecked;
		$.ajax({
			method:'POST',
			url:url,
			success: function( result ) {
				if(!parent.isValidActionMessage(result)) return;
				parent.window.location.href='../mysettings/home';
					}
						});
        });
    });
    </script>
      </html>