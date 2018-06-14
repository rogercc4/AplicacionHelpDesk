<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<c:if test="${empty sessionScope.sTrabajador}">
    <c:remove var="sTrabajador" />
    <c:remove var="sCargo" />
    <jsp:forward page="index.jsp" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Consultas en Linea - Proveedores</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<script language="javascript" src="lib_java/funciones.js" ></script>
<script type="text/javascript">
function enviarCargo(form) {
var codCargo = Trim(form.selectCargo.value) ;

    if ( codCargo == "" ) {
    alert("Usted debe seleccionar un cargo"); 
    }
    else {
    form.submit();
    }
    
}
</script>
</head>
<body>
<div id="parteDetalle" >
<h3>Seleccionar el cargo</h3>

<h4>Instrucciones:</h4>
<ul>
  <li>Seleccione el cargo con el que desea ingresar al sistema, luego de clic en &quot;Ingresar&quot;</li>
  </ul>
<form action="verificarLogin.do" method="post" name="form1" target="_parent">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><label><span><strong>Nombre Trabajador</strong></span></label></td>
      <td width="85%">
      <label><span>
          ${sessionScope.sTrabajador.nombre} ${sessionScope.sTrabajador.apellido}
          </span></label>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
        <td width="15%"><label><span><strong>Cargo</strong></span></label></td>
      <td>
      <label>
        <select name="selectCargo" id="selectCargo">
            <option value="" selected="selected">
                    Seleccione cargo .... &nbsp;&nbsp;&nbsp;    
            </option>
            <c:forEach var="itemCargo" items="${requestScope.cargosLogin}">
                
                <option value="${itemCargo.codCargo}" >
                    ${itemCargo.nombre} &nbsp;&nbsp;&nbsp;
                </option>
            </c:forEach>
        </select>
      </label>
          <input type="button" name="button" id="button"
           value="Ingresar" class="botonSave" onClick="enviarCargo(this.form);" />      </td>
      </tr>
  </table>
  </form>
</div>
</body>
</html>