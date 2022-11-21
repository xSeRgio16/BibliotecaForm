import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Biblioteca {

    public static Connection getCon() {
        return con;
    }

    private static Connection con;

    public static void main(String[] args) {
        conectar();
        FormularioClientes.createFormulario();
    }

    private static void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
            JOptionPane.showMessageDialog(null, "Conexion establecida");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en la conexion");
        }
    }
}