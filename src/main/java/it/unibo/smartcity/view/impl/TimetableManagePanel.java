package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.view.api.PanelFactory;
import it.unibo.smartcity.view.impl.PanelFactoryImpl;
import raven.datetime.TimePicker;

class TimetableManagePanel extends JPanel {

    private final static String SELECT_LABEL = "--- SELEZIONA ---";

    private final TimePicker timePicker;
    private final List<JRadioButton> days = new LinkedList<>();
    private final JComboBox<String> lineeAggiunta = new JComboBox<>();
    private final JComboBox<String> lineeRimozione = new JComboBox<>();
    private final JComboBox<String> orarioRimozione = new JComboBox<>();
    private Map<String, OrarioLinea> orarioMapper;

    public TimetableManagePanel(final Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        var timeArea = new JFormattedTextField();

        timePicker = new TimePicker();
        timePicker.setEditor(timeArea);
        timePicker.now();

        // Usa radio button invece di checkbox per i giorni
        ButtonGroup daysGroup = new ButtonGroup();
        String[] giorni = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        for (String giorno : giorni) {
            JRadioButton radio = new JRadioButton(giorno);
            days.add(radio);
            daysGroup.add(radio);
        }

        var daysPanel = new JPanel();
        daysPanel.setLayout(new BoxLayout(daysPanel, BoxLayout.PAGE_AXIS));
        days.forEach(d -> daysPanel.add(d));

        Map<String, JComponent> componentsLeft = new LinkedHashMap<>();
        componentsLeft.put("Linea:", lineeAggiunta);
        componentsLeft.put("Ora partenza:", timeArea);
        componentsLeft.put("Giorno di validità: ", daysPanel);
        var btnAggiungi = new JButton("Aggiungi Orario");

        btnAggiungi.addActionListener(e -> {
            String giorno = null;
            for (var buttons = daysGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) giorno = button.getText();
            }
            if (giorno != null && timePicker.getSelectedTime() != null) {
                try {
                    controller.addOrarioLinea((String)lineeAggiunta.getSelectedItem(), giorno, timePicker.getSelectedTime());
                } catch (DAOException ex) {
                    if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
                        controller.showMessage("Errore", "Orario già presente per la linea e il giorno selezionato");
                    }
                }
                timePicker.clearSelectedTime();
                controller.updateLineeInOrari();
            } else {
                controller.showMessage("Errore", "Seleziona un giorno e un'orario");
            }
        });

        Map<String, JComponent> componentsRight = new LinkedHashMap<>();
        componentsRight.put("Linea:", lineeRimozione);
        componentsRight.put("Orario:", orarioRimozione);
        var btnRimuovi = new JButton("Rimuovi Orario");

        btnRimuovi.addActionListener(e -> {
            if (this.orarioRimozione.getSelectedIndex() != -1) {
                controller.removeOrario(this.orarioMapper.get(this.orarioRimozione.getSelectedItem()));
                controller.updateLineeInOrari();
                this.orarioRimozione.removeAllItems();
            } else {
                controller.showMessage("Errore", "Seleziona una linea e un orario");
            }
        });

        this.lineeRimozione.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!e.getItem().equals(SELECT_LABEL)) {
                    controller.updateOrariLineaInManagement(e.getItem().toString());
                } else {
                    this.orarioRimozione.removeAllItems();
                }
            }
        });

        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
            "Aggiunta orario a linea",
            componentsLeft,
            btnAggiungi
        );

        var rightPanel = panelFactory.createLeftPanel(
            "Rimuovi orario a linea",
            componentsRight,
            btnRimuovi
        );

        this.add(leftPanel);
        this.add(rightPanel);
    }

    void updateLinesLists(List<Linea> linee) {
        this.lineeAggiunta.removeAllItems();
        this.lineeRimozione.removeAllItems();
        this.lineeRimozione.addItem(SELECT_LABEL);
        linee.forEach(l -> {
            this.lineeAggiunta.addItem(l.getCodiceLinea());
            this.lineeRimozione.addItem(l.getCodiceLinea());
        });

    }

    public void updateOrari(List<OrarioLinea> list) {
        this.orarioRimozione.removeAllItems();
        this.orarioMapper = new HashMap<>();
        list.forEach(e -> {
            var str = e.getGiornoSettimanale() + " - " + e.getOraPartenza();
            orarioMapper.put(str, e);
            this.orarioRimozione.addItem(str);
        });
    }

}
