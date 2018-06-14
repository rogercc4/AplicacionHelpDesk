// JavaScript Document
function agregarTareas() {

$('tr#mensajeError').hide(); 

	$.ajax({
	url: "controlTrabajador.do?opcion=_registrarTarea", 
	type: "POST", 
	data: $('#frmAgregarTarea').serialize() , 
	global: false, 
	success: function(data) {			
	
			 $(data).find('respuestaOperacion').each(function(index, elemento){
			 var resultado = $(elemento).find('resultado').text(); 
			 var mensaje = $(elemento).find('mensaje').text(); 
				
				if ( resultado == "true" ) {
				$('div#dialoGestionAddTarea').dialog("close");
				cargarTareas(); 
				}
				else {
				$('tr#mensajeError > td').html(""); 
				$('tr#mensajeError > td').html(mensaje); 
				$('tr#mensajeError').fadeIn('slow'); 				
				}

			 }); 

			
			}, 
	dataType: 'xml' 
	}); 
	
}

function eliminarTarea() {

	$.ajax({
	url: "controlTrabajador.do", 
	type: "POST", 
	data: {opcion: '_eliminarTarea', codTarea: $(':radio[name|="rbtnTarea"]:checked').val()} , 
	global: false, 
	success: function(data) {
	
			 $(data).find('respuestaOperacion').each(function(index, elemento){
			 var resultado = $(elemento).find('resultado').text(); 
			 var mensaje = $(elemento).find('mensaje').text(); 
				
				if ( resultado == "true" ) {
				$('div#dialoGestionTareas').dialog("close");
				cargarTareas(); 
				}
				else {
				$('div#dialoGestionTareas > p').html(""); 
				$('div#dialoGestionTareas > p').html(mensaje); 
				$('div#dialoGestionTareas').dialog({buttons: [
						{
							text: "OK", 
							click: function() {
									$(this).dialog("close"); 
									}
						}
				]});
				
				}

			 }); 

			
			}, 
	dataType: 'xml' 
	}); 
	
}

function editarTarea() {
$('tr#mensajeError').hide(); 
//alert($('input#txtDescripcion').val()); 
//alert($('#frmAgregarTarea').serialize()); 
/*
	$.ajax({
	url: "controlTrabajador.do", 
	type: "POST", 
	data: {opcion: '_editarTarea', hdCodTarea: $('input#hdCodTarea').val(), 
		   txtDescripcion: $('input#txtDescripcion').val(), txtFechaInicio: $('input#txtFechaInicio').val(), 
		   txtFechaFin: $('input#txtFechaFin').val()}, 
	global: false, 
	success: function(data) {			
	
			 $(data).find('respuestaOperacion').each(function(index, elemento){
			 var resultado = $(elemento).find('resultado').text(); 
			 var mensaje = $(elemento).find('mensaje').text(); 
				
				if ( resultado == "true" ) {
				$('div#dialoGestionAddTarea').dialog("close");
				cargarTareas(); 
				}
				else {
				$('tr#mensajeError > td').html(""); 
				$('tr#mensajeError > td').html(mensaje); 
				$('tr#mensajeError').fadeIn('slow'); 				
				}

			 }); 

			
			}, 
	dataType: 'xml' 
	}); 
*/

	$.ajax({
	url: "controlTrabajador.do?opcion=_editarTarea", 
	type: "POST", 
	data: $('#frmAgregarTarea').serialize() , 
	global: false, 
	success: function(data) {			
	
			 $(data).find('respuestaOperacion').each(function(index, elemento){
			 var resultado = $(elemento).find('resultado').text(); 
			 var mensaje = $(elemento).find('mensaje').text(); 
				
				if ( resultado == "true" ) {
				$('div#dialoGestionAddTarea').dialog("close");
				cargarTareas(); 
				}
				else {
				$('tr#mensajeError > td').html(""); 
				$('tr#mensajeError > td').html(mensaje); 
				$('tr#mensajeError').fadeIn('slow'); 				
				}

			 }); 

			
			}, 
	dataType: 'xml' 
	}); 

}

