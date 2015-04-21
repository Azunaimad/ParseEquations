package namedstruct;

/**
 * Created by Azunai on 13.04.2015.
 */
public class ConstStructure {
    Const[] consts;

    public ConstStructure(){}

    public ConstStructure(String[] names, double[] values) throws ArrayIndexOutOfBoundsException{
        consts = new Const[names.length];
        for(int i=0; i<consts.length; i++)
            consts[i] = new Const(names[i], values[i]);
    }

    public ConstStructure(Const[] consts){
        this.consts = consts;
    }

    public double getConstValue(int index){
        return consts[index].getValue();
    }

    public double getConstValue(String name){
        double result = 0;
        for (Const aConst : consts)
            if (aConst.getName().equals(name))
                result = aConst.getValue();
        return result;
    }

    public void setConstValue(int index, double value){
        consts[index].setValue(value);
    }

    public void setConstValue(String name, double value){
        for(Const aConst : consts)
            if(aConst.getName().equals(name))
                aConst.setValue(value);
    }

    public Const getConst(int index){
        return consts[index];
    }

    public Const getConst(String name){
        Const result = null;
        for(Const aConst : consts)
            if(aConst.getName().equals(name))
                result = aConst;
        return result;
    }

    public String getName(int index){
        return consts[index].getName();
    }

    public void addConst(Const c){
        if(consts != null){
            Const[] tmp = new Const[consts.length + 1];
            System.arraycopy(consts, 0, tmp, 0, tmp.length - 1);
            tmp[tmp.length - 1] = c;

            consts = new Const[tmp.length];
            consts = tmp;
        } else {
            Const[] tmp = new Const[1];
            tmp[0] = c;
            consts = tmp;
        }
    }

    public boolean exist(String name){
        boolean flag = false;
        if(consts != null)
            for (Const aConst : consts)
                if (aConst.getName().equals(name))
                    flag = true;
        return flag;
    }

    public int length(){
        return consts.length;
    }
}
