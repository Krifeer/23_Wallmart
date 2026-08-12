package pkg_modelo;
import java.util.ArrayList;

/**
 *
 * @author h45ch
 */
public class Carrito {

    private ArrayList<Producto> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }
    
    // Agregar producto al carrito
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
    }}
    
    // Quitar producto del carrito por su posición
    public void quitarProducto(int indice) {
        if (indice >= 0 && indice < productos.size()) {
            productos.remove(indice);
    }}
    
    // Obtener todos los productos del carrito
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
    // Vaciar el carrito
    public void vaciarCarrito() {
        productos.clear();
    }
    
    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return productos.isEmpty();
    }
    
    // Obtener cantidad de productos
    public int cantidadProductos() {
        return productos.size();
    }
    
    // Calcular el total de la compra
    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }
}