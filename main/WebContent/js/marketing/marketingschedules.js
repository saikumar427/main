function deleteSchedule(tempid,schid){
	var agree;
	agree=confirm("<h3>Confirm delete</h3>Schedule will be deleted from this list for ever. This operation cannot be revert back.");
	if(agree){
		lurl='../mymarketing/marketingmanage!deleteSchedule?schId='+schid;
		$.ajax({
		  	   type: "POST",
		  	   url: lurl,
		  	   success: function(response) {
		  		window.location.reload(true);
		  	   },failure:function(){
		  		  alert("fail");
		  	   }});
	}
}





