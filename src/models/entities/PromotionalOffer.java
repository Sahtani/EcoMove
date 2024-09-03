package models.entities;

import config.Db;
import models.enums.TicketStatus;
import models.enums.OfferStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class PromotionalOffer {




    private UUID id;
    private String offerName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private DiscountType discountType;
    private String conditions;
    private OfferStatus offerStatus;
    private UUID contractId;
    private Connection connection;

    // Constructor
    public Promotion(UUID id, String offerName, String description, LocalDate startDate, LocalDate endDate,
                     DiscountType discountType, String conditions, OfferStatus offerStatus, UUID contractId) {
        this.id = id;
        this.offerName = offerName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountType = discountType;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
        this.contractId = contractId;
        this.connection = Db.getInstance().getConnection(); // Assuming Db is a singleton managing the connection
    }

    // Default constructor
    public Promotion() {
        this.connection = Db.getInstance().getConnection();
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }

    // Method to store a Promotion
    public void store() {
        String sql = "INSERT INTO promotions (id, offer_name, description, start_date, end_date, discount_type, " +
                "conditions, offer_status, contract_id) VALUES (?, ?, ?, ?, ?, CAST(? AS discountType), ?, CAST(? AS offerStatus), ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1, id);
            pstmt.setString(2, offerName);
            pstmt.setString(3, description);
            pstmt.setObject(4, startDate);
            pstmt.setObject(5, endDate);
            pstmt.setString(6, discountType.name());
            pstmt.setString(7, conditions);
            pstmt.setString(8, offerStatus.name());
            pstmt.setObject(9, contractId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Promotion added successfully.");
            } else {
                System.out.println("Failed to add promotion.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding promotion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}