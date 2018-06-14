<%-- 
    Document   : resultadosBusqueda.inc
    Created on : 12/10/2011, 08:49:01 AM
    Author     : rcontreras
--%>
<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p><strong>Se han encontrado las siguientes solicitudes</strong></p>
<table width="100%" border="0" cellspacing="0" cellpadding="2">
<thead>
                          <tr>
                            <th width="11%">N°</th>
                            <th width="30%">Asunto</th>
                            <th width="34%">Fecha</th>
                            <th width="19%">Tipo</th>
                            <th width="6%">&nbsp;</th>
                          </tr>		
                        </thead>
                        <tbody>
                        <c:forEach var="miSolicitud" items="${requestScope.misSolicitudes}" varStatus="contador" >
                          <tr>
                            <td>${contador.count}</td>
                            <td>
                            ${miSolicitud.asunto}
                            </td>
                            <td>
                            ${miSolicitud.fechaFormateada}
                            </td>
                            <td>
                            ${miSolicitud.tipoSolicitud.nombre}
                            </td>
                            <td>
                                <a href="${miSolicitud.codSolicitud}" name="lnkDetalleSolicitud" >
                                ver
                                </a>
                            </td>
                         </tr>
                        </c:forEach>
                                     
                  </tbody>
                </table>