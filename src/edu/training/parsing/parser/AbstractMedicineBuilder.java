package edu.training.parsing.parser;

import edu.training.parsing.entity.AbstractMedicine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Roman on 20.11.2016.
 */
public abstract class AbstractMedicineBuilder  {
    protected static final Logger LOG = LogManager.getLogger();
    protected Set<AbstractMedicine> medicines;
    public AbstractMedicineBuilder() {
        medicines = new HashSet<AbstractMedicine>();
    }

    public Set<AbstractMedicine> getMedicines() {
        return medicines;
    }
    abstract public void buildSetMedicines(String fileName);

}
