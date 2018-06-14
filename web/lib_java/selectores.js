// JavaScript Document
var urlImagen = '<img id="cargador" src="imagenes/ajax-loader.gif" width="16" height="11">';	

function cargarDatosActivos() {

$('select option').remove(); 
$('select').append('<option value="" selected="selected"> Seleccione ...&nbsp;&nbsp;&nbsp; </option>');


	$.get('respuestasXML.do', {callMetodo: '_callMostrarActivos'},  function(data) {
	//elementoDestino = $('select[id|="selectActivo"]') ;
		$(data).find('activo').each(function(index, elemento){
		var html = ""; 
		var entrada = $(elemento); 
		var valorCodigo = entrada.find('codigo').text(); 
		var valorNombre = entrada.find('nombre').text(); 
		html += '<option value="' + valorCodigo + '">' ; 
		html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
		
		$(html).appendTo($('select#selectActivo'));
		
		});
		  
	}, 'xml');

}

function cargarDatosCategorias( valorSeleccionado ) {

$('select#selectCategoria option').remove();
$('select#selectCategoria').append('<option value="" selected="selected"> Seleccione Categoria ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectSubCategoria option').remove();
$('select#selectSubCategoria').append('<option value="" selected="selected">Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectCargo option').remove();
$('select#selectCargo').append('<option value="" selected="selected">Seleccione Cargo ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectPersona option').remove();
$('select#selectPersona').append('<option value="" selected="selected">Seleccione Seleccione Responsable ...&nbsp;&nbsp;&nbsp;</option>');

	$.ajax({
	url: 'respuestasXML.do',
	type: 'GET',
	data: {callMetodo: '_callMostrarCategorias',  codActivo: valorSeleccionado},
	cache: false,
	beforeSend: function() {
	$('select#selectCategoria').hide();
	$('label#lblSelectCategoria').append(urlImagen);
	$('select#selectSubCategoria').hide();
	$('label#lblSelectSubCategoria').append(urlImagen);
	
	$('select#selectCargo').hide();
	$('label#lblSelectCargo').append(urlImagen);
	
	},
	complete: function() {										
	$('label#lblSelectCategoria img').remove();
	$('label#lblSelectSubCategoria img').remove();
	$('label#lblSelectCargo img').remove();
	$('select#selectCategoria').fadeIn('slow');
	$('select#selectSubCategoria').fadeIn('slow');
	$('select#selectCargo').fadeIn('slow');
	}, 
	success: function(data){

			$(data).find('categoria').each(function(index, elemento){
			var html = "";
			var entrada = $(elemento);
			var valorCodigo = entrada.find('codigo').text();
			var valorNombre = entrada.find('nombre').text();
			html += '<option value="' + valorCodigo + '">' ;
			html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';

			$(html).appendTo($('select[id|="selectCategoria"]'));

			});

		}, 
	dataType: 'xml'
	});
	
}

function cargarDatosSubCategorias(valorSeleccionado) {
$('select#selectSubCategoria option').remove();
$('select#selectSubCategoria').append('<option value="" selected="selected"> Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectCargo option').remove();
$('select#selectCargo').append('<option value="" selected="selected">Seleccione Cargo ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectPersona option').remove();
$('select#selectPersona').append('<option value="" selected="selected">Seleccione Seleccione Responsable ...&nbsp;&nbsp;&nbsp;</option>');

	$.ajax({
	url: 'respuestasXML.do',
	type: 'GET',
	data: {callMetodo: '_callMostrarSubCategorias',  codCategoria: valorSeleccionado},
	cache: false,
	global: false, 
	beforeSend: function() {
	$('select#selectSubCategoria').hide();
	$('label#lblSelectSubCategoria').append(urlImagen);
	
	$('select#selectCargo').hide();
	$('label#lblSelectCargo').append(urlImagen);
	
	},
	complete: function() {
	$('label#lblSelectSubCategoria img').remove();
	$('select#selectSubCategoria').fadeIn('slow');
	
	$('label#lblSelectCargo img').remove();
	$('select#selectCargo').fadeIn('slow');
	},
	success: function(dataSubCategorias){                                        
			$(dataSubCategorias).find('subCategoria').each(function(index, elemento){
			var html = "";
			var entrada = $(elemento);
			var valorCodigo = entrada.find('codigo').text();
			var valorNombre = entrada.find('nombre').text();
			html += '<option value="' + valorCodigo + '">' ;
			html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
								  
			$(html).appendTo($('select#selectSubCategoria'));

			});

	},
	dataType: 'xml'
	});
	
}


