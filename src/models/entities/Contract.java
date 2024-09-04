package models.entities;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.UUID;

import config.Db;
import models.enums.ContractStatus;


import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class Contract {


    private UUID id;
    private UUID partnerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private float specialRate;
    private String agreementConditions;
    private boolean renewable;
    private ContractStatus contractStatus;
    private Connection connection;

    public Contract(UUID id, UUID partnerId, LocalDate startDate, LocalDate endDate, float specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus){
        this.id=id;
        this.partnerId=partnerId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.specialRate=specialRate;
        this.agreementConditions=agreementConditions;
        this.renewable=renewable;
        this.contractStatus=contractStatus;
    }
    // constructor
    public Contract() {
        this.connection = Db.getInstance().getConnection();
    }




    //Getters
    public UUID getId() {
        return id;
    }

    public UUID getPartnerId(){return  partnerId;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate(LocalDate endDate) {
        return this.endDate;
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

    public void setPartnerId(UUID partnerId){
        this.partnerId=partnerId;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
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



    // display list of contracts

    public ResultSet diplayContracts() {
        String sql = "SELECT * FROM contracts";
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


    public void store(Contract contract) {
        String sql = "INSERT INTO contracts (id, partner_id, start_date, end_date, special_rate, agreement_conditions, renewable, contract_status) VALUES (?, ?, ?, ?, ?, ?, ?,CAST(? AS statusContract))";

        try {
             PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1, this.getId());
            pstmt.setObject(2, this.getPartnerId());
            pstmt.setObject(3, this.getStartDate());
            if (this.getEndDate(endDate) != null) {
                pstmt.setObject(4, this.getEndDate(endDate));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setFloat(5, this.getSpecialRate());
            pstmt.setString(6, this.getAgreementConditions());
            pstmt.setBoolean(7, this.isRenewable());

            ContractStatus status = this.getContractStatus();
            if (status != null) {
                System.out.printf("hi");
                pstmt.setString(8, status.name());
            } else {
                pstmt.setNull(8, java.sql.Types.VARCHAR);
            }


            pstmt.executeUpdate();
            System.out.println("Contract stored successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update contract
    public Object update(UUID id) {

        System.out.printf("UUID provided: %s%n", id);

        String query = "UPDATE contracts SET start_date = ?, end_date = ?, special_rate = ?, agreement_conditions = ?, renewable = ?, contract_status = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

//            pstmt.setObject(1, getPartnerId(), java.sql.Types.OTHER);
           pstmt.setObject(1, getStartDate(), java.sql.Types.DATE);
            pstmt.setDate(2, java.sql.Date.valueOf(getEndDate(endDate)));
            pstmt.setFloat(3, getSpecialRate());
            pstmt.setString(4, getAgreementConditions());
            pstmt.setBoolean(5, isRenewable());
            pstmt.setObject(6, getContractStatus().toString(), java.sql.Types.OTHER);
            pstmt.setObject(7, id, java.sql.Types.OTHER);

            System.out.printf("UUID set in PreparedStatement: %s%n", id);


            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Contract updated successfully.");
            } else {
                System.out.println("No contract found with the provided id.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating contract: " + e.getMessage());
        }

        return null;
    }

    //Remove sepcific Contract :

    public String destroy(UUID id) {
        String resultMessage;
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM contracts WHERE id = ?");
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
