package controllers;

import java.sql.ResultSet;
import java.util.Scanner;
import java.util.UUID;
import java.time.LocalDate;
import models.entities.PromotionalOffer;
import models.enums.ContractStatus;
import models.enums.DiscountType;
import models.enums.OfferStatus;

public class PromotionController {

    private Scanner scanner;
    private PromotionalOffer promo ;


    public void indexPromotion() {
        try {
            int choice;
            do {
                promotionsList();
                System.out.printf("#   1. Add new Partner                       %n");
                System.out.printf("#   2. Edit a Partner                       %n");
                System.out.printf("#   3. Delete a Partner                     %n");
                System.out.printf("#   0. Main Menu                         %n");
                System.out.printf("# > Enter a number: ");
                choice = this.scanner.nextInt();
                scanner.nextLine();
                switch (choice) {

                    case 1 -> addPromotion();
                    case 2 -> updatePromo();
                    case 3 -> deletePromotion();

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

    public PromotionController() {
        this.scanner = new Scanner(System.in);
        this.promo=new PromotionalOffer();

    }
    // display all promotions :
    public void promotionsList() {
        try {
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("# %-36s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-36s | %-20s #%n",
                    "UUID id", "Offer Name", "Description", "Start Date", "End Date", "Discount Type", "Conditions", "Contract ID", "Offer Status");
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");

            ResultSet resultPromotions = promo.displayromotions();  // Assume that `promotion.index()` returns the ResultSet containing promotion data.

            while (resultPromotions.next()) {
                System.out.printf("# %-36s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-36s | %-20s #%n",
                        resultPromotions.getString("id"),
                        resultPromotions.getString("offername"),
                        resultPromotions.getString("description"),
                        resultPromotions.getDate("startdate").toString(),
                        resultPromotions.getDate("enddate") != null ? resultPromotions.getDate("enddate").toString() : "Indefinite",
                        resultPromotions.getString("discounttype"),
                        resultPromotions.getString("conditions"),
                        resultPromotions.getString("contractid"),
                        resultPromotions.getString("offerstatus"));
            }

            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------%n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // add promotion :
    public void addPromotion() {
        try {

            System.out.print("Enter Offer Name: ");
            String offerName = scanner.nextLine().strip();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().strip();

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().strip());

            System.out.print("Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = scanner.nextLine().strip();
            LocalDate endDate = endDateInput.isEmpty() ? null : LocalDate.parse(endDateInput);

            System.out.print("Enter Discount Type (percentage , fixedPrice): ");
            DiscountType discountType = DiscountType.valueOf(scanner.nextLine().strip());

            System.out.print("Enter Conditions: ");
            String conditions = scanner.nextLine().strip();

            System.out.print("Enter Offer Status (ACTIVE, INACTIVE, EXPIRED): ");
            OfferStatus offerStatus = OfferStatus.valueOf(scanner.nextLine().strip().toLowerCase());

            System.out.print("Enter Contract ID (UUID): ");
            UUID contractId = UUID.fromString(scanner.nextLine().strip());

            UUID promoId = UUID.randomUUID();
            promo.setId(promoId);

            PromotionalOffer promotion = new PromotionalOffer(promoId, offerName, description, startDate, endDate,
                    discountType, conditions, offerStatus, contractId);


            promotion.store();

            System.out.println("Promotion added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while adding the promotion.");
        }
    }

    public void updatePromo() {
        try {

            System.out.print("Enter Promo UUID: ");
            UUID id = UUID.fromString(scanner.nextLine().strip());

            System.out.printf("# > Enter Offer Name: ");
            promo.setOfferName(scanner.nextLine().strip());

            System.out.printf("# > Enter Description: ");
            promo.setDescription(scanner.nextLine().strip());

            System.out.printf("# > Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().strip());
            promo.setStartDate(startDate);

            System.out.printf("# > Enter End Date (YYYY-MM-DD, or leave empty if indefinite): ");
            String endDateInput = scanner.nextLine().strip();
            LocalDate endDate = endDateInput.isEmpty() ? null : LocalDate.parse(endDateInput);
            promo.setEndDate(endDate);

            System.out.printf("# > Enter Discount Type (percentage, fixedPrice): ");
            String discountTypeInput = scanner.nextLine().strip();
            promo.setDiscountType(DiscountType.valueOf(discountTypeInput.toLowerCase()));

//            System.out.print("# > Enter Contract Status (ongoing, completed, suspended): ");
//            String contractStatus = this.scanner.nextLine().strip();
//            contract.setContractStatus(ContractStatus.valueOf(contractStatus));
//
            System.out.printf("# > Enter Conditions: ");
            promo.setConditions(scanner.nextLine().strip());

            System.out.printf("# > Enter Offer Status (ACTIVE, EXPIRED, PENDING): ");
            String offerStatusInput = scanner.nextLine().strip();
            promo.setOfferStatus(OfferStatus.valueOf(offerStatusInput.toLowerCase()));

            System.out.print("# > Enter Contract UUID: ");
            UUID contractId = UUID.fromString(scanner.nextLine().strip());
            promo.setContractId(contractId);

            boolean isUpdated = promo.updatePromo(id);

            if (isUpdated) {
                System.out.printf("---------------------------------------------%n");
                System.out.printf(" Update successful for Promo with UUID: %s%n", id.toString());
                System.out.printf("---------------------------------------------%n");
            } else {
                System.out.printf("No promo was updated. Please check the UUID and try again.%n");
            }

        } catch (IllegalArgumentException e) {
            System.out.printf("Invalid input provided: %s. Please enter valid values.%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("An error occurred while updating the promo: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    // delete a promotion
    public void deletePromotion() {
        try {
            System.out.printf("# > Enter Partner id: ");
            UUID id = UUID.fromString(this.scanner.nextLine().strip());
            System.out.printf("---------------------------------------------%n");
            System.out.printf("             %13s          %n", promo.destroy(id));
            System.out.printf("---------------------------------------------%n");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
