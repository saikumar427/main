<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<style>

 tr#edited:hover {
 background-color: #FFFFFF !important;
}

table {
    table-layout:fixed;
}
td{
    overflow:hidden;
    text-overflow: ellipsis;
}

/* table > thead > tr > td, table > tbody > tr > td{
    font-size: 12px !important;
}

.discountname{
font-size: 14px !important;
} */


</style>

<div class="row sticky-out-button pull-right">
	<div class="col-md-12"> 
		 <div class="pull-right">
			<ul class="dropdown-menu" role="menu" aria-labelledby="eventOptions"
				id="eventOptions">
			</ul> 

			<%-- <button style="margin-left: 15px" class="btn btn-primary"
				onclick="newEventClick()" ><span id="recurevent"> From All Dates </span>&nbsp; <span style="cursor: pointer;  margin-top: 3px;" id="toggling-menu" class="glyphicon glyphicon-menu-down"></span></button> --%>
			<button id="adddiscount" class="btn btn-primary" style="margin-left: 15px">+&nbsp;<s:text name="disc.add.btn.lbl"/></button>
		 </div> 
	 </div> 
	<div style="clear:both"></div>
</div>
<div id="notify-msg"></div>
			 <div> 
				<div class="panel-body no-button" >
				<div>
                    <div class="row">
                        <div class="col-md-12">      
                            <div id="discounts" style="display:block;"></div>               <!-- all discounts table -->
                         
                            <div style="height:35px;" id="discountsData_br"></div>
                         
                            <div id="discountbox"></div>  
                            <div id="forload" ></div>
                           <div id="dataDiv"  style="display:block;"></div>               <!-- discount settings -->
                        </div>
                        
                    </div>
                </div>
  				</div>   
  				
  				</div>
  				
            
            
    <script src="/main/js/eventmanage/discount.js"></script>
    <script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
      <script>
        window.resizeIframe = function() {
            var obj = document.getElementById('popup');
            obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
        }
    </script>
    
        <script type="text/javascript" src="ManageDiscounts!populateDiscountsDetails?eid=${eid}"></script>
        <script>
        var eid='${eid}';
        var jsonData='${jsonData}';

        
        $(document).ready(function(){  
        	 $('#adddiscount').click(function(){
            	// loadingPopup();
        		 openDiscount();
        		 
      		});
		prepareDiscountTable(discdata);
       });
        </script>
        
        <style type="text/css" title="currentStyle">
            /* @import "//www.eventbee.com/home/css/blockManage/demo_page.css"; */
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>
        
