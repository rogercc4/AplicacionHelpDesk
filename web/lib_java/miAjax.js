function getXMLHTTPRequest(){
try {
req=new XMLHttpRequest();
}
	catch(err1){
		try{
			req= new ActiveXObject("Msxml2.XMLHTTP")
		}
		
		catch(err2){
			try{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			catch(err3){
				req=false;
			}
		}
	}
return req;
}

var miAjax=getXMLHTTPRequest();
/*##############################################################################*/
function LlamarCmbTrabajadores(form){
var codigo = form.select_area.value;
var aleatorio=Math.random();

var url="trabajadores.php?codArea=" + codigo + "&aleatorio=" + aleatorio;
miAjax.open("GET",url,true);
miAjax.onreadystatechange=MostrarCmbTrabajadores;
miAjax.send(null);
}
/*##############################################################################*/
function MostrarCmbTrabajadores(){
	
	if (miAjax.readyState==4){		
		if (miAjax.status==200){
		document.getElementById('cmbTrabajadores').innerHTML=miAjax.responseText;
		}
		else {
		alert ("Ha ocurrido un error " + miAjax.statusText);
		}
	}
	
	else {
	document.getElementById('cmbTrabajadores').innerHTML='<img src="../images/cargando_16_16.gif" width="16" height="16" />';
	}
}
/*##############################################################################*/
function LlamarTablaConsulta(form){
var codArea = form.select_area.value;
var codTrabajador = form.select_trabajador.value;
var codEstPapeleta = form.select_estado.value;
var fecha1 = form.txt_fecha1.value;
var fecha2 = form.txt_fecha2.value;
var url;
var pagina;
	
	if (form.select_pagina) {
	pagina = form.select_pagina.value;
	}
	else {
	pagina = 1;
	}

var aleatorio=Math.random();

url = "resultado_consulta.php?select_area=" + codArea + "&select_trabajador=" + codTrabajador ;
url = url + "&select_estado=" + codEstPapeleta + "&txt_fecha1=" + fecha1; 
url = url + "&txt_fecha2=" + fecha2 + "&select_pagina=" + pagina + "&aleatorio=" + aleatorio;

miAjax.open("GET",url,true);
miAjax.onreadystatechange=MostrarTablaConsulta;
miAjax.send(null);

}
/*##############################################################################*/
function MostrarTablaConsulta(){
	
	if (miAjax.readyState==4){		
		if (miAjax.status==200){
		document.getElementById('resultadoConsulta').innerHTML=miAjax.responseText;
		}
		else {
		alert ("Ha ocurrido un error " + miAjax.statusText);
		}
	}
	
	else {
	document.getElementById('resultadoConsulta').innerHTML='<table width="100%" border="0" cellspacing="0" cellpadding="0">        <tr><td colspan="2" align="center" valign="middle"><img src="../images/loading.gif" width="64" height="64" /></td></tr></table>';
	}
}
/*##############################################################################*/
function LlamarTblDetallePapeleta(form){
var codigo = form.txtCodPapeleta.value;
var aleatorio=Math.random();

var url="tbl_detalle_papeleta.php?codPapeleta=" + codigo + "&aleatorio=" + aleatorio;
miAjax.open("GET",url,true);
miAjax.onreadystatechange=MostrarTblDetallePapeleta;
miAjax.send(null);
}
/*##############################################################################*/
function MostrarTblDetallePapeleta(){
	
	if (miAjax.readyState==4){		
		if (miAjax.status==200){
		document.getElementById('detallePapeleta').innerHTML=miAjax.responseText;
		}
		else {
		alert ("Ha ocurrido un error " + miAjax.statusText);
		}
	}
	
	else {
	document.getElementById('detallePapeleta').innerHTML='<table width="100%" border="0" cellspacing="0" cellpadding="0">        <tr><td colspan="2" align="center" valign="middle"><img src="../images/loading2.gif" width="32" height="32" /></td></tr></table>';
	}
}
/*##############################################################################*/
function llamarTablaConsultaPersonal(form){
var txtBusqueda = form.txtNombre.value;	
var seleccionado;
var codArea = form.select_area.value;
var aleatorio=Math.random();
var filtrar;

		if (form.hdFiltrar) {
		filtrar = form.hdFiltrar.value;		
		}
		else {
		seleccionado = form.chkFiltrar.checked;
		 
			if ( seleccionado == true ) {
			filtrar = 0;
			}
			else {
			filtrar = 1;
			}
		
		}

var pagina;
	
	if (form.select_pagina) {
	pagina = form.select_pagina.value;
	}
	else {
	pagina = 1;
	}

var url="lista_trabajadores.php?txtBusqueda=" + txtBusqueda + "&filtrar=" + filtrar + "&pagina=" + pagina + "&codArea=" + codArea;


miAjax.open("GET",url,true);
miAjax.onreadystatechange=mostrarTablaConsultaPersonal;
miAjax.send(null);

}
/*##############################################################################*/
function mostrarTablaConsultaPersonal(form){

	if (miAjax.readyState==4){		
		if (miAjax.status==200){
		document.getElementById('resultadoConsulta').innerHTML=miAjax.responseText;
		}
		else {
		alert ("Ha ocurrido un error " + miAjax.statusText);
		}
	}
	
	else {
	document.getElementById('resultadoConsulta').innerHTML='<table width="100%" border="0" cellspacing="0" cellpadding="0">        <tr><td colspan="2" align="center" valign="middle"><img src="../images/loading.gif" width="64" height="64" /></td></tr></table>';
	}


}