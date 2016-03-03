<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../build/json/json-min.js"></script>
<script type="text/javascript" src="../build/swf/swf-min.js"></script>
<script type="text/javascript" src="../build/charts/charts-min.js"></script>
<title>Insert title here</title>
<style>
#chart
	{
		float: left;
		width: 450px;
		height: 300px;
	}

	.chart_title
	{
		display: block;
		font-size: 1.2em;
		font-weight: bold;
		margin-bottom: 0.4em;
	}
</style>
</head>
<body>
<div id="ticketschart_recentevent"  style="width: 400px; height: 600px;"></div>
<script type="text/javascript">

	YAHOO.widget.Chart.SWFURL = "../build/charts/assets/charts.swf";

//--- data

	YAHOO.example.publicOpinion =
	[
		{ response: "My name is khan Summer", count: 564815 },
		{ response: "How dare you try such a big name Fall", count: 664182 },
		{ response: "Spring", count: 248124 },
		{ response: "Winter", count: 271214 },
		{ response: "Undecided", count: 81845 },
		{ response: "Fall", count: 664182 },
		{ response: "Spring", count: 248124 },
		{ response: "Winter", count: 271214 }
	]

	var opinionData = new YAHOO.util.DataSource( YAHOO.example.publicOpinion );
	opinionData.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
	opinionData.responseSchema = { fields: [ "response", "count" ] };

//--- chart

	var mychart = new YAHOO.widget.PieChart( "chart", opinionData,
	{
		dataField: "count",
		categoryField: "response",
		style:
		{
			padding: 20,
			legend:
			{
				display: "bottom",
				padding: 10,
				spacing: 5,
				font:
				{
					family: "Arial",
					size: 13
				}
			}
		},
		//only needed for flash player express install
		expressInstall: "assets/expressinstall.swf"
	});


</script>
</body>
</html>