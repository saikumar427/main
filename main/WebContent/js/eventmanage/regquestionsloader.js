function instantiateTransactionQTable(ds, cname,eid){
var recordIndex=-1;
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["attribname","attrib_id","attribtype", "isrequired","moveup_attrib","movedn_attrib"]
        }
        
    });
    var CustomActionsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("attrib_id");
    	recordIndex++;
    	var displaycontent="<span>"+oRecord.getData("attribname");
    	if(qid.indexOf("-1")>-1) {
    	}else if(qid.indexOf("-2")>-1) {
    	}else if(qid.indexOf("-3")>-1) {
    	}else if(qid.indexOf("-4")>-1) {
    	displaycontent+="<div align='left'>[<a href='javascript:editTransactionPhoneQuestion("+eid+","+qid+");'>Edit</a>]</div> ";
    	}else{
    	displaycontent+="<div align='left'>[<a href='javascript:editTransactionQuestion("+eid+","+qid+");'>Edit</a> | <a href='javascript:deleteTransactionQuestion("+eid+","+qid+");'>Delete</a>] ";
    	
    	var prevattribid=oRecord.getData("moveup_attrib");
    	var nextattribid=oRecord.getData("movedn_attrib");
    	
    	var str='';
    	if(prevattribid.indexOf("-4")>-1) {
    	str='';
    	}else{
    	str='<img src="../images/up.gif" onclick="javascript:swapQuestions('+qid+','+prevattribid+','+eid+');"/>';
    	}
   
    	if(nextattribid=='0') {
    	str+='';
    	}else{
    	str+='<img src="../images/dn.gif" onclick="javascript:swapQuestions('+qid+','+nextattribid+','+eid+');"/>';
    	}
    	displaycontent+=str+'</div>';
    	
    	}
    	displaycontent+="</span>";
         elLiner.innerHTML = displaycontent;                   
    };
    
    YAHOO.widget.DataTable.Formatter.actionsdisplay = CustomActionsDisplayFormatter;
    var myColumnDefs = [
            {key:"attribname", label:"Name",formatter:"actionsdisplay"},
            {key:"isrequired", label:"Type"}                    
        ];
    var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}


/*
 * 
 * 
 * 
 * 
 * 
 */
function instantiateTicketQTable(ds, cname,eid){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["attribname","attrib_id","attribtype","tickets", "isrequired","moveup_attrib","movedn_attrib"]
        }
        
    });
    
    var CustomTicketsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("attrib_id");
    	var ticketsdisplaycontent="<span>";
    	var tickets = oRecord.getData("tickets");
    	for(var i=0;i<tickets.length;i++){
    	if(tickets[i].name)
        	ticketsdisplaycontent+="<li> "+tickets[i].name+"</li>";
    	}
    	
    	ticketsdisplaycontent+="</span>";
         elLiner.innerHTML = ticketsdisplaycontent;                   
    };
    var CustomActionsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("attrib_id");
    	var displaycontent="<span>"+oRecord.getData("attribname");
    	displaycontent+="<div align='left'>[";
    	if(qid.indexOf("-")>-1) {
    	}else{
    	displaycontent+="<a href='javascript:editTicketQuestion("+eid+","+qid+");'>Edit</a> | <a href='javascript:deleteTicketQuestion("+eid+","+qid+");'>Delete</a> | ";
    	}
    	displaycontent+="<a href='javascript:applyTicketsToQuestion("+eid+","+qid+");'>Select Tickets</a>] ";
    	if(qid.indexOf("-")>-1) {
    	}else{
    	var prevattribid=oRecord.getData("moveup_attrib");
    	var nextattribid=oRecord.getData("movedn_attrib");
    	
    	var str='';
    	if(prevattribid.indexOf("-8")>-1) {
    	str='';
    	}else{
    	str='<img src="../images/up.gif" onclick="javascript:swapQuestions('+qid+','+prevattribid+','+eid+');"/>';
    	}
   
    	if(nextattribid=='0') {
    	str+='';
    	}else{
    	str+='<img src="../images/dn.gif" onclick="javascript:swapQuestions('+qid+','+nextattribid+','+eid+');"/>';
    	}
    	displaycontent+=str;
    	}
    	displaycontent+="</div></span>";
         elLiner.innerHTML = displaycontent;                   
    };
    
    YAHOO.widget.DataTable.Formatter.ticketsdisplay = CustomTicketsDisplayFormatter;
    YAHOO.widget.DataTable.Formatter.actionsdisplay = CustomActionsDisplayFormatter;
    var myColumnDefs = [
            {key:"attribname", label:"Name",formatter:"actionsdisplay"},
            {key:"Tickets", label:"Selected Tickets",formatter:"ticketsdisplay"}           
        ];
    var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}
