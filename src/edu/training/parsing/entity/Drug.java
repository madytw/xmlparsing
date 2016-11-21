package edu.training.parsing.entity;

import edu.training.parsing.type.MedGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 18.11.2016.
 */
public class Drug extends AbstractMedicine {
    private Pharm pharm = new Pharm();
    private List<String> analogueNames = new ArrayList<>();
    private List<Version> versions = new ArrayList<>();

    public Pharm getPharm() {
        return pharm;
    }

    public void setPharm(Pharm pharm) {
        this.pharm = pharm;
    }

    public boolean addAnalogue(String s) {
        return analogueNames.add(s);
    }

    public boolean addVersion(Version version) {
        return versions.add(version);
    }

    public List<Version> getVersions() {
        return versions;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("\n\t\t");
        versions.stream().forEach(s -> sb.append(s.toString()));
        return "Drug:\n" +
                super.toString() +
                '\t' + pharm + '\n' +
                "\tAnalogue Names=" + analogueNames + '\n' +
                "\tVersions: " + sb.toString() +
                '\n';
    }
}
