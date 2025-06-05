package Shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop extends JFrame {

    private DefaultListModel<String> catalogModel;
    private JList<String> catalogList;

    private DefaultListModel<String> cartModel;
    private JList<String> cartList;

    private DefaultListModel<String> wishlistModel;
    private JList<String> wishlistList;

    private JButton addToCartBtn, addToWishlistBtn, removeFromCartBtn, removeFromWishlistBtn;

    public Shop() {
        setTitle("Store");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Modelos y listas
        catalogModel = new DefaultListModel<>();
        catalogList = new JList<>(catalogModel);
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        wishlistModel = new DefaultListModel<>();
        wishlistList = new JList<>(wishlistModel);

        // Productos catálogo
        catalogModel.addElement("Producto 1 - $10");
        catalogModel.addElement("Producto 2 - $20");
        catalogModel.addElement("Producto 3 - $30");

        // Panel catálogo con botón para agregar a carrito o lista de deseos
        JPanel catalogPanel = new JPanel(new BorderLayout());
        catalogPanel.setBorder(BorderFactory.createTitledBorder("Catálogo"));
        catalogPanel.add(new JScrollPane(catalogList), BorderLayout.CENTER);

        addToCartBtn = new JButton("Agregar al Carrito");
        addToWishlistBtn = new JButton("Agregar a Lista de Deseos");

        JPanel catalogButtons = new JPanel();
        catalogButtons.add(addToCartBtn);
        catalogButtons.add(addToWishlistBtn);

        catalogPanel.add(catalogButtons, BorderLayout.SOUTH);

        // Panel carrito con botón para eliminar
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        removeFromCartBtn = new JButton("Eliminar del Carrito");
        JPanel cartButtons = new JPanel();
        cartButtons.add(removeFromCartBtn);
        cartPanel.add(cartButtons, BorderLayout.SOUTH);

        // Panel lista de deseos con botón para eliminar
        JPanel wishlistPanel = new JPanel(new BorderLayout());
        wishlistPanel.setBorder(BorderFactory.createTitledBorder("Lista de Deseos"));
        wishlistPanel.add(new JScrollPane(wishlistList), BorderLayout.CENTER);

        removeFromWishlistBtn = new JButton("Eliminar de Lista de Deseos");
        JPanel wishlistButtons = new JPanel();
        wishlistButtons.add(removeFromWishlistBtn);
        wishlistPanel.add(wishlistButtons, BorderLayout.SOUTH);

        // Layout general: catálogo a la izquierda, carrito y lista de deseos a la derecha
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.add(cartPanel);
        rightPanel.add(wishlistPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(catalogPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        // Acción botones
        addToCartBtn.addActionListener(e -> {
            String selected = catalogList.getSelectedValue();
            if (selected != null && !cartModel.contains(selected)) {
                cartModel.addElement(selected);
            }
        });

        addToWishlistBtn.addActionListener(e -> {
            String selected = catalogList.getSelectedValue();
            if (selected != null && !wishlistModel.contains(selected)) {
                wishlistModel.addElement(selected);
            }
        });

        removeFromCartBtn.addActionListener(e -> {
            String selected = cartList.getSelectedValue();
            if (selected != null) {
                cartModel.removeElement(selected);
            }
        });

        removeFromWishlistBtn.addActionListener(e -> {
            String selected = wishlistList.getSelectedValue();
            if (selected != null) {
                wishlistModel.removeElement(selected);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Shop shop = new Shop();
            shop.setVisible(true);
        });
    }
}
