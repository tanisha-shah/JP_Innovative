// Package name - this file belongs to the service.smart package
package service.smart;

// We need ArrayList to store all notifications
import java.util.ArrayList;

// We need Notification class from model package
import model.Notification;

// NotificationService class - this is a SMART FEATURE
// It handles all notifications in the system
// When a new drive is posted or application status changes
// a notification is sent to the student
public class NotificationService {

    // This ArrayList works as our database to store all notifications
    private ArrayList<Notification> notificationList;

    // This counter helps us give unique ID to each new notification
    private int idCounter;

    // --- Constructor ---
    public NotificationService() {
        notificationList = new ArrayList<Notification>();
        idCounter = 1; // first notification will get ID = 1
    }

    // =========================================================
    //                    ADD NOTIFICATION
    // =========================================================

    // --- Add a new notification ---
    // This method creates a new notification and adds it to the list
    // userId  = the student ID who should receive this notification
    // message = the text message to show to the student
    public void addNotification(int userId, String message) {

        // Create a new Notification object with unique ID
        Notification newNotification = new Notification(idCounter, userId, message);
        idCounter++; // increase counter so next notification gets different ID

        // Add notification to our list
        notificationList.add(newNotification);

        // Print confirmation that notification was added
        System.out.println("🔔 Notification sent to User ID " + userId + ": " + message);
    }

    // =========================================================
    //                   GET NOTIFICATIONS
    // =========================================================

    // --- Get all notifications for a specific user ---
    // This method returns all notifications that belong to a student
    // userId = the student ID whose notifications we want
    // Returns ArrayList of Notification objects for that student
    public ArrayList<Notification> getNotifications(int userId) {

        // This list will store all notifications for this student
        ArrayList<Notification> userNotifications = new ArrayList<Notification>();

        // Loop through all notifications
        // Find ones that belong to this specific student
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getUserId() == userId) {
                userNotifications.add(notificationList.get(i));
            }
        }

        return userNotifications;
    }

    // =========================================================
    //                  DISPLAY NOTIFICATIONS
    // =========================================================

    // --- Display all notifications for a specific user ---
    // This method prints all notifications of a student on screen
    // Called when student wants to view their notifications
    public void displayNotifications(int userId) {

        // Get all notifications for this student
        ArrayList<Notification> userNotifications = getNotifications(userId);

        System.out.println("\n=============================");
        System.out.println("      YOUR NOTIFICATIONS     ");
        System.out.println("=============================");

        // Check if student has any notifications at all
        if (userNotifications.size() == 0) {
            System.out.println("📭 No notifications found for you.");
            System.out.println("   You will be notified when:");
            System.out.println("   - A new drive is posted");
            System.out.println("   - Your application status changes");
            System.out.println("=============================");
            return;
        }

        // Print total count of notifications
        System.out.println("📬 You have " + userNotifications.size() + " notification(s)");
        System.out.println("-----------------------------");

        // Print each notification one by one with a number
        for (int i = 0; i < userNotifications.size(); i++) {
            Notification n = userNotifications.get(i);
            System.out.println((i + 1) + ". 🔔 " + n.getMessage());
            System.out.println("   Notification ID : " + n.getId());
            System.out.println();
        }

        System.out.println("=============================");
    }

    // =========================================================
    //               HELPER / UTILITY METHODS
    // =========================================================

    // --- Notify all students about a new drive ---
    // This method sends a notification to a list of student IDs
    // Used when a new drive is posted and we want to notify students
    public void notifyStudentsAboutDrive(ArrayList<Integer> studentIds,
                                         String companyName, String role) {

        // Build the notification message
        String message = "New drive posted! " + companyName + " is hiring for " + role + ". Apply now!";

        // Loop through all student IDs and send each one a notification
        for (int i = 0; i < studentIds.size(); i++) {
            addNotification(studentIds.get(i), message);
        }

        System.out.println("✅ Drive notification sent to " + studentIds.size() + " student(s).");
    }

    // --- Notify a student about application status change ---
    // This method sends a notification when admin updates application status
    // studentId = which student to notify
    // companyName = which company's drive
    // status = new status like "Selected" or "Rejected"
    public void notifyApplicationStatus(int studentId, String companyName, String status) {

        // Build the message based on status
        String message = "";

        if (status.equalsIgnoreCase("Selected")) {
            // Good news - student got selected
            message = "🎉 Congratulations! You have been SELECTED for " + companyName + " drive!";
        } else if (status.equalsIgnoreCase("Rejected")) {
            // Bad news - student got rejected
            message = "😔 Sorry! You have been REJECTED for " + companyName + " drive. Keep trying!";
        } else {
            // Some other status update
            message = "Your application status for " + companyName + " has been updated to: " + status;
        }

        // Send the notification to the student
        addNotification(studentId, message);
    }

    // --- Get all notifications in the system ---
    // Returns the complete notification list
    // Used by Admin to see all notifications sent
    public ArrayList<Notification> getAllNotifications() {
        return notificationList;
    }

    // --- Display all notifications in system ---
    // Admin can use this to see every notification sent
    public void displayAllNotifications() {

        System.out.println("\n===== ALL NOTIFICATIONS IN SYSTEM =====");

        // Check if there are any notifications at all
        if (notificationList.size() == 0) {
            System.out.println("No notifications found in system.");
            System.out.println("=======================================");
            return;
        }

        System.out.println("Total Notifications : " + notificationList.size());
        System.out.println("---------------------------------------");

        // Print every notification in the list
        for (int i = 0; i < notificationList.size(); i++) {
            Notification n = notificationList.get(i);
            System.out.println((i + 1) + ". ID: " + n.getId()
                    + " | User ID: " + n.getUserId()
                    + " | Message: " + n.getMessage());
        }

        System.out.println("=======================================");
    }

    // --- Count notifications for a user ---
    // Returns how many notifications a student has
    public int countNotifications(int userId) {
        return getNotifications(userId).size();
    }

    // --- Clear all notifications for a user ---
    // Removes all notifications of a specific student
    // Like clearing your inbox
    public void clearNotifications(int userId) {

        // We will build a new list without this student's notifications
        ArrayList<Notification> remainingNotifications = new ArrayList<Notification>();

        // Keep all notifications that do NOT belong to this student
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getUserId() != userId) {
                remainingNotifications.add(notificationList.get(i));
            }
        }

        // Replace old list with new filtered list
        notificationList = remainingNotifications;

        System.out.println("✅ All notifications cleared for User ID: " + userId);
    }
}