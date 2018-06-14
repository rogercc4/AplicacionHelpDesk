<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin t&iacute;tulo</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css" />
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
	
		$( "#dialog:ui-dialog" ).dialog( "destroy" );	
		
		$( "#dialog-confirm" ).dialog({
			autoOpen: false,	
			resizable: false,	
			height:180,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog( "close" );
					$('#formGenerarSolicitud').submit(); 
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
			modal: true,
			buttons: {					
				"OK": function() {					
				$( this ).dialog( "close" );
				miControlEntrada.hide().addClass("textError").show("slow");
				}
			}
		});	
	
	
		$('#btnSelectFormato').click( function(evento){ 
		var miFormato = jQuery.trim($(':input[name|="selectArchivoFormato"]').val()) ; 
		var miControlEntrada ; 
		var datosCompletos ; 		
		
			
			if ( miFormato == "" ) {
			alert('Debe seleccionar un formato de tramite ...'); 
			$(':input[name|="selectArchivoFormato"]').hide(); 
			$(':input[name|="selectArchivoFormato"]').addClass("textError"); 
			$(':input[name|="selectArchivoFormato"]').fadeIn("slow");
			}
			else {
			window.open("formatoTramite.do?codigo=" + miFormato);
			}
			
		});
		
		$(':input').focus( function(evento) { 
		var atributo = 	jQuery.trim($(this).attr("name")); 
		var tipo = jQuery.trim($(this).attr("type")); 
		
			if ( atributo == "selectArchivoFormato" || tipo == "text" ) {
			$(this).removeClass();
			}
			
		}); 
		
		$('#btnGuardar').click( function(evento){
		datosCompletos = true ; 
		var fArchivo =  jQuery.trim($(':input[name|="fileArchivo"]').val());
			
			$(':input').each( function(index, elemento) {
			miControlEntrada = $(elemento); 
			var nombreControlEntrada = jQuery.trim( miControlEntrada.attr('name') ); 
			var valorControlEntrada = jQuery.trim( miControlEntrada.val() ); 			
				
				if (  ( nombreControlEntrada == "txtAsunto" || nombreControlEntrada == "txtFecha" || 
						nombreControlEntrada == "txtDetalle" ) || 
						( nombreControlEntrada == "txtNombreArchivo" && fArchivo != "" ) ) { 
						
						if ( valorControlEntrada == "" ) {
						datosCompletos = false ; 
						return false ; 
						}
				
				}
			
			}); 
		
			if ( datosCompletos == false ) {
			$('#dialog-message > p').html("<strong>Falta completar: " + 
											miControlEntrada.attr('title') + 
											"</strong>"); 
												
			$( "#dialog-message" ).dialog( "open" );
			}
			else {
			$( "#dialog-confirm" ).dialog( "open" );
			}
		
		}); 	
		
				
	}); 
</script>
<script type="text/javascript">
function regresar() {

var respuesta = confirm('Desea salir de este formulario ..?'); 
	
	if ( respuesta == true ) {
	document.location.href='controlSolicitud.do?opcion=_verSolicitudes';
	}	
}

function validarFormulario(form) {
var asunto = Trim(form.txtAsunto.value); 
var fecha = Trim(form.txtFecha.value); 
var detalle = Trim(form.txtDetalle.value); 
var fArchivo = Trim(form.fileArchivo.value); 
var nombreArchivo = Trim(form.txtNombreArchivo.value); 

	if ( asunto == "" ) {
	alert("Falta ingresar el asunto de la solicitud ..."); 
	form.txtAsunto.focus(); 
	}
	else if ( fecha == "" ) {
	alert("Falta ingresar la fecha de la solicitud ..."); 
	form.txtFecha.focus(); 
	}
	else if ( detalle == "" ) {
	alert("Falta ingresar el detalle de la solicitud ..."); 
	form.txtDetalle.focus(); 
	}
	else if ( fArchivo != "" && nombreArchivo == "" ) {		
	alert("Falta colocar el nombre del archivo ..."); 
	form.txtNombreArchivo.focus();
	}	
	else {
	var pregunta = confirm("Desea generar la solicitud con estos datos ingresados ..?"); 
		
		if ( pregunta == true ) {
		form.submit(); 
		}

	}

}
</script>

</head>
<body>
<div id="parteDetalle">
<h3>Generar Solicitud</h3>
<p>Ingrese los datos solicitados para generar su solicitud ....</p>

<form action="controlSolicitud.do?opcion=_registrarSolicitud" method="post" 
	enctype="multipart/form-data" name="formGenerarSolicitud" id="formGenerarSolicitud">
