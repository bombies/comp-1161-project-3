package group.anmv.ui.models;

/**
 * Class to facilitate objects for ingredients in grocery list
 * @author Nathan Smith
 */
public class Ingredient implements Comparable <Ingredient> {

    private String name;
    private double cost;
    public Ingredient() {
        this.name = null;
        this.cost = -1;
        this.quantity=0;
    }
    private int quantity;   /**
     * Constructor for Ingredients object
    * @param name name of ingredient
     * @param cost cost of ingredient
     * @param quantity quantity of ingredient
     */
    public Ingredient(String name, double cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
    public double getQuan() {
        return quantity;
    }

    public double calctotalingcost(){return cost*quantity;}
    public int compareTo(Ingredient other)
    {
        return (int) (other.getCost() - this.getCost());
    }

}
