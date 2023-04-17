package group.anmv.ui.components;

import group.anmv.ui.models.Ingredient;
import group.anmv.utils.save.SaveHandler;

import javax.swing.*;
import java.io.IOException;

/**
 * This is the component that will be used to display
 * and mutate the suggestions retrieved.
 *
 * @author Ajani Green
 */
public class SuggestionComponent extends JPanel {
    private final String suggestion;
    private final JComponent parent;
    private final JFrame frame;

    /**
     * This will create the suggestion component. It will be formatted
     * in such a way that the suggestion and the buttons to add and remove
     * will be in the same line.
     * @param frame The parent frame the panel is located on
     * @param parent The parent panel the component will be placed on
     * @param suggestion The suggestion to be mutated
     */
    public SuggestionComponent(JFrame frame, JComponent parent, String suggestion) {
        this.suggestion = suggestion;
        this.parent = parent;
        this.frame = frame;

        final var label = new JLabel(suggestion);
        label.setHorizontalAlignment(JLabel.LEFT);
        add(label);
        add(acceptSuggestionButton());
        add(deleteSuggestionButton());
        setVisible(true);
        parent.add(this);
    }

    /**
     * Create the button that will be used to accept the suggestion
     * @return The button
     */
    private JButton acceptSuggestionButton() {
        final var button = new JButton("Accept");
        button.addActionListener((e) -> {
            try {
                SaveHandler.appendItem(new Ingredient(suggestion, 0, 0));
                // TODO: In-memory item list update
                parent.remove(this);
                parent.validate();
                parent.repaint();
                if (parent.getComponents().length == 0)
                    frame.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                new ErrorFrame("There was an error adding that item to the list!");
            }
        });
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }

    /**
     * Create the button that will be used to reject the suggestion
     * @return The button
     */
    private JButton deleteSuggestionButton() {
        final var button = new JButton("Reject");
        button.addActionListener((e) -> {
            parent.remove(this);
            parent.validate();
            parent.repaint();
            if (parent.getComponents().length == 0)
                frame.dispose();
        });
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }
}
