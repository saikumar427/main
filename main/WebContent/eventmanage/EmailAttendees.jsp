<%@taglib uri="/struts-tags" prefix="s"%>

 <link href="/main/bootstrap/js/google-code-prettify/prettify.css" rel="stylesheet">    
		<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
		<script src="/main/bootstrap/js/jquery.hotkeys.js"></script>  
    <script src="/main/bootstrap/js/google-code-prettify/prettify.js"></script>		
    <script src="/main/bootstrap/js/bootstrap-wysiwyg.js"></script>   
    <script src="/main/colpick-jQuery-Color-Picker-master/js/colpick.js" type="text/javascript"></script>
	<link rel="stylesheet" href="/main/colpick-jQuery-Color-Picker-master/css/colpick.css" type="text/css"/>
    <style>
    .desc_css{    
    		    -moz-border-bottom-colors: none;
			    -moz-border-left-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-top-colors: none;
			    background-color: #f5f5f5;
			    background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
			    background-repeat: repeat-x;
			    border-color: #cccccc #cccccc #b3b3b3;
			    border-image: none;
			    border-radius: 4px;
			    border-style: solid;
			    border-width: 1px;
			    box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05);
			    color: #333333;
			    cursor: pointer;
			    display: inline-block;
			    font-size: 14px;
			    line-height: 20px;
			    margin-bottom: 0;
			    padding: 6px 12px;
			    text-align: center;
			    text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
			    vertical-align: middle;    
    }
    
    #editor {
    background-color: white;
    border: 1px solid rgb(204, 204, 204);
    border-collapse: separate;
    #border-radius: 3px;
    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.075) inset;
    box-sizing: content-box;
    height: 250px;
    max-height: 250px;
    outline: medium none;
    overflow: scroll;
    padding: 4px;  
 
}


.btn-info {
    background-color: #428bca;
    background-image: linear-gradient(to bottom, #428bca, #428bca);
    background-repeat: repeat-x;
    #border-color: #428bca;
    color: #ffffff;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
}


.color-desc{
	 -moz-border-bottom-colors: none;
			    -moz-border-left-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-top-colors: none;
			    background-color: #f5f5f5;
			    background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
			    background-repeat: repeat-x;
			    border-color: #cccccc #cccccc #b3b3b3;
			    border-image: none;
			    border-radius: 4px;
			    border-style: solid;
			    border-width: 1px;
			    box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05);
			    color: #333333;
			    cursor: pointer;
			    display: inline-block;
			    font-size: 14px;
			    line-height: 20px;
			    margin-bottom: 0;
			    padding: 3px 0px;
			    text-align: center;
			    text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
			    vertical-align: middle;   
				width:37px;
}

    </style>


