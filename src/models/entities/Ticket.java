package models.entities;

import models.enums.TicketStatus;

import java.sql.Date;
import java.util.UUID;
import models.enums.TransportType;


public class Ticket {


    private UUID id;
    private TransportType transportType;
    private float purchasePrice;
    private float salePrice;
    private Date saleDate;
    private TicketStatus ticketStatus;

    // Constructor
    public Ticket(UUID id, TransportType transportType, float purchasePrice, float salePrice, Date saleDate, TicketStatus ticketStatus) {
        this.id = id;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
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

    public Date getSaleDate() {
        return saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
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

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }



}
