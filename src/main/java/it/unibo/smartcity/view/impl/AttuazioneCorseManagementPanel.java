package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
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
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.view.api.PanelFactory;
import raven.datetime.DatePicker;

class AttuazioneCorseManagementPanel extends JPanel {

    private final JComboBox<String> linee = new JComboBox<>();
    private final JComboBox<String> orari = new JComboBox<>();
    private final JComboBox<String> autisti = new JComboBox<>();
    private final JComboBox<String> mezzi = new JComboBox<>();
    private final DatePicker datePicker = new DatePicker();

    public AttuazioneCorseManagementPanel(Controller controller) {
        super(new GridLayout(1, 1, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);

        Map<String, JComponent> components = new LinkedHashMap<>();
        components.put("Giorno:", editor);
        components.put("Linea:", linee);
        components.put("Orario:", orari);
        components.put("Mezzo:", mezzi);
        components.put("Autista:", autisti);
        var btnAggiungi = new JButton("Aggiungi");

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
                System.out.println("Hai scelto la linea " + e.getItem());
                /* TODO: Adesso mostra gli orari della linea scelta coerenti con il giorno scelto (il 14/06/2025 è un sabato)
                 * e mostra le targhe dei mezzi coerenti con il tipo di linea scelta
                 */
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
}
