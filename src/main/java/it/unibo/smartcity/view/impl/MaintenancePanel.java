package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

import static com.google.common.base.Preconditions.*;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;

public class MaintenancePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static String[] columnNamesManutGravose = {"Codice Linea", "Nome", "Durata Lavoro", "Num Linee Sostitutive", "Visualizza"};
    private final static String[] columnNamesLinee = {"Codice Linea", "Nome", "data inizio", "data fine", "descrizione", "partita iva", "Visualizza"};
    private final static String[] columnNamesMezzi = {"Num immatricolazione", "nome", "data inizio", "data fine", "descrizione", "partita iva", "Visualizza"};
    private static final List<String> options = new ArrayList<>(List.of(
        "estrai 5 manutenzioni più gravose",
        "manutenzione mezzi",
        "manutenzione linee"
    ));

    private final Controller controller;
    private final JPanel northPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> optionList = new JComboBox<>();
    private final JComboBox<String> manutList = new JComboBox<>();
    private JScrollPane maintenanceDetails;

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
                case "estrai 5 manutenzioni più gravose" -> controller.updateManutGravose();
                case "manutenzione mezzi" -> controller.updateManutMezziPanel();
                case "manutenzione linee" -> controller.updateManutLineePanel();
                default -> throw new IllegalArgumentException("Opzione non valida: " + this.optionList.getSelectedItem());
            }
        });
        northPanel.add(btn);
    }

    public void showManutGravose(ArrayList<ManutenzioneGravosa> manutenzioneGravose) {
        clearContentExceptNorth();
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

        var tabella = new JTable(righe, columnNamesManutGravose) {
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

    // Generic left panel creation
    private <T> void createLeftPanel(
        String title,
        Supplier<List<String>> itemsSupplier,
        java.util.function.Function<FormFields, T> buildManutenzione,
        Consumer<T> addAction
    ) {
        clearContentExceptNorth();
        var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta manutenzione", SwingConstants.CENTER);
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
        JLabel arrivoLabel = new JLabel("data inizio:");
        var dataInizio = new JTextField(10);
        var dataFine = new JTextField(10);
        dataPanel.add(arrivoLabel);
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
                var formFields = new FormFields(
                    (String) combo.getSelectedItem(),
                    dataInizio.getText(),
                    dataFine.getText(),
                    nomeField.getText(),
                    descField.getText(),
                    pIvaField.getText()
                );
                T manut = buildManutenzione.apply(formFields);
                addAction.accept(manut);
                combo.setSelectedIndex(0);
                dataFine.setText("");
                dataInizio.setText("");
                descField.setText("");
                nomeField.setText("");
            } catch (Exception ex) {
                controller.showError("Errore inserimento manutenzione", ex.getMessage());
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
        java.util.function.Consumer<T> visualAction
    ) {
        clearContentExceptNorth();
        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        var removeTitle = new JLabel("Rimozione " + title, SwingConstants.CENTER);
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
                visualAction.accept(manutenzioni.get(manutList.getSelectedIndex()));
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
        T manut,
        String[] columnNames,
        java.util.function.Function<T, Object[]> rowExtractor
    ) {
        var visualPanel = new JPanel();
        Object[][] righe = new Object[][]{ rowExtractor.apply(manut) };
        var tabella = new JTable(righe, columnNames);
        visualPanel.add(tabella);
        panel.add(visualPanel, BorderLayout.CENTER);
    }

    private void clearContentExceptNorth() {
        this.removeAll();
        this.add(northPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    public void showManutMezziPanel() {
        createRightPanel(
            "mezzo",
            () -> controller.getManutenzioniMezzi(),
            (nImmatricolazione, dataInizio) -> controller.removeManutMezzo(nImmatricolazione, dataInizio),
            ManutenzioneMezzoImpl::getnImmatricolazione,
            ManutenzioneMezzoImpl::getDataInzio,
            manut -> updateVisualPanel(
                this,
                manut,
                columnNamesMezzi,
                m -> new Object[]{
                    m.getnImmatricolazione(),
                    m.getNome(),
                    m.getDataInzio(),
                    m.getDataFine(),
                    m.getpIva()
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
            (codiceLinea, dataInizio) -> controller.removeManutLinea(codiceLinea, dataInizio),
            ManutenzioneLineaImpl::getCodiceLinea,
            ManutenzioneLineaImpl::getDataInizio,
            manut -> updateVisualPanel(
                this,
                manut,
                columnNamesLinee,
                l -> new Object[] {
                    l.getCodiceLinea(),
                    l.getNome(),
                    l.getDataInizio(),
                    l.getDataFine(),
                    l.getDescrizione(),
                    l.getPIva()
                }
            )
        );
        createLeftPanel(
            "linea",
            () -> controller.getManutLinee().stream().map(l -> l.getCodiceLinea() + "-" + l.getNome()).toList(),
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
        String dataInizio,
        String dataFine,
        String nome,
        String descrizione,
        String partitaIva
    ) {}
}
