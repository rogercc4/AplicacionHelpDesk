<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>MENSAJE SISTEMA</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<style type="text/css">
<!--
body {
background-color:#DBDBDB
/*background-color:#999999; */
/*	background-color: #666666;*/
}
-->
.cabecera {
	Position: relative;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	color:#FFFFFF; /*#2e3192;*/
	height: 25px;
	background-color: #006699;
}
.etiqueta {
	font: 12px Arial;
	color: #006699;
	text-align:justify;
	font-weight: bold;
}
a:link {
	font-family:Arial, Helvetica, sans-serif;
	font-size:10px;
	color: #666666;
	text-decoration: none;
}
a:visited {
	color: #666666;
	text-decoration: none;
}
a:hover {
	color: #FF9900;
	text-decoration: none;
}
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" align="center"><table width="400" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">

      <tr>
        <td align="center" class="cabecera">MENSAJE DEL SISTEMA</td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td colspan="2" class="etiqueta">
              ${requestScope.msgSistema}
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td colspan="2" align="center">                 
                <a href="${requestScope.msgurl}" target="${requestScope.msgTarget}" >
              :: REGRESAR ::
              </a>
              </td>
              </tr>
        </table></td>
      </tr>

      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>


</table>
</body>
</html>