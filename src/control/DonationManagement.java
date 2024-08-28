package control;

import adt.*;
import boundary.DonationManagementUI;
import dao.*;
import entity.*;
import utility.MessageUI;

/**
 *
 * @author user : Cheong Wei Zhe
 */
public class DonationManagement {

    private final DonationManagementUI donationUI = new DonationManagementUI();
    private Donation donation = new Donation();

    private MapInterface<String, Donation> donationList = new LinkedHashMap<>();
    private MapInterface<String, Donor> donorList = new LinkedHashMap<>();
    private MapInterface<String, Donee> doneeList = new LinkedHashMap<>();

    private final DonationDAO donationDAO = new DonationDAO("donation.txt");
    private final DonorDAO donorDAO = new DonorDAO("donor.txt");
    private final DoneeDAO doneeDAO = new DoneeDAO("donee.txt");

    public static void main(String[] args) {
        DonationManagement donation = new DonationManagement();
        donation.runDonationManagement();
    }

    public void runDonationManagement() {
        boolean cont = true;
        int choice;

        while (cont) {
            // Read every file to ensure data is up-to-date
            donationList = donationDAO.retrieveFromFile();
            donorList = donorDAO.retrieveFromFile();
            doneeList = doneeDAO.retrieveFromFile();

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

//    public void listDonationsByDonor() {
//        // Initialize a LinkedHashMap to group donations by donor ID
//        MapInterface<String, ListInterface<Donation>> donationsByDonor = new LinkedHashMap<>();
//
//        // Iterate over all donations in the donationList
//        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
//            Donation donation = entry.getValue();
//            String donorId = donation.getDonorId();
//
//            // If the donorId is already in the map, add the donation to the list
//            if (donationsByDonor.containsKey(donorId)) {
//                donationsByDonor.get(donorId).add(donation);
//            } else {
//                // If the donorId is not in the map, create a new list and add the donation
//                ListInterface<Donation> donorDonations = new ArrayList<>();
//                donorDonations.add(donation);
//                donationsByDonor.put(donorId, donorDonations);
//            }
//        }
//
//        // Display the grouped donations
//        for (MapEntryInterface<String, ListInterface<Donation>> entry : donationsByDonor.entrySet()) {
//            String donorId = entry.getKey();
//            ListInterface<Donation> donorDonations = entry.getValue();
//
//            // Display donor details (you may want to fetch donor details from donorList)
//            System.out.println("Donor ID: " + donorId);
//            donationUI.getListDoneeDonationHeader();
//
//            // Display all donations for the current donor
//            for (Donation donation : donorDonations) {
//                donationUI.printAllDonationList(donation);
//            }
//            System.out.println(); // Print a newline for better readability
//        }
//    }
    public void addDonation() {

        // Get donation details from the user
        donation = inputDonationDetail();

        // Add donation to the donation list
        donationList.put(donation.getDonationId(), donation);

        // Save the updated donation list to text file
        donationDAO.saveToFile(donationList);

        // Display success message and print donation details
        donationUI.displaySuccessAddDonationMessage();
        donationUI.printDonationDetails(donation);
        MessageUI.systemPause();
    }

    public Donation inputDonationDetail() {
        // Generate and gather essential details
        String donationID = generateDonationID();
        String donationName = donationUI.inputDonationName();
        String donationType = isValidDonationType();

        // Determine donation amounts based on donation type
        double cashAmount = (donationType.equals("Cash") || donationType.equals("Both"))
                ? isValidDonationCashAmount()
                : 0.0;
        int inKindAmount = (donationType.equals("In-Kind") || donationType.equals("Both"))
                ? isValidDonationInKindAmount()
                : 0;

        // Gather additional details
        String donationCategory = isValidDonationCategory();
        String donorId = isValidDonorDetail();
        String doneeId = isValidDoneeDetail();
        String donationDate = donationUI.getCurrentDate();

        // Create and return a Donation object with all gathered information
        return new Donation(
                donationID,
                donationName,
                donationType,
                cashAmount,
                inKindAmount,
                donationCategory,
                donationDate,
                donorId,
                doneeId
        );
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
        String donationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(donationId);

        if (found) {
            // Retrieve the existing donation
            Donation existingDonation = donationList.get(donationId);

            boolean continueModifying = true;
            while (continueModifying) {
                // Display modification menu
                int choice = donationUI.displayModificationMenu();

                switch (choice) {
                    case 1 -> {
                        String newDonationName = donationUI.inputDonationName();
                        existingDonation.setDonationName(newDonationName);
                    }
                    case 2 -> {
                        String newDonationType = isValidDonationType();
                        existingDonation.setDonationType(newDonationType);
                    }
                    case 3 -> {
                        String newDonationCategory = isValidDonationCategory();
                        existingDonation.setDonationCategory(newDonationCategory);
                    }
                    case 4 ->
                        continueModifying = false;
                    default ->
                        donationUI.displayErrorInputMessage();
                }
            }

            // Save the updated donation list to the text file
            donationList.put(donationId, existingDonation);
            donationDAO.saveToFile(donationList);

            // Display success message and print updated donation details
            donationUI.displaySuccessModifyDonationMessage();
            donationUI.printDonationDetails(existingDonation);
        } else {
            donationUI.displayErrorDonationIdMessage();
        }
        MessageUI.systemPause();
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

    public String isValidDonationType() {
        String selection;
        do {
            switch (donationUI.inputDonationType()) {
                case 1 ->
                    selection = "Cash";
                case 2 ->
                    selection = "In-Kind";
                case 3 ->
                    selection = "Both";
                default -> {
                    donationUI.displayErrorInputMessage();
                    selection = null;
                }
            }
        } while (selection == null);

        return selection;
    }

    public double isValidDonationCashAmount() {
        double cash;

        while ((cash = donationUI.inputCashAmount()) <= 0) {
            donationUI.displayErrorInputMessage();
        }

        return cash;
    }

    public int isValidDonationInKindAmount() {
        int inKind;

        while ((inKind = donationUI.inputInKindAmount()) <= 0) {
            donationUI.displayErrorInputMessage();
        }

        return inKind;
    }

    public String isValidDonationCategory() {
        boolean valid = false;
        String selection = null;
        while (!valid) {
            switch (donationUI.inputDonationCategory()) {
                case 1 -> {
                    return "Education";
                }
                case 2 -> {
                    return "Health";
                }
                case 3 -> {
                    return "Disaster Relief";
                }
                case 4 -> {
                    return "Animal Welfare";
                }
                default -> {
                    donationUI.displayErrorInputMessage();
                }
            }
        }
        return selection;
    }

    public String isValidDonorDetail() {
        String donorId;
        do {
            donorId = donationUI.inputDonorDetail();
            if (donorList.containsKey(donorId)) {
                return donorId;
            } else {
                donationUI.displayErrorInputMessage();
            }
        } while (true);
    }

    public String isValidDoneeDetail() {
        String doneeId;
        do {
            doneeId = donationUI.inputDoneeDetail();
            if (doneeList.containsKey(doneeId)) {
                return doneeId;
            } else {
                donationUI.displayErrorInputMessage();
            }
        } while (true);
    }

    public String generateDonationID() {
        String prefix = "DT";
        String newDonationId;

        do {
            // Generate random number between 1000 and 9999
            int randomNumber = (int) (Math.random() * 9000) + 1000;
            newDonationId = prefix + randomNumber;
        } while (donationList.containsKey(newDonationId)); // Repeat until a unique ID is generated

        return newDonationId;
    }

    public void removeDonationFromFile(String donationId) {
        boolean isRemoved = donationList.remove(donationId);
        if (isRemoved) {
            donationDAO.saveToFile(donationList);
        }
    }
}
