package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JComponent;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.view.api.PanelFactory;

public class InsertServiceVariationPanel extends JPanel {
    private final JComboBox<String> manutenzioneLinea = new JComboBox<>();
    private final Map<String, ManutenzioneLinea> manutLineaMap = new HashMap<>();
    private final Map<String, Linea> lineaMap = new HashMap<>();
    private final JComboBox<String> codeLineaSost = new JComboBox<>();

    InsertServiceVariationPanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> components = Map.of(
            "manutenzione linea:",
            manutenzioneLinea,
            "Codice Linea sostituta:",
            codeLineaSost 
        );
        JButton btnSubmit = new JButton("Aggiungi variazione");
        btnSubmit.addActionListener(e -> {
            try {
                Linea selectedLinea = null;
                ManutenzioneLinea selectedManutenzione = null;
                if (codeLineaSost.getSelectedIndex() > 0) {
                    selectedLinea = lineaMap.get(codeLineaSost.getSelectedItem());
                }
                if (manutenzioneLinea.getSelectedIndex() > 0) {
                    selectedManutenzione = manutLineaMap.get(manutenzioneLinea.getSelectedItem());
                }
                if (selectedLinea == null || selectedManutenzione == null) {
                    controller.showMessage("Errore", "Nessuna linea o manutenzione selezionata");
                    return;
                }
                if (selectedLinea.getCodiceLinea().equals(selectedManutenzione.getCodiceLinea() )) {
                    controller.showMessage("Errore", "Le linee da sostituire e sostituta sono identiche");
                    return;
                }
                controller.addServiceVariation(
                    selectedManutenzione,
                    selectedLinea
                );
            } catch (Exception ex) {
                controller.showMessage("Errore inserimento variazione servizio", ex.getMessage());
            }
        });
        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
            "Aggiunta variazione servizio",
            components,
            btnSubmit
        );
        this.add(leftPanel);
    }

    /**
     * Aggiorna la lista delle linee disponibili per la selezione.
     */
    public void updateLineList(final List<Linea> linee) {
        lineaMap.clear();
        codeLineaSost.removeAllItems();
        linee.forEach(l -> {
            String key = l.getCodiceLinea();
            lineaMap.put(key, l);
            codeLineaSost.addItem(key);
        });
    }

    public void updateManutenzioneList(final List<ManutenzioneLinea> manutenzioni) {
        manutLineaMap.clear();
        manutenzioneLinea.removeAllItems();
        manutenzioni.forEach(m -> {
            String key = m.getCodiceLinea() + " - " + m.getDataInizio();
            manutLineaMap.put(key, m);
            manutenzioneLinea.addItem(key);
        });
    }
}
