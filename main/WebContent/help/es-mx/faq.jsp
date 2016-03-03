<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/main/bootstrap/css/main.css" rel="stylesheet">
<link href="/main/bootstrap/css/bootswatch.min.css" rel="stylesheet">
<script src="/main/bootstrap/js/jquery-1.10.2.min.js"></script>
</head>
<style>
.navbar-nav {
	margin: 18px -8px;
}
li.bullet{
	list-style:disc !important
}
.signup {
	margin-top: -8px
}
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
<body>
	<div style="width: 100%; background-color: #FFFFFF" class="container">
		<div class="container"
			style="padding-bottom: 50px; padding-top: 50px;">
			<div class="row">
				<div class="col-md-12 section_header" style="padding-bottom: 20px">
					PREGUNTAS FRECUENTES</div>
			</div>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">1. Qué es Eventbee?</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Fundada en 2003, Eventbee ofrece venta de tiquetes a más de 40.000 administradores de eventos en todo el mundo. Operamos con una sola misión: Maximizar las ventas de tiquetes mientras se proporciona la mejor tecnología para los administradores de eventos alrededor del mundo. Transformamos la industria mediante la introducción de nuestro sencillo modelo de precios con tarifa fija. Independientemente del precio o la moneda local, nuestros precios permanecen iguales!!
</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Echa un vistazo a nuestro breve video de introducción!</div>
			</div>
			<br />
			<div class="row" style="margin-left: 5px !important;">
				<div class="col-md-7 normal_desc_grey_ans responsive-video">
					<iframe width="640" height="360"
						src="http://www.youtube.com/embed/EI1_xDgGvb0?rel=0"
						frameborder="0" allowfullscreen></iframe>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">2. ¿Cómo funciona Eventbee?</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					Eventbee es fácil de usar y la creación de un evento es muy simple. Sólo tienes que seguir estos tres simples pasos:<br>
					<br>
				</div>
				<div class="col-md-10 normal_desc_grey_ans">
					<ol class="pricing_desc_grey u"
						style="padding-left: 4px !important; margin-bottom: 0px !important;">
						<li><span class="">1)</span> Regístrate para obtener una cuenta gratuita en 
							<a href="/main/user/signup?lang=es-mx" target="_blank">http://www.eventbee.com/main/user/signup</a></li>
						<li><span class="">2)</span> Crea y edita la información sobre tu evento y tiquetes</li>
						<li><span class="">3)</span> Comienza a vender entradas en tu propia página web, página seguidores en Facebook y muchas otras herramientas</li>
					</ol>
					<br>
				</div>
				<div class="col-md-12 normal_desc_grey_ans">
					Cuando alguien compra un tiquete para tu evento, recibirá un correo de confirmación con un código QR e información adicional que sea necesaria para facilitar el registro. Puedes controlar la venta de tiquetes en tiempo real desde tu cuenta, y generar informes de los asistentes en cualquier momento .</br>