/*response level q*/
function instantiateResponseQTable(ds, cname,eid){
	
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["attribname","attrib_id","attribtype","responses", "isrequired","moveup_attrib","movedn_attrib"]
        }
        
    });
    
    var CustomResponsesDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	
    	var qid = oRecord.getData("attrib_id");
    	  	var responsesdisplaycontent="<span>";
    	var responses = oRecord.getData("responses");
    	if(responses){
    	for(var i=0;i<responses.length;i++){
    	if(responses[i].name)
        	responsesdisplaycontent+="<li> "+responses[i].name+"</li>";
    	}  
    	}else{
    		//responsesdisplaycontent+="<li>Attending</li><li>Not Sure</li>";
    		responsesdisplaycontent+="Requidgfgfgred";
    	}
    	responsesdisplaycontent+="</span>";
    	
        elLiner.innerHTML = responsesdisplaycontent;                   
    };
    var CustomActionsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var qid = oRecord.getData("attrib_id");
    	var displaycontent="<span>"+oRecord.getData("attribname");
    	displaycontent+="<div align='left'>";
    	if(qid.indexOf("-9")>-1) {
    	}else if(qid.indexOf("-10")>-1){
    	}else if(qid.indexOf("-11")>-1){
    		//displaycontent+="<a href='javascript:applyTicketsToQuestion("+eid+","+qid+");'>Select Responses</a> ";
    	}
		else if(qid.indexOf("-12")>-1){
			//displaycontent+="<a href='javascript:applyTicketsToQuestion("+eid+","+qid+");'>Select Responses</a> ";
		}
    	else{
    	 	displaycontent+="[<a href='javascript:editTicketQuestion("+eid+","+qid+");'>Edit</a> | <a href='javascript:deleteTicketQuestion("+eid+","+qid+");'>Delete</a> | ";
    	
    	//displaycontent+="<a href='javascript:applyTicketsToQuestion("+eid+","+qid+");'>Select Responses</a>] ";
    	}
    	//displaycontent+="<a href='javascript:applyTicketsToQuestion("+eid+","+qid+");'>Select Responses</a>] ";
    	if(qid.indexOf("-")>-1) {
    	}else{
    	var prevattribid=oRecord.getData("moveup_attrib");
    	var nextattribid=oRecord.getData("movedn_attrib");
    	
    	var str='';
    	if(prevattribid.indexOf("-13")>-1) {
    	str='';
    	}else{
    	str='<img src="../images/up.gif" onclick="javascript:swapQuestions('+qid+','+prevattribid+','+eid+');"/>';
    	}
   
    	if(nextattribid=='0') {
    	str+='';
    	}else{
    	str+='<img src="../images/dn.gif" onclick="javascript:swapQuestions('+qid+','+nextattribid+','+eid+');"/>';
    	}
    	displaycontent+=str;
    	}
    	displaycontent+="</div></span>";
         elLiner.innerHTML = displaycontent;                   
    };
    
    YAHOO.widget.DataTable.Formatter.responsesdisplay = CustomResponsesDisplayFormatter;
    YAHOO.widget.DataTable.Formatter.actionsdisplay = CustomActionsDisplayFormatter;
    var myColumnDefs = [
            {key:"attribname", label:"Name",formatter:"actionsdisplay"},
            //{key:"Responses", label:"Type",formatter:"responsesdisplay"}
            {key:"isrequired", label:"Type"}
                       
        ];
    var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}



/*end*/