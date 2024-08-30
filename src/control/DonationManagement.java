package control;

import adt.*;
import boundary.DonationManagementUI;
import dao.*;
import entity.*;
import java.time.LocalDate;
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
                    searchDonation();
                case 5 ->
                    trackDonatedItemsByCategory();
                case 6 ->
                    System.out.println("None");
                case 7 ->
                    listAllDonations();
                case 8 ->
                    filterDonation();
                case 9 ->
                    generateReports();
                case 10 -> {
                    System.out.println("Thank you for using the Donation Management System.");
                    System.exit(0);
                }
                default ->
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void trackDonatedItemsByCategory() {
        // Map to store category-wise donation tracking
        MapInterface<String, DonationTracking> categoryTrackingMap = new LinkedHashMap<>();

        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            Donation donation = entry.getValue();
            String category = donation.getDonationCategory();

            // If the category is already present in the map, update the existing entry
            if (categoryTrackingMap.containsKey(category)) {
                DonationTracking existingTracking = categoryTrackingMap.get(category);
                existingTracking.addDonation(donation);
            } else {
                // Create a new entry for this category if not present
                DonationTracking newTracking = new DonationTracking();
                newTracking.addDonation(donation);
                categoryTrackingMap.put(category, newTracking);
            }
        }

        // Display the results
        donationUI.displayTrackItemsHeader();
        for (MapEntryInterface<String, DonationTracking> entry : categoryTrackingMap.entrySet()) {
            String category = entry.getKey();
            DonationTracking tracking = entry.getValue();
            donationUI.displayCategoryTracking(category, tracking);
        }

        MessageUI.systemPause();
    }

