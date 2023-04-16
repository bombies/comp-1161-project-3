package group.anmv.ui.components;

import group.anmv.ui.DriverFrame;
import group.anmv.ui.models.Ingredient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * The frame that will be used to display the suggestions
 * and mutate the suggestions.
 *
 * @author Ajani
 */
public class RemoveFrame extends JFrame {

    private DriverFrame Dframe;
    public RemoveFrame(ArrayList<Ingredient> grocerylist, DriverFrame dframe) {
        super("Ingredient Removal");
        this.Dframe=dframe;
        final var removalPanel = new JPanel();
        final var nameTextField = new JTextField(20);
        removalPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        removalPanel .setLayout(new GridLayout(0, 1));
        removalPanel .setSize(700, 500);
        setContentPane(removalPanel);

        removalPanel.add(new JLabel("Ingredient Name to Remove:"));
        removalPanel.add(nameTextField);
        nameTextField.addActionListener((e)->
        {
            final var entry = nameTextField.getText();
            for (int i = 0; i <= grocerylist.size(); i++) {
                if (grocerylist.get(i).getName().equals(entry)) {
                    grocerylist.remove(i);
                    dframe.getTableModel().removeRow(i);
                } else new ErrorFrame("Invalid Entry!");
            }
        });

        setResizable(false);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }

    private void removalPanel(JPanel removalPanel) {
    }
}
