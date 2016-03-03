angular.module('ticketsapp.controllers.profile', [])
    .controller('profile', ['$rootScope', '$scope', '$http','$filter',
        function($rootScope, $scope, $http,$filter) {
            $scope.promotions = true;
            $scope.loadingQuestions = true;
            $scope.loadingSubmit = false;
            $scope.transactionId='';
            $scope.eid='';
            $scope.selectDate='';
            $scope.isSeatingEvent=false;
            $scope.subQuestionsArray=new Array();
            $scope.getProfileJSON = function(){
            	
            $http.get($rootScope.baseUrl + 'getProfileJSON.jsp', {
                params: {
                    api_key: '123',
                    event_id: $scope.eid,
                    transaction_id: $scope.transactionId
                }
            })
                .success(function(data, status, headers, config) {
                //	alert("the dat ais:::"+JSON.stringify(data,undefined,2));
                    $scope.profileQuestions = data;
                    $scope.loadingQuestions = false;
                })
                .error(function(data, status, headers, config) {
                    alert('Unknown error occured. Please try reloading the page.');
                });
            $scope.assignEmpty = function(Obj,qid){
            	if(!Obj[qid])
            		Obj[qid]=new Object();
            };

            };
            
            $scope.trimInputElements=function(question){
            	//alert("the question::"+question.response);
            	if(question.response==undefined)
            		question.response='';
            };
            
            
            
            $scope.getSubQuestions = function(mainQuestion){
            	angular.forEach(mainQuestion.options,function(eachOption,index){
            		if(eachOption.sub_questions){
            			angular.forEach(eachOption.sub_questions,function(subqns,keyval){
            				if(!($scope.subQuestionsArray.indexOf(subqns.id)>-1))
            					$scope.subQuestionsArray.push(subqns.id);
            			});
            		}
            	});
            	
            	//alert("Main JSON::"+JSON.stringify(mainQuestion,undefined,2));
            };
            

            $scope.sub = function() {
            	//alert("in submit");
            	
            	document.getElementById('imageLoad').innerHTML="<center>Processing...<br><img src='../images/loading.gif'></center>";
            	document.getElementById('profilebuttons').style.display='none';
            	document.getElementById('imageLoad').style.display='block';
                $scope.loadingSubmit = true;
                var buyer_info = {};
                var attendee_info = {};
                //collect buyer_info
                angular.forEach($scope.profileQuestions.buyer_questions, function(item, index) {
                    switch (item.type) {
                        case 'text':
                            buyer_info[item.id] = {
                                value: item.response
                            }
                            break;
                        case 'textarea':
                            buyer_info[item.id] = {
                                value: item.response
                            }
                            break;
                        case 'radio':
                            buyer_info[item.id] = {
                                value: item.response
                            }
                            angular.forEach(item.options, function(eachVal, index) {
                                if(eachVal.sub_questions && eachVal.value==item.response){
                                	angular.forEach(eachVal.sub_questions,function(eachquestion,ind){
                                		if(eachquestion.type=='checkbox'){
                                			 var value = [];
                                             angular.forEach(Object.keys(eachquestion.response), function(el, index) {
                                                 if (eachquestion.response[el])
                                                     value.push(el);
                                             });
                                             if (value.length > 0)
                                                 buyer_info[eachquestion.id] = {
                                                     value: value
                                                 };
                                             else buyer_info[eachquestion.id] = {};
                                		}else
                                		buyer_info[eachquestion.id] = {value : eachquestion.response};
                                	});
                                }
                            });
                            
                            break;
                        case 'checkbox':
                            var value = [];
                            angular.forEach(Object.keys(item.response), function(el, index) {
                                if (item.response[el])
                                    value.push(el);
                            });
                            if (value.length > 0)
                                buyer_info[item.id] = {
                                    value: value
                                };
                            else buyer_info[item.id] = {};
                            
                            angular.forEach(item.options, function(eachVal, index) {
                                if(eachVal.sub_questions && eachVal.value==item.response){
                                	angular.forEach(eachVal.sub_questions,function(eachquestion,ind){
                                		if(eachquestion.type=='checkbox'){
                                			 var value = [];
                                             angular.forEach(Object.keys(eachquestion.response), function(el, index) {
                                                 if (eachquestion.response[el])
                                                     value.push(el);
                                             });
                                             if (value.length > 0)
                                                 buyer_info[eachquestion.id] = {
                                                     value: value
                                                 };
                                             else buyer_info[eachquestion.id] = {};
                                		}else
                                		buyer_info[eachquestion.id] = {value : eachquestion.response};
                                	});
                                }
                            });
                            break;
                        case 'select':
                            buyer_info[item.id] = {
                                value: item.response
                            }
                            angular.forEach(item.options, function(eachVal, index) {
                                if(eachVal.sub_questions && eachVal.value==item.response){
                                	angular.forEach(eachVal.sub_questions,function(eachquestion,ind){
                                		if(eachquestion.type=='checkbox'){
                                			 var value = [];
                                             angular.forEach(Object.keys(eachquestion.response), function(el, index) {
                                                 if (eachquestion.response[el])
                                                     value.push(el);
                                             });
                                             if (value.length > 0)
                                                 buyer_info[eachquestion.id] = {
                                                     value: value
                                                 };
                                             else buyer_info[eachquestion.id] = {};
                                		}else
                                		buyer_info[eachquestion.id] = {value : eachquestion.response};
                                	});
                                }
                            });
                            break;
                    }
                });
                
                //collect attendee_info
                angular.forEach($scope.profileQuestions.attendee_questions, function(ticket, index) {
                    var ticket_id = ticket.ticket_id;
                    attendee_info[ticket_id] = {
                        qty: ticket.profiles.length
                    };
                    angular.forEach(ticket.profiles, function(profile, index) {
                        (attendee_info[ticket_id])['qty_' + (index + 1)] = {};
                        var profileobj = (attendee_info[ticket_id])['qty_' + (index + 1)];
                        angular.forEach(profile.response, function(value, key) {
                        	
                        	//alert(ticket_id+"_"+(index+1)+"_"+key);
                        	if($scope.subQuestionsArray.indexOf(key)>-1 && !(document.getElementById(ticket_id+"_"+(index+1)+"_"+key)))
                        		return true;
                            var val = [];
                            if (angular.isObject(value)) {
                                angular.forEach(value, function(v, k) {
                                    if (value[k])
                                        val.push(k);
                                });
                                if (val.length > 0)
                                    profileobj[key] = {
                                        value: val
                                    };
                                else profileobj[key] = {};
                            } else
                                profileobj[key] = {
                                    value: value
                                };
                        });
                    });
                });

             // alert("buyer info::"+JSON.stringify(buyer_info,undefined,2));
                //console.log("attendee info::"+JSON.stringify(attendee_info,undefined,2));
                
            //    data;
                //alert("promotion value is::"+$scope.promotions);
                $http.get($rootScope.baseUrl + 'submitProfileInfo.jsp', {
                    params: {
                        api_Key: '123',
                        event_id: $scope.eid,
                        event_date: $scope.selectDate,
                        seating_enabled: $scope.isSeatingEvent==true?'y':'n',
                        transaction_id: $scope.transactionId,
                        buyer_info: JSON.stringify(buyer_info),
                        attendee_info: JSON.stringify(attendee_info)
                    }
                })
                    .success(function(data, status, headers, config) {
                        $scope.loadingSubmit = false;
                        if (data.status == 'success'){
                        	document.getElementById('imageLoad').style.display='none';
                        	getTempPaymentBlock($scope.eid,$scope.transactionId);
                        //$location.url('/event/payment?eid=' + $rootScope.eid + '&tid=' + $rootScope.transactionId);
                            //$location.path('/event/payment');
                        }
                        else
                            alert('Unknown error occured. Please try again.');
                    })
                    .error(function(data, status, headers, config) {
                        alert('Unknown error occured. Please try again.');
                    });
            };

            $scope.atleastOneTrue = function(response) {
                var result = false;
                angular.forEach(response, function(value, key) {
                    result = result || value;
                });
                return result;
            };

            $scope.allProfiles = function() {
                var allProfiles = [];

                allProfiles.push({
                    ticketid: '',
                    profileid: 'Buyer Information',
                    copyFrom: 'buyerinfo'
                });

                angular.forEach($scope.profileQuestions.attendee_questions, function(item, index) {
                    for (var i = 0; i < item.profiles.length; i++)
                        allProfiles.push({
                        	ticketname: item.ticket_name,
                            ticketid: item.ticket_id,
                            profileid: 'Profile #' + (i + 1),
                            response: item.profiles[i].response
                        });
                });
                return allProfiles;
            };

            $scope.copyResponse = function(from, to, toquestions) {

                if (from.copyFrom == 'buyerinfo') {
                    angular.forEach($scope.profileQuestions.buyer_questions, function(item, index) {
                        if ($filter('filter')(toquestions, {
                            id: item.id
                        }).length > 0)
                            to.response[item.id] = angular.copy(item.response);
                    });
                    return;
                }

                var fromquestions = $filter('filter')($scope.profileQuestions.attendee_questions, {
                    ticket_id: from.ticketid
                })[0].questions;

                angular.forEach(from.response, function(value, key) {
                    if ($filter('filter')(toquestions, {
                        id: key
                    }).length > 0)
                        to.response[key] = angular.copy(from.response[key]);
                });

                if (fromquestions.length != Object.keys(from.response).length)
                    angular.forEach(fromquestions, function(item, index) {
                        if (!(from.response).hasOwnProperty(item.id))
                            to.response[item.id] = undefined;
                    });
            };

        }
    ]);