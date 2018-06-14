// JavaScript Document
function enviarMensaje() {	

$('tr#mensajeError').hide(); 

	$('form#frmSendMensaje').ajaxSubmit({
	url: "controlTrabajador.do?opcion=_registrarMensaje", 
	type: "POST", 
	beforeSubmit: function() {
				  $('div#dialoGestionSendMessage').dialog("close"); 
				  mostrarDialogBarraProgreso( "Enviando Mensaje ..." ); 
				  }, 
	success: function(data) {
			 $('div#dialogRegresar').dialog("close"); 
			 
				 $(data).find('respuestaOperacion').each(function(index, elemento){
				 var resultado = $(elemento).find('resultado').text(); 
				 var mensaje = $(elemento).find('mensaje').text(); 
				
					if ( resultado == "true" ) {
					cargarMensajes(); 
					}
					else {
					$('div#dialoGestionSendMessage').dialog("open"); 
					$('tr#mensajeError > td').html(""); 
					$('tr#mensajeError > td').html(mensaje); 
					$('tr#mensajeError').fadeIn('slow');
					}
	
				 });

			 }, 
	dataType: 'xml'
	}); 
	
}

function mostrarDialogBarraProgreso( titulo ) {	
	
	var htmlBarraProgreso = '<table width="100" ><tr>'; 
	htmlBarraProgreso += '<td style="vertical-align:middle; text-align:center">'; 
	htmlBarraProgreso += '<img src="imagenes/progressBar.gif">'; 
	htmlBarraProgreso += '</td></tr></table>';
	
	$('div#dialogRegresar > p').html(""); 
	$('div#dialogRegresar > p').html(htmlBarraProgreso);
	$('div#dialogRegresar').dialog({buttons: null}); 
	$('div#dialogRegresar').dialog({title: titulo}); 
	$('div#dialogRegresar').dialog("open");

}

function mostrarMensajeDialogo(titulo, mensaje) {
	
		$('div#dialogRegresar > p').html( mensaje ); 
		
		$('div#dialogRegresar').dialog({buttons: [
				{
					text: "OK", 
					click: function() {
							$(this).dialog("close"); 
							}
				}
		]});
		
		$('div#dialogRegresar').dialog({title: titulo}); 
		
		$('div#dialogRegresar').dialog("open");
}


