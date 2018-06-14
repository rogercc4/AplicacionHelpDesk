<%-- 
    Document   : formEnviarConformidad
    Created on : 02/09/2011, 12:01:23 PM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Finalizar Solicitud</title>        
		<link rel="stylesheet" type="text/css" href="css/estilos.css">
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
		var controlEntrada ; 
		var datosCompletos; 
		
			$('#dialogMensaje').dialog({
				autoOpen: false, 
				height: 170, 
				resizable: false, 
				modal: true
			});
			
			$('#btnGuardar:button').click(function() {
			var nombreControl ; 	
			datosCompletos = true; 
			
				$(':input').each( function(index, elemento) { 
				controlEntrada = $(elemento); 	
				nombreControl = jQuery.trim(controlEntrada.attr('name')); 
					
					if ( nombreControl == "hdCodSolic" || nombreControl == "txtFecha" ||
                                             nombreControl == "txtDetalle" ) {
						
						if ( jQuery.trim(controlEntrada.val()) == "" ) {
						datosCompletos = false; 
						return false; 
						}
						
					}
					
				});

				if ( datosCompletos == false ) {
					$('#dialogMensaje > p').html(""); 
					$('#dialogMensaje > p').html("<strong>Falta completar: " + 
												  controlEntrada.attr("title") + 
												  "</strong>");

						$('#dialogMensaje').dialog({buttons: [
								{
									text: "OK", 
									click: function() {
											$(this).dialog("close"); 
											}
								}
						]}); 
						
					$('#dialogMensaje').dialog('open');
					
				}
				else {
					$('#dialogMensaje > p').html(""); 
					$('#dialogMensaje > p').html("<strong>Desea finalizar la atencion de la solicitud ..?</strong>"); 
					
					$('#dialogMensaje').dialog({buttons: [
							{
								text: "Finalizar", 
								click: function() {
									   $('#frmFinalizarAtencion').submit(); 
									   }
							}, 
							{
								text: "Cancelar", 
								click: function() {
										$(this).dialog("close"); 
										}
							}
							
					]}); 
					
					$('#dialogMensaje').dialog("open"); 
					
				}
				
			}); 
			
			$('#btnCancelar').click(function() {
			
				$('#dialogMensaje > p').html(""); 
				$('#dialogMensaje > p').html("<strong>Desea regresar a la ventana anterior</strong>"); 
				
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
			
				$('#dialogMensaje').dialog("open"); 
			
			});
					
		}); 
		</script>
        
        
		<script type="text/javascript">
		function regresar( form ) {		
		var numPage = Trim(form.hdNumPage.value); 
		var codSolic = Trim(form.hdCodSolic.value); 
			
			if ( numPage == "" ) {
			numPage = "1"; 
			}
			if ( esNumero(numPage) == false ) {
			numPage = "1"; 
			}
			
			if ( codSolic == "" ) {
			alert("Falta especificar la solicitud ..."); 
			}
			else if ( esNumero(codSolic) == false ) {
			alert("Especifique un codigo de solicitud correcto ..."); 
			}
			else {
			var urlRegresar = "controlSolicitud.do?opcion=_detalleSolicAtenVB"; 
				urlRegresar = urlRegresar + "&codSolic=" + codSolic + "&txtPage=" + numPage; 
			document.location.href = urlRegresar ; 			
			}
		
		
		}
				
		</script>
    </head>
    <body>
		<div id="parteDetalle">
		<h3>Finalizar Atencion de la Solicitud</h3>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do" method="post" name="frmFinalizarAtencion" id="frmFinalizarAtencion">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8%">Fecha</td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
                	size="30" readonly="true" title="Fecha de la finalizacion">	
              <input type="hidden" name="opcion" id="opcion" value="_finalizarAtencionSolicitud">
              </label>              </td>
            </tr>
            <tr>
              <td>Detalle</td>
              <td colspan="2"><label>
                <textarea name="txtDetalle" cols="40" rows="5" 
                	id="txtDetalle" title="Detalle de la finalizacion de la solicitud "  ></textarea>
              </label></td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" title="Codigo de solicitud"
		              value="${requestScope.miSolicitud.codSolicitud}"></td>
              <td>&nbsp;</td>
              <td width="45%"><label>
                <input type="button" id="btnGuardar" name="btnGuardar" 
                	value="Guardar" class="botonSave" >
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" id="btnCancelar" name="btnCancelar" 
                	value="Regresar" class="botonRegresar"  >
              </label>			  
              </td>
            </tr>
          </table>
		
		</form>
		<br/>
		</div>
        
        <div id="dialogMensaje" title="Finalizar Solicitud" class="mensajeDialogo">
        <p></p>
        </div>
        
    </body>
</html>