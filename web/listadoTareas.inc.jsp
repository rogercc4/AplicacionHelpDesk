<%-- 
    Document   : listadoTareas.inc
    Created on : 11/09/2011, 01:22:49 AM
    Author     : Roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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