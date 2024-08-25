/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author leewa
 */
public class Donor {

    private String donorId, donorName, donorContactNo, donorEmail, donorIdentity, donorType;
    private Date donorRegDate;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    //private static int totalDonor = 0, totalIndividual = 0, totalOrganisation = 0, totalGovernment = 0, totalPrivate = 0, totalPublic = 0;

    //Constructor
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

    //Getter
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

    //Setter
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

//Return data type is String as format the donorRegDate 
    public String getFormattedDonorRegDate() {
        return formatter.format(donorRegDate);
    }

//toString
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
