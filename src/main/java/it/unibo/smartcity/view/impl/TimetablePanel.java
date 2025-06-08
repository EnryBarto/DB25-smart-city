package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Linea;

class TimetablePanel extends JPanel {

    private final JPanel southPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> linesList = new JComboBox<>();
    private JComponent timeTable;

    public TimetablePanel(final Controller controller) {
        super(new BorderLayout());
        this.add(southPanel, BorderLayout.NORTH);

        JLabel lbl = new JLabel("Scegli una linea");
        southPanel.add(lbl);
        linesList.setPreferredSize(new Dimension(linesList.getPreferredSize().width * 3, linesList.getPreferredSize().height));
        southPanel.add(linesList);
        JButton btn = new JButton("OK");
        btn.addActionListener(e -> {
            controller.showTimetable((String)this.linesList.getSelectedItem());
        });
        southPanel.add(btn);
    }

    public void updateLinesList(List<Linea> list) {
        this.linesList.removeAllItems();
        list.forEach(l -> this.linesList.addItem(l.getCodiceLinea()));
    }

    public void showLineTimetable(String codLinea) {
        if (timeTable != null) this.remove(timeTable);
        // TODO: Chiedi i dati al database e mostrali
        timeTable = new JLabel("Scelta linea " + codLinea);
        this.add(timeTable, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
