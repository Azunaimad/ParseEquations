package rewrote.ns.structures;

import namedstruct.Array;
import rewrote.ns.basics.AbstractNamed;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


public class OperandsList<E extends AbstractNamed> extends ArrayList<E>{

    /**
     * Construct an empty list with an initial capacity of ten
     */
    public OperandsList(){
        super();
    }

    /**
     * Create list contains specified elements
     * @param namedOperands operands we want to specify in OperandsList
     */
    public OperandsList(E[] namedOperands){
        super();
        Collections.addAll(this, namedOperands);
    }

    /**
     * Get name of operand by its position (id) in OperandsList if id < size()
     * @param id of the operand
     * @return operand's name with position id
     */
    public String getNameById(int id){
        String name = "";
        try{
            name = get(id).getName();
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Get id (or position in OperandsList) of the operand by its name if it exists
     * @param name of the operand
     * @return position in OperandsList
     */
    public int getIdByName(String name){
        int id = -1;

        for(int i=0; i<size(); i++)
            if(get(i).getName().equals(name))
                id = i;

        return id;
    }

    /**
     * Get operand by its name if it exists in OperandsList
     * @param name of the operand
     * @return operand
     */
    public E get(String name){
        E operand = null;

        for(E namedOperand : this)
            if(namedOperand.getName().equals(name))
                operand = namedOperand;

        return operand;
    }

    /**
     * Returns true if and only if contains at least one operand with specified name
     * @param name of the operand
     * @return true if the list contains operand with specified name
     */
    public boolean contains(String name){
        boolean flag = false;
        for (E namedOperand : this)
            if (namedOperand.getName().equals(name))
                flag = true;

        return flag;
    }

}
