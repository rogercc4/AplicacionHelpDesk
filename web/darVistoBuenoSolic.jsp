<%-- 
    Document   : darVistoBuenoSolic
    Created on : 03/08/2011, 09:12:01 PM
    Author     : Roger
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dar Visto Bueno</title>        
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
					$('#dialogMensaje > p').html("<strong>Desea dar visto bueno a solicitud ..?</strong>"); 
					
					$('#dialogMensaje').dialog({buttons: [
							{
								text: "Dar Visto", 
								click: function() {
									   $('#frmDarVistoBueno').submit(); 
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
		
		function enviarVistoBueno(form) {
		var miFecha = Trim(form.txtFecha.value) ; 
		var detalle = Trim(form.txtDetalle.value) ; 
		var codSolic = Trim(form.hdCodSolic.value) ; 
			
			if ( miFecha == "" ) {
			alert("Falta Ingresar la fecha"); 
			}
			else if ( detalle == "" ) {
			alert("Falta Ingresar el detalle"); 
			}
			else if ( codSolic == "" ) {
			alert("Falta especificar la solicitud ..."); 
			}
			else if ( esNumero(codSolic) == false ) {
			alert("Especifique un codigo de solicitud correcto ..."); 
			}
			else {
			var respuesta = confirm("Desea dar visto bueno a la solicitud ...?"); 
				
				if ( respuesta == true ) {
				form.submit(); 
				}

			}
			
				
		}
		</script>
    </head>
    <body>
		<div id="parteDetalle">
		<h3>Dar Visto Bueno Solicitud</h3>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do" method="post" name="frmDarVistoBueno" id="frmDarVistoBueno">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8%">Fecha</td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
                	size="30" readonly="true" title="Fecha del visto bueno">	
                <input type="hidden" name="opcion" id="opcion" value="_darVistoBueno">
              </label>              </td>
            </tr>
            <tr>
              <td>Detalle</td>
              <td colspan="2"><label>
                <textarea name="txtDetalle" cols="40" rows="5" 
                	id="txtDetalle" title="Detalle del visto bueno"  ></textarea>
              </label></td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" title="Codigo de solicitud"
		              value="${requestScope.miSolicitud.codSolicitud}">
              <input type="hidden" name="hdNumPage" 
              		id="hdNumPage" value="${requestScope.page}">
			  </td>
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
        
        <div id="dialogMensaje" title="Dar Visto Bueno" class="mensajeDialogo">
        <p></p>
        </div>
        
    </body>
</html>