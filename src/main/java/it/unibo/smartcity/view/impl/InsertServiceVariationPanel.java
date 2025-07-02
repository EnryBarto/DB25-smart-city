package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

class InsertServiceVariationPanel extends JPanel {
    private final JComboBox<String> manutenzioneLinea = new JComboBox<>();
    private final Map<String, ManutenzioneLinea> manutLineaMap = new HashMap<>();
    private final Map<String, Linea> lineaMap = new HashMap<>();
    private final JComboBox<String> codeLineaSost = new JComboBox<>();

    InsertServiceVariationPanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> components = new LinkedHashMap<>();
        components.put("manutenzione linea:", manutenzioneLinea);
        components.put("Codice Linea sostituta:", codeLineaSost);
        JButton btnSubmit = new JButton("Aggiungi variazione");
        btnSubmit.addActionListener(e -> {
            try {
                Linea selectedLinea = null;
                ManutenzioneLinea selectedManutenzione = null;
                if (codeLineaSost.getSelectedIndex() >= 0) {
                    selectedLinea = lineaMap.get(codeLineaSost.getSelectedItem());
                }
                if (manutenzioneLinea.getSelectedIndex() >= 0) {
                    selectedManutenzione = manutLineaMap.get(manutenzioneLinea.getSelectedItem());
                }
                if (selectedLinea == null || selectedManutenzione == null) {
                    controller.showErrorMessage("Errore", "Nessuna linea o manutenzione selezionata");
                    return;
                }
                if (selectedLinea.getCodiceLinea().equals(selectedManutenzione.getCodiceLinea() )) {
                    controller.showErrorMessage("Errore", "Le linee da sostituire e sostituta sono identiche");
                    return;
                }
                controller.addServiceVariation(
                    selectedManutenzione,
                    selectedLinea
                );
                controller.showSuccessMessage("Aggiunta variazione servizio", "Variazione servizio aggiunta con successo");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento variazione servizio", ex.getMessage());
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
     * Updates the line list available for selection. Only ordinary lines are shown.
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
