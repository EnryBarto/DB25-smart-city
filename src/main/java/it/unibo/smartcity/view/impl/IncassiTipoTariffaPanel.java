package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.IncassiTariffa;
import it.unibo.smartcity.model.api.TariffaBiglietto;
import raven.datetime.DatePicker;

class IncassiTipoTariffaPanel extends JPanel {
    private JScrollPane tableArea;
    private DatePicker datePickerFrom = new DatePicker();
    private DatePicker datePickerTo = new DatePicker();
    private JComboBox<String> tariffeList = new JComboBox<>();
    private HashMap<String, TariffaBiglietto> tariffeMapper = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final static String[] columnNames = {
            "Tariffa", "Inizio periodo", "Fine periodo", "Incassi"
    };

    public IncassiTipoTariffaPanel(final Controller c) {
        this.setLayout(new BorderLayout());
        var headerPanel = new JPanel(new FlowLayout());
        var searchButton = new JButton("Cerca");
        JFormattedTextField dateFromField = new JFormattedTextField();
        JFormattedTextField dateToField = new JFormattedTextField();
        datePickerFrom.setEditor(dateFromField);
        datePickerTo.setEditor(dateToField);
        List<TariffaBiglietto> tar = c.getTariffeBiglietti();

        tar.forEach(t -> {
            var key = t.getNome() + " [" + t.getDurata() + " min]";
            tariffeMapper.put(key, t);
            tariffeList.addItem(key);
        });

        headerPanel.add(new JLabel("Tariffa: "));
        headerPanel.add(tariffeList);
        headerPanel.add(new JLabel("Data Inizio: "));
        headerPanel.add(dateFromField);
        headerPanel.add(new JLabel("Data Fine: "));
        headerPanel.add(dateToField);
        headerPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            if (datePickerFrom.getSelectedDate() != null && datePickerTo.getSelectedDate() != null && tariffeList.getSelectedIndex() != -1) {
                var dateFrom = getDateFrom();
                var dateTo = getDateTo();
                var tariffa = tariffeMapper.get(tariffeList.getSelectedItem());
                if(dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
                    c.updateIncassiTariffa(tariffa, dateFrom, dateTo);
                    return;
                }
            }
            c.showErrorMessage("Errore", "Seleziona una tariffa e un intervallo di date validi");
        });
        this.add(headerPanel, BorderLayout.NORTH);
    }

    public void updateIncassiTariffa(final IncassiTariffa incassi) {
        if (this.tableArea != null)
            this.remove(tableArea);

        Object[][] riga = new Object[1][4];
        riga[0][0] = incassi.tariffa().getNome() + " [" + incassi.tariffa().getDurata() + " min] - " + incassi.tariffa().getPrezzo() + "€";
        riga[0][1] = incassi.dateFrom();
        riga[0][2] = incassi.dateTo();
        riga[0][3] = incassi.importo() + "€";

        var tabella = new JTable(riga, columnNames);
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

    private Date getDateFrom() {
        return Date.valueOf(datePickerFrom.getSelectedDate().format(DATE_FORMAT));
    }

    private Date getDateTo() {
        return Date.valueOf(datePickerTo.getSelectedDate().format(DATE_FORMAT));
    }
}
