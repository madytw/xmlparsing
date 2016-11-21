package edu.training.parsing.valid;

import edu.training.parsing.exception.FileNotValidException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by Roman on 18.11.2016.
 */
public class ValidatorXSD {
    private static final String LANGUAGE = XMLConstants.W3C_XML_SCHEMA_NS_URI;

    public static void validate(MedicineErrorHandler medHandler, final String fileName, final String schemaName) throws FileNotValidException, SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(LANGUAGE);
        File schemaLocation = new File(schemaName);
        Schema schema = factory.newSchema(schemaLocation);
        Validator validator = schema.newValidator();
        validator.setErrorHandler(medHandler);
        Source source = new StreamSource(fileName);
        validator.validate(source);
        if (!medHandler.isValid()) {
            throw new FileNotValidException("File not valid!");
        }
    }
}
