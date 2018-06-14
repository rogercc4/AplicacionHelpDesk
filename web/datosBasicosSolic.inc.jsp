<%-- 
    Document   : datosBasicosSolic.inc
    Created on : 03/08/2011, 08:55:23 PM
    Author     : Roger
--%>


<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<fieldset>
	<legend>Datos basicos solicitud</legend>
	<p>Informacion principal de la solicitud</p>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="13%"><strong>Codigo</strong></td>
        <td width="26%">${requestScope.miSolicitud.codSolicitud}</td>
        <td width="6%"><strong>Tipo</strong></td>
        <td width="26%">${requestScope.miSolicitud.tipoSolicitud.nombre}</td>
        <td width="6%"><strong>Fecha</strong></td>
        <td width="23%">${requestScope.miSolicitud.fecha}</td>
      </tr>
      <tr>
        <td><strong>Generado por</strong></td>
        <td colspan="5">
		${requestScope.miSolicitud.trabajador.nombre} ${requestScope.miSolicitud.trabajador.apellido} 
		</td>
      </tr>
      <tr>
        <td><strong>Area</strong></td>
        <td>${requestScope.miSolicitud.cargo.area.nombre}</td>
        <td><strong>Cargo</strong></td>
        <td colspan="3">${requestScope.miSolicitud.cargo.nombre}</td>
      </tr>
      <tr>
        <td><strong>Asunto</strong></td>
        <td colspan="5">${requestScope.miSolicitud.asunto}</td>
      </tr>
    </table>
	</fieldset>