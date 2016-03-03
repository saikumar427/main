<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<%@page import="com.eventbee.general.EbeeConstantsF"%>
<%@page import="org.json.JSONException"%>
<%@page import="java.awt.Color"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.eventbee.layout.EventPageRenderer"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.layout.DBHelper"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="EventParams.jsp"%>
<%
String eventid = request.getParameter("eventid");
if(eventid==null || "".equals(eventid)) {
	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	return;
}
String prev = request.getParameter("preview");
prev=prev==null?"final":prev;
String jsonData = DBHelper.getLayout(eventid,"",prev); 
//System.out.println("all content::"+jsonData);
JSONObject layout = new JSONObject(jsonData);
String columnWideHTML,columnNarrowHTML,columnSingleHTML,columnSingleBottomHTML;
columnSingleHTML = "<div class=\"single\">";
columnWideHTML = "<div class=\"wide\">";
columnNarrowHTML = "<div class=\"narrow\">";
columnSingleBottomHTML= "<div class=\"single\">";

JSONArray single_widgets=null,wide_widgets=null,narrow_widgets=null,single_bottom_widgets=null;
JSONObject total_widgets=new JSONObject();
try {
	single_widgets = layout.getJSONArray("column-single");
	wide_widgets = layout.getJSONArray("column-wide");
	narrow_widgets = layout.getJSONArray("column-narrow");
	single_bottom_widgets=layout.getJSONArray("column-single-bottom");
total_widgets.put("single",single_widgets);

total_widgets.put("wide",wide_widgets);
total_widgets.put("narrow",narrow_widgets);
total_widgets.put("singlebottom",single_bottom_widgets);
} catch(JSONException je) {
	out.print("<h1>Error establishing a database connection</h1>");
}
//System.out.println("this i s"+single_widgets);

String headerTheme = layout.getString("header_theme");

JSONObject global_style = layout.getJSONObject("global_style");

String bodyBackgroundImage = global_style.getString("bodyBackgroundImage");
String bodyBackgroundColor = global_style.getString("bodyBackgroundColor");
String bodyBackgroundPosition = global_style.getString("backgroundPosition");
 
String coverPhoto = global_style.getString("coverPhoto");
String titleColor = global_style.getString("title");
String backgroundRgba = global_style.getString("contentBackground");
String logoURL="",logoMsg="";
if(global_style.has("logourl"))
	logoURL=global_style.getString("logourl");
if(global_style.has("logomsg"))
	logoMsg=global_style.getString("logomsg");
String backgroundHex = backgroundRgba.split("rgba")[1];
backgroundHex = backgroundHex.substring(1, backgroundHex.length()-1);
int r = Integer.parseInt(backgroundHex.split(",")[0]);
int g = Integer.parseInt(backgroundHex.split(",")[1]);
int b = Integer.parseInt(backgroundHex.split(",")[2]);
Color color = new Color(r,g,b);
backgroundHex = "#"+Integer.toHexString(color.getRGB()).substring(2);
String backgroundOpacity = backgroundRgba.split(",")[3];
backgroundOpacity = backgroundOpacity.substring(0, backgroundOpacity.length()-1);
String details = global_style.getString("details");
String header = global_style.getString("header");
String headerText = global_style.getString("headerText");
String border = global_style.getString("border");
String content = global_style.getString("content");
String contentText = global_style.getString("contentText");

String headerTextFont="Verdana";
String headerTextSize="16";
String contentTextSize = "14";
String contentTextFont = "Verdana";


if(global_style.has("headerTextFont"))
	headerTextFont = global_style.getString("headerTextFont");
if(global_style.has("headerTextSize"))
	headerTextSize = global_style.getString("headerTextSize");
if(global_style.has("contentTextSize"))
	contentTextSize = global_style.getString("contentTextSize");
if(global_style.has("contentTextFont"))
	contentTextFont = global_style.getString("contentTextFont");



JSONObject radius = new JSONObject(global_style.getString("radius"));
String topLeft = radius.getString("topLeft");
String topRight = radius.getString("topRight");
String bottomLeft = radius.getString("bottomLeft");
String bottomRight = radius.getString("bottomRight");

JSONObject widgetStyles = DBHelper.getWidgetStyles(eventid,prev);//widget color bordre etc attributes json
//System.out.println("widgetStyles:::"+widgetStyles);

