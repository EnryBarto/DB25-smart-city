package it.unibo.smartcity.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.smartcity.view.api.PanelFactory;

public class PanelFactoryImpl implements PanelFactory {

    // Generic left panel creation
    /**
     * Creates a styled left panel with a vertical layout, containing a set of labeled components and a submit button.
     * The panel uses a compound border with a colored line and padding, and applies consistent styling to labels and the submit button.
     *
     * @param title      the title of the panel (currently unused in the method)
     * @param components a map associating each {@link JLabel} with its corresponding {@link JComponent} to be added to the panel
     * @param btnSubmit  the submit {@link JButton} to be styled and added to the panel
     * @return a {@link JPanel} containing the provided components arranged vertically with custom styling
     */
    @Override
    public JPanel createLeftPanel(
        String title,
        Map<JLabel, JComponent> components,
        JButton btnSubmit
    ) {
        JPanel leftPanel = new JPanel();
        leftPanel.removeAll();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        leftPanel.setBackground(Color.WHITE);

        JLabel addTitle = new JLabel("Aggiunta hub mobilità", SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(addTitle);
        components.forEach((l, c) -> {
            var p =  new JPanel();
            p.setBackground(Color.WHITE);
            l.setFont(new Font("Arial", Font.BOLD, 18));
            l.setForeground(new Color(52, 152, 219));
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
            p.add(l);
            p.add(c);
            leftPanel.add(p);
        });
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(40, 167, 69));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnSubmit);
        return leftPanel;
    }

    /**
     * Creates a styled right panel with a vertical layout, containing a title label, a combo box, and a submit button.
     * The panel uses a compound border with a colored line and padding, and applies consistent styling to the title label and the submit button.
     *
     * @param title      the title of the panel
     * @param comboBox   the {@link JComboBox} to be added to the panel
     * @param btnSubmit  the submit {@link JButton} to be styled and added to the panel
     * @return a {@link JPanel} containing the title, combo box, and submit button arranged vertically with custom styling
     */
    @Override
    public JPanel createRightPanel(
        String title,
        JComboBox<String> comboBox,
        JButton btnSubmit
    ) {
        var rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        rightPanel.setBackground(Color.WHITE);

        JLabel removeTitle = new JLabel(title, SwingConstants.CENTER);
        removeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        removeTitle.setForeground(new Color(220, 53, 69));
        removeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(220, 53, 69));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSubmit.setAlignmentX(Component.BOTTOM_ALIGNMENT);

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSubmit);

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(comboBox);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createVerticalGlue());

        return rightPanel;
    }
}
