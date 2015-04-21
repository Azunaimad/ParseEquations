package namedstruct;


/**
 * Named array
 * @author Ponomarev George
 */
public class Array {
    private String name;
    private double[][] value;

    public Array(String name, int nOfElements, int nOfIterations){
        this.name = name;
        value = new double[nOfElements][nOfIterations];
    }

    /**
     * Set element value
     * @param val - value, which we want to set
     * @param position - position in array
     */
    public void setValue(double val, int position, int iteration){
        try{
            value[position][iteration] = val;
        } catch (ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
    }

    /**
     * Get element value
     * @param position - position in array
     * @return - element value
     */
    public double getValue(int position, int iteration){
        double res = Double.NaN;
        try{
            res = value[position][iteration];
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
