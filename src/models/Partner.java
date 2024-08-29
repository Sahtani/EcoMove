package models;

import java.time.LocalDate;
import java.util.UUID;

public class Partner {

    public enum TransportType {
        plane, train, bus
    }

    public enum PartnerStatus{
        active, inactive, suspended
    }

    private UUID id;
    private String companyName;
    private String commercialContact;
    private String geographicalArea;
    private String specialConditions;
    private LocalDate creationDate;
    private  TransportType transportType;
    private  PartnerStatus partnerStatus;


    // Constructor
    public Partner (UUID id,String companyName,String commercialContact,String geographicalArea,String specialConditions,LocalDate creationDate,TransportType transportType,PartnerStatus partnerStatus ){
        this.id=id;
        this.companyName=companyName;
        this.commercialContact=commercialContact;
        this.geographicalArea=geographicalArea;
        this.specialConditions=specialConditions;
        this.transportType=transportType;
        this.partnerStatus=partnerStatus;
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

    public PartnerStatus getPartnerStatus() {
        return partnerStatus;
    }

    public TransportType getTransportType() {
        return transportType;
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

    public void setPartnerStatus(PartnerStatus partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }
}
