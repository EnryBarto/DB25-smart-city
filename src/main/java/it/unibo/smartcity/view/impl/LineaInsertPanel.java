package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.TipologiaMezzo;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.view.api.PanelFactory;
import raven.datetime.DatePicker;

public class LineaInsertPanel extends JPanel {
    private final JComboBox<String> mezzi = new JComboBox<>();
    private final JComboBox<String> selectableTratte = new JComboBox<>();
    private final Map<String, Tratta> tratteMapper = new HashMap<>();
    private final Map<String, Tratta> selectableTratteMap = new HashMap<>();
    private final Map<String, TipologiaMezzo> mezziMapper = new HashMap<>();
    private final List<Tratta> selectedTratte = new LinkedList<>();
    private final DatePicker datePickerInizio = new DatePicker();
    private final DatePicker datePickerFine = new DatePicker();

    public LineaInsertPanel(Controller c) {
        super(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        var innerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        innerPanel.setBackground(new Color(245, 249, 255));

        JTextField codiceLineaField = new JTextField(30);
        JCheckBox straordinariaCheck = new JCheckBox();
        JFormattedTextField inizioValiditaField = new JFormattedTextField();
        JFormattedTextField fineValiditaField = new JFormattedTextField();
        datePickerInizio.setEditor(inizioValiditaField);
        datePickerFine.setEditor(fineValiditaField);
        inizioValiditaField.setColumns(30);
        fineValiditaField.setColumns(30);
        inizioValiditaField.setEnabled(false);
        fineValiditaField.setEnabled(false);
        fineValiditaField.setEnabled(false);

        straordinariaCheck.addActionListener(e -> {
            inizioValiditaField.setEnabled(straordinariaCheck.isSelected());
            fineValiditaField.setEnabled(straordinariaCheck.isSelected());
        });

        Map<String, JComponent> componentsInserisciLinea = new LinkedHashMap<>();
        componentsInserisciLinea.put("codice linea:", codiceLineaField);
        componentsInserisciLinea.put("mezzo:", mezzi);
        componentsInserisciLinea.put("straordinaria:", straordinariaCheck);
        componentsInserisciLinea.put("inizio validità:", inizioValiditaField);
        componentsInserisciLinea.put("fine validità:", fineValiditaField);

        var btnEliminaUltimaTratta = new JButton("Elimina ultima tratta");
        var btnAggiungiLinea = new JButton("Aggiungi linea");

        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
                "Aggiunta linea",
                componentsInserisciLinea,
                btnEliminaUltimaTratta);

        var selectedTratteArea = new JTextArea();

        var btnSelezionaTratta = new JButton("Aggiungi tratta");
        btnEliminaUltimaTratta.addActionListener(e -> {
            if (!selectedTratte.isEmpty()) {
                selectedTratte.removeLast();
                updateTratte();
                updateSelectedTratteArea(selectedTratteArea);
            }
        });

        btnSelezionaTratta.addActionListener(e -> {
            Tratta selectedTratta;
            if (selectableTratte.getSelectedIndex() >= 0) {
                selectedTratta = tratteMapper.get(selectableTratte.getSelectedItem());
            } else {
                c.showMessage("Errore", "Seleziona una tratta valida.");
                return;
            }
            selectedTratte.add(selectedTratta);
            updateTratte();
            updateSelectedTratteArea(selectedTratteArea);
        });

        Map<String, JComponent> componentsSelezionaTratta = new LinkedHashMap<>();
        componentsSelezionaTratta.put("Tratte:", selectableTratte);
        componentsSelezionaTratta.put("Tratte selezionate:", selectedTratteArea);

        var rightPanel = panelFactory.createGreenPanel(
                "Seleziona tratta",
                componentsSelezionaTratta,
                btnSelezionaTratta);

        makeButtonFancy(btnAggiungiLinea);

        btnAggiungiLinea.addActionListener(e -> {
            String codiceLinea = codiceLineaField.getText();
            TipologiaMezzo mezzo = mezziMapper.get(mezzi.getSelectedItem());
            boolean straordinaria = straordinariaCheck.isSelected();
            Date inizioValidita;
            Date fineValidita;
            if (codiceLinea.isEmpty() || mezzo == null || selectedTratte.isEmpty()) {
                c.showMessage("Errore", "Compila tutti i campi obbligatori e seleziona almeno una tratta.");
                return;
            }
            if (straordinaria) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataFormattataInizio = datePickerInizio.getSelectedDate().format(formatter);
                String dataFormattataFine = datePickerFine.getSelectedDate().format(formatter);
                inizioValidita = Date.valueOf(dataFormattataInizio);
                fineValidita = Date.valueOf(dataFormattataFine);
            } else {
                inizioValidita = Date.valueOf("1970-01-01");
                fineValidita = Date.valueOf("1970-01-02");
            }
            Linea linea = new LineaImpl(codiceLinea, 0, inizioValidita, fineValidita, true,
                    mezzo.getCodiceTipoMezzo());
            c.addLinea(linea, selectedTratte, straordinaria);
        });

        innerPanel.add(leftPanel);
        innerPanel.add(rightPanel);
        this.add(innerPanel, BorderLayout.CENTER);
        this.add(btnAggiungiLinea, BorderLayout.SOUTH);
    }

    void updateTratteList(List<Tratta> tratte) {
        this.selectableTratte.removeAllItems();
        this.tratteMapper.clear();
        tratte.forEach(t -> {
            var key = t.getPartenzaCodiceFermata() + " → " + t.getArrivoCodiceFermata() + " [" + t.getTempoPercorrenza()
                    + "min]";
            tratteMapper.put(key, t);
        });
        this.updateTratte();
    }

    void updateMezziList(List<TipologiaMezzo> mezzi) {
        this.mezzi.removeAllItems();
        this.mezziMapper.clear();
        mezzi.forEach(m -> {
            String key = m.getNome();
            mezziMapper.put(key, m);
            this.mezzi.addItem(key);
        });
    }

    /**
     * It updates the list of possible tratte based on the last selected
     */
    private void updateTratte() {
        this.selectableTratteMap.clear();
        this.selectableTratte.removeAllItems();
        if (selectedTratte.isEmpty()) {
            this.selectableTratteMap.putAll(tratteMapper);
        } else {
            this.selectableTratteMap.putAll(
                    this.tratteMapper.entrySet().stream()
                            .filter(e -> e.getValue().getPartenzaCodiceFermata() == selectedTratte.getLast()
                                    .getArrivoCodiceFermata())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
        this.selectableTratteMap.forEach((k, v) -> this.selectableTratte.addItem(k));
    }

    private void updateSelectedTratteArea(JTextArea selectedTratteArea) {
        StringBuilder sb = new StringBuilder();
        selectedTratte.forEach(t -> {
            sb.append(t.getPartenzaCodiceFermata())
                    .append(" → ")
                    .append(t.getArrivoCodiceFermata())
                    .append("\n");
        });
        selectedTratteArea.setText(sb.toString());
    }

    private void makeButtonFancy(JButton button) {
            button.setBackground(new Color(76, 175, 80));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            button.setFont(button.getFont().deriveFont(16f));
            button.setOpaque(true);
            button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(56, 142, 60));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(76, 175, 80));
                }
            });
        }
}
