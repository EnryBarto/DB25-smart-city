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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.view.api.PanelFactory;
import raven.datetime.TimePicker;

class TimetableManagePanel extends JPanel {

    private final static String SELECT_LABEL = "--- SELEZIONA ---";

    private final TimePicker timePicker;
    private final List<JCheckBox> days = new LinkedList<>();
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

        String[] giorni = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        for (String giorno : giorni) {
            JCheckBox check = new JCheckBox(giorno);
            days.add(check);
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
            boolean eseguito = false;
            var time = timePicker.getSelectedTime();
            for (var giorno: days) {
                if (giorno.isSelected() && time != null) {
                    eseguito = true;
                    try {
                        controller.addOrarioLinea((String)lineeAggiunta.getSelectedItem(), giorno.getText(), time);
                        controller.showSuccessMessage("Aggiunta orario", "Orario aggiunto con successo:\n" + giorno.getText() + " " + time);
                        giorno.setSelected(false);
                    } catch (DAOException ex) {
                        if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
                            controller.showErrorMessage("Errore", "Orario già presente per la linea e il giorno selezionato");
                        }
                    }
                }
            }
            if (eseguito) {
                timePicker.clearSelectedTime();
                controller.updateLineeInOrari();
            } else {
                controller.showErrorMessage("Errore", "Seleziona un giorno e un'orario");
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
                controller.showSuccessMessage("Rimozione orario", "Orario rimosso con successo");
            } else {
                controller.showErrorMessage("Errore", "Seleziona una linea e un orario");
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

        var rightPanel = panelFactory.createRedPanel(
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
