package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.data.ListLineeMulte;

public class LineePiuMultePanel extends JPanel {
    private JScrollPane tableArea;
    private final static String[] columnNames = {
        "Codice Linea", "Numero di Multe"
    };

    public LineePiuMultePanel() {
        this.setLayout(new BorderLayout());
    }

    public void updateLinesWithMostFines(final Set<ListLineeMulte> lines) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = lines.stream().
            map(l -> {
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
}