//    public void listDonationsByDonor() {
//        MapInterface<String, ListInterface<Donation>> donationsByDonor = new LinkedHashMap<>();
//
//        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
//            Donation donation = entry.getValue();
//            String donorId = donation.getDonorId();
//
//            if (donationsByDonor.containsKey(donorId)) {
//                donationsByDonor.get(donorId).add(donation);
//            } else {
//                ListInterface<Donation> donorDonations = new ArrayList<>(); // Ensure ListInterface is correctly implemented
//                donorDonations.add(donation);
//                donationsByDonor.put(donorId, donorDonations);
//            }
//        }
//
//        for (MapEntryInterface<String, ListInterface<Donation>> entry : donationsByDonor.entrySet()) {
//            String donorId = entry.getKey();
//            ListInterface<Donation> donorDonations = entry.getValue();
//
//            System.out.println("Donor ID: " + donorId);
//            donationUI.getListDoneeDonationHeader();
//
//            for (Donation donation : donorDonations) {
//                donationUI.printAllDonationList(donation);
//            }
//            System.out.println();
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
        try {
            // Generate and gather essential details
            String donationID = generateDonationID();
            String donationType = isValidDonationType(); // Ensure this method returns a valid donation type
            String inKindItem = "None"; // Default to "None" for in-kind items if not applicable
            double cashAmount = 0.0;
            int inKindAmount = 0;

            switch (donationType) {
                case "Cash" -> {
                    cashAmount = isValidDonationCashAmount();
                }
                case "In-Kind" -> {
                    inKindItem = isValidInKindItem();
                    inKindAmount = isValidDonationInKindAmount();
                }
                case "Both" -> {
                    cashAmount = isValidDonationCashAmount();
                    inKindItem = isValidInKindItem();
                    inKindAmount = isValidDonationInKindAmount();
                }
                default -> {
                    System.out.println("Invalid donation type selected. Please try again.");
                    return null;
                }
            }

            String donationCategory = isValidDonationCategory();
            String donorId = isValidDonorDetail();
            String doneeId = isValidDoneeDetail();
            LocalDate donationDate = getCurrentDate();

            // Create and return a Donation object with all gathered information
            return new Donation(
                    donationID,
                    donationType,
                    inKindItem,
                    cashAmount,
                    inKindAmount,
                    donationCategory,
                    donationDate,
                    donorId,
                    doneeId
            );
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
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

//    public void modifyDonation() {
//        String donationId = donationUI.inputDonationId();
//        boolean found = donationList.containsKey(donationId);
//
//        if (found) {
//
//            Donation existingDonation = donationList.get(donationId);
//
//            boolean continueModifying = true;
//            while (continueModifying) {
//
//                int choice = donationUI.displayModificationMenu();
//
//                switch (choice) {
//                    case 1 -> {
//                        String newinKindItem = isValidInKindItem();
//                        existingDonation.setInKindItem(newinKindItem);
//                    }
//                    case 2 -> {
//                        String newDonationType = isValidDonationType();
//                        existingDonation.setDonationType(newDonationType);
//                    }
//                    case 3 -> {
//                        String newDonationCategory = isValidDonationCategory();
//                        existingDonation.setDonationCategory(newDonationCategory);
//                    }
//                    case 4 ->
//                        continueModifying = false;
//                    default ->
//                        donationUI.displayErrorInputMessage();
//                }
//            }
//
//            donationList.put(donationId, existingDonation);
//            donationDAO.saveToFile(donationList);
//
//            donationUI.displaySuccessModifyDonationMessage();
//            donationUI.printDonationDetails(existingDonation);
//        } else {
//            donationUI.displayErrorDonationIdMessage();
//        }
//        MessageUI.systemPause();
//    }
    public void modifyDonation() {
        String donationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(donationId);

        if (found) {
            Donation existingDonation = donationList.get(donationId);
            boolean continueModifying = true;
            
            donationUI.printDonationDetails(existingDonation);

            while (continueModifying) {
                int choice = donationUI.displayModificationMenu();

                switch (choice) {
                    case 1 -> {
                        String newInKindItem = isValidInKindItem();
                        existingDonation.setInKindItem(newInKindItem);
                    }
                    case 2 -> {
                        String newDonationType = isValidDonationType();
                        existingDonation.setDonationType(newDonationType);

                        // Adjusting amounts based on new donation type
                        if (newDonationType.equals("Cash")) {
                            existingDonation.setInKindItem("None");
                            existingDonation.setInKindAmount(0);
                        } else if (newDonationType.equals("In-Kind")) {
                            existingDonation.setCashAmount(0.0);
                            existingDonation.setInKindAmount(existingDonation.getInKindAmount());
                        } else if (newDonationType.equals("Both")) {
                            existingDonation.setCashAmount(existingDonation.getCashAmount());
                            existingDonation.setInKindItem(existingDonation.getInKindItem());
                        }
                    }
                    case 3 -> {
                        String newDonationCategory = isValidDonationCategory();
                        existingDonation.setDonationCategory(newDonationCategory);
                    }
                    case 4 -> {
                        LocalDate newDonationDate = donationUI.inputDate("Enter new donation date");
                        existingDonation.setDonationDate(newDonationDate);
                    }
                    case 5 -> {
                        double newCashAmount = donationUI.inputCashAmount();
                        existingDonation.setCashAmount(newCashAmount);
                    }
                    case 6 -> {
                        int newInKindAmount = donationUI.inputInKindAmount();
                        existingDonation.setInKindAmount(newInKindAmount);
                    }
                    case 7 -> {
                        continueModifying = false;
                    }
                    default -> {
                        donationUI.displayErrorInputMessage();
                    }
                }
            }

            donationList.put(donationId, existingDonation);
            donationDAO.saveToFile(donationList);

            donationUI.displaySuccessModifyDonationMessage();
            donationUI.printDonationDetails(existingDonation);
        } else {
            donationUI.displayErrorDonationIdMessage();
        }
        MessageUI.systemPause();
    }

    public void searchDonation() {
        String searchDonationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(searchDonationId);

        if (found) {
            donationUI.printDonationDetails(donationList.get(searchDonationId));

        } else {
            donationUI.displayErrorDonationIdMessage();
            MessageUI.systemPause();
        }

    }

    public void filterDonation() {
        LocalDate startDate = donationUI.inputDate("Enter the start date");
        LocalDate endDate = donationUI.inputDate("Enter the end date");

        boolean haveEntity = false;

        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            Donation donation = entry.getValue();
            LocalDate donationDate = donation.getDonationDate();

            if ((donationDate.isEqual(startDate) || donationDate.isAfter(startDate))
                    && (donationDate.isEqual(endDate) || donationDate.isBefore(endDate))) {
                if (!haveEntity) {
                    donationUI.displayFilterTitle(startDate, endDate);
                    donationUI.getListDonationHeader();
                    haveEntity = true;
                }
                donationUI.printAllDonationList(donation);
            }
        }

        if (!haveEntity) {
            System.out.println("");
            donationUI.displayNoDonationEntityDateRangeMessage();
        }

        MessageUI.systemPause();
    }

    public void listAllDonations() {
        donationUI.displayAllDonationTitle();
        donationUI.getListDonationHeader();
        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            donationUI.printAllDonationList(entry.getValue());
        }
        MessageUI.systemPause();
    }

    public void generateReports() {
//        Map<String, Integer> categoryReport = new LinkedHashMap<>();
//
//        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
//            Donation donation = entry.getValue();
//            String category = donation.getDonationCategory();
//
//            categoryReport.put(category, categoryReport.getOrDefault(category, 0) + 1);
//        }
//
//        donationUI.displayReportHeader();
//        for (Map.Entry<String, Integer> reportEntry : categoryReport.entrySet()) {
//            System.out.printf("%-20s : %d donations\n", reportEntry.getKey(), reportEntry.getValue());
//        }
//        MessageUI.systemPause();
    }

    public String isValidInKindItem() {
        String selection;
        do {
            switch (donationUI.inputInKindItem()) {
                case 1 ->
                    selection = "Clothing";
                case 2 ->
                    selection = "Food";
                case 3 ->
                    selection = "School Supplies";
                case 4 ->
                    selection = "Household Items";
                case 5 ->
                    selection = "Medical Supplies";
                case 6 ->
                    selection = "Personal Care Items";
                default -> {
                    donationUI.displayErrorInputMessage();
                    selection = null;
                }
            }
        } while (selection == null);

        return selection;
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

    public LocalDate getCurrentDate() {
        return LocalDate.now();
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
