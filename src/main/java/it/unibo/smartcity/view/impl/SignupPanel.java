package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.view.api.View.SignupData;

class SignupPanel extends JPanel {

    private static final int TEXT_WIDTH = 40;
    private final Controller controller;

    public SignupPanel(Controller controller) {
        super(new BorderLayout());

        Preconditions.checkNotNull(controller, "Controller cannot be null");
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
            String name = Preconditions.checkNotNull(nameField.getText());
            Preconditions.checkArgument(!name.isEmpty(), "Nome cannot be empty");
            String surname = Preconditions.checkNotNull(surnameField.getText());
            Preconditions.checkArgument(!surname.isEmpty(), "Cognome cannot be empty");
            String documento = Preconditions.checkNotNull(documentField.getText());
            Preconditions.checkArgument(!documento.isEmpty(), "Documento cannot be empty");
            String cf = cfField.getText();
            if (!cf.isEmpty()) Preconditions.checkArgument(
                cf.matches("[A-Z0-9]{16}"), "Codice Fiscale must be 16 alphanumeric characters"
            );
            Preconditions.checkArgument(cf.length() == 16, "Codice Fiscale must be 16 characters long");
            String tel = Preconditions.checkNotNull(telField.getText());
            Preconditions.checkArgument(
                tel.matches("\\d{10}"), "Telefono must be a 10-digit number"
            );
            String email = Preconditions.checkNotNull(emailField.getText());
            Preconditions.checkArgument(
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
            String username = Preconditions.checkNotNull(userField.getText());
            Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
            String password = Preconditions.checkNotNull(passwordField.getSelectedText());
            Preconditions.checkArgument(
                password != null && !password.isEmpty(), "Password cannot be empty"
            );

            // Calling the controller to handle the signup logic
            SignupData data = new SignupData(
                name, surname, documento, cf, tel, email, username, password
            );
            this.controller.signup(data);
        });
        this.add(signupButton, BorderLayout.SOUTH);
    }

}
