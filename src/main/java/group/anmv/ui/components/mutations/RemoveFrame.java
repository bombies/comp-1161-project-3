package group.anmv.ui.components.mutations;

import group.anmv.ui.DriverFrame;
import group.anmv.ui.components.dialouges.ErrorFrame;
import group.anmv.ui.components.dialouges.SuccessFrame;
import group.anmv.ui.models.Ingredient;
import group.anmv.utils.save.SaveHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The frame that will be used to allow
 * the user to input the ingredient they
 * want to remove from the list
 *
 * @author Mohitha
 */
public class RemoveFrame extends JFrame {

    private DriverFrame driverFrame;

    public RemoveFrame(final ArrayList<Ingredient> groceryList, DriverFrame driverFrame) {
        super("Ingredient Removal");
        this.driverFrame = driverFrame;
        final var removalPanel = new JPanel();
        final var nameTextField = new JTextField(20);
        removalPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        removalPanel.setLayout(new GridLayout(0, 1));
        removalPanel.setSize(700, 500);
        setContentPane(removalPanel);

        removalPanel.add(new JLabel("Ingredient Name to Remove:"));
        removalPanel.add(nameTextField);
        nameTextField.addActionListener(e -> {
            final var entry = nameTextField.getText();
            groceryList.stream()
                    .filter(item -> item.getName().equalsIgnoreCase(entry.strip()))
                    .findFirst()
                    .ifPresentOrElse(item -> {
                        driverFrame.removeIngredient(item);
                        try {
                            SaveHandler.removeItem(item);
                            driverFrame.removeIngredient(item);
                            new SuccessFrame("Removed item with name: " + item.getName());
                            setVisible(false);
                        } catch (IOException ex) {
                            new ErrorFrame("There was an error attempting to remove that ingredient from your save!");
                        }
                    }, () -> new ErrorFrame("Invalid Entry!"));

        });

        setResizable(false);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }


}
