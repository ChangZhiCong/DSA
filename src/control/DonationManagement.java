package control;

import adt.LinkedHashMap;
import adt.MapEntryInterface;
import adt.MapInterface;
import boundary.DonationManagementUI;
import dao.DonationDAO;
import entity.Donation;
import utility.MessageUI;

public class DonationManagement {

    private MapInterface<String, Donation> donationList = new LinkedHashMap<>();
    private final DonationManagementUI donationUI = new DonationManagementUI();
    private Donation donation = new Donation();
    private final DonationDAO donationDAO = new DonationDAO("donation.txt");

    public static void main(String[] args) {
        DonationManagement donation = new DonationManagement();
        donation.runDonationManagement();
    }

    public void runDonationManagement() {
        boolean cont = true;
        int choice;

        while (cont) {
            donationList = donationDAO.retrieveFromFile();
            System.out.println(donationList); // Verify if can retrieve the data inside text file
            DonationManagementUI.getDonationLogo();
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

        // Get donation details from the user
        donation = donationUI.inputDonationDetail();

        // Add donation to the donation list
        donationList.put(donation.getDonationId(), donation);

        // Save the updated donation list to text file
        donationDAO.saveToFile(donationList);

        // Display success message and print donation details
        donationUI.displaySuccessAddDonationMessage();
        donationUI.printDonationDetails(donation);
    }

    public void removeDonation() {
        String searchDonationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(searchDonationId);

        if (found) {
            removeDonationFromFile(searchDonationId);
            donationUI.displaySuccessRemoveDonationMessage();
        } else {
            donationUI.displayErrorDonationIdMessage();
        }
        MessageUI.systemPause();
    }

    public void modifyDonation() {

    }

    public void searchDonations() {
        String searchDonationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(searchDonationId);

        if (found) {
            donationUI.printDonationDetails(donationList.get(searchDonationId));

        } else {
            donationUI.displayErrorDonationIdMessage();
            MessageUI.systemPause();
        }

    }

    public void listAllDonations() {
        donationUI.getListDoneeDonationHeader();
        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            donationUI.printAllDonationList(entry.getValue());
        }
    }

    public void generateReports() {

    }

    public void removeDonationFromFile(String donationId) {
        boolean isRemoved = donationList.remove(donationId);
        if (isRemoved) {
            donationDAO.saveToFile(donationList);
        }
    }
}