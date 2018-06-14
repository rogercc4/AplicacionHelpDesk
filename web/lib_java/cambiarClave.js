// JavaScript Document        
function mostrarDialogBarraProgreso( titulo ) {

var htmlBarraProgreso = '<table width="100" ><tr>';
htmlBarraProgreso += '<td style="vertical-align:middle; text-align:center">';
htmlBarraProgreso += '<img src="imagenes/progressBar.gif">';
htmlBarraProgreso += '</td></tr></table>';

$('div#dialogProgreso').html("");
$('div#dialogProgreso').html(htmlBarraProgreso);
$('div#dialogProgreso').dialog({buttons: null});
$('div#dialogProgreso').dialog({title: titulo});
$('div#dialogProgreso').dialog("open");

}

function restablecerAparienciaFormulario() {
$('td#tdMensajeSistema').hide();
$(':input').each(function(index, elemento) { 
    if ( jQuery.trim($(elemento).attr("type")) == "text" ||
                     jQuery.trim($(elemento).attr("type")) == "password" ) {

            $(elemento).removeClass();

    }
});

}

function limpiarFormulario() {

$('input#txtClaveAnt').attr("value", "");
$('input#txtClaveNueva1').attr("value", "");
$('input#txtClaveNueva2').attr("value", "");

}


$(document).ready(function(){

        $('div#tabsDetalleSolicitud').tabs();

        $('div#dialogProgreso').dialog({
        autoOpen: false,
        resizable: false,
        width: 300,
        height: 200,
        modal: true
        });

        $('td#tdMensajeSistema').hide();

        $('div#dialogVentana').dialog({
        autoOpen: false,
        resizable: false,
        modal: true
        });

        $('button#btnCambiarClave').click(function() {
        var faltaCompletarDatos = false ;

        restablecerAparienciaFormulario();

                $(':input').each(function(index, elemento) {
                var mensajeHTML = "" ;

                    if ( jQuery.trim($(elemento).attr("type")) == "text" ||
                             jQuery.trim($(elemento).attr("type")) == "password" ) {

                            if ( jQuery.trim($(elemento).val()) == "" ) {
                                    mensajeHTML = '<strong>';
                                    mensajeHTML += 'Falta completar el campo: ' ;
                                    mensajeHTML += $(elemento).attr('title') ;
                                    mensajeHTML += '</strong>';

                                    $('div.ui-state-error').html( mensajeHTML );
                                    $('td#tdMensajeSistema').hide();
                                    $('td#tdMensajeSistema').show('slow');

                                    $(elemento).addClass('textError');
                                    $(elemento).focus();

                                    faltaCompletarDatos = true ;
                                    return false;
                            }

                    }

                });

                if ( faltaCompletarDatos == false ) {

                    if ( jQuery.trim($('input#txtClaveNueva1').val()) !=
                         jQuery.trim($('input#txtClaveNueva2').val()) ) {
                    $('div.ui-state-error').html( '<strong>El valor no coincide con la nueva clave que desea tener ...</strong>' );

                    $('input#txtClaveNueva1').attr("value", "");
                    $('input#txtClaveNueva2').attr("value", "");
                    $('input#txtClaveNueva1').addClass('textError');
                    $('input#txtClaveNueva1').focus();
                    $('td#tdMensajeSistema').hide();
                    $('td#tdMensajeSistema').show('slow');
                    faltaCompletarDatos = true ;
                    }

                }

                if (  faltaCompletarDatos == false ) {

                $('div#dialogVentana').empty();
                $('div#dialogVentana').html('<strong>Desea cambiar su clave ...</strong>')

                $('div#dialogVentana').dialog({title: "Cambiar Clave", buttons: [
                        {
                            text: "OK",
                            click: function() {

                                $.ajax({
                                    url: "controlTrabajador.do",
                                    type: "post",
                                    data: {opcion: "_cambiarClave",
                                           claveAnterior: $('input#txtClaveAnt').val(),
                                           claveNueva: $('input#txtClaveNueva1').val() },
                                    success: function(data) {

                                    $('td#tdMensajeSistema').hide();

                                    $('div#dialogVentana').empty();

                                    $('div#dialogProgreso').dialog("close");

/*                                    if ( resultado == "true" ) {
                                        restablecerAparienciaFormulario();
                                        limpiarFormulario(); 
                                    }*/

                                    $(data).find('respuestaOperacion').each(function(index3, elemento3){
                                    var resultado = $(elemento3).find('resultado').text();
                                    var mensaje = $(elemento3).find('mensaje').text();

                                    $('div#dialogVentana').html('<strong>' + mensaje + '</strong>');

                                        if ( resultado == "true" ) {
                                        restablecerAparienciaFormulario();
                                        limpiarFormulario(); 
                                        $('div#dialogVentana').empty();
                                        $('div#dialogVentana').html('<strong>La clave fue cambiada correctamente</strong>');
                                        }

                                    $('div#dialogVentana').dialog({buttons: [
                                            {
                                                text: "OK",
                                                click: function() { $(this).dialog("close"); }
                                            }
                                    ]});

                                    $('div#dialogVentana').dialog("open");

                                    });

                                    },
                                    beforeSend: function() {
                                    $('div#dialogVentana').dialog("close");
                                    mostrarDialogBarraProgreso("Cambiando Clave ...");
                                    },
                                    dataType: "xml"
                                });


                            }
                        },
                        {
                            text: "Cancelar",
                            click: function() {
                            $(this).dialog("close");
                            }
                        }
                ],
                height: 150,
                width: 240
                });


                $('div#dialogVentana').dialog("open");
                }


        });

        $('button').button();

        $('button#btnCancelar').click(function() {
        restablecerAparienciaFormulario();
        limpiarFormulario();
        });

}); 