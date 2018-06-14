<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>SATCh - SERVICIOS EN LINEA</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<style type="text/css"> 
<!--
body {
	background-color:#006699;
}
-->
</style>
</head>
<body topmargin="0" leftmargin="0">
<div id="cabecera">
<p class="logo">SATCh - SERVICIO DE ADMINISTRACI&Oacute;N TRIBUTARIA DE CHICLAYO</p>
<p class="logo">Modulo de Sistema Help Desk</p>
<p class="usuario">
    Usuario:
    <c:if test="${sessionScope.sTrabajador != null}">
        <c:set var="nombreUsuario" value="${sessionScope.sTrabajador.nombre} ${sessionScope.sTrabajador.apellido}" />
    </c:if>
   <c:out value="${nombreUsuario}" default="Invitado" /> |  Fecha: 
   <jsp:useBean id="miFecha" class="helpdesk.web.Fecha" />
   <jsp:getProperty name="miFecha" property="fechaActual" />
</p>
</div>
</body>
</html>