<style>
tr.tempRow:hover {
  background-color: #FFFFFF !important;
}
</style>
<script>
var json=${jsonData};
var eid='${eid}';
var powertype='${powertype}';
</script>
<script type="text/javascript" language="JavaScript" src="../js/emailattendees.js?id=2"></script>


            <br/>
            <div class="row sticky-out-button pull-right">
           		<button class="btn btn-primary" id="createemail" type="button"><s:text  name="ea.create.new.mail.btn"></s:text></button>
            </div>            
              
           
            <div class="white-box">
				
				 <div class="row ">	 	 
                   <div class="col-sm-6 col-md-6 col-xs-12 "  id="buttongroup" >
					 	<div class="center btn-group btn-toggle" data-toggle="buttons">
						
						<label id="scheduledlbl" class="optiontype btn btn-default no-radius">                             
							<input type="radio"   class="emailattendee" id="scheduledid"    />&nbsp; <s:text name="ea.scheduled.tab.lbl"></s:text> 
						</label>
						<label id="draftslbl" class="optiontype btn btn-active no-radius">
							<input type="radio"   class="emailattendee" id="draftsid"   />&nbsp;<s:text name="ea.drafts.tab.lbl"></s:text>
						</label>
						<label id="sentlbl" class="optiontype btn btn-active no-radius">
							<input type="radio"  class="emailattendee" id="sentid"  />&nbsp;<s:text name="ea.sent.tab.lbl"></s:text>
						</label>
						
						</div>
					</div> 
                 </div>    
                 <br>
                           
                                <div  id="tab1" style="display:block;">
                                    <table class="table table-hover " id='tabl1' >
                                        <thead style="display:none;" id="head1"></thead>
                                        <tbody id="tab1body"></tbody>
                                    </table>
                                 </div>
                                
                                <div  id="tab2" style="display:none;">
                                    <table class="table table-hover " id='tabl2' >
                                          <thead style="display:none;" id="head2"></thead>
                                          <tbody id="tab2body"></tbody>                                      	
                                    </table>
                                
                                </div>
                                
                                <div  id="tab3" style="display:none;">
                                   <table class="table table-hover " id='tabl3' >
                                       <thead style="display:none;" id="head3"></thead>
                                       <tbody id="tab3body"></tbody>                                           
                                    </table>
                                    
                                </div>
                               
                            
                     <!-- <div class="col-md-12 col-xs-12 text-center"> -->
                     <div id="forLoad"></div>
                     <!-- <div style="height:0px;"></div> -->
                     <div id="newEmailBox" ></div> 
                </div>      <!-- white box -->
                           
                
				<script type="text/javascript" charset="utf-8">
				
				$('input.emailattendee').change(function() {
					var selected=$(this).attr('id');
					activate(selected);		
				});
				
				function closePopup() {
					parent.$('#myModal').modal('hide');
				}
				
				
				
				function activate(selected)
				{
					if(selected=='scheduledid'){
						$('#scheduledlbl').addClass("btn-default").removeClass("btn-active");
				        $('#draftslbl').addClass('btn-active').removeClass("btn-default");
				        $('#sentlbl').addClass('btn-active').removeClass("btn-default");
				        $('#tab1').show();
				        $('#tab2').hide();
				        $('#tab3').hide();
				        
					}
					if(selected=='draftsid'){
						$('#scheduledlbl').addClass('btn-active').removeClass("btn-default");
				        $('#draftslbl').addClass("btn-default").removeClass("btn-active");
				        $('#sentlbl').addClass('btn-active').removeClass("btn-default");
				        $('#tab1').hide();
				        $('#tab2').show();
				        $('#tab3').hide();
					}
					
					if(selected=='sentid'){
						$('#scheduledlbl').addClass('btn-active').removeClass("btn-default");
				        $('#draftslbl').addClass('btn-active').removeClass("btn-default");
				        $('#sentlbl').addClass("btn-default").removeClass("btn-active");
				        $('#tab1').hide();
				        $('#tab2').hide();
				        $('#tab3').show();
					}
				}
				
           		 $(document).ready(function() 
           			{
               		 generateEmailAtteendeeTable(json);
		           	 $('#createemail').click(function()
		           			 {
		           		      document.getElementById("createemail").disabled = true;
		           		      showProcessing('forLoad');
		           		      createNewEmail();          		
		           	         });
                     });
				
				
           		
           		 
           		 
           		 
           		 function createNewEmail(){ 
				
           			var url = "EmailAttendees!getEmailAttendee?eid="+eid+"&powertype"+powertype;
           				$('#newEmailBox').html('');
           				
           			  /*   $('#newEmailBox').load(url,function(){
            				$('#newEmailBox').fadeIn(slideTime);
            			//$('#newEmailBox').css("display","block").animate({ scrollDown: 500 }, 'slow'); 
            				$('html, body').animate({ scrollTop: $("#newEmailBox").offset().top-scrollTo}, 1000);
            				hideProcessing();
           				}); */
           			    
           			    
           			 $.ajax({
							type:"GET",
							url: 'EmailAttendees!getEmailAttendee?eid='+eid+'&powertype'+powertype,
							success: function( result )
							{
								if(!isValidActionMessage(result)) 
									return;
								$('#newEmailBox').html(result);
								$('#newEmailBox').fadeIn(slideTime);
								$('html, body').animate({ scrollTop: $("#newEmailBox").offset().top-scrollTo}, 1000);
	            				hideProcessing();
							}
           			 });
        				
           		}
            </script>
                
                <style type="text/css" title="currentStyle">
        	           @import "/main/bootstrap/css/demo_table.css";
	                   .dataTables_filter input { 
	                   @import '.form-control'; 
	                   }
        		</style>
                
