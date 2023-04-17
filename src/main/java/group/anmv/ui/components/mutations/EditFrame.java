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
 * want to edit in the list
 *
 * @author Mohitha
 */
public class EditFrame extends JFrame {

    private DriverFrame driverFrame;
    private JPanel buttonPanel;

    public EditFrame(ArrayList<Ingredient> groceryList, DriverFrame driverFrame) {
        super("Editing of Ingredient");
        JButton editIngredientButton = new JButton("Edit Ingredient");
        buttonPanel = new JPanel();
        this.driverFrame = driverFrame;
        final var editPanel = new JPanel();
        final var editComponentPanel = new JPanel();
        final var nameTextField = new JTextField(20);
        editPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        editPanel.setLayout(new GridLayout(0, 1));
        editPanel.setSize(1500, 2000);
        setContentPane(editPanel);

        editPanel.add(new JLabel("Ingredient Name to Edit:"));
        editPanel.add(nameTextField);
        nameTextField.addActionListener((e) ->
        {
            final var entry = nameTextField.getText();
            groceryList.stream()
                    .filter(item -> item.getName().equalsIgnoreCase(entry.strip()))
                    .findFirst()
                    .ifPresentOrElse(item -> {
                        editComponentPanel.setBorder(new EmptyBorder(10, 40, 5, 40));
                        editComponentPanel.setLayout(new GridLayout(3, 1));
                        editComponentPanel.setSize(1000, 2000);
                        setContentPane(editComponentPanel);
                        final var costTextField = new JTextField(50);
                        final var quantityTextField = new JTextField(50);
                        editComponentPanel.add(new JLabel("Updated Cost:"));
                        editComponentPanel.add(costTextField);
                        editComponentPanel.add(new JLabel("Edited Quantity:"));
                        editComponentPanel.add(quantityTextField);
                        editComponentPanel.setVisible(true);
                        buttonPanel.add(editIngredientButton);
                        add(buttonPanel);
                        buttonPanel.setVisible(true);

                        revalidate();
                        repaint();

                        editIngredientButton.addActionListener((l) -> {
                            double cost = item.getCost();
                            int quantity = (int) item.getQuantity();
                            try {
                                final var costEntry = costTextField.getText();
                                final var quantityEntry = quantityTextField.getText();

                                if (!costEntry.isBlank() && !costEntry.isEmpty())
                                    cost = Double.parseDouble(costEntry);
                                if (!quantityEntry.isBlank() && !quantityEntry.isEmpty())
                                    quantity = Integer.parseInt(quantityEntry);

                                final var newIngredient = new Ingredient(item.getName(), cost, quantity);

                                SaveHandler.ins().editItem(item.getName(), newIngredient);
                                this.driverFrame.editIngredient(item.getName(), newIngredient);
                                new SuccessFrame("Successfully edit item with name: " + item.getName());
                                setVisible(false);
                            } catch (NumberFormatException ex) {
                                new ErrorFrame("Invalid Entry");
                            } catch (IOException ex) {
                                new ErrorFrame("There was an error attempting to edit that ingredient in your save!");
                            }
                        });
                    }, () -> new ErrorFrame("Invalid Entry!"));
        });
        setResizable(true);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }
}
