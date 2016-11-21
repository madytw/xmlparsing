package edu.training.parsing.parser;

import edu.training.parsing.entity.AbstractMedicine;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Roman on 19.11.2016.
 */
public class MedicineSAXBuilder extends AbstractMedicineBuilder{
    private MedicineHandler handler;
    private XMLReader reader;
    public MedicineSAXBuilder() {
        super();
        handler = new MedicineHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
        } catch (SAXException e) {
            LOG.error("SAX parser error: " + e);
        }
    }
    public Set<AbstractMedicine> getMedicines() {
        return medicines;
    }
    public void buildSetMedicines(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            LOG.error("SAX parser error: " + e);
        } catch (IOException e) {
            LOG.error("I/O error: " + e);
        }
        medicines = this.handler.getStudents();
    }

}
