<%-- 
    Document   : listadoSolicitudes
    Created on : 27/07/2011, 11:12:49 AM
    Author     : rcontreras
--%>
<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="nextPage" scope="page" value="${requestScope.miBuscador.pagina + 1}" />

    <c:choose>
        <c:when test="${requestScope.miBuscador.pagina > 1 }">
        <c:set var="prevPage" scope="page" value="${requestScope.miBuscador.pagina - 1}" />
        </c:when>
        <c:otherwise>
        <c:set var="prevPage" scope="page" value="1" />
        </c:otherwise>
    </c:choose>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Resultados de búsqueda</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<script type="text/javascript" src="lib_java/funciones.js"></script>
<script type="text/javascript">
	function busqReg(form, e) {
	var numRows = ${requestScope.miBuscador.numRegistros}; 
	var numPages = ${requestScope.miBuscador.numPaginas}; 
	var pageActual = Trim(form.txtPage.value); 
	
	tecla=(document.all) ? e.keyCode : e.which;
		
		if ( tecla == 13 ) {

			if ( pageActual == "" ) {
			alert("Falta especificar la pagina .."); 
			form.txtPage.focus(); 
			return false;
			}
			else if ( esNumero(pageActual) == false ) {
			alert("Indique un valor correcto para el numero de pagina .."); 
			form.txtPage.focus(); 
			return false; 
			}
			else if ( pageActual > numPages ) {
			alert( "El numero de pagina no puede ser mayor a " + numPages + " .." ); 
			form.txtPage.focus(); 
			return false; 
			}
			else if ( pageActual < 1 ) {
			alert( "El numero de pagina no puede ser menor a 1 ..." ); 
			form.txtPage.focus(); 
			return false; 
			}
			else {
			form.submit(); 
			}
		
		}
	
	}
	
	function irPagina ( pagina, form ) {
	pagina = Trim(pagina); 
	var numRows = ${requestScope.miBuscador.numRegistros}; 
	var numPages = ${requestScope.miBuscador.numPaginas}; 	
		
		if ( pagina == "" ) {
		alert("Falta especificar la pagina ..");
		}
		else if ( esNumero(pagina) == false ) {
		alert("Indique un valor correcto para el numero de pagina .."); 
		}
		else if ( pagina > numPages ) {
		alert( "El numero de pagina no puede ser mayor a " + numPages + " ..." ); 
		}
		else if ( pagina < 1 ) {
		alert( "Estamos en la pagina inicial" );
		}
		else {
		form.txtPage.value = pagina ; 
		form.submit(); 
		}
	
	}
	
	</script>
	
    </head>
	
    <body>    
    <div id="parteDetalle">
    <h3>${requestScope.tituloPagina}</h3>
    <p><strong>Número total de registros:</strong> ${requestScope.miBuscador.numRegistros}</p>
    <p><strong>Número total de Páginas:</strong> ${requestScope.miBuscador.numPaginas}</p>
    
    <c:choose>
        <c:when test="${requestScope.miBuscador.numRegistros > 0}">
<form action="controlTrabajador.do" method="get" name="formListaSolicitudes" id="formListaSolicitudes"  >
<input name="opcion" type="hidden" value="${requestScope.miOpcion}">
<table cellspacing="0" >
<thead>
<tr>
	<th width="8%">Codigo</th>
	<th width="32%">Asunto</th>
	<th width="14%">Fecha</th>
	<th width="38%">Tipo</th>
	<th width="8%">&nbsp;</th>
</tr>
</thead>

    <c:if test="${requestScope.miBuscador.numPaginas > 1 }">    
        <tfoot>
        <tr >
                <td width="8%"><strong>Página</strong></td>
                <td colspan="4">
                <label>
                <input type="button" name="btnFirst" id="btnFirst" value="|&lt;" onClick="irPagina(1, this.form);">
                </label>
                <label>
                <input type="button" name="btnPrevious" id="btnPrevious" value="&lt;&lt;" onClick="irPagina(${pageScope.prevPage}, this.form);">
                </label>
                <label>
                <input name="txtPage" type="text" id="txtPage" size="5" maxlength="5" 
                       value="${requestScope.miBuscador.pagina}" 
                  onKeyPress=" return busqReg(this.form, event); " >
                </label>
                <label>
                <input type="button" name="btnNext" id="btnNext" value="&gt;&gt;" alt="siguiente" onClick="irPagina(${pageScope.nextPage}, this.form);">
                </label>
                <label>
                <input type="button" name="btnLast" id="btnLast" value="&gt;|" onClick="irPagina(${requestScope.miBuscador.numPaginas}, this.form);">
                </label>
                <label>(Páginas: ${requestScope.miBuscador.numPaginas} | Registros: ${requestScope.miBuscador.numRegistros})</label>
                </td>
                </tr>
        </tfoot>
    </c:if>
        
<tbody>

    <c:forEach var="miSolicitud" items="${requestScope.misSolicitudes}" varStatus="estado" >

        <c:choose>
            <c:when test="${estado.count mod 2 == 0 }">
            <tr class="par">
            </c:when>
            <c:otherwise>
            <tr class="impar"  >
            </c:otherwise>
        </c:choose>

        <td width="8%">${miSolicitud.codSolicitud}</td>
            <td width="32%">${miSolicitud.asunto}</td>
            <td width="14%">${miSolicitud.fecha}</td>
            <td width="38%">${miSolicitud.tipoSolicitud.nombre}</td>
            <td width="8%"><a href="${requestScope.miUrlDetail}&codSolic=${miSolicitud.codSolicitud}&txtPage=${requestScope.miBuscador.pagina}" target="_self" ><span class="formatoDetail">ver</span></a></td>
        </tr>

    </c:forEach>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>          
        </table>
</tbody>
</form>        
        </c:when>
        <c:otherwise>
            <p>No se han encontrado solicitudes en esta categoria ...</p>
        </c:otherwise>
    </c:choose>
    
	</div>    
    
</body>
</html>