package interf;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Azunai on 21.04.2015.
 */
public class TableModel extends AbstractTableModel {

    //TODO: сделать нормальное расположение по центру
    private String[] columnsNames = {"       Name", "    Definition", "       Value"};
    private Object[][] data;

    public TableModel(Object[][] data){
        this.data = data;
    }
    public TableModel(){

    }

    public void setData(Object[][] data){
        this.data = data;
    }

    @Override
    public int getRowCount() {
        if(data != null)
            return data.length;
        else
            return 0;
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnsNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

}
