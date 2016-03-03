<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="/main/css/bootstrap/bootstrap.min.css" />
  <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/animatecss/3.1.0/animate.min.css" /> 
   <!--  <link rel="stylesheet" type="text/css" href="/main/css/bootstrap/offcanvas.css" /> -->
   <%
   
   
   %>
     <style>
 
        .panel-title a {
            display: block;
            padding: 10px 15px;
            margin: -10px -15px;
            text-decoration: none
        }
        .panel-group table {
            margin-bottom: 0;
        }
        .panel-group tr a {
            display: block;
        }
        .panel-group tr a {
            display: block;
        }
        #graphContainer {
            width: 775px;
            height: 470px;
            border-bottom: 2px solid #5388c4;
            border-left: 2px solid #5388c4;
            padding: 30px
        }
        .container{
         margin-left: inherit;
    margin-right: auto;
    padding-left: 2px;
    padding-right: 15px;
        }
        #graphContainer img {
            position: absolute;
            border: 2px solid #5388c4;
            top: 10%;
            left: 10%;
            z-index:9998;
        }
        #graphContainer img:hover {
            border: 2px solid #ff9933;
            cursor: pointer;
        }
        .tooltip{
        z-index: 9999;
        }
         .time {
            /* text-align: right;
 */            height: 395px;
        }
        .time span,#dates span {
            position: absolute;
        }
        .container {
    width: 830px;
}
        
    </style>
    

  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
	

<div class="container">
<div class="row">

 <div class="col-md-1 col-sm-1 col-xs-1 time" style="width:7.65%;left: 10px">
                        <span style="top:7%;">12 AM</span>
                       <%--  <span style="top:7.91666666667%;">22</span> --%>
                        <span style="top:25.8333333333%;">8 PM</span>
                        <%-- <span style="top:23.75%;">18</span> --%>
                        <span style="top:44.6666666667%;">4 PM</span>
                        <%-- <span style="top:39.5833333333%;">14</span> --%>
                        <span style="top:62.7%;">12 PM</span>
                        <%-- <span style="top:55.4166666667%;">10</span> --%>
                        <span style="top:81.3333333333%;">8 AM</span>
                        <%-- <span style="top:71.25%;">6</span> --%>
                        <span style="top:99.1666666667%;">4 AM</span>
                        <%-- <span style="top:87.0833333333%;">2</span>
                        <span style="top:95%">0</span> --%>
                    </div>
                    <div class="col-md-11 col-sm-11 col-xs-11">

                        <div id="graphContainer">
                          <!--  <img src="http://graph.facebook.com/zuck/picture?type=large" title="This is one" class="img-thumbnail img-circle" width="50" height="50" id="one">
                            <img src="http://graph.facebook.com/raghavcreddy/picture?type=large" title="This is two" class="img-thumbnail img-circle" width="80" height="80" id="two">
                            <img src="http://graph.facebook.com/517267866/picture?type=large" title="This is three" class="img-thumbnail img-circle" width="100" height="100" id="three">
                            <img src="http://graph.facebook.com/johnlennon/picture?type=large" title="This is four" class="img-thumbnail img-circle" width="70" height="70" id="four">
                            <img src="http://graph.facebook.com/BenjaminFranklinAuthor/picture?type=large" title="This is five" class="img-thumbnail img-circle" width="60" height="60" id="five">
                        --> 
                        </div>

<div id="dates">
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 20</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 22</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 24</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 26</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 28</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Sep 30</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 1</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 3</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 5</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 7</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 9</div>
                        <div class="col-md-1 col-sm-1 col-xs-1">Oct 11</div>
</div>


                    </div>
                    
                    <!-- <div class="col-md-12">
                    <div>2014-05-13</div>
                    </div> -->
                    

</div>
</div>

 <span class="pull-right" style="left: 778px;position: absolute;top: 420px;">
<a id="fa_left"><i class="fa fa-3x fa-chevron-left"  style="cursor: pointer;color:#ccc;font-size:45px" onMouseOut="this.style.color='#CCC'" onMouseOver="this.style.color='#AAA'" ></i></a>
<a id="fa_right"><i class="fa fa-3x fa-chevron-right" style="cursor: pointer;color:#ccc;font-size:45px" onMouseOut="this.style.color='#CCC'" onMouseOver="this.style.color='#AAA'"></i></a>
                    </span> 

