<%-- 
    Document   : formAtenderSolic
    Created on : 07/08/2011, 11:51:36 PM
    Author     : Roger
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
        <script type="text/javascript" src="lib_java/funciones.js"></script>        
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
		var $controlEntrada ; 
			
			$( "#dialog:ui-dialog" ).dialog( "destroy" );
			
			$('#dialog-confirm').dialog({
			autoOpen: false, 
			height: 170, 
			modal: true, 
			resizable: false 			
			}); 
			
			$(':button[name|="btnGuardar"]').click(function() {
			var valoresIncompletos = false;	
				
				$(':input').each(function(index, elemento){
				$controlEntrada = $(elemento); 
				nombreControl = jQuery.trim($controlEntrada.attr('name')); 
				valorControl =  jQuery.trim($controlEntrada.val()); 
					
					if ( nombreControl == "selectActivo" || 
						 nombreControl == "selectCategoria" ||
						 nombreControl == "selectSubCategoria" || 
						 nombreControl == "selectPrioridad" || 
						 nombreControl == "txtFecha" ) {
					
						if (valorControl == "") {
						valoresIncompletos = true; 
						return false; 
						}
						 
					}
				
				}); 
				
				
				if ( valoresIncompletos == true ) {
				$('#dialog-confirm > p').html(""); 
				$('#dialog-confirm > p').html("<strong>Falta completar: " +  
												$controlEntrada.attr('title') + 
												"</strong>"); 
				
				$('#dialog-confirm').dialog({buttons: [
						{
						text: "OK", 
						click: function() {
								$(this).dialog('close'); 
								}
						}
				]}); 
				
				$('#dialog-confirm').dialog('open'); 
																
				}
				else {
				$('#dialog-confirm > p').html(""); 
				$('#dialog-confirm > p').html("<strong>Desea atender la solicitud</strong>"); 
				
				$('#dialog-confirm').dialog({buttons: [
						{
						text: "Atender", 
						click: function() {
								$('#formAtenderSolic').submit(); 
								}
						}, 
						{
						text: "Cancelar", 
						click: function() {
								$(this).dialog('close');
								}
						}
						
				]});
				
				$('#dialog-confirm').dialog('open');  
				
				}
				
				
			
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
			var urlRegresar = "controlSolicitud.do?opcion=_detalleSolicPorAtender"; 
				urlRegresar = urlRegresar + "&codSolic=" + codSolic + "&txtPage=" + numPage; 
			document.location.href = urlRegresar ;
			}
		
		
		}
		
		function enviarVistoBueno(form) {
		var miFecha = Trim(form.txtFecha.value) ;
                var codActivo = Trim(form.selectActivo.value);
		var codCateg = Trim(form.selectCategoria.value);
		var codSubCateg = Trim(form.selectSubCategoria.value);
		var codPrioridad = Trim(form.selectPrioridad.value);
		var codSolic = Trim(form.hdCodSolic.value);
			
			if ( miFecha == "" ) {
			alert("Falta Ingresar la fecha");
			}
                        else if (codActivo == ""){
			alert("Falta Seleccionar un Activo de la lista");
                        }
			else if ( codCateg == "" ) {
			alert("Falta Seleccionar una Categoria");
			}
			else if ( codSubCateg == "" ) {
			alert("Falta Seleccionar una Sub-Categoria");
			}
			else if ( codPrioridad == "" ) {
			alert("Falta asignar una Prioridad para la Atención");
			}
			else if ( codSolic == "" ) {
			alert("Falta especificar la solicitud ...");
			}
			else if ( esNumero(codSolic) == false ) {
			alert("Especifique un codigo de solicitud correcto ...");
			}
			else {
			var respuesta = confirm("Desea atender la solicitud ...?");
				
				if ( respuesta == true ) {
				form.submit(); 
				}

			}
			
				
		}
		</script>
    </head>
    <body>
<div id="parteDetalle">
		<h3>Atender Solicitud </h3>
                <jsp:include page="datosBasicosSolic.inc.jsp" />    
				<br/>
         <jsp:useBean class="helpdesk.web.Fecha" id="miFecha" scope="page" />
		<form action="controlTrabajador.do" method="post" name="formAtenderSolic" id="formAtenderSolic">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Activo</strong></td>
              <td colspan="2"><label>
                <select name="selectActivo" id="selectActivo" title="Activo">
                    <option value="" selected="selected">
                        Seleccione Activo ...&nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="1">
                        Datos/Información &nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="2">
                        Servicios &nbsp;&nbsp;&nbsp;
                    </option>
                </select>
              </label></td>
            </tr>
            <tr>
              <td><strong>Categoría</strong></td>
              <td colspan="2">
              <select name="selectCategoria" id="selectCategoria" title="Categoria" >
                    <option value="" selected="selected">
                        Seleccione Categoria ...&nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="1">
                        BD Externas &nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="2">
                        BD Intranet &nbsp;&nbsp;&nbsp;
                    </option>
              </select></td>
            </tr>          
            <tr>
              <td><strong>Sub. Categoría </strong></td>
              <td colspan="2">
                  <select name="selectSubCategoria" id="selectSubCategoria" title="Sub-Categoria de la Solicitud">
                    <option value="" selected="selected">
                        Seleccione SubCategoria ...&nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="1">
                        Actualización de Datos &nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="2">
                        Proceso &nbsp;&nbsp;&nbsp;
                    </option>
                  </select></td>
            </tr>
            <tr>
              <td><strong>Prioridad</strong></td>
              <td colspan="2">
                  <select name="selectPrioridad" id="selectPrioridad" title="Prioridad de la solicitud">
                    <option value="" selected="selected">
                        Seleccione Prioridad ...&nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="1">
                        Alta &nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="2">
                        Media &nbsp;&nbsp;&nbsp;
                    </option>
                    <option value="3">
                        Baja &nbsp;&nbsp;&nbsp;
                    </option>
                  </select></td>
            </tr>
            <tr>
              <td width="14%"><strong>Fecha</strong></td>
              <td colspan="2"><label>
                <input name="txtFecha" type="text" id="txtFecha" value="${pageScope.miFecha.fechaHoraActual}" 
				size="30" readonly="true" title="Fecha de atencion">	
                <input type="hidden" name="opcion" id="opcion" value="_atenderSolicitud">
              </label>              </td>
            </tr>            
            <tr>
              <td>&nbsp;</td>
              <td width="44%">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>
              <input type="hidden" name="hdCodSolic" id="hdCodSolic" 
		              value="${requestScope.miSolicitud.codSolicitud}" title="Codigo de la solicitud">
              <input type="hidden" name="hdNumPage" 
              		id="hdNumPage" value="${requestScope.page}">			  </td>
              <td>&nbsp;</td>
              <td width="42%"><label>
                <input type="button" name="btnGuardar" value="Guardar" 
                	class="botonSave" >
                &nbsp;&nbsp;&nbsp;
                </label>
                <label>
                <input type="button" name="btnCancelar" value="Regresar" 
                		class="botonRegresar" onClick="regresar(this.form);" >
              </label>              </td>
            </tr>
          </table>
		
		</form>
		<br/>
		</div>
		
		<div id="dialog-confirm" class="mensajeDialogo" title="Atender Solicitud">
		<p></p>
		</div>
		
    </body>
</html>
