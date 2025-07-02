package it.unibo.smartcity.view.impl;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.impl.FermataImpl;
import it.unibo.smartcity.model.api.Fermata;

class FermataManagePanel extends JPanel {

    private final JComboBox<String> fermateList = new JComboBox<>();
    private Map<String, Fermata> fermateMap = new HashMap<>();

    public FermataManagePanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        // LEFT PANEL: aggiunta fermata
        var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta fermata", SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        var nomePanel = new JPanel();
        nomePanel.setBackground(Color.WHITE);
        var nomeField = new JTextField(15);
        nomePanel.add(new JLabel("Nome:"));
        nomePanel.add(nomeField);

        var viaPanel = new JPanel();
        viaPanel.setBackground(Color.WHITE);
        var viaField = new JTextField(15);
        viaPanel.add(new JLabel("Via:"));
        viaPanel.add(viaField);

        var civicoPanel = new JPanel();
        civicoPanel.setBackground(Color.WHITE);
        var civicoField = new JTextField(15);
        civicoPanel.add(new JLabel("Civico:"));
        civicoPanel.add(civicoField);

        var comunePanel = new JPanel();
        comunePanel.setBackground(Color.WHITE);
        var comuneField = new JTextField(15);
        comunePanel.add(new JLabel("Comune:"));
        comunePanel.add(comuneField);

        var capPanel = new JPanel();
        capPanel.setBackground(Color.WHITE);
        var capField = new JTextField(15);
        capPanel.add(new JLabel("CAP:"));
        capPanel.add(capField);

        var latPanel = new JPanel();
        latPanel.setBackground(Color.WHITE);
        var latField = new JTextField(15);
        latPanel.add(new JLabel("Latitudine:"));
        latPanel.add(latField);

        var lonPanel = new JPanel();
        lonPanel.setBackground(Color.WHITE);
        var lonField = new JTextField(15);
        lonPanel.add(new JLabel("Longitudine:"));
        lonPanel.add(lonField);

        var aggiungiBtn = new JButton("Aggiungi Fermata");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungiBtn.setAlignmentX(CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            try {
                var fermata = new FermataImpl(0,
                    nomeField.getText(),
                    viaField.getText(),
                    civicoField.getText(),
                    comuneField.getText(),
                    Integer.parseInt(capField.getText()),
                    latField.getText(),
                    lonField.getText());
                controller.addFermata(fermata);
                nomeField.setText("");
                viaField.setText("");
                civicoField.setText("");
                comuneField.setText("");
                capField.setText("");
                latField.setText("");
                lonField.setText("");
                controller.showSuccessMessage("Aggiunta fermata", "Fermata aggiunta con successo");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento fermata", ex.getMessage());
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(nomePanel);
        leftPanel.add(viaPanel);
        leftPanel.add(civicoPanel);
        leftPanel.add(comunePanel);
        leftPanel.add(capPanel);
        leftPanel.add(latPanel);
        leftPanel.add(lonPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(aggiungiBtn);

        // RIGHT PANEL: rimozione fermata
        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        JLabel removeTitle = new JLabel("Rimozione fermata", SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(CENTER_ALIGNMENT);

        fermateList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        fermateList.setMaximumSize(fermateList.getPreferredSize());

        var rimuoviBtn = new JButton("Rimuovi");
        rimuoviBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rimuoviBtn.setBackground(new Color(220, 53, 69));
        rimuoviBtn.setForeground(Color.WHITE);
        rimuoviBtn.setFocusPainted(false);
        rimuoviBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        rimuoviBtn.setAlignmentX(CENTER_ALIGNMENT);
        rimuoviBtn.addActionListener(e -> {
            if (fermateList.getSelectedIndex() != -1) {
                Fermata f = fermateMap.get(fermateList.getSelectedItem());
                if (f != null) {
                    try {
                        controller.removeFermata(f);
                        controller.showSuccessMessage("Rimozione fermata", "Fermata rimossa con successo");
                    } catch (Exception ex) {
                        controller.showErrorMessage("Errore rimozione fermata", ex.getMessage());
                        return;
                    }
                }
            }
        });

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(fermateList);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(rimuoviBtn);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    /**
     * Aggiorna la lista delle fermate disponibili per la rimozione.
     */
    public void updateFermateList(List<Fermata> fermate) {
        fermateList.removeAllItems();
        fermateMap.clear();
        fermate.forEach(f -> {
            String key = f.getNome() + " - " + f.getCodiceFermata();
            fermateMap.put(key, f);
            fermateList.addItem(key);
        });
    }
}
