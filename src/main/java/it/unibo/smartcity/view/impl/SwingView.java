package it.unibo.smartcity.view.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatLightLaf;
import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.Controller.UserLevel;
import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.CausaleMulta;
import it.unibo.smartcity.model.api.Contenuto;
import it.unibo.smartcity.model.api.ContenutoHub;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.Persona;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.TipologiaMezzo;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.view.api.View;

public class SwingView implements View {

    private final Controller controller;
    private final JFrame frame = new JFrame();
    private JTabbedPane tabPane;
    private final Map<UserLevel, List<String>> tabsForUserLevel = new HashMap<>();
    private final Map<String, JPanel> tabs = new LinkedHashMap<>();

    private static final float RIDIM = 1.5f;
    private static final int MIN_WIDTH = 900;
    private static final int MIN_HEIGHT = 550;

    public SwingView(final Controller controller) {
        checkNotNull(controller);
        this.controller = controller;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() / RIDIM), (int) (screenSize.getHeight() / RIDIM));
        frame.setTitle("Smart City");
        frame.setLocationByPlatform(true);
        frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setLookAndFeel();
        this.initTabPanes();
    }

    @Override
    public void showMainMenu() {
        this.frame.setVisible(true);
        controller.updateLinesList();
        controller.updateTimetableLinesList();
    }

    @Override
    public void updateOrariLavoro(Map<Date, OrarioLinea> orariLavoro) {
        ((OrariLavoroPanel)tabs.get("Lavoro")).updateOrariLavoro(orariLavoro);
    }

    @Override
    public void updateLinesList(List<InfoLinea> linee) {
        ((LinesPanel)tabs.get("Linee")).updateLines(linee);
    }

    @Override
    public void updateTimetableLinesList(List<Linea> list) {
        ((TimetablePanel)this.tabs.get("Orari")).updateLinesList(list);
        ((InsertServiceVariationPanel)this.tabs.get("Ins. Var. Servizio")).updateLineList(list);
    }

    @Override
    public void showLineTimetable(String codLinea) {
        this.tabPane.setSelectedComponent(this.tabs.get("Orari"));
        ((TimetablePanel)this.tabs.get("Orari")).showLineTimetable(codLinea);
        // TODO: Mostra l'orario della linea scelta
    }

    @Override
    public void updateHubsList(Set<ListHubMobilita> set) {
        ((HubMobilityPanel)this.tabs.get("Hub")).updateHubs(set);
    }

    @Override
    public void userLevelChanged(UserLevel newLevel) {
        this.setTabPane(newLevel);
    }

    private void initTabPanes() {
        this.tabs.put("Linee", new LinesPanel(controller));
        this.tabs.put("Orari", new TimetablePanel(controller));
        this.tabs.put("Hub", new HubMobilityPanel());
        this.tabs.put("Login", new LoginPanel(controller));
        this.tabs.put("Registrati", new SignupPanel(controller));
        this.tabs.put("Gest. Dipendenti", new EmployeeManagementPanel(controller));
        this.tabs.put("Profilo", new UserPanel(controller));
        this.tabs.put("Lavoro", new OrariLavoroPanel(controller));
        this.tabs.put("Manutenzioni", new MaintenancePanel(controller));
        this.tabs.put("Gest. Tragitti", new TragittiManagePanel(controller));
        this.tabs.put("Gest. Fermate", new FermataManagePanel(controller));
        this.tabs.put("Gest. Orari Linee", new TimetableManagePanel(controller));
        this.tabs.put("Gest. Tratte", new TratteManagePanel(controller));
        this.tabs.put("Gest. Hub", new HubMobilitaManagePanel(controller));
        this.tabs.put("Ins. Var. Servizio", new InsertServiceVariationPanel(controller));
        this.tabs.put("Ins. Linea", new LineaInsertPanel(controller));
        this.tabs.put("Assoc. Hub-Contenuto", new AssocContenutoToHubPanel(controller));
        this.tabs.put("Ins. Multa", new InsertMultaPanel(controller));

        this.tabsForUserLevel.put(UserLevel.NOT_LOGGED, List.of("Linee", "Orari", "Hub", "Login", "Registrati"));
        this.tabsForUserLevel.put(UserLevel.ADMIN, List.of("Linee", "Orari", "Hub","Gest. Dipendenti", "Manutenzioni", "Profilo", "Ins. Linea", "Gest. Fermate", "Gest. Tragitti", "Gest. Orari Linee", "Gest. Hub", "Gest. Tratte", "Ins. Var. Servizio", "Assoc. Hub-Contenuto"));
        this.tabsForUserLevel.put(UserLevel.USER, List.of("Linee", "Orari", "Hub", "Profilo"));
        this.tabsForUserLevel.put(UserLevel.DRIVER, List.of("Linee", "Orari", "Hub","Lavoro", "Profilo"));
        this.tabsForUserLevel.put(UserLevel.CONTROLLER, List.of("Linee", "Orari", "Hub","Lavoro", "Profilo", "Ins. Multa"));
    }

    private void setTabPane(UserLevel userLevel) {
        if (this.tabPane != null) this.frame.remove(this.tabPane);

        this.tabPane = new JTabbedPane();

        this.tabsForUserLevel.get(userLevel).forEach(title -> this.tabPane.add(title, this.tabs.get(title)));

        this.tabPane.setSelectedComponent(this.tabs.get("Linee"));

        this.frame.add(tabPane);
        this.frame.repaint();
        this.frame.revalidate();

        this.tabPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabPane.getSelectedIndex();
                if (selectedIndex == -1) return;
                switch (tabPane.getTitleAt(selectedIndex)) {
                    case "Orari":
                        controller.updateTimetableLinesList();
                        break;
                    case "Linee":
                        controller.updateLinesList();
                        break;
                    case "Hub":
                        controller.updateHubsList();
                        break;
                    case "Profilo":
                        controller.updateUserInfo();
                        break;
                    case "Gest. Dipendenti":
                        controller.updateEmployeesList();
                        break;
                    case "Manutenzioni":
                        controller.updateManutGravose();
                        break;
                    case "Lavoro":
                        controller.updateOrariLavoro();
                    case "Gest. Fermate":
                        controller.updateFermateList();
                        break;
                    case "Gest. Hub":
                        controller.updateFermateList();
                        controller.updateHubsList();
                        break;
                    case "Gest. Tratte":
                        controller.updateFermateList();
                        controller.updateTratte();
                        break;
                    case "Ins. Var. Servizio":
                        controller.updateManutLineePanel();
                        controller.updateTimetableLinesList();
                        break;
                    case "Gest. Tragitti":
                        controller.updateListsManagementLinee();
                        break;
                    case "Ins. Linea":
                        controller.updateTratte();
                        controller.updateTipoMezzi();
                        break;
                    case "Gest. Orari Linee":
                        controller.updateLineeInOrari();
                        break;
                    case "Assoc. Hub-Contenuto":
                        controller.updateHubsList();
                        controller.updateContenutiHub();
                        controller.updateContenuti();
                        break;
                    case "Ins. Multa":
                        controller.updatePersone();
                        controller.updateCausaliMulta();
                        controller.updateCorse();
                        break;
                }
            }
        });
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void showLoginUser(String username) {
        this.tabPane.setSelectedComponent(this.tabs.get("Login"));
        ((LoginPanel)this.tabs.get("Login")).setUsername(username);
    }

    @Override
    public void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this.frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateUserInfo(Utente user, UserLevel userLevel) {
        ((UserPanel)this.tabs.get("Profilo")).updateUserInfo(user, userLevel);
    }

    @Override
    public void updateEmployeesList(List<Dipendente> employees, List<Utente> notEmployeed) {
        ((EmployeeManagementPanel)this.tabs.get("Gest. Dipendenti")).updateLists(employees, notEmployeed);
    }

    @Override
    public void updateManutGravose(List<ManutenzioneGravosa> manutenzioneGravose) {
        ((MaintenancePanel)this.tabs.get("Manutenzioni")).showManutGravose(manutenzioneGravose);
    }

    @Override
    public void updateFermateList(List<Fermata> fermate) {
        ((FermataManagePanel)this.tabs.get("Gest. Fermate")).updateFermateList(fermate);
        ((HubMobilitaManagePanel)this.tabs.get("Gest. Hub")).updateFermateList(fermate);
        ((TratteManagePanel)this.tabs.get("Gest. Tratte")).updateFermateList(fermate);
    }

    @Override
    public void updateHubs(List<HubMobilita> hubs) {
        ((HubMobilitaManagePanel)this.tabs.get("Gest. Hub")).updateHubList(hubs);
        ((AssocContenutoToHubPanel)this.tabs.get("Assoc. Hub-Contenuto")).updateHubList(hubs);
    }

    @Override
    public void updateTratte(Set<Tratta> set) {
        ((TratteManagePanel)this.tabs.get("Gest. Tratte")).updateTratteList(set.stream().toList());
        ((LineaInsertPanel)this.tabs.get("Ins. Linea")).updateTratteList(set.stream().toList());
    }

    @Override
    public void updateManutMezziPanel(List<ManutenzioneMezzoImpl> list) {
        ((MaintenancePanel)this.tabs.get("Manutenzioni")).showManutMezziPanel();;
    }

    @Override
    public void updateManutLineePanel(List<ManutenzioneLinea> list) {
        ((MaintenancePanel)this.tabs.get("Manutenzioni")).showManutLineePanel();
        ((InsertServiceVariationPanel)this.tabs.get("Ins. Var. Servizio")).updateManutenzioneList(list);
    }

    @Override
    public void updateAziendeNoManut(List<AziendaImpl> aziende) {
        ((MaintenancePanel)this.tabs.get("Manutenzioni")).showAziendeNoManut(aziende);
    }

    @Override
    public void updateManutPerMezzo(ArrayList<MezzoConNome> mezzi) {
        ((MaintenancePanel)this.tabs.get("Manutenzioni")).showManutPerMezzoPanel(mezzi);
    }

    @Override
    public void updateListsManagementLinee(List<Linea> daAggiungere, List<Tragitto> daRimuovere) {
        ((TragittiManagePanel)this.tabs.get("Gest. Tragitti")).updateLinesLists(daAggiungere, daRimuovere);
    }

    @Override
    public void updateTratteListPerLinea(List<Tratta> tratte) {
        ((TragittiManagePanel)this.tabs.get("Gest. Tragitti")).updateTratteList(tratte);
    }

    @Override
    public void updateTipoMezzi(Set<TipologiaMezzo> list) {
        ((LineaInsertPanel)this.tabs.get("Ins. Linea")).updateMezziList(list.stream().toList());
    }

    @Override
    public void updateLineeListInOrari(List<Linea> list) {
        ((TimetableManagePanel)this.tabs.get("Gest. Orari Linee")).updateLinesLists(list);
    }

    @Override
    public void updateOrariLineaInManagement(List<OrarioLinea> list) {
        ((TimetableManagePanel)this.tabs.get("Gest. Orari Linee")).updateOrari(list);
    }

    @Override
    public void updateContenutiHub(Set<ContenutoHub> contenutiHub) {
        ((AssocContenutoToHubPanel)this.tabs.get("Assoc. Hub-Contenuto")).updateContenutoHubList(contenutiHub.stream().toList());
    }

    @Override
    public void updateContenuti(List<Contenuto> contenuti) {
        ((AssocContenutoToHubPanel)this.tabs.get("Assoc. Hub-Contenuto")).updateContenutoList(contenuti.stream().toList());
    }

    @Override
    public void updatePersone(List<Persona> list) {
        ((InsertMultaPanel)this.tabs.get("Ins. Multa")).updatePersonaList(list);
    }

    @Override
    public void updateCausaliMulta(List<CausaleMulta> list) {
        ((InsertMultaPanel)this.tabs.get("Ins. Multa")).updateCausaleList(list);
    }

    @Override
    public void updateCorse(List<AttuazioneCorsa> list) {
        ((InsertMultaPanel)this.tabs.get("Ins. Multa")).updateCorsaList(list);
    }
}
