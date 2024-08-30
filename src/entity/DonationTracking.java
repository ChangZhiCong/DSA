package entity;

/**
 *
 * @author Cheong Wei Zhe
 */
public class DonationTracking {

    private double totalCash;
    private int totalInKindItems;

    public DonationTracking() {
        this.totalCash = 0.0;
        this.totalInKindItems = 0;
    }

    public void addDonation(Donation donation) {
        this.totalCash += donation.getCashAmount();
        this.totalInKindItems += donation.getInKindAmount();
    }

    public double getTotalCash() {
        return totalCash;
    }

    public int getTotalInKindItems() {
        return totalInKindItems;
    }
}
