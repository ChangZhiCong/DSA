/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedHashMap;
import adt.MapEntryInterface;
import adt.MapInterface;
import boundary.DonorManagementUI;
import dao.DonationDAO;
import dao.DonorDAO;
import entity.Donation;
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
    private MapInterface<String, Donation> donationList = new LinkedHashMap<>();
    private final DonorDAO donorDAO = new DonorDAO("donor.txt");
    private final DonationDAO donationDAO = new DonationDAO("donation.txt");
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
            donationList = donationDAO.retrieveFromFile();
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
                case 5 ->
                    listDonor();
                case 6 ->
                    filterDonor();
                case 7 ->
                    categoriseDonor();
                case 8 ->
                    generateDonorReport();
                default -> {
                    MessageUI.displayExitSubSystemMessage();
                    System.exit(0);
                }
            }
        } while (choice != 9);
    }

    //1. Add a new donor
    public void addDonor() {
        donor = inputDonorDetails();
        donorList.put(donor.getDonorId(), donor);
        donorDAO.saveToFile(donorList);
        donorUI.displaySucessAddDonorMessage();
        donorUI.printDonorDetails(donor);
        MessageUI.systemPause();
    }

    public Donor inputDonorDetails() {
        String donorName = donorUI.inputDonorName();
        String donorContactNo = donorUI.inputDonorContactNo();
        String donorEmail = donorUI.inputDonorEmail();
        int donorIdentityChoice = donorUI.selectDonorIdentity();
        int donorTypeChoice = donorUI.selectDonorType();
        System.out.println();

        String donorIdentity = validateDonorIdentity(donorIdentityChoice);
        String donorType = validateDonorType(donorTypeChoice);

        return new Donor(donorName, donorContactNo, donorEmail, donorIdentity, donorType);
    }

    public String validateDonorIdentity(int donorIdentityChoice) {
        if (donorIdentityChoice == 1) {
            return "Organisation";
        } else {
            return "Individual";
        }
    }

    public String validateDonorType(int donorTypeChoice) {
        return switch (donorTypeChoice) {
            case 1 ->
                "Public";
            case 2 ->
                "Government";
            default ->
                "Private";
        };
    }

    //2. Remove a donor
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

    //3. Update donors details
    public void updateDonor() {
        int choice;
        String updateDonorID = donorUI.inputDonorID();
        boolean validUpdateDonor = donorList.containsKey(updateDonorID);

        if (!validUpdateDonor) {
            donorUI.displayInvalidIDMessgae();
        } else {
            boolean isChanged = false;

            Donor oldDonorData = donorList.get(updateDonorID);
            donorUI.displayValidIDMessage();
            donorUI.printDonorDetails(oldDonorData);

            do {
                choice = donorUI.getUpdateDonorChoice();

                switch (choice) {
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
                        if (donorIdentityChoice == 1) {
                            newDonorIdentity = "Organisation";
                        } else {
                            newDonorIdentity = "Individual";
                        }
                        oldDonorData.setDonorIdentity(newDonorIdentity);
                        isChanged = true;
                    }
                    case 5 -> {
                        String newDonorType;
                        int donorTypeChoice = donorUI.selectDonorType();
                        switch (donorTypeChoice) {
                            case 1:
                                newDonorType = "Public";
                                break;
                            case 2:
                                newDonorType = "Government";
                                break;
                            default:
                                newDonorType = "Private";
                                break;
                        }
                        oldDonorData.setDonorType(newDonorType);
                        isChanged = true;
                    }
                    case 6 -> {
                        if (isChanged) {
                            donorList.put(oldDonorData.getDonorId(), oldDonorData);
                            donorDAO.saveToFile(donorList);
                            donorUI.displaySuccessUpdateDonorMessage();
                            donorUI.printDonorDetails(oldDonorData);
                            MessageUI.systemPause();
                        }
                    }
                    default ->
                        donorUI.displayInvalidMenuMessage();
                }

            } while (choice != 6);
        }
    }

    //4. Search donor details
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

    //5. List donors with all donations made
    public void listDonor() {
        donorUI.getListDonorDonationHeader();
        int totalDonor = 0, totalDonation = 0;

        for (MapEntryInterface<String, Donor> entryDonor : donorList.entrySet()) {
            totalDonor++;
            int donationAmount = 0; //0 - no donation, 1 - only one donation, 2 - more than one donations   
            String donationName = "";
            String donationCategory = "";
            String donationType = "";

            double cashAmount = 0.0;
            int inKindAmount = 0;
            for (MapEntryInterface<String, Donation> entryDonation : donationList.entrySet()) {
                if (entryDonor.getValue().getDonorId().equals(entryDonation.getValue().getDonorId())) {
                    totalDonation++;
                    donationAmount++;
                    donationName = entryDonation.getValue().getDonationName();
                    donationCategory = entryDonation.getValue().getDonationCategory();
                    donationType = entryDonation.getValue().getDonationType();
                    cashAmount = entryDonation.getValue().getCashAmount();
                    inKindAmount = entryDonation.getValue().getInKindAmount();
                    if (donationAmount == 1) {
                        donorUI.printAllDonorWithDonation(entryDonor.getValue(), donationName, donationType, cashAmount, inKindAmount, donationCategory);
                    } else {
                        donorUI.printAllDonorWithManyDonation(donationName, donationType, cashAmount, inKindAmount, donationCategory);
                    }
                }

            }

            if (donationAmount == 0) {
                donorUI.printAllDonorWithNoDonation(entryDonor.getValue());
            }
        }

        donorUI.printAllDonorWithDonationFooter(totalDonor, totalDonation);

        MessageUI.systemPause();
    }

    //6. Filter donor based on criteria
    public void filterDonor() {
        int choice;
        do {
            choice = donorUI.getDonorFilterChoice();
            switch (choice) {
                case 1 -> {
                    String enteredName = donorUI.inputDonorName();
                    donorUI.getListNameContainsHeader(enteredName);
                    listDonor(enteredName);
                }

                case 2 -> {
                    int donorIdentityChoice = donorUI.selectDonorIdentity();
                    String donorIdentity;
                    if (donorIdentityChoice == 1) {
                        donorIdentity = "Organisation";
                    } else {
                        donorIdentity = "Individual";
                    }
                    donorUI.getListIdentityDonorHeader(donorIdentity);
                    listDonor(donorIdentity);
                }

                case 3 -> {
                    int donorTypeChoice = donorUI.selectDonorType();
                    String donorType;
                    switch (donorTypeChoice) {
                        case 1:
                            donorType = "Public";
                            break;
                        case 2:
                            donorType = "Government";
                            break;
                        default:
                            donorType = "Private";
                            break;
                    }
                    donorUI.getListTypeDonorHeader(donorType);
                    listDonor(donorType);
                }

                case 4 -> {
                    int donorIdentityChoice = donorUI.selectDonorIdentity();
                    String donorIdentity;
                    if (donorIdentityChoice == 1) {
                        donorIdentity = "Organisation";
                    } else {
                        donorIdentity = "Individual";
                    }
                    int donorTypeChoice = donorUI.selectDonorType();
                    String donorType;
                    switch (donorTypeChoice) {
                        case 1:
                            donorType = "Public";
                            break;
                        case 2:
                            donorType = "Government";
                            break;
                        default:
                            donorType = "Private";
                            break;
                    }
                    String donorIdentityType = donorIdentity + " Identity and " + donorType + " Type";
                    donorUI.getListIdentityTypeDonorHeader(donorIdentityType);
                    listDonor(donorIdentityType);
                }

                case 5 -> {
                    return;
                }

                default ->
                    donorUI.displayInvalidMenuMessage();
            }
            MessageUI.systemPause();
        } while (choice != 5);
    }

    public void listDonor(String donorData) {
        int totalResult = 0;
        for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
            String donorIdentityType = entry.getValue().getDonorIdentity() + " Identity and " + entry.getValue().getDonorType() + " Type";
            if (donorIdentityType.equals(donorData)) {
                totalResult++;
                donorUI.printCertainIdentityTypeDonor(entry.getValue());
            } else if (entry.getValue().getDonorIdentity().equals(donorData)) { // for filter identity
                totalResult++;
                donorUI.printCertainIdentityDonor(entry.getValue());
            } else if (entry.getValue().getDonorType().equals(donorData)) { // for filter type
                totalResult++;
                donorUI.printCertainTypeDonor(entry.getValue());
            } else if (stringContains(entry.getValue().getDonorName(), donorData)) { //For filter donor names
                totalResult++;
                donorUI.printAllDonor(entry.getValue());
            }
        }
        donorUI.getFilterListFooter(totalResult);
    }

    public boolean stringContains(String container, String contained) {
        // Edge case handling
        if (contained == null || container == null) {
            return false;
        }
        if (contained.isEmpty()) {
            return true;
        }

        // Create arrays to count occurrences of each character
        int[] containerCharCount = new int[256];
        int[] containedCharCount = new int[256];

        // Count frequency of each character in the container string
        for (int i = 0; i < container.length(); i++) {
            containerCharCount[container.charAt(i)]++;
        }

        // Count frequency of each character in the contained string
        for (int i = 0; i < contained.length(); i++) {
            containedCharCount[contained.charAt(i)]++;
        }

        // Check if container has all characters of contained with sufficient frequency
        for (int i = 0; i < 256; i++) {
            if (containedCharCount[i] > containerCharCount[i]) {
                return false;
            }
        }
        return true;
    }

    //7. Categorise donors (type: government, private, public)
    public void categoriseDonor() {
        String choice;
        int totalGovernment = 0, totalPrivate = 0, totalPublic = 0;
        donorUI.getListCategorisedDonorHeader();
        donorUI.getGovernmentTypeHeader();
        for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
            if (entry.getValue().getDonorType().equals("Government")) {
                donorUI.printCertainTypeDonor(entry.getValue());
                totalGovernment++;
            }
        }
        donorUI.getGovernmentTypeFooter(totalGovernment);
        donorUI.getPrivateTypeHeader();
        for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
            if (entry.getValue().getDonorType().equals("Private")) {
                donorUI.printCertainTypeDonor(entry.getValue());
                totalPrivate++;
            }
        }
        donorUI.getPrivateTypeFooter(totalPrivate);
        donorUI.getPublicTypeHeader();
        for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
            if (entry.getValue().getDonorType().equals("Public")) {
                donorUI.printCertainTypeDonor(entry.getValue());
                totalPublic++;
            }
        }
        donorUI.getPublicTypeFooter(totalPublic);
        donorUI.getTypeFooter(totalGovernment + totalPrivate + totalPublic);
        MessageUI.systemPause();

    }

    //8. Generate summary reports
    public void generateDonorReport() {
        int choice;
        do {
            choice = donorUI.getReportMenuChoice();
            switch (choice) {
                case 1 -> {
                    increaseTotal();
                    donorUI.getCategoryReport(donor);
                    Donor.resetTotal();

                    MessageUI.systemPause();
                }
                case 2 -> {
                    String startDate = donorUI.inputStartDate();
                    String endDate = donorUI.inputEndDate();

                    donorUI.getActivityReportHeader(startDate, endDate);
                    dateComparison(startDate, endDate);

                    donorUI.displayActivityReport(donor);
                    Donor.resetTotal();

                    MessageUI.systemPause();
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

    public void increaseTotal() {
        for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
            Donor.increaseTotalDonor();
            switch (entry.getValue().getDonorIdentity() + entry.getValue().getDonorType()) {
                case "IndividualGovernment" -> {
                    Donor.increaseTotalIndividualGovernment();
                }
                case "IndividualPrivate" -> {
                    Donor.increaseTotalIndividualPrivate();
                }
                case "IndividualPublic" -> {
                    Donor.increaseTotalIndividualPublic();
                }
                case "OrganisationGovernment" -> {
                    Donor.increaseTotalOrganisationGovernment();
                }
                case "OrganisationPrivate" -> {
                    Donor.increaseTotalOrganisationPrivate();
                }
                default -> {
                    Donor.increaseTotalOrganisationPublic();
                }
            }
        }
    }

    public void dateComparison(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date formattedStartDate = sdf.parse(startDate);
            Date formattedEndDate = sdf.parse(endDate);

            for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
                Date originalDate = entry.getValue().getDonorRegDate();

                // compare the original Donor date with the new input date & display
                if (originalDate.compareTo(formattedStartDate) >= 0 && originalDate.compareTo(formattedEndDate) <= 0) {
                    donorUI.printAllDonor(entry.getValue());
                    Donor.increaseTotalDonor();
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
