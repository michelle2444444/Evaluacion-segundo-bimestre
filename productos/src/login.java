import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    private JPanel loginPanel;
    private JTextField usuarioTextField;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/productos_cp";
                String user = "root";
                String password = "172843";
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String sql = "SELECT * FROM USUARIO WHERE username = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, usuarioTextField.getText());
                    preparedStatement.setString(2, new String(passwordField1.getPassword()));
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Login exitoso");
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginPanel);
                        frame.dispose();
                        Main.mostrarPantallaPrincipal();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void mostrar() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new login().mostrar();
    }
}