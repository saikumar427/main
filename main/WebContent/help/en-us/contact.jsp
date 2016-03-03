<style>
  #maincontent {
  padding: 0px;
  }
  #container a {
 color: #0C50A1;
 }
 
</style>

 
     


 <div class="row" style="margin-left:-30px;">
                    <div class="col-md-12">
                       <span class="text-muted"> <h1>Contact</h1></span>
                           
			
<h1><small>Address</small></h1>
550 Battery Street, #1619<br/>
San Francisco, CA 94111
</div>
</div>


<div class="row" style="margin-left:-28px;">
<div class="col-md-12">     
			
<h1><small>Email</small></h1>
<a href="javascript:;" id="emailus">
Click here</a>&nbsp;to email us!

            
<div style="height:25px;"></div>          
	      
</div>
</div>

   
<script>
$(function(){
$('#emailus').click(function() {
$('.modal-title').html('Contact Eventbee');
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",'/main/user/homepagesupportemail.jsp');
$('iframe#popup').css("height","440px"); 
});
$('#myModal').modal('show');
});	
$('#myModal').on('hide.bs.modal', function () {
$('iframe#popup').attr("src",'');
$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
});
});	
</script>