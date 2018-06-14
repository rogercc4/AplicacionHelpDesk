<jsp:include page="verificarSesion.inc.jsp"></jsp:include>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin t&iacute;tulo</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<script type="text/javascript" src="lib_java/funciones.js" ></script>
<script type="text/javascript">
function validarSeleccion(form) {
	
	if ( botonSeleccionado( form.btnSelectTipoSolic ) == false ) {
	alert('Debe seleccionar un tipo de solicitud ...'); 
	}
	else  {
	form.submit(); 
	}
	
}
</script>
</head>

<body>
<div id="parteDetalle">
<h3>Seleccionar Tipo de Solicitud</h3>
<p>Antes de generar la solicitud, es necesario que escoja un tipo de solicitud:</p>
<form action="controlSolicitud.do" method="get" name="formSelectCargo" target="_self">
<div class="container span-18">
	<c:forEach var="itemTipoSolic" items="${sessionScope.misTiposSolicitud}">
	    <div class="span-18 last">	
    	<input name="btnSelectTipoSolic" type="radio" value="${itemTipoSolic.codigo}" />
	    <label>${itemTipoSolic.nombre}</label>
    	</div>        
        <div class="span-18 last">
		<p>${itemTipoSolic.descripcion}</p>
        </div>
        </c:forEach>
        <c:remove var="misTiposSolicitud" scope="session" />
    <div class="span-18 last">
      <input name="btnGrabar" type="button" value="Grabar" class="botonSave" onclick="validarSeleccion(this.form); " />
      <input type="hidden" name="opcion" id="hiddenField" value="_seleccionarTipoSolicitud" />    
    </div>
    <br/><br/><br/>
</div>
</form>
</div>
</body>
</html>