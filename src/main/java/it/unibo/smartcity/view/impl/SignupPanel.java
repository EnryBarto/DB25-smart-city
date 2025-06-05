package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class SignupPanel extends JPanel {

    private static final int TEXT_WIDTH = 40;

    public SignupPanel() {
        super(new BorderLayout());

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
        var passwordField = new JTextField(TEXT_WIDTH);
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
        this.add(signupButton, BorderLayout.SOUTH);
    }

}
