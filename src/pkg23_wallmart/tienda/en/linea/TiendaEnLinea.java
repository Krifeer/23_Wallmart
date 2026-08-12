package pkg23_wallmart.tienda.en.linea;

import pkg_controlador.TiendaControlador;
import pkg_vista.Vista;

/**
 *
 * @author h45ch
 */
public class TiendaEnLinea {

    /**
     * @param args the command line arguments
     */
 
        
        public static void main(String[] args) {
            // TODO code application logic here
         Vista vista= new Vista();
        TiendaControlador control= new TiendaControlador(vista);
        vista.setVisible(true);
    }
    
}
