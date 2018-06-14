<%-- 
    Document   : frmAgregarTarea
    Created on : 26/08/2011, 04:43:55 PM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
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
		<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.datepicker.min.js" ></script>
		<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.datepicker-es.js"></script>		
		<script type="text/javascript" src="lib_java/gestionTareas.js"></script>        
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Gestionar Tareas</title>
		<script type="text/javascript">
		$(document).ready(function(){		
		
			$('button#btnRegresar').click(function() {
			$('div#dialoGestionTareas > p').html(""); 
			$('div#dialoGestionTareas > p').html("<strong>Desea regresar a la ventana anterior</strong>"); 
			
				$('div#dialoGestionTareas').dialog({buttons: [
							{
							text: "OK", 
							click: function() {
									document.location.href = "${requestScope.urlRetorno}"; 
									}
							}
				]}); 
			
			$('div#dialoGestionTareas').dialog("open"); 
			
			});
		
		$('div#listadoTareas').click( function(event) {
		var elemento = $(':radio[name|="rbtnTarea"]'); 
		
			if ( $(event.target).is(elemento) ) {
			var idFilaSelect = "tr" + $(event.target).val();			
				
				$('table#tableListadoTareas tr').each( function(index, elemento) {
				
					if ( $(elemento).attr("id") == idFilaSelect ) {
					$(elemento).addClass("ui-state-focus"); 
					}
					else {
					$(elemento).removeClass(); 
					}					
				
				});
				
			}
		
		}); 
		
		
		
		
		
		}); 	
		
		function cargarTareas() {
		var dirURL = "controlSolicitud.do?opcion=_mostrarListadoTareas&"; 
		dirURL += "codSolic=${requestScope.miSolicitud.codSolicitud}";
		//$('div#listadoTareas').empty().load(dirURL); 

			$.ajax({
			url: "controlSolicitud.do", 
			type: "GET", 
			data: {opcion: '_mostrarListadoTareas', codSolic: "${requestScope.miSolicitud.codSolicitud}"}, 
			global: false, 
			cache: false, 
			beforeSend: function() {		
						$('div#listadoTareas').append('<img src="imagenes/progressBar.gif">');
						},
			success: function(data) {
					 $('div#listadoTareas').empty();
					 $('div#listadoTareas').html(data);
					 }, 
			dataType: 'html' 
			});

		}
		</script> 
    </head>
    <body>
    <div id="parteDetalle">
    <h3>Gestionar tareas</h3>
    
    <div id="tabsGestionTareas">
            <ul >
                <li ><a href="#tabs-1">Datos Solicitud</a></li>
                <li ><a href="#tabs-2">Tareas Registradas</a></li>
            </ul>
        
        <div id="tabs-1">
        <jsp:include page="datosBasicosSolic.inc.jsp" />   
        </div>        
        
        <br/>
       
        <div id="tabs-2">
		
		<div id="listadoTareas">
		<c:choose>
                    <c:when test="${requestScope.miSolicitud.ultimoTramite != null}" >
                     <p>Seleccione la tarea que desea editar o eliminar </p>
					
					<form name="form1" method="post" action="">
					<table cellspacing="0" width="100%" id="tableListadoTareas" >
                                            
				<c:forEach var="miTarea" items="${requestScope.miSolicitud.ultimoTramite.tareas}" varStatus="contador">
				  <tr id="tr${miTarea.codigo}" >
					<td width="4%">${contador.count} .</td>
					<td width="92%">${miTarea.descripcion}</td>
					<td width="4%"><label>
					<input name="rbtnTarea" type="radio" value="${miTarea.codigo}">
					</label></td>
				  </tr>
                                  
                      </c:forEach>
				
					</table>
		        	</form>					
			</c:when>		
			<c:otherwise>
			<p>No se han agregado tareas hasta el momento</p>
			</c:otherwise>
		</c:choose>
		</div>
        
        </div>
    </div>
            <br/>
        <table width="100%" >
          <tr>
            <td>
            <button type="button" id="agregarTarea">
            <span>Agregar Tarea...</span>
            </button>
            
			&nbsp;&nbsp;
			<button type="button" id="btnEliminar">
			<span>Eliminar ...</span> 
			</button>
			
			&nbsp;&nbsp;
			<button type="button" id="btnEditar">
			<span>Editar ...</span>
			</button>
			
			&nbsp;&nbsp;
            <button type="button" id="btnMostrarDetalles">
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
	
    <div id="dialoGestionTareas" class="mensajeDialogo" title="Gestionar Tareas">
        <p></p>
    </div>
	
	<div id="dialoGestionAddTarea" class="mensajeDialogo" title="Agregar Tarea">
	<form name="frmAgregarTarea" id="frmAgregarTarea" method="post" action="">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15%">Descripcion</td>
              <td width="85%"><label>
                <input name="txtDescripcion" type="text" id="txtDescripcion" size="45">
              </label></td>
            </tr>
            <tr>
              <td>
			  <input name="hdCodSolic" type="hidden" 
			  	id="hdCodSolicitud" value="${requestScope.miSolicitud.codSolicitud}" >
                <input name="hdCodTarea" type="hidden" id="hdCodTarea" value="" >
                </td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>Fecha de inicio </td>
              <td><input name="txtFechaInicio" type="text" id="txtFechaInicio" size="30"> 
                <label></label>
              (dd/mm/yyyy HH:MM AM/PM) </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>Fecha de fin </td>
              <td><input name="txtFechaFin" type="text" id="txtFechaFin" size="30">
              (dd/mm/yyyy HH:MM AM/PM) </td>
            </tr>            
            <tr id="mensajeError" class="ui-state-error">
              <td colspan="2"></td>
            </tr>
      </table>
      </form>
	</div>
	
	<div id="dlgMostrarDetalleTarea" class="mensajeDialogo" title="Detalle Tarea">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><strong>Codigo</strong></td>
              <td colspan="3">
			  <label id="lblCodigoTarea"></label>
			  </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td width="15%"><strong>Descripcion</strong></td>
              <td colspan="3"><label id="lblDescripcionTarea"></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Fecha de inicio</strong> </td>
              <td width="30%"><label id="lblFechaInicio" ></label></td>
              <td width="11%"><strong>Fecha de fin</strong> </td>
              <td width="44%"><label id="lblFechaFin" ></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Registrado por</strong> </td>
              <td colspan="3"><label id="lblRegistradoPor" ></label></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td><strong>Cargo</strong></td>
              <td><label id="lblCargo" ></label></td>
              <td><strong>Area</strong></td>
              <td><label id="lblArea" ></label></td>
            </tr>
      </table>
	</div>	
        
    </body>
</html>
