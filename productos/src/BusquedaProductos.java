import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusquedaProductos {
    private JTextField codigoProductoTextField;
    private JButton buscarButton;
    private JButton registrarProductosButton;
    private JButton inicioSesionButton;
    private JPanel busquedaPanel;
    private JLabel nombreresultado;
    private JLabel descripcionresultado;
    private JLabel precioresultado;
    private JLabel cantidadresultado;
    private JLabel categoriaresultado;

    public BusquedaProductos() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/productos_cp";
                String user = "root";
                String password = "172843";
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String sql = "SELECT * FROM PRODUCTO WHERE codigo_producto = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, codigoProductoTextField.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        nombreresultado.setText("Nombre: " + resultSet.getString("nombre"));
                        descripcionresultado.setText("Descripción: " + resultSet.getString("descripcion"));
                        precioresultado.setText("Precio: " + resultSet.getBigDecimal("precio").toString());
                        cantidadresultado.setText("Cantidad: " + resultSet.getInt("cantidad"));
                        categoriaresultado.setText("Categoría: " + resultSet.getString("categoria"));
                    } else {
                        nombreresultado.setText("Producto no encontrado");
                        descripcionresultado.setText("");
                        precioresultado.setText("");
                        cantidadresultado.setText("");
                        categoriaresultado.setText("");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registrarProductosButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(busquedaPanel);
            frame.dispose();
            new RegistrarProductos().mostrar();
        });

        inicioSesionButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(busquedaPanel);
            frame.dispose();
            new login().mostrar();
        });
    }

    public void mostrar() {
        JFrame frame = new JFrame("Buscar Productos");
        frame.setContentPane(busquedaPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
