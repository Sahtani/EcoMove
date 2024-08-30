package models;

import config.Db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;
import java.sql.PreparedStatement;
import models.TransportType;
import  models.PartnerStatus;


public class Partner {

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

            //create transport type :
            String sql = "CREATE TYPE transportType AS ENUM ('AVION', 'BUS', 'TRAIN');";
            statement.executeUpdate(sql);

            // Create partner status
            String requete = "CREATE TYPE partnerStatus AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED');";

            statement.executeUpdate(requete);

            System.out.println("ENUM types created or already exist.");

        }catch (Exception e){
           e.printStackTrace();
        }
    }

    //methode to create partners table

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS partners (" +
                "id UUID PRIMARY KEY, " +
                "company_name VARCHAR(255) NOT NULL, " +
                "commercial_contact VARCHAR(255) NOT NULL, " +
                "transport_type transportType NOT NULL, " +
                "geographical_zone VARCHAR(255) NOT NULL, " +
                "special_conditions TEXT, " +
                "partner_status partnerStatus NOT NULL, " +
                "creation_date DATE NOT NULL" +
                ");";

        try (Connection connection = Db.getInstance("EcoMove", "postgres", "soumia").getConnection();
             Statement stmt = connection.createStatement()) {


            stmt.executeUpdate(sql);

            System.out.println("Table 'partners' created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //methode to insert Partner
    public static void addPartner(UUID id, String companyName, String commercialContact,
                                  TransportType transportType, String geographicalZone,
                                  String specialConditions, PartnerStatus partnerStatus) {
        String sql = "INSERT INTO partners (id, company_name, commercial_contact, transport_type, " +
                "geographical_zone, special_conditions, partner_status, creation_date) " +
                "VALUES (?, ?, ?, CAST(? AS transportType), ?, ?, CAST(? AS partnerStatus), CURRENT_DATE)";

        try (Connection connection = Db.getInstance("EcoMove", "postgres", "soumia").getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set the values for the placeholders
            pstmt.setObject(1, id);
            pstmt.setString(2, companyName);
            pstmt.setString(3, commercialContact);
            pstmt.setString(4, transportType.name()); // Enum's name method returns the string representation
            pstmt.setString(5, geographicalZone);
            pstmt.setString(6, specialConditions);
            pstmt.setString(7, partnerStatus.name());

            // Execute the SQL to insert the data
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Partner added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding partner: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
