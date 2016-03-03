<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
<s:set value="%{streamerData}" var="data" id="data" />
<script type="text/javascript">
document.write('<style type="text/css">');
document.write('.ItemBody {');
document.write('        font-family: <s:property value="#data['MEDIUM_FONT_TYPE']"/>;');
document.write('        font-size:<s:property value="#data['MEDIUM_FONT_SIZE']"/>;');
document.write('        padding: 0px;');
document.write('        color: <s:property value="#data['MEDIUM_TEXT_COLOR']"/>;');
document.write('        margin-top: 0px;');
document.write('        margin-left: 5px;');
document.write('        margin-right: 5px;');
document.write('        margin-bottom: 10px;');
document.write('}');
document.write('.titletop {');
document.write('        font-family: <s:property value="#data['BIGGER_FONT_TYPE']"/>;');
document.write('        font-size:<s:property value="#data['BIGGER_FONT_SIZE']"/>;');
document.write('        font-weight:bold;');
document.write('        text-align:center;');
document.write('        color: <s:property value="#data['BIGGER_TEXT_COLOR']"/>;');
document.write('        margin-top: 0px;');
document.write('        margin-bottom: 15px;');
document.write('}');
document.write('.bottom {');
document.write('	z-index: 10;');
document.write('        text-align:center;');
document.write('        font-family: <s:property value="#data['SMALL_FONT_TYPE']"/>;');
document.write('        font-size:<s:property value="#data['SMALL_FONT_SIZE']"/>;');
document.write('        color: <s:property value="#data['SMALL_TEXT_COLOR']"/>;');
document.write('	padding: 5px;');
document.write('}       ');
document.write('.blockblock{');
document.write('        border: solid 1px <s:property value="#data['BORDERCOLOR']"/>;');
document.write('        width:<s:property value="#data['STREAMERSIZE']"/>;');
document.write('        font-size:11px;');
document.write('        background:<s:property value="#data['BACKGROUND']"/>;');
document.write('}');
document.write('.blocklist{');
document.write('        margin-top: 5;');
document.write('        margin-bottom: 0;');
document.write('        margin-left: 0;');
document.write('        margin-right: 0;');
document.write('        padding: 0;');
document.write('        border: solid 0px <s:property value="#data['BORDERCOLOR']"/>;');
document.write('        background:<s:property value="#data['BACKGROUND']"/>;');
document.write('}');
document.write('.blockitem {');
document.write('	display: inline; ');
document.write('	list-style-type: none; ');
document.write('	list-style-image: none; ');
document.write('	padding: 0; ');
document.write('        margin-right: 3;');
document.write('        margin-left: 3;');
document.write('        margin-top: 3;');
document.write('        margin-bottom: 39;');
document.write('        background:<s:property value="#data['BACKGROUND']"/>;');
document.write('} ');
document.write('img{');
document.write('	border: solid 0px <s:property value="#data['BORDERCOLOR']"/>; ');
document.write('	margin-left: 0px; ');
document.write('} ');
document.write('.blockitem a { ');
document.write('	 color: <s:property value="#data['LINKCOLOR']"/> ');
document.write('}');
document.write('a {');
document.write('        text-decoration: none;');
document.write('        font-family: Verdana;');
document.write('        color: <s:property value="#data['LINKCOLOR']"/>');
document.write('}');
document.write('</style>');
document.write('<div class="blockblock" >');
document.write('<ul class="blocklist">');
</script>
<s:if test="%{events.size-1>0}">
<script type="text/javascript">
document.write('<li class="blockitem">');
document.write('<div class="titletop" >');
document.write('<s:property value="#data['TITLE']"/>');
document.write('</div>');
document.write('</li>');
</script>
</s:if>
<s:iterator value="%{events}" var="type" id="event">
<script type="text/javascript">
document.write('<li class="blockitem">');
document.write('<div class="ItemBody">');
document.write('<s:property value="#event['eventdate']"/>');	
document.write('  ');
document.write('<s:property value="#event['eventname']" escape="false"/>');
document.write('</div>');
document.write('</li>');		
</script>
</s:iterator>
<s:if test="%{#event['link'] == 'yes'}">
<script type="text/javascript">
document.write('</ul>');
document.write('<div class="bottom"><a href="http://www.eventbee.com" target="blank" style="text-decoration: none; " ><font color="#696969">Powered By Eventbee</font></a></div>');
</script>
</s:if>
</body>
</html>