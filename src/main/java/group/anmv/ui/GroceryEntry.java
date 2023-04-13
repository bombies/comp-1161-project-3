package group.anmv.ui;

import group.anmv.ui.components.ErrorFrame;
import group.anmv.ui.models.Ingredient;
import group.anmv.utils.save.SaveHandler;

import javax.swing.*;
import javax.swing.text.InternationalFormatter;
import java.awt.*;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Class to facilitate user addition of ingredients to grocery list via GUI
 *
 * @author Nathan Smith
 */
public class GroceryEntry extends JFrame {

    ArrayList<Ingredient> grocerylist;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private GroceryEntry thisgroceryentry;

    public GroceryEntry(ArrayList<Ingredient> grocerylist) {
        super("Add a New Ingredient");
        this.grocerylist = grocerylist;
        thisgroceryentry = this;
        buttonPanel = new JPanel();
        displayPanel = new JPanel();

        final var nameTextField = new JTextField(20);
        final var costTextField = new JTextField(20);

        displayPanel.add(new JLabel("Ingredient Name:"));
        displayPanel.add(nameTextField);
        displayPanel.add(new JLabel("Ingredient Cost:"));
        displayPanel.add(costTextField);
        displayPanel.setLayout(new GridLayout(3, 2));
        JButton addIngredient = new JButton("Add Ingredient");
        JButton closeWindow = new JButton("Close");

        closeWindow.addActionListener((actionPerformed) -> thisgroceryentry.setVisible(false));

        addIngredient.addActionListener((e) -> {
            try {
                final var itemName = nameTextField.getText();
                final var cost = Double.parseDouble(costTextField.getText());

                // TODO: Updating in-memory item list
                SaveHandler.appendItem(new Ingredient(itemName, cost));
                setVisible(false);
            } catch (NumberFormatException ex) {
                new ErrorFrame("The cost provided is invalid!");
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
