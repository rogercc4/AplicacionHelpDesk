<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style >
body{
font-family:"Lucida Grande", "Lucida Sans Unicode", Verdana, Arial, Helvetica, sans-serif;
font-size:12px;
}
p, h1, form, button{border:0; margin:0; padding:0;}
.spacer{clear:both; height:1px;}
/* ----------- My Form ----------- */
.myform{
margin:0 auto;
width:450px;
padding:14px;
}


/* ----------- stylized ----------- */
#stylized{
border:solid 2px #b7ddf2;
background:#ebf4fb;
}
#stylized h1 {
font-size:14px;
font-weight:bold;
margin-bottom:8px;
}
#stylized p{
font-size:11px;
color:#666666;
margin-bottom:20px;
border-bottom:solid 1px #b7ddf2;
padding-bottom:10px;
}
#stylized label{
display:block;
font-weight:bold;
text-align:right;
width:140px;
float:left;
}
#stylized .small{
color:#666666;
display:block;
font-size:11px;
font-weight:normal;
text-align:right;
width:140px;
}
#stylized input{
float:left;
font-size:12px;
padding:4px 2px;
border:solid 1px #aacfe4;
width:200px;
margin:2px 0 20px 10px;
}
#stylized .boton{
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	color: #FFFFFF;
	background-color: #666666;
	padding: 10px;
	border: 1px solid #FFFFCC;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Iniciar Sesion</title>
<script type="text/javascript" src="lib_java/funciones.js"></script>
<script type="text/javascript">
function iniciarSesion(miForm) {
var usuario = Trim(miForm.txtUsuario.value); 
var clave = Trim(miForm.txtClave.value); 
	
	if ( usuario == "" ) {
	alert("Falta ingresar el usuario"); 
	}
	else if ( clave == "" ) {
	alert("Falta ingresar la clave de usuario"); 	
	}

miForm.submit(); 

}
</script>
</head>

<body >
<div id="stylized" class="myform">
    <form id="formLogin" name="formLogin" method="post" action="verificarLogin.do" target="_parent" >
<h1>Iniciar Sesion </h1>
<p>Ingrese su usuario y clave para entrar al sistema </p>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>
      <label>Usuario 
      <span class="small">Agrega tu usuario</span>      </label>	 </td>
      <td><input type="text" name="txtUsuario" id="txtUsuario" /></td>
    </tr>
    <tr>
      <td><label>Clave
<span class="small">Min. 6 caracteres</span></label></td>
      <td><input type="password" name="txtClave" id="txtClave" /></td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2">
      <input type="button" name="button" id="button" 
      		value="Ingresar" onclick="iniciarSesion(this.form) ; " class="boton" />      </td>
    </tr>
  </table>
<div class="spacer"></div>
</form>
</div>
</body>
</html>