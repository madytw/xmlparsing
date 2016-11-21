package edu.training.parsing.entity;

import edu.training.parsing.type.InjectionType;
import edu.training.parsing.type.MedGroup;

/**
 * Created by Roman on 18.11.2016.
 */
public class Injection extends AbstractMedicine {
    private Dosage dosage = new Dosage();
    private InjectionType injectionType = InjectionType.NONE;

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public InjectionType getInjectionType() {
        return injectionType;
    }

    public void setInjectionType(InjectionType injectionType) {
        this.injectionType = injectionType;
    }

    @Override
    public String toString() {
        return "Injection:\n" +
                super.toString() +
                '\t' + dosage + '\n' +
                "\tinjectionType: " + injectionType +
                '\n';
    }
}