$(document).ready( function() {
	
	$('button').button();
	$('div#tabsGestionMensajes').tabs();
	
	$('div#dialogRegresar').dialog({
	autoOpen: false, 
	resizable: false, 
	width: 300, 
	height: 200, 
	modal: true 
	});
	
	$('div#dialoGestionSendMessage').dialog({
	autoOpen: false, 
	width: 500, 
	height: 350, 
	modal: true, 
	resizable: false 
	});
	
	$('div#dialogDetailMensaje').dialog({
	autoOpen: false, 
	width: 600, 
	height: 400, 
	modal: true, 
	resizable: false 
	});
	
	$('div#listaMensajes').click(function(event) {
	var rbtSelect = $(':radio[name|="rbtnMensaje"]'); 
	
		if ( $(event.target).is(rbtSelect) ) {
		
		var idFilaSelect = "tr" +  jQuery.trim($(event.target).val()); 
			
			$('table#tableMensajes tbody tr').each( function(index, elemento) {
			
				if ( jQuery.trim($(elemento).attr('id')) == idFilaSelect ) {
				$(elemento).addClass("ui-state-focus"); 
				}
				else {
				$(elemento).removeClass(); 
				}
				
			});
		
		}			
	
	});
	
	$('button#btnEnviarMensaje').click(function() { 
												
	var tabSelect = $('#tabsGestionMensajes').tabs().tabs('option', 'selected'); 
	
		if ( tabSelect == 0 ) {
		mostrarMensajeDialogo("Mensaje", "<strong>Seleccione la ficha Mensajes</strong>");
		}
		else {
			
		$('input#txtAsunto').attr("value", ""); 		
		$('input#txtDescripcion').attr("value", "");
		$('input#txtArchivo').attr("value", "");
		$('input#fileAdjunto').attr("value", "");
		
		$('tr#mensajeError').hide();	
		
			$('div#dialoGestionSendMessage').dialog({buttons: [
					{
						text: "Enviar", 
						click: function() {
								//$('form#frmSendMensaje').submit();
								enviarMensaje();
							   }
					}, 
					{
						text: "Cancelar", 
						click: function() {
								$(this).dialog("close"); 
							   }
					}
			]}); 
			
			$('div#dialoGestionSendMessage').dialog("open"); 
		
		}										
	
	});
	
	
	$('button#btnMostrarDetalle').click( function() {	

	var tabSelect = $('#tabsGestionMensajes').tabs().tabs('option', 'selected'); 
	
		if ( tabSelect == 0 ) {
		mostrarMensajeDialogo("Mensaje", "<strong>Seleccione la ficha Mensajes</strong>");
		}
		else {

		var numSelect = $(':radio[name|="rbtnMensaje"]:checked').length;	
		
			if ( numSelect <= 0 ) {
			mostrarMensajeDialogo("Mensaje", "Usted debe seleccionar un mensaje ..."); 	
			}
			else {
	
				$.ajax({
					url: "respuestasXML.do", 
					type: "GET", 
					cache: false, 
					global: false, 
					data: {callMetodo: '_callMostrarDetalleMensaje', 
						   codMensaje: $(':radio[name|="rbtnMensaje"]:checked').val() }, 
					beforeSend: function () {
								mostrarDialogBarraProgreso( "Cargando Informacion ..." ); 
								}, 
					success: function ( data ) {	
							 
							 $('label#lblDetailAsunto').empty(); 
							 $('label#lblDetailDescripcion').empty(); 
							 $('label#lblDetailFecha').empty(); 
							 $('label#lblDetailOrigen').empty();
							 $('label#lblDetailDestino').empty();
							 $('label#lblDetailArchivoAdjunto').empty();
							 
							 $(data).find('mensaje').each( function(index, elemento) {	
																	
								$('label#lblDetailAsunto').html( $(elemento).find('asunto').text() ); 
								$('label#lblDetailDescripcion').html( $(elemento).find('descripcion').text() ); 
								$('label#lblDetailFecha').html( $(elemento).find('fechaRegistro').text() ); 
								$('label#lblDetailOrigen').html( $(elemento).find('trabajadorOrigen').text() ); 
								$('label#lblDetailDestino').html( $(elemento).find('trabajadorDestino').text() ); 
								
									if ( jQuery.trim($(elemento).find('nombreAdjunto').text()) != ""  ) { 
									
									var linkHtml =  '<img src="imagenes/download.png"> &nbsp;&nbsp;&nbsp; ';
										linkHtml += '<a href="controlMensaje.do?opcion=_visualizarArchivo&' ; 
										linkHtml += ( 'codMensaje=' + $(elemento).find('codigo').text() + '"' ) ; 
										linkHtml += 'target="_blank">'; 
										linkHtml += $(elemento).find('nombreAdjunto').text(); 
										linkHtml += "</a>"; 
										
									$('label#lblDetailArchivoAdjunto').html( linkHtml ); 
									
									}
									else {
									$('label#lblDetailArchivoAdjunto').html( "No hay archivo adjunto" );
									}
									
							 }); 
							
							 $('div#dialogRegresar').dialog("close"); 	
							 
							 $('div#dialogDetailMensaje').dialog({buttons: [
										{
											text: "OK", 
											click: function() { $(this).dialog("close"); }
										}
							 ]});
							 
							 $('div#dialogDetailMensaje').dialog("open"); 	
							 
							 }, 
					dataType: "xml"
				});
				
			}

		}
		
	});

});