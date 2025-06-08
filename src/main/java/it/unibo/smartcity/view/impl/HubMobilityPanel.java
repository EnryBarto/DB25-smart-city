package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import it.unibo.smartcity.data.ListHubMobilita;

public class HubMobilityPanel extends JPanel {
    private final static String[] columnNames = {
        "Codice Hub", "Nome Hub", "Indirizzo", "Longitudine", "Latitudine", "Nome Fermata", "Tipo Contenuto", "Posti Disponibili"
    };
    private JScrollPane tableArea;

    public HubMobilityPanel() {
        this.setLayout(new BorderLayout());
    }

    public void updateHubs(final Set<ListHubMobilita> hubs) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = hubs.stream().
            map(h -> {
                var column = new Object[8];
                column[0] = h.hub().getCodiceHub();
                column[1] = h.hub().getNome();
                column[2] = h.hub().getIndirizzo();
                column[3] = h.hub().getLongitudine();
                column[4] = h.hub().getLatitudine();
                column[5] = h.nomeFermata().orElse("N/A");
                column[6] = h.tipoContenuto();
                column[7] = h.postiDisponibili();
                return column;
            }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames);
        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

}
