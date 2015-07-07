package rewrote.ui.tables;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class RealDataTableModel extends DefaultTableModel {

    private final static String TAG_BLOCK_NAME = "real-data";
    private final static String TIME_TAG = "time";
    protected static final String MAIN_TAB_XML = "maintab.xml";
    private int columns;
    private int rows;

    public RealDataTableModel(){
        super();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
        Object[] columnHeaders = new Object[columnIdentifiers.length+1];
        columnHeaders[0] = getHeader();
        System.arraycopy(columnIdentifiers, 0, columnHeaders, 1, columnHeaders.length - 1);
        Object[][] data = new Object[dataVector.length][dataVector[0].length+1];
        for(int i=0; i<data.length; i++)
            data[i][0] = i;

        for(int i=0; i<data.length; i++)
            System.arraycopy(dataVector[i], 0, data[i], 1, data[0].length - 1);

        columns = data[0].length;
        rows = data.length;

        super.setDataVector(data, columnHeaders);
    }

    //TODO: Сделать
   /* @Override
    public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }*/


    public Object[] getColumn(String columnName){
        int index = findColumn(columnName);
        return getColumn(index);
    }

    public Object[] getColumn(int index){
        List<Object> column = new ArrayList<>();
        for(int i = 0; i < rows; i++)
            column.add(getValueAt(i,index));
        return column.toArray();
    }

    public Object[] getRow(int index){
        List<Object> row = new ArrayList<>();
        for(int i=1; i<columns; i++)
            row.add(getValueAt(i,index));
        return row.toArray();
    }

    private String getHeader(){
        String time = "";
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(new File(MAIN_TAB_XML));
            document.getDocumentElement().normalize();
            Node node = document.getElementsByTagName(TAG_BLOCK_NAME).item(0);
            time = ((Element) node).getElementsByTagName(TIME_TAG).item(0).getTextContent();
        } catch (ParserConfigurationException | IOException | SAXException e){
            e.printStackTrace();
        }
        return time;
    }

}
