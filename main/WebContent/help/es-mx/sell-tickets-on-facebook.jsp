<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
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
			<h1 class="col-md-12 section_header">Vender Tiquetes en Facebook con la Aplicación Eventbee para Venta de Tiquetes</h1>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">Eventbee cuenta con una aplicación de venta de tiquetes a través de Facebook la cual permite a los administradores de eventos vender tiquetes directamente a través de Facebook a sus seguidores. La aplicación Eventbee está diseñada para ayudar a los administradores a usar los medios sociales como un impulso para contactar seguidores y vender a la mayor cantidad de personas en Facebook.</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Requerimientos para la Venta de Tiquetes en Facebook</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Para usar la aplicación de venta de tiquetes en Facebook, deberás contar con una página de seguidores en Facebook. Si no cuentas con una, ingresa a tu cuenta de Facebook como de costumbre, haz clic en la flecha de la esquina superior derecha de la página, a continuación aparecerá un menú desplegable, en éste busca la opción Crear Página y sigue los pasos para crear tu propia página de seguidores. <br>
			</div>
		</div>
		<br>


		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Agregar la Aplicación Eventbee a tu Página de Seguidores de Facebook</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Para agregar la Aplicación Eventbee a tu Página de Seguidores de Facebook, sigue las siguientes instrucciones:
				<br><br>
				<ol>
					<li ><span>1.</span>Ve a: <a
						href="https://apps.facebook.com/eventbeeticketing">https://apps.facebook.com/eventbeeticketing</a>
					</li>
					<li><span>2.</span>Da clic en "Install Facebook Eventbee Ticketing Application to your Fan Page". </li>
					<li><span>3.</span>Selecciona la página de seguidores que deseas agregar a la aplicación (si cuentas con varias páginas de seguidores), luego haz clic en  "Add Page Tab".</li>
					<li><span>4.</span>Haz clic en Aceptar en la páginas de permisos.</li>
					<li><span>5.</span>Verás una página de inicio de sesión de Eventbee. Si tienes una cuenta como cliente de Eventbee ingresa tus datos de registro. Sino, regístrate para obtener una cuenta en   <a
						href="/main/user/signup?lang=es-co" target="_blank">http://www.eventbee.com/main/user/signup.</a>
					</li>
					<li><span>6.</span>Después de iniciar sesión satisfactoriamente, serás direccionado al Administrador de Ajustes de la página, donde podrás personalizar la visualización de tus eventos.</li>					
				</ol>
				<br>
					<p>¡Eso es todo! de esta manera el botón de &quot;Compra de Tiquetes&quot;  estará visible para todos tus seguidores y ellos podrán ahora comprarlos directamente desde tu página de seguidores en Facebook.</p>
					<p>TIP: Si la pestaña de &quot;Buy Tickets&quot; no es visible en tu página de seguidores, haz clic en &quot;Más&quot;, y luego en &quot;Administrar Pestañas&quot;, y arrastra &quot;Buy Tickets&quot; a una posición donde tus seguidores puedan verla cuando ingresen a tu página...</p>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Características de la Aplicación Eventbee de Venta de Tiquetes en Facebook</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					Algunas de la características que ofrece Eventbee para realizar la venta de tiquetes en Facebook a través de la aplicación son:
					<ul>
						<br>
						<li>Capacidad para crear una pestaña &quot;Comprar Tiquetes&quot; en tu página de seguidores de Facebook para permitir a los asistentes comprar sus tiquetes directamente desde Facebook...</li>
						<li>Vincular la pestaña &quot;Comprar Tiquetes&quot;  a uno o a todos tus eventos para permitir que los asistentes puedan comprar los tiquetes de uno o varios eventos.</li>
						<li>Personalizar la imagen y redacción para dar un diseño propio a tu evento.</li>
						<li>Rastrear la venta de tiquetes desde tu página de seguidores en Facebook para saber cuántas personas han comprado tiquetes y realizar cambios basados en estas ventas.</li>
						<br>

					</ul>
					<p>La Aplicación Eventbee de Venta de Tiquetes en Facebook es una gran herramienta para la venta de tiquetes, para dirigir todo el tráfico a tu página de seguidores en Facebook y promocionar tus eventos a todos tus seguidores. ¡Comienza a vender tiquetes en Facebook desde tu Página de Seguidores!</p>
				</div>
			</div>
		</div>
		<div
			style="background-color: #F3F6FA; width: 100%; padding: 60px 0px 50px 0px;">
			<div class="row">
				<div class="col-md-12" id="fbappimg">
					<div class="col-md-12"><img width="348px" src="/main/images/es-co/fbapp.png"  alt="Install Facebook Ticketing App on Your Fan Page, Sell Tickets on Facebook!"
						alt="Eventbee Ticketing Application for Facebook">
						<a style="position: relative;" href="http://apps.facebook.com/eventbeeticketing" class="btn btn-lg btn-primary" target="_blank" style="">Instalar Aplicación de Facebook</a>
				</div>
				
			</div>
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
</html>