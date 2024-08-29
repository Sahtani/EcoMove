package models;

import java.time.LocalDate;
import java.util.UUID;

public class Partner {
    private UUID id;
    private String companyName;
    private String commercialContact;
    private String geographicalArea;
    private String specialConditions;
    private LocalDate creationDate;

    public enum TransportType {
        plane, train, bus
    }
    public enum partnerStatus{
        active, inactive, suspended
    }


    // Constructor
    public Partner (UUID id,String companyName,String commercialContact,String geographicalArea,String specialConditions,LocalDate creationDate){
        this.id=id;
        this.companyName=companyName;
        this.commercialContact=commercialContact;
        this.geographicalArea=geographicalArea;
        this.specialConditions=specialConditions;
    }
    //getters
    public UUID getId() {
        return id;
    }

    public String getcompanyName() {
        return  companyName;
    }
    public String  getcommercialContact(){
        return commercialContact;
    }

    public String getGeographicalArea(){
        return geographicalArea;
    }

    public String getSpecialConditions(){
        return specialConditions;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    //Setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public void setGeographicalArea(String geographicalArea){
        this.geographicalArea=geographicalArea;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }











}
