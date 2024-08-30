package models;

import config.Db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    //methode to add partner in the database

//    public boolean addPartner(){
//
//    }

    //methode to create enums :
    public static void createEnums(){

        try(Connection connection=Db.getInstance("EcoMove","postgres","soumia").getConnection();
        Statement statement=connection.createStatement()){

            //create transport_type_enum :
            String createTransportEnumSQL = "CREATE TYPE transport_type_enum AS ENUM ('AVION', 'BUS', 'TRAIN');";
            statement.executeUpdate(createTransportEnumSQL);

            // Create partner_status_enum
            String createStatusEnumSQL = "DO $$ BEGIN " +
                    "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'partner_status_enum') THEN " +
                    "        CREATE TYPE partner_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED'); " +
                    "    END IF; " +
                    "END $$;";
            statement.executeUpdate(createStatusEnumSQL);

            System.out.println("ENUM types created or already exist.");

        }catch (Exception e){
           e.printStackTrace();
        }
    }

    //methode to create partners table

//    public static void createTable() {
//        String sql = "CREATE TABLE IF NOT EXISTS partners (" +
//                "id UUID PRIMARY KEY DEFAULT gen_random_uuid(), " +
//                "company_name VARCHAR(255) NOT NULL, " +
//                "commercial_contact VARCHAR(255) NOT NULL, " +
//                "transport_type VARCHAR(50) NOT NULL, " +
//                "geographic_zone VARCHAR(100) NOT NULL, " +
//                "special_conditions TEXT, " +
//                "partner_status VARCHAR(50) NOT NULL CHECK (partner_status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')), " +
//                "creation_date DATE NOT NULL DEFAULT CURRENT_DATE" +
//                ")";
//
//        try (Connection connection = Db.getInstance("EcoMove","postgres","soumia").getConnection();
//             Statement statement = connection.createStatement()) {
//
//            statement.executeUpdate(sql);
//            System.out.println("Table 'partners' created or already exists.");
//
//        } catch (SQLException e) {
//            System.out.println("Error creating table: " + e.getMessage());
//        }
//    }

}
