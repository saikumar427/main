<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="/main/css/displaytag.css" />
<script>
	var json=${promotionsJson};
	var ntsenable='${ntsEnable}';
	var feeshowstatus='${feeshowstatus}';
	function displayYUIPromotionsTable(){
	var dt_table_dd=instantiatePromosTable(json.promotions,"promo");
}
function instantiatePromosTable(ds, cname){
var message='';
if(ntsenable=='Y' && json.promotions.length==0)
	message='&nbsp;';
if(ntsenable=='N'){
ds=[];
	message="<div align='right'><input type='button' value='Enable Network Ticket Selling' id='enablebtn'></div>";
	}	
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["eventid","fbuid","name","day",{key:"visits",parser:"number"},{key:"sales",parser:"number"},"createdat","visitstotal","salestotal"]
        }
    });
    var FBLink = function(elLiner, oRecord, oColumn, oData) {
    	var fbuid = oRecord.getData("fbuid");
    	var name = oRecord.getData("name");
    	var s="<table style='border:0px;'><tr><td width='50px' style='border:0px;'>"+"<input type='hidden' value='"+name+"'/><a href='http://www.facebook.com/profile.php?id="+fbuid+"' target='_blank'><img title='"+name+"' src='https://graph.facebook.com/"+fbuid+"/picture' /></a></td><td style='border:0px;width:5px'></td><td style='border:0px;'><a href='http://www.facebook.com/profile.php?id="+fbuid+"' target='_blank'>"+name+"</a></td></tr></table>";
         elLiner.innerHTML = s;
    };
    var whenformat = function(elLiner, oRecord, oColumn, oData) {
    	var day = oRecord.getData("day");
    	var html1=day+"&nbsp;<img src='/main/images/home/icon_facebook.png' width='15px' height='15px'>";
         elLiner.innerHTML = html1;
    };
    
    var visitfeeformat=function(elLiner, oRecord, oColumn, oData){
    	var disstr='';
        disstr="Total: "+oRecord.getData("visits");
    	if(oRecord.getData("visits")>0)
    	   disstr=disstr+"&nbsp;&nbsp;&nbsp;&nbsp;Fees: "+oRecord.getData("visitstotal");
    	else
    		disstr=disstr+"&nbsp;&nbsp;&nbsp;&nbsp;Fees: $0";
    	elLiner.innerHTML = disstr;
    }
    var salefeeformat=function(elLiner, oRecord, oColumn, oData){
    	var disstr='';
    	disstr="Total: "+oRecord.getData("sales");
    	if(oRecord.getData("sales")>0)
        	disstr=disstr+"&nbsp;&nbsp;&nbsp;&nbsp;Fees: "+oRecord.getData("salestotal");
        else
        	disstr=disstr+"&nbsp;&nbsp;&nbsp;&nbsp;Fees: $0";	
       elLiner.innerHTML = disstr;
    	
     }
    YAHOO.widget.DataTable.Formatter.myfbdisplay =FBLink;
    YAHOO.widget.DataTable.Formatter.mywhendisplay =whenformat;
    YAHOO.widget.DataTable.Formatter.visitsdisplay =visitfeeformat;
    YAHOO.widget.DataTable.Formatter.salesdisplay =salefeeformat;
     	var myColumnDefs = [            
            {key:"Who", label:"Who",formatter:"myfbdisplay",sortable:true},
            {key:"day", label:"When",formatter:"mywhendisplay",sortable:true}
        ];
        myColumnDefs.push({key:"visits", label:"Event Page Visits",formatter:"visitsdisplay",sortable:true},{key:"sales", label:"Ticket Sales",formatter:"salesdisplay",sortable:true});  
        var oConfigs = {
        			MSG_EMPTY:message, 
	                paginator: new YAHOO.widget.Paginator({ 
	                    rowsPerPage: 4 
	                }),sortedBy : {key:"day", dir:YAHOO.widget.DataTable.CLASS_ASC} 
       }; 
       if(json.promotions.length<2) oConfigs.sortedBy={};
       if(json.promotions.length<5) oConfigs.paginator={};
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,oConfigs);
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);
    return myDataTable;
    
}

function loadNTSVideo(){
   var url='/main/eventmanage/NetworkSelling!loadNtsVideo?eid=${eid}';
   loadURLBasedWindow(url, displayNtsVideoEnable);	
}

var displayNtsVideoEnable=function(responseObj){
	 showPopUpDialogWithCustomButtons(responseObj,"Network Ticket Selling","");
	 YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		 document.getElementById("videoframe").innerHTML="";
		 YAHOO.ebee.popup.wait.hide();
		 
     });
	
}

 function loadframe(){
   document.getElementById('loaderimage').style.display='none';
   document.getElementById('videoframe').style.display='block';
 }

