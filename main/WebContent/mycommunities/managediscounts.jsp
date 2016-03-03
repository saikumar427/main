<%@taglib uri="/struts-tags" prefix="s" %>
<link rel="stylesheet" type="text/css" href="../build/menu/assets/skins/sam/menu.css" />
<script type="text/javascript" src="../build/menu/menu-min.js"></script>
<script>
YAHOO.namespace("ebee.event.discounts");
YAHOO.namespace("ebee.discountdetails");

function addDiscount(){
	var groupId='${groupId}';
	var url='DiscountDetails?groupId='+groupId;	
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getDiscountsSuccess,failure:getFailure});
	  	
}
function editDiscount(funcevent,event,jsonObj){
	var jsonData = eval(jsonObj);
	var groupId = jsonData.groupId;
	var id=jsonData.id;
	
	var url='DiscountDetails!edit?groupId='+groupId+'&id='+id;
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getDiscountsSuccess,failure:getFailure});
	
}
var getDiscountsSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Discount Details", save, handleCancel);
	changeType();	
}
var getFailure = function(){
	alert("error");
	}

var save=function(){	
	$('discountform').request({
	    onFailure: function() {alert("in failure"); $('errormsg').update('Failed to get results') },
	    onSuccess: function(t) {
	        var result=t.responseText;	
	        if(result.indexOf("success")>-1){		       
	        		
	        	window.location.reload();		        	
	        }
	        else
	        {
            $('discounterrormessages').update(result);
	        }
	    }
	});
}
</script>
<script type="text/javascript" src="ManageDiscounts!populateDiscountsDetails?groupId=${groupId}"></script>
<div class="box" align="left">
<div class="boxheader">Discount Details</div>
<div class="boxcontent">
<table width="100%">
<tr><td>
<div id="event_discountdetails_dt"></div>
</td></tr>
</table>

</div>
<div class="boxfooter">
<input type="button" name="Add Discount Code" value="Add Discount Code" id="addDiscountbtn">
</div>
</div>
<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
</div>
<script>

/*Discount Details YUI Data Table Start*/

var dt_table_dd = instantiateDiscountDetailsTable(YAHOO.ebee.event.discounts.Data.DiscountDetails,"event_discountdetails_dt")

function instantiateDiscountDetailsTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["discountName","discountType","discountId"]
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("discountId");
         elLiner.innerHTML = "<div id='evtDiscountId_"+ qid +"'></div>";                   
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var myColumnDefs = [
            {key:"discountName", label:"Name"},
            {key:"discountType", label:"Type"},
            {key:"Manage", label:"",formatter:"mydisplay"}            
        ];
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}

YAHOO.ebee.discountdetails.init = function () {
	for(var i=0;i<YAHOO.ebee.event.discounts.Data.DiscountDetails.length;i++){
		var jsonobj = YAHOO.ebee.event.discounts.Data.DiscountDetails[i];
		var divid = "evtDiscountId_"+jsonobj.discountId;	
			var DiscountDetails_ManageMenu = [				
				{ text: "Edit", value: 1, onclick: { fn: editDiscount ,obj: {"groupId":"${groupId}","id":jsonobj.discountId } } },
				{ text: "Delete", url: "ManageDiscounts!delete?groupId=${groupId}&id="+jsonobj.discountId}
			];
			var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: DiscountDetails_ManageMenu, container: divid });
	}
}();
var btn = new YAHOO.widget.Button("addDiscountbtn", { onclick: {  fn: addDiscount } });
//var addDiscountlnk = new YAHOO.widget.Button("addDiscountlnk",  {type: "link"});
function changeType(){
var selectedtype='';
options=document.forms['discountform'].elements['discountData.discountType'];
for(i=0;i<options.length;i++){
opt=options[i];
if(opt.checked) selectedtype=opt.value;
}
if(selectedtype=='Absolute' || selectedtype=='ABSOLUTE'){
document.getElementById('discountprefixlabel').style.display='inline';
document.getElementById('discountsufixlabel').style.display='none';
}else{
document.getElementById('discountprefixlabel').style.display='none';
document.getElementById('discountsufixlabel').style.display='inline';
}
if(document.getElementById("nolimit").checked)
document.getElementById('limitValue').value="";
}
function backToList(){
window.location.href='${discountsURL}';
}
function changeLimitType(){
idx=document.getElementById('limitselector').selectedIndex;
if(idx==2){
document.getElementById('limitvaluespan').style.display='inline';
}else{
document.getElementById('limitValue').value="";
document.getElementById('limitvaluespan').style.display='none';
}
}
</script>