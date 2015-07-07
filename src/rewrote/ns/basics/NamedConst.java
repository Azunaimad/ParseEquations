package rewrote.ns.basics;

/**
 * Created by Azunai on 21.05.2015.
 */
public class NamedConst extends AbstractNamed {
    private double value;

    public NamedConst(String name){
        setName(name);
    }

    public NamedConst(String name, double value){
        setName(name);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
