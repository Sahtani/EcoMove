package models.entities;

import config.Db;
import models.enums.DiscountType;
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
    public PromotionalOffer(UUID id, String offerName, String description, LocalDate startDate, LocalDate endDate,
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
    public PromotionalOffer() {
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


    // display promotions :
    public ResultSet displayromotions() {
        String sql = "SELECT * FROM promotions";
        ResultSet resultPromotions = null;

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);

            resultPromotions = stmt.executeQuery();

        } catch (Exception exception) {
            System.out.println("Statement Exception: " + exception.getMessage());
            exception.printStackTrace();
        }

        return resultPromotions;
    }

    // Method to store a Promotion
    public void store() {
        String sql = "INSERT INTO promotions (id, offername, description, startdate, enddate, discounttype, " +
                "conditions, offerstatus, contractid) VALUES (?, ?, ?, ?, ?, CAST(? AS discountType), ?, CAST(? AS offerStatus), ?)";

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
                System.out.println("Promotion test successfully.");
            } else {
                System.out.println("Failed to add promotion.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding promotion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean updatePromo(UUID id) {
        String query = "UPDATE promotions SET offername = ?, description = ?, startdate = ?, enddate = ?, discounttype = ?, conditions = ?, offerstatus = ?, contractid = ? WHERE id = ?";

        try (Connection conn = Db.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, getOfferName());
            pstmt.setString(2, getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(getEndDate()));

            pstmt.setObject(5, getDiscountType().toString(), java.sql.Types.OTHER); // Assuming `getDiscountType()` returns an enum

            pstmt.setString(6, getConditions());

            pstmt.setObject(7, getOfferStatus().toString(), java.sql.Types.OTHER); // Assuming `getOfferStatus()` returns an enum

            pstmt.setObject(8, getContractId(), java.sql.Types.OTHER);
            pstmt.setObject(9, id, java.sql.Types.OTHER);

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        // delete Promotion
        public String destroy(UUID id) {
            String resultMessage;
            try {
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM promotions WHERE id = ?");
                pstmt.setObject(1, id, java.sql.Types.OTHER);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    resultMessage = "Promotion deleted successfully.";
                } else {
                    resultMessage = "No promotion found with the provided UUID.";
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                resultMessage = "An error occurred while trying to delete the promotion.";
            }
            return resultMessage;
        }

    }