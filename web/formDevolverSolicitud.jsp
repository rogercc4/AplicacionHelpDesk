<%-- 
    Document   : formDevolverSolicitud
    Created on : 16/08/2011, 10:42:16 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Devolver Solicitud</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />		
		<link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">	
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/funciones.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>		
        <script type="text/javascript">
		$(document).ready(function() {
		
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
		$('#dialogDevolverSolic').dialog({
		autoOpen: false, 
		resizable: false, 
		height: 170, 
		modal: true
		}); 
				
			$('#btnRegresar:button').click( function(evento){                   
			$('#dialogDevolverSolic > p').html("");
			$('#dialogDevolverSolic > p').html("<strong>Desea regresar a la ventana anterior</strong>");

					$('#dialogDevolverSolic').dialog({buttons: [
							{
							text: "OK",
							click: function() {
									document.location.href = "${requestScope.urlRetorno}";
									}
							}
		
					]});
                        
              $('#dialogDevolverSolic').dialog("open");
			  
              }); 
			  
			$('#btnGuardar:button').click( function(evento) {  			
			var nombreElemento ; 
			var valorElemento ; 
			var fArchivo = jQuery.trim( $(':input[name|="fileArchivo"]').val() ); 
			var datosCompletos = true; 
			var $controlSeleccionado ; 
			
				$(':input').each( function (index, elemento) {
				$controlSeleccionado = $(elemento);
				nombreElemento = jQuery.trim($controlSeleccionado.attr('name'));
				valorElemento = jQuery.trim($controlSeleccionado.val());
					
					if (  nombreElemento == "txtDetalle" ||  nombreElemento == "hdCodSolic" || 
						  nombreElemento == "txtFecha" || 
					  	( nombreElemento == "txtNombreArchivo" &&  fArchivo != "" )  ) {
						
						if ( valorElemento == "" ) {
						datosCompletos = false; 
						return false; 
						}
						
					}
					
				
				}); 				
			
				if ( datosCompletos == false ) {
				var mensajeDialogo = "Falta completar: " + $controlSeleccionado.attr('title'); 
				$('#dialogDevolverSolic > p').html(""); 
				$('#dialogDevolverSolic > p').html("<strong>" + mensajeDialogo + "</strong>"); 
				$('#dialogDevolverSolic').dialog({ buttons: [
								{
									text: "OK", 
									click: function() {
										   $(this).dialog("close"); 
										   seleccionarDatosFaltantes(); 
										   }
								}
							 ]});
								
				$('#dialogDevolverSolic').dialog("open"); 
				seleccionarDatosFaltantes(); 
				
				}
				else {
				var mensajeDialogo = "Desea devolver la solicitud ..."; 
				$('#dialogDevolverSolic > p').html(""); 
				$('#dialogDevolverSolic > p').html("<strong>" + mensajeDialogo + "</strong>"); 
				$('#dialogDevolverSolic').dialog({ buttons: [
								{
									text: "Devolver", 
									click: function() {
										   $('#frmDevolverSolicitud').submit(); 
										   }
								}, 
								{
									text: "Cancelar", 
									click: function() {
										   $(this).dialog("close"); 
										   }
								}
							 ]});
							 
				$('#dialogDevolverSolic').dialog("open"); 
				
				}
			
			});
			  
			  
	
			/*
			$('#btnGuardar:button').click( function(evento) {  
			var nombreFile =  jQuery.trim($(':input[name|="fileArchivo"]').val()); 
			var nombreDesFile = jQuery.trim($(':input[name|="txtNombreArchivo"]').val()) ; 
				
				if ( nombreFile != "" && nombreDesFile == "" ) {
				alert("Falta ingresar el nombre del archivo");				
				$(':input[name|="txtNombreArchivo"]').hide(); 
				$(':input[name|="txtNombreArchivo"]').addClass("textError"); 
				$(':input[name|="txtNombreArchivo"]').fadeIn("slow");
				$(':input[name|="txtNombreArchivo"]').focus();
				}
				else if ( nombreFile == "" && nombreDesFile != "" ) {
				alert("Falta especificar el archivo que se desea subir");
				$(':input[name|="fileArchivo"]').hide(); 
				$(':input[name|="fileArchivo"]').addClass("textError"); 
				$(':input[name|="fileArchivo"]').fadeIn("slow");
				$(':input[name|="fileArchivo"]').focus(); 
				}
				else {
				var pregunta = confirm('Desea devolver la solicitud ...'); 
					
					if ( pregunta == true ) {
					$('#frmDevolverSolicitud').submit(); 	
					}
				
				}
			
			});
			*/
			
			$(':input').keypress( function(evento) {
						$(this).removeClass(); 
			});
			
		});

		</script>
    </head>
    <body>    
	<div id="parteDetalle">
		<h3>Devolver Solicitud </h3>
        <jsp:include page="datosBasicosSolic.inc.jsp" />    
        <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<br/>
        <form action="controlTrabajador.do?opcion=_devolverSolicitud" method="post" enctype="multipart/form-data" 
        		name="frmDevolverSolicitud" id="frmDevolverSolicitud">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Destino</strong></td>
              <td colspan="2">${requestScope.miSolicitud.trabajador.nombre} ${requestScope.miSolicitud.trabajador.apellido}</td>
            </tr>
            <tr>
              <td width="8%"><strong>Fecha</strong></td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
				size="30" readonly="true" title="Fecha de la devolucion">	
                <input type="hidden" name="opcion" id="opcion" value="_darVistoBueno">
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
		              value="${requestScope.miSolicitud.codSolicitud}" title="Codigo de la solicitud">
              <input type="hidden" name="hdNumPage" 
              		id="hdNumPage" value="${requestScope.page}">			  </td>
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
	<div id="dialogDevolverSolic" title="Devolver Solicitud" class="mensajeDialogo">
	<p></p>
	</div>
	
    </body>
</html>
