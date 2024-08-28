package boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entity.*;

public class DonationManagementUI {

    private static final Scanner sc = new Scanner(System.in);

    public static int donationManagementMenu() {

        System.out.println("\n=============================================");
        System.out.println("         Donation Management System");
        System.out.println("=============================================");
        System.out.println("1. Add Donation");
        System.out.println("2. Remove Donation");
        System.out.println("3. Modify Donation Details");
        System.out.println("4. Search Donations");
        System.out.println("5. List All Donations");
        System.out.println("6. Generate Reports");
        System.out.println("7. Exit");
        System.out.println("=============================================");
        System.out.print("Choices (1-7): ");

        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public void getListDoneeDonationHeader() {
        System.out.println("                                                  Donation List                                                             ");
        System.out.println("============================================================================================================================");
        System.out.println("ID       Name                          Donation     Cash Amount    No. of       Donation      Date of       Donor     Donee ");
        System.out.println("                                         Type          (RM)        In-Kind      Category      Donation       ID        ID   ");
        System.out.println("============================================================================================================================");
    }

    public static void getDonationLogo() {
        System.out.println("""
                             _____                    _   _             
                            |  __ \\                  | | (_)            
                            | |  | | ___  _ __   __ _| |_ _  ___  _ __  
                            | |  | |/ _ \\| '_ \\ / _` | __| |/ _ \\| '_ \\ 
                            | |__| | (_) | | | | (_| | |_| | (_) | | | |
                            |_____/ \\___/|_| |_|\\__,_|\\__|_|\\___/|_| |_|
                                                                        
                                                                        """);
    }

    public void printAllDonationList(Donation donation) {
        System.out.printf(
                "%-8s %-25s %10s %14.2f %10d %15s %15s %9s %9s\n",
                donation.getDonationId(),
                donation.getDonationName(),
                donation.getDonationType(),
                donation.getCashAmount(),
                donation.getInKindAmount(),
                donation.getDonationCategory(),
                donation.getDonationDate(),
                donation.getDonorId(),
                donation.getDoneeId()
        );
    }

//    public Donation inputDonationDetail() {
//        String donationID = generateDonationID();
//        String donationName = inputDonationName();
//        String donationType = inputDonationType();
//        double cashAmount = inputCashAmount();
//        int inKindAmount = inputInKindAmount();
//        String donationCategory = inputDonationCategory();
//        String donorId = inputDonorDetail();
//        String doneeId = inputDoneeDetail();
//        String donationDate = getCurrentDate();
//        return new Donation(donationID,
//                donationName,
//                donationType,
//                cashAmount,
//                inKindAmount,
//                donationCategory,
//                donationDate,
//                donorId,
//                doneeId
//        );
//    }
    public String inputDonationName() {
        System.out.print("Please enter donation name: ");
        return sc.nextLine().trim();
    }

    public String inputDonationId() {

        System.out.print("Please enter Donation ID: ");
        String input = sc.nextLine().toUpperCase().trim();
        return input;
    }

    public int inputDonationType() {
        System.out.println("===========================");
        System.out.println("        Donation type      ");
        System.out.println("===========================");
        System.out.println("(1) Cash");
        System.out.println("(2) In-Kind");
        System.out.println("(3) Both");
        System.out.println("===========================");
        System.out.print("Your Selection > ");
        int input = sc.nextInt();
        sc.nextLine();

        return input;
    }

    public int inputDonationCategory() {
        System.out.println("===========================");
        System.out.println("      Donation Category    ");
        System.out.println("===========================");
        System.out.println("(1) Education");
        System.out.println("(2) Health");
        System.out.println("===========================");
        System.out.print("Your Selection > ");
        int input = sc.nextInt();
        sc.nextLine();
        
        return input;
    }

    public double inputCashAmount() {
        System.out.print("Please enter cash amount (RM):  ");
        double input = sc.nextDouble();
        sc.nextLine();

        String formattedInput = String.format("%.2f", input);
        return Double.parseDouble(formattedInput);
    }

    public int inputInKindAmount() {
        System.out.print("Please enter the quantity of In-Kind Items: ");
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    public String inputDonorDetail() {
        System.out.print("Please enter Donor ID: ");
        return sc.nextLine().toUpperCase().trim();
    }

    public String inputDoneeDetail() {
        System.out.print("Please enter Donee ID: ");
        return sc.nextLine().toUpperCase().trim();
    }

    public String getCurrentDate() {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return todayDate.format(formatter);
    }

    public void displaySuccessAddDonationMessage() {
        System.out.println("New donation detail has been successfully added!");
    }

    public void displayErrorDonationIdMessage() {
        System.out.println("The Donation ID cannot be found!");
    }

    public void displaySuccessRemoveDonationMessage() {
        System.out.println("The donation detail has been successfully removed!");
    }

    public void displayErrorInputMessage() {
        System.out.println("Invalid Input. Please try again!");
    }
    

    public void printDonationDetails(Donation donation) {
        System.out.println("======================================");
        System.out.println("             Donation Detail          ");
        System.out.println("======================================");
        System.out.println("ID            : " + donation.getDonationId());
        System.out.println("Name          : " + donation.getDonationName());
        System.out.println("Type          : " + donation.getDonationType());
        System.out.println("Cash Amount   : RM " + String.format("%.2f", donation.getCashAmount()));
        System.out.println("In-Kind Amount: " + donation.getInKindAmount());
        System.out.println("Category      : " + donation.getDonationCategory());
        System.out.println("Date          : " + donation.getDonationDate());
        System.out.println("Donor ID      : " + donation.getDonorId());
        System.out.println("Donee ID      : " + donation.getDoneeId());
        System.out.println("======================================");
    }
}
