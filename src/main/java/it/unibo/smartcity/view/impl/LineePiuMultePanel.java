package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.ListLineeMulte;
import raven.datetime.DatePicker;

class LineePiuMultePanel extends JPanel {
    private JScrollPane tableArea;
    private DatePicker datePickerFrom = new DatePicker();
    private DatePicker datePickerTo = new DatePicker();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final static String[] columnNames = {
            "Codice Linea", "Numero di Multe"
    };

    public LineePiuMultePanel(Controller c) {
        this.setLayout(new BorderLayout());
        var headerPanel = new JPanel(new FlowLayout());
        var searchButton = new JButton("Cerca");
        JFormattedTextField dateFromField = new JFormattedTextField();
        JFormattedTextField dateToField = new JFormattedTextField();
        datePickerFrom.setEditor(dateFromField);
        datePickerTo.setEditor(dateToField);
        headerPanel.add(new JLabel("Data Inizio: "));
        headerPanel.add(dateFromField);
        headerPanel.add(new JLabel("Data Fine: "));
        headerPanel.add(dateToField);
        headerPanel.add(searchButton);
        searchButton.addActionListener(e -> {
            if (datePickerFrom.getSelectedDate() != null && datePickerTo.getSelectedDate() != null) {
                var dateFrom = getDateFrom();
                var dateTo = getDateTo();
                if(dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
                    c.updateLineeMulte(dateFrom, dateTo);
                    return;
                }
            }
            c.showErrorMessage("Errore", "Seleziona un intervallo di date valido");
        });
        this.add(headerPanel, BorderLayout.NORTH);
    }

    public void updateLinesWithMostFines(final List<ListLineeMulte> lines) {
        if (this.tableArea != null)
            this.remove(tableArea);
        Object[][] righe = lines.stream().map(l -> {
            var column = new Object[2];
            column[0] = l.codiceLinea();
            column[1] = l.numeroMulte();
            return column;
        }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames);
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

    public Date getDateFrom() {
        return Date.valueOf(datePickerFrom.getSelectedDate().format(DATE_FORMAT));
    }

    public Date getDateTo() {
        return Date.valueOf(datePickerTo.getSelectedDate().format(DATE_FORMAT));
    }
}
