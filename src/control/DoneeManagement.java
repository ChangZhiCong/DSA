/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.*;
import boundary.*;
import utility.*;
import adt.*;
import dao.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user : Chang Zhi Cong
 */
public class DoneeManagement {                
    private MapInterface<String, Donee> doneeList = new LinkedHashMap();
    private MapInterface<String, Donation> donationList = new LinkedHashMap();

    private final DoneeDAO doneeDAO = new DoneeDAO("donee.txt");
    private final DonationDAO donationDAO = new DonationDAO("donation.txt");

    private final DoneeManagementUI doneeUI = new DoneeManagementUI();
    private Donee donee = new Donee();

    public void runDoneeManagement() {
        int choice;
        do {
            doneeUI.getDoneeLogo();
            doneeList = doneeDAO.retrieveFromFile();
            donationList = donationDAO.retrieveFromFile();

            choice = doneeUI.getDoneeMenuChoice();
            switch (choice) {
                case 1 ->
                    addDonee();
                case 2 ->
                    removeDonee();
                case 3 ->
                    updateDonee();
                case 4 ->
                    searchDonee();
                case 5 ->
                    selectListDonee();
                case 6 ->
                    filterDonee();
                case 7 ->
                    generateReport();
                case 8 -> {
                    MessageUI.displayExitSubSystemMessage();
                    System.exit(0);
                }
                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
            MessageUI.systemPause();
        } while (choice != 8);
    }

    public void addDonee() {
        donee = doneeUI.inputDoneeDetails();
        donee.setDoneeId(generateDoneeID());

        doneeList.put(donee.getDoneeId(), donee);
        doneeDAO.saveToFile(doneeList);

        doneeUI.displaySucessAddDoneeMessage();
        doneeUI.printDoneeDetails(donee);
    }

    public void removeDonee() {
        String removeDoneeID = doneeUI.inputDoneeID();

        boolean validRemove = doneeList.remove(removeDoneeID);

        if (!validRemove) {
            doneeUI.displayInvalidIDMessgae();
        } else {
            // cascade deletion 
            removeDonation(removeDoneeID);

            doneeDAO.saveToFile(doneeList);
            donationDAO.saveToFile(donationList);

            doneeUI.displaySucessRemoveDoneeMessage();
        }
    }

    public void removeDonation(String removeDoneeID) {
        for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
            if (entry.getValue().getDoneeId().equals(removeDoneeID)) {
                donationList.remove(entry.getKey());
            }
        }
    }

    public void updateDonee() {
        int choice;
        String updateDoneeID = doneeUI.inputDoneeID();
        boolean validUpdateDonee = doneeList.containsKey(updateDoneeID);

        if (!validUpdateDonee) {
            doneeUI.displayInvalidIDMessgae();
        } else {
            Donee oldDonee = doneeList.get(updateDoneeID);
            doneeUI.displayValidIDMessage();
            doneeUI.printDoneeDetails(oldDonee);

            do {
                choice = doneeUI.getUpdateDoneeChoice();
                switch (choice) {
                    case 1 -> {
                        String newName = doneeUI.inputDoneeName();
                        oldDonee.setDoneeName(newName);
                    }
                    case 2 -> {
                        String newIdentity = doneeUI.inputDoneeIdentity();
                        oldDonee.setDoneeIdentity(newIdentity);
                    }
                    case 3 -> {
                        String newContactNo = doneeUI.inputDoneeContactNo();
                        oldDonee.setDoneeContactNo(newContactNo);
                    }
                    case 4 -> {
                        String newEmail = doneeUI.inputDoneeEmail();
                        oldDonee.setDoneeEmail(newEmail);
                    }
                    case 5 -> {
                        doneeList.put(oldDonee.getDoneeId(), oldDonee);
                        doneeDAO.saveToFile(doneeList);

                        doneeUI.displaySuccessUpdateDoneeMessage();
                        doneeUI.printDoneeDetails(oldDonee);
                    }
                    case 6 -> {
                        return;
                    }
                    default ->
                        doneeUI.displayInvalidMenuMessage();
                }
            } while (choice != 6);
        }
    }

    public void searchDonee() {
        String searchDoneeID = doneeUI.inputDoneeID();
        boolean validSearchDonee = doneeList.containsKey(searchDoneeID);

        if (!validSearchDonee) {
            doneeUI.displayInvalidIDMessgae();
        } else {
            doneeUI.displayValidIDMessage();
            doneeUI.printDoneeDetails(doneeList.get(searchDoneeID));
        }
    }

