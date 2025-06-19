package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.Mezzo;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.view.api.PanelFactory;
import raven.datetime.DatePicker;

class AttuazioneCorseManagementPanel extends JPanel {

    private final JComboBox<String> linee = new JComboBox<>();
    private final JComboBox<String> orari = new JComboBox<>();
    private final JComboBox<String> autisti = new JComboBox<>();
    private final JComboBox<String> mezzi = new JComboBox<>();
    private final DatePicker datePicker = new DatePicker();
    private Map<String, Dipendente> autistiMapper;
    private Map<String, OrarioLinea> orariMapper;

    public AttuazioneCorseManagementPanel(Controller controller) {
        super(new GridLayout(1, 1, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);

        Map<String, JComponent> components = new LinkedHashMap<>();
        components.put("Giorno:", editor);
        components.put("Linea:", linee);
        components.put("Orario partenza:", orari);
        components.put("Mezzo:", mezzi);
        components.put("Autista:", autisti);
        var btnAggiungi = new JButton("Aggiungi");

        btnAggiungi.addActionListener(e -> {
            if (orari.getSelectedIndex() != -1 && mezzi.getSelectedIndex() != -1 && autisti.getSelectedIndex() != -1) {
                controller.addAttuazioneCorsa(
                    datePicker.getSelectedDate(),
                    orariMapper.get(this.orari.getSelectedItem()),
                    autistiMapper.get(this.autisti.getSelectedItem()),
                    (String)this.mezzi.getSelectedItem()
                );
                controller.updateLineeAttuazioneCorse(datePicker.getSelectedDate());
            } else {
                controller.showErrorMessage("Errore", "Imposta tutti i campi");
            }
        });

        datePicker.addDateSelectionListener(e -> {
            var data = datePicker.getSelectedDate();
            if (data != null) {
                controller.updateLineeAttuazioneCorse(data);
            } else {
                this.linee.removeAllItems();
            }
        });

        linee.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.updateOrariLineaAttuazioneCorse((String)e.getItem(), datePicker.getSelectedDate());
                controller.updateMezziAttuazioneCorse((String)e.getItem(), datePicker.getSelectedDate());
            }
        });

        PanelFactory panelFactory = new PanelFactoryImpl();
        var addPanel = panelFactory.createLeftPanel(
            "Aggiunta attuazione corsa",
            components,
            btnAggiungi
        );

        this.add(addPanel);
    }

    public void updateLineeList(List<Linea> lineeAttive) {
        this.linee.removeAllItems();
        lineeAttive.stream()
            .map(Linea::getCodiceLinea)
            .forEach(linee::addItem);
    }

    public void updateOrariList(List<OrarioLinea> orari) {
        this.orari.removeAllItems();
        this.orariMapper = new HashMap<>();
        orari.forEach(o -> this.orariMapper.put(o.getOraPartenza().toString(), o));
        orari.stream()
            .map(OrarioLinea::getOraPartenza)
            .map(LocalTime::toString)
            .forEach(this.orari::addItem);
    }

    public void updateMezziList(List<Mezzo> mezzi) {
        this.mezzi.removeAllItems();
        mezzi.stream()
            .map(Mezzo::getnImmatricolazione)
            .forEach(this.mezzi::addItem);
    }

    public void updateAutistiList(List<Dipendente> autisti) {
        this.autisti.removeAllItems();
        this.autistiMapper = new HashMap<>();
        autisti.forEach(a -> {
            var str = a.getCognome() + " " + a.getNome();
            this.autistiMapper.put(str, a);
            this.autisti.addItem(str);
        });
    }
}
