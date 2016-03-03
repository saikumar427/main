<%@ page contentType="text/html; charset=UTF-8" %>
<style>
.carousel-inner>.item>img,.carousel-inner>.item>a>img {
	font-weight: normal !important;
	display: block;
	margin: auto;
	max-width: 100%;
}

.fa {
	font-weight: none !important;
	font-size: 35px !important;
	color: #bbb !important;
}
</style>

</head>
<style>
.white {
	color: white;
}

.btn-lg {
	font-size: 38px;
	line-height: 1.33;
	border-radius: 6px;
}

.box>.icon {
	text-align: center;
	position: relative;
}

.box>.icon>.image {
	position: relative;
	z-index: 2;
	margin: auto;
	width: 88px;
	height: 88px;
	border: 7px solid white;
	line-height: 88px;
	border-radius: 50%;
	background: #63B76C;
	vertical-align: middle;
}

.box>.icon:hover>.image {
	border: 4px solid #428bd3;
}

.box>.icon>.image>i {
	font-size: 40px !important;
	color: #fff !important;
}

.box>.icon:hover>.image>i {
	color: white !important;
}

.box>.icon>.info {
	margin-top: -24px;
	background: rgba(0, 0, 0, 0.04);
	border: 1px solid #e0e0e0;
	padding: 15px 0 10px 0;
}

.box>.icon>.info>h3.title {
	color: #428bd3;
	font-size: 32px;
	font-weight: 500;
}

.box>.icon>.info>p {
	color: #666;
	line-height: 1.5em;
	margin: 20px;
}

.box>.icon:hover>.info>h3.title,.box>.icon:hover>.info>p,.box>.icon:hover>.info>.more>a
	{
	color: #428bd3;
}

.box>.icon>.info>.more a {
	color: #222;
	line-height: 12px;
	text-transform: uppercase;
	text-decoration: none;
}

.box>.icon:hover>.info>.more>a {
	color: #000;
	padding: 6px 8px;
	border-bottom: 4px solid black;
}

.box .space {
	height: 30px;
}

.shape {
	border-style: solid;
	border-width: 0 80px 60px 0;
	float: right;
	height: 0px;
	width: 0px;
	-ms-transform: rotate(360deg); /* IE 9 */
	-o-transform: rotate(360deg); /* Opera 10.5 */
	-webkit-transform: rotate(360deg); /* Safari and Chrome */
	transform: rotate(360deg);
}

.listing {
	background: #fff;
	border: 1px solid #ddd;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
	margin: 15px 0;
	overflow: hidden;
}

.listing:hover {
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: rotate scale(1.1);
	-webkit-transition: all 0.4s ease-in-out;
	-moz-transition: all 0.4s ease-in-out;
	-o-transition: all 0.4s ease-in-out;
	transition: all 0.4s ease-in-out;
}

.shape {
	border-color: rgba(255, 255, 255, 0) #d9534f rgba(255, 255, 255, 0)
		rgba(255, 255, 255, 0);
}

.listing-radius {
	border-radius: 7px;
}

.listing-danger {
	border-color: #d9534f;
}

.listing-danger .shape {
	border-color: transparent #d9533f transparent transparent;
}

.listing-success {
	border-color: #5cb85c;
}

.listing-success .shape {
	border-color: transparent #5cb75c transparent transparent;
}

.listing-default {
	border-color: #999999;
}

.listing-default .shape {
	border-color: transparent #999999 transparent transparent;
}

.listing-primary {
	border-color: #428bca;
}

.listing-primary .shape {
	border-color: transparent #318bca transparent transparent;
}

.listing-info {
	border-color: #5bc0de;
}

.listing-info .shape {
	border-color: transparent #5bc0de transparent transparent;
}

.listing-warning {
	border-color: #f0ad4e;
}

.listing-warning .shape {
	border-color: transparent #f0ad4e transparent transparent;
}

.shape-text {
	color: #fff;
	font-size: 12px;
	font-weight: bold;
	position: relative;
	right: -34px;
	top: 2px;
	white-space: nowrap;
	-ms-transform: rotate(30deg); /* IE 9 */
	-o-transform: rotate(360deg); /* Opera 10.5 */
	-webkit-transform: rotate(30deg); /* Safari and Chrome */
	transform: rotate(30deg);
}

.listing-content {
	padding: 0 20px 10px;
}

