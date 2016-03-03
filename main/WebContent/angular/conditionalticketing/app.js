var ctControllerScope=null;
/*function closeHint(){
	 jQuery("#conditionalHint").hide();
}
function showHint(){
	jQuery("#conditionalHint").show();
}*/
//initializing angular app
var app = angular.module('CT', []);

app.directive('icheck', ['$timeout', '$parse', function($timeout, $parse) {
		return {
		    require: 'ngModel',
		    link: function($scope, element, $attrs, ngModel) {
		        return $timeout(function() {
		            var value;
		            value = $attrs['value'];
		
		            $scope.$watch($attrs['ngModel'], function(newValue){
		                $(element).iCheck('update');
		            })
		
		            return $(element).iCheck({
		                checkboxClass: 'icheckbox_square-grey',
		                radioClass: 'iradio_square-grey'
		
		            }).on('ifChanged', function(event) {
		                if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
		                    $scope.$apply(function() {
		                        return ngModel.$setViewValue(event.target.checked);
		                    });
		                }
		                if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
		                    return $scope.$apply(function() {
		                        return ngModel.$setViewValue(value);
		                    });
		                }
		            });
		        });
		    }
		};
    }
]);


app.filter('range', function() {
	  return function(input, min, max) {
	    min = parseInt(min); //Make string input int
	    max = parseInt(max);
	    min=min==0?1:min;
	    for (var i=min; i<=max; i++)
	      input.push(i+'');
	    return input;
	  };
});

