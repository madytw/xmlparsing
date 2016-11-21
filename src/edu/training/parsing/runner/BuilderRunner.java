package edu.training.parsing.runner;

import edu.training.parsing.exception.FileNotValidException;
import edu.training.parsing.parser.AbstractMedicineBuilder;
import edu.training.parsing.parser.MedicineBuilderFactory;
import edu.training.parsing.valid.MedicineErrorHandler;
import edu.training.parsing.valid.ValidatorXSD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by Roman on 19.11.2016.
 */
public class BuilderRunner {
    private static final String PARSING_TYPE = "sax";
    private static final String FILE_NAME = "data/meds.xml";
    private static final String SCHEMA_NAME = "data/schema.xsd";
    private static final Logger LOG = LogManager.getLogger();

    public static void main(String... args) {
        try {
            ValidatorXSD.validate(new MedicineErrorHandler(), FILE_NAME, SCHEMA_NAME);
            MedicineBuilderFactory factory = new MedicineBuilderFactory();
            AbstractMedicineBuilder builder = factory.createStudentBuilder(PARSING_TYPE);
            builder.buildSetMedicines(FILE_NAME);
            LOG.info(builder.getMedicines());
        } catch (FileNotValidException | SAXException | IOException e) {
            LOG.fatal(e);
            throw new RuntimeException(e);
        }
    }
}
