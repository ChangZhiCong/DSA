/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.*;
/**
 *
 * @author user
 */
public class DoneeInitializer {
    //  Method to return a collection of with hard-coded entity values
  public MapInterface<String, Donee> initializeDonee() {
    MapInterface<String, Donee> doneeList = new LinkedHashMap();
    Donee donee1 = new Donee("John", "013-2345612", "john123@gmail.com", "individual");
    Donee donee2 = new Donee("Mei", "015-2545412", "mei123@gmail.com", "family");
    
    doneeList.put(donee1.getDoneeId(),donee1);
    doneeList.put(donee2.getDoneeId(),donee2);
    
    return doneeList;
  }

  public static void main(String[] args) {
    // To illustrate how to use the initializeProducts() method
    DoneeInitializer donee = new DoneeInitializer();
    MapInterface<String, Donee> doneeList = donee.initializeDonee();
  }
}

