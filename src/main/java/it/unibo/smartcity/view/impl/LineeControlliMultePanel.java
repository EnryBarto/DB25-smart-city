package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.ListLineeCinqueContrDiecMul;

class LineeControlliMultePanel extends JPanel{
    private final static String[] columnNames = {
        "Codice linea"
    };
    private JScrollPane tableArea;

    public LineeControlliMultePanel(final Controller controller) {
        this.setLayout(new BorderLayout());
    }

    public void updateLineeControlliMulte(final Set<ListLineeCinqueContrDiecMul> lineeCinqueContrDiecMuls) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = lineeCinqueContrDiecMuls.stream().
            map(l -> {
                var column = new Object[1];
                column[0] = l.codiceLinea();
                return column;
            }).toArray(Object[][]::new);
        var lineTable = new JTable(righe, columnNames);

        this.tableArea = new JScrollPane(lineTable);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
