package namedstruct;

/**
 * Created by Azunai on 13.04.2015.
 */
public class Const {
    private String name;
    private double value;

    public Const(String name){
        this.name = name;
    }
    public Const(String name, double value){
        this.name = name;
        this.value = value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public String getName(){
        return name;
    }
}
