package pkg_modelo;

/**
 *
 * @author h45ch
 */
public class Ropa extends Producto {

    private String talla;

    public Ropa(String talla, int id, String nombre, double precio, int stock, String marca) {
        super(id, nombre, precio, stock, marca);
        this.talla = "Talla M";
    }


    @Override
    public String getTipo() {
        return "Ropa";
    }
}