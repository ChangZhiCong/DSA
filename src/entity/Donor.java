package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lee Wai Xian
 */
public class Donor {

    private String donorId, donorName, donorContactNo, donorEmail, donorIdentity, donorType;
    private Date donorRegDate;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static int totalDonor = 0, totalIndividualGovernment = 0, totalIndividualPrivate = 0, totalIndividualPublic = 0, totalOrganisationGovernment = 0, totalOrganisationPrivate = 0, totalOrganisationPublic = 0;

    public Donor() {
    }

    public Donor(String donorName, String donorContactNo, String donorEmail, String donorIdentity, String donorType) {
        donorId = "DR" + String.format("%04d", (int) (System.currentTimeMillis() % 10000));
        this.donorName = donorName;
        this.donorContactNo = donorContactNo;
        this.donorEmail = donorEmail;
        this.donorIdentity = donorIdentity;
        this.donorType = donorType;

        donorRegDate = new Date();
    }

    public Donor(String[] parts) throws Exception {
        this.donorId = parts[0];
        this.donorName = parts[1];
        this.donorContactNo = parts[2];
        this.donorEmail = parts[3];
        this.donorIdentity = parts[4];
        this.donorType = parts[5];
        this.donorRegDate = formatter.parse(parts[6]);
    }

    public String getDonorId() {
        return donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDonorContactNo() {
        return donorContactNo;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public String getDonorIdentity() {
        return donorIdentity;
    }

    public String getDonorType() {
        return donorType;
    }

    public Date getDonorRegDate() {
        return donorRegDate;
    }

    public static int getTotalDonor() {
        return totalDonor;
    }

    public static int getTotalIndividualGovernment() {
        return totalIndividualGovernment;
    }

    public static int getTotalIndividualPrivate() {
        return totalIndividualPrivate;
    }

    public static int getTotalIndividualPublic() {
        return totalIndividualPublic;
    }

    public static int getTotalOrganisationGovernment() {
        return totalOrganisationGovernment;
    }

    public static int getTotalOrganisationPrivate() {
        return totalOrganisationPrivate;
    }

    public static int getTotalOrganisationPublic() {
        return totalOrganisationPublic;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setDonorContactNo(String donorContactNo) {
        this.donorContactNo = donorContactNo;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public void setDonorIdentity(String donorIdentity) {
        this.donorIdentity = donorIdentity;
    }

    public void setDonorType(String donorType) {
        this.donorType = donorType;
    }

    public String getFormattedDonorRegDate() {
        return formatter.format(donorRegDate);
    }

    public static void increaseTotalDonor() {
        totalDonor++;
    }

    public static void increaseTotalIndividualGovernment() {
        totalIndividualGovernment++;
    }

    public static void increaseTotalIndividualPrivate() {
        totalIndividualPrivate++;
    }

    public static void increaseTotalIndividualPublic() {
        totalIndividualPublic++;
    }

    public static void increaseTotalOrganisationGovernment() {
        totalOrganisationGovernment++;
    }

    public static void increaseTotalOrganisationPrivate() {
        totalOrganisationPrivate++;
    }

    public static void increaseTotalOrganisationPublic() {
        totalOrganisationPublic++;
    }

    public static void resetTotal() {
        totalDonor = 0;
        totalIndividualGovernment = 0;
        totalIndividualPrivate = 0;
        totalIndividualPublic = 0;
        totalOrganisationGovernment = 0;
        totalOrganisationPrivate = 0;
        totalOrganisationPublic = 0;
    }

    @Override
    public String toString() {
        return "Donor ID : " + donorId
                + "\nDonor Name : " + donorName
                + "\nDonor Contact No : " + donorContactNo
                + "\nDonor Email : " + donorEmail
                + "\nDonor Identity : " + donorIdentity
                + "\nDonor Type : " + donorType
                + "\nDonor Registration Date : " + getFormattedDonorRegDate() + "\n";
    }

    public String toCsvString() {
        return donorId + ","
                + donorName + ","
                + donorContactNo + ","
                + donorEmail + ","
                + donorIdentity + ","
                + donorType + ","
                + getFormattedDonorRegDate();
    }
}
