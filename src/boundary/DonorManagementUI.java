/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donor;
import java.util.Scanner;

/**
 *
 * @author leewa
 */
public class DonorManagementUI {

    //Scanner
    Scanner sc = new Scanner(System.in);
    
    //Display Donor logo
    public void getDonorLogo(){
        System.out.println("                                                                      ");
        System.out.println("                  ,----..             ,--.     ,----..                ");
        System.out.println("    ,---,        /   /   \\          ,--.'|    /   /   \\   ,-.----.    ");
        System.out.println("  .'  .' `\\     /   .     :     ,--,:  : |   /   .     :  \\    /  \\   ");
        System.out.println(",---.'     \\   .   /   ;.  \\ ,`--.'`|  ' :  .   /   ;.  \\ ;   :    \\ ");
        System.out.println(" |   |  .`\\  | .   ;   /  ` ; |   :  :  | | .   ;   /  ` ; |   | .\\ :  ");
        System.out.println(":   : |  '  | ;   |  ; \\ ; | :   |   \\ | : ;   |  ; \\ ; | .   : |: |  ");
        System.out.println("|   ' '  ;  : |   :  | ; | ' |   : '  '; | |   :  | ; | ' |   |  \\ :  ");
        System.out.println("'   | ;  .  | .   |  ' ' ' : '   ' ;.    ; .   |  ' ' ' : |   : .  /  ");
        System.out.println("|   | :  |  ' '   ;  \\; /  | |   | | \\   | '   ;  \\; /  | ;   | |  \\  ");
        System.out.println("'   : | /  ;   \\   \\  ',  /  '   : |  ; .'  \\   \\  ',  /  |   | ;\\  \\ ");
        System.out.println("|   | '` ,/     ;   :    /   |   | '`--'     ;   :    /   :   ' | \\.' ");
        System.out.println(";   :  .'        \\   \\ .'    '   : |          \\   \\ .'    :   : :-'  ");
        System.out.println(" |   ,.'           `---`      ;   |.'           `---`      |   |.'     ");
        System.out.println("'---'                        '---'                        `---'     ");
        System.out.println("                                                                        ");
        System.out.println("========================================");
    }
    
