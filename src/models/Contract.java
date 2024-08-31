package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import config.Db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;

public class Contract {


    private UUID id;
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

    //Getters
    public UUID getId() {
        return id;
    }

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

    public ContractStatus getContractStatus(){
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

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public void store() {
        String sql = "INSERT INTO contracts (id, start_date, end_date, special_rate, agreement_conditions, renewable, contract_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Db.getInstance("EcoMove", "postgres", "soumia").getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setObject(1, getId()); // UUID
            pstmt.setObject(2, getStartDate()); // LocalDate
            pstmt.setObject(3, getEndDate()); // Date
            pstmt.setFloat(4, getSpecialRate()); // float
            pstmt.setString(5, getAgreementConditions()); // String
            pstmt.setBoolean(6, isRenewable()); // boolean
            pstmt.setString(7, getContractStatus().name()); // Enum's name method returns the string representation

            pstmt.executeUpdate();
            System.out.println("Contract stored successfully.");

        } catch (SQLException e) {
            System.out.println("Error storing contract: " + e.getMessage());
        }
    }


}
