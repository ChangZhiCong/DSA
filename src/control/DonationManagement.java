package control;

import adt.*;
import boundary.DonationManagementUI;
import dao.*;
import entity.*;
import java.time.LocalDate;
import java.util.Iterator;
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

            donationUI.getDonationLogo();
            choice = donationUI.donationManagementMenu();

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
                    listDonationsByDonor();
                case 7 ->
                    listAllDonations();
                case 8 ->
                    filterDonationbyDate();
                case 9 ->
                    generateReports();
                //generateTotalDonationsReport();
                //generateHistoricalTrendReport();
                case 10 -> {
                    System.out.println("Thank you for using the Donation Management System.");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                }
            }
            MessageUI.systemPause();
        }
    }

    public void trackDonatedItemsByCategory() {

        MapInterface<String, DonationTracking> categoryTrackingMap = new LinkedHashMap<>();

        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            Donation donation = entry.getValue();
            String category = donation.getDonationCategory();

            if (categoryTrackingMap.containsKey(category)) {
                DonationTracking existingTracking = categoryTrackingMap.get(category);
                existingTracking.addDonation(donation);
            } else {
                DonationTracking newTracking = new DonationTracking();
                newTracking.addDonation(donation);
                categoryTrackingMap.put(category, newTracking);
            }
        }

        donationUI.displayTrackItemsHeader();
        for (MapEntryInterface<String, DonationTracking> entry : categoryTrackingMap.entrySet()) {
            String category = entry.getKey();
            DonationTracking tracking = entry.getValue();
            donationUI.displayCategoryTracking(category, tracking);
        }

    }

    public void listDonationsByDonor() {
        String donorId = donationUI.inputDonorDetail();
        if (!donorList.containsKey(donorId)) {
            donationUI.displayNoDonorFoundMessage(donorId);
            return;
        }

        Donor donor = donorList.get(donorId);
        if (donor == null) {
            donationUI.displayNoDonorFoundMessage(donorId);
            return;
        }

        boolean found = false;
        Iterator<MapEntryInterface<String, Donation>> iterator = donationList.entrySet().iterator();
        while (iterator.hasNext()) {
            MapEntryInterface<String, Donation> entry = iterator.next();
            Donation donation = entry.getValue();
            if (donation.getDonorId().equals(donorId)) {
                donationUI.printDonationDetailsByDonor(donation);
                found = true;
            }
        }

        if (!found) {
            donationUI.displayNoDonationsFoundMessage();
        }
    }

    public void addDonation() {

        donation = inputDonationDetail();

        donationList.put(donation.getDonationId(), donation);

        donationDAO.saveToFile(donationList);

        donationUI.displaySuccessAddDonationMessage();
        donationUI.printDonationDetails(donation);
    }

    public Donation inputDonationDetail() {

        String donationID = generateDonationID();
        String donationType = isValidDonationType();
        String inKindItem = "None";
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
    }

    public void modifyDonation() {
        String donationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(donationId);

        if (found) {
            Donation existingDonation = donationList.get(donationId);
            boolean continueModifying = true;

            while (continueModifying) {
                donationUI.printDonationDetails(existingDonation);
                int choice = donationUI.displayModificationMenu();

                switch (choice) {
                    case 1 -> {
                        String newDonationType = isValidDonationType();
                        existingDonation.setDonationType(newDonationType);

                        if (newDonationType.equalsIgnoreCase("Cash")) {
                            existingDonation.setInKindItem("None");
                            existingDonation.setInKindAmount(0);
                            double newCashAmount = donationUI.inputCashAmount();
                            existingDonation.setCashAmount(newCashAmount);
                        } else if (newDonationType.equalsIgnoreCase("In-Kind")) {
                            existingDonation.setCashAmount(0.0);
                            String newInKindItem = isValidInKindItem();
                            int newInKindAmount = donationUI.inputInKindAmount();
                            existingDonation.setInKindItem(newInKindItem);
                            existingDonation.setInKindAmount(newInKindAmount);
                        } else if (newDonationType.equalsIgnoreCase("Both")) {
                            double newCashAmount = donationUI.inputCashAmount();
                            String newInKindItem = isValidInKindItem();
                            int newInKindAmount = donationUI.inputInKindAmount();
                            existingDonation.setCashAmount(newCashAmount);
                            existingDonation.setInKindItem(newInKindItem);
                            existingDonation.setInKindAmount(newInKindAmount);
                        }
                    }
                    case 2 -> {
                        String newDonationCategory = isValidDonationCategory();
                        existingDonation.setDonationCategory(newDonationCategory);
                    }
                    case 3 -> {
                        LocalDate newDonationDate = donationUI.inputDate("Enter new donation date");
                        existingDonation.setDonationDate(newDonationDate);
                    }
                    case 4 -> {
                        if (existingDonation.getDonationType().equalsIgnoreCase("In-Kind")) {
                            donationUI.displayErrorInputMessage();
                            donationUI.displayErrorModifyInKindItemMessage();
                        } else {
                            double newCashAmount = donationUI.inputCashAmount();
                            existingDonation.setCashAmount(newCashAmount);
                        }
                    }
                    case 5 -> {
                        if (existingDonation.getDonationType().equalsIgnoreCase("Cash")) {
                            donationUI.displayErrorInputMessage();
                            donationUI.displayErrorModifyCashMessage();
                        } else {
                            String newInKindItem = isValidInKindItem();
                            existingDonation.setInKindItem(newInKindItem);
                        }
                    }

                    case 6 -> {
                        if (existingDonation.getDonationType().equalsIgnoreCase("Cash")) {
                            donationUI.displayErrorInputMessage();
                            donationUI.displayErrorModifyCashMessage();
                        } else {
                            int newInKindAmount = donationUI.inputInKindAmount();
                            existingDonation.setInKindAmount(newInKindAmount);
                        }
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
    }

    public void searchDonation() {
        String searchDonationId = donationUI.inputDonationId();
        boolean found = donationList.containsKey(searchDonationId);

        if (found) {
            donationUI.printDonationDetails(donationList.get(searchDonationId));

        } else {
            donationUI.displayErrorDonationIdMessage();
        }

    }

    public void filterDonationbyDate() {
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

    }

    public void listAllDonations() {
        donationUI.displayAllDonationTitle();
        donationUI.getListDonationHeader();
        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            donationUI.printAllDonationList(entry.getValue());
        }
    }

    public void generateReports() {
        boolean isValid = false;

        do {
            int choice = donationUI.displayReportTypeMenu();

            switch (choice) {
                case 1 -> {
                    generateTotalDonationsReport();
                    isValid = true;
                }
                case 2 -> {
                    generateHistoricalTrendReport();
                    isValid = true;
                }
                default -> donationUI.displayErrorInputMessage();
            }
        } while (!isValid);
    }

    public void generateHistoricalTrendReport() {
        MapInterface<String, Double> monthlyCashTrends = new LinkedHashMap<>();
        MapInterface<String, Integer> monthlyItemsTrends = new LinkedHashMap<>();

        Iterable<MapEntryInterface<String, Donation>> entries = donationList.entrySet();
        for (MapEntryInterface<String, Donation> entry : entries) {
            Donation donation = entry.getValue();
            String monthKey = donation.getDonationDate().getMonth() + " " + donation.getDonationDate().getYear();

            Double currentCash = monthlyCashTrends.get(monthKey);
            if (currentCash == null) {
                currentCash = 0.0;
            }
            monthlyCashTrends.put(monthKey, currentCash + donation.getCashAmount());

            Integer currentItems = monthlyItemsTrends.get(monthKey);
            if (currentItems == null) {
                currentItems = 0;
            }
            monthlyItemsTrends.put(monthKey, currentItems + donation.getInKindAmount());
        }

        donationUI.displayHistoricalTrendReport(monthlyCashTrends, monthlyItemsTrends);
    }

    public void generateTotalDonationsReport() {
        double totalCash = 0;
        int totalInKind = 0;

        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            Donation donation = entry.getValue();

            if ("Cash".equals(donation.getDonationType()) || "Both".equals(donation.getDonationType())) {
                totalCash += donation.getCashAmount();
            }
            if ("In-Kind".equals(donation.getDonationType()) || "Both".equals(donation.getDonationType())) {
                totalInKind += donation.getInKindAmount();
            }
        }

        donationUI.displayTotalDonationsReport(totalCash, totalInKind);
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
                    selection = "Household";
                case 5 ->
                    selection = "Medical Supplies";
                case 6 ->
                    selection = "Personal Care";
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
