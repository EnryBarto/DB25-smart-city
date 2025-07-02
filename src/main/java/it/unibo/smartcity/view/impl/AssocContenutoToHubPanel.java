package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.model.api.Contenuto;
import it.unibo.smartcity.model.api.ContenutoHub;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.view.api.PanelFactory;

class AssocContenutoToHubPanel extends JPanel {

    private final JComboBox<String> hubComboBox = new JComboBox<>();
    private final JComboBox<String> contenutoHubComboBox = new JComboBox<>();
    private final JComboBox<String> contenutoComboBox = new JComboBox<>();
    private final Map<String, Contenuto> contenutoMapper = new HashMap<>();
    private final Map<String, ContenutoHub> contenutoHubMapper = new HashMap<>();
    private final Map<String, HubMobilita> hubMapper = new HashMap<>();

    AssocContenutoToHubPanel(Controller controller) {
        super(new GridLayout(1, 2, 20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setBackground(new Color(245, 249, 255));

        JTextField maxSlot = new JTextField();
        Map<String, JComponent> components = new LinkedHashMap<>();
        components.put("hub mobilita:", hubComboBox);
        components.put("contenuto:", contenutoHubComboBox);
        components.put("posti massimi:", maxSlot);

        JButton btnSubmit = new JButton("Aggiungi contenuto a hub");
        btnSubmit.addActionListener(e -> {
            try {
                HubMobilita selectedHub = hubMapper.get(hubComboBox.getSelectedItem());
                ContenutoHub selectedContenuto = contenutoHubMapper.get(contenutoHubComboBox.getSelectedItem());
                int postiMassimi;
                try {
                    postiMassimi = Integer.parseInt(maxSlot.getText());
                } catch (Exception ex) {
                    controller.showErrorMessage("Errore", "Inserire un numero valido per i posti massimi");
                    return;
                }
                if (postiMassimi <= 0) {
                    controller.showErrorMessage("Errore", "I posti massimi devono essere maggiori di zero");
                    return;
                }
                if (selectedHub == null || selectedContenuto == null) {
                    controller.showErrorMessage("Errore", "Nessun hub o contenuto selezionato");
                    return;
                }
                controller.addContenutoToHub(selectedContenuto, selectedHub, postiMassimi);
                controller.showSuccessMessage("Aggiunta riuscita", "Contenuto aggiunto con successo all'hub");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore inserimento contenuto", ex.getMessage());
            }
        });
        PanelFactory panelFactory = new PanelFactoryImpl();
        var leftPanel = panelFactory.createLeftPanel(
                "Aggiunta associazione contenuto a hub",
                components,
                btnSubmit);
        this.add(leftPanel);

        var componentsRight = new LinkedHashMap<String, JComponent>();
        componentsRight.put("Contenuto-hub da eliminare:", contenutoComboBox);

        JButton btnDelete = new JButton("Elimina contenuto-hub");
        btnDelete.addActionListener(e -> {
            try {
                Contenuto selectedContenutoHub = contenutoMapper.get(contenutoComboBox.getSelectedItem());
                if (selectedContenutoHub == null) {
                    controller.showErrorMessage("Errore", "Nessun contenuto-hub selezionato");
                    return;
                }
                controller.deleteContenutoHub(selectedContenutoHub);
                controller.showSuccessMessage("Eliminazione riuscita", "Contenuto-hub eliminato con successo");
            } catch (Exception ex) {
                controller.showErrorMessage("Errore eliminazione contenuto-hub", ex.getMessage());
            }
        });
        var rightPanel = panelFactory.createRedPanel(
                "Rimozione associazione contenuto a hub",
                componentsRight,
                btnDelete);
        this.add(rightPanel);
    }

    /**
     * Aggiorna la lista degli hub disponibili per la selezione.
     */
    public void updateHubList(final List<HubMobilita> hubList) {
        hubMapper.clear();
        hubComboBox.removeAllItems();
        hubList.forEach(h -> {
            String key = h.getNome() + " (" + h.getCodiceHub() + ")";
            hubMapper.put(key, h);
            hubComboBox.addItem(key);
        });
    }

    /**
     * Aggiorna la lista dei contenuti disponibili per la selezione.
     */
    public void updateContenutoHubList(final List<ContenutoHub> contenuti) {
        contenutoHubMapper.clear();
        contenutoHubComboBox.removeAllItems();
        contenuti.forEach(c -> {
            String key = c.getDescrizione();
            contenutoHubMapper.put(key, c);
            contenutoHubComboBox.addItem(key);
        });
    }

    public void updateContenutoList(final List<Contenuto> contenuti, final List<ContenutoHub> contenutiHub) {
        contenutoMapper.clear();
        contenutoComboBox.removeAllItems();
        contenuti.forEach(c -> {
            String key = contenutiHub.stream()
                .filter(ch -> ch.getCodiceContenuto() == c.getCodiceContenuto())
                .findFirst()
                .map(ContenutoHub::getDescrizione)
                .orElse("Unknown") + " - " + c.getCodiceHub();
            contenutoMapper.put(key, c);
            contenutoComboBox.addItem(key);
        });
    }
}
