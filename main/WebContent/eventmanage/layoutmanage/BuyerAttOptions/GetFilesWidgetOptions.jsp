<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String eventid = request.getParameter("eid");
String widgetid = request.getParameter("widgetid");
String widgetTitle = request.getParameter("title");
String widgettype = request.getParameter("type");
String layout_type = request.getParameter("layout_type");
String title=BuyerAttHelper.getTitle(eventid,widgetid,layout_type); 
String reset = request.getParameter("reset");
reset=reset==null?"":reset;
String data = "reset".equals(reset)?BuyerAttHelper.getWidgetOptions("0", widgetid,layout_type):BuyerAttHelper.getWidgetOptions(eventid, widgetid,layout_type);

%>
<!DOCTYPE html>
<html>
<head>
<title><%=widgetTitle%></title>
<link rel="stylesheet" href="/main/css/layoutmanage/WidgetOptions.css" type="text/css" />
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap.css">
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<%@include file="../../../getresourcespath.jsp"%>
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<script src="/main/bootstrap/js/custom.js"></script>


<style>
.ui-widget-overlay{
background: #aaaaaa url(images/ui-bg_flat_0_aaaaaa_40x100.png) 50% 50% repeat-x;
opacity: .3;
 }
 .ui-dialog .ui-dialog-titlebar 
{
    height: 20px; /* or whatever you want */
}
.ui-button-text {
    font-size: 15px;
}
.table > thead > tr > th {
    vertical-align: bottom !important;
    border-bottom: 2px solid #e4e4e4 !important;
}
.icon-size{
	font-size: 30px !important;
}
.modal-backdrop{
background-color:transparent;
}
.not-found{
padding-left: 20px;
    font-size: 16px;
    color: #AAA;
}
</style>
</head>
<body>

       	<div class=" alert alert-danger" id="errormsg" style="display: none" ></div>
 


    <form id="filesform">
        <input type="hidden" name="data" id="data" />
        <div id="urls"></div>
        <div id="ext"></div>
        <div id="fid"></div>
        <div class="col-md-12 col-xs-12 col-sm-12 ">
            <div class="col-md-2 col-sm-2 col-xs-12 ">
                <label><s:text name="pg.widgets.title" />
                </label>
            </div>
            <div class="col-md-10 col-sm-10 col-xs-12 form-group">
                <input type="text" name="title" value="<%=title%>" id="filename" size="30" />
            </div>
            
             <div class="col-md-2 col-sm-2 col-xs-12 ">
                <label>File Name
                </label>
            </div>
            <div class="col-md-10 col-sm-10 col-xs-12 form-group">
            <input type="text" name="fname" id="fname"   size="30"/>
            	<a id="coverImg" style="cursor:pointer;    margin-left: 10px;"><span class="sm-font">Upload File</span></a> 
            	<span id="docname" style="margin-left: 10px;"></span>  
            </div>
            
            
            
            
            
            
            
            <div class="col-md-2 col-sm-2 col-xs-12 ">
            	 <label>Description
                </label>
            </div>
            

            <div class="col-md-10 col-sm-10 col-xs-12 form-group">
           		<textarea rows="4" cols="50" name="fdesc" id="fdesc" ></textarea>
                <!-- <input type="text" name="fdesc" id="fdesc" size="30" placeholder="File Description"/> -->
            </div>

            <%-- <div class="col-md-2 col-sm-2 col-xs-12">
                <a id="coverImg" style="cursor:pointer"><span class="sm-font"><s:text name="pg.upload.image.lbl" /></span></a>
                <button class="btn btn-primary" id="coverImg"><s:text name="pg.upload.image.lbl" />
                    </button>
            </div> --%>
            
        </div>
    </form>

    <div class="col-md-12 col-sm-12 col-xs-12 text-center">
        <button class="btn btn-primary saveButton" id="save"><s:text name="pg.widgets.save" />
        </button>
        <button class="btn btn-cancel" id="exit"><i class="fa fa-times"></i>
        </button>
    </div>
	<br>
	 <div class="col-md-12 col-sm-12 col-xs-12" id="tab1" style="display:none;">
        <table class="table table-hover " >
            <thead  >
           <tr> <th>
            	<div class="col-sm-3 col-md-3 col-xs-3 text-center">File Name</div>
            	<div class="col-sm-5 col-md-5 col-xs-5 text-center">File Description</div>
            	<div class="col-sm-2 col-md-2 col-xs-2 text-right">Type</div>
            	<div class="col-sm-2 col-md-2 col-xs-2 text-center"></div>
            </th></tr></thead>
            <tbody id='tabl1'>


            </tbody>
        </table>
    </div>

    <div class="modal fade" id="myModalStyle" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"><b><s:text name="pg.upload.file.lbl" /></b></h4>
                </div>
                <div class="modal-body">
                    <!--  style="height: 300px; !importanat" -->
                    <iframe src="" style="border:none; width:100%; "  id="imageUploadIframe"></iframe>
                </div>
                <div class="modal-footer" style="display:none;">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>


   
