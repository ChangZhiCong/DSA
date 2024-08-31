/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.LinkedHashMap;
import adt.MapEntryInterface;
import adt.MapInterface;
import entity.Donor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Lee Wai Xian
 */
public class DonorDAO {
    private String fileName;

    public DonorDAO() {
    }

    public DonorDAO(String fileName) {
        this.fileName = fileName;
    }

    public void saveToFile(MapInterface<String, Donor> donorList) {
        File file = new File(fileName);
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (MapEntryInterface<String, Donor> entry : donorList.entrySet()) {
                    writer.println(entry.getValue().toCsvString());
                }
            }
        } catch (IOException ex) {
            System.out.println("Cannot save to file !");
        }
    }

    public MapInterface<String, Donor> retrieveFromFile() {
        File file = new File(fileName);
        MapInterface<String, Donor> donorList = new LinkedHashMap();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0];
                Donor donor = new Donor(parts);
                donorList.put(key, donor);
            }
        } catch (IOException ex) {
            System.out.println("Cannot read from file !");
        } finally {
            return donorList;
        }
    }
}
