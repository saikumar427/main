<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
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
.box > .icon {
    text-align: center;
    position: relative;
}
.box > .icon > .image {
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
.box > .icon:hover > .image {
    border: 4px solid #428bd3;
}
.box > .icon > .image > i {
    font-size: 40px !important;
    color: #fff !important;
}
.box > .icon:hover > .image > i {
    color: white !important;
}
.box > .icon > .info {
    margin-top: -24px;
    background: rgba(0, 0, 0, 0.04);
    border: 1px solid #e0e0e0;
    padding: 15px 0 10px 0;
}
.box > .icon > .info > h3.title {
    color: #428bd3;
	font-size:32px;
    font-weight: 500;
}
.box > .icon > .info > p {
	color: #666;
	line-height: 1.5em;
	margin: 20px;
}
.box > .icon:hover > .info > h3.title, .box > .icon:hover > .info > p, .box > .icon:hover > .info > .more > a {
    color: #428bd3;
}
.box > .icon > .info > .more a {
    color: #222;
    line-height: 12px;
    text-transform: uppercase;
    text-decoration: none;
}
.box > .icon:hover > .info > .more > a {
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
    border-color: rgba(255,255,255,0) #d9534f rgba(255,255,255,0) rgba(255,255,255,0);
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
    color:#5CB85C;
}
.height {
    min-height: 200px;
}
.icon {
    font-size: 47px;
    color: #5CB85C;
	cursor:pointer;
}
.iconbig {
    font-size: 77px;
    color: #5CB85C;
}
.table > tbody > tr > .emptyrow {
    border-top: none;
}
.table > thead > tr > .emptyrow {
    border-bottom: none;
}
.table > tbody > tr > .highrow {
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
    color:#5CB85C;
}

.ratetext {
    font-size: 37px;
    text-decoration: underline;
    padding-bottom: 10px;
}

.votes {
    font-size: 47px;
    padding-right: 20px;
    color:#197BB5;
}
a.list-group-item {
    height: auto;
    min-height: 250px;
}

a.list-group-item:hover, a.list-group-item:focus {
    border-left:10px solid #5CB85C;
    border-right:10px solid #5CB85C;
}
a.list-group-item:hover, a.list-group-item:focus {
    background-color:#edf5fc !important;
}

a.list-group-item {
    border-left:10px solid transparent;
    border-right:10px solid transparent;
}
#create{
	cursor:pointer;
}
#promote{
	cursor:pointer;
}
#manage{
	cursor:pointer;
}
.blogicon2{
	font-size:128px;
}
.blogicon3{
	font-size:60px;
}
	.btn-lg {
		font-size: 38px;
		line-height: 1.33;
		border-radius: 6px;
	}
	.box > .icon {
		text-align: center;
		position: relative;
	}
	.box > .icon > .image {
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
		color:#5CB85C;
	}
	.icon {
		font-size: 47px;
		color: #5CB85C;
	}
	.iconbig {
		font-size: 77px;
		color: #5CB85C;
	}  
	 .caption_header_blue{
		font-size:32px;
		color: #428BCA;
	}
	p{
		line-height:24px;
	}
	.pricing_desc_grey{
color: #999999;
font-size: 20px;
}

	
	</style>
	<body>
		<div class="container" style="width:100% !important;padding-left:0 !important;background-color:#fff !important">
			<div class="row" style="padding-top:50px !important;padding-bottom:25px !important;">
				<div class="col-xs-12">
					<div class="panel-heading" style="border:none !important;color: #58585a;background-color:#fff !important; text-align:center;">
						<span style="color: #999999;font-size: 42px;">COMO FUNCIONA</span>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="width:100% !important;padding-left:0 !important;background-color:#fff !important">
			<div class="container">				
			<div class="row" style="margin: 0px 0px 0px 0px!important;padding-bottom:50px !important;">
				<div class="col-xs-12" style="padding-left:0px !important;padding-right:0px !important;">					
					<div class="row">
						<div class="col-xs-12 col-md-4 col-lg-4 pull-left">
							<div class="panel panel-default height" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;" >
								<div class="" align="center" style="padding:0px !important;">
									<div id="createImg"> <img id="create" onclick="showContent('create');" src="/main/bootstrap/images/createrevised100.png" height="223px" width="257px" alt="Create"> </div>
								</div>
								<div class="panel-heading" style="border:none !important;color: #58585a;background-color:#fff !important; text-align:center;"><span style="color:#999999;    font-size: 20px;">1. CREA</span></div>
							</div>
						</div>
						<div class="col-xs-12 col-md-4 col-lg-4">
							<div class="panel panel-default height" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
								<div class="" align="center" style="padding:0px !important;">
									<div id="promoteImg"> <img id="promote" onclick="showContent('promote');" src="/main/bootstrap/images/promoterevised70.png" alt="Promote" height="223px" width="257px"> </div>
								</div>
								<div class="panel-heading" style="background-color:#fff !important; border:none !important;text-align:center;font-size:32px;color: #58585a;"><span style="color:#999999;    font-size: 20px;">2. PROMUEVE</span></div>
							</div>
						</div>
						<div class="col-xs-12 col-md-4 col-lg-4">
							<div class="panel panel-default height" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;" >
								<div class="" align="center" style="padding:0px !important;">
									<div id="manageImg"> <img id="manage" onclick="showContent('manage');" src="/main/bootstrap/images/geariconrevised70.png" alt="manage" height="223px" width="257px"> </div>
								</div>
								<div class="panel-heading" style="background-color:#fff !important; border:none !important;text-align:center;font-size:32px;color: #58585a;"><span style="color:#999999;    font-size: 20px;">3. ADMINISTRA</span></div>
							</div>
						</div>
						
					   
					</div>
				</div>
			</div>
			</div>
			<div class="container" style="width:100%;padding-left:0px !important;padding-right:0px !important;">	
			<div id="createContentMain" class="row" style=" margin-bottom: 0 !important;">
				<div class="col-md-12" style="padding-left:0px !important;padding-right:0px !important;">
					<div class="panel panel-default " id="createContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;" >
							<div class="col-md-3 col-md-offset-1" align="left"  style="padding-bottom: 10px; padding-top: 10px;">
								<div align="center"> <img src="/main/bootstrap/images/ticketsgreytransparent.png" alt="Ticekts" width="150px" height="134px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important;" align="left">
								<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Crea tu página de eventos
