package it.unibo.smartcity.view.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.UtenteImpl;

class SignupPanel extends JPanel {

    private static final int TEXT_WIDTH = 40;
    private final Controller controller;

    public SignupPanel(Controller controller) {
        super(new BorderLayout());

        checkNotNull(controller, "Controller cannot be null");
        this.controller = controller;

        var namePanel = new JPanel();
        var nameField = new JTextField(TEXT_WIDTH);
        namePanel.add(new JLabel("Nome: "));
        namePanel.add(nameField);

        var surnamePanel = new JPanel();
        var surnameField = new JTextField(TEXT_WIDTH);
        surnamePanel.add(new JLabel("Cognome: "));
        surnamePanel.add(surnameField);

        var documentPanel = new JPanel();
        var documentField = new JTextField(TEXT_WIDTH);
        documentPanel.add(new JLabel("Documento: "));
        documentPanel.add(documentField);

        var cfPanel = new JPanel();
        var cfField = new JTextField(TEXT_WIDTH);
        cfPanel.add(new JLabel("Codice Fiscale: "));
        cfPanel.add(cfField);

        var telPanel = new JPanel();
        var telField = new JTextField(TEXT_WIDTH);
        telPanel.add(new JLabel("Telefono: "));
        telPanel.add(telField);

        var emailPanel = new JPanel();
        var emailField = new JTextField(TEXT_WIDTH);
        emailPanel.add(new JLabel("Email: "));
        emailPanel.add(emailField);

        var userNamePanel = new JPanel();
        var userField = new JTextField(TEXT_WIDTH);
        userNamePanel.add(new JLabel("Username: "));
        userNamePanel.add(userField);

        var passwordPanel = new JPanel();
        var passwordField = new JPasswordField(TEXT_WIDTH);
        passwordField.setEchoChar('*'); // Mask the password input
        passwordPanel.add(new JLabel("Password: "));
        passwordPanel.add(passwordField);

        var ruoloPanel = new JPanel();
        var ruoloField = new JTextField(TEXT_WIDTH);
        ruoloPanel.add(new JLabel("Ruolo: "));
        ruoloPanel.add(ruoloField);

        var centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(namePanel);
        centerPanel.add(surnamePanel);
        centerPanel.add(documentPanel);
        centerPanel.add(cfPanel);
        centerPanel.add(telPanel);
        centerPanel.add(emailPanel);
        centerPanel.add(userNamePanel);
        centerPanel.add(passwordPanel);
        centerPanel.add(ruoloPanel);
        centerPanel.add(Box.createVerticalGlue());
        this.add(centerPanel, BorderLayout.CENTER);

        var signupButton = new JButton("Registrati");
        //this thing should probably vecome a function
        signupButton.addActionListener(e -> {
            // gather input data
            try {
                String name = checkNotNull(nameField.getText());
                checkArgument(!name.isEmpty(), "Nome cannot be empty");
                String surname = checkNotNull(surnameField.getText());
                checkArgument(!surname.isEmpty(), "Cognome cannot be empty");
                String documento = checkNotNull(documentField.getText());
                checkArgument(!documento.isEmpty(), "Documento cannot be empty");
                String cf = cfField.getText();
                if (!cf.isBlank()) checkArgument(
                    cf.matches("[A-Za-z0-9]{16}"), "Codice Fiscale must be 16 alphanumeric characters"
                );
                String tel = checkNotNull(telField.getText());
                checkArgument(
                    tel.matches("^.{1,14}$"), "Telefono must be from 1 to 14 characters"
                );
                String email = checkNotNull(emailField.getText());
                checkArgument(
                    !email.isEmpty() &&
                    email.contains("@") &&
                    email.contains(".") &&
                    !email.contains(" ") &&
                    !email.contains(",") &&
                    !email.contains(";") &&
                    !email.contains(":") &&
                    !email.contains("!") &&
                    !email.contains("?") &&
                    !email.contains("#"),
                    "Email is invalid"
                );

                String ruolo = checkNotNull(ruoloField.getText());
                checkArgument(!ruolo.isEmpty(), "Ruolo cannot be empty");
                checkArgument(
                    ruolo.equalsIgnoreCase("utente") ||
                    ruolo.equalsIgnoreCase("controllore")||
                    ruolo.equalsIgnoreCase("amministrativo")||
                    ruolo.equalsIgnoreCase("autista"),
                    "Ruolo must be either 'utente', 'controllore', 'amministrativo' or 'autista'"
                );

                String username = checkNotNull(userField.getText());
                checkArgument(!username.isEmpty(), "Username cannot be empty");
                checkArgument(passwordField.getPassword().length != 0);
                String password = new String(passwordField.getPassword());
                checkArgument(!password.isEmpty(), "Password cannot be empty");

                Utente newUser = new UtenteImpl(surname, name, documento, cf,username, email, tel, BCrypt.hashpw(password, BCrypt.gensalt()));
                this.controller.signup(newUser, ruolo);

                nameField.setText("");
                surnameField.setText("");
                documentField.setText("");
                cfField.setText("");
                telField.setText("");
                emailField.setText("");
                userField.setText("");
                passwordField.setText("");
                ruoloField.setText("");

                controller.showLoginUser(username);

            } catch (IllegalArgumentException | NullPointerException ex) {
                controller.showErrorMessage("Errore Registrazione", ex.getMessage());
            }
        });
        this.add(signupButton, BorderLayout.SOUTH);
    }

}
