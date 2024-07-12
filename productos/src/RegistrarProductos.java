import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarProductos {
    private JTextField codigoProductoTextField;
    private JTextField nombreProductoTextField;
    private JTextField descripcionTextField;
    private JTextField precioTextField;
    private JTextField cantidadTextField;
    private JTextField categoriaTextField;
    private JPanel registroPanel;
    private JButton busquedaButton;
    private JButton registrarButton;

    public RegistrarProductos() {
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/productos_cp";
                String user = "root";
                String password = "172843";
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO PRODUCTO (codigo_producto, nombre, descripcion, precio, cantidad, categoria) VALUES (?, ?, ?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, codigoProductoTextField.getText());
                    preparedStatement.setString(2, nombreProductoTextField.getText());
                    preparedStatement.setString(3, descripcionTextField.getText());

                    BigDecimal precio = null;
                    try {
                        precio = new BigDecimal(precioTextField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un precio válido.");
                        return;
                    }
                    preparedStatement.setBigDecimal(4, precio);

                    // Convertir la cantidad a entero
                    int cantidad = Integer.parseInt(cantidadTextField.getText());
                    preparedStatement.setInt(5, cantidad);

                    preparedStatement.setString(6, categoriaTextField.getText());

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Producto registrado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar el producto");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + ex.getMessage());
                } finally {
                    try {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        busquedaButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(registroPanel);
            frame.dispose();
            new BusquedaProductos().mostrar();
        });
    }

    public void mostrar() {
        JFrame frame = new JFrame("Registrar Productos");
        frame.setContentPane(registroPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrarProductos registrarProductos = new RegistrarProductos();
            registrarProductos.mostrar();
        });
    }
}