/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.patterns;

import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Roger
 */
public class ParamRegistroSolicitud {
    
    private String asunto;
    private String detalle; 
    private String codTipoSolic;
    private String adjuntos;
    private String vistoBueno; 
    private String nombreArchivo;
    private FileItem itemArchivo;
    private String directorioFiles;
    private double tamanioMaxFiles;
    
    private Object cargo;
    
    private Object trabajador;
    

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the codTipoSolic
     */
    public String getCodTipoSolic() {
        return codTipoSolic;
    }

    /**
     * @param codTipoSolic the codTipoSolic to set
     */
    public void setCodTipoSolic(String codTipoSolic) {
        this.codTipoSolic = codTipoSolic;
    }

    /**
     * @return the adjuntos
     */
    public String getAdjuntos() {
        return adjuntos;
    }

    /**
     * @param adjuntos the adjuntos to set
     */
    public void setAdjuntos(String adjuntos) {
        this.adjuntos = adjuntos;
    }

    /**
     * @return the vistoBueno
     */
    public String getVistoBueno() {
        return vistoBueno;
    }

    /**
     * @param vistoBueno the vistoBueno to set
     */
    public void setVistoBueno(String vistoBueno) {
        this.vistoBueno = vistoBueno;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the itemArchivo
     */
    public FileItem getItemArchivo() {
        return itemArchivo;
    }

    /**
     * @param itemArchivo the itemArchivo to set
     */
    public void setItemArchivo(FileItem itemArchivo) {
        this.itemArchivo = itemArchivo;
    }

    /**
     * @return the directorioFiles
     */
    public String getDirectorioFiles() {
        return directorioFiles;
    }

    /**
     * @param directorioFiles the directorioFiles to set
     */
    public void setDirectorioFiles(String directorioFiles) {
        this.directorioFiles = directorioFiles;
    }

    /**
     * @return the tamanioMaxFiles
     */
    public double getTamanioMaxFiles() {
        return tamanioMaxFiles;
    }

    /**
     * @param tamanioMaxFiles the tamanioMaxFiles to set
     */
    public void setTamanioMaxFiles(double tamanioMaxFiles) {
        this.tamanioMaxFiles = tamanioMaxFiles;
    }

    /**
     * @return the cargo
     */
    public Object getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Object cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the trabajador
     */
    public Object getTrabajador() {
        return trabajador;
    }

    /**
     * @param trabajador the trabajador to set
     */
    public void setTrabajador(Object trabajador) {
        this.trabajador = trabajador;
    }
    
}