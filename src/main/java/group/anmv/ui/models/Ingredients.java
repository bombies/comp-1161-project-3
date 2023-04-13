package group.anmv.ui.models;

/**
 * Class to facilitate objects for ingredients in grocery list
 * @author Nathan Smith
 */
public class Ingredients {

    private String name;
    private int cost;

    /**
     * Constructor for Ingredients object
     * @param name name of ingredient
     * @param cost cost of ingredient
     */
    public Ingredients(String name, int cost) {
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
