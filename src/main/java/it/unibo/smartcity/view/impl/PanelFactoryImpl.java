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

    @Override
    public JPanel createLeftPanel(
        String title,
        Map<String, JComponent> components,
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

        JLabel addTitle = new JLabel(title, SwingConstants.CENTER);
        addTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addTitle.setForeground(new Color(52, 152, 219));
        addTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(addTitle);
        components.forEach((s, c) -> {
            var p =  new JPanel();
            p.setBackground(Color.WHITE);
            var l = new JLabel(s, SwingConstants.CENTER);
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

        var comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        comboBoxPanel.setBackground(Color.WHITE);
        comboBoxPanel.add(comboBox);

        rightPanel.add(removeTitle);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(comboBoxPanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createVerticalGlue());

        return rightPanel;
    }

    @Override
    public JPanel createGreenPanel(String title, Map<String, JComponent> components, JButton btnSubmit) {
        JPanel greenPanel = new JPanel();
        greenPanel.setLayout(new BoxLayout(greenPanel, BoxLayout.PAGE_AXIS));
        greenPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 167, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        greenPanel.setBackground(Color.WHITE);

        JLabel greenTitle = new JLabel(title, SwingConstants.CENTER);
        greenTitle.setFont(new Font("Arial", Font.BOLD, 18));
        greenTitle.setForeground(new Color(40, 167, 69));
        greenTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        greenPanel.add(greenTitle);
        components.forEach((s, c) -> {
            JPanel p = new JPanel();
            p.setBackground(Color.WHITE);
            JLabel l = new JLabel(s, SwingConstants.CENTER);
            p.add(l);
            p.add(c);
            greenPanel.add(p);
        });
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(40, 167, 69));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        greenPanel.add(Box.createVerticalStrut(20));
        greenPanel.add(btnSubmit);

        return greenPanel;
    }

    @Override
    public JPanel createRedPanel(String title, Map<String, JComponent> components, JButton btnSubmit) {
        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.PAGE_AXIS));
        redPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        redPanel.setBackground(Color.WHITE);

        JLabel redTitle = new JLabel(title, SwingConstants.CENTER);
        redTitle.setFont(new Font("Arial", Font.BOLD, 18));
        redTitle.setForeground(new Color(220, 53, 69));
        redTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        redPanel.add(redTitle);
        components.forEach((s, c) -> {
            JPanel p = new JPanel();
            p.setBackground(Color.WHITE);
            JLabel l = new JLabel(s, SwingConstants.CENTER);
            p.add(l);
            p.add(c);
            redPanel.add(p);
        });
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(220, 53, 69));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        redPanel.add(Box.createVerticalStrut(20));
        redPanel.add(btnSubmit);

        return redPanel;
    }
}
