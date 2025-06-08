package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.InfoLinea;

class LinesPanel extends JPanel {

    private JScrollPane tableArea;
    private final static String[] columnNames = { "Linea", "Mezzo", "Da", "A", "Tempo Percorrenza [min]", "Orario" };
    private final Controller controller;

    public LinesPanel(final Controller controller) {
        this.setLayout(new BorderLayout());
        this.controller = controller;
    }

    public void updateLines(final List<InfoLinea> linee) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = linee.stream().
            map(l -> {
                var row = new Object[6];
                row[0] = l.linea().getCodiceLinea();
                row[1] = l.mezzo().getNome();
                row[2] = l.partenza().getNome();
                row[3] = l.arrivo().getNome();
                row[4] = l.linea().getTempoPercorrenza() + "";
                var b = new JButton("Visualizza");
                b.addActionListener(e -> controller.showTimetable(l.linea().getCodiceLinea()));
                row[5] = b;
                return row;
            }).toArray(Object[][]::new);

        var tabella = new JTable(righe, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        // Renderer per la colonna dei pulsanti (colonna 5)
        tabella.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
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
        tabella.getColumnModel().getColumn(5).setCellEditor(new MyCellEditor());

        this.tableArea = new JScrollPane(tabella);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
