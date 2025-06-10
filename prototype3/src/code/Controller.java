package code;

import view.Login;
import view.Register;
import view.Catalog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    // Vistas
    private Login loginView;
    private Register registerView;
    private Catalog catalogView;

    // Lista de usuarios registrados
    private List<User> userList = new ArrayList<>();

    // Inicia el programa
    public void startApp() {
        loginSection();
    }

    // ----------------------------
    // SECCIÓN: LOGIN
    // ----------------------------
    private void loginSection() {
        loginView = new Login();
        loginView.setLocationRelativeTo(null); // Centrar ventana
        loginView.setVisible(true);

        loginView.sign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginView.login.addActionListener(e -> {
            String username = loginView.username.getText();
            String password = new String(loginView.password.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Por favor completa todos los campos");
                return;
            }

            boolean found = false;
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(loginView, "¡Bienvenido, " + username + "!");
                loginView.dispose();
                openCatalog();
            } else {
                JOptionPane.showMessageDialog(loginView, "Credenciales incorrectas");
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
        registerView.setLocationRelativeTo(null); // Centrar ventana
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
        catalogView.setLocationRelativeTo(null); // Centrar ventana
        catalogView.setVisible(true);
    }
}