</h4><br>
									<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Crea uno o múltiples tipos de tiquetes</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Crea tiquetes para eventos únicos o individuales</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Crea tiquetes para eventos recurrentes o periódicos</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Establece entradas generales o asientos reservados
										</span></li>												
									</ul>
						</div>
					</div>
					<div class="panel panel-default" id="promoteContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background:#fff;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1" style="color:#999999 !important;" align="left">
								<div align="center"> <img src="/main/bootstrap/images/brushgreytransparent.png" alt="Brush" width="150px" height="155px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" align="left" style="">								
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Personaliza tu evento
</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Crea formatos de registro dinámicos</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Diseña la apariencia y estilo de tu marca</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Personaliza tus correos de confirmación</span></li>												
									</ul>
								
							</div>
						</div>
					</div>
					<div class="panel panel-default" id="manageContent" style=" margin-bottom: 0 !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1" align="left"  style=" padding-bottom: 10px; padding-top: 34px;padding-left:0px !important;">
								<div align="center"> <img src="/main/bootstrap/images/creditcardgreytransparent.png" alt="Credit Card" width="150px" height="90px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important;" align="left" >
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Procesos de pago flexibles</h4><br>
							<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Elige entre Paypal, Stripe, Braintree, Authorize.net o Eventbee</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Acceso inmediato a fondos con PayPal, Stripe, Braintree o Authorize.net</span></li>								
										<li style="color:#999999;"><span class="pricing_desc_grey">Para un mejor proceso de pago, elige Eventbee</span></li>												
									</ul>
								
							</div>
						</div>
					</div>				
				</div>
				<div style="clear:left;"></div>
			</div>
			</div>
			<div class="container" style="width:100%;padding-left:0px !important;padding-right:0px !important;">	
			<div id="promoteContentMain" class="row" style="display:none;">
				<div class="col-md-12" style="padding-left:0px !important;padding-right:0px !important;">
					<div class="panel panel-default" id="promoteContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;" >
							<div class="col-md-3 col-md-offset-1"  style="" align="left">
								<div align="center" > <img src="/main/bootstrap/images/linksgreytransparent.png" alt="Links" width="150px" height="150px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important;" align="left">
								<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Haz pública la página de tu evento</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Publica el link del evento en tu página web o por correo</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Visualiza el widget de los tiquetes de tu evento en tu página web</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Difunde el link del evento en tus redes sociales</span></li>												
									
							</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-default" id="promoteContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background:#fff;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1"  style="color:#999999 !important;padding-bottom: 10px; padding-top: 14px;" align="left">
								<div align="center"> <img src="/main/bootstrap/images/socialsharinggreytransparent.png" alt="Social Sharing" width="150px" height="132px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" align="left" style="">								
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Comparte en tus redes sociales</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Habilita la opción de compartir para Facebook, Twitter y Google+
 </span></li>
										<li style="color:#999999;" ><span class="pricing_desc_grey">Permite los comentarios de Facebook en la página de tu evento</span></li>											
										<li style="color:#999999;"><span class="pricing_desc_grey">Recompensa a los asistentes que comparten tu evento en sus redes sociales</span></li>												
									</ul>
								
							</div>
						</div>
					</div>
					
					<div class="panel panel-default" id="manageContent" style=" margin-bottom: 0 !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1" align="left"  style="padding-bottom: 10px; padding-top: 10px;padding-left:0px !important;">
								<div align="center"> <img src="/main/bootstrap/images/facebookticketgreytransparent.png" alt="Facebook Ticket" width="150px" height="123px"> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important;" align="left" >
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Vende Tiquetes a través de tu página de seguidores en Facebook</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Habilita el botón &quot;Comprar Tiquetes&quot; en tu página de seguidores</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Permítele a tus fans comprar tiquetes a través de Facebook</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Personaliza cuál evento quieres que se visualice en tu página de seguidores</span></li>												
								
								</ul>
							</div>
						</div>
					</div>				
				</div>
				<div style="clear:left;"></div>
			</div>
			</div>
			<div class="container" style="width:100%;padding-left:0px !important;padding-right:0px !important;">	
			<div id="manageContentMain" class="row" style="display:none;">
				<div class="col-md-12" style="padding-left:0px !important;padding-right:0px !important;">
					<div class="panel panel-default" id="manageContent" style=" margin-bottom: 0 !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1" align="left"  style="padding-bottom: 10px; padding-top: 10px;padding-left:0px !important;">
								<div align="center"> <img src="/main/bootstrap/images/24hoursgrey.png" alt="24 Hours" width="150px" height="146px"/> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important;padding-top: 4px;" align="left" >
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Optimiza la gestión de tus eventos</h4><br>
									<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Agrega Co-Administradores a tus eventos y asígnales tareas</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Envía correos a tus asistentes sólo con un clic para informarles a cerca de actualizaciones</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Accede a tu cuenta 24/7 para realizar cualquier cambio</span></li>												
									</ul>
								
							</div>
						</div>
					</div>
					<div class="panel panel-default" id="promoteContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background:#fff;padding-top:50px !important;padding-bottom:50px !important;">
							<div class="col-md-3 col-md-offset-1"  style="color:#999999 !important;padding-bottom: 10px; padding-top: 10px;" align="left">
								<div align="center"> <img src="/main/bootstrap/images/reportsgrey.png" alt="Reports" width="150px" height="202px"/> </div>				
							</div>
							<div class="col-md-7  col-md-offset-1" align="left" style="padding-top: 38px;">
								<h4 class="list-group-item-heading  caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Maximiza tus reportes</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Recopila datos de tu asistentes</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Genera informes de ventas</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Exporta tus informes a Excel o PDF</span></li>												
									</ul>
								
							</div>
						</div>
					</div>
					<div class="panel panel-default" id="createContent" style="margin-bottom: 0px !important;border:none !important;box-shadow:none !important;">
						<div class="panel-body" style="background-color:#f1f5f7 !important;padding-top:50px !important;padding-bottom:50px !important;" >
							<div class="col-md-3 col-md-offset-1" align="left"  style="padding-bottom: 10px; padding-top: 10px;">
								<div align="center"> <img src="/main/bootstrap/images/qrcodegrey.png" alt="QR Code" width="150px" height="150px"/> </div>
							</div>
							<div class="col-md-7  col-md-offset-1" style="color:#999999 !important; padding-top: 8px;" align="left">
								<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Aplicaciones Eventbee</h4><br>
								<ul style="padding-left: 20px !important;">
										<li style="color:#999999;"><span class="pricing_desc_grey">Usa la aplicación móvil Eventbee para chequear asistentes</span></li>
										<li style="color:#999999;"><span class="pricing_desc_grey">Escanéa los códigos QR y códigos de barras para realizar un chequeo más rápido</span></li>												
										<li style="color:#999999;"><span class="pricing_desc_grey">Vende tiquetes y realiza pagos con la aplicación Eventbee</span></li>												
									</ul>
							
							</div>
						</div>
					</div>								
				</div>
				<div style="clear:left;"></div>
			</div>	
			</div>
		</div>
	</body>
