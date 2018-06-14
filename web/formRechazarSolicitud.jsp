<%-- 
    Document   : formRechazarSolicitud
    Created on : 18/08/2011, 09:17:38 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript">
		$(document).ready(function() {
			$('#btnRegresar:button').click( function(evento){	
			var retornar = confirm('Desea regresar a la ventana anterior'); 
				
				if ( retornar == true  ) {
				document.location.href = "${requestScope.urlRetorno}"; 	
				}
			
			});
			
			$('#btnGuardar:button').click( function(evento) {  
			var nombreFile =  jQuery.trim($(':input[name|="fileArchivo"]').val()); 
			var nombreDesFile = jQuery.trim($(':input[name|="txtNombreArchivo"]').val()) ; 
			var detalle = jQuery.trim( $(':input[name|="txtDetalle"]').val() ); 
				
				if ( detalle == "" ) {
				alert("Falta especificar el motivo por el cual se rechaza la solicitud ..."); 
				}				
				else if ( nombreFile != "" && nombreDesFile == "" ) {
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
				var pregunta = confirm('Desea rechazar la solicitud ...');
					
					if ( pregunta == true ) {
					$('#frmRechazarSolicitud').submit();
					}
				
				}
			
			});
			
			$(':input').keypress( function(evento) {
						$(this).removeClass(); 
			});
			
		});
		</script>        
        <title>Rechazar Solicitud</title>
    </head>
    <body>
     <div id="parteDetalle">
		<h3>Rechazar Solicitud </h3>
        <jsp:include page="datosBasicosSolic.inc.jsp" />    
        <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<br/>
       <form action="controlTrabajador.do?opcion=_rechazarSolicitud" method="post" enctype="multipart/form-data" 
        		name="frmRechazarSolicitud" id="frmRechazarSolicitud">
	     <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8%"><strong>Fecha</strong></td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" size="30" readonly="true">
              </label>              </td>
            </tr>
            <tr>
              <td><strong>Motivo</strong></td>
              <td colspan="2"><label>
              <textarea name="txtDetalle" cols="40" rows="5" id="txtDetalle"></textarea>
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
                    <input name="txtNombreArchivo" type="text" size="30" maxlength="150" />
                  </span></td>
                </tr>
                <tr>
                  <td><label>Archivos</label></td>
                  <td colspan="2"><label><span class="span-15 last">
                    <input name="fileArchivo" type="file" size="50" onkeypress="return false" />
                  </span></label></td>
                </tr>
              </table>
              </fieldset>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" 
		              value="${requestScope.miSolicitud.codSolicitud}">
              <input type="hidden" name="hdNumPage" 
              		id="hdNumPage" value="${requestScope.page}">			  </td>
              <td>&nbsp;</td>
              <td width="45%"><label>
              <input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" 
                	class="botonSave" />
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input name="btnRegresar" type="button" class="botonRegresar" id="btnRegresar" value="Regresar" />
              </label>              </td>
            </tr>
         </table>
		
		</form>
    </div>
    </body>
</html>
