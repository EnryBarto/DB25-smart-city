package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import static com.google.common.base.Preconditions.*;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;
import raven.datetime.DatePicker;

public class MaintenancePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static String[] columnNamesLinee = {"Codice Linea", "Nome", "data inizio", "data fine", "descrizione", "partita iva"};
    private final static String[] columnNamesMezzi = {"Num immatricolazione", "Nome", "data inizio", "data fine", "descrizione", "partita iva"};
    private static final List<String> options = new ArrayList<>(List.of(
        "Mezzi",
        "Linee"
        //"attiva linee" Da utilizzare se non presenti gli automatismi nel DB
    ));

    private final Controller controller;
    private final JPanel northPanel = new JPanel(new FlowLayout());
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private final JComboBox<String> optionList = new JComboBox<>();
    private final JComboBox<String> manutList = new JComboBox<>();

    public MaintenancePanel(final Controller controller) {
        super(new BorderLayout());
        // Check that the controller is not null
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        this.add(northPanel, BorderLayout.NORTH);

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
                case "Mezzi" -> controller.updateManutMezziPanel();
                case "Linee" -> controller.updateManutLineePanel();
                //case "attiva linee" -> controller.updateAttivaLineePanel();
                default -> throw new IllegalArgumentException("Opzione non valida: " + this.optionList.getSelectedItem());
            }
        });
        northPanel.add(btn);
    }

    public void showAttivaLineePanel(List<Linea> linee) {
        clearContentExceptNorth();

        // Main panel
        var lineaPanel = new JPanel();
        lineaPanel.setLayout(new BoxLayout(lineaPanel, BoxLayout.Y_AXIS));
        lineaPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        lineaPanel.setBackground(Color.WHITE);

        // Title label
        var titleLabel = new JLabel("Attiva Linea");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(52, 73, 94));
        lineaPanel.add(titleLabel);
        lineaPanel.add(Box.createVerticalStrut(30));

        // Combo box panel
        var formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        formPanel.setBackground(Color.WHITE);
        var lineaLabel = new JLabel("Linea:");
        lineaLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JComboBox<String> combo = new JComboBox<>();
        combo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        combo.setMaximumSize(combo.getPreferredSize());
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(250, 30));
        combo.setEditable(false);
        linee.forEach(l -> combo.addItem(l.getCodiceLinea()));

        formPanel.add(lineaLabel);
        formPanel.add(combo);
        lineaPanel.add(formPanel);
        lineaPanel.add(Box.createVerticalStrut(30));

        // Button
        var aggiungiBtn = new JButton("Attiva");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 15));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        aggiungiBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            if (combo.getSelectedIndex() != -1) {
                this.controller.attivaLinea((String) combo.getSelectedItem());
            }
        });
        lineaPanel.add(aggiungiBtn);

        // Add spacing at the bottom
        lineaPanel.add(Box.createVerticalGlue());

        // Final placement
        this.add(lineaPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void showManutMezziPanel() {
        createRightPanel(
            "mezzo",
            () -> controller.getManutenzioniMezzi(),
            (nImmatricolazione, dataInizio) -> controller.removeManutMezzo(nImmatricolazione, java.sql.Date.valueOf(dataInizio.toString())),
            ManutenzioneMezzoImpl::getnImmatricolazione,
            ManutenzioneMezzoImpl::getDataInzio,
            manut -> updateVisualPanel(
                rightPanel,
                manut,
                columnNamesMezzi,
                m -> {
                    var row = new Object[columnNamesMezzi.length];
                    row[0] = m.getnImmatricolazione();
                    row[1] = m.getNome();
                    row[2] = m.getDataInzio();
                    row[3] = m.getDataFine();
                    row[4] = m.getDescrizione();
                    row[5] = m.getpIva();
                    return row;
                }
            )
        );
        createLeftPanel(
            "mezzo",
            () -> controller.getMezzi().stream().map(m -> m.nImmatricolazione() + "-" + m.nomeMezzo()).toList(),
            fields -> new ManutenzioneMezzoImpl(
                fields.comboValue(),
                java.sql.Date.valueOf(fields.dataInizio()),
                java.sql.Date.valueOf(fields.dataFine()),
                fields.nome(),
                fields.descrizione(),
                fields.partitaIva()
            ),
            manut -> controller.addManutenzioneMezzo(manut)
        );
        this.repaint();
        this.revalidate();
    }

    public void showManutLineePanel() {
        createRightPanel(
            "linea",
            () -> controller.getManutLinee(),
            (codiceLinea, dataInizio) -> controller.removeManutLinea(codiceLinea, java.sql.Date.valueOf(dataInizio.toString())),
            ManutenzioneLinea::getCodiceLinea,
            ManutenzioneLinea::getDataInizio,
            manut -> updateVisualPanel(
                rightPanel,
                manut,
                columnNamesLinee,
                m -> {
                    var row = new Object[columnNamesLinee.length];
                    row[0] = m.getCodiceLinea();
                    row[1] = m.getNome();
                    row[2] = m.getDataInizio();
                    row[3] = m.getDataFine();
                    row[4] = m.getDescrizione();
                    row[5] = m.getPIva();
                    return row;
                }
            )
        );
        createLeftPanel(
            "linea",
            () -> controller.getLinee().stream().map(l -> l.getCodiceLinea()).toList(),
            fields -> new ManutenzioneLineaImpl(
                fields.comboValue(),
                java.sql.Date.valueOf(fields.dataInizio()),
                java.sql.Date.valueOf(fields.dataFine()),
                fields.nome(),
                fields.descrizione(),
                fields.partitaIva()
            ),
            manut -> controller.addManutenzioneLinea(manut)
        );
        this.repaint();
        this.revalidate();
    }

    // Helper record to pass form data
    public record FormFields(
        String comboValue,
        LocalDate dataInizio,
        LocalDate dataFine,
        String nome,
        String descrizione,
        String partitaIva
    ) {}

    // Generic left panel creation
    private <T> void createLeftPanel(
        String title,
        Supplier<List<String>> itemsSupplier,
        java.util.function.Function<FormFields, T> buildManutenzione,
        Consumer<T> addAction
    ) {
        leftPanel.removeAll();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta manutenzione " +title, SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(CENTER_ALIGNMENT);

        var mezzoPanel = new JPanel();
        mezzoPanel.setBackground(Color.WHITE);
        JLabel partenzaLabel = new JLabel(title + ":");
        JComboBox<String> combo = new JComboBox<>();
        combo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        combo.setMaximumSize(combo.getPreferredSize());
        combo.setEditable(false);
        itemsSupplier.get().forEach(combo::addItem);
        mezzoPanel.add(partenzaLabel);
        mezzoPanel.add(combo);

        var dataPanel = new JPanel();
        dataPanel.setBackground(Color.WHITE);
        var dataInizio = new JFormattedTextField(10);
        var dataFine = new JFormattedTextField(10);
        DatePicker datePickerInizio = new DatePicker();
        datePickerInizio.setEditor(dataInizio);
        DatePicker datePickerFine = new DatePicker();
        datePickerFine.setEditor(dataFine);

        dataPanel.add(new JLabel("data inizio:"));
        dataPanel.add(dataInizio);
        dataPanel.add(Box.createHorizontalStrut(10)); // Spazio tra i due field
        dataPanel.add(new JLabel("data fine:"));
        dataPanel.add(dataFine);

        var nomePanel = new JPanel();
        nomePanel.setBackground(Color.WHITE);
        JLabel nomeLabel = new JLabel("nome:");
        JTextField nomeField = new JTextField(8);
        nomePanel.add(nomeLabel);
        nomePanel.add(nomeField);

        var descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.WHITE);
        JLabel descLabel = new JLabel("descrizione:");
        JTextField descField = new JTextField(20);
        descriptionPanel.add(descLabel);
        descriptionPanel.add(descField);

        var pIvaPanel = new JPanel();
        pIvaPanel.setBackground(Color.WHITE);
        JLabel pIvaLabel = new JLabel("partita iva (opzionale):");
        JTextField pIvaField = new JTextField(10);
        pIvaPanel.add(pIvaLabel);
        pIvaPanel.add(pIvaField);

        var aggiungiBtn = new JButton("Aggiungi Manutenzione");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungiBtn.setAlignmentX(CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            try {
                checkNotNull(combo.getSelectedIndex(), "Selezionare un " + title);
                checkNotNull(dataInizio.getText(), "Inserire la data di inizio manutenzione");
                checkArgument(!dataInizio.getText().isEmpty(), "La data di inizio manutenzione non può essere vuota");
                checkNotNull(dataFine.getText(), "Inserire la data di fine manutenzione");
                checkArgument(!dataFine.getText().isEmpty(), "La data di fine manutenzione non può essere vuota");
                checkNotNull(nomeField.getText(), "Inserire nome manutenzione");
                checkArgument(!nomeField.getText().isEmpty(), "Il nome della manutenzione non può essere vuoto");
                checkNotNull(descField.getText(), "Inserire descrizione manutenzione");
                checkArgument(!descField.getText().isEmpty(), "La descrizione della manutenzione non può essere vuota");
                if (!pIvaField.getText().isEmpty()) checkArgument(pIvaField.getText().matches("\\d{11}"), "La partita IVA deve essere un numero di 11 cifre");
                var selected = (String) combo.getSelectedItem();
                String id = selected.split("-")[0];
                var formFields = new FormFields(
                    id,
                    datePickerInizio.getSelectedDate(),
                    datePickerFine.getSelectedDate(),
                    nomeField.getText(),
                    descField.getText(),
                    pIvaField.getText().isBlank() ? null : pIvaField.getText()
                );
                T manut = buildManutenzione.apply(formFields);
                addAction.accept(manut);
                combo.setSelectedIndex(0);
                dataFine.setText("");
                dataInizio.setText("");
                descField.setText("");
                nomeField.setText("");
                JOptionPane.showMessageDialog(this, "manutenione inserita correttamente");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento manutenzione", ex.getMessage());
            }
        });

        leftPanel.add(addTitle);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(mezzoPanel);
        leftPanel.add(dataPanel);
        leftPanel.add(nomePanel);
        leftPanel.add(descriptionPanel);
        leftPanel.add(pIvaPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(aggiungiBtn);
        this.add(leftPanel, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }

    // Generic right panel creation with generics and function parameters
    private <T> void createRightPanel(
        String title,
        Supplier<List<T>> manutenzioniSupplier,
        BiConsumer<String, Date> removeAction,
        java.util.function.Function<T, String> getId,
        java.util.function.Function<T, Date> getDate,
        java.util.function.Consumer<List<T>> visualAction
    ) {
        clearContentExceptNorth();
        rightPanel.removeAll();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        var removeTitle = new JLabel("Rimozione manutenzione " +title, SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(CENTER_ALIGNMENT);

        manutList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        var manutenzioni = manutenzioniSupplier.get();
        manutList.removeAllItems();
        manutenzioni.forEach(man -> manutList.addItem(getId.apply(man) + " - " + getDate.apply(man)));
        manutList.setMaximumSize(manutList.getPreferredSize());

        var visualBtn = new JButton("Visualizza");
        visualBtn.setFont(new Font("Arial", Font.BOLD, 14));
        visualBtn.setBackground(new Color(20, 233, 39));
        visualBtn.setForeground(Color.WHITE);
        visualBtn.setFocusPainted(false);
        visualBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        visualBtn.setAlignmentX(BOTTOM_ALIGNMENT);
        visualBtn.addActionListener(e -> {
            if(manutList.getSelectedIndex() != -1) {
                if (rightPanel.getComponentCount() > 6) rightPanel.remove(6);
                var items = new ArrayList<T>();
                items.add(manutenzioni.get(manutList.getSelectedIndex()));
                visualAction.accept(items);
                this.revalidate();
                this.repaint();
            }
        });

        var rimuoviBtn = new JButton("Rimuovi");
        rimuoviBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rimuoviBtn.setBackground(new Color(220, 53, 69));
        rimuoviBtn.setForeground(Color.WHITE);
        rimuoviBtn.setFocusPainted(false);
        rimuoviBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        rimuoviBtn.setAlignmentX(BOTTOM_ALIGNMENT);
        rimuoviBtn.addActionListener(e -> {
            var selInd = manutList.getSelectedIndex();
            if (selInd != -1) {
                var manut = manutenzioni.get(selInd);
                removeAction.accept(getId.apply(manut), getDate.apply(manut));
                // Refresh list
                var updated = manutenzioniSupplier.get();
                manutList.removeAllItems();
                updated.forEach(man -> manutList.addItem(getId.apply(man) + " - " + getDate.apply(man)));
            }
        });

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(rimuoviBtn);
        buttonPanel.add(visualBtn);

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(manutList);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createVerticalGlue());

        this.add(rightPanel);
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

    private void clearContentExceptNorth() {
        this.removeAll();
        this.add(northPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

}
