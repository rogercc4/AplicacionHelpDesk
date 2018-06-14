<%-- 
    Document   : formAsignarSolic
    Created on : 11/08/2011, 09:08:52 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atender Solicitud</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />			
        <link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">		
		<script type="text/javascript" src="lib_java/funciones.js" ></script>
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/selectores.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
        <script type="text/javascript">
		$(document).ready(function() { 			
		
		cargarDatosActivos(); 
		mostrarPrioridades(); 
		
		$('select#selectActivo').change( function() {
		var valorSeleccionado = jQuery.trim($(this).val()) ;
			cargarDatosCategorias( valorSeleccionado ); 
		}); 
		
		$('select#selectCategoria').change( function() {
		var valorSeleccionado = jQuery.trim($(this).val()) ;
			cargarDatosSubCategorias(valorSeleccionado); 
		}); 
		
		$('select#selectSubCategoria').change( function() {
		var valorSeleccionado = jQuery.trim($(this).val());
			mostrarCargosSubCategorias(valorSeleccionado);
		}); 
		
		$('select#selectCargo').change( function() {
		var valorSeleccionado = jQuery.trim($(this).val());
			mostrarPersonasCargo(valorSeleccionado);
		}); 
		
		
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
		$('#dialog-confirm').dialog({
			autoOpen: false, 
			height: 170, 
			modal: true, 
			resizable: false 			
		});
		
		$('#btnGuardar:button').click(function() {
		var nombreElemento ; 
		var valorElemento ; 
		var fArchivo = jQuery.trim( $(':input[name|="fileArchivo"]').val() ); 
		var datosCompletos = true; 
			
			$(':input').each( function (index, elemento) {
			$controlSeleccionado = $(elemento);
			nombreElemento = jQuery.trim($controlSeleccionado.attr('name'));
			valorElemento = jQuery.trim($controlSeleccionado.val());
				
				if (  nombreElemento == "selectActivo" ||  nombreElemento == "selectCategoria" || 
					  nombreElemento == "selectSubCategoria" ||  nombreElemento == "selectCargo" || 	
					  nombreElemento == "selectPersona" ||  nombreElemento == "selectPrioridad" || 	
					  nombreElemento == "txtFecha" ||  nombreElemento == "txtDetalle" || 	
						  ( nombreElemento == "txtNombreArchivo" &&  fArchivo != "" )  ) {
						
						if ( valorElemento == "" ) {
						datosCompletos = false; 
						return false; 
						}
						
				}				
				
			}); 
			
			if ( datosCompletos == false ) {
			var mensajeDialogo = "Falta completar: " + $controlSeleccionado.attr('title'); 
			$('#dialog-confirm > p').html(""); 
			$('#dialog-confirm > p').html("<strong>" + mensajeDialogo + "</strong>"); 
			$('#dialog-confirm').dialog({ buttons: [
							{
								text: "OK", 
								click: function() {
									   $(this).dialog("close"); 
									   }
							}
						 ]});
							
			$('#dialog-confirm').dialog("open"); 
			seleccionarDatosFaltantes(); 
			
			}
			else {
			var mensajeDialogo = "Desea derivar la solicitud."; 
			$('#dialog-confirm > p').html(""); 
			$('#dialog-confirm > p').html("<strong>" + mensajeDialogo + "</strong>"); 
			$('#dialog-confirm').dialog({ buttons: [
							{
								text: "Derivar", 
								click: function() {									   
									   $('#formDerivarSolic').submit(); 
									   }
							}, 
							{
								text: "Cancelar", 
								click: function() {
									   $(this).dialog("close"); 
									   }
							}
						 ]});
						 
			$('#dialog-confirm').dialog("open"); 
			
			}
		
		}); 
		
		
		$('#btnRegresar:button').click( function(evento){
		$('#dialog-confirm > p').html(""); 
		$('#dialog-confirm > p').html("<strong>Desea regresar a la ventana anterior ...</strong>"); 
			
			$('#dialog-confirm').dialog( { buttons: [ 
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
		
		$('#dialog-confirm').dialog("open"); 	
		
		});
		
				

		}); 
		</script>
    </head>
    <body>
<div id="parteDetalle">
		<h3>Derivar Solicitud </h3>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do?opcion=_derivarSolicitud" method="post" enctype="multipart/form-data" 
        	name="formDerivarSolic" id="formDerivarSolic">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr id="trSelectActivo">
              <td width="17%"><strong>Activo</strong></td>
              <td colspan="2"><label id="lblSelectActivo">
              <select name="selectActivo" id="selectActivo" title="Activo de la solicitud">
                <option value="" selected="selected"> Seleccione Activo ...&nbsp;&nbsp;&nbsp; </option>
                <option value="1"> Datos/Información&nbsp;&nbsp;&nbsp; </option>
                <option value="2"> Servicios&nbsp;&nbsp;&nbsp; </option>
              </select>
              </label></td>
            </tr>
            <tr id="trSelectCategoria">
              <td><strong>Categoría</strong></td>
              <td colspan="2">
              <label id="lblSelectCategoria">
              <select name="selectCategoria" id="selectCategoria" title="Categoria de la solicitud">
                    <option value="" selected="selected">
                        Seleccione Categoria ...&nbsp;&nbsp;&nbsp;                    </option>
                    <option value="1">
                        BD Externas&nbsp;&nbsp;&nbsp;                    </option>
                    <option value="2">
                        BD Intranet&nbsp;&nbsp;&nbsp;                    </option>
              </select>
              </label>              </td>
            </tr>          
            <tr id="trSelectSubCategoria">
              <td><strong>Sub. Categoría </strong></td>
              <td colspan="2">
              <label id="lblSelectSubCategoria">
                  <select name="selectSubCategoria" id="selectSubCategoria" title="Sub-categoria de la solicitud">
                    <option value="" selected="selected">
                        Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp;                    </option>
                    <option value="1">
                        Actualización de Datos&nbsp;&nbsp;&nbsp;                    </option>
                    <option value="2">
                        Proceso&nbsp;&nbsp;&nbsp;                    </option>
                  </select>              
              </label>			</td>
            </tr>            
            <tr>
              <td><strong>Derivar a</strong></td>
              <td colspan="2"><label id="lblSelectCargo">
                <select name="selectCargo" id="selectCargo" title="Cargo del responsable">
                  <option value="">Seleccione Cargo ... </option>
                  <option value="1">Cargo 1</option>
                  <option value="2">Cargo 2</option>
                </select>
                </label>              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="2"><label id="lblSelectPersona">
              <select name="selectPersona" id="selectPersona" title="Persona a la que se deriva">
                <option value="" selected="selected">Seleccione Responsable ...</option>
                <option value="1">Cargo 1</option>
                <option value="2">Cargo 2</option>
              </select>
              </label></td>
            </tr>
            <tr>
              <td><strong>Prioridad</strong></td>
              <td colspan="2">
              <label id="lblSelectPrioridad" >
              <select name="selectPrioridad" id="selectPrioridad" title="Prioridad de la solicitud">
                  <option value="" selected="selected"> Seleccione Prioridad ...&nbsp;&nbsp;&nbsp; </option>
                  <option value="1"> Alta&nbsp;&nbsp;&nbsp; </option>
                  <option value="2"> Media&nbsp;&nbsp;&nbsp; </option>
                  <option value="3"> Baja&nbsp;&nbsp;&nbsp; </option>
              </select>              
              </label>              </td>
            </tr>
            <tr>
              <td><strong>Fecha</strong></td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
					size="30" readonly="true" title="Fecha de derivacion">
                <input type="hidden" name="opcion" id="opcion" value="_derivarSolicitud">
                </label>              </td>
            </tr>
            <tr>
              <td><strong>Detalle</strong></td>
              <td colspan="2"><label>
                <textarea name="txtDetalle" id="txtDetalle" cols="45" 
				rows="5" title="Detalle de la derivacion" >
				</textarea>
              </label></td>
            </tr>                        
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td colspan="3"><fieldset>
              <legend>Archivos adjuntos (Max. ${initParam["tamanioArchivos"]}Mb por archivo)</legend>
              <table width="100%" border="1">
                <tr>
                  <td colspan="3">Si necesita enviar mas de un archivo le recomendamos enviarlos todos en un archivo comprimido que puede ser ZIP o RAR ...</td>
                </tr>
                <tr>
                  <td width="23%"><label>Nombre Archivo</label></td>
                  <td width="77%" colspan="2"><span class="span-15 last">
                    <input name="txtNombreArchivo" type="text" size="30" maxlength="150" title="Nombre del archivo" />
                  </span></td>
                </tr>
                <tr>
                  <td><label>Archivos</label></td>
                  <td colspan="2"><label><span class="span-15 last">
                    <input name="fileArchivo" type="file" size="50" onkeypress="return false" title="Archivo adjunto" />
                  </span></label></td>
                </tr>
              </table>
              </fieldset></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td width="41%">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><input type="hidden" name="hdCodSolic" id="hdCodSolic" 
		              value="${requestScope.miSolicitud.codSolicitud}">
              <input type="hidden" name="hdNumPage" 
              		id="hdNumPage" value="${requestScope.page}">                    </td>
              <td>&nbsp;</td>
              <td width="42%"><label>
                <input type="button" name="btnGuardar" id="btnGuardar" 
                	value="Guardar" class="botonSave" >
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" name="btnRegresar" id="btnRegresar" 
                	value="Regresar" class="botonRegresar" >
              </label>              </td>
            </tr>
          </table>
		
  </form>
<br/>
		</div>
		
		<div id="dialog-confirm" class="mensajeDialogo" title="Derivar Solicitud">
		<p></p>
		</div>		
		
    </body>
</html>
