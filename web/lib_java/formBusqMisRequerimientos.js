// JavaScript Document
function buscarMisRequerimientos() {
	
	$.ajax({
	url: "controlBuscador.do", 
	type: "POST", 
	data: {opcion: "_mostrarMisReqPorUltimoTramite", 
		   codTramite: $('select#selectEstado').val(), 
		   fechaInicio: $('input#txtFechaInicio').val(), 
		   fechaFin: $('input#txtFechaFin').val()
		   },
        cache: false, 
	beforeSend: function() {
                    mostrarDialogoProgreso("Buscando ...");
                    },
	success: function(data) {
                   $('div#dialogMensaje').dialog("close");
                   var respuestaXML = false ; 

                   $(data).find("respuestaOperacion").each(function (index, elemento) {
                   var mensaje = $(elemento).find('mensaje').text();
                   mostrarMensajeDialogo("Mensaje", '<strong>' + mensaje + '</strong>');
                   respuestaXML = true;
                   });

                       if ( respuestaXML == false ) {
                       $('div#tabsConsultarMisReq').tabs("add", "#tabs-2", "Resultados");
                       $('div#tabs-2').empty();
                       $('div#tabs-2').html(data);
                       $('div#tabsConsultarMisReq').tabs({selected: 1});
                       }
                       else {
                       
                       }
                   
                   }
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



function verificarDatosBusqCompletos() {
$('tr.ui-state-error').hide();

var datosCompletos = true; 

    $('form#formBusqMisReq :input').each(function(index, elemento){
    var tipo = $(elemento).attr("type");
    var valor = "";
    var titulo = "" ; 

        if ( tipo != "button" ) {
        valor =  jQuery.trim( $(elemento).val() );
        titulo = jQuery.trim( $(elemento).attr("title") );

            if ( valor == "" && $(elemento).is($('select#selectEstado')) == false ) {

            $('span.mensajeTrError').empty();
            $('span.mensajeTrError').html("Falta completar: " + titulo );
            $('tr.ui-state-error').fadeIn('slow');

            datosCompletos = false;

            return false; 
            
            }

        }
        
    });

return datosCompletos ;

}

function mostrarDetalleSolicitud(codSolicitud) {
$('div#detalleSolicitud').hide();
$('div#detalleSolicitud').empty();

    $.ajax({
        url: 'controlBuscador.do', 
        type: 'GET',        
        data: {opcion: "_mostrarDetalleSolicitud", codSolic: codSolicitud},
        cache: false, 
        beforeSend: function() {
            mostrarDialogoProgreso("Buscando ...");
        },
        success: function(data) {
            $('div#dialogMensaje').dialog("close");
            $('div#detalleSolicitud').empty();
            $('div#detalleSolicitud').html(data);            
            var htmlButton = '<button type="button" id="btnRegresarBusq" >';
                htmlButton += '<span>Regresar a la busqueda</span></button>';
            $('div#detalleSolicitud').append(htmlButton); 
            $('button#btnRegresarBusq').button();
            $('div#tabsDetalleSolicitud').tabs(); 
            $('button#btnRegresarBusq').click(function() {
                $('div#detalleSolicitud').effect('blind', null, 400, function() {
                    $('div#tabsConsultarMisReq').fadeIn('normal'); 
                });
            });
            
            $('div#tabsConsultarMisReq').effect('blind', null, 400, function() {
            $('div#detalleSolicitud').fadeIn('normal');            
            });

            $('button#regresarLista').hide();
            
        },
        dataType: 'html'
    });

}

$(document).ready(function() {
	$('div#detalleSolicitud').hide();           
        $('input#txtFechaInicio').datepicker({changeMonth: true, changeYear: true});
	$('input#txtFechaFin').datepicker({changeMonth: true, changeYear: true});
        var $tabs = $('div#tabsConsultarMisReq').tabs({
            tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>Remove Tab</span></li>"
        });

        $( "div#tabsConsultarMisReq span.ui-icon-close" ).live( "click", function() {
			var index = $( "li", $tabs ).index( $( this ).parent() );
			$tabs.tabs( "remove", index );
        });


            $( "div#dialogDetalleTramite.mensajeDialogo" ).dialog({
            autoOpen: false,
            resizable: false,
            height: 400,
            width: 650,
            modal: true,
            buttons: {
                        "OK": function() {
                        $( this ).dialog( "close" );
                        }
                    }
            });

	$('button#btnBuscar').button(); 
	//$('div#tabsConsultarMisReq').tabs();
	$('tr.ui-state-error').hide();
        $('div#dialogMensaje').dialog({
            autoOpen: false,
            modal: true,
            resizable: false, 
            height: 200,
            width: 300
        });

	$('button#btnBuscar').click(function() {
        
        $('div#tabsConsultarMisReq').tabs( "remove", 1 );

            if ( verificarDatosBusqCompletos() == true  ) {
            buscarMisRequerimientos(); 
            }
            
	});

        $('div#tabsConsultarMisReq').click(function(event) {
        var elementoSelect = $('a[name|="lnkDetalleSolicitud"]');

            if ( $(event.target).is(elementoSelect) ) {
               //alert("Item seleccionado " +  $(event.target).attr('href') );
               mostrarDetalleSolicitud( $(event.target).attr('href') ); 
               event.preventDefault(); 
            }

     
        });

        $('div#detalleSolicitud').click(function(event){
        var elementoSelect = $('a[name|="lnkDetalleTramite"]');

            if ( $(event.target).is(elementoSelect) ) {
            var codTram = $(event.target).attr("href");

                $.ajax({
                    url: "controlTramite.do",
                    type: "GET",
                    data: {opcion: "_verDetalle", codTramite: codTram },
                    cache: false,
                    global: false,
                    beforeSend: function() {
                                    mostrarDialogoProgreso("Buscando ...") ;
                                },
                    success: function(data) {
                             $("div#dialogMensaje").dialog("close");
                             $('div#dialogDetalleTramite').html(data);
                             $('div#dialogDetalleTramite').dialog("open");
                             },
                    dataType: "html"
                });

            event.preventDefault();
            
            }
            

        });
	
});