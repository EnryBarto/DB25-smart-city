package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.IncassiTariffa;
import it.unibo.smartcity.data.LineaPiuHubMobilita;
import it.unibo.smartcity.data.ListLineeCinqueContrDiecMul;
import it.unibo.smartcity.data.ListLineeMulte;
import it.unibo.smartcity.data.MediaSoldiMulte;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;

class StatisticsPanel extends JPanel {
    private final static String[] columnNamesManutGravose = {"Codice Linea", "Nome", "Durata Lavoro", "Num Linee Sostitutive"};
    private final static String[] columnNamesMezzi = {"Num immatricolazione", "Nome", "data inizio", "data fine", "descrizione", "partita iva"};
    private final static String[] columnNamesAziende = {"partita iva", "ragione sociale", "comune", "via", "civico", "telefono", "email"};
    private final static String[] columnNamesFermate = {"Codice", "Nome", "Indirizzo", "Latitudine", "Longitudine"};

    private static final List<String> options = List.of(
        "5 manutenzioni più gravose",
        "Aziende senza manutenzioni nell'ultimo mese",
        "Manutenzioni dato un mezzo",
        "Linee con più multe",
        "Linee con > 5 controlli e <= 10 multe al giorno",
        "Media dei soldi spesi in multe per persona",
        "Linea con più hub mobilità lungo il percorso",
        "Incassi per titolo di viaggio in periodo definito",
        "Fermate con almeno un hub contenente tutti i servizi green"
    );

    private final Controller controller;
    private final JPanel northPanel = new JPanel(new FlowLayout());
    private final JComboBox<String> optionList = new JComboBox<>();
    private LineePiuMultePanel lineePiuMultePanel;
    private LineeControlliMultePanel lineeMulteControlli;
    private MediaSoldiMultePanel mediaSoldiMultePanel = new MediaSoldiMultePanel();
    private LineaMaggioriHubPanel lineaMaggioriHubPanel = new LineaMaggioriHubPanel();
    private IncassiTipoTariffaPanel incassiTipoTariffaPanel;

    public StatisticsPanel(final Controller controller) {
        super(new BorderLayout());
        // Check that the controller is not null
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        this.lineeMulteControlli = new LineeControlliMultePanel(controller);
        this.lineePiuMultePanel = new LineePiuMultePanel(controller);
        this.incassiTipoTariffaPanel = new IncassiTipoTariffaPanel(controller);
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
                case "5 manutenzioni più gravose" -> controller.updateManutGravose();
                case "Aziende senza manutenzioni nell'ultimo mese" -> controller.updateAziendeNoManut();
                case "Manutenzioni dato un mezzo" -> controller.updateManutPerMezzo();
                case "Linee con più multe" -> {
                    this.showPanel(lineePiuMultePanel);
                }
                case "Linee con > 5 controlli e <= 10 multe al giorno" -> {
                    this.showPanel(lineeMulteControlli);
                    controller.updateStatistics();
                }
                case "Media dei soldi spesi in multe per persona" -> {
                    this.showPanel(mediaSoldiMultePanel);
                    controller.updateStatistics();
                }
                case "Linea con più hub mobilità lungo il percorso"-> {
                    this.showPanel(lineaMaggioriHubPanel);
                    controller.updateStatistics();
                }
                case "Incassi per titolo di viaggio in periodo definito" -> {
                    this.showPanel(incassiTipoTariffaPanel);
                }
                case "Fermate con almeno un hub contenente tutti i servizi green" -> controller.updateFermateHubTuttiContenuti();
                default -> throw new IllegalArgumentException("Opzione non valida: " + this.optionList.getSelectedItem());
            }
        });
        northPanel.add(btn);
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

    public void showManutGravose(List<ManutenzioneGravosa> manutenzioneGravose) {
        clearContentExceptNorth();
        updateVisualPanel(
            this,
            manutenzioneGravose,
            columnNamesManutGravose,
            m -> {
                var row = new Object[columnNamesManutGravose.length];
                row[0] = m.codiceLinea();
                row[1] = m.nome();
                row[2] = m.durata_lavoro();
                row[3] = m.numLineeSostitutive();
                return row;
            }
        );
        this.repaint();
        this.revalidate();
    }

    public void showAziendeNoManut(List<AziendaImpl> aziende) {
        clearContentExceptNorth();
        updateVisualPanel(
            this,
            aziende,
            columnNamesAziende,
            a -> {
                var row = new Object[columnNamesAziende.length];
                row[0] = a.getPartitaIva();
                row[1] = a.getRagioneSociale();
                row[2] = a.getIndirizzoComune();
                row[3] = a.getIndirizzoVia();
                row[4] = a.getIndirizzoCivico();
                row[5] = a.getTelefono();
                row[6] = a.getEmail();
                return row;
            });
        this.repaint();
        this.revalidate();
    }

    public void showFermateHubTuttiContenuti(List<Fermata> fermate) {
        clearContentExceptNorth();
        updateVisualPanel(
            this,
            fermate,
            columnNamesFermate,
            f -> {
                var row = new Object[columnNamesFermate.length];
                row[0] = f.getCodiceFermata();
                row[1] = f.getNome();
                row[2] = f.getIndirizzo().toString();
                row[3] = f.getLatitudine();
                row[4] = f.getLongitudine();
                return row;
            });
        this.repaint();
        this.revalidate();
    }

    public void showManutPerMezzoPanel(List<MezzoConNome> mezzi) {
        clearContentExceptNorth();

        var mezzoPanel = new JPanel(new BorderLayout());
        mezzoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(60, 30, 20, 30)
        ));
        mezzoPanel.setBackground(Color.WHITE);

        JLabel mezzoLabel = new JLabel("mezzo :");
        JComboBox<String> combo = new JComboBox<>();
        combo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        combo.setMaximumSize(combo.getPreferredSize());
        combo.setEditable(false);

        mezzi.forEach(m -> combo.addItem(m.nImmatricolazione() + "-" + m.nomeMezzo()));
        combo.setSelectedIndex(-1);
        mezzoPanel.add(mezzoLabel);
        mezzoPanel.add(combo);

        this.add(mezzoPanel, BorderLayout.NORTH);

        combo.addActionListener(e -> {
            String choice = (String)combo.getSelectedItem();
            choice = choice.split("-")[0];
            var manutenioni = controller.getManutPerMezzo(choice);
            if (this.getComponentCount() > 2) this.remove(2);
            updateVisualPanel(
                this,
                manutenioni,
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
            );
            this.revalidate();
            this.repaint();
        });
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

    void updateLineeConPiuMulte(List<ListLineeMulte> lines) {
        this.lineePiuMultePanel.updateLinesWithMostFines(lines);
    }

    void updateLineeConControlliMulte(Set<ListLineeCinqueContrDiecMul> lines) {
        this.lineeMulteControlli.updateLineeControlliMulte(lines);
    }

    void updateMediaSoldiMulte(MediaSoldiMulte media) {
        this.mediaSoldiMultePanel.update(media);
    }

    private void clearContentExceptNorth() {
        this.removeAll();
        this.add(northPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    private void showPanel(JPanel panel) {
        this.clearContentExceptNorth();
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void updateLineaPiuHub(LineaPiuHubMobilita lineaPiuHub) {
        this.lineaMaggioriHubPanel.updateLineaPiuHub(lineaPiuHub);
    }

    public void updateIncassiTariffa(IncassiTariffa inc) {
        this.incassiTipoTariffaPanel.updateIncassiTariffa(inc);
    }

}
