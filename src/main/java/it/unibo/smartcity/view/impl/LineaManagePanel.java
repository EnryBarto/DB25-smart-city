package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.view.api.PanelFactory;

class LineaManagePanel extends JPanel {

    private final static String SELECT_LABEL = "--- SELEZIONA ---";

    private final JComboBox<String> linee = new JComboBox<>();
    private final JComboBox<String> lineeUltimaTratta = new JComboBox<>();
    private final JComboBox<String> tratte = new JComboBox<>();
    private Map<String, Linea> lineeMapper;
    private Map<String, Tratta> tratteMapper;

    public LineaManagePanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> components = new LinkedHashMap<>(Map.of(
            "Linea:",
            linee,
            "Tratta:",
            tratte
        ));

        var btnAggiungiTratta = new JButton("Aggiungi tratta");

        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
            "Aggiunta tratta a linea",
            components,
            btnAggiungiTratta
        );

        var btnRimuoviTratta = new JButton("Rimuovi tratta");

        lineeUltimaTratta.addItem("Ciao");

        var rightPanel = panelFactory.createRightPanel(
            "Rimozione tratta da linea",
            lineeUltimaTratta,
            btnRimuoviTratta
        );
        this.add(leftPanel);
        this.add(rightPanel);

        this.linee.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!e.getItem().equals(SELECT_LABEL)) {
                    controller.updateTratteListPerLinea(e.getItem().toString());
                } else {
                    this.tratte.removeAllItems();
                }
            }
        });

        btnAggiungiTratta.addActionListener(e -> {
            controller.addTrattaLinea(
                (String)this.linee.getSelectedItem(),
                this.tratteMapper.get(this.tratte.getSelectedItem())
            );
        });
    }

    void updateLinesLists(List<Linea> linee, Map<Linea, Tratta> ultimeTratte) {
        this.linee.removeAllItems();
        this.lineeUltimaTratta.removeAllItems();
        this.tratte.removeAllItems();
        this.lineeMapper = new HashMap<>();
        this.linee.addItem(SELECT_LABEL);
        linee.forEach(l -> this.linee.addItem(l.getCodiceLinea()));
        ultimeTratte.forEach((l, t) -> {
            var string = l.getCodiceLinea() + ": " + t.getPartenzaCodiceFermata() + " → " + t.getArrivoCodiceFermata();
            this.lineeUltimaTratta.addItem(string);
            this.lineeMapper.put(string, l);
        });
    }

    void updateTratteList(List<Tratta> tratte) {
        this.tratte.removeAllItems();
        this.tratteMapper = new HashMap<>();
        tratte.forEach(t -> {
            var key = t.getPartenzaCodiceFermata() + " → " + t.getArrivoCodiceFermata() + " [" + t.getTempoPercorrenza() + "min]";
            tratteMapper.put(key, t);
            this.tratte.addItem(key);
        });
    }
}