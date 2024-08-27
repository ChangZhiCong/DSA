/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author user : Chang Zhi Cong
 */
public class Donee {

    private String doneeId, doneeName, doneeContactNo, doneeEmail, doneeIdentity;
    private Date doneeRegDate;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // use to format date 
    private static int totalDonee = 0, totalIndividual = 0, totalFamily = 0, totalOrganisation = 0;

    public Donee() {
    }

    public Donee(String doneeName, String doneeContactNo, String doneeEmail, String doneeIdentity) {
        this.doneeId = "DE" + String.format("%04d", (int) (System.currentTimeMillis() % 10000));
        this.doneeName = doneeName;
        this.doneeContactNo = doneeContactNo;
        this.doneeEmail = doneeEmail;
        this.doneeIdentity = doneeIdentity;
        this.doneeRegDate = new Date();
    }

    public Donee(String[] parts) throws Exception {
        this.doneeId = parts[0];
        this.doneeName = parts[1];
        this.doneeContactNo = parts[2];
        this.doneeEmail = parts[3];
        this.doneeIdentity = parts[4];
        this.doneeRegDate = formatter.parse(parts[5]);
    }

    // getter method
    public String getDoneeId() {
        return doneeId;
    }

    public String getDoneeName() {
        return doneeName;
    }

    public String getDoneeContactNo() {
        return doneeContactNo;
    }

    public String getDoneeEmail() {
        return doneeEmail;
    }

    public String getDoneeIdentity() {
        return doneeIdentity;
    }

    public int getTotalDonee() {
        return totalDonee;
    }

    public int getTotalIndividual() {
        return totalIndividual;
    }

    public int getTotalFamily() {
        return totalFamily;
    }

    public int getTotalOrganisation() {
        return totalOrganisation;
    }

    public Date getDoneeRegDate() {
        return doneeRegDate;
    }

    // return data type is String as format the donee Reg date 
    public String getFormattedDoneeRegDate() {
        return formatter.format(doneeRegDate);
    }

    // for update purpose 
    public void setDoneeId(String doneeId) {
        this.doneeId = doneeId;
    }

    public void setDoneeName(String doneeName) {
        this.doneeName = doneeName;
    }

    public void setDoneeContactNo(String doneeContactNo) {
        this.doneeContactNo = doneeContactNo;
    }

    public void setDoneeEmail(String doneeEmail) {
        this.doneeEmail = doneeEmail;
    }

    public void setDoneeIdentity(String doneeIdentity) {
        this.doneeIdentity = doneeIdentity;
    }

    public void increaseTotalDonee() {
        totalDonee++;
    }

    public void increaseTotalIndividual() {
        totalIndividual++;
    }

    public void increaseTotalFamily() {
        totalFamily++;
    }

    public void increaseTotalOrganisation() {
        totalOrganisation++;
    }

    public void resetTotal() {
        totalDonee = 0;
        totalIndividual = 0;
        totalFamily = 0;
        totalOrganisation = 0;
    }

    @Override
    public String toString() {
        return "Donee ID : " + doneeId + "\nDonee Name : " + doneeName + "\nDonee Contact No : " + doneeContactNo + "\nDonee Email : " + doneeEmail + "\nDonee Identity : " + doneeIdentity + "\nDonee Registration Date : " + getFormattedDoneeRegDate() + "\n";
    }

    public String toCsvString() {
        return doneeId + "," + doneeName + "," + doneeContactNo + "," + doneeEmail + "," + doneeIdentity + "," + getFormattedDoneeRegDate();
    }
}