.details {
	min-height: 355px;
	display: inline-block;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.height {
	min-height: 200px;
}

.icon {
	font-size: 47px;
	color: #5CB85C;
	cursor: pointer;
}

.iconbig {
	font-size: 77px;
	color: #5CB85C;
}

.table>tbody>tr>.emptyrow {
	border-top: none;
}

.table>thead>tr>.emptyrow {
	border-bottom: none;
}

.table>tbody>tr>.highrow {
	border-top: 3px solid;
}

.panel:hover {
	background-color: none;
}

.clearfix {
	clear: both;
}

.rowcolor {
	background-color: #CCCCCC;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.ratetext {
	font-size: 37px;
	text-decoration: underline;
	padding-bottom: 10px;
}

.votes {
	font-size: 47px;
	padding-right: 20px;
	color: #197BB5;
}

a.list-group-item {
	height: auto;
	min-height: 250px;
}

a.list-group-item:hover,a.list-group-item:focus {
	border-left: 10px solid #5CB85C;
	border-right: 10px solid #5CB85C;
}

a.list-group-item:hover,a.list-group-item:focus {
	background-color: #edf5fc !important;
}

a.list-group-item {
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

#create {
	cursor: pointer;
}

#promote {
	cursor: pointer;
}

#manage {
	cursor: pointer;
}

.blogicon2 {
	font-size: 128px;
}

.blogicon3 {
	font-size: 60px;
}

.btn-lg {
	font-size: 38px;
	line-height: 1.33;
	border-radius: 6px;
}

.box>.icon {
	text-align: center;
	position: relative;
}

.box>.icon>.image {
	position: relative;
	z-index: 2;
	margin: auto;
	width: 88px;
	height: 88px;
	border: 7px solid white;
	line-height: 88px;
	border-radius: 50%;
	background: #63B76C;
	vertical-align: middle;
}

.box .space {
	height: 30px;
}

