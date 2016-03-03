<%@taglib uri="/struts-tags" prefix="s"%>
<html>

<head>
<style>
body 
{
margin-top: 0px;
}
</style>
  <script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.3.0/css/font-awesome.css" /> 
</head>


<body>
<s:form theme="simple" id="EventsPageTheme" name="EventsPageTheme" action="CustomizeEventsPageTheme!saveTheme" method="post">
<s:hidden name="userThemes.selectedTheme"></s:hidden>





		<div class="container">
            <div class="row">
                <div class="col-xs-6">
                <s:text name="cept.html.header.lbl"></s:text>
                <s:textarea cssClass="form-control" name="attribMap['HTML']" theme="simple" rows="15" cols="50"/>
                </div>
                <div class="col-xs-6">
                <s:text name="cept.css.header.lbl"></s:text>
                <s:textarea cssClass="form-control" name="attribMap['CSS']" theme="simple" rows="15" cols="50"/>
                </div>
           </div>
         </div>

</s:form>
 						<div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="evtgroupbtn" class="btn btn-primary"><s:text name="global.submit.btn.lbl"></s:text></button>
                                <button  class="btn btn-active" id="closePopup" onclick="parent.closePopup();"><s:text name="global.cancel.btn.lbl"></s:text></button>
                            </div>
                        </div>
</body>


		
    <script>
    $(document).ready(function() {
        $('#evtgroupbtn').click(function(){
    		var url = "CustomizeEventsPageTheme!saveTheme";
		$.ajax({
			method:'POST',
			url:url,
			data:$('#EventsPageTheme').serialize(),
			success: function( result ) {
				parent.$('#myModal').modal('hide');
				
					}
						});
        });
    });
    
    
    $('#closePopup').click(function(){
    	 parent.$('#myModal').modal('hide'); 
});
    
    </script>
