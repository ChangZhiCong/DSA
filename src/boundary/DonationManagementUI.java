package boundary;

import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class DonationManagementUI {
    
    public static void main(String[] args){
        donationManagementMenu();
    }

    public static void donationManagementMenu() {
        
        Scanner sc = new Scanner(System.in);
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
        System.out.println("\nChoices (1-7): ");
        
        int choice = sc.nextInt();
            switch (choice) {
//                case 1 -> addDonationUI();
//                case 2 -> removeDonationUI();
//                case 3 -> modifyDonationUI();
//                case 4 -> searchDonationsUI();
//                case 5 -> listAllDonationsUI();
//                case 6 -> generateReportsUI();
                case 7 -> {
                    System.out.println("Thank you for using the Donation Management System.");
                    sc.close();
                    System.exit(0);
            }
                default -> System.out.println("Invalid option. Please try again.");
            }
    }

}
