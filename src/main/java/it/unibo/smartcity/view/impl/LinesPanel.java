package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.SmartCityEvent;
import it.unibo.smartcity.data.InfoLinea;

class LinesPanel extends JPanel {

    private JScrollPane tableArea;
    private final static String[] columnNames = { "Linea", "Mezzo", "Da", "A", "Tempo Percorrenza [min]" };

    public LinesPanel(final Controller controller) {
        this.setLayout(new BorderLayout());
        this.setAlignmentX(CENTER_ALIGNMENT);
        var updateButton = new JButton("Aggiorna");
        updateButton.addActionListener(e -> {
            controller.handleEvent(SmartCityEvent.SHOW_LINES, null);
        });
        this.add(updateButton, BorderLayout.SOUTH);
    }

    public void updateLines(final Set<InfoLinea> linee) {
        if (this.tableArea != null) this.remove(tableArea);
        String[][] righe = linee.stream().
            map(l -> {
                var row = new String[5];
                row[0] = l.linea().getCodiceLinea();
                row[1] = l.mezzo().getNome();
                row[2] = l.partenza().getNome();
                row[3] = l.arrivo().getNome();
                row[4] = l.linea().getTempoPercorrenza() + "";
                return row;
            }).toArray(String[][]::new);
        var tabella = new JTable(righe, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
