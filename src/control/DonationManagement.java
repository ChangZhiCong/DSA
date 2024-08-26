package control;

import adt.LinkedHashMap;
import adt.MapInterface;
import boundary.DonationManagementUI;
import dao.DonationDAO;
import entity.Donation;

public class DonationManagement {
    
    private MapInterface<String, Donation> donationList = new LinkedHashMap<>();
    private final DonationDAO donationDAO = new DonationDAO("donation.txt");

    public static void main(String[] args){
        DonationManagement donation = new DonationManagement();
        donation.runDonationManagement();
    }

    public void runDonationManagement() {
        boolean cont = true;
        int choice;

        while (cont) {
            DonationManagementUI.getDonationLogo();
            donationList = donationDAO.retrieveFromFile();
            System.out.println(donationList);
            choice = DonationManagementUI.donationManagementMenu();

            switch (choice) {
                case 1 ->
                    addDonation();
                case 2 ->
                    removeDonation();
                case 3 ->
                    modifyDonation();
                case 4 ->
                    searchDonations();
                case 5 ->
                    listAllDonations();
                case 6 ->
                    generateReports();
                case 7 -> {
                    System.out.println("Thank you for using the Donation Management System.");
                    System.exit(0);
                }
                default ->
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    public void addDonation() {
        DonationManagementUI donationUI = new DonationManagementUI();
        
        // Get donation details from the user
        Donation donation = donationUI.inputDonationDetail();
        
        // Add donation to the donation list
        donationList.put(donation.getDonationId(), donation);
        
        // Save the updated donation list to text file
        donationDAO.saveToFile(donationList);

        // Display success message and print donation details
        donationUI.displaySuccessAddDonationMessage();
        donationUI.printDonationDetails(donation);
    }
    
    public void removeDonation(){
        
    }
    
    public void modifyDonation(){
        
    }
    
    public void searchDonations(){
        
    }
    
    public void listAllDonations(){
        
    }
    
    public void generateReports(){
        
    }
}
