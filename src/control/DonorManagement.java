/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedHashMap;
import adt.MapEntryInterface;
import adt.MapInterface;
import boundary.DonorManagementUI;
import dao.DonorDAO;
import entity.Donor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import utility.MessageUI;

/**
 *
 * @author leewa
 */
public class DonorManagement {

    private MapInterface<String, Donor> donorList = new LinkedHashMap();
    private final DonorDAO donorDAO = new DonorDAO("donor.txt");
    private Donor donor = new Donor();
    private final DonorManagementUI donorUI = new DonorManagementUI();

    public static void main(String[] args) {
        DonorManagement donorManagement = new DonorManagement();
        donorManagement.runDonorManagement();
    }

    public void runDonorManagement() {
        int choice;
        do {
            donorUI.getDonorLogo();
            donorList = donorDAO.retrieveFromFile();
            choice = donorUI.getDonorMenu();
            switch (choice) {
                case 1 ->
                    addDonor();
                case 2 ->
                    removeDonor();
                case 3 ->
                    updateDonor();
                case 4 ->
                    searchDonor();
                case 8 ->
                    generateDonorReport();
                default -> {
                    MessageUI.displayExitSubSystemMessage();
                    System.exit(0);
                }
            }
        } while (choice != 9);
    }

    public void addDonor() {
        donor = donorUI.inputDonorDetails();
        donorList.put(donor.getDonorId(), donor);
        donorDAO.saveToFile(donorList);
        donorUI.displaySucessAddDonorMessage();
        donorUI.printDonorDetails(donor);
        MessageUI.systemPause();
    }
    
    public void removeDonor() {
        String removeDonorID = donorUI.inputDonorID();
        boolean validRemove = removeFromFile(removeDonorID);
        if (!validRemove) {
            donorUI.displayInvalidIDMessgae();
            MessageUI.systemPause();
        } else {
            donorUI.displaySucessRemoveDonorMessage();
            MessageUI.systemPause();
        }
    }

    public void searchDonor() {
        String searchDonorID = donorUI.inputDonorID();
        boolean validSearchDonor = donorList.containsKey(searchDonorID);

        if (!validSearchDonor) {
            donorUI.displayInvalidIDMessgae();
            MessageUI.systemPause();

        } else {
            donorUI.displayValidIDMessage();
            donorUI.printDonorDetails(donorList.get(searchDonorID));
            MessageUI.systemPause();
        }
    }
    
    public void updateDonor() {
        int choice;
        String updateDonorID = donorUI.inputDonorID();
        boolean validUpdateDonor = donorList.containsKey(updateDonorID);
        
        if(!validUpdateDonor){
            donorUI.displayInvalidIDMessgae();
        }
        else{
            boolean isChanged = false;
            
            Donor oldDonorData = donorList.get(updateDonorID);
            donorUI.displayValidIDMessage();
            donorUI.printDonorDetails(oldDonorData);
            
            do {
                choice = donorUI.getUpdateDonorChoice();
                
                switch(choice){
                    case 1 -> {
                        String newDonorName = donorUI.inputDonorName();
                        oldDonorData.setDonorName(newDonorName);
                        isChanged = true;
                    }
                    case 2 -> {
                        String newDonorContactNo = donorUI.inputDonorContactNo();
                        oldDonorData.setDonorContactNo(newDonorContactNo);
                        isChanged = true;
                    }
                    case 3 -> {
                        String newDonorEmail = donorUI.inputDonorEmail();
                        oldDonorData.setDonorEmail(newDonorEmail);
                        isChanged = true;
                    }
                    case 4 -> {
                        String newDonorIdentity;
                        int donorIdentityChoice = donorUI.selectDonorIdentity();
                        if(donorIdentityChoice == 1)
                            newDonorIdentity = "Organisation";
                        else
                            newDonorIdentity = "Individual";
                        oldDonorData.setDonorIdentity(newDonorIdentity);
                        isChanged = true;
                    }
                    case 5 -> {
                        String newDonorType;
                        int donorTypeChoice = donorUI.selectDonorType();
                        if(donorTypeChoice == 1)
                            newDonorType = "Public";
                        else if (donorTypeChoice == 2)
                            newDonorType = "Government";
                        else
                            newDonorType = "Private";
                        oldDonorData.setDonorType(newDonorType);
                        isChanged = true;
                    }
                    case 6 -> {
                        if(isChanged){
                            donorList.put(oldDonorData.getDonorId(), oldDonorData);
                            donorDAO.saveToFile(donorList);
                            donorUI.displaySuccessUpdateDonorMessage();
                            donorUI.printDonorDetails(oldDonorData);
                        }
                    }
                    default ->
                        donorUI.displayInvalidMenuMessage();
                }
                
            }while(choice != 6);
        }
    }
    
    public void generateDonorReport(){
        int choice;
        do {
            choice = donorUI.getReportMenuChoice();
            switch (choice) {
                case 1 -> {
//                    donorUI.getCategoryReportHeader();
//                    increaseTotal();
//
//                    donorUI.displayCategoryReport(donor);
//                    donor.resetTotal();
                }
                case 2 -> {
                    String startDate = donorUI.inputStartDate();
                    String endDate = donorUI.inputEndDate();

                    donorUI.getActivityReportHeader(startDate, endDate);
                    dateComparison(startDate, endDate);

                    donorUI.displayActivityReport(donor);
                    donor.resetTotal();
                }

                case 3 -> {
                    return;
                }

                default ->
                    donorUI.displayInvalidMenuMessage();
            }
        } while (choice != 3);
    }
    
    public boolean removeFromFile(String id) {
        boolean isRemoved = donorList.remove(id);
        if (isRemoved) {
            donorDAO.saveToFile(donorList);
        }
        return isRemoved;
    }
    
    public void dateComparison(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date formattedStartDate = sdf.parse(startDate);
            Date formattedEndDate = sdf.parse(endDate);

            for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
                Date originalDate = entry.getValue().getDonorRegDate();

                // compare the original Donee date with the new input date & display
                if (originalDate.compareTo(formattedStartDate) >= 0 && originalDate.compareTo(formattedEndDate) <= 0) {
                    donorUI.printAllDonor(entry.getValue());
                    donor.increaseTotalDonor();
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