<fieldset>
<legend>Datos solicitud</legend>
<table width="100%" >
    <tr>
      <td width="15%">
      <label>Tipo de solicitud</label>
        <input type="hidden" name="codTipoSolic" id="hiddenField"   
       value="${requestScope.miTipoSolic.codigo}" />       </td>
      <td width="34%"><label>${requestScope.miTipoSolic.nombre}</label></td>
      <td width="10%"><label>Cargo</label></td>
      <td width="41%"><label>${sessionScope.sCargo.nombre}</label></td>
    </tr>    
    <tr>
      <td><label>Asunto</label></td>
      <td colspan="3"><span class="span-15 last">
        <input name="txtAsunto" type="text" size="60" maxlength="150" title="Asunto de la solicitud" />
      </span></td>
      </tr>
    <tr>
      <td><label>Fecha</label></td>
      <td colspan="3"><span class="span-15 last">
              <input name="txtFecha" type="text" value="${pageScope.miFecha.fechaHoraActual}" 
              size="24" readonly="true" title="Fecha de registro" />
      </span></td>
      </tr>
    <tr>
      <td><label>Detalle</label></td>
      <td colspan="3"><label><span class="span-15 last">
        <textarea name="txtDetalle" cols="50" rows="5" title="Detalle de la solicitud" >
		</textarea>
      </span></label></td>
      </tr>
      
      <c:if test="${requestScope.misFormatos != null}">      
    <tr>
      <td><label>Formularios</label></td>
      <td colspan="3"><p>Si su solicitud se ajusta a alguno de estos tr&aacute;mites, de doble clic en alguno de los siguientes formatos, complete los datos solicitados y envielos como archivos adjuntos ...</p></td>
      </tr>
      <tr>
      <td>&nbsp;</td>
      <td colspan="3"><select name="selectArchivoFormato" size="5" id="selectArchivoFormato">
        <c:forEach var="itemFormato" items="${requestScope.misFormatos}">
          <option value="${itemFormato.codFormato}"> ${itemFormato.codFormato} - ${itemFormato.nombre} </option>
        </c:forEach>
        <c:remove var="misFormatos" scope="session" />
      </select>
      <label>
	  <input type="button" name="btnSelectFormato" id="btnSelectFormato" value="Abrir"  class="botonSearch" />
              </label></td>
      </tr>
      </c:if>
  </table>
</fieldset>  

<p>&nbsp;</p>

<fieldset>
    <legend>Archivos adjuntos (Max. ${initParam["tamanioArchivos"]}Mb por archivo)</legend>
<table width="100%" border="1">
    <tr>
      <td colspan="3">Si necesita enviar mas de un archivo le recomendamos enviarlos todos en un archivo comprimido que puede ser ZIP o RAR ...</td>
      </tr>
    <tr>
      <td width="23%"><label>Nombre Archivo</label></td>
      <td width="77%" colspan="2"><span class="span-15 last">
        <input name="txtNombreArchivo" type="text" size="30" 
        	maxlength="150" title="Nombre descriptivo Archivo" />
      </span></td>
      </tr>
    <tr>
      <td><label>Archivos</label></td>
      <td colspan="2"><label><span class="span-15 last">
        <input name="fileArchivo" type="file" size="50" title="Archivo adjunto" onkeypress="return false" />
      </span></label></td>
      </tr>
  </table>
</fieldset>

<p>&nbsp;</p>

<table width="100%" border="1">    
    <tr>
      <td width="30%"><label>Adjuntos a la solicitud</label></td>
      <td width="70%" colspan="2"><span class="span-15 last">
        <textarea name="txtAdjuntos" cols="50" rows="5" title="Adjuntos a la solicitud"  >
		</textarea>
      </span></td>
      </tr>
    <tr>
      <td colspan="3"><label>
        <span class="span-18 last">
        <input name="chkVistoBueno" type="checkbox" id="chkVistoBueno" value="1" checked="checked" />
        </span> Visto Bueno Jefe Inmediato</label>
        <label></label></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><label></label>
        <label></label></td>
      <td><label><span class="span-18 last">
        <input name="btnGuardar" type="button" value="Guardar" id="btnGuardar" class="botonSave"  /> 
        &nbsp;&nbsp;&nbsp;
        <input name="btnCancelar" type="button" class="botonCancelar" id="btnCancelar" value="Cancelar" onclick="regresar();" />
      </span></label></td>
      <td>&nbsp;</td>
    </tr>
  </table>

</form>
</div>


        <div id="dialog-confirm" title="Generar Solicitud ..." class="mensajeDialogo">
            <p>
            <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
            Desea usted generar la solicitud ..?. 
            Si acepta, una nueva solicitud sera registrada y enviada para su atencion.  
            </p>
		</div>
        
        <div id="dialog-message" title="Generar Solicitud ..." class="mensajeDialogo">
        	<p></p>
        </div>

</body>
</html>