package controllers;

import models.entities.Contract;
import models.enums.ContractStatus;

import java.sql.Date;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.UUID;


public class ContractController {
    private Scanner scanner =new Scanner(System.in);

    public void createContract() {
        try {
            System.out.printf("# > Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().strip());

            System.out.printf("# > Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = scanner.nextLine().strip();
            Date endDate = endDateInput.isEmpty() ? null : Date.valueOf(LocalDate.parse(endDateInput));

            System.out.printf("# > Enter Special Rate: ");
            float specialRate = Float.parseFloat(scanner.nextLine().strip());

            System.out.printf("# > Enter Agreement Conditions: ");
            String agreementConditions = scanner.nextLine().strip();

            System.out.printf("# > Is Contract Renewable? (true/false): ");
            boolean renewable = Boolean.parseBoolean(scanner.nextLine().strip());

            System.out.printf("# > Enter Contract Status (ongoing, completed, suspended;): ");
            ContractStatus contractStatus = ContractStatus.valueOf(scanner.nextLine().strip().toUpperCase());

            UUID contractId = UUID.randomUUID();

            Contract contract = new Contract(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus);
            contract.store();

            System.out.println("Contract created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating contract: " + e.getMessage());
        }
    }

}
