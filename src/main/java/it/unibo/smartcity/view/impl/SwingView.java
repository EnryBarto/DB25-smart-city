package it.unibo.smartcity.view.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.view.api.View;

public class SwingView implements View {

    private final Controller controller;
    private final JFrame frame = new JFrame();
    private final JTabbedPane tabPane = new JTabbedPane();
    private final Map<String, JPanel> tabs = new LinkedHashMap<>();

    private static final float RIDIM = 1.5f;
    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 400;

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
        this.createTabs();
    }

    @Override
    public void showMainMenu() {
        this.frame.setVisible(true);
    }

    private void createTabs() {
        this.tabs.put("Linee", new JPanel());
        this.tabs.put("Login", new LoginPanel());
        this.tabs.put("Registrati", new SignupPanel(controller));
        this.tabs.entrySet().forEach(e -> tabPane.addTab(e.getKey(), e.getValue()));
        this.frame.add(tabPane);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e
            ) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
