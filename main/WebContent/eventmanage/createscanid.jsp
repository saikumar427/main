<%@taglib uri="/struts-tags" prefix="s" %>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<form name="ScanIDsfrm" action="ScanIDs!insertScanID" id="ScanIDsfrm" method="post">
<s:hidden name="eid" id="eid"></s:hidden>

<div class="col-xs-12 col-sm-12">
	<div class="row">
		<div class="col-xs-12">	
			<div id='errormsg' class="alert alert-danger" style="display:none"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">		
			<div class="col-xs-2" style="padding-top: 5px;">
				Scan ID
			</div>
			<div class="col-xs-10">
				<input type="text" name="name" id="name" class="form-control"/>
			</div>
		</div>
	</div>
	<br>
	<hr>
	<div class="row">
		<!-- <div class="col-xs-4"></div>
		<div class="col-xs-6">
			<input type="button" class="btn btn-primary" value="Submit" onclick="submitScanid();">&nbsp;<input type="button" class="btn btn-primary" value="Cancel" onclick="parent.closepopup();">
		</div> -->
		 <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="submit" class="btn btn-primary">
                                <i class="fa fa-pencil-square-o"></i> Submit</button>
                            <button type="button" class="btn btn-danger cancel" onclick="parent.closepopup();">
                                <i class="fa fa-times"></i> Cancel</button>
                        </div><br><br><br>
	</div>
</div>
</form>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/tooltipster.css" />
  <script src="/main/js/jquery.tooltipster.js"></script>
<script src="/main/js/jquery.validate.min.js"></script>
  <style>
   /* tooltip custom theme */


/* tooltip custom theme */
.form-validate-theme {
	border-radius: 5px; 
	border: 2px solid #ff4747;
	background: #fff4f4;
	color: #000;
}
.form-validate-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
}
   
   </style>
<script>
function submitScanid(){
	insertscanid();
}


$('#ScanIDsfrm input[type="text"], #ScanIDsfrm textarea, #ScanIDsfrm select').tooltipster({
    trigger: 'custom',
    onlyOne: false,
    fixedWidth:'300px',
    position: 'top-right',
    theme:'form-validate-theme'
});

$('#ScanIDsfrm').validate({
errorPlacement: function (error, element) {
                
    var lastError = $(element).data('lastError'),
        newError = $(error).text();
    
    $(element).data('lastError', newError);
                    
    if(newError !== '' && newError !== lastError){
        $(element).tooltipster('content', newError);
        $(element).tooltipster('show');
    }
},
success: function (label, element) {
    $(element).tooltipster('hide');
    $('#errormsg').hide();
},
rules: {
		name: {
			required : true		  
		},
},
messages: {
	name: {required:'Please Enter Scan ID'}
},
 submitHandler: function (form) {
	 var eid=document.getElementById('eid').value;	
     var name=document.getElementById('name').value;	
	 submitForm("ScanIDs!insertScanID",eid,name);	
 }
 
 });



function submitForm(url,eid,name){	
	var scanids = getScanidsView();
	var message='';
	parent.loadingPopup();
	url=url+"?eid="+eid+"&name="+name;	
	$.ajax({
		url : url,
		type : 'get',
		success : function(t){			
			var result=t;
			parent.hidePopup();			
			if(result.indexOf("Name Exists")>-1){
				document.getElementById("errormsg").innerHTML="This Scan ID is not available, please enter new Scan ID";
				$('#errormsg').show();
			}else if(result.indexOf("spacesInUrl")>-1){
					document.getElementById("errormsg").innerHTML="Use alphanumeric characters only";		
					$('#errormsg').show();
			}else{	
				window.location.reload(true);
				  parent.createScanidsView(scanids);
			}	
			parent.resizeIframe();
		}
	});
}


function getErrorMsg(message){
	
}

var getScanidsView = function(){
	 var scanids = {};
	 scanids.name = $('#name').val();
	 return scanids;
};



 // function insertscanid(){		
//	var eid=document.getElementById('eid').value;	
//	var name=document.getElementById('name').value;		
	//document.getElementById('name').value=name.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
/*	if(name==""){
	document.getElementById("errormsg").innerHTML="Please Enter Scan ID";
	document.getElementById("errormsg").style.display="block";
	return;
	}
	
	parent.submitForm("ScanIDs!insertScanID",eid,name);
} */
</script>