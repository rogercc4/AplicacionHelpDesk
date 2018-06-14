<%-- 
    Document   : formGestionarMensajes
    Created on : 26/09/2011, 09:37:59 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />
        <link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">
        <script type="text/javascript" src="lib_java/funciones.js"></script>
        <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.tabs.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/plugin/jQuery.form.js"></script>
    	<script type="text/javascript" src="lib_java/gestionarMensajes.js"></script>
        <script type="text/javascript">
		$(document).ready(function() {	
		
			$('button#btnRegresar').click(function() {
			
			$('div#dialogRegresar').dialog({title: 'Regresar'}); 
			
			$('div#dialogRegresar p').html("Desea regresar a la ventana anterior ..."); 
			
				$('div#dialogRegresar').dialog({buttons: [
						{
						text: "OK", 
						click: function() { document.location.href = "${requestScope.urlRetorno}"; }
						}, 
						{
						text: "Cancelar", 
						click: function() { $(this).dialog("close"); }
						}
				]}); 
		
			$('div#dialogRegresar').dialog("open"); 
			
			});
		
		});
		
		function cargarMensajes() {
		
				$.ajax({
				url: "controlSolicitud.do", 
				type: "GET", 
				data: {opcion: '_mostrarListadoMensajes', codSolic: "${requestScope.miSolicitud.codSolicitud}"}, 
				global: false, 
				cache: false, 
				beforeSend: function() { 
							$('div#listaMensajes').append('<img src="imagenes/progressBar.gif">');
							},
				success: function(data) {
						 $('div#listaMensajes').empty();
						 $('div#listaMensajes').html(data);
						 }, 
				dataType: 'html' 
				});
					
		}
		</script>
        
        <title>Gestionar Mensajes</title>
    </head>
    <body>
        <div id="parteDetalle">
        <h3>Administrar mensajes</h3>
        
            <div id="tabsGestionMensajes">
                <ul >
                    <li ><a href="#tabs-1">Datos Solicitud</a></li>
                    <li ><a href="#tabs-2">Mensajes</a></li>
                </ul>
                
                <div id="tabs-1">
                <jsp:include page="datosBasicosSolic.inc.jsp" />
                </div>
                
                <br/>
                
                <div id="tabs-2">
                    <div id="listaMensajes">
                    <jsp:include page="listadoMensajes.inc.jsp" />
                    </div>
                </div>
                
            </div>
         <br/><br/>
		<table width="100%" >
          <tr>
            <td>
            <button type="button" id="btnEnviarMensaje">
            <span>Enviar Mensaje ...</span>
            </button>            
            
			&nbsp;&nbsp;
            <button type="button" id="btnMostrarDetalle">
            <span>Mostrar Detalles ...</span>
            </button>
			
			&nbsp;&nbsp;
            <button type="button" id="btnRegresar">
            <span>Regresar ...</span>
            </button>
            </td>
          </tr>
        </table>
        </div>  
        <br/><br/>
        
        <div id="dialogRegresar" title="Regresar" class="mensajeDialogo" >
        <p></p>
        </div>  
        
	<div id="dialoGestionSendMessage" class="mensajeDialogo" title="Enviar Mensaje">
	<form id="frmSendMensaje" name="frmSendMensaje" action="" method="post" enctype="multipart/form-data" >
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15%"><strong>Asunto</strong></td>
              <td width="85%"><label>
                <input name="txtAsunto" type="text" id="txtAsunto" size="45">
              </label></td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" 
              			value="${requestScope.miSolicitud.codSolicitud}">
              </td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Descripcion</strong></td>
              <td><label>
                <textarea name="txtDescripcion" id="txtDescripcion" cols="45" rows="5"></textarea>
              </label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Titulo Archivo</strong></td>
              <td><input name="txtArchivo" type="text" id="txtArchivo" size="45"></td>
            </tr>
            <tr>
              <td><strong>Archivo</strong></td>
              <td>
              <label>
                <input type="file" name="fileAdjunto" id="fileAdjunto"> 
	              (Max. ${initParam["tamanioArchivos"]}Mb )			  </label>
			  </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>            
            <tr id="mensajeError" class="ui-state-error">
              <td colspan="2"></td>
            </tr>
      </table>
      </form>
	</div>              
    
    <div id="dialogDetailMensaje" class="mensajeDialogo" title="Detalle Mensaje">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Origen</strong></td>
              <td><label id="lblDetailOrigen"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Destino</strong></td>
              <td><label id="lblDetailDestino"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td width="15%"><strong>Asunto</strong></td>
              <td width="85%"><label id="lblDetailAsunto"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Fecha</strong></td>
              <td><label id="lblDetailFecha"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Descripcion</strong></td>
              <td><label id="lblDetailDescripcion"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Archivo Adjunto</strong></td>
              <td><label id="lblDetailArchivoAdjunto"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
      </table>
    </div>
    
        
    </body>
</html>
