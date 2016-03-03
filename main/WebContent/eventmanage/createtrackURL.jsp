<%@taglib uri="/struts-tags" prefix="s" %>
<div class="showThis"></div>
<s:hidden name="eid" id="eid"></s:hidden>
<div class="col-sm-12 col-xs-12 col-md-12 well" style="margin-top:15px;">
<div class="col-sm-12">
	<div id="errormsg"  class="alert alert-danger" style="display:none"></div>
</div>
	<div class="col-sm-4 col-xs-12">
		<input type="text" name="name" placeholder="<s:text name="turl.name.ph"/>" id="name" onkeypress="searchKeyPress(event);" onkeyup="gettrackname()"; onkeydown="gettrackname()"; size="30" class="form-control"/>
	</div>
	<div class="col-sm-5 col-xs-12 sm-font" style="margin-top: 7px;">
		<s:property value="TrackURLData.eventURL"/><label name="url" id="url"></label>
	</div>
	<div class="col-sm-3 center">
		<input type="button" class="btn btn-primary addTrackUrl" value="<s:text name="global.add.btn.lbl"/>" onclick="submittrackurl();" />
		<button type="button" class="btn btn-cancel" id="cancelTrackUrl"><i class="fa fa-times"></i></button>
	</div>
</div>


<script>

$(document).ready(function(){
	$('#createTracking').prop("disabled",true);
});
$('#cancelTrackUrl').click(function(){
	closeWell();
});
function gettrackname(){
	document.getElementById('url').innerHTML=document.getElementById('name').value;	
	parent.resizeIframe();
}

var j=0;
function submittrackurl()
{
	++j;
if(j==1)
inserttrackingurl();
}

function inserttrackingurl(){
	var eid=document.getElementById('eid').value;	
	var name=document.getElementById('name').value;
	name=name.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	name=name.replace(/[^a-z0-9]|\s+|\r?\n|\r/gmi, "");
	document.getElementById('name').value=name;
	if(name==""){
	document.getElementById("errormsg").innerHTML=props.turl_enter_name_msg;
	document.getElementById("errormsg").style.display="block";
	$('#url').html('');
	j=0;
	return;
	} 
	submitForm("IntegrationLinks!insertTrackURL",eid,name);
}

function submitForm(url,eid,name){
	showProcessing('forload');
	url=url+"?eid="+eid+"&name="+name;	
	$.ajax({
		url: url,
		type: 'get',
		success: function(transport){
			var result=transport;
			console.log(result);
			if(result.indexOf("Name Exists")>-1){					
				document.getElementById("errormsg").innerHTML=props.turl_name_already_exists_errmsg;	
				document.getElementById("errormsg").style.display="block";
				//parent.resizeIframe();
				hideProcessing();
			    j=0;
			}else if(result.indexOf("spacesInUrl")>-1){
					document.getElementById("errormsg").innerHTML=props.global_use_alphanumeric_chars_msg;
					document.getElementById("errormsg").style.display="block";
					//parent.resizeIframe();
					 hideProcessing();
				//setErrorMsg("<li>Use alphanumeric characters only</li>");	
			    j=0;
			}else{						
				getReloadedTrackURLData('add');
				$('html, body').animate({ scrollTop: $("#createTracking").offset().top-scrollTo}, 1000);
				 hideProcessing();
			}
		}
	});		
}
</script>
