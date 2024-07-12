import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new login().mostrar();
    }

    public static void mostrarPantallaPrincipal() {
        JFrame frame = new JFrame("Gestión de Productos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JButton insertarButton = new JButton("Insertar Productos");
        JButton buscarButton = new JButton("Buscar Productos");

        insertarButton.addActionListener(e -> {
            new RegistrarProductos().mostrar();
        });

        buscarButton.addActionListener(e -> {
            new BusquedaProductos().mostrar();
        });

        mainPanel.add(insertarButton);
        mainPanel.add(buscarButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}