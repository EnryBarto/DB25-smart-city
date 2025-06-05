package it.unibo.smartcity.view.api;

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

}