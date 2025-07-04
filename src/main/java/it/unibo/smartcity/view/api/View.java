package it.unibo.smartcity.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.AttuazioneCorsaConLineaOrario;
import it.unibo.smartcity.data.IncassiTariffa;
import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.LineaPiuHubMobilita;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Biglietto;
import it.unibo.smartcity.data.ListLineeCinqueContrDiecMul;
import it.unibo.smartcity.data.ListLineeMulte;
import it.unibo.smartcity.data.ListVariazioniServizi;
import it.unibo.smartcity.data.MediaSoldiMulte;
import it.unibo.smartcity.data.OrarioLavoro;
import it.unibo.smartcity.model.api.CausaleMulta;
import it.unibo.smartcity.model.api.Contenuto;
import it.unibo.smartcity.model.api.ContenutoHub;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.Mezzo;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.TariffaBiglietto;
import it.unibo.smartcity.model.api.Persona;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.TipologiaMezzo;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl.ManutenzioneGravosa;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.data.TragittoConTempo;
import it.unibo.smartcity.controller.api.Controller.UserLevel;

public interface View {

    void showMainMenu();

    void updateOrariLavoro(List<OrarioLavoro> orariLavoro);

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea, List<TragittoConTempo> tragitti, List<OrarioLinea> orariLinea);

    void updateHubsList(List<ListHubMobilita> set);

    void userLevelChanged(UserLevel newLevel);

    void showLoginUser(String username);

    void showMessage(String title, String message);

    void updateUserInfo(Utente user, UserLevel userLevel);

    void updateEmployeesList(List<Dipendente> employees, List<Utente> notEmployeed);

    void updateManutGravose(List<ManutenzioneGravosa> estrazManutPiuGravose);

    void updateFermateList(List<Fermata> fermate);

    void updateHubs(List<HubMobilita> hubs);

    void updateTratte(List<Tratta> list, List<Fermata> fermate);

    void updateManutMezziPanel(List<ManutenzioneMezzoImpl> list);

    void updateManutLineePanel(List<ManutenzioneLinea> list);

    void updateAziendeNoManut(List<AziendaImpl> extracAziendeNoManut);

    void updateManutPerMezzo(List<MezzoConNome> mezzi);

    void updateListsManagementLinee(List<Linea> daAggiungere, List<Tragitto> daRimuovere);

    void updateTratteListPerLinea(List<Tratta> tratte);

    void updateTipoMezzi(Set<TipologiaMezzo> list);

    void updateBuyTicket(List<TariffaBiglietto> list);

    void updateValidateTicket(List<Biglietto> biglietti, List<AttuazioneCorsaConLineaOrario> corse);

    void updateLineeListInOrari(List<Linea> list);

    void updateOrariLineaInManagement(List<OrarioLinea> list);

    void updateContenutiHub(Set<ContenutoHub> list);

    void updateContenuti(List<Contenuto> list, List<ContenutoHub> listContenutiHub);

    void updatePersone(List<Persona> list);

    void updateCausaliMulta(List<CausaleMulta> list);

    void updateCorse(List<AttuazioneCorsaConLineaOrario> list);

    void updateLineeControlliMulte(Set<ListLineeCinqueContrDiecMul> lineeMulteControlli);

    void updateLineeStraordinarie(List<Linea> lineeStraordinarie);

    void updateMediaSoldiMulte(MediaSoldiMulte mediaSoldiMulte);

    void updateLineeMulte(List<ListLineeMulte> lineeMulte);

    void updateVariazioniServizio(Set<ListVariazioniServizi> list);

    void updateLineeAttuazioneCorsa(List<Linea> lineeAttive);

    void updateOrariLineaAttuazioneCorse(List<OrarioLinea> orari);

    void updateMezziAttuazioneCorse(List<Mezzo> mezzi);

    void updateAutistiAttuazioneCorse(List<Dipendente> autisti);

    void updateAttivaLineePanel(List<Linea> linee);

    void updateControllori(List<Dipendente> controllori);

    void updateLineaPiuHubMobilita(LineaPiuHubMobilita lineaPiuHub);

    void showSuccessMessage(String title, String message);

    void updateIncassiTariffa(IncassiTariffa inc);

    void  updateFermateHubTuttiContenuti(List<Fermata> list);

    void updateOrdinaryLines(List<Linea> list);
}