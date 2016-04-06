
<style>
  #maincontent {
  padding: 0px;
  }
  
 #container a {
 color: #0C50A1;
 } 
  
  .serialnumber {
color: #C6C6C6;
font-family: Arial,Helvetica,sans-serif;
font-size: 23px;
font-weight: bold;
line-height: 22px;
width:40px;
padding-bottom:15px;
}


</style>
<%@taglib uri="/struts-tags" prefix="s" %>
    <%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
    
    
<div class="row" style="margin-left:-30px;">
<div class="col-md-12"><span class="text-muted"><h1> Eventbee For Common Good - 25% Non-Profit Discount</h1></span></div>     
</div>


<div class="row" style="margin-left:-28px;">
<div class="col-md-12">
    
    
   <table  cellpadding="0" cellspacing="0" align="left"  valign="top">
	  	   <tr> 
              <td valign="top" >            
                </td></tr>
                <tr><td style="padding-left:8px">
                <p>
                We are offering 25% discount on our PRO, ADVANCED and BUSINESS ticketing service fees to all non-profit events dedicated to the common good in 2016</p>
                </td></tr>
                <tr><td style="padding-left:8px">
                <p>  <span class="roundedboxheader">Non-profit pricing:</span></p>
                </td></tr>
                <tr><td style="padding-left:8px"><p>
                BASIC  Ticketing – $1 per ticket, regardless of ticket price and currency</p>
			<p>PRO Ticketing – $1.15 per ticket, regardless of ticket price and currency (Regular price $1.50)</p>
				<p>ADVANCED Ticketing – $1.50 per ticket, regardless of ticket price and currency (Regular price $2)</p>
				<p>BUSINESS Ticketing – $2.25 per ticket, regardless of ticket price and currency (Regular price $3)</p>
				 </td></tr>
                <tr><td >
                <p> <span class="roundedboxheader">&nbsp; Follow these simple steps to claim your non-profit discount:</span><br><br>
               <span class="serialnumber">1.</span>  Sign up free account and create your event at <a href="http://www.eventbee.com">http://www.eventbee.com</a><br><br>
                <span class="serialnumber">2.</span>  Fan us on Facebook and follow us on Twitter!<br><br>
                &nbsp;&nbsp;&nbsp;<a href="http://www.facebook.com/eventbeeinc" target="new"><img src="/main/images/icon_small_facebook.png"/> Fan us on Facebook </a><br/>
				 &nbsp;&nbsp;&nbsp;<a href="http://twitter.com/eventbee" target="new" style="background-image: url(/main/images/icon_small_twitter.png);background-repeat: no-repeat;padding-left: 19px;height:28px;display:inline-block;">Follow us on Twitter  </a><br/><br/>
                <span class="serialnumber">3.</span>  Copy and paste the following code on your website: <br><br>
                 &nbsp;&nbsp;&nbsp;&nbsp;<s:textarea id="textar" readonly="true" cols="75" rows="5"></s:textarea><br><br>
                <span class="serialnumber">4.</span>  <a href="javascript:;"  id="cus">Contact us </a>with your event URL and website (where you pasted our code) for discount approval.


                
              </p>
                           
				</td></tr>
		          
				   </table>
				   
				   </div></div>
		       <script>
		       call();
		       function call(){
		       document.getElementById('textar').value='<a href="http://www.eventbee.com" target="new"><img src="http://www.eventbee.com/home/images/poweredby.jpg"/></a><br><br><font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 10px;font-weight: lighter;color: #666666;">Online Ticketing Powered by Eventbee</font>';
		       }
			   
			   $(function(){
		       $('#cus').click(function() {
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



       
		  