app.controller('ctController', function($scope, $sce, $filter,$http,$rootScope) {
	$scope.isMsgShow=false;
	$scope.isTabShow=false;
	if(currentEventLevel==requireLevel)
		$scope.isTabShow=true;
	
	/*$scope.requireClickProgram=function(index){
		 $scope.isTabShow=true;
		 $scope.$broadcast ('requireNewProgram',index);		
	};*/
	$scope.requireClick=function(index){
		 $scope.$broadcast ('requireNew',index);
		 $scope.elseRequireClose();
	};
	$scope.mustClick=function(index){
		 $scope.$broadcast ('mustNew',index);
		 $scope.elseMustClose();
	};
	$scope.blockClick=function(index){
		 $scope.$broadcast ('blockNew',index);
		 $scope.elseBlockClose();
	};
	$scope.elseRequireClose=function(){
		 $scope.$broadcast ('mustClose');
		 $scope.$broadcast ('blockClose');
	};
	$scope.elseMustClose=function(){
		 $scope.$broadcast ('requireClose');
		 $scope.$broadcast ('blockClose');
	};
	$scope.elseBlockClose=function(){
		 $scope.$broadcast ('requireClose');
		 $scope.$broadcast ('mustClose');
	};
	
	  /*all controllers scopes*/
	   $scope.scopes={"requireController":null,
			"conditionalController":null, /*i.e must controller */
			"blockController":null
			};
	
	    ctControllerScope=$scope;
		$scope.tickets=[];
	    $scope.dupAllTickets=angular.copy($scope.tickets); 
		ticketsJSONFill(tktdata.TicketDetails,"1");
	   
	 $scope.existingRulesObj = eval('('+existingRules+')');
	 
	/* if(Object.keys($scope.existingRulesObj).length>0){
		 closeHint();
	 }*/	
	 
	
	   $scope.refreshDup=function(){
		   $scope.dupAllTickets=angular.copy($scope.tickets); 
		 };
		 
	     $scope.hoverIn = function(){
	        this.hoverEffect = true;
	    };

	    $scope.hoverOut = function(){
	        this.hoverEffect = false;
	    };
	  
	
	 $scope.radioToggle=function(scope,value){
		 $("input[type='radio']").iCheck('uncheck');
		 $("input[value='"+value+"']").iCheck('check');
		 scope.condition=value;
	 };   
	    
	
	 $scope.saveText=$sce.trustAsHtml("SAVE ALL");
	 
	 
	 $scope.saveRules=function(ruleType,actionType){
		 $scope.refreshDup();
		 var rulesArray={};
		 /*if($scope.blockRules.length > 0 || $scope.mustRules.length > 0 || $scope.requireRules.length > 0 )
			 closeHint();
		 else
			 showHint();*/
		 
		 if(ruleType=="blockRules")
			 rulesArray=$scope.blockRules;
		 else if(ruleType=="mustRules")
			 rulesArray=$scope.mustRules;
		 else  if(ruleType=="requireRules")
			 rulesArray=$scope.requireRules;
		// console.log("inside save rules::"+JSON.stringify(rulesArray)+"ruleType::"+ruleType);
		var request= jQuery.ajax({
			  url: "../eventmanage/ManageTickets!saveConditionalTicketingRules",
			  type: "POST",
			  data: { rules : JSON.stringify(rulesArray),eid: eventid,ruleType:ruleType }		 
			});
			request.done(function( msg ) {
				var status=JSON.parse(msg);
				if(status.status=="success"){
					if("create"==actionType)
						statusMessageForRules(props.tickt_rule_add_success,"success");
					else if("update"==actionType)
						statusMessageForRules(props.tickt_rule_update_success,"success");
					else if("delete"==actionType)
						statusMessageForRules(props.tickt_rule_delet_success,"success");
				}
				else{
					statusMessageForRules("Network error, please try again later ","warning");
				}
			});
			 
	 };        
   
    $scope.activeTab = "Required";
    $scope.blockRules = [];
    /*$scope.customRules = {
    	'condition': 'custom',
        'info': ''
    };*/
    $scope.mustRules = [];
    $scope.requireRules = [];
    if('blockRules' in $scope.existingRulesObj )
    	$scope.blockRules=$scope.existingRulesObj.blockRules;
    /*if('customRules' in $scope.existingRulesObj )
    	$scope.customRules=$scope.existingRulesObj.customRules;*/
    if('mustRules' in $scope.existingRulesObj )
    	$scope.mustRules=$scope.existingRulesObj.mustRules;
    if('requireRules' in $scope.existingRulesObj )
    	$scope.requireRules=$scope.existingRulesObj.requireRules;

    $scope.tabChange = function(tab) {
        $scope.activeTab = tab;
    };

    $scope.buttonSettings = {
        smartButtonMaxItems: 1,
        smartButtonTextConverter: function(itemText, originalItem) {
            if (itemText.length > 15)
                return itemText.substr(0, 15) + "...";

            return itemText;
        }
    };
    $scope.singleSelect = {
        selectionLimit: 1,
        buttonDefaultText:'Select Ticket',
        smartButtonMaxItems: 1,        
        smartButtonTextConverter: function(itemText, originalItem) {        	
            if (itemText.length > 15)            	
                return itemText.substr(0, 15) + "...";

            return itemText;
        }
    }; 
    
    $scope.getNameById = function(id) {
         if (id)
            return $filter('filter')( $scope.tickets, {
            	id: id
            })[0].label;
        
    }; 
    

    
    $scope.getMINMAX = function(id) {
        if (id)
           return $filter('filter')( $scope.tickets, {
           	id: id
           })[0];
       
   }; 
 
});
app.controller('mustController', function($scope, $sce ) {	
	$scope.scopes.conditionalController=$scope;
	$scope.thisScope=null;
	$scope.noRule=props.tickt_conditional_buy_rule;
	$scope.ruleTypes=[{"value":"MustByOR","label":props.tickt_one_of_lbl},{"value":"MustByAND","label":props.tickt_all_of_lbl}];
	   $scope.must;
	   $scope.status={};
	   $scope.status.isEditing=false;
	   $scope.copy=function(ruleObject){
		   $scope.elseMustClose();
		   $scope.refreshDup();
		   $scope.must=angular.copy(ruleObject); 		  
		   angular.forEach($scope.must.trg,function(value,index){
		 		 angular.forEach($scope.dupAllTickets,function(innerValue,innerIndex){
		 			 if(innerValue.id==value.id){
		 				 innerValue['check']=true;
		 				 innerValue['type']=value.type;
		 				 innerValue['qty']=value.qty;
		 				 innerValue['min']=value.min;
		 				 innerValue['max']=value.max;
		 			 }
		 		 });	 		
			 });
	   };
	   

		  $scope.$on('mustNew', function(e,index) {  
			 $scope.addNewMustRule(index);
		    });
		  $scope.$on('mustClose', function(e) {  
			  $scope.must=null; 
			  $scope.thisScope.clickedIndex=-2;
			  $scope.status.isEditing=false;
			  
			 });
	   
	$scope.init=function(obj){		
		if("type" in obj){}
			else
			obj.type="NOLIMIT";
		
		/*$scope.$watchCollection('obj', function(newVal,oldVal) {
		       console.log('hey, myVar has changed!');
		  });	*/
		
	};
    $scope.safe = function(html) {
        return $sce.trustAsHtml(html);
    };
    
    
	 $scope.isSelected=function(){		
		 var flag=false;
		 angular.forEach($scope.dupAllTickets,function(value,index){
			 if(value.check==true){
				 flag=true;				 
			 }
		 });	
		 return flag;
	 };    
	 
	 $scope.selectedTickets=function(){		
		 var tickets=[];
		 angular.forEach($scope.dupAllTickets,function(value,index){
			 if(value.check==true){
				 tickets.push(value);				 
			 }
		 });	
		 return tickets;
	 };
	 
    $scope.addClick=function(index){ 	 
    	 $scope.thisScope.clickedIndex=-2;
 	   $scope.saveClick(index);
    };
    $scope.addNewMustRule = function(index) {
    	 $scope.thisScope.clickedIndex=index;
        $scope.must={
            'condition': 'MustByOR',
            'src': {},
            'trg': []
        };
       // $scope.must=$scope.mustRules[0];
        $scope.status.isEditing=true;
    };
    
    $scope.deleteRule = function(index, arr) {
         deleteObject(arr,index,'mustRules');
         $scope.must=null;
         $scope.status.isEditing=false;
    };

    
    $scope.cancelClick=function(mustScope){
    	  mustScope.clickedIndex=-2;
    	  $scope.must=null;
    	  $scope.status.isEditing=false;
    	  $scope.refreshDup();
    	 
    };
    $scope.saveClick=function(index){    	
    	 var actionType="";
    	 $scope.must.trg=[];
 		 angular.forEach($scope.dupAllTickets,function(value,index){	 			
 			 if(value.check==true)
 			    $scope.must.trg.push({id:value.id,type:value.type,qty:value.qty,min:value.min,max:value.max});
 		 });
    	 if(isNaN(index)){
    		 $scope.mustRules.push( $scope.must);
    		 actionType="create";
    	 }
    	 else{
	    	 if($scope.mustRules.length-1>=index){
	    		 $scope.mustRules[index]= $scope.must;
	    		 actionType="update";
	    	 }
	    	 else{
	    		 $scope.mustRules.push( $scope.must);
	    		 actionType="create";
	    	 }
    	 }
    	 $scope.saveRules('mustRules',actionType);
    	 
    	 $scope.must=null;
    	  $scope.status.isEditing=false;
    	  
    	
    };
   $scope.updateSelection=function($event, obj,type){    	
    	 if(type=='NOLIMIT'){
    		 obj.qty=0;
    		 obj.min=0;
    		 obj.max=0;
    	 } else{
    		 obj.qty=1;
    		 obj.min=1;
    		 obj.max=1;
    	 }   	  
    };
    
    
    $scope.mustRulesAddState = function() {
    	var status = false;  
        if($scope.status.isEditing)
        	return true;
        if($scope.must){
	        if (!$scope.must.src.id || $scope.must.trg.length <= 0)
	        	status = true;	        
	        else
	        	statu=false;
        }      
        return status;

    };
    
    $scope.mustRuleValid = function(eachObject) {
        var status = false;        
         if (!eachObject.src.id || eachObject.trg.length <= 0) status = true;
            if(eachObject.trg.length>0){
            	angular.forEach(eachObject.trg, function(obj, index) {
            		if( obj.qty==null || typeof obj.qty=='undefined')	
            			status = true;
            		if( obj.min==null || typeof obj.min=='undefined')	
            			status = true;
            		if( obj.max==null || typeof obj.max=='undefined')
            			status = true;            		
            	});
            }           
       
        return status;
    };
});
app.controller('blockController', function($scope) {
   $scope.noRule=props.tickt_cant_buy_rule;
   $scope.scopes.blockController=$scope;
   $scope.thisScope=null;
   $scope.rule;
   $scope.status={};
   $scope.status.isEditing=false;
   $scope.copy=function(ruleObject){
	   $scope.elseBlockClose();
	   $scope.rule=angular.copy(ruleObject);	   
	   angular.forEach($scope.rule.trg,function(value,index){
	 		 angular.forEach($scope.dupAllTickets,function(innerValue,innerIndex){
	 			 if(innerValue.id==value.id){
	 				 innerValue.check=true;
	 			 }
	 		 });	 		
		 });
   };
   
  
	 
    $scope.$on('blockNew', function(e,index) {  
		 $scope.addNewBlockRule(index);
	 });
    $scope.$on('blockClose', function(e) {  
    	 $scope.rule=null;
    	 $scope.thisScope.clickedIndex=-2;
		 $scope.status.isEditing=false;
		 });
   
   $scope.isValid=function(){		
		 var keepGoing = true;
		  angular.forEach($scope.dupAllTickets,function(value,index){
			  if(keepGoing) {
	                if(value.check==true)	
	                	keepGoing=false;		                
			  }
          });
		  if(keepGoing)
			  return false;			  
		  else
			  return true;
		  
	 };
   $scope.addClick=function(index){ 	 
	   $scope.thisScope.clickedIndex=-2;
 	   $scope.saveClick(index);
    };

    $scope.addNewBlockRule = function(index) {
    	$scope.thisScope.clickedIndex=index;
    	 $scope.rule={
            'condition': 'Block',
            'src': {},
            'trg': []
        };      
    	 $scope.status.isEditing=true;
             
    };
    $scope.deleteRule = function(index, arr) {
        deleteObject(arr,index,'blockRules');
        $scope.rule=null;
        $scope.status.isEditing=false;
    };
    $scope.cancelClick=function(blockScope){
    	  blockScope.clickedIndex=-2;
    	  $scope.rule=null;
    	  $scope.status.isEditing=false;
    	  $scope.refreshDup();
    };
    $scope.saveClick=function(index){
    	var actionType="";    	
    	 $scope.rule.trg=[];
 		 angular.forEach($scope.dupAllTickets,function(value,index){	 			
 			 if(value.check==true)
 			    $scope.rule.trg.push({id:value.id});
 		 });
    	
    	 if(isNaN(index)){
    		 $scope.blockRules.push( $scope.rule);
    		 actionType="create";
    	 }
    	 else{
	    	 if($scope.blockRules.length-1>=index){
	    		 $scope.blockRules[index]= $scope.rule;
	    		 actionType="update";
	    	 }
	    	 else{
	    		 $scope.blockRules.push( $scope.rule);
	    		 actionType="create";
	    	 }
    	 }
    	 $scope.saveRules('blockRules',actionType);
    	 $scope.rule=null;
    	  $scope.status.isEditing=false;
    	
    };
    $scope.blockRulesAddState = function() {        
        var status = false;  
        if($scope.status.isEditing)
        	return true;
      
        if($scope.rule){
	        if (!$scope.rule.src.id || $scope.rule.trg.length <= 0)
	        	status = true;
	        else
	        	statu=false;
        }      
        return status;
    };
});


