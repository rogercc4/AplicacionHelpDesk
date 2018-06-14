<%-- 
    Document   : detailTramite
    Created on : 29/09/2011, 01:28:36 PM
    Author     : rcontreras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <c:choose>
            <c:when test="${requestScope.miTramite != null}">

            <table width="630" border="0" cellspacing="0" class="detalleTramite">
              <tr>
                <td width="16%"><label class="titulo">Cod. Tramite</label></td>
                <td width="11%">${requestScope.miTramite.codTramite}</td>
                <td width="18%"><label class="titulo">Fecha registro:</label></td>
                <td width="55%">${requestScope.miTramite.fechaFormateada}</td>
              </tr>
              <tr>
                <td><label class="titulo">Origen</label></td>
                <td colspan="3">
                    <c:if test="${requestScope.miTramite.trabajadorOrigen != null}">
                    ${requestScope.miTramite.trabajadorOrigen.nombre} 
                    ${requestScope.miTramite.trabajadorOrigen.apellido}
                    </c:if>
                    <c:if test="${requestScope.miTramite.cargoOrigen != null}">
                    / [ ${requestScope.miTramite.cargoOrigen.nombre} ] 
                    </c:if>
                </td>
              </tr>
              <tr>
                <td><label class="titulo">Destino</label></td>
                <td colspan="3">
                    <c:if test="${requestScope.miTramite.trabajadorDestino != null}">
                    ${requestScope.miTramite.trabajadorDestino.nombre}
                    ${requestScope.miTramite.trabajadorDestino.apellido}
                    </c:if>
                    <c:if test="${requestScope.miTramite.cargoDestino != null}">
                    / [ ${requestScope.miTramite.cargoOrigen.nombre} ]
                    </c:if>                    
                </td>
              </tr>
              <tr>
                <td><label class="titulo">Tipo</label></td>
                <td colspan="3">${requestScope.miTramite.tipoTramite.nombre}</td>
              </tr>
              <tr>
                <td><label class="titulo">Detalle</label></td>
                <td colspan="3">${requestScope.miTramite.detalle}</td>
          </tr>

              <c:if test="${requestScope.miTramite.archivo != null}">
                  <tr>
                    <td><label class="titulo">Archivo</label></td>
                    <td colspan="3">
                        <img src="imagenes/download.png" alt="descarga" width="16" height="16" />
                        <a href="controlTramite.do?opcion=_verArchivo&codTramite=${requestScope.miTramite.codTramite}" target="_blank">
                        ${requestScope.miTramite.archivo.nombre}
                        </a>
                    </td>
                  </tr>
              </c:if>

                  <c:if test="${requestScope.miTramite.tareas != null}">
                      <tr>
                        <td><label class="titulo">Tareas</label></td>
                        <td colspan="3">
						<ol>
                        	<c:forEach items="${requestScope.miTramite.tareas}" var="miTarea">
                            <li>${miTarea.descripcion}</li>
                            </c:forEach>
						</ol>                                                        
                        </td>
                     </tr>
                  </c:if>
            
        </table>
            </c:when>
            <c:otherwise>
                <span>No existe este tramite</span>
            </c:otherwise>
        </c:choose>