/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedHashMap;
import adt.MapInterface;
import boundary.DonorManagementUI;
import dao.DonorDAO;
import entity.Donor;
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
                case 4 ->
                    searchDonor();
                default -> {
                    MessageUI.displayExitSubSystemMessage();
                    System.exit(0);
                }
            }
        } while (choice != 8);
    }

    public void addDonor() {
        donor = donorUI.inputDonorDetails();
        donorList.put(donor.getDonorId(), donor);

        donorDAO.saveToFile(donorList);

        donorUI.displaySucessAddDonorMessage();
        donorUI.printDonorDetails(donor);
    }

    public void searchDonor() {
        String searchDonorID = donorUI.inputDonorID();
        boolean validSearchDonor = donorList.containsKey(searchDonorID);

        if (!validSearchDonor) {
            donorUI.displayInvalidIDMessgae();
        } else {
            donorUI.displayValidIDMessage();
            donorUI.printDonorDetails(donorList.get(searchDonorID));
        }
    }
}