app.controller('requireController', function($scope) {	
		$scope.thisScope=null;
		$scope.noRule=props.tickt_must_buy_rule_display;
		$scope.scopes.requireController=$scope;
	    $scope.require;
	    $scope.status={};
	    $scope.status.isEditing=false;
		$scope.ruleTypes=[{"value":"requireOR","label":props.tickt_one_of_lbl},{"value":"requireAND","label":props.tickt_all_of_lbl}];
	    
	   
		$scope.$on('requireNew', function(e,index) {  
			 $scope.addNewRequireRule(index);
		    });
		 /*$scope.$on('requireNewProgram', function(e,index) {  
			 $scope.addNewRequireRuleProgramClick(index);
		    });*/
		
		
		
		$scope.$on('requireClose', function(e) {  
			 $scope.require=null;
			 $scope.thisScope.clickedIndex=-2;
			 $scope.status.isEditing=false;
			
			 });
		 
		 
		 $scope.isValid=function(){		
			 var keepGoing = true;
			  angular.forEach($scope.dupAllTickets,function(value,index){
				  if(keepGoing) {
		                if(value.check==true)	
		                	keepGoing=false;		                
				  }
	            });
			  if(keepGoing)
				  return false;			  
			  else
				  return true;
			  
		 };
	    
	    $scope.copy=function(ruleObject){
	    	$scope.elseRequireClose();
	 	   $scope.require=angular.copy(ruleObject);  		
	 	  angular.forEach($scope.require.trg,function(value,index){
		 		 angular.forEach($scope.dupAllTickets,function(innerValue,innerIndex){
		 			 if(innerValue.id==value.id){
		 				 innerValue.check=true;
		 			 }
		 		 });
		 		
	 		 });
	    };
	   
	     $scope.addNewRequireRule = function(index) {
	    	 /*if(currentEventLevel!=requireLevel){
	    		 specialFee(eid,requireLevel,"TicketingRules","Ticketing");
	    		 return false;
	    	 }*/
	    	 $scope.thisScope.clickedIndex=index;
	     	 $scope.require={
	     			'condition': 'requireOR',
	     	        'trg': []
	         };      
	     	 $scope.status.isEditing=true;	   
	     };
	     
	     /*$scope.addNewRequireRuleProgramClick=function(index){
	    	 if(currentEventLevel!=requireLevel){
	    		 specialFee(eid,requireLevel,"TicketingRules","Ticketing");
	    		 return false;
	    	 }
	    	 $scope.$apply(function () {
		    	 $scope.thisScope.clickedIndex=index;
		     	 $scope.require={
		     			'condition': 'requireOR',
		     	        'trg': []
		         };      
		     	 $scope.status.isEditing=true;	   
	    	 });
	     };*/
	     
	     $scope.deleteRule = function(index, arr) {
	         deleteObject(arr,index,'requireRules');
	         $scope.require=null;
	         $scope.status.isEditing=false;
	     };
	     
	     $scope.cancelClick=function(requireScope){
	     	 requireScope.clickedIndex=-2;
	     	  $scope.require=null;
	     	  $scope.status.isEditing=false;
	     	 $scope.refreshDup();
	     };
	
	     $scope.requireRuleAddState = function() {  
	    	 if(ctControllerScope.tickets.length <= 1)
	    		 return true;
	    	 if($scope.status.isEditing)
	         	return true;
	         var status = false;  
	         var count=0;
	         angular.forEach($scope.requireRules, function(eachRule, index) {
	        	 if(eachRule.condition=="requireOR" || eachRule.condition=="requireAND")
	        		 count=count+1;
	         });
	         if(count>=2)
	        	 status=true;
	         return status;
	     };
	 	$scope.saveClick=function(index){	
	 		 var actionType="";
	 		 $scope.require.trg=[];
	 		 angular.forEach($scope.dupAllTickets,function(value,index){	 			
	 			 if(value.check==true)
	 			    $scope.require.trg.push({id:value.id});
	 		 });
	    	 if(isNaN(index)){
	    		 $scope.requireRules.push( $scope.require);
	    		 actionType="create";
	    	 }
	    	 else{
		    	 if($scope.requireRules.length-1>=index){
		    		 $scope.requireRules[index]= $scope.require;
		    		 actionType="update";
		    	 }
		    	 else{
		    		 $scope.requireRules.push( $scope.require);
		    		 actionType="create";
		    	 }
	    	 }
	    	 $scope.saveRules('requireRules', actionType);
	    	 
	    	 $scope.require=null;
	    	 $scope.status.isEditing=false;
	  		
	    };
	    
	     $scope.existedRule=function(){
	    	 if($scope.requireRules.length==0)
	    		 return "";
	    	if($scope.requireRules[0].condition=="requireOR"){
	    		$scope.require.condition="requireAND";
	    		return "requireOR";
	    	}
	    	else if($scope.requireRules[0].condition=="requireAND"){
	    		$scope.require.condition="requireOR";
	    		return "requireAND";
	    	}
	    	else{
	    		if($scope.require){
	    			$scope.require.condition="requireOR";
	    			return "requireAND";
	    		}
	    		else{
	    			return "";
	    		}
	    	}
	    		
	     };
	     
});

