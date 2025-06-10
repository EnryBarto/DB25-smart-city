package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;

public class MaintenancePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static String[] columnNames = {"Codice Linea", "Nome", "Durata Lavoro", "Linee Sostitutive"};
    private static final List<String> options = new ArrayList<>(List.of(
        "estrai 5 manutenzioni più gravose",
        "manutenzione mezzi",
        "manutenzione linee"
    ));

    private final Controller controller;
    private final JPanel northPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> optionList = new JComboBox<>();
    private final JComboBox<String> mezzoCombo = new JComboBox<>();
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
                case "manutenzione mezzi" -> addManutMezziPanel();
                case "manutenzione linee" -> addManutLineePanel();
                default -> throw new IllegalArgumentException("Opzione non valida: " + this.optionList.getSelectedItem());
            }
        });
        northPanel.add(btn);
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

    private void addManutMezziPanel() {
        //LEFT PANEL
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
        JLabel partenzaLabel = new JLabel("mezzo:");
        mezzoCombo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        mezzoCombo.setMaximumSize(mezzoCombo.getPreferredSize());
        mezzoCombo.setEditable(false);
        //adding all the mezzi to the combo box
        controller.getMezzi().forEach(m -> mezzoCombo.addItem(m.nImmatricolazione() + "-" +m.nomeMezzo()));
        mezzoPanel.add(partenzaLabel);
        mezzoPanel.add(mezzoCombo);

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

        var aggiungiBtn = new JButton("Aggiungi Manutenzone");
        aggiungiBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aggiungiBtn.setBackground(new Color(40, 167, 69));
        aggiungiBtn.setForeground(Color.WHITE);
        aggiungiBtn.setFocusPainted(false);
        aggiungiBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        aggiungiBtn.setAlignmentX(CENTER_ALIGNMENT);
        aggiungiBtn.addActionListener(e -> {
            try {
                // Validazione dei campi
                checkNotNull(mezzoCombo.getSelectedIndex(), "Selezionare un mezzo");
                checkNotNull(dataInizio.getText(), "Inserire la data di inizio manutenzione");
                checkArgument(!dataInizio.getText().isEmpty(), "La data di inizio manutenzione non può essere vuota");
                checkNotNull(dataFine.getText(), "Inserire la data di fine manutenzione");
                checkArgument(!dataFine.getText().isEmpty(), "La data di fine manutenzione non può essere vuota");
                checkNotNull(nomeField.getText(), "Inserire nome manutenzione");
                checkArgument(!nomeField.getText().isEmpty(), "Il nome della manutenzione non può essere vuoto");
                checkNotNull(descField.getText(), "Inserire descrizione manutenzione");
                checkArgument(!descField.getText().isEmpty(), "La descrizione della manutenzione non può essere vuota");
                if (!pIvaField.getText().isEmpty()) checkArgument(pIvaField.getText().matches("\\d{11}"), "La partita IVA deve essere un numero di 11 cifre");
                // Creazione della manutenzione
                var manut = new ManutenzioneMezzoImpl(
                    (String) mezzoCombo.getSelectedItem(),
                    Date.valueOf(dataInizio.getText()),
                    Date.valueOf(dataFine.getText()),
                    nomeField.getText(),
                    descField.getText(),
                    pIvaField.getText()
                );
                this.controller.addManutenzione(manut);
                //setting tutto vuoto
                mezzoCombo.setSelectedIndex(0);
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
    }

    private void addManutLineePanel() {
        // Implementazione per rimuovere una manutenzione
    }
}
