package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.ListVariazioniServizi;
import it.unibo.smartcity.model.api.Linea;

public class VariazioniServizioPanel extends JPanel {

    private JScrollPane tableArea;
    private JComboBox<String> lineeBox = new JComboBox<>();
    private Map<String, Linea> lineeMap = new LinkedHashMap<>();

    private final static String[] columnNames = {
        "Motivo manutenzione", "Descrizione manutenzione", "Data Inizio", "Data Fine", "Linee sostitutive"
    };

    public VariazioniServizioPanel(Controller controller) {
        this.setLayout(new BorderLayout());
        var selectPanel = new JPanel(new FlowLayout());
        selectPanel.add(lineeBox);
        var btnOk = new JButton("Visualizza variazioni di servizio");
        selectPanel.add(btnOk);
        this.add(selectPanel, BorderLayout.NORTH);
        btnOk.addActionListener(e -> {
            if (lineeBox.getSelectedIndex() <= 0) {
                return;
            }
            Linea selectedLinea = lineeMap.get(lineeBox.getSelectedItem());
            if (selectedLinea != null) {
                controller.updateVariazioniServizio(selectedLinea);
            }
        });
    }

    public void updateVariazioniServizio(final List<ListVariazioniServizi> variazioni) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = variazioni.stream().
            map(v -> {
                var column = new Object[5];
                column[0] = v.manutenzione().getNome();
                column[1] = v.manutenzione().getDescrizione();
                column[2] = v.manutenzione().getDataInizio();
                column[3] = v.manutenzione().getDataFine();
                column[4] = v.codiciLineeSostituite().stream().collect(Collectors.joining(", ", "[", "]"));
                return column;
            }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames);
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

    public void updateLinee(final List<Linea> linee) {
        this.lineeBox.removeAllItems();
        this.lineeMap.clear();
        linee.forEach(l -> {
            this.lineeBox.addItem(l.getCodiceLinea());
            this.lineeMap.put(l.getCodiceLinea(), l);
        });
    }
}