Para una descripción más detallada acerca de cómo funciona Eventbee, visita nuestra página web  <a href="/main/how-it-works/es-mx"
						target="_blank">http://www.eventbee.com/main/how-it-works</a>
				</div>
				<br>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">3. ¿Qué características especiales en gestión de eventos tiene Eventbee?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Hemos utilizado nuestros más de 10 años de experiencia en la industria implementando una gran tecnología, para que nuestros clientes puedan gestionar más fácilmente sus eventos de principio a fin. Por favor, visita el siguiente link para obtener más información:
					<a target="_blank">http://www.eventbee.com/main/features</a></div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">4. ¿Qué características especiales en promoción de eventos tiene Eventbee?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Eventbee cuenta con una gran integración a través de la difusión en medios sociales, como:</div>
			</div>
			<br>
			<ul type="disc" style="padding-left: 20px !important; ">
				<li class="bullet">Herramientas para compartir en redes sociales la página de tu evento</li>
				<li class="bullet">Comentarios de Facebook integrados en la página de tu evento y en el perfil del usuario</li>
				<li class="bullet">Listas RSVP en Facebook que muestra directamente los asistentes y los conduce a la página de tu evento</li>
				<li class="bullet">Venta de tiquetes en red: Aprovecha a tus asistentes para que vendan tiquetes por ti</li>
			</ul>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Echa un vistazo a nuestro video de Venta de Tiquetes en Red de aquí.</div>
			</div>
			<br>
			<div class="row" style="margin-left: 5px !important;">
				<div class="col-md-7 normal_desc_grey_ans responsive-video">
					<iframe width="640" height="360"
						src="http://www.youtube.com/embed/sz9gL5Iz5Gc?rel=0"
						frameborder="0" allowfullscreen></iframe>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">5. ¿Cuáles son las tarifas por usar el servicio de Eventbee?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Hay dos tarifas que estarías pagando por la Venta de Tiquetes Online</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					<b>Tarifa Fija de Servicio</b><br> Para realizar la Venta de Tiquetes más sencilla, nuestra tarifa es de un dólar por boleto vendido, independientemente del precio del Tiquete. No hay costos porcentuales adicionales como en otras compañías. Para obtener más detalles, por favor visita <a href="/main/pricing/es-mx" target="_blank">
					 http://www.eventbee.com/main/pricing</a>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					<b>Tarifa de Servicio por Procesamiento con Tarjeta de Crédito</b> <br>Dependiendo del servicio de procesamiento que decidas usar, pagarás una cuota de procesamiento de tarjetas de crédito. Nosotros soportamos las plataformas de Paypal, Stripe, Braintree y Authorize.net. Estos proveedores cobran alrededor de 2.9% + 30 centavos de dólar por transacción.
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">También tienes la opción de usar la tarjeta de crédito Eventbee, nuestra tarifa de procesamiento es de 4,95% + 50 centavos de dólar. Con esta opción, no hay necesidad de registrarse en otros servicios de procesamiento de tarjetas de crédito.</div>
			</div>
			<br>
			<div class="row">

				<div class="col-md-12 normal_desc_grey_ans">Tienes control completo sobre cuales tarifas recaudar por parte del asistente. Las siguientes son las tres opciones:</div>
			</div>
			<br>
			<ul>
				<li class="bullet">Recauda la tarifa de servicio y la cuota de procesamiento de tarjetas de crédito desde tus asistentes</li>
				<li class="bullet">Divide las tarifas con tus asistentes</li>
				<li class="bullet">Paga tu mismo la tarifa de servicio y la cuota de procesamiento de tarjetas de crédito</li>
			</ul>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">También puedes recaudar cualquier cargo adicional a los asistentes del evento.
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">6.  ¿Cuándo recibo los ingresos procedentes de la Venta de Tiquetes?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans"> Si utilizas PayPal, Stripe, Braintree o Authorize.net para el procesamiento de tarjetas de crédito, el dinero se consigna directamente en tu cuenta.</div>
					</div>
				<br>
            <div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Para los pagos procesados con Eventbee, el 90% de los ingresos se envían por correo postal a través de cheques, la primera semana del mes para todos los eventos cerrados en el mes anterior, y el 10% restante se envía en los 60 días siguientes. El saldo mínimo requerido en la cuenta para procesar cheques es de $100 dólares.</div>
					</div>
				<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">7. ¿Cuánto estoy ahorrando por cambiarme a Eventbee?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">La mayoría de los clientes ahorran una gran cantidad de dinero al cambiarse a Eventbee. El hecho de que no cobramos ninguna tasa porcentual significa que ahorras más dinero así los precios de tus tiquetes suban. Te animamos a revisar nuestra calculadora de ahorros en: <a href="/main/pricing/es-mx" target="_blank">http://www.eventbee.com/main/pricing</a> 
				para ver cuánto vas a ahorrar cambiándote a Eventbee.</div>
					</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">8. Somos una organización sin ánimo de lucro, ¿tienen un precio especial?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						¡Sí! Ofrecemos un descuento del 25% con nuestro servicio para venta de tiquetes Avanzado y Pro para los eventos de organizaciones sin fines de lucro. Para obtener más detalles, visita  
						<a  target="_blank">http://www.eventbee.com/main/good</a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">9. ¿Puedo ver alguna página de un evento como ejemplo?</div>
				</div>
				<br>
				<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Tenemos diferentes tipos de eventos para mostrarte. Aquí están algunos:</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Evento </b> Simple: <a
							href="http://www.eventbee.com/event?eid=860191324"
							target="_blank">Haga clic aquí</a> para ver un evento de venta tiquetes de un espectáculo que es presenta en diferentes fechas.
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Evento</b> Periódico:  <a
							href="http://www.eventbee.com/event?eid=810793324"
							target="_blank">Haga clic aquí  </a>  para ver un evento de venta tiquetes de un espectáculo que es presenta en diferentes fechas.
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Evento de Asiento</b> Reservados:  <a
							href="http://www.eventbee.com/event?eid=850399221"
							target="_blank"> Haga clic aquí  </a>  para ver el sitio digitalizado y vender tiquetes utilizando características de asientos reservados.
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">10. No estoy en USA, ¿puedo utilizar Eventbee?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">¡Sí! Nuestra plataforma te permite trabajar con 130 tipos de monedas internacionales. Igualmente te permitimos personalizar cada palabra en el registro y así puedas realizar el evento en tu idioma local.</div>
				</div>
				<br>
				<!-- <div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Ejemplo evento en el  <b>Reino Unido</b>: <a
							href="http://www.eventbee.com/event?eid=880294224"
							target="_blank">Windrush Aquathlon</a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Ejemplo de evento realizado en <b>frances</b>: <a
							href="http://www.eventbee.com/event?eid=890697221"
							target="_blank">Gala Phenicia 2011 - CCGQ </a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Ejemplo evento en <b>Hungría</b>: <a
							href="http://www.eventbee.com/event?eid=890396224"
							target="_blank">Culinary Walking Tour</a>
					</div>
				</div> -->
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">11. ¿Tengo que firmar un contrato con Eventbee para comenzar?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">No, nosotros valoramos tu flexibilidad, y estamos seguros de que una vez comiences a utilizar Eventbee, no vas a querer cambiarte. Preferimos no atar a nuestros clientes con contratos.</div>
						</div>
					<br>
					<div class="row">
						<div class="col-md-12 caption_header_blue_faq">12.Tengo más preguntas, ¿a quién contacto?</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							<a href="javascript:;" id="emailus">Haz clic aquí</a> para ponerte en contacto con nuestro centro de apoyo, contamos con soporte de servicio al cliente 24x7 a través de correo electrónico.
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							<b>¡Síguenos para promociones especiales y premios!</b>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							Facebook: <a href="http://facebook.com/eventbeeinc"
								target="_blank">http://facebook.com/eventbeeinc</a>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							Twitter: <a href="http://twitter.com/eventbee" target="_blank">http://twitter.com/eventbee</a>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							YouTube: <a href="http://www.youtube.com/user/eventbee/videos"
								target="_blank">http://www.youtube.com/user/eventbee/videos</a>
						</div>
					</div>

				</div>
			</div>
</body>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootswatch.js"></script>
<script>
$(function(){
$('#emailus').click(function() {
$('.modal-title').html('Contact Eventbee');
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",'/main/user/homepagesupportemail.jsp');
$('iframe#popup').css("height","440px"); 
});
$('#myModal').modal('show');
});	
$('#myModal').on('hide.bs.modal', function () {
$('iframe#popup').attr("src",'');
$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
});
});	
</script>
</html>