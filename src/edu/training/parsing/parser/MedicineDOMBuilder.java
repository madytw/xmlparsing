package edu.training.parsing.parser;

import edu.training.parsing.entity.*;
import edu.training.parsing.entity.Package;
import edu.training.parsing.type.InjectionType;
import edu.training.parsing.type.MedGroup;
import edu.training.parsing.type.MedView;
import edu.training.parsing.type.PackageType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Roman on 19.11.2016.
 */
public class MedicineDOMBuilder extends AbstractMedicineBuilder{
    private DocumentBuilder docBuilder;
    public MedicineDOMBuilder() {
        super();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOG.error("Parser configuration error: " + e);
        }
    }

    public Set<AbstractMedicine> getStudents() {
        return medicines;
    }

    public void buildSetMedicines(String fileName) {
        Document doc = null;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList drugList = root.getElementsByTagName("drug");
            NodeList injectionList = root.getElementsByTagName("injection");
            for (int i = 0; i < drugList.getLength(); i++) {
                Element drugElement = (Element) drugList.item(i);
                Drug drug = buildDrug(drugElement);
                medicines.add(drug);
            }

            for(int i = 0; i < injectionList.getLength(); i++){
                Element injectionElement = (Element) injectionList.item(i);
                Injection injection = buildInjection(injectionElement);
                medicines.add(injection);
            }
        } catch (IOException e) {
            LOG.error("File error or I/O error: " + e);
        } catch (SAXException e) {
            LOG.error("Parsing failure: " + e);
        }
    }

    private Drug buildDrug(Element drugElement) {
        Drug drug = new Drug();
        drug.setName(getElementTextContent(drugElement, "name"));
        drug.setGroup(MedGroup.valueOf(getElementTextContent(drugElement, "group").toUpperCase()));

        Element pharmElement = (Element) drugElement.getElementsByTagName("pharm").item(0);
        drug.setPharm(buildPharm(pharmElement));

        NodeList analogs = drugElement.getElementsByTagName("analog");
        for(int i = 0; i < analogs.getLength(); i++){
            drug.addAnalogue(analogs.item(i).getTextContent());
        }

        NodeList versions = drugElement.getElementsByTagName("version");
        for(int i = 0; i < versions.getLength(); i++){
            Version version = new Version();
            Element versionElement = (Element) versions.item(i);
            version.setView(MedView.valueOf(getElementTextContent(versionElement, "view").toUpperCase()));
            NodeList producers = versionElement.getElementsByTagName("producer");
            for(int j = 0; j < producers.getLength(); j++){
                Element producerElement = (Element)producers.item(j);
                version.add(buildPharm(producerElement));
            }
            drug.addVersion(version);
        }

        return drug;
    }

    private Injection buildInjection(Element injectionElement) {
        Injection injection = new Injection();
        Dosage dosage = injection.getDosage();

        injection.setName(getElementTextContent(injectionElement, "name"));
        injection.setGroup(MedGroup.valueOf(getElementTextContent(injectionElement, "group").toUpperCase()));
        injection.setInjectionType(InjectionType.valueOf(getElementTextContent(injectionElement, "injection-type").toUpperCase()));

        Element dosageElement = (Element) injectionElement.getElementsByTagName("dosage").item(0);
        dosage.setDrugDosage(Integer.parseInt(dosageElement.getAttribute("drug-dosage")));
        if(!"".equals(dosageElement.getAttribute("frequency-reception"))){
            dosage.setFrequencyReception(Integer.parseInt(dosageElement.getAttribute("frequency-reception")));
        }
        return injection;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }

    private Pharm buildPharm(Element pharmElement){
        Pharm pharm = new Pharm();
        Certificate certificate = pharm.getCertificate();
        Dosage dosage = pharm.getDosage();
        Package medPackage = pharm.getMedPackage();

        Element certificateElement = (Element) pharmElement.getElementsByTagName("certificate").item(0);
        certificate.setCertificateId(certificateElement.getAttribute("id"));
        certificate.setDateOfIssue(LocalDate.parse(getElementTextContent(certificateElement, "date-of-issue")));
        certificate.setExpirationDate(LocalDate.parse(getElementTextContent(certificateElement, "expiration-date")));
        certificate.setRegistryOrganization(getElementTextContent(certificateElement, "registry-organization"));

        Element dosageElement = (Element) pharmElement.getElementsByTagName("dosage").item(0);
        dosage.setDrugDosage(Integer.parseInt(dosageElement.getAttribute("drug-dosage")));
        if(!"".equals(dosageElement.getAttribute("frequency-reception"))){
            dosage.setFrequencyReception(Integer.parseInt(dosageElement.getAttribute("frequency-reception")));
        }

        Element packageElement = (Element) pharmElement.getElementsByTagName("package").item(0);
        medPackage.setType(PackageType.valueOf(packageElement.getAttribute("package-type").toUpperCase()));
        medPackage.setAmount(Integer.parseInt(getElementTextContent(packageElement, "amount")));
        medPackage.setPrice(Integer.parseInt(getElementTextContent(packageElement, "price")));
        return pharm;
    }
}
