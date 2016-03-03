<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:set name="menucontextH" value="#attr['menucontext']" />

<%
String eventid = request.getParameter("eid");
String themecode = DBHelper.getMyHeaderTheme(eventid);
%>
<!DOCTYPE html>
<html>
<head>
<title>My Header Theme</title>
<style>
.content{
	    overflow-y: scroll;
    height: 400px;
}
input,select {
	padding:5px;
	border:1px solid #aaa;
	box-shadow: 0 0 5px #aaa;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	outline:none;
	-webkit-transition: all 0.30s ease-in-out;
	-moz-transition: all 0.30s ease-in-out;
	-ms-transition: all 0.30s ease-in-out;
	-o-transition: all 0.30s ease-in-out;
	outline:none;
}
input:focus,input:hover, select:focus,select:hover {
	box-shadow: 0 0 5px #1b90f2;
	border: 1px solid #1b90f2;
}
button {
	padding:6px 10px;
	margin-bottom:3px;
	border:1px solid #444;
	background-color:#444;
	color:#fff;
	border-radius: 8px;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	-webkit-transition: all 0.30s ease-in-out;
	-moz-transition: all 0.30s ease-in-out;
	-ms-transition: all 0.30s ease-in-out;
	-o-transition: all 0.30s ease-in-out;
	outline: none;
}
button:hover,button:focus,button:active {
	box-shadow: 0 0 8px #1b90f2;
	border:1px solid #1b90f2;
}
.sidebar {
	height: 370px;
	overflow: auto;
	width:20%;
	float: right;
}
.sidebar ul li {
	list-style: none;
	margin-left: -25px;
	line-height: 1.5em;
}
.themecode {
	height:400px;
	width:75%;
	float:left;
}
.CodeMirror-selected { background: #d1f0f9 !important; }
.cm-highlightSearch {
    background: yellow;
}
.CodeMirror {
  border: 1px solid #eee;
  height: 100%;
  width: 100%;
}
.CodeMirror-scroll {
  overflow-y: hidden;
  overflow-x: auto;
}
.addKeyword {
	text-decoration: underline;
	cursor: pointer;
	line-height: 1.5em;
}
.addKeyword:hover {
	background-color: yellow;
}
 ::-webkit-scrollbar-track
{

	-webkit-box-shadow: inset 0 0 0px rgba(0,0,0,0.3);
	background-color: tranparent;
	width:10px;
}
::-webkit-scrollbar{height:10px;width:7px}
::-webkit-scrollbar-button{height:0;width:0}
 ::-webkit-scrollbar-thumb{
 background-clip:padding-box;
 background-color:rgba(0,0,0,.3);
 -webkit-border-radius:10px;
 border-radius:10px;
 min-height:10px;
 min-width:10px;
 height:10px;
 width:10px
 }
::-webkit-scrollbar-thumb:hover, ::-webkit-scrollbar-thumb:active {
  background-color:rgba(0,0,0,.4)
 }
 ::-webkit-scrollbar-track:hover, ::-webkit-scrollbar-track:active{
   -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.3);
    background-color: #f4f4f4;
	width:10px;
}
</style>
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script>
$(function(){
	$('#saveMyHeader').on('click',function() {
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		var data = getThemeData();
		var saveHeader ={};
		saveHeader['type'] = 'saveHeaderTheme';
		saveHeader['eid'] = '<%=eventid%>';
		saveHeader['action'] = 'SaveSettings';
		saveHeader['data']=data;
		$.ajax({
			type:'POST',
			url:'SaveSettings',
			data:saveHeader,
			success:function(response){
				if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
				if(response.status=="success") {
					savebutton.html(props.pg_header_changes_saved_lbl);
					setTimeout(function(){
						savebutton.removeAttr('disabled');
						savebutton.html(props.pg_widgets_save_lbl);
					},1000);
				}
			},
			error:function(){
				alert(props.pg_widgets_not_respond);
				savebutton.removeAttr('disabled');
			}
		});
		
	});
	$('#saveExitMyHeader').on('click',function() {
		var savebutton = $(this);
		savebutton.html(props.pg_widgets_saving_lbl);
		savebutton.attr('disabled','disabled');
		var data = getThemeData();
		var saveHeaderExit ={};
		saveHeaderExit['type'] = 'saveHeaderTheme';
		saveHeaderExit['eid'] = '<%=eventid%>';
		saveHeaderExit['action'] = 'SaveSettings';
		saveHeaderExit['data']=data;
		$.ajax({
			type:'POST',
			url:'SaveSettings',
			data:saveHeaderExit,
			success:function(response){
				if(!isValidActionMessageIFRAME(JSON.stringify(response))) return;
				if(response.status=="success")
					parent.disablePopup();
			},
			error:function(){
				alert(props.pg_header_server_not_received_lbl);
				savebutton.removeAttr('disabled');
			}
		});
	});
	$('#exitMyHeader').on('click',function() {
		//if(confirm("Are you sure?"))
			parent.disablePopup();
	});
	$('.addKeyword').on('click',function(){
		var scrollinfo = editor.getScrollInfo();
		var cursor = editor.getCursor();
		var line = cursor.line;
		var ch = cursor.ch;
		var newtext = "";
		var lines = editor.getValue().split("\n");
		for(var i=0;i<lines.length;i++) {
			if(i==line) {
				newtext += lines[i].slice(0, ch) + $(this).data("keyword") + lines[i].slice(ch);
			} else {
				newtext += lines[i];
			}
			newtext += "\n";
		}
		editor.setValue(newtext);
		editor.setCursor(line,ch);
		editor.scrollTo(scrollinfo.left,scrollinfo.top);
	});
	
});
</script>
<link rel="stylesheet" href="/main/js/layoutmanage/codemirror/codemirror.css">
<script src="/main/js/layoutmanage/codemirror/codemirror.js"></script>
<script src="/main/js/layoutmanage/codemirror/xml.js"></script>
<script src="/main/js/layoutmanage/codemirror/overlay.js"></script>
<script src="/main/js/layoutmanage/codemirror/htmlmixed.js"></script>
<script src="/main/js/layoutmanage/codemirror/javascript.js"></script>
<script src="/main/js/layoutmanage/codemirror/css.js"></script>
<script src="/main/js/layoutmanage/codemirror/searchcursor.js"></script>
<script src="/main/js/layoutmanage/codemirror/search.js"></script>
<script src="/main/js/layoutmanage/codemirror/match-highlighter.js"></script>
<script src="/main/js/layoutmanage/codemirror/formatting.js"></script>
</head>
<body>
<%-- 	<div class="tabmenu">
		<ul class="menu">
		    <li><a href="/main/eventmanage/layoutmanage/HeaderSettings?eid=<%=eventid%>">Header Settings</a>
			<li><a href="/main/eventmanage/layoutmanage/ChooseHeaderTheme?eid=<%=eventid%>">Choose Header Theme</a></li>
			 <li><a href="/main/eventmanage/layoutmanage/HeaderSettings?eid=<%=eventid%>">Header Settings</a>
			<li class="active"><a href="#">My Header Theme</a></li>
		</ul>
	</div> --%><div>
	<div class="content">
		<div class="themecode">
			<textarea id="themecode">
				<%=themecode%>
			</textarea>
		</div>
		<div class="sidebar">
			<s:text name="pg.header.cursor.position.lbl" />
			<ul>
				<li><s:text name="pg.headertheme.when.lbl" />
					<ul>
						<li class="addKeyword" data-keyword="$$startDay$$"><s:text name="pg.headertheme.startday.lbl" /></li>
						<li class="addKeyword" data-keyword="$$startMonth$$"><s:text name="pg.headertheme.startmon.lbl" /></li>
						<li class="addKeyword" data-keyword="$$startDate$$"><s:text name="pg.headertheme.startdate.lbl" /></li>
						<li class="addKeyword" data-keyword="$$startYear$$"><s:text name="pg.headertheme.startyear.lbl" /></li>
						<li class="addKeyword" data-keyword="$$startTime$$"><s:text name="pg.headertheme.starttime.lbl" /></li>
						<li class="addKeyword" data-keyword="$$startAMPM$$"><s:text name="pg.headertheme.start.ampm.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endDay$$"><s:text name="pg.headertheme.endday.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endMonth$$"><s:text name="pg.headertheme.endmon.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endDate$$"><s:text name="pg.headertheme.enddate.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endYear$$"><s:text name="pg.hedaretheme.endyear.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endTime$$"><s:text name="pg.headertheme.endtime.lbl" /></li>
						<li class="addKeyword" data-keyword="$$endAMPM$$"><s:text name="pg.headertheme.end.ampm.lbl" /></li>
					</ul>
				</li>
				<li><s:text name="pg.headerthemewhere.lbl" />
					<ul>
						<li class="addKeyword" data-keyword="$$venue$$"><s:text name="pg.headertheme.venue.lbl" /></li>
						<li class="addKeyword" data-keyword="$$address1$$"><s:text name="pg.headertheme.address.one.lbl" /></li>
						<li class="addKeyword" data-keyword="$$address2$$"><s:text name="pg.headertheme.address.two.lbl" /></li>
						<!-- <li class="addKeyword" data-keyword="$$state$$">State</li> -->
						<li class="addKeyword" data-keyword="$$city$$"><s:text name="pg.headertheme.city.lbl" /></li>
						<li class="addKeyword" data-keyword="$$country$$"><s:text name="pg.headertheme.country.lbl" /></li>
						
					</ul>
				</li>
				<li class="addKeyword" data-keyword="$$eventTitle$$"><s:text name="pg.headertheme.event.title.lbl" /></li>
				<li class="addKeyword" data-keyword="$$titleColor$$"><s:text name="pg.headertheme.title.color.lbl" /></li>
				<li class="addKeyword" data-keyword="$$detailsColor$$"><s:text name="pg.headertheme.detais.color.lbl" /></li>
				<li class="addKeyword" data-keyword="$$coverPhotoURL$$"><s:text name="pg.headertheme.cover.photo.lbl" /></li>
				<li class="addKeyword" data-keyword="$$googleMapHTML$$"><s:text name="pg.headertheme.google.html.lbl" /></li>
				<li class="addKeyword" data-keyword="$$borderColor$$"><s:text name="pg.headertheme.border.color.lbl" /></li>
                <li class="addKeyword" data-keyword="$$bookMark$$"><s:text name="pg.headertheme.bookmark.lbl" /></li>
				<li class="addKeyword" data-keyword="$$logoUrl$$"><s:text name="pg.headertheme.logo.url" /></li>
				<li class="addKeyword" data-keyword="$$logoMsg$$"><s:text name="pg.headertheme.lgo.msg" /></li>
			</ul>

		</div>
		<div style="clear: both;"></div>
		<br>
		
		
	</div>
	<div class="text-center" style="margin-top:10px;">
			<button class="btn btn-primary" id="saveMyHeader"><s:text name="pg.widgets.save" /></button>
			<button class="btn btn-primary" id="saveExitMyHeader"><s:text name="pg.widgets.save.exit" /></button>
			<button class="btn btn-primary" id="exitMyHeader"><s:text name="pg.widgets.exit" /></button>
		</div>
		</div>
	<script>
	    var keyword3 = "$$eventTitle$$";
		var keyword4 = "$$titleColor$$";
		var keyword5 = "$$detailsColor$$";
		var keyword6 = "$$coverPhotoURL$$";
		var keyword7 = "$$googleMapHTML$$";
		var keyword8 = "$$borderColor$$";
		var keyword9 = "$$startDay$$";
		var keyword10= "$$startMonth$$";
		var keyword11= "$$startDate$$";
		var keyword12= "$$startYear$$";
		var keyword13= "$$startTime$$";
		var keyword14= "$$startAMPM$$";
		var keyword15= "$$endDay$$";
		var keyword16= "$$endMonth$$";
		var keyword17= "$$endDate$$";
		var keyword18= "$$endYear$$";
		var keyword19= "$$endTime$$";
		var keyword20= "$$endAMPM$$";
		var keyword21= "$$venue$$";
		var keyword22= "$$address1$$";
		var keyword23= "$$address2$$";
		var keyword24= "$$state$$";
		var keyword25= "$$city$$";
		var keyword26= "$$country$$";
        var keyword27= "$$bookMark$$";
		var keyword28= "$$logoUrl$$";
		var keyword29= "$$logoMsg$$";

		
		CodeMirror.defineMode("highlightSearch", function(config, parserConfig) {
		  var searchOverlay = {
		    token: function(stream, state) {
		    	//alert("in strem data::"+stream+"::"+state);
		        if (
		        		stream.match(keyword3) ||
		        		stream.match(keyword4) ||
		        		stream.match(keyword5) ||
		        		stream.match(keyword6) ||
		        		stream.match(keyword7) ||
		        		stream.match(keyword8) ||
		        		stream.match(keyword9) ||
		        		stream.match(keyword10) ||
		        		stream.match(keyword11) ||
		        		stream.match(keyword12) ||
		        		stream.match(keyword13) ||
		        		stream.match(keyword14) ||
		        		stream.match(keyword15) ||
		        		stream.match(keyword16) ||
		        		stream.match(keyword17) ||
		        		stream.match(keyword18) ||
		        		stream.match(keyword19) ||
		        		stream.match(keyword20) ||
		        		stream.match(keyword21) ||
		        		stream.match(keyword22) ||
		        		stream.match(keyword23) ||
		        		stream.match(keyword24) ||
		        		stream.match(keyword25) ||
		        		stream.match(keyword26) ||
		        		stream.match(keyword27) ||
		        		stream.match(keyword28) ||
		        		stream.match(keyword29)) {

		            return "highlightSearch";
		        }

		        while (stream.next() != null && !(
		        		
		        		stream.match(keyword3, false) ||
		        		stream.match(keyword4, false) ||
		        		stream.match(keyword5, false) ||
		        		stream.match(keyword6, false) ||
		        		stream.match(keyword7, false) ||
		        		stream.match(keyword8, false) ||
		        		stream.match(keyword9, false) ||
		        		stream.match(keyword10, false) ||
		        		stream.match(keyword11, false) ||
		        		stream.match(keyword12, false) ||
		        		stream.match(keyword13, false) ||
		        		stream.match(keyword14, false) ||
		        		stream.match(keyword15, false) ||
		        		stream.match(keyword16, false) ||
		        		stream.match(keyword17, false) ||
		        		stream.match(keyword18, false) ||
		        		stream.match(keyword19, false) ||
		        		stream.match(keyword20, false) ||
		        		stream.match(keyword21, false) ||
		        		stream.match(keyword22, false) ||
		        		stream.match(keyword23, false) ||
		        		stream.match(keyword24, false) ||
		        		stream.match(keyword25, false) ||
		        		stream.match(keyword26, false) ||
		        		stream.match(keyword27, false) ||
		        		stream.match(keyword28, false) ||
		        		stream.match(keyword29, false))) {}
		        return null;
		    }
		  };
		  return CodeMirror.overlayMode(CodeMirror.getMode(config, parserConfig.backdrop || "htmlmixed"), searchOverlay);
		});
		var editor = CodeMirror.fromTextArea(document.getElementById("themecode"), {
			mode: "highlightSearch",
			
		});
		editor.setSize("100%",400);
		
		function autoFormat() {
			var totalLines = editor.lineCount();
			var totalChars = editor.getTextArea().value.length;
			editor.autoFormatRange({line:0, ch:0}, {line:totalLines, ch:totalChars});
			editor.setCursor(0,0);
		}
		autoFormat();
		function getThemeData() {
			return editor.getValue();
		}
	</script>
</body>
</html>
