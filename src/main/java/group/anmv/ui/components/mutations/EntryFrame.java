package group.anmv.ui.components.mutations;

import group.anmv.ui.DriverFrame;
import group.anmv.ui.components.dialouges.ErrorFrame;
import group.anmv.ui.components.dialouges.SuccessFrame;
import group.anmv.ui.models.Ingredient;
import group.anmv.utils.save.SaveHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to facilitate user addition of ingredients to grocery list via GUI
 *
 * @author Nathan Smith
 */
public class EntryFrame extends JFrame {

    ArrayList<Ingredient> grocerylist;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private DriverFrame driverFrame;

    public EntryFrame(final ArrayList<Ingredient> groceryList, DriverFrame driverFrame) {
        super("Add a New Ingredient");
        this.grocerylist = groceryList;
        this.driverFrame = driverFrame;
        buttonPanel = new JPanel();
        displayPanel = new JPanel();

        final var nameTextField = new JTextField(20);
        final var costTextField = new JTextField(20);
        final var quanTextField = new JTextField(20);

        displayPanel.add(new JLabel("Ingredient Name:"));
        displayPanel.add(nameTextField);
        displayPanel.add(new JLabel("Ingredient Unit Cost:"));
        displayPanel.add(costTextField);
        displayPanel.add(new JLabel("Ingredient Quantity:"));
        displayPanel.add(quanTextField);
        displayPanel.setLayout(new GridLayout(3, 4));
        JButton addIngredient = new JButton("Add Ingredient");
        JButton closeWindow = new JButton("Close");

        closeWindow.addActionListener((actionPerformed) -> setVisible(false));

        addIngredient.addActionListener((e) -> {
            try {
                final var itemName = nameTextField.getText();
                final var cost = Double.parseDouble(costTextField.getText());
                final var quantity = Integer.parseInt(quanTextField.getText());
                final var ingredient = new Ingredient(itemName, cost, quantity);

                String[] ingredientMetaData = {
                        itemName,
                        String.valueOf(cost),
                        String.valueOf(quantity),
                        String.valueOf(ingredient.calcTotalCost())
                };

                driverFrame.getTableModel().addRow(ingredientMetaData);
                driverFrame.addIngredient(ingredient);
                SaveHandler.appendItem(ingredient);
                new SuccessFrame("Successfully added ingredient with name: " + ingredient.getName());
                setVisible(false);
            } catch (NumberFormatException ex) {
                new ErrorFrame("Invalid Entry");
            } catch (IOException ex) {
                ex.printStackTrace();
                new ErrorFrame("There was an error saving that data!");
            }

        });


        buttonPanel.add(addIngredient);
        buttonPanel.add(closeWindow);
        add(displayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        this.setLocationByPlatform(true);
        pack();
        setVisible(true);
    }
}
