function getWhosPromotingData(eventid){
	//var url='/main/eventmanage/layoutmanage/fbeventpromotions?eid='+eventid;
	var url='/main/eventmanage/layoutmanage/socialnetworking/fbeventpromotions.jsp?eid='+eventid;
	//var url='/main/home/fbeventpromotions.jsp?eid='+eventid;
	jQuery( "#whospromotingdata" ).load(url);
}


