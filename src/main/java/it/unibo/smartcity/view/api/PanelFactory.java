package it.unibo.smartcity.view.api;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface PanelFactory {

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
    JPanel createLeftPanel(
            String title,
            Map<String, JComponent> components,
            JButton btnSubmit);

    /**
     * Creates a styled right panel with a vertical layout, containing a title label, a combo box, and a submit button.
     * The panel uses a compound border with a colored line and padding, and applies consistent styling to the title label and the submit button.
     *
     * @param title      the title of the panel
     * @param comboBox   the {@link JComboBox} to be added to the panel
     * @param btnSubmit  the submit {@link JButton} to be styled and added to the panel
     * @return a {@link JPanel} containing the title, combo box, and submit button arranged vertically with custom styling
     */
    JPanel createRightPanel(
            String title,
            JComboBox<String> comboBox,
            JButton btnSubmit);

    JPanel createGreenPanel(
            String title,
            Map<String, JComponent> components,
            JButton btnSubmit);

}