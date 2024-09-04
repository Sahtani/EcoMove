package controllers;

import config.Db;
import models.entities.Contract;
import models.entities.Partner;
import models.enums.ContractStatus;
import models.enums.PartnerStatus;

import java.sql.*;
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
                contractList();
                System.out.printf("#   1. Add new Contract                       %n");
                System.out.printf("#   2. Edit a Contract                       %n");
                System.out.printf("#   3. Delete a Contract                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                    case 1 -> addContract();
                    case 2 -> updateContract();
                    case 3 -> deleteContract();

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

    // display ALL contracts
    public void contractList() {
        try {

            System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("# %-36s | %-36s | %-10s | %-10s | %-12s | %-30s | %-10s | %-18s #%n",
                    "Contract ID", "Partner ID", "Start Date", "End Date", "Special Rate", "Agreement Conditions", "Renewable", "Status");
            System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");

            ResultSet resultContracts = contract.diplayContracts();

            while (resultContracts.next()) {
                System.out.printf("# %-36s | %-36s | %-10s | %-10s | %-12.2f | %-30s | %-10b | %-18s #%n",
                        resultContracts.getString("id"),
                        resultContracts.getString("partner_id"),
                        resultContracts.getDate("start_date").toLocalDate(),  // Conversion de java.sql.Date à java.time.LocalDate
                        resultContracts.getDate("end_date") != null ? resultContracts.getDate("end_date").toLocalDate() : "N/A",  // Gestion des dates nulles
                        resultContracts.getFloat("special_rate"),
                        resultContracts.getString("agreement_conditions"),
                        resultContracts.getBoolean("renewable"),
                        resultContracts.getString("contract_status"));
            }

            System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addContract() {
        try {
            Scanner scanner = new Scanner(System.in);
            Contract contract = new Contract();

            System.out.print("Enter partner ID (UUID): ");
            UUID partnerId = UUID.fromString(scanner.nextLine());
            contract.setPartnerId(partnerId);

            System.out.printf("# > Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().strip());
            contract.setStartDate(startDate);

            System.out.printf("# > Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = scanner.nextLine().strip();
            LocalDate endDate = endDateInput.isEmpty() ? null : LocalDate.parse(endDateInput);
            contract.setEndDate(endDate);

            System.out.printf("# > Enter Special Rate: ");
            float specialRate = Float.parseFloat(scanner.nextLine().strip());
            contract.setSpecialRate(specialRate);

            System.out.printf("# > Enter Agreement Conditions: ");
            String agreementConditions = scanner.nextLine().strip();
            contract.setAgreementConditions(agreementConditions);

            System.out.printf("# > Is Contract Renewable? (true/false): ");
            boolean renewable = Boolean.parseBoolean(scanner.nextLine().strip());
            contract.setRenewable(renewable);

            System.out.print("# > Enter Contract Status (ongoing, completed, suspended): ");
            String contractStatus = this.scanner.nextLine().strip();
            contract.setContractStatus(ContractStatus.valueOf(contractStatus));


            UUID contractId = UUID.randomUUID();
            contract.setId(contractId);

            contract.store(contract);

            System.out.println("Contract created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update contrat
    public void updateContract() {
        try {

            System.out.printf("# > Enter Contract UUID: ");
            UUID id = UUID.fromString(this.scanner.nextLine().strip());
            System.out.printf("Contract ID: %s%n", id);

            contract.setId(id);


            System.out.printf("# > Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(this.scanner.nextLine().strip());
            contract.setStartDate(startDate);

            System.out.printf("# > Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = this.scanner.nextLine().strip();
            LocalDate endDate = endDateInput.isEmpty() ? null : LocalDate.parse(endDateInput);
            contract.setEndDate(endDate);

            System.out.printf("# > Enter Special Rate: ");
            float specialRate = Float.parseFloat(this.scanner.nextLine().strip());
            contract.setSpecialRate(specialRate);

            System.out.printf("# > Enter Agreement Conditions: ");
            String agreementConditions = this.scanner.nextLine().strip();
            contract.setAgreementConditions(agreementConditions);

            System.out.printf("# > Is Contract Renewable? (true/false): ");
            boolean renewable = Boolean.parseBoolean(this.scanner.nextLine().strip());
            contract.setRenewable(renewable);

            System.out.printf("# > Enter Contract Status (ongoing, completed, suspended): ");
            ContractStatus contractStatus;
            try {
                contractStatus = ContractStatus.valueOf(this.scanner.nextLine().strip().toLowerCase());
                contract.setContractStatus(contractStatus);
            } catch (IllegalArgumentException e) {
                System.out.printf("Invalid contract status provided. Using default (ongoing).%n");
                contractStatus = ContractStatus.ongoing;
                contract.setContractStatus(contractStatus);
            }

            Object updateResult = contract.update(id);
            if (updateResult != null) {
                System.out.printf("---------------------------------------------%n");
                System.out.printf("Update successful: %s%n", updateResult.toString());
                System.out.printf("---------------------------------------------%n");
            } else {
                System.out.printf("No contract was updated. Please check the UUID and try again.%n");
            }

        } catch (IllegalArgumentException e) {
            System.out.printf("Invalid input provided: %s. Please enter valid values.%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("An error occurred while updating the contract: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    // delete a contract :
    public void deleteContract() {
        try {
            System.out.printf("# > Enter Partner id: ");
            UUID id = UUID.fromString(this.scanner.nextLine().strip());
            System.out.printf("---------------------------------------------%n");
            System.out.printf("             %13s          %n", contract.destroy(id));
            System.out.printf("---------------------------------------------%n");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }




}
