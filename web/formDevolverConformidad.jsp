<%-- 
    Document   : formDevolverConformidad
    Created on : 23/08/2011, 06:34:36 PM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Devolver la solicitud</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />			
        <link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">
		<script type="text/javascript" src="lib_java/funciones.js" ></script>
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
        <script type="text/javascript">
		$(document).ready(function() {	
		//Variables 
		var $controlSeleccionado ; 
		var ventanaActual;	
		var datosCompletos; 	
		//------------------------
		
			$( "#dialog:ui-dialog" ).dialog( "destroy" );				
			
			$('#dialogConfirmarReenvio').dialog({
				autoOpen: false, 
				resizable: false, 
				height: 170, 
				modal: true	
			}); 
			
			function seleccionarDatosFaltantes() {
				
				if ( datosCompletos == false ) {

					if ( jQuery.trim($controlSeleccionado.attr('name')) != "hdCodSolic" ) {
					$controlSeleccionado.removeClass(); 
					$controlSeleccionado.hide().addClass("textError").fadeIn("slow");	
					$controlSeleccionado.focus();
					}			
				
				}
				
			}
		
			$('#btnRegresar:button').click( function(evento){
			$('#dialogConfirmarReenvio > p').html(""); 
			$('#dialogConfirmarReenvio > p').html("<strong>Desea regresar a la ventana anterior ...</strong>"); 
				
				$('#dialogConfirmarReenvio').dialog( { buttons: [ 
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
			
			$('#dialogConfirmarReenvio').dialog("open"); 	
			
			});
			
			$('#btnGuardar:button').click( function(evento) {  			
			var nombreElemento ; 
			var valorElemento ; 
			var fArchivo = jQuery.trim( $(':input[name|="fileArchivo"]').val() ); 
			datosCompletos = true; 
			
				$(':input').each( function (index, elemento) {
				$controlSeleccionado = $(elemento);
				nombreElemento = jQuery.trim($controlSeleccionado.attr('name'));
				valorElemento = jQuery.trim($controlSeleccionado.val());
					
					if (  nombreElemento == "txtDetalle" ||  nombreElemento == "hdCodSolic" || 
						  ( nombreElemento == "txtNombreArchivo" &&  fArchivo != "" )  ) {
						
						if ( valorElemento == "" ) {
						datosCompletos = false; 
						return false; 
						}
						
					}
					
				
				}); 				
			
				if ( datosCompletos == false ) {
				var mensajeDialogo = "Falta completar: " + $controlSeleccionado.attr('title'); 
				$('#dialogConfirmarReenvio > p').html(""); 
				$('#dialogConfirmarReenvio > p').html("<strong>" + mensajeDialogo + "</strong>"); 
				$('#dialogConfirmarReenvio').dialog({ buttons: [
								{
									text: "OK", 
									click: function() {
										   $(this).dialog("close"); 
										   seleccionarDatosFaltantes(); 
										   }
								}
							 ]});
								
				$('#dialogConfirmarReenvio').dialog("open"); 
				seleccionarDatosFaltantes(); 
				
				}
				else {
				var mensajeDialogo = "Desea reenviar la solicitud."; 
				$('#dialogConfirmarReenvio > p').html(""); 
				$('#dialogConfirmarReenvio > p').html("<strong>" + mensajeDialogo + "</strong>"); 
				$('#dialogConfirmarReenvio').dialog({ buttons: [
								{
									text: "Reenviar", 
									click: function() {
										   document.frmDevolverSolicitudConformidad.submit(); 	
										   //$('#frmReenviarSolicitud').submit(); 
										   }
								}, 
								{
									text: "Cancelar", 
									click: function() {
										   $(this).dialog("close"); 
										   }
								}
							 ]});
							 
				$('#dialogConfirmarReenvio').dialog("open"); 
				
				}
			
			});
			
			
			$(':input').keypress( function(evento) {
						$(this).removeClass(); 
			});
			
		});

		</script>
    </head>
    <body>
    <div id="parteDetalle">
        <h3>Devolver solicitud enviada para conformidad</h3>
        <p>Ingrese los datos solicitados para devolver la solicitud</p>
        <jsp:include page="datosBasicosSolic.inc.jsp" />           
        <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<br/>
      <form action="controlTrabajador.do?opcion=_devolverSolicConformidad" method="post" enctype="multipart/form-data" 
        		name="frmDevolverSolicitudConformidad" id="frmDevolverSolicitudConformidad">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Destino</strong></td>
              <td colspan="2">${requestScope.miSolicitud.ultimoTramite.trabajadorOrigen.nombre}  ${requestScope.miSolicitud.ultimoTramite.trabajadorOrigen.apellido}</td>
            </tr>
            <tr>
              <td width="8%"><strong>Fecha</strong></td>
              <td colspan="2"><label>
              <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
                	size="30" readonly="true" title="Fecha de devolucion">
              </label>              </td>
            </tr>
            <tr>
              <td><strong>Detalle</strong></td>
              <td colspan="2"><label>
                <textarea name="txtDetalle" cols="40" rows="5" id="txtDetalle" title="Detalle de la devolucion"></textarea>
              </label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td colspan="3">
              <fieldset>
              <legend>Archivos adjuntos (Max. ${initParam["tamanioArchivos"]}Mb por archivo)</legend>
              <table width="100%" border="1">
                <tr>
                  <td colspan="3">Si necesita enviar mas de un archivo le recomendamos enviarlos todos en un archivo comprimido que puede ser ZIP o RAR ...</td>
                </tr>
                <tr>
                  <td width="23%"><label>Nombre Archivo</label></td>
                  <td width="77%" colspan="2"><span class="span-15 last">
                    <input name="txtNombreArchivo" type="text" size="30" maxlength="150" title="Nombre descriptivo del archivo" />
                  </span></td>
                </tr>
                <tr>
                  <td><label>Archivos</label></td>
                  <td colspan="2"><label><span class="span-15 last">
                    <input name="fileArchivo" type="file" size="50" onkeypress="return false" title="Archivo adjunto" />
                  </span></label></td>
                </tr>
              </table>
              </fieldset>              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" 
              title="Codigo de solicitud" 
              value="${requestScope.miSolicitud.codSolicitud}"></td>
              <td>&nbsp;</td>
              <td width="45%"><label>
                <input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" 
                	class="botonSave" >
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" name="btnRegresar" id="btnRegresar" value="Regresar" 
                		class="botonRegresar" >
              </label>              </td>
            </tr>
        </table>		
	  </form>
    </div>
    
    <div id="dialogConfirmarReenvio" class="mensajeDialogo" title="Reenviar Solicitud">
    	<p></p>
    </div>
    
    </body>
</html>
