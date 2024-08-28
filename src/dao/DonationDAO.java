/**
 *
 * @author user : Cheong Wei Zhe
 */

package dao;

import adt.LinkedHashMap;
import adt.MapEntryInterface;
import adt.MapInterface;
import entity.Donation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DonationDAO {
    private String fileName;

    public DonationDAO() {
    }

    public DonationDAO(String fileName) {
        this.fileName = fileName;
    }

    public void saveToFile(MapInterface<String, Donation> donationList) {
        File file = new File(fileName);
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
                    writer.println(entry.getValue().toCsvString());
                }
            }
        } catch (IOException ex) {
            System.out.println("Cannot save to file !");
        }
    }

    public MapInterface<String, Donation> retrieveFromFile() {
        File file = new File(fileName);
        MapInterface<String, Donation> donationList = new LinkedHashMap();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0];
                Donation donation = new Donation(parts);
                donationList.put(key, donation);
            }
        } catch (IOException ex) {
            System.out.println("Cannot read from file !");
        } finally {
            return donationList;
        }
    }
    
    public void appendToFile(MapInterface<String, Donation> donationList) {
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            for (MapEntryInterface<String, Donation> entry : donationList.entrySet()) {
                writer.println(entry.getValue().toCsvString());
            }
        } catch (IOException ex) {
            System.out.println("Cannot append to file!");
        }
    }
}
