package edu.training.parsing.parser;

/**
 * Created by Roman on 19.11.2016.
 */
public enum InjectionEnum {
    INJECTION("injection"), NAME("name"), GROUP("group"), INJECTION_TYPE("injection-type"),
    DRUG_DOSAGE("drug-dosage"), FREQUENCY_RECEPTION("frequency-reception"), DOSAGE("dosage");
    private String value;
    private InjectionEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
