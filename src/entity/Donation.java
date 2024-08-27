package entity;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class Donation {

    private String donationId;
    private String donationName;
    private String donationType;
    private double cashAmount;
    private int inKindAmount;
    private String donationCategory;
    private String donationDate;
    private String donorId;
    private String doneeId;

    public Donation() {

    }

    public Donation(String donationId, String donationName, String donationType, double cashAmount, int inKindAmount, String donationCategory, String donationDate, String donorId, String doneeId) {
        this.donationId = donationId;
        this.donationName = donationName;
        this.donationType = donationType;
        this.cashAmount = cashAmount;
        this.inKindAmount = inKindAmount;
        this.donationCategory = donationCategory;
        this.donationDate = donationDate;
        this.donorId = donorId;
        this.doneeId = doneeId;
    }

    public Donation(String[] parts) throws Exception {
        this.donationId = parts[0];
        this.donationName = parts[1];
        this.donationType = parts[2];
        this.cashAmount = Double.parseDouble(parts[3]);
        this.inKindAmount = Integer.parseInt(parts[4]);
        this.donationCategory = parts[5];
        this.donationDate = parts[6];
        this.donorId = parts[7];
        this.doneeId = parts[8];
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getDonationName() {
        return donationName;
    }

    public void setDonationName(String donationName) {
        this.donationName = donationName;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
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

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
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

//    @Override
//    public String toString() {
//        return "ID: " + getDonationId() + "\n"
//                + "Name: " + getDonationName() + "\n"
//                + "Type: " + getDonationType() + "\n"
//                + "Cash Amount: RM " + getCashAmount() + "\n"
//                + "In-Kind Amount: " + getInKindAmount() + "\n"
//                + "Category: " + getDonationCategory() + "\n"
//                + "Date: " + getDonationDate() + "\n"
//                + "Donor ID: " + getDonorId() + "\n"
//                + "Donee ID: " + getDoneeId();
//    }

    public String toCsvString() {
        return donationId + ","
                + donationName + ","
                + donationType + ","
                + cashAmount + ","
                + inKindAmount + ","
                + donationCategory + ","
                + donationDate + ","
                + donorId + ","
                + doneeId;
    }

}
