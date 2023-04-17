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
    private DriverFrame DFrame;
    public GroceryEntry(ArrayList<Ingredient> grocerylist, DriverFrame DFrame) {
        super("Add a New Ingredient");
        this.grocerylist = grocerylist;
        thisgroceryentry = this;
        buttonPanel = new JPanel();
        displayPanel = new JPanel();
        this.DFrame=DFrame;

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

        closeWindow.addActionListener((actionPerformed) -> thisgroceryentry.setVisible(false));

        addIngredient.addActionListener((e) -> {
            try {
                final var itemName = nameTextField.getText();
                final var cost = Double.parseDouble(costTextField.getText());
                final var quantity = Integer.parseInt(quanTextField.getText());
                Ingredient i=new Ingredient(itemName, cost, quantity);
                grocerylist.add(i);
                String ing[]={i.getName(), String.valueOf(i.getCost()), String.valueOf(i.getQuantity()), String.valueOf(i.calcTotalCost())};
                DFrame.getTableModel().addRow(ing);
                // TODO: Updating in-memory item list
                SaveHandler.appendItem(new Ingredient(itemName, cost, quantity));
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
