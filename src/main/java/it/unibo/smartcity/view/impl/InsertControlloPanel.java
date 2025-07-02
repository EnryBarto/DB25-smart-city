package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.view.api.PanelFactory;

class InsertControlloPanel extends JPanel {
    private final Map<String, Dipendente> controlloreMapper = new LinkedHashMap<>();
    private final Map<String, AttuazioneCorsa> attuazioneCorsaMapper = new LinkedHashMap<>();
    private final JComboBox<String> controlloreComboBox = new JComboBox<>();
    private final JComboBox<String> attuazioneCorsaComboBox = new JComboBox<>();

    public InsertControlloPanel(Controller controller) {
        super(new GridLayout(1, 1, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> componentsInsert = new LinkedHashMap<>();
        componentsInsert.put("Controllore:", controlloreComboBox);
        componentsInsert.put("Attuazione Corsa:", attuazioneCorsaComboBox);

        JButton btnInsertOrario = new JButton("Assegna orario");
        btnInsertOrario.addActionListener(e -> {
            try {
                AttuazioneCorsa c = attuazioneCorsaMapper.get(attuazioneCorsaComboBox.getSelectedItem());
                Dipendente d = controlloreMapper.get(controlloreComboBox.getSelectedItem());
                if (c == null || d == null) {
                    controller.showErrorMessage("Errore", "Nessun controllore o attuazione corsa selezionato");
                    return;
                }
                controller.addControllo(c, d);
                controller.showSuccessMessage("Assegnazione Orario", "Orario assegnato con successo");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento controllo", ex.getMessage());
            }
        });
        PanelFactory panelFactory = new PanelFactoryImpl();
        JPanel leftPanel = panelFactory.createLeftPanel(
            "Assegnazione Orario al Controllore",
            componentsInsert,
            btnInsertOrario
        );
        this.add(leftPanel);
    }

    public void updateControlloreMap(List<Dipendente> controllori) {
        controlloreMapper.clear();
        controlloreComboBox.removeAllItems();
        controllori.forEach(d -> {
            var str =  d.getNome() + " " + d.getCognome() + " - " + d.getUsername();
            controlloreMapper.put(str, d);
            controlloreComboBox.addItem(str);
        });
    }

    public void updateAttuazioneCorsaMap(List<AttuazioneCorsa> attuazioni) {
        attuazioneCorsaMapper.clear();
        attuazioneCorsaComboBox.removeAllItems();
        attuazioni.forEach(a -> {
            var str = a.getData().toString() + " - " + a.getCodiceCorsa();
            attuazioneCorsaMapper.put(str, a);
            attuazioneCorsaComboBox.addItem(str);
        });
    }
}
