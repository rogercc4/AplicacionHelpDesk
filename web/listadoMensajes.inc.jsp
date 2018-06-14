<%-- 
    Document   : listadoMensajes.inc
    Created on : 26/09/2011, 09:46:59 AM
    Author     : rcontreras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
    <c:when test="${requestScope.miSolicitud.ultimoTramite != null}" >
	<form id="formMensajes" name="formMensajes" method="post" action="">    
    <table width="100%" cellspacing="0" class="tblMensajes" id="tableMensajes" >
    <thead>
    <tr>
    <th width="8%">N&ordm;</th>
    <th width="12%">Origen</th>
    <th width="27%">Fecha</th>
    <th width="50%">Asunto</th>
    <th width="3%">&nbsp;</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="miMensaje" items="${requestScope.miSolicitud.ultimoTramite.mensajes}" varStatus="contador">
    <tr id="tr${miMensaje.codigo}" >
    <td>${contador.count} .</td>
    <td>${miMensaje.usuarioOrigen}</td>
    <td>${miMensaje.fecRegistroFormateada}</td>
    <td>${miMensaje.asunto}</td>
    <td><label>
    <input name="rbtnMensaje" type="radio" value="${miMensaje.codigo}" />
    </label></td>
	</tr>
    </c:forEach>
    </tbody>
    </table>
    </form>
    </c:when>
    <c:otherwise>
        <p>No hay mensajes ... </p>
    </c:otherwise>    
</c:choose>
