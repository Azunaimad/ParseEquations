package rewrote.ns.basics;


public class NamedVector extends AbstractNamed {
    protected double[] vector;

    public NamedVector(String name){
        setName(name);
    }

    public NamedVector(String name, int periods){
        setName(name);
        vector = new double[periods];
    }

    public NamedVector(String name, double[] vector){
        setName(name);
        this.vector = vector;
    }

    public double getValue(int period){
        double result = Double.NaN;
        try{
            result = vector[period];
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return result;
    }

    public void setValue(double value, int period){
        try {
            vector[period] = value;
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    public int getNOfElements(){
        return vector.length;
    }
}
