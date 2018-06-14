<%--
    Document   : detailSolicitud.inc
    Created on : 30/07/2011, 06:32:48 PM
    Author     : Roger
--%>

<jsp:include page="verificarSesion.inc.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="tabsDetalleSolicitud">
	<ul >
        <li ><a href="#tabs-1">Datos Solicitud</a></li>
    	<li ><a href="#tabs-2">Datos Origen</a></li>
        <li ><a href="#tabs-3">Archivos Adjuntos</a></li>
        <li ><a href="#tabs-4">Tramites de solicitud</a></li>
        </ul>

    <div id="tabs-1">
            <fieldset>
                <legend>Datos de la solicitud</legend>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="15%"><strong>Codigo</strong></td>
            <td width="39%">${requestScope.miSolicitud.codSolicitud}</td>
            <td width="8%"><strong>Tipo</strong></td>
            <td width="38%">${requestScope.miSolicitud.tipoSolicitud.nombre}</td>
          </tr>
          <tr>
            <td><strong>Asunto</strong></td>
            <td colspan="3">${requestScope.miSolicitud.asunto}</td>
            </tr>
          <tr>
            <td><strong>Fecha</strong></td>
            <td>${requestScope.miSolicitud.fecha}</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><strong>Detalle</strong></td>
            <td colspan="3">${requestScope.miSolicitud.detalle}</td>
            </tr>
            <c:if test="${requestScope.miSolicitud.adjuntos != null}">
            <tr>
                <td><strong>Adjuntos</strong></td>
                <td colspan="3">${requestScope.miSolicitud.adjuntos}</td>
            </tr>
            </c:if>
        </table>
        </fieldset>
    </div>

    <div id="tabs-2">
            <fieldset>
                <legend>Datos de la persona que genero la solicitud</legend>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="16%"><strong>Generado por</strong></td>
            <td colspan="3">
            ${requestScope.miSolicitud.trabajador.nombre} ${requestScope.miSolicitud.trabajador.apellido}
            </td>
            </tr>
          <tr>
            <td><strong>Area</strong></td>
            <td width="42%">${requestScope.miSolicitud.cargo.area.nombre}</td>
            <td width="8%"><strong>Cargo</strong></td>
            <td width="34%">${requestScope.miSolicitud.cargo.nombre}</td>
          </tr>
        </table>
        </fieldset>
    </div>

    <div id="tabs-3">
        <c:choose>
        <c:when test="${requestScope.miSolicitud.archivos != null}">
        <fieldset>
                <legend>Archivos adjuntos</legend>
                <p>De clic en los siguientes archivos para tener mas detalle de lo solicitado ...</p>
                <ul>
                    <c:forEach items="${requestScope.miSolicitud.archivos}" var="miArchivo"  >
                    <li>
                    <a href="controlArchivo.do?opcion=_verArchivo&codArchivo=${miArchivo.codArchivo}" target="_blank">
                    ${miArchivo.nombre}
                    </a>
                    </li>
                </c:forEach>
                </ul>

        </fieldset>
        </c:when>
        <c:otherwise>
            <p><strong>Esta solicitud no tiene archivos adjuntos ...</strong></p>
        </c:otherwise>
        </c:choose>
    </div>

    <div id="tabs-4">
        <c:choose>
            <c:when test="${requestScope.miSolicitud.tramites != null}">
                <fieldset>
                        <legend>Tramite solicitud </legend>
                        <p>Tramites por los que la solicitud ha pasado ...</p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <thead>
                          <tr>
                            <th width="11%">N°</th>
                            <th width="30%">Origen</th>
                            <th width="34%">Destino</th>
                            <th width="19%">Estado</th>
                            <th width="6%">&nbsp;</th>
                          </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="miTramite" items="${requestScope.miSolicitud.tramites}" varStatus="contador" >
                                      <tr>
                                        <td>${contador.count}</td>
                                        <td>
                                        <strong>${miTramite.cargoOrigen.nombre}</strong>
                                        <c:if test="${miTramite.trabajadorOrigen != null}">
                                        <strong>: <br/></strong>
                                        ${miTramite.trabajadorOrigen.nombre}&nbsp;${miTramite.trabajadorOrigen.apellido}
                                        </c:if>
                                        </td>
                                        <td>
                                        <strong>${miTramite.cargoDestino.nombre}</strong>
                                        <c:if test="${miTramite.trabajadorDestino != null}">
                                        <strong>: <br/></strong>
                                        ${miTramite.trabajadorDestino.nombre}&nbsp;${miTramite.trabajadorDestino.apellido}
                                        </c:if>
                                        </td>
                                        <td>${miTramite.tipoTramite.nombre}</td>
                                        <td>
                                        <c:if test="${sessionScope.sTrabajador.usuario == miTramite.usuarioOrigen or
                                                      sessionScope.sTrabajador.usuario == miTramite.usuarioDestino}">
                                            <a href="${miTramite.codTramite}" name="lnkDetalleTramite" >
                                            ver
                                            </a>
                                        </c:if>
                                        </td>
                                     </tr>
                                    </c:forEach>

                  </tbody>
                </table>
                </fieldset>
            </c:when>
            <c:otherwise>
                <p>La solicitud no tiene tramites registrados</p>
            </c:otherwise>
        </c:choose>
    </div>

</div>