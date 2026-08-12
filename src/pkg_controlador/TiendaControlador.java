/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg_controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pkg_modelo.Carrito;
import pkg_modelo.CarritoVacioException;
import pkg_modelo.Producto;
import pkg_modelo.ProductoDAO;
import pkg_modelo.ProductosException;
import pkg_modelo.StockInsuficienteException;
import pkg_vista.Vista;

/**
 *
 * @author dizqu
 */
public class TiendaControlador {
    
    private Vista vista;
    private ProductoDAO dao;
    private Carrito carrito;
    private ArrayList<Producto> productosDisponibles;
  
    public TiendaControlador(Vista vista){
        this.vista = vista;
        this.dao = new ProductoDAO();
        this.carrito = new Carrito();
        this.productosDisponibles = new ArrayList<>();
      
        cargarProductos();
    }
  
    public void cargarProductos(){
    try{
        productosDisponibles = dao.consultarTodos();
        llenarTablaProductos();
    }
    catch(SQLException ex){
        JOptionPane.showMessageDialog(vista, "No se pudieron cargar los productos: " + ex.getMessage());
    }
}
  
    public void agregarAlCarrito(){
        int fila = vista.tblProductos.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tienda.");
            return;
        }
      
        Producto producto = productosDisponibles.get(fila);
      
        try{
            int cantidadEnCarrito = contarEnCarrito(producto.getId());
            if(producto.getStock() <= cantidadEnCarrito){
                throw new StockInsuficienteException(
                        "No hay stock suficiente de \"" + producto.getNombre() + "\".");
            }
            carrito.agregarProducto(producto);
            actualizarVistaCarrito();
        }
        catch(StockInsuficienteException ex){
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        }
    }
  
    public void quitarDelCarrito(){
        int fila = vista.tableCarrito.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(vista, "Seleccione un producto del carrito.");
            return;
        }
        carrito.quitarProducto(fila);
        actualizarVistaCarrito();
    }
  
    public void actualizarTienda(){
        cargarProductos();
        JOptionPane.showMessageDialog(vista, "Catálogo actualizado.");
    }
  
    public void realizarCompra(){
        try{
            if(carrito.estaVacio()){
                throw new CarritoVacioException("El carrito está vacío. Agregue productos antes de comprar.");
            }
          
            validarStockCompra();
            descontarStock();
            carrito.vaciarCarrito();
            actualizarVistaCarrito();
            cargarProductos();
          
            JOptionPane.showMessageDialog(vista, "Compra realizada con éxito.");
        }
        catch(CarritoVacioException | StockInsuficienteException ex){
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(vista, "Error al procesar la compra: " + ex.getMessage());
        }
        catch(ProductosException ex){
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        }
    }
  
    private void validarStockCompra() throws StockInsuficienteException {
        for(Producto item : carrito.getProductos()){
            int solicitado = contarEnCarrito(item.getId());
            Producto disponible = buscarProductoPorId(item.getId());
          
            if(disponible == null || disponible.getStock() < solicitado){
                throw new StockInsuficienteException(
                        "Stock insuficiente para \"" + item.getNombre()
                        + "\". Disponible: "
                        + (disponible != null ? disponible.getStock() : 0)
                        + ", solicitado: " + solicitado + ".");
            }
        }
    }
  
    private void descontarStock() throws SQLException, ProductosException {
        ArrayList<Integer> idsProcesados = new ArrayList<>();
      
        for(Producto item : carrito.getProductos()){
            int id = item.getId();
            if(idsProcesados.contains(id)){
                continue;
            }
          
            Producto producto = buscarProductoPorId(id);
            if(producto == null){
                throw new ProductosException("No se encontró el producto con ID " + id + ".");
            }
          
            int cantidad = contarEnCarrito(id);
            producto.setStock(producto.getStock() - cantidad);
            dao.actualizar(producto, producto.getNombre());
            idsProcesados.add(id);
        }
    }
  
    private Producto buscarProductoPorId(int id){
        for(Producto producto : productosDisponibles){
            if(producto.getId() == id){
                return producto;
            }
        }
        return null;
    }
  
    private int contarEnCarrito(int idProducto){
        int cantidad = 0;
        for(Producto producto : carrito.getProductos()){
            if(producto.getId() == idProducto){
                cantidad++;
            }
        }
        return cantidad;
    }
  
    private void llenarTablaProductos(){
        DefaultTableModel modelo = vista.tablaProducto;
        modelo.setRowCount(0);
      
        for(Producto producto : productosDisponibles){
            modelo.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getTipo(),
                producto.getMarca(),
                producto.getPrecio(),
                producto.getStock()
            });
        }
    }
  
    private void actualizarVistaCarrito(){
        DefaultTableModel modelo = vista.tablaCarrito;
        modelo.setRowCount(0);
      
        for(Producto producto : carrito.getProductos()){
            modelo.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getTipo(),
                producto.getMarca(),
                producto.getPrecio(),
                producto.getStock()
            });
        }
      
        vista.lblTotal.setText(String.format("Total: $%.2f", carrito.calcularTotal()));
    }
}
