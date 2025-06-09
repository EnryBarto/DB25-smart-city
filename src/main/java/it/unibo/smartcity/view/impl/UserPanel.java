package it.unibo.smartcity.view.impl;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.smartcity.controller.api.Controller;

class UserPanel extends JPanel {

    public UserPanel(final Controller controller) {
        var jb = new JButton("LogOut");
        jb.addActionListener(e -> {
            controller.logout();
        });
        this.add(jb);
    }

}
