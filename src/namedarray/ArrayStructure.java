package namedarray;

/**
 * Array of named arrays
 * @author Ponomarev George
 */
public class ArrayStructure {
    private Array[] arrays;

    public ArrayStructure(String[] name, int nOfElements, int nOfArrays) {
        arrays = new Array[nOfArrays];
        for (int i=0; i< arrays.length; i++)
            arrays[i] = new Array(name[i], nOfElements);
    }

    /**
     * get element from an named Array
     * @param arrayName - array Name
     * @param position - position in array
     * @return element if exist
     */
    public double getElement(String arrayName, int position){
        double res = Double.NaN;
        try {
            for (Array component : arrays) {
                if (component.getName().equals(arrayName))
                    res = component.getValue(position);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Set element to named array
     * @param arrayName - array Name
     * @param position - position in array
     * @param value - element, which we want to set
     */
    public void setElement(String arrayName, int position, double value){
        try{
            for (Array component : arrays)
                if (component.getName().equals(arrayName))
                    component.setValue(value, position);
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
