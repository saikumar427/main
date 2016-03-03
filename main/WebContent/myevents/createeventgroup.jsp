<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
<style>
body {
margin-top: 0px;
}
 .moves-arrow{
cursor:pointer !important;
 font-size: 15px !important;
 padding: 1px !important;
}
</style>
</body>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
      <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
       <link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
    </head>
      <body>
      <div class="alert alert-danger" style="display:none;"></div>
      <form id="createeventsgroup" action="CreateEventGroup!saveEventGroup" method="post" name="createeventsgroup">
       <div class="container">
            <div class="row">
               <s:hidden name="purpose"></s:hidden>
				<s:hidden name="grpevent.eventgroupid"></s:hidden>
				    <label for="photoURL" class="col-xs-2"><s:text name="mg.title.lbl"></s:text></label>
				    <div class="col-xs-10">
				     <s:textfield id="grpevttitle" name="grpevent.title" theme="simple" size="60" cssClass="form-control"/>
				    </div>
             	</div>  
             	<br/>
             	<div class="row">
				    <label for="photoURL" class="col-xs-12"><s:text name="mg.select.events.lbl"></s:text></label><br/>
             	</div>
             	<div class="row">
             	<div class="col-xs-5">
				    <s:select name="selevents" cssClass="form-control" list="grpevent.events" listKey="key" listValue="value" 
					multiple="true" size="10" theme="simple"/>
				</div>
				<div class="col-xs-1"><br/><br/><br/>
				<!-- <input class="form-control" type="button" value="&nbsp;&gt;&nbsp;"
			 onclick="moveOptions(this.form.selevents, this.form.selgroupevents);" style="width:45px;"/>
			<input class="form-control" type="button" value="&nbsp;&lt;&nbsp;"
			 onclick="moveOptions(this.form.selgroupevents, this.form.selevents);" style="width:45px;"/> -->
			 
			 <i class="fa fa-arrow-right moves-arrow" title="Move Up" onclick="javascript:moveOptions(document.createeventsgroup.selevents, document.createeventsgroup.selgroupevents);"  ></i>
					<br/>
					<!-- <img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selgroupevents'))"> -->
					<i class="fa fa-arrow-left moves-arrow" title="Move Up" onclick="javascript:moveOptions(document.createeventsgroup.selgroupevents, document.createeventsgroup.selevents);"  ></i>
			 
				</div>
				<div class="col-xs-5">
				  <s:select cssClass="form-control" name="selgroupevents" list="grpevent.groupEvents" listKey="key" listValue="value" 
	multiple="true" size="10" theme="simple" />
				</div>
					<div class="col-xs-1"><br/><br/><br/>
					<!-- <img src="../images/up.gif" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selgroupevents'))"> -->
					<i class="fa fa-arrow-up moves-arrow" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selgroupevents'))"  ></i>
					<br/>
					<!-- <img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selgroupevents'))"> -->
					<i class="fa fa-arrow-down moves-arrow" title="Move Up" onclick="javascript:ManageQuestion_mvDnOption(document.getElementById('selgroupevents'))"  ></i>
					</div>
             	</div>  
            </div>
            <br/>
            </form>
             <!-- <div class="form-group">
                            <div class="col-sm-6" style="line-height: 54px; text-align: center;">
                                <button id="createevtgrp" class="btn btn-primary">Submit</button>
                            </div>
                        </div> -->
                        
                        <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="button" class="btn btn-primary" id="createevtgrp"><s:text name="global.submit.btn.lbl"></s:text></button>
                            <button class="btn btn-cancel" onclick="closepopup();"><s:text name="global.cancel.btn.lbl"></s:text></button>
                        </div>
                    </div>  
                        
                        
      </body>
      <script src="/mainboot/bootstrap/js/bootstrap.js"></script>
      <script src="/mainboot/bootstrap/js/jquery-1.11.2.min.js"></script>
      <link type="text/css" rel="stylesheet" href="/mainboot/bootstrap/css/bootstrap.css" />
 	  <link type="text/css" rel="stylesheet" href="/mainboot/bootstrap/css/custom.css" />
      
      <%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> --%>
    
      <%-- <script src="/mainboot/js/jquery.ezpz_hint.js"></script> --%>
    <script>
    
    
    $(document).ready(function() {
    	
        
        
        
        $('#createevtgrp').click(function(){
        $('.alert').hide();
    	var title=$('#grpevttitle').val();
    	if(title==''){
    		parent.$('iframe#popup').css("height","450px"); 
        	$('.alert').show();
        	$('.alert').html('Title is Empty.');
        	return;
        	}
    	var selectBox = document.getElementById("selgroupevents");
    	for ( var i = 0; i < selectBox.options.length; i++) {
    		var obj1 = createHiddenTextElement(i, "value",
    				selectBox.options[i].text);
    		var obj2 = createHiddenTextElement(i, "key", selectBox.options[i].value);
    		$('#createeventsgroup').append(obj1);
    		$('#createeventsgroup').append(obj2);
    	}
		var url = "CreateEventGroup!saveEventGroup";
		 $.ajax({
			method:'POST',
			data:$('#createeventsgroup').serialize(),
			url:url,
			success: function( t ) {
				 if(!parent.isValidActionMessage(t)) return;
			 	   /* parent.location.reload(true);   
			 	  closeeventspopup();  */
			 	 var purpose='${purpose}';
			 	 
			 	  if(purpose=='edit')
			 		parent.rebuildsingletable();
			 	  else
				 	parent.rebuildEVTable(); 
					} 
						}); 
        });
    });

     function closepopup(){
    	 var gid='${grpevent.eventgroupid}';
         var purpose='${purpose}';
    	 
    	 if(purpose=='edit')
    		 parent.closesingleEGpopup();
    	 else
    	 	parent.closeeventspopup();
    	/* parent.$('#myModal').modal('hide'); */
    } 
    

    function createHiddenTextElement(index,etype,val){
    	var pname=document.createElement("input");
    	pname.type="hidden";
    	pname.name="optionsList["+index+"]."+etype;	
    	pname.value=val;
    	return pname;
    }
    function moveOptions(theSelFrom, theSelTo)
    {
      var selLength = theSelFrom.length;
      var selectedText = new Array();
      var selectedValues = new Array();
      var selectedCount = 0;
      
      var i;
      for(i=selLength-1; i>=0; i--)
      {
        if(theSelFrom.options[i].selected)
        {
          if(theSelFrom.options[i].text=="Attendee Name"){
        	  return;
          }
          selectedText[selectedCount] = theSelFrom.options[i].text;
          selectedValues[selectedCount] = theSelFrom.options[i].value;
          deleteOption(theSelFrom, i);
          selectedCount++;
        }
      }
     
      for(i=selectedCount-1; i>=0; i--)
      {
        addOption(theSelTo, selectedText[i], selectedValues[i]);
      }
      
     // if(NS4) history.go(0);
    }

    function addOption(theSel, theText, theValue)
    {
      var newOpt = new Option(theText, theValue);
      var selLength = theSel.length;
      theSel.options[selLength] = newOpt;
    }

    function deleteOption(theSel, theIndex)
    { 
      var selLength = theSel.length;
      if(selLength>0)
      {
        theSel.options[theIndex] = null;
      }
    }

    function ManageQuestion_mvUpOption(elem) {

    	selindex = elem.selectedIndex;
    	if (selindex <= 0) {
    		return;
    	}
    	temp = elem.options[selindex].text;
    	val = elem.options[selindex].value;
    	elem.options[selindex].text = elem.options[selindex - 1].text;
    	elem.options[selindex].value = elem.options[selindex - 1].value;
    	
    	elem.options[selindex - 1].text = temp;
    	elem.options[selindex - 1].value = val;
    	elem.selectedIndex = selindex - 1;
    }

    function ManageQuestion_mvDnOption(elem) {

    	selindex = elem.selectedIndex;
    	if (selindex == elem.length - 1) {
    		return;
    	}
    	temp = elem.options[selindex].text;
    	val = elem.options[selindex].value;
    	elem.options[selindex].text = elem.options[selindex + 1].text;
    	elem.options[selindex].value = elem.options[selindex + 1].value;
    	
    	elem.options[selindex + 1].text = temp;
    	elem.options[selindex + 1].value = val;
    	elem.selectedIndex = selindex + 1;
    }
    </script>
      </html>