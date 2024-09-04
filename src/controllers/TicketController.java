package controllers;

import models.entities.PromotionalOffer;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.UUID;

public class TicketController {

    private Scanner scanner;
    private Ticket ticket    ;


    public void indexTicket() {
        try {
            int choice;
            do {
                ticketList();
                System.out.printf("#   1. Add new Ticket                       %n");
                System.out.printf("#   2. Edit a Ticket                       %n");
                System.out.printf("#   3. Delete a Ticket                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                      case 1 -> addTicket();
                      case 2 -> updateTicket();
//                    case 3 -> deletePromotion();

                    default -> {
                        System.out.printf("---------------------------------------------%n");
                        System.out.printf("|            Please Choose a Number         |%n");
                        System.out.printf("---------------------------------------------%n");
                    }
                }
            }while(choice != 0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TicketController() {
        this.scanner = new Scanner(System.in);
        this.ticket=new Ticket();

    }

    // display all tickets :
    public void ticketList() {
        try {
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("# %-36s | %-20s | %-20s | %-20s | %-20s | %-20s | %-36s #%n",
                    "UUID id", "Transport Type", "Purchase Price", "Sale Price", "Sale Date", "Ticket Status", "Contract ID");
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            ResultSet resultTickets = ticket.displayTickets();

            while (resultTickets != null && resultTickets.next()) {
                System.out.printf("# %-36s | %-20s | %-20.2f | %-20.2f | %-20s | %-20s | %-36s #%n",
                        resultTickets.getString("id"),
                        resultTickets.getString("transporttype"),
                        resultTickets.getFloat("purchaseprice"),
                        resultTickets.getFloat("saleprice"),
                        resultTickets.getTimestamp("saledate") != null ? resultTickets.getTimestamp("saledate").toString() : "N/A",
                        resultTickets.getString("ticketstatus"),
                        resultTickets.getString("contractid"));
            }

            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // add new ticket :

    public void addTicket() {
        try {
            System.out.println("Enter ticket details:");

            System.out.print("Transport Type (AVION, BUS, TRAIN): ");
            String transportType = scanner.nextLine().strip().toUpperCase();

            System.out.print("Purchase Price: ");
            float purchasePrice = Float.parseFloat(scanner.nextLine().strip());

            System.out.print("Sale Price: ");
            float salePrice = Float.parseFloat(scanner.nextLine().strip());

            System.out.print("Sale Date (YYYY-MM-DD HH:MM:SS): ");
            String saleDate = scanner.nextLine().strip();

            System.out.print("Ticket Status ( SOLD,CANCELED,PENDING): ");
            String ticketStatus = scanner.nextLine().strip().toLowerCase();

            System.out.print("Contract ID (UUID): ");
            UUID contractId = UUID.fromString(scanner.nextLine().strip());


            ticket.setTransportType(TransportType.valueOf(transportType));
            ticket.setPurchasePrice(purchasePrice);
            ticket.setSalePrice(salePrice);
            ticket.setSaleDate(Timestamp.valueOf(saleDate));
            ticket.setTicketStatus(TicketStatus.valueOf(ticketStatus));
            ticket.setContractId(contractId);

            boolean success = ticket.store();

            if (success) {
                System.out.println("Ticket added successfully.");
            } else {
                System.out.println("Failed to add ticket.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTicket() {
        try {

            System.out.printf("# > Enter Ticket UUID: ");
            UUID id = UUID.fromString(scanner.nextLine().strip());

            System.out.printf("# > Enter Transport Type (AVION, BUS, TRAIN): ");
            String transportTypeInput = scanner.nextLine().strip();
            ticket.setTransportType(TransportType.valueOf(transportTypeInput.toUpperCase()));

            System.out.printf("# > Enter Purchase Price: ");
            float purchasePrice = Float.parseFloat(scanner.nextLine().strip());
            ticket.setPurchasePrice(purchasePrice);

            System.out.printf("# > Enter Sale Price: ");
            float salePrice = Float.parseFloat(scanner.nextLine().strip());
            ticket.setSalePrice(salePrice);

            System.out.printf("# > Enter Sale Date (YYYY-MM-DD HH:MM:SS): ");
            String saleDateInput = scanner.nextLine().strip();
            Timestamp saleDate = Timestamp.valueOf(saleDateInput);
            ticket.setSaleDate(saleDate);

            System.out.printf("# > Enter Ticket Status (sold,canceled,pending): ");
            String ticketStatusInput = scanner.nextLine().strip();
            ticket.setTicketStatus(TicketStatus.valueOf(ticketStatusInput.toLowerCase()));

            System.out.printf("# > Enter Contract UUID: ");
            UUID contractId = UUID.fromString(scanner.nextLine().strip());
            ticket.setContractId(contractId);

              boolean isUpdated = ticket.updateTicket(id);

            if (isUpdated) {
                System.out.printf("---------------------------------------------%n");
                System.out.printf(" Ticket updated successfully.%n");
                System.out.printf("---------------------------------------------%n");
            } else {
                System.out.printf("No ticket was updated. Please check the UUID and try again.%n");
            }

        } catch (Exception e) {
            System.out.printf("An error occurred while updating the ticket: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }


}
