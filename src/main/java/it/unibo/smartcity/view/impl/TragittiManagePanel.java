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
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.view.api.PanelFactory;

class TragittiManagePanel extends JPanel {

    private final static String SELECT_LABEL = "--- SELEZIONA ---";

    private final JComboBox<String> linee = new JComboBox<>();
    private final JComboBox<String> tragittiFinali = new JComboBox<>();
    private final JComboBox<String> tratte = new JComboBox<>();
    private Map<String, Tragitto> tragittiMapper;
    private Map<String, Tratta> tratteMapper;

    public TragittiManagePanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        Map<String, JComponent> components = new LinkedHashMap<>();
        components.put("Linea:", linee);
        components.put("Tratta:", tratte);

        var btnAggiungiTragitto = new JButton("Aggiungi tratta");

        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
            "Aggiunta tratta a linea",
            components,
            btnAggiungiTragitto
        );

        var btnRimuoviTragitto = new JButton("Rimuovi tratta");

        tragittiFinali.addItem("Ciao");

        var rightPanel = panelFactory.createRightPanel(
            "Rimozione tratta da linea",
            tragittiFinali,
            btnRimuoviTragitto
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

        btnAggiungiTragitto.addActionListener(e -> {
            if (!this.linee.getSelectedItem().toString().equals(SELECT_LABEL)) {
                controller.addTragitto(
                    (String)this.linee.getSelectedItem(),
                    this.tratteMapper.get(this.tratte.getSelectedItem())
                );
            } else {
                controller.showMessage("Errore", "Seleziona una linea");
            }
        });

        btnRimuoviTragitto.addActionListener(e -> {
            if (this.tragittiFinali.getSelectedIndex() != -1) {
                controller.removeTragitto(this.tragittiMapper.get(this.tragittiFinali.getSelectedItem()));
            } else {
                controller.showMessage("Errore", "Seleziona un tragitto da rimuovere");
            }
        });
    }

    void updateLinesLists(List<Linea> linee, List<Tragitto> ultimeTratte) {
        this.linee.removeAllItems();
        this.tragittiFinali.removeAllItems();
        this.tratte.removeAllItems();
        this.tragittiMapper = new HashMap<>();
        this.linee.addItem(SELECT_LABEL);
        linee.forEach(l -> this.linee.addItem(l.getCodiceLinea()));
        ultimeTratte.forEach((t) -> {
            var string = t.getCodiceLinea() + ": " + t.getPartenzaCodiceFermata() + " → " + t.getArrivoCodiceFermata();
            this.tragittiFinali.addItem(string);
            this.tragittiMapper.put(string, t);
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