<script>
var json=${promotionsJson};
//alert("json::"+JSON.stringify(json));
var ntsenable='${ntsEnable}';
var feeshowstatus='${feeshowstatus}';
var dt_table_dd=json.promotions;
var timeObj={};
var min=0;max=5;
var finalJSON={};
var innerArr=[];
var diffDates=[];
for(var json=0;json<dt_table_dd.length;json++){
var datefomat=dt_table_dd[json].dateformat;
if(datefomat in finalJSON){
	innerArr=finalJSON[datefomat];
	innerArr.push(dt_table_dd[json]);
}else{
	innerArr=new Array();
	diffDates.push(datefomat);
	innerArr.push(dt_table_dd[json]);
}	
finalJSON[datefomat]=innerArr;
}




    function connect(div1, div2, color, thickness) {
        var off1 = getOffset(div1);
        var off2 = getOffset(div2);
        // center of first div
        var x1 = off1.left + off1.width / 2;
        var y1 = off1.top + off1.height / 2;
        // center of second div
        var x2 = off2.left + off2.width / 2;
        var y2 = off2.top + off2.height / 2;
        // distance
        var length = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
        // center
        var cx = ((x1 + x2) / 2) - (length / 2);
        var cy = ((y1 + y2) / 2) - (thickness / 2);
        // angle
        var angle = Math.atan2((y1 - y2), (x1 - x2)) * (180 / Math.PI);
        // make hr
        var htmlLine = "<div class='joiner' style='border-top:dotted;display:none;z-index:8888;padding:0px; margin:0px; height:" + thickness + "px; border-top-color:" + color + "; line-height:1px; position:absolute; left:" + cx + "px; top:" + cy + "px; width:" + length + "px; -moz-transform:rotate(" + angle + "deg); -webkit-transform:rotate(" + angle + "deg); -o-transform:rotate(" + angle + "deg); -ms-transform:rotate(" + angle + "deg); transform:rotate(" + angle + "deg);' />";
        //append html to body
        document.body.innerHTML += htmlLine;
    }

    function getOffset(el) {
        var _x = 0;
        var _y = 0;
        var _w = el.offsetWidth | 0;
        var _h = el.offsetHeight | 0;
        while (el && !isNaN(el.offsetLeft) && !isNaN(el.offsetTop)) {
            _x += el.offsetLeft - el.scrollLeft;
            _y += el.offsetTop - el.scrollTop;
            el = el.offsetParent;
        }
        return {
            top: _y + $(window).scrollTop(),
            left: _x + $(window).scrollLeft(),
            width: _w,
            height: _h
        };
    }
    
    var temp=1;
    var globalImg=0;
    var globalDate=0;

    function run(direction) {
    	   globalImg=0;
    	   globalDate=0;
    	   if(max>=diffDates.length)
    			max=diffDates.length;
    			
    	var images='';
    	var dates='';
    	for(var i=min;i<max;i++){
    		if(i>=diffDates.length)
    			continue;
    		var tooltipmsg='';
    		var width=40;
    		var height=40;
    		var date=diffDates[i];
    		var dateArr=finalJSON[date];
    		var exists=new Array();
    		for(var inn=0;inn<dateArr.length;inn++){
    			var elems=dateArr[inn];
    			var fbuid=elems.fbuid; 
    			var name=elems.name;
    			var sales=parseInt(elems.sales);
    			var visits=parseInt(elems.visits);
    			var hour=parseInt(elems.hour);
    			 var found = $.inArray(hour,exists) > -1;
    			
    			
    			//alert("sales::"+sales+"visits::"+visits);
    			tooltipmsg=name;
    			if(visits>0){
    			width=60,height=60;
    			tooltipmsg+='<br/>visits: '+visits;
    			}else{
    			tooltipmsg+='<br/>visits: 0';	
    			}
    			if(sales>0){
    			width=80,height=80;
    			tooltipmsg+=', sales: '+sales;
    			}else{
    			tooltipmsg+=', sales: 0';
    			}
    			
    			if(visits==0 && sales==0){
    				width=40,height=40;
    			}
    			if(found){
    				timeObj['link_'+globalImg]=dateArr;	
				//images+='<a class="link" id="link_'+globalImg+'">';
    				//tooltipmsg+='More Promotions';
    			}
        			exists.push(hour); 
    				
    			
    			//alert("width:"+width+"height::"+height);
    			
    			images+=' <img src="https://graph.facebook.com/'+fbuid+'/picture" title="'+tooltipmsg+'" class="animated fadeIn'+direction+' img-thumbnail img-circle" width="'+width+'" height="'+height+'" id="img_'+globalImg+'"/>';
				//images+='<span id="span_'+globalImg+'"style="position:relative;">More</span>';
    			if(found)
        			images+='</a>';
    			globalImg+=1;
    	}
    		dates+='<span id="dates_'+globalDate+'">'+date+'</span>';
    		globalDate+=1;
    	}
    	
    	
    	
    	$('#graphContainer').html(images);
    	
    	
    	$('#dates').html(dates);
    	
    	
    	
    	var top=0;
    	var left=3;
    	globalImg=0;
    	globalDate=0;
    		for(var i=min;i<max;i++){
    			if(i>=diffDates.length)
        			continue;
    			var date=diffDates[i];
        		var dateArr=finalJSON[date];
        		
//alert(JSON.stringify(dateArr));        	
var exists=new Array();
        		for(var inn=0;inn<dateArr.length;inn++){

        			var elems=dateArr[inn];
        			var hour=parseInt(elems.hour);
        			 var found = $.inArray(hour,exists) > -1;
        			if(found && hour!=24)
            			hour+=1;
        			exists.push(hour); 
        			
        			if(hour<=1)
					top=76;
            		else
        			top=90-((hour/24)*100);
        			if(top>80)
            			top=76;
        			//alert("top::"+top);
                    $('#img_'+globalImg).css({"top": (top+10)+'%',left: left+'%'});
                   // $('#span_'+globalImg).css({"top": (top+15)+'%',left: (left+1)+'%'});
        			globalImg+=1;
        		}
        		$('#dates_'+globalDate).css("left",(left-1)+'%');
        		left=left+21;
        		globalDate+=1;
    		}
    		
    		globalImg=0;
        	globalDate=0;
        
        var connectImages=function(){
        	var temp=0;
        	for(var i=min;i<max-1;i++){
    			var date=diffDates[i];
        		var dateArr=finalJSON[date];
        		for(var inn=0;inn<dateArr.length;inn++){
        			var l=globalImg+1;
        			if(temp==0)
        		    connect(document.getElementById('img_'+globalImg), document.getElementById('img_'+globalImg), "lightgray", 2);
        			connect(document.getElementById('img_'+globalImg), document.getElementById('img_'+l), "lightgray", 2);
        			globalImg+=1;
        			
        	}
        		
        		if(i==max-2){
        			var dateval=diffDates[max-1];
        			var arr=finalJSON[dateval];
        			if(arr.length>1){
        				for(var temp=0;temp<arr.length-1;temp++){
        					var l=globalImg+1;
                			connect(document.getElementById('img_'+globalImg), document.getElementById('img_'+l), "lightgray", 2);
                			// $('#img_'+globalImg).tooltip({html:true,trigger: 'manual',placement : 'top'}).tooltip('show');
                			globalImg+=1;
        				}
        			}
        		}
        	
        	}
        	
        };
        
        
        $(window).resize(function(){
        	connectImages();
        });
        
        	connectImages();
             $('img').tooltip({
                placement: 'top',
                html: true,
                title: '<span style="font-size:1em;">Tooltip title</span>'
            }); 

            $('.joiner').fadeIn();
            if (navigator.userAgent.toLowerCase().indexOf('safari')>-1 && !(navigator.userAgent.toLowerCase().indexOf('chrome')>-1)){
            	$('.img-circle').css("border-radius","12%");
            }
    }
   
    $(window).load(function(){
    	run('Right');
    });
    $(document).ready(function() {
    	$(document).on('click','#fa_left',function(){
    		$('#graphContainer img')
    			.removeClass('fadeInRight')
    			.removeClass('fadeInLeft')
    			.removeClass('fadeOutLeft')
    			.removeClass('fadeOutRight')
    			.addClass('fadeOutRight');
    		$('.joiner').fadeOut(function(){
    			$( ".joiner" ).remove();
    		}); 
		if(min<=0){
			min=0;
		    max=5;
		}else{
		min-=1;
		max-=1;
		}
    		setTimeout(run,1000,'Left');
    	});

$(document).on('click','.link',function(){
	
	var id=$(this).attr('id');
	//alert("hi this::"+id);
	//alert(JSON.stringify(timeObj[id]));
});
    	
    	
		$(document).on('click','#fa_right',function(){
			$('#graphContainer img')
				.removeClass('fadeInRight')
				.removeClass('fadeInLeft')
				.removeClass('fadeOutLeft')
				.removeClass('fadeOutRight')
				.addClass('fadeOutLeft');
			
			$('.joiner').fadeOut();
			setTimeout(function(){
				$('.joiner').remove();
			},1000);
		if(max>=diffDates.length){
		max=diffDates.length;
		}else{
			min+=1;
			max+=1;
		}
		
		setTimeout(run,1000,'Right');
			
    		
    	});
    	
    	
    });

function click(){
//alert("hi");	
}
    
</script>
