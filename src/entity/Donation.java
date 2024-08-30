package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author user : Cheong Wei Zhe
 */
public class Donation {

    private String donationId;
    private String donationType;
    private String inKindItem;
    private double cashAmount;
    private int inKindAmount;
    private String donationCategory;
    private LocalDate donationDate;
    private String donorId;
    private String doneeId;

    public Donation() {
    }

    public Donation(String donationId, String donationType, String inKindItem, double cashAmount, int inKindAmount, String donationCategory, LocalDate donationDate, String donorId, String doneeId) {
        this.donationId = donationId;
        this.donationType = donationType;
        this.inKindItem = inKindItem;
        this.cashAmount = cashAmount;
        this.inKindAmount = inKindAmount;
        this.donationCategory = donationCategory;
        this.donationDate = donationDate;
        this.donorId = donorId;
        this.doneeId = doneeId;
    }

    public Donation(String[] parts) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.donationId = parts[0];
        this.donationType = parts[1];
        this.inKindItem = parts[2];
        this.cashAmount = Double.parseDouble(parts[3]);
        this.inKindAmount = Integer.parseInt(parts[4]);
        this.donationCategory = parts[5];
        this.donationDate = LocalDate.parse(parts[6], formatter);
        this.donorId = parts[7];
        this.doneeId = parts[8];
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getInKindItem() {
        return inKindItem;
    }

    public void setInKindItem(String inKindItem) {
        this.inKindItem = inKindItem;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public int getInKindAmount() {
        return inKindAmount;
    }

    public void setInKindAmount(int inKindAmount) {
        this.inKindAmount = inKindAmount;
    }

    public String getDonationCategory() {
        return donationCategory;
    }

    public void setDonationCategory(String donationCategory) {
        this.donationCategory = donationCategory;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDoneeId() {
        return doneeId;
    }

    public void setDoneeId(String doneeId) {
        this.doneeId = doneeId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Donation ID   : " + getDonationId() + "\n"
                + "Type          : " + getDonationType() + "\n"
                + "In-Kind Item  : " + getInKindItem() + "\n"
                + "Cash Amount   : RM " + getCashAmount() + "\n"
                + "In-Kind Amount: " + getInKindAmount() + "\n"
                + "Category      : " + getDonationCategory() + "\n"
                + "Date          : " + getDonationDate().format(formatter) + "\n"
                + "Donor ID      : " + getDonorId() + "\n"
                + "Donee ID      : " + getDoneeId() + "\n";
    }

    public String toCsvString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return donationId + ","
                + donationType + ","
                + inKindItem + ","
                + cashAmount + ","
                + inKindAmount + ","
                + donationCategory + ","
                + donationDate.format(formatter) + ","
                + donorId + ","
                + doneeId;
    }
}
