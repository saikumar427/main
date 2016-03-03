
var k=0;
function addTemplate(){
	var url='home!addTemplate?userId='+userid;
	$('.modal-title').html('Template Details');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","900px"); 
	});
	$('#myModal').modal('show');
}


function closediv(){
	$('#myModal').modal('hide');
 } 


function editTemplate(tempid){
var url='home!editTemplate?userId='+userid+'&templateid='+tempid;
$('.modal-title').html('Template Details');
$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","900px"); 
	});
	$('#myModal').modal('show');
}

function sendTestEmail(tempid){
var url='home!testEmail?userId='+userid+'&templateid='+tempid;
$('.modal-title').html('Send Test Email');
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",url);
$('iframe#popup').css("height","300px"); 
});
$('#myModal').modal('show');

}


function unsubscribeEmail(){
    var url='home!unsubscribe?userId='+userid;
	$('.modal-title').html('Unsubscribe Email');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","400px"); 
	});
	$('#myModal').modal('show');
		
}


function deleteTemplate(tempid)
{
  bootbox.confirm('<h3>Confirm delete</h3>Marketing Template will be deleted from this list for ever. This operation cannot be revert back.', function (result) {
        if (result){
        	var url='home!deleteTemplate?templateid='+tempid;
           	$.ajax({
           		type: "POST",
                url:url,
                success:function(result){
                	window.location.reload();
                },failure:function(){
  		  		  alert("fail");
 		  	   }
           	});
        }
    });
}