function updatentsstatus(){
YAHOO.ebee.popup.wait.show();
if(ntsenable=='N'){
var url="NetworkSelling!updateNTSStatus?eid=${eid}&status=Y";
new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			if(!isValidActionMessage(transport.responseText)) return;
			//YAHOO.ebee.popup.wait.hide();
			//window.location.reload();
			window.location.href="/main/eventmanage/NetworkSelling?eid=${eid}";
		}
	});
	}
	else
		window.location.href="/main/eventmanage/NetworkSelling?eid=${eid}";
  /* var url='/main/eventmanage/NetworkSelling!getNTSChargeScreen?eid=${eid}';
   loadURLBasedWindow(url,ntschargesscreen);*/		
}

function disableNTS(){
	YAHOO.ebee.popup.wait.show();
	var url='/main/eventmanage/NetworkSelling!disableNTS?eid=${eid}';
	callURLandReload(url);
	}


	var ntschargesscreen = function (responseObj){
		if(!isValidActionMessage(responseObj.responseText)) return;
		var myButtons = [
		                 { text: "Agree", handler: submitNTSChargesForm },
		                 { text: "Cancel", handler: handleCancel }
		             ];
		showPopUpDialogWithCustomButtons(responseObj, "Network Ticket Selling", myButtons);
	   
	}	

	var submitNTSChargesForm=function(){
		var url="/main/eventmanage/NetworkSelling!updateNTSChargeEnableStatus?eid=${eid}";
		new Ajax.Request(url, {
			  method: 'get',
			  onSuccess: function(transport) {
				var result=transport.responseText;
				if(!isValidActionMessage(transport.responseText)) return;
				if(result.indexOf("success")>-1)
				window.location.href="/main/eventmanage/NetworkSelling?eid=${eid}";
			},onFailure:function(){alert("fail");}
		});
	}
</script>
<div class="box" align="left">
<div class="boxheader">Network Ticket Selling&nbsp;<span id="networksellinghelp"><img src="../images/questionMark.gif"/></span></div>
<div id="networktktsellingfeehelppanel" style="visibility: hidden">
   <div class="hd"><script>setHelpTitle(Networktktselling_fee_helptitle)</script></div>
   <div class="bd"><script>setHelpContent("Networktktselling_fee_helpcontent")</script></div>
  </div>
<div class="boxcontent">
<s:if test='%{ntsEnable=="N"}'>
<s:if test="%{promotedMembers.size>0}">
<table id="mypromo" class='dataTable'>
<s:iterator var="listiter" value="%{promotedMembers}" status="liststat">
<s:if test="%{#liststat.count<5}">
<s:if test="%{#liststat.count==1}">
<thead>
<tr>
<th align="center">Who</th>
<th align="center">When</th>
<th align="center">Event Page Visits</th>
<th align="center">Ticket Sales</th>
</tr>
</thead>	
</s:if>
<tr class="<s:if test="#liststat.odd==true ">odd</s:if><s:else>even</s:else>">
	<td>
	<table style='border:0px;'><tr><td width='50px' style='border:0px;'><a href='http://www.facebook.com/profile.php?id=<s:property value="#listiter['fbuid']"/>' target='_blank'><img title='<s:property value="#listiter['name']"/>' src='https://graph.facebook.com/<s:property value="#listiter['fbuid']"/>/picture' /></a></td><td style='border:0px;width:5px'></td><td style='border:0px;'><a href='http://www.facebook.com/profile.php?id=<s:property value="#listiter['fbuid']"/>' target='_blank'><s:property value="#listiter['name']"/></a></td></tr></table>
	</td>
	<td>
		<s:property value="#listiter['day']"/>&nbsp;<img src='/main/images/home/icon_facebook.png' width='15px' height='15px'>
	</td>
	<s:if test='%{ntsEnable=="Y"}'>
		<td><s:property value="#listiter['impressions']"/></td>
		<td><s:property value="#listiter['sales']"/></td>	
	</s:if>
	<s:else>
	<s:if test="%{#liststat.count==1}">
		<td rowspan="4" colspan="2"><input type="button" name='enablebts' id='enablents' value='Enable Network Ticket Selling'></td>
	</s:if>
	</s:else>
</tr>
</s:if>
</s:iterator>
</table>
</s:if>
<s:else>
<div id='promo'></div>
<script>
displayYUIPromotionsTable();
</script>
</s:else>
</s:if>
<s:else>
<div id='promo'></div>
<script>
displayYUIPromotionsTable();
</script>
</s:else>
</div>
<s:if test='%{ntsEnable=="Y"}'>
<div class="boxfooter">
	   <input id="disable" type="button" value="Disable Network Ticket Selling"/></div>
</s:if>
</div>
<script>
loadHelpPanel("networktktsellingfeehelppanel", "networksellinghelp", "500px");
var disable=new YAHOO.widget.Button("disable",{ onclick:{ fn: disableNTS}});
var myBtn = new YAHOO.widget.Button("probtn",{ onclick:{ fn: updatentsstatus}});
var myBtn1 = new YAHOO.widget.Button("enablents",{ onclick:{ fn: updatentsstatus}});
if(document.getElementById("enablebtn"))
	var enblBtn = new YAHOO.widget.Button("enablebtn",{ onclick:{ fn: updatentsstatus}});
</script>