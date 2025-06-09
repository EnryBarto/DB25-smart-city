package it.unibo.smartcity.view.impl;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.impl.FermataImpl;
import it.unibo.smartcity.model.api.Fermata;

public class FermataManagePanel extends JPanel {

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
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(15);
        nomePanel.add(nomeLabel);
        nomePanel.add(nomeField);

        var viaPanel = new JPanel();
        viaPanel.setBackground(Color.WHITE);
        JLabel viaLabel = new JLabel("Via:");
        JTextField viaField = new JTextField(15);
        viaPanel.add(viaLabel);
        viaPanel.add(viaField);

        var capPanel = new JPanel();
        capPanel.setBackground(Color.WHITE);
        JLabel capLabel = new JLabel("CAP:");
        JTextField capField = new JTextField(15);
        capPanel.add(capLabel);
        capPanel.add(capField);

        var latPanel = new JPanel();
        latPanel.setBackground(Color.WHITE);
        JLabel latLabel = new JLabel("Latitudine:");
        JTextField latField = new JTextField(15);
        latPanel.add(latLabel);
        latPanel.add(latField);

        var lonPanel = new JPanel();
        lonPanel.setBackground(Color.WHITE);
        JLabel lonLabel = new JLabel("Longitudine:");
        JTextField lonField = new JTextField(15);
        lonPanel.add(lonLabel);
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
                    Integer.parseInt(capField.getText()),
                    latField.getText(),
                    lonField.getText());
                controller.addFermata(fermata);
                nomeField.setText("");
                viaField.setText("");
                capField.setText("");
                latField.setText("");
                lonField.setText("");
            } catch (Exception ex) {
                controller.showError("Errore inserimento fermata", ex.getMessage());
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(nomePanel);
        leftPanel.add(viaPanel);
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
                    controller.removeFermata(f);
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
            String key = f.getNome() + "-" + f.getCodiceFermata();
            fermateMap.put(key, f);
            fermateList.addItem(key);
        });
    }
}
