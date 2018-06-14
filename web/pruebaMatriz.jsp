<%--
    Document   : pruebaMatriz
    Created on : 18/07/2011, 10:06:31 AM
    Author     : rcontreras
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Prueba de JSTL: </h1>
        <c:forEach var="moviesList" items="${movies}" >
            <c:forEach var = "itemLista" items="${moviesList}" varStatus="contador" step="3" >
            ${itemLista}  - Item: ${contador.count} - Primero: ${contador.first} <br/>
            </c:forEach>
        </c:forEach>

        <br/><br/><br/>

        <c:set target="${requestScope.tramite}" 
                    property="nombre" value="Nuevo archivo"  />
        
       Valor de la propiedad: ${tramite.nombre}

       <c:set var="last" value="Hidden Cursor" />
       <c:set var="first" value="Crouching Pixels" />

       <c:url value="/inputComments.jsp" var="inputURL" >
            <c:param name="firstName" value="${first}" />
            <c:param name="lastName" value="${last}" />
       </c:url>

       ${inputURL}

    </body>
</html>