function mostrarPrioridades() {
$('select#selectPrioridad option').remove(); 
$('select#selectPrioridad').append('<option value="" selected="selected"> Seleccione Prioridad ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectPersona option').remove();
$('select#selectPersona').append('<option value="" selected="selected">Seleccione Seleccione Responsable ...&nbsp;&nbsp;&nbsp;</option>');

	$.ajax( {
	url: 'respuestasXML.do',
	type: 'GET', 
	data: {callMetodo: '_callMostrarPrioridades'},
	cache: false,
	global: false, 
	beforeSend: function() {
				$('select#selectPrioridad').hide();
				$('label#lblSelectPrioridad').append(urlImagen);
				}, 
	complete: function() {
				$('label#lblSelectPrioridad img').remove(); 
				$('select#selectPrioridad').fadeIn('slow'); 
				},
	success: function(dataPrioridades){
	
				$(dataPrioridades).find('prioridad').each(function(index, elemento){
				var html = "";
				var entrada = $(elemento);
				var valorCodigo = entrada.find('codigo').text();
				var valorNombre = entrada.find('nombre').text();
				html += '<option value="' + valorCodigo + '">' ;
				html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
									  
				$(html).appendTo($('select#selectPrioridad'));
	
				});

			},
	dataType: 'xml' 
	}); 
	
	
}


function mostrarCargosSubCategorias(valorSeleccionado) {

$('select#selectCargo option').remove();
$('select#selectCargo').append('<option value="" selected="selected">Seleccione Cargo ...&nbsp;&nbsp;&nbsp; </option>');

$('select#selectPersona option').remove();
$('select#selectPersona').append('<option value="" selected="selected">Seleccione Seleccione Responsable ...&nbsp;&nbsp;&nbsp;</option>');

	$.ajax({
	url: 'respuestasXML.do',
	type: 'GET', 
	data: {callMetodo: '_callMostrarCargosSubCategoria',  codSubCategoria: valorSeleccionado},
	beforeSend: function() {
				$('select#selectCargo').hide();
				$('label#lblSelectCargo').append(urlImagen);
				}, 
	complete: function() {
			  $('label#lblSelectCargo img').remove(); 
			  $('select#selectCargo').fadeIn('slow');
			  }, 
	success: function(dataCargos) {
				$(dataCargos).find('cargo').each(function(index, elemento){
				var html = "";
				var entrada = $(elemento);
				var valorCodigo = entrada.find('codigo').text();
				var valorNombre = entrada.find('nombre').text();
				html += '<option value="' + valorCodigo + '">' ;
				html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
									  
				$(html).appendTo($('select#selectCargo'));
	
				});
			 }, 
	dataType: 'xml'
	}); 
	
	
}


function mostrarPersonasCargo(valorSeleccionado) {

$('select#selectPersona option').remove();
$('select#selectPersona').append('<option value="" selected="selected">Seleccione Seleccione Responsable ...&nbsp;&nbsp;&nbsp;</option>');


	$.ajax({
	url: 'respuestasXML.do',
	type: 'GET', 
	data: {callMetodo: '_callMostrarResponsablesCargo',  codCargo: valorSeleccionado},
	beforeSend: function() {
				$('select#selectPersona').hide();
				$('label#lblSelectPersona').append(urlImagen);
				}, 
	complete: function() {
			  $('label#lblSelectPersona img').remove(); 
			  $('select#selectPersona').fadeIn('slow');
			  }, 
	success: function(dataCargos) {
				$(dataCargos).find('trabajador').each(function(index, elemento){
				var html = "";
				var entrada = $(elemento);
				var valorCodigo = entrada.find('usuario').text();
				var valorNombre = entrada.find('nombre').text();
				html += '<option value="' + valorCodigo + '">' ;
				html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
									  
				$(html).appendTo($('select#selectPersona'));
				
				});
			 }, 
	dataType: 'xml'
	}); 
	
	
}