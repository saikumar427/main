<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link rel='stylesheet' type='text/css' href='http://eventbee.com/home/css/pagination.css' />
<script type="text/javascript" src="http://eventbee.com/home/js/customfonts/cufon-yui.js"></script>
<script type='text/javascript' language='JavaScript' src='/main/js/jquery.pagination.js'></script>
<script src="http://eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
<%-- <script type="text/javascript">

Cufon.replace('.featuredeventsheader h2');</script> --%>

</head>
<body>

<style>

.featuredeventsheader h2 {
margin-top:0px;
margin-bottom:0px;
font-family: Myraid Pro;
color:#3a3a3a;
font-size: 18px;
font-weight: 1200;
}
  #promotionsbox{
  font-family:Lucida Grande,Lucida Sans Unicode,sans-serif;
font-size:12px;
color: black;
line-height: 20px;
  }

#promotionsbox a{
color: blue;
text-decoration:none;
}

  .pg-normal {
                
                font-weight: normal;
                text-decoration: none;    
                cursor: pointer;
				background: none repeat scroll 0 0 #FFFFFF;
				border-color: #999999;
				color: #999999;
				border: 1px solid 
				margin-bottom: 5px;
				margin-right: 5px;
				min-width: 1em;
				padding: 0.3em 0.5em;
				text-align: center;
            }
            .pg-selected {
                font-weight: bold;        
                cursor: pointer;
				background: none repeat scroll 0 0 #2266BB;
				border: 1px solid #AAAAEE;
				color: #FFFFFF;
    margin-bottom: 5px;
    margin-right: 5px;
    min-width: 1em;
    padding: 0.3em 0.5em;
    text-align: center;
            }
  .taskcontent .td{
  padding-bottom:10px; 
  }

.searchform {
    background: transparent;
    border: 0px solid rgba(0, 0, 0, 0.25);
    border-radius: 6px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.4) inset, 0 1px 0 rgba(255, 255, 255, 0.1);
    color: #FFFFFF;
    position: relative;
	#padding:3px;
    margin-bottom:20px;
}

.searchform .icon-search {
    background: url(/main/images/home/icon_search.png) no-repeat;
	border: 0px;
    bottom: 3px;
    color: #FFFFFF;
    padding: 10px;
    position: absolute;
    right: 3px;
    cursor:pointer;
    top:100px;
	top:11px;
}

.visuallyhidden {
    border: 0 none;
    clip: rect(0px, 0px, 0px, 0px);
    height: 1px;
    margin: -1px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    width: 1px;
}


.searchform input {
    background: transparent;
	border: 0px solid #ccc;
    box-shadow: none;
    font: 15px "Lucida Grande",Lucida,Verdana,sans-serif;
    margin: 0;
    padding: 5px 10px;
    text-decoration: none;
   width: 100%;
   margin:4px;
}

.searchform input:-moz-placeholder {
    color: #ccc;
}

.input-group {
border-top-left-radius: 0px;
}
 </style>


<div class="row" style="margin-left:-30px;padding-top:25px;">
<div class="col-md-12">


<form name="searchevent" action="search" method="post" class="searchform" id="eventsearchform">

<div class="input-group">
<input type="text"  name="searchcontent" id="val" style="color:#333" placeholder="Enter event name or venue...">
<span class="input-group-btn">
<button class="btn btn-primary" type="submit" id="searchevtbtn" >Find Events</button>
</span>
</div>




<!--<button class="icon-search" onclick="searchevt();" type="button"></button>
<label>
		<span class="visuallyhidden">Enter Event Name or Venue</span>
		<input type="text" placeholder="Enter event name or venue!" value="" name="searchcontent" id="val" size="150">
	</label>
--></form>


<div class="reloadData">


</div></div>
</div>


</body>
</html>

<script>

$.fn.serializeObject = function(){
	  var o = {};
	  var a = this.serializeArray();
	  $.each(a, function() {
	      if (o[this.name] !== undefined) {
	          if (!o[this.name].push) {
	              o[this.name] = [o[this.name]];
	          }
	          o[this.name].push(this.value || '');
	      } else {
	          o[this.name] = this.value || '';
	      }
	  });
	  return o;
	};
function sort(){
var sortkey=document.getElementById('sort').value;
//document.searchevent.action='search!searchResult?sortedtype='+sortkey;
//document.searchevent.action='search?sortedtype='+sortkey;
//document.searchevent.submit();
 var subFormData=$('#eventsearchform').find("select, textarea, input").serializeObject();
$.ajax({
	url : 'search!searchResult?sortedtype='+sortkey,
	type : 'POST',
	data : subFormData,
   success: function(t){
   	$('.reloadData').html(t);
   	}
   }); 
}


/*if(document.getElementById('pageNavPosition2')){
if(document.getElementById("eventsearch")){
        var pager = new Pager('eventsearch',20); 
        pager.init(); 
        //pager.showPageNav('pager', 'pageNavPosition'); 
         pager.showPageNav('pager', 'pageNavPosition2'); 
         pager.showPage(1);
		}
		}*/

$("#eventsearchform").submit(function(event) {
	event.preventDefault();
	var str = document.getElementById('val').value;
	str=str.replace(/^\s+|\s+$/g,'');
	if(str.length<=2){
	alert("Please Enter atleast 3 characters to search");
	} else{
		document.getElementById('val').value=str;
		//alert(str);
		var subFormData=$('#eventsearchform').find("select, textarea, input").serializeObject();
		 $.ajax({
			url : 'search!searchResult',
			type : 'POST',
			data : subFormData,
		    success: function(t){
		    	$('.reloadData').html(t);
		    	}
		    }); 
		
		}
});		
		
function searchevt(){
var str=document.getElementById('val').value;

str=str.replace(/^\s+|\s+$/g,'');
if(str.length<=2){
alert("Please Enter atleast 3 characters to search");
}else{
	document.getElementById('val').value=str;
	//alert(str);
	var subFormData=$('#eventsearchform').find("select, textarea, input").serializeObject();
	 $.ajax({
		url : 'search!searchResult',
		type : 'POST',
		data : subFormData,
	    success: function(t){
	    	$('.reloadData').html(t);
	    	}
	    }); 
	
	}
}

function call(val){
document.getElementById('stype').value=val;
document.search.submit();


}

//var result_label = localStorage.getItem("result_label");

call2();
function call2(){
var serach='${searchcontent}';
document.getElementById('val').value=decodeURIComponent(escape(serach));
//document.getElementById('val').value=result_label;
//delete localStorage.result_label;
if(decodeURIComponent(escape(serach))==''){
	//$("#eventsearchform").submit();
}else{
	$("#eventsearchform").submit();
}


}

function openReg(link){
window.open(link,'_blank');
}

</script>

