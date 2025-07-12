package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.data.MediaSoldiMulte;

class MediaSoldiMultePanel extends JPanel {
    private final static String[] columnNames = {
        "Media di soldi spesi in multe",
    };
    private JScrollPane tableArea;

    public MediaSoldiMultePanel() {
        this.setLayout(new BorderLayout());
    }

    public void update(final MediaSoldiMulte mediaSoldiMulte) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = new Object[][] {
            { mediaSoldiMulte.mediaSoldi() }
        };
        var tabella = new JTable(righe, columnNames);
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
