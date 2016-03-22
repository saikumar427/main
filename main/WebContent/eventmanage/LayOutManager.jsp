<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="com.eventbee.layout.LayoutManager"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<s:set name="powertype" value="powertype"></s:set>
<s:hidden name="powertype" id="powertype" /> 
<s:set name="currentlevel" value="currentLevel"></s:set>
<s:hidden value="%{currentlevel}" id="currentlevel" />
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

</head> 
<% String eventid = request.getParameter("eid");%>

<!DOCTYPE html>
<html lang="en">
<div id="layoutwidget" class="layoutwidget"></div>
<div id="layoutwidget2" class="layoutwidget2"></div>
	<head>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="/main/eventmanage/layoutmanage/css/layoutmanage.css">
		<title>Layout Manager</title>

		<script type="text/javascript" src="/main/js/layoutmanage/jquery-ui-1.10.3.js"></script>
		<script type="text/javascript" src="/main/js/layoutmanage/eventbee.js"></script>
		<script>
		var json=${jsonData};
		var added=json.added;
		var isSeatingEvent = json['isSeating'];
		var single_widgets=json['column-single'];
		var wide_widgets=json['column-wide'];
		
		// start for seating event
		/* var existsSingle_widgets='NO';
		var existsWide_widgets='NO';
		var val1 = '';
		var val2 = '';
		for(var i=0;i<single_widgets.length;i++){
			var Objs=single_widgets[i];
			if(Object.keys(single_widgets[i])=='tickets'){
				existsSingle_widgets="YES";
				val1=Objs[Object.keys(single_widgets[i])];
				}
		}
		for(var i=0;i<wide_widgets.length;i++){
			var Objs=wide_widgets[i];
			if(Object.keys(wide_widgets[i])=='tickets'){
				existsWide_widgets="YES";
				val2=Objs[Object.keys(wide_widgets[i])];
				}
		}
		if(isSeatingEvent){
			if(existsSingle_widgets=='NO'){
					wide_widgets = wide_widgets.filter(function(el){
					return el.tickets!==val2;
				});
				single_widgets.push({'tickets':val2});
			};
		} */
		//end for seating event
		
		var narrow_widgets=json['column-narrow'];
		var single_bottom_widgets=json['column-single-bottom'];
		var sync = json.sync;
		var hidewidgets=json['hide-widgets'];
		var themes= json['allThemes'];
		var themeCode = json['themeCodeName'];
		var themeName="";
		for(var i=0;i<themes.length;i++){
			var eachObject=themes[i];
			if(eachObject.themeKey==themeCode){
				themeName=eachObject.themeValue;
				break;
			}
		}
			
		
		$(document).ready(function(){
				$('.layoutTep').show();
				$("#styleLabel").click(function(){
					/* $('#previewPageBtn').hide(); */
					$('.layoutTep').hide();
					$('.styleWidget').show();
					$('.hideShowBtn').show();
					$('.buyerPage').hide();
	        		$("#styleLabel").addClass("btn-default").removeClass("btn-active");
	        		$("#activeLabel").removeClass("btn-default").addClass("btn-active");
	        		$("#buyerLabel").removeClass("btn-default").addClass("btn-active");
	        		globLayoutPage='event-layout';
	        	 });
				$("#activeLabel").click(function(){
					/* $('#previewPageBtn').show(); */
					$('.layoutTep').show();
					$('.styleWidget').hide();
					$('.hideShowBtn').show();
					$('.buyerPage').hide();
            		$("#activeLabel").addClass("btn-default").removeClass("btn-active");
            		$("#styleLabel").removeClass("btn-default").addClass("btn-active");
            		$("#buyerLabel").removeClass("btn-default").addClass("btn-active");
            		globLayoutPage='event-layout';
	        	 });
				$("#buyerLabel").click(function(){
					var eid=<%=eventid%>;
					if($('#currentlevel').val()=='400'){
						$('.layoutTep').hide();
						$('.styleWidget').hide();
						$('.hideShowBtn').hide();
						$('.buyerPage').show();
						$("#activeLabel").removeClass("btn-default").addClass("btn-active");
						$("#styleLabel").removeClass("btn-default").addClass("btn-active");
						$("#buyerLabel").removeClass("btn-active").addClass("btn-default");
					 }else{
						 if($('#powertype').val()=='Ticketing')
								specialFee(eid,'400','BuyerPage','Ticketing');
							/* else
								specialFee(eid,'150','BuyerPage','RSVP'); */
					 }
					globLayoutPage='buyer-layout';
					
				});
				if($('#powertype').val()=='Ticketing'){
					var urlBuyerPage = "/main/eventmanage/BuyerPage?eid="+<%=eventid%>;
					$.ajax({
						url:urlBuyerPage,
						type:'POST',
						success:function(resultBuyerPage){
							if(!isValidActionMessage(resultBuyerPage)) return;
							$('.buyerPage').html(resultBuyerPage);
						},
						error:function(){
							
						}
					});
				}
			});
		
		   var themeDropDown = function(){
			   if($("#eventThemes").is(':hidden'))
	        	 	 $("#eventThemes").show();
	        	  else
	        		  $("#eventThemes").hide();
		   };

		   
		   $(document).on('click', function(e) {
			   if (!$(e.target).is('#btnValue') && !$(e.target).parents().is('#btnValue')) {
			   $('#eventThemes').hide();
			   }
			   });
		   
			/* $(document).on('click','#layout_body',function(){
				$('#eventThemes').hide();
			}); */
		   
		</script>
	</head>
	<s:hidden name="powertype" id="ptype"></s:hidden>
	<s:hidden name="isrecurring" id="isRecurring"></s:hidden>
    <body ><div style="clear:both;"></div>
	<div class="row sticky-out-button pull-right hideShowTheme">
	<div class="col-md-12"> 
		 <div class="pull-right">
			<ul class="dropdown-menu" role="menu" aria-labelledby="eventOptions" id="eventThemes">
			
			</ul> 

			<button style="margin-left: 15px" id="btnValue" class="btn btn-primary" onclick="themeDropDown()"></button>

		 </div> 
	 </div> 
	<div style="clear:both"></div>
