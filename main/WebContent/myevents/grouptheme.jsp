<%@taglib uri="/struts-tags" prefix="s"%>
<html>

    <head>
<style>
body {
margin-top: 0px;
}
</style>
</head>
<body>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
    </head>
      <body>
      <s:form id="GroupTheme" name="GroupTheme" action="ManageGroup!saveGroupTheme" method="post">
      <s:hidden name="gid"></s:hidden>
       <div class="container">
            <div class="row">
                <div class="col-xs-6">
                HTML
                <s:textarea cssClass="form-control" name="themeContent['HTML']" theme="simple" rows="15" cols="50"/>
                </div>
                <div class="col-xs-6">
                CSS
                <s:textarea cssClass="form-control" name="themeContent['CSS']" theme="simple" rows="15" cols="50"/>
                </div>
                </div>
                </div>
                </s:form>
       <!-- <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="groupthemebtn" class="btn btn-primary">Submit</button>
                            </div>
                        </div> -->
                   <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="button" class="btn btn-primary" id="groupthemebtn">
                                 Submit</button>
                            <button class="btn btn-cancel" onclick="closepopup();">
                                 Cancel</button>
                        </div>
                    </div>     
                        
                        
                        
      </body>
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="/main/bootstrap/js/bootstrap.js"></script>
      <script src="/main/js/jquery.ezpz_hint.js"></script>
    <script>
    $(document).ready(function() {
        $('#groupthemebtn').click(function(){
    		var url = "ManageGroup!saveGroupTheme";
		$.ajax({
			method:'POST',
			url:url,
			data:$('#GroupTheme').serialize(),
			success: function( result ) {
				parent.$('#myModal').modal('hide'); 
					}
						});
        });
    });

function closepopup(){
	parent.$('#myModal').modal('hide');
}
    
    </script>
      </html>