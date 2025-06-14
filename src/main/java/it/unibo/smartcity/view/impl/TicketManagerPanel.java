package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.Biglietto;
import it.unibo.smartcity.model.api.TariffaBiglietto;

public class TicketManagerPanel extends JPanel{


    private static final long serialVersionUID = 1L;
    private static final String[] columnTariffe = {"nome", "durata", "prezzo"};
    private static final List<String> options = new ArrayList<>(List.of(
        "acquisto biglietti",
        "convalida biglietti"
    ));

    private final JPanel northPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> optionList = new JComboBox<>();
    private final Controller controller;

    private JPanel buyPanel = new JPanel();
    private JPanel validatePanel = new JPanel();
    private JComboBox<Integer> tariffeList = new JComboBox<>();
    private JComboBox<Integer> bigliettiList = new JComboBox<>();
    private JComboBox<Integer> corseList = new JComboBox<>();

    public TicketManagerPanel(Controller controller) {
        this.controller = controller;
        Preconditions.checkNotNull(controller, "Controller cannot be null");

        this.setLayout(new BorderLayout());
        createNorthPanel();
    }

    public void updateBuyTicketPanel(ArrayList<TariffaBiglietto> tariffe) {
        clearContentExceptNorth();
        buyPanel.removeAll();
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));
        buyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Acquisto Biglietti",
                0, 0, new Font("Segoe UI", Font.BOLD, 18), new Color(52, 152, 219)
            ),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        buyPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Acquista un biglietto");
        addTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel tariffeLabel = new JLabel("Scegli la durata:");
        tariffeLabel.setAlignmentX(LEFT_ALIGNMENT);

        tariffeList.removeAllItems();
        tariffe.forEach(t -> tariffeList.addItem(t.getDurata()));
        tariffeList.setMaximumSize(new Dimension(200, 30));
        tariffeList.setAlignmentX(LEFT_ALIGNMENT);

        JButton buyBtn = new JButton("Acquista");
        buyBtn.setBackground(new Color(40, 167, 69));
        buyBtn.setForeground(Color.WHITE);
        buyBtn.setFocusPainted(false);
        buyBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        buyBtn.setAlignmentX(CENTER_ALIGNMENT);
        buyBtn.addActionListener(e -> {
            if (tariffeList.getSelectedIndex() != -1) {
                this.controller.addBiglietto((Integer)tariffeList.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Biglietto acquistato con successo");
            }
        });

        JButton visualBtn = new JButton("Visualizza Tariffe");
        visualBtn.setBackground(new Color(52, 152, 219));
        visualBtn.setForeground(Color.WHITE);
        visualBtn.setFocusPainted(false);
        visualBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        visualBtn.setAlignmentX(CENTER_ALIGNMENT);
        visualBtn.addActionListener(e -> {
            if (buyPanel.getComponentCount() > 7) buyPanel.remove(7);
            updateVisualPanel(
                buyPanel,
                tariffe,
                columnTariffe,
                t -> new Object[]{ t.getNome(), t.getDurata(), t.getPrezzo() }
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
        buyPanel.add(Box.createVerticalStrut(20));
        buyPanel.add(buttonPanel);
        buyPanel.add(Box.createVerticalGlue());

        this.add(buyPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void updateValidateTicketPanel(List<Biglietto> biglietti, List<AttuazioneCorsa> corse) {
        clearContentExceptNorth();
        validatePanel.removeAll();
        validatePanel.setLayout(new BoxLayout(validatePanel, BoxLayout.Y_AXIS));
        validatePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Convalida Biglietto",
                0, 0, new Font("Segoe UI", Font.BOLD, 18), new Color(52, 152, 219)
            ),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        validatePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Convalida un biglietto");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(52, 152, 219));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel bigliettoLabel = new JLabel("Scegli il biglietto:");
        bigliettoLabel.setAlignmentX(LEFT_ALIGNMENT);

        biglietti.forEach(b -> bigliettiList.addItem(b.getCodice()));
        bigliettiList.setMaximumSize(new Dimension(250, 30));
        bigliettiList.setAlignmentX(LEFT_ALIGNMENT);

        JLabel corsaLabel = new JLabel("Scegli la corsa:");
        corsaLabel.setAlignmentX(LEFT_ALIGNMENT);

        corse.forEach(c -> corseList.addItem(c.getCodiceCorsa()));
        corseList.setMaximumSize(new Dimension(250, 30));
        corseList.setAlignmentX(LEFT_ALIGNMENT);

        JButton validateBtn = new JButton("Convalida");
        validateBtn.setBackground(new Color(40, 167, 69));
        validateBtn.setForeground(Color.WHITE);
        validateBtn.setFocusPainted(false);
        validateBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        validateBtn.setAlignmentX(CENTER_ALIGNMENT);
        validateBtn.setToolTipText("Convalida il biglietto selezionato sulla corsa scelta");

        validateBtn.addActionListener(e -> {
            if (bigliettiList.getSelectedIndex() != -1 && corseList.getSelectedIndex() != -1) {
                int selectedBiglietto = (Integer)bigliettiList.getSelectedItem();
                int selectedCorsa = (Integer)corseList.getSelectedItem();
                this.controller.validateBiglietto(selectedBiglietto, selectedCorsa);
                JOptionPane.showMessageDialog(this, "Biglietto convalidato con successo");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(validateBtn);

        validatePanel.add(title);
        validatePanel.add(Box.createVerticalStrut(15));
        validatePanel.add(bigliettoLabel);
        validatePanel.add(bigliettiList);
        validatePanel.add(Box.createVerticalStrut(10));
        validatePanel.add(corsaLabel);
        validatePanel.add(corseList);
        validatePanel.add(Box.createVerticalStrut(20));
        validatePanel.add(buttonPanel);
        validatePanel.add(Box.createVerticalGlue());

        this.add(validatePanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void createNorthPanel(){
        JLabel lbl = new JLabel("Scegli una opzione");
        northPanel.add(lbl);
        optionList.setPreferredSize(new Dimension(optionList.getPreferredSize().width * 3, optionList.getPreferredSize().height));
        //adding options to the JComboBox
        options.forEach(op -> optionList.addItem(op));
        northPanel.add(optionList);
        //adding a button to the north panel
        JButton btn = new JButton("OK");
        //selecting the option from the JComboBox and calling the appropriate method in the controller
        btn.addActionListener(e -> {
            switch ((String)this.optionList.getSelectedItem()) {
                case "acquisto biglietti" -> controller.updateBuyTicket();
                case "convalida biglietti" -> controller.updateValidateTicket();
                default -> throw new IllegalArgumentException("Opzione non valida: " + this.optionList.getSelectedItem());
            }
        });
        northPanel.add(btn);
        this.add(northPanel, BorderLayout.NORTH);
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

    private void clearContentExceptNorth() {
        this.removeAll();
        this.add(northPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }
}
