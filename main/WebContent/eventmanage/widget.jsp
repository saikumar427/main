<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<%@taglib uri="/struts-tags" prefix="s" %>

<div class="col-xs-12 col-sm-12">
<s:form name="widgetform"  method="post" id="widgetform">
<div class="row">
	<div class="col-md-8">
	<div id="widgeterrormessages"></div>
	<s:hidden name="eid"></s:hidden>
	<s:hidden name="trackcode"></s:hidden>
	</div>
	<div>
	<s:fielderror cssErrorClass="errorMessage">
	</s:fielderror>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<h5><s:text name="turl.widget.code.lbl"/></h5>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<textarea cols="90" rows="7"  class="form-control" onClick="this.select()"><script type='text/javascript' language='JavaScript' src='<s:text name='eventData.serverAddress'></s:text>/home/js/widget/eventregistration.js'></script><iframe  id='_EbeeIFrame' name='_EbeeIFrame'  src='<s:text name='eventData.serverAddress'></s:text>/eregister?eid=${eid}&track=${trackcode}&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='700'></iframe>
	   </textarea>
	</div>	
</div>
</s:form>
<div class="col-sm-12">
	<div class="text-center">
		<input type="button" value="Close"  onclick="parent.closepopup();" class="btn btn-primary">
	</div>
</div>

</div>