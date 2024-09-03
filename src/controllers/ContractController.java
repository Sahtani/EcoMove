package controllers;

import models.entities.Contract;
import models.entities.Partner;
import models.enums.ContractStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.UUID;


public class ContractController {
    private Scanner scanner =new Scanner(System.in);
    private Contract contract=new Contract();

    public void indexContract() {
        try {
            int choice;
            do {

                System.out.printf("#   1. Add new Contract                       %n");
                System.out.printf("#   2. Edit a Contract                       %n");
                System.out.printf("#   3. Delete a Contract                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                    case 1 -> addContract();
//                    case 2 -> updatePartner();
//                    case 3 -> deletePartner();

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

    //display ALL contracts
//    public void contractList() {
//        try {
//            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
//            System.out.printf("# %-20s | %-20s | %-20s | %-20s | %-20s | %-20s #%n",
//                    "UUID id", "Company Name", "Commercial Contact", "Geographical Area", "Transport Type", "Status");
//            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
//            Partner book = new Partner();
//            ResultSet resultPartners = contract.index();
//
//            while (resultPartners.next()) {
//                System.out.printf("# %-20s | %-20s | %-20s | %-20s | %-20s | %-20s #%n",
//                        resultPartners.getString("id"),
//                        resultPartners.getString("company_name"),
//                        resultPartners.getString("commercial_contact"),
//                        resultPartners.getString("geographical_zone"),
//                        resultPartners.getString("transport_type"),
//                        resultPartners.getString("partner_status"));
//            }
//
//            System.out.printf("---------------------------------------------------------------------------------------------%n");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addContract() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter partner ID (UUID): ");
            UUID partnerId = UUID.fromString(scanner.nextLine());

            System.out.printf("# > Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().strip());

            System.out.printf("# > Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = scanner.nextLine().strip();
            LocalDate endDate = endDateInput.isEmpty() ? null : LocalDate.parse(endDateInput);

            // Input special rate
            System.out.printf("# > Enter Special Rate: ");
            float specialRate = Float.parseFloat(scanner.nextLine().strip());

            // Input agreement conditions
            System.out.printf("# > Enter Agreement Conditions: ");
            String agreementConditions = scanner.nextLine().strip();

            // Input renewable status
            System.out.printf("# > Is Contract Renewable? (true/false): ");
            boolean renewable = Boolean.parseBoolean(scanner.nextLine().strip());

            // Input contract status
            System.out.print("# > Enter Contract Status (ongoing, completed, suspended): ");
            ContractStatus contractStatus;
            try {
                contractStatus = ContractStatus.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid contract status. Using default (ONGOING).");
                contractStatus = ContractStatus.ONGOING;
            }

            // Create a new Contract object
            UUID contractId = UUID.randomUUID();
            Contract contract = new Contract();

            // Store the contract
            contract.store();

            System.out.println("Contract created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
