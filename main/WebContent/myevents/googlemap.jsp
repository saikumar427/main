    <style>
      html, body, #map-canvas {
        margin: 0;
        padding: 0;
        height: 100%;
      }
    </style>
     <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>  
    <script>    
	var map;
	var lat=<%=request.getParameter("lat")%>;
	var lng=<%=request.getParameter("lng")%>;
	function initialize() {
	  var mapOptions = {
	    zoom: 13,
	    center: new google.maps.LatLng(lat,lng),
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	  };
	  map = new google.maps.Map(document.
	getElementById('map-canvas'),
	      mapOptions);
	  (new google.maps.Marker({map:map,draggable:!1})).setPosition(map.getCenter());
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);

    </script>

    <div id="map-canvas"></div>
