package group.anmv.ui.components;

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
    final JPanel suggestionsPanel;

    public SuggestionsFrame() {
        super("Ingredient Suggestions");

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

    public SuggestionsFrame setLoading() {
        final var loadingLabel = new JLabel("Loading your suggestions");
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        suggestionsPanel.add(loadingLabel);
        return this;
    }

    public void setRecipes(List<String> suggestions) {
        System.out.println(suggestions.size());
        suggestionsPanel.removeAll();
        suggestions.forEach(suggestion -> suggestionsPanel.add(new JLabel(suggestion)));
        suggestionsPanel.validate();
        suggestionsPanel.repaint();
    }

    public void setItems(List<String> suggestions) {
        suggestionsPanel.removeAll();
        suggestions.forEach(suggestion -> new SuggestionComponent(this, suggestionsPanel, suggestion));
        suggestionsPanel.validate();
        suggestionsPanel.repaint();
    }
}
