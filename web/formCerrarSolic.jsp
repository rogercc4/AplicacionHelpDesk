<%-- 
    Document   : darVistoBuenoSolic
    Created on : 03/08/2011, 09:12:01 PM
    Author     : Roger
--%>

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
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
                
        <script type="text/javascript">
		
		$(document).ready( function() {
		
		var $controlEntrada ; 
		var $valoresImcompletos ; 
		
			$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
			$( "#dialog-confirm" ).dialog({
				autoOpen: false,	
				resizable: false,	
				height:160,
				modal: true,
				buttons: {
					"Aceptar": function() {
						$( this ).dialog( "close" );
						$('#frmCerrarSolicitud').submit(); 
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			
			$( "#dialog-message" ).dialog({
				autoOpen: false,	
				resizable: false,	
				height:160, 
				modal: true
			});
			
			$('#btnGuardar:button').click( function(evento) { 	
			$valoresImcompletos = false; 
				
				$(':input').each( function(index, elemento){
				$controlEntrada = $(elemento); 
					
					if ( jQuery.trim( $controlEntrada.attr('name') ) == "txtFecha" || 
						 jQuery.trim( $controlEntrada.attr('name') ) == "txtDetalle" || 
						 jQuery.trim( $controlEntrada.attr('name') ) == "hdCodSolic" ) {	
						
						if ( jQuery.trim( $controlEntrada.val() ) == "" ) {
						$valoresImcompletos = true ; 
						return false; 
						}
						
					}
					
			  	}); 
				
				if ( $valoresImcompletos == true ) {
				$('#dialog-message > p').html(""); 
				$('#dialog-message > p').html("<strong>Falta completar: " + $controlEntrada.attr('title') + 
												"</strong>"); 
												
				$('#dialog-message').dialog({ buttons: [
						{
						text: "OK", 
						click: function() {
								$(this).dialog("close"); 
								}
						}
				]}); 
												
												
				$( "#dialog-message" ).dialog( "open" );
				}
				else {
				$( "#dialog-confirm" ).dialog( "open" );
				}
			
			});
			
			$('#btnCancelar').click( function() {
			$('#dialog-message > p').html(""); 
			$('#dialog-message > p').html("<strong>Desea regresar a la ventana anterior ...</strong>");
			
				$("#dialog-message").dialog( {buttons: [
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
			
			$("#dialog-message").dialog("open"); 
			
			}); 
			
		
		});
		</script>
    </head>
    <body>
		<div id="parteDetalle">
		<h3>Cerrar solicitud</h3>
        <p>Complete la informacion solicitada para cerrar una solicitud. Una solicitud es cerrada para 
        	indicar la conformidad por parte del usuario. </p>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do" method="post" name="frmCerrarSolicitud" id="frmCerrarSolicitud">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8%">Fecha</td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" title="Fecha del cierre"
                	value="${pageScope.miFecha.fechaHoraActual}" size="30" 
                    readonly="true">	
                <input type="hidden" name="opcion" id="opcion" value="_cerrarSolicitud" >
              </label>              </td>
            </tr>
            <tr>
              <td>Detalle</td>
              <td colspan="2"><label>
                <textarea name="txtDetalle" cols="40" rows="5" title="Detalle del cierre" 
                	id="txtDetalle" ></textarea>
              </label></td>
            </tr>
            <tr>
              <td><input type="hidden" name="hdCodSolic" id="hdCodSolic" title="Codigo de solicitud" 
		              value="${requestScope.miSolicitud.codSolicitud}"></td>
              <td>&nbsp;</td>
              <td width="45%"><label>
                <input type="button" name="btnGuardar" id="btnGuardar" 
                	value="Guardar" class="botonSave" >	
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" name="btnCancelar" id="btnCancelar" value="Regresar" 
                		class="botonRegresar"  >
              </label>              </td>
            </tr>
          </table>
		
		</form>
		<br/>
		</div>
        
        <div id="dialog-confirm" title="Desea cerrar la solicitud ..?" class="mensajeDialogo">
            <p>
            <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
            Desea usted cerar la solicitud ..?. 
            Al cerrar, usted da por finalizada la atencion a su solicitud.
            </p>
		</div>
        
        <div id="dialog-message" title="Cerrar la solicitud" class="mensajeDialogo">
        	<p></p>
        </div>
        
    </body>
</html>