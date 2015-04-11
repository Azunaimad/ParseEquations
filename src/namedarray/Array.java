package namedarray;


/**
 * Named array
 * @author Ponomarev George
 */
public class Array {
    private String name;
    private double[] value;

    protected Array(String name, int nOfElements){
        this.name = name;
        value = new double[nOfElements];
    }

    /**
     * Set element value
     * @param val - value, which we want to set
     * @param position - position in array
     */
    protected void setValue(double val, int position){
        try{
            value[position] = val;
        } catch (ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
    }

    /**
     * Get element value
     * @param position - position in array
     * @return - element value
     */
    protected double getValue(int position){
        double res = Double.NaN;
        try{
            res = value[position];
        } catch (ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
        return res;
    }

    /**
     * Get array name
     * @return - name
     */
    public String getName(){
        return name;
    }
}
