/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.*;
import boundary.*;
import utility.*;
import adt.*;
import dao.DoneeDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user : Chang Zhi Cong
 */
public class DoneeManagement {

    private MapInterface<String, Donee> doneeList = new LinkedHashMap();
    private final DoneeDAO doneeDAO = new DoneeDAO("donee.txt");
    private Donee donee = new Donee();
    private final DoneeManagementUI doneeUI = new DoneeManagementUI();

    public void runDoneeManagement() {
        int choice;
        do {
            doneeUI.getDoneeLogo();
            if (doneeDAO.retrieveFromFile() != null) {
                doneeList = doneeDAO.retrieveFromFile();
            }
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
                    listDoneeDonation();
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
        } while (choice != 8);
    }

    public void addDonee() {
        donee = doneeUI.inputDoneeDetails();
        doneeList.put(donee.getDoneeId(), donee);

        doneeDAO.saveToFile(doneeList);

        doneeUI.displaySucessAddDoneeMessage();
        doneeUI.printDoneeDetails(donee);
    }

    public void removeDonee() {
        String removeDoneeID = doneeUI.inputDoneeID();

        boolean validRemove = removeFromFile(removeDoneeID);

        if (!validRemove) {
            doneeUI.displayInvalidIDMessgae();
        } else {
            doneeUI.displaySucessRemoveDoneeMessage();
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
                    case 1:
                        String newName = doneeUI.inputDoneeName();
                        oldDonee.setDoneeName(newName);
                        break;
                    case 2:
                        String newIdentity = doneeUI.inputDoneeIdentity();
                        oldDonee.setDoneeIdentity(newIdentity);
                        break;
                    case 3:
                        String newContactNo = doneeUI.inputDoneeContactNo();
                        oldDonee.setDoneeContactNo(newContactNo);
                        break;
                    case 4:
                        String newEmail = doneeUI.inputDoneeEmail();
                        oldDonee.setDoneeEmail(newEmail);
                        break;
                    case 5:
                        doneeList.put(oldDonee.getDoneeId(), oldDonee);
                        doneeDAO.saveToFile(doneeList);

                        doneeUI.displaySuccessUpdateDoneeMessage();
                        doneeUI.printDoneeDetails(oldDonee);
                    case 6:
                        return;
                    default:
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

    public void listDoneeDonation() {
        doneeUI.getListDoneeDonationHeader();
        for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
            doneeUI.printAllDoneeWithDonation(entry.getValue());
        }
    }

    public void listDonee(String doneeIdentity) {
        for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
            if (entry.getValue().getDoneeIdentity().equals(doneeIdentity)) {
                doneeUI.printAllDonee(entry.getValue());
            } else if (doneeIdentity.equals("")) {
                doneeUI.printAllDonee(entry.getValue());
            }
        }
    }

    public void filterDonee() {
        int choice;
        do {
            choice = doneeUI.getDoneeFilterChoice();
            switch (choice) {
                case 1 -> {
                    doneeUI.getListDoneeHeader();
                    doneeList.sortByAscending();
                    listDonee("");
                }

                case 2 -> {
                    doneeUI.getListDoneeHeader();
                    doneeList.sortByDescending();
                    listDonee("");
                }

                case 3 -> {
                    String doneeIdentity = doneeUI.inputDoneeIdentity();
                    doneeUI.getListDoneeHeader();
                    listDonee(doneeIdentity);
                }
                case 4 -> {
                    return;
                }

                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
        } while (choice != 3);
    }

    public void generateReport() {
        int choice;
        do {
            choice = doneeUI.getReportMenuChoice();
            switch (choice) {
                case 1 -> {
                    doneeUI.getCategoryReportHeader();
                    increaseTotal();

                    doneeUI.displayCategoryReport(donee);
                    donee.resetTotal();
                }
                case 2 -> {
                    String startDate = doneeUI.inputStartDate();
                    String endDate = doneeUI.inputEndDate();

                    doneeUI.getActivityReportHeader(startDate, endDate);
                    dateComparison(startDate, endDate);

                    doneeUI.displayActivityReport(donee);
                    donee.resetTotal();
                }

                case 3 -> {
                    return;
                }

                default ->
                    doneeUI.displayInvalidMenuMessage();
            }
        } while (choice != 3);
    }

    public void increaseTotal() {
        for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
            switch (entry.getValue().getDoneeIdentity()) {
                case "individual" ->
                    donee.increaseTotalIndividual();
                case "family" ->
                    donee.increaseTotalFamily();
                default ->
                    donee.increaseTotalOrganisation();
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

                // compare the original Donee date with the new input date & display
                if (originalDate.compareTo(formattedStartDate) >= 0 && originalDate.compareTo(formattedEndDate) <= 0) {
                    doneeUI.printAllDonee(entry.getValue());
                    donee.increaseTotalDonee(); 
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public boolean removeFromFile(String id) {
        boolean isRemoved = doneeList.remove(id);
        if (isRemoved) {
            doneeDAO.saveToFile(doneeList);
        }
        return isRemoved;
    }

    public static void main(String[] args) {
        DoneeManagement doneeManagement = new DoneeManagement();
        doneeManagement.runDoneeManagement();
    }
}
