// JavaScript Document  
function datosCompletos() {
var resultado = true;
$('tr.ui-state-error').hide();

    $(':input').each( function(index, elemento) {

     var id = jQuery.trim($(elemento).attr("id"));
     var valor = jQuery.trim($(elemento).val());

        if ( id == "txtFechaInicio" || id == "txtFechaFin" ) {

            if ( valor == "" ) {                
            var titulo = jQuery.trim($(elemento).attr("title"));
            $('span.mensajeTrError').html("Falta completar: " + titulo);
            resultado = false ; 
            return false;
            }

        }

    });

    if ( resultado == false  ) {
    $('tr.ui-state-error').fadeIn('slow'); 
    }

return resultado ;

}

function mostrarResultadosBusqueda() {

$('div#resultadosBusqueda').empty();

    $.ajax({
        url: "controlBuscador.do", 
        type: "POST",
        data: {opcion: "_mostrarNumSolicitudesPorActivo",
               fechaInicio: $('input#txtFechaInicio').val(),
               fechaFin: $('input#txtFechaFin').val()},
        cache: false,
        global: false,
        beforeSend: function() {
        mostrarDialogoProgreso("Buscando ...");
        },
        success: function(data) {
        
        var existenDatos = true;
        var datos = { data: [] } ;

        $('div#dialogMensaje').dialog("close");

            $(data).find('respuestaOperacion').each(function (index, elemento){
            existenDatos = false;
            var mensaje = $(elemento).find('mensaje').text();
            mostrarMensajeDialogo("Mensaje", '<strong>' + mensaje + '</strong>');
            return false; 
            });


            if ( existenDatos == true ) {
            var codActivo = "" ;
            var nomActivo = "" ;
            var numSolic = "" ;

            datos.type = 'pie'; 

                $(data).find('activo').each(function (index, elemento){
                codActivo = $(elemento).find('codigo').text();
                nomActivo = $(elemento).find('descripcion').text();
                numSolic = $(elemento).find('numero').text();
                
                datos.data.push({name: nomActivo, y: parseFloat(numSolic)});

                //datos.data.push([nomActivo, numSolic]);
                
                }); 

            dibujarGrafico(datos);

            }

        },
        dataType: 'xml' 
    });

}

function mostrarDialogoProgreso(titulo) {

var htmlBarraProgreso = '<table width="100" ><tr>';
	htmlBarraProgreso += '<td style="vertical-align:middle; text-align:center">';
	htmlBarraProgreso += '<img src="imagenes/progressBar.gif">';
	htmlBarraProgreso += '</td></tr></table>';

 $('div#dialogMensaje').dialog({title: titulo, width: 300, height: 200, buttons: null});
 $('div#dialogMensaje').empty();
 $('div#dialogMensaje').html(htmlBarraProgreso);
 $('div#dialogMensaje').dialog("open");

}

function mostrarMensajeDialogo(titulo, mensaje) {

 $('div#dialogMensaje').dialog({title: titulo,
                                width: 250,
                                height: 150,
                                buttons: [
                                    {
                                        text: "OK",
                                        click: function() {$(this).dialog("close");}
                                    }
                                ]});

 $('div#dialogMensaje').empty();
 $('div#dialogMensaje').html(mensaje);
 $('div#dialogMensaje').dialog("open");

}

function dibujarGrafico(datos) {

    var chart;    
    var opciones = {
                    chart: {
                            renderTo: 'resultadosBusqueda',
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                    },
                    title: {
                            text: 'Clasificacion de Solicitudes por Activos'
                    },
                    tooltip: {
                            formatter: function() {
                                    return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
                            }
                    },
                    plotOptions: {
                            pie: {
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                            enabled: true,
                                            color: '#000000',
                                            connectorColor: '#000000',
                                            formatter: function() {
                                                    return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
                                            }
                                    }
                            }
                    },
                series: []
            };

    opciones.series.push(datos);

    chart = new Highcharts.Chart(opciones);

}

$(document).ready(function () { 
    $('input#txtFechaInicio').datepicker({changeMonth: true, changeYear: true});
    $('input#txtFechaFin').datepicker({changeMonth: true, changeYear: true});
    $('button#btnBuscar').button();
    $('div#tabsConsultarMisReq').tabs();
    $('tr.ui-state-error').hide();

    $('button#btnBuscar').click(function() {

        if ( datosCompletos() == true ) {
        mostrarResultadosBusqueda();
        }
    
    });

});