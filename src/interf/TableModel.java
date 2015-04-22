package interf;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.NoSuchElementException;

/**
 * Created by Azunai on 21.04.2015.
 */
public class TableModel extends DefaultTableModel {

    //TODO: сделать нормальное расположение по центру
    private Object[][] data;

    public TableModel(Object[][] data){
        this.data = data;
    }
    public TableModel(){
        super(new Object[]{"Name", "             Definition", "       Value"},0);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column==0)
            return false;
        else
            return true;
    }

    public Object[][] getData(){
        return data;
    }

    /*@Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 2: return Double.class;
            default: return String.class;
        }
    }*/

    //TODO: исправить баг, который не позваляет задать число #.0, т.к. считает это Double, а остальные числа String
    public double getValue(String name){
        int index = -1;
        for(int i=0; i<getRowCount();i++)
            if(getValueAt(i,0).equals(name))
                index = i;
        if(index != -1)
            return Double.parseDouble((String) getValueAt(index,2));
        else
            return 0.0;
    }



    public String getDefinition(String name) {
        int index = -1;
        for(int i=0; i<getRowCount();i++)
            if(getValueAt(i,0).equals(name))
                index = i;
        return (String) getValueAt(index,2);
    }

}
