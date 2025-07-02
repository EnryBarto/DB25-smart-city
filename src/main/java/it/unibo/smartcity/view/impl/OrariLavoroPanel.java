package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.OrarioLavoro;

class OrariLavoroPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JScrollPane tableArea;
    private final static String[] columnNames = {"Data", "Giorno", "Linea", "Ora Partenza", "Visualizza"};
    private final Controller controller;

    public OrariLavoroPanel(final Controller controller) {
        this.setLayout(new BorderLayout());
        Preconditions.checkNotNull(controller, "Il controller non può essere null");
        this.controller = controller;
    }

    public void updateOrariLavoro(final List<OrarioLavoro> orariLavoro) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = orariLavoro.stream().
            map(e -> {
                var row = new Object[5];
                row[0] = e.data();
                row[1] = e.orarioLinea().getGiornoSettimanale();
                row[2] = e.orarioLinea().getCodiceLinea();
                row[3] = e.orarioLinea().getOraPartenza();
                var b = new JButton("Visualizza");
                b.addActionListener(e1 -> controller.showTimetable(e.orarioLinea().getCodiceLinea()));
                row[4] = b;
                return row;
            }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // La colonna dei pulsanti è l'unica modificabile
            }
        };
        // Renderer per la colonna dei pulsanti (colonna 5)
        tabella.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            return (JButton) value;
        });

        // Editor per la colonna dei pulsanti (colonna 5)
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

        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }

}
