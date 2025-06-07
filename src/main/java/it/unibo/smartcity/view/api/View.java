package it.unibo.smartcity.view.api;

import java.util.Set;

import it.unibo.smartcity.model.api.Linea;

public interface View {

    public record  SignupData(
        String name,
        String surname,
        String document,
        String cf,
        String phone,
        String email,
        String username,
        String password
    ) {}
    void showMainMenu();
    void showLines(Set<Linea> linee);

}