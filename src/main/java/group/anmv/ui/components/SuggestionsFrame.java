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

    public SuggestionsFrame(List<String> suggestions) {
        super("Ingredient Suggestions");

        final var suggestionsPanel = new JPanel();
        suggestionsPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setSize(700, 500);
        setContentPane(suggestionsPanel);

        suggestions.forEach(suggestion -> new SuggestionComponent(this, suggestionsPanel, suggestion));

        setResizable(false);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }
}
