package it.unibo.smartcity.view.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.controller.api.Controller.UserLevel;

public interface View {

    void showMainMenu();

    void updateOrariLavoro(Map<Date, OrarioLinea> orariLavoro);

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea);

    void updateHubsList(Set<ListHubMobilita> set);

    void userLevelChanged(UserLevel newLevel);

    void showLoginUser(String username);

    void showMessage(String title, String message);

    void updateUserInfo(Utente user, UserLevel userLevel);

    void updateEmployeesList(List<Dipendente> employees, List<Utente> notEmployeed);

    void updateManutGravose(List<ManutenzioneGravosa> estrazManutPiuGravose);

    void updateFermateList(List<Fermata> fermate);

    void updateHubs(List<HubMobilita> hubs);

    void updateTratte(Set<Tratta> list);

    void updateManutMezziPanel(List<ManutenzioneMezzoImpl> list);

    void updateManutLineePanel(List<ManutenzioneLinea> list);

    void updateAziendeNoManut(List<AziendaImpl> extracAziendeNoManut);

    void updateManutPerMezzo(ArrayList<MezzoConNome> mezzi);
}