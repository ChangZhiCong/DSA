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
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static int totalDonee = 0, totalIndividual = 0, totalFamily = 0, totalOrganisation = 0;

    public Donee() {
    }

    public Donee(String doneeName, String doneeContactNo, String doneeEmail, String doneeIdentity) {
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

    public static int getTotalDonee() {
        return totalDonee;
    }

    public static int getTotalIndividual() {
        return totalIndividual;
    }

    public static int getTotalFamily() {
        return totalFamily;
    }

    public static int getTotalOrganisation() {
        return totalOrganisation;
    }

    public Date getDoneeRegDate() {
        return doneeRegDate;
    }

    public String getFormattedDoneeRegDate() {
        return formatter.format(doneeRegDate);
    }

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

    public static void increaseTotalDonee() {
        totalDonee++;
    }

    public static void increaseTotalIndividual() {
        totalIndividual++;
    }

    public static void increaseTotalFamily() {
        totalFamily++;
    }

    public static void increaseTotalOrganisation() {
        totalOrganisation++;
    }
    
    public static void resetTotal() {
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
