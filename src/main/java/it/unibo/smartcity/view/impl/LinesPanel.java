package it.unibo.smartcity.view.impl;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.SmartCityEvent;
import it.unibo.smartcity.model.api.Linea;

class LinesPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();

    public LinesPanel(final Controller controller) {
        this.setLayout(new BorderLayout());
        this.setAlignmentX(CENTER_ALIGNMENT);
        var updateButton = new JButton("Aggiorna");
        updateButton.addActionListener(e -> {
            controller.handleEvent(SmartCityEvent.SHOW_LINES, null);
        });
        this.add(updateButton, BorderLayout.NORTH);
        this.add(textArea, BorderLayout.CENTER);
    }

    public void updateLines(final Set<Linea> linee) {
        this.textArea.setText("");
        linee.stream()
            .map(l -> l.getCodiceLinea() + " - Tempo Percorrenza: " + l.getTempoPercorrenza() + "min\n")
            .forEach(this.textArea::append);
    }
}
