package edu.training.parsing.entity;

/**
 * Created by Roman on 18.11.2016.
 */
public class Pharm {
    private Certificate certificate = new Certificate();
    private Dosage dosage = new Dosage();
    private Package medPackage = new Package();

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public Package getMedPackage() {
        return medPackage;
    }

    public void setMedPackage(Package medPackage) {
        this.medPackage = medPackage;
    }

    @Override
    public String toString() {
        return "Pharm:\n" +
                "\t\t" + certificate + '\n' +
                "\t\t" + dosage + '\n' +
                "\t\t" + medPackage;
    }
}
