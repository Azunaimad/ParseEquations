package rewrote.ui.tables;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.File;
import java.io.IOException;

//TODO: изменить ширину столбцов
public class ConstantsTableModel extends ArraysTableModel {
    private static final String TAG_BLOCK_NAME = "constants";
    private static final String NAME_TAG = "name";
    private static final String DESCRIPTION_TAG = "description";
    private static final String VALUE_TAG = "value";

    public ConstantsTableModel(){
        super();
    }

    @Override
    protected Object[] getHeader() throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(new File(MAIN_TAB_XML));
        document.getDocumentElement().normalize();
        Node node = document.getElementsByTagName(TAG_BLOCK_NAME).item(0);
        String[] header = new String[3];

        header[0] = ((Element) node).getElementsByTagName(NAME_TAG).item(0).getTextContent();
        header[1] = ((Element) node).getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
        header[2] = ((Element) node).getElementsByTagName(VALUE_TAG).item(0).getTextContent();

        return new Object[]{header[0], header[1], header[2]};
    }

    public double getValue(String name){
        int index = getRow(name);
        return (index != -1) ? (double) getValueAt(index, 2) : 0.0;
    }

    public static void main(String[] args) {
        ConstantsTableModel cst = new ConstantsTableModel();

    }
}
