package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.TariffaAbbonamento;
import raven.datetime.DatePicker;

class AbbonamentiPanel extends JPanel {

    static private final String[] columnTariffe = { "Nome", "Durata", "Prezzo" };
    private final JPanel buyPanel = new JPanel();
    private final Controller controller;
    private JComboBox<Integer> tariffeList = new JComboBox<>();

    public AbbonamentiPanel(Controller controller) {
        this.controller = controller;
        Preconditions.checkNotNull(controller, "Controller cannot be null");

        this.setLayout(new BorderLayout());
    }

    public void updateBuyAbbonamentiPanel(List<TariffaAbbonamento> tariffe) {
        buyPanel.removeAll();
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));
        buyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Acquisto Abbonamenti",
                0, 0, new Font("Segoe UI", Font.BOLD, 18), new Color(52, 152, 219)
            ),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        buyPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Acquista un abbonamento");
        addTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel tariffeLabel = new JLabel("Scegli la durata:");
        tariffeLabel.setAlignmentX(LEFT_ALIGNMENT);

        tariffeList.removeAllItems();
        tariffe.forEach(t -> tariffeList.addItem(t.getNum_giorni()));
        tariffeList.setMaximumSize(new Dimension(200, 30));
        tariffeList.setAlignmentX(LEFT_ALIGNMENT);

        var dataPanel = new JPanel();
        dataPanel.setBackground(Color.WHITE);
        var dataInizio = new JFormattedTextField(10);
        DatePicker datePickerInizio = new DatePicker();
        datePickerInizio.setEditor(dataInizio);
        dataPanel.add(new JLabel("Data Inizio:"));
        dataPanel.add(dataInizio);

        JButton buyBtn = new JButton("Acquista");
        buyBtn.setBackground(new Color(40, 167, 69));
        buyBtn.setForeground(Color.WHITE);
        buyBtn.setFocusPainted(false);
        buyBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        buyBtn.setAlignmentX(CENTER_ALIGNMENT);
        buyBtn.addActionListener(e -> {
            if (tariffeList.getSelectedIndex() != -1) {
                this.controller.addAbbonamento((Integer)tariffeList.getSelectedItem(), datePickerInizio.getSelectedDate());
                controller.showSuccessMessage("Acquisto abbonamento", "Abbonamento acquistato con successo");
            }
        });

        JButton visualBtn = new JButton("Visualizza Tariffe");
        visualBtn.setBackground(new Color(52, 152, 219));
        visualBtn.setForeground(Color.WHITE);
        visualBtn.setFocusPainted(false);
        visualBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        visualBtn.setAlignmentX(CENTER_ALIGNMENT);
        visualBtn.addActionListener(e -> {
            if (buyPanel.getComponentCount() > 9) buyPanel.remove(9);
            updateVisualPanel(
                buyPanel,
                tariffe,
                columnTariffe,
                t -> new Object[]{ t.getNome(), t.getNum_giorni(), t.getPrezzo() }
            );
            this.revalidate();
            this.repaint();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(buyBtn);
        buttonPanel.add(visualBtn);

        buyPanel.add(addTitle);
        buyPanel.add(Box.createVerticalStrut(15));
        buyPanel.add(tariffeLabel);
        buyPanel.add(tariffeList);
        buyPanel.add(Box.createVerticalStrut(10));
        buyPanel.add(dataPanel);
        buyPanel.add(Box.createVerticalStrut(20));
        buyPanel.add(buttonPanel);
        buyPanel.add(Box.createVerticalGlue());

        this.add(buyPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    private <T> void updateVisualPanel(
        JPanel panel,
        List<T> items,
        String[] columnNames,
        java.util.function.Function<T, Object[]> rowExtractor
    ) {
        Object[][] righe = items.stream()
            .map(elem -> rowExtractor.apply(elem))
            .toArray(Object[][]::new);
        var tabella = new JTable(righe, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //nothing is editable
            }
        };
        resizeColumnWidth(tabella);
        tabella.setFillsViewportHeight(true);
        tabella.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        var scrollPane = new JScrollPane(tabella);
        tabella.setFillsViewportHeight(true);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.add(scrollPane);
    }

    private void resizeColumnWidth(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                var comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            table.getColumnModel().getColumn(column).setPreferredWidth(width);
        }
    }
}
