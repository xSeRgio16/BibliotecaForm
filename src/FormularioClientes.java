import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormularioClientes extends JFrame {
    public JTextField textNombre;
    private JLabel dni;
    private JTextField textDni;
    private JLabel nombre;
    private JLabel apellidos;
    private JTextField textApellidos;
    private JButton botonGuardar;
    private JButton botonLimpiar;
    private JPanel panelFormulario;
    private JList<Object> lista;
    private JButton borrar;
    private JLabel baseDeDatos;
    private JButton botonActualizar;
    private JLabel buscarPor;
    private JComboBox comboBox1;
    private JTextField textField1;

    public FormularioClientes() {
        setContentPane(panelFormulario);
        botonGuardar();
        botonLimpiar();
        llenarLista();
        borrar();
        actualizar();
        //Validación sólo números
        textDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();
                } else {
                    validar.validameEsta(textDni.getText(), evt);
                }
            }
        });
        //Validación sólo letras
        textNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isDigit(c)) {
                    evt.consume();
                } else if (c<=64 || c>122 || (c>90 && c<97)) {
                    evt.consume();
                }
            }
        });
    }

    private static void setStringsForColumns(String dni, String nombre, String apellidos,
                                             PreparedStatement ps) throws SQLException {
        ps.setString(1, dni);
        ps.setString(2, nombre);
        ps.setString(3, apellidos);
        ps.executeUpdate();
    }

    public static void createFormulario() {
        FormularioClientes form = new FormularioClientes();
        form.setExtendedState(form.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        form.setVisible(true);
        form.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void actualizar() {
        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dni, nombre, apellidos;
                String[] asignarLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z",
                        "S", "Q", "V", "H", "L", "C", "K", "E"};
                try {
                    boolean dniblank = textDni.getText().isBlank();
                    dni = textDni.getText();
                    if (!dniblank && dni.length() == 8) {
                        int x = Integer.parseInt(dni);
                        int resto = x % 23;
                        dni = (x + asignarLetra[resto]);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío o en el formato incorrecto", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!textNombre.getText().isBlank()) {
                        nombre = textNombre.getText();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!textApellidos.getText().isBlank()) {
                        apellidos = textApellidos.getText();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    PreparedStatement ps = Biblioteca.getCon().prepareStatement("UPDATE " +
                            "clientes SET dni=?, nombre=?, apellidos=? WHERE dni = ?");
                    String campo = String.valueOf(lista.getSelectedValue());
                    String[] split = campo.split(",", -1);
                    String dni2 = split[0];
                    ps.setString(1, dni);
                    ps.setString(2, nombre);
                    ps.setString(3, apellidos);
                    ps.setString(4, dni2);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Datos introducidos correctamente",
                            "Exito!", JOptionPane.INFORMATION_MESSAGE);
                    llenarLista();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error al introducir los datos",
                            "Error al insertar", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Sólo números en el campo de DNI, por favor");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Error");
                }
                limpiar();
            }
        });
    }

    private void borrar() {
        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement ps = Biblioteca.getCon().prepareStatement("DELETE FROM " +
                            "clientes WHERE dni = ?");
                    String campo = String.valueOf(lista.getSelectedValue());
                    String[] dni = campo.split(",", -1);
                    ps.setString(1, dni[0]);
                    ps.execute();
                    llenarLista();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al borrar");
                }
                limpiar();
            }
        });
    }

    private void llenarLista() {
        DefaultListModel<Object> modeloLista = new DefaultListModel<>();
        lista.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        try {
            PreparedStatement ps = Biblioteca.getCon().prepareStatement("SELECT * FROM clientes");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                modeloLista.addElement(dni + ", " + nombre + ", " + apellidos);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al llenar la lista");
        }
        lista.setModel(modeloLista);
    }

    private void botonLimpiar() {
        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }

    private void botonGuardar() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dni, nombre, apellidos;
                String[] asignarLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z",
                        "S", "Q", "V", "H", "L", "C", "K", "E"};
                try {
                    boolean dniblank = textDni.getText().isBlank();
                    dni = textDni.getText();
                    if (!dniblank && dni.length() == 8) {
                        int x = Integer.parseInt(dni);
                        int resto = x % 23;
                        dni = (x + asignarLetra[resto]);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío o en el formato incorrecto", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!textNombre.getText().isBlank()) {
                        nombre = textNombre.getText();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!textApellidos.getText().isBlank()) {
                        apellidos = textApellidos.getText();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Algun campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    PreparedStatement ps = Biblioteca.getCon().prepareStatement("INSERT INTO clientes" +
                            "(dni, nombre, apellidos) VALUES (?,?,?)");
                    setStringsForColumns(dni, nombre, apellidos, ps);
                    JOptionPane.showMessageDialog(null, "Datos introducidos correctamente",
                            "Exito!", JOptionPane.INFORMATION_MESSAGE);
                    llenarLista();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(null, "Error al introducir los datos",
                            "Error al insertar", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Sólo números en el campo de DNI, por favor");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Error");
                }

                limpiar();
            }
        });
    }

    private void limpiar() {
        textDni.setText("");
        textNombre.setText("");
        textApellidos.setText("");
    }
}
