package it.unibo.smartcity.view.impl;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.Controller.UserLevel;
import it.unibo.smartcity.model.api.Utente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

class UserPanel extends JPanel {

    private JPanel centerPanel;

    public UserPanel(final Controller controller) {
        super(new BorderLayout());

        var jb = new JButton("LogOut");
        jb.setFont(new Font("Arial", Font.BOLD, 14));
        jb.setBackground(new Color(220, 53, 69));
        jb.setForeground(Color.WHITE);
        jb.setFocusPainted(false);
        jb.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jb.addActionListener(e -> {
            controller.logout();
        });
        this.add(jb, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.setBackground(Color.WHITE);
    }

    void updateUserInfo(final Utente user, final UserLevel level) {
        if (centerPanel != null) this.remove(centerPanel);

        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new GridLayout(0, 1, 10, 10));
        this.centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        this.centerPanel.setBackground(new Color(245, 249, 255));

        JLabel title = new JLabel("Profilo Utente", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(52, 152, 219));
        this.centerPanel.add(title);

        if (user != null) {
            JLabel nome = new JLabel("Nome: " + user.getNome(), SwingConstants.CENTER);
            JLabel cognome = new JLabel("Cognome: " + user.getCognome(), SwingConstants.CENTER);
            JLabel documento = new JLabel("Documento: " + user.getDocumento(), SwingConstants.CENTER);
            JLabel codFisc = new JLabel("Cod Fisc: " + (user.getCodiceFiscale().isEmpty() ? "N/A" : user.getCodiceFiscale().get()), SwingConstants.CENTER);
            JLabel email = new JLabel("Email: " + user.getEmail(), SwingConstants.CENTER);
            JLabel username = new JLabel("Username: " + user.getUsername(), SwingConstants.CENTER);
            JLabel telefono = new JLabel("Telefono: " + user.getTelefono(), SwingConstants.CENTER);
            JLabel livello = new JLabel("Livello utente: " + level, SwingConstants.CENTER);

            Font infoFont = new Font("Arial", Font.PLAIN, 16);
            nome.setFont(infoFont);
            cognome.setFont(infoFont);
            documento.setFont(infoFont);
            codFisc.setFont(infoFont);
            email.setFont(infoFont);
            username.setFont(infoFont);
            telefono.setFont(infoFont);
            livello.setFont(infoFont);

            this.centerPanel.add(nome);
            this.centerPanel.add(cognome);
            this.centerPanel.add(documento);
            this.centerPanel.add(codFisc);
            this.centerPanel.add(email);
            this.centerPanel.add(username);
            this.centerPanel.add(telefono);
            this.centerPanel.add(livello);
        } else {
            JLabel noUser = new JLabel("Nessun utente autenticato.", SwingConstants.CENTER);
            noUser.setFont(new Font("Arial", Font.ITALIC, 16));
            noUser.setForeground(Color.GRAY);
            this.centerPanel.add(noUser);
        }

        this.add(this.centerPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
