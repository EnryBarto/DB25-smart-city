package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Utente;

class EmployeeManagementPanel extends JPanel {

    private final JComboBox<String> employeeList = new JComboBox<>();
    private final JComboBox<String> userList = new JComboBox<>();
    private final JComboBox<String> rolesList = new JComboBox<>();
    private Map<String, Utente> people;


    public EmployeeManagementPanel(final Controller controller) {
        this.setLayout(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        // Dopo aver aggiunto tutti gli item ai combo box
        for (var r: Dipendente.Ruolo.values()) {
            rolesList.addItem(r.toString());
        }

        // Imposta la larghezza dei combo box in base al contenuto massimo
        userList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        rolesList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        employeeList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        JLabel addTitle = new JLabel("Aggiunta dipendente", SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel removeTitle = new JLabel("Rimozione dipendente", SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(CENTER_ALIGNMENT);

        userList.setMaximumSize(userList.getPreferredSize());
        rolesList.setMaximumSize(rolesList.getPreferredSize());
        employeeList.setMaximumSize(employeeList.getPreferredSize());

        var aggiungi = new JButton("Aggiungi");
        aggiungi.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungi.setBackground(new Color(40, 167, 69));
        aggiungi.setForeground(Color.WHITE);
        aggiungi.setFocusPainted(false);
        aggiungi.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungi.setAlignmentX(CENTER_ALIGNMENT);
        aggiungi.addActionListener(e -> {
            if (userList.getSelectedIndex() != -1) {
                controller.addEmployee(people.get(userList.getSelectedItem()), Dipendente.Ruolo.valueOf(((String)rolesList.getSelectedItem()).toUpperCase()));
            }
        });

        var rimuovi = new JButton("Rimuovi");
        rimuovi.setFont(new Font("Arial", Font.BOLD, 14));
        rimuovi.setBackground(new Color(220, 53, 69));
        rimuovi.setForeground(Color.WHITE);
        rimuovi.setFocusPainted(false);
        rimuovi.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        rimuovi.setAlignmentX(CENTER_ALIGNMENT);
        rimuovi.addActionListener(e -> {
            if (employeeList.getSelectedIndex() != -1) {
                controller.removeEmployee((Dipendente)people.get(employeeList.getSelectedItem()));
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(this.userList);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(this.rolesList);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(aggiungi);

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(this.employeeList);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(rimuovi);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    public void updateLists(List<Dipendente> employeeList, List<Utente> userList) {
        this.employeeList.removeAllItems();
        this.userList.removeAllItems();
        this.people = new HashMap<>();
        employeeList.forEach(e -> {
            this.people.put(e.getDocumento() + " - " + e.getCognome() + " " + e.getNome() + " - " + e.getRuolo().toString(), e);
            this.employeeList.addItem(e.getDocumento() + " - " + e.getCognome() + " " + e.getNome() + " - " + e.getRuolo().toString());
        });
        userList.forEach(u -> {
            this.people.put(u.getDocumento() + " - " + u.getCognome() + " " + u.getNome(), u);
            this.userList.addItem(u.getDocumento() + " - " + u.getCognome() + " " + u.getNome());
        });
    }

}
