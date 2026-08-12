package pkg_modelo;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author h45ch
 */
public class ProductoDAO {

    private final String URL = "jdbc:mysql://localhost:3306/tienda_virtual";
    private final String USER = "root";
    private final String PASS = "root";

    // INSERTAR
    public void insertar(Producto p, String tipo)
            throws SQLException {
        
        String sql = "INSERT INTO producto"
                + "(nombre, precio, stock, marca, tipo)"
                + " VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setInt(3, p.getStock());
            stmt.setString(4, p.getMarca());
            stmt.setString(5, tipo);

            stmt.executeUpdate();
    }}
    
    // ACTUALIZAR
    public void actualizar(Producto p, String nombreOriginal)
            throws SQLException {

        String sql = "UPDATE producto SET "
                + "nombre=?,"
                + "precio=?,"
                + "stock=?,"
                + "marca=? "
                + "WHERE nombre=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setInt(3, p.getStock());
            stmt.setString(4, p.getMarca());
            stmt.setString(5, nombreOriginal);

            stmt.executeUpdate();
    }}

        // ELIMINAR
    public void eliminar(String nombre, String tipo)
            throws SQLException {

        String sql = "DELETE FROM producto "
                + "WHERE nombre=? AND tipo=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, tipo);

            stmt.executeUpdate();
}}
    
    // CONSULTAR
    public ArrayList<Producto> obtenerPorTipo(String tipo)
            throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE tipo = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String garantiaMeses=rs.getString("garantiaMeses");
                String talla=rs.getString("talla");
                String fechaCaducidad=rs.getString("fechaCaducidad");
                
                int id=rs.getInt("id");
                String nombre=rs.getString("nombre");
                double precio=rs.getDouble("precio");
                int stock=rs.getInt("stock");
                String marca=rs.getString("marca");

                switch (tipo) {
                    case "Electronico":
                        lista.add(new Electronico(garantiaMeses, id, nombre, precio, stock, marca
                        ));
                        break;

                    case "Ropa":
                        lista.add(new Ropa(talla, id, nombre, precio, stock, marca
                        ));
                        break;

                    case "Comida":
                        lista.add(new Comida(fechaCaducidad, id, nombre, precio, stock, marca
                        ));
                        break;
                }}}
        return lista;
}

// CONSULTAR TODOS
    public ArrayList<Producto> consultarTodos()
            throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String garantiaMeses=rs.getString("garantiaMeses");
                String talla=rs.getString("talla");
                String fechaCaducidad=rs.getString("fechaCaducidad");
                
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String marca = rs.getString("marca");
                String tipo = rs.getString("tipo");

                switch (tipo) {
                    case "Electronico":
                        lista.add(new Electronico(garantiaMeses, id, nombre, precio, stock, marca
                        ));
                        break;

                    case "Ropa":
                        lista.add(new Ropa(talla, id, nombre, precio, stock, marca
                        ));
                        break;

                    case "Comida":
                        lista.add(new Comida(fechaCaducidad, id, nombre, precio, stock, marca
                        ));
                        break;
        }}}
        return lista;
    }

}

