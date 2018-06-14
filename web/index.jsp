<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:remove var="sTrabajador" />
<c:remove var="sCargo" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Iniciar Sesion</title>
</head>

<frameset rows="80,*" frameborder="no" border="0" framespacing="0">
  <frame src="cabecera.inc.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" />
  <frame src="login.jsp" name="mainFrame" id="mainFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>