package dao;

import adt.*;
import entity.*;
import java.io.*;

/**
 *
 * @author user : Chang Zhi Cong
 */
public class DoneeDAO {

    private String fileName;

    public DoneeDAO() {
    }

    public DoneeDAO(String fileName) {
        this.fileName = fileName;
    }

    public void saveToFile(MapInterface<String, Donee> doneeList) {
        File file = new File(fileName);
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (MapEntryInterface<String, Donee> entry : doneeList.entrySet()) {
                    writer.println(entry.getValue().toCsvString());
                }
            }
        } catch (IOException ex) {
            System.out.println("Cannot save to file !");
        }
    }

    public MapInterface<String, Donee> retrieveFromFile() {
        File file = new File(fileName);
        MapInterface<String, Donee> doneeList = new LinkedHashMap();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0];
                Donee donee = new Donee(parts);
                doneeList.put(key, donee);
            }
        } catch (IOException ex) {
            System.out.println("Cannot read from file !");
        } finally {
            return doneeList;
        }
    }
}
