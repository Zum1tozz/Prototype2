package code;

import view.Login;
import view.Register;
import view.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    // ✅ Usuario activo
    private User currentUser;

    // Vistas
    private Login loginView;
    private Register registerView;
    private Catalog catalogView;

    // Listas de datos
    private List<User> userList = new ArrayList<>();
    private List<String> wishList = new ArrayList<>();
    private List<String> cartList = new ArrayList<>();

    // Inicia el programa
    public void startApp() {
        loginSection();
    }

    // ----------------------------
    // SECCIÓN: LOGIN
    // ----------------------------
    private void loginSection() {
        loginView = new Login();
        loginView.setLocationRelativeTo(null);
        loginView.setVisible(true);

        loginView.sign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginView.login.addActionListener(e -> {
            String username = loginView.username.getText();
            String password = new String(loginView.password.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Por favor completa todos los campos");
                return;
            }

            if (userList.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Debe registrarse antes de iniciar sesión");
                return;
            }

            boolean found = false;
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    found = true;
                    currentUser = user; // 🔑 GUARDAR USUARIO ACTIVO
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(loginView, "¡Bienvenido, " + username + "!");
                loginView.dispose();
                openCatalog();
            } else {
                JOptionPane.showMessageDialog(loginView, "Usuario o contraseña incorrectos.");
            }
        });

        loginView.sign.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginView.dispose();
                registerSection();
            }
        });
    }

    // ----------------------------
    // SECCIÓN: REGISTER
    // ----------------------------
    private void registerSection() {
        registerView = new Register();
        registerView.setLocationRelativeTo(null);
        registerView.setVisible(true);

        registerView.logReturn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerView.register.addActionListener(e -> {
            String user = registerView.registerUser.getText();
            String pass1 = registerView.registerPassword.getText();
            String pass2 = registerView.repeatPassword.getText();

            if (user.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(registerView, "Completa todos los campos");
                return;
            }

            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(registerView, "Las contraseñas no coinciden");
                return;
            }

            userList.add(new User(user, pass1));
            JOptionPane.showMessageDialog(registerView, "Usuario registrado con éxito");

            registerView.dispose();
            loginSection();
        });

        registerView.logReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerView.dispose();
                loginSection();
            }
        });
    }

    // ----------------------------
    // SECCIÓN: CATALOGO
    // ----------------------------
    private void openCatalog() {
        catalogView = new Catalog();
        catalogView.setLocationRelativeTo(null);
        catalogView.setVisible(true);

        // Cursor para labels
        catalogView.pick1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.pick2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.pick3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.favs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.history.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.cart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Lista de deseos
        catalogView.pick1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!wishList.contains("BORCELLE bag")) {
                    wishList.add("BORCELLE bag");
                    JOptionPane.showMessageDialog(catalogView, "BORCELLE bag añadido a la lista de deseos.");
                }
            }
        });
        catalogView.pick2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!wishList.contains("SHOES")) {
                    wishList.add("SHOES");
                    JOptionPane.showMessageDialog(catalogView, "SHOES añadido a la lista de deseos.");
                }
            }
        });
        catalogView.pick3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!wishList.contains("LADY BAG")) {
                    wishList.add("LADY BAG");
                    JOptionPane.showMessageDialog(catalogView, "LADY BAG añadido a la lista de deseos.");
                }
            }
        });
        catalogView.favs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (wishList.isEmpty()) {
                    JOptionPane.showMessageDialog(catalogView, "There is no added item to wish list.");
                } else {
                    StringBuilder sb = new StringBuilder("Wish List:\n");
                    for (String item : wishList) {
                        sb.append("- ").append(item).append("\n");
                    }
                    JOptionPane.showMessageDialog(catalogView, sb.toString());
                }
            }
        });
        catalogView.history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(catalogView, "Here will appear your last 15 bought products.");
            }
        });

        // Carrito
        catalogView.car1.addActionListener(e -> addToCart("BORCELLE BAG"));
        catalogView.car2.addActionListener(e -> addToCart("SHOES"));
        catalogView.car3.addActionListener(e -> addToCart("LADY BAG"));
        catalogView.cart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCart();
            }
        });

        // 👇 LISTENER PARA USER
        catalogView.user.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String[] options = {"Cerrar sesión", "Cambiar datos de usuario"};
                int choice = JOptionPane.showOptionDialog(
                        catalogView,
                        "Opciones de usuario",
                        "User Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 0) {
                    // Cerrar sesión
                    wishList.clear();
                    cartList.clear();
                    currentUser = null;
                    catalogView.dispose();
                    loginSection();
                } else if (choice == 1) {
                    // Cambiar datos de usuario
                    JTextField newUsername = new JTextField();
                    JPasswordField newPassword = new JPasswordField();
                    JPasswordField repeatPassword = new JPasswordField();

                    Object[] message = {
                        "Nuevo nombre de usuario:", newUsername,
                        "Nueva contraseña:", newPassword,
                        "Repetir contraseña:", repeatPassword
                    };
                    int option = JOptionPane.showConfirmDialog(
                        catalogView, message, "Cambiar datos", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String newUser = newUsername.getText();
                        String pass1 = new String(newPassword.getPassword());
                        String pass2 = new String(repeatPassword.getPassword());

                        if (newUser.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                            JOptionPane.showMessageDialog(catalogView, "Todos los campos son obligatorios.");
                            return;
                        }

                        if (!pass1.equals(pass2)) {
                            JOptionPane.showMessageDialog(catalogView, "Las contraseñas no coinciden.");
                            return;
                        }

                        currentUser.setUsername(newUser);
                        currentUser.setPassword(pass1);
                        JOptionPane.showMessageDialog(catalogView, "Datos actualizados correctamente.");
                    }
                }
            }
        });
    }

    // ----------------------------
    // Añadir al carrito
    // ----------------------------
    private void addToCart(String item) {
        cartList.add(item);
        JOptionPane.showMessageDialog(catalogView, item + " añadido al carrito.");
    }

    // ----------------------------
    // Mostrar carrito
    // ----------------------------
    private void showCart() {
        if (cartList.isEmpty()) {
            JOptionPane.showMessageDialog(catalogView, "Your cart is empty.");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String item : new ArrayList<>(cartList)) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel(item);
            JButton deleteButton = new JButton("Delete");

            deleteButton.setMargin(new Insets(2, 5, 2, 5));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cartList.remove(item);
                    showCart();
                }
            });
            row.add(label);
            row.add(deleteButton);
            panel.add(row);
        }

        JOptionPane.showMessageDialog(catalogView, panel, "Cart", JOptionPane.PLAIN_MESSAGE);
    }
}