    public void selectListDonee() {
        int choice;
        do {
            choice = doneeUI.getListDoneeChoice();
            switch (choice) {
                case 1 -> {
                    listDonee("");
                }
                case 2 ->
                    listDoneeDonation();
                case 3 -> {
                    return;
                }
                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
            MessageUI.systemPause();
        } while (choice != 3);
    }

    public void listDoneeDonation() {
        doneeUI.getListDoneeDonationHeader();
        for (MapEntryInterface<String, Donee> entryDonee : doneeList.entrySet()) {
            boolean hasDonation = false;

            StringBuilder donationCategory = new StringBuilder();
            StringBuilder donationType = new StringBuilder();

            double cashAmount = 0.0;
            int inKindAmount = 0;

            for (MapEntryInterface<String, Donation> entryDonation : donationList.entrySet()) {
                if (entryDonee.getValue().getDoneeId().equals(entryDonation.getValue().getDoneeId())) {
                    hasDonation = true;
                    donationCategory.append(entryDonation.getValue().getDonationCategory()).append(" ");
                    donationType.append(entryDonation.getValue().getDonationType()).append(" ");
                    cashAmount += entryDonation.getValue().getCashAmount();
                    inKindAmount += entryDonation.getValue().getInKindAmount();
                }
            }

            if (hasDonation) {
                doneeUI.printAllDoneeWithDonation(entryDonee.getValue(), donationCategory.toString(), donationType.toString(), cashAmount, inKindAmount);
            }
        }
    }

    public void listDonee(String doneeIdentity) {
        doneeUI.getListDoneeHeader();
        for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
            if (entry.getValue().getDoneeIdentity().equals(doneeIdentity) || doneeIdentity.equals("")) {
                doneeUI.printAllDonee(entry.getValue());
            }
        }
    }

    public void filterDonee() {
        int choice;
        do {
            choice = doneeUI.getFilterDoneeChoice();
            switch (choice) {
                case 1 ->
                    listDonee("individual");

                case 2 ->
                    listDonee("family");

                case 3 ->
                    listDonee("organisation");

                case 4 -> {
                    return;
                }

                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
            MessageUI.systemPause();
        } while (choice != 4);
    }

    public void generateReport() {
        int choice;
        do {
            choice = doneeUI.getReportMenuChoice();
            Donee.resetTotal();
            switch (choice) {
                case 1 -> {
                    doneeUI.getCategoryReportHeader();
                    increaseTotalByIdentity();
                    doneeUI.displayCategoryReport(donee);
                }
                case 2 -> {
                    String startDate = doneeUI.inputStartDate();
                    String endDate = doneeUI.inputEndDate();

                    doneeUI.getSummaryReportHeader(startDate, endDate);
                    dateComparison(startDate, endDate);

                    doneeUI.displaySummaryReport(donee);
                }

                case 3 -> {
                    return;
                }

                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
            MessageUI.systemPause();
        } while (choice != 3);
    }

    public void increaseTotalByIdentity() {
        for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
            Donee.increaseTotalDonee();
            switch (entry.getValue().getDoneeIdentity()) {
                case "individual" ->
                    Donee.increaseTotalIndividual();
                case "family" ->
                    Donee.increaseTotalFamily();
                default ->
                    Donee.increaseTotalOrganisation();
            }
        }
    }

    public void dateComparison(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date formattedStartDate = sdf.parse(startDate);
            Date formattedEndDate = sdf.parse(endDate);

            for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
                Date originalDate = entry.getValue().getDoneeRegDate();

                if (originalDate.compareTo(formattedStartDate) >= 0 && originalDate.compareTo(formattedEndDate) <= 0) {
                    doneeUI.printAllDoneeWithoutDate(entry.getValue());
                    Donee.increaseTotalDonee();
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public String generateDoneeID() {
        String prefix = "DE";
        String newDoneeId;

        do {
            int randomNumber = (int) (Math.random() * 9000) + 1000;
            newDoneeId = prefix + randomNumber;
        } while (doneeList.containsKey(newDoneeId));

        return newDoneeId;
    }

    public static void main(String[] args) {
        DoneeManagement doneeManagement = new DoneeManagement();
        doneeManagement.runDoneeManagement();
    }
}
