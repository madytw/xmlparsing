package edu.training.parsing.parser;

/**
 * Created by Roman on 20.11.2016.
 */
public class MedicineBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }
    public AbstractMedicineBuilder createStudentBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new MedicineDOMBuilder();
            case STAX:
                return new MedicineStAXBuilder();
            case SAX:
                return new MedicineSAXBuilder();
            default:
                throw new EnumConstantNotPresentException (type.getDeclaringClass(), type.name());
        }
    }

}
