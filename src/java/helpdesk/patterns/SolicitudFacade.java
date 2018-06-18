/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.patterns;

import helpdesk.model.Archivo;
import helpdesk.model.Cargo;
import helpdesk.model.HelpDeskException;
import helpdesk.model.Solicitud;
import helpdesk.model.TipoSolicitud;
import helpdesk.model.Trabajador;
import helpdesk.model.ValorTipoTramite;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Roger
 */
public class SolicitudFacade {
    
    private Solicitud solicitud;
    
    private ParamRegistroSolicitud parametroRegistro;
    
    private Archivo miArchivo; 
    
    
    public SolicitudFacade(String codSolic) {
        this.cargarSolicitud(codSolic);
    }
    
    
    public SolicitudFacade(ParamRegistroSolicitud parametrosRegistroSolicitud) {
        this.parametroRegistro = parametrosRegistroSolicitud;
    }
    
    
    private void cargarSolicitud(String codSolic) {
        
        if (codSolic != null && codSolic.trim().length()>0){
            
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]+");
            java.util.regex.Matcher m = p.matcher(codSolic);
            
            if (m.find()){
                this.solicitud = Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
           }
            
        }
        
    }

    /**
     * @return the solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }
    
    public void registrarSolicitud() throws HelpDeskException {
    
        try {
            
            if ( !this.procesarArchivoAdjunto() ) {
                throw new HelpDeskException("El archivo no pudo ser adjuntado");
            }
            
            this.registrarSolicitudEnBD();
            
        } catch (Exception ex) {
            throw new HelpDeskException(ex.getMessage());
        }
        
    }
    
    private boolean procesarArchivoAdjunto() throws HelpDeskException {
        
        if ( this.parametroRegistro.getItemArchivo() == null || 
                this.parametroRegistro.getItemArchivo().isFormField() ) {
            return true;
        }
        
        try {
        
        FileItem item = this.parametroRegistro.getItemArchivo();
        String directorioFiles = this.parametroRegistro.getDirectorioFiles();
        
        String fileName = item.getName();
        String contentType = item.getContentType();
        long sizeInBytes = item.getSize();
        
        if ( sizeInBytes <= 0 ) {
            return true;
        }
        
        double tamanioMB = ((sizeInBytes)/1024)/1024;
        double tamanioMaxFiles = this.parametroRegistro.getTamanioMaxFiles();

        if ( tamanioMB > tamanioMaxFiles )
            throw new HelpDeskException("El archivo que se desea subir no "
                                        + "debe pesar mas de " + tamanioMaxFiles + " MB");

    File fichero = new File(fileName);

        File miFile = new File(directorioFiles, fichero.getName());
        item.write(miFile);

            if ( miFile.exists() ) {

            String[] divNombres = fileName.split("\\.");
            String nombreArchivoTemp = String.valueOf( new java.util.Date().getTime() );

                if ( divNombres.length > 1 ) {
                nombreArchivoTemp = nombreArchivoTemp + "." + divNombres[divNombres.length -1];
                }

            boolean cambio = miFile.renameTo( new java.io.File(directorioFiles,  nombreArchivoTemp) );

                if ( cambio == true ) {
                    this.miArchivo = new Archivo();
                    this.miArchivo.setArchivo(nombreArchivoTemp);
                    this.miArchivo.setFecha(new Date());
                    this.miArchivo.setTipoContenido(contentType);
                }
                return true;
            }

            
        } catch (Exception ex) {
            throw new HelpDeskException(ex.getMessage());
        }
        
        return false;
    }
    
    
    private void registrarSolicitudEnBD() throws HelpDeskException {
        Solicitud miSolic = new Solicitud();
        
        String adjuntos = this.parametroRegistro.getAdjuntos();
        String asunto = this.parametroRegistro.getAsunto();
        Cargo cargo = (Cargo)this.parametroRegistro.getCargo();
        String detalle = this.parametroRegistro.getDetalle();
        String codTipoSolic = this.parametroRegistro.getCodTipoSolic();
        Trabajador trabajador = (Trabajador)this.parametroRegistro.getTrabajador();
        String nombreArchivo = this.parametroRegistro.getNombreArchivo();
        String vistoBueno = this.parametroRegistro.getVistoBueno();
        
        miSolic.setAdjuntos(adjuntos);
        miSolic.setAsunto(asunto);
        miSolic.setCargo(cargo);
        miSolic.setDetalle(detalle);
        miSolic.setFecha(new Date());
        miSolic.setTipoSolicitud( new TipoSolicitud(Integer.parseInt( codTipoSolic )) );
        miSolic.setTrabajador(trabajador);
        

        Trabajador miTrabajador = new Trabajador();
        java.util.ArrayList<Archivo> misArchivos = null;

            if ( miArchivo != null )  {
            misArchivos = new java.util.ArrayList<Archivo>();
            miArchivo.setNombre(nombreArchivo);
            misArchivos.add(miArchivo);
            }

        miTrabajador.guardarSolicitud(miSolic, misArchivos, vistoBueno.trim().equals("1") );
    }
    
    public boolean existeSolicitud() {
        return ( this.solicitud != null );
    }
    
    public boolean isUltimoTramitePorAtender() {
        return   this.solicitud.getUltimoTramite() != null &&
                 this.solicitud.getUltimoTramite().getTipoTramite().getCodigo() == 
                 ValorTipoTramite.ATENDER.getCodigo();
    }
    
    
    public void cargarFormulario(String paginaJSP, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.existeSolicitud()){
            request.setAttribute("miSolicitud", this.getSolicitud());
            request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
            request.getRequestDispatcher(paginaJSP).forward(request, response);
        }
        
    }
    
}