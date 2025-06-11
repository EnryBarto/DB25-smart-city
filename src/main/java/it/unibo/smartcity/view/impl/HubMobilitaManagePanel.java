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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;

public class HubMobilitaManagePanel extends JPanel {
    private static final String NONE_OPTION = "-- nessuna --";
    private final JComboBox<String> hubList = new JComboBox<>();
    private Map<String, HubMobilita> hubMap = new HashMap<>();
    private final JComboBox<String> fermataCombo = new JComboBox<>();
    private Map<String, Fermata> fermataMap = new HashMap<>();

    @SuppressWarnings("null")
    public HubMobilitaManagePanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        // LEFT PANEL: aggiunta hub
        var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta hub mobilità", SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        var nomePanel = new JPanel();
        nomePanel.setBackground(Color.WHITE);
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(15);
        nomePanel.add(nomeLabel);
        nomePanel.add(nomeField);

        var indirizzoPanel = new JPanel();
        indirizzoPanel.setBackground(Color.WHITE);
        JLabel indirizzoLabel = new JLabel("Indirizzo:");
        JTextField indirizzoField = new JTextField(15);
        indirizzoPanel.add(indirizzoLabel);
        indirizzoPanel.add(indirizzoField);

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

        var fermataPanel = new JPanel();
        fermataPanel.setBackground(Color.WHITE);
        JLabel fermataLabel = new JLabel("Fermata (opzionale):");
        fermataCombo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        fermataCombo.setMaximumSize(fermataCombo.getPreferredSize());
        fermataPanel.add(fermataLabel);
        fermataPanel.add(fermataCombo);

        var aggiungiBtn = new JButton("Aggiungi Hub");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungiBtn.setAlignmentX(CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            try {
                Fermata selectedFermata = null;
                if (fermataCombo.getSelectedIndex() > 0) {
                    selectedFermata = fermataMap.get(fermataCombo.getSelectedItem());
                }
                var hub = new HubMobilitaImpl(
                    0,
                    lonField.getText(),
                    latField.getText(),
                    nomeField.getText(),
                    indirizzoField.getText(),
                    selectedFermata.getCodiceFermata()
                );
                controller.addHub(hub);
                nomeField.setText("");
                indirizzoField.setText("");
                latField.setText("");
                lonField.setText("");
                fermataCombo.setSelectedIndex(0);
            } catch (Exception ex) {
                controller.showMessage("Errore inserimento hub", ex.getMessage());
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(nomePanel);
        leftPanel.add(indirizzoPanel);
        leftPanel.add(latPanel);
        leftPanel.add(lonPanel);
        leftPanel.add(fermataPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(aggiungiBtn);

        // RIGHT PANEL: rimozione hub
        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        JLabel removeTitle = new JLabel("Rimozione hub", SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(CENTER_ALIGNMENT);

        hubList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        hubList.setMaximumSize(hubList.getPreferredSize());

        var rimuoviBtn = new JButton("Rimuovi");
        rimuoviBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rimuoviBtn.setBackground(new Color(220, 53, 69));
        rimuoviBtn.setForeground(Color.WHITE);
        rimuoviBtn.setFocusPainted(false);
        rimuoviBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        rimuoviBtn.setAlignmentX(CENTER_ALIGNMENT);
        rimuoviBtn.addActionListener(e -> {
            if (hubList.getSelectedIndex() != -1) {
                HubMobilita h = hubMap.get(hubList.getSelectedItem());
                if (h != null) {
                    controller.removeHub(h);
                }
            }
        });

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(hubList);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(rimuoviBtn);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    /**
     * Aggiorna la lista degli hub disponibili per la rimozione.
     */
    public void updateHubList(final List<HubMobilita> hub) {
        this.hubMap.clear();
        hubList.removeAllItems();
        hub.forEach(h -> {
            hubList.addItem(h.getNome());
            hubMap.put(h.getNome(), h);
        });
    }

    /**
     * Aggiorna la lista delle fermate disponibili per la selezione.
     */
    public void updateFermateList(final List<Fermata> fermate) {
        fermataMap.clear();
        fermataCombo.removeAllItems();
        fermataCombo.addItem(NONE_OPTION);
        fermate.forEach(f -> {
            String key = f.getNome() + " - " + f.getCodiceFermata();;
            fermataMap.put(key, f);
            fermataCombo.addItem(key);
        });
    }
}