    //Display Donor menu
    public int getDonorMenu(){
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Add new donor");
        System.out.println("2. Remove donor");
        System.out.println("3. Update donor");
        System.out.println("4. Search donor");
        System.out.println("5. List donor with all donations made");
        System.out.println("6. Filter donor");
        System.out.println("7. Categorise donors");
        System.out.println("8. Generate donor report");
        System.out.println("========================================");
        System.out.println("9. Back");
        System.out.println("========================================");
        System.out.print("\nSelect an option: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }
    
    //1. Add a new donor
    public String inputDonorName() {
        System.out.print("(1/5) Enter donor name : ");
        String donorName = sc.nextLine().trim();
        return donorName;
    }

    public String inputDonorContactNo() {
        System.out.print("(2/5) Enter donor phone number (eg: 011-23828858) : ");
        String donorContactNo = sc.nextLine().trim();
        return donorContactNo;
    }
    
    public String inputDonorEmail() {
        System.out.print("(3/5) Enter donor email : ");
        String donorEmail = sc.nextLine().trim();
        return donorEmail;
    }

    public int selectDonorIdentity() {
        System.out.print("(4/5) Select donor identity (Default = Individual, 1 = Organisation) : ");
        int choice = sc.nextInt();
        return choice;
    }
    
    public int selectDonorType() {
        System.out.print("(5/5) Select donor identity (Default = Private, 1 = Public, 2 = Government) : ");
        int choice = sc.nextInt();
        return choice;
    }
    
    public Donor inputDonorDetails() {
        String donorName = inputDonorName();
        String donorContactNo = inputDonorContactNo();
        String donorEmail = inputDonorEmail();
        int donorIdentityChoice = selectDonorIdentity();
        int donorTypeChoice = selectDonorType();
        System.out.println();
        return new Donor(donorName, donorContactNo, donorEmail, donorIdentityChoice, donorTypeChoice);
    }
        
    //Enter donorID
    public String inputDonorID() {
        System.out.print("Enter a donor ID (eg: DRXXXX) : ");
        String donorId = sc.nextLine().toUpperCase().trim();
        return donorId;
    }
    
    //Display donor details
    public void printDonorDetails(Donor donor) {
        System.out.println("\nDonor Details");
        System.out.println("=================");
        System.out.println(donor);
    }
    
    
    //3. Update donors details
    public int getUpdateDonorChoice() {
        System.out.println("");
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Update donor name");
        System.out.println("2. Update donor contact number");
        System.out.println("3. Update donor email");
        System.out.println("4. Change donor identity");   
        System.out.println("5. Change donor type");
        System.out.println("========================================");
        System.out.println("6. Save and exit.");
        System.out.println("========================================");
        System.out.print("\nSelect an option: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }
    
    //5. List donors with all the donations made
    public void getListDonorDonationHeader() {
        System.out.println("                                                                 Donor List with All Donations Made ");
        System.out.println("===================================================================================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s %-20s %-20s\n", "ID", "Name", "Identity", "Type", "Contact Number", "Email", "Registered Date", "Donation Category", "Donation Type");
    }
    
    public void printAllDoneeWithDonation(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s %-20s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorIdentity(), donor.getDonorType(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate(), "Health", "Food");
    }
    
    //6. Filter donor based on criteria
    public int getDonorFilterChoice() {
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Filter by donorID in ascending order");
        System.out.println("2. Filter by donorID in descending order");
        System.out.println("3. Filter by identity");
        System.out.println("========================================");
        System.out.println("Default. Exit the function");
        System.out.println("========================================");
        System.out.print("\nSelect an option: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }
    
    public void getListDonorHeader() {
        System.out.println("                                                Donor List ");
        System.out.println("===========================================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Identity", "Type", "Contact Number", "Email", "Registered Date");
    }
    
    public void printAllDonor(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorIdentity(), donor.getDonorType(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate());
    }
    
    //7. Categorise donors (type: government, private, public)
    
    //8. Generate summary reports
    public int getReportMenuChoice(){
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Donor Category Summary Report");
        System.out.println("2. Donor Acitvity Report");
        System.out.println("========================================");
        System.out.println("3. Exit the function");
        System.out.println("========================================");
        System.out.print("\nSelect an option : ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }
    
    public void getCategoryReport(Donor donor) {
        System.out.println("          Donor Category Summary Report");
        System.out.println("============================================================");
        System.out.println("| Identity \\\\ Type | Private | Public | Government | Total |");
        System.out.println("============================================================");
        System.out.printf("|    Individual    |  %4d   |  %4d  |    %4d    |  %4d |\n", donor.getTotalIndividualPrivate(), donor.getTotalIndividualPublic(), donor.getTotalIndividualGovernment(), donor.getTotalIndividualPrivate() + donor.getTotalIndividualPublic() + donor.getTotalIndividualGovernment());
        System.out.printf("|   Organisation   |  %4d   |  %4d  |    %4d    |  %4d |\n", donor.getTotalOrganisationPrivate(), donor.getTotalOrganisationPublic(), donor.getTotalOrganisationGovernment(), donor.getTotalOrganisationPrivate() + donor.getTotalOrganisationPublic() + donor.getTotalOrganisationGovernment());
        System.out.println("============================================================");
        System.out.printf("|      Total       |  %4d   |  %4d  |    %4d    |  %4d |\n", donor.getTotalIndividualPrivate() + donor.getTotalOrganisationPrivate(), donor.getTotalIndividualPublic() + donor.getTotalOrganisationPublic(), donor.getTotalIndividualGovernment() + donor.getTotalOrganisationGovernment(), donor.getTotalDonor());
        System.out.println("============================================================");
    }
    
    public String inputStartDate() {
        System.out.print("Enter the start date (eg. 14/08/2024) : ");
        String str = sc.nextLine().trim();
        return str;
    }

    public String inputEndDate() {
        System.out.print("Enter the end date (eg. 14/08/2024) : ");
        String str = sc.nextLine().trim();
        return str;
    }
    
    public void getActivityReportHeader(String startDate, String endDate) {
        System.out.printf("\n\n                                New Donors Report from %s to %s\n", startDate, endDate);
        System.out.println("===========================================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Identity", "Type", "Contact Number", "Email", "Registered Date");
    }
    
    public void displayActivityReport(Donor donor) {
        System.out.println("\nTotal number of donor(s) joined within the specific date : " + donor.getTotalDonor() + "\n");
    }
        
    //Common Messages
    public void displaySucessAddDonorMessage() {
        System.out.println("You have added a new donor successfully !\n");
    }

    public void displaySucessRemoveDonorMessage() {
        System.out.println("You have removed a donor successfully!\n");
    }

    public void displaySuccessUpdateDonorMessage() {
        System.out.println("You have updated this donor details successfully!\n");
    }
    
    public void displayInvalidMenuMessage() {
        System.out.println("\nYou can select the given menu option only!\n");
    }
    
    public void displayInvalidIDMessgae() {
        System.out.println("\nYou should enter a valid donor ID!\n");
    }
    
    public void displayValidIDMessage() {
        System.out.println("\nThe donor ID is found!\n");
    }
}