function mostrarDialogBarraProgreso() {	
	
	var htmlBarraProgreso = '<table width="100" ><tr>'; 
	htmlBarraProgreso += '<td style="vertical-align:middle; text-align:center">'; 
	htmlBarraProgreso += '<img src="imagenes/progressBar.gif">'; 
	htmlBarraProgreso += '</td></tr></table>';
	
	$('div#dialoGestionTareas > p').html(""); 
	$('div#dialoGestionTareas > p').html(htmlBarraProgreso);
	$('div#dialoGestionTareas').dialog({buttons: null}); 
	$('div#dialoGestionTareas').dialog({title: 'Cargando Informacion ...'}); 
	$('div#dialoGestionTareas').dialog("open"); 	

}

function mostrarMensajeDialogo(titulo, mensaje) {
	
		$('div#dialoGestionTareas > p').html( mensaje ); 
		
		$('div#dialoGestionTareas').dialog({buttons: [
				{
					text: "OK", 
					click: function() {
							$(this).dialog("close"); 
							}
				}
		]});
		
		$('div#dialoGestionTareas').dialog({title: titulo}); 
		
		$('div#dialoGestionTareas').dialog("open");
}


$(document).ready(function(){
						   
	$('button').button();
	
	$('#tabsGestionTareas').tabs();
						   
	$('div#dialoGestionTareas').dialog({
	autoOpen: false,  
	height: 170, 
	modal: true, 
	resizable: false
	}); 
	
	$('div#dialoGestionAddTarea').dialog({
	autoOpen: false,  
	height: 250, 
	width: 450, 
	modal: true, 
	resizable: false
	}); 
	
	$('div#dlgMostrarDetalleTarea').dialog({
	autoOpen: false, 
	modal: true, 
	height: 350,
	width: 500, 
	resizable: false
	});
	
	$('div#dialoGestionAddTarea').dialog({
			
			beforeClose: function(event, ui) {
						 $('input#txtDescripcion').attr("value", "" ); 
						 $('input#txtFechaInicio').attr("value", "" ); 
						 $('input#txtFechaFin').attr("value", "" ); 
						 }
						 
	});
	
	$('button#agregarTarea').click(function(){
	
	$('tr#mensajeError').hide();
	
	var tabSelect = $('#tabsGestionTareas').tabs().tabs('option', 'selected'); 
	
		if ( tabSelect == 0 ) {
		mostrarMensajeDialogo("Mensaje", "<strong>Seleccione la ficha Tareas Registradas</strong>");
		}
		else {

		$('div#dialoGestionAddTarea').dialog({buttons: [
				{
					text: "Agregar", 
					click: function() {
							agregarTareas(); 	
							}
				}, 
				{
					text: "Cancelar", 
					click: function() {
							$(this).dialog("close"); 
							}
				}
		]}); 
		
		$('div#dialoGestionAddTarea').dialog({title: 'Agregar Tarea'}); 
		
		$('div#dialoGestionAddTarea').dialog("open"); 

		}
	
	});  
	
	$('button#btnEliminar').click(function() {		
	var numSelect = $(':radio[name|="rbtnTarea"]:checked').length; 	
	
		if ( numSelect == 0 ) {
		
		$('div#dialoGestionTareas > p').html("Selecccione una tarea"); 
		
		$('div#dialoGestionTareas').dialog({buttons: [
				{
					text: "OK", 
					click: function() {
							$(this).dialog("close"); 
							}
				}
		]});
		
		$('div#dialoGestionTareas').dialog({title: 'Mensaje'}); 
		
		$('div#dialoGestionTareas').dialog("open"); 		
		
		}
		else {
			
		$('div#dialoGestionTareas > p').html("Desea eliminar la tarea seleccionada ..?");
		$('div#dialoGestionTareas').dialog({buttons: [
				{
					text: "Eliminar", 
					click: function() {
							eliminarTarea(); 
							}
				}, 
				{
					text: "Cancelar", 
					click: function() {
							$(this).dialog("close"); 
							}
				}
		]});
		
		$('div#dialoGestionTareas').dialog({title: 'Mensaje'}); 
		$('div#dialoGestionTareas').dialog("open"); 
		
		}
	
	});
	
	$('button#btnMostrarDetalles').click(function() { 
	var numSelect = $(':radio[name|="rbtnTarea"]:checked').length; 	
	
		if ( numSelect == 0 ) {
			
		mostrarMensajeDialogo("Mensaje", "<strong>Seleccione una tarea</strong>");
		
		}
		else {
			
			$.ajax({
			url: "respuestasXML.do", 
			data: {callMetodo: '_callMostrarDetalleTarea', codTarea: $(':radio[name|="rbtnTarea"]:checked').val()}, 
			type: 'GET', 
			global: false, 
			cache: false, 
			success: function(data) {	
			
						$(data).find('tarea').each(function(index, elemento){ 
															
						$('label#lblCodigoTarea').html( $(elemento).find('codigo').text() ); 
						$('label#lblDescripcionTarea').html( $(elemento).find('descripcion').text() ); 
						$('label#lblFechaInicio').html( $(elemento).find('fechaInicio').text() ); 
						$('label#lblFechaFin').html( $(elemento).find('fechaFin').text() ); 
						$('label#lblRegistradoPor').html( $(elemento).find('nombreTrabajador').text() ); 
						$('label#lblCargo').html( $(elemento).find('cargo').text() ); 
						$('label#lblArea').html( $(elemento).find('area').text() ); 						
						
						}); 
						
						$('div#dlgMostrarDetalleTarea').dialog({buttons: [
								{
									text: "OK", 
									click: function() { $(this).dialog("close"); }
								}
						]}); 
						
						$('div#dialoGestionTareas').dialog("close"); 
						
						$('div#dlgMostrarDetalleTarea').dialog("open"); 
						
					 }, 
			beforeSend: function() { mostrarDialogBarraProgreso(); }, 
			dataType: 'xml'
			});
			
		}
	
	});
	
	$('button#btnEditar').click( function() {
	var numSelect = $(':radio[name|="rbtnTarea"]:checked').length; 	
	
		if ( numSelect == 0 ) {
		mostrarMensajeDialogo("Mensaje", "<strong>Seleccione una tarea</strong>");		
		}
		else {
		
			$.ajax({
			url: "respuestasXML.do", 
			data: {callMetodo: '_callMostrarDetalleTarea', codTarea: $(':radio[name|="rbtnTarea"]:checked').val()}, 
			type: 'GET', 
			global: false, 
			cache: false, 
			success: function(data) {	
			
						$(data).find('tarea').each(function(index, elemento){ 
															
						$('input#hdCodTarea').attr("value", $(elemento).find('codigo').text() ); 
						$('input#txtDescripcion').attr("value", $(elemento).find('descripcion').text() ); 
						$('input#txtFechaInicio').attr("value", $(elemento).find('fechaInicio').text() ); 
						$('input#txtFechaFin').attr("value", $(elemento).find('fechaFin').text() ); 		
						
						}); 
						
						$('div#dialoGestionAddTarea').dialog({buttons: [
								{
									text: "Editar", 
									click: function() { 
											editarTarea(); 
											}
								}, 
								{
									text: "Cancelar", 
									click: function() { $(this).dialog("close"); }
								}
						]}); 
						
						$('div#dialoGestionAddTarea').dialog({title: 'Editar Tarea'}); 
						
						$('div#dialoGestionTareas').dialog("close"); 
						
						$('tr#mensajeError').hide(); 
						
						$('div#dialoGestionAddTarea').dialog("open"); 
						
					 }, 
			beforeSend: function() { mostrarDialogBarraProgreso(); }, 
			dataType: 'xml'
			});
		
		}
	
	}); 

						   
}); 