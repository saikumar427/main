function instantiateMembershipTypesListTable(ds, cname){

 var membershipListDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","id","action","description"]
        },
        doBeforeCallback : function (req,raw,res,cb) {
            // This is the filter function
            var data     = res.results || [],
                filtered = [],
                i,l;

            if (req) {
                req = req.toLowerCase();
                for (i = 0, l = data.length; i < l; ++i) {                	
                    if (data[i].name.toLowerCase().indexOf(req)>-1) {
                        filtered.push(data[i]);
                    }
                }
                res.results = filtered;
            }

            return res;
        }
    });
    var mypaginator = new YAHOO.widget.Paginator({
        rowsPerPage   : 5,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    /*var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var temp = oRecord.getData("id");
         elLiner.innerHTML = "<div id='membershipId_"+ temp +"'></div>";  
         alert("html"+elLiner.innerHTML);
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    */
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	//editMembership
    	var temp=oRecord.getData("id");
    	var temp1='&nbsp;<a href="#" onclick=editMembership('+currentGroupId+','+temp+') >';
    	var temp2='<a href="#" onclick=deleteMembership('+currentGroupId+','+temp+')>';
    	elLiner.innerHTML = temp1+'Edit</a>&nbsp;|&nbsp;'+temp2+'Delete</a>';
   
    // elLiner.innerHTML = '&nbsp;<a href="../mycommunities/CommunityManage!editMembershipType?groupId='+currentGroupId+'&membershipId='+oRecord.getData("id")+'">Manage</a>';
      
    };
    
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"name", sortable:true, label:"Name", width:278},
            {key:"description", sortable:true, label:"Description", width:278},
            {key:"Manage", label:"",formatter:"myActions"}
            ];
   

   var memshipListDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, membershipListDataSource,conf);
   memshipListDataTable.subscribe("", memshipListDataTable.onEventHighlightRow); 
   memshipListDataTable.subscribe("", memshipListDataTable.onEventUnhighlightRow);   
   return memshipListDataTable;
}
var createMembershipTypeslistTable= function() {		
        var campfilterTimeout = null;
        instantiateMembershipTypesListTable(YAHOO.membershiptypes.Data.citems,"membershiptypeslisttable");
}




