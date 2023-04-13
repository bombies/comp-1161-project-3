package group.anmv.models;

public class Ingredients {

    private String name;
    private int cost;

    public Ingredients(String name, int cost){
        this.name=name;
        this.cost=cost;
    }

    public String getName(){
        return name;
    }

    public int getCost(){
        return cost;
    }

}
