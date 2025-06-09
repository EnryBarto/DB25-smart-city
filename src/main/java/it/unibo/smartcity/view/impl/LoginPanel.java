package it.unibo.smartcity.view.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unibo.smartcity.controller.api.Controller;

class LoginPanel extends JPanel {

    private static final int TEXT_WIDTH = 40;

    public LoginPanel(final Controller controller) {
        super(new BorderLayout());

        var userNamePanel = new JPanel();
        var userField = new JTextField(TEXT_WIDTH);
        userNamePanel.add(new JLabel("Username: "));
        userNamePanel.add(userField);

        var passwordPanel = new JPanel();
        var passwordField = new JPasswordField(TEXT_WIDTH);
        passwordField.setEchoChar('*');
        passwordPanel.add(new JLabel("Password: "));
        passwordPanel.add(passwordField);

        var centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(userNamePanel);
        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createVerticalGlue());
        this.add(centerPanel, BorderLayout.CENTER);

        var loginButton = new JButton("Login");
        this.add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            try {
                checkNotNull(userField.getText());
                checkArgument(!userField.getText().isBlank());
                checkNotNull(passwordField.getPassword());
                var psw = new String(passwordField.getPassword());
                checkArgument(!psw.isBlank());
                controller.login(userField.getText(), psw);
            } catch (IllegalArgumentException | NullPointerException ec) {
                JOptionPane.showMessageDialog(this, "Inserire tutti i dati", "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                passwordField.setText("");
            }
        });
    }

}