/*app.controller('customController', function($scope) {
	  $scope.saveClick=function(){
		  
	    };
});*/

function deleteObject(array,index,ruleType){
	bootbox.confirm(props.tickt_delete_msg_lbl, function(result) {		
        if(result){ 
        	array.splice(index,1);
        	ctControllerScope.saveRules(ruleType,"delete");
        	ctControllerScope.$apply(function(){
        		
            });
        }
    });
};


/*function afterSpcialFeePopup(){
	currentEventLevel=requireLevel;
	ctControllerScope.requireClickProgram(-1);
}*/
function ticketsJSONFill(array,calledBy){	
	var newArray=[];
	for(var i=0;i<array.length;i++){
		if(array[i].type!="group" /*&& (array[i].showyn !='N' &&  array[i].showyn !='n')*/  && (array[i].isdonation =='N' || array[i].isdonation =='n')){
			var object= new Object();
			object['id']=array[i].tid;
			object['label']=array[i].name;
			object['min_qty']=parseInt(""+array[i].min_qty) == 0 ? "1" : array[i].min_qty;
			object['max_qty']=array[i].max_qty;
			object['is_donation']=false; /*(array[i].isdonation =='N' || array[i].isdonation =='n') ? false : true*/;
			newArray.push(object);
		}	
	}	
	ctControllerScope.tickets=newArray;
	ctControllerScope.dupAllTickets=angular.copy(ctControllerScope.tickets); 

	if(calledBy!="1")	
		ctControllerScope.$apply(function(){		
	    });
	showHideConditionsDiv(newArray);
}

function statusMessageForRules(message, type) {		
	notification(message,type);
}
   
function showHideConditionsDiv(tickets){
	if (tickets.length <= 1){
		ctControllerScope.isMsgShow=true;
		$("#ticketingRules").hide();	
	}else{
		$("#ticketingRules").show();
	}
}; 

function isTicketHasRule(ticketID){	
	var rules=jQuery.extend(true,[], ctControllerScope.blockRules);
	if(eachRuleTypeTest(rules,ticketID))
		return true;
	rules=jQuery.extend(true, [], ctControllerScope.mustRules);
	if(eachRuleTypeTest(rules,ticketID))
		return true;	
	rules=jQuery.extend(true, [], ctControllerScope.requireRules);
	for(var i=0;i<rules.length;i++){
		for(var j=0;j<rules[i].trg.length;j++){
			if(rules[i].trg[j].id==ticketID)
				return true;			
		}		
	}
	return false;	
}

function eachRuleTypeTest(rules,ticketID){
	for(var i=0;i<rules.length;i++){
		if(rules[i].src.id==ticketID){
			return true;
		}
		for(var j=0;j<rules[i].trg.length;j++){
			if(rules[i].trg[j].id==ticketID){
				return true;
			}
		}		
	}
}
