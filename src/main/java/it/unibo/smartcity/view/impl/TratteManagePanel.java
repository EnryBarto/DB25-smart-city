package it.unibo.smartcity.view.impl;

import javax.swing.*;

import static com.google.common.base.Preconditions.checkArgument;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.impl.TrattaImpl;

public class TratteManagePanel extends JPanel {
    private final JComboBox<String> trattaList = new JComboBox<>();
    private Map<String, Tratta> trattaMap = new HashMap<>();
    private final JComboBox<String> partenzaCombo = new JComboBox<>();
    private final JComboBox<String> arrivoCombo = new JComboBox<>();
    private Map<String, Fermata> fermataMap = new HashMap<>();

    public TratteManagePanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        // LEFT PANEL: aggiunta tratta
        var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta tratta", SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        var partenzaPanel = new JPanel();
        partenzaPanel.setBackground(Color.WHITE);
        JLabel partenzaLabel = new JLabel("Fermata di partenza:");
        partenzaCombo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        partenzaCombo.setMaximumSize(partenzaCombo.getPreferredSize());
        partenzaPanel.add(partenzaLabel);
        partenzaPanel.add(partenzaCombo);

        var arrivoPanel = new JPanel();
        arrivoPanel.setBackground(Color.WHITE);
        JLabel arrivoLabel = new JLabel("Fermata di arrivo:");
        arrivoCombo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        arrivoCombo.setMaximumSize(arrivoCombo.getPreferredSize());
        arrivoPanel.add(arrivoLabel);
        arrivoPanel.add(arrivoCombo);

        var tempoPanel = new JPanel();
        tempoPanel.setBackground(Color.WHITE);
        JLabel tempoLabel = new JLabel("Tempo di percorrenza (min):");
        JTextField tempoField = new JTextField(8);
        tempoPanel.add(tempoLabel);
        tempoPanel.add(tempoField);

        var aggiungiBtn = new JButton("Aggiungi Tratta");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungiBtn.setAlignmentX(CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            try {
                checkArgument(partenzaCombo.getSelectedIndex() != -1 && arrivoCombo.getSelectedIndex() != -1, "Selezionare entrambe le fermate");
                checkArgument(partenzaCombo.getSelectedItem() != arrivoCombo.getSelectedItem(), "Le fermate di partenza e arrivo devono essere diverse");
                Fermata partenza = fermataMap.get(partenzaCombo.getSelectedItem());
                Fermata arrivo = fermataMap.get(arrivoCombo.getSelectedItem());
                int tempo = Integer.parseInt(tempoField.getText());
                checkArgument(tempo > 0, "Il tempo di percorrenza deve essere positivo");
                var tratta = new TrattaImpl(arrivo.getCodiceFermata(), partenza.getCodiceFermata(), tempo);
                controller.addTratta(tratta);
                partenzaCombo.setSelectedIndex(0);
                arrivoCombo.setSelectedIndex(0);
                tempoField.setText("");
                controller.showSuccessMessage("Aggiunta tratta", "Tratta aggiunta con successo");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento tratta", ex.getMessage());
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(partenzaPanel);
        leftPanel.add(arrivoPanel);
        leftPanel.add(tempoPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(aggiungiBtn);

        // RIGHT PANEL: rimozione tratta
        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        JLabel removeTitle = new JLabel("Rimozione tratta", SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(CENTER_ALIGNMENT);

        trattaList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        trattaList.setMaximumSize(trattaList.getPreferredSize());

        var rimuoviBtn = new JButton("Rimuovi");
        rimuoviBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rimuoviBtn.setBackground(new Color(220, 53, 69));
        rimuoviBtn.setForeground(Color.WHITE);
        rimuoviBtn.setFocusPainted(false);
        rimuoviBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        rimuoviBtn.setAlignmentX(CENTER_ALIGNMENT);
        rimuoviBtn.addActionListener(e -> {
            if (trattaList.getSelectedIndex() != -1) {
                Tratta t = trattaMap.get(trattaList.getSelectedItem());
                if (t != null) {
                    try {
                        controller.removeTratta(t);
                        controller.showSuccessMessage("Rimozione tratta", "Tratta rimossa con successo");
                    } catch (Exception e1) {
                        controller.showErrorMessage("Errore rimozione tratta", e1.getMessage());
                    }
                }
            }
        });

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(trattaList);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(rimuoviBtn);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    /**
     * Aggiorna la lista delle fermate disponibili per la selezione.
     */
    public void updateFermateList(final List<Fermata> fermate) {
        fermataMap.clear();
        partenzaCombo.removeAllItems();
        arrivoCombo.removeAllItems();
        fermate.forEach(f -> {
            String key = f.getNome() + " - " + f.getCodiceFermata();
            fermataMap.put(key, f);
            partenzaCombo.addItem(key);
            arrivoCombo.addItem(key);
        });
    }

    /**
     * Aggiorna la lista delle tratte disponibili per la rimozione.
     */
    public void updateTratteList(final List<Tratta> tratte, final List<Fermata> fermate) {
        trattaMap.clear();
        trattaList.removeAllItems();
        tratte.forEach(t -> {
            String partenza = fermate.stream().filter(f -> f.getCodiceFermata() == t.getPartenzaCodiceFermata()).findFirst().get().getNome();
            String arrivo = fermate.stream().filter(f -> f.getCodiceFermata() == t.getArrivoCodiceFermata()).findFirst().get().getNome();
            String key = partenza
                + " → "
                + arrivo
                + " (" + t.getTempoPercorrenza() + " min)";
            trattaMap.put(key, t);
            trattaList.addItem(key);
        });
    }
}
