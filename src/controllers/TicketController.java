package controllers;

import models.entities.PromotionalOffer;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                //promotionsList();
                System.out.printf("#   1. Add new Ticket                       %n");
                System.out.printf("#   2. Edit a Ticket                       %n");
                System.out.printf("#   3. Delete a Ticket                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                      case 1 -> addTicket();
//                    case 2 -> updatePromo();
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


    // add new ticket

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

}
