package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;

public class MaintenancePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static String[] columnNames = {"Codice Linea", "Nome", "Durata Lavoro", "Linee Sostitutive"};

    private final Controller controller;
    private final JPanel southPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> optionList = new JComboBox<>();
    private JScrollPane maintenanceDetails;

    public MaintenancePanel(final Controller controller) {
        super(new BorderLayout());
        // Check that the controller is not null
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        this.add(southPanel, BorderLayout.NORTH);

        JLabel lbl = new JLabel("Scegli una opzione");
        southPanel.add(lbl);
        optionList.setPreferredSize(new Dimension(optionList.getPreferredSize().width * 3, optionList.getPreferredSize().height));
        southPanel.add(optionList);
        JButton btn = new JButton("OK");
        btn.addActionListener(e -> {
            switch ((String)this.optionList.getSelectedItem()) {
                case "5 manutenzioni più gravose" -> controller.updateManutGravose();
            }
        });
        southPanel.add(btn);
    }

    public void showManutGravose(ArrayList<ManutenzioneGravosa> manutenzioneGravose) {
        if(this.maintenanceDetails != null) this.remove(maintenanceDetails);
        Object[][] righe = manutenzioneGravose.stream()
            .map(m -> {
                var row = new Object[5];
                row[0] = m.codiceLinea();
                row[1] = m.nome();
                row[2] = m.durata_lavoro();
                row[3] = m.numLineeSostitutive();
                var b = new JButton("Visualizza");
                b.addActionListener(e -> controller.showTimetable(m.codiceLinea()));
                row[4] = b;
                return row;
            }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // La colonna dei pulsanti è l'unica modificabile
            }
        };
        // Renderer per la colonna dei pulsanti (colonna 4)
        tabella.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            return (JButton) value;
        });

        // Editor per la colonna dei pulsanti (colonna 4)
        class MyCellEditor extends AbstractCellEditor implements TableCellEditor {
            @Override
            public Object getCellEditorValue() {
                return null;
            }
            @Override
            public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return (JButton) value;
            }
        }
        tabella.getColumnModel().getColumn(4).setCellEditor(new MyCellEditor());

        this.add(maintenanceDetails, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

}
