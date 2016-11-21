package edu.training.parsing.parser;

import edu.training.parsing.entity.*;
import edu.training.parsing.type.InjectionType;
import edu.training.parsing.type.MedGroup;
import edu.training.parsing.type.MedView;
import edu.training.parsing.type.PackageType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Roman on 18.11.2016.
 */
public class MedicineHandler extends DefaultHandler {
    private Set<AbstractMedicine> medicines;
    private boolean isDrug;
    private boolean isVersion;
    private Pharm producer = new Pharm();
    private Version version = new Version();
    private AbstractMedicine current = null;
    private DrugEnum currDrugEnum = null;
    private InjectionEnum currInjEnum = null;
    private EnumSet<DrugEnum> drugEnum;
    private EnumSet<InjectionEnum> injectionEnum;

    public MedicineHandler() {
        medicines = new HashSet<AbstractMedicine>();
        drugEnum = EnumSet.allOf(DrugEnum.class);
        injectionEnum = EnumSet.allOf(InjectionEnum.class);
    }

    public Set<AbstractMedicine> getStudents() {
        return medicines;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        localName = localName.replaceAll("-","_");
        switch (localName) {
            case "drug":
                createDrug();
                break;
            case "injection":
                createInjection();
                break;
            case "certificate":
                setCertificateAttributes(attributes);
                break;
            case "dosage":
                setDosageAttributes(attributes);
                break;
            case "package":
                setPackageAttributes(attributes);
                break;
            case "version":
                createVersion();
                break;
            case "producer":
                createProducer();
                break;
            case "medicines":
                return;
        }
        setCurrentTag(localName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("producer".equals(localName)) {
            version.add(producer);
            producer = null;
        }
        if("version".equals(localName)) {
            ((Drug)current).addVersion(version);
            isVersion=false;
            version = null;
        }
        if("drug".equals(localName) || "injection".equals(localName)){
            medicines.add(current);
            current = null;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length).trim();
        if (isDrug) {
            setDrugInfo(s);
        }else{
            setInjectionInfo(s);
        }
    }

    private void createDrug() {
        current = new Drug();
        isDrug = true;
    }

    private void createInjection() {
        current = new Injection();
        isDrug = false;
    }

    private void setCertificateAttributes(Attributes attributes) {
        if(isVersion) {
            producer.getCertificate().setCertificateId(attributes.getValue(0));
            return;
        }
        if (isDrug) {
            ((Drug) current).getPharm().getCertificate().setCertificateId(attributes.getValue(0));
        }
    }

    private void setDosageAttributes(Attributes attributes) {
        if(isVersion) {
            producer.getDosage().setDrugDosage(Integer.parseInt(attributes.getValue(0)));
            if (attributes.getLength() == 2) {
                producer.getDosage().setFrequencyReception(Integer.parseInt(attributes.getValue(1)));
            }
            return;
        }
        if (isDrug) {
            ((Drug) current).getPharm().getDosage().setDrugDosage(Integer.parseInt(attributes.getValue(0)));
            if (attributes.getLength() == 2) {
                ((Drug) current).getPharm().getDosage().setFrequencyReception(Integer.parseInt(attributes.getValue(1)));
            }
        } else {
            ((Injection) current).getDosage().setDrugDosage(Integer.parseInt(attributes.getValue(0)));
            if (attributes.getLength() == 2) {
                ((Injection) current).getDosage().setFrequencyReception(Integer.parseInt(attributes.getValue(1)));
            }
        }
    }

    private void setPackageAttributes(Attributes attributes) {
        if(isVersion) {
            producer.getMedPackage().setType(PackageType.valueOf(attributes.getValue(0).toUpperCase()));
            return;
        }
        if (isDrug) {
            ((Drug) current).getPharm().getMedPackage().setType(PackageType.valueOf(attributes.getValue(0).toUpperCase()));
        }
    }

    private void createVersion(){
        isVersion = true;
        version = new Version();
    }

    private void createProducer(){
        isVersion = true;
        producer = new Pharm();
    }


    private void setCurrentTag(String localName){
        if (isDrug) {
            DrugEnum temp = DrugEnum.valueOf(localName.toUpperCase());
            if (drugEnum.contains(temp)) {
                currDrugEnum = temp;
            }
        } else {
            InjectionEnum temp = InjectionEnum.valueOf(localName.toUpperCase());
            if (injectionEnum.contains(temp)) {
                currInjEnum = temp;
            }
        }
    }

    private void setDrugInfo(String s){
        if (currDrugEnum != null) {
            switch (currDrugEnum) {
                case NAME:
                    current.setName(s);
                    break;
                case GROUP:
                    current.setGroup(MedGroup.valueOf(s.toUpperCase()));
                    break;
                case DATE_OF_ISSUE:
                    if (isVersion) {
                        producer.getCertificate().setDateOfIssue(LocalDate.parse(s));
                    } else {
                        ((Drug) current).getPharm().getCertificate().setDateOfIssue(LocalDate.parse(s));
                    }
                    break;
                case EXPIRATION_DATE:
                    if (isVersion) {
                        producer.getCertificate().setExpirationDate(LocalDate.parse(s));
                    } else {
                        ((Drug) current).getPharm().getCertificate().setExpirationDate(LocalDate.parse(s));
                    }
                    break;
                case REGISTRY_ORGANIZATION:
                    if (isVersion) {
                        producer.getCertificate().setRegistryOrganization(s);
                    } else {
                        ((Drug) current).getPharm().getCertificate().setRegistryOrganization(s);
                    }
                    break;
                case AMOUNT:
                    if (isVersion) {
                        producer.getMedPackage().setAmount(Integer.parseInt(s));
                    } else {
                        ((Drug) current).getPharm().getMedPackage().setAmount(Integer.parseInt(s));
                    }
                    break;
                case PRICE:
                    if (isVersion) {
                        producer.getMedPackage().setPrice(Integer.parseInt(s));
                    } else {
                        ((Drug) current).getPharm().getMedPackage().setPrice(Integer.parseInt(s));
                    }
                    break;
                case ANALOG:
                    ((Drug) current).addAnalogue(s);
                    break;
                case VIEW:
                    version.setView(MedView.valueOf(s.toUpperCase()));
                    break;
            }
        }
        currDrugEnum=null;
    }

    private void setInjectionInfo(String s){
        if(currInjEnum!=null){
            switch (currInjEnum){
                case NAME:
                    current.setName(s);
                    break;
                case GROUP:
                    current.setGroup(MedGroup.valueOf(s.toUpperCase()));
                    break;
                case INJECTION_TYPE:
                    ((Injection)current).setInjectionType(InjectionType.valueOf(s.toUpperCase()));
                    break;
            }
        }
        currInjEnum=null;
    }
}
