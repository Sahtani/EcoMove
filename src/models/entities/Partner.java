package models.entities;

import config.Db;
import models.enums.PartnerStatus;
import models.enums.TransportType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Partner {

    private UUID id;
    private String companyName;
    private String commercialContact;
    private String geographicalArea;
    private String specialConditions;
    private LocalDate creationDate;
    private  TransportType transportType;
    private  PartnerStatus partnerStatus;
    private ResultSet partners = null;
    private List<Contract> contracts;
    private Connection connection;

    public Partner (UUID id,String companyName,String commercialContact,String geographicalArea,String specialConditions,LocalDate creationDate,TransportType transportType,PartnerStatus partnerStatus ){
        this.id=id;
        this.companyName=companyName;
        this.commercialContact=commercialContact;
        this.geographicalArea=geographicalArea;
        this.specialConditions=specialConditions;
        this.transportType=transportType;
        this.partnerStatus=partnerStatus;
        this.contracts=new ArrayList<>();
    }

    // constructor
    public Partner() {
            this.connection = Db.getInstance().getConnection();
    }
    //getters
    public  UUID getId() {
        return id;
    }

    public String getcompanyName() {
        return  companyName;
    }
    public  String  getcommercialContact(){
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
//   public Contract getContract(){
//
//   }
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

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    // display list of partners

    public ResultSet index() {
        String sql = "SELECT * FROM partners";
        ResultSet resultPartners = null;

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);

            resultPartners = stmt.executeQuery();

        } catch (Exception exception) {
            System.out.println("Statement Exception: " + exception.getMessage());
            exception.printStackTrace();
        }

        return resultPartners;
    }



    //methode to insert Partner
    public  void store(Partner partner) {
        String sql = "INSERT INTO partners (id, company_name, commercial_contact, transport_type, " +
                "geographical_zone, special_conditions, partner_status, creation_date) " +
                "VALUES (?, ?, ?, CAST(? AS transportType), ?, ?, CAST(? AS partnerStatus), CURRENT_DATE)";

        try  {

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1,partner.getId());
            pstmt.setString(2, partner.getcompanyName());
            pstmt.setString(3, partner.getcommercialContact());
            pstmt.setString(4, partner.getTransportType().name());
            pstmt.setString(5, partner.getGeographicalArea());
            pstmt.setString(6, partner.getSpecialConditions());
            pstmt.setString(7, partner.getPartnerStatus().name());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Partner added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding partner: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Méthode update Partner :
    public Object update(UUID id) {

        String query = "UPDATE partners SET company_name = ?, commercial_contact = ?, transport_type = ?, geographical_zone = ?, special_conditions = ?, partner_status = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, getcompanyName());
            pstmt.setString(2, getcommercialContact());
            pstmt.setObject(3, getTransportType().toString(), java.sql.Types.OTHER);
            pstmt.setString(4, getGeographicalArea());
            pstmt.setString(5, getSpecialConditions());
            pstmt.setObject(6, getPartnerStatus().toString(), java.sql.Types.OTHER);
            pstmt.setObject(7, id, java.sql.Types.OTHER);


            System.out.printf("UUID set in PreparedStatement: %s%n", id);

            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Partner updated successfully.");
            } else {
                System.out.println("No partner found with the provided id.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating partner: " + e.getMessage());
        }

        return null;
    }

    //Remove sepcific Partner :

    public String destroy(UUID id) {
        String resultMessage;
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM partners WHERE id = ?");
            pstmt.setObject(1, id, java.sql.Types.OTHER);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                resultMessage = "Partner deleted successfully.";
            } else {
                resultMessage = "No partner found with the provided UUID.";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            resultMessage = "An error occurred while trying to delete the partner.";
        }
        return resultMessage;
    }




}