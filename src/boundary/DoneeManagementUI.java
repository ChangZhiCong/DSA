/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package boundary;

import entity.Donee;
import java.util.Scanner;

/**
 *
 * @author user : Chang Zhi Cong
 */
public class DoneeManagementUI {

    Scanner sc = new Scanner(System.in);

    public String inputDoneeID() {
        System.out.print("Enter donee id (eg: DE0001) : ");
        String doneeId = sc.nextLine().toUpperCase();
        return doneeId;
    }

    public String inputDoneeName() {
        System.out.print("Enter donee name : ");
        String doneeName = sc.nextLine();
        return doneeName;
    }

    public String inputDoneeContactNo() {
        System.out.print("Enter donee phone number (eg: 011-23828858) : ");
        String doneeContactNo = sc.nextLine();
        return doneeContactNo;
    }

    public String inputDoneeIdentity() {
        System.out.print("Enter donee identity (organisation, individual, family) : ");
        String doneeType = sc.nextLine();
        return doneeType;
    }

    public String inputDoneeEmail() {
        System.out.print("Enter donee email : ");
        String doneeEmail = sc.nextLine();
        return doneeEmail;
    }

    public void getDoneeLogo() {
        System.out.println("    _____                        ");
        System.out.println("    |  __ \\                       ");
        System.out.println("    | |  | | ___  _ __   ___  ___ ");
        System.out.println("    | |  | |/ _ \\| '_ \\ / _ \\/ _ \\");
        System.out.println("    | |__| | (_) | | | |  __/  __/");
        System.out.println("    |_____/ \\___/|_| |_|\\___|\\___|");
        System.out.println("========================================");
    }

    public int getDoneeMenuChoice() {
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Add new donee");
        System.out.println("2. Remove donee");
        System.out.println("3. Update donee");
        System.out.println("4. Search donee");
        System.out.println("5. List donee with all donations made");
        System.out.println("6. Filter donee");
        System.out.println("7. Generate donee report");
        System.out.println("8. Exit subsystem");
        System.out.print("\nSelect an option (1 - 8) : ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public int getDoneeFilterChoice() {
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Filter by id in ascending order");
        System.out.println("2. Filter by id in descending order");
        System.out.println("3. Filter by identity");
        System.out.println("4. Exit the function");
        System.out.print("\nSelect an option (1 - 4) : ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }

    public Donee inputDoneeDetails() {
        String doneeName = inputDoneeName();
        String doneeContactNo = inputDoneeContactNo();
        String doneeIdentity = inputDoneeIdentity();
        String doneeEmail = inputDoneeEmail();
        System.out.println();
        return new Donee(doneeName, doneeContactNo, doneeEmail, doneeIdentity);
    }

    public int getUpdateDoneeChoice() {
        System.out.println("");
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Update by name");
        System.out.println("2. Update by identity");
        System.out.println("3. Update by contact number");
        System.out.println("4. Update by email");
        System.out.println("5. Save update");
        System.out.println("6. Exit the function");
        System.out.print("\nSelect an option (1 - 6) : ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }

    public int getReportMenuChoice() {
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Donee Category Summary Report");
        System.out.println("2. Donee Acitvity Report");
        System.out.println("3. Exit the function");
        System.out.print("\nSelect an option (1 - 3) : ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }

    public void getCategoryReportHeader() {
        System.out.println("   Donee Category Summary Report");
        System.out.println("===================================");
    }

    public void getActivityReportHeader(String startDate, String endDate) {
        System.out.printf("\n\n                                Donee Activity Report from %s to %s\n", startDate, endDate);
        System.out.println("======================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Identity", "Contact Number", "Email", "Registered Date");
    }

    public void displayCategoryReport(Donee donee) {
        System.out.println("Total number of individual   : " + donee.getTotalIndividual());
        System.out.println("Total number of family       : " + donee.getTotalFamily());
        System.out.println("Total number of organisation : " + donee.getTotalOrganisation() + "\n");
    }

    public void displayActivityReport(Donee donee) {
        System.out.println("\nTotal number of donee within the specific date : " + donee.getTotalDonee() + "\n");
    }

    public String inputStartDate() {
        System.out.print("Enter the start date (eg. 14/08/2024) : ");
        String str = sc.nextLine();
        return str;
    }

    public String inputEndDate() {
        System.out.print("Enter the end date (eg. 14/08/2024) : ");
        String str = sc.nextLine();
        return str;
    }

    public void printDoneeDetails(Donee donee) {
        System.out.println("\nDonee Details");
        System.out.println("=================");
        System.out.println(donee);
    }

    public void getListDoneeDonationHeader() {
        System.out.println("                                                                 Donee List with All Donations Made ");
        System.out.println("==============================================================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s %-20s %-20s\n", "ID", "Name", "Identity", "Contact Number", "Email", "Registered Date", "Donation Category", "Donation Type");
    }

    public void getListDoneeHeader() {
        System.out.println("                                                Donee List ");
        System.out.println("======================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Identity", "Contact Number", "Email", "Registered Date");
    }

    public void printAllDoneeWithDonation(Donee donee) {
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s %-20s %-20s\n", donee.getDoneeId(), donee.getDoneeName(), donee.getDoneeIdentity(), donee.getDoneeContactNo(), donee.getDoneeEmail(), donee.getFormattedDoneeRegDate(), "Health", "Food");
    }

    public void printAllDonee(Donee donee) {
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", donee.getDoneeId(), donee.getDoneeName(), donee.getDoneeIdentity(), donee.getDoneeContactNo(), donee.getDoneeEmail(), donee.getFormattedDoneeRegDate());
    }

    public void displaySucessAddDoneeMessage() {
        System.out.println("You have successfully add a new donee !\n");
    }

    public void displaySucessRemoveDoneeMessage() {
        System.out.println("You have successfully remove a new donee !\n");
    }

    public void displaySuccessUpdateDoneeMessage() {
        System.out.println("You have successfully update the donee !");
    }
    
    public void displayInvalidMenuMessage() {
        System.out.println("\nYou only can select the given menu option !");
    }
    
    public void displayInvalidIDMessgae() {
        System.out.println("\nYou should enter a valid ID !");
    }
    
    public void displayValidIDMessage() {
        System.out.println("\nThe particular ID is found !");
    }
}
