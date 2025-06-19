package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.CausaleMulta;
import it.unibo.smartcity.model.api.Persona;
import it.unibo.smartcity.view.api.PanelFactory;

public class InsertMultaPanel extends JPanel{
    private final Map<String, Persona> personaMapper = new LinkedHashMap<>();
    private final Map<String, CausaleMulta> causaleMapper = new LinkedHashMap<>();
    private final Map<String, AttuazioneCorsa> corsaMapper = new LinkedHashMap<>();
    private final JComboBox<String> personaComboBox = new JComboBox<>();
    private final JComboBox<String> causaleMultaComboBox = new JComboBox<>();
    private final JComboBox<String> corsaComboBox = new JComboBox<>();


    public InsertMultaPanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> componentsPersona = new LinkedHashMap<>();
        JTextField cognomeField = new JTextField(30);
        JTextField nomeField = new JTextField(30);
        JTextField documentoField = new JTextField(30);
        JTextField codiceFiscaleField = new JTextField(30);

        componentsPersona.put("cognome:", cognomeField);
        componentsPersona.put("nome:", nomeField);
        componentsPersona.put("documento:", documentoField);
        componentsPersona.put("codice fiscale:", codiceFiscaleField);

        JButton btnInsertPersona = new JButton("Aggiungi persona");
        btnInsertPersona.addActionListener(e -> {
            try {
                String cognome = cognomeField.getText();
                String nome = nomeField.getText();
                String documento = documentoField.getText();
                String codiceFiscale = codiceFiscaleField.getText();

                if (cognome.isEmpty() || nome.isEmpty() || documento.isEmpty()) {
                    controller.showErrorMessage("Errore", "Tutti i campi devono essere compilati");
                    return;
                }
                controller.addPersona(cognome, nome, documento, codiceFiscale);
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento persona", ex.getMessage());
            }
        });
        PanelFactory panelFactory = new PanelFactoryImpl();
        JPanel leftPanel = panelFactory.createLeftPanel(
            "Inserimento persona",
            componentsPersona,
            btnInsertPersona
        );
        this.add(leftPanel);


        Map<String, JComponent> componentsMulta = new LinkedHashMap<>();
        JTextField importoField = new JTextField();
        componentsMulta.put("persona:", personaComboBox);
        componentsMulta.put("causale multa:", causaleMultaComboBox);
        componentsMulta.put("corsa:", corsaComboBox);
        componentsMulta.put("importo:", importoField);
        JButton btnInsertMulta = new JButton("Aggiungi multa");
        btnInsertMulta.addActionListener(e -> {
            try {
                Persona selectedPersona = personaMapper.get(personaComboBox.getSelectedItem());
                CausaleMulta selectedCausale = causaleMapper.get(causaleMultaComboBox.getSelectedItem());
                AttuazioneCorsa selectedCorsa = corsaMapper.get(corsaComboBox.getSelectedItem());
                double importo;

                try {
                    importo = Double.parseDouble(importoField.getText());
                } catch (NumberFormatException ex) {
                    controller.showErrorMessage("Errore", "Importo non valido");
                    return;
                }

                if (selectedPersona == null || selectedCausale == null || selectedCorsa == null) {
                    controller.showErrorMessage("Errore", "Tutti i campi devono essere selezionati");
                    return;
                }

                if (importo < selectedCausale.prezzoBase() || importo > selectedCausale.prezzoMassimo()) {
                    controller.showErrorMessage("Errore", "L'importo deve essere compreso tra " + selectedCausale.prezzoBase() + " e " + selectedCausale.prezzoMassimo());
                    return;
                }

                controller.addMulta(selectedPersona, selectedCausale, selectedCorsa, importo);
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento multa", ex.getMessage());
            }
        });
        JPanel rightPanel = panelFactory.createRedPanel(
            "Inserimento multa",
            componentsMulta,
            btnInsertMulta
        );

        this.add(rightPanel);
    }

    /**
     * Aggiorna la lista delle persone disponibili per la selezione.
     */
    public void updatePersonaList(final List<Persona> personaList) {
        personaMapper.clear();
        personaComboBox.removeAllItems();
        personaList.forEach(p -> {
            String key = "(" + p.getDocumento() + "): " + p.getNome() + " " + p.getCognome();
            personaMapper.put(key, p);
            personaComboBox.addItem(key);
        });
    }

    /**
     * Aggiorna la lista delle causali di multa disponibili per la selezione.
     */
    public void updateCausaleList(final List<CausaleMulta> contenuti) {
        causaleMapper.clear();
        causaleMultaComboBox.removeAllItems();
        contenuti.forEach(c -> {
            String key = c.reato();
            causaleMapper.put(key, c);
            causaleMultaComboBox.addItem(key);
        });
    }

    public void updateCorsaList(final List<AttuazioneCorsa> corse) {
        corsaMapper.clear();
        corsaComboBox.removeAllItems();
        corse.forEach(c -> {
            String key = c.getData().toString() + " - " + c.getCodiceCorsa();
            corsaMapper.put(key, c);
            corsaComboBox.addItem(key);
        });
    }
}
