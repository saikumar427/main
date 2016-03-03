<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%-- <script type="text/javascript" src="/main/js/layoutmanage/jquery-ui-1.10.3.js"></script>
<link href="/main/bootstrap/css/custom.css" rel="stylesheet">
<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" /> --%>
<%-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/main/eventmanage/layoutmanage/css/layoutmanage.css"> --%>
</head>
<s:hidden name="powertype" id="ptype"></s:hidden>
<s:hidden name="isrecurring" id="isRecurring"></s:hidden>
<body>
	<!-- <div class="col-md-12 col-sm-12 col-xs-12 row" style="background-color:#fff; padding:15px 0px;"> -->
         	
         	<div class="columnBA single col-md-12 col-sm-12 col-xs-12 ui-sortable" id="column-single-BA" style="min-height:30px;">
	         	<div class='insertA-BA'></div>
	         	<div style='height:5px'></div>
         	</div>
			<div style="display:none">
         	<div class='col-md-12 col-sm-12 col-xs-12' >
         		<div class="addwidgetBA" id='addwidget-column-single-BA' style='' >
         			<a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA " title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a>
         		</div>
         	</div> 
			</div>
        	
        	<div class="columnBA wide col-md-7 col-sm-7 col-xs-7 ui-sortable" id="column-wide-BA" style="min-height:30px;">
	        	<div class='insertA-BA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	<div class="columnBA narrow col-md-5 col-sm-5 col-xs-5 ui-sortable" id="column-narrow-BA" style="min-height:30px;">
	        	<div class='insertA-BA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	
        	<div class='col-md-7 col-sm-7 col-xs-7'>
         		<div class="addwidgetBA" id='addwidget-column-wide-BA' style=' '>
         			<a href="#" class="dropdown-widgets-BA text-center"   ><span class="btn-floating-BA" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a>
         		</div>
         	</div>
        	
        	<div class='col-md-5 col-sm-5 col-xs-5'>
         	<div class="addwidgetBA" id='addwidget-column-narrow-BA' style=''>
         		<a href="#" class="dropdown-widgets-BA text-center"   ><span class="btn-floating-BA" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a></div>
         	</div>
        	
        	<div class="columnBA bootomsingle col-md-12 col-sm-12 col-xs-12 ui-sortable" id="column-single-bottom-BA" style="min-height:30px;">
	        	<div class='insertA-BA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	<div class='col-md-12 col-sm-12 col-xs-12'>
        		<div class="addwidgetBA" id='addwidget-column-single-bottom-BA' style=''><a href="#" class="dropdown-widgets-BA text-center"><span class="btn-floating-BA" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a></div>
        	</div> 
        	
        	<div style="clear:both;"></div>
        	<div class="text-center"><button class="btn btn-primary" id="saveLayoutBA"><s:text name="global.save.btn.lbl" /></button>
        	<button class="btn btn-primary" id="resetLayoutBA"><s:text name="global.cancel.btn.lbl" /></button>
        	</div>
        	
	<!-- </div> -->
<script>
var json=${jsonData};
var addedBuyer=json.addedBuyer;
</script>
<script type="text/javascript" src="/main/js/layoutmanage/buyer_att_page.js"></script>
<script>
var single_widgets=json['column-single'];
var wide_widgets=json['column-wide'];
var narrow_widgets=json['column-narrow'];
var single_bottom_widgets=json['column-single-bottom'];
var sync = json.sync;
var layout_type=json.layout_type;
var hidewidgets=json['hide-widgets'];
getSingleWidgetsBuyer(single_widgets,hidewidgets,'column-single-BA');
getSingleWidgetsBuyer(wide_widgets,hidewidgets,'column-wide-BA');
getSingleWidgetsBuyer(narrow_widgets,hidewidgets,'column-narrow-BA');
getSingleWidgetsBuyer(single_bottom_widgets,hidewidgets,'column-single-bottom-BA');
var pType =  $('#ptype').val();
</script>
</body>
</html>