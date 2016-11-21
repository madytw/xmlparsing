package edu.training.parsing.parser;

/**
 * Created by Roman on 19.11.2016.
 */
public enum DrugEnum {
    DRUG("drug"), NAME("name"), GROUP("group"), PHARM("pharm"), CERTIFICATE("certificate"), PACKAGE("package"), DOSAGE("dosage"),
    ID("id"), DATE_OF_ISSUE("date-of-issue"), EXPIRATION_DATE("expiration-date"), VERSION("version"),
    REGISTRY_ORGANIZATION("registry-organization"), PACKAGE_TYPE("package-type"), AMOUNT("amount"),
    PRICE("price"), DRUG_DOSAGE("drug-dosage"), FREQUENCY_RECEPTION("frequency-reception"), ANALOG("analog"),
    PRODUCER("producer"), VIEW("view");
    private String value;
    private DrugEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