</div>
	<div style="background-color:#fff; padding:15px 0px;" id="layout_body">
	<div class="row" style="padding-top: 20px; padding-left: 25px;" >
       	<div class="alert alert-danger" id="errormsg"  style="display: none; width:98%;" ></div>
 	</div>
		
		<div class="col-sm-12 col-xs-12 col-md-12"> 
	    <div class="tab-bottom-gap">
			<div class="center btn-group btn-toggle" data-toggle="buttons">
					<label id="activeLabel" class="optiontype btn btn-default no-radius">
						<input class="datesel" id="active" name="layoutOpt" value="1" type="radio"><s:text name="pg.toggle.layout.lbl"/>
					</label>
					<s:if test="%{#powertype=='Ticketing'}">
						<label id="buyerLabel" class="optiontype btn btn-active no-radius">
							<input class="datesel" id="buyer" name="layoutOpt" value="2" type="radio"><s:text name="pg.toggle.buyer.lbl"/>
							<i class="fa fa-info-circle info" style="cursor:pointer;font-size:16px!important;"id="buyerPageInfo" ></i>
						</label> 
					</s:if>
					<label id="styleLabel" class="optiontype btn btn-active no-radius">
						<input class="datesel" id="all" name="layoutOpt" value="3" type="radio"><s:text name="pg.toggle.style.lbl" />
					</label>
			</div>
	   </div>
    </div>
    <div style="clear:both"></div>
    
    <div class="styleWidget" id="" style="display: none; margin-top: 15px;">
    	<iframe src="" id="styleTab" style="width: 100%; border: none; height: 450px;"></iframe>
    </div>	
    <div class="layoutTep" style="display: none; margin-top: 10px;">
    
    <!-- 'Some changes are in draft,click Save button to save' temporary WE ARE NOT GIVING start -->
     <div class="col-md-12" style="display:none;">
      <div id="statusmsg"></div>
     </div> 
     <!-- 'Some changes are in draft,click Save button to save' temporary WE ARE NOT GIVING end -->
     <div style="clear:both;"></div>
		<div class="wrapper">
			<!-- <div class="settings">
				 <div class="section-main-header">Layout Manager</div> 
				<div class="options col-md-12 col-sm-12 col-xs-12">
					<div class="pull-right">
					</div>
				</div>
				<div style="clear:both;"></div> 
			</div> -->
			
			<%-- <a class="configureHeaderLink col-md-12 col-sm-12 col-xs-12" href="#"><div class="configureHeader"><s:text name="pg.configureheader.lbl"/></div></a> --%>
			
         	<div style="clear:both"></div>
         	
         	 <!-- <div class='col-md-12 col-sm-12 col-xs-12' >
         		<div class="addwidget" id='addwidget-column-single' style='' >
         			<a href="#" class="dropdown-widgets"   >&nbsp;&nbsp;Add a widget</a>
         		</div>
         	</div>  -->
         	<div class="column single col-md-12 col-sm-12 col-xs-12 ui-sortable" id="column-single" style="min-height:30px;">
	         	<div class='insertA'></div>
	         	<div style='height:5px'></div>
         	</div>
			<div style="display:none">
			<!-- ADD WIDGET AT TOP temporary WE ARE NOT GIVING -->
         	
         	
         	<div class='col-md-12 col-sm-12 col-xs-12' >
         		<div class="addwidget" id='addwidget-column-single' style='' >
         			<a href="#" class="dropdown-widgets text-center"><span class="btn-floating " title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a>
         		</div>
         	</div> 
         	
			</div>

         	
         	<!-- <div class='col-md-7 col-sm-7 col-xs-7'>
         		<div class="addwidget" id='addwidget-column-wide' style=' '>
         			<a href="#" class="dropdown-widgets"   >&nbsp;&nbsp;Add a widget</a>
         		</div>
         	</div> --> 
         	 <!-- <div class='col-md-5 col-sm-5 col-xs-5'>
         	<div class="addwidget" id='addwidget-column-narrow' style=''>
         		<a href="#" class="dropdown-widgets"   >&nbsp;&nbsp;Add a widget</a></div>
         	</div> --> 
        	
        	
        	<div class="column wide col-md-7 col-sm-7 col-xs-7 ui-sortable" id="column-wide" style="min-height:30px;">
	        	<div class='insertA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	
        	
        	
        	<div class="column narrow col-md-5 col-sm-5 col-xs-5 ui-sortable" id="column-narrow" style="min-height:30px;">
	        	<div class='insertA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	
        	<div class='col-md-7 col-sm-7 col-xs-7'>
         		<div class="addwidget" id='addwidget-column-wide' style=' '>
         			<a href="#" class="dropdown-widgets text-center"   ><span class="btn-floating" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a>
         		</div>
         	</div>
        	
        	<div class='col-md-5 col-sm-5 col-xs-5'>
         	<div class="addwidget" id='addwidget-column-narrow' style=''>
         		<a href="#" class="dropdown-widgets text-center"   ><span class="btn-floating" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a></div>
         	</div>
        	
        	
        	 
        	<!-- <div class='col-md-12 col-sm-12 col-xs-12'>
        		<div class="addwidget " id='addwidget-column-single-bottom' style=''><a href="#" class="dropdown-widgets">&nbsp;&nbsp;Add a widget</a></div>
        	</div> -->
        	
        	<div class="column bootomsingle col-md-12 col-sm-12 col-xs-12 ui-sortable" id="column-single-bottom" style="min-height:30px;">
	        	<div class='insertA'></div>
	        	<div style='height:5px'></div>
        	</div>
        	<div class='col-md-12 col-sm-12 col-xs-12'>
        		<div class="addwidget " id='addwidget-column-single-bottom' style=''><a href="#" class="dropdown-widgets text-center"><span class="btn-floating" title="<s:text name="pg.add.new.widget.lbl" />"><i class="fa fa-plus"></i></span></a></div>
        	</div> 
        	
        	
        	
			</div>
			
			<div id="toPopup"> 
		        
		      <!--   <a href="javascript:disablePopup();"><img src="http://www.eventbee.com/home/images/close.png" class="imgclose" alt="popupLayout close"></a> -->
				<div id="popup_content">
					
					<iframe src="" frameborder="no" height="565" width="960"></iframe>
				</div>
		    </div>
		   	<div id="backgroundPopup"></div>
		</div>
		
		<div style="clear:both;"></div>
		<div class="text-center hideShowBtn" style="margin-top:10px;">
			<!-- <button  id="previewbtn" class="btn btn-lg btn-primary">Preview&nbsp;&nbsp;>></button> -->
			<%-- <button  id="previewPageBtn" class="btn  btn-primary"><s:text name="global.preview.lnk.lbl" /></button> --%>
			<button  id="saveLayout"  class="btn btn-primary"><s:text name="global.save.btn.lbl" /></button>
			<button  id="resetlastbtn" class="btn btn-primary"><s:text name="global.cancel.btn.lbl" /></button>
		</div>
		<div class="buyerPage" style="display: none; margin-top: 10px;">
			<!-- <iframe id="buyerPageIframe" src="" width="100%" style="height: 430px" frameborder="0"></iframe> -->
		</div>
		
			<script>
			var pType =  $('#ptype').val();
			var isRecurring = $('#isRecurring').val();
			$('#btnValue').html(themeName+'&nbsp;&nbsp;<i class="fa fa-angle-down"></i>');
			var html = '';
			for(var i=0;i<themes.length;i++){
				html=html+' <li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="themeAction(\''+themes[i].themeKey+'\',\''+themes[i].themeValue+'\')">'+themes[i].themeValue+'</a></li>';
			}
			document.getElementById("eventThemes").innerHTML = html;
			
		var themeAction = function(key,name){
			themeDropDown();
			$('#btnValue').html(name+'&nbsp;&nbsp;<i class="fa fa-angle-down"></i>');
			var url = 'LayOutManager!chanagetheme?eid=<%=eventid%>&themecode='+key;
			$.ajax({
				type:'POST',
				url:url,
				success:function(result){
					$('#styleTab').attr('src','/main/eventmanage/layoutmanage/StyleEditor?eid=<%= eventid %>');
					//alert(result);
				}
			});
		};
		
		var loaded=false;
		
		function setIframeHeight(){
			setTimeout(function(){
			//alert("hi this");
			
			var obj = document.getElementById('styleTab');
			obj.style.height = obj.contentWindow.document.body.scrollHeight+'px';
			//alert(obj.style.height);
			},1000);
		}
		
		var divs={"headerDiv":186,"bodyDiv":300,"mainContentDiv":102,"boxDiv":424};
		
		function decIframeHeight(divid){
			setTimeout(function(){
				var obj = document.getElementById('styleTab');
				//alert(obj.contentWindow.document.body.scrollHeight);
				obj.style.height = Number(obj.contentWindow.document.body.scrollHeight-divs[divid])+'px';
				//alert(obj.style.height);
				},900);
		}
		$('#styleLabel').click(function(){
			if(!loaded){
			loaded=true;
			$('#styleTab').attr('src','/main/eventmanage/layoutmanage/StyleEditor?eid=<%= eventid %>');
			}
		});
		
		<%-- $(function(){          
            $('#previewPageBtn').click(function(){
            	$('#previewPage').hide();
            	$('#previewPageBtn').html(props.pg_refresh_preview_lbl);
            	$('html, body').animate({ scrollTop: $("#previewPageBtn").offset().top-65}, 1000);
                $('#loadingpreview').show();
                $('#previewPage').attr('src', '/event?eid=<%=eventid%>&preview=draft');
               loadIframe();
            });
             function loadIframe(){
            	 $('#previewPage').load(function(){
                     $(this).show();
                      $('#loadingpreview').hide();
                  });
             }
            setInterval(function(){
            	//alert($('#previewPage').is(':hidden'));
            	 if($('#previewPage').is(':hidden'))
          			$('#previewPageBtn').html(props.globa_preview);
          		else
          			$('#previewPageBtn').html(props.pg_refresh_preview_lbl);
    			}, 1000);
        }); --%>
			
		var animateDIV = function(){
			$('html, body').animate({ scrollTop: $("#saveLayout").offset().top-65}, 1000);
		}
	
		
		<%-- $(function(){
			 var iFrameLoading=false;
		    $('#previewPage').load(function(){
		    	if(iFrameLoading){
		        	$(this).show();
		        	$('#loadingpreview').hide();
		    	}
		    });
		    
		    $('#previewPageBtn').click(function(){
		    	 if(!iFrameLoading){
		    		    iFrameLoading=true;
		    		 	$('#previewPageBtn').html(props.pg_preview_hide_lbl);
				    	$('html, body').animate({ scrollTop: $("#previewPageBtn").offset().top-65}, 1000);
				    	$('#loadingpreview').show();
				        $('#previewPage').attr('src', '/event?eid=<%=eventid%>&preview=draft');
				        $('#previewPage').hide();
		    	 }else{
		    		 iFrameLoading=false;
		    		 $('#previewPage').attr('src', '');
		    		 $('#previewPageBtn').html(props.pg_refresh_preview_lbl);
		    		 $('#previewPage').hide();
		    		 $('#loadingpreview').hide();
		    	 }
		    });
		}); 
			var sizeIframe = function(){				
				setTimeout(function(){
					var mydiv = $("#previewPage").contents().find("#rootDiv");
					var obj=document.getElementById('previewPage');
					obj.style.height =(mydiv.height()+80)+ 'px';
		       },2000);
				$('#previewPage').hide();
			};--%>
			
		    
			</script>
		<div class="modal" id="myModalLayout" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<script>
							function hidePopup() {
								$('#myModalhtml').hide();
								$('.background').hide();
								$('.container input').removeAttr("tabindex");
								$('.container button').removeAttr("tabindex")
								$('.container a').removeAttr("tabindex");
								$('.container select').removeAttr("tabindex");
								var obj = document.getElementById('popupLayout');
								obj.style.height = obj.contentWindow.document.body.scrollHeight	+ 'px';
							}

							function modalOnLoad() {
								//alert("mu modalonload");
								hidePopup();
							}
						</script>
						<iframe id="popupLayout" src="" width="100%" style="height: 430px"
							frameborder="0" onload="modalOnLoad()"></iframe>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
		  <div class="modal-dialog modal-sm">
		    <div class="modal-content">
		      <input type="text" name="" value="" />Enter
		    </div>
		  </div>
		</div>
		
		</div>
    </body>
   
    <script>
    
    var value = props.bu_pg_tl_tp_msg1_lbl+'<br>'+props.bu_pg_tl_tp_msg2_lbl+'<br>'+props.bu_pg_tl_tp_msg3_lbl;
    $('#buyerPageInfo').tooltipster({
	    fixedWidth:'100px',
	     content: $('<span>'+value+'</span>')
	    }); 
    
    $('#myModalLayout').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$('#myModalLayout').on('hide.bs.modal', function() {
						$('iframe#popupLayout').attr("src", '');
						$('#myModalLayout .modal-body')
								.html('<iframe id="popupLayout" src="" width="100%" style="height:440px" frameborder="0" onload="modalOnLoad()"></iframe>');
					});
	window.resizeIframe = function() {
		var obj = document.getElementById('popupLayout');
		obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
	}
    
    getSingleWidgets(single_widgets,hidewidgets,'column-single');
    getSingleWidgets(wide_widgets,hidewidgets,'column-wide');
    getSingleWidgets(narrow_widgets,hidewidgets,'column-narrow');
    getSingleWidgets(single_bottom_widgets,hidewidgets,'column-single-bottom');

    if(sync=='no'){
		var msg='<div class="alert alert-warning"><ul class="errorMessage" ><li><span><s:text name="pg.somechange.draft.msg" /></span><a class="closebtn" href="javascript:hidestatusmessage()"></a></li></ul></div>';
		$('#statusmsg').html(msg);
		}
	function hidestatusmessage(){
	$('#statusmsg').html("");
}
	

    </script>
    
</html>
