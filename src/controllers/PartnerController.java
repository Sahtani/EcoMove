package controllers;

import models.Partner;
import models.PartnerStatus;
import models.TransportType;

import java.awt.*;
import java.util.Scanner;
import java.util.UUID;

import static models.Partner.store;

public class PartnerController {


    private Scanner scanner =new Scanner(System.in);

    // display all books
    public void index() {
        try {
            int choice;
            do {
//                booksList();
                System.out.printf("#   1. Add new Book                      %n");
                System.out.printf("#   2. Show a Book                       %n");
                System.out.printf("#   3. Filter Books                      %n");
                System.out.printf("#   4. Edit a Book                       %n");
                System.out.printf("#   5. Delete a Book                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> addPartner();
//                    case 2 -> showBook();
//                    case 3 -> filterBooks();
//                    case 4 -> updateBook();
//                    case 5 -> deleteBook();
                    default -> {
                        Color Colors;
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

    // add new book

    public void addPartner() {
        try {
            Partner partner = new Partner();
            System.out.printf("# > Enter Company Name: ");
            partner.setCompanyName(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Commercial Contact: ");
            partner.setCommercialContact(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Transport Type (AVION, BUS, TRAIN): ");
            String transportTypeInput = this.scanner.nextLine().strip();
            partner.setTransportType(TransportType.valueOf(transportTypeInput.toUpperCase())); // Convert input to Enum
            System.out.printf("# > Enter Geographical Zone: ");
            partner.setGeographicalArea(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Special Conditions: ");
            partner.setSpecialConditions(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Partner Status (ACTIVE, INACTIVE, SUSPENDED): ");
            String partnerStatusInput = this.scanner.nextLine().strip();
            partner.setPartnerStatus(PartnerStatus.valueOf(partnerStatusInput.toUpperCase())); // Convert input to Enum

            UUID partnerId = UUID.randomUUID();
            Partner.store(partner);


            System.out.printf("---------------------------------------------%n");
            System.out.printf("             %13s          %n");
            System.out.printf("---------------------------------------------%n");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
