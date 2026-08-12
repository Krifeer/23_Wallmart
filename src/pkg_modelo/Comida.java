package pkg_modelo;

/**
 *
 * @author h45ch
 */
public class Comida extends Producto {

    private String fechaCaducidad;

    public Comida(String fechaCaducidad, int id, String nombre, double precio, int stock, String marca) {
        super(id, nombre, precio, stock, marca);
        this.fechaCaducidad = "15/12/2030";
    }


    @Override
    public String getTipo() {
        return "Comida";
    }
}