</body>

<script>
buildtable();
var address='<%=resourceaddress%>';
var eid=<%=eventid%>;
$(document).on('mouseover','tr',function() {
	$(this).find('.hideThis a').show();
	});
$(document).on('mouseout','tr',function() {
	$(this).find('.hideThis a').hide();
	});
var getData = function(){
	var data={};
	data['filename']=$('#fname').val();
	data['filedesc']=$('#fdesc').val();    
	data['url'] =$('#urls').val();
	data['extension'] =$('#ext').val(); 
	data['fileid'] =$('#fid').val(); 
	return JSON.stringify(data);
};
var getTitle=function(){
	var title=$('#tkttitle').val();
	return title;
};



$('.saveButton').click(function(){
	var savebutton = $(this);
	savebutton.html(props.pg_widgets_saving_lbl);
	savebutton.attr('disabled','disabled');
	
	$('#errormsg').hide();
	$('#errormsg').html('');
	
	var docname=$('#fname').val();
	var docpath=$('#urls').val();
	
	var flag=true;
	var msg="";
	if(docname.trim()=='' || docname.trim()==null){
		flag=false;
		$('#errormsg').append('<ul class="errorMessage">  <li><span> File Name is Empty</span></li> </ul>');
	}
	if(docpath.trim()=='' || docpath.trim()==null){
		flag=false;
		$('#errormsg').append('<ul class="errorMessage">  <li><span>Please upload File</span></li> </ul>');
	}
	if(flag){
		var data = getData();
		$('#data').val(data);
			$('#fname').val('');	$('#fdesc').val('');	$('#urls').val('');
		var url = "SaveSettings?type=BuyerAttOptions&eid=<%=eventid%>&widgetid=<%=widgetid%>&ref_title=<%=widgetTitle%>&action=SaveSettings&layout_type=<%=layout_type%>";
		$.ajax({
			type:'POST',
			url:url,
			data:$('#filesform').serialize(),
			success:function(response){
				if(response.status=="success") 
				{
					parent.notification(props.widgets_status_msg,"success");
					$('#docname').html('');
					if(!isValidActionMessageIFRAME(JSON.stringify(response))) 
						return;
					if(savebutton.attr('id').indexOf('Exit')>-1)			
						parent.disablePopup();
					else{
						savebutton.html(props.pg_widgets_save_lbl);
						savebutton.removeAttr('disabled');
						buildtable();
					}
				} 
				else 
				{
					$('#errormsg').show();
					$('#errormsg').append('<ul class="errorMessage">  <li><span>'+props.pg_widgets_not_respond+'</span></li> </ul>');
					savebutton.removeAttr('disabled');
					savebutton.html(props.pg_widgets_save_lbl);
				}
			},
			error:function(){
				//alert('no');
			}
		});
		}
	else
		{
		$('#errormsg').show();
		savebutton.html("Save");
		savebutton.removeAttr('disabled');
		}
	
	
	
});