<script>
var tabIndex=0;
var tabs = ["promote", "manage","create"];
var timer = setInterval(function() {
	showContent(tabs[tabIndex]);
	tabIndex++;
	if(tabIndex==3)
	tabIndex=0;
}, 60000) 
function showContent(id)
{
	if(id=="create")
	{
		$('#promoteImg img').attr('src','/main/bootstrap/images/promoterevised70.png');
		$('#createImg img').attr('src','/main/bootstrap/images/createrevised100.png');
		$('#manageImg img').attr('src','/main/bootstrap/images/geariconrevised70.png');
		$('#createContentMain').css('display','block');
		$('#promoteContentMain').css('display','none');
		$('#manageContentMain').css('display','none');
	}
	else if(id=="promote")
	{
		$('#promoteImg img').attr('src','/main/bootstrap/images/promoterevised100.png');
		$('#createImg img').attr('src','/main/bootstrap/images/createrevised70.png');
		$('#manageImg img').attr('src','/main/bootstrap/images/geariconrevised70.png');
		$('#createContentMain').css('display','none');
		$('#promoteContentMain').css('display','block');
		$('#manageContentMain').css('display','none');
	}
	else if(id=="manage")
	{
		$('#promoteImg img').attr('src','/main/bootstrap/images/promoterevised70.png');
		$('#createImg img').attr('src','/main/bootstrap/images/createrevised70.png');
		$('#manageImg img').attr('src','/main/bootstrap/images/geariconrevised100.png');
		$('#createContentMain').css('display','none');
		$('#promoteContentMain').css('display','none');
		$('#manageContentMain').css('display','block');
	}
}
</script>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootswatch.js"></script>

</html>