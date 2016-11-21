package edu.training.parsing.parser;

import edu.training.parsing.entity.*;
import edu.training.parsing.entity.Package;
import edu.training.parsing.type.InjectionType;
import edu.training.parsing.type.MedGroup;
import edu.training.parsing.type.MedView;
import edu.training.parsing.type.PackageType;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Roman on 20.11.2016.
 */
public class MedicineStAXBuilder extends AbstractMedicineBuilder{
    private XMLInputFactory inputFactory;

    public MedicineStAXBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    public Set<AbstractMedicine> getMedicines() {
        return medicines;
    }

    public void buildSetMedicines(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if("medicines".equals(name)){
                        continue;
                    }
                    if (name.equals(DrugEnum.DRUG.getValue())) {
                        Drug drug = buildDrug(reader);
                        medicines.add(drug);
                    } else if (name.equals(InjectionEnum.INJECTION.getValue())) {
                        Injection injection = buildInjection(reader);
                        medicines.add(injection);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            LOG.error("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            LOG.error("File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.error("Impossible close file " + fileName + " : " + e);
            }
        }
    }

    private Drug buildDrug(XMLStreamReader reader) throws XMLStreamException {
        Drug drug = new Drug();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    switch (DrugEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            drug.setName(getXMLText(reader));
                            break;
                        case GROUP:
                            drug.setGroup(MedGroup.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case PHARM:
                            drug.setPharm(getXMLPharm(reader));
                            break;
                        case ANALOG:
                            drug.addAnalogue(getXMLText(reader));
                            break;
                        case VERSION:
                            drug.addVersion(getXMLVersion(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.DRUG) {
                        return drug;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Drug");
    }

    private Injection buildInjection(XMLStreamReader reader) throws XMLStreamException {
        Injection injection = new Injection();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    switch (InjectionEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            injection.setName(getXMLText(reader));
                            break;
                        case GROUP:
                            injection.setGroup(MedGroup.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case INJECTION_TYPE:
                            injection.setInjectionType(InjectionType.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case DOSAGE:
                            injection.setDosage(getXMLDosage(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (InjectionEnum.valueOf(name.toUpperCase()) == InjectionEnum.INJECTION) {
                        return injection;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Injection");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private Pharm getXMLPharm(XMLStreamReader reader) throws XMLStreamException {
        Pharm pharm = new Pharm();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    switch (DrugEnum.valueOf(name.toUpperCase())) {
                        case CERTIFICATE:
                            pharm.setCertificate(getXMLCertificate(reader));
                            break;
                        case DOSAGE:
                            pharm.setDosage(getXMLDosage(reader));
                            break;
                        case PACKAGE:
                            pharm.setMedPackage(getXMLPackage(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.PHARM || DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.PRODUCER) {
                        return pharm;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Pharm");
    }

    private Certificate getXMLCertificate(XMLStreamReader reader) throws XMLStreamException {
        Certificate certificate = new Certificate();
        certificate.setCertificateId(reader.getAttributeValue(null, DrugEnum.ID.getValue()));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    switch (DrugEnum.valueOf(name.toUpperCase())) {
                        case DATE_OF_ISSUE:
                            certificate.setDateOfIssue(LocalDate.parse(getXMLText(reader)));
                            break;
                        case EXPIRATION_DATE:
                            certificate.setExpirationDate(LocalDate.parse(getXMLText(reader)));
                            break;
                        case REGISTRY_ORGANIZATION:
                            certificate.setRegistryOrganization(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.CERTIFICATE) {
                        return certificate;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Certificate");
    }

    private Dosage getXMLDosage(XMLStreamReader reader) {
        Dosage dosage = new Dosage();
        dosage.setDrugDosage(Integer.parseInt(reader.getAttributeValue(null, DrugEnum.DRUG_DOSAGE.getValue())));
        if(reader.getAttributeValue(null, DrugEnum.FREQUENCY_RECEPTION.getValue()) != null){
            dosage.setFrequencyReception(Integer.parseInt(reader.getAttributeValue(null, DrugEnum.FREQUENCY_RECEPTION.getValue())));
        }
        return dosage;
    }

    private Package getXMLPackage(XMLStreamReader reader) throws XMLStreamException {
        Package medPackage = new Package();
        medPackage.setType(PackageType.valueOf(reader.getAttributeValue(null, DrugEnum.PACKAGE_TYPE.getValue()).toUpperCase()));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name.replaceAll("-","_");
                    switch (DrugEnum.valueOf(name.toUpperCase())) {
                        case AMOUNT:
                            medPackage.setAmount(Integer.parseInt(getXMLText(reader)));
                            break;
                        case PRICE:
                            medPackage.setPrice(Integer.parseInt(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.PACKAGE) {
                        return medPackage;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Package");
    }

    private Version getXMLVersion(XMLStreamReader reader) throws XMLStreamException {
        Version version = new Version();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    name.replaceAll("-","_");
                    switch (DrugEnum.valueOf(name.toUpperCase())) {
                        case PRODUCER:
                            version.add(getXMLPharm(reader));
                            break;
                        case VIEW:
                            version.setView(MedView.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    name = name.replaceAll("-","_");
                    if (DrugEnum.valueOf(name.toUpperCase()) == DrugEnum.VERSION) {
                        return version;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Version");
    }

}
