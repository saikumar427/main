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
                <div class="col-xs-12">
                <s:hidden name="trackid" id="trackid"></s:hidden>
                  <div class="form-group">
    <label for="photoURL" class="col-xs-3 control-label">Photo URL</label>
    <div class="col-xs-9">
     <input type="text" class="form-control" name="photoURL" id="photoURL"
			value="<s:property value='%{photoURL}' />" size="30" />
    </div>
  </div><br/><br/>
  <div class="form-group">
  &nbsp;&nbsp;&nbsp;&nbsp;<s:checkbox name="statuscheck"
			id='statuscheck' fieldValue="true" />Apply to all events of this
		partner
  </div>
  <br/>
  <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="trackptnrphoto" class="btn btn-primary">Submit</button>
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
        $('#trackptnrphoto').click(function(){
        	var photoURL = document.getElementById('photoURL').value;
    		var trackid = document.getElementById('trackid').value;
    		var ischecked = document.getElementById('statuscheck').checked;
    		var url = "ManageTrackingPartner!changeTrackPartnerPhotoURL?trackid="
    				+ trackid + "&photoURL=" + photoURL + "&checked=" + ischecked;

		$.ajax({
			method:'POST',
			url:url,
			success: function( result ) {
				if(!parent.isValidActionMessage(result)) return;
				parent.location.reload();
					}
						});
        });
    });
    </script>
      </html>