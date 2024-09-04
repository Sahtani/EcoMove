package models.entities;

import config.Db;
import models.enums.TicketStatus;

import java.sql.*;
import java.util.UUID;

import models.enums.TransportType;


public class Ticket {


    private UUID id;
    private TransportType transportType;
    private float purchasePrice;
    private float salePrice;
    private Timestamp saleDate;
    private TicketStatus ticketStatus;
    private UUID contractId;
    private Connection connection;


    // Constructor
    public Ticket(UUID id, TransportType transportType, float purchasePrice, float salePrice, Timestamp saleDate, TicketStatus ticketStatus) {
        this.id = id;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
    }

    // constructor
    public Ticket() {
        this.connection = Db.getInstance().getConnection();
    }


    //Getters

    public UUID getId() {
        return id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public UUID getContractId(){
        return  contractId;
    }
    //Setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setContractId(UUID contractId){
        this.contractId=contractId;
    }

    // display tickets :
    public ResultSet displayTickets() {
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


    //add Ticket :

    public boolean store () {
        String query = "INSERT INTO ticket (id, transporttype, purchaseprice, saleprice, saledate, ticketstatus, contractid) VALUES (?,CAST(? AS transportType), ?, ?, ?, CAST(? AS ticketStatus), ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            UUID ticketId = UUID.randomUUID();
            pstmt.setObject(1, ticketId, java.sql.Types.OTHER);


            pstmt.setString(2, this.getTransportType().toString());
            pstmt.setFloat(3, this.getPurchasePrice());
            pstmt.setFloat(4, this.getSalePrice());
            pstmt.setTimestamp(5, this.getSaleDate());
            pstmt.setString(6, this.getTicketStatus().name());


            pstmt.setObject(7, this.getContractId(), java.sql.Types.OTHER);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
