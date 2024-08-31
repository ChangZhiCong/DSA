package utility;

/**
 *
 * @author Cheong Wei Zhe
 * @author Chang Zhi Cong
 * @author Lee Wai Xian
 * 
 */
public class MessageUI {
    public static void displayExitSubSystemMessage() {
        System.out.println("\nThank you for using the subsystem , exiting the subsystem ...");
    }

    public static void displayMainSystemMessage() {
        System.out.println("\nThank you for using the system , exiting the main system ...");
    }
    
    public static void systemPause() {
        System.out.println("\nPress Any Key To Continue...");
          new java.util.Scanner(System.in).nextLine();
    }
}
