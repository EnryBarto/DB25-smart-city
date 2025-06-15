package it.unibo.smartcity.controller.api;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.CausaleMulta;
import it.unibo.smartcity.model.api.Contenuto;
import it.unibo.smartcity.model.api.ContenutoHub;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Dipendente.Ruolo;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.Persona;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.TrattaImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.view.api.View;

public interface Controller {

    public enum UserLevel {
        NOT_LOGGED("Non autenticato"),
        USER("Utente base"),
        ADMIN("Amministrativo"),
        DRIVER("Autista"),
        CONTROLLER("Controllore");

        private final String toString;

        private UserLevel(final String nome) {
            this.toString = nome;
        }

        @Override
        public String toString() {
            return this.toString;
        }
    }

    void attachView(View v);

    void showMainMenu();

    void updateLinesList();

    void updateOrariLavoro();

    void signup(Utente user, String ruolo);

    void updateTimetableLinesList();

    void showTimetable(String linea);

    void updateHubsList();

    void login(String username, String password);

    void logout();

    void showLoginUser(String username);

    void showMessage(String title, String message);

    void updateUserInfo();

    void updateEmployeesList();

    void addEmployee(Utente utente, Ruolo ruolo);

    void removeEmployee(Dipendente dipendente);

    void addFermata(Fermata fermata);

    void removeFermata(Fermata f);

    void updateFermateList();

    void removeHub(HubMobilita h);

    void addHub(HubMobilitaImpl hub);

    void addTratta(TrattaImpl tratta);

    void removeTratta(Tratta t);

    void updateTratte();

    void updateManutGravose();

    void addManutenzioneMezzo(ManutenzioneMezzoImpl manut);

    List<MezzoConNome> getMezzi();

    List<ManutenzioneMezzoImpl> getManutenzioniMezzi();

    void removeManutMezzo(String getnImmatricolazione, Date dataInzio);

    void updateManutMezziPanel();

    void updateManutLineePanel();

    List<ManutenzioneLinea> getManutLinee();

    void addManutenzioneLinea(ManutenzioneLineaImpl manut);

    void removeManutLinea(String codiceLinea, Date dataInizio);

    void updateAziendeNoManut();

    void updateManutPerMezzo();

    List<ManutenzioneMezzoImpl> getManutPerMezzo(String nImmatricolazione);

	void addServiceVariation(ManutenzioneLinea selectedManutenzione, Linea selectedLinea);

    void updateListsManagementLinee();

    void updateTratteListPerLinea(String codiceLinea);

    void addTragitto(String codLinea, Tratta tratta);

    void removeTragitto(Tragitto tragitto);

    void addLinea(Linea linea, List<Tratta> selectedTratte, boolean straordinaria);

    void updateTipoMezzi();

    void updateBuyTicket();

    void updateValidateTicket();

    void addBiglietto(int biglietto);

    void validateBiglietto(int codiceBiglietto, int codice_corsa);

    void updateLineeInOrari();

    void updateOrariLineaInManagement(String codiceLinea);

    void addOrarioLinea(String codLinea, String giorno, LocalTime orario);

    void removeOrario(OrarioLinea orarioLinea);

    void addContenutoToHub(ContenutoHub selectedContenuto, HubMobilita selectedHub, int postiMassimi);

    void deleteContenutoHub(Contenuto selectedContenutoHub);

    void updateContenuti();

    void updateContenutiHub();

    void addPersona(String cognome, String nome, String documento, String codiceFiscale);

    void addMulta(Persona persona, CausaleMulta causale, AttuazioneCorsa corsa, double importo);

    void updatePersone();

    void updateCausaliMulta();

    void updateCorse();

    void updateStatistics();

    void updateLineeMulte(Date dataInizio, Date dataFine);

    void updateVariazioniServizio(Linea selectedLinea);

    void updateLineeAttuazioneCorse(LocalDate data);

    void updateOrariLineaAttuazioneCorse(String codLinea, LocalDate data);

    void updateMezziAttuazioneCorse(String codLinea, LocalDate data);

    void updateAutistiListCreazioneCorsa();

    void addAttuazioneCorsa(LocalDate data, OrarioLinea orario, Dipendente dipendente, String codMezzo);

    List<Linea> getLinee();

	void addControllo(AttuazioneCorsa c, Dipendente d);

    void updateControllori();
}