HashMap<String, String> configHash = new HashMap<String, String>();

refHash.put("eventid", eventid);
refHash.put("stage", prev);
refHash.put("serveraddress", "http://"+EbeeConstantsF.get("serveraddress","localhost")+"/");
//refHash :: event config metadata
//dataHash :: $variables original content 

String config_options = DBHelper.getAllWidgetOptions(eventid,prev).toString();// $variables custom attributes 
//System.out.println("config_options:::"+config_options);
/* System.out.println("widget content1::"+config_options);
System.out.println("widget content2::"+widgetStyles.toString());
System.out.println("widget content3::"+total_widgets.toString()); */
configHash.put("config_options", config_options);
configHash.put("styles", widgetStyles.toString());
configHash.put("widgets",total_widgets.toString());

String googleMapsHTML = "<iframe width=\"100%\" height=\"100%\" frameborder=\"no\" src=\"http://www.eventbee.com/portal/customevents/googlemap_js.jsp?lon="+dataHash.get("longitude")+"&amp;lat="+dataHash.get("latitude")+"\"></iframe>";



StringBuffer bookmark=new StringBuffer("");
bookmark.append("<div>");
bookmark.append("<script type='text/javascript' src='http://www.eventbee.com/home/js/customfonts/cufon-yui.js'></script>");
bookmark.append("<script src='http://www.eventbee.com/home/js/customfonts/Verdana_400.font.js' type='text/javascript'></script>");
bookmark.append("<script>Cufon.replace('.large');</script>");
bookmark.append("<script type=\"text/javascript\">addthis_url= location.href;addthis_title = document.title;addthis_pub='eventbee';</script>");
bookmark.append("<script type=\"text/javascript\" src=\"http://s7.addthis.com/js/addthis_widget.php?v=12\"></script>");
bookmark.append("</div>");

System.out.println("refHashrefHashrefHashrefHashrefHash"+refHash);
JSONObject singleWidgets=EventPageRenderer.getWidgetsHTML(single_widgets,eventid,refHash,dataHash,configHash);
JSONObject wideWidgets =EventPageRenderer.getWidgetsHTML(wide_widgets,eventid,refHash,dataHash,configHash);
JSONObject narrowWidgets=EventPageRenderer.getWidgetsHTML(narrow_widgets,eventid,refHash,dataHash,configHash);
JSONObject singleBottomWidget=EventPageRenderer.getWidgetsHTML(single_bottom_widgets,eventid,refHash,dataHash,configHash);

%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Event</title>
<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet" />

<style>
body {
	margin: 0 0 0 0;
	font-family: Tahoma, Geneva, sans-serif;
	background-position: top;
	background-color: <%=bodyBackgroundColor%>;
	<%if(!"".equals(bodyBackgroundImage)){%>
	background-image: url('<%=bodyBackgroundImage%>');
	<%}%>
	<%
	if("cover".equals(bodyBackgroundPosition)) {
		out.println("background-size:100% auto;");
		out.print("\tbackground-repeat:no-repeat;");
	} else
		out.print("background-repeat:"+bodyBackgroundPosition+";");%>
}
#whosAttending .widget-content{
    height: 250px;
    overflow-y: auto;
}
#whosAttending .widget-content #attendeeinfo ul hr{
    margin: 10px 0px;
}
.container {
	#width:980px;
	#margin-left:auto;
	box-shadow:-60px 0px 100px -90px #000000, 60px 0px 100px -90px #000000;
	#margin-right:auto;
	background-color: <%=backgroundRgba%> !important;
}
.header {
	  margin-bottom: 15px;
} 
/* .single {
	width:940px;
	float:left;
} */
/* .wide {
	width:610px;
	float:left;
} */
/* .narrow {
	width:310px;
	float:right;
} */
/* .widgets {
	padding:10px 20px;
} */
h2{
	text-transform: capitalize;
}
.widget {
	
	
	margin:5px 2px  20px;
	position:relative;
	border:1px solid <%=border%>;
	width: 100%;
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-webkit-border-bottom-right-radius: <%=bottomRight%>;
	-webkit-border-bottom-left-radius: <%=bottomLeft%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	-moz-border-radius-bottomright: <%=bottomRight%>;
	-moz-border-radius-bottomleft: <%=bottomLeft%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
	border-bottom-right-radius: <%=bottomRight%>;
	border-bottom-left-radius: <%=bottomLeft%>;
}
.widget h2{
font-family:<%=headerTextFont%>;
	margin:0;
	font-size:<%=headerTextSize%>px;
	background-color:<%=header%>;
	color:<%=headerText%>;
	border-bottom:1px solid <%=border%>;
	padding:10px;
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
}
.widget-content{
	background-color:<%=content%>;
	color:<%=contentText%>;
	font-family:<%=contentTextFont%>;
	font-size:<%=contentTextSize%>px;
	line-height:20px;
	padding:15px;
	-webkit-border-bottom-right-radius: <%=bottomRight%>;
	-webkit-border-bottom-left-radius: <%=bottomLeft%>;
	-moz-border-radius-bottomright: <%=bottomRight%>;
	-moz-border-radius-bottomleft: <%=bottomLeft%>;
	border-bottom-right-radius: <%=bottomRight%>;
	border-bottom-left-radius: <%=bottomLeft%>;
	position: static;
	overflow: hidden;
}
.widget-content-ntitle{
	-webkit-border-top-left-radius: <%=topLeft%>;
	-webkit-border-top-right-radius: <%=topRight%>;
	-moz-border-radius-topleft: <%=topLeft%>;
	-moz-border-radius-topright: <%=topRight%>;
	border-top-left-radius: <%=topLeft%>;
	border-top-right-radius: <%=topRight%>;
}

