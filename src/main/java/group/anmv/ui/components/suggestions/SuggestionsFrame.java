package group.anmv.ui.components.suggestions;

import group.anmv.ui.DriverFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * The frame that will be used to display the suggestions
 * and mutate the suggestions.
 *
 * @author Ajani
 */
public class SuggestionsFrame extends JFrame {
    private final JPanel suggestionsPanel;
    private final DriverFrame driverFrame;

    public SuggestionsFrame(DriverFrame driverFrame) {
        super("Ingredient Suggestions");
        this.driverFrame = driverFrame;

        suggestionsPanel = new JPanel();
        suggestionsPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setSize(700, 500);

        setSize(700, 500);
        setContentPane(suggestionsPanel);
        setResizable(true);
        setLocationByPlatform(true);
        setVisible(true);
    }

    /**
     * Set the content of the frame to reflecting the loading state
     * @return This object
     */
    public SuggestionsFrame setLoading() {
        final var loadingLabel = new JLabel("Loading your suggestions");
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        suggestionsPanel.add(loadingLabel);
        return this;
    }

    /**
     * Set the content of the frame to be the loaded recipe suggestions
     * @param suggestions The list of loaded recipe suggestions
     */
    public void setRecipes(List<String> suggestions) {
        System.out.println(suggestions.size());
        suggestionsPanel.removeAll();
        suggestions.forEach(suggestion -> suggestionsPanel.add(new JLabel(suggestion)));
        suggestionsPanel.validate();
        suggestionsPanel.repaint();
    }

    /**
     * Set the content of the frame to be the loaded ingredient suggestions
     * @param suggestions The list of loaded ingredient suggestions
     */
    public void setItems(List<String> suggestions) {
        suggestionsPanel.removeAll();
        suggestions.forEach(suggestion -> new SuggestionComponent(driverFrame, this, suggestionsPanel, suggestion));
        suggestionsPanel.validate();
        suggestionsPanel.repaint();
    }
}
