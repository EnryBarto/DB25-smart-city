package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.view.api.View;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

class LoginPanel extends JPanel {

    private static final int TEXT_WIDTH = 40;

    public LoginPanel(final Controller controller) {
        super(new BorderLayout());

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
                checkNotNull(passwordField.getText());
                checkArgument(!passwordField.getText().isBlank());

                // calling the controller to login, the password is not hashed here
                // for security reasons, the controller will hash it.
                controller.login(userField.getText(), passwordField.getText());
            } catch (IllegalArgumentException | NullPointerException ec) {
                View.showErrorDialog(ec.getMessage());
                ec.printStackTrace();
            } finally {
                passwordField.setText("");
            }
        });
    }

}
