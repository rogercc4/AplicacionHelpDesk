/***********************************************************************
 * Module:  GestorFichero.java
 * Author:  rcontreras
 * Purpose: Defines the Class GestorFichero
 ***********************************************************************/

package helpdesk.web;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileUpload.*;


/** @pdOid ffea0b67-86ba-453c-838c-c227723aa3fb */
public class GestorFichero {
   /** Nombre descriptivo del fichero
    *
    * @pdOid ca8535dc-f616-4320-9e40-077c20d4400b */
   private String nombre;
   /** Fecha del archivo
    *
    * @pdOid 735a46d7-57d7-4a29-92b2-4d259c6c0726 */
   private Date fecha;
   /** Tipo del contenido
    *
    * @pdOid 1846a42f-a990-484f-b585-434e99a0bf63 */
   private String tipoContenido;

   /** @pdOid 29b5788a-508f-4cb6-b8c4-4fed8f1a8dcd */
   public String getNombre() {
      return nombre;
   }

   /** @pdOid 37da66eb-5fc4-41f4-a5e8-9a90c2fb64cd */
   public Date getFecha() {
      return fecha;
   }

   /** @pdOid 545581ff-82dd-48a9-8ae7-daae498914b4 */
   public String getTipoContenido() {
      return tipoContenido;
   }
   
   /** Permite saber si el Item que representa un fichero, pudo registrarse en el disco duro.
    *
    * @param fichero Fichero que se desea guardar en el disco duro
    * @param request
    * @exception helpdesk.model.HelpDeskException
    * @pdOid ed86d75f-839d-4efa-85bc-b695edd37e9b */
   public void guardarFicheroDisco(org.apache.commons.fileupload.FileItem item, javax.servlet.http.HttpServletRequest request) throws helpdesk.model.HelpDeskException {
    // TODO: implement
        try {
            String fieldName = item.getFieldName();
            String fileName = item.getName();
            String contentType = item.getContentType();
            boolean isInMemory = item.isInMemory();
            long sizeInBytes = item.getSize();
            String directorioFiles = request.getServletContext().getInitParameter("directorioArchivos");

                if (sizeInBytes <= 0) {
                    throw new helpdesk.model.HelpDeskException("El archivo enviado no es valido");
                }
            
            double tamanioMB = ((sizeInBytes) / 1024) / 1024;
            double tamanioMaxFiles = Double.valueOf(request.getServletContext().getInitParameter("tamanioArchivos"));

            if (tamanioMB > tamanioMaxFiles) {
                throw new helpdesk.model.HelpDeskException("El archivo que se desea subir no " + "debe pesar mas de " + tamanioMaxFiles + " MB");
            }

            String[] divNombres = fileName.split("\\.");

                if (divNombres.length <= 1) {
                throw new helpdesk.model.HelpDeskException("El archivo que se desea subir no tiene extencion");
                }

            File fichero = new File(fileName);
            File miFile = new File(directorioFiles, fichero.getName());
            item.write(miFile);            

            if (miFile.exists()) {                
                this.fecha = new java.util.Date(); 
                String nombreArchivoTemp = String.valueOf(this.fecha.getTime());
                
                nombreArchivoTemp = nombreArchivoTemp + "." + divNombres[divNombres.length - 1];

                boolean cambio = miFile.renameTo( new java.io.File(directorioFiles,  nombreArchivoTemp) );

                    if (cambio == true) {
                    this.nombre = nombreArchivoTemp.trim();
                    }
                    else {
                    throw new helpdesk.model.HelpDeskException("El archivo no pudo ser guardado");
                    }

            this.tipoContenido = contentType.trim() ;
            
            }
            else {
            throw new helpdesk.model.HelpDeskException("El archivo no pudo ser guardado");
            }

        } catch (Exception ex) {
            Logger.getLogger(GestorFichero.class.getName()).log(Level.SEVERE, null, ex);

                if ( ex instanceof helpdesk.model.HelpDeskException )
                    throw new helpdesk.model.HelpDeskException(ex.getMessage());
                else
                    throw new helpdesk.model.HelpDeskException("El archivo no pudo ser guardado");
            
        }

    }
}
