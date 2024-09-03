package controllers;

import models.entities.Partner;
import models.enums.PartnerStatus;
import models.enums.TransportType;

import java.awt.*;
import java.awt.dnd.DropTargetEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;



public class PartnerController {

    private Partner partner = new Partner();
    private Scanner scanner =new Scanner(System.in);

    // Principal menu :
    public void indexPartner() {
        try {
            int choice;
            do {
                partnersList();
                System.out.printf("#   1. Add new Partner                       %n");
                System.out.printf("#   2. Edit a Partner                       %n");
                System.out.printf("#   3. Delete a Partner                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                    case 1 -> addPartner();
                    case 2 -> updatePartner();
                    case 3 -> deletePartner();

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

    //Parnter list :

    public void partnersList() {
        try {
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("# %-20s | %-20s | %-20s | %-20s | %-20s | %-20s #%n",
                    "UUID id", "Company Name", "Commercial Contact", "Geographical Area", "Transport Type", "Status");
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
                Partner book = new Partner();
            ResultSet resultPartners = partner.index();

            while (resultPartners.next()) {
                System.out.printf("# %-20s | %-20s | %-20s | %-20s | %-20s | %-20s #%n",
                        resultPartners.getString("id"),
                        resultPartners.getString("company_name"),
                        resultPartners.getString("commercial_contact"),
                        resultPartners.getString("geographical_zone"),
                        resultPartners.getString("transport_type"),
                        resultPartners.getString("partner_status"));
            }

            System.out.printf("---------------------------------------------------------------------------------------------%n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // add new book

      public void addPartner() {

         try {
            System.out.printf("# > Enter Company Name: ");
            partner.setCompanyName(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Commercial Contact: ");
            partner.setCommercialContact(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Transport Type (AVION, BUS, TRAIN): ");
            String transportTypeInput = this.scanner.nextLine().strip();
            partner.setTransportType(TransportType.valueOf(transportTypeInput.toUpperCase()));
            System.out.printf("# > Enter Geographical Zone: ");
            partner.setGeographicalArea(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Special Conditions: ");
            partner.setSpecialConditions(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Partner Status (ACTIVE, INACTIVE, SUSPENDED): ");
            String partnerStatusInput = this.scanner.nextLine().strip();
            partner.setPartnerStatus(PartnerStatus.valueOf(partnerStatusInput));

            UUID partnerId = UUID.randomUUID();
            partner.setId(partnerId);
            partner.store(partner);


            System.out.printf("---------------------------------------------%n");
            System.out.printf("             %13s          %n");
            System.out.printf("---------------------------------------------%n");

        }catch (Exception e) {
            e.printStackTrace();
    }
}

    //methode to update Partner

    public void updatePartner() {
        try {
            System.out.printf("# > Enter Partner UUID: ");
            UUID id = UUID.fromString(this.scanner.nextLine().strip());
            System.out.printf(String.valueOf(id));
            System.out.printf("# > Enter Company Name: ");
            partner.setCompanyName(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Transport Type (AVION, BUS, TRAIN): ");
            String transportTypeInput = this.scanner.nextLine().strip();
            partner.setTransportType(TransportType.valueOf(transportTypeInput.toUpperCase()));
            System.out.printf("# > Enter Commercial Contact: ");
            partner.setCommercialContact(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Geographical Zone: ");
            partner.setGeographicalArea(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Special Conditions: ");
            partner.setSpecialConditions(this.scanner.nextLine().strip());
            System.out.printf("# > Enter Partner Status (ACTIVE, INACTIVE, SUSPENDED): ");
            String partnerStatusInput = this.scanner.nextLine().strip();
            partner.setPartnerStatus(PartnerStatus.valueOf(partnerStatusInput));
            Object updateResult = partner.update(id);
            if (updateResult != null) {
                System.out.println(updateResult);  // Directly prints the object
                System.out.printf("---------------------------------------------%n");
                System.out.printf(" Update successful: %s%n", updateResult.toString());
                System.out.printf("---------------------------------------------%n");
            } else {
                System.out.printf("No partner was updated. Please check the UUID and try again.%n");
            }

        } catch (IllegalArgumentException e) {
            System.out.printf("Invalid input provided: %s. Please enter valid values.%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("An error occurred while updating the partner: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    // delete a partner
    public void deletePartner() {
        try {
            System.out.printf("# > Enter Partner id: ");
            UUID id = UUID.fromString(this.scanner.nextLine().strip());
            System.out.printf("---------------------------------------------%n");
            System.out.printf("             %13s          %n", partner.destroy(id));
            System.out.printf("---------------------------------------------%n");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }





}

