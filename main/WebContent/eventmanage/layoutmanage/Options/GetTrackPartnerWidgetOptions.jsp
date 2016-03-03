<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.layout.DBHelper"%>

<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
System.out.println("the eventid :"+eventid+widgetid+widgetTitle+"::"+widgettype);
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?DBHelper.getWidgetOptions("0", widgetid):DBHelper.getWidgetOptions(eventid, widgetid);

if(data==null || "".equals(data.trim()))data="{}";
String title=DBHelper.getTitle(eventid,widgetid); 
JSONObject jsonData = new JSONObject(data);

%>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>
<script>
$(function(){


	var getData = function(){
		var data = {};
		return JSON.stringify(data);
	};
	
	var getTitle=function(){
		return $('#trackwidgettitle').val();
		};

		
		
		$('.saveButton').click(function(){
			var savebutton = $(this);
			savebutton.html(props.pg_widgets_saving_lbl);
			savebutton.attr('disabled','disabled');
			var data = getData();
			$('#data').val(data);
			var url = "SaveSettings?type=widgetOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=<%=widgetTitle%>&action=SaveSettings";
			$.ajax({
				type:'POST',
				url:url,
				data:$('#gettrackfrom').serialize(),
				success:function(response){
					if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
					if(response.status=="success") {
						parent.notification(props.widgets_status_msg,"success");
						
						if(savebutton.attr('id').indexOf('Exit')>-1)			
							parent.disablePopup();
							else{
								savebutton.html(props.pg_widgets_save_lbl);
								savebutton.removeAttr('disabled');
							  }
					} else {
						alert(props.pg_widgets_not_respond);
						savebutton.removeAttr('disabled');
					}
				},
				error:function(){
					//alert('no');
				}
			});
		});
			

	$('.savebtn').on('click',function(){
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbls);
		savebutton.attr('disabled','disabled');
		$.getJSON('SaveSettings?type=widgetOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=Tracking Partner',{
			data: parent.convert(getData()),
			title: parent.convert(getTitle())
		}).done(function(response){
			if(response.status=="success") {
				if(savebutton.attr('id').indexOf('Exit')>-1)			
					parent.disablePopup();
					else{
						savebutton.html(props.pg_widgets_save_lbl);
						savebutton.removeAttr('disabled');
					  }
			} else {
				alert(props.pg_widgets_not_respond);
				savebutton.removeAttr('disabled');
			}
		});
	});
	$('#exit').on('click',function() {
		//if(confirm("All changes will be lost. Are you sure?"))
			parent.disablePopup();
			//window.close();
	});



});


</script>

<html>
<body>
<form id="gettrackfrom">
<input type="hidden" name="data" id="data" />

 <div class="col-md-12 col-sm-12 col-xs-12">
 	<div class="col-md-2 col-sm-2 col-xs-12">
 		<label><s:text name="pg.widgets.title" /></label>
 	</div>
 	<div class="col-md-10 col-sm-10 col-xs-12 form-group">
 		<input type="text" size="30" name="title" value="<%=title%>" id="trackwidgettitle">
 	</div>
 	</div>
 	</form>
 	<div class="col-md-12 co-xs-12 col-sm-12 text-center">
		<button id="save" class='btn btn-primary saveButton'><s:text name="pg.widgets.save" /></button>
		<button id="saveExit" class='btn btn-primary saveButton' style="display:none;"><s:text name="pg.widgets.save.exit" /></button>
		<button id="exit" class="btn btn-cancel"><i class="fa fa-times"></i></button>
		<button id="reset" class="btn btn-primary" style="display:none;"><s:text name="pg.widgets.reset" /></button>
 	</div>
 	</body>
 	<script>
 		$('#reset').click(function(){
 			bootbox.confirm(props.pg_widgets_change_lbl, function (result) {
 		        if(result){
 		        	var ref=window.location.href;
 					if(ref.indexOf("&reset=reset")>-1){}else
 					ref=ref+"&reset=reset";
 					window.location.href=ref;  
 		        }
 		        	
 		       });
 		});
 	</script>

</html>