package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.impl.TragittoImpl.TragittiConTempo;

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

    public void showLineTimetable(String codiceLinea, List<TragittiConTempo> tragittiConTempo, List<OrarioLinea> orariLinee) {
        if (timeTable != null) {
            this.remove(timeTable);
        }

        int numOrari = orariLinee.size();
        int numFermate = tragittiConTempo.size()+1;

        if (numOrari == 0 || numFermate == 1) {
            this.timeTable = new JLabel("Non sono previste orari e/o fermate per questa linea");
            this.add(this.timeTable, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            return;
        }

        String[] columns = new String[numOrari + 1];
        columns[0] = "Fermata";
        for (int i = 1; i <= numOrari; i++) {
            columns[i] = orariLinee.get(i-1).getGiornoSettimanale().substring(0, 3);
        }

        Object[][] data = new Object[numFermate][numOrari+1];

        for(int i=0; i < numFermate-1; i++) {
            data[i][0] = tragittiConTempo.get(i).tragitto().getPartenzaCodiceFermata();
        }
        data[numFermate-1][0] = tragittiConTempo.getLast().tragitto().getArrivoCodiceFermata();

        for(int i=1; i <= numOrari; i++) {

            LocalTime ora = orariLinee.get(i-1).getOraPartenza();
            data[0][i] = ora.toString();

            for(int j=1; j < numFermate; j++) {
                ora = ora.plusMinutes(tragittiConTempo.get(j-1).tempoPercorrenza());
                data[j][i] = ora.toString();
            }
        }

        JTable table = new JTable(data, columns);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.timeTable = scrollPane;

        this.add(timeTable, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
