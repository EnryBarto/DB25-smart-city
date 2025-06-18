package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.data.LineaPiuHubMobilita;

class LineaMaggioriHubPanel extends JPanel{
    private final static String[] columnNames = {
        "Codice linea", "Numero Hub"
    };
    private JScrollPane tableArea;

    public LineaMaggioriHubPanel() {
        this.setLayout(new BorderLayout());
    }

    public void updateLineaPiuHub(final LineaPiuHubMobilita res) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = new Object[1][2];
        righe[0][0] = res.codLinea();
        righe[0][1] = res.numHub();
        var lineTable = new JTable(righe, columnNames);

        this.tableArea = new JScrollPane(lineTable);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
