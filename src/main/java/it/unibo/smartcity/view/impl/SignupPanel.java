package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.view.api.View.SignupData;
import it.unibo.smartcity.view.api.View;

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
                if (!cf.isEmpty()) checkArgument(
                    cf.matches("[A-Za-z0-9]{16}"), "Codice Fiscale must be 16 alphanumeric characters"
                );
                checkArgument(cf.length() == 16, "Codice Fiscale must be 16 characters long");
                String tel = checkNotNull(telField.getText());
                checkArgument(
                    tel.matches("\\d{10}"), "Telefono must be a 10-digit number"
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
                String username = checkNotNull(userField.getText());
                checkArgument(!username.isEmpty(), "Username cannot be empty");
                String password = checkNotNull(passwordField.getSelectedText());
                checkArgument(!password.isEmpty(), "Password cannot be empty");

                // Calling the controller to handle the signup logic
                //the password is not hashed here for security reasons,
                // the controller will hash it.
                SignupData data = new SignupData(
                    name, surname, documento, cf, tel, email, username, password
                );
                this.controller.signup(data);

            } catch (IllegalArgumentException | NullPointerException ex) {
                // Show error message if any input is invalid
                View.showErrorDialog(ex.getMessage());
            }
        });
        this.add(signupButton, BorderLayout.SOUTH);
    }

}
