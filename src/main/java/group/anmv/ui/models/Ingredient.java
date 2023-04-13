package group.anmv.ui.models;

/**
 * Class to facilitate objects for ingredients in grocery list
 * @author Nathan Smith
 */
public class Ingredient {

    private String name;
    private int cost;

    public Ingredient() {
        this.name = null;
        this.cost = -1;
    }

    /**
     * Constructor for Ingredients object
     * @param name name of ingredient
     * @param cost cost of ingredient
     */
    public Ingredient(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

}