.small {
font-family: Verdana, Arial, Helvetica, sans-serif;
font-size: 10px;
font-weight: lighter;
color:#666;
overflow: hidden;
}

hr {
	border: 0;
    height: 1px;
    background-image: -webkit-linear-gradient(left, rgba(0,0,0,0), <%=border%>, rgba(0,0,0,0)); 
    background-image:    -moz-linear-gradient(left, rgba(0,0,0,0), <%=border%>, rgba(0,0,0,0)); 
    background-image:     -ms-linear-gradient(left, rgba(0,0,0,0), <%=border%>, rgba(0,0,0,0)); 
    background-image:      -o-linear-gradient(left, rgba(0,0,0,0), <%=border%>, rgba(0,0,0,0)); 
}
#whosPromoting .widget .widget-content{
	overflow: auto;
}
img{max-width:100%}
#subForm{
	background-color:#fff;
}
</style>
<script src="/main/js/layoutmanage/jquery-1.10.2.min.js"></script>
<script src="/main/js/layoutmanage/eventPage.js"></script>
<!-- <script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script> -->


</head>
<body > 
<div id="rootDiv">
<!-- facebook js sdk -->
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
<%-- var json=<%= jsonData %>;
var added=json.added;
var single_widgets=json['column-single'];
var wide_widgets=json['column-wide'];
var narrow_widgets=json['column-narrow'];
var single_bottom_widgets=json['column-single-bottom']; --%>
/* var sync = json.sync;
var hidewidgets=json['hide-widgets']; */
/* console.log(JSON.stringify(single_widgets));
console.log(JSON.stringify(wide_widgets));


	
console.log(JSON.stringify(narrow_widgets));
console.log(JSON.stringify(single_bottom_widgets)); */



