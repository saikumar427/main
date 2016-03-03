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
		<h1 class="col-md-12 section_header">Gestión de Asistentes y Eventos con Aplicaciones Eventbee</h1>
		
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">Eventbee proporciona todas las herramientas y trucos que necesitas para manejar con éxito tu evento, con características en las Aplicaciones Eventbee, como el registro de los asistentes, la venta de entradas, etc.</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Check-in de Asistentes</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 medium_desc_grey" style="text-align: left;">Con la Lista de Asistentes</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
			Puedes administrar y chequear la asistencia de personas en el evento generando e imprimiendo la lista de asistentes. Desde la página administradora del Evento, imprime la lista de asistentes y permite que el personal encargado del evento realice el chequeo desde el lugar del encuentro. Luego ellos revisarán los nombres de los asistentes con los correos de confirmación que estos traigan al lugar. <br><br>			
			</div>
			</div>
			
		<div class="row">
			<div class="col-md-12 medium_desc_grey" style="text-align:left;">Con la Aplicación Eventbee para Android</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div class="features-ticketing">
					<p>Con la aplicación para Android, puedes administrar y realizar tareas fácilmente, incluso cuando no hay Internet en el lugar.</p>
					<p>Estas son algunas de las características que ofrecemos en la aplicación de Android:</p>
					<i>Asistente de Búsqueda Fácil</i>
					<p>Con el Asistente de búsqueda fácil, puedes encontrar rápidamente los asistentes por su nombre o apellido. También puedes ordenar y filtrar la lista de asistentes por su nombre o apellido o su estado de registro.</p>
					<i>Escaneo de Código QR</i>
					<p>Puedes utilizar el código QR del asistente que se encuentra en tu correo de confirmación impreso o en tu teléfono móvil para chequearlos en el evento. La cámara integrada en el dispositivo se puede utilizar para chequear rápidamente los asistentes en el evento.</p>					
					<i>Escaneo de código de barras</i>
					<p>Con un bluetooth habilitado con escáner de código de barras, se puede chequear a los asistentes de forma más rápida mediante el escaneo del código contenido impreso en sus correos de confirmación o en sus dispositivos móviles.</p>
					<i>Check-In sin Acceso a Internet</i>
					<p>Si no tienes acceso a Internet en el evento, hay una opción de Check-In Offline. Con esta función, se puede descargar la lista de asistentes a su aplicación, ya sea en casa o en su oficina donde usted tenga conexión a Internet. Esto entonces hace posible el registro de entrada de los asistentes en el lugar sin tener que conectarse a Internet.</p>
					<p>Esta aplicación es gratuita y funciona en todos los últimos dispositivos Android. Haz clic abajo para descargar la aplicación:</p>		
<div></div>					
								
	<div style="float:left; width=20%;"> <a href="//play.google.com/store/apps/details?id=com.eventbee.eventbeemanager" target="_blank"> <img src="/main/images/googleplayicon.png" alt="Event Manager App for Android - Event Management" width="150px" style="margin:7px 15px 0 0;"> </a>
				</div>	
				<div style="float:left"><a href="//www.amazon.com/Eventbee-Manager/dp/B00O8L7V5Y/ref=sr_1_1?s=mobile-apps&ie=UTF8&qid=1412689478&sr=1-1&keywords=eventbee" target="_blank"><img src="/main/images/amazonappstore.png"  alt="Event Manager App for Android - Event Management" width="150px" style="margin-top:7px"></a>
				</div>																
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 medium_desc_grey" style="text-align:left;">Con la Aplicación para iOS</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>La aplicación para iOS es una aplicación hecha específicamente para iPhone y iPad. Puedes utilizar esta aplicación para registrar la entrada de los asistentes en la puerta principal. Tan solo escaneando sus códigos de barra o códigos QR ubicados en el contenido de sus correos de confirmación o en sus teléfonos móviles. Una vez realizado el chequeo de los asistentes, nosotros sincronizamos esa información con los servidores Eventbee de manera que tengas muchos registros de entrada de asistentes en diferentes iPhones o iPads. Ya no tienes que usar grandes y costosos dispositivos de escaneo, lo que resulta en un gran ahorro. </p>
					
					<p>Esta aplicación es gratuita en la App Store. Haz clic abajo para descargar la aplicación:</p>
					<div></div>
					<div style="float:left; width=20%;"><a href="https://itunes.apple.com/us/app/eventbee-real-time-attendee/id403069848?mt=8" target="_blank"><img src="/main/images/home/iphone.png" alt="Attendee Check-In App for iOS - Event Management" width="150px" style="margin:7px 15px 0 0"></a>
				</div>	
											
				</div>
			</div>
		</div>
		<br>
			<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Vende Tiquetes</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				
					<p>¿Por qué no vender Tiquetes en el lugar mediante el uso de la aplicación Eventbee para  Android? Todo lo que tienes que hacer es habilitar el método de procesamiento de pagos que te gustaría usar y la información que deseas obtener de los asistentes directamente desde la página de administración del evento. El personal en la entrada del evento puede vender tiquetes directamente en el lugar, procesar los pagos y generar confirmaciones de Tiquetes en tiempo real.</p>	
															
				
			</div>
		</div>
		
		<br>
			<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Co-Administradores</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>Los Co-Administradores son una gran manera de delegar y extender las tareas del evento para facilitar la gestión de éste, como el control de los asistentes en el lugar o la gestión de la venta de tiquetes. Los Co-Administradores sólo pueden acceder a la parte de la funcionalidad de gestión de eventos que les asignes. Esta es una gran manera de dar tareas a los demás, sin darles acceso a otra información confidencial, como el volumen de ventas y los ingresos de venta de tiquetes.</p>										
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