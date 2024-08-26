/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author user
 */
public class MessageUI {
    public static void displayExitSubSystemMessage() {
        System.out.println("\nThank you for using the subsystem , exiting the subsystem ...");
    }

    public static void displayMainSystemMessage() {
        System.out.println("\nThank you for using the system , exiting the main system ...");
    }
    
    public static void systemPause() {
        System.out.println("Press Any Key To Continue...");
          new java.util.Scanner(System.in).nextLine();
    }
}