</script>
<!-- sdk end -->
<div class="container"><!-- container -->
	<div class="header">
		<%=headerTheme
			.replace("$$titleColor$$", titleColor)
			.replace("$$eventTitle$$", dataHash.get("eventname"))
			.replace("$$coverPhotoURL$$", coverPhoto)
			.replace("$$detailsColor$$", details)
			.replace("$$borderColor$$", border)
			.replace("$$googleMapHTML$$", googleMapsHTML)
			//when section
				//start section
				.replace("$$startDay$$", dataHash.get("startday"))
				.replace("$$startMonth$$", dataHash.get("startmon"))
				.replace("$$startDate$$", dataHash.get("startdate"))
				.replace("$$startYear$$", dataHash.get("startyear"))
				.replace("$$startTime$$", dataHash.get("starttime"))
				.replace("$$startAMPM$$", dataHash.get("startampm"))
				//end section
				.replace("$$endDay$$",dataHash.get("endday"))
				.replace("$$endMonth$$", dataHash.get("endmon"))
				.replace("$$endDate$$",dataHash.get("enddate"))
				.replace("$$endYear$$", dataHash.get("endyear"))
				.replace("$$endTime$$", dataHash.get("endtime"))
				.replace("$$endAMPM$$", dataHash.get("endampm"))
			//where section
				.replace("$$venue$$", dataHash.get("venue"))
				.replace("$$address1$$", dataHash.get("address1"))
				.replace("$$address2$$",dataHash.get("address2"))
				.replace("$$state$$",dataHash.get("state"))
				.replace("$$city$$",dataHash.get("city"))
				.replace("$$country$$", dataHash.get("country"))
				.replace("$$bookMark$$",bookmark.toString())
				.replace("$$logoUrl$$","<img src='"+logoURL+"' width=200/>")
				.replace("$$logoMsg$$","<span class='small'>"+logoMsg+"</span>")
		%> 
	</div>
	<div style="clear:both;"></div>
		
	<div class="widgets">
		<div class="col-sm-12 col-md-12 col-xs-12">
			<div class="single" id="single_widgets">
				
			</div>
		</div>
		<div class="col-sm-12 col-md-8 col-xs-12">
			<div class="wide" id="wide_widgets">
				
			</div>
		</div>
		<div class="col-sm-12 col-md-4 col-xs-12">
			<div class="narrow" id="narrow_widgets">
				
			</div>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="single" id="single_bottom_widgets">
				
			</div>
		</div>
	</div>
	
	<!-- Footer start -->
	<div style="clear:both;"></div>
	<div class="row">
		<table align="center" cellpadding="5" style="margin-bottom: 15px;">
		    <tbody>
		        <tr>
		            <td align="left" valign="middle">
		                <a style="margin-right:15px" href="http://www.eventbee.com/"><img src="http://www.eventbee.com/home/images/poweredby.jpg" border="0">
		                </a>
		            </td>
		            <td>&nbsp;&nbsp;</td>
		            <td align="left" valign="middle"><span class="small">Powered by Eventbee - Your Online Registration, Membership Management and Event <br>Promotion solution. For more information, send an email to support at eventbee.com</span>
		            </td>
		        </tr>
		    </tbody>
		</table>
	</div> 
	<!-- Footer end -->
	
</div>
<script>
var single_widgets = <%=single_widgets%>
var wide_widgets = <%=wide_widgets%>
var narrow_widgets = <%=narrow_widgets%>
var single_bottom_widgets = <%=single_bottom_widgets%>
var singleWidgets = <%=singleWidgets%>;
var wideWidgets = <%=wideWidgets%>;
var narrowWidgets = <%=narrowWidgets%>;
var singleBottomWidget = <%=singleBottomWidget%>;
	getAllWidgets('single_widgets', singleWidgets, single_widgets);
	getAllWidgets('wide_widgets', wideWidgets, wide_widgets);
	getAllWidgets('narrow_widgets', narrowWidgets, narrow_widgets);
	getAllWidgets('single_bottom_widgets', singleBottomWidget, single_bottom_widgets);
	
/* console.log(JSON.stringify(single_widgets));
console.log(JSON.stringify(wide_widgets));
console.log(JSON.stringify(narrow_widgets));
console.log(JSON.stringify(single_bottom_widgets)); */
<%-- var eventid='<%=eventid%>';
var dataHashJS=<%=new JSONObject(dataHash).toString()%>;
var refHashJS=<%=new JSONObject(refHash).toString()%>;
var configHashJS=<%=new JSONObject(configHash).toString()%>; --%>
//configHashJS=eval('('+configHashJS+')');
//console.log('this this'+Object.keys(configHashJS.styles));
/* console.log("this is refHashJS"+JSON.stringify(refHashJS));
console.log("this is configHashJS"+JSON.stringify(configHashJS));
console.log("this is dataHashJS"+JSON.stringify(dataHashJS));
	getWidgets(single_widgets,'single_widgets',eventid,refHashJS,dataHashJS,configHashJS);
	getWidgets(wide_widgets,'wide_widgets',eventid,refHashJS,dataHashJS,configHashJS);
	getWidgets(narrow_widgets,'narrow_widgets',eventid,refHashJS,dataHashJS,configHashJS);
	getWidgets(single_bottom_widgets,'single_bottom_widgets',eventid,refHashJS,dataHashJS,configHashJS); */
</script>
</div>
</body>
</html>
