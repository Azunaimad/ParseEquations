package namedstruct;

/**
 * Array of named arrays
 * @author Ponomarev George
 */
public class ArrayStructure {
    private Array[] arrays;

    public ArrayStructure(String[] name, int nOfElements, int nOfIterations) {
        arrays = new Array[name.length];
        for (int i=0; i< arrays.length; i++)
            arrays[i] = new Array(name[i], nOfElements, nOfIterations);
    }

    public ArrayStructure(Array[] arrays){
        this.arrays = arrays;
    }

    public ArrayStructure(){}

    /**
     * get element from an named Array
     * @param arrayName - array Name
     * @param position - position in array
     * @return element if exist
     */
    public double getElement(String arrayName, int position, int iteration){
        double res = Double.NaN;
        try {
            for (Array component : arrays) {
                if (component.getName().equals(arrayName))
                    res = component.getValue(position,iteration);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return res;
    }

    public double getElement(int arrayIndex, int position, int iteration){
        double res = Double.NaN;
        try {
            res = arrays[arrayIndex].getValue(position, iteration);
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
    public void setElement(String arrayName, int position, int iteration, double value){
        try{
            for (Array component : arrays)
                if (component.getName().equals(arrayName))
                    component.setValue(value, position, iteration);
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public void setElement(int arrayIndex, int position, int iteration, double value){
        try{
            arrays[arrayIndex].setValue(value, position, iteration);
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public void addArray(Array array){
        if(arrays != null) {
            Array[] tmp = new Array[arrays.length + 1];
            System.arraycopy(arrays, 0, tmp, 0, tmp.length - 1);
            tmp[tmp.length - 1] = array;

            arrays = new Array[tmp.length];
            arrays = tmp;
        } else {
            Array[] tmp = new Array[1];
            tmp[0] = array;
            arrays = tmp;
        }
    }

    public Array getArray(int arrayIndex){
        return arrays[arrayIndex];
    }

    public String getName(int arrayIndex){
        return arrays[arrayIndex].getName();
    }

    public Array getArray(String name){
        Array result = null;
        for(Array a : arrays)
            if(a.getName().equals(name))
                result = a;
        return result;
    }


    public boolean exist(String name){
        boolean flag = false;
        if(arrays != null)
            for (Array array : arrays)
                if (array.getName().equals(name))
                    flag = true;
        return flag;
    }

    public int length(){
        return arrays.length;
    }
}
