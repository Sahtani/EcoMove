package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Contract {

    public enum ContractStatus {
        ACTIVE, INACTIVE, TERMINATED, PENDING
    }

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



}
