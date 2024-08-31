package boundary;

import adt.MapEntryInterface;
import adt.MapInterface;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entity.*;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

/**
 *
 * @author user : Cheong Wei Zhe
 */
public class DonationManagementUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public int donationManagementMenu() {

        System.out.println("=============================================");
        System.out.println("        Donation Management SubSystem        ");
        System.out.println("=============================================");
        System.out.println(" (1)  Add New Donation");
        System.out.println(" (2)  Remove Donation");
        System.out.println(" (3)  Amend Donation Detail");
        System.out.println(" (4)  Search Donation");
        System.out.println(" (5)  Track Donations by Category");
        System.out.println(" (6)  List Donation by Different Donor");
        System.out.println(" (7)  List All Donations");
        System.out.println(" (8)  Filter donation by Date");
        System.out.println(" (9)  Generate Reports");
        System.out.println(" (10) Exit");
        System.out.println("=============================================");
        System.out.print("Choices (1-10) > ");

        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public void getListDonationHeader() {
        System.out.println("=================================================================================================================================");
        System.out.println("ID      Donation       Cash Amount          In-Kind        Quantity of           Donation      Date of         Donor       Donee ");
        System.out.println("          Type            (RM)                Item         In-Kind Item          Category     Donation          ID          ID   ");
        System.out.println("=================================================================================================================================");
    }

    public void getDonationLogo() {
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

        String formattedDate = donation.getDonationDate().format(DATE_FORMATTER);
        System.out.printf(
                "%-8s %-10s %10.2f %20s %15d %20s %15s %11s %11s\n",
                donation.getDonationId(),
                donation.getDonationType(),
                donation.getCashAmount(),
                donation.getInKindItem(),
                donation.getInKindAmount(),
                donation.getDonationCategory(),
                formattedDate,
                donation.getDonorId(),
                donation.getDoneeId()
        );
    }

    public int inputInKindItem() {
        System.out.println("===========================");
        System.out.println("        In-Kind Item       ");
        System.out.println("===========================");
        System.out.println("(1) Clothing");
        System.out.println("(2) Food");
        System.out.println("(3) School Supplies");
        System.out.println("(4) Household");
        System.out.println("(5) Medical Supplies");
        System.out.println("(6) Personal");
        System.out.println("===========================");
        System.out.print("Your Selection > ");
        int input = sc.nextInt();
        sc.nextLine();

        return input;

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
        System.out.println("(3) Disaster Relief");
        System.out.println("(4) Animal Welfare");
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

    public void displayErrorModifyCashMessage() {
        System.out.println("Modification not allowed: Donation type is cash only.");
    }

    public void displayErrorModifyInKindItemMessage() {
        System.out.println("Modification not allowed: Donation type is in-kind only.");
    }

    public void displaySuccessModifyDonationMessage() {
        System.out.println("Donation detail has been updated successfully!");
    }

    public void displayNoDonationEntityDateRangeMessage() {
        System.out.println("No donations found within the specified date range.");
    }

    public void displayNoDonorFoundMessage(String donorId) {
        System.out.println("No donor found with ID: " + donorId);
    }

    public void displayNoDonationsFoundMessage() {
        System.out.println("No donations found for the specified Donor ID.");
    }

    public void printDonationDetails(Donation donation) {
        System.out.println("\n");
        System.out.println("=============================================");
        System.out.println("              Donation Detail                ");
        System.out.println("=============================================");
        System.out.println("ID             : " + donation.getDonationId());
        System.out.println("Type           : " + donation.getDonationType());
        System.out.println("Cash Amount    : RM " + String.format("%.2f", donation.getCashAmount()));
        System.out.println("In-Kind Item   : " + donation.getInKindItem());
        System.out.println("In-Kind Amount : " + donation.getInKindAmount());
        System.out.println("Category       : " + donation.getDonationCategory());
        System.out.println("Date           : " + donation.getDonationDate().format(DATE_FORMATTER));
        System.out.println("Donor ID       : " + donation.getDonorId());
        System.out.println("Donee ID       : " + donation.getDoneeId());
        System.out.println("=============================================");
    }

    public int displayModificationMenu() {
        System.out.println("===================================");
        System.out.println("Select the data you want to modify ");
        System.out.println("===================================");
        System.out.println("(1) Donation Type");
        System.out.println("(2) Donation Category");
        System.out.println("(3) Donation Date");
        System.out.println("(4) Cash Amount");
        System.out.println("(5) In-Kind Item");
        System.out.println("(6) In-Kind Amount");
        System.out.println("(7) Exit");
        System.out.println("===================================");
        System.out.print("Enter your choice > ");

        int input = sc.nextInt();
        sc.nextLine();

        return input;
    }

    public int displayReportTypeMenu() {
        System.out.println("===================================");
        System.out.println("           Donation Report         ");
        System.out.println("===================================");
        System.out.println("(1) Total Donations Summary Report ");
        System.out.println("(2) Historial Trends Report        ");
        System.out.println("===================================");
        System.out.print("Enter your choice > ");

        int input = sc.nextInt();
        sc.nextLine();

        return input;

    }

    public void displayTrackItemsHeader() {
        System.out.println("\n");
        System.out.println("                   Track Donated Items by Category");
        System.out.println("=====================================================================");
        System.out.println("Donation Category             Total Cash       Total In-Kind           ");
        System.out.println("                                 (RM)              Items              ");
        System.out.println("=====================================================================");
    }

    public void displayCategoryTracking(String category, DonationTracking tracking) {
        System.out.printf("%-22s %15.2f %15d\n", category, tracking.getTotalCash(), tracking.getTotalInKindItems());
    }

    public LocalDate inputDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (dd/MM/yyyy): ");
            String input = sc.nextLine().trim();
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    }