function buildtable(){ 
	 var url = '../BuyerPage!getFilesData?eid='+<%=eventid%>;
	$.ajax({
		type:'POST',
		url:url,
		success:function(response)
		{
			constructtable(response);
		}
	}); 
}

var eid = '${eid}';
$('#coverImg').click(function(){
	url = "/main/membertasks/FileUpload?type=filesupload&eid="+eid;
	$('#imageUploadIframe').attr("src", url);
	$('iframe#imageUploadIframe').css("height", "114px");
	$('#myModalStyle').modal();
	$('#myModalStyle').modal('show');
	$('#myModalStyle').modal();
	$('.modal-backdrop').css("background-color","transparent");
});

$('#exit').on('click',function() {
	//if(confirm("All changes will be lost. Are you sure?"))
		parent.disablePopup();
		//window.close();
});
function closeThis(){
	parent.$('#myModalStyle').modal('hide');
}

function closeFileUploadWindow(path,extension,fileid,filename){
	$('#myModalStyle').modal('hide');
	$('#urls').val(path);
	$('#ext').val(extension);
	$('#fid').val(fileid); 
	$('#docname').html("");
	$('#docname').html(filename);
	//alert(filename);
}


function deleteFile(fileid,eid){
	bootbox.confirm("Do you want to Delete File?",function(result)
			{
			if(result)
			{
				//console.log(fileid+"...."+eid);
				//console.log("in delete");
				 var url = '../BuyerPage!deleteFile?eid='+<%=eventid%>+'&fileid='+fileid;
				// console.log(url);
					$.ajax({
						type:'POST',
						url:url,
						success:function(response)
						{
							constructtable(response);
						}
					});
			}
		
			});
}

function closepopup(){
	$('#myModalStyle').modal('hide');
	
}


function constructtable(response){ 
	var obj=JSON.parse(response);
	var data="";
	var type='';
	var count = obj.files.length;
	$('#tab1').show();
	if(count==0)
		$('#tab1').hide();
	
		
		$.each(obj.files, function(key, valueobj){
			if('def_widget_options'==valueobj.type){
				//data +='<tr><td><div class="not-found">No upload files to show.</div></td></tr>';
				$('#tab1').hide();
			}else{
				if('.xlsx'==valueobj.extension || '.csv'==valueobj.extension || '.xls'==valueobj.extension)
					type='fa fa-file-excel-o';
				if('.doc'==valueobj.extension || '.docx'==valueobj.extension)
					type='fa fa-file-word-o';
				if('.ppt'==valueobj.extension)
					type='fa fa-file-powerpoint-o';
				if('.pdf'==valueobj.extension)
					type='fa fa-file-pdf-o';
				if('.txt'==valueobj.extension)
					type='fa fa-file-text-o';
				if('.png'==valueobj.extension || '.gif'==valueobj.extension || '.jpg'==valueobj.extension || '.jpeg'==valueobj.extension || '.bmp'==valueobj.extension)
					type='fa fa-picture-o';
				data += '<tr id="'+ valueobj.fileid +'">   <td> <div class="row">';
				data += '<div class="col-sm-3 col-md-3 col-xs-3 text-center"> '+ valueobj.filename+'</div>';
				data += '<div class="col-sm-5 col-md-5 col-xs-5 text-center"> '+ valueobj.filedesc+'</div>';
				data += '<div class="col-sm-2 col-md-2 col-xs-2 text-right"> <i class="'+type+' icon-size"></i></div>';
				data += '<div class="col-sm-2 col-md-2 col-xs-2  hideThis sm-font" ><a href="javascript:;" onClick=deleteFile(\''+valueobj.fileid+'\','+eid+') style="display:none;"><i class="fa fa-trash-o"></i></a></div> ';
				data += '</div> </td><tr>';
			}
		}); 
		$('#tabl1').html("");
		$('#tabl1').html(data); 
		
}


</script>
</html> 
