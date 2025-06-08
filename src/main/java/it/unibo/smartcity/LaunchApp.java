package it.unibo.smartcity;

import it.unibo.smartcity.controller.impl.ControllerImpl;
import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.view.impl.SwingView;
import it.unibo.smartcity.view.api.View;

/**
 * Entry point of the app, it creates a controller and launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    private LaunchApp() { }

    /**
     * Starts the application.
     * @param args unused
     */
    public static void main(final String[] args) {
        final Controller controller = new ControllerImpl();
        final View view = new SwingView(controller);
        controller.attachView(view);
        controller.showMainMenu();
    }
}