    public void displayFilterTitle(LocalDate startDate, LocalDate endDate) {
        System.out.println("                                        Donation Entities between "
                + startDate.format(DATE_FORMATTER)
                + " and "
                + endDate.format(DATE_FORMATTER));
    }

    public void displayAllDonationTitle() {
        System.out.println("                                                  Donation List                                                             ");
    }

    public void printDonationDetailsByDonor(Donation donation) {
        System.out.println("-------------------------------------------------");
        System.out.println("Donation ID          : " + donation.getDonationId());
        System.out.println("Donation Type        : " + donation.getDonationType());
        System.out.println("Cash Amount          : RM " + String.format("%.2f", donation.getCashAmount()));
        System.out.println("In-Kind Item         : " + donation.getInKindItem());
        System.out.println("In-Kind Item Amount  : " + donation.getInKindAmount());
        System.out.println("Donation Categories  : " + donation.getDonationCategory());
        System.out.println("Date of Donation     : " + donation.getDonationDate().format(DATE_FORMATTER));
        System.out.println("-------------------------------------------------");
    }

    public void displayTotalDonationsReport(double totalCash, int totalInKind) {
        System.out.println("\n");
        System.out.println("=============================================");
        System.out.println("         Total Donation Summary Report       ");
        System.out.println("=============================================");
        System.out.printf("Total Cash Donations Received : RM %.2f\n", totalCash);
        System.out.println("Total In-Kind Items Donated   : " + totalInKind);
        System.out.println("=============================================");
    }

    public void displayHistoricalTrendReport(MapInterface<String, Double> cashTrends, MapInterface<String, Integer> itemsTrends) {
        System.out.println("\n");
        System.out.println("=========================================================");
        System.out.println("                Historical Trends Report                 ");
        System.out.println("=========================================================");
        System.out.println("Month                 Total Cash (RM)        Total Items");

        Iterable<MapEntryInterface<String, Double>> cashEntries = cashTrends.entrySet();
        Iterable<MapEntryInterface<String, Integer>> itemEntries = itemsTrends.entrySet();

        Iterator<MapEntryInterface<String, Double>> cashIter = cashEntries.iterator();
        while (cashIter.hasNext()) {
            MapEntryInterface<String, Double> cashEntry = cashIter.next();
            String month = cashEntry.getKey();
            Double totalCash = cashEntry.getValue();
            Integer totalItems = itemsTrends.get(month);  // Assuming same months present in both maps

            System.out.printf("%s\t%15.2f\t\t%9d\n", month, totalCash, totalItems);
        }
    }

}
