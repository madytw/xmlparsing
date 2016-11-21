package edu.training.parsing.entity;

import java.time.LocalDate;

/**
 * Created by Roman on 18.11.2016.
 */
public class Certificate {
    private String certificateId;
    private LocalDate dateOfIssue;
    private LocalDate expirationDate;
    private String registryOrganization;

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRegistryOrganization() {
        return registryOrganization;
    }

    public void setRegistryOrganization(String registryOrganization) {
        this.registryOrganization = registryOrganization;
    }

    @Override
    public String toString() {
        return "Certificate:\n" +
                "\t\t\tId: " + certificateId + '\n' +
                "\t\t\tDate Of Issue: " + dateOfIssue + '\n' +
                "\t\t\tExpiration Date: " + expirationDate + '\n' +
                "\t\t\tRegistry Organization: " + registryOrganization;
    }
}
