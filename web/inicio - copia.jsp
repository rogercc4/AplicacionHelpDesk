<%-- 
    Document   : inicio
    Created on : 21/07/2011, 08:36:37 AM
    Author     : rcontreras
--%>
<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <style>
		body {
	background-color:#F3F3F3;
		}		
		div.tituloPendientes {
		background-color: #E5E5E5;
		font-family: Verdana, Arial, Helvetica, sans-serif;
		font-size: 14px;
		font-weight: bold;
		color: #003366;
		height: 20px;
		padding: 10px;
		vertical-align: text-top;
		display:block;
		background-image: url(imagenes/scheduledTasks.png);
		background-repeat: no-repeat;
		background-position: 15px;
		text-indent: 50px;
		border: 1px solid #666666;		
		}
		
		div.tituloRequerimientos {
		background-color: #E5E5E5;
		font-family: Verdana, Arial, Helvetica, sans-serif;
		font-size: 14px;
		font-weight: bold;
		color: #003366;
		height: 20px;
		padding: 10px;
		vertical-align: text-top;
		display:block;
		background-image: url(imagenes/icoSend.gif);
		background-repeat: no-repeat;
		background-position: 15px;
		text-indent: 50px;
		border: 1px solid #666666;
		}	
		
		div.detalle {
		height: 180px;
		border-bottom-width: 1px;
		border-bottom-style: solid;
		border-bottom-color: #666666;
		background-color: #FFFFFF;
		}
		div.detalle img {
		vertical-align: middle;
		}
		
		div.categoria {
		font-family: Georgia, "Times New Roman", Times, serif;
		font-size: 16px;
		color: #333333;
		width: 33%;
		padding: 10px;
		float: left;		
		}
		
		.fondo1 {
		color: #003366;
		background-color: #FFFFCC;
		font-weight: bold;
		cursor: pointer;
		}
		</style>

<script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.sortable.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {			
		
		$('div.categoria').hover(function() {
			$(this).toggleClass('fondo1');
		});
		
		$('div#itemsOrdenar').sortable({
			placeholder: "ui-state-highlight"
		});
		$( "div#itemsOrdenar" ).disableSelection();
		
	}); 
	</script>
            
    </head>
    <body>
    
    <div id="itemsOrdenar">
    
	<div>
    <div class="tituloRequerimientos">
    Mis Requerimientos
    </div>
    <div class="detalle">
	<div class="categoria">
    <img src="imagenes/fileOpen.png" width="32" height="32" />
    Solicitudes Registradas: ${requestScope.solicitudes.registradas}    </div>
    <div class="categoria">
    <img src="imagenes/applicationFormEdit.png" width="32" height="32" />
    En proceso de atenci&oacute;n: ${requestScope.solicitudes.requerimientosEnProcesoAtencion}</div>
    <div class="categoria">
    <img src="imagenes/iconAssign.gif" width="32" height="32" />
    Asignadas: ${requestScope.solicitudes.asignadas}        </div>
    <div class="categoria">
    <img src="imagenes/check.png" width="32" height="32" />
    Para dar conformidad: ${requestScope.solicitudes.enviarParaConformidad}    </div>    
    <div class="categoria">
    <img src="imagenes/returnTrans.png" width="32" height="32" />
    Devueltas: ${requestScope.solicitudes.devueltas}        </div>    
    <div class="categoria">
    <img src="imagenes/cancel.png" width="32" height="32" />
    Rechazadas: ${requestScope.solicitudes.rechazadas}</div>            
  	</div>    
	</div>    
    <br/>
    <div>
    <div class="tituloPendientes">
    Mis Pendientes</div>
    <div class="detalle">
	<div class="categoria">
    <img src="imagenes/verify.gif" width="32" height="32" />
    Dar Visto Bueno: ${requestScope.solicitudes.darVistoBueno}    </div>
    <div class="categoria">
    <img src="imagenes/hyperPapers.png" width="32" height="32" />
    Para Atender: ${requestScope.solicitudes.paraAtender}    </div>
    <div class="categoria">
    <img src="imagenes/iconAssign.gif" width="32" height="32" />
    Derivadas: ${requestScope.solicitudes.derivadas}   </div>
    <div class="categoria">
    <img src="imagenes/applicationFormEdit.png" width="32" height="32" />    En proceso de Atenci&oacute;n: ${requestScope.solicitudes.pendientesEnProcesoAtencion}    </div>    
    <div class="categoria">
    <img src="imagenes/check.png" alt="" width="32" height="32" />    Enviar para conformidad: ${requestScope.solicitudes.enviarParaConformidad}    </div>    
    </div>
</div>
    
    
    
    </div>
    
</body>
</html>
