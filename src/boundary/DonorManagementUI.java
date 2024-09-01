package boundary;

import entity.Donor;
import java.util.Scanner;

/**
 *
 * @author Lee Wai Xian
 */
public class DonorManagementUI {

    Scanner sc = new Scanner(System.in);

    public void getDonorLogo() {
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

    public int getDonorMenu() {
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

    public String inputDonorName() {
        System.out.print("Enter donor name : ");
        String donorName = sc.nextLine().trim();
        return donorName;
    }

    public String inputDonorContactNo() {
        System.out.print("Enter donor phone number (eg: 011-23828858) : ");
        String donorContactNo = sc.nextLine().trim();
        return donorContactNo;
    }

    public String inputDonorEmail() {
        System.out.print("Enter donor email : ");
        String donorEmail = sc.nextLine().trim();
        return donorEmail;
    }

    public int selectDonorIdentity() {
        System.out.print("Select donor identity (Default = Individual, 1 = Organisation) : ");
        int choice = sc.nextInt();
        return choice;
    }

    public int selectDonorType() {
        System.out.print("Select donor identity (Default = Private, 1 = Public, 2 = Government) : ");
        int choice = sc.nextInt();
        return choice;
    }

    public String inputDonorID() {
        System.out.print("Enter a donor ID (eg: DRXXXX) : ");
        String donorId = sc.nextLine().toUpperCase().trim();
        return donorId;
    }

    public void printDonorDetails(Donor donor) {
        System.out.println("\nDonor Details");
        System.out.println("=================");
        System.out.println(donor);
    }

    public int getUpdateDonorChoice() {
        System.out.println("");
        System.out.println("       Update Donor Details Menu");
        System.out.println("========================================");
        System.out.println("1. Name");
        System.out.println("2. Contact Number");
        System.out.println("3. Email");
        System.out.println("4. Donor Identity");
        System.out.println("5. Donor Type");
        System.out.println("========================================");
        System.out.println("6. Save and Exit");
        System.out.println("========================================");
        System.out.print("\nSelect an option: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return choice;
    }

    public void getListDonorDonationHeader() {
        System.out.println("                                                                                       Donor List with All Donations Made ");
        System.out.println("=========================================================================================================================================================================================================");
        System.out.printf("%-8s %-15s %-15s %-10s %-15s %-25s %-15s %-25s %-15s %-15s %-15s %-15s\n", "ID", "Name", "Identity", "Type", "Contact Number", "Email", "Registered Date", "In-Kind Item", "Donation Type", "Cash Amount", "In-kind Amount", "Donation Category");
    }

    public void printAllDonorWithDonation(Donor donor, String inKindItem, String donationType, double cashAmount, int inKindAmount, String donationCategory) {
        System.out.printf("%-8s %-15s %-15s %-10s %-15s %-25s %-15s %-25s %-15s %-15.2f %-15d %-15s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorIdentity(), donor.getDonorType(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate(), inKindItem, donationType, cashAmount, inKindAmount, donationCategory);
    }

    public void printAllDonorWithManyDonation(String inKindItem, String donationType, double cashAmount, int inKindAmount, String donationCategory) {
        System.out.printf("%-8s %-15s %-15s %-10s %-15s %-25s %-15s %-25s %-15s %-15.2f %-15d %-15s\n", "", "", "", "", "", "", "", inKindItem, donationType, cashAmount, inKindAmount, donationCategory);
    }

    public void printAllDonorWithNoDonation(Donor donor) {
        System.out.printf("%-8s %-15s %-15s %-10s %-15s %-25s %-15s %-25s %-15s %-15s %-15s %-15s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorIdentity(), donor.getDonorType(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate(), "(No Donations Made)", "-", "-", "-", "-");
    }

    public void printAllDonorWithDonationFooter(int totalDonor, int totalDonation) {
        System.out.println("\nTotal donor(s) listed: " + totalDonor + ", Total donation(s) listed: " + totalDonation);
    }

    public int getDonorFilterChoice() {
        System.out.println("          Donor Filter Menu");
        System.out.println("========================================");
        System.out.println("1. Filter by donor's name");
        System.out.println("2. Filter by donor's identity");
        System.out.println("3. Filter by donor's type");
        System.out.println("4. Filter by donor's identity and type");
        System.out.println("========================================");
        System.out.println("5. Exit the function");
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
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Donor Identity", "Donor Type", "Contact Number", "Email", "Registered Date");
    }

    public void printAllDonor(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorIdentity(), donor.getDonorType(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate());
    }

    public void getListNameContainsHeader(String name) {
        System.out.printf("\n                                                 Donor List of Names that Contains \"%s\"\n", name);
        System.out.println("===========================================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Donor Identity", "Donor Type", "Contact Number", "Email", "Registered Date");
    }

    public void getListIdentityDonorHeader(String donorIdentity) {
        System.out.printf("\n                                            Donor List of %s Identity\n", donorIdentity);
        System.out.println("=========================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Donor Type", "Email", "Registered Date");
    }

    public void getListTypeDonorHeader(String donorType) {
        System.out.printf("\n                                          Donor List of %s Type\n", donorType);
        System.out.println("=========================================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Donor Identity", "Email", "Registered Date");
    }

    public void getListIdentityTypeDonorHeader(String donorIdentityType) {
        System.out.printf("\n                          Donor List of %s\n", donorIdentityType);
        System.out.println("==================================================================================================");
        System.out.printf("%-8s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Email", "Registered Date");
    }

    public void printCertainIdentityDonor(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorContactNo(), donor.getDonorType(), donor.getDonorEmail(), donor.getFormattedDonorRegDate());
    }

    public void printCertainTypeDonor(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorContactNo(), donor.getDonorIdentity(), donor.getDonorEmail(), donor.getFormattedDonorRegDate());
    }

    public void printCertainIdentityTypeDonor(Donor donor) {
        System.out.printf("%-8s %-20s %-20s %-30s %-20s\n", donor.getDonorId(), donor.getDonorName(), donor.getDonorContactNo(), donor.getDonorEmail(), donor.getFormattedDonorRegDate());
    }

    public void getFilterListFooter(int totalResult) {
        System.out.println("\nTotal donor(s) found: " + totalResult);
    }

    public void getListCategorisedDonorHeader() {
        System.out.println("\n                                Categorised Donor List Based on Donor Type");
        System.out.println("======================================================================================================================");
    }

    public void getGovernmentTypeHeader() {
        System.out.println("\nGOVERNMENT");
        System.out.println("==========\n");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Donor Identity", "Email", "Registered Date");
    }

    public void getGovernmentTypeFooter(int totalGovernment) {
        System.out.println("\nTotal government typed donor(s): " + totalGovernment);
    }

    public void getPrivateTypeHeader() {
        System.out.println("\nPRIVATE");
        System.out.println("=======\n");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Donor Identity", "Email", "Registered Date");
    }

    public void getPrivateTypeFooter(int totalPrivate) {
        System.out.println("\nTotal private typed donor(s): " + totalPrivate);
    }

    public void getPublicTypeHeader() {
        System.out.println("\nPUBLIC");
        System.out.println("======\n");
        System.out.printf("%-8s %-20s %-20s %-20s %-30s %-20s\n", "ID", "Name", "Contact Number", "Donor Identity", "Email", "Registered Date");
    }

    public void getPublicTypeFooter(int totalPublic) {
        System.out.println("\nTotal public typed donor(s): " + totalPublic);
    }

    public void getTypeFooter(int total) {
        System.out.println("\nTotal donor(s) listed: " + total);
    }

    public int getReportMenuChoice() {
        System.out.println("              Report Menu");
        System.out.println("========================================");
        System.out.println("1. Donors Category Summary Report");
        System.out.println("2. New Donors Report");
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
        System.out.println("                Donors Category Summary Report");
        System.out.println("============================================================");
        System.out.println("| Identity \\\\ Type | Private | Public | Government | Total |");
        System.out.println("============================================================");
        System.out.printf("|    Individual    |  %4d   |  %4d  |    %4d    |  %4d |\n", Donor.getTotalIndividualPrivate(), Donor.getTotalIndividualPublic(), Donor.getTotalIndividualGovernment(), Donor.getTotalIndividualPrivate() + Donor.getTotalIndividualPublic() + Donor.getTotalIndividualGovernment());
        System.out.printf("|   Organisation   |  %4d   |  %4d  |    %4d    |  %4d |\n", Donor.getTotalOrganisationPrivate(), Donor.getTotalOrganisationPublic(), Donor.getTotalOrganisationGovernment(), Donor.getTotalOrganisationPrivate() + Donor.getTotalOrganisationPublic() + Donor.getTotalOrganisationGovernment());
        System.out.println("============================================================");
        System.out.printf("|      Total       |  %4d   |  %4d  |    %4d    |  %4d |\n", Donor.getTotalIndividualPrivate() + Donor.getTotalOrganisationPrivate(), Donor.getTotalIndividualPublic() + Donor.getTotalOrganisationPublic(), Donor.getTotalIndividualGovernment() + Donor.getTotalOrganisationGovernment(), Donor.getTotalDonor());
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
        System.out.println("\nTotal number of donor(s) joined within the specific date: " + Donor.getTotalDonor() + "\n");
    }

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
