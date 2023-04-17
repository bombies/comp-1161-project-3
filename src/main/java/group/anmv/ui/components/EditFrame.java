package group.anmv.ui.components;

import group.anmv.ui.DriverFrame;
import group.anmv.ui.models.Ingredient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private int index_found;
    private Ingredient found_ingredient;

    public EditFrame(ArrayList<Ingredient> grocerylist, DriverFrame dframe) {
        super("Editing of Ingredient");
        JButton Edit = new JButton("Edit Ingredient");
        buttonPanel=new JPanel();
        this.driverFrame = dframe;
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
            boolean found = false;
            for (int i = 0; i < grocerylist.size(); i++) {
                if (grocerylist.get(i).getName().equalsIgnoreCase(entry.strip())) {
                    found = true;
                    found_ingredient=grocerylist.get(i);
                    index_found=i;
                    editPanel.setVisible(false);
                    break;
                }
            }
            if (!found)
                new ErrorFrame("Invalid Entry!");
            if (found) {
                editComponentPanel.setBorder(new EmptyBorder(10, 40, 5, 40));
                editComponentPanel.setLayout(new GridLayout(3, 1));
                editComponentPanel.setSize(1000, 2000);
                setContentPane(editComponentPanel);
                final var costTextField = new JTextField(50);
                final var quanTextField = new JTextField(50);
                editComponentPanel.add(new JLabel("Updated Cost:"));
                editComponentPanel.add(costTextField);
                editComponentPanel.add(new JLabel("Edited Quantity:"));
                editComponentPanel.add(quanTextField);
                editComponentPanel.setVisible(true);
                buttonPanel.add(Edit);
                add(buttonPanel);
                buttonPanel.setVisible(true);
                Edit.addActionListener((l)->{
                    double cost = 0;
                    int quan = 0;
                    try {
                        cost = Double.parseDouble(costTextField.getText());
                        quan = Integer.parseInt(quanTextField.getText());
                    } catch (NumberFormatException ex) {
                        new ErrorFrame("Invalid Entry");
                    }
                    grocerylist.set(index_found, new Ingredient(found_ingredient.getName(), cost, quan));
                    driverFrame.getTableModel().setRowCount(0);
                    driverFrame.populateTable(grocerylist);
                    setVisible(false);
                });


            }
        });
        setResizable(true);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }
}
