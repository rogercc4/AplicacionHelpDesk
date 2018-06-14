<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>SATCh - SERVICIOS EN LINEA</title>
<style type="text/css">
body {
	background-color: #E2E2E2;
}
#accordionMenu {
	font-size: 12px;
}

p.optionMenu {
	margin-left: 11px;
}

p.optionMenu a {
	color: #003366;
	font-weight: bold;
	text-decoration: none;	
}
</style>
<link rel="stylesheet" type="text/css" 
	href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css" />
<script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.sortable.min.js"></script>
<script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.accordion.min.js"></script>
<script type="text/javascript">
	$(function() {
		var stop = false;
		$( "#accordionMenu h3" ).click(function( event ) {
			if ( stop ) {
				event.stopImmediatePropagation();
				event.preventDefault();
				stop = false;
			}
		});
		$( "#accordionMenu" )
			.accordion({
				header: "> div > h3"
			})
			.sortable({
				axis: "y",
				handle: "h3",
				stop: function() {
					stop = true;
				}
			});
			
			$('p.optionMenu > a').hover(
				function() {
				$(this).css('color', 'red'); 
				}, 
				function() {
				$(this).css('color', '#003366'); 
				}
			);
			
	});
</script>

</head>
<body >
<div id="accordionMenu">
	<div>
    	<h3><a href="#">Inicio</a></h3>
        <div>
        <p class="optionMenu"><a href="controlTrabajador.do?opcion=_verPaginaInicio" target="mainFrame">Pagina de Inicio</a></p>
        <p class="optionMenu"><a href="formCambiarClave.jsp" target="mainFrame">Cambiar Clave</a></p>
        <p class="optionMenu"><a href="informacionUsuario.jsp" target="mainFrame">Informacion de usuario</a></p>
        <p class="optionMenu"><a href="index.jsp" target="_parent">Cerrar Sesion</a></p>
        </div>
        <h3><a href="#">Mis requerimientos</a></h3>
        <div>
            <p class="optionMenu"><a href="controlSolicitud.do?opcion=_verSolicitudes" target="mainFrame">Generar Solicitud</a></p>
            <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicReg" target="mainFrame">Solicitudes Registradas</a></p>
            <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicProcAtencion" target="mainFrame">En proceso de atencion</a></p>
            <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicAsignadas" target="mainFrame">Asignadas</a></p>
            <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicDarConformidad" target="mainFrame">Para dar conformidad</a></p>
          <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicDevueltas" target="mainFrame">Devueltas</a></p>
            <p class="optionMenu"><a href="controlTrabajador.do?opcion=_misReqVerSolicRechazadas" target="mainFrame">Rechazadas</a></p>
        </div>
  </div>
    <div>
	<h3><a href="#">Mis pendientes</a></h3>
	<div>
    	<p class="optionMenu">
            <a href="controlTrabajador.do?opcion=_verSolicAtenVB" target="mainFrame">
            Dar visto bueno            </a>        </p>
        
        <p class="optionMenu">
            <a href="controlTrabajador.do?opcion=_verSolicPorAtender" target="mainFrame">
            Para Atender            </a>        </p>
        
        <p class="optionMenu">
            <a href="controlTrabajador.do?opcion=_verSolicDerivadas" target="mainFrame">
            Derivadas</a></p>
            
         <p class="optionMenu">
            <a href="controlTrabajador.do?opcion=_verSolicProcAtencion" target="mainFrame">
            En proceso de atencion</a></p>   
         
        
  		<p class="optionMenu">
        	<a href="controlTrabajador.do?opcion=_verSolicEnvConformidad" target="mainFrame">
            Enviar para conformidad </a></p>
        
	</div>
    
  </div>
    <div>
	<h3><a href="#">Reportes</a></h3>
	<div>
    	<p class="optionMenu">
            <a href="controlBuscador.do?opcion=_mostrarFormBusqMisReq" target="mainFrame">
            Consultar mis requerimientos
            </a>
		</p>
        
        <p class="optionMenu">
            <a href="formBuscarResumenCategoria.jsp" target="mainFrame">
            Resumen grafico
            </a>
		</p>
				
	</div>
  </div>
</div>
</body>
</html>