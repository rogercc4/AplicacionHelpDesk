<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<c:if test="${empty sessionScope.sTrabajador or empty sessionScope.sCargo }">
    <c:remove var="sTrabajador" />
    <c:remove var="sCargo" />    

    <c:set var="msgSistema" scope="request" value="Usted debe iniciar sesion" />
    <c:set var="msgTarget" scope="request" value="_parent" />
    <c:set var="msgurl" scope="request" value="index.jsp" />
    <jsp:forward page="mensajeSistema.jsp" />

    <%--
    <jsp:include page="mensajeSistema.jsp" />
    --%>    

</c:if>

