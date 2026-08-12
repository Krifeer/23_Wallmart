package pkg_modelo;

/**
 *
 * @author h45ch
 */
public class Electronico extends Producto {
    
    private String garantiaMeses;

    public Electronico(String garantiaMeses, int id, String nombre, double precio, int stock, String marca) {
        super(id, nombre, precio, stock, marca);
        this.garantiaMeses = "5 meses";
    }

    @Override
    public String getTipo() {
        return "Electrónico";
    }
}