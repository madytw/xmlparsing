package edu.training.parsing.entity;

/**
 * Created by Roman on 18.11.2016.
 */
public class Dosage {
    private int drugDosage;
    private int frequencyReception = 1;

    public int getDrugDosage() {
        return drugDosage;
    }

    public void setDrugDosage(int drugDosage) {
        this.drugDosage = drugDosage;
    }

    public int getFrequencyReception() {
        return frequencyReception;
    }

    public void setFrequencyReception(int frequencyReception) {
        this.frequencyReception = frequencyReception;
    }

    @Override
    public String toString() {
        return "Dosage: " +
                "{Drug Dosage: " + drugDosage + "; " +
                "Frequency Reception: " + frequencyReception +
                "}";
    }
}
