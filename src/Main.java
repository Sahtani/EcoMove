import config.Db;
import models.Partner;
import models.TransportType;
import models.PartnerStatus;


import java.sql.Connection;
import java.util.Scanner;
import java.util.UUID;
public class Main {
    public static void main(String[] args) {
        Db db = Db.getInstance("EcoMove","postgres","soumia");
        Connection connection = db.getConnection();


        UUID partnerId = UUID.randomUUID();

                Scanner scanner = new Scanner(System.in);

                // Collect partner data from the user
                System.out.println("Enter company name:");
                String companyName = scanner.nextLine();

                System.out.println("Enter commercial contact:");
                String commercialContact = scanner.nextLine();

                System.out.println("Enter transport type (AVION, BUS, TRAIN):");
                String transportTypeInput = scanner.nextLine();
                TransportType transportType = TransportType.valueOf(transportTypeInput);

                System.out.println("Enter geographical zone:");
                String geographicalZone = scanner.nextLine();

                System.out.println("Enter special conditions:");
                String specialConditions = scanner.nextLine();

                System.out.println("Enter partner status (active, inactive, suspended):");
                String partnerStatusInput = scanner.nextLine();
                PartnerStatus partnerStatus = PartnerStatus.valueOf(partnerStatusInput.toUpperCase());


                // Add the partner using the DAO
//                Partner.addPartner(partnerId, companyName, commercialContact, transportType,
//                        geographicalZone, specialConditions, partnerStatus);

    }}

