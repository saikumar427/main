function getContentPreview(event,jsonObj){
	var type=jsonObj.type;
	var campId=jsonObj.campId;
	document.getElementById('msg').value=nicEditors.findEditor('msg').getContent();
	$('createcampaign').action='createcampaign!contentShow';
	//FCKeditorAPI.Instances['mytextarea'].UpdateLinkedField();
	$('createcampaign').request({
         onSuccess: function(t) {
	        //var previewFileName=t.responseText;		
	       // alert("previewFileName"+previewFileName);
	       // var url='createcampaign!contentShow';
	        var result=t.responseText;
    		showPopUpDialogWithCustomButtons(t, "Preview", "");

	    },onFailure:function(){
	    	alert("Error");
	    }
    });
	$('createcampaign').action='createcampaign!saveCampaign';
}
function getPreview(event,jsonObj){
	var campId=jsonObj.campId;
	var url='createcampaign!previewExistingCampaign?campId='+campId;
	showYUIinIframe("Preview",url,600,500);
}