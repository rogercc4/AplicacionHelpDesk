<%-- 
    Document   : formAtenderSolicDerivada
    Created on : 24/08/2011, 06:26:01 PM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atender Solicitud derivadaJSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />			
		<link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">
		<script type="text/javascript" src="lib_java/funciones.js" ></script>
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>            
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
        <script type="text/javascript">
		$(document).ready( function() {
		var controlEntrada; 
		var datosCompletos; 
		var urlImagen = '<img id="cargador" src="imagenes/ajax-loader.gif" width="16" height="11">';
		$('#btnVolverClasificar').button(); 
		
		$('#dialogMensaje').dialog({
		autoOpen: false, 
		height: 170, 
		dialog: true, 
		resizable: false
		}); 
		
		ocultarSelects( true ); 		
		
		function cargarDatosActivos() {
		
			$.get('respuestasXML.do', {callMetodo: '_callMostrarActivos'},  function(data) {
				
				$(data).find('activo').each(function(index, elemento){
				var html = ""; 
				var entrada = $(elemento); 
				var valorCodigo = entrada.find('codigo').text(); 
				var valorNombre = entrada.find('nombre').text(); 
				html += '<option value="' + valorCodigo + '">' ; 
				html += valorNombre + '&nbsp;&nbsp;&nbsp;</option>';
				
				$(html).appendTo($('select[id|="selectActivo"]')); 
				
				});
				  
			}, 'xml');
		
		}
		
		function ocultarSelects( ocultar ) {
			
			if ( ocultar == true ) {
			$('#trSelectActivo').hide();
			$('#trSelectCategoria').hide(); 
			$('#trSelectSubCategoria').hide(); 
			}
			else {
			$('#trSelectActivo').show();
			$('#trSelectCategoria').show();
			$('#trSelectSubCategoria').show();
			}
			
			$('select').each( function(index, elemento)  {
				
				if ( ocultar == true ) {
				$(elemento).hide();				
				}
				else {
				$(elemento).show(); 
				}
			});
		
		$('select > option').remove();
			
			if ( ocultar == false ) {
			$('select').append('<option value="">Seleccione ... &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
			cargarDatosActivos();
			}
			else {
			var codSubCategoria = "${requestScope.miSolicitud.subCategoria.codigo}";
			$('#selectSubCategoria').append('<option value="' + codSubCategoria + '" selected="selected">' + 
									    "${requestScope.miSolicitud.subCategoria.nombre}" + 
										'</option>'); 
			}

		}

			$('#btnVolverClasificar').toggle(
					function(){
					ocultarSelects( false ); 					
					$('#btnVolverClasificar > span').html(""); 
					$('#btnVolverClasificar > span').html("Cancelar");
					},
					function(){
					ocultarSelects( true ); 
					$('#btnVolverClasificar > span').html(""); 
					$('#btnVolverClasificar > span').html("Volver a clasificar");					
					}
			); 
											
			$('#dialogMensaje').dialog({
				autoOpen: false, 
				height: 170, 
				resizable: false, 
				modal: true
			});
			
			
			$('#btnGuardar').click( function() {			
			var nombreControl; 
			var valorEntradaControl; 
			datosCompletos = true; 
				
				$(':input').each( function(index, elemento){
				controlEntrada = $(elemento); 
				nombreControl = jQuery.trim(controlEntrada.attr('name')); 
				valorEntradaControl = jQuery.trim(controlEntrada.val()); 				
					
					if ( nombreControl == "selectSubCategoria" || nombreControl == "hdPrioridad" || 
							nombreControl == "txtFecha" || nombreControl == "hdCodSolic" ) 
							
							if ( valorEntradaControl == "" ) {
							datosCompletos = false ; 
							return false ; 
							}
					
				});			
				
				if ( datosCompletos == false ) {
				$('#dialogMensaje > p').html(""); 
				$('#dialogMensaje > p').html("<strong>Falta completar: " + 
										     controlEntrada.attr('title') + 
											 "</strong>"); 
					
					$('#dialogMensaje').dialog({ buttons: [
							{
							text: "OK", 
							click: function() {
									$(this).dialog('close'); 
									}
							}
					]});
				
				$('#dialogMensaje').dialog('open'); 
				
				}
				else {
				$('#dialogMensaje > p').html(""); 
				$('#dialogMensaje > p').html("<strong>Desea atender esta solicitud ..?</strong>"); 
					
					$('#dialogMensaje').dialog({ buttons: [
							{
								text: "Atender", 
								click: function() {
										$('#formAtenderSolicDerivada').submit(); 
										}
							}, 
							{
								text: "Cancelar", 
								click: function() {
										$(this).dialog('close'); 
										}
							}
					]}); 
				
				$('#dialogMensaje').dialog('open'); 
				
				}
			
			
			}); 
			
			$('#btnCancelar').click( function() {
			$('#dialogMensaje > p').html(""); 
			$('#dialogMensaje > p').html("<strong>Desea regresar a la ventana anterior ...</strong>"); 
			
				$('#dialogMensaje').dialog({buttons: [
					{
						text: "OK", 
						click: function() {
								document.location.href = "${requestScope.urlRetorno}"; 
								}
					}, 
					{
						text: "Cancelar", 
						click: function() {
								$(this).dialog("close"); 
								}
					}
					
				]}); 
				
			$('#dialogMensaje').dialog('open'); 
				
			});

                        $('select#selectActivo').change( function() {
                        var valorSeleccionado = jQuery.trim($(this).val()) ;
                        $('select#selectCategoria option').remove();
                        $('select#selectCategoria').append('<option value="" selected="selected"> Seleccione Categoria ...&nbsp;&nbsp;&nbsp; </option>');
                        $('select#selectSubCategoria option').remove();
                        $('select#selectSubCategoria').append('<option value="" selected="selected">Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp; </option>');

				if ( valorSeleccionado != "" ) {
                                    
                                    $.ajax({
                                        url: 'respuestasXML.do',
                                        type: 'GET',
                                        data: {callMetodo: '_callMostrarCategorias',  codActivo: valorSeleccionado},
                                        cache: false,
                                        beforeSend: function() {
                                        $('select#selectCategoria').hide();
                                        $('tr#trSelectCategoria td:eq(1)').append(urlImagen);
                                        $('select#selectSubCategoria').hide();
					$('tr#trSelectSubCategoria td:eq(1)').append(urlImagen);
                                        },
                                        complete: function() {										
					$('tr#trSelectCategoria td:eq(1) img').remove();
					$('tr#trSelectSubCategoria td:eq(1) img').remove();
                                        $('select#selectCategoria').fadeIn('slow');
                                        $('select#selectSubCategoria').fadeIn('slow');
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
                        
                        });
			
			
			$('select#selectCategoria').change( function() {
			var valorSeleccionado = jQuery.trim($(this).val()) ;
			$('select#selectSubCategoria option').remove();
			$('select#selectSubCategoria').append('<option value="" selected="selected"> Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp; </option>');			
                        
				if ( valorSeleccionado != "" ) {
                                
                                    $.ajax({
                                        url: 'respuestasXML.do',
                                        type: 'GET',
                                        data: {callMetodo: '_callMostrarSubCategorias',  codCategoria: valorSeleccionado},
                                        cache: false,
                                        global: false, 
                                        beforeSend: function() {
                                        $('select#selectSubCategoria').hide();
					$('tr#trSelectSubCategoria td:eq(1)').append(urlImagen);
                                        },
                                        complete: function() {
                                        $('tr#trSelectSubCategoria td:eq(1) img').remove();
                                        $('select#selectSubCategoria').fadeIn('slow');
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
			
			});
                        
		});
		</script>
    </head>
    <body>
        <div id="parteDetalle">
		<h3>Atender Solicitud derivada </h3>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do" method="post" 
        	name="formAtenderSolicDerivada" id="formAtenderSolicDerivada">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Activo</strong></td>
              <td colspan="2">
              <span>
              ${requestScope.miSolicitud.subCategoria.categoria.activo.nombre}
              </span>
              </td>
            </tr>
            <tr id="trSelectActivo">
              <td>&nbsp;</td>
              <td colspan="2"><select name="selectActivo" id="selectActivo">
                <option value="" selected="selected"> Seleccione Activo ...&nbsp;&nbsp;&nbsp; </option>
              </select>
              </td>
            </tr>
            <tr>
              <td><strong>Categoría</strong></td>
              <td colspan="2">
              <span>
              ${requestScope.miSolicitud.subCategoria.categoria.nombre}              
              </span>
			</td>
            </tr>          
            <tr id="trSelectCategoria" >
              <td>&nbsp;</td>
              <td colspan="2"><select name="selectCategoria" id="selectCategoria">
                <option value="" selected="selected"> Seleccione Categoria ...&nbsp;&nbsp;&nbsp; </option>
              </select></td>
            </tr>
            <tr>
              <td><strong>Sub. Categoría </strong></td>
              <td colspan="2">
              <span>
              ${requestScope.miSolicitud.subCategoria.nombre}              
              </span>
			</td>
            </tr>
            <tr id="trSelectSubCategoria" >
              <td>&nbsp;</td>
              <td colspan="2"><select name="selectSubCategoria" id="selectSubCategoria" title="Sub-categoria de solicitud">
                <option value="" selected="selected"> Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp; </option>
              </select></td>
            </tr>
            <tr>
              <td><strong>Prioridad</strong></td>
              <td colspan="2">
              <span>
              ${requestScope.miSolicitud.prioridad.nombre}
              </span>
              <input type="hidden" name="hdPrioridad" id="hdPrioridad" 
              value="${requestScope.miSolicitud.prioridad.codigo}" title="Prioridad" >
              </td>
            </tr>
            <tr>
              <td width="14%"><strong>Fecha</strong></td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
                	size="30" readonly="true" title="Fecha de atencion">	
                <input type="hidden" name="opcion" id="opcion" value="_atenderSolicitudDerivada">
              </label>              </td>
            </tr>            
            <tr>
              <td>&nbsp;</td>
              <td width="44%">
              <button id="btnVolverClasificar" type="button" >
              <span>
              Volver a clasificar              </span>              </button>              </td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" title="Codigo de solicitud" 
		       value="${requestScope.miSolicitud.codSolicitud}"></td>
              <td>&nbsp;</td>
              <td width="42%"><label>
                <input type="button" name="btnGuardar" id="btnGuardar" 
                	value="Guardar"  class="botonSave" >
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" name="btnCancelar" id="btnCancelar" 
                	value="Regresar" class="botonRegresar" >
              </label>              </td>
            </tr>
          </table>
		
		</form>
		<br/>
		</div>        
        
        <div id="dialogMensaje" title="Atender Solicitud derivada" class="mensajeDialogo">
        <p></p>
        </div>
        
    </body>
</html>
