package rewrote.ui.tables;

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



public class ArraysTableModel extends DefaultTableModel {

    protected static final String MAIN_TAB_XML = "maintab.xml";
    private static final String TAG_BLOCK_NAME = "arrays";
    private static final String NAME_TAG = "name";
    private static final String DESCRIPTION_TAG = "description";


    public ArraysTableModel(){
        super();
        try {
            setColumnIdentifiers(getHeader());
        } catch (SAXException | ParserConfigurationException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    protected Object[] getHeader() throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(new File(MAIN_TAB_XML));
        document.getDocumentElement().normalize();
        Node node = document.getElementsByTagName(TAG_BLOCK_NAME).item(0);
        String[] header = new String[2];

        header[0] = ((Element) node).getElementsByTagName(NAME_TAG).item(0).getTextContent();
        header[1] = ((Element) node).getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
        return new Object[]{header[0], header[1]};
    }


    public String getDescription(String name){
        int index = getRow(name);
        return (index != -1) ? (String) getValueAt(index, 1) : "";
    }

    protected int getRow(String name){
        int index = -1;
        for(int i = 0; i < getRowCount();i++)
            if(getValueAt(i,0).equals(name))
                index = i;
        return index;
    }

    public static void main(String[] args) {
        ArraysTableModel atm = new ArraysTableModel();

    }

}