.listing-content {
	padding: 0 20px 10px;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.icon {
	font-size: 47px;
	color: #5CB85C;
}

.iconbig {
	font-size: 77px;
	color: #5CB85C;
}

.fixed {
	top: 0;
	position: fixed;
	display: none;
	border: none;
	z-index: 999999999;
	background-color: #EEEEEE;
}

.section_header {
	font-size: 42px;
	font-weight: 500;
	text-align: center;
	color: #999999;
}

.main_header_orange {
	font-size: 42px;
	font-weight: 800;
	text-align: center;
	color: #F27D2F;
}

.caption_header_blue {
	font-size: 32px; #
	font-weight: normal;
	text-align: center;
	color: #428BCA;
}

.caption_header_blue_faq {
	font-size: 32px; #
	font-weight: normal; #
	text-align: center;
	color: #428BCA;
}

.medium_desc_grey {
	color: #999999;
	font-size: 20px;
	text-align: center;
}

.normal_desc_grey {
	color: #333333;
	font-size: 14px;
	text-align: center;
}

.normal_desc_grey_ans {
	color: #333333;
	font-size: 14px;
}

.dropdown {
	background-color: white;
	border: 1px solid white;
	border-radius: 11px 11px 11px 11px;
	height: 182px;
	margin: 26px;
	width: 212px;
}

.subevent {
	border: 1px solid #F3F6FA;
	background-color: #F3F6FA;
	border-radius: 27px 27px 27px 27px;
	cursor: pointer;
	height: 45px;
	margin: 7px;
	padding: 5px;
	width: 315px;
	color: #ffffff;
}

.textbox {
	margin: 10px;
	padding-left: 30px;
}

.input-field {
	background-color: #FFFFFF;
	border: medium none #FFFFFF;
	height: 30px;
	width: 50px;
}

.avgtooltip {
	background-color: #F27A28;
	bottom: 18px;
	box-shadow: 0 0 1px 1px #DDDDDD;
	color: #FFFFFF;
	left: 218px;
	padding: 17px 0 5px;
	position: absolute;
	text-align: center;
	width: 50px;
}

.range-max {
	font-size: 20px;
}

.range-min {
	font-size: 20px;
}

li {
	list-type: desc;
}

.serialnumber { #
	color: #c6c6c6;
	font-family: Arial, Helvetica, sans-serif; #
	font-size: 23px;
	font-weight: bold;
	line-height: 22px;
	padding-bottom: 15px;
	width: 40px;
}

ol.u {
	list-style-type: none;
}

a {
	outline: none !important;
}

.responsive-video {
	position: relative;
	padding-bottom: 200px;
	padding-top: 150px;
	overflow: hidden;
}

.responsive-video iframe,.responsive-video object,.responsive-video embed
	{
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}
</style>
<div style="width: 100%; background-color: #F3F6FA" class="container">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<h1 class="col-md-12 section_header">Vende Tiquetes Online de Asientos Reservados con Eventbee</h1>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">Eventbee tiene las características y personalizaciones para potencializar cualquier tipo de venta de tiquetes en taquillas de teatros o escenarios  y en asignación de asientos.</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Diseños y Digitalización Personalizables</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Los escenarios pueden ser de diferentes tipos. Los hay con formas y tamaños diferentes, desde centros de artes escénicas hasta teatros. Aquí en Eventbee, digitalizamos tu escenario para que puedas manejar y organizar la cantidad de asientos, haciendo fácil y rápida la venta de tiquetes de asientos reservados para los asistentes.

			</div>
		</div>
		<!--------------------- carousel - start ---------------------------->
		<div class="col-md-11">
			<br> <br>
			<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
				<!-- Wrapper for slides -->
				<div class="carousel-inner">
					<div class="item active">
						<img src="/main/images/Venue_1_Theatre.PNG"
							alt="Digitized venue layout for reserved seating" width="600px"
							height="473px">
					</div>

					<div class="item">
						<img src="/main/images/Venue_3_Tables.PNG"
							alt="Digitized venue layout for reserved seating" width="600px"
							height="473px">
					</div>

					<div class="item">
						<img src="/main/images/Venue_4_Tent.PNG"
							alt="Digitized venue layout for reserved seating" width="600px"
							height="473px">
					</div>
				</div>
				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel"
					style="background-image: none !important;" role="button"
					data-slide="prev"> <span style="font-size: 55px !important;"
					class="glyphicon glyphicon-chevron-left fa fa -3x fa-chevron-left"
					aria-hidden="true"></span> <span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel"
					style="background-image: none !important;" role="button"
					data-slide="next"> <span style="font-size: 55px !important;"
					class="glyphicon glyphicon-chevron-right fa fa-3x fa-chevron-right"
					aria-hidden="true"></span> <span class="sr-only">Next</span>
				</a>
			</div>

			<br>
		</div>
		<!--------------------- carousel - end ---------------------------->
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Tamaño de Escenario y Número de Asientos</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
			En Eventbee, trabajamos con los administradores de eventos para construir y personalizar una distribución ideal que cumpla con los requerimientos y cualquier tipo de ajustes o especificaciones que se requieran para la venta de tiquetes con asientos reservados.
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Asignación de Asientos</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Las herramientas online de Eventbee te permiten administrar los asientos reservados mucho más fácil. Nuestro Proceso Paso-a-Paso te ayudará a organizar y administrar cómo elegir los tipos de asientos para vender, para qué tipo de tiquetes y a qué precios. También puedes cambiar los tipos de tiquetes y precios para aquellos asientos dependiendo de la demanda de tu evento.
			</div>
		</div>
		<br>

		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Venta de Tiquetes</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Los asistentes que visitan tu página verán un diseño digitalizado del escenario, seleccionarán sus asientos preferidos y realizarán la compra. Nosotros enviaremos un correo electrónico de confirmación con los asientos seleccionados para los asistentes, lo cual hace fácil para ti el chequeo de los asistentes y que estos tomen sus respectivos asientos.
			</div>
		</div>
		<br>

		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Fácil Administración</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Nuestras herramientas fácil-de-usar te permiten cambiar dinámicamente la asignación de asientos, precios de tiquetes, etc. Incluso puedes asignar compra de asientos para personas discapacitadas y/o con movilidad reducida  y también bloquear asientos por venta. Los reportes ampliados de los asistentes con información de asientos reservados harán fácil para ti registrar a los asistentes en el lugar del evento. También puedes usar nuestra Aplicación Eventbee para dispositivos Android para realizar un chequeo más fácil de tus asistentes.
			</div>
		</div>
		<br>

		<div>
			<div style="float: left">
				<a href="https://play.google.com/store/apps/details?id=com.eventbee.eventbeemanager&hl=en" target="_blank"> <img src="/main/images/googleplayicon.png"
					width="150px" style="margin: 7px 15px 0 0">
				</a>
			</div>
			<div style="float: left">
				<a
					href="http://www.amazon.com/Eventbee-Manager/dp/B00O8L7V5Y/ref=sr_1_1?s=mobile-apps&amp;ie=UTF8&amp;qid=1412689478&amp;sr=1-1&amp;keywords=eventbee"
					target="_blank"> <img src="/main/images/amazonappstore.png"
					width="150px" style="margin-top: 7px">
				</a>
			</div>
		</div>

	</div>
</div>
<!-- <script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="/main/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$('#emailus').click(
				function() {
					$('.modal-title').html('Contact Eventbee');
					$('#myModal').on(
							'show.bs.modal',
							function() {
								$('iframe#popup').attr("src",
										'/main/user/homepagesupportemail.jsp');
								$('iframe#popup').css("height", "440px");
							});
					$('#myModal').modal('show');
				});
		$('#myModal')
				.on(
						'hide.bs.modal',
						function() {
							$('iframe#popup').attr("src", '');
							$('#myModal .modal-body')
									.html(
											'<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
						});
	});
</script>
