package models.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import config.Db;
import models.enums.ContractStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.concurrent.LinkedBlockingDeque;

public class Contract {


    private UUID id;
    private UUID partnerId;
    private LocalDate startDate;
    private Date endDate;
    private float specialRate;
    private String agreementConditions;
    private boolean renewable;
    private ContractStatus contractStatus;

    public Contract(UUID id,LocalDate startDate,Date endDate,float specialRate,String agreementConditions,boolean renewable,ContractStatus contractStatus){
        this.id=id;
        this.startDate=startDate;
        this.endDate=endDate;
        this.specialRate=specialRate;
        this.agreementConditions=agreementConditions;
        this.renewable=renewable;
        this.contractStatus=contractStatus;
    }

    // constructor
    public Contract (){

    }

    //Getters
    public UUID getId() {
        return id;
    }

    public UUID getPartnerId(){return  partnerId;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getSpecialRate() {
        return specialRate;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    //Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setSpecialRate(float specialRate) {
        this.specialRate = specialRate;
    }

    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public String setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
        return null ;
    }

//    public index(){
//
//    }


    public void store() {
        String sql = "INSERT INTO contracts (id, partner_id, start_date, end_date, special_rate, agreement_conditions, renewable, contract_status) VALUES (?, ?, ?, ?, ?, ?, ?, CAST(? AS contractStatus))";

        try (Connection connection = Db.getInstance("EcoMove", "postgres", "soumia").getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Définir les paramètres de la requête
            pstmt.setObject(1, this.getId()); // UUID
            pstmt.setObject(2, this.getPartnerId()); // UUID
            pstmt.setObject(3, this.getStartDate()); // LocalDate
            if (this.getEndDate() != null) {
                pstmt.setObject(4, this.getEndDate()); // LocalDate
            } else {
                pstmt.setNull(4, java.sql.Types.DATE); // End date can be null
            }
            pstmt.setFloat(5, this.getSpecialRate()); // Float
            pstmt.setString(6, this.getAgreementConditions()); // String
            pstmt.setBoolean(7, this.isRenewable()); // Boolean

            ContractStatus status = this.getContractStatus();
            if (status != null) {
                System.out.printf("hi");
                pstmt.setString(8, status.name()); // Enum as String
            } else {
                pstmt.setNull(8, java.sql.Types.VARCHAR); // Contract status can be null
            }

            // Exécuter la requête
            pstmt.executeUpdate();
            System.out.println("Contract stored successfully.");

        } catch (SQLException e) {
            System.out.println("Error storing contract: " + e.getMessage());
        }
    }



}
