package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.ListLineeCinqueContrDiecMul;

public class LineeControlliMultePanel extends JPanel{
    private final static String[] columnNames = {
        "Codice linea", "Visualizza Orario"
    };
    private JScrollPane tableArea;
    private final Controller controller;

    public LineeControlliMultePanel(final Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());
    }

    public void updateLineeControlliMulte(final Set<ListLineeCinqueContrDiecMul> lineeCinqueContrDiecMuls) {
        if (this.tableArea != null) this.remove(tableArea);
        Object[][] righe = lineeCinqueContrDiecMuls.stream().
            map(l -> {
                var column = new Object[2];
                column[0] = l.codiceLinea();
                var b = new JButton("Visualizza");
                b.addActionListener(e -> controller.showTimetable(l.codiceLinea()));
                column[1] = b;
                return column;
            }).toArray(Object[][]::new);
        var lineTable = new JTable(righe, columnNames);

        lineTable.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            return (JButton) value;
        });

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
        lineTable.getColumnModel().getColumn(5).setCellEditor(new MyCellEditor());
        this.tableArea = new JScrollPane(lineTable);
        this.add(tableArea, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
}
