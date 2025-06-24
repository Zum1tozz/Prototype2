package code;

import view.Login;
import view.Register;
import view.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    private List<String> purchaseHistory = new ArrayList<>();

    // Inicia el programa
    public void startApp() {
        loadUsersFromFile(); // ✅ Cargar usuarios guardados
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
                    currentUser = user; // ✅ GUARDAR USUARIO ACTIVO
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

        loginView.sign.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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
            saveUsersToFile(); // ✅ Guardar en archivo
            JOptionPane.showMessageDialog(registerView, "Usuario registrado con éxito");

            registerView.dispose();
            loginSection();
        });

        registerView.logReturn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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

        // Cursor para labels y botones
        catalogView.pick1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.pick2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.pick3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.favs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.history.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.car3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.cart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.buy1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.buy2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.buy3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.user.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catalogView.adminPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mostrar admin panel solo si es admin
        if (currentUser.getUsername().equalsIgnoreCase("admin")) {
            catalogView.adminPanel.setVisible(true);
        } else {
            catalogView.adminPanel.setVisible(false);
        }

        // Lista de deseos
        catalogView.pick1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (!wishList.contains("BORCELLE bag")) {
                    wishList.add("BORCELLE bag");
                    JOptionPane.showMessageDialog(catalogView, "BORCELLE bag añadido a la lista de deseos.");
                }
            }
        });
        catalogView.pick2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (!wishList.contains("SHOES")) {
                    wishList.add("SHOES");
                    JOptionPane.showMessageDialog(catalogView, "SHOES añadido a la lista de deseos.");
                }
            }
        });
        catalogView.pick3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (!wishList.contains("LADY BAG")) {
                    wishList.add("LADY BAG");
                    JOptionPane.showMessageDialog(catalogView, "LADY BAG añadido a la lista de deseos.");
                }
            }
        });
        catalogView.favs.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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

        // Historial real de compras
        catalogView.history.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (purchaseHistory.isEmpty()) {
                    JOptionPane.showMessageDialog(catalogView, "You have no purchase history yet.");
                } else {
                    StringBuilder sb = new StringBuilder("Last purchases:\n");
                    for (String p : purchaseHistory) {
                        sb.append("- ").append(p).append("\n");
                    }
                    JOptionPane.showMessageDialog(catalogView, sb.toString());
                }
            }
        });

        // Carrito
        catalogView.car1.addActionListener(e -> addToCart("BORCELLE BAG"));
        catalogView.car2.addActionListener(e -> addToCart("SHOES"));
        catalogView.car3.addActionListener(e -> addToCart("LADY BAG"));
        catalogView.cart.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                showCart();
            }
        });

        // Comprar ahora y registrar en historial
        catalogView.buy1.addActionListener(e -> buyNow("BORCELLE BAG"));
        catalogView.buy2.addActionListener(e -> buyNow("SHOES"));
        catalogView.buy3.addActionListener(e -> buyNow("LADY BAG"));

        // USER OPTIONS
        catalogView.user.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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
                    purchaseHistory.clear();
                    currentUser = null;
                    catalogView.dispose();
                    loginSection();
                } else if (choice == 1) {
                    // Cambiar datos
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
                        saveUsersToFile();
                        JOptionPane.showMessageDialog(catalogView, "Datos actualizados correctamente.");
                    }
                }
            }
        });

        // Admin panel
        catalogView.adminPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                showAdminPanel();
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
    // Comprar ahora
    // ----------------------------
    private void buyNow(String item) {
        purchaseHistory.add(item);
        JOptionPane.showMessageDialog(catalogView, item + " comprado y añadido a tu historial.");
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
            deleteButton.addActionListener(e -> {
                cartList.remove(item);
                showCart();
            });
            row.add(label);
            row.add(deleteButton);
            panel.add(row);
        }

        JOptionPane.showMessageDialog(catalogView, panel, "Cart", JOptionPane.PLAIN_MESSAGE);
    }

    // ----------------------------
    // Mostrar panel admin
    // ----------------------------
    private void showAdminPanel() {
        JTextArea infoArea = new JTextArea(15, 40);
        infoArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Lista de Usuarios ===\n");
        for (User u : userList) {
            sb.append("- ").append(u.getUsername()).append("\n");
        }
        sb.append("\n=== Historial de compras ===\n");
        for (String p : purchaseHistory) {
            sb.append("- ").append(p).append("\n");
        }

        infoArea.setText(sb.toString());
        JScrollPane scroll = new JScrollPane(infoArea);

        JOptionPane.showMessageDialog(catalogView, scroll, "Panel de Administrador", JOptionPane.INFORMATION_MESSAGE);
    }

    // ----------------------------
    // Guardar usuarios en archivo
    // ----------------------------
    private void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User u : userList) {
                bw.write(u.getUsername() + "," + u.getPassword());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("No se pudo guardar usuarios: " + e.getMessage());
        }
    }

    // ----------------------------
    // Cargar usuarios desde archivo
    // ----------------------------
    private void loadUsersFromFile() {
        userList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userList.add(new User(parts[0], parts[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar usuarios: " + e.getMessage());
        }
    }
}
